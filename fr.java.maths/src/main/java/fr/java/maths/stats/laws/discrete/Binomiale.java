package fr.java.maths.stats.laws.discrete;

import java.math.BigInteger;

import fr.java.maths.stats.StatUtils;

public class Binomiale extends DiscreteDistribution {
	int    N;		// numberOfTrials
	double p;		// probabilityOfSuccess
	
	public Binomiale(int _N, double _p) {
		super();
		N = _N;
		p = _p;
	}
	
	public int numberOfTrials() {
		return N;
	}
	public double probabilityOfSuccess() {
		return p;
	}

	@Override
	public double compute(int _k) {
		BigInteger nCk = StatUtils.combination(N, (int) _k);
		double pk   = Math.pow(p, _k);
		double qNmk = Math.pow(1.0 - p, N - _k);
		return nCk.intValue() * pk * qNmk;
	}

	@Override
	public double getMean() {
		return N * p;
	}

	@Override
	public double getStd() {
		return Math.sqrt( getVar() );
	}

	@Override
	public double getVar() {
		return N * p * (1.0 - p);
	}

}
