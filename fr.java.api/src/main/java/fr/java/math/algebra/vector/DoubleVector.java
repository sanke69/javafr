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

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotSupportedException;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.tensor.DoubleTensor;

public interface DoubleVector extends NumberVector, DoubleTensor {

	public static interface Editable extends DoubleVector, NumberVector.Editable, DoubleTensor.Editable {

		public void 			set(final double _value);
		public void 			set(final double[] _values);
		public void 			set(final DoubleVector _vector);

		public void 			set(final double _value, final int _i);

		public DoubleVector 	plusEquals(final double _value);
		public DoubleVector 	plusEquals(final double[] _values);
		public DoubleVector 	plusEquals(final DoubleVector _vector);

		public DoubleVector 	minusEquals(final double _value);
		public DoubleVector 	minusEquals(final double[] _values);
		public DoubleVector 	minusEquals(final DoubleVector _vector);

		public DoubleVector 	timesEquals(final double _value);
		public DoubleVector 	timesEquals(final double[] _values);
		public DoubleVector 	timesEquals(final DoubleVector _vector);

		public DoubleVector 	dividesEquals(final double _value);
		public DoubleVector 	dividesEquals(final double[] _values);
		public DoubleVector 	dividesEquals(final DoubleVector _vector);

		public DoubleVector.Editable 	clone() throws NotSupportedException, CloneNotSupportedException;

	}
	
	// TENSOR METHODS
	@Override
	public default Primitive 	getPrimitive() {
		return Primitive.DOUBLE;
	}

	public double 				get(final int _i);

	public DoubleVector 		plus(final double _value);
	public DoubleVector 		plus(final double[] _values);
	public DoubleVector 		plus(final DoubleVector _vector);

	public DoubleVector 		minus(final double _value);
	public DoubleVector 		minus(final double[] _values);
	public DoubleVector 		minus(final DoubleVector _vector);

	public DoubleVector 		times(final double _value);
	public DoubleVector 		times(final double[] _values);
	public DoubleVector 		times(final DoubleVector _vector);

	public DoubleVector 		divides(final double _value);
	public DoubleVector 		divides(final double[] _values);
	public DoubleVector 		divides(final DoubleVector _vector);

	public boolean 				isEqual(final double _t);
	public boolean 				isEqual(final double[] _v);
	public boolean 				isEqual(final DoubleVector _d);

	public boolean 				isDifferent(final double _t);
	public boolean 				isDifferent(final double[] _v);
	public boolean 				isDifferent(final DoubleVector _d);

	public boolean 				isColinear(final DoubleVector _vector);
	public boolean 				isOrthogonal(final DoubleVector _vector);

	@Override
	public DoubleVector 			clone() throws NotSupportedException, CloneNotSupportedException;
	public DoubleVector.Editable 	cloneEditable() throws NotSupportedException, CloneNotSupportedException;

}
