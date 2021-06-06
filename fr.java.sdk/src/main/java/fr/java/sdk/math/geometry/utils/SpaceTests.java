package fr.java.sdk.math.geometry.utils;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Ray3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.matrices.Matrix33d;
import fr.java.sdk.math.algebra.matrices.Matrix44d;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;
import fr.java.sdk.math.algebra.vectors.DoubleVector4D;
import fr.java.sdk.math.geometry.space.shapes.Line3D;
import fr.java.sdk.math.geometry.space.shapes.Segment3D;
import fr.java.sdk.math.geometry.space.shapes.quadrics.surfaces.Plane3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

public class SpaceTests {

	public static void main(String[] args) {
		Point3D A   = Points.of(1, 0, 0);
		Point3D B   = Points.of(0, 1, 0);
		Point3D X   = Points.of(0, 0, 1);
		Segment3D S = Segment3D.of(A, B);
		Line3D  L   = Line3D.of(A, B);
		Plane3D P   = Plane3D.of(Points.zero3(), Vectors.of(0, 0, 1));

		System.out.println( areCollinear(Vectors.of(1, 0, 0), Vectors.of(1, 0, 0)) );
		System.out.println( areCollinear(Vectors.of(1, 0, 0), Vectors.of(0, 1, 0)) );
		System.out.println( areCollinear(Vectors.of(1, 0, 0), A, B) );

		System.out.println( getDistance(X, X) );
		System.out.println( getDistance(X, A) );
		System.out.println( getDistance(X, B) );
		System.out.println( getDistance(X, S) );
		System.out.println( getDistance(X, L) );
		System.out.println( getDistance(X, P) );
	}

	public static boolean areCollinear(Vector3D _v, Point3D a, Point3D b) {
		return areCollinear(_v, Vectors.delta(a, b));
	}
	public static boolean areCollinear(Vector3D _v, Vector3D _w) {
		Vector3D test = _v.crossProduct(_w);
		return test.getX() == 0 && test.getY() == 0 && test.getZ() == 0;
	}


	/**
	 * DISTANCES
	 */
	public static double getDistance(Point3D _a, Point3D _b) {
		return Vectors.delta(_b, _a).norm();
	}

	public static double getDistance(Segment3D _segment, Point3D _point) {
		return getDistance(_point, _segment);
	}
	public static double getDistance(Point3D _point, Segment3D _segment) {
		Vector3D AP = Vectors.delta(_segment.A(), _point);
		double   t  = AP.dotProduct( _segment.AB() ) / _segment.AB().norm2();
		
		t = Math.min( Math.max( 0, t ), 1 );

		Point3D P = _segment.A().plus(_segment.AB().times(t));
		return getDistance(_point, P);
	}

	public static double getDistance(Line3D _line, Point3D _point) {
		return getDistance(_point, _line);
	}
	public static double getDistance(Point3D _point, Line3D _line) {
		Vector3D AP = Vectors.delta(_point, _line.getPoint());
		Vector3D AB = _line.getDirection();
		return Math.abs(AP.dotProduct(AB)) / AB.norm2();
	}

	public static double getDistance(Plane3D _plane, Point3D _point) {
		return getDistance(_point, _plane);
	}
	public static double getDistance(Point3D _point, Plane3D _plane) {
		double A = _plane.A();
		double B = _plane.B();
		double C = _plane.C();
		double D = _plane.D();
		return Math.abs(A*_point.getX() + B*_point.getY() + C*_point.getZ() + D) / Math.sqrt(A*A+B*B+C*C);
	}

	/**
	 * PROJECTIONS
	 */
	public static Point3D getProjection(Point3D _point, Line3D _line) {
		Vector3D AP = Vectors.delta(_point, _line.getPoint());
		Vector3D AB = _line.getDirection();
		return _line.getPoint().plus(AB.times(AP.dotProduct(AB)/AB.dotProduct(AB)));
	}
	public static Point3D getProjection(Point3D _point, Plane3D _plane) {
		Vector3D V = Vectors.delta(_point, _plane.getZero());
		double   l = V.dotProduct(_plane.getNormal());

		return _point.minus(_plane.getNormal().normalized().times(l));
	}

	/**
	 * INTERSECTIONS
	 */
	public static Point3D getIntersection(Ray3D _ray1, Ray3D _ray2) {
		Point3D  O = _ray1.getOrigin();
		Point3D  P = _ray2.getOrigin();
		Vector3D D = _ray1.getDirection();
		Vector3D N = _ray2.getDirection();

		if(O == null || P == null | D == null || N == null)
			return null;
		
		// L1(t) = P0 + t . D0
		// L2(s) = P1 + s . D1
		
		double s = -1;

		if(D.getX() != 0) {
			if(N.getY() != 0) {
				s = (D.getX() / (D.getY() * N.getX() - D.getX() * N.getY())) * ( (P.getY() - O.getY()) - (D.getY() / D.getX()) * (P.getX() - O.getX()) );
				return P.plus(N.times(s));
			} else if(N.getZ() != 0) {
				s = (D.getX() / (D.getZ() * N.getX() - D.getX() * N.getZ())) * ( (P.getZ() - O.getZ()) - (D.getZ() / D.getX()) * (P.getX() - O.getX()) );
				return P.plus(N.times(s));
			}
		}
		
		if(D.getY() != 0) {
			if(N.getX() != 0) {
				s = (D.getY() / (D.getX() * N.getY() - D.getY() * N.getX())) * ( (P.getX() - O.getX()) - (D.getX() / D.getY()) * (P.getY() - O.getY()) );
				return P.plus(N.times(s));
			} else if(N.getZ() != 0) {
				s = (D.getY() / (D.getZ() * N.getY() - D.getY() * N.getZ())) * ( (P.getZ() - O.getZ()) - (D.getZ() / D.getY()) * (P.getY() - O.getY()) );
				return P.plus(N.times(s));
			}
		}

		if(D.getZ() != 0) {
			if(N.getX() != 0) {
				s = (D.getZ() / (D.getX() * N.getZ() - D.getZ() * N.getX())) * ( (P.getX() - O.getX()) - (D.getX() / D.getZ()) * (P.getZ() - O.getZ()) );
				return P.plus(N.times(s));
			} else if(N.getY() != 0) {
				s = (D.getZ() / (D.getY() * N.getZ() - D.getZ() * N.getY())) * ( (P.getY() - O.getY()) - (D.getY() / D.getZ()) * (P.getZ() - O.getZ()) );
				return P.plus(N.times(s));
			}
		}

//		throw new ArithmeticException("null vector");
		return null;
	}

	public static Point3D getIntersection(Plane3D _plane, SimpleRay3D _ray) {
		return getIntersection(_ray, _plane);
	}
	public static Point3D getIntersection(SimpleRay3D _ray, Plane3D _plane) {
		Point3D  P0 = Points.of(0, 0, _plane.getSurfacePointZ(0, 0));
		Vector3D V0 = Vectors.delta(P0, _ray.getOrigin());
		double   t  = V0.dotProduct(_plane.getNormal()) / _ray.getDirection().dotProduct(_plane.getNormal());

		return t > 0 ? _ray.get(t) : null;
	}

	// https://en.wikipedia.org/wiki/Plane_%28geometry%29#Distance_from_a_point_to_a_plane
	public static Line3D getIntersection(Plane3D _planeA, Plane3D _planeB) {
		return null;
	}


	/**
	 * OTHER TESTS
	 */
	public static boolean contains(Point3D _pt, Segment3D _segment) {
		
		
		return false;
	}
	
	
	
	

	/**
	 * OTHER TESTS
	 */
	public Point2D transformPoint(Point2D _p, Matrix33d _T) {
		Vector3D up = Vectors.of(_p.getX(), _p.getY(), 1.0f);
		up = _T.times(up);
		return Points.of(up.getX(), up.getY());
	}
	public Vector2D transformVector(Vector2D _v, Matrix33d _T) {
		Vector3D uv = new DoubleVector3D(_v.getX(), _v.getY(), 0.0f);
		uv = _T.times(uv);
		return Vectors.of(uv.getX(), uv.getY());
	}

	public Point3D transformPoint(Point3D _p, Matrix44d _T) {
		DoubleVector4D up = new DoubleVector4D(_p.getX(), _p.getY(), _p.getZ(), 1.0f);
		return _T.times(up).as3D();
	}
	public Vector3D transformVector(Vector3D _v, Matrix44d _T) {
		DoubleVector4D uv = new DoubleVector4D(_v.getX(), _v.getY(), _v.getZ(), 0.0f);
		return _T.times(uv).as3D();
	}

	public Vector3D transformPoint(Vector3D _p, Matrix33d _T) {
		return _T.times(_p);
	}
	public DoubleVector4D transformPoint(DoubleVector4D _p, Matrix44d _T) {
		return _T.times(_p);
	}

	// pointLineTest
	// ===============
	public final static int	ONSEGMENT	= 0;
	public final static int	LEFT		= 1;
	public final static int	RIGHT		= 2;
	public final static int	INFRONTOFA	= 3;
	public final static int	BEHINDB		= 4;
	public final static int	ERROR		= 5;

	public static int pointLineTest(Point3D _p, Point3D _a, Point3D _b) {
		double dx = _b.getX() - _a.getX();
		double dy = _b.getY() - _a.getY();
		double res = dy * (_p.getX() - _a.getX()) - dx * (_p.getY() - _a.getY());

		if(res < 0)
			return LEFT;
		if(res > 0)
			return RIGHT;

		if(dx > 0) {
			if(_p.getX() < _a.getX())
				return INFRONTOFA;
			if(_b.getX() < _p.getX())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dx < 0) {
			if(_p.getX() > _a.getX())
				return INFRONTOFA;
			if(_b.getX() > _p.getX())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dy > 0) {
			if(_p.getY() < _a.getY())
				return INFRONTOFA;
			if(_b.getY() < _p.getY())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dy < 0) {
			if(_p.getY() > _a.getY())
				return INFRONTOFA;
			if(_b.getY() > _p.getY())
				return BEHINDB;
			return ONSEGMENT;
		}
		System.out.println("Error, pointLineTest with a=b ");
		return ERROR;
	}

	Vector3D circumcenter(Vector3D _v, Vector3D a, Vector3D b) {
		double u = ((a.getX() - b.getX()) * (a.getX() + b.getX()) + (a.getY() - b.getY()) * (a.getY() + b.getY())) / 2.0f;
		double v = ((b.getX() - _v.getX()) * (b.getX() + _v.getX()) + (b.getY() - _v.getY()) * (b.getY() + _v.getY())) / 2.0f;
		double den = (a.getX() - b.getX()) * (b.getY() - _v.getY()) - (b.getX() - _v.getX()) * (a.getY() - b.getY());
		if(den == 0.0) // oops
			System.out.println("circumcenter, degenerate case");
		return Vectors.of((u * (b.getY() - _v.getY()) - v * (a.getY() - b.getY())) / den, (v * (a.getX() - b.getX()) - u * (b.getX() - _v.getX())) / den, 0.0f);
	}

}
