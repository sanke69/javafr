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
package fr.java.math.geometry.space;

import java.io.Serializable;

import fr.java.math.geometry.Dimension;

public interface Dimension3D extends Dimension, Serializable {

	public static interface Editable extends Dimension3D {

		public void set(double _w, double _h, double _d);

		public void setWidth(double _w);
		public void setHeight(double _h);
		public void setDepth(double _d);

	}
	
	public double 			getWidth();
	public double 			getHeight();
	public double 			getDepth();

	public default double 	getGreatest() { return getWidth() > getHeight() ? (getWidth() > getDepth() ? getWidth() : getDepth()) : (getHeight() > getDepth() ? getHeight() : getDepth()); }

}
