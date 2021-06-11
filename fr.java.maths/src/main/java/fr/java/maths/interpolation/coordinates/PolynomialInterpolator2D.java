package fr.java.maths.interpolation.coordinates;

import java.util.Arrays;
import java.util.List;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.interpolation.CoordinateInterpolator;
import fr.java.math.interpolation.FunctionInterpolator;
import fr.java.math.polynoms.Polynom;
import fr.java.maths.geometry.types.Points;

public class PolynomialInterpolator2D implements CoordinateInterpolator.TwoDims {
	int							nbPoints;
	FunctionInterpolator.OneVar interpolatorX, 
								interpolatorY;

	public PolynomialInterpolator2D(double[] _x, double[] _y) {
		super();

		if(_x.length != _y.length)
			throw new IllegalArgumentException();

		nbPoints = _x.length;

		Polynom.Real[] 
		PIdx          = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		interpolatorX = PolynomialInterpolators.computeValuePolynomial(PIdx, _x);
		interpolatorY = PolynomialInterpolators.computeValuePolynomial(PIdx, _y);
	}
	public PolynomialInterpolator2D(Point2D[] _points) {
		super();

		nbPoints = _points.length;

		Polynom.Real[] 
		PIdx          = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		interpolatorX = PolynomialInterpolators.computeValuePolynomial(PIdx, Arrays.stream(_points).mapToDouble(Point2D::getX).toArray());
		interpolatorY = PolynomialInterpolators.computeValuePolynomial(PIdx, Arrays.stream(_points).mapToDouble(Point2D::getY).toArray());
	}
	public PolynomialInterpolator2D(List<Point2D> _points) {
		super();

		nbPoints = _points.size();

		Polynom.Real[] 
		PIdx          = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		interpolatorX = PolynomialInterpolators.computeValuePolynomial(PIdx, _points.stream().mapToDouble(Point2D::getX).toArray());
		interpolatorY = PolynomialInterpolators.computeValuePolynomial(PIdx, _points.stream().mapToDouble(Point2D::getY).toArray());
	}

	@Override
	public Point2D 		evaluate(double _t) {
		return Points.of(interpolatorX.evaluate(_t), interpolatorY.evaluate(_t));
	}

	public Point2D[] 	interpolate(int _nbPoints) {
		double    dt      = 1d / (_nbPoints - 1);
		Point2D[] interps = new Point2D[_nbPoints];

		for(int i = 0; i < _nbPoints; ++i) {
			double t   = 1 + (nbPoints - 1) * (i * dt);
			interps[i] = evaluate(t);
		}

		return interps;
	}

}
