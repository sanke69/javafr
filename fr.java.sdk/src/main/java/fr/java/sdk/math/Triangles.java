package fr.java.sdk.math;

import java.util.Iterator;
import java.util.Set;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Triangle2D;
import fr.java.math.topology.Coordinate;

public class Triangles {

	public static final Triangle2D of(Coordinate.TwoDims _ptA, Coordinate.TwoDims _ptB, Coordinate.TwoDims _ptC) {
		double min_x = _ptA.getFirst()  < _ptB.getFirst()  ? _ptA.getFirst()  : _ptB.getFirst()  < _ptC.getFirst()  ? _ptB.getFirst()  : _ptC.getFirst();
		double min_y = _ptA.getSecond() < _ptB.getSecond() ? _ptA.getSecond() : _ptB.getSecond() < _ptC.getSecond() ? _ptB.getSecond() : _ptC.getSecond();
		double max_x = _ptA.getFirst()  > _ptB.getFirst()  ? _ptA.getFirst()  : _ptB.getFirst()  > _ptC.getFirst()  ? _ptB.getFirst()  : _ptC.getFirst();
		double max_y = _ptA.getSecond() > _ptB.getSecond() ? _ptA.getSecond() : _ptB.getSecond() > _ptC.getSecond() ? _ptB.getSecond() : _ptC.getSecond();

		return new Triangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double  getX() 		{ return min_x; }
			@Override public double  getY() 		{ return min_y; }

			@Override public double  getWidth() 	{ return max_x - min_x; }
			@Override public double  getHeight() 	{ return max_y - min_y; }

			@Override public Point2D getA() 		{ return Points.of(_ptA); }
			@Override public Point2D getB() 		{ return Points.of(_ptB); }
			@Override public Point2D getC() 		{ return Points.of(_ptC); }

		};
	}
	public static final Triangle2D of(Set<Point2D> _tops) {
		if(_tops.size() < 3)
			return null;

		Iterator<Point2D> itor = _tops.iterator();

		Point2D _ptA  = itor.next();
		Point2D _ptB  = itor.next();
		Point2D _ptC  = itor.next();

		double  min_x = _ptA.getFirst()  < _ptB.getFirst()  ? _ptA.getFirst()  : _ptB.getFirst()  < _ptC.getFirst()  ? _ptB.getFirst()  : _ptC.getFirst();
		double  min_y = _ptA.getSecond() < _ptB.getSecond() ? _ptA.getSecond() : _ptB.getSecond() < _ptC.getSecond() ? _ptB.getSecond() : _ptC.getSecond();
		double  max_x = _ptA.getFirst()  > _ptB.getFirst()  ? _ptA.getFirst()  : _ptB.getFirst()  > _ptC.getFirst()  ? _ptB.getFirst()  : _ptC.getFirst();
		double  max_y = _ptA.getSecond() > _ptB.getSecond() ? _ptA.getSecond() : _ptB.getSecond() > _ptC.getSecond() ? _ptB.getSecond() : _ptC.getSecond();

		return new Triangle2D() {
			private static final long serialVersionUID = 1L;

			@Override public double  getX() 		{ return min_x; }
			@Override public double  getY() 		{ return min_y; }

			@Override public double  getWidth() 	{ return max_x - min_x; }
			@Override public double  getHeight() 	{ return max_y - min_y; }

			@Override public Point2D getA() 		{ return _ptA; }
			@Override public Point2D getB() 		{ return _ptB; }
			@Override public Point2D getC() 		{ return _ptC; }

		};
	}

}
