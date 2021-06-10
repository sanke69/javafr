package fr.java.maths.interpolation.coordinates;

import java.util.Arrays;
import java.util.List;

import fr.java.math.geometry.space.Point3D;
import fr.java.maths.Points;
import fr.java.maths.interpolation.CoordinateInterpolator;

public class LinearInterpolator3D implements CoordinateInterpolator.ThreeDims {
	private double[] x, y, z;

	public LinearInterpolator3D(double[] _x, double[] _y, double[] _z) {
		super();
		x = _x.clone();
		y = _y.clone();
		z = _z.clone();
	}
	public LinearInterpolator3D(Point3D... _points) {
		super();
		x = Arrays.stream(_points).mapToDouble(Point3D::getX).toArray();
		y = Arrays.stream(_points).mapToDouble(Point3D::getY).toArray();
		z = Arrays.stream(_points).mapToDouble(Point3D::getZ).toArray();
	}
	public LinearInterpolator3D(List<Point3D> _points) {
		super();
		x = _points.stream().mapToDouble(Point3D::getX).toArray();
		y = _points.stream().mapToDouble(Point3D::getY).toArray();
		z = _points.stream().mapToDouble(Point3D::getZ).toArray();
	}

	public Point3D 		evaluate(double _t) {
		int    T  = (int) Math.floor(_t);
		double dt = _t - T;

		if(T >= x.length - 1)
			return Points.of(x[x.length - 1], y[x.length - 1], z[x.length - 1]);

		double X = (1d - dt) * x[T] + dt * x[T+1];
		double Y = (1d - dt) * y[T] + dt * y[T+1];
		double Z = (1d - dt) * z[T] + dt * z[T+1];

		return Points.of(X, Y, Z);
	}

	@Override
	public Point3D[] 	interpolate(int _nbPoints) {
		Point3D[] pts = new Point3D[_nbPoints];
		double    dt  = (x.length - 1d) / (_nbPoints - 1d);
		
		for(int i = 0; i < _nbPoints; ++i)
			pts[i] = evaluate(i * dt);
	
		return pts;
	}

}
