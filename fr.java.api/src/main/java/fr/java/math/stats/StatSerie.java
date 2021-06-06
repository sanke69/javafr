package fr.java.math.stats;

import java.util.Optional;

public interface StatSerie extends DataSerie {
	
	public static interface WhiskerBox {

		public double 	getMinValue();
		public double 	getMaxValue();

		public double 	getLowerQuartile();
		public double 	getLowerQuartile_IQR();		// IQR - InterQuartile Range
		public double 	getHigherQuartile();
		public double 	getHigherQuartile_IQR();

		public double[] getLowerOutliers();
		public double[] getHigherOutliers();

		/*
	    the minimum and maximum of all of the data[1] (as in figure 2)
	    the lowest datum still within 1.5 IQR of the lower quartile, and the highest datum still within 1.5 IQR of the upper quartile (often called the Tukey boxplot)[2][3][4] (as in figure 3)
	    one standard deviation above and below the mean of the data
	    the 9th percentile and the 91st percentile
	    the 2nd percentile and the 98th percentile.
	    */
	}

	public double 	            getMin();
	public double 	            getMax();

	public double   			getMedian();
	public double   			getMean();

	public double   			getStd();
	public double   			getVar();

	public Optional<WhiskerBox> getWhiskerBox();

}
