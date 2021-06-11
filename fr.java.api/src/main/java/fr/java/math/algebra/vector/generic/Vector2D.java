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

import fr.java.lang.enums.Primitive;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;

public interface Vector2D extends DoubleVector {

	public static interface Editable extends Vector2D, DoubleVector.Editable {

		public void   				set(final double _t);
		public void   				set(final double _x, final double _y);
		public void 				set(final double[] _values);

		public void 				set(final Number _value);
		public void 				set(final Number _x, final Number _y);
		public void 				set(final Number[] _values);

		public void   				set(final Point2D _pt);
		public void   				set(final Vector2D _vec);
//		public void   				set(final NumberVector _vec);

		public void   				setX(final double _x);
		public void   				setY(final double _y);

		public void					setMagnitude(double _mag);

		public Vector2D.Editable 	plusEquals(final double _t);
		public Vector2D.Editable 	plusEquals(final double _x, final double _y);
		public Vector2D.Editable 	plusEquals(final double[] _vec);
		public Vector2D.Editable 	plusEquals(final Number _value);
		public Vector2D.Editable 	plusEquals(final Number _x, final Number _y);
		public Vector2D.Editable 	plusEquals(final Number[] _values);
		public Vector2D.Editable	plusEquals(final Point2D _pt);
		public Vector2D.Editable	plusEquals(final Vector2D _vec);
		public Vector2D.Editable	plusEquals(final Dimension2D _dim);
		public Vector2D.Editable	plusEquals(final Coordinate.TwoDims _c);
		public Vector2D.Editable	plusEquals(final Dimension.TwoDims _dim);
//		public Vector2D.Editable	plusEquals(final NumberVector _vec);
//		public Vector2D.Editable	plusEquals(final DoubleVector _vec);

		public Vector2D.Editable	minusEquals(final double _t);
		public Vector2D.Editable	minusEquals(final double _x, final double _y);
		public Vector2D.Editable	minusEquals(final double[] _vec);
		public Vector2D.Editable	minusEquals(final Number _value);
		public Vector2D.Editable	minusEquals(final Number _x, final Number _y);
		public Vector2D.Editable	minusEquals(final Number[] _values);
		public Vector2D.Editable	minusEquals(final Point2D _pt);
		public Vector2D.Editable	minusEquals(final Vector2D _vec);
		public Vector2D.Editable	minusEquals(final Dimension2D _dim);
		public Vector2D.Editable	minusEquals(final Coordinate.TwoDims _c);
		public Vector2D.Editable	minusEquals(final Dimension.TwoDims _dim);
//		public Vector2D.Editable	minusEquals(final NumberVector _vec);
//		public Vector2D.Editable	minusEquals(final DoubleVector _vec);

		public Vector2D.Editable 	timesEquals(final double _t);
		public Vector2D.Editable 	timesEquals(final double _x, final double _y);
		public Vector2D.Editable 	timesEquals(final double[] _vec);
		public Vector2D.Editable 	timesEquals(final Number _value);
		public Vector2D.Editable 	timesEquals(final Number _x, final Number _y);
		public Vector2D.Editable 	timesEquals(final Number[] _values);
		public Vector2D.Editable 	timesEquals(final Vector2D _vec);
		public Vector2D.Editable 	timesEquals(final Dimension2D _dim);
		public Vector2D.Editable 	timesEquals(final Dimension.TwoDims _dim);
//		public Vector2D.Editable	timesEquals(final NumberVector _vec);
//		public Vector2D.Editable	timesEquals(final DoubleVector _vec);

		public Vector2D.Editable 	dividesEquals(final double _t);
		public Vector2D.Editable 	dividesEquals(final double _x, final double _y);
		public Vector2D.Editable 	dividesEquals(final double[] _vec);
		public Vector2D.Editable 	dividesEquals(final Number _value);
		public Vector2D.Editable 	dividesEquals(final Number _x, final Number _y);
		public Vector2D.Editable 	dividesEquals(final Number[] _values);
		public Vector2D.Editable 	dividesEquals(final Vector2D _vec);
		public Vector2D.Editable 	dividesEquals(final Dimension2D _dim);
		public Vector2D.Editable 	dividesEquals(final Dimension.TwoDims _dim);
//		public Vector2D.Editable	dividesEquals(final NumberVector _vec);
//		public Vector2D.Editable	dividesEquals(final DoubleVector _vec);

		public Vector2D.Editable 	clone();
	}

	@Override
	public default Primitive 		getPrimitive() {
		return Primitive.DOUBLE;
	}

	@Override
	public default int 				getCapacity() {
		return 2;
	}
	@Override
	public default int 				size() {
		return 2;
	}

	public double 					getX();
	public double 					getY();

	public double					getMagnitude();

	public Vector2D 				plus(final double _t);
	public Vector2D 				plus(final double _x, final double _y);
	public Vector2D 				plus(final double[] _vec);
	public Vector2D 				plus(final Number _value);
	public Vector2D 				plus(final Number _x, final Number _y);
	public Vector2D 				plus(final Number[] _values);
	public Vector2D					plus(final Point2D _pt);
	public Vector2D					plus(final Vector2D _vec);
	public Vector2D					plus(final Dimension2D _dim);
	public Vector2D					plus(final Coordinate.TwoDims _c);
	public Vector2D					plus(final Dimension.TwoDims _dim);
//	public Vector1D					plus(final NumberVector _vec);
//	public Vector1D					plus(final DoubleVector _vec);

	public Vector2D 				minus(final double _t);
	public Vector2D 				minus(final double _x, final double _y);
	public Vector2D 				minus(final double[] _vec);
	public Vector2D 				minus(final Number _value);
	public Vector2D 				minus(final Number _x, final Number _y);
	public Vector2D 				minus(final Number[] _values);
	public Vector2D 				minus(final Point2D _pt);
	public Vector2D 				minus(final Vector2D _vec);
	public Vector2D 				minus(final Dimension2D _dim);
	public Vector2D 				minus(final Coordinate.TwoDims _c);
	public Vector2D 				minus(final Dimension.TwoDims _dim);
//	public Vector1D					minus(final NumberVector _vec);
//	public Vector1D					minus(final DoubleVector _vec);

	public Vector2D 				times(final double _t);
	public Vector2D 				times(final double _x, final double _y);
	public Vector2D 				times(final double[] _vec);
	public Vector2D 				times(final Number _value);
	public Vector2D 				times(final Number _x, final Number _y);
	public Vector2D 				times(final Number[] _values);
	public Vector2D 				times(final Vector2D _vec);
	public Vector2D 				times(final Dimension2D _dim);
	public Vector2D 				times(final Dimension.TwoDims _dim);
//	public Vector1D					times(final NumberVector _vec);
//	public Vector1D					times(final DoubleVector _vec);

	public Vector2D 				divides(final double _t);
	public Vector2D 				divides(final double _x, final double _y);
	public Vector2D 				divides(final double[] _vec);
	public Vector2D 				divides(final Number _value);
	public Vector2D 				divides(final Number _x, final Number _y);
	public Vector2D 				divides(final Number[] _values);
	public Vector2D 				divides(final Vector2D _vec);
	public Vector2D 				divides(final Dimension2D _dim);
	public Vector2D 				divides(final Dimension.TwoDims _dim);
//	public Vector1D					divides(final NumberVector _vec);
//	public Vector1D					divides(final DoubleVector _vec);

	public Vector2D 				normalize(final double _norm);

	public boolean 					isEqual(final double _x, final double _y);
	public boolean 					isEqual(final double[] _v);
	public boolean 					isEqual(final Vector2D _vec);

	public boolean 					isDifferent(final double _x, final double _y);
	public boolean 					isDifferent(final double[] _v);
	public boolean 					isDifferent(final Vector2D _vec);

	public Vector2D					clone();
	public Vector2D.Editable 		cloneEditable();

	public Vector2D 				abs();
	public Vector2D 				negate();
	public Vector2D 				normalized();

	public Vector1D 				downgrade();
	public Vector3D 				uniform();
	public Vector3D 				uniform(double _w);

	public boolean 					isOrthogonal(final Vector2D _vec);
	public boolean 					isColinear(final Vector2D _vec);

	public double 					dotProduct(final double _x, final double _y);
	public double 					dotProduct(final Vector2D _vector);
	public Vector2D					crossProduct(final double _x, final double _y);
	public Vector2D					crossProduct(final Vector2D _vector);

}
