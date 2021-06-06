package fr.java.sdk.math.functions.onedim;

import fr.java.math.functions.MathFunction;

public class HeavysideStepMathFunction implements MathFunction.OneVar,
												  MathFunction.OneVar.WithDerivative,
												  MathFunction.OneVar.WithIntegral {

	double X, A;
	boolean useHalfMaximum = true;

	public HeavysideStepMathFunction() {
		super();
		X = 0d;
		A = 1d;
	}

	@Override
	public double evaluate(double _x) {
		if(_x < X)
			return 0d;
		if(useHalfMaximum && _x == X)
			return A / 2d;
		return A;
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	@Override
	public double derivate(double _x) {
		return 0;
	}

	@Override
	public double integrate(double _x0, double _x1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double derivate(double _x, int _nth) {
		// TODO Auto-generated method stub
		return 0;
	}

}
