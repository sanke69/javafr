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
 * @file     Moveable.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns.displayable;

public interface Moveable {

	public interface Editable extends Moveable {

		/**
		 * Defines whether this object shall be movable.
		 *
		 * @param v the state to set
		 */
		public void 				setMovable(boolean v);

	}
	public interface OneDim   extends Moveable {

		public interface Editable extends OneDim, Moveable.Editable {

			public void 			setX(double _x);

		}

		public double 				getX();

	}
	public interface TwoDims  extends Moveable.OneDim {

		public interface Editable extends TwoDims, OneDim.Editable {

			public void 			setY(double _y);

		}

		public double 				getY();

	}

	/**
	 * Indicates whether the object is movable.
	 *
	 * @return <code>true</code> if the window is movable; <code>false</code>
	 * otherwise
	 */
	public boolean 					isMoveable();

	/**
	 * Indicates whether this object is currently moving.
	 *
	 * @return {@code} if this object is currently moving; {@code false} otherwise
	 */
	public boolean 					isMoving();

}
