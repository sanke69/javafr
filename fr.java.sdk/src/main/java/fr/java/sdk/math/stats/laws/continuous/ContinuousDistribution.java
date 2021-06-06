package fr.java.sdk.math.stats.laws.continuous;

import fr.java.sdk.math.stats.Distribution;

public abstract class ContinuousDistribution extends Distribution {

	protected ContinuousDistribution() {
		super(Domain.CONTINUOUS);
	}

	public abstract double compute(double _x);

}
