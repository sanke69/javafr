package fr.java.sdk.math.functions.onedim;

import fr.java.math.functions.MathFunction;
import fr.java.sdk.math.functions.MathFunctions;

public class AverageMathFunction implements MathFunction.OneVar,
											MathFunction.OneVar.WithDerivative,
											MathFunction.OneVar.WithIntegral {

	private MathFunction.OneVar[] func;

	public AverageMathFunction(MathFunction.OneVar... func) {
		if(func == null)
			throw new NullPointerException();
		this.func = func.clone();
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	public MathFunction.OneVar[] 	getFunctions() {
		return func.clone();
	}

	@Override
	public double 					evaluate(double x) {
		double sum = 0;
		for(final MathFunction.OneVar fi : func)
			sum += fi.evaluate(x);
		return sum / func.length;
	}

	@Override
	public double 					derivate(double x) {
		double sum = 0;
		for(final MathFunction.OneVar fi : func)
			sum += MathFunctions.derivative(fi, x);
		return sum / func.length;
	}
	@Override
	public double 					derivate(double x, int n) {
		if(n < 0)
			throw new IllegalArgumentException("n must be greater than or equal to 0");
		if(n == 0)
			return evaluate(x);
		double sum = 0;
		for(final MathFunction.OneVar fi : func)
			sum += MathFunctions.derivative(fi, x, n);
		return sum / func.length;
	}

	@Override
	public double 					integrate(double a, double b) {
		double sum = 0;
		for(final MathFunction.OneVar fi : func)
			sum += MathFunctions.integral(fi, a, b);
		return sum / func.length;
	}

}
