package fr.java.maths.stats.laws.discrete;

import fr.java.maths.stats.Distribution;

public abstract class DiscreteDistribution extends Distribution {

	protected DiscreteDistribution() {
		super(Domain.DISCRET);
	}

	public abstract double compute(int _k);

}
