package fr.media.image.utils;

import java.awt.image.BufferedImage;

import fr.java.lang.enums.PixelFormat;
import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ByteTensor;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.tensor.IntTensor;
import fr.java.media.image.Image;
import fr.java.media.image.ImageFormat;
import fr.java.sdk.math.algebra.tensors.ByteArrayTensor;
import fr.java.sdk.math.algebra.tensors.DoubleArrayTensor;
import fr.java.sdk.math.algebra.tensors.IntArrayTensor;
import fr.java.utils.primitives.Arrays;

public interface Awt2Tensor {

	public static Image<ByteTensor>		asByteImage			(BufferedImage _image) {
		switch(_image.getType()) {
		case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: 
		case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: 
		case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: return asByteGrayImage(_image);

		case /*  1 */ BufferedImage.TYPE_INT_RGB 		: 
		case /*  4 */ BufferedImage.TYPE_INT_BGR 		: 
		case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: 
		case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: 
		case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: 
		case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: 
		case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: 
		case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return asByteRGBImage(_image);

		case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
		case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
		case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
		default											: throw new NotYetImplementedException();
		}
	}
	public static Image<IntTensor> 		asIntImage			(BufferedImage _image) {
		switch(_image.getType()) {
		case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: 
		case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: 
		case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: return asIntGrayImage(_image);

		case /*  1 */ BufferedImage.TYPE_INT_RGB 		: 
		case /*  4 */ BufferedImage.TYPE_INT_BGR 		: 
		case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: 
		case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: 
		case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: 
		case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: 
		case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: 
		case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return asIntRGBImage(_image);

		case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
		case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
		case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
		default											: throw new NotYetImplementedException();
		}
	}
	public static Image<DoubleTensor> 	asDoubleImage		(BufferedImage _image) {
		switch(_image.getType()) {
		case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: 
		case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: 
		case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: return asDoubleGrayImage(_image);

		case /*  1 */ BufferedImage.TYPE_INT_RGB 		: 
		case /*  4 */ BufferedImage.TYPE_INT_BGR 		: 
		case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: 
		case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: 
		case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: 
		case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: 
		case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: 
		case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return asDoubleRGBImage(_image);

		case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
		case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
		case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
		default											: throw new NotYetImplementedException();
		}
	}

	public static Image<ByteTensor> 	asByteBinaryImage	(BufferedImage _image, int _threshold) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		ByteTensor arrayData = new ByteArrayTensor(new byte[width * height], false, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int  rgb = rgbData[y * width + x];
				byte lum = (byte) LumaConverter.convert(((rgb & 0xFF0000) >>> 16), ((rgb & 0xFF00) >>> 8), (rgb  & 0xFF));
				arrayData.setValue((byte) (lum > _threshold ? 255 : 0), y, x);
			}
		}

		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L8));
	}
	public static Image<IntTensor> 		asIntBinaryImage	(BufferedImage _image, int _threshold) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		IntTensor arrayData = new IntArrayTensor(new int[width * height], false, 1, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = rgbData[y * width + x];
				int lum = (int) LumaConverter.convert(((rgb & 0xFF0000) >>> 16), ((rgb & 0xFF00) >>> 8), (rgb  & 0xFF));
				arrayData.setValue(lum > _threshold ? 255 : 0, 0, y, x);
			}
		}
		return Image.ofInt(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L32));
	}
	public static Image<DoubleTensor> 	asDoubleBinaryImage	(BufferedImage _image, int _threshold) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		DoubleTensor.Editable arrayData = new DoubleArrayTensor(new double[width * height], false, 1, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = rgbData[y * width + x];
				int lum = (int) LumaConverter.convert(((rgb & 0xFF0000) >>> 16), ((rgb & 0xFF00) >>> 8), (rgb  & 0xFF));
				arrayData.setValue(lum > _threshold ? 255. : 0., 0, y, x);
			}
		}

		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L64D));
	}

	public static Image<ByteTensor> 	asByteGrayImage		(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		ByteTensor arrayData = new ByteArrayTensor(new byte[width * height], false, 1, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				byte rgbValue = (byte) rgbData[y * width + x];
				rgbValue      = (byte) LumaConverter.convert(
												((rgbValue & 0xFF0000) >>> 16), 
												((rgbValue & 0xFF00) >>> 8), 
												(rgbValue  & 0xFF));
				arrayData.setValue(rgbValue, 0, y, x);
			}
		}
		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L8));
	}
	public static Image<IntTensor> 		asIntGrayImage		(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		IntTensor arrayData = new IntArrayTensor(new int[width * height], false, 1, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgbValue = rgbData[y * width + x];
				rgbValue     = (int) LumaConverter.convert(
												((rgbValue & 0xFF0000) >>> 16), 
												((rgbValue & 0xFF00) >>> 8), 
												(rgbValue  & 0xFF));
				arrayData.setValue(rgbValue, 0, y, x);
			}
		}
		return Image.ofInt(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L32));
	}
	public static Image<DoubleTensor> 	asDoubleGrayImage	(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		DoubleTensor.Editable arrayData = new DoubleArrayTensor(new double[width * height], false, 1, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgbValue = rgbData[y * width + x];
				rgbValue = (int) LumaConverter.convert(
												((rgbValue & 0xFF0000) >>> 16), 
												((rgbValue & 0xFF00) >>> 8), 
												(rgbValue  & 0xFF));
				arrayData.setValue(rgbValue, 0, y, x);
			}
		}

		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_L64D));
	}

	public static Image<ByteTensor> 	asByteRGBImage		(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		ByteTensor arrayData = new ByteArrayTensor(new byte[3 * width * height], false, 3, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int  rgbValue = rgbData[y * width + x];
				byte red      = (byte) ( ( (rgbValue & 0xFF0000) >>> 16) );
				byte green    = (byte) ( ( (rgbValue & 0xFF00) >>> 8) );
				byte blue     = (byte) (   (rgbValue & 0xFF) );

				arrayData.setValue(red,   0, y, x);
				arrayData.setValue(green, 1, y, x);
				arrayData.setValue(blue,  2, y, x);
			}
		}
		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_RGB8));
	}
	public static Image<IntTensor> 		asIntRGBImage		(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		IntTensor arrayData = new IntArrayTensor(new int[3 * width * height], false, 3, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgbValue = rgbData[y * width + x];
				int red = 0, green = 0, blue = 0;
				
				switch(_image.getType()) {
				case /*  1 */ BufferedImage.TYPE_INT_RGB 		: red      = ( (rgbValue & 0xFF0000) >>> 16);
																  green    = ( (rgbValue & 0xFF00) >>> 8);
																  blue     =   (rgbValue & 0xFF);
																  break;
				case /*  4 */ BufferedImage.TYPE_INT_BGR 		: blue     = ( (rgbValue & 0xFF0000) >>> 16);
																  green    = ( (rgbValue & 0xFF00) >>> 8);
																  red      =   (rgbValue & 0xFF);
																  break;
				case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: red      = ( (rgbValue & 0xFF0000) >>> 16);
																  green    = ( (rgbValue & 0xFF00) >>> 8);
																  blue     =   (rgbValue & 0xFF);
																  break;
				case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: red      = ( (rgbValue & 0xFF0000) >>> 16);
																  green    = ( (rgbValue & 0xFF00) >>> 8);
																  blue     = ( (rgbValue & 0xFF) >>> 0);
																  break;
				case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
				case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: 
				case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: 
				case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : 
				case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
				case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
				case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: 
				case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: 
				case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: 
				case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: 
				default											: throw new NotYetImplementedException();
				}

				arrayData.setValue(red,   0, y, x);
				arrayData.setValue(green, 1, y, x);
				arrayData.setValue(blue,  2, y, x);
			}
		}
		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_RGB32));
	}
	public static Image<DoubleTensor> 	asDoubleRGBImage	(BufferedImage _image) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();
		int[]     rgbData  = _image.getRGB(0, 0, width, height, null, 0, width);

		DoubleTensor.Editable arrayData = new DoubleArrayTensor(new double[3 * width * height], false, 3, height, width);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgbValue = rgbData[y * width + x];
				int red      = ( (rgbValue & 0xFF0000) >>> 16);
				int green    = ( (rgbValue & 0xFF00) >>> 8);
				int blue     =   (rgbValue & 0xFF);

				arrayData.setValue(red,   0, y, x);
				arrayData.setValue(green, 1, y, x);
				arrayData.setValue(blue,  2, y, x);
			}
		}

		return Image.of(arrayData, ImageFormat.of(width, height, PixelFormat.PXF_RGB64D));
	}
	
	// TODO:: Manage Multi-Channel
	@SuppressWarnings("unchecked")
	public static BufferedImage 		asBufferedImage			(Image<? extends NumberTensor> _grayscale) {
		if(_grayscale.data().getPrimitive() == Primitive.BYTE)
			return ByteAsBufferedImage( (Image<ByteTensor>) _grayscale);
		else if(_grayscale.data().getPrimitive() == Primitive.INTEGER)
			return IntAsBufferedImage( (Image<IntTensor>) _grayscale);
		else if(_grayscale.data().getPrimitive() == Primitive.DOUBLE)
			return DoubleAsBufferedImage( (Image<DoubleTensor>) _grayscale);

		return null;
	}

	public static BufferedImage 		ByteAsBufferedImage		(Image<ByteTensor> _grayscale) {
		int width  = _grayscale.format().getWidth();
		int height = _grayscale.format().getHeight();

		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int argb = 0;
				switch(_grayscale.format().getDepth()) {
				default :
				case 1  :   byte lum = _grayscale.data().getValue(y, x);
							argb = (lum << 16) | (lum << 8) | lum | 0xFF000000;
							break;
				case 3  :	byte red = _grayscale.data().getValue(0, y, x);
							byte gre = _grayscale.data().getValue(1, y, x);
							byte blu = _grayscale.data().getValue(2, y, x);
							argb = /*0xFF000000 | */(red << 16) | (gre << 8) | blu;
							break;
				}

				outputImage.setRGB(x, y, argb);
			}
		}

		return outputImage;
	}
	public static BufferedImage 		IntAsBufferedImage		(Image<IntTensor> _grayscale) {
		int w   = _grayscale.format().getWidth();
		int h   = _grayscale.format().getHeight();

		BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int argb = 0;
				switch(_grayscale.format().getDepth()) {
				default :
				case 1  :   int lum = _grayscale.data().getValue(y, x);
							argb = (lum << 16) | (lum << 8) | lum | 0xFF000000;
							break;
				case 3  :	int red = _grayscale.data().getValue(0, y, x);
							int gre = _grayscale.data().getValue(1, y, x);
							int blu = _grayscale.data().getValue(2, y, x);
							argb = /*0xFF000000 | */(red << 0) | (gre << 8) | (blu << 16);
							break;
				}

				outputImage.setRGB(x, y, argb);
			}
		}

		return outputImage;
	}
	public static BufferedImage 		DoubleAsBufferedImage	(Image<DoubleTensor> _grayscale) {
		int w   = _grayscale.format().getWidth();
		int h   = _grayscale.format().getHeight();
		double max = Arrays.max( _grayscale.data().getArray() );

		BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int n        = Math.min((int) Math.round(_grayscale.data().getValue(y, x) * 255.0 / max), 255);
				int rgbValue = 
//						(n << 16) | (n << 8) | 0x00 | -0x01000000);
//						(n << 24) |  (n << 16) | (n << 8) | 0x00;
						(n << 16) |  (n << 8) | n;
				
				
				outputImage.setRGB(x, y, rgbValue);
//				outputImage.setRGB(x, h - 1 - y, (n << 16) | (n << 8) | 0x00 | -0x01000000);
			}
		}

		return outputImage;
	}

}
