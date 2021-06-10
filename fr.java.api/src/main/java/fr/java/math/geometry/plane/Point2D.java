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
 * @file     Point2D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.plane;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Point;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public interface Point2D extends Point, Coordinate.Cartesian2D {
	public static interface Editable extends Point2D, Coordinate.Cartesian2D.Editable {

		public void   				set(final double _t);
		public void   				set(final double _x, final double _y);
		public void 				set(final double[] _values);
		public void 				set(final Number _value);
		public void 				set(final Number _x, final Number _y);
		public void 				set(final Number[] _values);
		public void   				set(final Point2D _pt);
		public void   				set(final Vector2D _vec);
//		public void   				set(final NumberVector _vec);

		public Point2D.Editable 	plusEquals(final double _t);
		public Point2D.Editable 	plusEquals(final double[] _vec);
		public Point2D.Editable 	plusEquals(final double _x, final double _y);
		public Point2D.Editable 	plusEquals(final Number _t);
		public Point2D.Editable 	plusEquals(final Number[] _vec);
		public Point2D.Editable 	plusEquals(final Number _x, final Number _y);
		public Point2D.Editable 	plusEquals(final Point2D _pt);
		public Point2D.Editable 	plusEquals(final Vector2D _vec);
		public Point2D.Editable 	plusEquals(final Dimension2D _dim);
		public Point2D.Editable 	plusEquals(final Coordinate.TwoDims _dim);
		public Point2D.Editable 	plusEquals(final Dimension.TwoDims _dim);
//		public Point2D.Editable 	plusEquals(final NumberVector _vec);

		public Point2D.Editable 	minusEquals(final double _t);
		public Point2D.Editable 	minusEquals(final double[] _vec);
		public Point2D.Editable 	minusEquals(final double _x, final double _y);
		public Point2D.Editable 	minusEquals(final Number _t);
		public Point2D.Editable 	minusEquals(final Number[] _vec);
		public Point2D.Editable 	minusEquals(final Number _x, final Number _y);
		public Point2D.Editable 	minusEquals(final Point2D _pt);
		public Point2D.Editable 	minusEquals(final Vector2D _vec);
		public Point2D.Editable 	minusEquals(final Dimension2D _dim);
		public Point2D.Editable 	minusEquals(final Coordinate.TwoDims _dim);
		public Point2D.Editable 	minusEquals(final Dimension.TwoDims _dim);
//		public Point2D.Editable 	minusEquals(final NumberVector _vec);

		public Point2D.Editable 	timesEquals(final double _t);
		public Point2D.Editable 	timesEquals(final double _x, final double _y);
		public Point2D.Editable 	timesEquals(final double[] _vec);
		public Point2D.Editable 	timesEquals(final Number _x);
		public Point2D.Editable 	timesEquals(final Number _x, final Number _y);
		public Point2D.Editable 	timesEquals(final Number[] _vec);
		public Point2D.Editable 	timesEquals(final Vector2D _vec);
		public Point2D.Editable 	timesEquals(final Dimension2D _dim);
		public Point2D.Editable 	timesEquals(final Dimension.TwoDims _dim);
//		public Point2D.Editable 	timesEquals(final NumberVector _vec);

		public Point2D.Editable 	dividesEquals(final double _t);
		public Point2D.Editable 	dividesEquals(final double _x, final double _y);
		public Point2D.Editable 	dividesEquals(final double[] _vec);
		public Point2D.Editable 	dividesEquals(final Number _x);
		public Point2D.Editable 	dividesEquals(final Number _x, final Number _y);
		public Point2D.Editable 	dividesEquals(final Number[] _vec);
		public Point2D.Editable 	dividesEquals(final Vector2D _vec);
		public Point2D.Editable 	dividesEquals(final Dimension2D _dim);
		public Point2D.Editable 	dividesEquals(final Dimension.TwoDims _dim);
//		public Point2D.Editable 	dividesEquals(final NumberVector _vec);

		public Point2D.Editable		clone();

	}

	public Point2D 					plus(final double _t);
	public Point2D 					plus(final double _x, final double _y);
	public Point2D 					plus(final double[] _vec);
	public Point2D 					plus(final Number _x);
	public Point2D 					plus(final Number _x, final Number _y);
	public Point2D 					plus(final Number[] _vec);
	public Point2D 					plus(final Point2D _pt);
	public Point2D 					plus(final Vector2D _vec);
	public Point2D 					plus(final Dimension2D _dim);
	public Point2D 					plus(final Coordinate.TwoDims _c);
	public Point2D 					plus(final Dimension.TwoDims _dim);
//	public Point2D 					plus(final NumberVector _vec);

	public Point2D 					minus(final double _t);
	public Point2D 					minus(final double _x, final double _y);
	public Point2D 					minus(final double[] _vec);
	public Point2D 					minus(final Number _t);
	public Point2D 					minus(final Number _x, final Number _y);
	public Point2D 					minus(final Number[] _vec);
	public Point2D 					minus(final Point2D _pt);
	public Point2D 					minus(final Vector2D _vec);
	public Point2D 					minus(final Dimension2D _dim);
	public Point2D 					minus(final Coordinate.TwoDims _c);
	public Point2D 					minus(final Dimension.TwoDims _dim);
//	public Point2D 					minus(final NumberVector _vec);

	public Point2D 					times(final double _t);
	public Point2D 					times(final double _x, final double _y);
	public Point2D 					times(final double[] _vec);
	public Point2D 					times(final Number _x);
	public Point2D 					times(final Number _x, final Number _y);
	public Point2D 					times(final Number[] _vec);
	public Point2D 					times(final Vector2D _vec);
	public Point2D 					times(final Dimension2D _dim);
	public Point2D 					times(final Dimension.TwoDims _dim);
//	public Point2D  				times(final NumberVector _vec);

	public Point2D 					divides(final double _t);
	public Point2D 					divides(final double _x, final double _y);
	public Point2D 					divides(final double[] _vec);
	public Point2D 					divides(final Number _x);
	public Point2D 					divides(final Number _x, final Number _y);
	public Point2D 					divides(final Number[] _vec);
	public Point2D 					divides(final Vector2D _vec);
	public Point2D 					divides(final Dimension2D _dim);
	public Point2D 					divides(final Dimension.TwoDims _dim);
//	public Point2D  				divides(final NumberVector _vec);

	public Point2D 					clone();
	public Point2D.Editable 		cloneEditable();

	public Point2D 					abs();
	public Point2D 					negate();
	public Point2D 					normalized();

	public boolean 					isEqual(final double _x, final double _y);
	public boolean 					isEqual(final Point2D _pt);
	public boolean 					isDifferent(final double _x, final double _y);
	public boolean 					isDifferent(final Point2D _pt);

}
