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
 * @file     DoubleMatrix.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.algebra.matrix;

import fr.java.lang.enums.Primitive;
import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;

public interface DoubleMatrix extends NumberMatrix, DoubleTensor {

	public interface Editable extends DoubleMatrix, NumberMatrix.Editable, DoubleTensor.Editable {

		public void 				 set(final int _i, final int _j, final double _value);

		public DoubleMatrix.Editable plusEquals(final double  t);
		public DoubleMatrix.Editable plusEquals(final Number _t);
		public DoubleMatrix.Editable plusEquals(final NumberMatrix _m);
		public DoubleMatrix.Editable plusEquals(final DoubleMatrix B);

		public DoubleMatrix.Editable minusEquals(final double  t);
		public DoubleMatrix.Editable minusEquals(final Number _t);
		public DoubleMatrix.Editable minusEquals(final NumberMatrix _m);
		public DoubleMatrix.Editable minusEquals(final DoubleMatrix B);

		public DoubleMatrix.Editable timesEquals(final double s);
		public DoubleMatrix.Editable timesEquals(final Number _v);
		public DoubleMatrix.Editable timesEquals(final NumberMatrix _m);
		public DoubleMatrix.Editable timesEquals(final DoubleMatrix B);

		public DoubleMatrix.Editable arrayTimesEquals(final DoubleMatrix B);

		public DoubleMatrix.Editable arrayRightDivideEquals(final DoubleMatrix B);

		public DoubleMatrix.Editable arrayLeftDivideEquals(final DoubleMatrix B);

	}

	// TENSOR METHODS
	@Override
	public default Primitive 	getPrimitive() {
		return Primitive.DOUBLE;
	}

	// MATRIX METHODS
	public double 				get(final int _i, final int _j);

	@Override
	public DoubleVector			getRow(final int _i);
	@Override
	public DoubleVector			getColumn(final int _j);

	public DoubleMatrix 		plus(final double  t);
	public DoubleMatrix 		plus(final DoubleMatrix B);

	public DoubleMatrix 		minus(final double  t);
	public DoubleMatrix 		minus(final DoubleMatrix B);

	public DoubleMatrix 		times(final double s);
	public DoubleMatrix 		times(final DoubleMatrix B);
	public DoubleVector 		times(final DoubleVector b);

	public DoubleMatrix 		arrayTimes(final DoubleMatrix B);

	public DoubleMatrix 		arrayRightDivide(final DoubleMatrix B);

	public DoubleMatrix 		arrayLeftDivide(final DoubleMatrix B);

}
