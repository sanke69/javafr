package fr.java.rasters.utils;

import fr.java.math.geometry.plane.Viewport2D;

public class XRasterDrawer {
	
	interface XRasterReader {
		
	}
/*
	public static void drawRaster( 	byte[] out, int _oHeight, int _oWidth,
									byte[] _in, int _iDepth,  int _iHeight, int _iWidth,
									int _x0, int _y0, int _x1, int _y1) {
		int   mw     = (int)   _iWidth;
		int   mh     = (int)   _iHeight;
		int   md     = (int)   _iDepth;
		
		int channel  = _iHeight * _iWidth;

		int   ww     = _oWidth;
		int   wh     = _oHeight;

		int   vx     = (int)   _viewport.getViewAnchor().getFirst();
		int   vy     = (int)   _viewport.getViewAnchor().getSecond();
		float scale  = (float) _viewport.getViewScale();

		float iscale = 1 / scale;

		float cx     = vx < 0 ? 0 : vx * iscale;
		float cy     = vy < 0 ? 0 : vy * iscale;
		int   cd     = (int) md >= 2 ? 3 : 1;

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
						case 1 : 	out[pos]     = _in[rj * _width + ri];
									break;
						case 3 : 	out[pos]     = _in[rj * _width + ri];
									out[pos + 1] = _in[rj * _width + ri + channel];
									out[pos + 2] = _in[rj * _width + ri + 2 * channel];
									break;
						case 4 : 	out[pos]     = _in[rj * _width + ri];
									out[pos + 1] = _in[rj * _width + ri + channel];
									out[pos + 2] = _in[rj * _width + ri + 2 * channel];
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

//		GraphicsContext gc = _canvas.getGraphicsContext2D();
//		gc.getPixelWriter().setPixels(x0, y0, x1 - x0, y1 - y0, cd == 1 ? grayPXF : rgbPXF, dst, 0, ww * cd);
	}
*/
}
