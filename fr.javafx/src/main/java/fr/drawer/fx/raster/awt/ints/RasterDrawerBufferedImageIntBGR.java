package fr.drawer.fx.raster.awt.ints;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import fr.drawer.fx.raster.awt.RasterDrawerBufferedImage;
import fr.java.rasters.rasters.XRasterBufferedImage;

public class RasterDrawerBufferedImageIntBGR extends RasterDrawerBufferedImage {
	
	private final BufferedImage bimage;
	private final int[] 		buffer;

	public RasterDrawerBufferedImageIntBGR(XRasterBufferedImage _raster) {
		super(_raster);
		
		bimage = _raster.getObject();

		int        type    = bimage.getType();
		DataBuffer dbuffer = bimage.getRaster().getDataBuffer();

		if(type != BufferedImage.TYPE_INT_BGR || !(dbuffer instanceof DataBufferInt))
			throw new RuntimeException();

		buffer = ((DataBufferInt) dbuffer).getData();
	}

	public byte getValue(int _i, int _j, int _k)	{ return (byte) (buffer[(int) (_j * width + _i)] >>  (_k * 8)); }
	
	public byte getLuminance(int _i, int _j)   		{ return (byte) (buffer[(int) (depth * (_j * width + _i))]); }

	public byte getRed(int _i, int _j)   			{ return (byte) (buffer[(int) (_j * width + _i)] >> 16); }
	public byte getGreen(int _i, int _j) 			{ return (byte) (buffer[(int) (_j * width + _i)] >>  8); }
	public byte getBlue(int _i, int _j)  			{ return (byte) (buffer[(int) (_j * width + _i)]); }
/*
	public void drawRaster(Canvas _canvas, PlaneViewport<?, ?> _viewport) {
		int   mw     = (int)   width;
		int   mh     = (int)   height;
		int   md     = (int)   depth;

		int   ww     = (int)   _viewport.getWindowBounds().get().getWidth();
		int   wh     = (int)   _viewport.getWindowBounds().get().getHeight();

		int   vx     = (int)   _viewport.getViewAnchor().getFirst();
		int   vy     = (int)   _viewport.getViewAnchor().getSecond();
		float scale  = (float) _viewport.getViewScale();

		float iscale = 1f / scale;

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
		int x1 = ( - vx + (int) (scale * mw) > ww ) ? ww : - vx + (int) (scale * mw);
		int y0 = vy < 0 ? - vy : 0;
		int y1 = ( - vy + (int) (scale * mh) > wh ) ? wh : - vy + (int) (scale * mh);

		GraphicsContext gc = _canvas.getGraphicsContext2D();
		gc.getPixelWriter().setPixels(x0, y0, x1 - x0, y1 - y0, cd == 1 ? grayPXF : rgbPXF, dst, 0, ww * cd);
	}

	public void drawValues(Canvas _canvas, PlaneViewport<?, Point2D> _viewport) {
		GraphicsContext     		gc   = _canvas.getGraphicsContext2D();
		PlaneViewport<?, Point2D>	vp   = _viewport;
		BoundingBox.TwoDims			bbox = _viewport.getModelBounds(true).get();

		if(bbox.isEmpty())
			return ;

		int   rd     = (int) raster().getDepth();
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

					Coordinate.Planar pt = vp.modelInWindow(Points.of(i + 0.5, j + gap * (k + 1)));
					if(pt != null)
						gc.fillText(String.valueOf(value & 0xFF), pt.getFirst(), pt.getSecond());
					else 
						System.err.println("pt is null");
				}
			}

		gc.setFill(Color.BLACK);
	}
*/
}
