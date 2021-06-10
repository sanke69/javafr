package fr.javafx.graph.behaviors;

import fr.java.maths.Numbers;
import fr.javafx.io.mouse.DragContext;
import fr.javafx.scene.layout.ScalablePane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class GTViewerFXBehavior {
	private final DoubleProperty zoomSpeedProperty = new SimpleDoubleProperty(1.2d);
	private final DoubleProperty maxScaleProperty  = new SimpleDoubleProperty(10.0d);
	private final DoubleProperty minScaleProperty  = new SimpleDoubleProperty(0.1d);

	private final DragContext    dragContext       = new DragContext();

	private final ScalablePane canvas;

	public GTViewerFXBehavior(ScalablePane _canvas) {
		super();
		canvas = _canvas;
	}

	public void 				applyTo(Parent parent) {
		parent.addEventHandler		(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
		parent.addEventHandler		(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
		parent.addEventHandler		(ScrollEvent.ANY,          this::onMouseScrolled);
	}
	public void 				unapplyFrom(Parent parent) {
		parent.removeEventHandler	(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
		parent.removeEventHandler	(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
		parent.removeEventHandler	(ScrollEvent.ANY,          this::onMouseScrolled);
	}

	public DoubleProperty 		minScaleProperty() {
		return minScaleProperty;
	}
	public void 				setMinScale(double _minScale) {
		minScaleProperty.set(_minScale);
	}
	public double 				getMinScale() {
		return minScaleProperty.get();
	}

	public DoubleProperty 		maxScaleProperty() {
		return maxScaleProperty;
	}
	public void 				setMaxScale(double maxScale) {
		maxScaleProperty.set(maxScale);
	}
	public double 				getMaxScale() {
		return maxScaleProperty.get();
	}

	public void 				setScaleBounds(double minScale, double maxScale) {
		minScaleProperty.set(minScale);
		maxScaleProperty.set(maxScale);
	}

	public DoubleProperty 		zoomSpeedProperty() {
		return zoomSpeedProperty;
	}
	public void 				setZoomSpeed(double zoomSpeed) {
		zoomSpeedProperty.set(zoomSpeed);
	}
	public double 				getZoomSpeed() {
		return zoomSpeedProperty.get();
	}

	public void 				onMousePressed(MouseEvent _me) {
		if(!_me.isSecondaryButtonDown())
			return;

		dragContext.setMouse(_me);
	}
	public void 				onMouseDragged(MouseEvent _me) {
		if(!_me.isSecondaryButtonDown())
			return;

		canvas.setTranslateX(canvas.getTranslateX() + _me.getSceneX() - dragContext.x);
		canvas.setTranslateY(canvas.getTranslateY() + _me.getSceneY() - dragContext.y);

		_me.consume();
	}
	public void 				onMouseScrolled(ScrollEvent _me) {
		double scale = canvas.getScale(); // currently we only use Y, same value is used for X
		final double oldScale = scale;

		if(_me.getDeltaY() < 0) {
			scale /= getZoomSpeed();
		} else {
			scale *= getZoomSpeed();
		}

		scale = Numbers.clamp(scale, minScaleProperty.get(), maxScaleProperty.get());

		final double f = (scale / oldScale) - 1;

		// maxX = right overhang, maxY = lower overhang
		final double maxX = canvas.getBoundsInParent().getMaxX() - canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getX();
		final double maxY = canvas.getBoundsInParent().getMaxY() - canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getY();

		// minX = left overhang, minY = upper overhang
		final double minX = canvas.localToParent(0, 0).getX() - canvas.getBoundsInParent().getMinX();
		final double minY = canvas.localToParent(0, 0).getY() - canvas.getBoundsInParent().getMinY();

		// adding the overhangs together, as we only consider the width of canvas itself
		final double subX = maxX + minX;
		final double subY = maxY + minY;

		// subtracting the overall overhang from the width and only the left and upper overhang from the upper left point
		final double dx = (_me.getSceneX() - ((canvas.getBoundsInParent().getWidth() - subX) / 2 + (canvas.getBoundsInParent().getMinX() + minX)));
		final double dy = (_me.getSceneY() - ((canvas.getBoundsInParent().getHeight() - subY) / 2 + (canvas.getBoundsInParent().getMinY() + minY)));

		canvas.setScale(scale);

		// note: pivot value must be untransformed, i. e. without scaling
		canvas.setPivot(f * dx, f * dy);

		_me.consume();
	}

}