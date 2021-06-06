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
 * @file     Point3D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.space;

import java.text.NumberFormat;

import fr.java.math.algebra.NumberVector;
import fr.java.math.geometry.Point;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public interface Point3D extends Point, Coordinate.Cartesian3D, Shape3D {

	public static interface Editable extends Point3D {

		public void   	set(final double _x, final double _y, final double _z);
		public void   	set(final Point3D  _pt);
		public void 	set(final Vector3D _vec);

		public void   	setX(final double _x);
		public void   	setY(final double _y);
		public void   	setZ(final double _z);

		public Point3D 	plusEquals(final double _t);
		public Point3D 	plusEquals(final double[] _vec);
		public Point3D 	plusEquals(final double _x, final double _y, final double _z);
		public Point3D 	plusEquals(final Point3D  _pt);
		public Point3D 	plusEquals(final Vector3D _vec);
		public Point3D 	plusEquals(final NumberVector   _vec);

		public Point3D 	minusEquals(final double _t);
		public Point3D 	minusEquals(final double[] _vec);
		public Point3D 	minusEquals(final double _x, final double _y, final double _z);
		public Point3D 	minusEquals(final Point3D  _pt);
		public Point3D 	minusEquals(final Vector3D _vec);
		public Point3D 	minusEquals(final NumberVector   _vec);

		public Point3D 	timesEquals(final double _t);
		public Point3D 	timesEquals(final double _x, final double _y, final double _z);

		public Point3D 	dividesEquals(final double _t);

	}

	public default CoordinateSystem	getCoordinateSystem() {
		return CoordinateSystem.Cartesian3D;
	}

	public double 					getX();
	public double 					getY();
	public double 					getZ();

	public Point2D.Editable 		as2D();
	public Vector4D 				uniform();
	public Vector4D 				uniform(double _w);

	public Point3D 					plus(final double _t);
	public Point3D 					plus(final double[] _vec);
	public Point3D 					plus(final double _x, final double _y, final double _z);
	public Point3D 					plus(final Point3D _pt);
	public Point3D 					plus(final Vector3D _vec);

	public Point3D 					minus(final double _t);
	public Point3D 					minus(final double[] _vec);
	public Point3D 					minus(final double _x, final double _y, final double _z);
	public Point3D 					minus(final Point3D _pt);
	public Point3D 					minus(final Vector3D _vec);

	public Point3D 					times(final double _t);
	public Point3D 					times(final double _x, final double _y, final double _z);
	public Point3D 					times(final Vector3D _vec);

	public Point3D 					divides(final double _t);

	public boolean 					isEqual(final double _x, final double _y, final double _z);
	public boolean 					isEqual(final Point3D _pt);
	public boolean 					isDifferent(final double _x, final double _y, final double _z);
	public boolean 					isDifferent(final Point3D _pt);

	public double					norm();

	public Point3D.Editable 		clone();
	public Point3D 					abs();
	public Point3D 					negate();
	public Point3D 					normalized();

	public String 					toString(final NumberFormat _nf);

}
