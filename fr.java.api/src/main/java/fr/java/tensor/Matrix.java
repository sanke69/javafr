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
 * @file     Matrix.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.tensor;

import java.text.NumberFormat;

import fr.java.math.algebra.NumberTensor;

public interface Matrix extends NumberTensor {

	public interface Editable extends Matrix {

		public void				setRow(final Vector _row, final int _i);
		public void				setColumn(final Vector _column, final int _j);

		public void 			setMatrix(final Matrix _X, final int _i0, final int _i1, final int _j0, final int _j1);
		public void 			setMatrix(final Matrix _X, final int[] _rows, final int[] _columns);
		public void 			setMatrix(final Matrix _X, final int[] _rows, final int _j0, final int _j1);
		public void 			setMatrix(final Matrix _X, final int _i0, final int _i1, final int[] _columns);

	}

	// TENSOR METHODS
	@Override
	public default Nature 		getNature() {
		return Nature.MATRIX;
	}
	@Override
	public default Nature 		getNature(final boolean _simplified) {
		if(!_simplified)
			return getNature();
		if(columns() == 1 && rows() == 1)
			return Nature.SCALAR;
		return columns() == 1 || rows() == 1 ? Nature.VECTOR : Nature.MATRIX;
	}

	@Override
	public default int[] 		getShape() {
		return new int[] { rows(), columns() };
	}
	@Override
	public default int		 	getCapacity() {
		return columns() * rows();
	}
	@Override
	public default int		 	getSliceCapacity(final int _depth) {
		return _depth == 0 ? columns() * rows() : rows();
	}

	// MATRIX METHODS
	public int 					rows();
	public int 					columns();

	public Vector				getRow(final int _i);
	public Vector				getColumn(final int _j);

	public Matrix 				getMatrix(final int _i0, final int _i1, final int _j0, final int _j1);
	public Matrix 				getMatrix(final int[] _rows, final int[] _columns);
	public Matrix 				getMatrix(final int _i0, final int _i1, final int[] _columns);
	public Matrix 				getMatrix(final int[] _rows, final int _j0, final int _j1);
	
	public boolean 				isEqual(final Matrix _d);

	public boolean 				isDifferent(final Matrix _d);

	@Override
	public Matrix 				clone();

	public String 				toString(final NumberFormat _nf);

}
