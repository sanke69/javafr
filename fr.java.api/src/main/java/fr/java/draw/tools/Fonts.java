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
package fr.java.draw.tools;

import java.awt.Toolkit;

// TODO:: Move to SDK or Graphics
public class Fonts {
	static final double dpi;
	
	static {
		dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	}

	// https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html#getSize()
	public static double getHeight(Font _font) {
		System.out.println("Text height ~ " + (72d * _font.getSize() / dpi) + "\t(" + _font.getSize() + ")");
		return 72d * _font.getSize() / dpi;
	}
	
	public static double getWidth(String _text, Font _font) {
		System.out.println("Text width ~ " + (_text.length() * getHeight(_font)));
		return _text.length() * getHeight(_font);
	}

}
