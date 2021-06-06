package fr.java.sdk.math.interpolation.coordinates;

import java.util.Arrays;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.polynoms.Polynom;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.interpolation.CoordinateInterpolator;
import fr.java.sdk.math.interpolation.FunctionInterpolator;

public class PolynomialInterpolator3D implements CoordinateInterpolator.ThreeDims {

	int							nbPoints;
	FunctionInterpolator.OneVar interpolatorX, 
								interpolatorY, 
								interpolatorZ;

	public PolynomialInterpolator3D(double[] _x, double[] _y, double[] _z) {
		super();

		if(_x.length != _y.length || _y.length != _z.length)
			throw new IllegalArgumentException();

		nbPoints = _x.length;

		Polynom.Real[] 
		PIdx          = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		interpolatorX = PolynomialInterpolators.computeValuePolynomial(PIdx, _x);
		interpolatorY = PolynomialInterpolators.computeValuePolynomial(PIdx, _y);
		interpolatorZ = PolynomialInterpolators.computeValuePolynomial(PIdx, _z);
	}
	public PolynomialInterpolator3D(Point3D[] _points) {
		super();

		nbPoints = _points.length;

		Polynom.Real[] 
		PIdx          = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		interpolatorX = PolynomialInterpolators.computeValuePolynomial(PIdx, Arrays.stream(_points).mapToDouble(Point3D::getX).toArray());
		interpolatorY = PolynomialInterpolators.computeValuePolynomial(PIdx, Arrays.stream(_points).mapToDouble(Point3D::getY).toArray());
		interpolatorZ = PolynomialInterpolators.computeValuePolynomial(PIdx, Arrays.stream(_points).mapToDouble(Point3D::getZ).toArray());
	}

	@Override
	public Point3D 		evaluate(double _t) {
		return Points.of(interpolatorX.evaluate(_t), interpolatorY.evaluate(_t), interpolatorZ.evaluate(_t));
	}

	@Override
	public Point3D[] 	interpolate(int _nbPoints) {
		double    dt      = 1d / (_nbPoints - 1);
		Point3D[] interps = new Point3D[_nbPoints];

		for(int i = 0; i < _nbPoints; ++i) {
			double t   = 1 + (nbPoints - 1) * (i * dt);
			interps[i] = evaluate(t);
		}

		return interps;
	}

}
