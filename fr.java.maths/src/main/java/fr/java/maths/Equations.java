package fr.java.maths;

public class Equations {

	public static double[] solve2nd(double _a, double _b, double _c) {
		double delta = _b*_b - 4 * _a * _c;

		if(delta < 0)
			return null;
		if(delta == 0)
			return new double[] { - _b / (2 * _a) };
		else
			return new double[] { - (_b - Math.sqrt(delta)) / (2 * _a), - (_b + Math.sqrt(delta)) / (2 * _a) };
	}
	
}
