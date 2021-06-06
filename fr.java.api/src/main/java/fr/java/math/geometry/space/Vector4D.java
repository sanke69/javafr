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

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.math.geometry.plane.Vector2D;

public interface Vector4D extends NumberVector, DoubleVector {

	public static interface Editable extends Vector4D, DoubleVector.Editable {

		public void   	set(final double _t);
		public void   	set(final double _x, final double _y, final double _z, final double _w);
		public void 	set(final double[] _values);

		public void 	set(final Number _value);
		public void 	set(final Number _x, final Number _y, final Number _z, final Number _w);
		public void 	set(final Number[] _values);

		public void   	set(final NumberVector _vec);
		public void   	set(final Vector4D _vec);

		public void   	setX(final double _x);
		public void   	setY(final double _y);
		public void   	setZ(final double _z);
		public void   	setW(final double _w);

		public Vector4D plusEquals(final double _t);
		public Vector4D plusEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D plusEquals(final double[] _vec);
		public Vector4D plusEquals(final NumberVector _vec);
		public Vector4D plusEquals(final Vector4D _vec);

		public Vector4D minusEquals(final double _t);
		public Vector4D minusEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D minusEquals(final double[] _vec);
		public Vector4D minusEquals(final NumberVector _vec);
		public Vector4D minusEquals(final Vector4D _pt);

		public Vector4D timesEquals(final double _t);
		public Vector4D timesEquals(final double _x, final double _y, final double _z, final double _w);
		public Vector4D timesEquals(final Vector4D _vec);

	}

	@Override
	public default int 		getCapacity() {
		return 4;
	}

	/**
	 * VECTOR METHODS
	**/
	public Vector3D 		as3D();
	public Vector2D 		as2D();

	public double 			getX();
	public double 			getY();
	public double 			getZ();
	public double 			getW();

	public Vector4D 		plus(double _t);
	public Vector4D 		plus(double _t, double _u, double _v, double _w);
	public Vector4D 		plus(double[] _v);
	public Vector4D 		plus(Number _t);
	public Vector4D 		plus(Number _t, Number _u, Number _v, Number _w);
	public Vector4D 		plus(Number[] _v);
	public Vector4D 		plus(NumberVector _v);
	public Vector4D 		plus(Vector4D _d);

	public Vector4D 		minus(double _t);
	public Vector4D 		minus(double _t, double _u, double _v, double _w);
	public Vector4D 		minus(double[] _v);
	public Vector4D 		minus(Number _t);
	public Vector4D 		minus(Number[] _v);
	public Vector4D 		minus(Number _t, Number _u, Number _v, Number _w);
	public Vector4D 		minus(NumberVector _v);
	public Vector4D 		minus(Vector4D _d);
	public Vector4D 		minus(DoubleVector _v);

	public Vector4D 		times(double _t);
	public Vector4D 		times(double[] _v);
	public Vector4D 		times(double _t, double _u, double _v, double _w);
	public Vector4D 		times(Number _t);
	public Vector4D 		times(Number[] _v);
	public Vector4D 		times(Number _t, Number _u, Number _v, Number _w);
	public Vector4D 		times(NumberVector _v);
	public Vector4D 		times(Vector4D _d);
	public Vector4D 		times(DoubleVector _v);


	public Vector4D 		divides(double _t);
	public Vector4D 		divides(double[] _v);
	public Vector4D 		divides(double _t, double _u, double _v, double _w);
	public Vector4D 		divides(Number _t);
	public Vector4D 		divides(Number[] _v);
	public Vector4D 		divides(NumberVector _v);
	public Vector4D 		divides(Vector4D _d);
	public Vector4D 		divides(DoubleVector _v);

	public boolean 		isValid();


	public boolean 		isEqual(double _t);
	public boolean 		isEqual(double[] _v);
	public boolean 		isEqual(double _u, double _v, double _w, double _z);
	public boolean 		isEqual(Number _t);
	public boolean 		isEqual(Number[] _v);
	public boolean 		isEqual(NumberVector _v);
	public boolean 		isEqual(Vector4D _other);
	public boolean 		isEqual(DoubleVector _v);

	public boolean 		isDifferent(double _t);
	public boolean 		isDifferent(double[] _v);
	public boolean 		isDifferent(double _u, double _v, double _w, double _z);
	public boolean 		isDifferent(Number _t);
	public boolean 		isDifferent(Number[] _v);
	public boolean 		isDifferent(NumberVector _v);
	public boolean 		isDifferent(Vector4D _other);
	public boolean 		isDifferent(DoubleVector _v);

	public boolean 		isColinear(NumberVector _other);
	public boolean 		isColinear(DoubleVector _vector);
	public boolean 		isColinear(Vector4D _other);

	public boolean 		isColinearToSegment(Vector4D _A, Vector4D _B);
	public boolean 		isColinearToLine(Vector4D _P, Vector4D _N);

	public boolean 		isOrthogonal(NumberVector _vec);
	public boolean 		isOrthogonal(DoubleVector _vector);
	public boolean 		isOrthogonal(Vector4D _other);

	public boolean 		isOrthogonalToSegment(Vector4D _A, Vector4D _B);
	public boolean 		isOrthogonalToLine(Vector4D _P, Vector4D _N);

	public Vector4D.Editable 	clone();
	public Vector4D 	abs();
	public Vector4D 	negate();
	public Vector4D 	normalized();

	public Number 		dotProduct(NumberVector _v);
	public NumberVector 		crossProduct(NumberVector _v);


	public double euclydianDistance(Vector4D _v);
	public double euclydianDistance2(Vector4D _v);

}
