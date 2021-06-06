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

import fr.java.math.algebra.NumberVector;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Point;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public interface Point2D extends Point, Coordinate.Cartesian2D, Shape2D {
	public static final Point2D  NaN  = new Point2D() {
		private static final long serialVersionUID = -9040236429462595119L;

		@Override
		public CoordinateSystem 	getCoordinateSystem() {
			return null;
		}

		@Override public double 	getX() 								{ return Double.NaN; }
		@Override public double 	getY() 								{ return Double.NaN; }

		@Override public Point2D 	plus(double _t) 					{ return this; }
		@Override public Point2D 	plus(double[] _vec) 				{ return this; }
		@Override public Point2D 	plus(double _x, double _y) 			{ return this; }
		@Override public Point2D 	plus(NumberVector _vec) 			{ return this; }
		@Override public Point2D 	plus(Coordinate.TwoDims _pt) 		{ return this; }
		@Override public Point2D 	plus(Point2D _pt) 					{ return this; }
		@Override public Point2D 	plus(Vector2D _vec) 				{ return this; }
		@Override public Point2D 	plus(Dimension2D _dim) 				{ return this; }
		@Override public Point2D 	plus(Dimension.TwoDims _dim) 		{ return this; }

		@Override public Point2D 	minus(double _t) 					{ return this; }
		@Override public Point2D 	minus(double[] _vec) 				{ return this; }
		@Override public Point2D 	minus(double _x, double _y) 		{ return this; }
		@Override public Point2D 	minus(NumberVector _vec) 			{ return this; }
		@Override public Point2D 	minus(Coordinate.TwoDims _pt) 		{ return this; }
		@Override public Point2D 	minus(Point2D _pt) 					{ return this; }
		@Override public Point2D 	minus(Vector2D _vec) 				{ return this; }
		@Override public Point2D 	minus(Dimension2D _dim) 			{ return this; }
		@Override public Point2D 	minus(Dimension.TwoDims _dim) 		{ return this; }

		@Override public Point2D 	times(double _t) 					{ return this; }
		@Override public Point2D 	times(double _x, double _y) 		{ return this; }
		@Override public Point2D 	times(Vector2D _vec) 				{ return this; }

		@Override public Point2D 	divides(double _t) 					{ return this; }
		@Override public Point2D 	divides(double _x, double _y) 		{ return this; }

		@Override public Point2D 	abs() 								{ return this; }
		@Override public Point2D 	negate() 							{ return this; }
		@Override public Point2D 	normalized() 						{ return this; }

		@Override public boolean 	isEqual(double _x, double _y) 		{ return _x == Double.NaN && _y == Double.NaN; }
		@Override public boolean 	isEqual(Point2D _pt) 				{ return _pt == this; }

		@Override public boolean 	isDifferent(double _x, double _y) 	{ return _x != Double.NaN || _y != Double.NaN; }
		@Override public boolean 	isDifferent(Point2D _pt) 			{ return _pt != this; }

		@Override public Vector3D 	uniform() 							{ return null; }
		@Override public Vector3D 	uniform(double _w) 					{ return null; }

		@Override public Point2D 	clone() 							{ return this; }

		@Override public int 		compareTo(Object o) 				{ return 0; }

		@Override public String 	toString() 							{ return "Point2D::NaN"; }
		@Override public String 	toString(NumberFormat _nf) 			{ return toString(); }
		@Override public String 	toString(DecimalFormat _df) 		{ return toString(); }

	};

	public static interface Editable extends Point2D, Coordinate.Cartesian2D.Editable {

		public void   	set(final double _x, final double _y);
		public void   	set(final double[] _values);
		public void   	set(final Point2D _pt);

		public Point2D 	plusEquals(final double _t);
		public Point2D 	plusEquals(final double[] _vec);
		public Point2D 	plusEquals(final double _x, final double _y);
		public Point2D 	plusEquals(final NumberVector _vec);
		public Point2D 	plusEquals(final Point2D _pt);
		public Point2D 	plusEquals(final Vector2D _vec);
		public Point2D 	plusEquals(final Dimension2D _dim);
		public Point2D 	plusEquals(final Dimension.TwoDims _dim);

		public Point2D 	minusEquals(final double _t);
		public Point2D 	minusEquals(final double[] _vec);
		public Point2D 	minusEquals(final double _x, final double _y);
		public Point2D 	minusEquals(final NumberVector _vec);
		public Point2D 	minusEquals(final Point2D _pt);
		public Point2D 	minusEquals(final Vector2D _vec);
		public Point2D 	minusEquals(final Dimension2D _dim);
		public Point2D 	minusEquals(final Dimension.TwoDims _dim);

		public Point2D 	timesEquals(final double _t);
		public Point2D 	timesEquals(final double _x, final double _y);
		public Point2D 	timesEquals(final Vector2D _vec);

	}

	public Point2D 	plus(final double _t);
	public Point2D 	plus(final double[] _vec);
	public Point2D 	plus(final double _x, final double _y);
	public Point2D 	plus(final Coordinate.TwoDims _c);
	public Point2D 	plus(final NumberVector _vec);
	public Point2D 	plus(final Point2D _pt);
	public Point2D 	plus(final Vector2D _vec);
	public Point2D 	plus(final Dimension2D _dim);
	public Point2D 	plus(final Dimension.TwoDims _dim);

	public Point2D 	minus(final double _t);
	public Point2D 	minus(final double[] _vec);
	public Point2D 	minus(final double _x, final double _y);
	public Point2D 	minus(final Coordinate.TwoDims _c);
	public Point2D 	minus(final NumberVector _vec);
	public Point2D 	minus(final Point2D _pt);
	public Point2D 	minus(final Vector2D _vec);
	public Point2D 	minus(final Dimension2D _dim);
	public Point2D 	minus(final Dimension.TwoDims _dim);

	public Point2D 	times(final double _t);
	public Point2D 	times(final double _x, final double _y);
	public Point2D 	times(final Vector2D _vec);

	public Point2D 	divides(final double _t);
	public Point2D 	divides(final double _x, final double _y);

	public Point2D 	clone();
	public Point2D 	abs();
	public Point2D 	negate();
	public Point2D 	normalized();

	public boolean 	isEqual(final double _x, final double _y);
	public boolean 	isEqual(final Point2D _pt);
	public boolean 	isDifferent(final double _x, final double _y);
	public boolean 	isDifferent(final Point2D _pt);

	public Vector3D uniform();
	public Vector3D uniform(double _w);

}
