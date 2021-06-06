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
 * @file     Vector.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.algebra;

import java.text.NumberFormat;

import fr.java.lang.exceptions.NotSupportedException;

public interface NumberVector extends NumberTensor {

	public static enum Norm {
		L1,
		Euclidian,
		EuclidianSquare,
		Manhattan, // (TaxiCab)
		P,
		Maximum,
		Zero
	}
	
	public static interface Editable extends NumberVector {

		public void 				set(final Number _value);
		public void 				set(final Number[] _values);
		public void 				set(final NumberVector _vector);

		public void 				setNumber(final Number _value, final int _i);
		public Number 				getNumber(final int _i);

		public NumberVector 		plusEquals(final Number _value);
		public NumberVector 		plusEquals(final Number[] _values);
		public NumberVector 		plusEquals(final NumberVector _vector);

		public NumberVector 		minusEquals(final Number _value);
		public NumberVector 		minusEquals(final Number[] _values);
		public NumberVector 		minusEquals(final NumberVector _vector);

		public NumberVector 		timesEquals(final Number _value);
		public NumberVector 		timesEquals(final Number[] _values);
		public NumberVector 		timesEquals(final NumberVector _vector);

		public NumberVector 		dividesEquals(final Number _value);
		public NumberVector 		dividesEquals(final Number[] _values);
		public NumberVector 		dividesEquals(final NumberVector _vector);

	}

	// VECTOR METHODS
	public default int 				size() { return getCapacity(); }

	public double 					norm(Norm _norm);
	@Deprecated // See with norm
	public double 					magnitude();
	@Deprecated // See with norm
	public double 					norm();
	@Deprecated // See with norm
	public double 					norm2();

	public NumberVector 			abs();
	public NumberVector 			negate();
	public NumberVector 			normalized();

	public NumberVector 			plus(final Number _value);
	public NumberVector 			plus(final Number[] _values);
	public NumberVector 			plus(final NumberVector _vector);

	public NumberVector 			minus(final Number _value);
	public NumberVector 			minus(final Number[] _values);
	public NumberVector 			minus(final NumberVector _vector);

	public NumberVector 			times(final Number _value);
	public NumberVector 			times(final Number[] _values);
	public NumberVector 			times(final NumberVector _vector);

	public NumberVector 			divides(final Number _value);
	public NumberVector 			divides(final Number[] _values);
	public NumberVector 			divides(final NumberVector _vector);

	public Number 					dotProduct(final NumberVector _vector);
	public NumberVector 			crossProduct(final NumberVector _vector);

	public boolean 					isValid();

	public boolean 					isEqual(final Number _value);
	public boolean 					isEqual(final Number[] _values);
	public boolean 					isEqual(final NumberVector _vector);

	public boolean 					isDifferent(final Number _value);
	public boolean 					isDifferent(final Number[] _values);
	public boolean 					isDifferent(final NumberVector _vector);

	public boolean 					isColinear(final NumberVector _vector);
	public boolean 					isOrthogonal(final NumberVector _vector);

	// TENSOR METHODS
	@Override
	public default Nature 			getNature() 				{ return Nature.VECTOR; }
	@Override
	public default Nature 			getNature(boolean _simplified) {
		if(!_simplified)
			return getNature();
		return size() == 1 ? Nature.SCALAR : Nature.VECTOR;
	}

	@Override
	public default int[] 			getShape() 						{ return new int[] { getCapacity() }; }

	@Override
	public default int	 			getSliceCapacity(int _depth) 	{ return size(); }
	@Override
	public default int[] 			getSliceShape(int... _slice) 	{ return new int[] { size() }; } 
	@Override
	public default int 				getSliceOffset(int... _slice) 	{ return 0; }

	// JAVA METHODS
	@Override
	public NumberVector				clone() throws NotSupportedException;
//	public NumberVector.Editable	cloneEditable() throws NotSupportedException;

	public String 					toString(final NumberFormat _nf);
	
	
	
/*
	@Override public default void 				reshape(final List<Integer> _shape) { throw new IllegalArgumentException("Can't reshape a Vector"); }

	@Override public default String 			printDimension() 			{ return "[ " + dim() + " ]"; }
	@Override
	public default String 						printValues() {
		StringBuilder sb = new StringBuilder();

		sb.append("[ ");
		for(int i = 0; i < dim() - 1; ++i)
			sb.append(getNumber(i) + ", ");
		sb.append(getNumber(dim() - 1));
		sb.append(" ]");

		return sb.toString();
	}
*/
	
/*
Toute la journée, j'ai pensé que ton message pouvait avoir un sens, et j'ai attendu...
Tu ne fais que confirmer qu'il n'avait de but que de te dédouaner
 
 
*/
	
	
	
}
