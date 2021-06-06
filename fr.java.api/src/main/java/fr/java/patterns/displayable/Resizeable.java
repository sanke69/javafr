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
 * @file     Resizeable.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns.displayable;

public interface Resizeable {

	public interface Editable  extends Resizeable {

		/**
		 * Defines whether this object shall be resizeable by the user.
		 *
		 * @param v the state to set
		 */
		public void 	setResizable(boolean v);

	}
	public interface OneDim    extends Resizeable {

		public interface Editable extends OneDim, Resizeable.Editable {

			public void setWidth(double _width);

		}

		public double 	getWidth();

	}
	public interface TwoDims   extends Resizeable.OneDim {

		public interface Editable extends TwoDims, OneDim.Editable {

			public void setHeight(double _height);

		}

		public double 	getHeight();

	}
	public interface ThreeDims extends Resizeable.TwoDims {

		public interface Editable extends ThreeDims, TwoDims.Editable {

			public void setDepth(double _depth);

		}

		public double 	getDepth();

	}

	public boolean 		isResizing();

	/**
	 * Indicates whether the control is resizeable by the user.
	 *
	 * @return <code>true</code> if the control is resizeable; <code>false</code> otherwise
	 */
	public boolean 		isResizable();

}
