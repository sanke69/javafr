package fr.drawer.fx.raster.awt;

import java.awt.image.BufferedImage;

import fr.drawer.fx.raster.RasterDrawer;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteABGR;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteABGRPreCalc;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteBGR;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteBinary;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteGray;
import fr.drawer.fx.raster.awt.bytes.RasterDrawerBufferedImageByteIndexed;
import fr.drawer.fx.raster.awt.ints.RasterDrawerBufferedImageIntARGB;
import fr.drawer.fx.raster.awt.ints.RasterDrawerBufferedImageIntARGBPreCalc;
import fr.drawer.fx.raster.awt.ints.RasterDrawerBufferedImageIntBGR;
import fr.drawer.fx.raster.awt.ints.RasterDrawerBufferedImageIntRGB;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.java.rasters.rasters.XRasterBufferedImage;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public abstract class RasterDrawerBufferedImage extends RasterDrawer {

	public static class Factory {
	
		public static RasterDrawer create(XRasterBufferedImage _raster) {
			int type = _raster.getObject().getType();

			switch(type) {
			case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: return new RasterDrawerBufferedImageByteBinary(_raster);
			case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: return new RasterDrawerBufferedImageByteGray(_raster);
			case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: return new RasterDrawerBufferedImageByteIndexed(_raster);
			case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: return new RasterDrawerBufferedImageByteBGR(_raster);
			case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: return new RasterDrawerBufferedImageByteABGR(_raster);
			case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return new RasterDrawerBufferedImageByteABGRPreCalc(_raster);

			case /*  1 */ BufferedImage.TYPE_INT_RGB 		: return new RasterDrawerBufferedImageIntRGB(_raster);
			case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: return new RasterDrawerBufferedImageIntARGB(_raster);
			case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: return new RasterDrawerBufferedImageIntARGBPreCalc(_raster);
			case /*  4 */ BufferedImage.TYPE_INT_BGR 		: return new RasterDrawerBufferedImageIntBGR(_raster);

			case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
			case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: 
			case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
			case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
			default											: throw new NotYetImplementedException();
			}
		}

	}

	protected final XRasterBufferedImage xbimage;
	protected final BufferedImage 		 bimage;

	protected final double			  	 width, height;
	protected final int					 depth;

	protected byte[] 					 temporaryBuffer;

	public RasterDrawerBufferedImage(XRasterBufferedImage _raster) {
		super(_raster);

		xbimage = _raster;
		bimage  = _raster.getObject();

		width  = _raster.getWidth();
		height = _raster.getHeight();
		depth  = _raster.getDepth();
	}

	public final XRasterBufferedImage 	xbufferedImage() {
		return xbimage;
	}
	public final BufferedImage 			bufferedImage() {
		return bimage;
	}

	public abstract byte 				getValue(int _i, int _j, int _k);

	public abstract byte 				getLuminance(int _i, int _j);

	public abstract byte 				getRed(int _i, int _j);
	public abstract byte 				getGreen(int _i, int _j);
	public abstract byte 				getBlue(int _i, int _j);

	public void 						drawRaster(Canvas _canvas, Viewport.TwoDims<?, ?> _viewport) {
		int   mw     = (int)   width;
		int   mh     = (int)   height;
		int   md     = (int)   depth;

		int   ww     = (int)   _viewport.getWindow().getWidth();
		int   wh     = (int)   _viewport.getWindow().getHeight();

		int   vx     = (int)   - _viewport.getViewAnchor().getFirst();
		int   vy     = (int)   - _viewport.getViewAnchor().getSecond();
		float vs     = (float) _viewport.getViewScale();

		float iscale = 1f / vs;

		float cx     = vx < 0 ? 0 : vx * iscale;
		float cy     = vy < 0 ? 0 : vy * iscale;
		int   cd     = (int) md >= 2 ? 3 : 1;

		if(temporaryBuffer == null || temporaryBuffer.length != ww * wh * cd)
			temporaryBuffer = new byte[ww * wh * cd];

		byte[] dst = temporaryBuffer;
		int    pos = (ww - 1 + (wh - 1) * ww) * cd;

		for(int y = wh; y-- != 0;) {
			float _yOnRaster = cy + y * iscale;
			int    rj        = (int) (_yOnRaster);

			if(rj >= 0 && rj < mh && !(rj == 0 && _yOnRaster < 0))
				for(int x = ww; x-- != 0;) {
					float _xOnRaster = cx + x * iscale;
					int    ri        = (int) (_xOnRaster);

					if((ri >= 0 && ri < mw) && (rj >= 0 && rj < mh))
						switch(cd) {
						case 1 : 	dst[pos]     = getLuminance(ri, rj);
									break;
						case 3 : 	dst[pos]     = getRed(ri, rj);
									dst[pos + 1] = getGreen(ri, rj);
									dst[pos + 2] = getBlue(ri, rj);
									break;
						case 4 : 	dst[pos]     = getRed(ri, rj);
									dst[pos + 1] = getGreen(ri, rj);
									dst[pos + 2] = getBlue(ri, rj);
									break;
						}
					
					pos -= cd;
				}
			else
				pos -= ww * cd;
		}

		int x0 = vx < 0 ? - vx : 0;
		int x1 = ( - vx + (int) (vs * mw) > ww ) ? ww : - vx + (int) (vs * mw);
		int y0 = vy < 0 ? - vy : 0;
		int y1 = ( - vy + (int) (vs * mh) > wh ) ? wh : - vy + (int) (vs * mh);

		GraphicsContext gc = _canvas.getGraphicsContext2D();
		gc.getPixelWriter().setPixels(x0, y0, x1 - x0, y1 - y0, cd == 1 ? grayPXF : rgbPXF, dst, 0, ww * cd);
	}

	public void 						drawValues(Canvas _canvas, Viewport.TwoDims<?, Point2D> _viewport) {
		GraphicsContext     			gc   = _canvas.getGraphicsContext2D();
		Viewport.TwoDims<?, Point2D>	vp   = _viewport;
		BoundingBox.TwoDims 			bbox = vp.getModelBounds(true);

		if(bbox.isEmpty())
			return ;

		int   rd     = (int) depth;
		float scale  = (float) _viewport.getViewScale();

		if(rd == 3 && scale < lodDepth3)
			return ;

		gc.setTextAlign		(TextAlignment.CENTER);
		gc.setTextBaseline	(VPos.CENTER);
		gc.setFont			(new Font(12 + (rd == 1 ? (0.52 * (scale - lodDepth1)) : (0.26 * (scale - lodDepth3)))));

		int I0 = (int) Math.ceil(bbox.getMinX()) - 1;	I0 = I0 < 0 ? 0 : I0;
		int I1 = (int) Math.ceil(bbox.getMaxX()) + 1; 	I1 = I1 < width - 1 ? I1 : (int) width - 1;

		int J0 = (int) Math.ceil(bbox.getMinY()) - 1;	J0 = J0 < 0 ? 0 : J0;
		int J1 = (int) Math.ceil(bbox.getMaxY()) + 1;	J1 = J1 < height - 1 ? J1 : (int) height - 1;


		for(int i = I0; i <= I1; i++)
			for(int j = J0; j <= J1; j++) {

				float gap = 1.0f / (rd + 1);
				for(int k = 0; k < rd; k++) {
					byte value = getValue(i, j, k);

					switch (rd) {
					case 1: 	gc.setFill((getLuminance(i, j) > 127) ? Color.BLACK : Color.WHITE); break;
					case 3: 	switch (k) {
								case 0  : gc.setFill((getBlue(i, j)  > 127) ? new Color(0, 0, 0.5, 1) : Color.BLUE); break;
								case 1  : gc.setFill((getGreen(i, j) > 127) ? new Color(0, 0.5, 0, 1) : Color.LIME); break;
								case 2  : gc.setFill((getRed(i, j)   > 127) ? new Color(0.5, 0, 0, 1) : Color.RED); break;
								default : break;
								}
					default: 	break;
					}

					Coordinate.TwoDims pt = vp.modelInWindow(Points.of(i + 0.5, j + gap * (k + 1)));
					if(pt != null)
						gc.fillText(String.valueOf(value & 0xFF), pt.getFirst(), pt.getSecond());
					else 
						System.err.println("pt is null");
				}
			}

		gc.setFill(Color.BLACK);
	}

}
