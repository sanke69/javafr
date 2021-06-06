package fr.java.rasters.rasters;

import java.awt.image.BufferedImage;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.rasters.XRaster;
import fr.java.rasters.rasters.awt.XRasterBufferedImageGeneric;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteABGR;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteABGRPreCalc;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteBGR;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteBinary;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteGray;
import fr.java.rasters.rasters.awt.bytes.XRasterBufferedImageByteIndexed;
import fr.java.rasters.rasters.awt.ints.XRasterBufferedImageIntARGB;
import fr.java.rasters.rasters.awt.ints.XRasterBufferedImageIntARGBPreCalc;
import fr.java.rasters.rasters.awt.ints.XRasterBufferedImageIntBGR;
import fr.java.rasters.rasters.awt.ints.XRasterBufferedImageIntRGB;

public abstract class XRasterBufferedImage implements XRaster {
	private static final long serialVersionUID = 1L;

	public static final class Factory {
	
		public static final XRasterBufferedImage of(BufferedImage _bi) {
			switch(_bi.getType()) {
			case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: return new XRasterBufferedImageByteBinary(_bi);
			case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: return new XRasterBufferedImageByteGray(_bi);
			case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: return new XRasterBufferedImageByteIndexed(_bi);
			case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: return new XRasterBufferedImageByteBGR(_bi);
			case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: return new XRasterBufferedImageByteABGR(_bi);
			case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return new XRasterBufferedImageByteABGRPreCalc(_bi);

			case /*  1 */ BufferedImage.TYPE_INT_RGB 		: return new XRasterBufferedImageIntRGB(_bi);
			case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: return new XRasterBufferedImageIntARGB(_bi);
			case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: return new XRasterBufferedImageIntARGBPreCalc(_bi);
			case /*  4 */ BufferedImage.TYPE_INT_BGR 		: return new XRasterBufferedImageIntBGR(_bi);

			case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
			case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: 
			case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
			case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
			default											: return new XRasterBufferedImageGeneric(_bi);
			}
		}
		
	}

	BufferedImage image;

	public XRasterBufferedImage(BufferedImage _bi) {
		image = _bi;
	}

	@Override public BufferedImage 	getObject()              			{ return image; }
	@Override public double	 		getWidth()               			{ return image.getWidth(); }
	@Override public double	 		getHeight()              			{ return image.getHeight(); }
	@Override public int	 		getDepth()               			{ return image.getColorModel().getNumComponents(); }

	@Override
	public double 			getX() {
		return 0;
	}
	@Override
	public double 			getY() {
		return 0;
	}

	@Override
	public boolean 			isEmpty() {
		return getWidth() != 0 && getHeight() != 0;
	}

	@Override
	public boolean 			contains(Coordinate _pt) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 			contains(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			intersects(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}

}
