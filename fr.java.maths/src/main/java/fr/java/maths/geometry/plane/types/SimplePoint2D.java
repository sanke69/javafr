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
package fr.java.maths.geometry.plane.types;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class SimplePoint2D implements Point2D.Editable {
	private static final long serialVersionUID = 1369L;

	private double x, y;

	public SimplePoint2D() {
	    this(0, 0);
	}
	public SimplePoint2D(final double _x, final double _y) {
		x = _x;
		y = _y;
	}
	public SimplePoint2D(final SimplePoint2D _copy) {
	    this(_copy.x, _copy.y);
	}

	@Override public CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

	@Override
	public final void 			set(final double _value) {
		this.x = _value;
		this.y = _value;
	}
	@Override
	public final void 			set(final double[] _values) {
		assert (_values.length >= 2);
		set(_values[0], _values[1]);
	}
	@Override
	public final void 			set(final double _x, final double _y) {
		this.x = _x;
		this.y = _y;
	}
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _values) {
		assert (_values.length >= 2);
		set(_values[0].doubleValue(), _values[1].doubleValue());
	}
	@Override
	public void 				set(final Number _x, final Number _y) {
		x = _x.doubleValue();
		y = _y.doubleValue();
	}
	@Override
	public final void 			set(final Point2D _pt) {
		x = _pt.getX();
		y = _pt.getY();
	}
	@Override
	public final void 			set(final Vector2D _vec) {
		x = _vec.getX();
		y = _vec.getY();
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
	public final SimplePoint2D plus(final double _t) {
		return new SimplePoint2D(x + _t, y + _t);
	}
	@Override
	public final SimplePoint2D plus(final double _u, final double _v) {
		return new SimplePoint2D(x + _u, y + _v);
	}
	@Override
	public final SimplePoint2D plus(final double[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x + _v[0], y + _v[1]);
	}
	@Override
	public final SimplePoint2D plus(final Number _t) {
		return new SimplePoint2D(x + _t.doubleValue(), y + _t.doubleValue());
	}
	@Override
	public final SimplePoint2D plus(final Number _x, final Number _y) {
		return new SimplePoint2D(x + _x.doubleValue(), y + _y.doubleValue());
	}
	@Override
	public final SimplePoint2D plus(final Number[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x + _v[0].doubleValue(), y + _v[1].doubleValue());
	}
	@Override
	public final SimplePoint2D plus(final Point2D _pt) {
		return new SimplePoint2D(x + _pt.getX(), y + _pt.getY());
	}
	@Override
	public final SimplePoint2D plus(final Vector2D _vec) {
		return new SimplePoint2D(x + _vec.getX(), y + _vec.getY());
	}
	@Override
	public final SimplePoint2D plus(final Dimension2D _d) {
		return new SimplePoint2D(x + _d.getWidth(), y + _d.getHeight());
	}
	@Override
	public final SimplePoint2D plus(final Coordinate.TwoDims _c) {
		return new SimplePoint2D(x + _c.getFirst(), y + _c.getSecond());
	}
	@Override
	public final SimplePoint2D plus(final Dimension.TwoDims _d) {
		return new SimplePoint2D(x + _d.getWidth(), y + _d.getHeight());
	}
//	@Override
	public final SimplePoint2D plus(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new SimplePoint2D(x + _v.get(0), y + _v.get(1));
	}

	@Override
	public final SimplePoint2D plusEquals(final double _t) {
		x += _t;
		y += _t;
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final double _u, final double _v) {
		x += _u;
		y += _v;
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x += _v[0];
		y += _v[1];
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Number _u, final Number _v) {
		x += _u.doubleValue();
		y += _v.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Point2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Vector2D _d) {
		x += _d.getX();
		y += _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Dimension2D _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Coordinate.TwoDims _dim) {
		x += _dim.getFirst();
		y += _dim.getSecond();
		return this;
	}
	@Override
	public final SimplePoint2D plusEquals(final Dimension.TwoDims _dim) {
		x += _dim.getWidth();
		y += _dim.getHeight();
		return this;
	}
//	@Override
	public final SimplePoint2D plusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		return this;
	}

	@Override
	public final SimplePoint2D minus(final double _t) {
		return new SimplePoint2D(x - _t, y - _t);
	}
	@Override
	public final SimplePoint2D minus(final double _u, final double _v) {
		return new SimplePoint2D(x - _u, y - _v);
	}
	@Override
	public final SimplePoint2D minus(final double[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x - _v[0], y - _v[1]);
	}
	@Override
	public final SimplePoint2D minus(final Number _t) {
		return new SimplePoint2D(x - _t.doubleValue(), y - _t.doubleValue());
	}
	@Override
	public final SimplePoint2D minus(final Number _x, final Number _y) {
		return new SimplePoint2D(x - _x.doubleValue(), y - _y.doubleValue());
	}
	@Override
	public final SimplePoint2D minus(final Number[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x - _v[0].doubleValue(), y - _v[1].doubleValue());
	}
	@Override
	public final SimplePoint2D minus(final Point2D _d) {
		return new SimplePoint2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final SimplePoint2D minus(final Vector2D _d) {
		return new SimplePoint2D(x - _d.getX(), y - _d.getY());
	}
	@Override
	public final SimplePoint2D minus(final Dimension2D _d) {
		return new SimplePoint2D(x - _d.getWidth(), y - _d.getHeight());
	}
	@Override
	public final SimplePoint2D minus(final Coordinate.TwoDims _c) {
		return new SimplePoint2D(x - _c.getFirst(), y - _c.getSecond());
	}
	@Override
	public final SimplePoint2D minus(final Dimension.TwoDims _d) {
		return new SimplePoint2D(x - _d.getWidth(), y - _d.getHeight());
	}
//	@Override
	public final SimplePoint2D minus(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new SimplePoint2D(x - _v.getNumber(0).doubleValue(), y - _v.getNumber(1).doubleValue());
	}
//	@Override
	public final SimplePoint2D minus(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new SimplePoint2D(x - _v.get(0), y - _v.get(1));
	}

	@Override
	public final SimplePoint2D minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final double _u, final double _v) {
		x -= _u;
		y -= _v;
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final double[] _v) {
		assert(_v.length >= 2);
		x -= _v[0];
		y -= _v[1];
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Number _u, final Number _v) {
		x -= _u.doubleValue();
		y -= _v.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Point2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Vector2D _d) {
		x -= _d.getX();
		y -= _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Dimension2D _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Coordinate.TwoDims _pt) {
		x -= _pt.getFirst();
		y -= _pt.getSecond();
		return this;
	}
	@Override
	public final SimplePoint2D minusEquals(final Dimension.TwoDims _dim) {
		x -= _dim.getWidth();
		y -= _dim.getHeight();
		return this;
	}
//	@Override
	public final SimplePoint2D minusEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final SimplePoint2D minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x -= _v.get(0);
		y -= _v.get(1);
		return this;
	}

	@Override
	public final SimplePoint2D times(final double _t) {
		return new SimplePoint2D(x * _t, y * _t);
	}
	public final SimplePoint2D times(final double _u, final double _v) {
		return new SimplePoint2D(x * _u, y * _v);
	}
	@Override
	public final SimplePoint2D times(final double[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x * _v[0], y * _v[1]);
	}
	@Override
	public final SimplePoint2D times(final Number _t) {
		return new SimplePoint2D(x * _t.doubleValue(), y * _t.doubleValue());
	}
	@Override
	public final SimplePoint2D times(final Number _x, final Number _y) {
		return new SimplePoint2D(x * _x.doubleValue(), y * _y.doubleValue());
	}
	@Override
	public final SimplePoint2D times(final Number[] _v) {
		assert(_v.length >= 2);
		return new SimplePoint2D(x * _v[0].doubleValue(), y * _v[1].doubleValue());
	}
	@Override
	public final SimplePoint2D times(final Vector2D _d) {
		return new SimplePoint2D(x * _d.getX(), y * _d.getY());
	}
	@Override
	public final SimplePoint2D times(final Dimension2D _dim) {
		return new SimplePoint2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
	@Override
	public final SimplePoint2D times(final Dimension.TwoDims _dim) {
		return new SimplePoint2D(x * _dim.getWidth(), y * _dim.getHeight());
	}
//	@Override
	public final SimplePoint2D times(final NumberVector _v) {
		assert(_v.size() >= 2);
		return new SimplePoint2D(x * _v.getNumber(0).doubleValue(), y * _v.getNumber(1).doubleValue());
	}
//	@Override
	public final SimplePoint2D times(final DoubleVector _v) {
		assert(_v.size() >= 2);
		return new SimplePoint2D(x * _v.get(0), y * _v.get(1));
	}

	@Override
	public final SimplePoint2D timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final double _u, final double _v) {
		x *= _u;
		y *= _v;
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final double[] _v) {
		assert(_v.length >= 2);
		x *= _v[0];
		y *= _v[1];
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Number _u, final Number _v) {
		x *= _u.doubleValue();
		y *= _v.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Vector2D _d) {
		x *= _d.getX();
		y *= _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Dimension2D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		return this;
	}
	@Override
	public final SimplePoint2D timesEquals(final Dimension.TwoDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		return this;
	}
//	@Override
	public final SimplePoint2D timesEquals(final NumberVector _v) {
		assert(_v.size() >= 2);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final SimplePoint2D timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 2);
		x *= _v.get(0);
		y *= _v.get(1);
		return this;
	}

	@Override
	public final SimplePoint2D divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _t, y / _t);
	}
	public final SimplePoint2D divides(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _u, y / _v);
	}
	@Override
	public final SimplePoint2D divides(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _v[0], y / _v[1]);
	}
	@Override
	public final SimplePoint2D divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _t.doubleValue(), y / _t.doubleValue());
	}
	@Override
	public final SimplePoint2D divides(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _u.doubleValue(), y / _v.doubleValue());
	}
	@Override
	public final SimplePoint2D divides(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _v[0].doubleValue(), y / _v[1].doubleValue());
	}
	@Override
	public final SimplePoint2D divides(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _d.getX(), y / _d.getY());
	}
	@Override
	public final SimplePoint2D divides(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _d.getWidth(), y / _d.getHeight());
	}
	@Override
	public final SimplePoint2D divides(Dimension.TwoDims _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _dim.getWidth(), y / _dim.getHeight());
	}
//	@Override
	public final SimplePoint2D divides(final NumberVector _v) {
		assert(_v.size() >= 2);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _v.getNumber(0).doubleValue(), y / _v.getNumber(1).doubleValue());
	}
//	@Override
	public final SimplePoint2D divides(final DoubleVector _v) {
		assert(_v.size() >= 2);
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		return new SimplePoint2D(x / _v.get(0), y / _v.get(1));
	}

	@Override
	public final SimplePoint2D dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		return this;
	}
	public final SimplePoint2D dividesEquals(final double _u, final double _v) {
		if(_u == 0 || _v == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final double[] _v) {
		assert(_v.length >= 2);
		if(_v[0] == 0 || _v[1] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Number _u, final Number _v) {
		if(_u.doubleValue() == 0 || _v.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _u.doubleValue();
		y /= _v.doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Number[] _v) {
		assert(_v.length >= 2);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Vector2D _d) {
		if(_d.getX() == 0 || _d.getY() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Dimension2D _d) {
		if(_d.getWidth() == 0 || _d.getHeight() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getWidth();
		y /= _d.getHeight();
		return this;
	}
	@Override
	public final SimplePoint2D dividesEquals(final Dimension.TwoDims _dim) {
		x *= _dim.getWidth();
		y *= _dim.getHeight();
		return this;
	}
//	@Override
	public final SimplePoint2D dividesEquals(final NumberVector _v) {
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		return this;
	}
//	@Override
	public final SimplePoint2D dividesEquals(final DoubleVector _v) {
		if(_v.get(0) == 0 || _v.get(1) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		return this;
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

	@Override
	public final boolean 		isDifferent(final double _u, final double _v) {
		return (x != _u || y != _v) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point2D _d) {
		return (x != _d.getX() || y != _d.getY()) ? true : false;
	}

	@Override
	public final SimplePoint2D clone() {
		return new SimplePoint2D(x, y);
	}
	@Override
	public final SimplePoint2D cloneEditable() {
		return new SimplePoint2D(x, y);
	}
	@Override
	public final SimplePoint2D abs() {
		return new SimplePoint2D(Math.abs(x), Math.abs(y));
	}
	@Override
	public final SimplePoint2D negate() {
		return new SimplePoint2D(-x, -y);
	}
	@Override
	public final SimplePoint2D normalized() {
		double length = Math.sqrt(x*x+y*y);
		if(length < 1e-6)
			return this;
		double invLength = 1.0f / length;
		return new SimplePoint2D(x * invLength, y * invLength);
	}

	@Override
	public int 					hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
		result = prime * result + java.lang.Double.doubleToLongBits(y);
		return (int) result;
	}

	@Override
	public boolean 				equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		SimplePoint2D other = (SimplePoint2D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public int 					compareTo(Object obj) {
		if(this == obj)
			return 0;
		if(obj == null)
			return 1;
		if(getClass() != obj.getClass())
			return 1;
		SimplePoint2D other = (SimplePoint2D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return java.lang.Double.doubleToLongBits(y) > java.lang.Double.doubleToLongBits(other.y) ? 1 : -1;
		return -1;
	}

	@Override
	public String 				toString() {
		return "(" + x + "," + y + ")";
	}
	@Override
	public String 				toString(NumberFormat _nf) {
		return "( " + _nf.format(x) + ";" + _nf.format(y) + " )";
	}
	@Override
	public String 				toString(DecimalFormat _df) {
		return "( " + _df.format(x) + ";" + _df.format(y) + " )";
	}

}
