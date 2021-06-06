package fr.java.sdk.math.functions;

import fr.java.math.functions.MathFunction;

public class MathFunctions {

	public static double H = 1e-6;

	private MathFunctions() {
	}

	// For Gauss-Lobatto: nodes Cg and weights Wg
	private static final double[]	Cg	= { 0, 0.17267316464601142812, 0.5, 0.82732683535398857188, 1 };
	private static final double[]	Wg	= { 0.05, 0.27222222222222222222, 0.35555555555555555555, 0.27222222222222222222, 0.05 };

	private static double[] fixBounds(MathFunction.OneVar func, double a, double b, int numIntervals) {
		// For functions which are 0 on parts of [a, b], shorten the interval
		// [a, b] to the non-zero part of f(x). Returns the shortened interval.

		final double h = (b - a) / numIntervals;
		double x = b;
		while((0 == func.evaluate(x)) && (x > a))
			x -= h;
		if(x < b)
			b = x + h;

		x = a;
		while((0 == func.evaluate(x)) && (x < b))
			x += h;
		if(x > a)
			a = x - h;
		double[] D = { a, b };
		return D;
	}

	public static int NUMINTERVALS = 1024;


	public static double derivative(MathFunction.OneVar func, double x) {
		if(func instanceof MathFunction.OneVar.WithDerivative)
			return ((MathFunction.OneVar.WithDerivative) func).derivate(x, 1);
		else
			return finiteCenteredDifferenceDerivative(func, x, H);
	}

	public static double derivative(MathFunction.OneVar func, double x, int n) {
		if(n == 0)
			return func.evaluate(x);
		else if(n == 1)
			return derivative(func, x);
		else if(func instanceof MathFunction.OneVar.WithDerivative)
			return ((MathFunction.OneVar.WithDerivative) func).derivate(x, n);
		else if(n % 2 == 0)
			return finiteCenteredDifferenceDerivative(func, x, n, H);
		else
			return finiteDifferenceDerivative(func, x, n, H);
	}

	public static double finiteDifferenceDerivative(
			MathFunction.OneVar func, double x, int n, double h) {
		if(n < 0)
			throw new IllegalArgumentException("n must not be negative");
		if(n == 0)
			return func.evaluate(x);
		final double err = Math.pow(h, 1.0 / n);
		final double[] values = new double[n + 1];
		for(int i = 0; i < values.length; i++)
			values[i] = func.evaluate(x + i * err);
		for(int j = 0; j < n; j++) {
			for(int i = 0; i < n - j; i++)
				values[i] = values[i + 1] - values[i];
		}
		return values[0] / h;
	}

	public static double finiteCenteredDifferenceDerivative(
			MathFunction.OneVar func, double x, double h) {
		final double fplus = func.evaluate(x + h);
		final double fminus = func.evaluate(x - h);
		return (fplus - fminus) / (2 * h);
	}

	public static double finiteCenteredDifferenceDerivative(
			MathFunction.OneVar func, double x, int n, double h) {
		if(n < 0)
			throw new IllegalArgumentException("n must not be negative");
		if(n == 0)
			return func.evaluate(x);
		if(n % 2 == 1)
			throw new IllegalArgumentException("n must be even");
		final double err = Math.pow(h, 1.0 / n);
		return finiteDifferenceDerivative(func, x - n * err / 2, n, h);
	}

	public static double[][] removeNaNs(double[] x, double[] y) {
		if(x.length != y.length)
			throw new IllegalArgumentException();
		int numNaNs = 0;
		for(int i = 0; i < x.length; i++)
			if(Double.isNaN(x[i]) || Double.isNaN(y[i]))
				++numNaNs;
		if(numNaNs == 0)
			return new double[][] { x, y };
		final double[] nx = new double[x.length - numNaNs];
		final double[] ny = new double[y.length - numNaNs];
		int j = 0;
		for(int i = 0; i < x.length; i++)
			if(!Double.isNaN(x[i]) && !Double.isNaN(y[i])) {
				nx[j] = x[i];
				ny[j++] = y[i];
			}
		return new double[][] { nx, ny };
	}

	public static double integral(MathFunction.OneVar func, double a, double b) {
		if(func instanceof MathFunction.OneVar.WithIntegral)
			return ((MathFunction.OneVar.WithIntegral) func).integrate(a, b);
		else
			return simpsonIntegral(func, a, b, NUMINTERVALS);
	}

	public static double simpsonIntegral(MathFunction.OneVar func, double a,
			double b, int numIntervals) {
		if(numIntervals % 2 != 0)
			throw new IllegalArgumentException("numIntervals must be an even number");
		if(Double.isInfinite(a) || Double.isInfinite(b) ||
				Double.isNaN(a) || Double.isNaN(b))
			throw new IllegalArgumentException("a and b must not be infinite or NaN");
		if(b < a)
			throw new IllegalArgumentException("b < a");
		if(a == b)
			return 0;
		double[] D = fixBounds(func, a, b, numIntervals);
		a = D[0];
		b = D[1];
		final double h = (b - a) / numIntervals;
		final double h2 = 2 * h;
		final int m = numIntervals / 2;
		double sum = 0;
		for(int i = 0; i < m - 1; i++) {
			final double x = a + h + h2 * i;
			sum += 4 * func.evaluate(x) + 2 * func.evaluate(x + h);
		}
		sum += func.evaluate(a) + func.evaluate(b) + 4 * func.evaluate(b - h);
		return sum * h / 3;
	}

	public static double gaussLobatto(MathFunction.OneVar func, double a, double b,
			double tol) {
		if(b < a)
			throw new IllegalArgumentException("b < a");
		if(Double.isInfinite(a) || Double.isInfinite(b) ||
				Double.isNaN(a) || Double.isNaN(b))
			throw new IllegalArgumentException("a or b is infinite or NaN");
		if(a == b)
			return 0;
		double r0 = simpleGaussLob(func, a, b);
		final double h = (b - a) / 2;
		double r1 = simpleGaussLob(func, a, a + h) +
				simpleGaussLob(func, a + h, b);
		if(Math.abs(r0 - r1) <= tol)
			return r1;
		return gaussLobatto(func, a, a + h, tol) +
				gaussLobatto(func, a + h, b, tol);
	}

	private static double simpleGaussLob(MathFunction.OneVar func, double a, double b) {
		// Gauss-Lobatto integral over [a, b] with 5 nodes
		if(a == b)
			return 0;
		final double h = b - a;
		double sum = 0;
		for(int i = 0; i < 5; i++) {
			sum += Wg[i] * func.evaluate(a + h * Cg[i]);
		}
		return h * sum;
	}

	public static double gaussLobatto(MathFunction.OneVar func, double a, double b,
			double tol, double[][] T) {
		if(b < a)
			throw new IllegalArgumentException("b < a");
		if(a == b) {
			T[0] = new double[1];
			T[1] = new double[1];
			T[0][0] = a;
			T[1][0] = 0;
			return 0;
		}

		int n = 1;         // initial capacity
		T[0] = new double[n];
		T[1] = new double[n];
		T[0][0] = a;
		T[1][0] = 0;
		int[] s = { 0 };    // actual number of intervals
		double res = innerGaussLob(func, a, b, tol, T, s);
		n = s[0] + 1;
		double[] temp = new double[n];
		System.arraycopy(T[0], 0, temp, 0, n);
		T[0] = temp;
		temp = new double[n];
		System.arraycopy(T[1], 0, temp, 0, n);
		T[1] = temp;
		return res;
	}

	private static double innerGaussLob(MathFunction.OneVar func, double a, double b,
			double tol, double[][] T, int[] s) {
		double r0 = simpleGaussLob(func, a, b);
		final double h = (b - a) / 2;
		double r1 = simpleGaussLob(func, a, a + h) +
				simpleGaussLob(func, a + h, b);
		if(Math.abs(r0 - r1) <= tol) {
			++s[0];
			int len = s[0];
			if(len >= T[0].length) {
				double[] temp = new double[2 * len];
				System.arraycopy(T[0], 0, temp, 0, len);
				T[0] = temp;
				temp = new double[2 * len];
				System.arraycopy(T[1], 0, temp, 0, len);
				T[1] = temp;
			}
			T[0][len] = b;
			T[1][len] = r1;
			return r1;
		}

		return innerGaussLob(func, a, a + h, tol, T, s) +
				innerGaussLob(func, a + h, b, tol, T, s);
	}

}
