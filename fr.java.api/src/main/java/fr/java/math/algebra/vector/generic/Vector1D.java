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
package fr.java.math.algebra.vector.generic;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.lang.enums.Primitive;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.linear.Dimension1D;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.topology.Coordinate;

public interface Vector1D extends DoubleVector {

	public static interface Editable extends Vector1D, DoubleVector.Editable {

		public void   				set(final double _t);
		public void 				set(final double[] _values);

		public void 				set(final Number _value);
		public void 				set(final Number[] _values);

		public void   				set(final Point1D _pt);
		public void   				set(final Vector1D _vec);
//		public void   				set(final NumberVector _vec);

		public void   				setX(final double _x);

		public void					setMagnitude(double _mag);

		public Vector1D.Editable	plusEquals(final double _t);
		public Vector1D.Editable	plusEquals(final double[] _vec);
		public Vector1D.Editable	plusEquals(final Number _x);
		public Vector1D.Editable	plusEquals(final Number[] _x);
		public Vector1D.Editable	plusEquals(final Point1D _pt);
		public Vector1D.Editable	plusEquals(final Vector1D _vec);
		public Vector1D.Editable	plusEquals(final Dimension1D _dim);
		public Vector1D.Editable	plusEquals(final Coordinate.OneDim _c);
		public Vector1D.Editable	plusEquals(final Dimension.OneDim _dim);
//		public Vector1D.Editable	plusEquals(final NumberVector _vec);
//		public Vector1D.Editable	plusEquals(final DoubleVector _vec);

		public Vector1D.Editable	minusEquals(final double _t);
		public Vector1D.Editable	minusEquals(final double[] _vec);
		public Vector1D.Editable	minusEquals(final Number _x);
		public Vector1D.Editable	minusEquals(final Number[] _vec);
		public Vector1D.Editable	minusEquals(final Point1D _pt);
		public Vector1D.Editable	minusEquals(final Vector1D _vec);
		public Vector1D.Editable	minusEquals(final Dimension1D _dim);
		public Vector1D.Editable	minusEquals(final Coordinate.OneDim _dim);
		public Vector1D.Editable	minusEquals(final Dimension.OneDim _dim);
//		public Vector1D.Editable	minusEquals(final NumberVector _vec);
//		public Vector1D.Editable	minusEquals(final DoubleVector _vec);

		public Vector1D.Editable	timesEquals(final double _t);
		public Vector1D.Editable	timesEquals(final double[] _vec);
		public Vector1D.Editable	timesEquals(final Number _x);
		public Vector1D.Editable	timesEquals(final Number[] _vec);
		public Vector1D.Editable	timesEquals(final Vector1D _vec);
		public Vector1D.Editable	timesEquals(final Dimension1D _dim);
		public Vector1D.Editable	timesEquals(final Dimension.OneDim _dim);
//		public Vector1D.Editable	timesEquals(final NumberVector _vec);
//		public Vector1D.Editable	timesEquals(final DoubleVector _vec);

		public Vector1D.Editable	dividesEquals(final double _t);
		public Vector1D.Editable	dividesEquals(final double[] _vec);
		public Vector1D.Editable	dividesEquals(final Number _x);
		public Vector1D.Editable	dividesEquals(final Number[] _vec);
		public Vector1D.Editable	dividesEquals(final Vector1D _vec);
		public Vector1D.Editable	dividesEquals(final Dimension1D _dim);
		public Vector1D.Editable	dividesEquals(final Dimension.OneDim _dim);
//		public Vector1D.Editable	dividesEquals(final NumberVector _vec);
//		public Vector1D.Editable	dividesEquals(final DoubleVector _vec);

		public Vector1D.Editable	clone();

	}

	@Override
	public default Primitive 		getPrimitive() {
		return Primitive.DOUBLE;
	}

	@Override
	public default int 				getCapacity() {
		return 1;
	}
	@Override
	public default int 				size() {
		return 1;
	}

	public double 					getX();

	public double 					getMagnitude();

	public Vector1D					plus(final double _t);
	public Vector1D					plus(final double[] _vec);
	public Vector1D					plus(final Number _x);
	public Vector1D					plus(final Number[] _vec);
	public Vector1D					plus(final Point1D _pt);
	public Vector1D					plus(final Vector1D _vec);
	public Vector1D					plus(final Dimension1D _dim);
	public Vector1D					plus(final Coordinate.OneDim _c);
	public Vector1D					plus(final Dimension.OneDim _dim);
//	public Vector1D					plus(final NumberVector _vec);
//	public Vector1D					plus(final DoubleVector _vec);

	public Vector1D					minus(final double _t);
	public Vector1D					minus(final double[] _vec);
	public Vector1D					minus(final Number _x);
	public Vector1D					minus(final Number[] _vec);
	public Vector1D					minus(final Point1D _pt);
	public Vector1D					minus(final Vector1D _vec);
	public Vector1D					minus(final Dimension1D _dim);
	public Vector1D					minus(final Coordinate.OneDim _c);
	public Vector1D					minus(final Dimension.OneDim _dim);
//	public Vector1D					minus(final NumberVector _vec);
//	public Vector1D					minus(final DoubleVector _vec);

	public Vector1D					times(final double _t);
	public Vector1D					times(final double[] _vec);
	public Vector1D					times(final Number _x);
	public Vector1D					times(final Number[] _vec);
	public Vector1D					times(final Vector1D _vec);
	public Vector1D					times(final Dimension1D _dim);
	public Vector1D					times(final Dimension.OneDim _dim);
//	public Vector1D					times(final NumberVector _vec);
//	public Vector1D					times(final DoubleVector _vec);

	public Vector1D					divides(final double _t);
	public Vector1D					divides(final double[] _vec);
	public Vector1D					divides(final Number _x);
	public Vector1D					divides(final Number[] _vec);
	public Vector1D					divides(final Vector1D _vec);
	public Vector1D					divides(final Dimension1D _dim);
	public Vector1D					divides(final Dimension.OneDim _dim);
//	public Vector1D					divides(final NumberVector _vec);
//	public Vector1D					divides(final DoubleVector _vec);

	public Vector1D 				normalize(final double _norm);

	public boolean 					isEqual(final double _x);
	public boolean 					isEqual(final double[] _v);
	public boolean 					isEqual(final Vector1D _vec);

	public boolean 					isDifferent(final double _x);
	public boolean 					isDifferent(final double[] _v);
	public boolean 					isDifferent(final Vector1D _vec);

	public Vector1D					clone();
	public Vector1D.Editable		cloneEditable();

	public Vector1D 				abs();
	public Vector1D 				negate();
	public Vector1D 				normalized();

	public Vector2D 				uniform();
	public Vector2D 				uniform(final double _w);

	public String 					toString(NumberFormat _nf);
	public String 					toString(DecimalFormat _df);

}
