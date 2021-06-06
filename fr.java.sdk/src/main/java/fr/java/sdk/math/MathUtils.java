package fr.java.sdk.math;

public interface MathUtils {

	public static float hypot(float x, float y) {
		return (float) Math.hypot(x, y);
//		return Math.abs(x) + Math.abs(y);
	}
 
	public static float gaussian(float x, float sigma) {
		return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
	}

}
