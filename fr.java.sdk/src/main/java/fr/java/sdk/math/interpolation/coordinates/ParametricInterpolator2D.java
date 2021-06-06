package fr.java.sdk.math.interpolation.coordinates;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.interpolation.CoordinateInterpolator;
import fr.java.sdk.math.interpolation.FunctionInterpolator;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSpline;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSplineMonotone;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSplineWithWeight;

public class ParametricInterpolator2D implements CoordinateInterpolator.TwoDims {
	int							nbPoints;
	FunctionInterpolator.OneVar x_t, y_t;

	public ParametricInterpolator2D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp) {
		super();
		x_t = _xInterp;
		y_t = _yInterp;
	}
	public ParametricInterpolator2D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, double[] _xs, double[] _ys) {
		this(_xInterp, _yInterp);
		init(_xs, _ys);
	}
	public ParametricInterpolator2D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, Point2D[] _pts) {
		this(_xInterp, _yInterp);
		init(
			Arrays.stream(_pts).mapToDouble(Point2D::getX).toArray(), 
			Arrays.stream(_pts).mapToDouble(Point2D::getY).toArray()
			 );
	}
	public ParametricInterpolator2D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, List<Point2D> _pts) {
		this(_xInterp, _yInterp);
		init(
			_pts.stream().mapToDouble(Point2D::getX).toArray(), 
			_pts.stream().mapToDouble(Point2D::getY).toArray()
			 );
	}
	public ParametricInterpolator2D(double[] _xs, double[] _ys) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		init(_xs, _ys);
	}
	public ParametricInterpolator2D(Point2D[] _pts) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		init(
			Arrays.stream(_pts).mapToDouble(Point2D::getX).toArray(), 
			Arrays.stream(_pts).mapToDouble(Point2D::getY).toArray()
			 );
	}
	public ParametricInterpolator2D(List<Point2D> _pts) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		init(
			_pts.stream().mapToDouble(Point2D::getX).toArray(), 
			_pts.stream().mapToDouble(Point2D::getY).toArray()
			 );
	}

	public void 	 	init(double[] _x, double[] _y) {
		if(_x.length != _y.length)
			throw new IllegalArgumentException();

		double[] t = IntStream.rangeClosed(1, _x.length).mapToDouble(Double::valueOf).toArray();

		nbPoints = t.length;

		if(x_t instanceof CubicSpline)
			((CubicSpline) x_t).init(t, _x);
		else if(x_t instanceof CubicSplineMonotone)
			((CubicSplineMonotone) x_t).init(t, _x);
		else if(x_t instanceof CubicSplineWithWeight)
			((CubicSplineWithWeight) x_t).init(t, _x);
		else 
			System.err.println("Unable to update X Interpolator");

		if(y_t instanceof CubicSpline)
			((CubicSpline) y_t).init(t, _x);
		if(y_t instanceof CubicSplineMonotone)
			((CubicSplineMonotone) y_t).init(t, _x);
		if(y_t instanceof CubicSplineWithWeight)
			((CubicSplineWithWeight) y_t).init(t, _x);
		else 
			System.err.println("Unable to update Y Interpolator");
	}

	@Override
	public Point2D 		evaluate(double _t) {
		return Points.of(x_t.evaluate(_t), y_t.evaluate(_t));
	}

	@Override
	public Point2D[] 	interpolate(int _nbPoints) {
		Point2D[] interps = new Point2D[_nbPoints];
		
		double dt = (double) ((nbPoints - 1d) / (_nbPoints - 1d));
		
		for(int i = 0; i < _nbPoints; ++i) {
			double t = 1d + (double) i * dt;
			interps[i] = Points.of(x_t.evaluate(t), y_t.evaluate(t));
		}
		
		return interps;
	}

}
