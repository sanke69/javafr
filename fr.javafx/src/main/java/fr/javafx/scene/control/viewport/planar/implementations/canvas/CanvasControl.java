package fr.javafx.scene.control.viewport.planar.implementations.canvas;

import fr.drawer.fx.DrawerFx;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.PlaneViewportAdapter;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.window.SimpleEdges2D;
import fr.javafx.scene.canvas.ResizableCanvas;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Skin;

public class CanvasControl<MODEL, COORD extends Coordinate.TwoDims> extends PlaneViewportControl<MODEL, COORD> {

	public interface Plugin<MODEL, COORD extends Coordinate.TwoDims> extends PlaneViewportControl.Plugin<MODEL, COORD> {

		public DrawerFx drawer();
		public void 	setViewportControl(CanvasControl<MODEL, COORD> vpControl);

	}

	private final ResizableCanvas canvas;

	public CanvasControl() {
		this(60, new PlaneViewportAdapter<>());
	}
	public CanvasControl(int _fps) {
		this(_fps, new PlaneViewportAdapter<>());
	}
	public CanvasControl(Viewport.TwoDims.Editable<MODEL, COORD> _viewport) {
		this(60, _viewport);
	}
	public CanvasControl(int _fps, Viewport.TwoDims.Editable<MODEL, COORD> _viewport) {
		super(_fps, _viewport);

		canvas = new ResizableCanvas(1, 1);
		canvas . widthProperty()  . bind(widthProperty());
		canvas . heightProperty() . bind(heightProperty());

		getViewport().setEdges	(new SimpleEdges2D());
		getViewport().setWindow	(BoundingBoxes.of(this));
	}

	public Canvas 			canvas() {
		return canvas;
	}

	public Skin<?> 			createDefaultSkin() {
		return new Skin<CanvasControl<MODEL, COORD>>() {

			@Override
			public CanvasControl<MODEL, COORD> 		getSkinnable() {
				return CanvasControl.this;
			}

			@Override
			public ResizableCanvas 					getNode() {
				return canvas;
			}

			@Override
			public void 							dispose() {
				;
			}

		};
	}

	public void 			refresh() {
		refreshRequested.set(false);
	}

}
