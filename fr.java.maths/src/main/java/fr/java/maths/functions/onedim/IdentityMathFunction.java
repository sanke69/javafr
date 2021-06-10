package fr.java.maths.functions.onedim;

import fr.java.math.functions.MathFunction;

public class IdentityMathFunction implements  MathFunction,
										      MathFunction.OneVar.WithDerivative,
										      MathFunction.OneVar.WithIntegral {

   public double evaluate (double x) {
      return x;
   }

	@Override
	public int 						getDerivativeDegree() {
		return 1;
	}

   public double derivate (double x) {
      return 1;
   }
   public double derivate (double x, int n) {
      return n > 1 ? 0 : 1;
   }

   public double integrate (double a, double b) {
      return (b*b - a*a) / 2;
   }
}
