package fr.javafx.windows.skins;

import fr.javafx.utils.FxFontUtils;
import fr.javafx.windows.WindowControl;
import fr.javafx.windows.WindowIcon;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DefaultWindowSkin extends SkinBase<WindowControl> {

	private class WindowTitleBar extends HBox {

		private class TitleIconPane extends Pane {
			private final double spacing = 2;
	
			public TitleIconPane() {
				super();
				setManaged(false);
				setPrefWidth(USE_COMPUTED_SIZE);
				setMinWidth(USE_COMPUTED_SIZE);
			}
	
			@Override
			protected double 	computeMinWidth(double h) {
				return getHeight() * getChildren().size() + spacing * (getChildren().size() - 1);
			}
			@Override
			protected double 	computeMaxWidth(double h) {
				return computeMinWidth(h);
			}
	
			@Override
			protected double 	computePrefWidth(double h) {
				return computeMinWidth(h);
			}
	
			@Override
			protected void 		layoutChildren() {
				double width  = getHeight();
				double height = getHeight();
	
				int count = 0;
				for(Node n : getManagedChildren()) {
					double x = (width + spacing) * count;
	
					n.resizeRelocate(x, 0, width, height);
	
					count++;
				}
			}
	
		}

		private static final double offset = 40;
	
		WindowControl 				control;
		private final Pane 			leftIconPane;
		private final Pane 			rightIconPane;
		private final Text 			label = new Text();
	
		private final double 		iconSpacing = 3;
		private double 				originalTitleWidth;
	
		private float 				labelWidth;
	
		public WindowTitleBar(WindowControl w) {
			super();
	
			control = w;
			leftIconPane  = new TitleIconPane();
			rightIconPane = new TitleIconPane();
			getChildren().addAll(leftIconPane, label, rightIconPane);

			getStylesheets()	.setAll(w.getStylesheets());
			getStyleClass()		.setAll(WindowControl.DEFAULT_STYLE_CLASS_TITLEBAR);

			setManaged(false);
			setSpacing(8);

			control.boundsInParentProperty()
				.addListener((ov, t, t1) -> {
					if(control.getTitle() == null || getLabel().getText() == null || getLabel().getText().isEmpty())
						return;
		
					getLabel().setText(control.getTitle());
				});
		}
	
		public final Text 	getLabel() {
			return label;
		}
	
		public void 		setTitle(String title) {
			getLabel().setText(title);
	
			originalTitleWidth = getLabel().getBoundsInParent().getWidth();
	
			double maxIconWidth = Math.max(leftIconPane.getWidth(), rightIconPane.getWidth());
	
			if(originalTitleWidth + maxIconWidth * 2 + offset >= getWidth())
				getLabel().setText("...");
	
			labelWidth = (float) FxFontUtils.computeStringWidth(label.getFont(), title);
	
			requestLayout();
			requestParentLayout();
		}
	
		public void 		addLeftIcon(Node n) {
			leftIconPane.getChildren().add(n);
			requestLayout();
			requestParentLayout();
		}
		public void 		addRightIcon(Node n) {
			rightIconPane.getChildren().add(n);
			requestLayout();
			requestParentLayout();
		}
	
		public void 		removeLeftIcon(Node n) {
			leftIconPane.getChildren().remove(n);
			requestLayout();
			requestParentLayout();
		}
		public void 		removeRightIcon(Node n) {
			rightIconPane.getChildren().remove(n);
			requestLayout();
			requestParentLayout();
		}
	
		@Override
		public double 		computeMinWidth(double h) {
			double result    = super.computeMinWidth(h);
			double iconWidth = Math.max(leftIconPane.prefWidth(h), rightIconPane.prefWidth(h)) * 2;
	
			result = Math.max(result, iconWidth + labelWidth + getInsets().getLeft() + getInsets().getRight());
	
			return result + iconSpacing * 2 + offset;
		}
		@Override
		protected double 	computePrefWidth(double h) {
			return computeMinWidth(h);
		}
	
		@Override
		protected void 		layoutChildren() {
			super.layoutChildren();
	
			leftIconPane	.resizeRelocate(getInsets().getLeft(), getInsets().getTop(), leftIconPane.prefWidth(USE_PREF_SIZE), getHeight() - getInsets().getTop() - getInsets().getBottom());
	
			rightIconPane	.resize(rightIconPane.prefWidth(USE_PREF_SIZE), getHeight() - getInsets().getTop() - getInsets().getBottom());
			rightIconPane	.relocate(getWidth() - rightIconPane.getWidth() - getInsets().getRight(), getInsets().getTop());
		}
	
	}

	private final WindowControl 	control;
	private WindowTitleBar      	titleBar;
	private Pane          			contentPane;

	private Timeline 				minimizeTimeLine;
	private double 					controlHeight;

	public DefaultWindowSkin(WindowControl w) {
		super(w);
		control = w;
		init();
	}

	private void 		init() {
		titleBar = new WindowTitleBar(control);

		titleBar.setTitle(control.getTitle());
		for (WindowIcon i : control.getLeftIcons())
			titleBar.addLeftIcon(i);
		for (WindowIcon i : control.getRightIcons())
			titleBar.addRightIcon(i);

		titleBar.setStyle							(control.getStyle());
		titleBar.getStylesheets()			.setAll	(control.getStylesheets());
		titleBar.getStyleClass()			.setAll	(control.getTitleBarStyleClass());
		titleBar.getLabel().getStyleClass()	.setAll	(control.getTitleBarStyleClass());

		contentPane = new Pane();
		contentPane.getChildren().addAll(titleBar, control.getContentPane());

		getChildren().add(contentPane);

		cacheHint(true);

		initControlClipping();
		initListeners();
	}
	private void		initControlClipping() {
		Rectangle clipRectangle = new Rectangle();
		control.getContentPane().setManaged(false);
		control.getContentPane().setClip(clipRectangle);
		control.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
			clipRectangle.setX(2);
			clipRectangle.setY(2);
			clipRectangle.setWidth(newValue.getWidth() - 1);
			clipRectangle.setHeight(newValue.getHeight() - 1);
		});
	}
	private void 		initListeners() {
		titleBar.widthProperty()
			.addListener((ov) -> {
				titleBar.setPrefWidth(titleBar.minWidth(-1));
				layoutChildren(0, 0, control.getWidth(), control.getHeight());
			});

		control.styleProperty()
			.addListener((_style, _old, _new) ->  titleBar.setStyle(_new));

		control.titleBarStyleClassProperty()
			.addListener((_style, _old, _new) -> {
				titleBar.getStyleClass().setAll(_new);
				titleBar.getLabel().getStyleClass().setAll(_new);
			});

		control.getStylesheets()
			.addListener((Change<? extends String> change) -> {
				while (change.next()) {
					if (change.wasRemoved())
						for(String i : change.getRemoved())
							titleBar.getStylesheets().remove(i);
					else if (change.wasAdded())
						for(String i : change.getAddedSubList())
							titleBar.getStylesheets().add(i);
				}
			});

		control.titleProperty()
			.addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
				titleBar.setTitle(newValue);
				control.requestLayout();
			});
		control.getLeftIcons()
			.addListener((ListChangeListener.Change<? extends WindowIcon> change) -> {
				while (change.next()) {
					if (change.wasRemoved()) {
						for (WindowIcon i : change.getRemoved()) {
							titleBar.removeLeftIcon(i);
						}
					} else if (change.wasAdded()) {
						for (WindowIcon i : change.getAddedSubList()) {
							titleBar.addLeftIcon(i);
						}
					}
				}
			});
		control.getRightIcons()
			.addListener((ListChangeListener.Change<? extends WindowIcon> change) -> {
				while (change.next()) {
					if (change.wasRemoved()) {
						for (WindowIcon i : change.getRemoved()) {
							titleBar.removeRightIcon(i);
						}
					} else if (change.wasAdded()) {
						for (WindowIcon i : change.getAddedSubList()) {
							titleBar.addRightIcon(i);
						}
					}
				}
			});

		control.prefHeightProperty()
			.addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
				if(control.isMinimized() && control.getPrefHeight() < titleBar.minHeight(0) + control.getContentPane().minHeight(0))
					control.getContentPane().setVisible(false);
				else if(!control.isMinimized() && control.getPrefHeight() >= titleBar.minHeight(0) + control.getContentPane().minHeight(0))
					control.getContentPane().setVisible(true);
			});

		control.minimizedProperty()
			.addListener((prop, oldValue, newValue) -> {	
				if (minimizeTimeLine != null) {
					minimizeTimeLine.stop();
					minimizeTimeLine = null;
				}
	
				if(minimizeTimeLine == null && newValue)
					controlHeight = control.getPrefHeight();
	
				minimizeTimeLine = new Timeline(
												new KeyFrame(Duration.ZERO,         new KeyValue(control.prefHeightProperty(), control.getPrefHeight())),
												new KeyFrame(Duration.seconds(0.1), new KeyValue(control.prefHeightProperty(), newValue ? titleBar.getHeight() : controlHeight))
												);
				minimizeTimeLine.statusProperty()
						.addListener((ObservableValue<? extends Status> ov2, Status oldStatus, Status newStatus) -> {
							if(newStatus != Status.STOPPED)
								return ;

							if (newValue)
								control.getContentPane().setVisible(false);
							cacheHint(false);
							minimizeTimeLine = null;
						});
				minimizeTimeLine.play();
	
				cacheHint(true);
			});

		control.selectedProperty()
			.addListener((prop, oldValue, newValue) -> {
				Border prevBorder = control.getBorder();
	
				if (newValue) {
					control.setBorder(new Border(new BorderStroke(control.getSelectionBorderColor(), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2))));
	
					if (control.isSelectionEffectEnabled()) {
						ColorAdjust effect = new ColorAdjust(-0.25, 0.2, 0.8, 0);
	                    Glow 		glow   = new Glow(0.5);
	                    glow.setInput(effect);
						control.setEffect(effect);
					}
				} else {
					control.setBorder(prevBorder);
					control.setEffect(null);
				}
			});


//      control.resizingProperty().addListener((ov) -> {
//          control.setCache(!control.isResizing());
//      });
	}

	private void 		cacheHint(boolean _enabled) {
		if(_enabled) {
//          // restore cache hint
			getSkinnable().setCache(true);
			getSkinnable().setCacheHint(CacheHint.SPEED);
		} else {
			// temporarily disable cache hint due to rendering bugs
			getSkinnable().setCache(false);
			getSkinnable().setCacheHint(CacheHint.DEFAULT);
		}
	}
	
	@Override
	protected void 		layoutChildren(double x, double y, double w, double h) {
		super.layoutChildren(x, y, w, h);

		titleBar.relocate(0, 0);

		contentPane.relocate(0, 0);
		contentPane.resize(
				contentPane.getWidth() + getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight(),
				contentPane.getHeight() + getSkinnable().getInsets().getTop() + getSkinnable().getInsets().getBottom());

		double titleBarWidth = titleBar.computeMinWidth(-1);
		double windowWidth   = contentPane.getWidth();

		titleBar.resize(windowWidth - getSkinnable().getInsets().getLeft() - getSkinnable().getInsets().getRight(), titleBar.prefHeight(-1));

		if(titleBarWidth > contentPane.getWidth()) {
			control.setPrefWidth(titleBarWidth + getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight());

			control.autosize();
			control.layout();

			contentPane.autosize();
			contentPane.layout();

			titleBar.autosize();
			titleBar.layout();
		}
		titleBar.layoutChildren();

		double leftAndRight = getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight();
//		double topAndBottom = getSkinnable().getInsets().getTop() + getSkinnable().getInsets().getBottom();

		control.getContentPane().relocate(getSkinnable().getInsets().getLeft(), titleBar.prefHeight(-1));

		double rootW = contentPane.getWidth(); // Math.max(root.getWidth(), root.getMinWidth());
		double rootH = contentPane.getHeight();// Math.max(root.getHeight(), root.getMinHeight());

		double contentWidth  = rootW - leftAndRight;
		double contentHeight = rootH - getSkinnable().getInsets().getBottom() - titleBar.prefHeight(-1);

		control.getContentPane().resize(contentWidth, contentHeight);
	}

	@Override
	protected double 	computeMinWidth(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double result   = contentPane.minWidth(width);
		double minWidth = Math.max(titleBar.minWidth(width), 
								   control.getContentPane().minWidth(width) + getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight());

		return Math.max(result, minWidth);
	}
	public double 		computeMinWidth() {
		double result   = contentPane.minWidth(-1);
		double minWidth = Math.max(titleBar.minWidth(-1),
								   control.getContentPane().minWidth(-1) + getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight());

		return Math.max(result, minWidth);
	}

	@Override
	protected double 	computePrefWidth(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		return computeMinWidth(width, topInset, rightInset, bottomInset, leftInset);
	}

	@Override
	protected double 	computeMinHeight(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double result    = contentPane.minHeight(height);
		double minHeight = titleBar.prefHeight(height);

		if(!control.isMinimized() && control.getContentPane().isVisible())
			minHeight += control.getContentPane().minHeight(height) + getSkinnable().getInsets().getBottom();

		return Math.max(result, minHeight);
	}

}
