package fr.java.maths.stats.laws.continuous;

import java.math.BigInteger;

import fr.java.maths.stats.StatUtils;
import fr.java.maths.stats.laws.discrete.DiscreteDistribution;

public class Poisson extends DiscreteDistribution {
	double lambda;
	
	public Poisson(double _lambda) {
		super();
		lambda = _lambda;
	}

	@Override
	public double compute(int _k) {
		double Lk        = Math.pow(lambda, _k);
		double expK      = Math.exp(lambda);
		BigInteger kfact = StatUtils.factorial((int) _k);
		
		return Lk / kfact.intValue() * expK;
	}

	@Override
	public double getMean() {
		return lambda;
	}

	@Override
	public double getStd() {
		return Math.sqrt( getVar() );
	}

	@Override
	public double getVar() {
		return lambda;
	}

}
