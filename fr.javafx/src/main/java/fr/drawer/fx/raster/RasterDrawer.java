package fr.drawer.fx.raster;

import java.nio.ByteBuffer;

import fr.drawer.fx.raster.awt.RasterDrawerBufferedImage;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.types.Points;
import fr.java.raster.XRaster;
import fr.java.rasters.rasters.XRasterBufferedImage;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.paint.Color;

public abstract class RasterDrawer {
	public static final PixelFormat<ByteBuffer> grayPXF = PixelFormat.createByteIndexedPremultipliedInstance(generateGrayScale());
	public static final PixelFormat<ByteBuffer> rgbPXF  = PixelFormat.getByteRgbInstance();

	public static final int	lodGrid			 = 5,
							lodDepth1		 = 25,
							lodDepth3		 = 40;

	public static class Factory {
		public static RasterDrawer create(XRaster _raster) {
			if(_raster instanceof XRasterBufferedImage)
				return RasterDrawerBufferedImage.Factory.create( (XRasterBufferedImage) _raster );

			return new RasterDrawerGeneric(_raster);
		}
	}

	public boolean 			enableRaster     = true,
							enableValues 	 = false,
							enableGrid       = true,
							enableBorder     = true,
							enableSelection  = false,
							repaintCompleted = true;

	protected final XRaster raster;

	public RasterDrawer(XRaster _raster) {
		super();

		raster = _raster;
	}

	public final XRaster 	raster() {
		return raster;
	}
	public final boolean 	repaintCompleted() {
		return repaintCompleted;
	}

	public abstract void 	drawRaster(Canvas _canvas, Viewport.TwoDims<?, ?> _viewport);
	public abstract void 	drawValues(Canvas _canvas, Viewport.TwoDims<?, Point2D> _viewport);

	public final void 		clear(Canvas _canvas, Color background) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();

		gc.setFill(background);
		gc.fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
	}
	public final void 		paint(Canvas _canvas, Viewport.TwoDims<?, Point2D> _viewport) {
		if(!Platform.isFxApplicationThread())
			throw new IllegalAccessError("This is not Fx Thread");

		repaintCompleted = true;

		if(enableBorder)
			drawBorder(_canvas, _viewport);

		if(enableRaster)
			drawRaster(_canvas, _viewport);

		if(enableGrid && _viewport.getViewScale() > lodGrid)
			drawGrid(_canvas, _viewport);

		if(enableValues && _viewport.getViewScale() > (raster().getDepth() > 1 ? lodDepth3 : lodDepth1))
			drawValues(_canvas, _viewport);

	}

	public final void 		drawBorder(Canvas _canvas, Viewport.TwoDims<?, Point2D> _viewport) {
		/*
		GraphicsContext gc = _canvas.getGraphicsContext2D();

		BoundingBox.TwoDims model   = _viewport.getModelBounds().get();
		BoundingBox.TwoDims mod2win = _viewport.modelInWindow(model);

		gc.setStroke(Color.RED);
		gc.setLineWidth(1d/_viewport.getViewScale());

		gc.strokeRect(mod2win.getX(), mod2win.getY(), mod2win.getWidth(), mod2win.getHeight());
		*/
	}

	public final void 		drawGrid(Canvas _canvas, Viewport.TwoDims<?, Point2D> _viewport) {
		GraphicsContext    				gc 			= _canvas.getGraphicsContext2D();
		Viewport.TwoDims<?, Point2D> 	vp 			= _viewport;
		BoundingBox.TwoDims 			modelBox   	= _viewport.getModelBounds(true);

		int mx0 = (int) modelBox.getMinX();
		int mx1 = (int) modelBox.getMaxX();
		int my0 = (int) modelBox.getMinY();
		int my1 = (int) modelBox.getMaxY();

		Coordinate.TwoDims TL = vp.modelInWindow(Points.of(mx0, my0));
		Coordinate.TwoDims BR = vp.modelInWindow(Points.of(mx1, my1));

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(0.69);
		for(int i = mx0; i <= mx1; i++) {
			Coordinate.TwoDims pt = vp.modelInWindow(Points.of(i, my0));
			gc.strokeLine(pt.getFirst(), TL.getSecond(), pt.getFirst(), BR.getSecond());
		}
		for(int i = my0; i <= my1; i++) {
			Coordinate.TwoDims pt = vp.modelInWindow(Points.of(mx0, i));
			gc.strokeLine(TL.getFirst(), pt.getSecond(), BR.getFirst(), pt.getSecond());
		}

	}

	public final void 		drawSelection(Canvas _canvas, Point2D[] _selectAreaRect) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();

		gc.setStroke(Color.LIME);
		gc.setLineWidth(1);
//		gc.setLineWidth(1 / control.getViewport().getViewScale());

		int x      = (int) Math.ceil(_selectAreaRect[0].getX());
		int y      = (int) Math.ceil(_selectAreaRect[0].getY());
		int width  = (int) Math.floor(_selectAreaRect[1].getX());
		int height = (int) Math.floor(_selectAreaRect[1].getY());

		gc.strokeRect(x, y, width, height);
	}
	public final void 		drawSelection(Canvas _canvas, float[] _roi) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();

		gc.setStroke(Color.RED);
		gc.strokeRect(_roi[0], _roi[1], _roi[2], _roi[3]);
	}

	static int[] 			generateGrayScale() {
		int[] grayColorMap = new int[256];
		for(int i = 0; i < grayColorMap.length; i++)
			grayColorMap[i] = 0xff000000 | i << 16 | i << 8 | i;
		return grayColorMap;
	}

}
