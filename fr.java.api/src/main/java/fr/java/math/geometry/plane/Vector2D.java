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
 * @file     Vector2D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.plane;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.space.Vector3D;

public interface Vector2D extends NumberVector, DoubleVector {

	public static interface Editable extends Vector2D, DoubleVector.Editable, NumberVector.Editable {

		public void   	set(final double _t);
		public void   	set(final double _x, final double _y);
		public void 	set(final double[] _values);

		public void 	set(final Number _value);
		public void 	set(final Number _x, final Number _y);
		public void 	set(final Number[] _values);

		public void   	set(final NumberVector _vec);
		public void   	set(final Point2D _pt);
		public void   	set(final Vector2D _vec);

		public void   	setX(final double _x);
		public void   	setY(final double _y);

		public void		setMagnitude(double _mag);

//		public Vector2D normalize();

		public Vector2D plusEquals(final double _t);
		public Vector2D plusEquals(final double _x, final double _y);
		public Vector2D plusEquals(final double[] _vec);
		public Vector2D plusEquals(final NumberVector _vec);
		public Vector2D plusEquals(final Vector2D _vec);

		public Vector2D minusEquals(final double _t);
		public Vector2D minusEquals(final double _x, final double _y);
		public Vector2D minusEquals(final double[] _vec);
		public Vector2D minusEquals(final NumberVector _vec);
		public Vector2D minusEquals(final Vector2D _pt);

		public Vector2D timesEquals(final double _t);
		public Vector2D timesEquals(final double _x, final double _y);
		public Vector2D timesEquals(final Vector2D _vec);

	}

	@Override
	public default int 				getCapacity() {
		return 2;
	}

	public double 					getX();
	public double 					getY();

	public Vector3D 				uniform();
	public Vector3D 				uniform(double _w);

	public Vector2D 				plus(final double _t);
	public Vector2D 				plus(final double _x, final double _y);
	public Vector2D 				plus(final double[] _vec);
	public Vector2D 				plus(final Number _value);
	public Vector2D 				plus(final Number _x, final Number _y);
	public Vector2D 				plus(final Number[] _values);
	public Vector2D 				plus(final NumberVector _vec);
	public Vector2D 				plus(final Vector2D _vec);

	public Vector2D 				minus(final double _t);
	public Vector2D 				minus(final double _x, final double _y);
	public Vector2D 				minus(final double[] _vec);
	public Vector2D 				minus(final Number _value);
	public Vector2D 				minus(final Number _x, final Number _y);
	public Vector2D 				minus(final Number[] _values);
	public Vector2D 				minus(final NumberVector _vec);
	public Vector2D 				minus(final Vector2D _vec);

	public Vector2D 				times(final double _t);
	public Vector2D 				times(final double _x, final double _y);
	public Vector2D 				times(final double[] _vec);
	public Vector2D 				times(final Number _value);
	public Vector2D 				times(final Number _x, final Number _y);
	public Vector2D 				times(final Number[] _values);
	public Vector2D 				times(final NumberVector _vec);
	public Vector2D 				times(final Vector2D _vec);

	public Vector2D 				normalize(final double _norm);

	public double 					dotProduct(final double _x, final double _y);
	public double 					dotProduct(final Vector2D _vector);
	public Vector2D					crossProduct(final double _x, final double _y);
	public Vector2D					crossProduct(final Vector2D _vector);

	public boolean 					isEqual(final double _x, final double _y);
	public boolean 					isEqual(final Vector2D _vec);
	public boolean 					isDifferent(final double _x, final double _y);
	public boolean 					isDifferent(final Vector2D _vec);

	public boolean 					isOrthogonal(final Vector2D _vec);
	public boolean 					isColinear(final Vector2D _vec);

	public Vector2D.Editable		clone();
	public Vector2D 				abs();
	public Vector2D 				negate();
	public Vector2D 				normalized();

}
