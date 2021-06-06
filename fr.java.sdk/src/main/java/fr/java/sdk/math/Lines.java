package fr.java.sdk.math;

import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.topology.Coordinate.TwoDims;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.vectors.DoubleVector2D;

public class Lines {
	public Line2D NaN = new Line2D() {

		public Point2D getPoint() 											{ return Point2D.NaN; }
		public Point2D getPoint(boolean _random) 							{ return Point2D.NaN; }

		public Vector2D getDirection() 										{ return new DoubleVector2D(Double.NaN,  Double.NaN); }
		public Vector2D getNormal() 										{ return new DoubleVector2D(Double.NaN,  Double.NaN); }

		public boolean contains(Point2D _pt) 								{ return false; }
		public boolean contains(Point2D _pt, double _epsilon) 				{ return false; }
		public boolean contains(Segment2D _seg) 							{ return false; }
		public boolean contains(Segment2D _seg, double _epsilon) 			{ return false; }
		public boolean contains(Line2D _line) 								{ return false; }
		public boolean contains(Line2D _line, double _epsilon) 				{ return false; }

		public boolean intersect(Point2D _a, Point2D _b) 					{ return false; }
		public boolean intersect(Point2D _a, Point2D _b, double _epsilon) 	{ return false; }
		public boolean intersect(Segment2D _seg) 							{ return false; }
		public boolean intersect(Segment2D _seg, double _epsilon) 			{ return false; }
		public boolean intersect(Line2D _line) 								{ return false; }
		public boolean intersect(Line2D _line, double _epsilon) 			{ return false; }

	};

	public static Line2D of(TwoDims beg, TwoDims end) {
		return of(Points.of(beg), Points.of(end));
	}

	public static final Line2D of(Point2D _pt, Vector2D _dir) {
		return new Line2D() {

			public Point2D getPoint() {
				return _pt;
			}
			public Point2D getPoint(boolean _random) {
				return _pt;
			}

			public Vector2D getDirection() {
				return _dir;
			}
			public Vector2D getNormal() {
				return new DoubleVector2D(Double.NaN,  Double.NaN);
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

		};
	}
	public static final Line2D of(Point2D _a, Point2D _b) {
		return new Line2D() {

			public Point2D getPoint() {
				return _a;
			}
			public Point2D getPoint(boolean _random) {
				return _b;
			}

			public Vector2D getDirection() {
				return Vectors.delta(_b,  _a);
			}
			public Vector2D getNormal() {
				return new DoubleVector2D(Double.NaN,  Double.NaN);
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

		};
	}

}
