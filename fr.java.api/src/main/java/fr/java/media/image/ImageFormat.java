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
 * @file     ImageInfo.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.media.image;

import fr.java.lang.enums.PixelFormat;
import fr.java.lang.enums.Primitive;
import fr.java.media.MediaFormat;

public interface ImageFormat extends MediaFormat {

	public static ImageFormat of(final int _w, final int _h, PixelFormat _pxf) {
		return new ImageFormat() {
			@Override public int 			getWidth() 			{ return _w; }
			@Override public int 			getHeight() 		{ return _h; }
			@Override public PixelFormat 	getPixelFormat() 	{ return _pxf; }
		};
	}

	public PixelFormat			getPixelFormat();
	public default Primitive 	getPrimitive() 			{ return getPixelFormat().primitive(); }

	public int 					getWidth();
	public int 					getHeight();
	public default int 			getDepth() 			{ return getPixelFormat().nbChannels(); }

}
