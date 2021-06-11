package fr.javafx.scene.control.viewport.planar.utils.minimap;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Viewport;
import fr.java.math.topology.Coordinate;
import fr.java.ui.Edges2D;
import fr.javafx.behavior.Visual;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlaneViewportMinimapVisual extends Pane implements Visual<PlaneViewportMinimapControl> {
	private final PlaneViewportMinimapControl control;

	public PlaneViewportMinimapVisual(PlaneViewportMinimapControl _control) {
		super();
		control = _control;
//		control . refreshRequestedProperty().addListener((_obs, _old, _new) -> { if(_new) refresh(); });
		setMouseTransparent(true);
	}

	@Override
	public PlaneViewportMinimapControl	 	getSkinnable() {
		return control;
	}

	@Override
	public Node 				getNode() {
		return this;
	}

	@Override
	public void 				dispose() {
		;
	}

	public void 				refresh() {
		getChildren().clear();

		Viewport.TwoDims<?, ?> vp           = control.getViewport();
		BoundingBox.TwoDims    modelBounds  = vp.getModelBounds();
		BoundingBox.TwoDims    viewBounds   = vp.getViewBounds();
		Dimension.TwoDims      windowBounds = vp.getWindow();
		Edges2D 			   ve           = vp.getEdges();
		Coordinate.TwoDims 	   va           = vp.getViewAnchor();
		double			 	   vs           = vp.getViewScale();

		double  qw    = getWidth()  / viewBounds.getWidth();
		double  qh    = getHeight() / viewBounds.getHeight();

		Rectangle modelDomain, viewDomain, windowDomain;

		viewDomain = new Rectangle(0, 0, qw * viewBounds.getWidth(), qh * viewBounds.getHeight());

		double mw = modelBounds.getWidth()  * vs;
		double mh = modelBounds.getHeight() * vs;
		double ww = windowBounds.getWidth();
		double wh = windowBounds.getHeight();
		double mx, my, wx, wy;

		if(vp.scaledModelOverWindow()) {
			mx = ve.getLeft();
			my = ve.getTop();

			wx = ve.getLeft() - va.getFirst();
			wy = ve.getTop()  - va.getSecond();
			
			modelDomain  = new Rectangle(qw*mx, qh*my, qw*mw, qh*mh);
			windowDomain = new Rectangle(qw*wx, qh*wy, qw*ww, qh*wh);

			getChildren().addAll(viewDomain, modelDomain, windowDomain);

		} else {
			mx = ve.getLeft() + va.getFirst();
			my = ve.getTop()  + va.getSecond();

			wx = ve.getLeft();
			wy = ve.getTop();

			modelDomain  = new Rectangle(qw*mx, qh*my, qw*mw, qh*mh);
			windowDomain = new Rectangle(qw*wx, qh*wy, qw*ww, qh*wh);

			getChildren().addAll(viewDomain, windowDomain, modelDomain);
		}

		modelDomain  . setStroke(Color.GREEN);
		modelDomain  . setFill(Color.TRANSPARENT);
		viewDomain   . setStroke(Color.RED);
		viewDomain   . setFill(Color.TRANSPARENT);
		windowDomain . setStroke(Color.BLUE);
		windowDomain . setFill(Color.TRANSPARENT);

		if( control.modelPaintProperty().get() != null )
			modelDomain.setFill( control.modelPaintProperty().get() );
	}

}
