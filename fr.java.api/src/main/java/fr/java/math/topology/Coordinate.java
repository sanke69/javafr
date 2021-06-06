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
 * @file     Coordinate.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.topology;

import java.text.DecimalFormat;

public interface Coordinate /*extends Serializable*/ {

	public static final Coordinate UNDEF = new Coordinate() {
		@Override public CoordinateSystem getCoordinateSystem() { return null; }
		@Override public String toString(DecimalFormat _df) { return "(" + "NaC" + ")"; }
		@Override public Coordinate clone() { return this; }
	};

	public interface Editable  extends Coordinate { }

	public interface OneDim    extends Coordinate { 
		public interface Editable extends OneDim {
			public void setFirst(double _first);
		}

		public double 	getFirst();
	}
	public interface TwoDims   extends Coordinate.OneDim { 
		public interface Editable extends Coordinate.TwoDims, Coordinate.OneDim.Editable {
			public void setSecond(double _second);
		}

		public double 	getSecond();

	}
	public interface ThreeDims extends Coordinate.TwoDims { 
		public interface Editable extends Coordinate.ThreeDims, Coordinate.TwoDims.Editable {
			public void setThird(double _third);
		}

		public double 	getThird();
	}

	public interface MultiDims extends Coordinate { 
		public interface Editable extends Coordinate.MultiDims, Coordinate.Editable {
			public void set(int _i, double _third);
		}

		public int  	getDims();
		public double 	get(int _i);
	}

	public interface Cartesian extends Coordinate {	}
	public interface Angular   extends Coordinate { }
	public interface Polar     extends Coordinate.TwoDims { }
	public interface Cylindric extends Coordinate { }
	public interface Spheric   extends Coordinate { }

	public interface Cartesian1D 	extends Coordinate.Cartesian,   Coordinate.OneDim {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian1D; }

		public interface Editable extends Coordinate.Cartesian1D {
			public default void setFirst(double _first) { setX(_first); }
			public void 		setX(double _x);
		}

		public default double 	getFirst() { return getX(); }
		public double 			getX();
	}
	public interface Cartesian2D 	extends Coordinate.Cartesian1D, Coordinate.TwoDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian2D; }

		public interface Editable extends Coordinate.Cartesian2D, Coordinate.Cartesian1D.Editable {
			public default void setSecond(double _second) { setY(_second); }
			public void 		setY(double _y);
		}

		public default double 	getSecond() { return getY(); }
		public double 			getY();
	}
	public interface Cartesian3D 	extends Coordinate.Cartesian2D, Coordinate.ThreeDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cartesian3D; }

		public interface Editable extends Coordinate.Cartesian3D, Coordinate.Cartesian2D.Editable {
			public default void setThird(double _third) { setZ(_third); }
			public void 		setZ(double _z);
		}

		public default double 	getThird() { return getZ(); }
		public double 			getZ();
	}
	public interface Angular1D 		extends Coordinate.Angular,     Coordinate.OneDim {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Angular1D; }

		public interface Editable extends Coordinate.Angular1D {
			public default void setFirst(double _first) { setRho(_first); }
			public void 		setRho(double _rho);
		}

		public default double 	getFirst() { return getRho(); }
		public double 			getRho();
	}
	public interface Angular2D 		extends Coordinate.Angular1D,   Coordinate.TwoDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Angular2D; }

		public interface Editable extends Coordinate.Angular2D, Coordinate.Angular1D.Editable {
			public default void setSecond(double _second) { setTheta(_second); }
			public void 		setTheta(double _theta);
		}

		public default double 	getSecond() { return getTheta(); }
		public double 			getTheta();
	}
	public interface Polar2D 		extends Coordinate.Polar,       Coordinate.TwoDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Polar2D; }

		public interface Editable extends Coordinate.Polar2D, Coordinate.Polar.Editable {			
			public default void setFirst(double _first)   { setRadius(_first); }
			public default void setSecond(double _second) { setAngle(_second); }
			public void 		setRadius(double _rad);
			public void 		setAngle(double _theta);
		}

		public default double 	getFirst()  { return getRadius(); }
		public default double 	getSecond() { return getAngle(); }
		public double 			getRadius();
		public double 			getAngle();
	}
	public interface Cylindric2D 	extends Coordinate.Cylindric,   Coordinate.Polar2D {

		public interface Editable extends Coordinate.Cylindric3D, Coordinate.Polar2D.Editable {	}

	}
	public interface Cylindric3D 	extends Coordinate.Cylindric2D, Coordinate.ThreeDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cylindric3D; }

		public interface Editable extends Coordinate.Cylindric3D, Coordinate.Cylindric2D.Editable {
			public default void getThird(double _third)   { setHeight(_third); }
			public void 		setHeight(double _theta);
		}

		public default double 	getThird() { return getHeight(); }
		public double 			getHeight();
	}
	public interface Spheric2D 		extends Coordinate.Angular2D,   Coordinate.Spheric { 

		public interface Editable extends Coordinate.Spheric2D, Coordinate.Angular2D.Editable { }

	}
	public interface Spheric3D 		extends Coordinate.Spheric2D,   Coordinate.ThreeDims {
		@Override public default CoordinateSystem getCoordinateSystem() { return CoordinateSystem.Cylindric3D; }

		public interface Editable extends Coordinate.Spheric3D, Coordinate.Angular2D.Editable {
			public default void getThird(double _third)   { setRadius(_third); }
			public void 		setRadius(double _radius);
		}

		public default double 	getThird() { return getRadius(); }
		public double 			getRadius();
	}

	public CoordinateSystem	getCoordinateSystem();

	public default boolean 	isLinear() 		{ return this instanceof Coordinate.OneDim; }
	public default boolean 	isPlanar() 		{ return this instanceof Coordinate.TwoDims; }
	public default boolean 	isSpatial() 	{ return this instanceof Coordinate.ThreeDims; }

	public default boolean 	isCartesian() 	{ return this instanceof Coordinate.Cartesian; }
	public default boolean 	isPolar() 		{ return this instanceof Coordinate.Polar; }
	public default boolean 	isCylindric() 	{ return this instanceof Coordinate.Cylindric; }
	public default boolean 	isSpheric() 	{ return this instanceof Coordinate.Spheric; }

//	public default String toString(DecimalFormat _df) { return toString(); }
	public String toString(DecimalFormat _df);

}
