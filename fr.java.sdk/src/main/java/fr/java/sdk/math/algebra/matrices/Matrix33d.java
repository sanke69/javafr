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
package fr.java.sdk.math.algebra.matrices;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;
import fr.java.math.algebra.matrix.SquareMatrix;
import fr.java.math.algebra.matrix.specials.Matrix33D;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Numbers;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.matrices.doubles.decompositions.SingularValueDecomposition;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;

public class Matrix33d implements Matrix33D.Editable {
	private static final long serialVersionUID = 1L;

	public final static Matrix33d zero() {
		return new Matrix33d(0,0,0,0,0,0,0,0,0);
	}
	public final static Matrix33d identity() {
		return new Matrix33d(1,0,0,0,1,0,0,0,1);
	}
	public final static Matrix33d unitary() {
		return new Matrix33d(1,1,1,1,1,1,1,1,1);
	}

	public double 	m00, m01, m02, 
					m10, m11, m12, 
					m20, m21, m22;

	public Matrix33d() {
	}
	public Matrix33d(final double _m00, final double _m01, final double _m02, final double _m10, final double _m11, final double _m12, final double _m20, final double _m21, final double _m22) {
		m00 = _m00; m01 = _m01; m02 = _m02;
		m10 = _m10; m11 = _m11; m12 = _m12;
		m20 = _m20; m21 = _m21; m22 = _m22;
	}
	public Matrix33d(final Matrix33d _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22;
	}

	// TENSOR METHODS
	@Override
	public final boolean 		isDirect() {
		return false;
	}
	@Override
	public final double[] 		getArray() {
		return new double[] { m00, m01, m02, m10, m11, m12, m20, m21, m22 };
	}
	@Override
	public final DoubleBuffer 	getBuffer() {
		return DoubleBuffer.wrap(getArray());
	}
    public final DoubleStream 	getStream() { return DoubleStream.of(m00, m01, m02, m10, m11, m12, m20, m21, m22); }

	@Override
	public Number 				getNumber(long _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public double 				getValue(long _index) {
		switch((int) _index) {
		case 0  : return m00;
		case 1  : return m01;
		case 2  : return m02;
		case 3  : return m10;
		case 4  : return m11;
		case 5  : return m12;
		case 6  : return m20;
		case 7  : return m21;
		case 8  : return m22;
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
			else
				return m02;
		} else if(_coords[1] == 1) {
			if(_coords[0] == 0)
				return m10;
			else if(_coords[0] == 1)
				return m11;
			else
				return m12;
		} else {
			if(_coords[0] == 0)
				return m20;
			else if(_coords[0] == 1)
				return m21;
			else
				return m22;
		}
	}

	@Override
	public void 				setValue(double _value, long _index) {
		switch((int) _index) {
		case 0  : m00 = _value;
		case 1  : m01 = _value;
		case 2  : m02 = _value;
		case 3  : m10 = _value;
		case 4  : m11 = _value;
		case 5  : m12 = _value;
		case 6  : m20 = _value;
		case 7  : m21 = _value;
		case 8  : m22 = _value;
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
			else
				m02 = _value;
		} else if(_coords[1] == 1) {
			if(_coords[0] == 0)
				m10 = _value;
			else if(_coords[0] == 1)
				m11 = _value;
			else
				m12 = _value;
		} else {
			if(_coords[0] == 0)
				m20 = _value;
			else if(_coords[0] == 1)
				m21 = _value;
			else
				m22 = _value;
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
		throw new IllegalArgumentException("Can't reshape a Matrix 3x3");
	}

	// MATRIX METHODS
	@Override
	public final int 			columns() { return 3; }
	@Override
	public final int 			rows() { return 3; }

	@Override
	public final Number 		getNumber(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else if(_j == 1)
				return m01;
			else
				return m02;
		} else if(_i == 1) {
			if(_j == 0)
				return m10;
			else if(_j == 1)
				return m11;
			else
				return m12;
		} else {
			if(_j == 0)
				return m20;
			else if(_j == 1)
				return m21;
			else
				return m22;
		}
	}
	@Override
	public final void 			setNumber(final int _i, final int _j, final Number _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value.doubleValue();
			else if(_j == 1)
				m01 = _value.doubleValue();
			else
				m02 = _value.doubleValue();
		} else if(_i == 1) {
			if(_j == 0)
				m10 = _value.doubleValue();
			else if(_j == 1)
				m11 = _value.doubleValue();
			else
				m12 = _value.doubleValue();
		} else {
			if(_j == 0)
				m20 = _value.doubleValue();
			else if(_j == 1)
				m21 = _value.doubleValue();
			else
				m22 = _value.doubleValue();
		}
	}

	@Override
	public final double		 	get(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else if(_j == 1)
				return m01;
			else
				return m02;
		} else if(_i == 1) {
			if(_j == 0)
				return m10;
			else if(_j == 1)
				return m11;
			else
				return m12;
		} else {
			if(_j == 0)
				return m20;
			else if(_j == 1)
				return m21;
			else
				return m22;
		}
	}
	@Override
	public final void 			set(final int _i, final int _j, final double _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value;
			else if(_j == 1)
				m01 = _value;
			else
				m02 = _value;
		} else if(_i == 1) {
			if(_j == 0)
				m10 = _value;
			else if(_j == 1)
				m11 = _value;
			else
				m12 = _value;
		} else {
			if(_j == 0)
				m20 = _value;
			else if(_j == 1)
				m21 = _value;
			else
				m22 = _value;
		}
	}

	@Override
	public final Vector3D	 	getRow(final int _i) {
		switch(_i) {
		case 0 : return new DoubleVector3D(m00, m01, m02);
		case 1 : return new DoubleVector3D(m10, m11, m12);
		case 2 : return new DoubleVector3D(m20, m21, m22);
		default : throw new IllegalAccessError();
		}
	}
	@Override
	public final Vector3D 		getColumn(final int _j) {
		switch(_j) {
		case 0 : return new DoubleVector3D(m00, m10, m20);
		case 1 : return new DoubleVector3D(m01, m11, m21);
		case 2 : return new DoubleVector3D(m02, m12, m22);
		default : throw new IllegalAccessError();
		}
	}

	@Deprecated
	@Override
	public final void 			set(final double _t) {
		m00 = _t; m01 = _t; m02 = _t;
		m10 = _t; m11 = _t; m12 = _t;
		m20 = _t; m21 = _t; m22 = _t;
	}
	@Deprecated
	@Override
	public final void 			set(final double _m00, final double _m01, final double _m02, final double _m10, final double _m11, final double _m12, final double _m20, final double _m21, final double _m22) {
		m00 = _m00; m01 = _m01; m02 = _m02;
		m10 = _m10; m11 = _m11; m12 = _m12;
		m20 = _m20; m21 = _m21; m22 = _m22;
	}
	@Deprecated
	@Override
	public final void 			set(final Vector3D _col0, final Vector3D _col1, final Vector3D _col2) {
		m00 = _col0.getX(); m01 = _col1.getX(); m02 = _col2.getX(); 
		m10 = _col0.getY(); m11 = _col1.getY(); m12 = _col2.getY(); 
		m20 = _col0.getZ(); m21 = _col1.getZ(); m22 = _col2.getZ(); 
	}
	@Deprecated
	@Override
	public final void 			set(final Matrix33D _m) {
		m00 = _m.get(0,0); m01 = _m.get(0,1); m02 = _m.get(0,2);
		m10 = _m.get(1,0); m11 = _m.get(1,1); m12 = _m.get(1,2);
		m20 = _m.get(2,0); m21 = _m.get(2,1); m22 = _m.get(2,2);
	}

	@Override
	public final void 			setMatrix(int _i0, int _i1, int _j0, int _j1, NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(int _i0, int _i1, int _j0, int _j1) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(int[] _r, int[] _c, NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(int[] _r, int[] _c) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(int _i0, int _i1, int[] _c, NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(int _i0, int _i1, int[] _c) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(int[] _r, int _j0, int _j1) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(int[] _r, int _j0, int _j1, NumberMatrix _X) {
		throw new IllegalAccessError();
	}

	@Override
	public final Matrix33d 		plus(final Number _t) {
		return new Matrix33d(m00 + _t.doubleValue(), m01 + _t.doubleValue(), m02 + _t.doubleValue(),
							 m10 + _t.doubleValue(), m11 + _t.doubleValue(), m12 + _t.doubleValue(),
							 m20 + _t.doubleValue(), m21 + _t.doubleValue(), m22 + _t.doubleValue());
	}
	@Override
	public final Matrix33d 		plus(final NumberMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return new Matrix33d(m00 + _m.getNumber(0,0).doubleValue(), m01 + _m.getNumber(0,1).doubleValue(), m02 + _m.getNumber(0,2).doubleValue(),
							 m10 + _m.getNumber(1,0).doubleValue(), m11 + _m.getNumber(1,1).doubleValue(), m12 + _m.getNumber(1,2).doubleValue(),
							 m20 + _m.getNumber(2,0).doubleValue(), m21 + _m.getNumber(2,1).doubleValue(), m22 + _m.getNumber(2,2).doubleValue());
	}

	@Override
	public final Matrix33d 		plus(final double _t) {
		return new Matrix33d(m00 + _t, m01 + _t, m02 + _t,
							 m10 + _t, m11 + _t, m12 + _t,
							 m20 + _t, m21 + _t, m22 + _t);
	}
	@Override
	public final Matrix33d 		plus(final DoubleMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return new Matrix33d(m00 + _m.get(0,0), m01 + _m.get(0,1), m02 + _m.get(0,2),
							 m10 + _m.get(1,0), m11 + _m.get(1,1), m12 + _m.get(1,2),
							 m20 + _m.get(2,0), m21 + _m.get(2,1), m22 + _m.get(2,2));
	}

	@Override
	public final Matrix33d 		plus(final Matrix33D _m) {
		return new Matrix33d(m00 + _m.get(0,0), m01 + _m.get(0,1), m02 + _m.get(0,2),
							 m10 + _m.get(1,0), m11 + _m.get(1,1), m12 + _m.get(1,2),
							 m20 + _m.get(2,0), m21 + _m.get(2,1), m22 + _m.get(2,2));
	}

	@Override
	public final Matrix33d 		plusEquals(final Number _t) {
		m00 += _t.doubleValue(); m01 += _t.doubleValue(); m02 += _t.doubleValue();
		m10 += _t.doubleValue(); m11 += _t.doubleValue(); m12 += _t.doubleValue();
		m20 += _t.doubleValue(); m21 += _t.doubleValue(); m22 += _t.doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		plusEquals(final NumberMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		m00 += _m.getNumber(0,0).doubleValue(); m01 += _m.getNumber(0,1).doubleValue(); m02 += _m.getNumber(0,2).doubleValue();
		m10 += _m.getNumber(1,0).doubleValue(); m11 += _m.getNumber(1,1).doubleValue(); m12 += _m.getNumber(1,2).doubleValue();
		m20 += _m.getNumber(2,0).doubleValue(); m21 += _m.getNumber(2,1).doubleValue(); m22 += _m.getNumber(2,2).doubleValue();
		return this;
	}

	@Override
	public final Matrix33d 		plusEquals(final double _t) {
		m00 += _t; m01 += _t; m02 += _t;
		m10 += _t; m11 += _t; m12 += _t;
		m20 += _t; m21 += _t; m22 += _t;
		return this;
	}
	@Override
	public final Matrix33d 		plusEquals(final DoubleMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		m00 += _m.get(0,0); m01 += _m.get(0,1); m02 += _m.get(0,2);
		m10 += _m.get(1,0); m11 += _m.get(1,1); m12 += _m.get(1,2);
		m20 += _m.get(2,0); m21 += _m.get(2,1); m22 += _m.get(2,2);
		return this;
	}

	@Override
	public final Matrix33d 		plusEquals(final Matrix33D _m) {
		m00 += _m.get(0,0); m01 += _m.get(0,1); m02 += _m.get(0,2);
		m10 += _m.get(1,0); m11 += _m.get(1,1); m12 += _m.get(1,2);
		m20 += _m.get(2,0); m21 += _m.get(2,1); m22 += _m.get(2,2);
		return this;
	}

	@Override
	public final Matrix33d 		minus(final Number _t) {
		return new Matrix33d(m00 - _t.doubleValue(), m01 - _t.doubleValue(), m02 - _t.doubleValue(),
							 m10 - _t.doubleValue(), m11 - _t.doubleValue(), m12 - _t.doubleValue(),
							 m20 - _t.doubleValue(), m21 - _t.doubleValue(), m22 - _t.doubleValue());
	}
	@Override
	public final Matrix33d 		minus(final NumberMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return new Matrix33d(m00 - _m.getNumber(0,0).doubleValue(), m01 - _m.getNumber(0,1).doubleValue(), m02 - _m.getNumber(0,2).doubleValue(),
							 m10 - _m.getNumber(1,0).doubleValue(), m11 - _m.getNumber(1,1).doubleValue(), m12 - _m.getNumber(1,2).doubleValue(),
							 m20 - _m.getNumber(2,0).doubleValue(), m21 - _m.getNumber(2,1).doubleValue(), m22 - _m.getNumber(2,2).doubleValue());
	}

	@Override
	public final Matrix33d 		minus(final double _t) {
		return new Matrix33d(m00 - _t, m01 - _t, m02 - _t,
							 m10 - _t, m11 - _t, m12 - _t,
							 m20 - _t, m21 - _t, m22 - _t);
	}
	@Override
	public final Matrix33d 		minus(final DoubleMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return new Matrix33d(m00 - _m.get(0,0), m01 - _m.get(0,1), m02 - _m.get(0,2),
							 m10 - _m.get(1,0), m11 - _m.get(1,1), m12 - _m.get(1,2),
							 m20 - _m.get(2,0), m21 - _m.get(2,1), m22 - _m.get(2,2));
	}

	@Override
	public final Matrix33d 		minus(final Matrix33D _m) {
		return new Matrix33d(m00 - _m.get(0,0), m01 - _m.get(0,1), m02 - _m.get(0,2),
							 m10 - _m.get(1,0), m11 - _m.get(1,1), m12 - _m.get(1,2),
							 m20 - _m.get(2,0), m21 - _m.get(2,1), m22 - _m.get(2,2));
	}

	@Override
	public final Matrix33d 		minusEquals(final Number _t) {
		m00 -= _t.doubleValue(); m01 -= _t.doubleValue(); m02 -= _t.doubleValue();
		m10 -= _t.doubleValue(); m11 -= _t.doubleValue(); m12 -= _t.doubleValue();
		m20 -= _t.doubleValue(); m21 -= _t.doubleValue(); m22 -= _t.doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		minusEquals(final NumberMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		m00 -= _m.getNumber(0,0).doubleValue(); m01 -= _m.getNumber(0,1).doubleValue(); m02 -= _m.getNumber(0,2).doubleValue();
		m10 -= _m.getNumber(1,0).doubleValue(); m11 -= _m.getNumber(1,1).doubleValue(); m12 -= _m.getNumber(1,2).doubleValue();
		m20 -= _m.getNumber(2,0).doubleValue(); m21 -= _m.getNumber(2,1).doubleValue(); m22 -= _m.getNumber(2,2).doubleValue();
		return this;
	}

	@Override
	public final Matrix33d 		minusEquals(final double _t) {
		m00 -= _t; m01 -= _t; m02 -= _t;
		m10 -= _t; m11 -= _t; m12 -= _t;
		m20 -= _t; m21 -= _t; m22 -= _t;
		return this;
	}
	@Override
	public final Matrix33d 		minusEquals(final DoubleMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m02 -= _m.get(0,2);
		m10 -= _m.get(1,0); m11 -= _m.get(1,1); m12 -= _m.get(1,2);
		m20 -= _m.get(2,0); m21 -= _m.get(2,1); m22 -= _m.get(2,2);
		return this;
	}

	@Override
	public final Matrix33d 		minusEquals(final Matrix33D _m) {
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m02 -= _m.get(0,2);
		m10 -= _m.get(1,0); m11 -= _m.get(1,1); m12 -= _m.get(1,2);
		m20 -= _m.get(2,0); m21 -= _m.get(2,1); m22 -= _m.get(2,2);
		return this;
	}

	@Override
	public final Matrix33d 		times(final Number _v) {
		return new Matrix33d(
							m00*_v.doubleValue(), m01*_v.doubleValue(), m02*_v.doubleValue(),
							m10*_v.doubleValue(), m11*_v.doubleValue(), m12*_v.doubleValue(),
							m20*_v.doubleValue(), m21*_v.doubleValue(), m22*_v.doubleValue()
						  );
	}
	@Override
	public final DoubleVector3D 		times(final NumberVector _v) {
		assert(_v.size() == 3);
		return new DoubleVector3D(
							m00*_v.getNumber(0).doubleValue() + m01*_v.getNumber(1).doubleValue() + m02*_v.getNumber(2).doubleValue(),
							m10*_v.getNumber(0).doubleValue() + m11*_v.getNumber(1).doubleValue() + m12*_v.getNumber(2).doubleValue(),
							m20*_v.getNumber(0).doubleValue() + m21*_v.getNumber(1).doubleValue() + m22*_v.getNumber(2).doubleValue()
						  );
	}
	@Override
	public final NumberMatrix 		times(final NumberMatrix _m) {
		assert(_m.rows() == 3);
		
		Matrixmnd result = new Matrixmnd(3, _m.columns());
		for(int i = 0; i < _m.columns(); ++i) {
			result.set(0, i, m00*_m.getNumber(0,i).doubleValue() + m01*_m.getNumber(1,i).doubleValue() + m02*_m.getNumber(2,i).doubleValue());
			result.set(1, i, m10*_m.getNumber(0,i).doubleValue() + m11*_m.getNumber(1,i).doubleValue() + m12*_m.getNumber(2,i).doubleValue());
			result.set(2, i, m20*_m.getNumber(0,i).doubleValue() + m21*_m.getNumber(1,i).doubleValue() + m22*_m.getNumber(2,i).doubleValue());
		}
		return result;
	}

	@Override
	public final Matrix33d 		times(final double _v) {
		return new Matrix33d(
							m00*_v, m01*_v, m02*_v,
							m10*_v, m11*_v, m12*_v,
							m20*_v, m21*_v, m22*_v
						  );
	}
	@Override
	public final DoubleVector3D 		times(final DoubleVector _v) {
		assert(_v.size() == 3);
		return new DoubleVector3D(
							m00*_v.get(0) + m01*_v.get(1) + m02*_v.get(2),
							m10*_v.get(0) + m11*_v.get(1) + m12*_v.get(2),
							m20*_v.get(0) + m21*_v.get(1) + m22*_v.get(2)
						  );
	}
	@Override
	public final Matrixmnd 		times(final DoubleMatrix _m) {
		assert(_m.rows() == 3);
		
		Matrixmnd result = new Matrixmnd(3, _m.columns());
		for(int i = 0; i < _m.columns(); ++i) {
			result.set(0, i, m00*_m.get(0,i) + m01*_m.get(1,i) + m02*_m.get(2,i));
			result.set(1, i, m10*_m.get(0,i) + m11*_m.get(1,i) + m12*_m.get(2,i));
			result.set(2, i, m20*_m.get(0,i) + m21*_m.get(1,i) + m22*_m.get(2,i));
		}
		return result;
/*
		return new Matrix33d(
									m00*_m.get(0,0) + m01*_m.get(1,0) + m02*_m.get(2,0),
									m00*_m.get(0,1) + m01*_m.get(1,1) + m02*_m.get(2,1),
									m00*_m.get(0,2) + m01*_m.get(1,2) + m02*_m.get(2,2),
									m10*_m.get(0,0) + m11*_m.get(1,0) + m12*_m.get(2,0),
									m10*_m.get(0,1) + m11*_m.get(1,1) + m12*_m.get(2,1),
									m10*_m.get(0,2) + m11*_m.get(1,2) + m12*_m.get(2,2),
									m20*_m.get(0,0) + m21*_m.get(1,0) + m22*_m.get(2,0),
									m20*_m.get(0,1) + m21*_m.get(1,1) + m22*_m.get(2,1),
									m20*_m.get(0,2) + m21*_m.get(1,2) + m22*_m.get(2,2)
								 );
*/
	}
	@Override
	public final DoubleVector3D 		times(final Vector3D _v) {
		return new DoubleVector3D(
							m00*_v.getX() + m01*_v.getY() + m02*_v.getZ(),
							m10*_v.getX() + m11*_v.getY() + m12*_v.getZ(),
							m20*_v.getX() + m21*_v.getY() + m22*_v.getZ()
						  );
	}
	@Override
	public final Matrix33d 		times(final Matrix33D _d) {
		return new Matrix33d(
									m00*_d.get(0,0) + m01*_d.get(1,0) + m02*_d.get(2,0),
									m00*_d.get(0,1) + m01*_d.get(1,1) + m02*_d.get(2,1),
									m00*_d.get(0,2) + m01*_d.get(1,2) + m02*_d.get(2,2),
									m10*_d.get(0,0) + m11*_d.get(1,0) + m12*_d.get(2,0),
									m10*_d.get(0,1) + m11*_d.get(1,1) + m12*_d.get(2,1),
									m10*_d.get(0,2) + m11*_d.get(1,2) + m12*_d.get(2,2),
									m20*_d.get(0,0) + m21*_d.get(1,0) + m22*_d.get(2,0),
									m20*_d.get(0,1) + m21*_d.get(1,1) + m22*_d.get(2,1),
									m20*_d.get(0,2) + m21*_d.get(1,2) + m22*_d.get(2,2)
								 );
	}

	@Override
	public final Matrix33d 		timesEquals(final Number _v) {
		m00*=_v.doubleValue(); m01*=_v.doubleValue(); m02*=_v.doubleValue();
		m10*=_v.doubleValue(); m11*=_v.doubleValue(); m12*=_v.doubleValue();
		m20*=_v.doubleValue(); m21*=_v.doubleValue(); m22*=_v.doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		timesEquals(final double _v) {
		m00*=_v; m01*=_v; m02*=_v;
		m10*=_v; m11*=_v; m12*=_v;
		m20*=_v; m21*=_v; m22*=_v;
		return this;
	}
	@Override
	public final Matrix33d 		timesEquals(NumberMatrix _m) {
		m00*=_m.getNumber(0,0).doubleValue(); m01*=_m.getNumber(0,1).doubleValue(); m02*=_m.getNumber(0,2).doubleValue();
		m10*=_m.getNumber(0,0).doubleValue(); m11*=_m.getNumber(0,1).doubleValue(); m12*=_m.getNumber(0,2).doubleValue();
		m20*=_m.getNumber(0,0).doubleValue(); m21*=_m.getNumber(0,1).doubleValue(); m22*=_m.getNumber(0,2).doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		timesEquals(DoubleMatrix _m) {
		m00*=_m.getNumber(0,0).doubleValue(); m01*=_m.getNumber(0,1).doubleValue(); m02*=_m.getNumber(0,2).doubleValue();
		m10*=_m.getNumber(0,0).doubleValue(); m11*=_m.getNumber(0,1).doubleValue(); m12*=_m.getNumber(0,2).doubleValue();
		m20*=_m.getNumber(0,0).doubleValue(); m21*=_m.getNumber(0,1).doubleValue(); m22*=_m.getNumber(0,2).doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		timesEquals(final SquareMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);

		throw new NotYetImplementedException();
//		return this;
	}

	@Override
	public final Matrix33d 		timesEquals(final Matrix33D _d) {
		set(times(_d));
		return this;
	}

	@Override
	public final Matrix33d 		arrayTimes(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(m00*B.getNumber(0, 0).doubleValue(), m01*B.getNumber(0, 1).doubleValue(), m02*B.getNumber(0, 2).doubleValue(),
							 m10*B.getNumber(1, 0).doubleValue(), m11*B.getNumber(1, 1).doubleValue(), m12*B.getNumber(1, 2).doubleValue(),
							 m20*B.getNumber(2, 0).doubleValue(), m21*B.getNumber(2, 1).doubleValue(), m22*B.getNumber(2, 2).doubleValue()
						 	);
	}
	@Override
	public final Matrix33d 		arrayTimes(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(m00*B.get(0, 0), m01*B.get(0, 1), m02*B.get(0, 2),
							 m10*B.get(1, 0), m11*B.get(1, 1), m12*B.get(1, 2),
							 m20*B.get(2, 0), m21*B.get(2, 1), m22*B.get(2, 2)
						 	);
	}

	@Override
	public final Matrix33d 		arrayTimesEquals(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 *= B.getNumber(0, 0).doubleValue();
		m01 *= B.getNumber(0, 1).doubleValue();
		m02 *= B.getNumber(0, 2).doubleValue();
		m10 *= B.getNumber(1, 0).doubleValue();
		m11 *= B.getNumber(1, 1).doubleValue();
		m12 *= B.getNumber(1, 2).doubleValue();
		m20 *= B.getNumber(2, 0).doubleValue();
		m21 *= B.getNumber(2, 1).doubleValue();
		m22 *= B.getNumber(2, 2).doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		arrayTimesEquals(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 *= B.get(0, 0);
		m01 *= B.get(0, 1);
		m02 *= B.get(0, 2);
		m10 *= B.get(1, 0);
		m11 *= B.get(1, 1);
		m12 *= B.get(1, 2);
		m20 *= B.get(2, 0);
		m21 *= B.get(2, 1);
		m22 *= B.get(2, 2);
		return this;
	}

	@Override
	public final Matrix33d 		arrayRightDivide(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(m00/B.getNumber(0, 0).doubleValue(), m01/B.getNumber(0, 1).doubleValue(), m02/B.getNumber(0, 2).doubleValue(),
							 m10/B.getNumber(1, 0).doubleValue(), m11/B.getNumber(1, 1).doubleValue(), m12/B.getNumber(1, 2).doubleValue(),
							 m20/B.getNumber(2, 0).doubleValue(), m21/B.getNumber(2, 1).doubleValue(), m22/B.getNumber(2, 2).doubleValue()
						 	);
	}
	@Override
	public final Matrix33d 		arrayRightDivide(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(m00/B.get(0, 0), m01/B.get(0, 1), m02/B.get(0, 2),
							 m10/B.get(1, 0), m11/B.get(1, 1), m12/B.get(1, 2),
							 m20/B.get(2, 0), m21/B.get(2, 1), m22/B.get(2, 2)
						 	);
	}

	@Override
	public final Matrix33d 		arrayRightDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 /= B.getNumber(0, 0).doubleValue();
		m01 /= B.getNumber(0, 1).doubleValue();
		m02 /= B.getNumber(0, 2).doubleValue();
		m10 /= B.getNumber(1, 0).doubleValue();
		m11 /= B.getNumber(1, 1).doubleValue();
		m12 /= B.getNumber(1, 2).doubleValue();
		m20 /= B.getNumber(2, 0).doubleValue();
		m21 /= B.getNumber(2, 1).doubleValue();
		m22 /= B.getNumber(2, 2).doubleValue();
		return this;
	}
	@Override
	public final Matrix33d 		arrayRightDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 /= B.get(0, 0);
		m01 /= B.get(0, 1);
		m02 /= B.get(0, 2);
		m10 /= B.get(1, 0);
		m11 /= B.get(1, 1);
		m12 /= B.get(1, 2);
		m20 /= B.get(2, 0);
		m21 /= B.get(2, 1);
		m22 /= B.get(2, 2);
		return this;
	}
	
	@Override
	public final Matrix33d 		arrayLeftDivide(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(B.getNumber(0, 0).doubleValue()/m00, B.getNumber(0, 1).doubleValue()/m01, B.getNumber(0, 2).doubleValue()/m02,
							 B.getNumber(1, 0).doubleValue()/m10, B.getNumber(1, 1).doubleValue()/m11, B.getNumber(1, 2).doubleValue()/m12,
							 B.getNumber(2, 0).doubleValue()/m20, B.getNumber(2, 1).doubleValue()/m21, B.getNumber(2, 2).doubleValue()/m22
						 	);
	}
	@Override
	public final Matrix33d 		arrayLeftDivide(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		return new Matrix33d(B.get(0, 0)/m00, B.get(0, 1)/m01, B.get(0, 2)/m02,
							 B.get(1, 0)/m10, B.get(1, 1)/m11, B.get(1, 2)/m12,
							 B.get(2, 0)/m20, B.get(2, 1)/m21, B.get(2, 2)/m22
						 	);
	}

	@Override
	public final Matrix33d 		arrayLeftDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 = B.getNumber(0, 0).doubleValue() / m00;
		m01 = B.getNumber(0, 1).doubleValue() / m01;
		m02 = B.getNumber(0, 2).doubleValue() / m02;
		m10 = B.getNumber(1, 0).doubleValue() / m10;
		m11 = B.getNumber(1, 1).doubleValue() / m11;
		m12 = B.getNumber(1, 2).doubleValue() / m12;
		m20 = B.getNumber(2, 0).doubleValue() / m20;
		m21 = B.getNumber(2, 1).doubleValue() / m21;
		m22 = B.getNumber(2, 2).doubleValue() / m22;
		return this;
	}
	@Override
	public final Matrix33d 		arrayLeftDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 3 && B.columns() == 3);
		m00 = B.get(0, 0) / m00;
		m01 = B.get(0, 1) / m01;
		m02 = B.get(0, 2) / m02;
		m10 = B.get(1, 0) / m10;
		m11 = B.get(1, 1) / m11;
		m12 = B.get(1, 2) / m12;
		m20 = B.get(2, 0) / m20;
		m21 = B.get(2, 1) / m21;
		m22 = B.get(2, 2) / m22;
		return this;
	}

	@Override
	public final boolean 		isValid() {
		return false;
	}

	@Override
	public final boolean 		isEqual(final double _d) {
		return (m00 == _d && m01 == _d && m10 == _d && m11 == _d) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final NumberMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return (m00 == _m.getNumber(0,0).doubleValue() && m01 == _m.getNumber(0,1).doubleValue() && m02 == _m.getNumber(0,2).doubleValue() 
				&& m10 == _m.getNumber(1,0).doubleValue() && m11 == _m.getNumber(1,1).doubleValue() && m12 == _m.getNumber(1,2).doubleValue()
				&& m20 == _m.getNumber(2,0).doubleValue() && m21 == _m.getNumber(2,1).doubleValue() && m22 == _m.getNumber(2,2).doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final DoubleMatrix _m) {
		assert(_m.rows() == 3 && _m.columns() == 3);
		return (m00 == _m.get(0,0) && m01 == _m.get(0,1) && m02 == _m.get(0,2) 
				&& m10 == _m.get(1,0) && m11 == _m.get(1,1) && m12 == _m.get(1,2)
				&& m20 == _m.get(2,0) && m21 == _m.get(2,1) && m22 == _m.get(2,2)) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final Matrix33D _m) {
		return (m00 == _m.get(0,0) && m01 == _m.get(0,1) && m02 == _m.get(0,2)
				&& m10 == _m.get(1,0) && m11 == _m.get(1,1) && m12 == _m.get(1,2)
				&& m20 == _m.get(2,0) && m21 == _m.get(2,1) && m22 == _m.get(2,2)) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _d) {
		return (m00 != _d || m01 != _d || m02 != _d || m10 != _d || m11 != _d || m12 != _d || m20 != _d || m21 != _d || m22 != _d) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberMatrix _m) {
		return (m00 != _m.getNumber(0,0).doubleValue() || m01 != _m.getNumber(0,1).doubleValue() || m02 != _m.getNumber(0,2).doubleValue() || m10 != _m.getNumber(1,0).doubleValue() || m11 != _m.getNumber(1,1).doubleValue() || m12 != _m.getNumber(1,2).doubleValue() || m20 != _m.getNumber(2,0).doubleValue() || m21 != _m.getNumber(2,1).doubleValue() || m22 != _m.getNumber(2,2).doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleMatrix _m) {
		return (m00 != _m.get(0,0) || m01 != _m.get(0,1) || m02 != _m.get(0,2) || m10 != _m.get(1,0) || m11 != _m.get(1,1) || m12 != _m.get(1,2) || m20 != _m.get(2,0) || m21 != _m.get(2,1) || m22 != _m.get(2,2)) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Matrix33D _m) {
		return (m00 != _m.get(0,0) || m01 != _m.get(0,1) || m02 != _m.get(0,2) || m10 != _m.get(1,0) || m11 != _m.get(1,1) || m12 != _m.get(1,2) || m20 != _m.get(2,0) || m21 != _m.get(2,1) || m22 != _m.get(2,2)) ? true : false;
	}

	@Override
	public final Matrix33d 		clone() {
		return new Matrix33d(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	@Override
	public final Matrix33d 		abs() {
		return new Matrix33d(Math.abs(m00), Math.abs(m01), Math.abs(m02), Math.abs(m10), Math.abs(m11), Math.abs(m12), Math.abs(m20), Math.abs(m21), Math.abs(m22));
	}
	@Override
	public final Matrix33d 		inverse() {
		double det = det();
		if(det != 0.0f)
			det = 1.0f / det;

		double a11 = m00, a12 = m01, a13 = m20;
		double a22 = m11, a23 = m21;
		double a33 = m22;

		return new Matrix33d(  	det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),

								det * (a13 * a23 - a12 * a33),
								det * (a11 * a33 - a13 * a13),
								det * (a13 * a12 - a11 * a23),

								det * (a12 * a23 - a13 * a22),
								det * (a13 * a12 - a11 * a23),
								det * (a11 * a22 - a12 * a12)
							);
	}
	@Override
	public final Matrix33d 		transpose() {
		return new Matrix33d(m00, m10, m20, m01, m11, m21, m02, m12, m22);
	}
	@Override
	public final Matrix33d 		uminus() {
		return new Matrix33d(-m00, -m01, -m02, -m10, -m11, -m12, -m20, -m21, -m22);
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
	public final double 		det() {
		double bx = m11 * m22 - m12 * m21;
		double by = m12 * m20 - m10 * m22;
		double bz = m10 * m21 - m11 * m20;
		
		return m00 * bx + m10 * by + m20 * bz;
	}
	@Override
	public final double 		trace() {
		return m00 + m11 + m22;
	}
	@Override
	public final double 		norm1() {
		double norm1 = Math.max(Math.abs(m00) + Math.abs(m10) + Math.abs(m20), Math.abs(m01) + Math.abs(m11) + Math.abs(m21));
		return Math.max(norm1, Math.abs(m02) + Math.abs(m12) + Math.abs(m22));
	}
	@Override
	public final double 		norm2() {
		return new SingularValueDecomposition(this).norm2();
	}
	@Override
	public final double 		normInf() {
		double normInf = Math.max(Math.abs(m00) + Math.abs(m01) + Math.abs(m02), Math.abs(m10) + Math.abs(m11) + Math.abs(m12));
		return Math.max(normInf, Math.abs(m20) + Math.abs(m21) + Math.abs(m22));
	}
	@Override
	public final double 		normF() {
		double f = 0;
		f = Numbers.hypot(f, m00);
		f = Numbers.hypot(f, m01);
		f = Numbers.hypot(f, m02);
		f = Numbers.hypot(f, m10);
		f = Numbers.hypot(f, m11);
		f = Numbers.hypot(f, m12);
		f = Numbers.hypot(f, m20);
		f = Numbers.hypot(f, m21);
		f = Numbers.hypot(f, m22);
		return f;
	}

	@Override
	public final String 		toString() {
		return "[" + m00 + " " + m01 + " " + m02 + ",\n " + m10 + " " + m11 + " " + m12 + ",\n " + m20 + " " + m21 + " " + m22 + "]";
	}
	@Override
	public final String 		toString(final NumberFormat _nf) {
		return "[" + _nf.format(m00) + " " + _nf.format(m01) + " " + _nf.format(m02) + ",\n " + _nf.format(m10) + " " + _nf.format(m11) + " " + _nf.format(m12) + ",\n " + _nf.format(m20) + " " + _nf.format(m21) + " " + _nf.format(m22) + "]";
	}

	public final Vector3D 		solve(final Vector3D b) {
		Vector3D X   = Vectors.crossProduct(getColumn(1), getColumn(2));
		double   det = Vectors.dotProduct(getColumn(0), X);
		if(det == 0.0f)
			return null;

		det = 1.0f / det;
		X = Vectors.crossProduct(getColumn(1), getColumn(2));
		final double x = det * Vectors.dotProduct(b, X);
		X = Vectors.crossProduct(b, getColumn(2));
		final double y = det * Vectors.dotProduct(getColumn(0), X);
		X = Vectors.crossProduct(getColumn(1), b);
		final double z = det * Vectors.dotProduct(getColumn(0), X);

		return Vectors.of(x, y, z);
	}

	@Override
	public boolean equals(Object _obj) {
		if(this == _obj)
			return true;
		if(_obj == null)
			return false;
		if(getClass() != _obj.getClass())
			return false;
		Matrix33d other = (Matrix33d) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return false;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return false;
		if(Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02))
			return false;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return false;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return false;
		if(Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
			return false;
		if(Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20))
			return false;
		if(Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
			return false;
		if(Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
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
		Matrix33d other = (Matrix33d) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return Double.doubleToLongBits(m00) > Double.doubleToLongBits(other.m00) ? 1 : -1;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return Double.doubleToLongBits(m01) > Double.doubleToLongBits(other.m01) ? 1 : -1;
		if(Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02))
			return Double.doubleToLongBits(m02) > Double.doubleToLongBits(other.m02) ? 1 : -1;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return Double.doubleToLongBits(m10) > Double.doubleToLongBits(other.m10) ? 1 : -1;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return Double.doubleToLongBits(m11) > Double.doubleToLongBits(other.m11) ? 1 : -1;
		if(Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12))
			return Double.doubleToLongBits(m12) > Double.doubleToLongBits(other.m12) ? 1 : -1;
		if(Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20))
			return Double.doubleToLongBits(m20) > Double.doubleToLongBits(other.m20) ? 1 : -1;
		if(Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21))
			return Double.doubleToLongBits(m21) > Double.doubleToLongBits(other.m21) ? 1 : -1;
		if(Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22))
			return Double.doubleToLongBits(m22) > Double.doubleToLongBits(other.m22) ? 1 : -1;
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn(0).hashCode();
		result = prime * result + getColumn(1).hashCode();
	    result = prime * result + getColumn(2).hashCode();
		return result;
	}
/*
	@Override
	public final double[][] getStorage() {
		throw new IllegalAccessError();
	}
	@Override
	public final double[][] getStorageCopy() {
		return new double[][] { { m00, m01, m02 }, { m10, m11, m12 }, { m20, m21, m22 } };
	}
	@Override
	public final double[] 	getColumnPackedCopy() {
		return new double[] { m00, m01, m02, m10, m11, m12, m20, m21, m22 };
	}
	@Override
	public final double[] 	getRowPackedCopy() {
		return new double[] { m00, m10, m20, m01, m11, m21, m02, m12, m22 };
	}
*/
}
