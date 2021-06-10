package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;
import fr.java.maths.functions.MathFunctions;

public class PowerMathFunction implements MathFunction.OneVar,
										  MathFunction.OneVar.WithDerivative {

	private MathFunction.OneVar	func;
	private double				a, b;
	private double				power;

	public PowerMathFunction(MathFunction.OneVar func, double power) {
		this(func, 1, 0, power);
	}
	public PowerMathFunction(MathFunction.OneVar func, double a, double b, double power) {
		if(func == null)
			throw new NullPointerException();
		this.func = func;
		this.a = a;
		this.b = b;
		this.power = power;
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

	public double getPower() {
		return power;
	}

	public double evaluate(double x) {
		final double v = func.evaluate(x);
		return Math.pow(a * v + b, power);
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	public double derivate(double x) {
		final double fder = MathFunctions.derivative(func, x);
		return getA() * getPower() * Math.pow(getA() * func.evaluate(x) + getB(), getPower() - 1) * fder;
	}
	@Override
	public double derivate(double _x, int _nth) {
		// TODO Auto-generated method stub
		return 0;
	}

}
