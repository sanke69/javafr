package fr.java.maths.stats.laws.continuous;

import fr.java.maths.stats.Distribution;

public abstract class ContinuousDistribution extends Distribution {

	protected ContinuousDistribution() {
		super(Domain.CONTINUOUS);
	}

	public abstract double compute(double _x);

}
