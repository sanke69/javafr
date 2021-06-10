package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;

// (also known as the rectangle function, rect function, Pi function, gate function, unit pulse, or the normalized boxcar function) 
public class RectangularMathFunction implements MathFunction.OneVar,
												MathFunction.OneVar.WithDerivative,
												MathFunction.OneVar.WithIntegral {

	double x0, x1, y;

	public RectangularMathFunction() {
		
	}

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

	@Override
	public double derivate(double _x) {
		// TODO Auto-generated method stub
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

	@Override
	public double evaluate(double _x) {
		// TODO Auto-generated method stub
		return 0;
	}

}
