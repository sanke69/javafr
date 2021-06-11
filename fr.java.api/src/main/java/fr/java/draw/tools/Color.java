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

public interface Color {

	public static interface RGB extends Color {
	    public double getRed();
	    public double getGreen();
	    public double getBlue();
	}
	public static interface HSB extends Color {
	    public double getHue();
	    public double getSaturation();
	    public double getBrightness();
	}

	public static interface Interpolable extends Color {
	    public Color 	brighter();
	    public Color 	darker();

	    public Color 	saturate();

	    public Color 	desaturate();

	    public Color 	grayscale();
	    public Color 	invert();

	    public Color 	interpolate(Color endValue, double t);
	    public Color 	derive(double hueShift, double saturationFactor, double brightnessFactor, double opacityFactor);
	    public Color 	opacity(double opacityFactor);
	}

	public RGB 				toRGB();
	public HSB 				toHSB();
	
    public default boolean 	isOpaque()   		{ return getOpacity() == 1d; }

    public default double   getRed() 	 		{ return toRGB().getRed(); }
    public default double   getGreen()   		{ return toRGB().getGreen(); }
    public default double   getBlue()    		{ return toRGB().getBlue(); }
    public double 			getOpacity();
    public double 			getTransparency();
//    public default double   getAlpha()   		{ return 1d - getOpacity(); }

}
