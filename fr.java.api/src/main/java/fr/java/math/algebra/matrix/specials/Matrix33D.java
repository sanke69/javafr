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
import fr.java.math.geometry.space.Vector3D;

public interface Matrix33D extends DoubleMatrix, SquareMatrix {

	public interface Editable extends Matrix33D, DoubleMatrix.Editable, SquareMatrix {

		@Deprecated
		public void 				set(double _t);
		@Deprecated
		public void 				set(double _m00, double _m01, double _m02, double _m10, double _m11, double _m12, double _m20, double _m21, double _m22);
		@Deprecated
		public void 				set(Vector3D _col0, Vector3D _col1, Vector3D _col2);
		@Deprecated
		public void 				set(Matrix33D _m);

		@Override
		public void 				setMatrix(int _i0, int _i1, int _j0, int _j1, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _r, int[] _c, NumberMatrix _X);
		@Override
		public void 				setMatrix(int _i0, int _i1, int[] _c, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _r, int _j0, int _j1, NumberMatrix _X);

		@Override
		public Matrix33D.Editable 	plusEquals(double _t);
		@Override
		public Matrix33D.Editable 	plusEquals(Number _t);
		@Override
		public Matrix33D.Editable 	plusEquals(NumberMatrix _m);
		@Override
		public Matrix33D.Editable 	plusEquals(DoubleMatrix _m);
		public Matrix33D.Editable 	plusEquals(Matrix33D _m);

		@Override
		public Matrix33D.Editable	minusEquals(double _t);
		@Override
		public Matrix33D.Editable	minusEquals(Number _t);
		@Override
		public Matrix33D.Editable	minusEquals(NumberMatrix _m);
		@Override
		public Matrix33D.Editable	minusEquals(DoubleMatrix _m);
		public Matrix33D.Editable	minusEquals(Matrix33D _m);

		@Override
		public Matrix33D.Editable	timesEquals(double _v);
		@Override
		public Matrix33D.Editable	timesEquals(Number _v);
//		@Override
		public Matrix33D.Editable	timesEquals(NumberMatrix _m);
//		@Override
		public Matrix33D.Editable	timesEquals(DoubleMatrix _m);
//		@Override
		public Matrix33D.Editable	timesEquals(SquareMatrix _m);
		public Matrix33D.Editable	timesEquals(Matrix33D _d);

		@Override
		public Matrix33D.Editable	arrayTimesEquals(NumberMatrix B);
		@Override
		public Matrix33D.Editable	arrayTimesEquals(DoubleMatrix B);

		@Override
		public Matrix33D.Editable	arrayRightDivideEquals(NumberMatrix B);
		@Override
		public Matrix33D.Editable	arrayRightDivideEquals(DoubleMatrix B);

		@Override
		public Matrix33D.Editable	arrayLeftDivideEquals(NumberMatrix B);
		@Override
		public Matrix33D.Editable	arrayLeftDivideEquals(DoubleMatrix B);

	}

	// TENSOR METHODS
	@Override
    public default int		getCapacity() {
		return 9;
	}

	@Override
	public default int 		getSliceOffset(int... _slice) {
		return _slice[0] * 3 + _slice[1];
	}
	@Override
	public default int[] 	getSliceShape(int... _slice) {
		return _slice == null || _slice.length == 0 ? getShape() : _slice.length == 1 ? new int[] { 3 } : new int[0];
	}

	@Override
	public boolean 		isDirect();
	@Override
	public double[] 		getArray();
	@Override
	public DoubleBuffer 	getBuffer();
    public DoubleStream 	getStream();

	// MATRIX METHODS
	public int 			columns();
	public int 			rows();

	public Number 		getNumber(int _i, int _j);
	public void 			setNumber(int _i, int _j, Number _value);

	public double		 	get(int _i, int _j);
	public void 			set(int _i, int _j, double _value);

	public DoubleVector 	getRow(int _i);
	public DoubleVector 	getColumn(int _j);

	@Override
	public NumberMatrix 		getMatrix(int _i0, int _i1, int _j0, int _j1);
	@Override
	public NumberMatrix 		getMatrix(int[] _r, int[] _c);
	@Override
	public NumberMatrix 		getMatrix(int _i0, int _i1, int[] _c);
	@Override
	public NumberMatrix 		getMatrix(int[] _r, int _j0, int _j1);

	public Matrix33D 		plus(Number _t);
	public Matrix33D 		plus(NumberMatrix _m);

	public Matrix33D 		plus(double _t);
	public Matrix33D 		plus(DoubleMatrix _m);

	public Matrix33D 		plus(Matrix33D _m);

	public Matrix33D 		minus(Number _t);
	public Matrix33D 		minus(NumberMatrix _m);

	public Matrix33D 		minus(double _t);
	public Matrix33D 		minus(DoubleMatrix _m);

	public Matrix33D 		minus(Matrix33D _m);

	public Matrix33D 		times(Number _v);
	public Vector3D 		times(NumberVector _v);
	public NumberMatrix 			times(NumberMatrix _m);
	
	public Matrix33D 		times(double _v);
	public Vector3D 		times(DoubleVector _v);
	public DoubleMatrix 	times(DoubleMatrix _m);

	public Vector3D 		times(Vector3D _v);
	public Matrix33D 		times(Matrix33D _d);

	@Override
	public Matrix33D 		arrayTimes(NumberMatrix B);
	@Override
	public Matrix33D 		arrayTimes(DoubleMatrix B);

	@Override
	public Matrix33D 		arrayRightDivide(NumberMatrix B);
	@Override
	public Matrix33D 		arrayRightDivide(DoubleMatrix B);

	@Override
	public Matrix33D 		arrayLeftDivide(NumberMatrix B);
	@Override
	public Matrix33D 		arrayLeftDivide(DoubleMatrix B);

	public boolean 		isValid();

	public boolean 		isEqual(double _d);
	public boolean 		isEqual(NumberMatrix _m);
	public boolean 		isEqual(DoubleMatrix _m);

	public boolean 		isEqual(Matrix33D _m);

	public boolean 		isDifferent(double _d);
	public boolean 		isDifferent(NumberMatrix _m);
	public boolean 		isDifferent(DoubleMatrix _m);

	public boolean 		isDifferent(Matrix33D _m);
	
	@Override
	public Matrix33D 		clone();
	@Override
	public Matrix33D 		abs();
	@Override
	public Matrix33D 		inverse();
	@Override
	public Matrix33D 		transpose();
	@Override
	public Matrix33D 		uminus();

	@Override
	public int 			rank();
	@Override
	public double 		cond();
	@Override
	public double 		det();
	@Override
	public double 		trace();
	@Override
	public double 		norm1();
	@Override
	public double 		norm2();
	@Override
	public double 		normInf();
	@Override
	public double 		normF();

	@Override
	public String 		toString();
	@Override
	public String 		toString(NumberFormat _nf);

	public Vector3D 		solve(Vector3D b);
	@Override
	public boolean equals(Object _obj);

	@Override
	public int compareTo(Object _obj);

	@Override
	public int hashCode();
/*
	@Override
	public double[][] getStorage();
	@Override
	public double[][] getStorageCopy();
	@Override
	public double[] 	getColumnPackedCopy();
	@Override
	public double[] 	getRowPackedCopy();
*/
}
