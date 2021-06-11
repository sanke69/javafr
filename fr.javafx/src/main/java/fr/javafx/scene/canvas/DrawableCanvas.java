package fr.javafx.scene.canvas;

import java.util.ArrayList;
import java.util.List;

import fr.drawer.fx.DrawableAreaFx;
import fr.drawer.fx.Drawer4Fx;
import fr.drawer.fx.DrawerFx;
import fr.java.draw.Drawable;
import fr.javafx.lang.FxRefreshable;
import fr.javafx.lang.FxRefresher;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCanvas extends Canvas implements DrawableAreaFx, FxRefreshable.WithFPS {
	public static final Color defaultRefreshColor = Color.MAGENTA;

	protected final DoubleProperty   preferredFps;
	protected final BooleanProperty  refreshRequested;

	protected Color					 refreshColor;
	final List<Drawable>             draws;

	transient Drawer4Fx				 drawer;
	transient GraphicsContext 		 gc;

	protected DrawableCanvas() {
		this(0, 0, 60, false);
	}
	protected DrawableCanvas(boolean _registerAsRefreshable) {
		this(0, 0, 60, _registerAsRefreshable);
	}
	public    DrawableCanvas(double _width, double _height) {
		this(_width, _height, 60, true);
	}
	public    DrawableCanvas(double _width, double _height, boolean _registerAsRefreshable) {
		this(_width, _height, 60, _registerAsRefreshable);
	}
	public    DrawableCanvas(double _width, double _height, double _fps) {
		this(_width, _height, _fps, true);
	}
	public    DrawableCanvas(double _width, double _height, double _fps, boolean _registerAsRefreshable) {
		super(_width, _height);
		preferredFps      = new SimpleDoubleProperty(_fps);
		refreshRequested  = new SimpleBooleanProperty(false);
		preferredFps.addListener(FxRefreshable.fpsListener(this));

		refreshColor     = defaultRefreshColor;
		draws            = new ArrayList<Drawable>();

		final InvalidationListener invalider = (obs) -> {
			drawer = null;
			gc     = null;
		};

		widthProperty().addListener(invalider);
		heightProperty().addListener(invalider);

		if(_registerAsRefreshable)
			FxRefresher.registerControl(this);
	}

	@Override
	public GraphicsContext 			getGraphics() {
//		if(gc == null)
//			gc = getGraphicsContext2D();
		return getGraphicsContext2D(); //gc;
	}
	@Override
	public Drawer4Fx 				getDrawer() {
		if(drawer == null)
			drawer = new DrawerFx(this);
		return drawer;
	}

	@Override
	public void 					add(Drawable _drawable) {
		draws.add(_drawable);
	}
	@Override
	public void 					remove(Drawable _drawable) {
		draws.remove(_drawable);
	}

	@Override
	public boolean 					isRefreshRequested() {
		return refreshRequested.get();
	}

	@Override
	public double 					getPreferredFps() {
		return preferredFps.get();
	}

	@Override
	public DoubleProperty 			preferredFpsProperty() {
		return preferredFps;
	}

	@Override
	public void 					requestRefresh() {
		refreshRequested.set(true);
	}

	@Override
	public void 					refresh() {
//		DrawerFx.clear(getGraphics(), refreshColor);

		for(Drawable d : draws)
			d.draw(getDrawer());
		
		refreshRequested.set(false);
	}
	@Override
	public BooleanProperty 			refreshRequestedProperty() {
		return refreshRequested;
	}

}
