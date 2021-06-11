package fr.java.maths.polynoms;

import fr.java.math.polynoms.Polynom;

public class RealPolynomial implements Polynom.Real {
	private static final long serialVersionUID = 4472325074646481009L;

	// P(x) = (x - r0) * (x - r1) ...
	public static Polynom.Real fromRoots(double... roots) {
		if(roots == null)
			return null;

		int pdeg = roots.length;

		double[] rootCoeff = new double[2];
		rootCoeff[0] = -roots[0];
		rootCoeff[1] = 1.0;
		Polynom.Real rPoly = new RealPolynomial(rootCoeff);
		for(int i = 1; i < pdeg; i++) {
			rootCoeff[0] = -roots[i];
			Polynom.Real cRoot = new RealPolynomial(rootCoeff);
			rPoly = rPoly.times(cRoot);
		}
		return rPoly;
	}
	// P(x) = C0 + C1 * X + C2 * X²  + C3 * X³ + ...
	public static Polynom.Real fromCoefs(double... coeffs) {
		return new RealPolynomial(coeffs);
	}

	/**
	 * Solve 2nd degree equation
	 * 
	 * @param _a
	 * @param _b
	 * @param _c
	 * @return
	 */
	public static double[]     getRoots(double _a, double _b, double _c) {
		double delta = _b*_b - 4 * _a * _c;

		if(delta < 0)
			return null;
		if(delta == 0)
			return new double[] { - _b / (2 * _a) };
		else
			return new double[] { - (_b - Math.sqrt(delta)) / (2 * _a), - (_b + Math.sqrt(delta)) / (2 * _a) };
	}

	int			degree;
	double[]	coeffs;	// ordered by degree of X

	public RealPolynomial(int _degree) {
		super();

		degree   = _degree;
		coeffs    = new double[_degree + 1];
	}
	/**
	 * return P(X) = a
	 */
	public RealPolynomial(double _a) {
		super();

		degree   = 0;
		coeffs    = new double[1];
		coeffs[0] = _a;
	}
	/**
	 * return P(X) = a + b . X
	 */
	public RealPolynomial(double _a, double _b) {
		super();

		degree   = 1;
		coeffs    = new double[2];
		coeffs[0] = _a;
		coeffs[1] = _b;
	}
	/**
	 * return P(X) = a + b . X + c . X^2
	 */
	public RealPolynomial(double _a, double _b, double _c) {
		super();

		degree    = 2;
		coeffs    = new double[3];
		coeffs[0] = _a;
		coeffs[1] = _b;
		coeffs[2] = _c;
	}
	/**
	 * return P(X) = a + b . X + c . X^2 + d . X^3
	 */
	public RealPolynomial(double _a, double _b, double _c, double _d) {
		super();

		degree   = 3;
		coeffs    = new double[4];
		coeffs[0] = _a;
		coeffs[1] = _b;
		coeffs[2] = _c;
		coeffs[3] = _d;
	}
	/**
	 * return P(x) = C0 + C1 * X + C2 * X²  + C3 * X³ + ...
	**/
	public RealPolynomial(double[] _coefs) {
		super();

		degree   = _coefs.length - 1;
		coeffs    = new double[degree + 1];
		for(int i = 0; i <= degree; i++)
			coeffs[i] = _coefs[i];
		
		compact();
	}

	public int 				getDegree() {
		return degree;
	}

	public double[] 		getCoeffs() {
		return coeffs;
	}
	public double 			getCoeff(int _degree) {
		return coeffs[_degree];
	}

	@Override
	public int 				getDerivativeDegree() {
		return degree - 1;
	}

	public Polynom.Real		getDerivative(int _nth) {
		assert(_nth < 0);

		if(_nth == 0)
			return this;

		if(_nth > degree)
			return new RealPolynomial(0.0);

		double[] nc = new double[degree - _nth + 1];

		int k = degree - _nth;
		for(int i = degree; i > _nth - 1; --i) {
			nc[k] = coeffs[i];
			for(int j = 0; j < _nth; --j)
				nc[k] = nc[k] * (i - j);
			k--;
		}

		return new RealPolynomial(nc);
	}
	public Polynom.Real 	getIntegral(double c) {
		final double[] coeffInt = new double[coeffs.length + 1];
		coeffInt[0] = c;
		for(int i = 0; i < coeffs.length; i++)
			coeffInt[i + 1] = coeffs[i] / (i + 1);
		return new RealPolynomial(coeffInt);
	}
/*
	public Polynomial.Real	derivativePolynomial(int n) {
		if(n < 0)
			throw new IllegalArgumentException("n < 0");
		if(n == 0)
			return this;
		if(n >= coeffs.length)
			return new RealPolynomial(0d);
		final double[] coeffDer = new double[coeffs.length - n];
		for(int i = coeffs.length - 1; i >= n; i--)
			coeffDer[i - n] = getCoeffDerivative(i, n);
		return new RealPolynomial(coeffDer);
	}
*/
	protected double 		getCoeffDerivative(int _i, int _nth) {
		double coeffDer = coeffs[_i];
		for(int j = _i; j > _i - _nth; j--)
			coeffDer *= j;
		return coeffDer;
	}

	public double 			evaluate(double _x) {
		if(degree == 0)
			return coeffs[0];
	
		double y = coeffs[degree];
		for(int i = degree - 1; i >= 0; i--)
			y = coeffs[i] + (y * _x);

		return y;
	}
	public double 			evaluate(double _x, int _nthDerivative) {
		return getDerivative(_nthDerivative).evaluate(_x);
	}

	public double 			derivate(double x) {
		return derivate(x, 1);
	}
	public double 			derivate(double x, int _nth) {
		if(_nth < 0)
			throw new IllegalArgumentException("n < 0");
		if(_nth == 0)
			return evaluate(x);
		if(_nth >= coeffs.length)
			return 0;
		double res = getCoeffDerivative(coeffs.length - 1, _nth);
		for(int i = coeffs.length - 2; i >= _nth; i--)
			res = getCoeffDerivative(i, _nth) + x * res;
		return res;
	}
	public double 			integrate(double a, double b) {
		return integralA0(b) - integralA0(a);
	}

	public Polynom.Real 	plus(double _d) {
		RealPolynomial c = clone();

		c.coeffs[0] += _d;

		return c.compact();
	}
	public Polynom.Real 	plus(Polynom.Real _p) {
		RealPolynomial c = null;

		if(_p.getDegree() <= degree) {

			c = new RealPolynomial(degree);
			for(int i = 0; i <= _p.getDegree(); ++i)
				c.coeffs[i] = coeffs[i] + _p.getCoeff(i);
			for(int i = _p.getDegree() + 1; i <= degree; ++i)
				c.coeffs[i] = coeffs[i];

		} else {

			c = new RealPolynomial(_p.getDegree());
			for(int i = 0; i <= degree; ++i)
				c.coeffs[i] = coeffs[i] + _p.getCoeff(i);
			for(int i = degree + 1; i <= _p.getDegree(); ++i)
				c.coeffs[i] = _p.getCoeff(i);

		}

		return c.compact();
	}

	public Polynom.Real 	minus(double _d) {
		RealPolynomial c = clone();

		c.coeffs[0] -= _d;

		return c.compact();
	}
	public Polynom.Real 	minus(Polynom.Real _p){
		RealPolynomial c = null;

		if(_p.getDegree() <= degree) {

			c = new RealPolynomial(degree);
			for(int i = 0; i <= _p.getDegree(); ++i)
				c.coeffs[i] = coeffs[i] - _p.getCoeff(i);
			for(int i = _p.getDegree() + 1; i <= degree; ++i)
				c.coeffs[i] = coeffs[i];

		} else {
			c = new RealPolynomial(_p.getDegree());

			for(int i = 0; i <= degree; ++i)
				c.coeffs[i] = coeffs[i] - _p.getCoeff(i);
			for(int i = degree + 1; i <= _p.getDegree(); ++i)
				c.coeffs[i] = - _p.getCoeff(i);

		}

		return c.compact();
	}


	public Polynom.Real 	times(double _d) {
		RealPolynomial c = this.clone();

		for(int i = 0; i <= degree; i++)
				c.coeffs[i] *= _d;

		return c.compact();
	}
	public Polynom.Real 	times(Polynom.Real _p) {
		int n = degree + _p.getDegree();

		RealPolynomial c = new RealPolynomial(n);
		for(int i = 0; i <= degree; i++)
			for(int j = 0; j <= _p.getDegree(); j++)
				c.coeffs[i + j] += coeffs[i] * _p.getCoeff(j);

		return c.compact();
	}
	
	public Polynom.Real 	compose(Polynom.Real _g) {
		Polynom.Real poly = new RealPolynomial(0);
        for(int i = degree; i >= 0; i--) {
        	RealPolynomial term = new RealPolynomial(coeffs[i]);
            poly = term.plus(_g.times(poly));
        }
        return poly;
    }

	public Polynom.Real 	divides(double _d) {
		RealPolynomial c = this.clone();

		for(int i = 0; i <= degree; i++)
				c.coeffs[i] /= _d;

		return c.compact();
	}


	public RealPolynomial 	clone() {
		return new RealPolynomial(coeffs);
	}

	public String 			toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = coeffs.length-1; i >= 1; --i)
			sb.append(coeffs[i] + " * " + "X^" + i + " + ");
		sb.append(coeffs[0]);
		
		return sb.toString();
	}

	private Polynom.Real 	compact() {
		int nonNullDegree = degree;
		while(nonNullDegree > 0 && coeffs[nonNullDegree] == 0)
			nonNullDegree--;

		if(nonNullDegree == degree)
			return this;

		double[] newCoeffs = new double[nonNullDegree + 1];
		for(int i = 0; i <= nonNullDegree; ++i)
			newCoeffs[i] = coeffs[i];

		degree = nonNullDegree;
		coeffs = newCoeffs;

		return this;
	}
	
	private double 			integralA0(double u) {
		final int n = coeffs.length - 1;
		double res = u * coeffs[n] / (n + 1);
		for(int i = coeffs.length - 2; i >= 0; i--)
			res = coeffs[i] * u / (i + 1) + u * res;
		return res;
	}

}
