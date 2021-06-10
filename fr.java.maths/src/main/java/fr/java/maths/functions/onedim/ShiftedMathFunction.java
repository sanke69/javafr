package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;
import fr.java.maths.functions.MathFunctions;

public class ShiftedMathFunction implements MathFunction.OneVar,
											MathFunction.OneVar.WithDerivative,
											MathFunction.OneVar.WithIntegral {
	MathFunction.OneVar	func;
	double				delta;

	public ShiftedMathFunction(MathFunction.OneVar func, double delta) {
		if(func == null)
			throw new NullPointerException();
		this.func = func;
		this.delta = delta;
	}

	public MathFunction getFunction() {
		return func;
	}

	public double getDelta() {
		return delta;
	}

	public double evaluate(double x) {
		return func.evaluate(x) - delta;
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	public double derivate(double x) {
		return MathFunctions.derivative(func, x);
	}

	public double derivate(double x, int _nth) {
		return MathFunctions.derivative(func, x, _nth);
	}

	public double integrate(double a, double b) {
		return MathFunctions.integral(func, a, b) - (b - a) * getDelta();
	}
}
