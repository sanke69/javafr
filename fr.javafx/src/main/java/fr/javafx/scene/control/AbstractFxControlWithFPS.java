package fr.javafx.scene.control;

import fr.javafx.lang.FxRefreshable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class AbstractFxControlWithFPS extends AbstractFxControl implements FxRefreshable.WithFPS {
	protected final DoubleProperty 	preferredFps = new SimpleDoubleProperty( 60 );

	public AbstractFxControlWithFPS() {
		this(-1D);
	}
	public AbstractFxControlWithFPS(double _fps) {
		super();
		preferredFps . set( _fps );
		preferredFps . addListener( FxRefreshable.fpsListener(this) );
	}

	public double					getPreferredFps() {
		return preferredFps != null && preferredFps.get() > 0 ? preferredFps.get() : -1;
	}

	@Override
	public DoubleProperty 			preferredFpsProperty() {
		return preferredFps;
	}

}
