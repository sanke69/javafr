package fr.java.maths.interpolation.coordinates;

import java.util.Arrays;
import java.util.List;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.interpolation.CoordinateInterpolator;
import fr.java.maths.geometry.types.Points;

public class LinearInterpolator2D implements CoordinateInterpolator.TwoDims {
	private double[] x, y;

	public LinearInterpolator2D(double[] _x, double[] _y) {
		super();
		x = _x.clone();
		y = _y.clone();
	}
	public LinearInterpolator2D(Point2D... _points) {
		super();
		x = Arrays.stream(_points).mapToDouble(Point2D::getX).toArray();
		y = Arrays.stream(_points).mapToDouble(Point2D::getY).toArray();
	}
	public LinearInterpolator2D(List<Point2D> _points) {
		super();
		x = _points.stream().mapToDouble(Point2D::getX).toArray();
		y = _points.stream().mapToDouble(Point2D::getY).toArray();
	}

	public Point2D 		evaluate(double _t) {
		int    T  = (int) Math.floor(_t);
		double dt = _t - T;

		if(T >= x.length - 1)
			return Points.of(x[x.length - 1], y[x.length - 1]);

		double X = (1d - dt) * x[T] + dt * x[T+1];
		double Y = (1d - dt) * y[T] + dt * y[T+1];

		return Points.of(X, Y);
	}

	@Override
	public Point2D[] 	interpolate(int _nbPoints) {
		Point2D[] pts = new Point2D[_nbPoints];
		double    dt  = (x.length - 1d) / (_nbPoints - 1d);
		
		for(int i = 0; i < _nbPoints; ++i)
			pts[i] = evaluate(i * dt);
	
		return pts;
	}

}
