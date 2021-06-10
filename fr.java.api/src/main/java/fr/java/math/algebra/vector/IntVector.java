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
package fr.java.math.algebra.vector;

import java.text.NumberFormat;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.IntTensor;

public interface IntVector extends NumberVector, IntTensor {

	// TENSOR METHODS
	@Override
	public default Nature 			getNature() {
		return Nature.VECTOR;
	}
	@Override
	public default Nature 			getNature(boolean _simplified) {
		if(!_simplified)
			return getNature();
		return size() == 1 ? Nature.SCALAR : Nature.VECTOR;
	}

	@Override
	public default int		 		getCapacity() {
		return size();
	}
	@Override
	public default int		 		getSliceCapacity(int _depth) {
		assert _depth == 0 : "Illegal argument";

		return size();
	}
/*
	@Override
	public default IntTensor     getSlice(final List<Integer> _slice) {
		return Vector.getSlice(_slice);
	}
*/
	// VECTOR METHODS
	public double 					get(final int _i);
	public void 					set(final int _i, final double _value);

	public void 					set(final double _value);
	public void 					set(final double[] _values);
	public void 					set(final IntVector _vector);

	@Override
	public IntVector		 		clone();
	@Override
	public IntVector 				abs();
	@Override
	public IntVector 				negate();
	@Override
	public IntVector 				normalized();

	@Override
	public double 					magnitude();
	@Override
	public double 					norm();
	@Override
	public double 					norm2();

	public IntVector 				plus(final double _d);
	public IntVector 				plus(final double[] _d);
	public IntVector 				plus(final IntVector _v);

	public IntVector 				plusEquals(final double _d);
	public IntVector 				plusEquals(final double[] _d);
	public IntVector 				plusEquals(final IntVector _v);

	public IntVector 				minus(final double _d);
	public IntVector 				minus(final double[] _d);
	public IntVector 				minus(final IntVector _v);

	public IntVector 				minusEquals(final double _d);
	public IntVector 				minusEquals(final double[] _d);
	public IntVector 				minusEquals(final IntVector _v);

	public IntVector 				times(final double _t);
	public IntVector 				times(final double[] _d);
	public IntVector 				times(final IntVector _v);

	public IntVector 				timesEquals(final double _t);
	public IntVector 				timesEquals(final double[] _d);
	public IntVector 				timesEquals(final IntVector _v);

	public IntVector 				divides(final double _t);
	public IntVector 				divides(final double[] _d);
	public IntVector 				divides(final IntVector _v);

	public IntVector 				dividesEquals(final double _t);
	public IntVector 				dividesEquals(final double[] _d);
	public IntVector 				dividesEquals(final IntVector _v);

	@Override
	public boolean 					isValid();

	public boolean 					isEqual(final IntVector _d);
	public boolean 					isEqual(final double _t);
	public boolean 					isEqual(final double[] _v);

	public boolean 					isDifferent(final IntVector _d);
	public boolean 					isDifferent(final double _t);
	public boolean 					isDifferent(final double[] _v);

	public boolean 					isColinear(final IntVector _vec);
	public boolean 					isOrthogonal(final IntVector _vec);

	public double 					dotProduct(final IntVector _v);
	public IntVector 				crossProduct(final IntVector _v);

	@Override
	public String 					toString(final NumberFormat _nf);

}
