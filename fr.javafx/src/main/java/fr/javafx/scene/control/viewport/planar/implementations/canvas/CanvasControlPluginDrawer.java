package fr.javafx.scene.control.viewport.planar.implementations.canvas;

import java.util.HashSet;
import java.util.Set;

import fr.drawer.fx.DrawerFx;
import fr.drawer.fx.ViewportDrawerFx;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Shape2D;
import fr.java.math.topology.Coordinate;
import fr.java.patterns.drawable.Drawable;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class CanvasControlPluginDrawer<MODEL, COORD extends Coordinate.TwoDims> implements CanvasControl.Plugin<MODEL, COORD> {
	private CanvasControl<MODEL, COORD>	control;
	private DrawerFx 				drawer;

	private final Set<Shape2D> 		shapes;
	private final Set<Drawable> 	drawables;

	public CanvasControlPluginDrawer() {
		super();
		shapes    = new HashSet<Shape2D>();
		drawables = new HashSet<Drawable>();
	}

	@Override
	public DrawerFx 	drawer() {
		return drawer;
	}
	public void redraw() {
		DrawerFx.clear(control.canvas().getGraphicsContext2D(), Colors.WHITE);

		for(Shape2D s : shapes)
			drawer.drawObject(s);

//		for(Drawable d : drawables)
//			d.draw(drawer);

	}

	@Override
	public void 				setViewportControl(CanvasControl<MODEL, COORD> _canvasControl) {
		control = _canvasControl;
		control . setFocusTraversable ( true );
		drawer  = new ViewportDrawerFx(_canvasControl.canvas(), _canvasControl.getViewport());

		control.refreshRequestedProperty().addListener((_obs, _old, _new) -> {
			if(_old && !_new) { // At the end of a refresh...
				redraw();
			}
		});
	}
	@Override
	public void 				setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		if(_pvpControl instanceof CanvasControl)
			setViewportControl((CanvasControl<MODEL, COORD>) _pvpControl);
		else
			System.err.println("::setViewportControl error");
	}
	public void 				unsetViewportControl() {
		if(control == null)
			return ;
		control = null;
		drawer  = null;
	}

	@Override
	public ObservableList<Node> getChildren() {
		return FXCollections.emptyObservableList();
	}

}
