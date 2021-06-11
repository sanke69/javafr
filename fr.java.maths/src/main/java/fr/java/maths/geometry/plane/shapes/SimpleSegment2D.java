package fr.java.maths.geometry.plane.shapes;

import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.maths.geometry.types.Points;

public class SimpleSegment2D implements Segment2D {
	private static final long serialVersionUID = 5956475445752445057L;

	Point2D begin;
	Point2D end;

	public SimpleSegment2D(double _bx, double _by, double _ex, double _ey) {
		super();
		begin = Points.of(_bx, _by);
		end   = Points.of(_ex, _ey);
	}
	public SimpleSegment2D(Point2D _begin, Point2D _end) {
		super();
		begin = _begin.clone();
		end   = _end.clone();
	}

	@Override
	public Point2D getBegin() {
		return begin;
	}
	@Override
	public Point2D getEnd() {
		return end;
	}

	@Override
	public Point2D getA() {
		return begin;
	}
	@Override
	public Point2D getB() {
		return end;
	}

	@Override
	public double getLength() { 
		double ax = begin.getFirst();
		double ay = begin.getSecond();
		double bx = end.getFirst();
		double by = end.getSecond();

		return Math.sqrt( (bx-ax)*(bx-ax) + (by-ay)*(by-ay));
	}

	@Override
	public boolean contains(Point2D _pt) {
		return false;
	}
	@Override
	public boolean contains(Point2D _pt, double _epsilon) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Segment2D _seg) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Segment2D _seg, double _epsilon) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersect(Point2D _a, Point2D _b) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersect(Point2D _a, Point2D _b, double _epsilon) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersect(Segment2D _seg) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersect(Segment2D _seg, double _epsilon) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersect(Line2D _line) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersect(Line2D _line, double _epsilon) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double distanceTo() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
