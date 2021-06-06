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
 * @file     Vector3D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.space;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Vector2D;

public interface Vector3D extends NumberVector, DoubleVector {

	public static interface Editable extends Vector3D, DoubleVector.Editable {

		public void   	set(final double _x, final double _y, final double _z);
		public void   	set(final Vector3D _pt);

		public void   	setX(final double _x);
		public void   	setY(final double _y);
		public void   	setZ(final double _z);

		public Vector3D plusEquals(final double _t);
		public Vector3D plusEquals(final double[] _vec);
		public Vector3D plusEquals(final double _x, final double _y, final double _z);
		public Vector3D plusEquals(final Point3D  _pt);
		public Vector3D plusEquals(final NumberVector   _vec);
		public Vector3D plusEquals(final Vector3D _vec);

		public Vector3D	minusEquals(final double _t);
		public Vector3D	minusEquals(final double[] _vec);
		public Vector3D	minusEquals(final double _x, final double _y, final double _z);
		public Vector3D	minusEquals(final Point3D _pt);
		public Vector3D	minusEquals(final NumberVector   _vec);
		public Vector3D	minusEquals(final Vector3D _vec);

		public Vector3D	timesEquals(final double _t);
		public Vector3D	timesEquals(final double _x, final double _y, final double _z);
		public Vector3D timesEquals(final Vector3D _d);
		
		public Vector3D	dividesEquals(final double _t);

	}

	@Override
	public default int 				getCapacity() {
		return 3;
	}

	public double 			getX();
	public double 			getY();
	public double 			getZ();

	public Vector2D 		as2D();
	public Vector4D 		uniform();
	public Vector4D			uniform(double _w);

	public Vector3D 		plus(final double _x, final double _y, final double _z);
	public Vector3D 		plus(final Vector3D _vec);
	public Vector3D 		minus(final double _x, final double _y, final double _z);
	public Vector3D 		minus(final Vector3D _vec);
	public Vector3D 		times(final double _t);
	public Vector3D 		times(final double _x, final double _y, final double _z);
	public Vector3D 		times(final Vector3D _vec);
	public Vector3D 		divides(final double _t);

	public double 			dotProduct(final double _x, final double _y, final double _z);
	public double 			dotProduct(final Vector3D _vector);
	public Vector3D			crossProduct(final double _x, final double _y, final double _z);
	public Vector3D			crossProduct(final Vector3D _vector);

	public boolean 			isEqual(final double _x, final double _y, final double _z);
	public boolean 			isEqual(final Vector3D _vec);
	public boolean 			isDifferent(final double _x, final double _y, final double _z);
	public boolean 			isDifferent(final Vector3D _vec);

	public boolean 			isOrthogonal(final double _x, final double _y, final double _z);
	public boolean 			isOrthogonal(final Vector3D _vec);
	public boolean 			isColinear(final double _x, final double _y, final double _z);
	public boolean 			isColinear(final Vector3D _vec);

	public Vector3D.Editable	clone();
	public Vector3D 			abs();
	public Vector3D 			negate();
	public Vector3D 			normalized();

}
