package fr.java.rasters.rasters.awt;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.function.BiFunction;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.rasters.rasters.XRasterBufferedImage;

public class XRasterBufferedImageGeneric extends XRasterBufferedImage {
	private static final long serialVersionUID = 12345L;

	PixelBuffer   buffer;

	public XRasterBufferedImageGeneric(BufferedImage _bi) {
		super(_bi);

		DataBuffer dbuffer = _bi.getRaster().getDataBuffer();

		if(dbuffer instanceof DataBufferByte)
			buffer = new ByteBuffer( ((DataBufferByte) _bi.getRaster().getDataBuffer()), _bi.getType() );
		else if(dbuffer instanceof DataBufferInt)
			buffer = new IntBuffer( ((DataBufferInt) _bi.getRaster().getDataBuffer()), _bi.getType() );
	}

	@Override public byte   	 	getValue(int _i, int _j, int _k)	{ return buffer.getValue(_i, _j, _k); }

	@Override public byte   	 	getLuminance(int _i, int _j)   		{ return buffer.getLuminance(_i, _j); }

	@Override public byte   	 	getRed(int _i, int _j)   			{ return buffer.getRed(_i, _j); }
	@Override public byte    	 	getGreen(int _i, int _j) 			{ return buffer.getGreen(_i, _j); }
	@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return buffer.getBlue(_i, _j); }

	interface PixelBuffer {

		public byte   	 	getValue(int _i, int _j, int _k);
		
		public byte   	 	getLuminance(int _i, int _j);

		public byte   	 	getRed(int _i, int _j);
		public byte    	 	getGreen(int _i, int _j);
		public byte  	 	getBlue(int _i, int _j);

	}

	class ByteBuffer implements PixelBuffer {
		byte[] buffer;

		public BiFunction<Integer, Integer, Byte> rValue, gValue, bValue;

		public ByteBuffer(DataBufferByte _byteBuffer, int _type) {
			super();
			buffer = _byteBuffer.getData();

			switch(_type) {
			case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: 
			case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: 
			case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: rValue = gValue = bValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())];
															  break;
			case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: rValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 2)];
															  gValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 1)];
															  bValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())];
															  break;
			case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: rValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 3)];
															  gValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 2)];
															  bValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 1)];
															  break;
			case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : rValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 3)];
															  gValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 2)];
															  bValue = (_i, _j) -> buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth() + 1)];
															  break;

			case /*  0 */ BufferedImage.TYPE_CUSTOM 		: 
			case /*  1 */ BufferedImage.TYPE_INT_RGB 		: 
			case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: 
			case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: 
			case /*  4 */ BufferedImage.TYPE_INT_BGR 		: 
			case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: 
			case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : 
			case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : 
			default											: throw new NotYetImplementedException();
			}

		}

		@Override public byte   	 	getValue(int _i, int _j, int _k)   	{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + _k)]; }

		@Override public byte   	 	getLuminance(int _i, int _j)   		{ return buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }

		@Override public byte   	 	getRed(int _i, int _j)   			{ return rValue.apply(_i, _j); }
		@Override public byte    	 	getGreen(int _i, int _j) 			{ return gValue.apply(_i, _j); }
		@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return bValue.apply(_i, _j); }

	}
	class IntBuffer  implements PixelBuffer {
		int[] buffer;

		public BiFunction<Integer, Integer, Byte> rValue;
		public BiFunction<Integer, Integer, Byte> gValue;
		public BiFunction<Integer, Integer, Byte> bValue;

		public IntBuffer(DataBufferInt _intBuffer, int _type) {
			super();
			buffer = _intBuffer.getData();

			switch(_type) {
			case /*  1 */ BufferedImage.TYPE_INT_RGB 		: rValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)]);
															  gValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >>  8);
															  bValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >> 16);
															  break;
			case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: rValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >>  8);
															  gValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >> 16);
															  bValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >> 24);
															  break;
			case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: rValue = (_i, _j) -> (byte) ((buffer[(int) (_j * getWidth() + _i)] >> 16));
															  gValue = (_i, _j) -> (byte) ((buffer[(int) (_j * getWidth() + _i)] >> 8));
															  bValue = (_i, _j) -> (byte) ((buffer[(int) (_j * getWidth() + _i)]));
															  break;
			case /*  4 */ BufferedImage.TYPE_INT_BGR 		: rValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >> 16);
															  gValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)] >>  8);
															  bValue = (_i, _j) -> (byte) (buffer[(int) (_j * getWidth() + _i)]);
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

		}

		@Override public byte   	 	getValue(int _i, int _j, int _k)   	{ return (byte) buffer[(int) (getDepth() * (_j * getWidth() + _i) + _k)]; }

		@Override public byte   	 	getLuminance(int _i, int _j)   		{ return (byte) buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }

		@Override public byte   	 	getRed(int _i, int _j)   			{ return rValue.apply(_i, _j); }
		@Override public byte    	 	getGreen(int _i, int _j) 			{ return gValue.apply(_i, _j); }
		@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return bValue.apply(_i, _j); }

	}

}
