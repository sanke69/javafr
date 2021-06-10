package fr.java.maths.stats.laws.continuous;

import fr.java.maths.stats.laws.continuous.ContinuousDistribution;

public class Gaussian extends ContinuousDistribution {
	double mean, std;
	
	public Gaussian(double _mean, double _std) {
		super();
		mean = _mean;
		std  = _std;
	}

	@Override
	public double compute(double _x) {
		return mean;
	}

	@Override
	public double getMean() {
		return mean;
	}

	@Override
	public double getStd() {
		return std;
	}

	@Override
	public double getVar() {
		return std*std;
	}

//	@Override
	public String getPDFFormula() {
		String formula = "\\frac{1}{\\sigma \\sqrt{2 \\pi} . \\exp{-\\frac{1}{2} ( \\frac{x-\\mu}{\\sigma} )^2";
				
		return "f(x) = " + formula;
	}

}
