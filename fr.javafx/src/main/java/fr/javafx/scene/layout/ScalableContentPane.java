package fr.javafx.scene.layout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class ScalableContentPane extends ContentPane {

	private boolean 		aspectScale = true;
	private boolean 		autoRescale = true;
	private boolean 		centerContent;

	private Scale          	contentScaleTransform;
	private double         	contentScaleWidth   = 1.0;
	private double         	contentScaleHeight  = 1.0;

	private DoubleProperty 	minScaleXProperty   = new SimpleDoubleProperty(Double.MIN_VALUE);
	private DoubleProperty 	maxScaleXProperty   = new SimpleDoubleProperty(Double.MAX_VALUE);
	private DoubleProperty 	minScaleYProperty   = new SimpleDoubleProperty(Double.MIN_VALUE);
	private DoubleProperty 	maxScaleYProperty   = new SimpleDoubleProperty(Double.MAX_VALUE);

	public ScalableContentPane() {
		super();
		setContentPane(new Pane());

		setPrefWidth(USE_PREF_SIZE);
		setPrefHeight(USE_PREF_SIZE);
	}

	public void 			setAspectScale(boolean _aspectScale) {
		aspectScale = _aspectScale;
	}
	public boolean 			isAspectScale() {
		return aspectScale;
	}

	public void    			setAutoRescale(boolean _autoRescale) {
		autoRescale = _autoRescale;
	}
	public boolean 			isAutoRescale() {
		return autoRescale;
	}

	public void 			centerContent(boolean _state) {
		centerContent = _state;
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

	public final void 		setContentPane(Pane contentPane) {
		super.setContentPane(contentPane);
		contentPane.setManaged(false);
		initContentPaneListener();

		contentScaleTransform = new Scale(1, 1);
//		getContentScaleTransform().setPivotX(0);
//		getContentScaleTransform().setPivotY(0);
//		getContentScaleTransform().setPivotZ(0);
		getContentPane().getTransforms().add(getContentScaleTransform());

		getChildren().clear();
		getChildren().add(contentPane);
	}

	public final Scale 		getContentScaleTransform() {
		return contentScaleTransform;
	}

	@Override
	protected void 			layoutChildren() {
		super.layoutChildren();

		double realWidth     = getContentPane().prefWidth(0);
		double realHeigh     = getContentPane().prefHeight(0);

		double leftAndRight  = getInsets().getLeft() + getInsets().getRight();
		double topAndBottom  = getInsets().getTop()  + getInsets().getBottom();

		double contentWidth  = getWidth() - leftAndRight;
		double contentHeight = getHeight() - topAndBottom;

		contentScaleWidth  = contentWidth / realWidth;
		contentScaleWidth  = Math.max(contentScaleWidth, getMinScaleX());
		contentScaleWidth  = Math.min(contentScaleWidth, getMaxScaleX());
		
		contentScaleHeight = contentHeight / realHeigh;
		contentScaleHeight = Math.max(contentScaleHeight, getMinScaleY());
		contentScaleHeight = Math.min(contentScaleHeight, getMaxScaleY());

		if (isAspectScale()) {
			double scale = Math.min(contentScaleWidth, contentScaleHeight);
			contentScaleWidth = scale;
			contentScaleHeight = scale;
		}

		getContentScaleTransform().setX(contentScaleWidth);
		getContentScaleTransform().setY(contentScaleHeight);

		double offsetX = (getBoundsInParent().getWidth() - getContentPane().getBoundsInParent().getWidth()) / 2;
		double offsetY = (getBoundsInLocal().getHeight() - getContentPane().getBoundsInParent().getHeight()) / 2;

		if (!centerContent) {
			offsetX = 0;
			offsetY = 0;
		}

		getContentPane().relocate(getInsets().getLeft() + offsetX, getInsets().getTop() + offsetY);
		getContentPane().resize((contentWidth) / contentScaleWidth, (contentHeight) / contentScaleHeight);
	}

	@Override
	protected double 		computeMinWidth(double d) {
		double result = getInsets().getLeft() + getInsets().getRight() + 1;
		return result;
	}
	@Override
	protected double 		computeMinHeight(double d) {
		double result = getInsets().getTop() + getInsets().getBottom() + 1;
		return result;
	}

	@Override
	protected double 		computePrefWidth(double d) {
		double result = 1;
		return result;
	}
	@Override
	protected double 		computePrefHeight(double d) {
		double result = 1;
		return result;
	}

	private void 			initContentPaneListener() {
		final ChangeListener<Bounds>   boundsListener   = (_obs, _old, _new) -> { if(isAutoRescale()) requestLayout(); };
		final ChangeListener<Number>   numberListener   = (_obs, _old, _new) -> { if(isAutoRescale()) requestLayout(); };
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

		getContentPane().getChildren().addListener(childrenListener);

		if(!getContentPane().getChildren().isEmpty())
			for(Node child : getContentPane().getChildren()) {
				child.boundsInLocalProperty().addListener(boundsListener);
				child.layoutXProperty().addListener(numberListener);
				child.layoutYProperty().addListener(numberListener);
			}
	}

}
