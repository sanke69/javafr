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
package fr.java.math.algebra;

import java.text.NumberFormat;

public interface NumberMatrix extends NumberTensor {

	public interface Editable extends NumberMatrix {

		public void 			setNumber(final int _i, final int _j, final Number _value);

		public void 			setMatrix(final int _i0, final int _i1, final int _j0, final int _j1, final NumberMatrix _X);
		public void 			setMatrix(final int[] _rows, final int[] _columns, final NumberMatrix _X);
		public void 			setMatrix(final int _i0, final int _i1, final int[] _columns, final NumberMatrix _X);
		public void 			setMatrix(final int[] _rows, final int _j0, final int _j1, final NumberMatrix _X);

		public NumberMatrix 	plusEquals(final Number  t);
		public NumberMatrix 	plusEquals(final NumberMatrix B);

		public NumberMatrix 	minusEquals(final Number  t);
		public NumberMatrix 	minusEquals(final NumberMatrix B);

		public NumberMatrix 	timesEquals(final Number s);

		public NumberMatrix 	arrayTimesEquals(final NumberMatrix B);
		public NumberMatrix 	arrayRightDivideEquals(final NumberMatrix B);
		public NumberMatrix 	arrayLeftDivideEquals(final NumberMatrix B);
	}

	// TENSOR METHODS
	@Override
	public default Nature 		getNature() {
		return Nature.MATRIX;
	}
	@Override
	public default Nature 		getNature(boolean _simplified) {
		if(!_simplified)
			return getNature();
		if(columns() == 1 && rows() == 1)
			return Nature.SCALAR;
		return columns() == 1 || rows() == 1 ? Nature.VECTOR : Nature.MATRIX;
	}

	@Override
	public default int[] 		getShape() {
		return new int[] { columns(), rows() };
	}
	@Override
	public default int		 	getCapacity() {
		return columns() * rows();
	}
	@Override
	public default int		 	getSliceCapacity(final int _depth) {
		return _depth == 0 ? columns() * rows() : rows();
	}
/*
	@Override
	public default String 		printDimension() {
		return "[ " + columns() + ", " + rows() + " ]";
	}
	@Override
	public default String 		printValues() {
		StringBuilder sb = new StringBuilder();

		sb.append("[ " + "\n");
		for(int j = 0; j < columns() - 1; ++j) {
			sb.append("\t" + "[ ");
			for(int i = 0; i < rows() - 1; ++i)
				sb.append(getNumber(i, j) + ", ");
			sb.append(getNumber(rows() - 1, j));
			sb.append(" ]" + "\n");
		}
		sb.append("\t" + " ]");

		return sb.toString();
	}
*/
	// MATRIX METHODS
	public int 					rows();
	public int 					columns();

	public Number 				getNumber(final int _i, final int _j);

	public NumberVector				getRow(final int _i);
	public NumberVector				getColumn(final int _j);

	public NumberMatrix 				getMatrix(final int _i0, final int _i1, final int _j0, final int _j1);
	public NumberMatrix 				getMatrix(final int[] _rows, final int[] _columns);
	public NumberMatrix 				getMatrix(final int _i0, final int _i1, final int[] _columns);
	public NumberMatrix 				getMatrix(final int[] _rows, final int _j0, final int _j1);
	
	public NumberMatrix 				plus(final Number  t);
	public NumberMatrix 				plus(final NumberMatrix B);

	public NumberMatrix 				minus(final Number  t);
	public NumberMatrix 				minus(final NumberMatrix B);

	public NumberMatrix 				times(final Number s);
	public NumberMatrix 				times(final NumberMatrix B);
	public NumberVector 				times(final NumberVector b);

	public NumberMatrix 				arrayTimes(final NumberMatrix B);

	public NumberMatrix 				arrayRightDivide(final NumberMatrix B);

	public NumberMatrix 				arrayLeftDivide(final NumberMatrix B);

	public boolean 				isEqual(final double _d);
	public boolean 				isEqual(final NumberMatrix _d);

	public boolean 				isDifferent(final double _d);
	public boolean 				isDifferent(final NumberMatrix _d);

	@Override
	public NumberMatrix 				clone();
	public NumberMatrix 				abs();
	public NumberMatrix 				transpose();
	public NumberMatrix 				inverse();
	public NumberMatrix 				uminus();

	public int 					rank();
	public double 				cond();
	public double 				det();
	public double 				trace();
	public double 				norm1();
	public double 				norm2();
	public double 				normInf();
	public double 				normF();

	public String 				toString(final NumberFormat _nf);

//	public double[][] 			getStorage();
//	public double[][] 			getStorageCopy();
//	public double[]		 		getColumnPackedCopy();
//	public double[]				getRowPackedCopy();
//	public <ARRAY> ARRAY 		getColumnPackedCopy(Primitive _arrayType);
//	public <ARRAY> ARRAY 		getRowPackedCopy(Primitive _arrayType);

}
