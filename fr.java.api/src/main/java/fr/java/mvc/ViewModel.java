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
package fr.java.mvc;

public interface ViewModel {

	public interface Editable  extends ViewModel {

		public void 						setVisible(boolean _enable);
		public void 						setCloseable(boolean _enable);

		public void 						setMovable(boolean v);
		public void 						setResizable(boolean v);

		public void 						setSelectable(boolean _value);
		public boolean 						requestSelection(boolean b);

	}

	public interface OneDim    extends ViewModel {

		public interface Editable extends ViewModel.OneDim, ViewModel.Editable {

			public void 					setX(double _x);
			public void 					setWidth(double _width);

		}

		public double 						getX();
		public double 						getWidth();

	}
	public interface TwoDims   extends ViewModel.OneDim {

		public interface Editable extends ViewModel.TwoDims, ViewModel.OneDim.Editable {

			public void 					setY(double _y);
			public void 					setHeight(double _height);

		}

		public double 						getY();
		public double 						getHeight();

	}
	public interface ThreeDims extends ViewModel.TwoDims {

		public interface Editable extends ViewModel.ThreeDims, ViewModel.TwoDims.Editable {

			public void 					setZ(double _z);
			public void 					setDepth(double _depth);

		}

		public double 						getZ();
		public double 						getDepth();

	}

	public boolean 		isCloseable();
	public boolean 		isSelectable();
	public boolean 		isMoveable();
	public boolean 		isResizable();

	public boolean 		isVisible();
	public boolean 		isSelected();
	public boolean 		isMoving();
	public boolean 		isResizing();

	public boolean		isCloseRequested();

}
