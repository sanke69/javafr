package fr.java.sdk.math.polynoms;

import fr.java.math.numbers.Complex;
import fr.java.math.polynoms.Polynom;
import fr.java.sdk.math.Numbers;
import fr.java.sdk.math.numbers.complex.ComplexNumber;

public class ComplexPolynomial {

	public static ComplexPolynomial rootsToPoly(Complex[] roots) {
		if(roots == null)
			return null;

		int pdeg = roots.length;

		Complex[] rootCoeff = new Complex[2];
		rootCoeff[0] = roots[0].times(new ComplexNumber(-1));
		rootCoeff[1] = new ComplexNumber(1);
		
		ComplexPolynomial rPoly = new ComplexPolynomial(rootCoeff);
		for(int i = 1; i < pdeg; i++) {
			rootCoeff[0] = roots[i].times(new ComplexNumber(-1));
			ComplexPolynomial cRoot = new ComplexPolynomial(rootCoeff);
			rPoly = rPoly.times(cRoot);
		}

		return rPoly;
	}

	private int			deg		= 0;            // Degree of the polynomial
	private Complex[]	coeff;        			// Array of polynomial coefficients

	private int			degwz	= 0;          	// Degree of the polynomial with zero roots removed
	private Complex[]	coeffwz;      			// Array of polynomial coefficients with zero roots removed

	public ComplexPolynomial(int _degree) {
		super();

		deg      = _degree;
		coeff    = new Complex[_degree + 1];
	}
	public ComplexPolynomial(double _a) {
		super();

		deg      = 0;
		coeff    = new Complex[1];
		coeff[0] = new ComplexNumber(_a);
	}
	public ComplexPolynomial(Complex _a) {
		super();

		deg      = 0;
		coeff    = new Complex[1];
		coeff[0] = _a;
	}
	public ComplexPolynomial(double _a, double _b) {
		super();

		deg      = 1;
		coeff    = new Complex[2];
		coeff[0] = new ComplexNumber(_b);
		coeff[1] = new ComplexNumber(_a);
	}
	public ComplexPolynomial(Complex _a, Complex _b) {
		super();

		deg      = 1;
		coeff    = new Complex[2];
		coeff[0] = _b;
		coeff[1] = _a;
	}
	public ComplexPolynomial(double _a, double _b, double _c) {
		super();

		deg      = 2;
		coeff    = new Complex[3];
		coeff[0] = new ComplexNumber(_c);
		coeff[1] = new ComplexNumber(_b);
		coeff[2] = new ComplexNumber(_a);
	}
	public ComplexPolynomial(Complex _a, Complex _b, Complex _c) {
		super();

		deg      = 2;
		coeff    = new Complex[3];
		coeff[0] = _c;
		coeff[1] = _b;
		coeff[2] = _a;
	}
	public ComplexPolynomial(double _a, double _b, double _c, double _d) {
		super();

		deg      = 3;
		coeff    = new Complex[4];
		coeff[0] = new ComplexNumber(_d);
		coeff[1] = new ComplexNumber(_c);
		coeff[2] = new ComplexNumber(_b);
		coeff[3] = new ComplexNumber(_a);
	}
	public ComplexPolynomial(Complex _a, Complex _b, Complex _c, Complex _d) {
		super();

		deg      = 3;
		coeff    = new Complex[4];
		coeff[0] = _d;
		coeff[1] = _c;
		coeff[2] = _b;
		coeff[3] = _a;
	}
	public ComplexPolynomial(double[] _coeffs) {
		super();

		deg   = _coeffs.length - 1;
		coeff = new Complex[deg + 1];
		for(int i = 0; i <= deg; ++i)
			coeff[i] = new ComplexNumber(_coeffs[i]);
	}
	public ComplexPolynomial(Complex[] _coeffs) {
		super();

		deg   = _coeffs.length - 1;
		coeff = new Complex[deg + 1];
		for(int i = 0; i <= deg; ++i)
			coeff[i] = _coeffs[i];
	}
	public ComplexPolynomial(Polynom.Real _cp) {
		this.deg = _cp.getDegree();
		coeff = new Complex[this.deg + 1];
		for(int i = 0; i <= deg; i++)
			coeff[i] = new ComplexNumber(_cp.getCoeff(i));
	}
	public ComplexPolynomial(Polynom.Complex _cp) {
		this.deg = _cp.getDegree();
		coeff = new Complex[this.deg + 1];
		for(int i = 0; i <= deg; i++)
			coeff[i] = _cp.getCoeff(i);
	}

	public int 					getDegree() {
		return deg;
	}
	public Complex[] 			getCoeffs() {
		return coeff;
	}
	public Complex 				getCoeff(int _degree) {
		return coeff[_degree];
	}

	public ComplexPolynomial 	getDerivative(int _nth) {
		if(_nth > deg)
			return new ComplexPolynomial(0.0);

		Complex[] nc = new Complex[deg - _nth + 1];

		int k = deg - _nth;
		for(int i = deg; i > _nth - 1; --i) {
			nc[k] = coeff[i];
			for(int j = 0; j < _nth; j++)
				nc[k] = nc[k].times(i - j);

			k--;
		}

		return new ComplexPolynomial(nc);
	}

	public Complex 				evaluate(double _d) {
		if(deg == 0)
			return coeff[0];

		Complex x = new ComplexNumber(_d);
		Complex y = coeff[deg];

		for(int i = deg - 1; i >= 0; --i)
			y = (y.times(x)).plus(coeff[i]);

		return y;
	}
	public Complex 				evaluate(Complex _c) {
		if(deg == 0)
			return coeff[0];

		Complex y = coeff[deg];

		for(int i = deg - 1; i >= 0; --i)
			y = (y.times(_c)).plus(coeff[i]);

		return y;
	}


	
	
	
	public ComplexPolynomial 	plus(double _d) {
		return plus(new ComplexPolynomial(new ComplexNumber(_d)));
	}
	public ComplexPolynomial 	plus(Complex _c) {
		return plus(new ComplexPolynomial(_c));
	}
	public ComplexPolynomial 	plus(ComplexPolynomial _cp) {
		ComplexPolynomial c = null;
		if(_cp.getDegree() <= deg) {
			c = new ComplexPolynomial(deg);
			for(int i = _cp.getDegree() + 1; i <= deg; ++i)
				c.coeff[i] = coeff[i];
			for(int i = 0; i <= _cp.getDegree(); ++i)
				c.coeff[i] = coeff[i].plus(_cp.getCoeff(i));
		} else {
			c = new ComplexPolynomial(_cp.getDegree());
			for(int i = deg + 1; i <= _cp.getDegree(); ++i)
				c.coeff[i] = _cp.getCoeff(i);
			for(int i = 0; i <= deg; ++i)
				c.coeff[i] = coeff[i].plus(_cp.getCoeff(i));
		}

		return c;
	}

	public ComplexPolynomial 	minus(double _d) {
		return minus(new ComplexPolynomial(new ComplexNumber(_d)));
	}
	public ComplexPolynomial 	minus(Complex _c) {
		ComplexPolynomial b = new ComplexPolynomial(_c);
		return this.minus(b);
	}
	public ComplexPolynomial 	minus(ComplexPolynomial _cp) {
		ComplexPolynomial c = null;
		if(_cp.getDegree() <= deg) {
			c = new ComplexPolynomial(deg);
			for(int i = _cp.deg + 1; i <= deg; i++)
				c.coeff[i] = coeff[i];
			for(int i = 0; i <= _cp.getDegree(); i++)
				c.coeff[i] = coeff[i].minus(_cp.getCoeff(i));
		} else {
			c = new ComplexPolynomial(_cp.getDegree());
			for(int i = deg + 1; i <= _cp.getDegree(); i++)
				c.coeff[i] = _cp.coeff[i].times(new ComplexNumber(-1));
			for(int i = 0; i <= deg; i++)
				c.coeff[i] = coeff[i].minus(_cp.getCoeff(i));
		}
		return c;
	}

	public ComplexPolynomial 	times(double _d) {
		ComplexPolynomial c = new ComplexPolynomial(deg);
		for(int i = 0; i <= deg; i++)
			c.coeff[i] = coeff[i].times(new ComplexNumber(_d));

		return c;
	}
	public ComplexPolynomial 	times(Complex _c) {
		ComplexPolynomial c = new ComplexPolynomial(deg);
		for(int i = 0; i <= deg; i++)
			c.coeff[i] = coeff[i].times(_c);

		return c;
	}
	public ComplexPolynomial 	times(ComplexPolynomial _cp) {
		ComplexPolynomial c = new ComplexPolynomial(deg + _cp.getDegree());
		for(int i = 0; i <= deg; i++)
			for(int j = 0; j <= _cp.getDegree(); j++)
				c.coeff[i + j] = coeff[i].times(_cp.getCoeff(j));

		return c;
	}


	public Complex[] 			roots() {
		boolean polish = true;
		return roots(polish, new ComplexNumber());
	}
	public Complex[] 			roots(boolean polish) {
		return roots(polish, new ComplexNumber());
	}
	public Complex[] 			roots(Complex estx) {
		boolean polish = true;
		return roots(polish, estx);
	}
	public Complex[] 			roots(boolean polish, Complex estx) {
		Complex[] roots = new Complex[deg];

		// degree == 0 => No Root
		if(deg == 0)
			return new Complex[0];

		// Check for no roots, i.e all coefficients except the first = 0
		int counter = 0;
		for(int i = 1; i <= deg; i++)
			if(coeff[i].isZero())
				counter++;
		if(counter == deg)
			return new Complex[0];

		// Check for only one non-zero coefficient
		int nonzerocoeff = 0;
		int nonzeroindex = 0;
		for(int i = 0; i <= deg; i++) {
			if(!coeff[i].isZero()) {
				nonzerocoeff++;
				nonzeroindex = i;
			}
		}
		if(nonzerocoeff == 1) {
			for(int i = 0; i < this.deg; i++)
				roots[i] = new ComplexNumber();
			return roots;
		}

		// check for leading zeros
		boolean testzero = true;
		int ii = 0, nzeros = 0;
		while(testzero) {
			if(coeff[ii].isZero()) {
				nzeros++;
				ii++;
			} else
				testzero = false;
		}
		if(nzeros > 0) {
			degwz   = deg - nzeros;
			coeffwz = new Complex[degwz + 1];
			for(int i = 0; i <= degwz; i++)
				coeffwz[i] = coeff[i + nzeros];
		} else {
			degwz   = deg;
			coeffwz = new Complex[degwz + 1];
			for(int i = 0; i <= degwz; i++)
				coeffwz[i] = coeff[i];
		}

		// calculate non-zero roots
		Complex[] root = new Complex[degwz];

		switch(degwz) {
		case 1: 	root[0] = (coeffwz[0].divides(coeffwz[1])).negate();
					break;
		case 2: 	root = quadratic(coeffwz[0], coeffwz[1], coeffwz[2]);
					break;
		case 3: 	root = cubic(coeffwz[0], coeffwz[1], coeffwz[2], coeffwz[3]);
					break;
		default: 	root = laguerreAll(polish, estx);
		}

		for(int i = 0; i < degwz; i++)
			roots[i] = root[i];

		if(nzeros > 0)
			for(int i = degwz; i < deg; i++)
				roots[i] = new ComplexNumber();

		return roots;
	}

	public static Complex[] 	quadratic(double c, double b, double a) {
		Complex aa = new ComplexNumber(a);
		Complex bb = new ComplexNumber(b);
		Complex cc = new ComplexNumber(c);

		return quadratic(cc, bb, aa);
	}
	public static Complex[] 	quadratic(Complex c, Complex b, Complex a) {
		double    qsign = 1.0;
		Complex   qsqrt = new ComplexNumber();
		Complex   qtest = new ComplexNumber();
		Complex   bconj = new ComplexNumber();
		Complex[] root  = new Complex[2];

		bconj = b.conjugate();
		qsqrt = Numbers.sqrt((Numbers.square(b)).minus((a.times(c)).times(4)));

		qtest = bconj.times(qsqrt);

		if(qtest.real() < 0.0)
			qsign = -1.0;

		qsqrt = ((qsqrt.divides(qsign)).plus(b)).divides(-2.0);
		root[0] = qsqrt.divides(a);
		root[1] = c.divides(qsqrt);

		return root;
	}

	public static Complex[] 	cubic(double d, double c, double b, double a) {
		Complex aa = new ComplexNumber(a);
		Complex bb = new ComplexNumber(b);
		Complex cc = new ComplexNumber(c);
		Complex dd = new ComplexNumber(d);

		return cubic(dd, cc, bb, aa);
	}
	public static Complex[] 	cubic(Complex aa, Complex bb, Complex cc, Complex dd) {
		Complex a = cc.divides(dd);
		Complex b = bb.divides(dd);
		Complex c = aa.divides(dd);

		Complex[] roots = new Complex[3];

		Complex bigQ = ((a.times(a)).minus(b.times(3.0))).divides(9.0);
		Complex bigR = (((((a.times(a)).times(a)).times(2.0)).minus((a.times(b)).times(9.0))).plus(c.times(27.0))).divides(54.0);

		Complex sign          = new ComplexNumber(1);
		Complex bigAsqrtTerm  = Numbers.sqrt((bigR.times(bigR)).minus((bigQ.times(bigQ)).times(bigQ)));
		Complex bigRconjugate = bigR.conjugate();
		if((bigRconjugate.times(bigAsqrtTerm)).real() < 0.0)
			sign = new ComplexNumber(-1);

		Complex bigA = (Numbers.pow(bigR.plus(sign.times(bigAsqrtTerm)), 1.0 / 3.0)).times(new ComplexNumber(-1));
		Complex bigB = null;

		if(bigA.isZero())
			bigB = new ComplexNumber();
		else
			bigB = bigQ.divides(bigA);

		Complex aPlusB      = bigA.plus(bigB);
		Complex aMinusB     = bigA.minus(bigB);
		Complex minusAplusB = aPlusB.times(new ComplexNumber(-1));
		Complex aOver3      = a.divides(3.0);
		Complex isqrt3over2 = new ComplexNumber(0.0, Math.sqrt(3.0) / 2.0);

		roots[0] = aPlusB.minus(aOver3);
		roots[1] = ((minusAplusB.divides(2.0)).minus(aOver3)).plus(isqrt3over2.times(aMinusB));
		roots[2] = ((minusAplusB.divides(2.0)).minus(aOver3)).minus(isqrt3over2.times(aMinusB));

		return roots;
	}

	// LAGUERRE'S METHOD FOR COMPLEX ROOTS OF A COMPLEX POLYNOMIAL

	// Laguerre method for one of the roots
	// Following the procedure in Numerical Recipes for C [Reference above]
	// estx     estimate of the root
	// coeff[]  coefficients of the polynomial
	// m        degree of the polynomial
	public static Complex laguerre(Complex est_root, Complex[] pcoeff, int m) {
		double eps = 1e-7;     	// estimated fractional round-off error
		int mr = 8;         	// number of fractional values in Adam's method of breaking a limit cycle
		int mt = 1000;      	// number of steps in breaking a limit cycle
		int maxit = mr * mt;  	// maximum number of iterations allowed
		int niter = 0;      	// number of iterations taken

		// fractions used to break a limit cycle
		double frac[] = { 0.5, 0.25, 0.75, 0.13, 0.38, 0.62, 0.88, 1.0 };

		Complex root  = new ComplexNumber();    // root
		Complex b     = new ComplexNumber();
		Complex d     = new ComplexNumber();
		Complex f     = new ComplexNumber();
		Complex g     = new ComplexNumber();
		Complex g2    = new ComplexNumber();
		Complex h     = new ComplexNumber();
		Complex sq    = new ComplexNumber();
		Complex gp    = new ComplexNumber();
		Complex gm    = new ComplexNumber();
		Complex dx    = new ComplexNumber();
		Complex x1    = new ComplexNumber();
		Complex temp1 = new ComplexNumber();
		Complex temp2 = new ComplexNumber();

		double abp = 0.0D, abm = 0.0D;
		double err = 0.0D, abx = 0.0D;

		for(int i = 1; i <= maxit; i++) {
			niter = i;
			b     = pcoeff[m];
			err   = Numbers.abs(b);
			d = f = new ComplexNumber();
			abx   = Numbers.abs(est_root);

			for(int j = m - 1; j >= 0; j--) {
				// Efficient computation of the polynomial and its first two derivatives
				f = (est_root.times(f)).plus(d);
				f = (est_root.times(f)).plus(d);
				d = (est_root.times(d)).plus(b);
				b = (est_root.times(b)).plus(pcoeff[j]);
				err = Numbers.abs(b) + abx * err;
			}
			err *= eps;

			// Estimate of round-off error in evaluating polynomial
			if(Numbers.abs(b) <= err) {
				root = est_root;
				niter = i;
				return root;
			}

			// Laguerre formula
			g   = d.divides(b);
			g2  = Numbers.square(g);
			h   = g2.minus(new ComplexNumber(2.0).times(f.divides(b)));
			sq  = Numbers.sqrt(new ComplexNumber((double) (m - 1)).times((new ComplexNumber((double) m).times(h)).minus(g2)));
			gp  = g.plus(sq);
			gm  = g.minus(sq);
			abp = Numbers.abs(gp);
			abm = Numbers.abs(gm);
			if(abp < abm)
				gp = gm;

			temp1 = new ComplexNumber((double) m);
			temp2 = new ComplexNumber(Math.cos((double) i), Math.sin((double) i));

			dx = ((Math.max(abp, abm) > 0.0 ? temp1.divides(gp) : new ComplexNumber(Math.exp(1.0 + abx)).times(temp2)));
			x1 = est_root.minus(dx);
//			if(Complex.isEqual(est_root, x1)) {
			if(est_root.real() == x1.real() && est_root.imag() == x1.imag()) {
				root = est_root;
				niter = i;
				return root;     // converged
			}
			if((i % mt) != 0) {
				est_root = x1;
			} else {
				// Every so often we take a fractional step to break any limit cycle
				// (rare occurence)
				est_root = est_root.minus(new ComplexNumber(frac[i / mt - 1]).times(dx));
			}
			niter = i;
		}

		// exceeded maximum allowed iterations
		root = est_root;
		System.out.println("Maximum number of iterations exceeded in laguerre");
		System.out.println("root returned at this point");
		return root;
	}

	public Complex[] laguerreAll() {
		Complex estx = new ComplexNumber();
		boolean polish = true;
		return laguerreAll(polish, estx);
	}

	//  Initial estimates estx, polish=true
	public Complex[] laguerreAll(Complex estx) {
		boolean polish = true;
		return laguerreAll(polish, estx);
	}

	//  Initial estimates are all zero.
	public Complex[] laguerreAll(boolean polish) {
		Complex estx = new ComplexNumber();
		return laguerreAll(polish, estx);
	}

	// Finds all roots of a complex polynomial by successive calls to laguerre
	//  Initial estimates are estx
	public Complex[] laguerreAll(boolean polish, Complex estx) {
		// polish boolean variable
		// if true roots polished also by Laguerre
		// if false roots returned to be polished by another method elsewhere.
		// estx estimate of root - Preferred default value is zero to favour convergence
		//   to smallest remaining root

		int m = this.degwz;
		double eps = 2.0e-6;  // tolerance in determining round off in imaginary part

		Complex x = new ComplexNumber();
		Complex b = new ComplexNumber();
		Complex c = new ComplexNumber();
		Complex[] ad = new Complex[m + 1];
		Complex[] roots = new Complex[m + 1];

		// Copy polynomial for successive deflation
		for(int j = 0; j <= m; j++)
			ad[j] = coeffwz[j];

		// Loop over each root found
		for(int j = m; j >= 1; j--) {
			x = estx;   // Preferred default value is zero to favour convergence to smallest remaining root and find the root
			x = laguerre(x, ad, j);
			if(Math.abs(x.imag()) <= 2.0 * eps * Math.abs(x.real()))
				x = new ComplexNumber(x.real());
			roots[j] = x;
			b = ad[j];
			for(int jj = j - 1; jj >= 0; jj--) {
				c      = ad[jj];
				ad[jj] = b;
				b      = (x.times(b)).plus(c);
			}
		}

		if(polish) {
			// polish roots using the undeflated coefficients
			for(int j = 1; j <= m; j++) {
				roots[j] = laguerre(roots[j], this.coeffwz, m);
			}
		}

		// Sort roots by their real parts by straight insertion
		for(int j = 2; j <= m; j++) {
			x = roots[j];
			int i = 0;
			for(i = j - 1; i >= 1; i--) {
				if(roots[i].real() <= x.real())
					break;
				roots[i + 1] = roots[i];
			}
			roots[i + 1] = x;
		}
		// shift roots to zero initial index
		for(int i = 0; i < m; i++)
			roots[i] = roots[i + 1];
		return roots;
	}
}
