package fr.media.image.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.lang.enums.PixelFormat;
import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ByteTensor;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.tensor.FloatTensor;
import fr.java.math.algebra.tensor.IntTensor;
import fr.java.math.algebra.tensor.LongTensor;
import fr.java.math.algebra.tensor.ShortTensor;
import fr.java.math.numbers.Interval;
import fr.java.maths.algebra.Tensors;
import fr.java.maths.algebra.tensors.ByteArrayTensor;
import fr.java.media.image.Image;
import fr.java.media.image.ImageFormat;
import fr.java.utils.primitives.Arrays;

public class TensorImages {
	private static final boolean indexedAsGray = true;

	public static Image<? extends NumberTensor> 	loadImage(Path _path) throws IOException {
		return loadImage(_path, Primitive.BYTE, PixelFormat.PXF_DEFAULT);
	}
	public static Image<? extends NumberTensor> 	loadImage(Path _path, Primitive _primitive) throws IOException {
		return loadImage(_path, _primitive, PixelFormat.PXF_DEFAULT);
	}
	public static Image<? extends NumberTensor> 	loadImage(Path _path, Primitive _primitive, PixelFormat _pxf) throws IOException {
		BufferedImage src = BufferedImages.loadImage(_path);
		return from(src, _primitive, _pxf);
	}

	public static class Byte {

		public static Image<ByteTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofByte(Tensors.Byte.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<ByteTensor> allocate(final byte[] _array, final int _d, final int _h, final int _w) {
			return Image.ofByte(Tensors.Byte.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<ByteTensor> allocate(final byte[][] _data) {
			return Image.ofByte(Tensors.Byte.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<ByteTensor> allocate(final byte[][][] _data) {
			return Image.ofByte(Tensors.Byte.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<ByteTensor> wrap(final byte[] _array, final int _d, final int _h, final int _w) {
			return Image.ofByte(Tensors.Byte.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<ByteTensor> wrap(final byte[] _array, final int _d, final int _h, final int _w, final PixelFormat _pxf) {
			return Image.ofByte(Tensors.Byte.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<ByteTensor> wrap(final byte[] _array, final int _n, final int _d, final int _h, final int _w, final PixelFormat _pxf) {
			return Image.ofByte(Tensors.Byte.wrap(_array, _n, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

		public static Image<ByteTensor> from(final NumberTensor _tensor) {
			if(_tensor.getSliceDepth() < 2 || _tensor.getSliceDepth() > 3)
				throw new IllegalArgumentException();

			byte[] array;
			if(_tensor.getArray() == null)
				throw new NotYetImplementedException();

			switch(_tensor.getPrimitive()) {
			case BYTE:		array = Arrays.copy((byte[]) _tensor.getArray());
							break;
			case SHORT:		array = Arrays.convertToByteArray((short[]) _tensor.getArray());
							break;
			case INTEGER:	array = Arrays.convertToByteArray((int[]) _tensor.getArray());
							break;
			case LONG:		array = Arrays.convertToByteArray((long[]) _tensor.getArray());
							break;
			case FLOAT:		array = Arrays.convertToByteArray((float[]) _tensor.getArray());
							break;
			case DOUBLE:	array = Arrays.convertToByteArray((double[]) _tensor.getArray());
							break;
			default:		throw new NotYetImplementedException();
			}

			return Image.ofByte(
							Tensors.Byte.wrap(array, _tensor.getSliceDepth() == 3 ? _tensor.getSliceDimension(2, true) : 1, _tensor.getSliceDimension(1, true), _tensor.getSliceDimension(0, true)), 
							ImageFormat.of(_tensor.getSliceDimension(0, true), _tensor.getSliceDimension(1, true), null)
							);
		}

	}
	public static class Short {

		public static Image<ShortTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofShort(Tensors.Short.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<ShortTensor> allocate(final short[] _array, final int _d, final int _h, final int _w) {
			return Image.ofShort(Tensors.Short.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<ShortTensor> allocate(final short[][] _data) {
			return Image.ofShort(Tensors.Short.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<ShortTensor> allocate(final short[][][] _data) {
			return Image.ofShort(Tensors.Short.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<ShortTensor> wrap(final short[] _array, final int _d, final int _h, final int _w) {
			return Image.ofShort(Tensors.Short.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<ShortTensor> wrap(final short[] _array, final int _d, final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofShort(Tensors.Short.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

	}
	public static class Int {

		public static Image<IntTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofInt(Tensors.Int.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<IntTensor> allocate(int[] _array, int _d, int _h, int _w) {
			return Image.ofInt(Tensors.Int.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<IntTensor> allocate(final int[][] _data) {
			return Image.ofInt(Tensors.Int.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<IntTensor> allocate(final int[][][] _data) {
			return Image.ofInt(Tensors.Int.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<IntTensor> wrap(IntTensor _tensor) {
			int         width  = _tensor.getSliceDimension(0, true);
			int         height = _tensor.getSliceDimension(1, true);
			int         depth  = _tensor.getSliceDimension(2, true);
			PixelFormat pxf    = PixelFormat.of(depth, _tensor.getPrimitive());

			return Image.ofInt(_tensor, ImageFormat.of(width, height, pxf));
		}
		public static Image<IntTensor> wrap(IntTensor _tensor, PixelFormat _pxf) {
			int         width  = _tensor.getSliceDimension(0, true);
			int         height = _tensor.getSliceDimension(1, true);
			int         depth  = _tensor.getSliceDimension(2, true);
			PixelFormat pxf    = _pxf != null ? _pxf : PixelFormat.of(depth, _tensor.getPrimitive());

			return Image.ofInt(_tensor, ImageFormat.of(width, height, pxf));
		}
		public static Image<IntTensor> wrap(int[] _array, int _d, int _h, int _w) {
			return Image.ofInt(Tensors.Int.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<IntTensor> wrap(int[] _array, int _d, int _h, int _w, PixelFormat _pxf) {
			return Image.ofInt(Tensors.Int.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

	}
	public static class Long {

		public static Image<LongTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofLong(Tensors.Long.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<LongTensor> allocate(final long[] _array, final int _d, final int _h, final int _w) {
			return Image.ofLong(Tensors.Long.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<LongTensor> allocate(final long[][] _data) {
			return Image.ofLong(Tensors.Long.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<LongTensor> allocate(final long[][][] _data) {
			return Image.ofLong(Tensors.Long.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<LongTensor> wrap(final long[] _array, final int _d, final int _h, final int _w) {
			return Image.ofLong(Tensors.Long.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<LongTensor> wrap(final long[] _array, final int _d, final int _h, final int _w, final PixelFormat _pxf) {
			return Image.ofLong(Tensors.Long.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

	}
	public static class Float {

		public static Image<FloatTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofFloat(Tensors.Float.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<FloatTensor> allocate(final float[] _array, final int _d, final int _h, final int _w) {
			return Image.ofFloat(Tensors.Float.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<FloatTensor> allocate(final float[][] _data) {
			return Image.ofFloat(Tensors.Float.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<FloatTensor> allocate(final float[][][] _data) {
			return Image.ofFloat(Tensors.Float.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<FloatTensor> wrap(final float[] _array, final int _d, final int _h, final int _w) {
			return Image.ofFloat(Tensors.Float.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<FloatTensor> wrap(final float[] _array, final int _d, final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofFloat(Tensors.Float.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

	}
	public static class Double {

		public static Image<DoubleTensor> allocate(final int _h, final int _w, PixelFormat _pxf) {
			return Image.ofDouble(Tensors.Double.create(_pxf.nbChannels(), _h, _w), ImageFormat.of(_w, _h, _pxf));
		}
		public static Image<DoubleTensor> allocate(final double[] _array, final int _d, final int _h, final int _w) {
			return Image.ofDouble(Tensors.Double.allocate(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<DoubleTensor> allocate(final double[][] _data) {
			return Image.ofDouble(Tensors.Double.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<DoubleTensor> allocate(final double[][][] _data) {
			return Image.ofDouble(Tensors.Double.of(_data), ImageFormat.of(_data[0].length, _data.length, null));
		}
		public static Image<DoubleTensor> wrap(final double[] _array, final int _d, final int _h, final int _w) {
			return Image.ofDouble(Tensors.Double.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, null));
		}
		public static Image<DoubleTensor> wrap(final double[] _array, final int _d, final int _h, final int _w, final PixelFormat _pxf) {
			return Image.ofDouble(Tensors.Double.wrap(_array, _d, _h, _w), ImageFormat.of(_w, _h, _pxf));
		}

	}



	public static Image<? extends NumberTensor>  	from(BufferedImage _img) {
		return from(_img, Primitive.BYTE, PixelFormat.PXF_DEFAULT);
	}
	public static Image<? extends NumberTensor>  	from(BufferedImage _img, Primitive _primitive) {
		return from(_img, _primitive, PixelFormat.PXF_DEFAULT);
	}
	public static Image<? extends NumberTensor>  	from(BufferedImage _img, Primitive _primitive, PixelFormat _pxf) {
		switch(_primitive) {
		case BYTE:		return byteFrom		(_img);
		case SHORT:		return shortFrom	(_img);
		case INTEGER:	return intFrom		(_img);
		case LONG:		return longFrom		(_img);
		case FLOAT:		return floatFrom	(_img);
		case DOUBLE:	return doubleFrom	(_img);
		case BOOLEAN:
		case STRING:
		case UNDEF:
		default:		throw new IllegalArgumentException();
		}
	}

	public static Image<ByteTensor>  				byteFrom	(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		byte[]      buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toByte(dataBuf, height, width, (byte) 0, (byte) 255);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toByte(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L8;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toByte(dataBuf, height, width, false);
															pxf    = PixelFormat.PXF_BGR8;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toByte(dataBuf, height, width, false, true);
															pxf    = PixelFormat.PXF_ABGR8;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toByte(dataBuf, height, width, false, true);
															pxf    = PixelFormat.PXF_ABGR8;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = indexedAsGray ? AwtPixel2Channel.ByteGray.toByte(dataBuf, height, width) : AwtPixel2Channel.ByteIndexed.toByte(dataBuf, height, width, _img.getColorModel());
															pxf    = indexedAsGray ? PixelFormat.PXF_L8 : PixelFormat.PXF_RGB8;
															break ;
				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferUShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toByte(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L8;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toByte(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB8;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toByte(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB8;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toByte(dataBuf, height, width, false);
															pxf    = PixelFormat.PXF_RGB8;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerRGB.toByte(dataBuf, height, width, false);
															pxf    = PixelFormat.PXF_BGR8;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toByte(dataBuf, height, width, false, true);
															pxf    = PixelFormat.PXF_ARGB8;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toByte(dataBuf, height, width, false, true);
															pxf    = PixelFormat.PXF_ARGB8;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return Byte.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}
	public static Image<ShortTensor>  				shortFrom	(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		short[]     buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toShort(dataBuf, height, width, UniformTransform.S_MIN, UniformTransform.S_MAX);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toShort(dataBuf, height, width, UniformTransform::byteToShort);
															pxf    = PixelFormat.PXF_L16;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toShort(dataBuf, height, width, UniformTransform::byteToShort, false);
															pxf    = PixelFormat.PXF_BGR16;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toShort(dataBuf, height, width, UniformTransform::byteToShort, false, true);
															pxf    = PixelFormat.PXF_ABGR16;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toShort(dataBuf, height, width, UniformTransform::byteToShort, false, true);
															pxf    = PixelFormat.PXF_ABGR16;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = AwtPixel2Channel.ByteIndexed.toShort(dataBuf, height, width, UniformTransform::byteToShort, ColorModel.getRGBdefault());
															pxf    = PixelFormat.PXF_RGB16;
															break ; 

				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toShort(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L16;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toShort(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB16;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toShort(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB16;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toShort(dataBuf, height, width, UniformTransform::byteToShort, false);
															pxf    = PixelFormat.PXF_RGB16;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerBGR.toShort(dataBuf, height, width, UniformTransform::byteToShort, false);
															pxf    = PixelFormat.PXF_BGR16;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toShort(dataBuf, height, width, UniformTransform::byteToShort, false, true);
															pxf    = PixelFormat.PXF_ARGB16;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toShort(dataBuf, height, width, UniformTransform::byteToShort, false, true);
															pxf    = PixelFormat.PXF_ARGB16;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return TensorImages.Short.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}
	public static Image<IntTensor>  				intFrom		(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		int[]       buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toInt(dataBuf, height, width, UniformTransform.I_MIN, UniformTransform.I_MAX);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toInt(dataBuf, height, width, UniformTransform::byteToInteger);
															pxf    = PixelFormat.PXF_L32;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false);
															pxf    = PixelFormat.PXF_BGR32;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false, true);
															pxf    = PixelFormat.PXF_ABGR32;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false, true);
															pxf    = PixelFormat.PXF_ABGR32;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = AwtPixel2Channel.ByteIndexed.toInt(dataBuf, height, width, UniformTransform::byteToInteger, _img.getColorModel());
															pxf    = PixelFormat.PXF_RGB32;
															break ; 

				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toInt(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L32;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toInt(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB32;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toInt(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB32;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false);
															pxf    = PixelFormat.PXF_RGB32;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerBGR.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false);
															pxf    = PixelFormat.PXF_BGR32;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false, true);
															pxf    = PixelFormat.PXF_ARGB32;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toInt(dataBuf, height, width, UniformTransform::byteToInteger, false, true);
															pxf    = PixelFormat.PXF_ARGB32;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return TensorImages.Int.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}
	public static Image<LongTensor>  				longFrom	(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		long[]      buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toLong(dataBuf, height, width, UniformTransform.L_MIN, UniformTransform.L_MAX);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toLong(dataBuf, height, width, UniformTransform::byteToLong);
															pxf    = PixelFormat.PXF_L64;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toLong(dataBuf, height, width, UniformTransform::byteToLong, false);
															pxf    = PixelFormat.PXF_BGR64;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toLong(dataBuf, height, width, UniformTransform::byteToLong, false, true);
															pxf    = PixelFormat.PXF_ABGR64;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toLong(dataBuf, height, width, UniformTransform::byteToLong, false, true);
															pxf    = PixelFormat.PXF_ABGR64;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = AwtPixel2Channel.ByteIndexed.toLong(dataBuf, height, width, UniformTransform::byteToLong, ColorModel.getRGBdefault());
															pxf    = PixelFormat.PXF_RGB64;
															break ; 

				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toLong(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L64;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toLong(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB64;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toLong(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB64;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toLong(dataBuf, height, width, UniformTransform::byteToLong, false);
															pxf    = PixelFormat.PXF_RGB64;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerBGR.toLong(dataBuf, height, width, UniformTransform::byteToLong, false);
															pxf    = PixelFormat.PXF_BGR64;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toLong(dataBuf, height, width, UniformTransform::byteToLong, false, true);
															pxf    = PixelFormat.PXF_ARGB64;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toLong(dataBuf, height, width, UniformTransform::byteToLong, false, true);
															pxf    = PixelFormat.PXF_ARGB64;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return TensorImages.Long.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}
	public static Image<FloatTensor> 				floatFrom	(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		float[]     buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toFloat(dataBuf, height, width, UniformTransform.F_MIN, UniformTransform.F_MAX);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toFloat(dataBuf, height, width, UniformTransform::byteToFloat);
															pxf    = PixelFormat.PXF_L32F;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, true);
															pxf    = PixelFormat.PXF_BGR32F;
															break ;	
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false, true);
															pxf    = PixelFormat.PXF_ABGR32F;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false, true);
															pxf    = PixelFormat.PXF_ABGR32F;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = AwtPixel2Channel.ByteIndexed.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, ColorModel.getRGBdefault());
															pxf    = PixelFormat.PXF_RGB32F;
															break ; 

				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toFloat(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L32F;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toFloat(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB32F;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toFloat(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB32F;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false);
															pxf    = PixelFormat.PXF_RGB32F;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerRGB.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false);
															pxf    = PixelFormat.PXF_BGR32F;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false, true);
															pxf    = PixelFormat.PXF_ARGB32F;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toFloat(dataBuf, height, width, UniformTransform::byteToFloat, false, true);
															pxf    = PixelFormat.PXF_ARGB32F;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return TensorImages.Float.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}
	public static Image<DoubleTensor> 				doubleFrom	(BufferedImage _img) {
		int type   = _img.getType();
		int dtype  = _img.getRaster().getDataBuffer().getDataType();
		int width  = _img.getWidth();
		int height = _img.getHeight();

		double[]     buffer = null;
		PixelFormat pxf    = null;

		switch(dtype) {
		case DataBuffer.TYPE_BYTE 		: {
				byte[] dataBuf = ((DataBufferByte) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_BYTE_BINARY		: 	buffer = AwtPixel2Channel.ByteBinary.toDouble(dataBuf, height, width, UniformTransform.D_MIN, UniformTransform.D_MAX);
															pxf    = PixelFormat.PXF_BINARY;
															break ;
				case BufferedImage.TYPE_BYTE_GRAY 		: 	buffer = AwtPixel2Channel.ByteGray.toDouble(dataBuf, height, width, UniformTransform::byteToDouble);
															pxf    = PixelFormat.PXF_L64D;
															break ;
				case BufferedImage.TYPE_3BYTE_BGR 		: 	buffer = AwtPixel2Channel.ByteBGR.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false);
															pxf    = PixelFormat.PXF_BGR64D;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR 		: 	buffer = AwtPixel2Channel.ByteABGR.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false, true);
															pxf    = PixelFormat.PXF_ABGR64D;
															break ;
				case BufferedImage.TYPE_4BYTE_ABGR_PRE 	: 	buffer = AwtPixel2Channel.ByteABGR_PRE.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false, true);
															pxf    = PixelFormat.PXF_ABGR64D;
															break ;
				case BufferedImage.TYPE_BYTE_INDEXED	: 	buffer = AwtPixel2Channel.ByteIndexed.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, ColorModel.getRGBdefault());
															pxf    = PixelFormat.PXF_RGB64D;
															break ; 

				default                                 : 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_USHORT 	: {
				short[] dataBuf = ((DataBufferShort) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_USHORT_GRAY 	: 	buffer = AwtPixel2Channel.UShortGray.toDouble(dataBuf, height, width);
															pxf    = PixelFormat.PXF_L64D;
															break ;
				case BufferedImage.TYPE_USHORT_565_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB565.toDouble(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB64D;
															break ;
				case BufferedImage.TYPE_USHORT_555_RGB 	: 	buffer = AwtPixel2Channel.UShortRGB555.toDouble(dataBuf, height, width);
															pxf    = PixelFormat.PXF_RGB64D;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_INT   		: 	{
				int[] dataBuf = ((DataBufferInt) _img.getRaster().getDataBuffer()).getData();
				switch(type) {
				case BufferedImage.TYPE_INT_RGB 		: 	buffer = AwtPixel2Channel.IntegerRGB.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false);
															pxf    = PixelFormat.PXF_RGB64D;
															break ;
				case BufferedImage.TYPE_INT_BGR 		: 	buffer = AwtPixel2Channel.IntegerBGR.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false);
															pxf    = PixelFormat.PXF_RGB64D;
															break ;
				case BufferedImage.TYPE_INT_ARGB 		: 	buffer = AwtPixel2Channel.IntegerARGB.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false, true);
															pxf    = PixelFormat.PXF_ARGB64D;
															break ;
				case BufferedImage.TYPE_INT_ARGB_PRE 	: 	buffer = AwtPixel2Channel.IntegerARGB_PRE.toDouble(dataBuf, height, width, UniformTransform::byteToDouble, false, true);
															pxf    = PixelFormat.PXF_ARGB64D;
															break ;

				default                                	: 	throw new IllegalArgumentException("image type and data type not compliant !");
				}
			}
			break;

		case DataBuffer.TYPE_SHORT 		: 	
		case DataBuffer.TYPE_FLOAT  	: 	
		case DataBuffer.TYPE_DOUBLE  	: 	
		case DataBuffer.TYPE_UNDEFINED 	: 	
		default 						:	throw new NotYetImplementedException();
		}

		return TensorImages.Double.wrap(buffer, pxf.nbChannels(), height, width, pxf);
	}



	public static Image<? extends NumberTensor> 	resize			(Image<? extends NumberTensor> _img, int _newWidth, int _newHeight) {
		if(_img.format().getWidth() == _newWidth && _img.format().getHeight() == _newHeight)
			return (Image<NumberTensor>) _img;
		
		switch(_img.format().getPrimitive()) {
		case BYTE:		return resizeByte	((Image<ByteTensor>) _img, _newWidth, _newHeight);
		case SHORT:		return resizeShort	((Image<ShortTensor>) _img, _newWidth, _newHeight);
		case INTEGER:	return resizeInt	((Image<IntTensor>) _img, _newWidth, _newHeight);
		case LONG:		return resizeLong	((Image<LongTensor>) _img, _newWidth, _newHeight);
		case FLOAT:		return resizeFloat	((Image<FloatTensor>) _img, _newWidth, _newHeight);
		case DOUBLE:	return resizeDouble	((Image<DoubleTensor>) _img, _newWidth, _newHeight);
		default:		throw new NotYetImplementedException();
		}
	}

	public static Image<ByteTensor> 				resizeByte		(Image<ByteTensor> _img, int _newWidth, int _newHeight) {
		byte[] temp = new byte[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return Byte.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}
	public static Image<ShortTensor> 				resizeShort		(Image<ShortTensor> _img, int _newWidth, int _newHeight) {
		short[] temp = new short[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return TensorImages.Short.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}
	public static Image<IntTensor>  				resizeInt		(Image<IntTensor> _img, int _newWidth, int _newHeight) {
		int[] temp = new int[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return TensorImages.Int.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}
	public static Image<LongTensor>  				resizeLong		(Image<LongTensor> _img, int _newWidth, int _newHeight) {
		long[] temp = new long[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return TensorImages.Long.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}
	public static Image<FloatTensor>  				resizeFloat		(Image<FloatTensor> _img, int _newWidth, int _newHeight) {
		float[] temp = new float[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return TensorImages.Float.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}
	public static Image<DoubleTensor>  				resizeDouble	(Image<DoubleTensor> _img, int _newWidth, int _newHeight) {
		double[] temp = new double[_img.format().getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    int x_ratio = (int) ((_img.format().getWidth() << 16) / _newWidth) + 1;
	    int y_ratio = (int) ((_img.format().getHeight() << 16) / _newHeight) + 1;
	    int I, J ;
	    for(int k=0;k<_img.format().getDepth();k++) {
		    for(int j=0;j<_newHeight;j++) {
		        for(int i=0;i<_newWidth;i++) {
		            I = ((i * x_ratio) >> 16);
		            J = ((j * y_ratio) >> 16);
		            temp[k * newChannelStride + (j * _newWidth) + i] = _img.data().getValue(k, J, I);
		        }
		    }
	    }

	    return TensorImages.Double.wrap(temp, _img.format().getDepth(), _newHeight, _newWidth);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*

	public static TensorImage 	convert(TensorImage _img, PixelFormat _newPxf) {
		if(_img.getWidth() == _newWidth && _img.getHeight() == _newHeight)
			return _img;
		
		switch(_img.getPrimitive()) {
		case BYTE:		return resizeByte	((ByteImage) _img, _newWidth, _newHeight);
		case SHORT:		return resizeShort	((ShortImage) _img, _newWidth, _newHeight);
		case INTEGER:	return resizeInt	((IntImage) _img, _newWidth, _newHeight);
		case LONG:		return resizeLong	((LongImage) _img, _newWidth, _newHeight);
		case FLOAT:		return resizeFloat	((FloatImage) _img, _newWidth, _newHeight);
		case DOUBLE:	return resizeDouble	((DoubleImage) _img, _newWidth, _newHeight);
		default:		throw new NotYetImplementedException();
		}
	}
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static Color[] createLinearPalette(Color _from, Color _to) {
		Color[] colors = new Color[256];
		for(int i = 0; i < 256; ++i)
			colors[i] = Colors.interpolate(_from, _to, i / 255.0);
        return colors;
    }

	public static Color[] randomPalette() {
		Random _r = new Random();
		Color[] colors = new Color[256];
		for(int i = 1; i < 255; ++i)
			colors[i] = Colors.of(_r.nextInt(256), _r.nextInt(256), _r.nextInt(256));
		colors[  0] = Colors.BLACK;
		colors[255] = Colors.WHITE;
        return colors;
    }
	
	public static final Color[] greenPalette = createLinearPalette(Colors.BLACK, Colors.GREEN);

	public static ByteTensor colorize(ByteTensor image, Color[] _palette) {
		int width  = image.getSliceDimension(0, true);
		int height = image.getSliceDimension(1, true);

		if(image.getSliceDimension(2, true) != 1)
			throw new IllegalArgumentException("image is not 1-depth");

		ByteTensor color3 = new ByteArrayTensor(true, 3, height, width);
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				int a   = image.getValue(0, row, col) & 255;

				int r   = (int) (_palette[a].toRGB().getRed() * 255.0);
				int g   = (int) (_palette[a].toRGB().getGreen() * 255.0);
				int b   = (int) (_palette[a].toRGB().getBlue() * 255.0);

				color3.setValue((byte) (r), 0, col, row); // TODO:: CHECK IT !!!
				color3.setValue((byte) (g), 1, col, row);
				color3.setValue((byte) (b), 2, col, row);
			}
		}

		return color3;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	


	public static NumberTensor 	convert(BufferedImage _image, PixelFormat _pxf, Primitive _primitive, Interval<?>... _channelRemappers) {
		int       width    = _image.getWidth();
		int       height   = _image.getHeight();

		switch(_primitive) {
		case BYTE    : byteFrom		(_image); break;
		case SHORT   : shortFrom	(_image); break;
		case INTEGER : intFrom		(_image); break;
		case LONG    : longFrom		(_image); break;
		case FLOAT   : floatFrom	(_image); break;
		case DOUBLE  : doubleFrom	(_image); break;

		case BOOLEAN : throw new UnsupportedOperationException();
		case STRING  : throw new UnsupportedOperationException();
		case UNDEF   : throw new UnsupportedOperationException();
		default      : throw new UnsupportedOperationException();
		}

		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
/*
	public static FloatImage 	resizeFloatDeprecated(FloatImage _img, int _newWidth, int _newHeight) {
		float[] temp = new float[_img.getDepth()*_newWidth*_newHeight];

		int newChannelStride = _newWidth * _newHeight;
	    double x_ratio = _img.getWidth() / (double)_newWidth;
	    double y_ratio = _img.getHeight() / (double)_newHeight;
	    double px, py; 
	    for(int k=0;k<_img.getDepth();k++) {
		    for(int i=0;i<_newHeight;i++) {
		        for(int j=0;j<_newWidth;j++) {
		            px = Math.floor(j*x_ratio);
		            py = Math.floor(i*y_ratio);
		            temp[k * newChannelStride + (i*_newWidth)+j] = _img.getValue(k, (int) py, (int) px);
		        }
		    }
	    }

	    return new FloatImageImpl(temp, _img.getDepth(), _newHeight, _newWidth);
	}

	static byte[] getByteBuffer(TensorImage _img, PixelFormat _pxf) {
		if(_img instanceof ByteImage)
			return getByteBuffer((ByteImage) _img, _pxf);
		if(_img instanceof IntImage)
			return getByteBuffer((IntImage) _img, _pxf);
		if(_img instanceof DoubleImage)
			return getByteBuffer((DoubleImage) _img, _pxf);
		return null;
	}

	public static byte[] getByteBuffer(ByteImage _img, PixelFormat _pxf) {
		byte[] bytes = new byte[_img.getWidth() * _img.getHeight() * _pxf.bytesPerPixel()];

		switch(_img.getRealNature()) {
		case MATRIX:	
		case CUBE:
		case TESSERACT:
		case HYPERCUBE:	return convertTensor(_img.getHeight(), _img.getWidth(), _img.getDepth(), _img.getArray(), defaultConversion(_img.getDepth(), _pxf), bytes, _pxf);
		case UNKNOWN:
		case SCALAR:
		case VECTOR:
		default:		throw new NotYetImplementedException();
		
		}
	}
	public static byte[] getByteBuffer(IntImage _img, PixelFormat _pxf) {
		byte[] bytes = new byte[_img.getWidth() * _img.getHeight() * _pxf.bytesPerPixel()];

		switch(_img.getRealNature()) {
		case MATRIX:	
		case CUBE:
		case TESSERACT:
		case HYPERCUBE:	return convertTensor(_img.getHeight(), _img.getWidth(), _img.getDepth(), _img.getArray(), defaultConversion(_img.getDepth(), _pxf), bytes, _pxf);
		case UNKNOWN:
		case SCALAR:
		case VECTOR:
		default:		throw new NotYetImplementedException();
		
		}
	}
	public static byte[] getByteBuffer(DoubleImage _img, PixelFormat _pxf) {
		byte[] bytes = new byte[_img.getWidth() * _img.getHeight() * _pxf.bytesPerPixel()];

		switch(_img.getRealNature()) {
		case MATRIX:	
		case CUBE:
		case TESSERACT:
		case HYPERCUBE:	return convertTensor(_img.getHeight(), _img.getWidth(), _img.getDepth(), _img.getArray(), defaultConversion(_img.getDepth(), _pxf), bytes, _pxf);
		case UNKNOWN:
		case SCALAR:
		case VECTOR:
		default:		throw new NotYetImplementedException();
		
		}
	}

	public static int[] defaultConversion(int _d, PixelFormat _pxf) {
		switch(_pxf) {
		case PXF_L8:			return _d > 1 ? new int[] { 1 } :  new int[] { 0 };
		case PXF_AL8:			return _d > 1 ? new int[] { 0, 1 } :  new int[] { 0, 0 };
		case PXF_RGB8:		return _d > 2 ? new int[] { 0, 1, 2 } :  new int[] { 0, 0, 0 };
		case PXF_ARGB8:		return _d > 3 ? new int[] { 0, 1, 2, 3 } :  new int[] { 0, 0, 0, 0 };
		case PXF_RGBA8:		return _d > 3 ? new int[] { 1, 2, 3, 0 } :  new int[] { 0, 0, 0, 0 };
		case PXF_A1R5G5B5:
		case PXF_A4R4G4B4:
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5:
		case PXF_UNKNOWN:
		default:				throw new NotYetImplementedException();
		
		}
		
	}

	public static byte[] convertTensor(int _w, int _h, int _d, byte[] _src, int[] _chanSrc, byte[] _dst, PixelFormat _pxf) {
		if(_dst.length < _w * _h * _pxf.bytesPerPixel())
			throw new IllegalArgumentException();

		int luminance  = -1, alpha = -1, red = -1, green = -1, blue = -1;
		
		switch(_pxf) {
		case PXF_L8: 		luminance = _chanSrc != null ? _chanSrc[0] : 0;
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i)
									_dst[j * _w + i] = _src[_d * (j * _w + i) + luminance];
							break;

		case PXF_AL8: 		if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d)
										throw new IllegalArgumentException();
									luminance = _chanSrc[0];
									alpha     = _chanSrc[1];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d)
									throw new IllegalArgumentException();
								luminance = _chanSrc[0];
								alpha     = _chanSrc[1];
							}
			
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = _src[_d * (j * _w + i) + luminance];
									_dst[j * _w + i + 1] = _src[_d * (j * _w + i) + alpha];
								}
							break;

		case PXF_RGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
										throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_ARGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
										throw new IllegalArgumentException();
									alpha = _chanSrc[0];
									red   = _chanSrc[1];
									green = _chanSrc[2];
									blue  = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								alpha = _chanSrc[0];
								red   = _chanSrc[1];
								green = _chanSrc[2];
								blue  = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = _src[_d * (j * _w + i) + alpha];
									_dst[j * _w + i + 1] = _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 2] = _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 3] = _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_RGBA8: 	if(_d >= _pxf.bytesPerPixel()) {
							if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
								if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
									alpha = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
								alpha = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = _src[_d * (j * _w + i) + blue];
									_dst[j * _w + i + 3] = _src[_d * (j * _w + i) + alpha];
								}
							break;
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5: 	
		case PXF_A4R4G4B4: 	
		case PXF_A1R5G5B5: 	
		case PXF_UNKNOWN: 	
		default: 			throw new NotYetImplementedException();
		}
		
		return _dst;
	}
	public static byte[] convertTensor(int _w, int _h, int _d, int[] _src, int[] _chanSrc, byte[] _dst, PixelFormat _pxf) {
		if(_dst.length < _w * _h * _pxf.bytesPerPixel())
			throw new IllegalArgumentException();

		int luminance  = -1, alpha = -1, red = -1, green = -1, blue = -1;
		
		switch(_pxf) {
		case PXF_L8: 		luminance = _chanSrc != null ? _chanSrc[0] : 0;
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i)
									_dst[j * _w + i] = (byte) _src[_d * (j * _w + i) + luminance];
							break;

		case PXF_AL8: 		if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d)
										throw new IllegalArgumentException();
									luminance = _chanSrc[0];
									alpha     = _chanSrc[1];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d)
									throw new IllegalArgumentException();
								luminance = _chanSrc[0];
								alpha     = _chanSrc[1];
							}
			
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + luminance];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + alpha];
								}
							break;

		case PXF_RGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
										throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_ARGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
										throw new IllegalArgumentException();
									alpha = _chanSrc[0];
									red   = _chanSrc[1];
									green = _chanSrc[2];
									blue  = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								alpha = _chanSrc[0];
								red   = _chanSrc[1];
								green = _chanSrc[2];
								blue  = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + alpha];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 3] = (byte) _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_RGBA8: 	if(_d >= _pxf.bytesPerPixel()) {
							if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
								if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
									alpha = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
								alpha = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + blue];
									_dst[j * _w + i + 3] = (byte) _src[_d * (j * _w + i) + alpha];
								}
							break;
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5: 	
		case PXF_A4R4G4B4: 	
		case PXF_A1R5G5B5: 	
		case PXF_UNKNOWN: 	
		default: 			throw new NotYetImplementedException();
		}
		
		return _dst;
	}
	public static byte[] convertTensor(int _w, int _h, int _d, double[] _src, int[] _chanSrc, byte[] _dst, PixelFormat _pxf) {
		if(_dst.length < _w * _h * _pxf.bytesPerPixel())
			throw new IllegalArgumentException();

		int luminance  = -1, alpha = -1, red = -1, green = -1, blue = -1;
		
		switch(_pxf) {
		case PXF_L8: 		luminance = _chanSrc != null ? _chanSrc[0] : 0;
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i)
									_dst[j * _w + i] = (byte) _src[_d * (j * _w + i) + luminance];
							break;

		case PXF_AL8: 		if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d)
										throw new IllegalArgumentException();
									luminance = _chanSrc[0];
									alpha     = _chanSrc[1];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d)
									throw new IllegalArgumentException();
								luminance = _chanSrc[0];
								alpha     = _chanSrc[1];
							}
			
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + luminance];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + alpha];
								}
							break;

		case PXF_RGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
										throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_ARGB8: 	if(_d >= _pxf.bytesPerPixel()) {
								if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
									if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
										throw new IllegalArgumentException();
									alpha = _chanSrc[0];
									red   = _chanSrc[1];
									green = _chanSrc[2];
									blue  = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								alpha = _chanSrc[0];
								red   = _chanSrc[1];
								green = _chanSrc[2];
								blue  = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + alpha];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 3] = (byte) _src[_d * (j * _w + i) + blue];
								}
							break;
		case PXF_RGBA8: 	if(_d >= _pxf.bytesPerPixel()) {
							if(_chanSrc != null && _chanSrc.length >= _pxf.bytesPerPixel()) {
								if(_chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
									red   = _chanSrc[0];
									green = _chanSrc[1];
									blue  = _chanSrc[2];
									alpha = _chanSrc[3];
								}
							} else {
								if(_chanSrc == null || _chanSrc.length < _pxf.bytesPerPixel() || _chanSrc[0] > _d || _chanSrc[1] > _d || _chanSrc[2] > _d || _chanSrc[3] > _d)
									throw new IllegalArgumentException();
								red   = _chanSrc[0];
								green = _chanSrc[1];
								blue  = _chanSrc[2];
								alpha = _chanSrc[3];
							}
					
							for(int j = 0; j < _h; ++j)
								for(int i = 0; i < _w; ++i) {
									_dst[j * _w + i]     = (byte) _src[_d * (j * _w + i) + red];
									_dst[j * _w + i + 1] = (byte) _src[_d * (j * _w + i) + green];
									_dst[j * _w + i + 2] = (byte) _src[_d * (j * _w + i) + blue];
									_dst[j * _w + i + 3] = (byte) _src[_d * (j * _w + i) + alpha];
								}
							break;
		case PXF_DXTC1:
		case PXF_DXTC3:
		case PXF_DXTC5: 	
		case PXF_A4R4G4B4: 	
		case PXF_A1R5G5B5: 	
		case PXF_UNKNOWN: 	
		default: 			throw new NotYetImplementedException();
		}
		
		return _dst;
	}
*/
}
