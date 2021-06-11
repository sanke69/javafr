package fr.java.maths.geometry.plane.types;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.types.Points;

public class SimpleLine2D implements Line2D {
	private static final long serialVersionUID = 1831792235520779047L;

	double a, b;
	double x0 = Double.NaN, y0 = Double.NaN;

	public SimpleLine2D() {
		this(1, 0);
	}
	public SimpleLine2D(double _a, double _b) {
		super();
		a = _a;
		b = _b;
	}
	public SimpleLine2D(double _val, boolean _horizontal) {
		super();
		if(_horizontal)
			x0 = _val;
		else
			y0 = _val;
	}

	/**
	 * 
	 * Special cases:<br/>if a=(x0) then return (x0, _x)<br/>if a=(y0) then return (_x, y0)<br/>
	 * otherwise return (_x, a * _x + b)
	 */
	@Override
	public Point2D getPoint(double _x) {
		if(!Double.isNaN(x0))
			return Points.of(x0, _x);
		if(!Double.isNaN(y0))
			return Points.of(_x, y0);
		return Points.of(_x, a * _x + b);
	}

	@Override
	public Vector2D getDirection() {
		return Vectors.of(1, a);
	}
	@Override
	public Vector2D getNormal() {
		throw new NotYetImplementedException();
	}

	public boolean contains(Point2D _pt) {
		return false;
	}
	public boolean contains(Point2D _pt, double _epsilon) {
		return false;
	}
	public boolean contains(Segment2D _seg) {
		return false;
	}
	public boolean contains(Segment2D _seg, double _epsilon) 			{ return false; }
	public boolean contains(Line2D _line) 								{ return false; }
	public boolean contains(Line2D _line, double _epsilon) 				{ return false; }

	public boolean intersect(Point2D _a, Point2D _b) 					{ return false; }
	public boolean intersect(Point2D _a, Point2D _b, double _epsilon) 	{ return false; }
	public boolean intersect(Segment2D _seg) 							{ return false; }
	public boolean intersect(Segment2D _seg, double _epsilon) 			{ return false; }
	public boolean intersect(Line2D _line) 								{ return false; }
	public boolean intersect(Line2D _line, double _epsilon) 			{ return false; }

}
