package fr.javafx.scene.control.viewport.planar.implementations.tiles.minimap;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.topology.Coordinate;
import fr.java.math.window.Edges2D;
import fr.java.patterns.tileable.TileViewport;
import fr.javafx.behavior.Visual;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileViewportMinimapVisual extends Pane implements Visual<TileViewportMinimapControl> {
	private final TileViewportMinimapControl control;

	public TileViewportMinimapVisual(TileViewportMinimapControl _control) {
		super();
		control = _control;
//		control . refreshRequestedProperty().addListener((_obs, _old, _new) -> { if(_new) refresh(); });
		setMouseTransparent(true);
	}

	@Override
	public TileViewportMinimapControl 	getSkinnable() {
		return control;
	}

	@Override
	public Node 						getNode() {
		return this;
	}

	@Override
	public void 						dispose() {
		;
	}

	public void 						refresh() {
		getChildren().clear();

		TileViewport<?, ?>     vp           = control.getViewport();
		BoundingBox.TwoDims    mapBounds    = vp.getMapBounds();
		BoundingBox.TwoDims    viewBounds   = vp.getViewBounds();
		Dimension.TwoDims      windowBounds = vp.getWindow();
		Edges2D 			   ve           = vp.getEdges();
		Coordinate.TwoDims 	   va           = vp.getViewAnchor();
		double			 	   ms           = vp.getMapScale();

		double  qw    = getWidth()  / viewBounds.getWidth();
		double  qh    = getHeight() / viewBounds.getHeight();

		Rectangle mapDomain, viewDomain, windowDomain;

		viewDomain = new Rectangle(0, 0, qw * viewBounds.getWidth(), qh * viewBounds.getHeight());

		double mw = mapBounds.getWidth()  * ms;
		double mh = mapBounds.getHeight() * ms;
		double ww = windowBounds.getWidth();
		double wh = windowBounds.getHeight();
		double mx, my, wx, wy;

		if(vp.scaledModelOverWindow()) {
			mx = ve.getLeft();
			my = ve.getTop();

			wx = ve.getLeft() - va.getFirst();
			wy = ve.getTop()  - va.getSecond();
			
			mapDomain  = new Rectangle(qw*mx, qh*my, qw*mw, qh*mh);
			windowDomain = new Rectangle(qw*wx, qh*wy, qw*ww, qh*wh);

			getChildren().addAll(viewDomain, mapDomain, windowDomain);

		} else {
			mx = ve.getLeft() + va.getFirst();
			my = ve.getTop()  + va.getSecond();

			wx = ve.getLeft();
			wy = ve.getTop();

			mapDomain  = new Rectangle(qw*mx, qh*my, qw*mw, qh*mh);
			windowDomain = new Rectangle(qw*wx, qh*wy, qw*ww, qh*wh);

			getChildren().addAll(viewDomain, windowDomain, mapDomain);
		}

		mapDomain    . setStroke(Color.GREEN);
		mapDomain    . setFill(Color.TRANSPARENT);
		viewDomain   . setStroke(Color.RED);
		viewDomain   . setFill(Color.TRANSPARENT);
		windowDomain . setStroke(Color.BLUE);
		windowDomain . setFill(Color.TRANSPARENT);
	}

}
