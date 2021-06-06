package fr.java.sdk.math.stats.laws.discrete;

import fr.java.sdk.math.stats.laws.discrete.DiscreteDistribution;

public class Bernoulli extends DiscreteDistribution {
	double p;		// probabilityOfSuccess
	
	public Bernoulli(double _p) {
		super();
		p = _p;
	}

	public double probabilityOfSuccess() { return p; }

	@Override
	public double compute(int _k) {
		if(_k == 0.0)
			return 1.0 - p;
		if(_k == 1.0)
			return p;
		return 0;
	}

	@Override
	public double getMean() {
		return p;
	}

	@Override
	public double getStd() {
		return Math.sqrt( getVar() );
	}

	@Override
	public double getVar() {
		return p * (1.0 - p);
	}

}
