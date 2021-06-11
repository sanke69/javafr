package fr.java.maths.interpolation.functions.splines.cubic;

import fr.java.math.interpolation.FunctionInterpolator;

public class CubicSplineMonotone implements FunctionInterpolator.OneVar {
	private double[]	x;
	private double[]	y;
	private double[]	dydx;

	public CubicSplineMonotone(double[] _x, double[] _y) {
		super();
		init(_x, _y);
	}
	public CubicSplineMonotone(double[] _x, double[] _y, double[] _dydx) {
		super();
		x = _x;
		y = _y;
		dydx = _dydx;
	}

	public void 	init(double[] _x, double[] _y) {
		if(_x == null || _y == null || _x.length != _y.length || _x.length < 2)
			throw new IllegalArgumentException("There must be at least two control points and arrays must be of equal length.");

		int      n = _x.length;
		x = _x.clone();
		y = _y.clone();
		double[] m = new double[n];

		double[] d = new double[n - 1]; // could optimize this out

		// Compute slopes of secant lines between successive points.
		for(int i = 0; i < n - 1; i++)
			d[i] = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);

		// Initialize the tangents as the average of the secants.
		m[0] = d[0];
		for(int i = 1; i < n - 1; i++)
			m[i] = (d[i - 1] + d[i]) * 0.5f;
		m[n - 1] = d[n - 2];

		// Update the tangents to preserve monotonicity.
		for(int i = 0; i < n - 1; i++) {
			if(d[i] == 0f) { // successive Y values are equal
				m[i] = 0f;
				m[i + 1] = 0f;
			} else {
				double a = m[i] / d[i];
				double b = m[i + 1] / d[i];
				double h = (float) Math.hypot(a, b);
				if(h > 9f) {
					double t = 3d / h;
					m[i] = t * a * d[i];
					m[i + 1] = t * b * d[i];
				}
			}
		}
		
		dydx = m;
	}
	public void 	init(double[] _x, double[] _y, double[] _dydx) {
		x    = _x.clone();
		y    = _y.clone();
		dydx = _dydx.clone();
	}

	@Override
	public double 	evaluate(double _x) {
		if(Double.isNaN(_x))
			return _x;

		final int n = x.length;

		if(_x <= x[0])
			return y[0];
		if(_x >= x[n - 1])
			return y[n - 1];

		int i = 0;
		while(_x >= x[i + 1]) {
			i += 1;
			if(_x == x[i])
				return y[i];
		}

		// Perform cubic Hermite spline interpolation.
		double h = x[i + 1] - x[i];
		double t = (_x - x[i]) / h;
		return (y[i] * (1 + 2 * t) + h * dydx[i] * t) * (1 - t) * (1 - t)
				+ (y[i + 1] * (3 - 2 * t) + h * dydx[i + 1] * (t - 1)) * t * t;
	}

}
