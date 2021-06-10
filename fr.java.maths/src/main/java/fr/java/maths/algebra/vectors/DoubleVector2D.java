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
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.linear.Vector1D;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.Tensors;
import fr.java.maths.algebra.Vectors;

public class DoubleVector2D implements Point2D.Editable, Vector2D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x, y;

	public DoubleVector2D() {
	    this(0, 0);
	}
	public DoubleVector2D(final double _x, final double _y) {
		x = _x;
		y = _y;
	}
	public DoubleVector2D(final DoubleVector2D _copy) {
	    this(_copy.x, _copy.y);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

	/**
	 * TENSOR METHODS
	**/
	@Override
	public final boolean 		isDirect()  { return false; }
	@Override
	public final double[] 		getArray()  { return new double[] { x, y }; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
	@Override
    public final DoubleStream 	getStream() { return DoubleStream.of(x, y); }

	@Override
	public final void 			setNumber(final Number _value, final int _index) {
		if(_index == 0)
			x = _value.doubleValue();
		else
			y = _value.doubleValue();
	}
	@Override
	public Number 				getNumber(int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public void 				setValue(double _value, int _index) {
		if(_index > 2)
			throw new IllegalArgumentException();

		if(_index == 0)
			x = _value;
		else
			y = _value;
	}
	@Override
	public double 				getValue(int _index) {
		if(_index > 2)
			throw new IllegalArgumentException();

		return _index == 0 ? x : y;
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 2);
		if(_coords[0] == 0)
			setX(_value);
		else if(_coords[0] == 1)
			setY(_value);
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 2);
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
		return Tensors.Double.allocate(new double[] { x, y }, 2);
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
		this.y = _value;
	}
	@Override
	public final void 			set(final double _x, final double _y) {
		this.x = _x;
		this.y = _y;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length >= 2);
		set(_values[0], _values[1]);
	}
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number _x, final Number _y) {
		x = _x.doubleValue();
		y = _y.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length >= 2);
		set(_values[0].doubleValue(), _values[1].doubleValue());
	}
	@Override
	public final void 			set(final Point2D _pt) {
		x = _pt.getX();
		y = _pt.getY();
	}
	public final void 			set(final Vector2D _vec) {
		x = _vec.getX();
		y = _vec.getY();
	}
	@Override
	public final void 			set(final NumberVector _vector) {
		assert (_vector.size() >= 2);
		x = _vector.getNumber(0).doubleValue();
		y = _vector.getNumber(1).doubleValue();
	}
	@Override
	public final void 			set(final DoubleVector _vector) {
		assert (_vector.size() >= 2);
		x = _vector.get(0);
		y = _vector.get(1);
	}

	@Override
	public final void 			set(final double _value, final int _index) {
		if(_index == 0)
			x = _value;
		else
			y = _value;
	}
	@Override
	public final double 		get(final int _index) {
		return (_index == 0) ? x : y;		
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
	public final void 			setY(final double _y) {
		y = _y;
	}
	@Override
	public final double 		getY() {
		return y;
	}

	@Override
	public void 				setMagnitude(double _mag) {
	        double old_mag = Math.sqrt((x*x + y*y));
	        
	        x = x * _mag / old_mag;
	        y = y * _mag / old_mag;
	}
	@Override
	public double 				getMagnitude() {
		return Math.sqrt((x*x + y*y));
	}

	@Override
	public final DoubleVector2D 		plus(final double _t) {
		return new DoubleVector2D(x + _t, y + _t);
	}
	@Override
	public final DoubleVector2D 		plus(final double _u, final double _v) {
		return new DoubleVector2D(x + _u, y + _v);
	}
	@Override
	public final DoubleVector2D 		plus(final double[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x + _v[0], y + _v[1]);
	}
	@Override
	public final DoubleVector2D 		plus(final Number _t) {
		return new DoubleVector2D(x + _t.doubleValue(), y + _t.doubleValue());
	}
	@Override
	public final DoubleVector2D 		plus(final Number _x, final Number _y) {
		return new DoubleVector2D(x + _x.doubleValue(), y + _y.doubleValue());
	}
	@Override
	public final DoubleVector2D 		plus(final Number[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x + _v[0].doubleValue(), y + _v[1].doubleValue());
	}
	@Override
	public final DoubleVector2D 		plus(final Point2D _pt) {
		return new DoubleVector2D(x + _pt.getX(), y + _pt.getY());
	}
	@Override
	public final DoubleVector2D 		plus(final Vector2D _vec) {
		return new DoubleVector2D(x + _vec.getX(), y + _vec.getY());
	}
	@Override
	public final DoubleVector2D 		plus(final Dimension2D _d) {
		return new DoubleVector2D(x + _d.getWidth(), y + _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		plus(final Coordinate.TwoDims _c) {
		return new DoubleVector2D(x + _c.getFirst(), y + _c.getSecond());
	}
	@Override
	public final DoubleVector2D 		plus(final Dimension.TwoDims _d) {
		return new DoubleVector2D(x + _d.getWidth(), y + _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		plus(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x + _v.getNumber(0).doubleValue(), y + _v.getNumber(1).doubleValue());
	}
	@Override
	public final DoubleVector2D 		plus(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x + _v.get(0), y + _v.get(1));
	}

	@Override
	public final DoubleVector2D 		plusEquals(final double _t) {
		x += _t;
		y += _t;
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final double _u, final double _v) {
		x += _u;
		y += _v;
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x += _v[0];
		y += _v[1];
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Number _x, final Number _y) {
		x += _x.doubleValue();
		y += _y.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Point2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Vector2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Dimension2D _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Coordinate.TwoDims _c) {
		x += _c.getFirst();
		y += _c.getSecond();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final Dimension.TwoDims _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		plusEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x += _v.get(0);
		y += _v.get(1);
		return this;
	}

	@Override
	public final DoubleVector2D 		minus(final double _t) {
		return new DoubleVector2D(x - _t, y - _t);
	}
	@Override
	public final DoubleVector2D 		minus(final double _u, final double _v) {
		return new DoubleVector2D(x - _u, y - _v);
	}
	@Override
	public final DoubleVector2D 		minus(final double[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x - _v[0], y - _v[1]);
	}
	@Override
	public final DoubleVector2D 		minus(final Number _t) {
		return new DoubleVector2D(x - _t.doubleValue(), y - _t.doubleValue());
	}
	@Override
	public final DoubleVector2D  		minus(final Number _x, final Number _y) {
		return new DoubleVector2D(x - _x.doubleValue(), y - _y.doubleValue());
	}
	@Override
	public final DoubleVector2D 		minus(final Number[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x - _v[0].doubleValue(), y - _v[1].doubleValue());
	}
	@Override
	public final DoubleVector2D 		minus(final Point2D _d) {
		return new DoubleVector2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final DoubleVector2D 		minus(final Vector2D _d) {
		return new DoubleVector2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final DoubleVector2D 		minus(final Dimension2D _d) {
		return new DoubleVector2D(x - _d.getWidth(), y - _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		minus(final Coordinate.TwoDims _c) {
		return new DoubleVector2D(x - _c.getFirst(), y - _c.getSecond());
	}
	@Override
	public final DoubleVector2D 		minus(final Dimension.TwoDims _d) {
		return new DoubleVector2D(x - _d.getWidth(), y - _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		minus(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x - _v.getNumber(0).doubleValue(), y - _v.getNumber(1).doubleValue());
	}
	@Override
	public final DoubleVector2D 		minus(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x - _v.get(0), y - _v.get(1));
	}

	@Override
	public final DoubleVector2D 		minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final double _u, final double _v) {
		x -= _u;
		y -= _v;
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x -= _v[0];
		y -= _v[1];
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final Number _x, final Number _y) {
		x -= _x.doubleValue();
		y -= _y.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		return this;
	}
	public final DoubleVector2D 		minusEquals(final Point2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	public final DoubleVector2D 		minusEquals(final Vector2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	public final DoubleVector2D 		minusEquals(final Dimension2D _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
	public final DoubleVector2D 		minusEquals(final Coordinate.TwoDims _c) {
		x -= _c.getFirst();
		y -= _c.getSecond();
		return this;
	}
	public final DoubleVector2D 		minusEquals(final Dimension.TwoDims _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x -= _v.get(0);
		y -= _v.get(1);
		return this;
	}

	@Override
	public final DoubleVector2D 		times(final double _t) {
		return new DoubleVector2D(x * _t, y * _t);
	}
	public final DoubleVector2D 		times(final double _u, final double _v) {
		return new DoubleVector2D(x * _u, y * _v);
	}
	@Override
	public final DoubleVector2D 		times(final double[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x * _v[0], y * _v[1]);
	}
	@Override
	public final DoubleVector2D 		times(final Number _t) {
		return new DoubleVector2D(x * _t.doubleValue(), y * _t.doubleValue());
	}
	@Override
	public final DoubleVector2D 		times(final Number _x, final Number _y) {
		return new DoubleVector2D(x * _x.doubleValue(), y * _y.doubleValue());
	}
	@Override
	public final DoubleVector2D 		times(final Number[] _v) {
		assert(_v.length >= 2);
		return new DoubleVector2D(x * _v[0].doubleValue(), y * _v[1].doubleValue());
	}
	public final DoubleVector2D 		times(final Vector2D _d) {
		return new DoubleVector2D(x * _d.getX(), y * _d.getY());
	}
	@Override
	public final DoubleVector2D 		times(final Dimension2D _dim) {
		return new DoubleVector2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
	@Override
	public final DoubleVector2D 		times(final Dimension.TwoDims _dim) {
		return new DoubleVector2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
	@Override
	public final DoubleVector2D 		times(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x * _v.getNumber(0).doubleValue(), y * _v.getNumber(1).doubleValue());
	}
	@Override
	public final DoubleVector2D 		times(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new DoubleVector2D(x * _v.get(0), y * _v.get(1));
	}
	
	@Override
	public final DoubleVector2D 		timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final double _u, final double _v) {
		x *= _u;
		y *= _v;
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final double[] _v) {
		assert(_v.length >= 2);
		x *= _v[0];
		y *= _v[1];
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Number _u, final Number _v) {
		x *= _u.doubleValue();
		y *= _v.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Vector2D _d) {
		x *= _d.getX();
		y *= _d.getY();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Dimension2D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final Dimension.TwoDims _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x *= _v.get(0);
		y *= _v.get(1);
		return this;
	}

	@Override
	public final DoubleVector2D 		divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _t, y / _t);
	}
	@Override
	public final DoubleVector2D 		divides(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _u, y / _v);
	}
	@Override
	public final DoubleVector2D 		divides(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _v[0], y / _v[1]);
	}
	@Override
	public final DoubleVector2D 		divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _t.doubleValue(), y / _t.doubleValue());
	}
	@Override
	public final DoubleVector2D 		divides(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _u.doubleValue(), y / _v.doubleValue());
	}
	@Override
	public final DoubleVector2D 		divides(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _v[0].doubleValue(), y / _v[1].doubleValue());
	}
	@Override
	public final DoubleVector2D 		divides(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _d.getX(), y / _d.getY());
	}
	@Override
	public final DoubleVector2D 		divides(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _d.getWidth(), y / _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		divides(final Dimension.TwoDims _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _d.getWidth(), y / _d.getHeight());
	}
	@Override
	public final DoubleVector2D 		divides(final NumberVector _v) {
		assert(_v.size() >= 2);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _v.getNumber(0).doubleValue(), y / _v.getNumber(1).doubleValue());
	}
	@Override
	public final DoubleVector2D 		divides(final DoubleVector _v) {
		assert(_v.size() >= 2);
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector2D(x / _v.get(0), y / _v.get(1));
	}

	@Override
	public final DoubleVector2D 		dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _u.doubleValue();
		y /= _v.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final Dimension.TwoDims _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final NumberVector _v) {
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector2D 		dividesEquals(final DoubleVector _v) {
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		return this;
	}

	@Override
	public Vector2D 					normalize(double _norm) {
		double length = Math.sqrt(x*x + y*y), Q = _norm / length;
		return new DoubleVector2D(x * Q, y * Q);
	}

	@Override
	public final boolean 		isValid() {
		return !java.lang.Double.isNaN(x) && !java.lang.Double.isInfinite(x) && !java.lang.Double.isNaN(y) && !java.lang.Double.isInfinite(y);
	}

	@Override
	public final boolean 		isEqual(final Number _t) {
		return (x == _t.doubleValue() && y == _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Number[] _v) {
		assert(_v.length == 2);
		return (x == _v[0].doubleValue() && y == _v[1].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final NumberVector _v) {
		assert(_v.size() == 2);
		return (x == _v.getNumber(0).doubleValue() && y == _v.getNumber(1).doubleValue()) ? true : false;
	}
		
	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t && y == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double[] _v) {
		assert(_v.length == 2);
		return (x == _v[0] && y == _v[1]) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final DoubleVector _v) {
		assert(_v.size() == 2);
		return (x == _v.get(0) && y == _v.get(1)) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final double _u, final double _v) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_u) || Double.isNaN(_v) ? true : false;
		return (x == _u && y == _v) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Point2D _other) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY()) ? true : false;
	}
	public final boolean 		isEqual(final Vector2D _other) {
		if(Double.isNaN(x) || Double.isNaN(y))
			return Double.isNaN(_other.getX()) || Double.isNaN(_other.getY()) ? true : false;
		return (x == _other.getX() && y == _other.getY()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Number _t) {
		return (x != _t.doubleValue() || y != _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Number[] _v) {
		assert(_v.length >= 2);
		return (x != _v[0].doubleValue() || y != _v[1].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberVector _v) {
		assert(_v.size() >= 2);
		return (x != _v.getNumber(0).doubleValue() || y != _v.getNumber(1).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _v) {
		assert(_v.length >= 2);
		return (x != _v[0] || y != _v[1]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return (x != _v.get(0) || y != _v.get(1)) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _u, final double _v) {
		return (x != _u || y != _v) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point2D _d) {
		return (x != _d.getX() || y != _d.getY()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Vector2D _d) {
		return (x != _d.getX() || y != _d.getY()) ? true : false;
	}

	public final boolean 		isColinear(final NumberVector _vec) {
		assert(_vec.size() >= 2);
		return (x * _vec.getNumber(1).doubleValue()) == (y * _vec.getNumber(0).doubleValue());
	}
	public final boolean 		isColinear(final DoubleVector _vec) {
		assert(_vec.size() >= 2);
		return (x * _vec.get(1)) == (y * _vec.get(0));
	}
	public final boolean 		isColinear(final Vector2D _vec) {
		return (x * _vec.getY()) == (y * _vec.getX());
	}

	public final boolean 		isColinearToSegment(final Point2D _A, final Point2D _B) {
		return (x * (_B.getY() - _A.getY())) == (y * (_B.getX() - _A.getX()));
	}
	public final boolean 		isColinearToLine(final Point2D _P, final Vector2D _N) {
		return (x * _N.getY()) == (y * _N.getY());
	}

	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Numbers.EPSILON) ? true : false;
	}
	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Numbers.EPSILON) ? true : false;
	}
	public final boolean 		isOrthogonal(final Vector2D _vec) {
		return (Vectors.dotProduct((Vector2D) this, _vec) < Numbers.EPSILON) ? true : false;
	}
	
	public final boolean 		isOrthogonalToSegment(final Point2D _A, final Point2D _B) {
		return (Vectors.dotProduct((Vector2D) this, Vectors.delta(_B, _A)) < Numbers.EPSILON) ? true : false;
	}
	public final boolean 		isOrthogonalToLine(final Point2D _P, final Vector2D _N) {
		return (Vectors.dotProduct((Vector2D) this, _N) < Numbers.EPSILON) ? true : false;
	}

	@Override
	public final DoubleVector2D 		clone() {
		return new DoubleVector2D(x, y);
	}
	@Override
	public final DoubleVector2D 		cloneEditable() {
		return new DoubleVector2D(x, y);
	}

	@Override
	public final DoubleVector2D 		abs() {
		return new DoubleVector2D(Math.abs(x), Math.abs(y));
	}
	@Override
	public final DoubleVector2D 		negate() {
		return new DoubleVector2D(-x, -y);
	}
	@Override
	public final DoubleVector2D 		normalized() {
		double length = norm();
		if(length < Numbers.EPSILON)
			return this;
		double invLength = 1.0f / length;
		return new DoubleVector2D(x * invLength, y * invLength);
	}
	
	@Override
	public Vector1D 					downgrade() 					{ return new DoubleVector1D(getX() * getY()); 	}
	@Override
	public Vector3D 					uniform() 						{ return new DoubleVector3D(getX(), getY(), 0.0d); }
	@Override
	public Vector3D 					uniform(double _w) 				{ return new DoubleVector3D(getX(), getY(), _w); }

	
	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return x*x+y*y;

		case Euclidian:			return Math.sqrt(x*x+y*y);

		case Manhattan:			return Math.abs( x ) + Math.abs( y );

		case P:					return Math.pow(  Math.pow( Math.abs( x ), p) +  Math.pow( Math.abs( y ), p), 1d/p);

		case Maximum:			return Math.abs( x ) > Math.abs( y ) ? Math.abs( x ) : Math.abs( y );
								
		default:				throw new NotYetImplementedException();
		}
	}
	@Override
	public final double 		magnitude() {
		return norm();
	}
	@Override
	public final double 		norm() {
		return Math.sqrt(norm2());
	}
	@Override
	public final double 		norm2() {
		return x * x + y * y;
	}

    @Override
    public final double 		dotProduct(final double _x, final double _y) {
        return x * _x + y * _y;
    }
	@Override
	public final Number 		dotProduct(final NumberVector _b) {
        return x * _b.getNumber(1).doubleValue() + y * _b.getNumber(1).doubleValue();
	}
    @Override
    public final double 		dotProduct(final Vector2D _b) {
        return x * _b.getX() + y * _b.getY();
    }

    @Override
	public final Vector2D 		crossProduct(final double _x, final double _y) {
		throw new NotYetImplementedException();
	}
    @Override
	public final Vector2D 		crossProduct(final Vector2D _b) {
		throw new NotYetImplementedException();
	}
    @Override
	public final NumberVector 	crossProduct(NumberVector _b) {
		throw new NotYetImplementedException();
	}

	public double euclydianDistance(final Vector2D _v) {
		return (double) Math.sqrt((x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()));
	}
	public double euclydianDistance2(final Vector2D _v) {
		return (double) (x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY());
	}

	/** ****************************** **\
	 *  Object / Comparable / Clonable  *
	\** ****************************** **/

	@Override
	public int 		hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
		result = prime * result + java.lang.Double.doubleToLongBits(y);
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
		DoubleVector2D other = (DoubleVector2D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
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
		DoubleVector2D other = (DoubleVector2D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return java.lang.Double.doubleToLongBits(y) > java.lang.Double.doubleToLongBits(other.y) ? 1 : -1;
		return -1;
	}

	@Override
	public String 	toString() {
		return "(" + x + "," + y + ")";
	}
	@Override
	public String 	toString(NumberFormat _nf) {
		return "( " + _nf.format(x) + ";" + _nf.format(y) + " )";
	}
	@Override
	public String 	toString(DecimalFormat _df) {
		return "( " + _df.format(x) + ";" + _df.format(y) + " )";
	}

}
