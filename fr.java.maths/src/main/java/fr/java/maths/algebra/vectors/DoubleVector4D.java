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
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.algebra.vector.generic.Vector4D;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.Tensors;

public class DoubleVector4D implements DoubleVector.Editable, Vector4D.Editable {
	private static final long serialVersionUID = 1L;

	public final static DoubleVector4D zero() {
		return new DoubleVector4D(0, 0, 0, 0);
	}
	public final static DoubleVector4D unitary() {
		return new DoubleVector4D(1, 1, 1, 1);
	}

	private double x, y, z, w;

	public DoubleVector4D() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	public DoubleVector4D(final double _x, final double _y) {
		x = _x;
		y = _y;
		z = 0;
		w = 0;
	}
	public DoubleVector4D(final double _x, final double _y, final double _z) {
		x = _x;
		y = _y;
		z = _z;
		w = 0;
	}
	public DoubleVector4D(final double _x, final double _y, final double _z, final double _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}
	public DoubleVector4D(final DoubleVector3D _d, final double _w) {
		x = _d.getX();
		y = _d.getY();
		z = _d.getZ();
		w = _w;
	}
	public DoubleVector4D(final DoubleVector4D _d) {
		x = _d.x;
		y = _d.y;
		z = _d.z;
		w = _d.w;
	}

	public double x() { return x; }
	public double y() { return y; }
	public double z() { return z; }
	public double w() { return w; }

	/**
	 * TENSOR METHODS
	**/
	@Override
	public final boolean 		isDirect()  { return false; }
	@Override
	public final double[] 		getArray()  { return new double[] { x, y, z, w }; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
    public final DoubleStream 	getStream() { return DoubleStream.of(x, y, z, w); }

	@Override
	public final int 			getCapacity() {
		return 4;
	}
	@Override
	public final Number 		getNumber(int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public double 				getValue(int _index) {
		switch((int) _index) {
		case 0  : return x;
		case 1  : return y;
		case 2  : return z;
		case 3  : return w;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public void 				setValue(double _value, int _index) {
		switch((int) _index) {
		case 0  : x = _value;
		case 1  : y = _value;
		case 2  : z = _value;
		case 3  : w = _value;
		default : throw new IllegalArgumentException();
		}
	}


	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 4);
		set(_value, _coords[0]);
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 4);
		return get(_coords[0]);
	}

	@Override
	public final void			setSlice(final DoubleTensor _tensor, final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceView(final int... _slice) {
		assert(_slice.length == 1);
		return Tensors.Double.allocate(new double[] { x, y, z, w }, 4);
	}
	@Override
	public final DoubleTensor 	getSliceCopy(final int... _slice) {
		assert(_slice.length == 1 && _slice[0] < 4);
		return Tensors.Double.allocate(new double[] { x, y, z, w }, 4);
	}

	@Override
	public void 				reshape(int... _shape) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * VECTOR METHODS
	**/
	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
		z = _value.doubleValue();
		w = 1.0f;
	}
	@Override
	public final void 			set(final Number[] _value) {
		assert(_value.length >= 4);
		x = _value[0].doubleValue();
		y = _value[1].doubleValue();
		z = _value[2].doubleValue();
		w = _value[3].doubleValue();
	}
	@Override
	public final void 			set(final NumberVector _vector) {
		assert (_vector.size() >= 4);
		x = _vector.getNumber(0).doubleValue();
		y = _vector.getNumber(1).doubleValue();
		z = _vector.getNumber(2).doubleValue();
		z = _vector.getNumber(3).doubleValue();
	}

	@Override
	public final void 			set(final double _value) {
		x = _value;
		y = _value;
		z = _value;
		w = 1.0f;
	}
	@Override
	public final void 			set(final double[] _value) {
		assert(_value.length >= 4);
		x = _value[0];
		y = _value[1];
		z = _value[2];
		w = _value[3];
	}
	@Override
	public final void 			set(final DoubleVector _vector) {
		assert (_vector.size() >= 4);
		x = _vector.get(0);
		y = _vector.get(1);
		z = _vector.get(2);
		z = _vector.get(3);
	}

	public final void 			set(final Vector4D _d) {
		x = _d.getX();
		y = _d.getY();
		z = _d.getZ();
		w = _d.getW();
	}

	public final void 			set(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public final void 			set(final Number x, final Number y, final Number z, final Number w) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
		this.z = z.doubleValue();
		this.w = w.doubleValue();
	}

	@Override
	public final void 			setNumber(final Number _value, final int _index) {
		if(_index == 0)
			x = _value.doubleValue();
		else if(_index == 1)
			y = _value.doubleValue();
		else if(_index == 2)
			z = _value.doubleValue();
		else
			w = _value.doubleValue();
	}

	@Override
	public final void 			set(final double _value, final int _index) {
		if(_index == 0)
			x = _value;
		else if(_index == 1)
			y = _value;
		else if(_index == 2)
			z = _value;
		else
			w = _value;
	}
	@Override
	public final double 		get(final int _index) {
		return (_index == 0) ? x : (_index == 1) ? y : (_index == 2) ? z : w;		
	}

	public final void 			setX(final double _x) {
		this.x = _x;
	}
	public final double 		getX() {
		return x;
	}

	public final void 			setY(final double _y) {
		this.y = _y;
	}
	public final double 		getY() {
		return y;
	}

	public final void 			setZ(final double _z) {
		this.z = _z;
	}
	public final double 		getZ() {
		return z;
	}

	public final void 			setW(final double _w) {
		this.w = _w;
	}
	public final double 		getW() {
		return w;
	}

	@Override
	public double 				getMagnitude() {
		return Math.sqrt(w*w*x*x + w*w*y*y + w*w*z*z);
	}
	@Override
	public void 				setMagnitude(double _mag) {
        double old_mag = Math.sqrt((w*w*x*x + w*w*y*y + w*w*z*z));
        
        x = x * _mag / old_mag;
        y = y * _mag / old_mag;
        z = z * _mag / old_mag;
	}

	@Override
	public final DoubleVector4D 		plus(final double _t) {
		return new DoubleVector4D(x+_t, y+_t, z+_t, w+_t);
	}
	@Override
	public final DoubleVector4D 		plus(final double _t, final double _u, final double _v, final double _w) {
		return new DoubleVector4D(x+_t, y+_u, z+_v, w+_w);
	}
	@Override
	public final DoubleVector4D 		plus(final double[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x+_v[0], y+_v[1], z+_v[2], w+_v[3]);
	}
	@Override
	public final DoubleVector4D 		plus(final Number _t) {
		return new DoubleVector4D(x+_t.doubleValue(), y+_t.doubleValue(), z+_t.doubleValue(), w+_t.doubleValue());
	}
	@Override
	public final DoubleVector4D 		plus(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new DoubleVector4D(x+_x.doubleValue(), y+_y.doubleValue(), z+_z.doubleValue(), w+_w.doubleValue());
	}
	@Override
	public final DoubleVector4D 		plus(final Number[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x+_v[0].doubleValue(), y+_v[1].doubleValue(), z+_v[2].doubleValue(), w+_v[3].doubleValue());
	}
	public final DoubleVector4D 		plus(final Vector4D _d) {
		return new DoubleVector4D(x+_d.getX(), y+_d.getY(), z+_d.getZ(), w+_d.getW());
	}
	@Override
	public final DoubleVector4D 		plus(final NumberVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x+_v.getNumber(0).doubleValue(), y+_v.getNumber(1).doubleValue(), z+_v.getNumber(2).doubleValue(), w+_v.getNumber(3).doubleValue());
	}
	@Override
	public final DoubleVector4D 		plus(final DoubleVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x+_v.get(0), y+_v.get(1), z+_v.get(2), w+_v.get(3));
	}

	@Override
	public final DoubleVector4D 		plusEquals(final double _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	public final DoubleVector4D 		plusEquals(final double _t, final double _u, final double _v, final double _w) {
		x += _t;
		y += _u;
		z += _v;
		w += _w;
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final double[] _v) {
		assert(_v.length >= 4);
		x += _v[0];
		y += _v[1];
		z += _v[2];
		w += _v[3];
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		z += _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		x += _x.doubleValue();
		y += _y.doubleValue();
		z += _z.doubleValue();
		w += _w.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		z += _v[2].doubleValue();
		w += _v[3].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final NumberVector _v) {
		assert(_v.size() >= 4);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		z += _v.getNumber(2).doubleValue();
		w += _v.getNumber(3).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final DoubleVector _v) {
		assert(_v.size() >= 4);
		x += _v.get(0);
		y += _v.get(1);
		z += _v.get(2);
		w += _v.get(3);
		return this;
	}
	@Override
	public final DoubleVector4D 		plusEquals(final Vector4D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		w += _d.getW();
		return this;
	}

	@Override
	public final DoubleVector4D 		minus(final double _t) {
		return new DoubleVector4D(x-_t, y-_t, z-_t, w-_t);
	}
	@Override
	public final DoubleVector4D 		minus(final double _t, final double _u, final double _v, final double _w) {
		return new DoubleVector4D(x-_t, y-_u, z-_v, w-_w);
	}
	@Override
	public final DoubleVector4D 		minus(final double[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x-_v[0], y-_v[1], z-_v[2], w-_v[3]);
	}
	@Override
	public final DoubleVector4D 		minus(final Number _t) {
		return new DoubleVector4D(x-_t.doubleValue(), y-_t.doubleValue(), z-_t.doubleValue(), w-_t.doubleValue());
	}
	@Override
	public final DoubleVector4D 		minus(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new DoubleVector4D(x-_x.doubleValue(), y-_y.doubleValue(), z-_z.doubleValue(), w-_w.doubleValue());
	}
	@Override
	public final DoubleVector4D 		minus(final Number[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x-_v[0].doubleValue(), y-_v[1].doubleValue(), z-_v[2].doubleValue(), w-_v[3].doubleValue());
	}
	@Override
	public final DoubleVector4D 		minus(final Vector4D _d) {
		return new DoubleVector4D(x-_d.getX(), y-_d.getY(), z-_d.getZ(), w-_d.getW());
	}
	@Override
	public final DoubleVector4D 		minus(final NumberVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x-_v.getNumber(0).doubleValue(), y-_v.getNumber(1).doubleValue(), z-_v.getNumber(2).doubleValue(), w-_v.getNumber(3).doubleValue());
	}
	@Override
	public final DoubleVector4D 		minus(final DoubleVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x-_v.get(0), y-_v.get(1), z-_v.get(2), w-_v.get(3));
	}

	@Override
	public final DoubleVector4D 		minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		w -= _t;
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final double _t, final double _u, final double _v, final double _w) {
		x -= _t;
		y -= _u;
		z -= _v;
		w -= _w;
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final double[] _v) {
		assert(_v.length >= 4);
		x -= _v[0];
		y -= _v[1];
		z -= _v[2];
		w -= _v[3];
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		z -= _t.doubleValue();
		w -= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D			minusEquals(Number _x, Number _y, Number _z, Number _w) {
		x -= _x.doubleValue();
		y -= _y.doubleValue();
		z -= _z.doubleValue();
		w -= _w.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		z -= _v[2].doubleValue();
		w -= _v[3].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final Vector4D _v) {
		assert(_v.size() >= 4);
		x -= _v.getX();
		y -= _v.getY();
		z -= _v.getZ();
		w -= _v.getW();
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final NumberVector _v) {
		assert(_v.size() >= 4);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		z -= _v.getNumber(2).doubleValue();
		w -= _v.getNumber(3).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 4);
		x -= _v.get(0);
		y -= _v.get(1);
		z -= _v.get(2);
		w -= _v.get(3);
		return this;
	}
	
	@Override
	public final DoubleVector4D 		times(final double _t) {
		return new DoubleVector4D(x*_t, y*_t, z*_t, w*_t);
	}
	@Override
	public final DoubleVector4D 		times(final double _t, final double _u, final double _v, final double _w) {
		return new DoubleVector4D(x*_t, y*_u, z*_v, w*_w);
	}
	@Override
	public final DoubleVector4D 		times(final double[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x*_v[0], y*_v[1], z*_v[2], w*_v[3]);
	}
	@Override
	public final DoubleVector4D 		times(final Number _t) {
		return new DoubleVector4D(x*_t.doubleValue(), y*_t.doubleValue(), z*_t.doubleValue(), w*_t.doubleValue());
	}
	@Override
	public final DoubleVector4D 		times(final Number _x, final Number _y, final Number _z, final Number _w) {
		return new DoubleVector4D(x*_x.doubleValue(), y*_y.doubleValue(), z*_z.doubleValue(), w*_w.doubleValue());
	}
	@Override
	public final DoubleVector4D 		times(final Number[] _v) {
		assert(_v.length >= 4);
		return new DoubleVector4D(x*_v[0].doubleValue(), y*_v[1].doubleValue(), z*_v[2].doubleValue(), w*_v[3].doubleValue());
	}
	public final DoubleVector4D 		times(final Vector4D _d) {
		return new DoubleVector4D(x*_d.getX(), y*_d.getY(), z*_d.getZ(), w*_d.getW());
	}
	@Override
	public final DoubleVector4D 		times(final NumberVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x*_v.getNumber(0).doubleValue(), y*_v.getNumber(1).doubleValue(), z*_v.getNumber(2).doubleValue(), w*_v.getNumber(3).doubleValue());
	}
	@Override
	public final DoubleVector4D 		times(final DoubleVector _v) {
		assert(_v.size() >= 4);
		return new DoubleVector4D(x*_v.get(0), y*_v.get(1), z*_v.get(2), w*_v.get(3));
	}


	@Override
	public final DoubleVector4D 		timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		w *= _t;
		return this;
	}
	public final DoubleVector4D 		timesEquals(final double _t, final double _u, final double _v, final double _w) {
		x *= _t;
		y *= _u;
		z *= _v;
		w *= _w;
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final double[] _v) {
		assert(_v.length >= 4);
		x *= _v[0];
		y *= _v[1];
		z *= _v[2];
		w *= _v[3];
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		z *= _t.doubleValue();
		w *= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		x *= _x.doubleValue();
		y *= _y.doubleValue();
		z *= _z.doubleValue();
		w *= _w.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final Number[] _v) {
		assert(_v.length >= 4);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		z *= _v[2].doubleValue();
		w *= _v[3].doubleValue();
		return this;
	}
	public final DoubleVector4D 		timesEquals(final Vector4D _d) {
		x *= _d.getX();
		y *= _d.getY();
		z *= _d.getZ();
		w *= _d.getW();
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final NumberVector _v) {
		assert(_v.size() >= 4);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		z *= _v.getNumber(2).doubleValue();
		w *= _v.getNumber(3).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 4);
		x *= _v.get(0);
		y *= _v.get(1);
		z *= _v.get(2);
		w *= _v.get(3);
		return this;
	}

	@Override
	public final DoubleVector4D 		divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_t, y/_t, z/_t, w/_t);
	}
	public final DoubleVector4D 		divides(final double _t, final double _u, final double _v, final double _w) {
		if(_t == 0 || _u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_t, y/_u, z/_v, w/_w);
	}
	@Override
	public final DoubleVector4D 		divides(final double[] _v) {
		assert(_v.length >= 4);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0 || _v[3] == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_v[0], y/_v[1], z/_v[2], w/_v[3]);
	}
	@Override
	public final DoubleVector4D 		divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_t.doubleValue(), y/_t.doubleValue(), z/_t.doubleValue(), w/_t.doubleValue());
	}
	@Override
	public final DoubleVector4D 		divides(final Number _x, final Number _y, final Number _z, final Number _w) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_x.doubleValue(), y/_y.doubleValue(), z/_z.doubleValue(), w/_w.doubleValue());
	}
	@Override
	public final DoubleVector4D 		divides(final Number[] _v) {
		assert(_v.length >= 4);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0 || _v[3].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_v[0].doubleValue(), y/_v[1].doubleValue(), z/_v[2].doubleValue(), w/_v[3].doubleValue());
	}
	public final DoubleVector4D 		divides(final Vector4D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0 || _d.getW() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_d.getX(), y/_d.getY(), z/_d.getZ(), w/_d.getW());
	}
	@Override
	public final DoubleVector4D 		divides(final NumberVector _v) {
		assert(_v.size() >= 4);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0 || _v.getNumber(3).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_v.getNumber(0).doubleValue(), y/_v.getNumber(1).doubleValue(), z/_v.getNumber(2).doubleValue(), w/_v.getNumber(3).doubleValue());
	}
	@Override
	public final DoubleVector4D 		divides(final DoubleVector _v) {
		assert(_v.size() >= 4);
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0 || _v.get(3) == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector4D(x/_v.get(0), y/_v.get(1), z/_v.get(2), w/_v.get(3));
	}

	@Override
	public final DoubleVector4D 		dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		z /= _t;
		w /= _t;	
		return this;
	}
	public final DoubleVector4D 		dividesEquals(final double _t, final double _u, final double _v, final double _w) {
		if(_t == 0 || _u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _u;
		z /= _v;
		w /= _w;
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final double[] _v) {
		assert(_v.length >= 4);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0 || _v[3] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		z /= _v[2];
		w /= _v[3];
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		z /= _t.doubleValue();
		w /= _t.doubleValue();	
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final Number _x, final Number _y, final Number _z, final Number _w) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0 || _w.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _x.doubleValue();
		y /= _y.doubleValue();
		z /= _z.doubleValue();
		w /= _w.doubleValue();	
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final Number[] _v) {
		assert(_v.length >= 4);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0 || _v[3].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		z /= _v[2].doubleValue();
		w /= _v[3].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final Vector4D _d) {
		if(_d.getX() == 0 || _d.getY() == 0 || _d.getZ() == 0 || _d.getW() == 0) throw new RuntimeException("Divide by 0");
		x /= _d.getX();
		y /= _d.getY();
		z /= _d.getZ();
		w /= _d.getW();
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final NumberVector _v) {
		assert(_v.size() >= 4);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0 || _v.getNumber(3).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		z /= _v.getNumber(2).doubleValue();
		w /= _v.getNumber(3).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector4D 		dividesEquals(final DoubleVector _v) {
		assert(_v.size() >= 4);
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0 || _v.get(3) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		z /= _v.get(2);
		w /= _v.get(3);
		return this;
	}


	@Override
	public final boolean 		isValid() {
		return !Double.isNaN(x) && !Double.isInfinite(x) && !Double.isNaN(y) && !Double.isInfinite(y) && !Double.isNaN(z) && !Double.isInfinite(z) && !Double.isNaN(w) && !Double.isInfinite(w);
	}

	@Override
	public final boolean 		isEqual(final Number _t) {
		return (x == _t.doubleValue() && y == _t.doubleValue() && z == _t.doubleValue() && w == _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Number[] _v) {
		assert(_v.length == 4);
		return (x == _v[0].doubleValue() && y == _v[1].doubleValue() && z == _v[2].doubleValue() && w == _v[3].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final NumberVector _v) {
		assert(_v.size() == 4);
		return (x == _v.getNumber(0).doubleValue() && y == _v.getNumber(1).doubleValue() && z == _v.getNumber(2).doubleValue() && w == _v.getNumber(3).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t && y == _t && z == _t && w == _t) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final double[] _v) {
		assert(_v.length == 4);
		return (x == _v[0] && y == _v[1] && z == _v[2] && w == _v[3]) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final DoubleVector _v) {
		assert(_v.size() == 4);
		return (x == _v.get(0) && y == _v.get(1) && z == _v.get(2) && w == _v.get(3)) ? true : false;
	}

	public final boolean 		isEqual(final double _u, final double _v, final double _w, final double _z) {
		return (x == _u && y == _v && z == _w && w == _z) ? true : false;
	}
	public final boolean 		isEqual(final Vector4D _other) {
		return (x == _other.getX() && y == _other.getY() && z == _other.getZ() && w == _other.getW()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Number _t) {
		return (x != _t.doubleValue() || y != _t.doubleValue() || z != _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Number[] _v) {
		assert(_v.length == 4);
		return (x != _v[0].doubleValue() || y != _v[1].doubleValue() || z != _v[2].doubleValue() || w != _v[3].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberVector _v) {
		assert(_v.size() == 4);
		return (x != _v.getNumber(0).doubleValue() || y != _v.getNumber(1).doubleValue() || z != _v.getNumber(2).doubleValue() || w != _v.getNumber(3).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _v) {
		assert(_v.length == 4);
		return (x != _v[0] || y != _v[1] || z != _v[2] || w != _v[3]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleVector _v) {
		assert(_v.size() == 4);
		return (x != _v.get(0) || y != _v.get(1) || z != _v.get(2) || w != _v.get(3)) ? true : false;
	}

	public final boolean 		isDifferent(final double _u, final double _v, final double _w, final double _z) {
		return (x != _u || y != _v || z != _w || w != _z) ? true : false;
	}
	public final boolean 		isDifferent(final Vector4D _other) {
		return (x != _other.getX() || y != _other.getY() || z != _other.getZ() || w != _other.getW()) ? true : false;
	}
	

	@Override
	public final boolean 		isColinear(final NumberVector _other) {
		return false;
	}
	@Override
	public final boolean 		isColinear(final DoubleVector _vector) {
		return false;
	}
	public final boolean 		isColinear(final Vector4D _other) {
		return false;
	}

	public final boolean 		isColinearToSegment(final Vector4D _A, final Vector4D _B) {
		return false;
	}
	public final boolean 		isColinearToLine(final Vector4D _P, final Vector4D _N) {
		return false;
	}


	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return false;
	}
	@Override
	public final boolean 		isOrthogonal(final DoubleVector _vector) {
		return false;
	}
	public final boolean 		isOrthogonal(final Vector4D _other) {
		return false;
	}

	public final boolean 		isOrthogonalToSegment(final Vector4D _A, final Vector4D _B) {
		return false;
	}
	public final boolean 		isOrthogonalToLine(final Vector4D _P, final Vector4D _N) {
		return false;
	}

	@Override
	public final DoubleVector4D clone() {
		return new DoubleVector4D(x, y, z, w);
	}
	@Override
	public final DoubleVector4D cloneEditable() {
		return new DoubleVector4D(x, y, z, w);
	}

	@Override
	public final DoubleVector4D abs() {
		return new DoubleVector4D(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}
	@Override
	public final DoubleVector4D negate() {
		return new DoubleVector4D(-x, -y, -z, -w);
	}
	@Override
	public final DoubleVector4D normalized() {
		double length = norm();
		if(length < Numbers.EPSILON)
			return this;
		double invLength = 1.0f / length;
		return new DoubleVector4D(x * invLength, y * invLength, z * invLength, w * invLength);
	}

	@Override
	public Vector3D 			downgrade() {
		return new DoubleVector3D(getW() * getX(), getW() * getY(), getW() * getZ());
	}

	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return x*x+y*y+z*z;

		case Euclidian:			return Math.sqrt(x*x+y*y+z*z);

		case Manhattan:			return Math.abs( x ) + Math.abs( y ) + Math.abs( z );

		case P:					return Math.pow(  Math.pow( Math.abs( x ), p) +  Math.pow( Math.abs( y ), p) +  Math.pow( Math.abs( z ), p), 1d/p);

		case Maximum:			return Math.abs( x ) > Math.abs( y ) ? 
											( Math.abs( x ) > Math.abs( z ) ? 
													( Math.abs( x ) > Math.abs( w ) ? Math.abs( x ) : Math.abs( w ) )
													:
													( Math.abs( z ) > Math.abs( w ) ? Math.abs( z ) : Math.abs( w ) )
											)
											:
											( Math.abs( y ) > Math.abs( z ) ? 
													( Math.abs( y ) > Math.abs( w ) ? Math.abs( y ) : Math.abs( w ) )
													:
													( Math.abs( z ) > Math.abs( w ) ? Math.abs( z ) : Math.abs( w ) ) )
											;
								
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
		return x*x + y*y + z*z + w*w;
	}

	@Override
	public final Number 		dotProduct(NumberVector _v) {
		return 0;
	}
	public final static double 	dotProduct(final Vector4D _a, final Vector4D _b) {
		return -1;//a.x * b.x + a.y * b.y;
	}

	@Override
	public final NumberVector 		crossProduct(NumberVector _v) {
		throw new NotYetImplementedException();
	}
	public final static DoubleVector4D crossProduct(final DoubleVector4D _a, final DoubleVector4D _b) {
		return new DoubleVector4D(  _a.y*_b.x - _a.x*_b.y, 
							_a.x*_b.y - _a.y*_b.x, 0, 0  );
	}


	public final double euclydianDistance(final Vector4D _v) {
		return (double) Math.sqrt((x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ()) + (w-_v.getW())*(w-_v.getW()));
	}
	public final double euclydianDistance2(final Vector4D _v) {
		return (double) (x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ()) + (w-_v.getW())*(w-_v.getW());
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		DoubleVector4D other = (DoubleVector4D) obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		if(Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
			return false;
		return true;
	}


	@Override
	public int compareTo(Object obj) {
		if(this == obj)
			return 0;
		if(obj == null)
			return 1;
		if(getClass() != obj.getClass())
			return 1;
		DoubleVector4D other = (DoubleVector4D) obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return Double.doubleToLongBits(x) > Double.doubleToLongBits(other.x) ? 1 : -1;
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return Double.doubleToLongBits(y) > Double.doubleToLongBits(other.y) ? 1 : -1;
		if(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return Double.doubleToLongBits(z) > Double.doubleToLongBits(other.z) ? 1 : -1;
		if(Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w))
			return Double.doubleToLongBits(w) > Double.doubleToLongBits(other.w) ? 1 : -1;
		return -1;
	}


	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + Double.doubleToLongBits(x);
		result = prime * result + Double.doubleToLongBits(y);
		result = prime * result + Double.doubleToLongBits(z);
		result = prime * result + Double.doubleToLongBits(w);
		return (int) result;
	}


	@Override
	public final String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}
	@Override
	public final String toString(NumberFormat _nf) {
		return "(" + _nf.format(x) + "," + _nf.format(y) + "," + _nf.format(z) + "," + _nf.format(w) + ")";
	}

}
