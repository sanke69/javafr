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
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.algebra.vector.generic.Vector4D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.Tensors;

public class DoubleVector3D implements DoubleVector.Editable, Point3D.Editable, Vector3D.Editable {
	private static final long serialVersionUID = 1L;

	private double x, y, z;

	public DoubleVector3D() {
		 x = java.lang.Double.NaN;
		 y = java.lang.Double.NaN;
		 z = java.lang.Double.NaN;
	}
	public DoubleVector3D(double _x, double _y, double _z) {
		 x = _x;
		 y = _y;
		 z = _z;
	}
	public DoubleVector3D(DoubleVector3D _copy) {
		 this(_copy.x, _copy.y, _copy.z);
	}

	/**
	 * TENSOR METHODS
	**/@Override
	public int 					getCapacity() {
		return 3;
	}
	
	@Override
	public final boolean 		isDirect()  { return false; }
	@Override
	public final double[] 		getArray()  { return new double[] { x, y, z }; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
    public final DoubleStream 	getStream() { return DoubleStream.of(x, y, z); }

	@Override
	public final Number 		getNumber(int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public final double 		getValue(int _index) {
		switch((int) _index) {
		case 0  : return x;
		case 1  : return y;
		case 2  : return z;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 3);
		return get(_coords[0]);
	}
	@Override
	public final void 			setValue(double _value, int _index) {
		switch((int) _index) {
		case 0  : x = _value;
		case 1  : y = _value;
		case 2  : z = _value;
		default : throw new IllegalArgumentException();
		}
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < 3);
		set(_value, _coords[0]);
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
		assert(_slice.length == 1 && _slice[0] < 3);
		return Tensors.Double.allocate(new double[] { x, y, z }, 3);
	}

	@Override
	public final void 			reshape(int... _shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void 				setMagnitude(double _mag) {
        double old_mag = Math.sqrt((x*x + y*y + z*z));
        
        x = x * _mag / old_mag;
        y = y * _mag / old_mag;
        z = z * _mag / old_mag;
	}
	@Override
	public double 				getMagnitude() {
		return Math.sqrt((x*x + y*y + z*z));
	}

	@Override
	public Vector3D 			normalize(double _norm) {
		double length = Math.sqrt(x*x + y*y + z*z), Q = _norm / length;
		return new DoubleVector3D(x * Q, y * Q, z * Q);
	}

	/**
	 * VECTOR METHODS
	**/
	@Override
	public final int    		size() { return 3; }

	@Override
	public final void 			set(final Number _value) {
		x = _value.doubleValue();
		y = _value.doubleValue();
		z = _value.doubleValue();
	}
	@Override
	public final void 			set(final Number[] _v) {
		assert (_v.length >= 3);
		x = _v[0].doubleValue();
		y = _v[1].doubleValue();
		z = _v[2].doubleValue();
	}
	@Override
	public final void 			set(final NumberVector _vector) {
		assert (_vector.size() >= 3);
		x = _vector.getNumber(0).doubleValue();
		y = _vector.getNumber(1).doubleValue();
		z = _vector.getNumber(2).doubleValue();
	}

	@Override
	public final void 			set(final double _value) {
		x = _value;
		y = _value;
		z = _value;
	}
	@Override
	public final void 			set(final double[] _v) {
		assert (_v.length >= 3);
		x = _v[0];
		y = _v[1];
		z = _v[2];
	}
	@Override
	public final void 			set(final DoubleVector _vector) {
		assert (_vector.size() >= 3);
		x = _vector.get(0);
		y = _vector.get(1);
		z = _vector.get(2);
	}

	@Override
	public void set(Number _x, Number _y, Number _z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void 			set(final double _x, final double _y, final double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	@Override
	public final void 			set(final Point3D _pt) {
		x = _pt.getX();
		y = _pt.getY();
		z = _pt.getZ();
	}
	@Override
	public final void 			set(final Vector3D _d) {
		x = _d.getX();
		y = _d.getY();
		z = _d.getZ();
	}

	@Override
	public final void 			setNumber(final Number _value, final int _index) {
		if(_index == 0)
			x = _value.doubleValue();
		else if(_index == 1)
			y = _value.doubleValue();
		else
			z = _value.doubleValue();
	}

	@Override
	public final void 			set(final double _value, final int _index) {
		if(_index == 0)
			x = _value;
		else if(_index == 1)
			y = _value;
		else
			z = _value;
	}
	@Override
	public final double 		get(final int _index) {
		return (_index == 0) ? x : (_index == 1) ? y : z;		
	}

	@Override
	public final void 			setX(double _x) {
		x = _x;
	}
	@Override
	public final double 		getX() {
		return x;
	}

	@Override
	public final void 			setY(double _y) {
		y = _y;
	}
	@Override
	public final double 		getY() {
		return y;
	}

	@Override
	public final void 			setZ(double _z) {
		z = _z;
	}
	@Override
	public final double 		getZ() {
		return z;
	}

	@Override
	public final DoubleVector3D 		plus(final double _t) {
		return new DoubleVector3D(x+_t, y+_t, z+_t);
	}
	@Override
	public final DoubleVector3D 		plus(final double _u, final double _v, final double _w) {
		return new DoubleVector3D(x+_u, y+_v, z+_w);
	}
	@Override
	public final DoubleVector3D 		plus(final double[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x+_v[0], y+_v[1], z+_v[2]);
	}
	@Override
	public final DoubleVector3D 		plus(final Number _t) {
		return new DoubleVector3D(x+_t.doubleValue(), y+_t.doubleValue(), z+_t.doubleValue());
	}
	@Override
	public final DoubleVector3D 		plus(final Number _x, final Number _y, final Number _z) {
		return new DoubleVector3D(x+_x.doubleValue(), y+_y.doubleValue(), z+_z.doubleValue());
	}
	@Override
	public final DoubleVector3D 		plus(final Number[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x+_v[0].doubleValue(), y+_v[1].doubleValue(), z+_v[2].doubleValue());
	}
	@Override
	public final DoubleVector3D 		plus(final Point3D _d) {
		return new DoubleVector3D(x+_d.getX(), y+_d.getY(), z+_d.getZ());
	}
	@Override
	public final DoubleVector3D 		plus(final Vector3D _d) {
		return new DoubleVector3D(x+_d.getX(), y+_d.getY(), z+_d.getZ());
	}
	@Override
	public final DoubleVector3D 		plus(final Dimension3D _d) {
		return new DoubleVector3D(x+_d.getWidth(), y+_d.getHeight(), z+_d.getDepth());
	}
	@Override
	public final DoubleVector3D 		plus(final Coordinate.ThreeDims _d) {
		return new DoubleVector3D(x+_d.getFirst(), y+_d.getSecond(), z+_d.getThird());
	}
	@Override
	public final DoubleVector3D 		plus(final Dimension.ThreeDims _d) {
		return new DoubleVector3D(x+_d.getWidth(), y+_d.getHeight(), z+_d.getDepth());
	}
	@Override
	public final DoubleVector3D 		plus(final NumberVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x+_v.getNumber(0).doubleValue(), y+_v.getNumber(1).doubleValue(), z+_v.getNumber(2).doubleValue());
	}
	@Override
	public final DoubleVector3D 		plus(final DoubleVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x+_v.get(0), y+_v.get(1), z+_v.get(2));
	}

	@Override
	public final DoubleVector3D 		plusEquals(final double _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final double _u, final double _v, final double _w) {
		x += _u;
		y += _v;
		z += _w;
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x += _v[0];
		y += _v[1];
		z += _v[2];
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Number _t) {
		x += _t.doubleValue();
		y += _t.doubleValue();
		z += _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Number _x, final Number _y, final Number _z) {
		x += _x.doubleValue();
		y += _y.doubleValue();
		z += _z.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x += _v[0].doubleValue();
		y += _v[1].doubleValue();
		z += _v[2].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Point3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Vector3D _d) {
		x += _d.getX();
		y += _d.getY();
		z += _d.getZ();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Dimension3D _d) {
		x += _d.getWidth();
		y += _d.getHeight();
		z += _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(Coordinate.ThreeDims _c) {
		x += _c.getFirst();
		y += _c.getSecond();
		z += _c.getThird();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final Dimension.ThreeDims _d) {
		x += _d.getWidth();
		y += _d.getHeight();
		z += _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x += _v.getNumber(0).doubleValue();
		y += _v.getNumber(1).doubleValue();
		z += _v.getNumber(2).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		plusEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		x += _v.get(0);
		y += _v.get(1);
		z += _v.get(2);
		return this;
	}

	@Override
	public final DoubleVector3D 		minus(final double _t) {
		return new DoubleVector3D(x-_t, y-_t, z-_t);
	}
	@Override
	public final DoubleVector3D 		minus(final double _u, final double _v, final double _w) {
		return new DoubleVector3D(x-_u, y-_v, z-_w);
	}
	@Override
	public final DoubleVector3D 		minus(final double[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x-_v[0], y-_v[1], z-_v[2]);
	}
	@Override
	public final DoubleVector3D 		minus(final Number _t) {
		return new DoubleVector3D(x-_t.doubleValue(), y-_t.doubleValue(), z-_t.doubleValue());
	}
	@Override
	public final DoubleVector3D 		minus(final Number _x, final Number _y, final Number _z) {
		return new DoubleVector3D(x-_x.doubleValue(), y-_y.doubleValue(), z-_z.doubleValue());
	}
	@Override
	public final DoubleVector3D 		minus(final Number[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x-_v[0].doubleValue(), y-_v[1].doubleValue(), z-_v[2].doubleValue());
	}
	@Override
	public final DoubleVector3D 		minus(final Point3D _d) {
		return new DoubleVector3D(x-_d.getX(), y-_d.getY(), z-_d.getZ());
	}
	@Override
	public final DoubleVector3D 		minus(final Vector3D _d) {
		return new DoubleVector3D(x-_d.getX(), y-_d.getY(), z-_d.getZ());
	}
	@Override
	public final DoubleVector3D 		minus(final Dimension3D _d) {
		return new DoubleVector3D(x-_d.getWidth(), y-_d.getHeight(), z-_d.getDepth());
	}
	@Override
	public final DoubleVector3D 		minus(final Coordinate.ThreeDims _d) {
		return new DoubleVector3D(x-_d.getFirst(), y-_d.getSecond(), z-_d.getThird());
	}
	@Override
	public final DoubleVector3D 		minus(final Dimension.ThreeDims _d) {
		return new DoubleVector3D(x-_d.getWidth(), y-_d.getHeight(), z-_d.getDepth());
	}
	@Override
	public final DoubleVector3D 		minus(final NumberVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x-_v.getNumber(0).doubleValue(), y-_v.getNumber(1).doubleValue(), z-_v.getNumber(2).doubleValue());
	}
	@Override
	public final DoubleVector3D 		minus(final DoubleVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x-_v.get(0), y-_v.get(1), z-_v.get(2));
	}

	@Override
	public final DoubleVector3D 		minusEquals(final double _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final double _u, final double _v, final double _w) {
		x -= _u;
		y -= _v;
		z -= _w;
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final double[] _v) {
		assert(_v.length >= 3);
		x -= _v[0];
		y -= _v[1];
		z -= _v[2];
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Number _t) {
		x -= _t.doubleValue();
		y -= _t.doubleValue();
		z -= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Number _x, final Number _y, final Number _z) {
		x -= _x.doubleValue();
		y -= _y.doubleValue();
		z -= _z.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x -= _v[0].doubleValue();
		y -= _v[1].doubleValue();
		z -= _v[2].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Point3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Vector3D _d) {
		x -= _d.getX();
		y -= _d.getY();
		z -= _d.getZ();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Dimension3D _d) {
		x -= _d.getWidth();
		y -= _d.getHeight();
		z -= _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Coordinate.ThreeDims _d) {
		x -= _d.getFirst();
		y -= _d.getSecond();
		z -= _d.getThird();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final Dimension.ThreeDims _d) {
		x -= _d.getWidth();
		y -= _d.getHeight();
		z -= _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x -= _v.getNumber(0).doubleValue();
		y -= _v.getNumber(1).doubleValue();
		z -= _v.getNumber(2).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		minusEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		x -= _v.get(0);
		y -= _v.get(1);
		z -= _v.get(2);
		return this;
	}

	@Override
	public final DoubleVector3D 		times(final double _t) {
		return new DoubleVector3D(x*_t, y*_t, z*_t);
	}
	@Override
	public final DoubleVector3D 		times(final double _u, final double _v, final double _w) {
		return new DoubleVector3D(x*_u, y*_v, z*_w);
	}
	@Override
	public final DoubleVector3D 		times(final double[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x*_v[0], y*_v[1], z*_v[2]);
	}
	@Override
	public final DoubleVector3D 		times(final Number _t) {
		return new DoubleVector3D(x*_t.doubleValue(), y*_t.doubleValue(), z*_t.doubleValue());
	}
	@Override
	public final DoubleVector3D 		times(final Number _x, final Number _y, final Number _z) {
		return new DoubleVector3D(x*_x.doubleValue(), y*_y.doubleValue(), z*_z.doubleValue());
	}
	@Override
	public final DoubleVector3D 		times(final Number[] _v) {
		assert(_v.length >= 3);
		return new DoubleVector3D(x*_v[0].doubleValue(), y*_v[1].doubleValue(), z*_v[2].doubleValue());
	}
	@Override
	public final DoubleVector3D 		times(final Vector3D _d) {
		return new DoubleVector3D(x*_d.getX(), y*_d.getY(), z*_d.getZ());
	}
	@Override
	public final DoubleVector3D 		times(final Dimension3D _d) {
		return new DoubleVector3D(x*_d.getWidth(), y*_d.getHeight(), z*_d.getDepth());
	}
	@Override
	public final DoubleVector3D			times(final Dimension.ThreeDims _dim) {
		return new DoubleVector3D(x*_dim.getWidth(), y*_dim.getHeight(), z*_dim.getDepth());
	}
	@Override
	public final DoubleVector3D 		times(final NumberVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x*_v.getNumber(0).doubleValue(), y*_v.getNumber(1).doubleValue(), z*_v.getNumber(2).doubleValue());
	}
	@Override
	public final DoubleVector3D 		times(final DoubleVector _v) {
		assert(_v.size() >= 3);
		return new DoubleVector3D(x*_v.get(0), y*_v.get(1), z*_v.get(2));
	}

	@Override
	public final DoubleVector3D 		timesEquals(final double _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final double _u, final double _v, final double _w) {
		x *= _u;
		y *= _v;
		z *= _w;
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final double[] _v) {
		assert(_v.length >= 3);
		x *= _v[0];
		y *= _v[1];
		z *= _v[2];
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Number _t) {
		x *= _t.doubleValue();
		y *= _t.doubleValue();
		z *= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Number _x, final Number _y, final Number _z) {
		x *= _x.doubleValue();
		y *= _y.doubleValue();
		z *= _z.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		x *= _v[0].doubleValue();
		y *= _v[1].doubleValue();
		z *= _v[2].doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Vector3D _d) {
		x *= _d.getX();
		y *= _d.getY();
		z *= _d.getZ();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Dimension3D _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		z *= _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final Dimension.ThreeDims _d) {
		x *= _d.getWidth();
		y *= _d.getHeight();
		z *= _d.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		x *= _v.getNumber(0).doubleValue();
		y *= _v.getNumber(1).doubleValue();
		z *= _v.getNumber(2).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		timesEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		x *= _v.get(0);
		y *= _v.get(1);
		z *= _v.get(2);
		return this;
	}
	
	@Override
	public final DoubleVector3D 		divides(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_t, y/_t, z/_t);
	}
	public final DoubleVector3D 		divides(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_u, y/_v, z/_w);
	}
	@Override
	public final DoubleVector3D 		divides(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_v[0], y/_v[1], z/_v[2]);
	}
	@Override
	public final DoubleVector3D 		divides(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_t.doubleValue(), y/_t.doubleValue(), z/_t.doubleValue());
	}
	@Override
	public final DoubleVector3D 		divides(final Number _x, final Number _y, final Number _z) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_x.doubleValue(), y/_y.doubleValue(), z/_z.doubleValue());
	}
	@Override
	public final DoubleVector3D 		divides(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_v[0].doubleValue(), y/_v[1].doubleValue(), z/_v[2].doubleValue());
	}
	@Override
	public final DoubleVector3D 		divides(final Vector3D _other) {
		if(_other.getX() == 0 || _other.getY() == 0 || _other.getZ() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_other.getX(), y/_other.getY(), z/_other.getZ());
	}
	@Override
	public final DoubleVector3D 		divides(final Dimension3D _other) {
		if(_other.getWidth() == 0 || _other.getHeight() == 0 || _other.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_other.getWidth(), y/_other.getHeight(), z/_other.getDepth());
	}
	@Override
	public final DoubleVector3D 		divides(final Dimension.ThreeDims _other) {
		if(_other.getWidth() == 0 || _other.getHeight() == 0 || _other.getDepth() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_other.getWidth(), y/_other.getHeight(), z/_other.getDepth());
	}
	@Override
	public final DoubleVector3D 		divides(final NumberVector _v) {
		assert(_v.size() >= 3);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_v.getNumber(0).doubleValue(), y/_v.getNumber(1).doubleValue(), z/_v.getNumber(2).doubleValue());
	}
	@Override
	public final DoubleVector3D 		divides(final DoubleVector _v) {
		assert(_v.size() >= 3);
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0) throw new RuntimeException("Divide by 0");
		return new DoubleVector3D(x/_v.get(0), y/_v.get(1), z/_v.get(2));
	}

	@Override
	public final DoubleVector3D 		dividesEquals(final double _t) {
		if(_t == 0) throw new RuntimeException("Divide by 0");
		x /= _t;
		y /= _t;
		z /= _t;
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final double _u, final double _v, final double _w) {
		if(_u == 0 || _v == 0 || _w == 0) throw new RuntimeException("Divide by 0");
		x /= _u;
		y /= _v;
		z /= _w;
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final double[] _v) {
		assert(_v.length >= 3);
		if(_v[0] == 0 || _v[1] == 0 || _v[2] == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0];
		y /= _v[1];
		z /= _v[2];
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final Number _t) {
		if(_t.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _t.doubleValue();
		y /= _t.doubleValue();
		z /= _t.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D			dividesEquals(final Number _x, final Number _y, final Number _z) {
		if(_x.doubleValue() == 0 || _y.doubleValue() == 0 || _z.doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _x.doubleValue();
		y /= _y.doubleValue();
		z /= _z.doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final Number[] _v) {
		assert(_v.length >= 3);
		if(_v[0].doubleValue() == 0 || _v[1].doubleValue() == 0 || _v[2].doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v[0].doubleValue();
		y /= _v[1].doubleValue();
		z /= _v[2].doubleValue();
		return this;
	}
	public final DoubleVector3D 		dividesEquals(final Point3D _other) {
		if(_other.getX() == 0 || _other.getY() == 0 || _other.getZ() == 0) throw new RuntimeException("Divide by 0");
		x /= _other.getX();
		y /= _other.getY();
		z /= _other.getZ();
		return this;
	}
	public final DoubleVector3D 		dividesEquals(final Vector3D _other) {
		if(_other.getX() == 0 || _other.getY() == 0 || _other.getZ() == 0) throw new RuntimeException("Divide by 0");
		x /= _other.getX();
		y /= _other.getY();
		z /= _other.getZ();
		return this;
	}
	public final DoubleVector3D 		dividesEquals(final Dimension3D _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0 || _dim.getDepth() == 0) throw new RuntimeException("Divide by 0");
		x /= _dim.getWidth();
		y /= _dim.getHeight();
		z /= _dim.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final Dimension.ThreeDims _dim) {
		if(_dim.getWidth() == 0 || _dim.getHeight() == 0 || _dim.getDepth() == 0) throw new RuntimeException("Divide by 0");
		x /= _dim.getWidth();
		y /= _dim.getHeight();
		z /= _dim.getDepth();
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final NumberVector _v) {
		assert(_v.size() >= 3);
		if(_v.getNumber(0).doubleValue() == 0 || _v.getNumber(1).doubleValue() == 0 || _v.getNumber(2).doubleValue() == 0) throw new RuntimeException("Divide by 0");
		x /= _v.getNumber(0).doubleValue();
		y /= _v.getNumber(1).doubleValue();
		z /= _v.getNumber(2).doubleValue();
		return this;
	}
	@Override
	public final DoubleVector3D 		dividesEquals(final DoubleVector _v) {
		assert(_v.size() >= 3);
		if(_v.get(0) == 0 || _v.get(1) == 0 || _v.get(2) == 0) throw new RuntimeException("Divide by 0");
		x /= _v.get(0);
		y /= _v.get(1);
		z /= _v.get(2);
		return this;
	}

	@Override
	public final Number 		dotProduct(final NumberVector _b) {
        return x * _b.getNumber(1).doubleValue() + y * _b.getNumber(1).doubleValue() + z * _b.getNumber(2).doubleValue();
	}
    @Override
    public final double 		dotProduct(final double _x, final double _y, final double _z) {
        return x * _x + y * _y + z * _z;
    }
    @Override
    public final double 		dotProduct(final Vector3D _b) {
        return x * _b.getX() + y * _b.getY() + z * _b.getZ();
    }

    @Override
	public final NumberVector 		crossProduct(NumberVector _b) {
		assert(_b.size() == 3);
		return new DoubleVector3D( y * _b.getNumber(2).doubleValue() - z * _b.getNumber(1).doubleValue(),
							 z * _b.getNumber(0).doubleValue() - x * _b.getNumber(2).doubleValue(),
							 x * _b.getNumber(1).doubleValue() - y * _b.getNumber(0).doubleValue()  );
	}
    @Override
	public final Vector3D 		crossProduct(final double _x, final double _y, final double _z) {
		return new DoubleVector3D( y * _z - z * _y,
							 z * _x - x * _z,
							 x * _y - y * _x  );
	}
    @Override
	public final Vector3D 		crossProduct(final Vector3D _b) {
		return new DoubleVector3D( y * _b.getZ() - z * _b.getY(),
							 z * _b.getX() - x * _b.getZ(),
							 x * _b.getY() - y * _b.getX()  );
	}

	@Override
    public final boolean 		isValid() {
		return !java.lang.Double.isNaN(x) && !java.lang.Double.isInfinite(x) && !java.lang.Double.isNaN(y) && !java.lang.Double.isInfinite(y) && !java.lang.Double.isNaN(z) && !java.lang.Double.isInfinite(z);
	}

	@Override
	public final boolean 		isEqual(final Number _t) {
		return (x == _t.doubleValue() && y == _t.doubleValue() && z == _t.doubleValue()) ? true : false;
	}	
	@Override
	public final boolean 		isEqual(final Number[] _v) {
		assert(_v.length == 3);
		return (x == _v[0].doubleValue() && y == _v[1].doubleValue() && z == _v[2].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final NumberVector _v) {
		assert(_v.size() == 3);
		return (x == _v.getNumber(0).doubleValue() && y == _v.getNumber(1).doubleValue() && z == _v.getNumber(2).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final double _t) {
		return (x == _t && y == _t && z == _t) ? true : false;
	}	
	@Override
	public final boolean 		isEqual(final double[] _v) {
		assert(_v.length == 3);
		return (x == _v[0] && y == _v[1] && z == _v[2]) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final DoubleVector _v) {
		assert(_v.size() == 3);
		return (x == _v.get(0) && y == _v.get(1) && z == _v.get(2)) ? true : false;
	}

	@Override
	public final boolean 		isEqual(final double _u, final double _v, final double _w) {
		return (x == _u && y == _v && z == _w) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Point3D _d) {
		return (x == _d.getX() && y == _d.getY() && z == _d.getZ()) ? true : false;
	}
	@Override
	public final boolean 		isEqual(final Vector3D _d) {
		return (x == _d.getX() && y == _d.getY() && z == _d.getZ()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final Number _t) {
		return (x != _t.doubleValue() || y != _t.doubleValue() || z != _t.doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Number[] _v) {
		assert(_v.length == 3);
		return (x != _v[0].doubleValue() || y != _v[1].doubleValue() || z != _v[2].doubleValue()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final NumberVector _v) {
		assert(_v.size() == 3);
		return (x != _v.getNumber(0).doubleValue() || y != _v.getNumber(1).doubleValue() || z != _v.getNumber(2).doubleValue()) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final double[] _v) {
		assert(_v.length == 3);
		return (x != _v[0] || y != _v[1] || z != _v[2]) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final DoubleVector _v) {
		assert(_v.size() == 3);
		return (x != _v.get(0) || y != _v.get(1) || z != _v.get(2)) ? true : false;
	}

	@Override
	public final boolean 		isDifferent(final double _x, final double _y, final double _z) {
		return (x != _x || y != _y || z != _z) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Point3D _d) {
		return (x != _d.getX() || y != _d.getY() || z != _d.getZ()) ? true : false;
	}
	@Override
	public final boolean 		isDifferent(final Vector3D _d) {
		return (x != _d.getX() || y != _d.getY() || z != _d.getZ()) ? true : false;
	}

	@Override
	public final boolean 		isColinear(final Vector3D _vec) {
		return (x * _vec.getY()) == (y * _vec.getX()) && (x * _vec.getZ()) == (z * _vec.getX());
	}
	@Override
	public final boolean 		isColinear(final NumberVector _vec) {
		assert(size() == _vec.size());
		return (x * _vec.getNumber(1).doubleValue()) == (y * _vec.getNumber(0).doubleValue()) && (x * _vec.getNumber(2).doubleValue()) == (z * _vec.getNumber(0).doubleValue());
	}
	@Override
	public final boolean 		isColinear(final DoubleVector _vec) {
		assert(size() == _vec.size());
		return (x * _vec.get(1)) == (y * _vec.get(0)) && (x * _vec.get(2)) == (z * _vec.get(0));
	}

	@Override
	public final boolean 		isOrthogonal(final Vector3D _vec) {
		double dotProd = getX() * _vec.getX() + getY() * _vec.getY() + getZ() * _vec.getZ();
		return (dotProd < Numbers.EPSILON) ? true : false;
//		return (Vector3D.dotProduct(this, _vec) < Numbers.EPSILON) ? true : false;
	}
	@Override
	public final boolean 		isOrthogonal(NumberVector _vec) {
		double dotProd = getX() * _vec.getNumber(0).doubleValue() + getY() * _vec.getNumber(1).doubleValue() + getZ() * _vec.getNumber(2).doubleValue();
		return (dotProd < Numbers.EPSILON) ? true : false;
	}
	@Override
	public final boolean 		isOrthogonal(DoubleVector _vec) {
		double dotProd = getX() * _vec.get(0) + getY() * _vec.get(1) + getZ() * _vec.get(2);
		return (dotProd < Numbers.EPSILON) ? true : false;
	}


	public final boolean 		isColinearToSegment(final Vector3D _A, final Vector3D _B) {
		return (x * (_B.getY() - _A.getY())) == (y * (_B.getX() - _A.getX())) && (x * (_B.getZ() - _A.getZ())) == (z * (_B.getX() - _A.getX()));
	}
	public final boolean 		isColinearToLine(final Vector3D _P, final Vector3D _N) {
		return (x * _N.getY()) == (y * _N.getX()) && (x * _N.getZ()) == (z * _N.getX());
	}

	public final boolean 		isOrthogonalToSegment(final Vector3D _A, final Vector3D _B) {
		return isOrthogonal(_B.minus(_A));
	}
	public final boolean 		isOrthogonalToLine(final Vector3D _P, final Vector3D _N) {
		return isOrthogonal(_N);
	}

	@Override
	public Vector2D 					downgrade() {
		return new DoubleVector2D(getZ() * getX(), getZ() * getY());
	}
	@Override
	public final Vector4D 				uniform() {
		return new DoubleVector4D(getX(), getY(), getZ(), 0.0d);
	}
	@Override
	public final Vector4D 				uniform(final double _w) {
		return new DoubleVector4D(getX(), getY(), getZ(), _w);
	}

	@Override
	public DoubleVector3D 				clone() {
		return new DoubleVector3D(x, y, z);
	}
	@Override
	public final DoubleVector3D 		cloneEditable() {
		return new DoubleVector3D(x, y, z);
	}

	@Override
	public final DoubleVector3D 		abs() {
		return new DoubleVector3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	@Override
	public final DoubleVector3D 		negate() {
		return new DoubleVector3D(-x, -y, -z);
	}
	@Override
	public final DoubleVector3D 		normalized() {
		double length = norm();
		if(length < Numbers.EPSILON)
			return this;
		double invLength = 1.0f / length;
		return new DoubleVector3D(x * invLength, y * invLength, z * invLength);
	}

	@Override
	public final double 				norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return x*x+y*y+z*z;

		case Euclidian:			return Math.sqrt(x*x+y*y+z*z);

		case Manhattan:			return Math.abs( x ) + Math.abs( y ) + Math.abs( z );

		case P:					return Math.pow(  Math.pow( Math.abs( x ), p) +  Math.pow( Math.abs( y ), p) +  Math.pow( Math.abs( z ), p), 1d/p);

		case Maximum:			return Math.abs( x ) > Math.abs( y ) ? 
											( Math.abs( x ) > Math.abs( z ) ? Math.abs( x ) : Math.abs( z ) )
											:
											( Math.abs( y ) > Math.abs( z ) ? Math.abs( y ) : Math.abs( z ) )
											;
								
		default:				throw new NotYetImplementedException();
		}
	}
	@Override
	public final double 				magnitude() {
		return norm();
	}
	@Override
	public final double 				norm() {
		return (double) Math.sqrt(norm2());
	}
	@Override
	public final double 				norm2() {
		return x*x + y*y + z*z;
	}

	public final double euclydianDistance(Vector3D _v) {
		return (double) Math.sqrt((x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ()));
	}
	public final double euclydianDistance2(Vector3D _v) {
		return (double) (x-_v.getX())*(x-_v.getX()) + (y-_v.getY())*(y-_v.getY()) + (z-_v.getZ())*(z-_v.getZ());
	}

	public final double euclydianDistanceXY(DoubleVector3D _v) {
		return (double) Math.sqrt((x-_v.x)*(x-_v.x) + (y-_v.y)*(y-_v.y));
	}
	public final double euclydianDistanceXY2(DoubleVector3D _v) {
		return (double) (x-_v.x)*(x-_v.x) + (y-_v.y)*(y-_v.y);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		DoubleVector3D other = (DoubleVector3D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return false;
		if(java.lang.Double.doubleToLongBits(z) != java.lang.Double.doubleToLongBits(other.z))
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
		DoubleVector3D other = (DoubleVector3D) obj;
		if(java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return java.lang.Double.doubleToLongBits(x) > java.lang.Double.doubleToLongBits(other.x) ? 1 : -1;
		if(java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return java.lang.Double.doubleToLongBits(y) > java.lang.Double.doubleToLongBits(other.y) ? 1 : -1;
		if(java.lang.Double.doubleToLongBits(z) != java.lang.Double.doubleToLongBits(other.z))
			return java.lang.Double.doubleToLongBits(z) > java.lang.Double.doubleToLongBits(other.z) ? 1 : -1;
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime * result + java.lang.Double.doubleToLongBits(x);
		result = prime * result + java.lang.Double.doubleToLongBits(y);
		result = prime * result + java.lang.Double.doubleToLongBits(z);
		return (int) result;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
	@Override
	public String toString(NumberFormat _df) {
		return "(" + _df.format(x) + "," + _df.format(y) + "," + _df.format(z) + ")";
	}
	@Override
	public String toString(DecimalFormat _df) {
		return "( " + _df.format(x) + ";" + _df.format(y) + "," + _df.format(z) + " )";
	}

}
