package fr.java.maths.interpolation.functions;

import fr.java.maths.interpolation.FunctionInterpolator;

// TODO:: Must be implemented
// Requiered to limit the degree of Lagrange polynom
public class LagrangeInterpolatorByPart implements FunctionInterpolator.OneVar {
	double[] x, y;

	public LagrangeInterpolatorByPart(double[] _x, double[] _y) {
		super();
		init(_x, _y);
	}

	@Override
	public void 	init(double[] _x, double[] _y) {
		assert(_x.length == _y.length);
		
		x = _x;
		y = _y;
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
