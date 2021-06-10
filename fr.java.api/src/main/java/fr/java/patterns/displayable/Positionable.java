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
package fr.java.patterns.displayable;

@Deprecated
public interface Positionable {

	public interface OneDim extends Positionable {

//		public DoubleBeanProperty 	xProperty();
		public void 				setX(double _x);
		public double 				getX();

	}
	public interface TwoDims extends Positionable.OneDim {

//		public DoubleBeanProperty 	yProperty();
		public void 				setY(double _y);
		public double 				getY();

	}

}
