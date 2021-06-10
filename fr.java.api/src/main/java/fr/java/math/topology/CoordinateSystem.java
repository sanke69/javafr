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
package fr.java.math.topology;

public interface CoordinateSystem {

	public static final CoordinateSystem Linear = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.LINE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	public static final CoordinateSystem Planar = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.PLANE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	public static final CoordinateSystem Spatial = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.SPACE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	
	// 1D Referential
	public static final CoordinateSystem Cartesian1D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.LINE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	public static final CoordinateSystem Angular1D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.LINE; }
		@Override public Type 		getType() 		{ return Type.POLAR; }
	};
	// 2D Referential
	public static final CoordinateSystem Cartesian2D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.PLANE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	public static final CoordinateSystem Polar2D     = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.SPACE; }
		@Override public Type 		getType() 		{ return Type.POLAR; }
	};

	public static final CoordinateSystem Angular2D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.PLANE; }
		@Override public Type 		getType() 		{ return Type.POLAR; }
	};// 3D Referential
	public static final CoordinateSystem Cartesian3D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.SPACE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	public static final CoordinateSystem Spheric3D   = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.SPACE; }
		@Override public Type 		getType() 		{ return Type.POLAR; }
	};
	public static final CoordinateSystem Cylindric3D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.SPACE; }
		@Override public Type 		getType() 		{ return Type.POLAR; }
	};
	// 3D Referential
	public static final CoordinateSystem Cartesian4D = new CoordinateSystem() {
		@Override public Dimension 	getDimension() 	{ return Dimension.TIMESPACE; }
		@Override public Type 		getType() 		{ return Type.CARTESIAN; }
	};
	
	public enum Dimension {
		POINT,		// 0 D
		LINE,		// 1 D : time
		PLANE,		// 2 D : surface
		SPACE,		// 3 D : volume
		TIMESPACE,	// 4 D : 3 D + time
		;
	}
	public enum Type {
		CARTESIAN	(),
		CYLINDRIC	(),
		POLAR		(),
		SPHERIC		();
	}

	public Dimension 		getDimension();
	public Type      		getType();

	public default boolean 	isCartesian() { return getType() == Type.CARTESIAN; }
	public default boolean 	isCylindric() { return getType() == Type.CYLINDRIC; }
	public default boolean 	isPolar()     { return getType() == Type.POLAR; }
	public default boolean 	isSpheric()   { return getType() == Type.SPHERIC; }

	public static  boolean 	areCompatible(CoordinateSystem _a, CoordinateSystem _b) { return _a.getType() == _b.getType(); }
	public static  boolean 	areSameType(CoordinateSystem _a, CoordinateSystem _b) { return _a.getType() == _b.getType(); }

}
