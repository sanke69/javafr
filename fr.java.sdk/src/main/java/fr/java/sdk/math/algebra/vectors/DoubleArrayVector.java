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
package fr.java.sdk.math.algebra.vectors;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.sdk.math.algebra.Tensors;

public class DoubleArrayVector implements DoubleVector.Editable, NumberVector.Editable {
	private static final long serialVersionUID = -3073045430773729265L;

	private static final double EPSILON = 0;

	public static final DoubleArrayVector allocate(final int _n) {
		DoubleArrayVector V = new DoubleArrayVector();

		V.data = new double[_n];

		return V;
	}
	public static final DoubleArrayVector allocate(final double[] _values) {
		DoubleArrayVector V = new DoubleArrayVector();

		V.data = new double[_values.length];
		System.arraycopy(_values, 0, V.data, 0, _values.length);

		return V;
	}
	public static final DoubleArrayVector wrap(final double[] _values) {
		DoubleArrayVector V = new DoubleArrayVector();

		V.data = _values;

		return V;
	}

	public static final double dot(final NumberVector _a, final NumberVector _b) {
		assert (_a.size() == _b.size());

		double dot = 0;
		for (int i = 0; i < _a.size(); ++i)
			dot += _a.getNumber(i).doubleValue() - _b.getNumber(i).doubleValue();
		return dot;
	}
	public static final double dot(final DoubleVector _a, final DoubleVector _b) {
		assert (_a.size() == _b.size());

		double dot = 0;
		for (int i = 0; i < _a.size(); ++i)
			dot += _a.get(i) - _b.get(i);
		return dot;
	}
	public static final double dot(final DoubleArrayVector _a, final DoubleArrayVector _b) {
		assert (_a.data.length == _b.data.length);

		double dot = 0;
		for (int i = 0; i < _a.data.length; ++i)
			dot += _a.data[i] - _b.data[i];
		return dot;
	}

	public double[] data;

	private DoubleArrayVector() {
		;
	}
	public DoubleArrayVector(final double _value) {
		data = new double[1];
		data[0] = _value;
	}
	public DoubleArrayVector(final int _size) {
		data = new double[_size];
	}
	public DoubleArrayVector(final int _size, final double _value) {
		data = new double[_size];
		for (int i = 0; i < data.length; i++)
			data[i] = _value;
	}
	public DoubleArrayVector(final int _size, final double[] _values) {
		data = new double[_size];
		System.arraycopy(_values, 0, data, 0, Math.min(_size, _values.length));
	}
	public DoubleArrayVector(final int _size, final DoubleArrayVector _vector) {
		data = new double[_size];
		System.arraycopy(_vector.data, 0, data, 0, Math.min(_size, _vector.data.length));
	}
	private DoubleArrayVector(final double[] _values) {
		data = new double[_values.length];
		System.arraycopy(_values, 0, data, 0, _values.length);
	}
	public DoubleArrayVector(final DoubleArrayVector _vector) {
		data = new double[_vector.data.length];
		System.arraycopy(_vector.data, 0, data, 0, _vector.data.length);
	}
	public DoubleArrayVector(final NumberVector _vector) {
		data = new double[_vector.size()];
		for(int i = 0; i < _vector.size(); ++i)
			data[i] = _vector.getNumber(i).doubleValue();
	}

	/**
	 * TENSOR METHODS
	**/
	@Override
	public int 					getCapacity() {
		return data.length;
	}

	@Override
	public final boolean 		isDirect()  { return true; }
	@Override
	public final double[] 		getArray()  { return data; }
	@Override
	public final DoubleBuffer 	getBuffer() { return DoubleBuffer.wrap(getArray()); }
    public final DoubleStream 	getStream() { return DoubleStream.of(data); }

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
		if(_index > size())
			throw new IllegalArgumentException();
		return data[ (int) _index];
	}
	@Override
	public final double 		getValue(final int... _coords) {
		assert(_coords.length == 1 && _coords[0] < size());
		return get(_coords[0]);
	}
	@Override
	public void 				setValue(double _value, long _index) {
		if(_index > size())
			throw new IllegalArgumentException();
		data[ (int) _index] = _value;
	}
	@Override
	public final void 			setValue(double _value, final int... _coords) {
		set(_value, _coords[0]);
	}

	@Override
	public final void			setSlice(DoubleTensor _tensor, final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceView(final int... _slice) {
		assert(_slice.length == 1);
		return Tensors.Double.allocate(data, size());
	}
	@Override
	public final DoubleTensor 	getSliceCopy(final int... _slice) {
		assert(_slice.length == 1 && _slice[0] < 4);
		return Tensors.Double.allocate(data, size());
	}

	@Override
	public void 				reshape(int... _shape) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * VECTOR METHODS
	**/
	public final int 			size() {
		return data.length;
	}

	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	double squares = 0;
								for (double d : data)
									squares += d * d;
								return squares;

		case Euclidian:			return Math.sqrt(norm(Norm.EuclidianSquare));

		case Manhattan:			double length = 0;
								for (double d : data)
									length += Math.abs( d );
								return length;

		case P:					double powers = 0;
								for (double d : data)
									powers += Math.pow( Math.abs( d ), p);
								return Math.pow( powers, 1d/p);

		case Maximum:			double max = 0;
								for (double d : data)
									if(max < d)
										max = Math.abs( d );
								return max;
								
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
		double l = 0;
		for (double d : data)
			l += d * d;
		return l;
	}

	public final void 			set(final Number _value) {
		for(int i = 0; i < data.length; ++i)
			data[i] = _value.doubleValue();
	}
	public final void 			set(final Number[] _values) {
		assert (data.length == _values.length);
		for(int i = 0; i < data.length; ++i)
			data[i] = _values[i].doubleValue();
	}
	@Override
	public final void 			set(final NumberVector _vector) {
		assert (data.length == _vector.size());
		for(int i = 0; i < _vector.size(); ++i)
			data[i] = _vector.getNumber(i).doubleValue();
	}
	
	public final void 			set(final double _value) {
		for (int i = 0; i < data.length; ++i)
			data[i] = _value;
	}
	public final void 			set(final double[] _values) {
		assert (data.length == _values.length);
		System.arraycopy(_values, 0, data, 0, _values.length);
	}
	@Override
	public final void 			set(final DoubleVector _vector) {
		assert (data.length == _vector.size());
		System.arraycopy(_vector.getArray(), 0, data, 0, _vector.size());
	}

	public final void 			set(final DoubleArrayVector _vector) {
		assert (data.length == _vector.data.length);
		System.arraycopy(_vector.data, 0, data, 0, _vector.data.length);
	}

	public final void 			setNumber(final Number _v, final int _i) {
		data[_i] = _v.doubleValue();
	}
	public final Number 		getNumber(final int _i) {
		return data[_i];
	}

	public final void 			set(final double _v, final int _i) {
		data[_i] = _v;
	}
	public final double 		get(final int _i) {
		return data[_i];
	}

	public final DoubleArrayVector 		plus(final Number _d) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d.doubleValue();
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		plus(final Number[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d[i].doubleValue();
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		plus(final NumberVector _v) {
		assert (data.length == _v.size());
		double[] v = new 	double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _v.getNumber(i).doubleValue();
		return new DoubleArrayVector(v.length, v);
	}

	public final DoubleArrayVector 		plus(final double _d) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d;
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		plus(final double[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _d[i];
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		plus(final DoubleVector _v) {
		assert (data.length == _v.size());
		double[] v = new 	double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _v.get(i);
		return new DoubleArrayVector(v.length, v);
	}

	public final DoubleArrayVector 		plus(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] + _v.data[i];
		return new DoubleArrayVector(v.length, v);
	}

	public final DoubleArrayVector 		plusEquals(final Number _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] += _d.doubleValue();
		return this;
	}
	public final DoubleArrayVector 		plusEquals(final Number[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] += _d[i].doubleValue();
		return this;
	}
	public final DoubleArrayVector 		plusEquals(final NumberVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] += _v.getNumber(i).doubleValue();
		return this;
	}

	public final DoubleArrayVector 		plusEquals(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] += _d;
		return this;
	}
	public final DoubleArrayVector 		plusEquals(final double[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] += _d[i];
		return this;
	}
	public final DoubleArrayVector 		plusEquals(final DoubleVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] += _v.get(i);
		return this;
	}

	public final DoubleArrayVector 		plusEquals(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		for(int i = 0; i < data.length; ++i)
			data[i] += _v.data[i];
		return this;
	}

	public final DoubleArrayVector 		minus(final Number _d) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _d.doubleValue();
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		minus(final Number[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _d[i].doubleValue();
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		minus(final NumberVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _v.getNumber(i).doubleValue();
		return new DoubleArrayVector(v);
	}
	
	public final DoubleArrayVector 		minus(final double _d) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _d;
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		minus(final double[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _d[i];
		return new DoubleArrayVector(v.length, v);
	}
	public final DoubleArrayVector 		minus(final DoubleVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _v.get(i);
		return new DoubleArrayVector(v);
	}

	public final DoubleArrayVector 		minus(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] - _v.data[i];
		return new DoubleArrayVector(v);
	}

	public final DoubleArrayVector 		minusEquals(final Number _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] -= _d.doubleValue();
		return this;
	}
	public final DoubleArrayVector 		minusEquals(final Number[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] -= _d[i].doubleValue();
		return this;
	}
	public final DoubleArrayVector 		minusEquals(final NumberVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] -= _v.getNumber(i).doubleValue();
		return this;
	}

	public final DoubleArrayVector 		minusEquals(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] -= _d;
		return this;
	}
	public final DoubleArrayVector 		minusEquals(final double[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] -= _d[i];
		return this;
	}
	public final DoubleArrayVector 		minusEquals(final DoubleVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] -= _v.get(i);
		return this;
	}

	public final DoubleArrayVector 		minusEquals(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		for(int i = 0; i < data.length; ++i)
			data[i] -= _v.data[i];
		return this;
	}

	public final DoubleArrayVector 		times(final Number _t) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _t.doubleValue();
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		times(final Number[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _d[i].doubleValue();
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		times(final NumberVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _v.getNumber(i).doubleValue();
		return new DoubleArrayVector(data.length, v);
	}

	public final DoubleArrayVector 		times(final double _t) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _t;
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		times(final double[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _d[i];
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		times(final DoubleVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _v.get(i);
		return new DoubleArrayVector(data.length, v);
	}

	public final DoubleArrayVector 		times(final DoubleArrayVector _v) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] * _v.data[i];
		return new DoubleArrayVector(data.length, v);
	}

	public final DoubleArrayVector 		timesEquals(final Number _t) {
		for(int i = 0; i < data.length; ++i)
			data[i] *= _t.doubleValue();
		return this;
	}
	public final DoubleArrayVector 		timesEquals(final Number[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] *= _d[i].doubleValue();
		return this;
	}
	public final DoubleArrayVector 		timesEquals(final NumberVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] *= _v.getNumber(i).doubleValue();
		return this;
	}

	public final DoubleArrayVector	 	timesEquals(final double _t) {
		for(int i = 0; i < data.length; ++i)
			data[i] *= _t;
		return this;
	}
	public final DoubleArrayVector 		timesEquals(final double[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] *= _d[i];
		return this;
	}
	public final DoubleArrayVector 		timesEquals(final DoubleVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] *= _v.get(i);
		return this;
	}

	public final DoubleArrayVector 		timesEquals(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		for(int i = 0; i < data.length; ++i)
			data[i] *= _v.data[i];
		return this;
	}

	public final DoubleArrayVector 		divides(final Number _t) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _t.doubleValue();
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		divides(final Number[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _d[i].doubleValue();
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		divides(final NumberVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _v.getNumber(i).doubleValue();
		return new DoubleArrayVector(data.length, v);
	}
	
	public final DoubleArrayVector	 	divides(final double _t) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _t;
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		divides(final double[] _d) {
		assert (data.length == _d.length);
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _d[i];
		return new DoubleArrayVector(data.length, v);
	}
	public final DoubleArrayVector 		divides(final DoubleVector _v) {
		assert (data.length == _v.size());
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _v.get(i);
		return new DoubleArrayVector(data.length, v);
	}
	
	public final DoubleArrayVector 		divides(final DoubleArrayVector _v) {
		double[] v = new double[data.length];
		for(int i = 0; i < data.length; ++i)
			v[i] = data[i] / _v.data[i];
		return new DoubleArrayVector(data.length, v);
	}

	public final DoubleArrayVector 		dividesEquals(final Number _t) {
		for(int i = 0; i < data.length; ++i)
			data[i] /= _t.doubleValue();
		return this;
	}
	public final DoubleArrayVector 		dividesEquals(final Number[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] /= _d[i].doubleValue();
		return this;
	}
	public final DoubleArrayVector 		dividesEquals(final NumberVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] /= _v.getNumber(i).doubleValue();
		return this;
	}

	public final DoubleArrayVector	 	dividesEquals(final double _t) {
		for(int i = 0; i < data.length; ++i)
			data[i] /= _t;
		return this;
	}
	public final DoubleArrayVector 		dividesEquals(final double[] _d) {
		assert (data.length == _d.length);
		for(int i = 0; i < data.length; ++i)
			data[i] /= _d[i];
		return this;
	}
	public final DoubleArrayVector 		dividesEquals(final DoubleVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			data[i] /= _v.get(i);
		return this;
	}

	public final DoubleArrayVector 		dividesEquals(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		for(int i = 0; i < data.length; ++i)
			data[i] /= _v.data[i];
		return this;
	}

	@Override
	public final boolean 		isValid() {
		return false;
	}

	public final boolean 		isEqual(final Number _t) {
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _t.doubleValue())
				return false;
		return true;
	}
	public final boolean 		isEqual(final Number[] _v) {
		assert (data.length == _v.length);
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _v[i].doubleValue())
				return false;
		return true;
	}
	public final boolean 		isEqual(final NumberVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _v.getNumber(i).doubleValue())
				return false;
		return true;
	}

	public final boolean 		isEqual(final double _t) {
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _t)
				return false;
		return true;
	}
	public final boolean 		isEqual(final double[] _v) {
		assert (data.length == _v.length);
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _v[i])
				return false;
		return true;
	}
	public final boolean 		isEqual(final DoubleVector _v) {
		assert (data.length == _v.size());
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _v.get(i))
				return false;
		return true;
	}

	public final boolean 		isEqual(final DoubleArrayVector _v) {
		assert (data.length == _v.data.length);
		for(int i = 0; i < data.length; ++i)
			if(data[i] != _v.data[i])
				return false;
		return true;
	}

	public final boolean 		isDifferent(final Number _t) {
		return !isEqual(_t);
	}
	public final boolean 		isDifferent(final Number[] _v) {
		return !isEqual(_v);
	}
	public final boolean 		isDifferent(final NumberVector _d) {
		return !isEqual(_d);
	}

	public final boolean 		isDifferent(final double _t) {
		return !isEqual(_t);
	}
	public final boolean 		isDifferent(final double[] _v) {
		return !isEqual(_v);
	}
	public final boolean 		isDifferent(final DoubleVector _d) {
		return !isEqual(_d);
	}

	public final boolean 		isDifferent(final DoubleArrayVector _d) {
		return !isEqual(_d);
	}

	public final boolean 		isColinear(final NumberVector _vec) {
		throw new NotYetImplementedException();
	}
	@Override
	public final boolean 		isColinear(DoubleVector _vector) {
		return false;
	}

	@Override
	public final boolean 		isOrthogonal(final NumberVector _vec) {
		return (dot(this, _vec) < EPSILON) ? true : false;
	}
	@Override
	public final boolean 		isOrthogonal(DoubleVector _vector) {
		return false;
	}
	public final boolean 		isOrthogonal(final DoubleArrayVector _vec) {
		return (dot(this, _vec) < EPSILON) ? true : false;
	}

	@Override
	public final DoubleArrayVector 		clone() {
		return new DoubleArrayVector(data.length, data);
	}
	@Override
	public final DoubleArrayVector 		abs() {
		for (double v : data)
			v = Math.abs(v);
		return this;
	}
	@Override
	public final DoubleArrayVector 		negate() {
		for (double v : data)
			v = -v;
		return this;
	}
	@Override
	public final DoubleArrayVector 		normalized() {
		double length = norm();
		if (length < EPSILON)
			return this;

		double invLength = 1.0f / length;
		double[] v = new double[data.length];
		for (int i = 0; i < data.length; ++i)
			v[i] = data[i] * invLength;
		return new DoubleArrayVector(data.length, v);
	}

	@Override
	public final Number 		dotProduct(NumberVector _v) {
/*
		assert(data.length == _v.dim());
		double sum = 0;

		for (int i = 0; i < data.length; ++i)
			sum += data[i] * _v.get(i);

		return sum;
*/
		return dot(this, _v);
	}
	public final double 		dotProduct(final DoubleArrayVector _v) {
/*
		assert(data.length == _v.data.length);
		double sum = 0;

		for (int i = 0; i < data.length; ++i)
			sum += data[i] * _v.data[i];

		return sum;
*/
		return dot(this, _v);
	}

	@Override
	public final NumberVector 		crossProduct(NumberVector _v) {
		throw new NotYetImplementedException();
	}


	@Override
	public final int 			hashCode() {
		final long prime = 31;
		long result = 1;
		for (int i = 0; i < data.length; ++i)
			result = prime * result + Double.doubleToLongBits(data[i]);
		return (int) result;
	}
	@Override
	public final boolean 		equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleArrayVector other = (DoubleArrayVector) obj;
		for (int i = 0; i < data.length; ++i)
			if (Double.doubleToLongBits(data[i]) != Double.doubleToLongBits(other.data[i]))
				return false;
		return true;
	}
	@Override
	public final int 			compareTo(Object obj) {
		if (this == obj)
			return 0;
		if (obj == null)
			return 1;
		if (getClass() != obj.getClass())
			return 1;
		DoubleArrayVector other = (DoubleArrayVector) obj;
		for (int i = 0; i < data.length; ++i)
			if (Double.doubleToLongBits(data[i]) != Double.doubleToLongBits(other.data[i]))
				return Double.doubleToLongBits(data[i]) > Double.doubleToLongBits(other.data[i]) ? 1 : -1;
		return -1;
	}

	@Override
	public final String 		toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ " + data[0]);
		for (int i = 1; i < data.length; ++i) {
			sb.append(", " + data[i]);
		}
		sb.append(" ]");
		return sb.toString();
	}
	@Override
	public final String 		toString(NumberFormat _nf) {
		// TODO Auto-generated method stub
		return null;
	}


}
