package fr.java.sdk.math.interpolation.functions;

import static fr.java.sdk.lang.Asserts.assertTrue;

import fr.java.sdk.math.interpolation.FunctionInterpolator;

public class LagrangeInterpolator implements FunctionInterpolator.OneVar {
	private double[] x, y;

	public LagrangeInterpolator(double[] _x, double[] _y) {
		super();
		init(_x, _y);
	}

	@Override
	public void 	init(double[] _x, double[] _y) {
		assertTrue(_x.length == _y.length);
		
		x = _x.clone();
		y = _y.clone();
	}

	@Override
	public double 	evaluate(double _x) {
		return L(_x);
	}

	public double 	L(double _X) {
		double t, LX = 0;
		for(int i = 0; i < y.length; i++) {

			t = 1;
			for(int j = 0; j < x.length; j++)
				if(i != j)
					t *=  ((_X - x[j]) / (x[i] - x[j]));

			LX += y[i] * t;
		}

		return LX;
	}

}
