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
package fr.java.math.geometry.space;

import java.text.NumberFormat;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.Point;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public interface Point3D extends Point, Coordinate.Cartesian3D {

	public static interface Editable extends Point3D {

		public void   	set(final double _t);
		public void   	set(final double _x, final double _y, final double _z);
		public void   	set(final double[] _v);
		public void   	set(final Number _t);
		public void   	set(final Number _x, final Number _y, final Number _z);
		public void   	set(final Number[] _v);
		public void   	set(final Point3D  _pt);
		public void 	set(final Vector3D _vec);

		public void   	setX(final double _x);
		public void   	setY(final double _y);
		public void   	setZ(final double _z);

		public Point3D.Editable 	plusEquals(final double _t);
		public Point3D.Editable 	plusEquals(final double _x, final double _y, final double _z);
		public Point3D.Editable 	plusEquals(final double[] _vec);
		public Point3D.Editable 	plusEquals(final Number _t);
		public Point3D.Editable 	plusEquals(final Number _x, final Number _y, final Number _z);
		public Point3D.Editable 	plusEquals(final Number[] _vec);
		public Point3D.Editable 	plusEquals(final Point3D _pt);
		public Point3D.Editable 	plusEquals(final Vector3D _vec);
		public Point3D.Editable 	plusEquals(final Dimension3D _dim);
		public Point3D.Editable 	plusEquals(Coordinate.ThreeDims _dim);
		public Point3D.Editable 	plusEquals(Dimension.ThreeDims _dim);
//		public Point3D.Editable 	plusEquals(NumberVector _vec);

		public Point3D.Editable 	minusEquals(final double _t);
		public Point3D.Editable 	minusEquals(final double _x, final double _y, final double _z);
		public Point3D.Editable 	minusEquals(final double[] _vec);
		public Point3D.Editable 	minusEquals(final Number _t);
		public Point3D.Editable 	minusEquals(final Number _x, final Number _y, final Number _z);
		public Point3D.Editable 	minusEquals(final Number[] _vec);
		public Point3D.Editable 	minusEquals(final Point3D _pt);
		public Point3D.Editable 	minusEquals(final Vector3D _vec);
		public Point3D.Editable 	minusEquals(final Dimension3D _dim);
		public Point3D.Editable 	minusEquals(Coordinate.ThreeDims _dim);
		public Point3D.Editable 	minusEquals(Dimension.ThreeDims _dim);
//		public Point3D.Editable 	minusEquals(NumberVector _vec);

		public Point3D.Editable		timesEquals(final double _t);
		public Point3D.Editable		timesEquals(final double _x, final double _y, final double _z);
		public Point3D.Editable		timesEquals(final double[] _vec);
		public Point3D.Editable		timesEquals(final Number _x);
		public Point3D.Editable		timesEquals(final Number _x, final Number _y, final Number _z);
		public Point3D.Editable		timesEquals(final Number[] _vec);
		public Point3D.Editable		timesEquals(final Vector3D _vec);
		public Point3D.Editable		timesEquals(final Dimension3D _dim);
		public Point3D.Editable		timesEquals(final Dimension.ThreeDims _dim);
//		public Point3D.Editable		timesEquals(final NumberVector _vec);

		public Point3D.Editable		dividesEquals(final double _t);
		public Point3D.Editable		dividesEquals(final double _x, final double _y, final double _z);
		public Point3D.Editable		dividesEquals(final double[] _vec);
		public Point3D.Editable		dividesEquals(final Number _x);
		public Point3D.Editable		dividesEquals(final Number _x, final Number _y, final Number _z);
		public Point3D.Editable		dividesEquals(final Number[] _vec);
		public Point3D.Editable		dividesEquals(final Vector3D _vec);
		public Point3D.Editable		dividesEquals(final Dimension3D _dim);
		public Point3D.Editable		dividesEquals(final Dimension.ThreeDims _dim);
//		public Point3D.Editable		dividesEquals(final NumberVector _vec);

		public Point3D.Editable 	clone();

	}

	public default CoordinateSystem	getCoordinateSystem() {
		return CoordinateSystem.Cartesian3D;
	}

	public double 					getX();
	public double 					getY();
	public double 					getZ();

	public Point3D 					plus(final double _t);
	public Point3D 					plus(final double _x, final double _y, final double _z);
	public Point3D 					plus(final double[] _vec);
	public Point3D 					plus(final Number _t);
	public Point3D 					plus(final Number _x, final Number _y, final Number _z);
	public Point3D 					plus(final Number[] _vec);
	public Point3D 					plus(final Point3D _pt);
	public Point3D 					plus(final Vector3D _vec);
	public Point3D 					plus(final Dimension3D _dim);
	public Point3D 					plus(Coordinate.ThreeDims _dim);
	public Point3D 					plus(Dimension.ThreeDims _dim);
//	public Point3D 					plus(NumberVector _vec);

	public Point3D 					minus(final double _t);
	public Point3D 					minus(final double _x, final double _y, final double _z);
	public Point3D 					minus(final double[] _vec);
	public Point3D 					minus(final Number _t);
	public Point3D 					minus(final Number _x, final Number _y, final Number _z);
	public Point3D 					minus(final Number[] _vec);
	public Point3D 					minus(final Point3D _pt);
	public Point3D 					minus(final Vector3D _vec);
	public Point3D 					minus(final Dimension3D _dim);
	public Point3D 					minus(Coordinate.ThreeDims _dim);
	public Point3D 					minus(Dimension.ThreeDims _dim);
//	public Point3D 					minus(NumberVector _vec);

	public Point3D 					times(final double _t);
	public Point3D 					times(final double _x, final double _y, final double _z);
	public Point3D 					times(final double[] _vec);
	public Point3D 					times(final Number _x);
	public Point3D 					times(final Number _x, final Number _y, final Number _z);
	public Point3D 					times(final Number[] _vec);
	public Point3D 					times(final Vector3D _vec);
	public Point3D 					times(final Dimension3D _dim);
	public Point3D 					times(final Dimension.ThreeDims _dim);
//	public Point3D  				times(final NumberVector _vec);

	public Point3D 					divides(final double _t);
	public Point3D 					divides(final double _x, final double _y, final double _z);
	public Point3D 					divides(final double[] _vec);
	public Point3D 					divides(final Number _x);
	public Point3D 					divides(final Number _x, final Number _y, final Number _z);
	public Point3D 					divides(final Number[] _vec);
	public Point3D 					divides(final Vector3D _vec);
	public Point3D 					divides(final Dimension3D _dim);
	public Point3D 					divides(final Dimension.ThreeDims _dim);
//	public Point3D  				divides(final NumberVector _vec);

	public boolean 					isEqual(final double _x, final double _y, final double _z);
	public boolean 					isEqual(final Point3D _pt);
	public boolean 					isDifferent(final double _x, final double _y, final double _z);
	public boolean 					isDifferent(final Point3D _pt);

	public double					norm();

	public Point3D			 		clone();
	public Point3D.Editable 		cloneEditable();

	public Point3D 					abs();
	public Point3D 					negate();
	public Point3D 					normalized();

//	public Point2D.Editable 		as2D();
//	public Vector4D 				uniform();
//	public Vector4D 				uniform(double _w);

	public String 					toString(final NumberFormat _nf);

}
