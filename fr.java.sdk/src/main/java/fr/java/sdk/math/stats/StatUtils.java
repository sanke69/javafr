package fr.java.sdk.math.stats;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import fr.java.math.stats.StatSerie;
import fr.java.math.stats.StatSerie.WhiskerBox;
import fr.java.sdk.math.stats.laws.discrete.Bernoulli;
import fr.java.sdk.math.stats.laws.discrete.Binomiale;

public class StatUtils {
	static HashMap<Integer, BigInteger>	cacheCombination	= new HashMap<Integer, BigInteger>();

	public static BigInteger factorial_no_cache(int n) {
        BigInteger ret = BigInteger.ONE;
        for(int i = 1; i <= n; ++i) ret = ret.multiply(BigInteger.valueOf(i));
        return ret;
    }

	static HashMap<Integer, BigInteger>	cacheFactorial		= new HashMap<Integer, BigInteger>();
	public static BigInteger factorial(int n) {
		BigInteger ret;

		if(n == 0) return BigInteger.ONE;
		if((ret = cacheFactorial.get(n)) != null) return ret;
		
		ret = BigInteger.valueOf(n).multiply(factorial(n - 1));
		cacheFactorial.put(n, ret);
		
		return ret;
	}

	public static BigInteger combination(int _N, int _k) {
		BigInteger Nfact   = factorial(_N);
		BigInteger Kfact   = factorial(_k);
		BigInteger NmKfact = factorial(_N - _k);

		return Nfact.divide(Kfact.multiply(NmKfact));
	}
/*
	public static int[] pascalCoefficient(int _order) {
		for(int n = 0; n < 10; n++) {
			int nCk = 1;
			for(int k = 0; k <= n; k++) {
				System.out.print(nCk + " ");
				nCk = nCk * (n - k) / (k + 1);
			}
			System.out.println();
		}
	}
*/
	public static void main(String[] args) {
		for(int i = 0; i < 10; ++i)
			System.out.println(i + "!=" + factorial(i));

		for(int n = 0; n < 10; ++n)
			for(int k = 0; k < 10; ++k)
				if(n >= k)
					System.out.println("C(" + "n=" + n + ", k=" + k + ")=" + combination(n, k));

		Bernoulli b = new Bernoulli(0.25);
		Binomiale B = new Binomiale(10, 0.05);

		System.out.println("Bernoulli(" + b.probabilityOfSuccess() + ")");
		System.out.println("P(X==0) = " + b.compute(0));
		System.out.println("P(X==1) = " + b.compute(1));

		System.out.println("Binomiale(" + B.numberOfTrials() + ", " + B.probabilityOfSuccess() + ")");
		System.out.println("P(X==0) = " + B.compute(0));
		System.out.println("P(X==1) = " + B.compute(1));
		System.out.println("P(X==10) = " + B.compute(10));
		
	}

	public static double 		min(final double[] values) {
		double min = values[0];

		for(int i = 1; i < values.length; ++i)
			if( min > values[i] )
				min = values[i];

		return min;
	}
	public static double 		max(final double[] values) {
		double max = values[0];

		for(int i = 1; i < values.length; ++i)
			if( max < values[i] )
				max = values[i];

		return max;
	}

	public static double 		sum(final double[] values) {
		double sum = 0;

		for(int i = 0; i < values.length; ++i)
			sum += values[i];

		return sum;
	}

	public static double 		mean(final double[] values) {
		return sum(values) / values.length;
	}

	public static double 		median(final double[] values) {
		double[] copy = values.clone();
		Arrays.sort(copy);
		
		int i = copy.length / 2;

		return copy[i];
	}

	public static double 		var(final double[] values) {
        double mean = mean(values);
		double var  = 0;

		for(int i = 0; i < values.length; ++i) {
			double d = values[i];
			var += (d-mean) * (d-mean);
		}

		return var / (values.length - 1);
	}

	public static double 		std(final double[] values) {
		return Math.sqrt(var(values));
	}

	public static double 		quartile(double[] values, double _quartile) {
		double[] copy = values.clone();
		Arrays.sort(copy);

		int quartileIndex      = (int) (values.length * _quartile);
		
		return copy[quartileIndex];
	}

	
	public static StatSerie computeStats(final double[] _values) {
		double mean       = 0.0d, min = 1e99, max = -1e99;
		double squaredSum = 0.0d;
		for(int i = 0; i < _values.length; ++i) {
			mean        += _values[i];
			squaredSum  += _values[i] * _values[i];

			if(_values[i] < min) min = _values[i];
			if(_values[i] > max) max = _values[i];
		}
		mean       /= (_values.length - 1);
		squaredSum /= (_values.length - 1);

		double variance     = squaredSum - (mean * mean);
		double stdDeviation = Math.sqrt(variance);

		double[] copy = _values.clone();
		Arrays.sort(copy);

		int    medianIndex        = (int) (_values.length * 0.5d);
		int    lowerQuartileIndex = (int) (_values.length * 0.25d);
		int    upperQuartileIndex = (int) (_values.length * 0.75d);
		int    lowerWhiskerIndex  = (int) (_values.length * 0.05d);
		int    upperWhiskerIndex  = (int) (_values.length * 0.95d);

		double   median        = _values[medianIndex];
		double   lowerQuartile = _values[lowerQuartileIndex];
		double   upperQuartile = _values[upperQuartileIndex];
		double   lowerWhisker  = _values[lowerWhiskerIndex];
		double   upperWhisker  = _values[upperWhiskerIndex];
		double[] lowerOutliers = null;
		double[] upperOutliers = null;

		int numberOfLowerOutliers = (lowerWhiskerIndex - 1);
		int numberOfUpperOutliers = (_values.length - upperWhiskerIndex);

		lowerOutliers = new double[numberOfLowerOutliers];
		upperOutliers = new double[numberOfUpperOutliers];

		for (int i = 0; i < lowerWhiskerIndex; i++)
			lowerOutliers[i] = _values[i];
		for (int i = upperWhiskerIndex + 1; i < _values.length; i++)
			upperOutliers[i] = _values[i];

		final double   MEAN      = mean;
		final double   MEDIAN    = median;
		final double   STD       = stdDeviation;
		final double   VAR       = variance;
		final double   MIN       = min;
		final double   MAX       = max;
		final double   LOW_Q     = lowerQuartile;
		final double   HIGH_Q    = upperQuartile;
		final double   LOW_W     = lowerWhisker;
		final double   HIGH_W    = upperWhisker;
		final double[] LOW_OUTS  = lowerOutliers.clone();
		final double[] HIGH_OUTS = upperOutliers.clone();
		
		final WhiskerBox wbox = new WhiskerBox() {

			@Override public double getMinValue() 			{ return MIN; }
			@Override public double getMaxValue() 			{ return MAX; }

			@Override public double getLowerQuartile() 		{ return LOW_Q; }
			@Override public double getLowerQuartile_IQR() 	{ return LOW_W; }

			@Override public double getHigherQuartile() 	{ return HIGH_Q; }
			@Override public double getHigherQuartile_IQR() { return HIGH_W; }

			@Override public double[] getLowerOutliers() 	{ return LOW_OUTS; }
			@Override public double[] getHigherOutliers() 	{ return HIGH_OUTS; }
			
		};
		final StatSerie stats = new StatSerie() {
			@Override public int                  valueCount()       { return 0; }
			@Override public double[]             getValues()        { return null; }
			@Override public double               getValue(int _idx) { return 0; }

			@Override public double               getMean()          { return MEAN; }
			@Override public double               getMedian()        { return MEDIAN; }
			@Override public double               getStd()           { return STD; }
			@Override public double               getVar()           { return VAR; }
			@Override public double               getMin()           { return MIN; }
			@Override public double               getMax()           { return MAX; }

			@Override public Optional<WhiskerBox> getWhiskerBox()    { return Optional.of(wbox); }

		};

		return stats;
	}

}
