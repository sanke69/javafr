package fr.javafx.scene.control.viewport.utils.statusbar;

import java.text.DecimalFormat;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;
import fr.java.patterns.tileable.TileCoordinate;
import fr.java.patterns.tileable.TileProvider;
import fr.java.patterns.tileable.TileViewport;
import fr.java.raster.XRaster;
import fr.javafx.scene.control.raster.RasterViewportControl;
import fr.javafx.scene.control.viewport.ViewportControl;
import fr.javafx.scene.control.viewport.planar.implementations.tiles.TileViewportControl;

class ViewportModels {
	private ViewportModels() {}

	public static String getCursorInfo(ViewportControl _control, Point2D _cursor) {
		StringBuffer  sb = new StringBuffer();
		/*
		ViewportPlane.Editable<?, ?>	vp     = _control.getViewport();
		BoundingBox.TwoDims				model  = vp.getModelBounds();
		Coordinate.Planar				c_mod  = (Coordinate.Planar) vp.windowInModel(_cursor);
		Point2D							cursor = Points.of( c_mod );
		
		if(cursor == null || model == null
			|| cursor.getX() == Double.NaN || cursor.getY() == Double.NaN
			|| (
				cursor.getX() < model.getMinX() || cursor.getX() > model.getMaxX()
				|| cursor.getY() < model.getMinY() || cursor.getY() > model.getMaxY()
					
			))
			return Points.NaN2.toString();

		StringBuffer  sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#");

		if(_control instanceof XRasterControl) {
			sb.append(xrasterCtrlCursorInfo((XRasterControl) _control, cursor, df));

		} else if(_control instanceof MapViewportControl) {
			TileProvider<?> tileProvider = ((MapViewportControl) _control).getTileProvider();

			sb.append(cursor.toString(df));

		} else {
//			System.err.println("No projector available...");
			sb.append(cursor.toString(df));

		}

*/
		return sb.toString();
	}

	private static String xrasterCtrlCursorInfo(RasterViewportControl _vp, Point2D _cursor, DecimalFormat _df) {	
		int x = (int) _cursor.getX();
		int y = (int) _cursor.getY();

		if(_vp.getRaster() != null) {
//			XRaster.BoundingBox box    = (XRaster.BoundingBox) _vp.getModel();
			XRaster             raster = _vp.getRaster();

			if(_cursor != null && _cursor.getX() != Double.NaN) {
				Point2D cursor = Points.of(Math.ceil(_cursor.getX()), Math.ceil(_cursor.getY()));
				return cursor.toString(_df) + ":" + "[" + raster.getRed(x, y) + ", " + raster.getGreen(x, y) + ", " + raster.getBlue(x, y) + "]";
			} else
				return "(not in model)" + ":" + "[" + "?" + ", " + "?" + ", " + "?" + "]";
		}

		return "no model";
	}

	private static String tileableCtrlCursorInfo(TileViewportControl _control, Point2D _cursor, DecimalFormat _df) {
		TileViewport viewport        = _control.getViewport();
		TileProvider<?> tileProvider = _control.getTileProvider();

		TileCoordinate tile = viewport.windowInTile(_cursor);

		return tile == null ? "Tile:NaN" : tile.toString(_df);
	}

}
