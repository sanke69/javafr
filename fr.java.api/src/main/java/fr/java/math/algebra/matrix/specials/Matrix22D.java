/** ************************************************************************ **\
 * Copyright (c) 2007-?XYZ Steve PECHBERTI                                    *
 *                                                                            *
 * @author <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>   *
 *                                                                            *
 * @section license License                                                   *
 *    [EN] This program is free software:                                     *
 *         you can redistribute it and/or modify it under the terms of        * 
 *         the GNU General Public License as published by                     *
 *         the Free Software Foundation, either version 3 of the License, or  *
 *         (at your option) any later version.                                *
 *         You should have received a copy of the GNU General Public License  *
 *         along with this program. If not, see                               *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *    [FR] Ce programme est un logiciel libre ; vous pouvez le redistribuer   * 
 *         ou le modifier suivant les termes de la GNU General Public License *
 *         telle que publiée par la Free Software Foundation ;                *
 *         soit la version 3 de la licence, soit (à votre gré) toute version  *
 *         ultérieure.                                                        *
 *         Vous devez avoir reçu une copie de la GNU General Public License   *
 *         en même temps que ce programme ; si ce n'est pas le cas, consultez *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *                                                                            *
 * @section disclaimer Disclaimer                                             *
 *    [EN] This program is distributed in the hope that it will be useful,    *
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.java.math.algebra.matrix.specials;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;
import fr.java.math.algebra.matrix.SquareMatrix;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Vector2D;

public interface Matrix22D extends DoubleMatrix, SquareMatrix {

	public interface Editable extends Matrix22D, DoubleMatrix.Editable {

		@Deprecated
		public void 				set(double _t);
		@Deprecated
		public void 				set(double _m00, double _m01, double _m10, double _m11);
		@Deprecated
		public void 				set(Vector2D _col0, Vector2D _col1);
		@Deprecated
		public void 				set(Matrix22D _m);

		@Override
		public void 				set(int _i, int _j, double _value);

		@Override
		public void 				setNumber(int _i, int _j, Number _value);

		@Override
		public void 				setMatrix(int _i0, int _i1, int _j0, int _j1, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _r, int[] _c, NumberMatrix _X);
		@Override
		public void 				setMatrix(int _i0, int _i1, int[] _c, NumberMatrix _X);
		@Override
		public void 				setMatrix(int[] _r, int _j0, int _j1, NumberMatrix _X);

		@Override
		public Matrix22D.Editable 	plusEquals(Number _t);
		@Override
		public Matrix22D.Editable 	plusEquals(NumberMatrix _m);
		@Override
		public Matrix22D.Editable 	plusEquals(double _t);
		@Override
		public Matrix22D.Editable 	plusEquals(DoubleMatrix _m);
		public Matrix22D 			plusEquals(Matrix22D _m);

		@Override
		public Matrix22D 			arrayTimesEquals(NumberMatrix B);
		@Override
		public Matrix22D.Editable 	arrayTimesEquals(DoubleMatrix B);

		@Override
		public Matrix22D 			arrayRightDivideEquals(NumberMatrix B);
		@Override
		public Matrix22D.Editable 	arrayRightDivideEquals(DoubleMatrix B);

		@Override
		public Matrix22D 			arrayLeftDivideEquals(NumberMatrix B);
		@Override
		public Matrix22D.Editable 	arrayLeftDivideEquals(DoubleMatrix B);

		@Override
		public Matrix22D.Editable 	minusEquals(Number _t);
		@Override
		public Matrix22D.Editable 	minusEquals(NumberMatrix _m);

		@Override
		public Matrix22D.Editable 	minusEquals(double _t);
		@Override
		public Matrix22D.Editable 	minusEquals(DoubleMatrix _m);
		public Matrix22D.Editable 	minusEquals(Matrix22D _other);

		@Override
		public Matrix22D.Editable 	timesEquals(Number _t);
		@Override
		public Matrix22D.Editable 	timesEquals(double _t);
		public Matrix22D.Editable 	timesEquals(SquareMatrix _m);
		public Matrix22D.Editable 	timesEquals(Matrix22D _d);

	}
	
	// TENSOR METHODS
	@Override
	public boolean 			isDirect();
	@Override
	public double[] 		getArray();
	@Override
	public DoubleBuffer 	getBuffer();
	@Override
    public DoubleStream 	getStream();

	@Override
    public default int		getCapacity() {
		return 4;
	}

	@Override
	public default int 		getSliceOffset(int... _slice) {
		return _slice[0] * 2 + _slice[1];
	}
	@Override
	public default int[] 	getSliceShape(int... _slice) {
		return _slice == null || _slice.length == 0 ? getShape() : _slice.length == 1 ? new int[] { 2 } : new int[0];
	}

	// MATRIX METHODS
	@Override
	public int 				columns();
	@Override
	public int 				rows();

	@Override
	public Number 			getNumber(int _i, int _j);

	@Override
	public double 			get(int _i, int _j);

	@Override
	public DoubleVector 	getRow(int _i);
	@Override
	public DoubleVector 	getColumn(int _j);

	@Override
	public NumberMatrix 			getMatrix(int _i0, int _i1, int _j0, int _j1);
	@Override
	public NumberMatrix 			getMatrix(int[] _r, int[] _c);
	@Override
	public NumberMatrix 			getMatrix(int _i0, int _i1, int[] _c);
	@Override
	public NumberMatrix 			getMatrix(int[] _r, int _j0, int _j1);

	@Override
	public Matrix22D 		plus(Number _t);
	@Override
	public Matrix22D 		plus(NumberMatrix _m);

	@Override
	public Matrix22D 		plus(double _t);
	@Override
	public Matrix22D 		plus(DoubleMatrix _m);

	public Matrix22D 		plus(Matrix22D _m);

	@Override
	public Matrix22D 		minus(Number _t);
	@Override
	public Matrix22D 		minus(NumberMatrix _m);

	@Override
	public Matrix22D 		minus(double _t);
	@Override
	public Matrix22D 		minus(DoubleMatrix _m);
	
	public Matrix22D 		minus(Matrix22D _m);

	@Override
	public Matrix22D 		times(Number _t);
	@Override
	public Vector2D 		times(NumberVector _v);
	@Override
	public Matrix22D 		times(NumberMatrix _m);

	@Override
	public Matrix22D 		times(double _t);
	@Override
	public DoubleVector 	times(DoubleVector _v);
	@Override
	public Matrix22D 		times(DoubleMatrix _m);
	
	public Vector2D 		times(Vector2D _v);
	public Matrix22D 		times(Matrix22D _m);

	@Override
	public Matrix22D 		arrayTimes(NumberMatrix B);
	@Override
	public Matrix22D 		arrayTimes(DoubleMatrix B);

	public Matrix22D 		arrayTimes(Matrix22D B);

	@Override
	public Matrix22D 		arrayRightDivide(NumberMatrix B);
	@Override
	public Matrix22D 		arrayRightDivide(DoubleMatrix B);

	@Override
	public Matrix22D 		arrayLeftDivide(NumberMatrix B);
	@Override
	public Matrix22D 		arrayLeftDivide(DoubleMatrix B);

	public boolean 			isValid();

	@Override
	public boolean 			isEqual(double _d);
	@Override
	public boolean 			isEqual(NumberMatrix _m);
	public boolean 			isEqual(DoubleMatrix _m);

	public boolean 			isEqual(Matrix22D _m);

	@Override
	public boolean 			isDifferent(double _d);
	@Override
	public boolean 			isDifferent(NumberMatrix _m);
	public boolean 			isDifferent(DoubleMatrix _m);
	
	public boolean 			isDifferent(Matrix22D _m);

	@Override
	public Matrix22D 		clone();
	@Override
	public Matrix22D 		abs();
	@Override
	public Matrix22D 		transpose();
	@Override
	public Matrix22D 		inverse();
	@Override
	public Matrix22D 		uminus();

	@Override
	public int 				rank();
	@Override
	public double 			cond();
	@Override
	public double 			det();
	@Override
	public double 			trace();
	@Override
	public double 			norm1();
	@Override
	public double 			norm2();
	@Override
	public double 			normInf();
	@Override
	public double 			normF();

	@Override
	public String 			toString();
	@Override
	public String 			toString(NumberFormat _nf);

	public Vector2D 		solve(Vector2D b);

	@Override
	public boolean 			equals(Object _obj);

	@Override
	public int 				compareTo(Object _obj);

	@Override
	public int 				hashCode();
/*
	@Override
	public double[][]		getStorage();
	@Override
	public double[][] 		getStorageCopy();
	@Override
	public double[] 		getColumnPackedCopy();
	@Override
	public double[] 		getRowPackedCopy();
*/
}
