package fr.java.maths.interpolation.coordinates;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import fr.java.math.geometry.space.Point3D;
import fr.java.maths.Points;
import fr.java.maths.interpolation.CoordinateInterpolator;
import fr.java.maths.interpolation.FunctionInterpolator;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSpline;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSplineMonotone;
import fr.java.maths.interpolation.functions.splines.cubic.CubicSplineWithWeight;

public class ParametricInterpolator3D implements CoordinateInterpolator.ThreeDims {
	int							nbPoints;
	FunctionInterpolator.OneVar x_t, y_t, z_t;

	public ParametricInterpolator3D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, FunctionInterpolator.OneVar _zInterp) {
		super();
		x_t = _xInterp;
		y_t = _yInterp;
		z_t = _zInterp;
	}
	public ParametricInterpolator3D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, FunctionInterpolator.OneVar _zInterp, double[] _xs, double[] _ys, double[] _zs) {
		this(_xInterp, _yInterp, _zInterp);
		resetData(_xs, _ys, _zs);
	}
	public ParametricInterpolator3D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, FunctionInterpolator.OneVar _zInterp, Point3D[] _pts) {
		this(_xInterp, _yInterp, _zInterp);
		resetData(
				Arrays.stream(_pts).mapToDouble(Point3D::getX).toArray(), 
				Arrays.stream(_pts).mapToDouble(Point3D::getY).toArray(), 
				Arrays.stream(_pts).mapToDouble(Point3D::getZ).toArray()
				 );
	}
	public ParametricInterpolator3D(FunctionInterpolator.OneVar _xInterp, FunctionInterpolator.OneVar _yInterp, FunctionInterpolator.OneVar _zInterp, List<Point3D> _pts) {
		this(_xInterp, _yInterp, _yInterp);
		resetData(
				_pts.stream().mapToDouble(Point3D::getX).toArray(), 
				_pts.stream().mapToDouble(Point3D::getY).toArray(), 
				_pts.stream().mapToDouble(Point3D::getZ).toArray()
				 );
	}
	public ParametricInterpolator3D(double[] _xs, double[] _ys, double[] _zs) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		resetData(_xs, _ys, _zs);
	}
	public ParametricInterpolator3D(Point3D[] _pts) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		resetData(
				Arrays.stream(_pts).mapToDouble(Point3D::getX).toArray(), 
				Arrays.stream(_pts).mapToDouble(Point3D::getY).toArray(), 
				Arrays.stream(_pts).mapToDouble(Point3D::getZ).toArray()
				 );
	}
	public ParametricInterpolator3D(List<Point3D> _pts) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		resetData(
				_pts.stream().mapToDouble(Point3D::getX).toArray(), 
				_pts.stream().mapToDouble(Point3D::getY).toArray(), 
				_pts.stream().mapToDouble(Point3D::getZ).toArray()
				 );
	}

	public void 	 	resetData(double[] _x, double[] _y, double[] _z) {
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
	public Point3D 		evaluate(double _t) {
		return Points.of(x_t.evaluate(_t), y_t.evaluate(_t), z_t.evaluate(_t));
	}

	@Override
	public Point3D[] 	interpolate(int _nbPoints) {
		Point3D[] interps = new Point3D[_nbPoints];
		
		double dt = (double) ((nbPoints - 1d) / (_nbPoints - 1d));
		
		for(int i = 0; i < _nbPoints; ++i) {
			double t = 1d + (double) i * dt;
			interps[i] = Points.of(x_t.evaluate(t), y_t.evaluate(t), z_t.evaluate(t));
		}
		
		return interps;
	}

}
