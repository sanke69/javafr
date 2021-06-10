package fr.java.maths.interpolation.coordinates;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Numbers;
import fr.java.maths.Points;
import fr.java.maths.interpolation.CoordinateInterpolator;

public class BezierCurve implements CoordinateInterpolator.TwoDims {

	public static BezierCurve linear	(Point2D _P0, Point2D _P1) {
		return new BezierCurve(_P0, _P1);
	}
	public static BezierCurve quadratic	(Point2D _P0, Point2D _P1, Point2D _P2) {
		return new BezierCurve(_P0, _P1, _P2);
	}
	public static BezierCurve cubic		(Point2D _P0, Point2D _P1, Point2D _P2, Point2D _P3) {
		return new BezierCurve(_P0, _P1, _P2, _P3);
	}
	public static BezierCurve of		(Point2D... _PTS) {
		assert(_PTS.length > 2);

		return new BezierCurve(_PTS);
	}

	Point2D[] pts;

	private BezierCurve(Point2D _P0, Point2D _P1) {
		super();
		pts    = new Point2D[] { _P0, _P1};
	}
	private BezierCurve(Point2D _P0, Point2D _P1, Point2D _P2) {
		super();
		pts    = new Point2D[] { _P0, _P1, _P2};
	}
	private BezierCurve(Point2D _P0, Point2D _P1, Point2D _P2, Point2D _P3) {
		super();
		pts    = new Point2D[] { _P0, _P1, _P2, _P3};
	}
	private BezierCurve(Point2D[] _PTS) {
		super();
		pts    = _PTS.clone();
	}

	public Point2D   evaluate(double t) {
		double bPoly[] = new double[pts.length];

		for(int i = 0; i < pts.length; i++)
			bPoly[i] = bernstein(pts.length - 1, i, t);

		double sumX = 0;
		double sumY = 0;

		for(int i = 0; i < pts.length; i++) {
			sumX += bPoly[i] * pts[i].getX();
			sumY += bPoly[i] * pts[i].getY();
		}

		return Points.of(sumX, sumY);
	}

	public Point2D[] interpolate(int _nbPoints) {
		double    dt      = 1d / (_nbPoints - 1);
		Point2D[] interps = new Point2D[_nbPoints];

		for(int i = 0; i < _nbPoints; i++)
			interps[i] = evaluate(i * dt);

		return interps;
	}

	public static double bernstein(int _n, int _i, double t) {
		return Numbers.combination(_n, _i) * Math.pow(1 - t, _n - _i) * Math.pow(t, _i);
	}

}
