package fr.java.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.geometry.types.Points;

public class jMath {

	public static final double clamp(double _x, double _min, double _max) {
		return _x < _min ? _min : _x > _max ? _max : _x;
	}

	public static List<Double> 	solve2ndDegree(double A, double B, double C) {
		// A . X² + B . X + C = 0
		double delta = B*B - 4d * A * C;

		if(delta < 0)
			return Collections.emptyList();

		if(delta == 0)
			return Arrays.asList(- B / (2 * A));

		// if(delta > 0)
		double kA = - (B + Math.sqrt(delta)) / (2 * A);
		double kB = - (B - Math.sqrt(delta)) / (2 * A);

		double k1 = kA <  kB ? kA : kB;
		double k2 = k1 == kB ? kA : kB;

		return Arrays.asList(k1, k2);
	}

	public static double 		triangleArea(Point2D _a, Point2D _b, Point2D _c) {
		double a = _a.getX();
		double b = _a.getY();
		double m = _a.getX();
		double n = _a.getY();
		double x = _a.getX();
		double y = _a.getY();

		return 0.5d * ( a * (n - y) + m * (y - b) + x * (b - n) );
	}

	public static boolean 		isAlign(Point2D _a, Point2D _b, Point2D _pt) {
		final double epsilon = 1e-6;

	    if(Math.abs(triangleArea(_a, _b, _pt)) < epsilon)
	        return true;
	    return false;
	}
	public static boolean 		isAlignOld(Point2D _a, Point2D _b, Point2D _pt) {
		final double epsilon = 1e-6;

	    double crossproduct = crossProduct(_a, _b, _pt);

	    if(Math.abs(crossproduct) > epsilon)
	        return false;

	    return true;
	}

	// TODO:: Clean !!!
	public static boolean 		isBetween(Point2D _a, Point2D _b, Point2D _pt) {
	    return isBetweenOld(_a, _b, _pt);
	}
	public static boolean 		isBetweenNew(Point2D _a, Point2D _b, Point2D _pt) {
		final double epsilon = 1e-2;

	    double crossproduct = crossProduct(_a, _b, _pt);

	    if(Math.abs(crossproduct) > epsilon)
	        return false;

	    Point2D _c = _pt;
		double KAC = dotProduct(_a, _b, _c);
		double KAB = dotProduct(_a, _b, _b);

		if(KAC < - epsilon)			// The point is before A on the line: C -> A -> B
			return false;
		if(KAC > KAB + epsilon)		// The point is after B on the line: A -> B -> C
			return false;
		if(KAC == 0)				// The point C is equal to A
			return true;
		if(KAC == KAB)				// The point C is equal to B
			return false;
		if(- epsilon < KAC && KAC < KAB + epsilon)	// The point C is equal to B
			return true;

	    return false;
	}
	public static boolean 		isBetweenOld(Point2D _a, Point2D _b, Point2D _pt) {
		final double cross_epsilon = 1e-6, dot_epsilon = 1e-2;

	    double crossproduct = crossProduct(_a, _b, _pt);

	    if(Math.abs(crossproduct) > cross_epsilon)
	        return false;

	    double dotproduct = dotProduct(_a, _b, _pt);

	    if(dotproduct < - dot_epsilon)
	        return false;

	    if(dotproduct > lengthSquare(_a, _b) + dot_epsilon)
	        return false;

	    return true;
	}

	public static boolean 		liesBetween(Point2D _a, Point2D _b, Point2D _pt) {
		double a2 = lengthSquare(_a, _b);
		double b2 = lengthSquare(_b, _pt);
		double c2 = lengthSquare(_pt, _a);

	    return a2+b2>c2 && a2+c2 > b2;
	}
	public static boolean 		liesBetweenOld(Point2D _a, Point2D _b, Point2D _pt, double _epsilon) {
		double a2 = lengthSquare(_a, _b);
		double b2 = lengthSquare(_b, _pt);
		double c2 = lengthSquare(_pt, _a);

	    return a2+b2>c2 && a2+c2 > b2 && length(_pt, getOrthoProjection(_a, _b, _pt)) < _epsilon;
	}
	public static boolean 		liesBetween(Point2D _a, Point2D _b, Point2D _pt, double _epsilon) {
		Point2D h = getOrthoProjection(_a, _b, _pt);

	    return isBetween(_a, _b, h) && length(_pt, getOrthoProjection(_a, _b, _pt)) < _epsilon;
	}

	public static Point2D		getProjection(Point2D _a, Point2D _b, Point2D _pt, Vector2D _dir) {
		return getProjection(_a, _b, _pt, _dir, false);
	}
	public static Point2D		getProjection(Point2D _a, Point2D _b, Point2D _pt, Vector2D _dir, boolean _onSegment) {
		double PAx =  _a.getX() - _pt.getX();
		double PAy =  _a.getY() - _pt.getY();
		double ABx =  _b.getX() - _a.getX();
		double ABy =  _b.getY() - _a.getY();
		double Nx  =  _dir.getX();
		double Ny  =  _dir.getY();
		double Ax  =  _a.getX();
		double Ay  =  _a.getY();

		double betaN = ABy/ABx * PAx - PAy;
		double betaQ = ABy/ABx * Nx  - Ny;
		double beta  = betaN / betaQ;

		if(beta < 0)
			return null;

		double alpha = 1d/ABx * (beta * Nx - PAx);

		if(_onSegment && (alpha < 0 || alpha > 1))
			return null;

		return Points.of(Ax + alpha * ABx, Ay + alpha * ABy);
	}

	public static Point2D		getOrthoProjection(Point2D _a, Point2D _b, Point2D _pt) {
		double xa =  _a.getX();
		double ya =  _a.getY();
		double xb =  _b.getX();
		double yb =  _b.getY();
		double xp = _pt.getX();
		double yp = _pt.getY();

		double k  = ((yb-ya) * (xp-xa) - (xb-xa) * (yp-ya)) / ((yb-ya) * (yb-ya) + (xb-xa) * (xb-xa));

		double x  = _pt.getX() - k * (_b.getY() - _a.getY());
		double y  = _pt.getY() + k * (_b.getX() - _a.getX());

		return Points.of(x, y);
	}
	public static double		getOrthoDistance(Point2D _a, Point2D _b, Point2D _pt) {
		return length(_pt, getOrthoProjection(_a, _b, _pt));
	}

	// Point2D A, B, C
	// compute cross product of (B-A) and (C-A) 
	public static double 		crossProduct(Point2D _a, Point2D _b, Point2D _c) {
	    return (_c.getY() - _a.getY()) * (_b.getX() - _a.getX()) - (_c.getX() - _a.getX()) * (_b.getY() - _a.getY());
	}
	public static double 		crossProduct(Vector2D _ba, Vector2D _ca) {
	    return _ca.getY() * _ba.getX() - _ca.getX() * _ba.getY();
	}
	// Point2D A, B, C
	// compute dot product of (B-A) and (C-A) 
	public static double 		dotProduct(Point2D _a, Point2D _b, Point2D _c) {
	    return (_c.getX() - _a.getX()) * (_b.getX() - _a.getX()) + (_c.getY() - _a.getY()) * (_b.getY() - _a.getY());
	}
	public static double 		dotProduct(Vector2D _ba, Vector2D _ca) {
	    return _ca.getX() * _ba.getX() + _ca.getY() * _ba.getY();
	}

	public static double 		distance(Point2D _a, Point2D _b) {
	    return Math.sqrt( lengthSquare(_a, _b) );
	}
	public static double 		length(Point2D _a, Point2D _b) {
	    return Math.sqrt( lengthSquare(_a, _b) );
	}
	public static double 		lengthSquare(Point2D _a, Point2D _b) {
	    return  (_b.getX() - _a.getX())*(_b.getX() - _a.getX()) + (_b.getY() - _a.getY())*(_b.getY() - _a.getY());
	}

	

	public static Point2D 		linearInterpolation				(Point2D _from, Point2D _to, double _alpha) {
		double x = _alpha * _to.getX() + (1 - _alpha) * _from.getX();
		double y = _alpha * _to.getY() + (1 - _alpha) * _from.getY();

		return Points.of(x, y);
	}
	public static Point2D 		linearInterpolationWithDistance	(Point2D _from, Point2D _to, double _distanceFrom) {
		return linearInterpolation(_from, _to, Math.sqrt( _distanceFrom / jMath.distance(_to, _from) ));
	}

	public static Point2D 		linearInterpolationWithDistance(Point2D _from, Point2D _to_a, Point2D _to_b, double _distanceFrom) {
		List<Double> K = getLinearInterpolationFactor(_from, _to_a, _to_b, _distanceFrom);

		double k = K.size() == 2 ? K.get(1) : K.size() == 1 ? K.get(0) : Double.NaN;

		if(Double.isNaN(k))
			return Points.NaN2;

		double x = k * _to_b.getX() + (1d - k) * _to_a.getX();
		double y = k * _to_b.getY() + (1d - k) * _to_a.getY();

		return Points.of(x, y);
	}
	public static List<Double> 	getLinearInterpolationFactor(Point2D _from, Point2D _to_a, Point2D _to_b, double _distanceFrom) {
		// assuming it is a well conditioned problem:
		//  AH  = k AB		A=_to_a, B=_to_b, k
		// |PH| = d			P=_from, d>0
		double xp = _from.getX(),
			   xa = _to_a.getX(),
			   xb = _to_b.getX(),
			   yp = _from.getY(),
			   ya = _to_a.getY(),
			   yb = _to_b.getY(),
			   d  = _distanceFrom;
		double Xa = xb - xa,
			   Xb = xa - xp,
			   Ya = yb - ya,
			   Yb = ya - yp;
		double A  = Xa*Xa+Ya*Ya,
			   B  = 2*Xa*Xb+2*Ya*Yb,
			   C  = Xb*Xb+Yb*Yb-d*d;
		// tq: A² . k + B . k + C = 0

		return jMath.solve2ndDegree(A, B, C);
	}


}
