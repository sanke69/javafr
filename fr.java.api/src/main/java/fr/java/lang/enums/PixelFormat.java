/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.lang.enums;

import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.IllegalFormatFlagsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.java.lang.exceptions.NotYetImplementedException;

public enum PixelFormat {
		// BINARY
    PXF_BINARY,  

		// LUMINANCE
    PXF_L,
    PXF_L1,
    PXF_L8,
    PXF_L16, 
    PXF_L32, 
    PXF_L32F, 
    PXF_L64, 
    PXF_L64D, 

		// LUMINANCE ALPHA
	PXF_LA, 
	PXF_LA1, 
	PXF_LA8, 
	PXF_LA16, 
	PXF_LA32, 
	PXF_LA32F, 
	PXF_LA64, 
	PXF_LA64D, 

		// ALPHA LUMINANCE
    PXF_AL, 
    PXF_AL1, 
    PXF_AL8, 
    PXF_AL16, 
    PXF_AL32, 
    PXF_AL32F, 
    PXF_AL64, 
    PXF_AL64D, 

		// RED GREEN BLUE
	PXF_RGB, 
	PXF_RGB1, 
	PXF_RGB8, 
	PXF_RGB16, 
	PXF_RGB32, 
	PXF_RGB32F, 
	PXF_RGB64, 
	PXF_RGB64D, 

		// RED GREEN BLUE ALPHA
	PXF_RGBA, 
	PXF_RGBA1, 
	PXF_RGBA8, 
	PXF_RGBA16, 
	PXF_RGBA32, 
	PXF_RGBA32F, 
	PXF_RGBA64, 
	PXF_RGBA64D, 

		// ALPHA RED GREEN BLUE
	PXF_ARGB, 
	PXF_ARGB1, 
	PXF_ARGB8, 
	PXF_ARGB16, 
	PXF_ARGB32, 
	PXF_ARGB32F, 
	PXF_ARGB64, 
	PXF_ARGB64D, 

		// BLUE GREEN RED
	PXF_BGR, 
	PXF_BGR1, 
	PXF_BGR8, 
	PXF_BGR16, 
	PXF_BGR32, 
	PXF_BGR32F, 
	PXF_BGR64, 
	PXF_BGR64D, 

		// BLUE GREEN RED ALPHA
	PXF_BGRA, 
	PXF_BGRA1, 
	PXF_BGRA8, 
	PXF_BGRA16, 
	PXF_BGRA32, 
	PXF_BGRA32F, 
	PXF_BGRA64, 
	PXF_BGRA64D, 

		// ALPHA BLUE GREEN RED
	PXF_ABGR, 
	PXF_ABGR1, 
	PXF_ABGR8, 
	PXF_ABGR16, 
	PXF_ABGR32, 
	PXF_ABGR32F, 
	PXF_ABGR64, 
	PXF_ABGR64D, 

		// LUMA CHROMINANCE_U CHROMINANCE_V - see https://fr.wikipedia.org/wiki/YUV
	PXF_YUV, 
	PXF_YUV1, 
	PXF_YUV8, 
	PXF_YUV16,
	PXF_YUV32,
	PXF_YUV32F,
	PXF_YUV64,
	PXF_YUV64D,

    	// COMPRESSED FORMAT
    PXF_A1R5G5B5, 
    PXF_A4R4G4B4, 
    PXF_DXTC1, 
    PXF_DXTC3, 
    PXF_DXTC5, 
    PXF_D24S8,		// 24-bits Depth, 8-bits Stencil	(OpenGL Compatibility)

    // NON EXPLICIT FORMAT
    PXF_CUSTOM,
    PXF_DEFAULT,
    PXF_UNKNOWN;

	private static final EnumSet<PixelFormat> compressed = EnumSet.of(PXF_A1R5G5B5, PXF_A4R4G4B4, PXF_DXTC1, PXF_DXTC3, PXF_DXTC5, PXF_D24S8);

	private static final EnumSet<PixelFormat> luminance  = EnumSet.of(PXF_L1,    PXF_L8,    PXF_L16,    PXF_L32,    PXF_L32F,    PXF_L64,    PXF_L64D);
	private static final EnumSet<PixelFormat> lumalpha   = EnumSet.of(PXF_LA1,   PXF_LA8,   PXF_LA16,   PXF_LA32,   PXF_LA32F,   PXF_LA64,   PXF_LA64D);
	private static final EnumSet<PixelFormat> alphalum   = EnumSet.of(PXF_AL1,   PXF_AL8,   PXF_AL16,   PXF_AL32,   PXF_AL32F,   PXF_AL64,   PXF_AL64D);
	private static final EnumSet<PixelFormat> rgb        = EnumSet.of(PXF_RGB1,  PXF_RGB8,  PXF_RGB16,  PXF_RGB32,  PXF_RGB32F,  PXF_RGB64,  PXF_RGB64D);
	private static final EnumSet<PixelFormat> bgr        = EnumSet.of(PXF_BGR1,  PXF_BGR8,  PXF_BGR16,  PXF_BGR32,  PXF_BGR32F,  PXF_BGR64,  PXF_BGR64D);
	private static final EnumSet<PixelFormat> yuv        = EnumSet.of(PXF_YUV1,  PXF_YUV8,  PXF_YUV16,  PXF_YUV32,  PXF_YUV32F,  PXF_YUV64,  PXF_YUV64D);
	private static final EnumSet<PixelFormat> rgba       = EnumSet.of(PXF_RGBA1, PXF_RGBA8, PXF_RGBA16, PXF_RGBA32, PXF_RGBA32F, PXF_RGBA64, PXF_RGBA64D);
	private static final EnumSet<PixelFormat> argb       = EnumSet.of(PXF_ARGB1, PXF_ARGB8, PXF_ARGB16, PXF_ARGB32, PXF_ARGB32F, PXF_ARGB64, PXF_ARGB64D);
	private static final EnumSet<PixelFormat> bgra       = EnumSet.of(PXF_BGRA1, PXF_BGRA8, PXF_BGRA16, PXF_BGRA32, PXF_BGRA32F, PXF_BGRA64, PXF_BGRA64D);
	private static final EnumSet<PixelFormat> abgr       = EnumSet.of(PXF_ABGR1, PXF_ABGR8, PXF_ABGR16, PXF_ABGR32, PXF_ABGR32F, PXF_ABGR64, PXF_ABGR64D);

	private static final EnumSet<PixelFormat> oneByte    = EnumSet.of(PXF_L8,  PXF_AL8,  PXF_LA8,  PXF_RGB8,  PXF_BGR8,  PXF_RGBA8,  PXF_ARGB8,  PXF_BGRA8,  PXF_ABGR8);
	private static final EnumSet<PixelFormat> twoBytes   = EnumSet.of(PXF_L16, PXF_AL16, PXF_LA16, PXF_RGB16, PXF_BGR16, PXF_RGBA16, PXF_ARGB16, PXF_BGRA16, PXF_ABGR16);
	private static final EnumSet<PixelFormat> fourBytes  = EnumSet.of(PXF_L32, PXF_AL32, PXF_LA32, PXF_RGB32, PXF_BGR32, PXF_RGBA32, PXF_ARGB32, PXF_BGRA32, PXF_ABGR32, PXF_L32F, PXF_AL32F, PXF_LA32F, PXF_RGB32F, PXF_BGR32F, PXF_RGBA32F, PXF_ARGB32F, PXF_BGRA32F, PXF_ABGR32F);
	private static final EnumSet<PixelFormat> eightBytes = EnumSet.of(PXF_L64, PXF_AL64, PXF_LA64, PXF_RGB64, PXF_BGR64, PXF_RGBA64, PXF_ARGB64, PXF_BGRA64, PXF_ABGR64, PXF_L64D, PXF_AL64D, PXF_LA64D, PXF_RGB64D, PXF_BGR64D, PXF_RGBA64D, PXF_ARGB64D, PXF_BGRA64D, PXF_ABGR64D);

	private static final EnumSet<PixelFormat> bytes      = EnumSet.of(PXF_L8,   PXF_AL8,   PXF_LA8,   PXF_RGB8,   PXF_BGR8,   PXF_RGBA8,   PXF_ARGB8,   PXF_BGRA8,   PXF_ABGR8);
	private static final EnumSet<PixelFormat> shorts     = EnumSet.of(PXF_L16,  PXF_AL16,  PXF_LA16,  PXF_RGB16,  PXF_BGR16,  PXF_RGBA16,  PXF_ARGB16,  PXF_BGRA16,  PXF_ABGR16);
	private static final EnumSet<PixelFormat> ints       = EnumSet.of(PXF_L32,  PXF_AL32,  PXF_LA32,  PXF_RGB32,  PXF_BGR32,  PXF_RGBA32,  PXF_ARGB32,  PXF_BGRA32,  PXF_ABGR32);
	private static final EnumSet<PixelFormat> longs      = EnumSet.of(PXF_L32F, PXF_AL32F, PXF_LA32F, PXF_RGB32F, PXF_BGR32F, PXF_RGBA32F, PXF_ARGB32F, PXF_BGRA32F, PXF_ABGR32F);
	private static final EnumSet<PixelFormat> floats     = EnumSet.of(PXF_L64,  PXF_AL64,  PXF_LA64,  PXF_RGB64,  PXF_BGR64,  PXF_RGBA64,  PXF_ARGB64,  PXF_BGRA64,  PXF_ABGR64);
	private static final EnumSet<PixelFormat> doubles    = EnumSet.of(PXF_L64D, PXF_AL64D, PXF_LA64D, PXF_RGB64D, PXF_BGR64D, PXF_RGBA64D, PXF_ARGB64D, PXF_BGRA64D, PXF_ABGR64D);

	public static PixelFormat of(int _depth) {
		return of(_depth, Primitive.BYTE);
	}
	public static PixelFormat of(int _depth, Primitive _primitive) {
		switch(_depth) {
		case 1  : switch(_primitive) {
					case BYTE:		return PXF_L8;
					case SHORT:		return PXF_L16;
					case INTEGER:	return PXF_L32;
					case LONG:		return PXF_L64;
					case FLOAT:		return PXF_L32F;
					case DOUBLE:	return PXF_L64D;
					case BOOLEAN:
					case STRING:
					case UNDEF:
					default:		throw new IllegalArgumentException("No available definition for " + _primitive);
					}
		case 2  : switch(_primitive) {
					case BYTE:		return PXF_AL8;
					case SHORT:		return PXF_AL16;
					case INTEGER:	return PXF_AL32;
					case LONG:		return PXF_AL64;
					case FLOAT:		return PXF_AL32F;
					case DOUBLE:	return PXF_AL64D;
					case BOOLEAN:
					case STRING:
					case UNDEF:
					default:		throw new IllegalArgumentException("No available definition for " + _primitive);
					}
		case 3  : switch(_primitive) {
					case BYTE:		return PXF_RGB8;
					case SHORT:		return PXF_RGB16;
					case INTEGER:	return PXF_RGB32;
					case LONG:		return PXF_RGB64;
					case FLOAT:		return PXF_RGB32F;
					case DOUBLE:	return PXF_RGB64D;
					case BOOLEAN:
					case STRING:
					case UNDEF:
					default:		throw new IllegalArgumentException("No available definition for " + _primitive);
					}
		case 4  : switch(_primitive) {
					case BYTE:		return PXF_RGBA8;
					case SHORT:		return PXF_RGBA16;
					case INTEGER:	return PXF_RGBA32;
					case LONG:		return PXF_RGBA64;
					case FLOAT:		return PXF_RGBA32F;
					case DOUBLE:	return PXF_RGBA64D;
					case BOOLEAN:
					case STRING:
					case UNDEF:
					default:		throw new IllegalArgumentException("No available definition for " + _primitive);
					}
		default : throw new IllegalArgumentException("No available definition for " + _depth  + " / " + _primitive);
		}
	}
	public static PixelFormat of(PixelFormat _pxf, Primitive _primitive) {
		if(_pxf.primitive() == _primitive)
			return _pxf;

		String regex = "PXF_" 
					 + "(?<FMT>LA|AL|L|RGB|BGR|ARGB|ABGR|RGBA|BGRA|YUV)"
					 + "(?<PRI>\\d+(D|F)?)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(_pxf.name());

		if(matcher.find()) {
			String pixelFormatName = "PXF_" + matcher.group("FMT") + _primitive.suffix();
			return PixelFormat.valueOf(pixelFormatName);
		}

		throw new IllegalArgumentException();
	}
	public static PixelFormat of(BufferedImage _bi) {
		switch(_bi.getType()) {
		case /*  0 */ BufferedImage.TYPE_CUSTOM 		: throw new NotYetImplementedException();

		case /*  1 */ BufferedImage.TYPE_INT_RGB 		: return PixelFormat.PXF_RGB8;
		case /*  4 */ BufferedImage.TYPE_INT_BGR 		: return PixelFormat.PXF_BGR8;
		case /*  2 */ BufferedImage.TYPE_INT_ARGB 		: return PixelFormat.PXF_ARGB8;
		case /*  3 */ BufferedImage.TYPE_INT_ARGB_PRE 	: return PixelFormat.PXF_ARGB8;

		case /* 12 */ BufferedImage.TYPE_BYTE_BINARY 	: return PixelFormat.PXF_BINARY;
		case /* 10 */ BufferedImage.TYPE_BYTE_GRAY 		: return PixelFormat.PXF_L8;
		case /*  5 */ BufferedImage.TYPE_3BYTE_BGR 		: return PixelFormat.PXF_BGR8;
		case /*  6 */ BufferedImage.TYPE_4BYTE_ABGR 	: return PixelFormat.PXF_ABGR8;
		case /*  7 */ BufferedImage.TYPE_4BYTE_ABGR_PRE : return PixelFormat.PXF_ABGR8;

		case /* 11 */ BufferedImage.TYPE_USHORT_GRAY 	: return PixelFormat.PXF_L16;
		case /*  8 */ BufferedImage.TYPE_USHORT_565_RGB : throw new NotYetImplementedException();
		case /*  9 */ BufferedImage.TYPE_USHORT_555_RGB : throw new NotYetImplementedException();

		case /* 13 */ BufferedImage.TYPE_BYTE_INDEXED 	: return PixelFormat.PXF_L8;
		}
		throw new NotYetImplementedException();
	}

	public static void main(String[] args) {
		PixelFormat.of(PixelFormat.PXF_ABGR64D, Primitive.FLOAT);
	}
	
	public static Primitive  getPrimitive(PixelFormat _pxf) {
		if(bytes.contains(_pxf))
			return Primitive.BYTE;
		if(shorts.contains(_pxf))
			return Primitive.SHORT;
		if(ints.contains(_pxf))
			return Primitive.INTEGER;
		if(longs.contains(_pxf))
			return Primitive.LONG;
		if(floats.contains(_pxf))
			return Primitive.FLOAT;
		if(doubles.contains(_pxf))
			return Primitive.DOUBLE;
		throw new IllegalFormatFlagsException("Invalid PixelFormat");
	}
	public static boolean     areCompatible(PixelFormat _dst, PixelFormat _src) {
		return getPrimitive(_src) == getPrimitive(_dst) && _dst.nbChannels() == _src.nbChannels();
	}

	public boolean 		isCompressed() {
        return compressed.contains(this);
    }

	public boolean 		isMonochrome() {
		if(luminance.contains(this) || lumalpha.contains(this) || alphalum.contains(this))
			return true;
		return false;
	}
	public boolean 		isRBG() {
		if(rgb.contains(this) || rgba.contains(this) || bgr.contains(this) || bgra.contains(this) || argb.contains(this) || abgr.contains(this))
			return true;
		return false;
	}

	public int			luminanceChannel() {
		if(this == PXF_BINARY || luminance.contains(this) || lumalpha.contains(this))
			return 0;
		if(alphalum.contains(this))
			return 1;

		return -1;
	}
	public int			alphaChannel() {
		if(argb.contains(this) || abgr.contains(this) || alphalum.contains(this))
			return 0;
		if(lumalpha.contains(this))
			return 1;
		if(rgba.contains(this) || bgra.contains(this))
			return 3;

		return -1;
	}
	
	public int			redChannel() {
		if(rgb.contains(this) || rgba.contains(this))
			return 0;
		if(bgr.contains(this) || bgra.contains(this))
			return 2;
		if(argb.contains(this))
			return 1;
		if(abgr.contains(this))
			return 3;

		return -1;
	}
	public int			greenChannel() {
		if(rgb.contains(this) || rgba.contains(this) || bgr.contains(this) || bgra.contains(this))
			return 1;
		if(argb.contains(this) || abgr.contains(this))
			return 2;

		return -1;
	}
	public int			blueChannel() {
		if(rgb.contains(this) || rgba.contains(this))
			return 2;
		if(bgr.contains(this) || bgra.contains(this))
			return 0;
		if(argb.contains(this))
			return 3;
		if(abgr.contains(this))
			return 1;

		return -1;
	}

	public Primitive	primitive() {
    	return getPrimitive(this);
    }

	public int 			bytesPerChannel() {
    	int bbc = oneByte.contains(this) ? 1 : twoBytes.contains(this) ? 2 : fourBytes.contains(this) ? 4 : eightBytes.contains(this) ? 8 : 0;
    	
    	if(bbc == 0)
    		throw new IllegalArgumentException("Unknown PixelFormat: " + this);
    	
    	return bbc;
    }
    public int 			nbChannels() {
    	if(this == PXF_BINARY || luminance.contains(this))
    		return 1;
    	if(alphalum.contains(this))
    		return 2;
    	if(rgb.contains(this) || bgr.contains(this) || yuv.contains(this))
    		return 3;
    	if(rgba.contains(this) || argb.contains(this) || bgra.contains(this) || abgr.contains(this))
    		return 4;

        switch(this) {
        case PXF_DXTC1    : return 4;
        case PXF_DXTC3    : return 4;
        case PXF_DXTC5    : return 4;
		default			  :	throw new IllegalArgumentException("Unknown PixelFormat: " + this);
	    }
    }

    public int 			bytesPerPixel() {
    	return nbChannels() * bytesPerChannel();
    }

};
