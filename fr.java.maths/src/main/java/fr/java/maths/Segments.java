package fr.java.maths;

import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.topology.Coordinate;

public class Segments {
	
	static class MySegment2D implements Segment2D {
		Point2D begin;
		Point2D end;

		public MySegment2D(double _bx, double _by, double _ex, double _ey) {
			super();
			begin = Points.of(_bx, _by);
			end   = Points.of(_ex, _ey);
		}
		public MySegment2D(Point2D _begin, Point2D _end) {
			super();
			begin = _begin.clone();
			end   = _end.clone();
		}
	
		@Override public Point2D getBegin() { return begin; }
		@Override public Point2D getEnd() 	{ return end; }

		@Override public Point2D getA() 	{ return begin; }
		@Override public Point2D getB() 	{ return end; }

		@Override public double getLength() { return Segments.length(begin, end); }

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

	}

	public static final Segment2D of(double _bx, double _by, double _ex, double _ey) {
		return new MySegment2D(_bx, _by, _ex, _ey);
	}
	public static final Segment2D of(Coordinate.TwoDims _begin, Coordinate.TwoDims _end) {
		return new MySegment2D(_begin.getFirst(), _begin.getSecond(), _end.getFirst(), _end.getSecond());
	}
	public static final Segment2D of(Point2D _begin, Point2D _end) {
		return new MySegment2D(_begin, _end);
	}

	public static double length(Point2D _begin, Point2D _end) {
		double ax = _begin.getFirst();
		double ay = _begin.getSecond();
		double bx = _end.getFirst();
		double by = _end.getSecond();

		return Math.sqrt( (bx-ax)*(bx-ax) + (by-ay)*(by-ay));
	}

}
