package fr.java.sdk.math.series;

import fr.java.math.stats.DataSerie;
import fr.java.math.stats.StatSerie;
import fr.java.sdk.math.stats.StatUtils;

public class StatSeries {

	public static StatSerie of(DataSerie _serie) {
		return StatUtils.computeStats(_serie.getValues());
	}

}
