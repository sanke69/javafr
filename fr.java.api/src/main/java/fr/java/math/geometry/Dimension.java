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
 * @file     Dimension.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry;

import java.io.Serializable;
import java.text.NumberFormat;

public interface Dimension {

	public static interface OneDim extends Dimension, Serializable/*, Duplicable*/ {
	
		public static interface Editable extends Dimension.OneDim {
	
		    public void 					set(double _w);
	
		    public void 					setWidth(double _w);
	
		}
	
		public double 						getWidth();

//		@Override
//	    public Dimension.OneDim.Editable	clone();
	
	}

	public static interface TwoDims extends Dimension.OneDim {
	
		public static interface Editable extends Dimension.TwoDims, Dimension.OneDim.Editable {

		    public default void 			set(double _w) 							{ set(_w, getHeight()); }
		    public void 					set(double _w, double _h);
	
		    public void 					setHeight(double _h);
	
		}
	
		public double 						getHeight();

//		@Override
//	    public Dimension.TwoDims.Editable	clone();
	
	}

	public static interface ThreeDims extends Dimension.TwoDims {
	
		public static interface Editable extends Dimension.ThreeDims, Dimension.TwoDims.Editable {

		    public default void 			set(double _w) 							{ set(_w, getHeight(), getDepth()); }
		    public default void 			set(double _w, double _h)				{ set(_w, _h, getDepth()); }
		    public void 					set(double _w, double _h, double _d);
	
		    public void 					setDepth(double _h);
	
		}
	
		public double 						getDepth();

//		@Override
//	    public Dimension.ThreeDims.Editable	clone();
	
	}

//	public default String toString(DecimalFormat df) { return "TBD"; }
	public String toString(NumberFormat df);

}
