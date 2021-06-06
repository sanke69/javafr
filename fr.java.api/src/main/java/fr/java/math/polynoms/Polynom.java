package fr.java.math.polynoms;

import java.io.Serializable;

import fr.java.math.functions.MathFunction;

public interface Polynom {

	public interface Real extends 	Polynom, Serializable, Cloneable,
									MathFunction.OneVar.WithDerivative,
									MathFunction.OneVar.WithIntegral {
	
		public double[] 						getCoeffs();
		public double 							getCoeff(int _degree);

		public double 							evaluate(double _x);
		public double 							evaluate(double _x, int _nthDerivative);

		public Polynom.Real 					plus(double _d);
		public Polynom.Real 					plus(Polynom.Real _p);
//		public Polynomial.Real 					plusEquals(Polynomial.Real _p);

		public Polynom.Real 					minus(double _d);
		public Polynom.Real 					minus(Polynom.Real _p);
//		public Polynomial.Real 					minusEquals(Polynomial.Real _p);

		public Polynom.Real 					times(double _d);
		public Polynom.Real 					times(Polynom.Real _p);
//		public Polynomial.Real 					timesEquals(Polynomial.Real _p);

		public Polynom.Real 					divides(double _d);
	}

	public interface Complex extends Polynom {
	
		public fr.java.math.numbers.Complex[] 	getCoeffs();
		public fr.java.math.numbers.Complex 	getCoeff(int _degree);
	
		public fr.java.math.numbers.Complex 	evaluate(fr.java.math.numbers.Complex _x);

		public Polynom.Complex 				plus(Polynom.Real b);
		public Polynom.Complex 				plus(Polynom.Complex b);
//		public Polynomial.Complex 				plusEquals(Polynomial.Complex b);

		public Polynom.Complex 				minus(Polynom.Real b);
		public Polynom.Complex 				minus(Polynom.Complex b);
//		public Polynomial.Complex 				minusEquals(Polynomial.Complex b);

		public Polynom.Complex 				times(Polynom.Real b);
		public Polynom.Complex 				times(Polynom.Complex b);
//		public Polynomial.Complex 				timesEquals(Polynomial.Complex b);

	}

	public int 									getDegree();

}
