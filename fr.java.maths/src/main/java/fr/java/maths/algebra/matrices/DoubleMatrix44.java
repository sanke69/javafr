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
package fr.java.maths.algebra.matrices;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;
import fr.java.math.algebra.matrix.generic.Matrix44D;
import fr.java.math.algebra.matrix.special.SquareMatrix;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.algebra.vector.generic.Vector4D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.doubles.decompositions.SingularValueDecomposition;
import fr.java.maths.algebra.vectors.DoubleVector4D;

public class DoubleMatrix44 implements Matrix44D.Editable {
	private static final long serialVersionUID = 1L;

	public static DoubleMatrix44 zero() {
		return new DoubleMatrix44(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
	}
	public static DoubleMatrix44 identity() {
		return new DoubleMatrix44(1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1);
	}
	public static DoubleMatrix44 unitary() {
		return new DoubleMatrix44(1,1,1,1,01,1,1,1,1,1,1,1,1,1,1,1);
	}

	public static DoubleMatrix44 from(Vector3D _i, Vector3D _j, Vector3D _k, Point3D _o) {
		DoubleMatrix44 m = new DoubleMatrix44();
/** /
		m.m00 = _i.getX(); 	m.m01 = _j.getX();  m.m02 = _k.getX();	m.m03 = -Vector3D.dotProduct(_i, Vector3D.of(_o));
		m.m10 = _i.getY(); 	m.m11 = _j.getY();  m.m12 = _k.getY();	m.m13 = -Vector3D.dotProduct(_j, Vector3D.of(_o));
		m.m20 = _i.getZ();  m.m21 = _j.getZ();  m.m22 = _k.getZ();	m.m23 = -Vector3D.dotProduct(_k, Vector3D.of(_o));
		m.m30 = 0.0f; 		m.m31 =  0.0f; 		m.m32 = 0.0f; 		m.m33 = 1.0f;
/*/
		m.m00 = _i.getX(); 	m.m01 = _i.getY();  m.m02 = _i.getZ();	m.m03 = -Vectors.dotProduct(_i, Vectors.of(_o));
		m.m10 = _j.getX(); 	m.m11 = _j.getY();  m.m12 = _j.getZ();	m.m13 = -Vectors.dotProduct(_j, Vectors.of(_o));
		m.m20 = _k.getX();  m.m21 = _k.getY();  m.m22 = _k.getZ();	m.m23 = -Vectors.dotProduct(_k, Vectors.of(_o));
		m.m30 = 0.0f; 		m.m31 =  0.0f; 		m.m32 = 0.0f; 		m.m33 = 1.0f;
/**/
		return m;
	}

	public double m00, m01, m02, m03,
				  m10, m11, m12, m13,
				  m20, m21, m22, m23,
				  m30, m31, m32, m33;

	public DoubleMatrix44() {
		super();
	}
	public DoubleMatrix44(final double _m00, final double _m01, final double _m02, final double _m03, final double _m10, final double _m11, final double _m12, final double _m13, final double _m20, final double _m21, final double _m22, final double _m23, final double _m30, final double _m31, final double _m32, final double _m33) {
		m00 = _m00; m01 = _m01; m02 = _m02; m03 = _m03;
		m10 = _m10; m11 = _m11; m12 = _m12; m13 = _m13;
		m20 = _m20; m21 = _m21; m22 = _m22; m23 = _m23;
		m30 = _m30; m31 = _m31; m32 = _m32; m33 = _m33;
	}
	public DoubleMatrix44(final double[] _d) {
		m00 =  _d[0]; m01 =  _d[1]; m02 =  _d[2]; m03 =  _d[3];
		m10 =  _d[4]; m11 =  _d[5]; m12 =  _d[6]; m13 =  _d[7];
		m20 =  _d[8]; m21 =  _d[9]; m22 = _d[10]; m23 = _d[11];
		m30 = _d[12]; m31 = _d[13]; m32 = _d[14]; m33 = _d[15];
	}
	public DoubleMatrix44(final DoubleMatrix44 _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02; m03 = _d.m03;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12; m13 = _d.m13;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22; m23 = _d.m23;
		m30 = _d.m30; m31 = _d.m31; m32 = _d.m32; m33 = _d.m33;
	}

	// TENSOR METHODS
	@Override
	public final boolean 		isDirect() {
		return false;
	}
	@Override
	public final double[] 		getArray() {
		return new double[] { m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33 };
	}
	@Override
	public final DoubleBuffer 	getBuffer() {
		return DoubleBuffer.wrap(getArray());
	}
    public final DoubleStream 	getStream() { return DoubleStream.of(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33); }

    public final DoubleBuffer 	getColumnBuffer() { return DoubleBuffer.wrap( new double[] { m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33 } ); }

	@Override
	public Number 				getNumber(int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public double 				getValue(int _index) {
		switch((int) _index) {
		case  0 : return m00;
		case  1 : return m01;
		case  2 : return m02;
		case  3 : return m03;
		case  4 : return m10;
		case  5 : return m11;
		case  6 : return m12;
		case  7 : return m13;
		case  8 : return m20;
		case  9 : return m21;
		case 10 : return m22;
		case 11 : return m23;
		case 12 : return m30;
		case 13 : return m31;
		case 14 : return m32;
		case 15 : return m33;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 2);
		
		if(_coords[0] > 3 || _coords[1] > 3)
			throw new IndexOutOfBoundsException();

		if(_coords[1] == 0) {
			if(_coords[0] == 0)
				return m00;
			else if(_coords[0] == 1)
				return m01;
			else if(_coords[0] == 2)
				return m02;
			else
				return m03;
		} else if(_coords[1] == 1) {
			if(_coords[0] == 0)
				return m10;
			else if(_coords[0] == 1)
				return m11;
			else if(_coords[0] == 2)
				return m12;
			else
				return m13;
		} else if(_coords[1] == 2) {
			if(_coords[0] == 0)
				return m20;
			else if(_coords[0] == 1)
				return m21;
			else if(_coords[0] == 2)
				return m22;
			else
				return m23;
		} else {
			if(_coords[0] == 0)
				return m30;
			else if(_coords[0] == 1)
				return m31;
			else if(_coords[0] == 2)
				return m32;
			else
				return m33;
		}
	}
	@Override
	public void 				setValue(double _value, int _index) {
		switch((int) _index) {
		case  0 : m00 = _value;
		case  1 : m01 = _value;
		case  2 : m02 = _value;
		case  3 : m03 = _value;
		case  4 : m10 = _value;
		case  5 : m11 = _value;
		case  6 : m12 = _value;
		case  7 : m13 = _value;
		case  8 : m20 = _value;
		case  9 : m21 = _value;
		case 10 : m22 = _value;
		case 11 : m23 = _value;
		case 12 : m30 = _value;
		case 13 : m31 = _value;
		case 14 : m32 = _value;
		case 15 : m33 = _value;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 2);
		
		if(_coords[0] > 3 || _coords[1] > 3)
			throw new IndexOutOfBoundsException();

		if(_coords[1] == 0) {
			if(_coords[0] == 0)
				m00 = _value;
			else if(_coords[0] == 1)
				m01 = _value;
			else if(_coords[0] == 2)
				m02 = _value;
			else
				m03 = _value;
		} else if(_coords[1] == 1) {
			if(_coords[0] == 0)
				m10 = _value;
			else if(_coords[0] == 1)
				m11 = _value;
			else if(_coords[0] == 2)
				m12 = _value;
			else
				m13 = _value;
		} else if(_coords[1] == 2) {
			if(_coords[0] == 0)
				m20 = _value;
			else if(_coords[0] == 1)
				m21 = _value;
			else if(_coords[0] == 2)
				m22 = _value;
			else
				m23 = _value;
		} else {
			if(_coords[0] == 0)
				m30 = _value;
			else if(_coords[0] == 1)
				m31 = _value;
			else if(_coords[0] == 2)
				m32 = _value;
			else
				m33 = _value;
		}
	}

	@Override
	public final void			setSlice(final DoubleTensor _tensor, final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceView(final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceCopy(final int... _slice) {
		throw new NotYetImplementedException();
	}

	@Override
	public final void 			reshape(final int... _shape) {
		throw new IllegalArgumentException("Can't reshape a Matrix 4x4");
	}

	// MATRIX METHODS
	@Override
	public final int 			columns() { return 4; }
	@Override
	public final int 			rows() { return 4; }

	@Override
	public final void 			setNumber(final int _i, final int _j, final Number _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value.doubleValue();
			else if(_j == 1)
				m01 = _value.doubleValue();
			else if(_j == 2)
				m02 = _value.doubleValue();
			else
				m03 = _value.doubleValue();
		} else if(_i == 1) {
			if(_j == 0)
				m10 = _value.doubleValue();
			else if(_j == 1)
				m11 = _value.doubleValue();
			else if(_j == 2)
				m12 = _value.doubleValue();
			else
				m13 = _value.doubleValue();
		} else if(_i == 2) {
			if(_j == 0)
				m20 = _value.doubleValue();
			else if(_j == 1)
				m21 = _value.doubleValue();
			else if(_j == 2)
				m22 = _value.doubleValue();
			else
				m23 = _value.doubleValue();
		} else {
			if(_j == 0)
				m30 = _value.doubleValue();
			else if(_j == 1)
				m31 = _value.doubleValue();
			else if(_j == 2)
				m32 = _value.doubleValue();
			else
				m33 = _value.doubleValue();
		}
	}
	@Override
	public final Number 		getNumber(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else if(_j == 1)
				return m01;
			else if(_j == 2)
				return m02;
			else
				return m03;
		} else if(_i == 1) {
			if(_j == 0)
				return m10;
			else if(_j == 1)
				return m11;
			else if(_j == 2)
				return m12;
			else
				return m13;
		} else if(_i == 2) {
			if(_j == 0)
				return m20;
			else if(_j == 1)
				return m21;
			else if(_j == 2)
				return m22;
			else
				return m23;
		} else {
			if(_j == 0)
				return m30;
			else if(_j == 1)
				return m31;
			else if(_j == 2)
				return m32;
			else
				return m33;
		}
	}

	@Override
	public final void 			set(final int _i, final int _j, final double _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value;
			else if(_j == 1)
				m01 = _value;
			else if(_j == 2)
				m02 = _value;
			else
				m03 = _value;
		} else if(_i == 1) {
			if(_j == 0)
				m10 = _value;
			else if(_j == 1)
				m11 = _value;
			else if(_j == 2)
				m12 = _value;
			else
				m13 = _value;
		} else if(_i == 2) {
			if(_j == 0)
				m20 = _value;
			else if(_j == 1)
				m21 = _value;
			else if(_j == 2)
				m22 = _value;
			else
				m23 = _value;
		} else {
			if(_j == 0)
				m30 = _value;
			else if(_j == 1)
				m31 = _value;
			else if(_j == 2)
				m32 = _value;
			else
				m33 = _value;
		}
	}
	@Override
	public final double 		get(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else if(_j == 1)
				return m01;
			else if(_j == 2)
				return m02;
			else
				return m03;
		} else if(_i == 1) {
			if(_j == 0)
				return m10;
			else if(_j == 1)
				return m11;
			else if(_j == 2)
				return m12;
			else
				return m13;
		} else if(_i == 2) {
			if(_j == 0)
				return m20;
			else if(_j == 1)
				return m21;
			else if(_j == 2)
				return m22;
			else
				return m23;
		} else {
			if(_j == 0)
				return m30;
			else if(_j == 1)
				return m31;
			else if(_j == 2)
				return m32;
			else
				return m33;
		}
	}

	@Override
	public final DoubleVector 	getRow(final int _i) {
		switch(_i) {
		case 0 : return new DoubleVector4D(m00, m01, m02, m03);
		case 1 : return new DoubleVector4D(m10, m11, m12, m13);
		case 2 : return new DoubleVector4D(m20, m21, m22, m23);
		case 3 : return new DoubleVector4D(m30, m31, m32, m33);
		default : throw new IllegalAccessError();
		}
	}
	@Override
	public final DoubleVector 	getColumn(final int _j) {
		switch(_j) {
		case 0 : return new DoubleVector4D(m00, m10, m20, m30);
		case 1 : return new DoubleVector4D(m01, m11, m21, m31);
		case 2 : return new DoubleVector4D(m02, m12, m22, m32);
		case 3 : return new DoubleVector4D(m03, m13, m23, m33);
		default : throw new IllegalAccessError();
		}
	}

	@Deprecated
	public final void 			set(final double _t) {
		m00 = _t; m01 = _t; m02 = _t; m03 = _t;
		m10 = _t; m11 = _t; m12 = _t; m13 = _t;
		m20 = _t; m21 = _t; m22 = _t; m23 = _t;
		m30 = _t; m31 = _t; m32 = _t; m33 = _t;
	}
	@Deprecated
	public final void 			set(final double _m00, final double _m01, final double _m02, final double _m03, final double _m10, final double _m11, final double _m12, final double _m13, final double _m20, final double _m21, final double _m22, final double _m23, final double _m30, final double _m31, final double _m32, final double _m33) {
		m00 = _m00; m01 = _m01; m02 = _m02; m03 = _m03;
		m10 = _m10; m11 = _m11; m12 = _m12; m13 = _m13;
		m20 = _m20; m21 = _m21; m22 = _m22; m23 = _m23;
		m30 = _m30; m31 = _m31; m32 = _m32; m33 = _m33;
	}
	@Deprecated
	public final void 			set(final Vector4D _col0, final Vector4D _col1, final Vector4D _col2, final Vector4D _col3) {
		m00 = _col0.getX(); m01 = _col1.getX(); m02 = _col2.getX(); m03 = _col3.getX(); 
		m10 = _col0.getY(); m11 = _col1.getY(); m12 = _col2.getY(); m13 = _col3.getY(); 
		m20 = _col0.getZ(); m21 = _col1.getZ(); m22 = _col2.getZ(); m23 = _col3.getZ(); 
		m30 = _col0.getW(); m31 = _col1.getW(); m32 = _col2.getW(); m33 = _col3.getW(); 
	}
	@Deprecated
	public final void 			set(final Matrix44D _d) {
		m00 = _d.get(0,0); m01 = _d.get(0,1); m02 = _d.get(0,2); m03 = _d.get(0,3);
		m10 = _d.get(1,0); m11 = _d.get(1,1); m12 = _d.get(1,2); m13 = _d.get(1,3);
		m20 = _d.get(2,0); m21 = _d.get(2,1); m22 = _d.get(2,2); m23 = _d.get(2,3);
		m30 = _d.get(3,0); m31 = _d.get(3,1); m32 = _d.get(3,2); m33 = _d.get(3,3);
	}

	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int _j0, final int _j1, final NumberMatrix _X) {
		for(int i = _i0; i <= _i1; i++)
			for(int j = _j0; j <= _j1; j++)
				set(i, j, _X.getNumber(i - _i0, j - _j0).doubleValue());
	}
	@Override
	public final NumberMatrix 		getMatrix(final int _i0, final int _i1, final int _j0, final int _j1) {
		DoubleMatrixMN  X = new DoubleMatrixMN(_i1 - _i0 + 1, _j1 - _j0 + 1);
		double[][] B = X.getStorage();

		for(int i = _i0; i <= _i1; i++)
			for(int j = _j0; j <= _j1; j++)
				B[i - _i0][j - _j0] = get(i, j);

		return X;
	}
	@Override
	public final void 			setMatrix(final int[] _rows, final int[] _columns, final NumberMatrix _X) {
		for(int i = 0; i < _rows.length; i++)
			for(int j = 0; j < _columns.length; j++)
				set(_rows[i], _columns[j], _X.getNumber(i, j).doubleValue());
	}
	@Override
	public final NumberMatrix 		getMatrix(final int[] _rows, final int[] _columns) {
		DoubleMatrixMN  X = new DoubleMatrixMN(_rows.length, _columns.length);
		double[][] B = X.getStorage();

		for(int i = 0; i < _rows.length; i++)
			for(int j = 0; j < _columns.length; j++)
				B[i][j] = get(_rows[i], _columns[j]);

		return X;
	}
	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int[] _columns, final NumberMatrix _X) {
		for(int i = _i0; i <= _i1; i++)
			for(int j = 0; j < _columns.length; j++)
				set(i, _columns[j], _X.getNumber(i - _i0, j).doubleValue());
	}
	@Override
	public final NumberMatrix 		getMatrix(final int _i0, final int _i1, final int[] _columns) {
		DoubleMatrixMN  X = new DoubleMatrixMN(_i1 - _i0 + 1, _columns.length);
		double[][] B = X.getStorage();
		
		for(int i = _i0; i <= _i1; i++)
			for(int j = 0; j < _columns.length; j++)
				B[i - _i0][j] = get(i, _columns[j]);

		return X;
	}
	@Override
	public final void 			setMatrix(final int[] _rows, final int _j0, final int _j1, final NumberMatrix _X) {
		for(int i = 0; i < _rows.length; i++)
			for(int j = _j0; j <= _j1; j++)
				set(_rows[i], j, _X.getNumber(i, j - _j0).doubleValue());

	}
	@Override
	public final NumberMatrix 		getMatrix(final int[] _rows, final int _j0, final int _j1) {
		DoubleMatrixMN  X = new DoubleMatrixMN(_rows.length, _j1 - _j0 + 1);
		double[][] B = X.getStorage();

		for(int i = 0; i < _rows.length; i++)
			for(int j = _j0; j <= _j1; j++)
				B[i][j - _j0] = get(_rows[i], j);

		return X;
	}

	@Override
	public final DoubleMatrix44 		plus(final Number _t) {
		return new DoubleMatrix44(	m00 + _t.doubleValue(), m01 + _t.doubleValue(), m02 + _t.doubleValue(), m03 + _t.doubleValue(),
								m10 + _t.doubleValue(), m11 + _t.doubleValue(), m12 + _t.doubleValue(), m13 + _t.doubleValue(),
								m20 + _t.doubleValue(), m21 + _t.doubleValue(), m22 + _t.doubleValue(), m23 + _t.doubleValue(),
								m30 + _t.doubleValue(), m31 + _t.doubleValue(), m32 + _t.doubleValue(), m33 + _t.doubleValue() );
	}
	@Override
	public final DoubleMatrix44 		plus(final NumberMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		return new DoubleMatrix44(	m00 + _m.getNumber(0,0).doubleValue(), m01 + _m.getNumber(0,1).doubleValue(), m02 + _m.getNumber(0,2).doubleValue(), m03 + _m.getNumber(0,3).doubleValue(),
								m10 + _m.getNumber(1,0).doubleValue(), m11 + _m.getNumber(1,1).doubleValue(), m12 + _m.getNumber(1,2).doubleValue(), m13 + _m.getNumber(1,3).doubleValue(),
								m20 + _m.getNumber(2,0).doubleValue(), m21 + _m.getNumber(2,1).doubleValue(), m22 + _m.getNumber(2,2).doubleValue(), m23 + _m.getNumber(2,3).doubleValue(),
								m30 + _m.getNumber(3,0).doubleValue(), m31 + _m.getNumber(3,1).doubleValue(), m32 + _m.getNumber(3,2).doubleValue(), m33 + _m.getNumber(3,3).doubleValue() );
	}
	
	@Override
	public final DoubleMatrix44 		plus(final double _t) {
		return new DoubleMatrix44(	m00 + _t, m01 + _t, m02 + _t, m03 + _t,
								m10 + _t, m11 + _t, m12 + _t, m13 + _t,
								m20 + _t, m21 + _t, m22 + _t, m23 + _t,
								m30 + _t, m31 + _t, m32 + _t, m33 + _t );
	}
	@Override
	public final DoubleMatrix44 		plus(final DoubleMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		return new DoubleMatrix44(	m00 + _m.get(0,0), m01 + _m.get(0,1), m02 + _m.get(0,2), m03 + _m.get(0,3),
								m10 + _m.get(1,0), m11 + _m.get(1,1), m12 + _m.get(1,2), m13 + _m.get(1,3),
								m20 + _m.get(2,0), m21 + _m.get(2,1), m22 + _m.get(2,2), m23 + _m.get(2,3),
								m30 + _m.get(3,0), m31 + _m.get(3,1), m32 + _m.get(3,2), m33 + _m.get(3,3) );
	}

	public final DoubleMatrix44 		plus(final Matrix44D _m) {
		return new DoubleMatrix44(	m00 + _m.get(0,0), m01 + _m.get(0,1), m02 + _m.get(0,2), m03 + _m.get(0,3),
								m10 + _m.get(1,0), m11 + _m.get(1,1), m12 + _m.get(1,2), m13 + _m.get(1,3),
								m20 + _m.get(2,0), m21 + _m.get(2,1), m22 + _m.get(2,2), m23 + _m.get(2,3),
								m30 + _m.get(3,0), m31 + _m.get(3,1), m32 + _m.get(3,2), m33 + _m.get(3,3) );
	}

	@Override
	public final DoubleMatrix44 		plusEquals(final Number _t) {
		m00 += _t.doubleValue(); m01 += _t.doubleValue(); m02 += _t.doubleValue(); m03 += _t.doubleValue();
		m10 += _t.doubleValue(); m11 += _t.doubleValue(); m12 += _t.doubleValue(); m13 += _t.doubleValue();
		m20 += _t.doubleValue(); m21 += _t.doubleValue(); m22 += _t.doubleValue(); m23 += _t.doubleValue();
		m30 += _t.doubleValue(); m31 += _t.doubleValue(); m32 += _t.doubleValue(); m33 += _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleMatrix44 		plusEquals(final NumberMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		m00 += _m.getNumber(0,0).doubleValue(); m01 += _m.getNumber(0,1).doubleValue(); m02 += _m.getNumber(0,2).doubleValue(); m03 += _m.getNumber(0,3).doubleValue();
		m10 += _m.getNumber(1,0).doubleValue(); m11 += _m.getNumber(1,1).doubleValue(); m12 += _m.getNumber(1,2).doubleValue(); m13 += _m.getNumber(1,3).doubleValue();
		m20 += _m.getNumber(2,0).doubleValue(); m21 += _m.getNumber(2,1).doubleValue(); m22 += _m.getNumber(2,2).doubleValue(); m23 += _m.getNumber(2,3).doubleValue();
		m30 += _m.getNumber(3,0).doubleValue(); m31 += _m.getNumber(3,1).doubleValue(); m32 += _m.getNumber(3,2).doubleValue(); m33 += _m.getNumber(3,3).doubleValue();
		return this;
	}
	
	@Override
	public final DoubleMatrix44 		plusEquals(final double _t) {
		m00 += _t; m01 += _t; m02 += _t; m03 += _t;
		m10 += _t; m11 += _t; m12 += _t; m13 += _t;
		m20 += _t; m21 += _t; m22 += _t; m23 += _t;
		m30 += _t; m31 += _t; m32 += _t; m33 += _t;
		return this;
	}
	@Override
	public final DoubleMatrix44 		plusEquals(final DoubleMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		m00 += _m.get(0,0); m01 += _m.get(0,1); m02 += _m.get(0,2); m03 += _m.get(0,3);
		m10 += _m.get(1,0); m11 += _m.get(1,1); m12 += _m.get(1,2); m13 += _m.get(1,3);
		m20 += _m.get(2,0); m21 += _m.get(2,1); m22 += _m.get(2,2); m23 += _m.get(2,3);
		m30 += _m.get(3,0); m31 += _m.get(3,1); m32 += _m.get(3,2); m33 += _m.get(3,3);
		return this;
	}
	
	public final DoubleMatrix44 		plusEquals(final Matrix44D _m) {
		m00 += _m.get(0,0); m01 += _m.get(0,1); m02 += _m.get(0,2); m03 += _m.get(0,3);
		m10 += _m.get(1,0); m11 += _m.get(1,1); m12 += _m.get(1,2); m13 += _m.get(1,3);
		m20 += _m.get(2,0); m21 += _m.get(2,1); m22 += _m.get(2,2); m23 += _m.get(2,3);
		m30 += _m.get(3,0); m31 += _m.get(3,1); m32 += _m.get(3,2); m33 += _m.get(3,3);
		return this;
	}

	@Override
	public final DoubleMatrix44 		minus(final Number _t) {
		return new DoubleMatrix44(	m00 - _t.doubleValue(), m01 - _t.doubleValue(), m02 - _t.doubleValue(), m03 - _t.doubleValue(),
								m10 - _t.doubleValue(), m11 - _t.doubleValue(), m12 - _t.doubleValue(), m13 - _t.doubleValue(),
								m20 - _t.doubleValue(), m21 - _t.doubleValue(), m22 - _t.doubleValue(), m23 - _t.doubleValue(),
								m30 - _t.doubleValue(), m31 - _t.doubleValue(), m32 - _t.doubleValue(), m33 - _t.doubleValue() );
	}
	@Override
	public final DoubleMatrix44 		minus(final NumberMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		return new DoubleMatrix44(	m00 - _m.getNumber(0,0).doubleValue(), m01 - _m.getNumber(0,1).doubleValue(), m02 - _m.getNumber(0,2).doubleValue(), m03 - _m.getNumber(0,3).doubleValue(),
								m10 - _m.getNumber(1,0).doubleValue(), m11 - _m.getNumber(1,1).doubleValue(), m12 - _m.getNumber(1,2).doubleValue(), m13 - _m.getNumber(1,3).doubleValue(),
								m20 - _m.getNumber(2,0).doubleValue(), m21 - _m.getNumber(2,1).doubleValue(), m22 - _m.getNumber(2,2).doubleValue(), m23 - _m.getNumber(2,3).doubleValue(),
								m30 - _m.getNumber(3,0).doubleValue(), m31 - _m.getNumber(3,1).doubleValue(), m32 - _m.getNumber(3,2).doubleValue(), m33 - _m.getNumber(3,3).doubleValue() );
	}

	@Override
	public final DoubleMatrix44 		minus(final double _t) {
		return new DoubleMatrix44(	m00 - _t, m01 - _t, m02 - _t, m03 - _t,
								m10 - _t, m11 - _t, m12 - _t, m13 - _t,
								m20 - _t, m21 - _t, m22 - _t, m23 - _t,
								m30 - _t, m31 - _t, m32 - _t, m33 - _t );
	}
	@Override
	public final DoubleMatrix44 		minus(final DoubleMatrix _m) {
		assert(_m.rows() == 4 && _m.columns() == 4);
		return new DoubleMatrix44(	m00 - _m.get(0,0), m01 - _m.get(0,1), m02 - _m.get(0,2), m03 - _m.get(0,3),
								m10 - _m.get(1,0), m11 - _m.get(1,1), m12 - _m.get(1,2), m13 - _m.get(1,3),
								m20 - _m.get(2,0), m21 - _m.get(2,1), m22 - _m.get(2,2), m23 - _m.get(2,3),
								m30 - _m.get(3,0), m31 - _m.get(3,1), m32 - _m.get(3,2), m33 - _m.get(3,3) );
	}

	public final DoubleMatrix44 		minus(final Matrix44D _m) {
		return new DoubleMatrix44(	m00 - _m.get(0,0), m01 - _m.get(0,1), m02 - _m.get(0,2), m03 - _m.get(0,3),
								m10 - _m.get(1,0), m11 - _m.get(1,1), m12 - _m.get(1,2), m13 - _m.get(1,3),
								m20 - _m.get(2,0), m21 - _m.get(2,1), m22 - _m.get(2,2), m23 - _m.get(2,3),
								m30 - _m.get(3,0), m31 - _m.get(3,1), m32 - _m.get(3,2), m33 - _m.get(3,3) );
	}

	@Override
	public final DoubleMatrix44 		minusEquals(final Number _t) {
		m00 -= _t.doubleValue(); m01 -= _t.doubleValue(); m02 -= _t.doubleValue(); m03 -= _t.doubleValue();
		m10 -= _t.doubleValue(); m11 -= _t.doubleValue(); m12 -= _t.doubleValue(); m13 -= _t.doubleValue();
		m20 -= _t.doubleValue(); m21 -= _t.doubleValue(); m22 -= _t.doubleValue(); m23 -= _t.doubleValue();
		m30 -= _t.doubleValue(); m31 -= _t.doubleValue(); m32 -= _t.doubleValue(); m33 -= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleMatrix44 		minusEquals(final NumberMatrix _m) {
		m00 -= _m.getNumber(0,0).doubleValue(); m01 -= _m.getNumber(0,1).doubleValue(); m02 -= _m.getNumber(0,2).doubleValue(); m03 -= _m.getNumber(0,3).doubleValue();
		m10 -= _m.getNumber(1,0).doubleValue(); m11 -= _m.getNumber(1,1).doubleValue(); m12 -= _m.getNumber(1,2).doubleValue(); m13 -= _m.getNumber(1,3).doubleValue();
		m20 -= _m.getNumber(2,0).doubleValue(); m21 -= _m.getNumber(2,1).doubleValue(); m22 -= _m.getNumber(2,2).doubleValue(); m23 -= _m.getNumber(2,3).doubleValue();
		m30 -= _m.getNumber(3,0).doubleValue(); m31 -= _m.getNumber(3,1).doubleValue(); m32 -= _m.getNumber(3,2).doubleValue(); m33 -= _m.getNumber(3,3).doubleValue();
		return this;
	}

	@Override
	public final DoubleMatrix44 		minusEquals(final double _t) {
		m00 -= _t; m01 -= _t; m02 -= _t; m03 -= _t;
		m10 -= _t; m11 -= _t; m12 -= _t; m13 -= _t;
		m20 -= _t; m21 -= _t; m22 -= _t; m23 -= _t;
		m30 -= _t; m31 -= _t; m32 -= _t; m33 -= _t;
		return this;
	}
	@Override
	public final DoubleMatrix44 		minusEquals(final DoubleMatrix _m) {
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m02 -= _m.get(0,2); m03 -= _m.get(0,3);
		m10 -= _m.get(1,0); m11 -= _m.get(1,1); m12 -= _m.get(1,2); m13 -= _m.get(1,3);
		m20 -= _m.get(2,0); m21 -= _m.get(2,1); m22 -= _m.get(2,2); m23 -= _m.get(2,3);
		m30 -= _m.get(3,0); m31 -= _m.get(3,1); m32 -= _m.get(3,2); m33 -= _m.get(3,3);
		return this;
	}

	public final DoubleMatrix44 		minusEquals(final Matrix44D _m) {
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m02 -= _m.get(0,2); m03 -= _m.get(0,3);
		m10 -= _m.get(1,0); m11 -= _m.get(1,1); m12 -= _m.get(1,2); m13 -= _m.get(1,3);
		m20 -= _m.get(2,0); m21 -= _m.get(2,1); m22 -= _m.get(2,2); m23 -= _m.get(2,3);
		m30 -= _m.get(3,0); m31 -= _m.get(3,1); m32 -= _m.get(3,2); m33 -= _m.get(3,3);
		return this;
	}

	@Override
	public final DoubleMatrix44 		times(final Number _d) {
		DoubleMatrix44 d = new DoubleMatrix44(
									m00*_d.doubleValue(), m01*_d.doubleValue(), m02*_d.doubleValue(), m03*_d.doubleValue(),
									m10*_d.doubleValue(), m11*_d.doubleValue(), m12*_d.doubleValue(), m13*_d.doubleValue(),
									m20*_d.doubleValue(), m21*_d.doubleValue(), m22*_d.doubleValue(), m23*_d.doubleValue(),
									m30*_d.doubleValue(), m31*_d.doubleValue(), m32*_d.doubleValue(), m33*_d.doubleValue()
								);
		return d;
	}
	@Override
	public final DoubleVector4D 		times(final NumberVector _v) {
		assert(_v.size() == 3);
		DoubleVector4D d = new DoubleVector4D(
									m00*_v.getNumber(0).doubleValue() + m01*_v.getNumber(1).doubleValue() + m02*_v.getNumber(2).doubleValue() + m03*_v.getNumber(3).doubleValue(),
									m10*_v.getNumber(0).doubleValue() + m11*_v.getNumber(1).doubleValue() + m12*_v.getNumber(2).doubleValue() + m13*_v.getNumber(3).doubleValue(),
									m20*_v.getNumber(0).doubleValue() + m21*_v.getNumber(1).doubleValue() + m22*_v.getNumber(2).doubleValue() + m23*_v.getNumber(3).doubleValue(),
									m30*_v.getNumber(0).doubleValue() + m31*_v.getNumber(1).doubleValue() + m32*_v.getNumber(2).doubleValue() + m33*_v.getNumber(3).doubleValue()
								);
		return d;
	}
	@Override
	public final DoubleMatrix44 		times(final NumberMatrix _m) {
		// TODO:: Gestion 4x4 . 4xm 
		assert(_m.rows() == 4 && _m.columns() == 4);
		DoubleMatrix44 D = new DoubleMatrix44(  m00*_m.getNumber(0,0).doubleValue() + m01*_m.getNumber(1,0).doubleValue() + m02*_m.getNumber(2,0).doubleValue() + m03*_m.getNumber(3,0).doubleValue(),
									  m00*_m.getNumber(0,1).doubleValue() + m01*_m.getNumber(1,1).doubleValue() + m02*_m.getNumber(2,1).doubleValue() + m03*_m.getNumber(3,1).doubleValue(),
									  m00*_m.getNumber(0,2).doubleValue() + m01*_m.getNumber(1,2).doubleValue() + m02*_m.getNumber(2,2).doubleValue() + m03*_m.getNumber(3,2).doubleValue(),
									  m00*_m.getNumber(0,3).doubleValue() + m01*_m.getNumber(1,3).doubleValue() + m02*_m.getNumber(2,3).doubleValue() + m03*_m.getNumber(3,3).doubleValue(),
									  m10*_m.getNumber(0,0).doubleValue() + m11*_m.getNumber(1,0).doubleValue() + m12*_m.getNumber(2,0).doubleValue() + m13*_m.getNumber(3,0).doubleValue(),
									  m10*_m.getNumber(0,1).doubleValue() + m11*_m.getNumber(1,1).doubleValue() + m12*_m.getNumber(2,1).doubleValue() + m13*_m.getNumber(3,1).doubleValue(),
									  m10*_m.getNumber(0,2).doubleValue() + m11*_m.getNumber(1,2).doubleValue() + m12*_m.getNumber(2,2).doubleValue() + m13*_m.getNumber(3,2).doubleValue(),
									  m10*_m.getNumber(0,3).doubleValue() + m11*_m.getNumber(1,3).doubleValue() + m12*_m.getNumber(2,3).doubleValue() + m13*_m.getNumber(3,3).doubleValue(),
									  m20*_m.getNumber(0,0).doubleValue() + m21*_m.getNumber(1,0).doubleValue() + m22*_m.getNumber(2,0).doubleValue() + m23*_m.getNumber(3,0).doubleValue(),
									  m20*_m.getNumber(0,1).doubleValue() + m21*_m.getNumber(1,1).doubleValue() + m22*_m.getNumber(2,1).doubleValue() + m23*_m.getNumber(3,1).doubleValue(),
									  m20*_m.getNumber(0,2).doubleValue() + m21*_m.getNumber(1,2).doubleValue() + m22*_m.getNumber(2,2).doubleValue() + m23*_m.getNumber(3,2).doubleValue(),
									  m20*_m.getNumber(0,3).doubleValue() + m21*_m.getNumber(1,3).doubleValue() + m22*_m.getNumber(2,3).doubleValue() + m23*_m.getNumber(3,3).doubleValue(),
									  m30*_m.getNumber(0,0).doubleValue() + m31*_m.getNumber(1,0).doubleValue() + m32*_m.getNumber(2,0).doubleValue() + m33*_m.getNumber(3,0).doubleValue(),
									  m30*_m.getNumber(0,1).doubleValue() + m31*_m.getNumber(1,1).doubleValue() + m32*_m.getNumber(2,1).doubleValue() + m33*_m.getNumber(3,1).doubleValue(),
									  m30*_m.getNumber(0,2).doubleValue() + m31*_m.getNumber(1,2).doubleValue() + m32*_m.getNumber(2,2).doubleValue() + m33*_m.getNumber(3,2).doubleValue(),
									  m30*_m.getNumber(0,3).doubleValue() + m31*_m.getNumber(1,3).doubleValue() + m32*_m.getNumber(2,3).doubleValue() + m33*_m.getNumber(3,3).doubleValue()
								 );
		return D;
	}
	
	@Override
	public final DoubleMatrix44 		times(final double _d) {
		DoubleMatrix44 d = new DoubleMatrix44(
									m00*_d, m01*_d, m02*_d, m03*_d,
									m10*_d, m11*_d, m12*_d, m13*_d,
									m20*_d, m21*_d, m22*_d, m23*_d,
									m30*_d, m31*_d, m32*_d, m33*_d
								);
		return d;
	}
	@Override
	public final DoubleVector4D 		times(final DoubleVector _v) {
		assert(_v.size() == 3);
		DoubleVector4D d = new DoubleVector4D(
									m00*_v.get(0) + m01*_v.get(1) + m02*_v.get(2) + m03*_v.get(3),
									m10*_v.get(0) + m11*_v.get(1) + m12*_v.get(2) + m13*_v.get(3),
									m20*_v.get(0) + m21*_v.get(1) + m22*_v.get(2) + m23*_v.get(3),
									m30*_v.get(0) + m31*_v.get(1) + m32*_v.get(2) + m33*_v.get(3)
								);
		return d;
	}
	@Override
	public final DoubleMatrix44 		times(final DoubleMatrix _m) {
		// TODO:: Gestion 4x4 . 4xm 
		assert(_m.rows() == 4 && _m.columns() == 4);
		DoubleMatrix44 D = new DoubleMatrix44(  m00*_m.get(0,0) + m01*_m.get(1,0) + m02*_m.get(2,0) + m03*_m.get(3,0),
									m00*_m.get(0,1) + m01*_m.get(1,1) + m02*_m.get(2,1) + m03*_m.get(3,1),
									m00*_m.get(0,2) + m01*_m.get(1,2) + m02*_m.get(2,2) + m03*_m.get(3,2),
									m00*_m.get(0,3) + m01*_m.get(1,3) + m02*_m.get(2,3) + m03*_m.get(3,3),
									m10*_m.get(0,0) + m11*_m.get(1,0) + m12*_m.get(2,0) + m13*_m.get(3,0),
									m10*_m.get(0,1) + m11*_m.get(1,1) + m12*_m.get(2,1) + m13*_m.get(3,1),
									m10*_m.get(0,2) + m11*_m.get(1,2) + m12*_m.get(2,2) + m13*_m.get(3,2),
									m10*_m.get(0,3) + m11*_m.get(1,3) + m12*_m.get(2,3) + m13*_m.get(3,3),
									m20*_m.get(0,0) + m21*_m.get(1,0) + m22*_m.get(2,0) + m23*_m.get(3,0),
									m20*_m.get(0,1) + m21*_m.get(1,1) + m22*_m.get(2,1) + m23*_m.get(3,1),
									m20*_m.get(0,2) + m21*_m.get(1,2) + m22*_m.get(2,2) + m23*_m.get(3,2),
									m20*_m.get(0,3) + m21*_m.get(1,3) + m22*_m.get(2,3) + m23*_m.get(3,3),
									m30*_m.get(0,0) + m31*_m.get(1,0) + m32*_m.get(2,0) + m33*_m.get(3,0),
									m30*_m.get(0,1) + m31*_m.get(1,1) + m32*_m.get(2,1) + m33*_m.get(3,1),
									m30*_m.get(0,2) + m31*_m.get(1,2) + m32*_m.get(2,2) + m33*_m.get(3,2),
									m30*_m.get(0,3) + m31*_m.get(1,3) + m32*_m.get(2,3) + m33*_m.get(3,3)
								 );
		return D;
	}
	
	public final DoubleVector4D 		times(final Vector4D _v) {
		DoubleVector4D d = new DoubleVector4D(
									m00*_v.getX() + m01*_v.getY() + m02*_v.getZ() + m03*_v.getW(),
									m10*_v.getX() + m11*_v.getY() + m12*_v.getZ() + m13*_v.getW(),
									m20*_v.getX() + m21*_v.getY() + m22*_v.getZ() + m23*_v.getW(),
									m30*_v.getX() + m31*_v.getY() + m32*_v.getZ() + m33*_v.getW()
								);
		return d;
	}
	public final DoubleMatrix44 		times(final Matrix44D _m) {
		DoubleMatrix44 D = new DoubleMatrix44(  m00*_m.get(0,0) + m01*_m.get(1,0) + m02*_m.get(2,0) + m03*_m.get(3,0),
									  m00*_m.get(0,1) + m01*_m.get(1,1) + m02*_m.get(2,1) + m03*_m.get(3,1),
									  m00*_m.get(0,2) + m01*_m.get(1,2) + m02*_m.get(2,2) + m03*_m.get(3,2),
									  m00*_m.get(0,3) + m01*_m.get(1,3) + m02*_m.get(2,3) + m03*_m.get(3,3),
									  m10*_m.get(0,0) + m11*_m.get(1,0) + m12*_m.get(2,0) + m13*_m.get(3,0),
									  m10*_m.get(0,1) + m11*_m.get(1,1) + m12*_m.get(2,1) + m13*_m.get(3,1),
									  m10*_m.get(0,2) + m11*_m.get(1,2) + m12*_m.get(2,2) + m13*_m.get(3,2),
									  m10*_m.get(0,3) + m11*_m.get(1,3) + m12*_m.get(2,3) + m13*_m.get(3,3),
									  m20*_m.get(0,0) + m21*_m.get(1,0) + m22*_m.get(2,0) + m23*_m.get(3,0),
									  m20*_m.get(0,1) + m21*_m.get(1,1) + m22*_m.get(2,1) + m23*_m.get(3,1),
									  m20*_m.get(0,2) + m21*_m.get(1,2) + m22*_m.get(2,2) + m23*_m.get(3,2),
									  m20*_m.get(0,3) + m21*_m.get(1,3) + m22*_m.get(2,3) + m23*_m.get(3,3),
									  m30*_m.get(0,0) + m31*_m.get(1,0) + m32*_m.get(2,0) + m33*_m.get(3,0),
									  m30*_m.get(0,1) + m31*_m.get(1,1) + m32*_m.get(2,1) + m33*_m.get(3,1),
									  m30*_m.get(0,2) + m31*_m.get(1,2) + m32*_m.get(2,2) + m33*_m.get(3,2),
									  m30*_m.get(0,3) + m31*_m.get(1,3) + m32*_m.get(2,3) + m33*_m.get(3,3)
								 );
		return D;
	}

	@Override
	public final DoubleMatrix44 		times(SquareMatrix _s) {
		return new DoubleMatrix44(this.times(_s));
	}

	@Override
	public final DoubleMatrix44 		timesEquals(final Number _d) {
		this.set(times(_d));
		return this;
	}
	@Override
	public final DoubleMatrix44 		timesEquals(final double _d) {
		this.set(times(_d));
		return this;
	}
	@Override
	public final DoubleMatrix44 		timesEquals(NumberMatrix _m) {
		double n00 = m00*_m.getNumber(0,0).doubleValue() + m01*_m.getNumber(1,0).doubleValue() + m02*_m.getNumber(2,0).doubleValue() + m03*_m.getNumber(3,0).doubleValue();
		double n01 = m00*_m.getNumber(0,1).doubleValue() + m01*_m.getNumber(1,1).doubleValue() + m02*_m.getNumber(2,1).doubleValue() + m03*_m.getNumber(3,1).doubleValue();
		double n02 = m00*_m.getNumber(0,2).doubleValue() + m01*_m.getNumber(1,2).doubleValue() + m02*_m.getNumber(2,2).doubleValue() + m03*_m.getNumber(3,2).doubleValue();
		double n03 = m00*_m.getNumber(0,3).doubleValue() + m01*_m.getNumber(1,3).doubleValue() + m02*_m.getNumber(2,3).doubleValue() + m03*_m.getNumber(3,3).doubleValue();
		double n10 = m10*_m.getNumber(0,0).doubleValue() + m11*_m.getNumber(1,0).doubleValue() + m12*_m.getNumber(2,0).doubleValue() + m13*_m.getNumber(3,0).doubleValue();
		double n11 = m10*_m.getNumber(0,1).doubleValue() + m11*_m.getNumber(1,1).doubleValue() + m12*_m.getNumber(2,1).doubleValue() + m13*_m.getNumber(3,1).doubleValue();
		double n12 = m10*_m.getNumber(0,2).doubleValue() + m11*_m.getNumber(1,2).doubleValue() + m12*_m.getNumber(2,2).doubleValue() + m13*_m.getNumber(3,2).doubleValue();
		double n13 = m10*_m.getNumber(0,3).doubleValue() + m11*_m.getNumber(1,3).doubleValue() + m12*_m.getNumber(2,3).doubleValue() + m13*_m.getNumber(3,3).doubleValue();
		double n20 = m20*_m.getNumber(0,0).doubleValue() + m21*_m.getNumber(1,0).doubleValue() + m22*_m.getNumber(2,0).doubleValue() + m23*_m.getNumber(3,0).doubleValue();
		double n21 = m20*_m.getNumber(0,1).doubleValue() + m21*_m.getNumber(1,1).doubleValue() + m22*_m.getNumber(2,1).doubleValue() + m23*_m.getNumber(3,1).doubleValue();
		double n22 = m20*_m.getNumber(0,2).doubleValue() + m21*_m.getNumber(1,2).doubleValue() + m22*_m.getNumber(2,2).doubleValue() + m23*_m.getNumber(3,2).doubleValue();
		double n23 = m20*_m.getNumber(0,3).doubleValue() + m21*_m.getNumber(1,3).doubleValue() + m22*_m.getNumber(2,3).doubleValue() + m23*_m.getNumber(3,3).doubleValue();
		double n30 = m30*_m.getNumber(0,0).doubleValue() + m31*_m.getNumber(1,0).doubleValue() + m32*_m.getNumber(2,0).doubleValue() + m33*_m.getNumber(3,0).doubleValue();
		double n31 = m30*_m.getNumber(0,1).doubleValue() + m31*_m.getNumber(1,1).doubleValue() + m32*_m.getNumber(2,1).doubleValue() + m33*_m.getNumber(3,1).doubleValue();
		double n32 = m30*_m.getNumber(0,2).doubleValue() + m31*_m.getNumber(1,2).doubleValue() + m32*_m.getNumber(2,2).doubleValue() + m33*_m.getNumber(3,2).doubleValue();
		double n33 = m30*_m.getNumber(0,3).doubleValue() + m31*_m.getNumber(1,3).doubleValue() + m32*_m.getNumber(2,3).doubleValue() + m33*_m.getNumber(3,3).doubleValue();

		m00 = n00; m01 = n01; m02 = n02; m03 = n03;
		m10 = n10; m11 = n11; m12 = n12; m13 = n13;
		m20 = n20; m21 = n21; m22 = n22; m23 = n23;
		m30 = n30; m31 = n31; m32 = n32; m33 = n33;

		return this;
	}
	@Override
	public final DoubleMatrix44 		timesEquals(DoubleMatrix _m) {
		double n00 = m00*_m.get(0,0) + m01*_m.get(1,0) + m02*_m.get(2,0) + m03*_m.get(3,0);
		double n01 = m00*_m.get(0,1) + m01*_m.get(1,1) + m02*_m.get(2,1) + m03*_m.get(3,1);
		double n02 = m00*_m.get(0,2) + m01*_m.get(1,2) + m02*_m.get(2,2) + m03*_m.get(3,2);
		double n03 = m00*_m.get(0,3) + m01*_m.get(1,3) + m02*_m.get(2,3) + m03*_m.get(3,3);
		double n10 = m10*_m.get(0,0) + m11*_m.get(1,0) + m12*_m.get(2,0) + m13*_m.get(3,0);
		double n11 = m10*_m.get(0,1) + m11*_m.get(1,1) + m12*_m.get(2,1) + m13*_m.get(3,1);
		double n12 = m10*_m.get(0,2) + m11*_m.get(1,2) + m12*_m.get(2,2) + m13*_m.get(3,2);
		double n13 = m10*_m.get(0,3) + m11*_m.get(1,3) + m12*_m.get(2,3) + m13*_m.get(3,3);
		double n20 = m20*_m.get(0,0) + m21*_m.get(1,0) + m22*_m.get(2,0) + m23*_m.get(3,0);
		double n21 = m20*_m.get(0,1) + m21*_m.get(1,1) + m22*_m.get(2,1) + m23*_m.get(3,1);
		double n22 = m20*_m.get(0,2) + m21*_m.get(1,2) + m22*_m.get(2,2) + m23*_m.get(3,2);
		double n23 = m20*_m.get(0,3) + m21*_m.get(1,3) + m22*_m.get(2,3) + m23*_m.get(3,3);
		double n30 = m30*_m.get(0,0) + m31*_m.get(1,0) + m32*_m.get(2,0) + m33*_m.get(3,0);
		double n31 = m30*_m.get(0,1) + m31*_m.get(1,1) + m32*_m.get(2,1) + m33*_m.get(3,1);
		double n32 = m30*_m.get(0,2) + m31*_m.get(1,2) + m32*_m.get(2,2) + m33*_m.get(3,2);
		double n33 = m30*_m.get(0,3) + m31*_m.get(1,3) + m32*_m.get(2,3) + m33*_m.get(3,3);

		m00 = n00; m01 = n01; m02 = n02; m03 = n03;
		m10 = n10; m11 = n11; m12 = n12; m13 = n13;
		m20 = n20; m21 = n21; m22 = n22; m23 = n23;
		m30 = n30; m31 = n31; m32 = n32; m33 = n33;

		return this;
	}
	@Override
	public final DoubleMatrix44 		timesEquals(final SquareMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);

		throw new NotYetImplementedException();
//		return this;
	}
	
	public final DoubleMatrix44 		timesEquals(final Matrix44D _d) {
		this.set(times(_d));
		return this;
	}

	@Override
	public final DoubleMatrix44 		arrayTimes(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							m00*B.getNumber(0,0).doubleValue(), m01*B.getNumber(0,1).doubleValue(), m02*B.getNumber(0,2).doubleValue(), m03*B.getNumber(0,3).doubleValue(),
							m10*B.getNumber(1,0).doubleValue(), m11*B.getNumber(1,1).doubleValue(), m12*B.getNumber(1,2).doubleValue(), m13*B.getNumber(1,3).doubleValue(),
							m20*B.getNumber(2,0).doubleValue(), m21*B.getNumber(2,1).doubleValue(), m22*B.getNumber(2,2).doubleValue(), m23*B.getNumber(2,3).doubleValue(),
							m30*B.getNumber(3,0).doubleValue(), m31*B.getNumber(3,1).doubleValue(), m32*B.getNumber(3,2).doubleValue(), m33*B.getNumber(3,3).doubleValue()
							);
	}
	@Override
	public final DoubleMatrix44 		arrayTimes(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							m00*B.get(0,0), m01*B.get(0,1), m02*B.get(0,2), m03*B.get(0,3),
							m10*B.get(1,0), m11*B.get(1,1), m12*B.get(1,2), m13*B.get(1,3),
							m20*B.get(2,0), m21*B.get(2,1), m22*B.get(2,2), m23*B.get(2,3),
							m30*B.get(3,0), m31*B.get(3,1), m32*B.get(3,2), m33*B.get(3,3)
							);
	}

	@Override
	public final DoubleMatrix44 		arrayTimesEquals(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			m00*B.getNumber(0,0).doubleValue(), m01*B.getNumber(0,1).doubleValue(), m02*B.getNumber(0,2).doubleValue(), m03*B.getNumber(0,3).doubleValue(),
			m10*B.getNumber(1,0).doubleValue(), m11*B.getNumber(1,1).doubleValue(), m12*B.getNumber(1,2).doubleValue(), m13*B.getNumber(1,3).doubleValue(),
			m20*B.getNumber(2,0).doubleValue(), m21*B.getNumber(2,1).doubleValue(), m22*B.getNumber(2,2).doubleValue(), m23*B.getNumber(2,3).doubleValue(),
			m30*B.getNumber(3,0).doubleValue(), m31*B.getNumber(3,1).doubleValue(), m32*B.getNumber(3,2).doubleValue(), m33*B.getNumber(3,3).doubleValue()
			);
		return this;
	}
	@Override
	public final DoubleMatrix44 		arrayTimesEquals(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			m00*B.get(0,0), m01*B.get(0,1), m02*B.get(0,2), m03*B.get(0,3),
			m10*B.get(1,0), m11*B.get(1,1), m12*B.get(1,2), m13*B.get(1,3),
			m20*B.get(2,0), m21*B.get(2,1), m22*B.get(2,2), m23*B.get(2,3),
			m30*B.get(3,0), m31*B.get(3,1), m32*B.get(3,2), m33*B.get(3,3)
			);
		return this;
	}

	@Override
	public final DoubleMatrix44 		arrayRightDivide(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							m00/B.getNumber(0,0).doubleValue(), m01/B.getNumber(0,1).doubleValue(), m02/B.getNumber(0,2).doubleValue(), m03/B.getNumber(0,3).doubleValue(),
							m10/B.getNumber(1,0).doubleValue(), m11/B.getNumber(1,1).doubleValue(), m12/B.getNumber(1,2).doubleValue(), m13/B.getNumber(1,3).doubleValue(),
							m20/B.getNumber(2,0).doubleValue(), m21/B.getNumber(2,1).doubleValue(), m22/B.getNumber(2,2).doubleValue(), m23/B.getNumber(2,3).doubleValue(),
							m30/B.getNumber(3,0).doubleValue(), m31/B.getNumber(3,1).doubleValue(), m32/B.getNumber(3,2).doubleValue(), m33/B.getNumber(3,3).doubleValue()
							);
	}
	@Override
	public final DoubleMatrix44 		arrayRightDivide(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							m00/B.get(0,0), m01/B.get(0,1), m02/B.get(0,2), m03/B.get(0,3),
							m10/B.get(1,0), m11/B.get(1,1), m12/B.get(1,2), m13/B.get(1,3),
							m20/B.get(2,0), m21/B.get(2,1), m22/B.get(2,2), m23/B.get(2,3),
							m30/B.get(3,0), m31/B.get(3,1), m32/B.get(3,2), m33/B.get(3,3)
							);
	}
	
	@Override
	public final DoubleMatrix44 		arrayRightDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			m00/B.getNumber(0,0).doubleValue(), m01/B.getNumber(0,1).doubleValue(), m02/B.getNumber(0,2).doubleValue(), m03/B.getNumber(0,3).doubleValue(),
			m10/B.getNumber(1,0).doubleValue(), m11/B.getNumber(1,1).doubleValue(), m12/B.getNumber(1,2).doubleValue(), m13/B.getNumber(1,3).doubleValue(),
			m20/B.getNumber(2,0).doubleValue(), m21/B.getNumber(2,1).doubleValue(), m22/B.getNumber(2,2).doubleValue(), m23/B.getNumber(2,3).doubleValue(),
			m30/B.getNumber(3,0).doubleValue(), m31/B.getNumber(3,1).doubleValue(), m32/B.getNumber(3,2).doubleValue(), m33/B.getNumber(3,3).doubleValue()
			);
		return this;
	}
	@Override
	public final DoubleMatrix44 		arrayRightDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			m00/B.get(0,0), m01/B.get(0,1), m02/B.get(0,2), m03/B.get(0,3),
			m10/B.get(1,0), m11/B.get(1,1), m12/B.get(1,2), m13/B.get(1,3),
			m20/B.get(2,0), m21/B.get(2,1), m22/B.get(2,2), m23/B.get(2,3),
			m30/B.get(3,0), m31/B.get(3,1), m32/B.get(3,2), m33/B.get(3,3)
			);
		return this;
	}

	@Override
	public final DoubleMatrix44 		arrayLeftDivide(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							B.getNumber(0,0).doubleValue()/m00, B.getNumber(0,1).doubleValue()/m01, B.getNumber(0,2).doubleValue()/m02, B.getNumber(0,3).doubleValue()/m03,
							B.getNumber(1,0).doubleValue()/m10, B.getNumber(1,1).doubleValue()/m11, B.getNumber(1,2).doubleValue()/m12, B.getNumber(1,3).doubleValue()/m13,
							B.getNumber(2,0).doubleValue()/m20, B.getNumber(2,1).doubleValue()/m21, B.getNumber(2,2).doubleValue()/m22, B.getNumber(2,3).doubleValue()/m23,
							B.getNumber(3,0).doubleValue()/m30, B.getNumber(3,1).doubleValue()/m31, B.getNumber(3,2).doubleValue()/m32, B.getNumber(3,3).doubleValue()/m33
							);
	}
	@Override
	public final DoubleMatrix44 		arrayLeftDivide(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		return new DoubleMatrix44(	
							B.get(0,0)/m00, B.get(0,1)/m01, B.get(0,2)/m02, B.get(0,3)/m03,
							B.get(1,0)/m10, B.get(1,1)/m11, B.get(1,2)/m12, B.get(1,3)/m13,
							B.get(2,0)/m20, B.get(2,1)/m21, B.get(2,2)/m22, B.get(2,3)/m23,
							B.get(3,0)/m30, B.get(3,1)/m31, B.get(3,2)/m32, B.get(3,3)/m33
							);
	}

	@Override
	public final DoubleMatrix44 		arrayLeftDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			B.getNumber(0,0).doubleValue()/m00, B.getNumber(0,1).doubleValue()/m01, B.getNumber(0,2).doubleValue()/m02, B.getNumber(0,3).doubleValue()/m03,
			B.getNumber(1,0).doubleValue()/m10, B.getNumber(1,1).doubleValue()/m11, B.getNumber(1,2).doubleValue()/m12, B.getNumber(1,3).doubleValue()/m13,
			B.getNumber(2,0).doubleValue()/m20, B.getNumber(2,1).doubleValue()/m21, B.getNumber(2,2).doubleValue()/m22, B.getNumber(2,3).doubleValue()/m23,
			B.getNumber(3,0).doubleValue()/m30, B.getNumber(3,1).doubleValue()/m31, B.getNumber(3,2).doubleValue()/m32, B.getNumber(3,3).doubleValue()/m33
			);
		return this;
	}
	@Override
	public final DoubleMatrix44 		arrayLeftDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 4 && B.columns() == 4);
		set(  	
			B.get(0,0)/m00, B.get(0,1)/m01, B.get(0,2)/m02, B.get(0,3)/m03,
			B.get(1,0)/m10, B.get(1,1)/m11, B.get(1,2)/m12, B.get(1,3)/m13,
			B.get(2,0)/m20, B.get(2,1)/m21, B.get(2,2)/m22, B.get(2,3)/m23,
			B.get(3,0)/m30, B.get(3,1)/m31, B.get(3,2)/m32, B.get(3,3)/m33
			);
		return this;
	}

	public final boolean 		isValid() {
		return false;
	}

	@Override
	public final boolean 		isEqual(final double _d) {
		return (m00 == _d && m01 == _d && m10 == _d && m11 == _d) ? true : false;
	}
	public final boolean 		isEqual(final NumberMatrix _m) {
		return false; //(m00 == _m.getNumber(0,0).doubleValue() && m01 == _m.getNumber(0,1).doubleValue() && m10 == _m.getNumber(1,0).doubleValue() && m11 == _m.getNumber(1,1).doubleValue()) ? true : false;
	}
	public final boolean 		isEqual(final DoubleMatrix _m) {
		return false; //(m00 == _m.get(0,0) && m01 == _m.get(0,1) && m10 == _m.get(1,0) && m11 == _m.get(1,1)) ? true : false;
	}

	public final boolean 		isEqual(final Matrix44D _m) {
		return false; //(m00 == _m.m00 && m01 == _m.m01 && m10 == _m.m10 && m11 == _m.m11) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _d) {
		return (m00 != _d || m01 != _d || m10 != _d || m11 != _d) ? true : false;
	}
	public final boolean 		isDifferent(final NumberMatrix _m) {
		return false; //(m00 == _m.getNumber(0,0).doubleValue() && m01 == _m.getNumber(0,1).doubleValue() && m10 == _m.getNumber(1,0).doubleValue() && m11 == _m.getNumber(1,1).doubleValue()) ? true : false;
	}
	public final boolean 		isDifferent(final DoubleMatrix _m) {
		return false; //(m00 == _m.get(0,0) && m01 == _m.get(0,1) && m10 == _m.get(1,0) && m11 == _m.get(1,1)) ? true : false;
	}

	public final boolean 		isDifferent(final Matrix44D _m) {
		return (m00 != _m.get(0,0) || m01 != _m.get(0,1) || m10 != _m.get(1,0) || m11 != _m.get(1,1)) ? true : false;
	}

	@Override
	public DoubleMatrix44 			clone() {
		return new DoubleMatrix44(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}
	@Override
	public final DoubleMatrix44 		abs() {
		return new DoubleMatrix44(Math.abs(m00), Math.abs(m01), Math.abs(m02), Math.abs(m03), Math.abs(m10), Math.abs(m11), Math.abs(m12), Math.abs(m13), Math.abs(m20), Math.abs(m21), Math.abs(m22), Math.abs(m23), Math.abs(m30), Math.abs(m31), Math.abs(m32), Math.abs(m33));
	}
	@Override
	public final DoubleMatrix44 		inverse() {
		double det = det();
		if(det() == 0)
			return null;

		DoubleMatrix44 inv = new DoubleMatrix44(); 
		inv.m00 = m12*m23*m31 - m13*m22*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33 + m11*m22*m33;
		inv.m01 = m03*m22*m31 - m02*m23*m31 - m03*m21*m32 + m01*m23*m32 + m02*m21*m33 - m01*m22*m33;
		inv.m02 = m02*m13*m31 - m03*m12*m31 + m03*m11*m32 - m01*m13*m32 - m02*m11*m33 + m01*m12*m33;
		inv.m03 = m03*m12*m21 - m02*m13*m21 - m03*m11*m22 + m01*m13*m22 + m02*m11*m23 - m01*m12*m23;
		inv.m10 = m13*m22*m30 - m12*m23*m30 - m13*m20*m32 + m10*m23*m32 + m12*m20*m33 - m10*m22*m33;
		inv.m11 = m02*m23*m30 - m03*m22*m30 + m03*m20*m32 - m00*m23*m32 - m02*m20*m33 + m00*m22*m33;
		inv.m12 = m03*m12*m30 - m02*m13*m30 - m03*m10*m32 + m00*m13*m32 + m02*m10*m33 - m00*m12*m33;
		inv.m13 = m02*m13*m20 - m03*m12*m20 + m03*m10*m22 - m00*m13*m22 - m02*m10*m23 + m00*m12*m23;
		inv.m20 = m11*m23*m30 - m13*m21*m30 + m13*m20*m31 - m10*m23*m31 - m11*m20*m33 + m10*m21*m33;
		inv.m21 = m03*m21*m30 - m01*m23*m30 - m03*m20*m31 + m00*m23*m31 + m01*m20*m33 - m00*m21*m33;
		inv.m22 = m01*m13*m30 - m03*m11*m30 + m03*m10*m31 - m00*m13*m31 - m01*m10*m33 + m00*m11*m33;
		inv.m23 = m03*m11*m20 - m01*m13*m20 - m03*m10*m21 + m00*m13*m21 + m01*m10*m23 - m00*m11*m23;
		inv.m30 = m12*m21*m30 - m11*m22*m30 - m12*m20*m31 + m10*m22*m31 + m11*m20*m32 - m10*m21*m32;
		inv.m31 = m01*m22*m30 - m02*m21*m30 + m02*m20*m31 - m00*m22*m31 - m01*m20*m32 + m00*m21*m32;
		inv.m32 = m02*m11*m30 - m01*m12*m30 - m02*m10*m31 + m00*m12*m31 + m01*m10*m32 - m00*m11*m32;
		inv.m33 = m01*m12*m20 - m02*m11*m20 + m02*m10*m21 - m00*m12*m21 - m01*m10*m22 + m00*m11*m22;
//		inv.dividesEquals(1/det);
		inv.timesEquals(1/det);
		return inv;
	}
	@Override
	public final DoubleMatrix44 		transpose() {
		return new DoubleMatrix44(m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
	}
	@Override
	public final DoubleMatrix44 		uminus() {
		return new DoubleMatrix44(-m00, -m01, -m02, -m03, -m10, -m11, -m12, -m13, -m20, -m21, -m22, -m23, -m30, -m31, -m32, -m33);
	}


	@Override
	public final int 			rank() {
		return new SingularValueDecomposition(this).rank();
	}
	@Override
	public final double 		cond() {
		return new SingularValueDecomposition(this).cond();
	}
	@Override
	public final double	 		det() {
		return  m03 * m12 * m21 * m30 - m02 * m13 * m21 * m30 - m03 * m11 * m22 * m30 + m01 * m13 * m22 * m30 +
				m02 * m11 * m23 * m30 - m01 * m12 * m23 * m30 - m03 * m12 * m20 * m31 + m02 * m13 * m20 * m31 +
				m03 * m10 * m22 * m31 - m00 * m13 * m22 * m31 - m02 * m10 * m23 * m31 + m00 * m12 * m23 * m31 +
				m03 * m11 * m20 * m32 - m01 * m13 * m20 * m32 - m03 * m10 * m21 * m32 + m00 * m13 * m21 * m32 +
				m01 * m10 * m23 * m32 - m00 * m11 * m23 * m32 - m02 * m11 * m20 * m33 + m01 * m12 * m20 * m33 +
				m02 * m10 * m21 * m33 - m00 * m12 * m21 * m33 - m01 * m10 * m22 * m33 + m00 * m11 * m22 * m33;
	}
	@Override
	public final double 		trace() {
		return m00 + m11 + m22 + m33;
	}
	@Override
	public final double 		norm1() {
		double norm1 = Math.max(Math.abs(m00) + Math.abs(m10) + Math.abs(m20) + Math.abs(m30), Math.abs(m01) + Math.abs(m11) + Math.abs(m21) + Math.abs(m31));
		norm1 = Math.max(norm1, Math.abs(m02) + Math.abs(m12) + Math.abs(m22) + Math.abs(m32));
		return Math.max(norm1, Math.abs(m03) + Math.abs(m13) + Math.abs(m23) + Math.abs(m33));
	}
	@Override
	public final double 		norm2() {
		return new SingularValueDecomposition(this).norm2();
	}
	@Override
	public final double 		normInf() {
		double normInf = Math.max(Math.abs(m00) + Math.abs(m01) + Math.abs(m02) + Math.abs(m03), Math.abs(m10) + Math.abs(m11) + Math.abs(m12) + Math.abs(m13));
		normInf = Math.max(normInf, Math.abs(m20) + Math.abs(m21) + Math.abs(m22) + Math.abs(m23));
		return Math.max(normInf, Math.abs(m30) + Math.abs(m31) + Math.abs(m32) + Math.abs(m33));
	}
	@Override
	public final double 		normF() {
		double f = 0;
		f = Numbers.hypot(f, m00);
		f = Numbers.hypot(f, m01);
		f = Numbers.hypot(f, m02);
		f = Numbers.hypot(f, m03);
		f = Numbers.hypot(f, m10);
		f = Numbers.hypot(f, m11);
		f = Numbers.hypot(f, m12);
		f = Numbers.hypot(f, m13);
		f = Numbers.hypot(f, m20);
		f = Numbers.hypot(f, m21);
		f = Numbers.hypot(f, m22);
		f = Numbers.hypot(f, m23);
		f = Numbers.hypot(f, m30);
		f = Numbers.hypot(f, m31);
		f = Numbers.hypot(f, m32);
		f = Numbers.hypot(f, m33);
		return f;
	}

	@Override
	public final String 		toString() {
		return "[" + m00 + "," + m01 + "," + m02 + "," + m03 + "\n"
				+ m10 + "," + m11 + "," + m12 + "," + m13 + "\n"
				+ m20 + "," + m21 + "," + m22 + "," + m23 + "\n"
				+ m30 + "," + m31 + "," + m32 + "," + m33 + "]";
	}
	@Override
	public final String 		toString(NumberFormat _nf) {
		return "[" + _nf.format(m00) + "," + _nf.format(m01) + "," + _nf.format(m02) + "," + _nf.format(m03) + "\n"
				   + _nf.format(m10) + "," + _nf.format(m11) + "," + _nf.format(m12) + "," + _nf.format(m13) + "\n"
				   + _nf.format(m20) + "," + _nf.format(m21) + "," + _nf.format(m22) + "," + _nf.format(m23) + "\n"
				   + _nf.format(m30) + "," + _nf.format(m31) + "," + _nf.format(m32) + "," + _nf.format(m33) + "]";
	}

	public final DoubleVector4D 		solve(final Vector4D b) {
		throw new IllegalAccessError();
	}

	@Override
	public boolean equals(Object _obj) {
		if(this == _obj)
			return true;
		if(_obj == null)
			return false;
		if(getClass() != _obj.getClass())
			return false;
		DoubleMatrix44 other = (DoubleMatrix44) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return false;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return false;
		if(Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02))
			return false;
		if(Double.doubleToLongBits(m03) != Double.doubleToLongBits(other.m03))
			return false;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return false;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return false;
		if(Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
			return false;
		if(Double.doubleToLongBits(m13) != Double.doubleToLongBits(other.m13))
			return false;
		if(Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20))
			return false;
		if(Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
			return false;
		if(Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
			return false;
		if(Double.doubleToLongBits(m23) != Double.doubleToLongBits(other.m23))
			return false;
		if(Double.doubleToLongBits(m30) != Double.doubleToLongBits(other.m30))
			return false;
		if(Double.doubleToLongBits(m31) != Double.doubleToLongBits(other.m31))
			return false;
		if(Double.doubleToLongBits(m32) != Double.doubleToLongBits(other.m32))
			return false;
		if(Double.doubleToLongBits(m33) != Double.doubleToLongBits(other.m33))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object _obj) {
		if(this == _obj)
			return 0;
		if(_obj == null)
			return 1;
		if(getClass() != _obj.getClass())
			return 1;
		DoubleMatrix44 other = (DoubleMatrix44) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return Double.doubleToLongBits(m00) > Double.doubleToLongBits(other.m00) ? 1 : -1;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return Double.doubleToLongBits(m01) > Double.doubleToLongBits(other.m01) ? 1 : -1;
		if(Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02))
			return Double.doubleToLongBits(m02) > Double.doubleToLongBits(other.m02) ? 1 : -1;
		if(Double.doubleToLongBits(m03) != Double.doubleToLongBits(other.m03))
			return Double.doubleToLongBits(m03) > Double.doubleToLongBits(other.m03) ? 1 : -1;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return Double.doubleToLongBits(m10) > Double.doubleToLongBits(other.m10) ? 1 : -1;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return Double.doubleToLongBits(m11) > Double.doubleToLongBits(other.m11) ? 1 : -1;
		if(Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
			return Double.doubleToLongBits(m12) > Double.doubleToLongBits(other.m12) ? 1 : -1;
		if(Double.doubleToLongBits(m13) != Double.doubleToLongBits(other.m13))
			return Double.doubleToLongBits(m13) > Double.doubleToLongBits(other.m13) ? 1 : -1;
		if(Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20))
			return Double.doubleToLongBits(m20) > Double.doubleToLongBits(other.m20) ? 1 : -1;
		if(Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
			return Double.doubleToLongBits(m21) > Double.doubleToLongBits(other.m21) ? 1 : -1;
		if(Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
			return Double.doubleToLongBits(m22) > Double.doubleToLongBits(other.m22) ? 1 : -1;
		if(Double.doubleToLongBits(m23) != Double.doubleToLongBits(other.m23))
			return Double.doubleToLongBits(m23) > Double.doubleToLongBits(other.m23) ? 1 : -1;
		if(Double.doubleToLongBits(m30) != Double.doubleToLongBits(other.m30))
			return Double.doubleToLongBits(m30) > Double.doubleToLongBits(other.m30) ? 1 : -1;
		if(Double.doubleToLongBits(m31) != Double.doubleToLongBits(other.m31))
			return Double.doubleToLongBits(m31) > Double.doubleToLongBits(other.m31) ? 1 : -1;
		if(Double.doubleToLongBits(m32) != Double.doubleToLongBits(other.m32))
			return Double.doubleToLongBits(m32) > Double.doubleToLongBits(other.m32) ? 1 : -1;
		if(Double.doubleToLongBits(m33) != Double.doubleToLongBits(other.m33))
			return Double.doubleToLongBits(m33) > Double.doubleToLongBits(other.m33) ? 1 : -1;
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn(0).hashCode();
		result = prime * result + getColumn(1).hashCode();
	    result = prime * result + getColumn(2).hashCode();
	    result = prime * result + getColumn(3).hashCode();
		return result;
	}

	public final DoubleMatrix33 as3D() {
		return new DoubleMatrix33(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	public final DoubleMatrix22 as2D() {
		return new DoubleMatrix22(m00, m01, m10, m11);
	}
/*
	@Override
	public final double[][] getStorage() {
		throw new IllegalAccessError();
	}
	@Override
	public final double[][] getStorageCopy() {
		return new double[][] { { m00, m01, m02, m03 }, { m10, m11, m12, m13 }, { m20, m21, m22, m23 }, { m30, m31, m32, m33 } };
	}
	@Override
	public final double[] 	getColumnPackedCopy() {
		return new double[] { m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33 };
	}
	@Override
	public final double[] 	getRowPackedCopy() {
		return new double[] { m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33 };
	}
*/
}
