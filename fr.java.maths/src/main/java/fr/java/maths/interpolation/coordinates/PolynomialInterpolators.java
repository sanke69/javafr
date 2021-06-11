package fr.java.maths.interpolation.coordinates;

import java.util.stream.IntStream;

import fr.java.math.interpolation.FunctionInterpolator;
import fr.java.math.polynoms.Polynom;
import fr.java.maths.polynoms.RealPolynomial;

public class PolynomialInterpolators {

	public static Polynom.Real 					lagrangeIndex(double _x, double[] _v) {
		Polynom.Real[] polys = new Polynom.Real[_v.length];

		for(int i = 0; i < _v.length; ++i) {
			double a =      1d / (_x - _v[i]);
			double b = - _v[i] / (_x - _v[i]);
			
			polys[i] = new RealPolynomial(b, a);
		}
		
		Polynom.Real L = polys[0];
		for(int i = 1; i < _v.length; ++i)
			L = L.times(polys[i]);

		return L;
	}

	public static Polynom.Real[] 				computeIndexPolynomials(int _size) {
		Polynom.Real[] Lagranges = new Polynom.Real[_size];

		for(int i = 0; i < _size; ++i) {
			final int I     = i;
			double[]  roots = IntStream.range(0, _size+1).filter(k -> k != I).mapToDouble(k -> k+1).toArray();
			Lagranges[i] = lagrangeIndex(I+1, roots);
		}

		return Lagranges;
	}
	public static FunctionInterpolator.OneVar 	computeValuePolynomial(Polynom.Real[] _PIdx, double[] _x) {
		Polynom.Real PX = _PIdx[0].times( _x[0] );

		for(int i = 1; i < _x.length; ++i)
			PX = PX.plus( _PIdx[i].times( _x[i] ) );

		final Polynom.Real FPX = PX;

		return new FunctionInterpolator.OneVar() {
			@Override public void init(double[] _x, double[] _y)  	{ throw new IllegalAccessError(); }
			@Override public double evaluate(double _x) 			{ return FPX.evaluate(_x); }

		};
	}

}
