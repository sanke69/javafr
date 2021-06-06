package fr.java.math.functions;

public interface MathFunction {

	public static interface OneVar    extends MathFunction {
		public double evaluate(double _x);

		public interface WithDerivative extends MathFunction.OneVar {
			public int 		      getDerivativeDegree();

			public default double derivate(double _x) { return derivate(_x, 1); } 
			public double 		  derivate(double _x, int _nth);
		}

		public interface WithIntegral extends MathFunction.OneVar {
		   public double integrate(double _x0, double _x1);
		}

	}
	public static interface TwoVars   extends MathFunction {
		public double evaluate(double _x1, double _x2);
	}
	public static interface ThreeVars extends MathFunction {
		public double evaluate(double _x1, double _x2, double _x3);
	}
	public static interface FourVars  extends MathFunction {
		public double evaluate(double _x1, double _x2, double _x3);
	}
	public static interface PolyVars  extends MathFunction {
		public double evaluate(double... _xs);
	}

}
