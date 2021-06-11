package fr.javafx.windows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.java.patterns.capabilities.Selectable;
import fr.javafx.utils.FxNodeUtils;
import fr.javafx.windows.behaviors.DefaultWindowControlBehavior;
import fr.javafx.windows.behaviors.DefaultWindowStageBehavior;
import fr.javafx.windows.behaviors.WindowBehavior;
import fr.javafx.xtra.Clipboard;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowControl extends Control implements Window, Styleable {

	static class StyleableProperties {

		static final CssMetaData<WindowControl, Paint>	SELECTION_BORDER_COLOR = 
				new CssMetaData<WindowControl, Paint>("-fx-selection-border-color", StyleConverter.getPaintConverter(), Color.GRAY) {
					@Override
					public boolean isSettable(WindowControl control) {
						return control.selectionBorderColor == null || !control.selectionBorderColor.isBound();
					}

					@Override
					public StyleableProperty<Paint> getStyleableProperty(WindowControl control) {
						return control.selectionBorderColorProperty();
					}
				};
		static final CssMetaData<WindowControl, Boolean> SELECTION_EFFECT_ENABLED = 
				new CssMetaData<WindowControl, Boolean>("-fx-selection-effect-enabled", StyleConverter.getBooleanConverter(), true) {
					@Override
					public boolean isSettable(WindowControl control) {
						return control.selectionBorderColor == null || !control.selectionBorderColor.isBound();
					}

					@Override
					public StyleableProperty<Boolean> getStyleableProperty(WindowControl control) {
						return control.selectionEffectEnabledProperty();
					}
				};

		private static final List<CssMetaData<? extends Styleable, ?>>	STYLEABLES;

		static {
			final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
			Collections.addAll(styleables, SELECTION_BORDER_COLOR, SELECTION_EFFECT_ENABLED);
			STYLEABLES = Collections.unmodifiableList(styleables);
		}
	}

	// Window
	private final StringProperty								titleProperty				= new SimpleStringProperty("Title");
	private final ObservableList<WindowIcon>					leftIcons					= FXCollections.observableArrayList();
	private final ObservableList<WindowIcon>					rightIcons					= FXCollections.observableArrayList();
	private final ObjectProperty<Pane>							contentPaneProperty			= new SimpleObjectProperty<Pane>();

	// Selectable
	private final BooleanProperty								selectableProperty			= new SimpleBooleanProperty(true);
	private final BooleanProperty								selectedProperty			= new SimpleBooleanProperty(false);
	// Moveable
	private final BooleanProperty								movableProperty				= new SimpleBooleanProperty(true);
	private final BooleanProperty								movingProperty				= new SimpleBooleanProperty();
	// Resizeable
	private final BooleanProperty								resizableProperty			= new SimpleBooleanProperty(true);
	private final BooleanProperty								resizingProperty			= new SimpleBooleanProperty();

	private final BooleanProperty								minimizeProperty			= new SimpleBooleanProperty();

	// Closeable
	private final BooleanProperty 								closeRequested 				= new SimpleBooleanProperty();

	private final ObjectProperty<EventHandler<ActionEvent>>		onCloseActionProperty		= new SimpleObjectProperty<>();
	private final ObjectProperty<EventHandler<ActionEvent>>		onClosedActionProperty		= new SimpleObjectProperty<>();

	private final ObjectProperty<Transition>					closeTransitionProperty		= new SimpleObjectProperty<>();

	// Styleable
	private final StringProperty 								titleBarStyleClassProperty 	= new SimpleStringProperty("window-titlebar");

	private final DoubleProperty 								resizableBorderWidthProperty= new SimpleDoubleProperty(10);
	private StyleableBooleanProperty 							selectionEffectEnabled;
	private StyleableObjectProperty<Paint> 						selectionBorderColor;

	private boolean												moveToFront					= true;

	WindowStage													stage = null;
	WindowBehavior 												behavior;

	public static List<CssMetaData<? extends Styleable, ?>> 	getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}

	public WindowControl() {
		super();
		init();
	}
	public WindowControl(String _title) {
		super();
		setTitle(_title);
		init();
	}
	public WindowControl(WindowStage _stage) {
		super();
		stage = _stage;
		init();
	}
	public WindowControl(String _title, WindowStage _stage) {
		super();
		stage = _stage;
		setTitle(_title);
		init();
	}

	public boolean												isStaged() {
		return stage != null;
	}
	public Stage												getStage() {
		return isStaged() ? stage : (Stage) getParent().getScene().getWindow();
	}
	
	private void 												init() {
		getStyleClass().setAll(DEFAULT_STYLE_CLASS);
		setContentPane(new StackPane());

		closeTransitionProperty.addListener((_transition, _old, _new) -> {
				_new.statusProperty().addListener((_animationStatus, _oldStatus,  _newStatus) -> {
						if(_newStatus == Animation.Status.STOPPED) {
							if(WindowControl.this.getParent() == null)
								return;

							if(getOnCloseAction() != null)
								getOnCloseAction().handle(new ActionEvent(this, WindowControl.this));

							if(WindowControl.this.getParent() != null)
								FxNodeUtils.removeFromParent(WindowControl.this);

							requestClose(true);

							if(getOnClosedAction() != null)
								getOnClosedAction().handle(new ActionEvent(this, WindowControl.this));
						}
				});
			});

		ScaleTransition st = new ScaleTransition();
		st.setNode(this);
		st.setFromX(1);
		st.setFromY(1);
		st.setToX(0);
		st.setToY(0);
		st.setDuration(Duration.seconds(0.2));

		setCloseTransition(st);

		minimizedProperty().addListener((prop, oldValue, newValue) -> minimizeSelectedWindows(newValue));

//		initializeMovingPropertyMonitor();
//		initializeResizingPropertyMonitor();

		skinProperty().addListener((_obs, _old, _new) -> {
			if(behavior == null)
				behavior = stage == null ? new DefaultWindowControlBehavior(this) : new DefaultWindowStageBehavior(this, stage);
		});
	}
	
/*
	private static Timer timer = new Timer("window-Timer", true);
	private void 												initializeResizingPropertyMonitor() {
		boolean[] timerTaskSet = { false };
		double[] wh = { 0, 0 };

		InvalidationListener resizingListener = (ov) -> {
			if(!timerTaskSet[0]) {
				timerTaskSet[0] = true;
				timer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						double w = getWidth();
						double h = getHeight();
						Platform.runLater(() -> {
									resizingProperty.set(w != wh[0] || h != wh[1]);

									wh[0] = w;
									wh[1] = h;
									if(!isResizing()) {
										cancel();
										timerTaskSet[0] = false;
									}
								});
					}
				}, 0, 250);
			}
		};

		widthProperty().addListener(resizingListener);
		heightProperty().addListener(resizingListener);
	}
	private void 												initializeMovingPropertyMonitor() {
		boolean[] timerTaskSet = { false };
		double[]  xy           = { 0, 0 };

		InvalidationListener movingListener = (ov) -> {
			if(!timerTaskSet[0] && (getLayoutX() != 0 || getLayoutY() != 0)) {
				timerTaskSet[0] = true;
				timer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {

						double x = getLayoutX();
						double y = getLayoutY();
						Platform.runLater(() -> {
							movingProperty.set(x != xy[0] || y != xy[1]);

							xy[0] = x;
							xy[1] = y;
							if(!isMoving()) {
								cancel();
								timerTaskSet[0] = false;
							}
						});
					}

				}, 0, 250);
			}
		};

		layoutXProperty().addListener(movingListener);
		layoutYProperty().addListener(movingListener);
	}
*/
	@Override
	public List<CssMetaData<? extends Styleable, ?>> 			getControlCssMetaData() {
		return getClassCssMetaData();
	}
	@Override
	public String 												getUserAgentStylesheet() {
		return this.getClass().getResource(DEFAULT_STYLE).toExternalForm();
	}

	public StringProperty 										titleBarStyleClassProperty() {
		return titleBarStyleClassProperty;
	}
	public void 												setTitleBarStyleClass(String name) {
		titleBarStyleClassProperty.set(name);
	}
	public String 												getTitleBarStyleClass() {
		return titleBarStyleClassProperty.get();
	}

	public final StringProperty 								titleProperty() {
		return titleProperty;
	}
	public final void 											setTitle(String title) {
		this.titleProperty.set(title);
	}
	public final String 										getTitle() {
		return titleProperty.get();
	}

	public ObjectProperty<Pane> 								contentPaneProperty() {
		return contentPaneProperty;
	}
	public void 												setContentPane(Pane contentPane) {
		contentPaneProperty.setValue(contentPane);
	}
	public Pane 												getContentPane() {
		return contentPaneProperty.getValue();
	}

	public ObservableList<WindowIcon> 							getLeftIcons() {
		return leftIcons;
	}
	public ObservableList<WindowIcon> 							getRightIcons() {
		return rightIcons;
	}

	

	@Override
	public BooleanProperty 										selectableProperty() {
		return selectableProperty;
	}
	@Override
	public void 												setSelectable(boolean selectable) {
		selectableProperty.set(selectable);
	}
	public boolean 												isSelectable() {
		return selectableProperty.get();
	}

	@Override
	public ReadOnlyBooleanProperty 								selectedProperty() {
		return selectedProperty;
	}
	public boolean 												setSelected(boolean _selected) {
		if(!isSelectable())
			return false;

		selectedProperty.set(_selected);
		return true;
	}
	@Override
	public boolean 												isSelected() {
		return selectedProperty().get();
	}

	@Override
	public BooleanProperty 										moveableProperty() {
		return movableProperty;
	}
	@Override
	public void 												setMoveable(boolean v) {
		movableProperty.set(v);
	}
	@Override
	public boolean 												isMoveable() {
		return movableProperty.get();
	}

	@Override
	public ReadOnlyBooleanProperty 								movingProperty() {
		return movingProperty;
	}
	void 														setMoving(boolean _t) {
		movingProperty.set(_t);
	}
	@Override
	public boolean 												isMoving() {
		return movingProperty().get();
	}

	public BooleanProperty 										resizeableProperty() {
		return resizableProperty;
	}
	public void 												setResizable(boolean v) {
		resizableProperty.set(v);
	}
	public boolean 												isResizable() {
		return resizableProperty.get();
	}

	public ReadOnlyBooleanProperty 								resizingProperty() {
		return resizingProperty;
	}
	void 														setResizing(boolean _t) {
		resizingProperty.set(_t);
	}
	public boolean 												isResizing() {
		return resizingProperty().get();
	}

	public BooleanProperty 										minimizedProperty() {
		return minimizeProperty;
	}
	public void 												setMinimized(boolean v) {
		minimizeProperty.set(v);
	}
	public boolean 												isMinimized() {
		return minimizeProperty.get();
	}

	public ReadOnlyBooleanProperty								closeRequestedProperty() {
		return closeRequested;
	}
	public void													requestClose(boolean _t) {
		closeRequested.set(_t);
	}
	public boolean												isCloseRequested() {
		return closeRequested.get();
	}

	public void 												setMoveableToFront(boolean _moveToFront) {
		moveToFront = _moveToFront;
	}
	public boolean 												isMoveableToFront() {
		return moveToFront;
	}

	public DoubleProperty 										resizableBorderWidthProperty() {
		return resizableBorderWidthProperty;
	}
	public void 												setResizableBorderWidth(double v) {
		resizableBorderWidthProperty.set(v);
	}
	public double 												getResizableBorderWidth() {
		return resizableBorderWidthProperty.get();
	}


	// SELECT
	public StyleableBooleanProperty 							selectionEffectEnabledProperty() {
		if(selectionBorderColor == null)
			selectionEffectEnabled = new SimpleStyleableBooleanProperty(
					StyleableProperties.SELECTION_EFFECT_ENABLED,
					WindowControl.this, 
					"selectionEffectEnabled",
					true);
		return selectionEffectEnabled;
	}
	public void 												setSelectionEffectEnabled(boolean enabled) {
		this.selectionEffectEnabledProperty().set(enabled);
	}
	public boolean 												isSelectionEffectEnabled() {
		return selectionEffectEnabled == null ? true : selectionEffectEnabled.get();
	}

	public StyleableObjectProperty<Paint> 	selectionBorderColorProperty() {
		if(selectionBorderColor == null) {
			selectionBorderColor = new SimpleStyleableObjectProperty<>(
					StyleableProperties.SELECTION_BORDER_COLOR,
					WindowControl.this, "selectionBorderColor",
					new Color(0.3, 0.7, 1.0, 1.0));
		}
		return selectionBorderColor;
	}
	public void 												setSelectionBorderColor(Paint color) {
		selectionBorderColorProperty().set(color);
	}
	public Paint 												getSelectionBorderColor() {
		return selectionBorderColor == null ? new Color(0.3, 0.7, 1.0, 1.0) : selectionBorderColor.get();
	}

	// CLOSE 
	public void 												close() {
		if(this.getParent() == null)
			return;

		requestClose(true);
		closeSelectedWindows();

		if(getCloseTransition() != null) {
			getCloseTransition().play();
		} else {
			if(getOnCloseAction() != null) {
				getOnCloseAction().handle(new ActionEvent(this, WindowControl.this));
			}
			FxNodeUtils.removeFromParent(WindowControl.this);
			if(getOnClosedAction() != null) {
				getOnClosedAction().handle(new ActionEvent(this, WindowControl.this));
			}
		}
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> 	onCloseActionProperty() {
		return onCloseActionProperty;
	}
	public void 												setOnCloseAction(EventHandler<ActionEvent> onClosedAction) {
		this.onCloseActionProperty.set(onClosedAction);
	}
	public EventHandler<ActionEvent> 							getOnCloseAction() {
		return this.onCloseActionProperty.get();
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> 	onClosedActionProperty() {
		return onClosedActionProperty;
	}
	public void 												setOnClosedAction(EventHandler<ActionEvent> onClosedAction) {
		this.onClosedActionProperty.set(onClosedAction);
	}
	public EventHandler<ActionEvent> 							getOnClosedAction() {
		return this.onClosedActionProperty.get();
	}

	public ReadOnlyObjectProperty<Transition> 					closeTransitionProperty() {
		return closeTransitionProperty;
	}
	public void 												setCloseTransition(Transition t) {
		closeTransitionProperty.set(t);
	}
	public Transition 											getCloseTransition() {
		return closeTransitionProperty.get();
	}

	
	


	// TODO:: Must be defined in another class, typically one managing
	// clipboard and actions
	private void closeSelectedWindows() {
		for(Selectable sN : Clipboard.SelectableItem.getSelectedItems()) {
			if(sN == this || !(sN instanceof WindowControl))
				continue;

			WindowControl selectedWindow = (WindowControl) sN;

			if(selectedWindow.closeRequested.get())
				continue;

			if(this.getParent().equals(selectedWindow.getParent()))
				selectedWindow.close();
		}
	}
	private void minimizeSelectedWindows(boolean state) {
		for(Selectable sN : Clipboard.SelectableItem.getSelectedItems()) {
			if(sN == this || !(sN instanceof WindowControl))
				continue;

			WindowControl selectedWindow = (WindowControl) sN;
			if(selectedWindow.isMinimized() == state)
				continue;

			if(this.getParent().equals(selectedWindow.getParent()))
				selectedWindow.setMinimized(state);
		} // end for sN
	}


}
