package fr.java.sdk.math;

import java.util.Set;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.topology.Coordinate;

public class Rectangles {

	public static final Rectangle2D of(double _min_x, double _min_y, double _max_x, double _max_y) {
		return new Rectangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return _min_x; }
			@Override public double getY() 		{ return _min_y; }

			@Override public double getWidth() 	{ return _max_x - _min_x; }
			@Override public double getHeight() { return _max_y - _min_y; }

			@Override public Coordinate.TwoDims getTopLeft()     { return Points.of(_min_x, _max_y); }
			@Override public Coordinate.TwoDims getTopRight()    { return Points.of(_max_x, _max_y); }
			@Override public Coordinate.TwoDims getBottomRight() { return Points.of(_max_x, _min_y); }
			@Override public Coordinate.TwoDims getBottomLeft()  { return Points.of(_min_x, _min_y); }

		};
	}
	public static final Rectangle2D of(Coordinate.TwoDims _begin, Coordinate.TwoDims _end) {
		double min_x = _begin.getFirst() < _end.getFirst()   ? _begin.getFirst()  : _end.getFirst();
		double min_y = _begin.getSecond() < _end.getSecond() ? _begin.getSecond() : _end.getSecond();
		double max_x = Math.abs(_end.getFirst()  - _begin.getFirst());
		double max_y = Math.abs(_end.getSecond() - _begin.getSecond());

		return new Rectangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return min_x; }
			@Override public double getY() 		{ return min_y; }

			@Override public double getWidth() 	{ return max_x - min_x; }
			@Override public double getHeight() { return max_y - min_y; }

			@Override public Coordinate.TwoDims getTopLeft()     { return Points.of(min_x, max_y); }
			@Override public Coordinate.TwoDims getTopRight()    { return Points.of(max_x, max_y); }
			@Override public Coordinate.TwoDims getBottomRight() { return Points.of(max_x, min_y); }
			@Override public Coordinate.TwoDims getBottomLeft()  { return Points.of(min_x, min_y); }

		};
	}
	public static final Rectangle2D of(Set<Coordinate.TwoDims> _tops) {
		double min_x =   Double.MAX_VALUE;
		double min_y =   Double.MAX_VALUE;
		double max_x = - Double.MAX_VALUE;
		double max_y = - Double.MAX_VALUE;

		for(Coordinate.TwoDims pt : _tops) {
			if(pt.getFirst() < min_x)
				min_x = pt.getFirst();
			if(pt.getSecond() < min_y)
				min_y = pt.getSecond();
			if(pt.getFirst() > max_x)
				max_x = pt.getFirst();
			if(pt.getSecond() > max_y)
				max_y = pt.getSecond();
		}

		final double _min_x = min_x;
		final double _min_y = min_y;
		final double _max_x = max_x;
		final double _max_y = max_y;

		return new Rectangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return _min_x; }
			@Override public double getY() 		{ return _min_y; }

			@Override public double getWidth() 	{ return _max_x - _min_x; }
			@Override public double getHeight() { return _max_y - _min_y; }

			@Override public Coordinate.TwoDims getTopLeft()     { return Points.of(_min_x, _max_y); }
			@Override public Coordinate.TwoDims getTopRight()    { return Points.of(_max_x, _max_y); }
			@Override public Coordinate.TwoDims getBottomRight() { return Points.of(_max_x, _min_y); }
			@Override public Coordinate.TwoDims getBottomLeft()  { return Points.of(_min_x, _min_y); }

		};
	}
	public static final Rectangle2D of(Point2D _begin, Point2D _end) {
		double min_x = _begin.getFirst() < _end.getFirst()   ? _begin.getFirst()  : _end.getFirst();
		double min_y = _begin.getSecond() < _end.getSecond() ? _begin.getSecond() : _end.getSecond();
		double max_x = Math.abs(_end.getFirst()  - _begin.getFirst());
		double max_y = Math.abs(_end.getSecond() - _begin.getSecond());

		return new Rectangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double getX() 		{ return min_x; }
			@Override public double getY() 		{ return min_y; }

			@Override public double getWidth() 	{ return max_x - min_x; }
			@Override public double getHeight() { return max_y - min_y; }

			@Override public Coordinate.TwoDims getTopLeft()     { return Points.of(min_x, max_y); }
			@Override public Coordinate.TwoDims getTopRight()    { return Points.of(max_x, max_y); }
			@Override public Coordinate.TwoDims getBottomRight() { return Points.of(max_x, min_y); }
			@Override public Coordinate.TwoDims getBottomLeft()  { return Points.of(min_x, min_y); }

		};
	}

}
