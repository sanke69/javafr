package fr.java.maths.geometry.plane;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.Plane;
import fr.java.maths.geometry.plane.shapes.SimpleRay2D;
import fr.java.maths.geometry.types.Points;


public class PlaneTests {

	public static void main(String[] args) {
		Segment2D   s = Plane.newSegment(-2, 8, 11, 11);
		Rectangle2D r = Plane.newRectangle(0, 0, 10, 10);
		Rectangle2D r2 = Plane.newRectangle(5, 5, 9, 9);

		System.out.println(getIntersection(s, r));
		System.out.println(intersect(r, r2));
	}
	

	public static boolean intersect(Segment2D _s1, Segment2D _s2) {
		return getIntersection(_s1, _s2) != null;
	}
	public static Point2D getIntersection(Segment2D _s1, Segment2D _s2) {
	    // Get A,B of first line - points : ps1 to pe1
	    double A1 = _s1.getEnd().getY()-_s1.getBegin().getY();
	    double B1 = _s1.getBegin().getX()-_s1.getEnd().getX();
	    // Get A,B of second line - points : ps2 to pe2
	    double A2 = _s2.getEnd().getY()-_s2.getBegin().getY();
	    double B2 = _s2.getBegin().getX()-_s2.getEnd().getX();

	    // Get delta and check if the lines are parallel
	    double delta = A1*B2 - A2*B1;
	    if(delta == 0) return null;

	    // Get C of first and second lines
	    double C2 = A2*_s2.getBegin().getX()+B2*_s2.getBegin().getY();
	    double C1 = A1*_s1.getBegin().getX()+B1*_s1.getBegin().getY();
	    //invert delta to make division cheaper
	    double invdelta = 1/delta;
	    // now return the Vector2 intersection point
	    return Points.of( (B2*C1 - B1*C2)*invdelta, (A1*C2 - A2*C1)*invdelta );
	}

	public static boolean intersect(Segment2D _s, Rectangle2D _r) {
		return getIntersection(_s, _r) != null;
	}
	public static Point2D getIntersection(Segment2D _s, Rectangle2D _r) {
		// https://gamedev.stackexchange.com/questions/111100/intersection-of-a-line-and-a-rectangle
		// see also https://gist.github.com/ChickenProp/3194723
		Point2D intersection;

	    if (_s.getEnd().getX() < _r.getLeft()) {
	    	//If the second point of the segment is at left/bottom-left/top-left of the AABB

	        if (_s.getEnd().getY() > _r.getBottom() && _s.getEnd().getY() < _r.getTop()) {
	        	//If it is at the left
	        	return getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getLeft(), _r.getTop())));

	        } else if (_s.getEnd().getY() < _r.getBottom()) {
	        	// //If it is at the bottom-left
	            intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getRight(), _r.getBottom())));
	            if (intersection == null) 
	            	intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getLeft(), _r.getTop())));
	            return intersection;

	        } else {
	        	//if p2.y > max_y, i.e. if it is at the top-left	
	            intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getTop()), Points.of(_r.getRight(), _r.getTop())));
	            if (intersection == null) 
	            	intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getLeft(), _r.getTop())));
	            return intersection;

	        }

	    } else if (_s.getEnd().getX() > _r.getRight()) {
	    	//If the second point of the segment is at right/bottom-right/top-right of the AABB

	        if (_s.getEnd().getY() > _r.getBottom() && _s.getEnd().getY() < _r.getTop()) {
	        	// If it is at the right
	        	return getIntersection(_s, Plane.newSegment(Points.of(_r.getRight(), _r.getBottom()), Points.of(_r.getRight(), _r.getTop())));

	        } else if (_s.getEnd().getY() < _r.getBottom()) {
	        	//If it is at the bottom-right
	            intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getRight(), _r.getBottom())));
	            if (intersection == null) 
	            	intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getRight(), _r.getBottom()), Points.of(_r.getRight(), _r.getTop())));
	            return intersection;
	        }
	        else {
	        	//if p2.y > max_y, i.e. if it is at the top-left
	            intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getTop()), Points.of(_r.getRight(), _r.getTop())));
	            if (intersection == null) 
	            	intersection = getIntersection(_s, Plane.newSegment(Points.of(_r.getRight(), _r.getBottom()), Points.of(_r.getRight(), _r.getTop())));
	            return intersection;
	        }
	    }

	    else {
	    	//If the second point of the segment is at top/bottom of the AABB
	    	
	        if (_s.getEnd().getY() < _r.getBottom()) 
	        	//If it is at the bottom
	        	return getIntersection(_s, Plane.newSegment(Points.of(_r.getLeft(), _r.getBottom()), Points.of(_r.getRight(), _r.getBottom())));
	        if (_s.getEnd().getY() > _r.getTop()) 
	        	//If it is at the top
	        	return getIntersection(_s, Plane.newSegment(Points.of(_r.getRight(), _r.getTop()), Points.of(_r.getRight(), _r.getTop()))); 
	    }

	    return null;
	}

	public static boolean intersect(Rectangle2D _r1, Rectangle2D _r2) {
		return ! ( _r2.getLeft() > _r1.getRight() || _r2.getRight() < _r1.getLeft() || _r2.getTop() < _r1.getBottom() || _r2.getBottom() > _r1.getTop());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static boolean intersect(Coordinate.TwoDims _p, Coordinate.TwoDims _a, Coordinate.TwoDims _b) {
		if ( ( _a.getSecond() > _p.getSecond() ) != ( _b.getSecond() > _p.getSecond() ) 
		   &&  _p.getFirst() < ( _b.getFirst() - _a.getFirst() ) * ( _p.getSecond() - _a.getSecond() ) / ( _b.getSecond() - _a.getSecond() ) + _a.getFirst() )
			return true;
		return false;
	}
	public static boolean intersect(Point2D _p, Point2D _a, Point2D _b) {
		if ( ( _a.getY() > _p.getY() ) != ( _b.getY() > _p.getY() ) 
		   &&  _p.getX() < ( _b.getX() - _a.getX() ) * ( _p.getY() - _a.getY() ) / ( _b.getY() - _a.getY() ) + _a.getX() )
			return true;
		return false;
	}
	public static Point2D getIntersection(SimpleRay2D _ray, Point2D _a, Point2D _b) {
		Point2D  P = _ray.getOrigin();
		Vector2D D = _ray.getDirection(),
				 N = Vectors.delta(_b, _a);

		if(N.isColinear(D))
			return null;
		
		// P + t1 . D = A + t2 . N
		double t2 = ( D.getY() * (_a.getX() - P.getX()) - D.getX() * (_a.getY() - P.getY()) ) / ( D.getX()*N.getY() - N.getX()*D.getY());
		if(t2 > 0 && t2 < 1)
			return Points.of(_a.getX() + t2 * _b.getX(), _a.getY() + t2 * _b.getY());
		
		return null;
	}

	// pointLineTest
	// ===============
	public final static int	ONSEGMENT	= 0;
	public final static int	LEFT		= 1;
	public final static int	RIGHT		= 2;
	public final static int	INFRONTOFA	= 3;
	public final static int	BEHINDB		= 4;
	public final static int	ERROR		= 5;

	public static int pointLineTest(Point2D _v, Point2D a, Point2D b) {

		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double res = dy * (_v.getX() - a.getX()) - dx * (_v.getY() - a.getY());

		if(res < 0)
			return LEFT;
		if(res > 0)
			return RIGHT;

		if(dx > 0) {
			if(_v.getX() < a.getX())
				return INFRONTOFA;
			if(b.getX() < _v.getX())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dx < 0) {
			if(_v.getX() > a.getX())
				return INFRONTOFA;
			if(b.getX() > _v.getX())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dy > 0) {
			if(_v.getY() < a.getY())
				return INFRONTOFA;
			if(b.getY() < _v.getY())
				return BEHINDB;
			return ONSEGMENT;
		}
		if(dy < 0) {
			if(_v.getY() > a.getY())
				return INFRONTOFA;
			if(b.getY() > _v.getY())
				return BEHINDB;
			return ONSEGMENT;
		}
		System.out.println("Error, pointLineTest with a=b ");
		return ERROR;
	}

	boolean areCollinear(Vector2D _v, Point2D a, Point2D b) {
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double res = dy * (_v.getX() - a.getX()) - dx * (_v.getY() - a.getY());
		return res == 0.0;
	}

	Vector2D circumcenter(Vector2D _v, Vector2D a, Vector2D b) {
		double u = ((a.getX() - b.getX()) * (a.getX() + b.getX()) + (a.getY() - b.getY()) * (a.getY() + b.getY())) / 2.0f;
		double v = ((b.getX() - _v.getX()) * (b.getX() + _v.getX()) + (b.getY() - _v.getY()) * (b.getY() + _v.getY())) / 2.0f;
		double den = (a.getX() - b.getX()) * (b.getY() - _v.getY()) - (b.getX() - _v.getX()) * (a.getY() - b.getY());
		if(den == 0.0) // oops
			System.out.println("circumcenter, degenerate case");
		return Vectors.of((u * (b.getY() - _v.getY()) - v * (a.getY() - b.getY())) / den, (v * (a.getX() - b.getX()) - u * (b.getX() - _v.getX())) / den);
	}

}
