package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;
import fr.java.maths.functions.MathFunctions;

public class SquareMathFunction implements MathFunction.OneVar.WithDerivative {
	private MathFunction.OneVar	func;
	private double				a, b;

	public SquareMathFunction(MathFunction.OneVar func) {
		this(func, 1, 0);
	}

	public SquareMathFunction(MathFunction.OneVar _func, double _a, double _b) {
		if(func == null)
			throw new NullPointerException();
		func = _func;
		a    = _a;
		b    = _b;
	}

	public MathFunction getFunction() {
		return func;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double evaluate(double x) {
		final double v = a * func.evaluate(x) + b;
		return v * v;
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	public double derivate(double x) {
		final double fder = MathFunctions.derivative(func, x);
		return 2 * a * (a * func.evaluate(x) + b) * fder;
	}

	@Override
	public double derivate(double _x, int _nth) {
		if(_nth > 1)
			throw new IllegalArgumentException();
		return 0;
	}

}
