package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;

public class SqrtMathFunction implements MathFunction.OneVar {
	private MathFunction.OneVar func;

	public SqrtMathFunction(MathFunction.OneVar func) {
		super();
		if(func == null)
			throw new NullPointerException();
		this.func = func;
	}

	public MathFunction.OneVar getFunction() {
		return func;
	}

	public double evaluate(double x) {
		return Math.sqrt(func.evaluate(x));
	}

}
