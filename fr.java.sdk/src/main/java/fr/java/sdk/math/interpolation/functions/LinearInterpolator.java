package fr.java.sdk.math.interpolation.functions;

import fr.java.sdk.math.interpolation.FunctionInterpolator;

public class LinearInterpolator implements FunctionInterpolator.OneVar {
	double[] x, y;

	public LinearInterpolator(double[] _x, double[] _y) {
		super();
		init(_x, _y);
	}
	
	public void 	init(double[] _x, double[] _y) {
		x = _x.clone();
		y = _y.clone();
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

		double h = x[i + 1] - x[i];
		double t = (_x - x[i]) / h;

		return (1d - t) * y[i] + t * y[i + 1];
	}

}
