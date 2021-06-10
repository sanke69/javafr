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
import fr.java.math.algebra.matrix.SquareMatrix;
import fr.java.math.algebra.matrix.specials.Matrix22D;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.matrices.doubles.decompositions.SingularValueDecomposition;
import fr.java.maths.algebra.vectors.DoubleVector2D;

public class Matrix22d implements Matrix22D.Editable {
	private static final long serialVersionUID = 1L;

	public final static Matrix22d zero() {
		return new Matrix22d(0,0,0,0);
	}
	public final static Matrix22d identity() {
		return new Matrix22d(1,0,0,1);
	}
	public final static Matrix22d unitary() {
		return new Matrix22d(1,1,1,1);
	}

	public double	m00, m01, 
					m10, m11;

	public Matrix22d() {
	}
	public Matrix22d(final double _m00, final double _m01, final double _m10, final double _m11) {
		m00 = _m00; m01 = _m01; m10 = _m10; m11 = _m11;
	}
	public Matrix22d(final Matrix22d _d) {
		m00 = _d.m00; m01 = _d.m01; m10 = _d.m10; m11 = _d.m11;
	}

	// TENSOR METHODS
	@Override
	public final boolean 		isDirect() { return false; }
	@Override
	public final double[] 		getArray() { return new double[] { m00, m01, m10, m11 }; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
    public final DoubleStream 	getStream() { return DoubleStream.of(m00, m01, m10, m11); }

	@Override
	public final Number 		getNumber(final int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}
	@Override
	public final Number 		getNumber(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else
				return m01;
		} else {
			if(_j == 0)
				return m10;
			else
				return m11;
		}
	}
	@Override
	public final void 			setNumber(final int _i, final int _j, final Number _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value.doubleValue();
			else
				m01 = _value.doubleValue();
		} else {
			if(_j == 0)
				m10 = _value.doubleValue();
			else
				m11 = _value.doubleValue();
		}
	}

	@Override
	public double 				getValue(final int _index) {
		switch((int) _index) {
		case 0  : return m00;
		case 1  : return m01;
		case 2  : return m10;
		case 3  : return m11;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 2);
		
		if(_coords[0] > 2 || _coords[1] > 2)
			throw new IndexOutOfBoundsException();

		if(_coords[1] == 0) {
			if(_coords[0] == 0)
				return m00;
			else
				return m01;
		} else {
			if(_coords[0] == 0)
				return m10;
			else
				return m11;
		}
	}
	@Override
	public void 				setValue(final double _value, int _index) {
		switch((int) _index) {
		case 0  : m00 = _value;
		case 1  : m01 = _value;
		case 2  : m10 = _value;
		case 3  : m11 = _value;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 2);
		
		if(_coords[0] > 2 || _coords[1] > 2)
			throw new IndexOutOfBoundsException();

		if(_coords[1] == 0) {
			if(_coords[0] == 0)
				m00 = _value;
			else
				m01 = _value;
		} else {
			if(_coords[0] == 0)
				m10 = _value;
			else
				m11 = _value;
		}
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
	public final void			setSlice(final DoubleTensor _tensor, final int... _slice) {
		throw new NotYetImplementedException();
	}

	@Override
	public final void 			reshape(final int... _shape) {
		throw new IllegalArgumentException("Can't reshape a Matrix 2x2");
	}

	// MATRIX METHODS
	public final int 			columns() { return 2; }
	public final int 			rows() { return 2; }


	@Override
	public final double 		get(final int _i, final int _j) {
		if(_i == 0) {
			if(_j == 0)
				return m00;
			else
				return m01;
		} else {
			if(_j == 0)
				return m10;
			else
				return m11;
		}
	}
	@Override
	public final void 			set(final int _i, final int _j, final double _value) {
		if(_i == 0) {
			if(_j == 0)
				m00 = _value;
			else
				m01 = _value;
		} else {
			if(_j == 0)
				m10 = _value;
			else
				m11 = _value;
		}
	}

	public final DoubleVector 	getRow(final int _i) {
		if(_i > 2)
			throw new ArrayIndexOutOfBoundsException();
		return (_i == 0) ? new DoubleVector2D(m00, m01) : new DoubleVector2D(m10, m11);
	}
	public final DoubleVector 	getColumn(final int _j) {
		if(_j > 2)
			throw new ArrayIndexOutOfBoundsException();
		return (_j == 0) ? new DoubleVector2D(m00, m10) : new DoubleVector2D(m01, m11);
	}

	@Deprecated
	public final void 			set(final double _t) {
		m00 = _t; m01 = _t; m10 = _t; m11 = _t;
	}
	@Deprecated
	public final void 			set(final double _m00, final double _m01, final double _m10, final double _m11) {
		m00 = _m00; m01 = _m01; m10 = _m10; m11 = _m11;
	}
	@Deprecated
	public final void 			set(final Vector2D _col0, final Vector2D _col1) {
		m00 = _col0.getX(); m01 = _col1.getX(); m10 = _col0.getY(); m11 = _col1.getY();
	}
	@Deprecated
	public final void 			set(final Matrix22D _m) {
		m00 = _m.get(0,0); m01 = _m.get(0,1); m10 = _m.get(1,0); m11 = _m.get(1,1);
	}

	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int _j0, final int _j1, final NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(final int _i0, final int _i1, final int _j0, final int _j1) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(final int[] _r, final int[] _c, final NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(final int[] _r, final int[] _c) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int[] _c, final NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(final int _i0, final int _i1, final int[] _c) {
		throw new IllegalAccessError();
	}
	@Override
	public final void 			setMatrix(final int[] _r, final int _j0, final int _j1, final NumberMatrix _X) {
		throw new IllegalAccessError();
	}
	@Override
	public final NumberMatrix 		getMatrix(final int[] _r, final int _j0, final int _j1) {
		throw new IllegalAccessError();
	}

	public final Matrix22d 		plus(final Number _t) {
		return new Matrix22d(m00 + _t.doubleValue(), m01 + _t.doubleValue(), m10 + _t.doubleValue(), m11 + _t.doubleValue());
	}
	public final Matrix22d 		plus(final NumberMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(m00 + _m.getNumber(0,0).doubleValue(), m01 + _m.getNumber(0,1).doubleValue(), m10 + _m.getNumber(1,0).doubleValue(), m11 + _m.getNumber(1,1).doubleValue());
	}

	public final Matrix22d 		plus(final double _t) {
		return new Matrix22d(m00 + _t, m01 + _t, m10 + _t, m11 + _t);
	}
	public final Matrix22d 		plus(final DoubleMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(m00 + _m.get(0,0), m01 + _m.get(0,1), m10 + _m.get(1,0), m11 + _m.get(1,1));
	}

	public final Matrix22d 		plus(final Matrix22D _m) {
		return new Matrix22d(m00 + _m.get(0,0), m01 + _m.get(0,1), m10 + _m.get(1,0), m11 + _m.get(1,1));
	}

	public final Matrix22d 		plusEquals(final Number _t) {
		m00 += _t.doubleValue(); m01 += _t.doubleValue(); m10 += _t.doubleValue(); m11 += _t.doubleValue();
		return this;
	}
	public final Matrix22d 		plusEquals(final NumberMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		m00 += _m.getNumber(0,0).doubleValue(); m01 += _m.getNumber(0,1).doubleValue(); m10 += _m.getNumber(1,0).doubleValue(); m11 += _m.getNumber(1,1).doubleValue();
		return this;
	}

	public final Matrix22d 		plusEquals(final double _t) {
		m00 += _t; m01 += _t; m10 += _t; m11 += _t;
		return this;
	}
	public final Matrix22d 		plusEquals(final DoubleMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		m00 += _m.get(0,0); m01 += _m.get(0,1); m10 += _m.get(1,0); m11 += _m.get(1,1);
		return this;
	}
	
	public final Matrix22d 		plusEquals(final Matrix22D _m) {
		m00 += _m.get(0,0); m01 += _m.get(0,1); m10 += _m.get(1,0); m11 += _m.get(1,1);
		return this;
	}

	public final Matrix22d 		minus(final Number _t) {
		return new Matrix22d(m00 - _t.doubleValue(), m01 - _t.doubleValue(), m10 - _t.doubleValue(), m11 - _t.doubleValue());
	}
	public final Matrix22d 		minus(final NumberMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(m00 - _m.getNumber(0,0).doubleValue(), m01 - _m.getNumber(0,1).doubleValue(), m10 - _m.getNumber(1,0).doubleValue(), m11 - _m.getNumber(1,1).doubleValue());
	}

	public final Matrix22d 		minus(final double _t) {
		return new Matrix22d(m00 - _t, m01 - _t, m10 - _t, m11 - _t);
	}
	public final Matrix22d 		minus(final DoubleMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(m00 - _m.get(0,0), m01 - _m.get(0,1), m10 - _m.get(1,0), m11 - _m.get(1,1));
	}
	
	public final Matrix22d 		minus(final Matrix22D _m) {
		return new Matrix22d(m00 - _m.get(0,0), m01 - _m.get(0,1), m10 - _m.get(1,0), m11 - _m.get(1,1));
	}

	public final Matrix22d 		minusEquals(final Number _t) {
		m00 -= _t.doubleValue(); m01 -= _t.doubleValue(); m10 -= _t.doubleValue(); m11 -= _t.doubleValue();
		return this;
	}
	public final Matrix22d 		minusEquals(final NumberMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		m00 -= _m.getNumber(0,0).doubleValue(); m01 -= _m.getNumber(0,1).doubleValue(); m10 -= _m.getNumber(1,0).doubleValue(); m11 -= _m.getNumber(1,1).doubleValue();
		return this;
	}
	
	public final Matrix22d 		minusEquals(final double _t) {
		m00 -= _t; m01 -= _t; m10 -= _t; m11 -= _t;
		return this;
	}
	public final Matrix22d 		minusEquals(final DoubleMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m10 -= _m.get(1,0); m11 -= _m.get(1,1);
		return this;
	}

	public final Matrix22d 		minusEquals(final Matrix22D _m) {
		m00 -= _m.get(0,0); m01 -= _m.get(0,1); m10 -= _m.get(1,0); m11 -= _m.get(1,1);
		return this;
	}

	public final Matrix22d 		times(final Number _t) {
		return new Matrix22d(m00 * _t.doubleValue(), m01 * _t.doubleValue(), m10 * _t.doubleValue(), m11 * _t.doubleValue());
	}
	public final DoubleVector2D 		times(final NumberVector _v) {
		assert(_v.size() == 2);
		return new DoubleVector2D(m00 * _v.getNumber(0).doubleValue() + m01 * _v.getNumber(1).doubleValue(), m10 * _v.getNumber(0).doubleValue() + m11 * _v.getNumber(1).doubleValue());
	}
	public final Matrix22d 		times(final NumberMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(	m00*_m.getNumber(0,0).doubleValue() + m01*_m.getNumber(1,0).doubleValue(),
								m00*_m.getNumber(0,1).doubleValue() + m01*_m.getNumber(1,1).doubleValue(),
								m10*_m.getNumber(0,0).doubleValue() + m11*_m.getNumber(1,0).doubleValue(),
								m10*_m.getNumber(0,1).doubleValue() + m11*_m.getNumber(1,1).doubleValue()
							 );
	}

	public final Matrix22d 		times(final double _t) {
		return new Matrix22d(m00 * _t, m01 * _t, m10 * _t, m11 * _t);
	}
	public final DoubleVector2D 		times(final DoubleVector _v) {
		assert(_v.size() == 2);
		return new DoubleVector2D(m00 * _v.get(0) + m01 * _v.get(1), m10 * _v.get(0) + m11 * _v.get(1));
	}
	public final Matrix22d 		times(final DoubleMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(	m00*_m.get(0,0) + m01*_m.get(1,0),
								m00*_m.get(0,1) + m01*_m.get(1,1),
								m10*_m.get(0,0) + m11*_m.get(1,0),
								m10*_m.get(0,1) + m11*_m.get(1,1)
							 );
	}
	public final Matrix22d 		times(final SquareMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		return new Matrix22d(	m00*_m.getNumber(0,0).doubleValue() + m01*_m.getNumber(1,0).doubleValue(),
								m00*_m.getNumber(0,1).doubleValue() + m01*_m.getNumber(1,1).doubleValue(),
								m10*_m.getNumber(0,0).doubleValue() + m11*_m.getNumber(1,0).doubleValue(),
								m10*_m.getNumber(0,1).doubleValue() + m11*_m.getNumber(1,1).doubleValue()
							 );
	}
	
	public final DoubleVector2D 		times(final Vector2D _v) {
		return new DoubleVector2D(m00 * _v.getX() + m01 * _v.getY(), m10 * _v.getX() + m11 * _v.getY());
	}
	public final Matrix22d 		times(final Matrix22D _m) {
		return new Matrix22d(	m00*_m.get(0,0) + get(0,1)*_m.get(1,0),
								m00*_m.get(0,1) + get(0,1)*_m.get(1,1),
								m10*_m.get(0,0) + m11*_m.get(1,0),
								m10*_m.get(0,1) + m11*_m.get(1,1)
							 );
	}


	public final Matrix22d 		timesEquals(final Number _t) {
		m00 *= _t.doubleValue(); m01 *= _t.doubleValue(); m10 *= _t.doubleValue(); m11 *= _t.doubleValue();
		return this;
	}
	public final Matrix22d 		timesEquals(final double _t) {
		m00 *= _t; m01 *= _t; m10 *= _t; m11 *= _t;
		return this;
	}
	@Override
	public final Matrix22d 		timesEquals(NumberMatrix _m) {
		m00*=_m.getNumber(0,0).doubleValue(); m01*=_m.getNumber(0,1).doubleValue();
		m10*=_m.getNumber(0,0).doubleValue(); m11*=_m.getNumber(0,1).doubleValue();
		return this;
	}
	@Override
	public final Matrix22d 		timesEquals(DoubleMatrix _m) {
		m00*=_m.get(0,0); m01*=_m.get(0,1);
		m10*=_m.get(0,0); m11*=_m.get(0,1);
		return this;
	}
	public final Matrix22d 		timesEquals(final SquareMatrix _m) {
		assert(_m.rows() == 2 && _m.columns() == 2);
		set(
				m00*_m.getNumber(0,0).doubleValue() + m01*_m.getNumber(1,0).doubleValue(),
				m00*_m.getNumber(0,1).doubleValue() + m01*_m.getNumber(1,1).doubleValue(),
				m10*_m.getNumber(0,0).doubleValue() + m11*_m.getNumber(1,0).doubleValue(),
				m10*_m.getNumber(0,1).doubleValue() + m11*_m.getNumber(1,1).doubleValue()
			);
		return this;
	}

	public final Matrix22d 		timesEquals(final Matrix22D _d) {
		set(
				m00*_d.get(0,0) + m01*_d.get(1,0),
				m00*_d.get(0,1) + m01*_d.get(1,1),
				m10*_d.get(0,0) + m11*_d.get(1,0),
				m10*_d.get(0,1) + m11*_d.get(1,1)
			);
		return this;
	}

	@Override
	public final Matrix22d 		arrayTimes(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(m00*B.getNumber(0, 0).doubleValue(), m01*B.getNumber(0, 1).doubleValue(),
							m10*B.getNumber(1, 0).doubleValue(), m11*B.getNumber(1, 1).doubleValue()
						 );
	}
	@Override
	public final Matrix22d 		arrayTimes(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(m00*B.get(0, 0), m01*B.get(0, 1),
							m10*B.get(1, 0), m11*B.get(1, 1)
						 );
	}

	public final Matrix22d 		arrayTimes(final Matrix22D B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(m00*B.get(0,0), m01*B.get(0,1),
							m10*B.get(1,0), m11*B.get(1,1)
						 );
	}

	@Override
	public final Matrix22d 		arrayTimesEquals(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 *= B.getNumber(0, 0).doubleValue();
		m01 *= B.getNumber(0, 1).doubleValue();
		m10 *= B.getNumber(1, 0).doubleValue();
		m11 *= B.getNumber(1, 1).doubleValue();
		return this;
	}
	@Override
	public final Matrix22d 		arrayTimesEquals(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 *= B.get(0, 0);
		m01 *= B.get(0, 1);
		m10 *= B.get(1, 0);
		m11 *= B.get(1, 1);
		return this;
	}

	@Override
	public final Matrix22d 		arrayRightDivide(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(m00/B.getNumber(0, 0).doubleValue(), m01/B.getNumber(0, 1).doubleValue(),
							m10/B.getNumber(1, 0).doubleValue(), m11/B.getNumber(1, 1).doubleValue()
						 );
	}
	@Override
	public final Matrix22d 		arrayRightDivide(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(m00/B.get(0, 0), m01/B.get(0, 1),
							m10/B.get(1, 0), m11/B.get(1, 1)
						 );
	}
	
	@Override
	public final Matrix22d 		arrayRightDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 /= B.getNumber(0, 0).doubleValue();
		m01 /= B.getNumber(0, 1).doubleValue();
		m10 /= B.getNumber(1, 0).doubleValue();
		m11 /= B.getNumber(1, 1).doubleValue();
		return this;
	}
	@Override
	public final Matrix22d 		arrayRightDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 /= B.get(0, 0);
		m01 /= B.get(0, 1);
		m10 /= B.get(1, 0);
		m11 /= B.get(1, 1);
		return this;
	}

	@Override
	public final Matrix22d 		arrayLeftDivide(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(B.getNumber(0, 0).doubleValue()/m00, B.getNumber(0, 1).doubleValue()/m01,
							 B.getNumber(1, 0).doubleValue()/m10, B.getNumber(1, 1).doubleValue()/m11
						 	);
	}
	@Override
	public final Matrix22d 		arrayLeftDivide(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		return new Matrix22d(B.get(0, 0)/m00, B.get(0, 1)/m01,
							 B.get(1, 0)/m10, B.get(1, 1)/m11
						 	);
	}

	@Override
	public final Matrix22d 		arrayLeftDivideEquals(final NumberMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 = B.getNumber(0, 0).doubleValue() / m00;
		m01 = B.getNumber(0, 1).doubleValue() / m01;
		m10 = B.getNumber(1, 0).doubleValue() / m10;
		m11 = B.getNumber(1, 1).doubleValue() / m11;
		return this;
	}
	@Override
	public final Matrix22d 		arrayLeftDivideEquals(final DoubleMatrix B) {
		assert(B.rows() == 2 && B.columns() == 2);
		m00 = B.get(0, 0) / m00;
		m01 = B.get(0, 1) / m01;
		m10 = B.get(1, 0) / m10;
		m11 = B.get(1, 1) / m11;
		return this;
	}

	public final boolean 		isValid() {
		return false;
	}

	public final boolean 		isEqual(final double _d) {
		return (m00 == _d && m01 == _d && m10 == _d && m11 == _d) ? true : false;
	}
	public final boolean 		isEqual(final NumberMatrix _m) {
		if(_m.rows() != 2 || _m.columns() != 2)
			return false;
		return (m00 == _m.getNumber(0,0).doubleValue() && m01 == _m.getNumber(0,1).doubleValue() && m10 == _m.getNumber(1,0).doubleValue() && m11 == _m.getNumber(1,1).doubleValue()) ? true : false;
	}
	public final boolean 		isEqual(final DoubleMatrix _m) {
		if(_m.rows() != 2 || _m.columns() != 2)
			return false;
		return (m00 == _m.get(0,0) && m01 == _m.get(0,1) && m10 == _m.get(1,0) && m11 == _m.get(1,1)) ? true : false;
	}

	public final boolean 		isEqual(final Matrix22D _m) {
		return (m00 == _m.get(0,0) && m01 == _m.get(0,1) && m10 == _m.get(1,0) && m11 == _m.get(1,1)) ? true : false;
	}

	public final boolean 		isDifferent(final double _d) {
		return (m00 != _d || m01 != _d || m10 != _d || m11 != _d) ? true : false;
	}
	public final boolean 		isDifferent(final NumberMatrix _m) {
		if(_m.rows() != 2 || _m.columns() != 2)
			return false;
		return (m00 != _m.getNumber(0,0).doubleValue() || m01 != _m.getNumber(0,1).doubleValue() || m10 != _m.getNumber(1,0).doubleValue() || m11 != _m.getNumber(1,1).doubleValue()) ? true : false;
	}
	public final boolean 		isDifferent(final DoubleMatrix _m) {
		if(_m.rows() != 2 || _m.columns() != 2)
			return false;
		return (m00 != _m.get(0,0) || m01 != _m.get(0,1) || m10 != _m.get(1,0) || m11 != _m.get(1,1)) ? true : false;
	}
	
	public final boolean 		isDifferent(final Matrix22D _m) {
		return (m00 != _m.get(0,0) || m01 != _m.get(0,1) || m10 != _m.get(1,0) || m11 != _m.get(1,1)) ? true : false;
	}

	@Override
	public final Matrix22d 		clone() {
		return new Matrix22d(m00, m01, m10, m11);
	}
	@Override
	public final Matrix22d 		abs() {
		return new Matrix22d(Math.abs(m00), Math.abs(m01), Math.abs(m10), Math.abs(m11));
	}
	@Override
	public final Matrix22d 		transpose() {
		return new Matrix22d(m00, m10, m01, m11);
	}
	@Override
	public final Matrix22d 		inverse() {
		double det = det();
		if(det == 0)
			return null;

		det = 1.0f / det;

		final Matrix22d B = new Matrix22d();
		B.m00 =  det * m11;
		B.m01 = -det * m01;
		B.m10 = -det * m10;
		B.m11 =  det * m00;
		return B;
	}
	@Override
	public final Matrix22d 		uminus() {
		return new Matrix22d(-m00, -m01, -m10, -m11);
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
		return m00 * m11 - m01 * m10;
	}
	@Override
	public final double 		trace() {
		return m00 + m11;
	}
	@Override
	public final double 		norm1() {
		return Math.max(Math.abs(m00) + Math.abs(m10), Math.abs(m01) + Math.abs(m11));
	}
	@Override
	public final double 		norm2() {
		return new SingularValueDecomposition(this).norm2();
	}
	@Override
	public final double 		normInf() {
		return Math.max(Math.abs(m00) + Math.abs(m01), Math.abs(m10) + Math.abs(m11));
	}
	@Override
	public final double 		normF() {
		double f = 0;
		f = Numbers.hypot(f, m00);
		f = Numbers.hypot(f, m01);
		f = Numbers.hypot(f, m10);
		f = Numbers.hypot(f, m11);
		return f;
	}

	@Override
	public final String 		toString() {
		return "[" + m00 + " " + m01 + ", " + m10 + " " + m11 + "]";
	}
	@Override
	public final String 		toString(final NumberFormat _nf) {
		return "(" + _nf.format(m00) + " " + _nf.format(m01) + "," + _nf.format(m10) + " " + _nf.format(m11) + ")";
	}

	public final Vector2D 		solve(final Vector2D b) {
		double det = m00 * m11 - m01 * m10;
		if(det == 0.0f)
			return null;
		
		det = 1.0f / det;
		return new DoubleVector2D(det * (m11 * b.getX() - m01 * b.getY()), det * (m00 * b.getY() - m10 * b.getX()));
	}

	@Override
	public boolean equals(Object _obj) {
		if(this == _obj)
			return true;
		if(_obj == null)
			return false;
		if(getClass() != _obj.getClass())
			return false;
		Matrix22d other = (Matrix22d) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return false;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return false;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return false;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
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
		Matrix22d other = (Matrix22d) _obj;
		if(Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00))
			return Double.doubleToLongBits(m00) > Double.doubleToLongBits(other.m00) ? 1 : -1;
		if(Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01))
			return Double.doubleToLongBits(m01) > Double.doubleToLongBits(other.m01) ? 1 : -1;
		if(Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10))
			return Double.doubleToLongBits(m10) > Double.doubleToLongBits(other.m10) ? 1 : -1;
		if(Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11))
			return Double.doubleToLongBits(m11) > Double.doubleToLongBits(other.m11) ? 1 : -1;
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn(0).hashCode();
		result = prime * result + getColumn(1).hashCode();
		return result;
	}
/*
	@Override
	public final double[][]	getStorage() {
		throw new IllegalAccessError();
	}
	@Override
	public final double[][] getStorageCopy() {
		return new double[][] { { m00, m01 }, { m10, m11 } };
	}
	@Override
	public final double[] 	getColumnPackedCopy() {
		return new double[] { m00, m01, m10, m11};
	}
	@Override
	public final double[] 	getRowPackedCopy() {
		return new double[] { m00, m10, m01, m11};
	}
*/


}
