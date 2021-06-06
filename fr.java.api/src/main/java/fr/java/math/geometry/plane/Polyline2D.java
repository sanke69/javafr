package fr.java.math.geometry.plane;

import java.util.Set;

public interface Polyline2D extends Shape2D {

	Point2D     	getPoint(int _index);
	Set<Point2D>   	getPoints();

	Segment2D   	getSegment(int _index);
	Set<Segment2D>  getSegments();

	Vector2D    	getDirection(int _index);
	Vector2D    	getNormal(int _index);
/*
	boolean     	contains(Point2D _pt);
	boolean     	contains(Point2D _pt, double _epsilon);
	boolean     	contains(Segment2D _seg);
	boolean     	contains(Segment2D _seg, double _epsilon);
	boolean     	contains(Polyline2D _line);
	boolean     	contains(Polyline2D _line, double _epsilon);

	boolean     	intersect(Point2D _a, Point2D _b);
	boolean     	intersect(Point2D _a, Point2D _b, double _epsilon);
	boolean     	intersect(Segment2D _seg);
	boolean     	intersect(Segment2D _seg, double _epsilon);
	boolean     	intersect(Polyline2D _line);
	boolean     	intersect(Polyline2D _line, double _epsilon);
*/
}
