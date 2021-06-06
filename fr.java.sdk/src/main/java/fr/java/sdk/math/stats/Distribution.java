package fr.java.sdk.math.stats;

public abstract class Distribution {
	public enum Domain { DISCRET, CONTINUOUS };

	Domain domain;

	protected Distribution(Domain _domain) {
		domain = _domain;
	}

	public abstract double getMean();
	public abstract double getStd();
	public abstract double getVar();

	/**
	 * Densité de fonction
	 * 	F_X(x) = P(X <= x)
	 */
//	public abstract String getPDFFormula();		// Probability Density Function
//	public abstract double getPDF();			// Probability Density Function

	/**
	 * Fonction de répartition
	 * 	F_X(x) = P(X <= x)
	 */
//	public abstract String getCDFFormula();		// Cumulative Distribution Function
//	public abstract double getCDF();			// Cumulative Distribution Function

}
