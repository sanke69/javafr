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
 * @file     Vector4D.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry.space;

import fr.java.lang.enums.Primitive;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Vector2D;

public interface Vector4D extends DoubleVector {

	public static interface Editable extends Vector4D, DoubleVector.Editable {

		public void   				set(final double _t);
		public void   				set(final double _x, final double _y, final double _z, final double _w);
		public void 				set(final double[] _values);

		public void 				set(final Number _value);
		public void 				set(final Number _x, final Number _y, final Number _z, final Number _w);
		public void 				set(final Number[] _values);

		public void   				set(final Vector4D _vec);
		public void   				set(final NumberVector _vec);

		public void   				setX(final double _x);
		public void   				setY(final double _y);
		public void   				setZ(final double _z);
		public void   				setW(final double _w);

		public void					setMagnitude(double _mag);

		public Vector4D.Editable 	plusEquals(final double _t);
		public Vector4D.Editable 	plusEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D.Editable 	plusEquals(final double[] _vec);
		public Vector4D.Editable 	plusEquals(final Number _t);
		public Vector4D.Editable 	plusEquals(final Number _x, final Number _y, final Number _z, final Number _w);
		public Vector4D.Editable 	plusEquals(final Number[] _vec);
		public Vector4D.Editable 	plusEquals(final Vector4D _vec);
//		public Vector4D.Editable	plusEquals(final NumberVector _vec);
//		public Vector4D.Editable	plusEquals(final DoubleVector _vec);

		public Vector4D.Editable 	minusEquals(final double _t);
		public Vector4D.Editable 	minusEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D.Editable 	minusEquals(final double[] _vec);
		public Vector4D.Editable 	minusEquals(final Number _t);
		public Vector4D.Editable 	minusEquals(final Number _x, final Number _y, final Number _z, final Number _w);
		public Vector4D.Editable 	minusEquals(final Number[] _vec);
		public Vector4D.Editable 	minusEquals(final Vector4D _vec);
//		public Vector4D.Editable	minusEquals(final NumberVector _vec);
//		public Vector4D.Editable	minusEquals(final DoubleVector _vec);

		public Vector4D.Editable 	timesEquals(final double _t);
		public Vector4D.Editable 	timesEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D.Editable 	timesEquals(final double[] _vec);
		public Vector4D.Editable 	timesEquals(final Number _t);
		public Vector4D.Editable 	timesEquals(final Number _x, final Number _y, final Number _z, final Number _w);
		public Vector4D.Editable 	timesEquals(final Number[] _vec);
		public Vector4D.Editable 	timesEquals(final Vector4D _vec);
//		public Vector4D.Editable	timesEquals(final NumberVector _vec);
//		public Vector4D.Editable	timesEquals(final DoubleVector _vec);

		public Vector4D.Editable 	dividesEquals(final double _t);
		public Vector4D.Editable 	dividesEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D.Editable 	dividesEquals(final double[] _vec);
		public Vector4D.Editable 	dividesEquals(final Number _t);
		public Vector4D.Editable 	dividesEquals(final Number _x, final Number _y, final Number _z, final Number _w);
		public Vector4D.Editable 	dividesEquals(final Number[] _vec);
		public Vector4D.Editable 	dividesEquals(final Vector4D _vec);
//		public Vector4D.Editable	dividesEquals(final NumberVector _vec);
//		public Vector4D.Editable	dividesEquals(final DoubleVector _vec);

		public Vector4D.Editable 	clone();

	}

	@Override
	public default Primitive 		getPrimitive() {
		return Primitive.DOUBLE;
	}

	@Override
	public default int 				getCapacity() {
		return 4;
	}
	@Override
	public default int 				size() {
		return 4;
	}

	public double 					getX();
	public double 					getY();
	public double 					getZ();
	public double 					getW();

	public double 					getMagnitude();

	public Vector4D 				plus(final double _t);
	public Vector4D 				plus(final double _t, final double _u, final double _v, final double _w);
	public Vector4D 				plus(final double[] _v);
	public Vector4D 				plus(final Number _t);
	public Vector4D 				plus(final Number _t, final Number _u, final Number _v, final Number _w);
	public Vector4D 				plus(final Number[] _v);
	public Vector4D 				plus(final Vector4D _d);
//	public Vector3D					plus(final NumberVector _vec);
//	public Vector3D					plus(final DoubleVector _vec);

	public Vector4D 				minus(final double _t);
	public Vector4D 				minus(final double _t, final double _u, final double _v, final double _w);
	public Vector4D 				minus(final double[] _v);
	public Vector4D 				minus(final Number _t);
	public Vector4D 				minus(final Number _t, final Number _u, final Number _v, final Number _w);
	public Vector4D 				minus(final Number[] _v);
	public Vector4D 				minus(final Vector4D _d);
//	public Vector3D					minus(final NumberVector _vec);
//	public Vector3D					minus(final DoubleVector _vec);

	public Vector4D 				times(final double _t);
	public Vector4D 				times(final double[] _v);
	public Vector4D 				times(final double _t, final double _u, final double _v, final double _w);
	public Vector4D 				times(final Number _t);
	public Vector4D 				times(final Number _t, final Number _u, final Number _v, final Number _w);
	public Vector4D 				times(final Number[] _v);
	public Vector4D 				times(final Vector4D _d);
//	public Vector3D					times(final NumberVector _vec);
//	public Vector3D					times(final DoubleVector _vec);

	public Vector4D 				divides(final double _t);
	public Vector4D 				divides(final double[] _v);
	public Vector4D 				divides(final double _t, final double _u, final double _v, final double _w);
	public Vector4D 				divides(final Number _t);
	public Vector4D 				divides(final Number _t, final Number _u, final Number _v, final Number _w);
	public Vector4D 				divides(final Number[] _v);
	public Vector4D 				divides(final Vector4D _d);
//	public Vector3D					divides(final NumberVector _vec);
//	public Vector3D					divides(final DoubleVector _vec);

	public boolean 					isEqual(final double _u, final double _v, final double _w, final double _z);
	public boolean 					isEqual(final double[] _v);
	public boolean 					isEqual(final Vector4D _other);

	public boolean 					isDifferent(final double _u, final double _v, final double _w, final double _z);
	public boolean 					isDifferent(final double[] _v);
	public boolean 					isDifferent(final Vector4D _other);

	public boolean 					isColinear(final NumberVector _other);
	public boolean 					isColinear(final DoubleVector _vector);
	public boolean 					isColinear(final Vector4D _other);

	public boolean 					isColinearToSegment(final Vector4D _A, final Vector4D _B);
	public boolean 					isColinearToLine(final Vector4D _P, final Vector4D _N);

	public boolean 					isOrthogonal(final NumberVector _vec);
	public boolean 					isOrthogonal(final DoubleVector _vector);
	public boolean 					isOrthogonal(final Vector4D _other);

	public boolean 					isOrthogonalToSegment(final Vector4D _A, final Vector4D _B);
	public boolean 					isOrthogonalToLine(final Vector4D _P, final Vector4D _N);

	public Vector4D 				clone();
	public Vector4D.Editable 		cloneEditable();

	public Vector4D 				abs();
	public Vector4D 				negate();
	public Vector4D 				normalized();

	public Vector3D 				downgrade();

	public Number 					dotProduct(final NumberVector _v);
	public NumberVector 			crossProduct(final NumberVector _v);

	public double 					euclydianDistance(final Vector4D _v);
	public double 					euclydianDistance2(final Vector4D _v);

}
