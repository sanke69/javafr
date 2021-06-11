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
package fr.java.maths.algebra.vectors;

import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.linear.Dimension1D;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.Tensors;
import fr.java.maths.algebra.Vectors;

public class DoubleVector1D implements Point1D.Editable, Vector1D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x;

	public DoubleVector1D() {
	    this(0);
	}
	public DoubleVector1D(final double _x) {
		x = _x;
	}
	public DoubleVector1D(final DoubleVector1D _copy) {
	    this(_copy.x);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian1D; }

	/**
	 * TENSOR METHODS
	**/
	@Override
	public final boolean 		isDirect()  { return false; }
	@Override
	public final double[] 		getArray()  { return new double[] { x }; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
	@Override
    public final DoubleStream 	getStream() { return DoubleStream.of(x); }

    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public double 				getValue(int _index) {
		if(_index > 0)
			throw new IllegalArgumentException();

		return _index == 0 ? x : Double.NaN;
	}
	@Override
	public void 				setValue(double _value, int _index) {
		if(_index != 0)
			throw new IllegalArgumentException();

		x = _value;
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 1 && _coords[0] == 0);
		setX(_value);
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 1 && _coords[0] == 0);
		return get(_coords[0]);
	}

	@Override
	public final void			setSlice(final DoubleTensor _tensor, final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceView(final int... _slice) {
		throw new IllegalAccessError();
	}
	@Override
	public final DoubleTensor 	getSliceCopy(final int... _slice) {
		assert(_slice.length == 1 && _slice[0] < 2);
		return Tensors.Double.allocate(new double[] { x }, 1);
	}

	@Override
	public void 				reshape(int... _shape) {
		throw new IllegalAccessError();
	}

	/**
	 * VECTOR METHODS
	**/
	@Override
	public final void 			set(final double _value) {
		this.x = _value;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length >= 1);
		set(_values[0]);
	}
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length >= 1);
		set(_values[0].doubleValue());
	}
	@Override
	public final void 			set(final Point1D _pt) {
		x = _pt.getX();
	}
	public final void 			set(final Vector1D _vec) {
		x = _vec.getX();
	}
	@Override
	public final void 			set(final NumberVector _vector) {
		assert (_vector.size() >= 1);
		x = _vector.getNumber(0).doubleValue();
	}
	@Override
	public final void 			set(final DoubleVector _vector) {
		assert (_vector.size() >= 1);
		x = _vector.get(0);
	}

	@Override
	public final void 			setNumber(final Number _value, final int _index) {
		if(_index == 0)
			x = _value.doubleValue();
	}
	@Override
	public final Number 		getNumber(final int _index) {
		return (_index == 0) ? x : null;		
	}

	@Override
	public final void 			set(final double _value, final int _index) {
		if(_index == 0)
			x = _value;
	}
	@Override
	public final double 		get(final int _index) {
		return (_index == 0) ? x : Double.NaN;		
	}

	@Override
	public final void 			setX(final double _x) {
		x = _x;
	}
	@Override
	public final double 		getX() {
		return x;
	}

	@Override
	public void 				setMagnitude(double _mag) {
        double old_mag = Math.abs((x));
        
        x = x * _mag / old_mag;
	}
	@Override
	public double 				getMagnitude() {
		return Math.abs(x);
	}

	@Override
	public final DoubleVector1D 		plus(final double _t) {
		return new DoubleVector1D(x + _t);
	}
	@Override
	public final DoubleVector1D 		plus(final double[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x + _v[0]);
	}
	@Override
	public final DoubleVector1D 		plus(final Number _t) {
		return new DoubleVector1D(x + _t.doubleValue());
	}
	@Override
	public final DoubleVector1D 		plus(final Number[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x + _v[0].doubleValue());
	}
	@Override
	public final DoubleVector1D 		plus(final Point1D _pt) {
		return new DoubleVector1D(x + _pt.getX());
	}
	@Override
	public final DoubleVector1D 		plus(final Vector1D _vec) {
		return new DoubleVector1D(x + _vec.getX());
	}
	@Override
	public final DoubleVector1D 		plus(final Dimension1D _d) {
		return new DoubleVector1D(x + _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		plus(final Coordinate.OneDim _c) {
		return new DoubleVector1D(x + _c.getFirst());
	}
	@Override
	public final DoubleVector1D 		plus(final Dimension.OneDim _d) {
		return new DoubleVector1D(x + _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		plus(final NumberVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x + _v.getNumber(0).doubleValue());
	}
	@Override
	public final DoubleVector1D 		plus(final DoubleVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x + _v.get(0));
	}

	@Override
	public final DoubleVector1D 		plusEquals(final double _t) {
		x += _t;
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final double[] _v) {
		assert(_v.length >= 1);
		x += _v[0];
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Number _t) {
		x += _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Number[] _v) {
		assert(_v.length >= 1);
		x += _v[0].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Point1D _d) {
		x += _d.getX();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Vector1D _d) {
		x += _d.getX();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Dimension1D _dim) {
		x += _dim.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Coordinate.OneDim _c) {
		x += _c.getFirst();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final Dimension.OneDim _dim) {
		x += _dim.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final NumberVector _v) {
		assert(_v.size() >= 1);
		x += _v.getNumber(0).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		plusEquals(final DoubleVector _v) {
		assert(_v.size() >= 1);
		x += _v.get(0);
		return this;
	}

	@Override
	public final DoubleVector1D 		minus(final double _t) {
		return new DoubleVector1D(x - _t);
	}
	@Override
	public final DoubleVector1D 		minus(final double[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x - _v[0]);
	}
	@Override
	public final DoubleVector1D 		minus(final Number _t) {
		return new DoubleVector1D(x - _t.doubleValue());
	}
	@Override
	public final DoubleVector1D 		minus(final Number[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x - _v[0].doubleValue());
	}
	@Override
	public final DoubleVector1D 		minus(final Point1D _d) {
		return new DoubleVector1D(x - _d.getX());
	}
	@Override
	public final DoubleVector1D 		minus(final Vector1D _d) {
		return new DoubleVector1D(x - _d.getX());
	}
	@Override
	public final DoubleVector1D 		minus(final Dimension1D _d) {
		return new DoubleVector1D(x - _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		minus(final Coordinate.OneDim _c) {
		return new DoubleVector1D(x - _c.getFirst());
	}
	@Override
	public final DoubleVector1D 		minus(final Dimension.OneDim _d) {
		return new DoubleVector1D(x - _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		minus(final NumberVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x - _v.getNumber(0).doubleValue());
	}
	@Override
	public final DoubleVector1D 		minus(final DoubleVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x - _v.get(0));
	}

	@Override
	public final DoubleVector1D 		minusEquals(final double _t) {
		x -= _t;
		return this;
	}
	@Override
	public final DoubleVector1D 		minusEquals(final double[] _v) {
		assert(_v.length >= 1);
		x -= _v[0];
		return this;
	}
	@Override
	public final DoubleVector1D 		minusEquals(final Number _t) {
		x -= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		minusEquals(final Number[] _v) {
		assert(_v.length >= 1);
		x -= _v[0].doubleValue();
		return this;
	}
	public final DoubleVector1D 		minusEquals(final Point1D _d) {
		x -= _d.getX();
		return this;
	}
	public final DoubleVector1D 		minusEquals(final Vector1D _d) {
		x -= _d.getX();
		return this;
	}
	public final DoubleVector1D 		minusEquals(final Dimension1D _dim) {
		x -= _dim.getWidth();
		return this;
	}
	public final DoubleVector1D 		minusEquals(final Coordinate.OneDim _c) {
		x -= _c.getFirst();
		return this;
	}
	public final DoubleVector1D 		minusEquals(final Dimension.OneDim _dim) {
		x -= _dim.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		minusEquals(final NumberVector _v) {
		assert(_v.size() >= 1);
		x -= _v.getNumber(0).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 1);
		x -= _v.get(0);
		return this;
	}

	@Override
	public final DoubleVector1D 		times(final double _t) {
		return new DoubleVector1D(x * _t);
	}
	@Override
	public final DoubleVector1D 		times(final double[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x * _v[0]);
	}
	@Override
	public final DoubleVector1D 		times(final Number _t) {
		return new DoubleVector1D(x * _t.doubleValue());
	}
	@Override
	public final DoubleVector1D 		times(final Number[] _v) {
		assert(_v.length >= 1);
		return new DoubleVector1D(x * _v[0].doubleValue());
	}
	public final DoubleVector1D 		times(final Vector1D _d) {
		return new DoubleVector1D(x * _d.getX());
	}
	@Override
	public final DoubleVector1D 		times(final Dimension1D _dim) {
		return new DoubleVector1D(x * _dim.getWidth());
	}
	@Override
	public final DoubleVector1D 		times(final Dimension.OneDim _dim) {
		return new DoubleVector1D(x * _dim.getWidth());
	}
	@Override
	public final DoubleVector1D 		times(final NumberVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x * _v.getNumber(0).doubleValue());
	}
	@Override
	public final DoubleVector1D 		times(final DoubleVector _v) {
		assert(_v.size() >= 1);
		return new DoubleVector1D(x * _v.get(0));
	}
	
	@Override
	public final DoubleVector1D 		timesEquals(final double _t) {
		x *= _t;
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final double[] _v) {
		assert(_v.length >= 1);
		x *= _v[0];
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final Number _t) {
		x *= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final Number[] _v) {
		assert(_v.length >= 1);
		x *= _v[0].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final Vector1D _d) {
		x *= _d.getX();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final Dimension1D _d) {
		x *= _d.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final Dimension.OneDim _d) {
		x *= _d.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final NumberVector _v) {
		assert(_v.size() >= 1);
		x *= _v.getNumber(0).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 1);
		x *= _v.get(0);
		return this;
	}

	@Override
	public final DoubleVector1D 		divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _t);
	}
	@Override
	public final DoubleVector1D 		divides(final double[] _v) {
		assert(_v.length >= 1);
		if(_v[0] == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _v[0]);
	}
	@Override
	public final DoubleVector1D 		divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _t.doubleValue());
	}
	@Override
	public final DoubleVector1D 		divides(final Number[] _v) {
		assert(_v.length >= 1);
		if(_v[0].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _v[0].doubleValue());
	}
	@Override
	public final DoubleVector1D 		divides(final Vector1D _d) {
		if(_d.getX() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _d.getX());
	}
	@Override
	public final DoubleVector1D 		divides(final Dimension1D _d) {
		if(_d.getWidth() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		divides(final Dimension.OneDim _d) {
		if(_d.getWidth() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _d.getWidth());
	}
	@Override
	public final DoubleVector1D 		divides(final NumberVector _v) {
		assert(_v.size() >= 1);
		if(_v.getNumber(0).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _v.getNumber(0).doubleValue());
	}
	@Override
	public final DoubleVector1D 		divides(final DoubleVector _v) {
		assert(_v.size() >= 1);
		if(_v.get(0) == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector1D(x / _v.get(0));
	}

	@Override
	public final DoubleVector1D 		dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final double[] _v) {
		assert(_v.length >= 1);
		if(_v[0] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final Number[] _v) {
		assert(_v.length >= 1);
		if(_v[0].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final Vector1D _d) {
		if(_d.getX() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final Dimension1D _d) {
		if(_d.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final Dimension.OneDim _d) {
		if(_d.getWidth() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final NumberVector _v) {
		if(_v.getNumber(0).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector1D 		dividesEquals(final DoubleVector _v) {
		if(_v.get(0) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		return this;
	}

	@Override
	public final DoubleVector1D			normalize(double _norm) {
		double length = Math.sqrt(x*x), Q = _norm / length;
		return new DoubleVector1D(x * Q);
	}

	@Override
	public final boolean 		isValid() {
		return !java.lang.Double.isNaN(x) && !java.lang.Double.isInfinite(x);
	}

	@Override
	public final boolean 		isEqual(final Number _t) {
		return (x == _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Number[] _v) {
		assert(_v.length == 1);
		return x == _v[0].doubleValue() ? true : false;
	}
	@Override
	public final boolean 		isEqual(final NumberVector _v) {
		assert(_v.size() == 1);
		return x == _v.getNumber(0).doubleValue() ? true : false;
	}
		
	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double[] _v) {
		assert(_v.length == 1);
		return (x == _v[0]) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Point1D _other) {
		if(Double.isNaN(x))
			return Double.isNaN(_other.getX()) ? true : false;
		return (x == _other.getX()) ? true : false;
	}
	public final boolean 		isEqual(final Vector1D _other) {
		if(Double.isNaN(x))
			return Double.isNaN(_other.getX()) ? true : false;
		return (x == _other.getX()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final DoubleVector _v) {
		assert(_v.size() == 1);
		return (x == _v.get(0)) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Number _t) {
		return (x != _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Number[] _v) {
		assert(_v.length >= 1);
		return (x != _v[0].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberVector _v) {
		assert(_v.size() >= 1);
		return (x != _v.getNumber(0).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _v) {
		assert(_v.length >= 1);
		return (x != _v[0]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleVector _v) {
		assert(_v.size() >= 1);
		return (x != _v.get(0)) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point1D _d) {
		return (x != _d.getX()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Vector1D _d) {
		return (x != _d.getX()) ? true : false;
	}

	public final boolean 		isColinear(final Vector1D _vec) {
		return true;
	}
	public final boolean 		isColinear(final NumberVector _vec) {
		assert(_vec.size() >= 1);
		return true;
	}
	public final boolean 		isColinear(final DoubleVector _vec) {
		assert(_vec.size() >= 1);
		return true;
	}

	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Numbers.EPSILON) ? true : false;
	}
	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Numbers.EPSILON) ? true : false;
	}

	@Override
	public final DoubleVector1D 		clone() {
		return new DoubleVector1D(x);
	}
	@Override
	public final DoubleVector1D 		cloneEditable() {
		return new DoubleVector1D(x);
	}

	@Override
	public final DoubleVector1D 		abs() {
		return new DoubleVector1D(Math.abs(x));
	}
	@Override
	public final DoubleVector1D 		negate() {
		return new DoubleVector1D(-x);
	}
	@Override
	public final DoubleVector1D 		normalized() {
		double length = norm();
		if(length < Numbers.EPSILON)
			return this;
		double invLength = 1.0f / length;
		return new DoubleVector1D(x * invLength);
	}

	@Override
	public Vector2D 					uniform() 						{ return new DoubleVector2D(getX(), 0.0d); }
	@Override
	public Vector2D 					uniform(double _w) 				{ return new DoubleVector2D(getX(), _w); }

	
	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return x*x;

		case Euclidian:			return x;

		case Manhattan:			return Math.abs( x );

		case P:					return Math.pow( Math.abs( x ), p);

		case Maximum:			return Math.abs( x );
								
		default:				throw new NotYetImplementedException();
		}
	}
	@Override
	public final double 		magnitude() {
		return norm();
	}
	@Override
	public final double 		norm() {
		return Math.abs(x);
	}
	@Override
	public final double 		norm2() {
		return x * x;
	}

	@Override
	public final Number 		dotProduct(final NumberVector _b) {
        return x * _b.getNumber(1).doubleValue();
	}

    @Override
	public final NumberVector 	crossProduct(NumberVector _b) {
		throw new NotYetImplementedException();
	}

	/** ****************************** **\
	 *  Object / Comparable / Clonable  *
	\** ****************************** **/

	@Override
	public int 		hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
		return (int) result;
	}

	@Override
	public boolean 	equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		DoubleVector1D other = (DoubleVector1D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		return true;
	}

	@Override
	public int 		compareTo(Object obj) {
		if(this == obj)
			return 0;
		if(obj == null)
			return 1;
		if(getClass() != obj.getClass())
			return 1;
		DoubleVector1D other = (DoubleVector1D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		return -1;
	}

	@Override
	public String 	toString() {
		return "(" + x + ")";
	}
	@Override
	public String 	toString(NumberFormat _nf) {
		return "( " + _nf.format(x) + " )";
	}
	@Override
	public String 	toString(DecimalFormat _df) {
		return "( " + _df.format(x) + " )";
	}

}
