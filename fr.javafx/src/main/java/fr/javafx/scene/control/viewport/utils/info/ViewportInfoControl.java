package fr.javafx.scene.control.viewport.utils.info;

import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.javafx.scene.control.AbstractFxControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ViewportInfoControl extends AbstractFxControl {

	ObjectProperty<Point2D> mouseProperty;
	Viewport<?, ?, ?, ?>  	viewport;

	public ViewportInfoControl(final Viewport<?, ?, ?, ?> _viewport) {
		super();
		mouseProperty = new SimpleObjectProperty<Point2D>();
		viewport      = _viewport;
	}

	public void					  	setViewport(Viewport<?, ?, ?, ?> _viewport) {
		viewport = _viewport;
	}
	public Viewport<?, ?, ?, ?>  	getViewport() {
		return viewport;
	}

	public ObjectProperty<Point2D> 	mouseProperty() {
		return mouseProperty;
	}

	@Override
	public void 					refresh() {
		;
	}

}
