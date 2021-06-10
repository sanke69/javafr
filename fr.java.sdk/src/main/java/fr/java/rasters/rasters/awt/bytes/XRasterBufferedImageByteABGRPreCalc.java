package fr.java.rasters.rasters.awt.bytes;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;

import fr.java.rasters.rasters.XRasterBufferedImage;

public class XRasterBufferedImageByteABGRPreCalc extends XRasterBufferedImage {
	private static final long serialVersionUID = 12345L;

	private final byte[] buffer;

	public XRasterBufferedImageByteABGRPreCalc(BufferedImage _bi) {
		super(_bi);
		
		int        type    = _bi.getType();
		DataBuffer dbuffer = _bi.getRaster().getDataBuffer();

		if(type != BufferedImage.TYPE_4BYTE_ABGR_PRE || !(dbuffer instanceof DataBufferByte))
			throw new RuntimeException();

		buffer = ((DataBufferByte) dbuffer).getData();
	}

	@Override public byte   	 	getValue(int _i, int _j, int _k)	{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + _k)]; }

	@Override public byte   	 	getLuminance(int _i, int _j)   		{ return buffer[(int) (getDepth() * (_j * getWidth() + _i))]; }

	@Override public byte   	 	getRed(int _i, int _j)   			{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + 3)]; }
	@Override public byte    	 	getGreen(int _i, int _j) 			{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + 2)]; }
	@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return buffer[(int) (getDepth() * (_j * getWidth() + _i) + 1)]; }

}
