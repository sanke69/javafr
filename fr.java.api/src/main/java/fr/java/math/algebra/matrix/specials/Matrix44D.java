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
package fr.java.math.algebra.matrix.specials;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;
import fr.java.math.algebra.matrix.SquareMatrix;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.space.Vector4D;

public interface Matrix44D extends DoubleMatrix, SquareMatrix {

	public interface Editable extends Matrix44D, DoubleMatrix.Editable, SquareMatrix {

		@Deprecated
		public void 				set(double _t);
		@Deprecated
		public void 				set(double _m00, double _m01, double _m02, double _m03, double _m10, double _m11, double _m12, double _m13, double _m20, double _m21, double _m22, double _m23, double _m30, double _m31, double _m32, double _m33);
		@Deprecated
		public void 				set(Vector4D _col0, Vector4D _col1, Vector4D _col2, Vector4D _col3);
		@Deprecated
		public void 				set(Matrix44D _d);

		@Override
		public void 				set(int _i, int _j, double _value);
		@Override
		public void 				setNumber(int _i, int _j, Number _value);

		@Override
		public void 				setMatrix(int _i0, int _i1, int _j0, int _j1, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _rows, int[] _columns, NumberMatrix _X);
		@Override
		public void 				setMatrix(int _i0, int _i1, int[] _columns, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _rows, int _j0, int _j1, NumberMatrix _X);

		@Override
		public Matrix44D.Editable 	plusEquals(Number _t);
		@Override
		public Matrix44D.Editable 	plusEquals(NumberMatrix _m);
		@Override
		public Matrix44D.Editable 	plusEquals(double _t);
		@Override
		public Matrix44D.Editable 	plusEquals(DoubleMatrix _m);
		public Matrix44D.Editable 	plusEquals(Matrix44D _m);

		@Override
		public Matrix44D.Editable 	minusEquals(Number _t);
		@Override
		public Matrix44D.Editable 	minusEquals(NumberMatrix _m);
		@Override
		public Matrix44D.Editable 	minusEquals(double _t);
		@Override
		public Matrix44D.Editable 	minusEquals(DoubleMatrix _m);
		public Matrix44D.Editable 	minusEquals(Matrix44D _m);

		@Override
		public Matrix44D.Editable 	timesEquals(Number _d);
		@Override
		public Matrix44D.Editable 	timesEquals(double _d);
		public Matrix44D.Editable 	timesEquals(SquareMatrix _m);
		public Matrix44D.Editable 	timesEquals(Matrix44D _d);

		@Override
		public Matrix44D.Editable 	arrayTimesEquals(NumberMatrix B);
		@Override
		public Matrix44D.Editable 	arrayTimesEquals(DoubleMatrix B);

		@Override
		public Matrix44D.Editable 	arrayRightDivideEquals(NumberMatrix B);
		@Override
		public Matrix44D.Editable 	arrayRightDivideEquals(DoubleMatrix B);

		@Override
		public Matrix44D.Editable 	arrayLeftDivideEquals(NumberMatrix B);
		@Override
		public Matrix44D.Editable 	arrayLeftDivideEquals(DoubleMatrix B);

	}

	// TENSOR METHODS
	@Override
    public default int				getCapacity() {
		return 16;
	}

	@Override
	public default int 		getSliceOffset(int... _slice) {
		return _slice[0] * 4 + _slice[1];
	}
	@Override
	public default int[] 	getSliceShape(int... _slice) {
		return _slice == null || _slice.length == 0 ? getShape() : _slice.length == 1 ? new int[] { 4 } : new int[0];
	}

	@Override
	public boolean 					isDirect();
	@Override
	public double[] 				getArray();
	@Override
	public DoubleBuffer 			getBuffer();
	@Override
    public DoubleStream 			getStream();

    public DoubleBuffer 			getColumnBuffer();

	// MATRIX METHODS
	@Override
	public int 						columns();
	@Override
	public int 						rows();

	@Override
	public Number 					getNumber(int _i, int _j);

	@Override
	public double 					get(int _i, int _j);
	@Override
	public DoubleVector 			getRow(int _i);
	@Override
	public DoubleVector 			getColumn(int _j);

	@Override
	public NumberMatrix 					getMatrix(int _i0, int _i1, int _j0, int _j1);
	@Override
	public NumberMatrix 					getMatrix(int[] _rows, int[] _columns);
	@Override
	public NumberMatrix 					getMatrix(int _i0, int _i1, int[] _columns);
	@Override
	public NumberMatrix 					getMatrix(int[] _rows, int _j0, int _j1);
	
	@Override
	public Matrix44D 				plus(double _t);
	@Override
	public Matrix44D 				plus(Number _t);
	@Override
	public Matrix44D 				plus(NumberMatrix _m);
	@Override
	public Matrix44D 				plus(DoubleMatrix _m);
	public Matrix44D 				plus(Matrix44D _m);

	@Override
	public Matrix44D 				minus(double _t);
	@Override
	public Matrix44D 				minus(Number _t);
	@Override
	public Matrix44D 				minus(NumberMatrix _m);
	@Override
	public Matrix44D 				minus(DoubleMatrix _m);
	public Matrix44D 				minus(Matrix44D _m);

	@Override
	public Matrix44D 				times(double _d);
	@Override
	public Matrix44D 				times(Number _d);
	@Override
	public Vector4D 				times(NumberVector _v);
	@Override
	public Matrix44D 				times(NumberMatrix _m);
	@Override
	public Vector4D 				times(DoubleVector _v);
	@Override
	public Matrix44D 				times(DoubleMatrix _m);
	public Vector4D 				times(Vector4D _v);
	public Matrix44D 				times(Matrix44D _m);

	@Override
	public Matrix44D 				arrayTimes(NumberMatrix B);
	@Override
	public Matrix44D 				arrayTimes(DoubleMatrix B);

	@Override
	public Matrix44D 				arrayRightDivide(NumberMatrix B);
	@Override
	public Matrix44D 				arrayRightDivide(DoubleMatrix B);

	@Override
	public Matrix44D 				arrayLeftDivide(NumberMatrix B);
	@Override
	public Matrix44D 				arrayLeftDivide(DoubleMatrix B);

	public boolean 					isValid();
	
	@Override
	public boolean 					isEqual(double _d);
	@Override
	public boolean 					isEqual(NumberMatrix _m);
	public boolean 					isEqual(DoubleMatrix _m);
	public boolean 					isEqual(Matrix44D _m);

	@Override
	public boolean 					isDifferent(double _d);
	@Override
	public boolean 					isDifferent(NumberMatrix _m);
	public boolean 					isDifferent(DoubleMatrix _m);

	public boolean 					isDifferent(Matrix44D _m);
	
	@Override
	public Matrix44D 				clone();
	@Override
	public Matrix44D 				abs();
	@Override
	public Matrix44D 				inverse();
	@Override
	public Matrix44D 				transpose();
	@Override
	public Matrix44D 				uminus();


	@Override
	public int 						rank();
	@Override
	public double 					cond();
	@Override
	public double				 	det();
	@Override
	public double 					trace();
	@Override
	public double 					norm1();
	@Override
	public double 					norm2();
	@Override
	public double 					normInf();
	@Override
	public double 					normF();

	@Override
	public String 					toString();
	@Override
	public String 					toString(NumberFormat _nf);

	public Vector4D 				solve(Vector4D b);

	@Override
	public boolean 					equals(Object _obj);

	@Override
	public int 						compareTo(Object _obj);

	@Override
	public int 						hashCode();

	public Matrix33D 				as3D();
	public Matrix22D				as2D();
/*
	@Override
	public double[][] 				getStorage();
	@Override
	public double[][] 				getStorageCopy();
	@Override
	public double[] 				getColumnPackedCopy();
	@Override
	public double[] 				getRowPackedCopy();
*/
}
