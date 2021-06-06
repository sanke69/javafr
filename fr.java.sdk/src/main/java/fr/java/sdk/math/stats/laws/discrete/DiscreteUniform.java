package fr.java.sdk.math.stats.laws.discrete;

import fr.java.sdk.math.stats.laws.discrete.DiscreteDistribution;

public class DiscreteUniform extends DiscreteDistribution {
	double a, b;
	
	public DiscreteUniform(double _a, double _b) {
		super();
		a = _a;
		b = _b;
	}

	public double lowerBound() { return a; }
	public double upperBound() { return b; }

	@Override
	public double compute(int _k) {
		return 0;
	}

	@Override
	public double getMean() {
		return (a+b)/2;
	}

	@Override
	public double getStd() {
		return Math.sqrt( getVar() );
	}

	@Override
	public double getVar() {
		return (b-a)*(b-a)/12;
	}

//	@Override
	public String getPDFFormula() {
		String formula = "\\frac{1}{\\sigma \\sqrt{2 \\pi} . \\exp{-\\frac{1}{2} ( \\frac{x-\\mu}{\\sigma} )^2";
				
		return "f(x) = " + formula;
	}

//	@Override
	public String getCDFFormula() {
		String formula = "\\frac{1}{\\sigma \\sqrt{2 \\pi} . \\exp{-\\frac{1}{2} ( \\frac{x-\\mu}{\\sigma} )^2";
				
		return "f(x) = " + formula;
	}

}
