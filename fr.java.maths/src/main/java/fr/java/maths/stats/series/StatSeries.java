package fr.java.maths.stats.series;

import fr.java.math.stats.DataSerie;
import fr.java.math.stats.StatSerie;
import fr.java.maths.stats.StatUtils;

public class StatSeries {

	public static StatSerie of(DataSerie _serie) {
		return StatUtils.computeStats(_serie.getValues());
	}

}
