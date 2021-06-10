package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;

public class DiracDeltaMathFunction implements  MathFunction,
												MathFunction.OneVar.WithDerivative,
												MathFunction.OneVar.WithIntegral {

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	@Override
	public double evaluate(double _x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double derivate(double _x) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double derivate(double _x, int _nth) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double integrate(double _x0, double _x1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
