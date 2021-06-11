package fr.java.maths.geometry.types;

import java.text.DecimalFormat;

import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.patterns.tiled.TileCoordinate;

public final class Coordinates {

//	public static record Coordinate1D(double first) implements Coordinate.TwoDims {
	public static class Coordinate1D implements Coordinate.OneDim {
		double first;

		public Coordinate1D(double _first) { first = _first; }

		@Override public CoordinateSystem 	getCoordinateSystem() 			{ return CoordinateSystem.Cartesian1D; }

		@Override public double 			getFirst() 						{ return first; }

		@Override public String 			toString() 						{ return "(" + first + ")"; }
		@Override public String 			toString(DecimalFormat _df) 	{ return "(" + _df.format( first ) + ")"; }

		@Override
	    public boolean equals(Object _o) {
			if(_o instanceof Coordinate.OneDim)
				return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.OneDim) _o).getFirst());
			return false;
	    }

	}
//	public static record Coordinate2D(double first, double second) implements Coordinate.TwoDims {
	public static class Coordinate2D implements Coordinate.TwoDims {
		double first, second;

		public Coordinate2D(double _first, double _second) { first = _first; second = _second; }

		@Override public CoordinateSystem 	getCoordinateSystem() 			{ return CoordinateSystem.Cartesian2D; }

		@Override public double 			getFirst() 						{ return first; }
		@Override public double 			getSecond() 					{ return second; }

		@Override public String 			toString() 						{ return "(" + first + ", " + second + ")"; }
		@Override public String 			toString(DecimalFormat _df) 	{ return "(" + _df.format( first ) + ", " + _df.format( second ) + ")"; }

		@Override
	    public boolean equals(Object _o) {
			if(_o instanceof Coordinate.TwoDims)
				return Double.doubleToLongBits(getFirst())  == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getFirst()) 
					&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getSecond());
			return false;
	    }

	}
//	public static record Coordinate3D(double first, double second, double third) implements Coordinate.ThreeDims {
	public static class Coordinate3D implements Coordinate.ThreeDims {
		double first, second, third;

		public Coordinate3D(double _first, double _second, double _third) { first = _first; second = _second;; third = _third; }

		@Override public CoordinateSystem 	getCoordinateSystem() 			{ return CoordinateSystem.Cartesian2D; }
		@Override public double 			getFirst() 						{ return first; }
		@Override public double 			getSecond() 					{ return second; }
		@Override public double 			getThird() 						{ return third; }
		@Override public String 			toString() 						{ return "(" + first + ", " + second + ", " + third + ")"; }
		@Override public String 			toString(DecimalFormat _df) 	{ return "(" + _df.format( first ) + ", " + _df.format( second )  + ", " + _df.format( third ) + ")"; }

		@Override
	    public boolean equals(Object _o) {
			if(_o instanceof Coordinate.ThreeDims)
				return Double.doubleToLongBits(getFirst())  == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getFirst()) 
					&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getSecond())
					&& Double.doubleToLongBits(getThird())  == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getThird());
			return false;
	    }

	}
//	public static record Coordinate5D(double x, double y, int i, int j, int level) implements TileCoordinate {
	public static class Coordinate5D implements TileCoordinate {
		double x, y; int i, j, level;
		
		public Coordinate5D(double _x, double _y, int _i, int _j, int _level) { x = _x; y = _y; i = _i; j = _j; level = _level; }

		public double 	getX() 		{ return x; }
		public double 	getY() 		{ return y; }

		public int 		getI() 		{ return i; }
		public int 		getJ() 		{ return j; }

		public int 		getLevel() 	{ return level; }

		public String 	toString() {
			StringBuilder sb = new StringBuilder();

			sb.append("(" + getX() + ", " + getY() + ")");
			sb.append(" @ ");
			sb.append("[" + getI() + ", " + getJ() + " : " + getLevel() + "]");

			return sb.toString();
		}
		public String 	toString(DecimalFormat _df) {
			StringBuilder sb = new StringBuilder();

			sb.append("(" + _df.format(getX()) + ", " + _df.format(getY()) + ")");
			sb.append(" @ ");
			sb.append("[" + getI() + "," + getJ() + ":" + getLevel() + "]");

			return sb.toString();
		}

		@Override
	    public boolean  equals(Object _o) {
			if(_o instanceof TileCoordinate) {
				TileCoordinate tc = (TileCoordinate) _o;
				return getLevel() == tc.getLevel() && getJ() == tc.getJ() && getI() == tc.getI()
					&& Double.doubleToLongBits(getY()) == Double.doubleToLongBits(tc.getY())
					&& Double.doubleToLongBits(getX()) == Double.doubleToLongBits(tc.getX());
			}
			return false;
	    }

	}

	public static Coordinate.OneDim 				of(double _x) {
		return new Coordinate1D(_x);
	}
	public static Coordinate.TwoDims 				of(double _x, double _y) {
		return new Coordinate2D(_x, _y);
	}
	public static Coordinate.ThreeDims 				of(double _x, double _y, double _z) {
		return new Coordinate3D(_x, _y, _z);
	}
	public static TileCoordinate					of(int _i, int _j, int _level) {
		return new Coordinate5D(0, 0, _i, _j, _level);
	}
	public static TileCoordinate					of(double _x, double _y, int _i, int _j, int _level) {
		return new Coordinate5D(_x, _y, _i, _j, _level);
	}

	public static Coordinate.OneDim.Editable 		ofEditable(double _x) {
		return new Coordinate.OneDim.Editable() {
			double first = _x;
			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Linear; }
			@Override public void 								setFirst(double _first)		{ first = _first; }
			@Override public double 							getFirst() 					{ return first; }
			@Override public Coordinate.OneDim.Editable 		clone() 					{ return ofEditable(getFirst()); }

			@Override public String 							toString() 					{ return "(" + getFirst() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.OneDim)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.OneDim) _o).getFirst());
				return false;
		    }

		};
	}
	public static Coordinate.TwoDims.Editable 		ofEditable(double _x, double _y) {
		return new Coordinate.TwoDims.Editable() {
			double first = _x, second = _y;

			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Cartesian2D; }

			@Override public void 								setFirst(double _x) 		{ first = _x; }
			@Override public double 							getFirst() 					{ return first; }

			@Override public void 								setSecond(double _y) 		{ second = _y; }
			@Override public double 							getSecond() 				{ return second; }

			@Override public Coordinate.TwoDims.Editable 		clone() 					{ return ofEditable(getFirst(), getSecond()); }

			@Override public String 							toString() 					{ return "(" + getFirst() + ", " + getSecond() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " + _df.format(getSecond()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.TwoDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getFirst()) 
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getSecond());
				return false;
		    }

		};
	}
	public static Coordinate.ThreeDims.Editable 	ofEditable(double _x, double _y, double _z) {
		return new Coordinate.ThreeDims.Editable() {
			double first = _x, second = _y, third = _z;

			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Cartesian3D; }

			@Override public void 								setFirst(double _x) 		{ first = _x; }
			@Override public double 							getFirst() 					{ return first; }

			@Override public void 								setSecond(double _y) 		{ second = _y; }
			@Override public double 							getSecond() 				{ return second; }

			@Override public void 								setThird(double _z) 		{ third = _z; }
			@Override public double 							getThird() 					{ return third; }

			@Override public Coordinate.ThreeDims.Editable 		clone() 					{ return ofEditable(getFirst(), getSecond(), getThird()); }

			@Override public String 							toString() 					{ return "(" + getFirst() + ", " + getSecond() + ", " + getThird() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " + _df.format(getSecond()) + ", " + _df.format(getThird()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				hash = hash * 11 + Long.valueOf(Double.doubleToLongBits(getThird())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.ThreeDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getFirst())
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getSecond())
						&& Double.doubleToLongBits(getThird()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getThird());
				return false;
		    }

		};
	}

	public static Coordinate.Cartesian1D.Editable 	newCartesian(double _x) {
		return new Coordinate.Cartesian1D.Editable() {
			double x = _x;

			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Cartesian1D; }

			@Override public void 								setX(double _x) 			{ x = _x; }
			@Override public double 							getX() 						{ return x; }

			@Override public Coordinate.Cartesian1D.Editable 	clone() 					{ return newCartesian(getX()); }

			@Override public String 							toString() 					{ return "(" + getX() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getX()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.OneDim)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.OneDim) _o).getFirst());
				return false;
		    }

		};
	}
	public static Coordinate.Cartesian2D.Editable 	newCartesian(double _x, double _y) {
		return new Coordinate.Cartesian2D.Editable() {
			double x = _x, y = _y;

			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Cartesian2D; }

			@Override public void 								setX(double _x) 			{ x = _x; }
			@Override public double 							getX() 						{ return x; }

			@Override public void 								setY(double _y) 			{ y = _y; }
			@Override public double 							getY() 						{ return y; }

			@Override public Coordinate.Cartesian2D.Editable 	clone() 					{ return newCartesian(getX(), getY()); }

			@Override public String 							toString() 					{ return "(" + getX() + ", " + getY() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getX()) + ", " + _df.format(getY()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.TwoDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getFirst()) 
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getSecond());
				return false;
		    }

		};
	}
	public static Coordinate.Cartesian3D.Editable 	newCartesian(double _x, double _y, double _z) {
		return new Coordinate.Cartesian3D.Editable() {
			double x = _x, y = _y, z = _z;

			@Override public CoordinateSystem					getCoordinateSystem() 		{ return CoordinateSystem.Cartesian3D; }

			@Override public void 								setX(double _x) 			{ x = _x; }
			@Override public double 							getX() 						{ return x; }

			@Override public void 								setY(double _y) 			{ y = _y; }
			@Override public double 							getY() 						{ return y; }

			@Override public void 								setZ(double _z) 			{ z = _z; }
			@Override public double 							getZ() 						{ return z; }

			@Override public Coordinate.Cartesian3D.Editable 	clone() 					{ return newCartesian(getX(), getY(), getZ()); }

			@Override public String 							toString() 					{ return "(" + getX() + ", " + getY() + ", " + getZ() + ")"; }
			@Override public String 							toString(DecimalFormat _df) { return "(" + _df.format(getX()) + ", " + _df.format(getY()) + ", " + _df.format(getZ()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				hash = hash * 11 + Long.valueOf(Double.doubleToLongBits(getThird())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.ThreeDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getFirst())
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getSecond())
						&& Double.doubleToLongBits(getThird()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getThird());
				return false;
		    }

		};
	}

	public static Coordinate.Cartesian2D.Editable 	newCartesian2D() {
		return newCartesian(0.0, 0.0);
	}
	public static Coordinate.Cartesian3D.Editable 	newCartesian3D() {
		return newCartesian(0.0, 0.0, 0.0);
	}
	public static Coordinate.Cartesian3D.Editable 	newCartesian3D(double _x, double _y) {
		return newCartesian(_x, _y, 0.0);
	}
	
	public static Coordinate.Polar2D.Editable 		newPolar2D() {
		return newPolar2D(0.0, 0.0);
	}
	public static Coordinate.Polar2D.Editable 		newPolar2D(double _radius, double _theta) {
		return new Coordinate.Polar2D.Editable() {
			double radius = _radius, theta = _theta;

			@Override public CoordinateSystem					getCoordinateSystem() 			{ return CoordinateSystem.Polar2D; }

			@Override public void 								setRadius(double _r) 			{ radius = _r; }
			@Override public double 							getRadius() 					{ return radius; }

			@Override public void 								setAngle(double _a) 			{ theta = _a; }
			@Override public double 							getAngle() 						{ return theta; }

			@Override public Coordinate.Polar.Editable 			clone() 						{ return newPolar2D(getRadius(), getAngle()); }

			@Override public String 							toString() 						{ return "(" + getRadius() + ", " + getAngle() + ")"; }
			@Override public String 							toString(DecimalFormat _df) 	{ return "(" + _df.format(getRadius()) + ", " + _df.format(getAngle()) + ")"; }

			@Override public int 								hashCode() 						{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.TwoDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getFirst()) 
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getSecond());
				return false;
		    }

		};
	}

	public static Coordinate.Angular2D.Editable 	newAngular2D() {
		return newAngular2D(0.0, 0.0);
	}
	public static Coordinate.Angular2D.Editable 	newAngular2D(double _rho, double _theta) {
		return new Coordinate.Angular2D.Editable() {
			double rho = _rho, theta = _theta;

			@Override public CoordinateSystem					getCoordinateSystem() 			{ return CoordinateSystem.Polar2D; }

			@Override public void 								setRho(double _rho) 			{ rho = _rho; }
			@Override public double 							getRho() 						{ return rho; }


			@Override public void 								setTheta(double _theta) 		{ theta = _theta; }
			@Override public double 							getTheta() 						{ return theta; }

			@Override public Coordinate.Angular2D.Editable 		clone() 						{ return newAngular2D(getRho(), getTheta()); }

			@Override public String 							toString() 						{ return "(" + getRho() + ", " + getTheta() + ")"; }
			@Override public String 							toString(DecimalFormat _df) 	{ return "(" + _df.format(getRho()) + ", " + _df.format(getTheta()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.TwoDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getFirst()) 
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.TwoDims) _o).getSecond());
				return false;
		    }

		};
	}

	public static Coordinate.Cylindric3D.Editable 	newCylindric3D() {
		return newCylindric3D(0.0, 0.0, 0.0);
	}
	public static Coordinate.Cylindric3D.Editable 	newCylindric3D(double _radius, double _theta, double _height) {
		return new Coordinate.Cylindric3D.Editable() {
			double radius = _radius, theta = _theta, height = _height;

			@Override public CoordinateSystem					getCoordinateSystem() 			{ return CoordinateSystem.Cylindric3D; }

			@Override public void   							setRadius(double _radius) 		{ radius = _radius; }
			@Override public double 							getRadius() 					{ return radius; }

			@Override public void   							setAngle(double _theta) 		{ theta = _theta; }
			@Override public double 							getAngle() 						{ return theta; }

			@Override public void   							setHeight(double _height) 		{ height = _height; }
			@Override public double 							getHeight() 					{ return height; }

			@Override public Coordinate.Cylindric3D.Editable 	clone() 						{ return newCylindric3D(getRadius(), getAngle(), getHeight()); }

			@Override public String 							toString() 						{ return "(" + getRadius() + ", " + getAngle() + ", " + getHeight() + ")"; }
			@Override public String 							toString(DecimalFormat _df) 	{ return "(" + _df.format(getRadius()) + ", " + _df.format(getAngle()) + ", " + _df.format(getHeight()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				hash = hash * 11 + Long.valueOf(Double.doubleToLongBits(getThird())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.ThreeDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getFirst())
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getSecond())
						&& Double.doubleToLongBits(getThird()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getThird());
				return false;
		    }

		};
	}

	public static Coordinate.Spheric3D.Editable 	newSpheric3D() {
		return newSpheric3D(0.0, 0.0, 0.0);
	}
	public static Coordinate.Spheric3D.Editable 	newSpheric3D(Coordinate.Angular2D _angular, double _radius) {
		return newSpheric3D(_radius, _angular.getRho(), _angular.getTheta());
	}
	public static Coordinate.Spheric3D.Editable 	newSpheric3D(double _radius, double _rho, double _theta) {
		return new Coordinate.Spheric3D.Editable() {
			double radius = _radius, rho = _rho, theta = _theta;

			@Override public CoordinateSystem					getCoordinateSystem() 			{ return CoordinateSystem.Cylindric3D; }

			@Override public void   							setRadius(double _radius) 		{ radius = _radius; }
			@Override public double 							getRadius() 					{ return radius; }

			@Override public void 								setRho(double _rho) 			{ rho = _rho; }
			@Override public double 							getRho() 						{ return rho; }

			@Override public void 								setTheta(double _theta) 		{ theta = _theta; }
			@Override public double 							getTheta() 						{ return theta; }

			@Override public Coordinate.Spheric3D.Editable 		clone() 						{ return newSpheric3D(getRadius(), getRho(), getTheta()); }

			@Override public String 							toString() 						{ return "(" + getRadius() + ", " + getRho() + ", " + getTheta() + ")"; }
			@Override public String 							toString(DecimalFormat _df) 	{ return "(" + _df.format(getRadius()) + ", " + _df.format(getRho()) + ", " + _df.format(getTheta()) + ")"; }

			@Override public int 								hashCode() 					{ 
				int hash = 1;
				hash = hash * 17 + Long.valueOf(Double.doubleToLongBits(getFirst())).hashCode();
				hash = hash * 13 + Long.valueOf(Double.doubleToLongBits(getSecond())).hashCode();
				hash = hash * 11 + Long.valueOf(Double.doubleToLongBits(getThird())).hashCode();
				return hash;
			}

			@Override
		    public boolean equals(Object _o) {
				if(_o instanceof Coordinate.ThreeDims)
					return Double.doubleToLongBits(getFirst()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getFirst())
						&& Double.doubleToLongBits(getSecond()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getSecond())
						&& Double.doubleToLongBits(getThird()) == Double.doubleToLongBits(((Coordinate.ThreeDims) _o).getThird());
				return false;
		    }

		};
	}

	public static Coordinate.Cartesian.TwoDims 		convertToCartesian2D(Coordinate.TwoDims _coord) {
		
		return null;
	}
	public static Coordinate.Polar 					convertToPolar2D(Coordinate.TwoDims _coord) {
		
		return null;
	}
	public static Coordinate.Cartesian.ThreeDims 	convertToCartesian3D(Coordinate.ThreeDims _coord) {
		
		return null;
	}
	public static Coordinate.Cylindric.ThreeDims	convertToCylindric3D(Coordinate.ThreeDims _coord) {
		
		return null;
	}
	public static Coordinate.Spheric.ThreeDims		convertToSpheric3D(Coordinate.ThreeDims _coord) {
		
		return null;
	}

	public static TileCoordinate					newTileCoordinate(int _i, int _j, int _level) {
		return new Coordinate5D(0, 0, _i, _j, _level);
	}
	public static TileCoordinate					newTileCoordinate(double _x, double _y, int _i, int _j, int _level) {
		return new Coordinate5D(_x, _y, _i, _j, _level);
	}

	public static Coordinate.TwoDims 				plus(Coordinate.TwoDims _p1, Coordinate.TwoDims _p2) {
		return new Coordinate2D(_p1.getFirst() + _p2.getFirst(), _p1.getSecond() + _p2.getSecond());
	}
	public static Coordinate.TwoDims 				minus(Coordinate.TwoDims _p1, Coordinate.TwoDims _p2) {
		return new Coordinate2D(_p1.getFirst() - _p2.getFirst(), _p1.getSecond() - _p2.getSecond());
	}
	public static Coordinate.TwoDims 				scaled(Coordinate.TwoDims _p, double _scale) {
		return of(_p.getFirst() * _scale, _p.getSecond() * _scale);
	}

	public static Coordinate.TwoDims 				clamp(Coordinate.TwoDims _coords, BoundingBox.TwoDims _region) {
		double first  = _coords.getFirst()  < _region.getMinX() ? _region.getMinX() : _coords.getFirst()  > _region.getMaxX() ? _region.getMaxX() : _coords.getFirst();
		double second = _coords.getSecond() < _region.getMinY() ? _region.getMinY() : _coords.getSecond() > _region.getMaxY() ? _region.getMaxY() : _coords.getSecond();
		return of(first, second);
	}
	public static Coordinate.TwoDims 				clamp(Coordinate.TwoDims _coords, double _minFirst, double _minSecond, double _maxFirst, double _maxSecond) {
		double first  = _coords.getFirst()  < _minFirst  ? _minFirst  : _coords.getFirst()  > _maxFirst  ? _maxFirst  : _coords.getFirst();
		double second = _coords.getSecond() < _minSecond ? _minSecond : _coords.getSecond() > _maxSecond ? _maxSecond : _coords.getSecond();
		return of(first, second);
	}

}
