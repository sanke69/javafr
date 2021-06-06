package fr.javafx.scene.layout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public class ScalablePane extends Pane {
	private final DoubleProperty scaleProperty;

	public ScalablePane() {
		this(new SimpleDoubleProperty(1.0));
	}
	public ScalablePane(DoubleProperty _scaleProperty) {
		scaleProperty = _scaleProperty;
		scaleXProperty().bind(scaleProperty);
		scaleYProperty().bind(scaleProperty);
	}

	public double getScale() {
		return scaleProperty.get();
	}

	public void setScale(double scale) {
		scaleProperty.set(scale);
	}

	public DoubleProperty scaleProperty() {
		return scaleProperty;
	}

	public void setPivot(double x, double y) {
		setTranslateX(getTranslateX() - x);
		setTranslateY(getTranslateY() - y);
	}

}

