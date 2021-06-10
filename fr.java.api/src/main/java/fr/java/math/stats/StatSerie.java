/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
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
