package fr.java.rasters.rasters.awt.bytes;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;

import fr.java.rasters.rasters.XRasterBufferedImage;

public class XRasterBufferedImageByteIndexed extends XRasterBufferedImage {
	private final byte[] buffer;

	public XRasterBufferedImageByteIndexed(BufferedImage _bi) {
		super(_bi);
		
		int        type    = _bi.getType();
		DataBuffer dbuffer = _bi.getRaster().getDataBuffer();

		if(type != BufferedImage.TYPE_BYTE_INDEXED || !(dbuffer instanceof DataBufferByte))
			throw new RuntimeException();

		buffer = ((DataBufferByte) dbuffer).getData();
	}

	@Override public byte   	 	getValue(int _i, int _j, int _k)	{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + _k)]; }

	@Override public byte   	 	getLuminance(int _i, int _j)   		{ return buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }

	@Override public byte   	 	getRed(int _i, int _j)   			{ return buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }
	@Override public byte    	 	getGreen(int _i, int _j) 			{ return buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }
	@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return buffer[(int) (_j * getWidth() * getDepth() + _i * getDepth())]; }

}
