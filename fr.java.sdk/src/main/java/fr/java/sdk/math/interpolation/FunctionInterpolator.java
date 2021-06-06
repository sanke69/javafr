package fr.java.sdk.math.interpolation;

import fr.java.math.functions.MathFunction;
import fr.java.math.topology.Coordinate;

public interface FunctionInterpolator extends MathFunction {
	
	public interface OneVar extends FunctionInterpolator, MathFunction.OneVar {

		public void   	init(double[] _x, double[] _y);
		public double 	evaluate(double _x);

	}
	public interface TwoVars extends FunctionInterpolator, MathFunction.TwoVars {

		public void   	init(double[] _x1, double[] _x2, double[][] _y);
		public double 	evaluate(double _x1, double _x2);

	}
	public interface ThreeVars extends FunctionInterpolator, MathFunction.ThreeVars {

		public void   	init(double[] _x1, double[] _x2, double[] _x3, double[][][] _y);
		public double 	evaluate(double _x1, double _x2, double _x3);

	}
	public interface PolyVars extends FunctionInterpolator, MathFunction.PolyVars {

		public void   	init(double[][] _xn, double[] _y);
    	public double 	evaluate(double... _xs);

	}

}
