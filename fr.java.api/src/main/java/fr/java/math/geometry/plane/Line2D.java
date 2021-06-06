package fr.java.math.geometry.plane;

public interface Line2D extends Shape2D {

	Point2D getPoint();
	Point2D getPoint(boolean _random);

	Vector2D getDirection();
	Vector2D getNormal();

	boolean contains(Point2D _pt);
	boolean contains(Point2D _pt, double _epsilon);
	boolean contains(Segment2D _seg);
	boolean contains(Segment2D _seg, double _epsilon);
	boolean contains(Line2D _line);
	boolean contains(Line2D _line, double _epsilon);

	boolean intersect(Point2D _a, Point2D _b);
	boolean intersect(Point2D _a, Point2D _b, double _epsilon);
	boolean intersect(Segment2D _seg);
	boolean intersect(Segment2D _seg, double _epsilon);
	boolean intersect(Line2D _line);
	boolean intersect(Line2D _line, double _epsilon);

}
