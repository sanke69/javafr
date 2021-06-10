package fr.java.rasters.rasters.awt.ints;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import fr.java.rasters.rasters.XRasterBufferedImage;

public class XRasterBufferedImageIntBGR extends XRasterBufferedImage {
	private static final long serialVersionUID = 12346L;

	int[]   buffer;

	public XRasterBufferedImageIntBGR(BufferedImage _bi) {
		super(_bi);
		
		int        type    = _bi.getType();
		DataBuffer dbuffer = _bi.getRaster().getDataBuffer();

		if(type != BufferedImage.TYPE_INT_BGR || !(dbuffer instanceof DataBufferInt))
			throw new RuntimeException();

		buffer = ((DataBufferInt) dbuffer).getData();
	}

	@Override public byte   	 	getValue(int _i, int _j, int _k)	{ return (byte) buffer[(int) (getDepth() * (_j * getWidth() + _i) + _k)]; }

	@Override public byte   	 	getLuminance(int _i, int _j)   		{ return (byte) buffer[(int) (getDepth() * (_j * getWidth() + _i))]; }

	@Override public byte   	 	getRed(int _i, int _j)   			{ return (byte) (buffer[(int) (_j * getWidth() + _i)] >> 16); }
	@Override public byte    	 	getGreen(int _i, int _j) 			{ return (byte) (buffer[(int) (_j * getWidth() + _i)] >>  8); }
	@Override public byte  	 	 	getBlue(int _i, int _j)  			{ return (byte) (buffer[(int) (_j * getWidth() + _i)]); }

}
