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
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;

public interface Vector3D extends DoubleVector {

	public static interface Editable extends Vector3D, DoubleVector.Editable {

		public void   				set(final double _t);
		public void   				set(final double _x, final double _y, final double _z);
		public void 				set(final double[] _values);

		public void 				set(final Number _value);
		public void 				set(final Number _x, final Number _y, final Number _z);
		public void 				set(final Number[] _values);

		public void   				set(final Point3D _pt);
		public void   				set(final Vector3D _vec);
//		public void   				set(final NumberVector _vec);

		public void   				setX(final double _x);
		public void   				setY(final double _y);
		public void   				setZ(final double _z);

		public void					setMagnitude(double _mag);

		public Vector3D.Editable 	plusEquals(final double _t);
		public Vector3D.Editable 	plusEquals(final double _x, final double _y, final double _z);
		public Vector3D.Editable 	plusEquals(final double[] _vec);
		public Vector3D.Editable 	plusEquals(final Number _t);
		public Vector3D.Editable 	plusEquals(final Number _x, final Number _y, final Number _z);
		public Vector3D.Editable 	plusEquals(final Number[] _vec);
		public Vector3D.Editable 	plusEquals(final Point3D _pt);
		public Vector3D.Editable 	plusEquals(final Vector3D _vec);
		public Vector3D.Editable 	plusEquals(final Dimension3D _dim);
		public Vector3D.Editable 	plusEquals(Coordinate.ThreeDims _dim);
		public Vector3D.Editable 	plusEquals(Dimension.ThreeDims _dim);
//		public Vector3D.Editable	plusEquals(final NumberVector _vec);
//		public Vector3D.Editable	plusEquals(final DoubleVector _vec);

		public Vector3D.Editable 	minusEquals(final double _t);
		public Vector3D.Editable 	minusEquals(final double _x, final double _y, final double _z);
		public Vector3D.Editable 	minusEquals(final double[] _vec);
		public Vector3D.Editable 	minusEquals(final Number _t);
		public Vector3D.Editable 	minusEquals(final Number _x, final Number _y, final Number _z);
		public Vector3D.Editable 	minusEquals(final Number[] _vec);
		public Vector3D.Editable 	minusEquals(final Point3D _pt);
		public Vector3D.Editable 	minusEquals(final Vector3D _vec);
		public Vector3D.Editable 	minusEquals(final Dimension3D _dim);
		public Vector3D.Editable 	minusEquals(Coordinate.ThreeDims _dim);
		public Vector3D.Editable 	minusEquals(Dimension.ThreeDims _dim);
//		public Vector3D.Editable	minusEquals(final NumberVector _vec);
//		public Vector3D.Editable	minusEquals(final DoubleVector _vec);

		public Vector3D.Editable	timesEquals(final double _t);
		public Vector3D.Editable	timesEquals(final double _x, final double _y, final double _z);
		public Vector3D.Editable	timesEquals(final double[] _vec);
		public Vector3D.Editable	timesEquals(final Number _x);
		public Vector3D.Editable	timesEquals(final Number _x, final Number _y, final Number _z);
		public Vector3D.Editable	timesEquals(final Number[] _vec);
		public Vector3D.Editable	timesEquals(final Vector3D _vec);
		public Vector3D.Editable	timesEquals(final Dimension3D _dim);
		public Vector3D.Editable	timesEquals(final Dimension.ThreeDims _dim);
//		public Vector3D.Editable	timesEquals(final NumberVector _vec);
//		public Vector3D.Editable	timesEquals(final DoubleVector _vec);

		public Vector3D.Editable	dividesEquals(final double _t);
		public Vector3D.Editable	dividesEquals(final double _x, final double _y, final double _z);
		public Vector3D.Editable	dividesEquals(final double[] _vec);
		public Vector3D.Editable	dividesEquals(final Number _x);
		public Vector3D.Editable	dividesEquals(final Number _x, final Number _y, final Number _z);
		public Vector3D.Editable	dividesEquals(final Number[] _vec);
		public Vector3D.Editable	dividesEquals(final Vector3D _vec);
		public Vector3D.Editable	dividesEquals(final Dimension3D _dim);
		public Vector3D.Editable	dividesEquals(final Dimension.ThreeDims _dim);
//		public Vector3D.Editable	dividesEquals(final NumberVector _vec);
//		public Vector3D.Editable	dividesEquals(final DoubleVector _vec);

		public Vector3D.Editable 	clone();

	}

	@Override
	public default Primitive 		getPrimitive() {
		return Primitive.DOUBLE;
	}

	@Override
	public default int 				getCapacity() {
		return 3;
	}
	@Override
	public default int 				size() {
		return 3;
	}

	public double 					getX();
	public double 					getY();
	public double 					getZ();

	public double					getMagnitude();

	public Vector3D 				plus(final double _t);
	public Vector3D 				plus(final double _x, final double _y, final double _z);
	public Vector3D 				plus(final double[] _vec);
	public Vector3D 				plus(final Number _t);
	public Vector3D 				plus(final Number _x, final Number _y, final Number _z);
	public Vector3D 				plus(final Number[] _vec);
	public Vector3D 				plus(final Point3D _pt);
	public Vector3D 				plus(final Vector3D _vec);
	public Vector3D 				plus(final Dimension3D _dim);
	public Vector3D 				plus(final Coordinate.ThreeDims _c);
	public Vector3D 				plus(final Dimension.ThreeDims _dim);
//	public Vector3D					plus(final NumberVector _vec);
//	public Vector3D					plus(final DoubleVector _vec);

	public Vector3D 				minus(final double _t);
	public Vector3D 				minus(final double _x, final double _y, final double _z);
	public Vector3D 				minus(final double[] _vec);
	public Vector3D 				minus(final Number _t);
	public Vector3D 				minus(final Number _x, final Number _y, final Number _z);
	public Vector3D 				minus(final Number[] _vec);
	public Vector3D 				minus(final Point3D _pt);
	public Vector3D 				minus(final Vector3D _vec);
	public Vector3D 				minus(final Dimension3D _dim);
	public Vector3D 				minus(final Coordinate.ThreeDims _c);
	public Vector3D 				minus(final Dimension.ThreeDims _dim);
//	public Vector3D					minus(final NumberVector _vec);
//	public Vector3D					minus(final DoubleVector _vec);

	public Vector3D					times(final double _t);
	public Vector3D					times(final double _x, final double _y, final double _z);
	public Vector3D					times(final double[] _vec);
	public Vector3D					times(final Number _x);
	public Vector3D					times(final Number _x, final Number _y, final Number _z);
	public Vector3D					times(final Number[] _vec);
	public Vector3D					times(final Vector3D _vec);
	public Vector3D					times(final Dimension3D _dim);
	public Vector3D					times(final Dimension.ThreeDims _dim);
//	public Vector3D					times(final NumberVector _vec);
//	public Vector3D					times(final DoubleVector _vec);

	public Vector3D					divides(final double _t);
	public Vector3D					divides(final double _x, final double _y, final double _z);
	public Vector3D					divides(final double[] _vec);
	public Vector3D					divides(final Number _x);
	public Vector3D					divides(final Number _x, final Number _y, final Number _z);
	public Vector3D					divides(final Number[] _vec);
	public Vector3D					divides(final Vector3D _vec);
	public Vector3D					divides(final Dimension3D _dim);
	public Vector3D					divides(final Dimension.ThreeDims _dim);
//	public Vector3D					divides(final NumberVector _vec);
//	public Vector3D					divides(final DoubleVector _vec);

	public Vector3D 				normalize(final double _norm);

	public boolean 					isEqual(final double _x, final double _y, final double _z);
	public boolean 					isEqual(final double[] _v);
	public boolean 					isEqual(final Vector3D _vec);

	public boolean 					isDifferent(final double _x, final double _y, final double _z);
	public boolean 					isDifferent(final double[] _v);
	public boolean 					isDifferent(final Vector3D _vec);

	public boolean 					isOrthogonal(final Vector3D _vec);
	public boolean 					isColinear(final Vector3D _vec);

	public double 					dotProduct(final double _x, final double _y, final double _z);
	public double 					dotProduct(final Vector3D _vector);
	public Vector3D					crossProduct(final double _x, final double _y, final double _z);
	public Vector3D					crossProduct(final Vector3D _vector);

	public Vector3D					clone();
	public Vector3D.Editable 		cloneEditable();

	public Vector3D 				abs();
	public Vector3D 				negate();
	public Vector3D 				normalized();

	public Vector2D 				downgrade();
	public Vector4D 				uniform();
	public Vector4D 				uniform(final double _w);

//	public Vector2D 				as2D();
//	public Vector4D 				uniform();
//	public Vector4D					uniform(double _w);

}
