package fr.java.maths.geometry.types;

import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Geometry;
import fr.java.math.geometry.linear.Point1D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.algebra.vectors.DoubleVector1D;
import fr.java.maths.algebra.vectors.DoubleVector2D;
import fr.java.maths.algebra.vectors.DoubleVector3D;

public class Points {

	// One Dimension -----------------------------------------------------------
	public static final Point1D  NaN1  = new DoubleVector1D(Double.NaN);

	public static Point1D.Editable	O1() 										{ return new DoubleVector1D(); }

	public static Point1D.Editable 	zero1() 									{ return new DoubleVector1D( 0); }
	public static Point1D.Editable 	unit1() 									{ return new DoubleVector1D( 1); }

	public static Point1D 			of(double _x) 								{ return new DoubleVector1D(_x); }
	public static Point1D 			of(Coordinate.OneDim _coords) 				{ return new DoubleVector1D(_coords.getFirst()); }
	public static Point1D 			of(Vector1D _vector) 						{ return new DoubleVector1D(_vector.getX()); }
	public static Point1D 			of1(NumberVector _v) 						{
		if(_v == null || _v.size() < 0)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector1D(_v.getNumber(0).doubleValue());
	}

	public static Point1D.Editable 	ofEditable(double _x) 						{ return new DoubleVector1D(_x); }
	public static Point1D.Editable 	ofEditable(Coordinate.OneDim _coords) 		{ return new DoubleVector1D(_coords.getFirst()); }
	public static Point1D.Editable	ofEditable(Vector1D _vector) 				{ return new DoubleVector1D(_vector.getX()); }
	public static Point1D.Editable	ofEditable1(NumberVector _v) 				{
		if(_v == null || _v.size() < 1)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector1D(_v.getNumber(0).doubleValue());
	}

	// Two Dimensions ----------------------------------------------------------
	public static final Point2D  NaN2  = new DoubleVector2D(Double.NaN, Double.NaN);

	public static Point2D.Editable	O2() 										{ return new DoubleVector2D(); }

	public static Point2D.Editable 	zero2() 									{ return new DoubleVector2D( 0,  0); }
	public static Point2D.Editable 	unit2() 									{ return new DoubleVector2D( 1,  1); }

	public static Point2D 			of(double _x, double _y) 					{ return new DoubleVector2D(_x, _y); }
	public static Point2D 			of(Coordinate.TwoDims _coords) 				{ return new DoubleVector2D(_coords.getFirst(), _coords.getSecond()); }
	public static Point2D 			of(Vector2D _vector) 						{ return new DoubleVector2D(_vector.getX(), _vector.getY()); }
	public static Point2D 			of2(NumberVector _v) 						{
		if(_v == null || _v.size() < 2)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector2D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue());
	}

	public static Point2D.Editable 	ofEditable(double _x, double _y) 			{ return new DoubleVector2D(_x, _y); }
	public static Point2D.Editable 	ofEditable(Coordinate.TwoDims _coords) 		{ return new DoubleVector2D(_coords.getFirst(), _coords.getSecond()); }
	public static Point2D.Editable	ofEditable(Vector2D _vector) 				{ return new DoubleVector2D(_vector.getX(), _vector.getY()); }
	public static Point2D.Editable 	ofEditable2(NumberVector _v) 				{
		if(_v == null || _v.size() < 2)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector2D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue());
	}

	// Three Dimensions --------------------------------------------------------
	public static final Point3D NaN3 = new DoubleVector3D(Double.NaN,  Double.NaN,  Double.NaN);

	public static Point3D.Editable 	O3() 										{ return new DoubleVector3D(); }

	public static Point3D.Editable 	zero3() 									{ return new DoubleVector3D( 0,  0,  0); }
	public static Point3D.Editable 	unit3() 									{ return new DoubleVector3D( 1,  1,  1); }

	public static Point3D 			of(double _x, double _y, double _z) 		{ return new DoubleVector3D(_x, _y, _z); }
	public static Point3D 			of(Coordinate.ThreeDims _coords) 			{ return new DoubleVector3D(_coords.getFirst(), _coords.getSecond(), _coords.getThird()); }
	public static Point3D 			of(Vector3D _vector) 						{ return new DoubleVector3D(_vector.getX(), _vector.getY(), _vector.getZ()); }
	public static Point3D 			of3(NumberVector _v) 						{
		if(_v == null || _v.size() < 3)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector3D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue(), _v.getNumber(2).doubleValue());
	}

	public static Point3D.Editable 	ofEditable(double _x, double _y, double _z) { return new DoubleVector3D(_x, _y, _z); }
	public static Point3D.Editable 	ofEditable(Coordinate.ThreeDims _coords) 	{ return new DoubleVector3D(_coords.getFirst(), _coords.getSecond(), _coords.getThird()); }
	public static Point3D.Editable	ofEditable(Vector3D _vector) 				{ return new DoubleVector3D(_vector.getX(), _vector.getY(), _vector.getZ()); }
	public static Point3D.Editable 	ofEditable3(NumberVector _v) 				{
		if(_v == null || _v.size() < 3)
			throw new IllegalAccessError("Why ???");

		return new DoubleVector3D(_v.getNumber(0).doubleValue(), _v.getNumber(1).doubleValue(), _v.getNumber(2).doubleValue());
	}

	// Two Dimension Operators -------------------------------------------------
	public static double distance(Point2D A, Point2D B, Geometry.Distance _distance) {
		switch(_distance) {
		case EUCLIDIAN: int    dx = (int) (A.getX() - B.getX());
						int    dy = (int) (A.getY() - B.getY());
						double d2 = dx * dx + dy * dy;
						return Math.sqrt(d2);
		default: 		throw new RuntimeException();
		}
	}

	public static Point2D plus(Coordinate.TwoDims _pt, double _dx, double _dy) {
		return Points.of(_pt.getFirst() + _dx, _pt.getSecond() + _dy);
	}
	public static Point2D minus(Coordinate.TwoDims _pt, double _dx, double _dy) {
		return Points.of(_pt.getFirst() - _dx, _pt.getSecond() - _dy);
	}
	public static Point2D times(Coordinate.TwoDims _pt, double _t) {
		return Points.of(_pt.getFirst() * _t, _pt.getSecond() * _t);
	}
	public static Point2D divides(Coordinate.TwoDims _pt, double _t) {
		return Points.of(_pt.getFirst() * _t, _pt.getSecond() * _t);
	}

}
