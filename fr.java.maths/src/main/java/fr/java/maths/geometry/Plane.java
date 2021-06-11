package fr.java.maths.geometry;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Frame2D;
import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Triangle2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.shapes.SimpleEllipse2D;
import fr.java.maths.geometry.plane.shapes.SimpleFrame2D;
import fr.java.maths.geometry.plane.shapes.SimplePolygon2D;
import fr.java.maths.geometry.plane.shapes.SimplePolyline2D;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.java.maths.geometry.plane.shapes.SimpleSegment2D;
import fr.java.maths.geometry.plane.shapes.SimpleTriangle2D;
import fr.java.maths.geometry.plane.types.SimpleLine2D;
import fr.java.maths.geometry.types.Points;

public class Plane {

	public static final Frame2D newFrame() {
		return new SimpleFrame2D();
	}

	public static final Line2D 			newLine(double _a, double _b) {
		return new SimpleLine2D(_a, _b);
	}
	public static final Line2D 			newLine(Point2D _one, Point2D _two) {
		double deltaX = _two.getX() - _one.getX();
		double deltaY = _two.getY() - _one.getY();
		double a      = deltaY / deltaX;
		double b      = _one.getY() - a * _one.getX();
		return new SimpleLine2D(a, b);
	}
	public static final Line2D 			newLine(Point2D _point, Vector2D _direction) {
		if(_direction.getX() == 0)
			return new SimpleLine2D(_point.getX(), true);
		if(_direction.getY() == 0)
			return new SimpleLine2D(_point.getY(), false);

		double a = _direction.getY() / _direction.getX();
		double b = _point.getY() - a * _point.getX();
		return new SimpleLine2D(a, b);
	}

	public static final Segment2D 		newSegment(double _bx, double _by, double _ex, double _ey) {
		return new SimpleSegment2D(_bx, _by, _ex, _ey);
	}
	public static final Segment2D 		newSegment(Coordinate.TwoDims _begin, Coordinate.TwoDims _end) {
		return new SimpleSegment2D(Points.of(_begin), Points.of(_end));
	}
	public static final Segment2D 		newSegment(Point2D _begin, Point2D _end) {
		return new SimpleSegment2D(_begin, _end);
	}

	public static final Triangle2D 		newTriangle(Coordinate.TwoDims _ptA, Coordinate.TwoDims _ptB, Coordinate.TwoDims _ptC) {
		return new SimpleTriangle2D(Points.of(_ptA), Points.of(_ptB), Points.of(_ptC));
	}
	public static final Triangle2D 		newTriangle(Point2D _ptA, Point2D _ptB, Point2D _ptC) {
		return new SimpleTriangle2D(_ptA, _ptB, _ptC);
	}

	public static final Rectangle2D 	newRectangle() {
		return new SimpleRectangle2D();
	}
	public static final Rectangle2D 	newRectangle(double _x, double _y, double _w, double _h) {
		return new SimpleRectangle2D(_x, _y, _w, _h);
	}

	public static final Rectangle2D 	newRectangle(Set<Coordinate.TwoDims> _tops) {
		return new SimpleRectangle2D(_tops);
	}
	public static final Rectangle2D 	newRectangle(Rectangle2D _rect) {
		return new SimpleRectangle2D(_rect);
	}

	public static final Ellipse2D 		newEllipse(double _x, double _y, double _width, double _height) {
		return new SimpleEllipse2D(_x, _y, _width, _height);
	}
	public static final Ellipse2D 		newEllipse(Set<Coordinate.TwoDims> _tops) {
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

		return new SimpleEllipse2D(_min_x, _min_y, _max_x - _min_x, _max_y - _min_y);
	}

	public static Polygon2D 			newPolygon(double[] _x, double[] _y) {
		int length = _x.length < _y.length ? _x.length : _y.length;
		
		return new SimplePolygon2D( IntStream.range(0,  length).mapToObj(i -> Points.of(_x[i], _y[i])).collect(Collectors.toList()) );
	}
	public static Polygon2D 			newPolygon(Set<Coordinate.TwoDims> _tops) {
		return new SimplePolygon2D( _tops.stream().map(Points::of).collect(Collectors.toSet()) );
	}

	public static Polyline2D 			newPolyline(double[] _x, double[] _y) {
		int length = _x.length < _y.length ? _x.length : _y.length;
		return new SimplePolyline2D( IntStream.range(0,  length).mapToObj(i -> Points.of(_x[i], _y[i])).collect(Collectors.toList()) );
	}
	public static Polyline2D 			newPolyline(Set<Coordinate.TwoDims> _tops) {
		return new SimplePolyline2D( _tops.stream().map(Points::of).collect(Collectors.toSet()) );
	}

}
