package fr.javafx.scene.layout;

import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import javafx.scene.layout.Region;

import javafx.scene.transform.Scale;

import fr.javafx.lang.enums.ScaleBehavior;

@DefaultProperty("content")
public class ScalableContentRegion extends Region {

	private boolean					aspectScale			= true;
	private boolean					autoRescale			= true;
	private boolean 				manualReset;

	private final BooleanProperty	fitToWidthProperty	= new SimpleBooleanProperty(true);
	private final BooleanProperty	fitToHeightProperty	= new SimpleBooleanProperty(true);

	private final Property<Node>	contentPaneProperty	= new SimpleObjectProperty<>();
	private Scale					contentScaleTransform;
	private double					contentScaleWidth	= 1.0;
	private double					contentScaleHeight	= 1.0;

	private final DoubleProperty	minScaleXProperty	= new SimpleDoubleProperty(Double.MIN_VALUE);
	private final DoubleProperty	maxScaleXProperty	= new SimpleDoubleProperty(Double.MAX_VALUE);
	private final DoubleProperty	minScaleYProperty	= new SimpleDoubleProperty(Double.MIN_VALUE);
	private final DoubleProperty	maxScaleYProperty	= new SimpleDoubleProperty(Double.MAX_VALUE);

	private final ObjectProperty<ScaleBehavior> scaleBehavior = new SimpleObjectProperty<>(ScaleBehavior.ALWAYS);

	public ScalableContentRegion() {
		setContent(new Pane());

		needsLayoutProperty().addListener((_obs, _old, _new) -> {
			if(_new
				&& (getWidth() <= getPrefWidth() || getHeight() <= getPrefHeight())
				|| getPrefWidth() == USE_COMPUTED_SIZE
				|| getPrefHeight() == USE_COMPUTED_SIZE) {
				computeScale();
			}
		});

		fitToWidthProperty().addListener((_obs, _old, _new) -> requestLayout());
		fitToHeightProperty().addListener((_obs, _old, _new) -> requestLayout());
		scaleBehaviorProperty().addListener((_obs, _old, _new) -> requestLayout());
	}
	public ScalableContentRegion(Node content) {
		this();
		setContent(content);
	}

	public void 			setAspectScale(boolean _aspectScale) {
		aspectScale = _aspectScale;
	}
	public boolean 			isAspectScale() {
		return aspectScale;
	}

	public void 			setAutoRescale(boolean _autoRescale) {
		autoRescale = _autoRescale;
	}
	public boolean 			isAutoRescale() {
		return autoRescale;
	}

	public DoubleProperty 	minScaleXProperty() {
		return minScaleXProperty;
	}
	public void 			setMinScaleX(double s) {
		minScaleXProperty().set(s);
	}
	public double 			getMinScaleX() {
		return minScaleXProperty().get();
	}

	public DoubleProperty 	minScaleYProperty() {
		return minScaleYProperty;
	}
	public void 			setMinScaleY(double s) {
		minScaleYProperty().set(s);
	}
	public double 			getMinScaleY() {
		return minScaleYProperty().get();
	}

	public DoubleProperty 	maxScaleXProperty() {
		return maxScaleXProperty;
	}
	public void 			setMaxScaleX(double s) {
		maxScaleXProperty().set(s);
	}
	public double 			getMaxScaleX() {
		return maxScaleXProperty().get();
	}

	public DoubleProperty 	maxScaleYProperty() {
		return maxScaleYProperty;
	}
	public void 			setMaxScaleY(double s) {
		maxScaleYProperty().set(s);
	}
	public double 			getMaxScaleY() {
		return maxScaleYProperty().get();
	}

	public Property<Node> 	contentProperty() {
		return contentPaneProperty;
	}
	public final void 		setContent(Node content) {
		contentPaneProperty.setValue(content);
		content.setManaged(false);
		initContentPaneListener();

		contentScaleTransform = new Scale(1, 1);
		getContentScaleTransform().setPivotX(0);
		getContentScaleTransform().setPivotY(0);
		getContentScaleTransform().setPivotZ(0);
		getContent().getTransforms().add(getContentScaleTransform());

		getChildren().add(content);

		ChangeListener<Number> changeListener = (_obs, _old, _new) -> requestScale();

		getContentScaleTransform().setOnTransformChanged((event) -> requestLayout());

		minScaleXProperty().addListener(changeListener);
		minScaleYProperty().addListener(changeListener);
		maxScaleXProperty().addListener(changeListener);
		maxScaleYProperty().addListener(changeListener);

	}
	public Node 			getContent() {
		return contentPaneProperty.getValue();
	}

	public final Scale 		getContentScaleTransform() {
		return contentScaleTransform;
	}

	public void 			requestScale() {
		computeScale();
	}
	public void 			resetScale() {
		if(manualReset)
			return;

		manualReset = true;

		try {
			computeScale();
		} finally {
			manualReset = false;
		}
	}

	public final BooleanProperty 				fitToWidthProperty() {
		return fitToWidthProperty;
	}
	public void 								setFitToWidth(boolean value) {
		fitToWidthProperty().set(value);
	}
	public boolean 								isFitToWidth() {
		return fitToWidthProperty().get();
	}

	public final BooleanProperty 				fitToHeightProperty() {
		return fitToHeightProperty;
	}
	public void 								setFitToHeight(boolean value) {
		fitToHeightProperty().set(value);
	}
	public boolean 								isFitToHeight() {
		return fitToHeightProperty().get();
	}

	public final ObjectProperty<ScaleBehavior> 	scaleBehaviorProperty() {
		return scaleBehavior;
	}
	public void 								setScaleBehavior(ScaleBehavior behavior) {
		scaleBehaviorProperty().set(behavior);
	}
	public ScaleBehavior 						getScaleBehavior() {
		return scaleBehaviorProperty().get();
	}

	@Override
	protected void 			layoutChildren() {
		super.layoutChildren();
	}

	@Override
	protected double 		computeMinWidth(double d) {
		double result = getInsets().getLeft() + getInsets().getRight();

		// apply content width (including scale)
		result += getContent().prefWidth(d) * getMinScaleX();

		return result;
	}
	@Override
	protected double 		computeMinHeight(double d) {
		double result = getInsets().getTop() + getInsets().getBottom();

		// apply content width (including scale)
		result += getContent().prefHeight(d) * getMinScaleY();

		return result;
	}

	@Override
	protected double 		computePrefWidth(double d) {
		double result = getInsets().getLeft() + getInsets().getRight();

		// apply content width (including scale)
		result += getContent().prefWidth(d) * contentScaleWidth;

		return result;
	}
	@Override
	protected double 		computePrefHeight(double d) {
		double result = getInsets().getTop() + getInsets().getBottom();

		// apply content width (including scale)
		result += getContent().prefHeight(d) * contentScaleHeight;

		return result;
	}

	private void 			computeScale() {
		double realWidth = getContent().prefWidth(getLayoutBounds().getHeight());
		double realHeight = getContent().prefHeight(getLayoutBounds().getWidth());

		double leftAndRight = getInsets().getLeft() + getInsets().getRight();
		double topAndBottom = getInsets().getTop() + getInsets().getBottom();

		double contentWidth = getLayoutBounds().getWidth() - leftAndRight;
		double contentHeight = getLayoutBounds().getHeight() - topAndBottom;

		contentScaleWidth = contentWidth / realWidth;
		contentScaleHeight = contentHeight / realHeight;

		contentScaleWidth = Math.max(contentScaleWidth, getMinScaleX());
		contentScaleWidth = Math.min(contentScaleWidth, getMaxScaleX());

		contentScaleHeight = Math.max(contentScaleHeight, getMinScaleY());
		contentScaleHeight = Math.min(contentScaleHeight, getMaxScaleY());

		double resizeScaleW;
		double resizeScaleH;

		boolean partOfSceneGraph = true;

		if(isAspectScale()) {
			double scale = Math.min(contentScaleWidth, contentScaleHeight);

			if(getScaleBehavior() == ScaleBehavior.ALWAYS || manualReset) {

				getContentScaleTransform().setX(scale);
				getContentScaleTransform().setY(scale);

			} else if(getScaleBehavior() == ScaleBehavior.IF_NECESSARY) {
				if(scale < getContentScaleTransform().getX()
						&& getLayoutBounds().getWidth() > 0
						&& partOfSceneGraph) {

					getContentScaleTransform().setX(scale);
					getContentScaleTransform().setY(scale);
				}
			}

		} else if(getScaleBehavior() == ScaleBehavior.ALWAYS || manualReset) {

			getContentScaleTransform().setX(contentScaleWidth);
			getContentScaleTransform().setY(contentScaleHeight);

		} else if(getScaleBehavior() == ScaleBehavior.IF_NECESSARY) {
			if(contentScaleWidth < getContentScaleTransform().getX()
					&& getLayoutBounds().getWidth() > 0 && partOfSceneGraph) {
				getContentScaleTransform().setX(contentScaleWidth);

			}
			if(contentScaleHeight < getContentScaleTransform().getY()
					&& getLayoutBounds().getHeight() > 0 && partOfSceneGraph) {
				getContentScaleTransform().setY(contentScaleHeight);

			}
		}

		resizeScaleW = getContentScaleTransform().getX();
		resizeScaleH = getContentScaleTransform().getY();

		getContent().relocate(
				getInsets().getLeft(), getInsets().getTop());

		double realContentWidth;
		double realContentHeight;

		if(isFitToWidth()) {
			realContentWidth = contentWidth / resizeScaleW;
		} else {
			realContentWidth = contentWidth / contentScaleWidth;
		}

		if(isFitToHeight()) {
			realContentHeight = contentHeight / resizeScaleH;
		} else {
			realContentHeight = contentHeight / contentScaleHeight;
		}

		getContent().resize(realContentWidth, realContentHeight);

	}

	private void 			initContentPaneListener() {
		final Runnable layoutRequester = () -> {
			if(isAutoRescale()) {
				setNeedsLayout(false);
				if(getContent() instanceof Region)
					((Region) getContent()).requestLayout();

				requestLayout();
			}
		};
		final ChangeListener<Bounds>   boundsListener   = (_obs, _old, _new) -> { layoutRequester.run(); };
		final ChangeListener<Number>   numberListener   = (_obs, _old, _new) -> { layoutRequester.run(); };
		final ListChangeListener<Node> childrenListener = (_c) -> {
			while (_c.next()) {
				if (_c.wasPermutated()) {
					for (int i = _c.getFrom(); i < _c.getTo(); ++i) {
						// permutate
					}
				} else if (_c.wasUpdated()) {
					// update item
				} else {
					if (_c.wasRemoved()) {
						for (Node n : _c.getRemoved()) {
							n.boundsInLocalProperty().removeListener(boundsListener);
							n.layoutXProperty().removeListener(numberListener);
							n.layoutYProperty().removeListener(numberListener);
						}
					} else if (_c.wasAdded()) {
						for (Node n : _c.getAddedSubList()) {
							n.boundsInLocalProperty().addListener(boundsListener);
							n.layoutXProperty().addListener(numberListener);
							n.layoutYProperty().addListener(numberListener);
						}
					}
				}
			}
		};

		if(getContent() instanceof Pane)
			((Pane) getContent()).getChildren().addListener(childrenListener);
	}

}
