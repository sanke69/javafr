package fr.java.sdk.math.stats.laws.discrete;

import fr.java.sdk.math.stats.Distribution;

public abstract class DiscreteDistribution extends Distribution {

	protected DiscreteDistribution() {
		super(Domain.DISCRET);
	}

	public abstract double compute(int _k);

}
