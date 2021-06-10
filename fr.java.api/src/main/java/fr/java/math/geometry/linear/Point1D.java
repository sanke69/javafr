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
package fr.java.math.geometry.linear;

import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Point;
import fr.java.math.topology.Coordinate;

public interface Point1D extends Point, Coordinate.Cartesian1D {

	public static interface Editable extends Point1D, Coordinate.Cartesian1D.Editable {

		public void   				set(final double _x);
		public void   				set(final double[] _values);
		public void   				set(final Number _x);
		public void   				set(final Number[] _values);
		public void   				set(final Point1D _pt);
		public void   				set(final Vector1D _vec);
//		public void   				set(final NumberVector _vec);

		public Point1D.Editable 	plusEquals(final double _t);
		public Point1D.Editable 	plusEquals(final double[] _vec);
		public Point1D.Editable 	plusEquals(final Number _x);
		public Point1D.Editable 	plusEquals(final Number[] _x);
		public Point1D.Editable 	plusEquals(final Point1D _pt);
		public Point1D.Editable 	plusEquals(final Vector1D _vec);
		public Point1D.Editable 	plusEquals(final Dimension1D _dim);
		public Point1D.Editable 	plusEquals(final Coordinate.OneDim _dim);
		public Point1D.Editable 	plusEquals(final Dimension.OneDim _dim);
//		public Point1D.Editable 	plusEquals(final NumberVector _vec);

		public Point1D.Editable 	minusEquals(final double _t);
		public Point1D.Editable 	minusEquals(final double[] _vec);
		public Point1D.Editable 	minusEquals(final Number _x);
		public Point1D.Editable 	minusEquals(final Number[] _vec);
		public Point1D.Editable 	minusEquals(final Point1D _pt);
		public Point1D.Editable 	minusEquals(final Vector1D _vec);
		public Point1D.Editable 	minusEquals(final Dimension1D _dim);
		public Point1D.Editable 	minusEquals(final Coordinate.OneDim _dim);
		public Point1D.Editable 	minusEquals(final Dimension.OneDim _dim);
//		public Point1D.Editable 	minusEquals(final NumberVector _vec);

		public Point1D.Editable 	timesEquals(final double _t);
		public Point1D.Editable 	timesEquals(final double[] _vec);
		public Point1D.Editable 	timesEquals(final Number _x);
		public Point1D.Editable 	timesEquals(final Number[] _vec);
		public Point1D.Editable 	timesEquals(final Vector1D _vec);
		public Point1D.Editable 	timesEquals(final Dimension1D _dim);
		public Point1D.Editable 	timesEquals(final Dimension.OneDim _dim);
//		public Point1D.Editable 	timesEquals(final NumberVector _vec);

		public Point1D.Editable 	dividesEquals(final double _t);
		public Point1D.Editable 	dividesEquals(final double[] _vec);
		public Point1D.Editable 	dividesEquals(final Number _x);
		public Point1D.Editable 	dividesEquals(final Number[] _vec);
		public Point1D.Editable 	dividesEquals(final Vector1D _vec);
		public Point1D.Editable 	dividesEquals(final Dimension1D _dim);
		public Point1D.Editable 	dividesEquals(final Dimension.OneDim _dim);
//		public Point1D.Editable 	dividesEquals(final NumberVector _vec);

		public Point1D.Editable 	clone();

	}

	public Point1D 					plus(final double _t);
	public Point1D 					plus(final double[] _vec);
	public Point1D 					plus(final Number _x);
	public Point1D 					plus(final Number[] _vec);
	public Point1D 					plus(final Point1D _pt);
	public Point1D 					plus(final Vector1D _vec);
	public Point1D 					plus(final Dimension1D _dim);
	public Point1D 					plus(final Coordinate.OneDim _c);
	public Point1D 					plus(final Dimension.OneDim _dim);
//	public Point1D 					plus(final NumberVector _dim);

	public Point1D 					minus(final double _t);
	public Point1D 					minus(final double[] _vec);
	public Point1D 					minus(final Number _x);
	public Point1D 					minus(final Number[] _vec);
	public Point1D 					minus(final Point1D _pt);
	public Point1D 					minus(final Vector1D _vec);
	public Point1D 					minus(final Dimension1D _dim);
	public Point1D 					minus(final Coordinate.OneDim _c);
	public Point1D 					minus(final Dimension.OneDim _dim);
//	public Point1D 					minus(final NumberVector _dim);

	public Point1D 					times(final double _t);
	public Point1D 					times(final double[] _vec);
	public Point1D 					times(final Number _x);
	public Point1D 					times(final Number[] _vec);
	public Point1D 					times(final Vector1D _vec);
	public Point1D 					times(final Dimension1D _dim);
	public Point1D 					times(final Dimension.OneDim _dim);
//	public Point1D 					times(final NumberVector _dim);

	public Point1D 					divides(final double _t);
	public Point1D 					divides(final double[] _vec);
	public Point1D 					divides(final Number _x);
	public Point1D 					divides(final Number[] _vec);
	public Point1D 					divides(final Vector1D _vec);
	public Point1D 					divides(final Dimension1D _dim);
	public Point1D 					divides(final Dimension.OneDim _dim);
//	public Point1D 					divides(final NumberVector _dim);

	public Point1D 					clone();
	public Point1D.Editable 		cloneEditable();

	public Point1D 					abs();
	public Point1D 					negate();
	public Point1D 					normalized();

	public boolean 					isEqual(final double _x);
	public boolean 					isEqual(final Point1D _pt);
	public boolean 					isDifferent(final double _x);
	public boolean 					isDifferent(final Point1D _pt);

}
