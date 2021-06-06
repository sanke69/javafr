package fr.java.math.geometry.plane;

public interface Segment2D extends Shape2D {

	public Point2D getBegin();
	public Point2D getEnd();

	Point2D getA();
	Point2D getB();

	double  getLength();

	boolean contains(Point2D _pt);
	boolean contains(Point2D _pt, double _epsilon);
	boolean contains(Segment2D _seg);
	boolean contains(Segment2D _seg, double _epsilon);

	boolean intersect(Point2D _a, Point2D _b);
	boolean intersect(Point2D _a, Point2D _b, double _epsilon);
	boolean intersect(Segment2D _seg);
	boolean intersect(Segment2D _seg, double _epsilon);
	boolean intersect(Line2D _line);
	boolean intersect(Line2D _line, double _epsilon);

	double  distanceTo();

}
