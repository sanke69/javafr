package fr.java.sdk.math;

import static fr.java.sdk.lang.Asserts.assertFalse;
import static fr.java.sdk.lang.Asserts.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.java.beans.reflect.utils.Primitives;
import fr.java.lang.enums.Primitive;
import fr.java.math.numbers.Complex;
import fr.java.sdk.lang.Asserts;
import fr.java.sdk.math.numbers.complex.ComplexNumber;

public class Numbers {
	public static final double epsilon	=  2.220446049250313e-016;
	
	public static final double exp   	=  2.718281828459046;

	public static boolean	FAST_ABS	= true;
	public static boolean	FAST_FLOOR	= true;
	public static boolean	FAST_CEIL	= true;
	public static boolean	FAST_ROUND	= true;
	public static boolean	FAST_POW	= true;

	public static Number 		of(Object x) {
		if(x instanceof Byte)
			return (Byte) x;
		else if(x instanceof Short)
			return (Short) x;
		else if(x instanceof Integer)
			return (Integer) x;
		else if(x instanceof Long)
			return (Long) x;
		else if(x instanceof Float)
			return (Float) x;
		else if(x instanceof Double)
			return (Double) x;
		else {
			if(Primitives.isWrapperType(x.getClass())) {
				Class<?> primitive = Primitives.getPrimitiveType(x.getClass());
				if(primitive.isAssignableFrom(byte.class))
					return Byte.valueOf((byte) x);
				if(primitive.isAssignableFrom(short.class))
					return Short.valueOf((short) x);
				if(primitive.isAssignableFrom(int.class))
					return Integer.valueOf((int) x);
				if(primitive.isAssignableFrom(long.class))
					return Long.valueOf((long) x);
				if(primitive.isAssignableFrom(float.class))
					return Float.valueOf((float) x);
				if(primitive.isAssignableFrom(double.class))
					return Double.valueOf((double) x);
			}
		}
		return null;
	}

	// SIGN
    public static int 			sign(int x) {
		return x < 0 ? -1 : 1;
    }
    public static long 			sign(long x){
		return x < 0L ? -1L : 1L;
    }
    public static float 		sign(float x) {
		return x < 0.0F ? -1.0F : 1.0F;
    }
	public static double 		sign(double x) {
		return x < 0.0 ? -1.0 : 1.0;
    }

	public static double 		abs(double a) {
		return Math.abs(a);
	}
	public static double 		abs(Complex a) {
		if(Math.abs(a.real()) == 0.0D)
			return Math.abs(a.imag());

		if(Math.abs(a.imag()) == 0.0D)
			return Math.abs(a.real());

		double rmod  = Math.abs(a.real());
		double imod  = Math.abs(a.imag());
		double ratio = 0.0D;
		double res   = 0.0D;

		if(rmod >= imod) {
			ratio = a.imag() / a.real();
			res = rmod * Math.sqrt(1.0D + ratio * ratio);
		} else {
			ratio = a.real() / a.imag();
			res = imod * Math.sqrt(1.0D + ratio * ratio);
		}
		return res;
	}

	// EXPS
	public static Complex 		exp(double _d) {
		return exp(new ComplexNumber(_d));
	}
	public static Complex 		exp(Complex _c) {
		if(_c.imag() == 0.0d)
			return new ComplexNumber(Math.exp(_c.real()));

		if(_c.real() == 0.0d)
			return new ComplexNumber(Math.cos(_c.imag()), Math.sin(_c.imag()));

		double c = Math.exp(_c.real());
		return new ComplexNumber(c * Math.cos(_c.imag()), c * Math.sin(_c.imag()));
	}

	// LOGS
	public static float 		log(float _x) {
        return (float) Math.log((double) _x);
    }
	public static double 		log(double _x) {
        return Math.log(_x);
    }
	public static Complex 		log(Complex _c) {
		return new ComplexNumber(Math.log(abs(_c)), Math.atan2(_c.imag(), _c.real()));
	}

	public static float 		log2(float _x) {
        return (float) (Math.log((double) _x) / Math.log(2.0D));
    }
	public static double 		log2(double _x) {
        return Math.log(_x) / Math.log(2.0D);
    }

	public static float 		log10(float _x) {
        return (float) (Math.log((double) _x) / Math.log(10.0D));
    }
	public static double 		log10(double _x) {
        return Math.log(_x) / Math.log(10.0D);
    }

	public static float 		log10(float  _x, int _y) {
        return (float) (Math.log((double) _x) / Math.log((double) _y));
    }
	public static float 		log10(float _x, float _y) {
        return (float) (Math.log((double) _x) / Math.log((double) _y));
    }

	public static double 		log10(double _x, int _y) {
        return Math.log(_x) / Math.log((double) _y);
    }
	public static double 		log10(double _x, double _y) {
        return Math.log(_x) / Math.log(_y);
    }

	public static double 		antilog(double _x) {
        return Math.exp(_x);
    }
	public static float 		antilog(float _x) {
        return (float)Math.exp((double) _x);
    }

	public static double 		antilog2(double _x) {
        return Math.pow(2.0D, _x);
    }
	public static float 		antilog2(float _x) {
        return (float)Math.pow(2.0D, (double) _x);
    }
	
	public static double 		antilog10(double _x) {
        return Math.pow(10.0D, _x);
    }
	public static float 		antilog10(float _x){
        return (float) Math.pow(10.0D, (double) _x);
    }

	// SQUARE ROOT
	public static Complex 		sqrt(Complex _c) {
		double re = _c.real(), c_re;
		double im = _c.imag(), c_im;

		if(im == 0.0D)
			return new ComplexNumber(Math.sqrt(Math.abs(re)));

		double w, ratio;
		double amod = Math.abs(re);
		double bmod = Math.abs(im);
		if(amod >= bmod) {
			ratio = im / re;
			w = Math.sqrt(amod) * Math.sqrt(0.5D * (1.0D + Math.sqrt(1.0D + ratio * ratio)));
		} else {
			ratio = re / im;
			w = Math.sqrt(bmod) * Math.sqrt(0.5D * (Math.abs(ratio) + Math.sqrt(1.0D + ratio * ratio)));
		}
		if(re >= 0.0) {
			c_re = w;
			c_im = im / (2.0d * w);
		} else {
			if(im >= 0.0) {
				c_im = w;
				c_re = im / (2.0d * c_im);
			} else {
				c_im = -w;
				c_re = im / (2.0d * c_im);
			}
		}
		return new ComplexNumber(c_re, c_im);
	}

	public static Complex 		nthRoot(Complex _c, int n) {
		if(n == 0) {
			return new ComplexNumber(Double.POSITIVE_INFINITY, 0.0);
		} else if(n == 1) {
			return _c;
		} else {
			return exp((log(_c)).divides((double) n));
		}
	}

	// SQUARES
    public static int 			square(int _x){
        return _x * _x;
    }
    public static long 			square(long _x){
        return _x * _x;
    }
    public static float 		square(float _x) {
        return _x * _x;
    }
    public static double 		square(double _x) {
        return _x * _x;
    }

    public static BigDecimal 	square(BigDecimal _x){
        return _x.multiply(_x);
    }
    public static BigInteger 	square(BigInteger _x){
        return _x.multiply(_x);
    }

    public static Complex 		square(Complex _c) {
		double re = _c.real() * _c.real() - _c.imag() * _c.imag();
		double im = 2.0D * _c.real() * _c.imag();
		return new ComplexNumber(re, im);
	}

    // POW
	public static Complex 		pow(Complex a, int n) {
		return powDouble(a, (double) n);
	}
	public static Complex 		pow(Complex a, double b) {
		return powDouble(a, b);
	}
	public static Complex 		pow(Complex a, Complex b) {
		if(a.isZero()) {
			if(b.isReal()) {
				if(b.isImag()) {
					return new ComplexNumber(1.0, 0.0);
				} else {
					return new ComplexNumber(a.real() > 0.0 ? 0.0 : Double.POSITIVE_INFINITY, 0.0);
				}
			} else {
				return exp(b.times(log(a)));
			}
		} else {
			return exp(b.times(log(a)));
		}
	}
	public static Complex 		pow(double a, Complex b) {
		if(a == 0) {
			if(b.isReal()) {
				if(b.isImag())
					return new ComplexNumber(1.0, 0.0);
				else
					return new ComplexNumber(b.real() > 0.0 ? 0.0 : Double.POSITIVE_INFINITY, 0.0);
			} else {
				Complex z = new ComplexNumber(Math.pow(a, b.real()));
				Complex c = exp(new ComplexNumber(0, 1).times(b.imag() * Math.log(a)));
				return z.times(c);
			}
		} else {
			Complex z = new ComplexNumber(Math.pow(a, b.real()));
			Complex c = exp(new ComplexNumber(0, 1).times(b.imag() * Math.log(a)));
			return z.times(c);
		}
	}

    // FACTORIALS
    public static int 			factorial(int n) {
    	assertFalse(n < 0,  "n must be a positive integer");
    	assertFalse(n > 12, "n must less than 13 to avoid integer overflow\nTry long or double argument");

        int f = 1;
        for(int i = 2; i <= n; ++i) f *= i;
        return f;
    }
    public static long 			factorial(long n) {
    	assertFalse(n < 0,  "n must be a positive integer");
    	assertFalse(n > 20, "n must less than 21 to avoid long integer overflow\nTry double argument");

        long f = 1;
        for(long i = 2; i <= n; ++i) f *= i;
//      long iCount = 2L;
//      while(iCount <= n) {
//          f *= iCount;
//          iCount += 1L;
//      }
        return f;
    }
    public static double 		factorial(double n) {
    	assertFalse(n<0.0 || (n-Math.floor(n))!=0, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");

        double f = 1.0D;
        double iCount = 2.0D;
        while(iCount<=n){
            f*=iCount;
            iCount += 1.0D;
        }
        return f;
    }
    public static BigInteger 	factorial(BigInteger n) {
        assertFalse(n.compareTo(BigInteger.ZERO)==-1, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");

        BigInteger one = BigInteger.ONE;
        BigInteger f = one;
        BigInteger iCount = new BigInteger("2");
        while(iCount.compareTo(n)!=1){
            f = f.multiply(iCount);
            iCount = iCount.add(one);
        }
        one = null;
        iCount = null;
        return f;
    }
    public static BigDecimal 	factorial(BigDecimal n){
    	assertFalse(n.compareTo(BigDecimal.ZERO)==-1 /*|| !isInteger(n)*/, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");
        BigDecimal one = BigDecimal.ONE;
        BigDecimal f = one;
        BigDecimal iCount = new BigDecimal(2.0D);
        while(iCount.compareTo(n)!=1){
            f = f.multiply(iCount);
            iCount = iCount.add(one);
        }
        one = null;
        iCount = null;
        return f;
    }


    // https://www.mathsisfun.com/combinatorics/combinations-permutations.html
	public static int 			combination(int _N, int _k) {
		Asserts.assertTrue(_N >= _k);

		int Nfact   = factorial(_N);
		int Kfact   = factorial(_k);
		int NmKfact = factorial(_N - _k);

		return Nfact / (Kfact * NmKfact);
	}
	
	public static int 			permutation(int _N, int _k) {
		int Nfact   = factorial(_N);
		int Kfact   = factorial(_k);
		int NmKfact = factorial(_N - _k);

		return Nfact / (Kfact * NmKfact);
	}
	public static int 			permutation(int _N, int _k, boolean _withRepetition) {
		Asserts.assertTrue(_N < 13);
		Asserts.assertTrue(_k < 13);

		if(_withRepetition) {
			return (int) Math.pow(_N,  _k);
		} else {
			int r = 1;
			for(int i = 0; i < _k; ++i)
				r *= (_N - i);
			return r;
		}
	}
	
	
	
	public static void main0(String[] args) {
		for(int i = 0; i < 13; ++i)
			System.out.println(i + "!=" + factorial(i));

		for(int n = 0; n < 13; ++n)
			for(int k = 0; k < 13; ++k)
				if(n >= k)
					System.out.println("C(" + "n=" + n + ", k=" + k + ")=" + combination(n, k));
	}
	
    // LOG FACTORIALS
    public static double 		logFactorial(int n){
    	assertFalse(n < 0, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");

        double f = 0.0D;
        for(int i = 2; i <= n; ++i) f += Math.log(i);
        return f;
    }
    public static double 		logFactorial(long n){
    	assertFalse(n < 0L, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");

        double f    = 0.0D;
        long iCount = 2L;
        while(iCount<=n){
            f+=Math.log(iCount);
            iCount += 1L;
        }
        return f;
    }
    public static double 		logFactorial(double n){
    	assertFalse(n < 0 || (n - Math.floor(n)) != 0, "n must be a positive integer\nIs a Gamma funtion [Fmath.gamma(x)] more appropriate?");

        double f      = 0.0D;
        double iCount = 2.0D;
        while(iCount<=n){
            f+=Math.log(iCount);
            iCount += 1.0D;
        }
        return f;
    }

    /// TRIGONOMETRY
    public static double 		sin(double _x) {
        return Math.sin(_x);
    }
    public static Complex 		sin(Complex _c) {
		double re = _c.real();
		double im = _c.imag();
		return new ComplexNumber(sin(re) * cosh(im), cos(re) * sinh(im));
	}
    public static double 		asin(double _x){
        assertFalse(_x < -1.0D && _x > 1.0D, "asin argument (" + _x + ") must be >= -1.0 and <= 1.0");

        return Math.asin(_x);
    }
	public static Complex 		asin(Complex a) {
		Complex c = sqrt(new ComplexNumber(1).minus(square(a)));
		c = (new ComplexNumber(0, 1).times(a)).plus(c);
		c = new ComplexNumber(0, -1).times(log(c));
		return c;
	}

	public static double 		sinh(double _x) {
		//Hyperbolic sine of a double number
		return 0.5D * (Math.exp(_x) - Math.exp(- _x));
	}
	public static Complex 		sinh(Complex _c) {
		Complex c = _c.times(0, 1);
		return new ComplexNumber(0, -1).times(sin(c));
	}
	public static double 		asinh(double _x) {
		// Inverse hyperbolic sine of a double number
		double sgn = 1.0D;
		if(_x < 0.0D) {
			sgn = -1.0D;
			_x  = -_x;
		}
		return sgn * Math.log(_x + Math.sqrt(_x * _x + 1.0D));
	}
	public static Complex 		asinh(Complex _c) {
		Complex c = _c.plus(sqrt(square(_c).plus(1.0d)));
		return log(c);
	}

	public static double 		cos(double _x) {
		return Math.cos(_x);
	}
	public static Complex 		cos(Complex _c) {
		double re = _c.real();
		double im = _c.imag();
		return new ComplexNumber(cos(re) * cosh(im), - sin(re) * sinh(im));
	}
	public static double 		acos(double _x) {
		assertFalse(_x < -1.0D && _x > 1.0D, "acos argument (" + _x + ") must be >= -1.0 and <= 1.0");

		return Math.acos(_x);
	}
	public static Complex 		acos(Complex _c) {
		Complex c = sqrt(square(_c).minus(1.0));
		c = _c.plus(c);
		c = new ComplexNumber(0, -1).times(log(c));
		return c;
	}

	public static double 		cosh(double _x) {
		//Hyperbolic cosine of a double number
		return 0.5D * (Math.exp(_x) + Math.exp(- _x));
	}
	public static Complex 		cosh(Complex _c) {
		return cos(_c.times(0, 1));
	}
	public static double 		acosh(double _x) {
		// Inverse hyperbolic cosine of a double number
		assertFalse(_x < 1.0D, "acosh real number argument (" + _x + ") must be >= 1");

		return Math.log(_x + Math.sqrt(_x * _x - 1.0D));
	}
	public static Complex 		acosh(Complex _c) {
		Complex c = _c.plus(sqrt(square(_c).minus(1.0d)));
		return log(c);
	}

	public static double 		tan(double _x) {
		return Math.tan(_x);
	}
	public static Complex 		tan(Complex _c) {
		double re = _c.real();
		double im = _c.imag();

		Complex x = new ComplexNumber(Math.sin(re) * cosh(im),  Math.cos(re) * sinh(im));
		Complex y = new ComplexNumber(Math.cos(re) * cosh(im), -Math.sin(re) * sinh(im));
		return x.divides(y);
	}
	public static double 		atan(double _x) {
		return Math.atan(_x);
	}
	public static Complex 		atan(Complex a) {
		Complex c = new ComplexNumber(0, 1).plus(a);
		Complex d = new ComplexNumber(0, 1).minus(a);

		c = c.divides(d);
		c = log(c);
		c = new ComplexNumber(0, 1).times(c);
		c = c.divides(2.0d);
		return c;
	}
	public static double 		atan2(double _x, double _y) {
		return Math.atan2(_x, _y);
	}

	public static double 		tanh(double _x) {
		//Hyperbolic tangent of a double number
		return sinh(_x) / cosh(_x);
	}
	public static Complex 		tanh(Complex _c) {
		return sinh(_c).divides(cosh(_c));
	}
	public static double 		atanh(double _x) {
		// Inverse hyperbolic tangent of a double number
		double sgn = 1.0D;
		if(_x < 0.0D) {
			sgn = -1.0D;
			_x = -_x;
		}

		assertFalse(_x > 1.0D, "atanh real number argument (" + sgn * _x + ") must be >= -1 and <= 1");

		return 0.5D * sgn * (Math.log(1.0D + _x) - Math.log(1.0D - _x));
	}
	public static Complex 		atanh(Complex a) {
		Complex c = new ComplexNumber(1).plus(a);
		Complex d = new ComplexNumber(1).minus(a);
		c = log(c.divides(d));
		return c.divides(2.0d);
	}

	public static double 		cot(double _x) {
		// Cotangent
		return 1.0D / Math.tan(_x);
	}
	public static Complex 		cot(Complex _c) {
		double re = _c.real();
		double im = _c.imag();

		Complex x = new ComplexNumber(Math.sin(re) * cosh(im),  Math.cos(re) * sinh(im));
		Complex y = new ComplexNumber(Math.cos(re) * cosh(im), -Math.sin(re) * sinh(im));
		return y.divides(x);
	}
	public static double 		acot(double _x) {
		// Inverse cotangent
		return Math.atan(1.0D / _x);
	}
	public static Complex 		acot(Complex _c) {
		return atan(_c.inverse());
	}
	public static double 		acot2(double _x, double _y) {
		// Inverse cotangent - ratio numerator and denominator provided
		return Math.atan2(_y, _x);
	}

	public static double 		coth(double _x) {
		//Hyperbolic cotangent
		return 1.0D / tanh(_x);
	}
	public static Complex 		coth(Complex _c) {
		return cosh(_c).divides(sinh(_c));
	}
	public static double 		acoth(double _x) {
		// Inverse hyperbolic cotangent
		double sgn = 1.0D;
		if(_x < 0.0D) {
			sgn = -1.0D;
			_x  = -_x;
		}

		assertFalse(_x < 1.0D, "acoth real number argument (" + sgn * _x + ") must be <= -1 or >= 1");

		return 0.5D * sgn * (Math.log(1.0D + _x) - Math.log(_x - 1.0D));
	}
	public static Complex 		acoth(Complex a) {
		Complex c = new ComplexNumber(1).plus(a);
		Complex d = a.plus(1.0d);
		return log(c.divides(d)).divides(2.0d);
	}

	public static double 		sec(double _x) {
		// Secant
		return 1.0 / Math.cos(_x);
	}
    public static Complex 		sec(Complex _c ){
        double re = _c.real();
        double im = _c.imag();
        return new ComplexNumber(Math.cos(re) * cosh(im), - Math.sin(re) * sinh(im)).inverse();
    }
	public static double 		asec(double _x) {
		// Inverse secant
		assertFalse(_x < 1.0D && _x > -1.0D, "asec argument (" + _x + ") must be >= 1 or <= -1");

		return Math.acos(1.0 / _x);
	}
	public static Complex 		asec(Complex _c) {
		return acos(_c.inverse());
	}

	public static double 		sech(double _x) {
		//Hyperbolic secant of a double number
		return 1.0D / cosh(_x);
	}
	public static Complex 		sech(Complex _c) {
		return cosh(_c).inverse();
	}
	public static double 		asech(double _x) {
		// Inverse hyperbolic secant of a double number
		assertFalse(_x > 1.0D || _x < 0.0D, "asech real number argument (" + _x + ") must be >= 0 and <= 1");

		return 0.5D * (Math.log(1.0D / _x + Math.sqrt(1.0D / (_x * _x) - 1.0D)));
	}
	public static Complex 		asech(Complex _c) {
		Complex c = _c.inverse();
		Complex d = (square(_c)).minus(1.0D);
		return log(c.plus(sqrt(d)));
	}

	public static double 		csc(double _x) {
		// Cosecant
		return 1.0D / Math.sin(_x);
	}
	public static Complex 		csc(Complex _c) {
		double re = _c.real();
		double im = _c.imag();
		return new ComplexNumber(sin(re) * cosh(im), cos(re) * sinh(im)).inverse();
	}
	public static double 		acsc(double _x) {
		// Inverse cosecant
		assertFalse(_x < 1.0D && _x > -1.0D, "acsc argument (" + _x + ") must be >= 1 or <= -1");

		return Math.asin(1.0 / _x);
	}
	public static Complex 		acsc(Complex a) {
		return asin(a.inverse());
	}

	public static double 		csch(double _x) {
		//Hyperbolic cosecant
		return 1.0D / sinh(_x);
	}
	public static Complex 		csch(Complex _c) {
		return sinh(_c).inverse();
	}
	public static double 		acsch(double _x) {
		// Inverse hyperbolic cosecant
		double sgn = 1.0D;
		if(_x < 0.0D) {
			sgn = -1.0D;
			_x = -_x;
		}
		return 0.5D * sgn * (Math.log(1.0 / _x + Math.sqrt(1.0D / (_x * _x) + 1.0D)));
	}
	public static Complex 		acsch(Complex _c) {
		Complex c = _c.inverse();
		Complex d = (square(_c)).plus(1.0D);
		return log(c.plus(sqrt(d)));
	}

	public static double 		exsec(double _x) {
		// Exsecant
		return (1.0 / Math.cos(_x) - 1.0D);
	}
	public static Complex 		exsec(Complex _c) {
		return sec(_c).minus(1.0D);
	}
	public static double 		aexsec(double _x) {
		// Inverse exsecant
		assertFalse(_x < 0.0d && _x > -2.0d, "aexsec argument (" + _x + ") must be >= 0.0 and <= -2");

		return Math.asin(1.0d / (1.0d + _x));
	}
	public static Complex 		aexsec(Complex a) {
		Complex c = a.plus(1.0d);
		return asin(c.inverse());
	}

	public static double 		vers(double _x) {
		// Versine
		return (1.0D - Math.cos(_x));
	}
	public static Complex 		vers(Complex _c) {
		return new ComplexNumber(1).minus(cos(_c));
	}
	public static double 		avers(double _x) {
		// Inverse  versine
		assertFalse(_x < 0.0d && _x > 2.0d, "avers argument (" + _x + ") must be <= 2 and >= 0");

		return Math.acos(1.0d - _x);
	}
	public static Complex 		avers(Complex _c) {
		Complex c = new ComplexNumber(1).plus(_c);
		return acos(c);
	}

	public static double 		covers(double _x) {
		// Coversine
		return (1.0D - Math.sin(_x));
	}
	public static Complex 		covers(Complex _c) {
		return new ComplexNumber(1).minus(sin(_c));
	}
	public static double 		acovers(double _x) {
		// Inverse coversine
		assertFalse(_x < 0.0D && _x > 2.0D, "acovers argument (" + _x + ") must be <= 2 and >= 0");

		return Math.asin(1.0D - _x);
	}
	public static Complex 		acovers(Complex a) {
		Complex c = new ComplexNumber(1).plus(a);
		return asin(c);
	}

	public static double 		hav(double _x) {
		// Haversine
		return 0.5D * vers(_x);
	}
	public static Complex 		hav(Complex _c) {
		return vers(_c).divides(2.0d);
	}
	public static double 		ahav(double _x) {
		// Inverse haversine
		assertFalse(_x < 0.0D && _x > 1.0D, "ahav argument (" + _x + ") must be >= 0 and <= 1");

		return acos(1.0D - 2.0D * _x);
	}
	public static Complex 		ahav(Complex a) {
		Complex c = new ComplexNumber(1).minus(a.times(2.0d));
		return acos(c);
	}

	public static double 		sinc(double _x) {
		// Unnormalised sinc (unnormalised sine cardinal)   sin(x)/x
		return (Math.abs(_x) < 1e-40) ? 1.0D : Math.sin(_x) / _x;
	}
	public static double 		nsinc(double _x) {
		// Normalised sinc (normalised sine cardinal)  sin(pi.x)/(pi.x)
		return (Math.abs(_x) < 1e-40) ? 1.0D : Math.sin(Math.PI * _x) / (Math.PI * _x);
	}

	public static float 		hypot(float _adj, float _opp) {
		return (float) hypot((double) _adj, (double) _opp);
	}
	/*
	public static double 		hypot(double _adj, double _opp) {
		double amod = Math.abs(_adj);
		double omod = Math.abs(_opp);
		double cc = 0.0D, ratio = 0.0D;
		if(amod == 0.0) {
			cc = omod;
		} else {
			if(omod == 0.0) {
				cc = amod;
			} else {
				if(amod >= omod) {
					ratio = omod / amod;
					cc = amod * Math.sqrt(1.0 + ratio * ratio);
				} else {
					ratio = amod / omod;
					cc = omod * Math.sqrt(1.0 + ratio * ratio);
				}
			}
		}
		return cc;
	}
	*/
	public static double 		hypot(Complex _adj, Complex _opp){
		double amod = abs(_adj);
		double bmod = abs(_opp);
		double cc   = 0.0D, ratio = 0.0D;

		if(amod == 0.0D) {
			cc = bmod;
		} else if(bmod == 0.0D) {
			cc = amod;
		} else if(amod >= bmod) {
			ratio = bmod / amod;
			cc = amod * Math.sqrt(1.0 + ratio * ratio);
		} else {
			ratio = amod / bmod;
			cc = bmod * Math.sqrt(1.0 + ratio * ratio);
		}
		return cc;
	}


	public static int[] 		abs(int[] _v) {
		int   n = _v.length;
		int[] v = new int[n];
		for(int i = 0; i < n; i++)
			v[i] = Math.abs(_v[i]);
		return v;
	}
	public static long[] 		abs(long[] _v) {
		int    n = _v.length;
		long[] v = new long[n];
		for(int i = 0; i < n; i++)
			v[i] = Math.abs(_v[i]);
		return v;
	}
	public static float[] 		abs(float[] _v) {
		int     n = _v.length;
		float[] v = new float[n];
		for(int i = 0; i < n; i++)
			v[i] = Math.abs(_v[i]);
		return v;
	}
	public static double[] 		abs(double[] _v) {
		int      n = _v.length;
		double[] v = new double[n];
		for(int i = 0; i < n; i++)
			v[i] = Math.abs(_v[i]);
		return v;
	}

	public static byte[] 		reverse(byte[] _v) {
		int     n = _v.length;
		byte[]  v = new byte[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}
	public static char[] 		reverse(char[] _v) {
		int     n = _v.length;
		char[]  v = new char[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}
	public static short[] 		reverse(short[] _v) {
		int     n = _v.length;
		short[]  v = new short[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}
	public static int[] 		reverse(int[] _v) {
		int   n = _v.length;
		int[] v = new int[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}
	public static long[] 		reverse(long[] _v) {
		int     n = _v.length;
		long[]  v = new long[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}
	public static float[] 		reverse(float[] _v) {
		int      n = _v.length;
		float[]  v = new float[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}/*
	public static double[] 		reverse(double[] _v) {
		int      n = _v.length;
		double[] v = new double[n];
		for(int i = 0; i < n; ++i)
			v[i] = _v[n - 1 - i];
		return v;
	}*/
	public static void 			reverse(double[] _v) {
		int    last   = _v.length - 1;
		int    middle = _v.length / 2;
		double temp   = -1;
		for(int i = 0; i <= middle; i++) {
			temp         = _v[i];
			_v[i]        = _v[last - i];
			_v[last - i] = temp;
		}
	}

	public static String 		toString(final double[] _v) {
		StringBuilder sb = new StringBuilder();
		
		for(double v : _v)
			sb.append(v + " ");
		
		return sb.toString();
	}

	public static double[] 		sorted(final double[] _v) {
        int      index     = 0;
        int      lastIndex = -1;
        int      n         = _v.length;
        double   hold      = 0.0d;
        double[] sorted    = new double[n];

		for(int i = 0; i < n; i++)
			sorted[i] = _v[i];

		while(lastIndex != n - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < n; i++)
				if(sorted[i] < sorted[index])
					index = i;

			lastIndex++;
			hold              = sorted[index];
			sorted[index]     = sorted[lastIndex];
			sorted[lastIndex] = hold;
		}
		return sorted;
    }
	

	public static int[] sortWithIndices(double[] _v){
		int index = 0;
		int lastIndex = -1;
		double holdb = 0.0D;
		int holdi = 0;
		double[] bb = new double[_v.length];
		int[] indices = new int[_v.length];
		for(int i = 0; i < _v.length; i++) {
			bb[i] = _v[i];
			indices[i] = i;
		}

		while(lastIndex != _v.length - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < _v.length; i++)
				if(bb[i] < bb[index])
					index = i;

			lastIndex++;
			holdb = bb[index];
			bb[index] = bb[lastIndex];
			bb[lastIndex] = holdb;
			holdi = indices[index];
			indices[index] = indices[lastIndex];
			indices[lastIndex] = holdi;
		}
		return indices;
    }
	
	public static void 			sort(final double[] _v) {							// OK
        int      index     = 0;
        int      lastIndex = -1;
        int      n         = _v.length;
        double   hold      = 0.0d;
        double[] sorted    = new double[n];

		for(int i = 0; i < n; i++)
			sorted[i] = _v[i];

		while(lastIndex != n - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < n; i++)
				if(sorted[i] < sorted[index])
					index = i;

			lastIndex++;
			hold              = sorted[index];
			sorted[index]     = sorted[lastIndex];
			sorted[lastIndex] = hold;
		}
		
		System.arraycopy(sorted, 0, _v, 0, _v.length);
//		return sorted;
    }
    public static void 			sort(final double[] _x, final double[] _y) {		// OK
        assertTrue(_x.length == _y.length, "First argument array, aa, (length = " + _x.length + ") and the second argument array, bb, (length = " + _y.length + ") should be the same length");

        int      index     = 0;
        int      lastIndex = -1;
        int      n         = _x.length;
        double[] x_sorted  = new double[n];
        double[] y_sorted  = new double[n];
		double   x_temp    = 0.0d;
		double   y_temp    = 0.0d;

		for(int i = 0; i < n; i++) {
			x_sorted[i] = _x[i];
			y_sorted[i] = _y[i];
		}

		while(lastIndex != n - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < n; i++)
				if(x_sorted[i] < x_sorted[index])
					index = i;

			lastIndex++;

			x_temp              = x_sorted[index];
			x_sorted[index]     = x_sorted[lastIndex];
			x_sorted[lastIndex] = x_temp;

			y_temp              = y_sorted[index];
			y_sorted[index]     = y_sorted[lastIndex];
			y_sorted[lastIndex] = y_temp;
		}

		System.arraycopy(x_sorted, 0, _x, 0, _x.length);
		System.arraycopy(y_sorted, 0, _y, 0, _y.length);
    }

    public static void 			sort(double[] _v, double[] _dst, int[] indices) {
    	assertTrue(_dst.length >= _v.length);

        int    index     = 0;
        int    lastIndex = -1;
        int    n         = _v.length;
        double hold_val  = 0.0d;
        int    hold_idx  = 0;

		for(int i = 0; i < n; i++) {
			_dst[i]    = _v[i];
			indices[i] = i;
		}

		while(lastIndex != n - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < n; i++)
				if(_dst[i] < _dst[index])
					index = i;

			lastIndex++;
			hold_val           = _dst[index];
			_dst[index]        = _dst[lastIndex];
			_dst[lastIndex]    = hold_val;
			hold_idx           = indices[index];
			indices[index]     = indices[lastIndex];
			indices[lastIndex] = hold_idx;
		}
    }
    public static void sort(double[] _x, double[] _y, double[] _dst_x, double[] _dst_y) {
        assertTrue	(_x.length     == _y.length, "First argument array, _x, (length = " + _x.length + ") and the second argument array, _y, (length = " + _y.length + ") should be the same length");
        assertFalse	(_dst_x.length <  _x.length, "The third argument array, _dst_x, (length = " + _y.length + ") should be at least as long as the first argument array, _x, (length = " + _x.length + ")");
        assertFalse	(_dst_y.length <  _y.length, "The fourth argument array, _dst_y, (length = " + _dst_y.length + ") should be at least as long as the second argument array, _y, (length = " + _y.length + ")");
        int index     = 0;
        int lastIndex = -1;
        int n         = _x.length;

		double holdx = 0.0d;
		double holdy = 0.0d;

		for(int i = 0; i < n; i++) {
			_dst_x[i] = _x[i];
			_dst_y[i] = _y[i];
		}

		while(lastIndex != n - 1) {
			index = lastIndex + 1;
			for(int i = lastIndex + 2; i < n; i++)
				if(_dst_x[i] < _dst_x[index])
					index = i;

			lastIndex++;
			holdx             = _dst_x[index];
			_dst_x[index]     = _dst_x[lastIndex];
			_dst_x[lastIndex] = holdx;
			holdy             = _dst_y[index];
			_dst_y[index]     = _dst_y[lastIndex];
			_dst_y[lastIndex] = holdy;
		}
    }
	
	public static double[] copy(double[] _ds) {
		double[] ds_copy = new double[_ds.length];
		System.arraycopy(_ds, 0, ds_copy, 0, _ds.length);
		return ds_copy;
	}
	public static double[][] copy(double[][] _ds) {
		double[][] ds_copy = new double[_ds.length][];
		
		for(int i = 0; i < _ds.length; ++i) {
			ds_copy[i] = new double[_ds[i].length];
			System.arraycopy(_ds[i], 0, ds_copy[i], 0, _ds[i].length);
		}
		
		return ds_copy;
	}

    public static double[][][] copy(double[][][] array){
        if(array==null)return null;
        int n = array.length;
        double[][][] copy = new double[n][][];
        for(int i=0; i<n; i++){
            int m = array[i].length;
            copy[i] = new double[m][];
            for(int j=0; j<m; j++){
                int l = array[i][j].length;
                copy[i][j] = new double[l];
                for(int k=0; k<l;k++)copy[i][j][k] = array[i][j][k];
            }
        }
        return copy;
    }
	

	public  static double   interpolate(double _x_eval, double[] _x, double[] _y, double[] _y_prime) {
		if (((_x.length != _y.length) || (_x.length != _y_prime.length)) || (_y.length != _y_prime.length))
			throw new IllegalArgumentException("array lengths are not all equal");

		int    n = _x.length;
		double h = 0.0D, b = 0.0D, a = 0.0D, yy = 0.0D;

		int k   = 0;
		int klo = 0;
		int khi = n - 1;
		while (khi - klo > 1) {
			k = (khi + klo) >> 1;
			if (_x[k] > _x_eval) {
				khi = k;
			} else {
				klo = k;
			}
		}
		h = _x[khi] - _x[klo];

		if (h == 0.0) {
			throw new IllegalArgumentException("Two values of x are identical");
		} else {
			a = (_x[khi] - _x_eval) / h;
			b = (_x_eval - _x[klo]) / h;
			yy = a * _y[klo] + b * _y[khi]
				 + ((a * a * a - a) * _y_prime[klo] + (b * b * b - b) * _y_prime[khi]) * (h * h) / 6.0;
		}

		return yy;
	}

    public  static int[]    orderPoints(double[] _x, double[] _y) {
    	int n = _x.length;

    	int[] newAndOldIndices = new int[n];

	    // Sort x into ascending order storing indices changes
	    Numbers.sort(_x, new double[n], newAndOldIndices);
	    // Sort x into ascending order and make y match the new order storing both new x and new y
	    Numbers.sort(_x, _y, _x, _y);

	    return newAndOldIndices;
	}
	public  static int      checkForIdenticalPoints(double[] x, double[] y) {
		int[] newAndOldIndices = Numbers.orderPoints(x, y);

		boolean averageIdenticalAbscissae = true;
		boolean debug = true;

		int nPoints = x.length;
		int nP = nPoints;
		boolean test1 = true;
		int ii = 0;
		while (test1) {
			boolean test2 = true;
			int jj = ii + 1;
			while (test2) {
				if (x[ii] == x[jj]) {
					if (y[ii] == y[jj]) {
						if(debug)
							System.out.println("CubicSpline:: Two identical points, " + x[ii] + ", " + y[ii] + ", in data array at indices " + newAndOldIndices[ii] + " and " + newAndOldIndices[jj] + ", latter point removed");

						double[] xx = new double[nPoints - 1];
						double[] yy = new double[nPoints - 1];
						int[] naoi = new int[nPoints - 1];
						for (int i = 0; i < jj; i++) {
							xx[i] = x[i];
							yy[i] = y[i];
							naoi[i] = newAndOldIndices[i];
						}
						for (int i = jj; i < nPoints - 1; i++) {
							xx[i] = x[i + 1];
							yy[i] = y[i + 1];
							naoi[i] = newAndOldIndices[i + 1];
						}
						nPoints--;
						System.arraycopy(xx, 0, x, 0, xx.length);
						System.arraycopy(yy, 0, y, 0, yy.length);
						System.arraycopy(naoi, 0, newAndOldIndices, 0, naoi.length);

					} else {
						if(averageIdenticalAbscissae) {
							if(debug)
								System.out.println("CubicSpline:: Two identical points on the absicca (x-axis) with different ordinate (y-axis) values, " + x[ii] + ": " + y[ii] + ", " + y[jj] + ", average of the ordinates taken");

							y[ii] = (y[ii] + y[jj]) / 2.0D;
							double[] xx = new double[nPoints - 1];
							double[] yy = new double[nPoints - 1];
							int[] naoi = new int[nPoints - 1];
							for (int i = 0; i < jj; i++) {
								xx[i] = x[i];
								yy[i] = y[i];
								naoi[i] = newAndOldIndices[i];
							}
							for (int i = jj; i < nPoints - 1; i++) {
								xx[i] = x[i + 1];
								yy[i] = y[i + 1];
								naoi[i] = newAndOldIndices[i + 1];
							}
							nPoints--;
							System.arraycopy(xx, 0, x, 0, x.length);
							System.arraycopy(yy, 0, y, 0, y.length);
							System.arraycopy(naoi, 0, newAndOldIndices, 0, y.length);

						} else {
							double sepn = (x[x.length - 1] - x[0]) * 0.0005D;
							if(debug)
								System.out.println("CubicSpline: Two identical points on the absicca (x-axis) with different ordinate (y-axis) values, " + x[ii] + ": " + y[ii] + ", " + y[jj]);

							boolean check = false;
							if (ii == 0) {
								if (x[2] - x[1] <= sepn)
									sepn = (x[2] - x[1]) / 2.0D;
								if (y[0] > y[1]) {
									if (y[1] > y[2]) {
										check = stay(x, y, ii, jj, sepn);
									} else {
										check = swap(x, y, ii, jj, sepn);
									}
								} else {
									if (y[2] <= y[1]) {
										check = swap(x, y, ii, jj, sepn);
									} else {
										check = stay(x, y, ii, jj, sepn);
									}
								}
							}
							if (jj == nPoints - 1) {
								if (x[nP - 2] - x[nP - 3] <= sepn)
									sepn = (x[nP - 2] - x[nP - 3]) / 2.0D;
								if (y[ii] <= y[jj]) {
									if (y[ii - 1] <= y[ii]) {
										check = stay(x, y, ii, jj, sepn);
									} else {
										check = swap(x, y, ii, jj, sepn);
									}
								} else {
									if (y[ii - 1] <= y[ii]) {
										check = swap(x, y, ii, jj, sepn);
									} else {
										check = stay(x, y, ii, jj, sepn);
									}
								}
							}
							if (ii != 0 && jj != nPoints - 1) {
								if (x[ii] - x[ii - 1] <= sepn)
									sepn = (x[ii] - x[ii - 1]) / 2;
								if (x[jj + 1] - x[jj] <= sepn)
									sepn = (x[jj + 1] - x[jj]) / 2;
								if (y[ii] > y[ii - 1]) {
									if (y[jj] > y[ii]) {
										if (y[jj] > y[jj + 1]) {
											if (y[ii - 1] <= y[jj + 1]) {
												check = stay(x, y, ii, jj, sepn);
											} else {
												check = swap(x, y, ii, jj, sepn);
											}
										} else {
											check = stay(x, y, ii, jj, sepn);
										}
									} else {
										if (y[jj + 1] > y[jj]) {
											if (y[jj + 1] > y[ii - 1] && y[jj + 1] > y[ii - 1]) {
												check = stay(x, y, ii, jj, sepn);
											}
										} else {
											check = swap(x, y, ii, jj, sepn);
										}
									}
								} else {
									if (y[jj] > y[ii]) {
										if (y[jj + 1] > y[jj]) {
											check = stay(x, y, ii, jj, sepn);
										}
									} else {
										if (y[jj + 1] > y[ii - 1]) {
											check = stay(x, y, ii, jj, sepn);
										} else {
											check = swap(x, y, ii, jj, sepn);
										}
									}
								}
							}

							if (check == false) {
								check = stay(x, y, ii, jj, sepn);
							}
							if(debug)
								System.out.println(", the two abscissae have been separated by a distance " + sepn);

							jj++;
						}
					}
					if ((nPoints - 1) == ii)
						test2 = false;
				} else {
					jj++;
				}
				if (jj >= nPoints)
					test2 = false;
			}
			ii++;
			if (ii >= nPoints - 1)
				test1 = false;
		}
		if (nPoints < 3)
			throw new IllegalArgumentException("Removal of duplicate points has reduced the number of points to less than the required minimum of three data points");

		return nPoints;
	}

	private static boolean  swap(double[] _x, double[] _y, int _i, int _j, double sepn) {
		_x[_i] += sepn;
		_x[_j] -= sepn;
		double hold = _x[_i];
		_x[_i] = _x[_j];
		_x[_j] = hold;
		hold = _y[_i];
		_y[_i] = _y[_j];
		_y[_j] = hold;
		return true;
	}
	private static boolean  stay(double[] _x, double[] _y, int _i, int _j, double sepn) {
		_x[_i] -= sepn;
		_x[_j] += sepn;
		return true;
	}

	
	
	
	/** sqrt(a^2 + b^2) without under/overflow. **/
	public static double hypot(double a, double b) {
		double r;
		if(Math.abs(a) > Math.abs(b)) {
			r = b / a;
			r = Math.abs(a) * Math.sqrt(1 + r * r);
		} else if(b != 0) {
			r = a / b;
			r = Math.abs(b) * Math.sqrt(1 + r * r);
		} else {
			r = 0.0;
		}
		return r;
	}
	public static Number convert(Number _from, Primitive _to) {
		switch(_to) {
		case BYTE:		return new Byte(_from.byteValue());
		case SHORT:		return new Short(_from.shortValue());
		case INTEGER:	return new Integer(_from.intValue());
		case LONG:		return new Long(_from.longValue());
		case FLOAT:		return new Float(_from.floatValue());
		case DOUBLE:	return new Double(_from.doubleValue());
		default:		throw new IllegalArgumentException();
		}
	}
	
	public static final double EPSILON = (double) 1e-12;

	public static int hashCode(double x) {
		long f = Double.doubleToLongBits(x);
		return (int) (f ^ (f >>> 32));
	}

	public static final int   abs(int x) {
		int y = x >> 31;
		return (x ^ y) - y;
	}
	public static final float abs(final float x) {
		return FAST_ABS ? __fastAbs(x) : StrictMath.abs(x);
	}
	private static final float __fastAbs(final float x) {
		return x > 0 ? x : -x;
	}

	public static final int   floor(final float x) {
		return FAST_FLOOR ? __fastFloor(x) : (int) StrictMath.floor(x);
	}
	private static final int  __fastFloor(final float x) {
		int y = (int) x;
		if(x < y)
			return y - 1;
		return y;
	}

	public static final int   ceil(final float x) {
		return FAST_CEIL ? __fastCeil(x) : (int) StrictMath.ceil(x);
	}
	private static final int  __fastCeil(final float x) {
		int y = (int) x;
		if(x > y)
			return y + 1;
		return y;
	}
	
	public static final int   round(final float x) {
		return FAST_ROUND ? floor(x + .5f) : StrictMath.round(x);
	}

	public final static float min(final float a, final float b) {
		return a < b ? a : b;
	}
	public final static float max(final float a, final float b) {
		return a > b ? a : b;
	}

	public static final float pow(float a, float b) {
		return FAST_POW ? __fastPow(a, b) : (float) StrictMath.pow(a, b);
	}
	public static final float __fastPow(float a, float b) {
		final float SHIFT23 = 1 << 23;
		final float INV_SHIFT23 = 1.0f / SHIFT23;

		float x = Float.floatToRawIntBits(a);
		x *= INV_SHIFT23;
		x -= 127;
		float y = x - (x >= 0 ? (int) x : (int) x - 1);
		b *= x + (y - y * y) * 0.346607f;
		y = b - (b >= 0 ? (int) b : (int) b - 1);
		y = (y - y * y) * 0.33971f;
		return Float.intBitsToFloat((int) ((b + 127 - y) * SHIFT23));
	}

	public static final float sqrt(float x) {
		return (float) StrictMath.sqrt(x);
	}



	public final static int   min(final int a, final int b) {
		return a < b ? a : b;
	}
	public final static int   max(final int a, final int b) {
		return a > b ? a : b;
	}
	
	
	
	public final static float    map(final float value, final float fromMin, final float fromMax, final float toMin, final float toMax) {
		final float mult = (value - fromMin) / (fromMax - fromMin);
		final float res  = toMin + mult * (toMax - toMin);
		return res;
	}

	public final static int      clamp(int _value, int _min, int _max) {
		if(Float.compare(_value, _min) < 0)
			return _min;
		if(Float.compare(_value, _max) > 0)
			return _max;
		return _value;
	}
	public final static float    clamp(float value, float min, float max) {
		if(Float.compare(value, min) < 0)
			return min;
		if(Float.compare(value, max) > 0)
			return max;
		return value;
	}
	/*
	public final static double   clamp(double value, double min, double max) {
		if(Double.compare(value, min) < 0)
			return min;
		if(Double.compare(value, max) > 0)
			return max;
		return value;
	}
	*/
	public static final double clamp(double _lower, double _value, double _higher) {
		return _value < _lower ? _lower : _value > _higher ? _higher : _value;
	}

	public static final double 	truncate(double _value, int _decimal) {
		double truncated = _value;
		if(!isNaN(_value) && !isPlusInfinity(_value) && !isMinusInfinity(_value) && _value != 0.0d) {
			double mul10 = 10 * _decimal;
			truncated = Math.floor(_value * mul10) / mul10;
		}
		return truncated;
	}
	
 	public static boolean 		isInfinity(double x) {
		return (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY) ? true : false;
	}
	public static boolean 		isInfinity(float x) {
		return (x == Float.POSITIVE_INFINITY || x == Float.NEGATIVE_INFINITY) ? true : false;
	}
	public static boolean 		isPlusInfinity(double x) {
		return (x == Double.POSITIVE_INFINITY) ? true : false;
	}
	public static boolean 		isPlusInfinity(float x) {
		return (x == Float.POSITIVE_INFINITY) ? true : false;
	}
	public static boolean 		isMinusInfinity(double x) {
		return (x == Double.NEGATIVE_INFINITY) ? true : false;
	}
	public static boolean 		isMinusInfinity(float x) {
		return (x == Float.NEGATIVE_INFINITY) ? true : false;
	}
	public static boolean 		isNaN(double x) {
		return (x != x) ? true : false;
	}
	public static boolean 		isNaN(float x) {
		return (x != x) ? true : false;
	}

	public final static boolean isPowerOfTwo(final int x) {
		return x > 0 && (x & x - 1) == 0;
	}

	public static boolean 		isEqual(double x, double y) {
		if(isNaN(x) && isNaN(y))
			return true;

		if(isPlusInfinity(x) && isPlusInfinity(y))
			return true;

		if(isMinusInfinity(x) && isMinusInfinity(y))
			return true;

		if(x == y)
			return true;
		
		return false;
	}
	public static boolean 		isEqualWithinLimits(double x, double y, double limit) {
		return (Math.abs(x - y) <= Math.abs(limit)) ? true: false;
	}
	public static boolean 		isEqualWithinPerCent(double x, double y, double perCent) {
		double limit = Math.abs((x + y) * perCent / 200.0d);
		if(Math.abs(x - y) <= limit)
			return true;
		return false;
	}

	public static final int     ceilPowerOf2(int x) {
		int pow2 = 1;
		while(pow2 < x)
			pow2 <<= 1;
		return pow2;
	}
	public final static int     nextPowerOfTwo(int x) { // SWAR Algorithm
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		x |= x >> 8;
		x |= x >> 16;
		return x + 1;
	}

	public static double        normalizeValue(double value, double min, double max, double newMin, double newMax) {
		return (value - min) * (newMax - newMin) / (max - min) + newMin;
	}

	public static final int 	getDecimalCount(double _value) {
		double d= 234.12413;
		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int decimalPlaces = text.length() - integerPlaces - 1;

		return decimalPlaces;
	}

	public static double 		min(final double[] _v) {
		double min = _v[0];

		for(int i = 1; i < _v.length; ++i)
			if( min > _v[i] )
				min = _v[i];

		return min;
	}
	public static double 		max(final double[] _v) {
		double max = _v[0];

		for(int i = 1; i < _v.length; ++i)
			if( max < _v[i] )
				max = _v[i];

		return max;
	}
	
	
	public static final double getPreviousMultiple(double _value, double _multiple) {
		return sign(_value) * _multiple*(Math.ceil(Math.abs(_value/_multiple)));
	}
	public static final double getNextMultiple(double _value, double _multiple) {
		return sign(_value) * _multiple*(Math.floor(Math.abs(_value/_multiple)));
	}
	
	public static void main(String[] args) {
		System.out.println( getPreviousMultiple(Math.PI, 0.25) );
		System.out.println( getNextMultiple(Math.PI, 0.25) );
	}
	

    public static double truncateOld(double xDouble, int trunc){
        double xTruncated = xDouble;
        if(!isNaN(xDouble) && !isPlusInfinity(xDouble) && !isMinusInfinity(xDouble) && xDouble!=0.0D){
                        String xString = ((new Double(xDouble)).toString()).trim();
                        xTruncated = Double.parseDouble(truncateProcedure(xString, trunc));
                    }
        return xTruncated;
	}
	private static String truncateProcedure(String xValue, int trunc) {
		String xTruncated = xValue;
		String xWorking   = xValue;
		String exponent   = " ";
		String first      = "+";
		int expPos        = xValue.indexOf('E');
		int dotPos        = xValue.indexOf('.');
		int minPos        = xValue.indexOf('-');

		if(minPos == 0) {
			xWorking = xWorking.substring(1);
			first    = "-";
			dotPos--;
			expPos--;
		}

		if(expPos > -1) {
			exponent = xWorking.substring(expPos);
			xWorking = xWorking.substring(0, expPos);
		}

		String xPreDot    = null;
		String xPostDot   = "0";
		String xDiscarded = null;
		String tempString = null;
		double tempDouble = 0.0D;

		if(dotPos > -1) {
			xPreDot     = xWorking.substring(0, dotPos);
			xPostDot    = xWorking.substring(dotPos + 1);
			int xLength = xPostDot.length();

			if(trunc < xLength) {
				xDiscarded = xPostDot.substring(trunc);
				tempString = xDiscarded.substring(0, 1) + ".";

				tempString += (xDiscarded.length() > 1) ? xDiscarded.substring(1) : "0";
				tempDouble  = Math.round(Double.parseDouble(tempString));

				if(trunc > 0) {
					if(tempDouble >= 5.0) {
						int[] xArray = new int[trunc + 1];
						for(int i = 0; i < trunc; i++)
							xArray[i + 1] = Integer.parseInt(xPostDot.substring(i, i + 1));

						boolean test     = true;
						int     iCounter = trunc;
						while(test) {
							xArray[iCounter] += 1;
							if(iCounter > 0) {
								if(xArray[iCounter] < 10) {
									test = false;
								} else {
									xArray[iCounter] = 0;
									iCounter--;
								}
							} else {
								test = false;
							}
						}
						int preInt = Integer.parseInt(xPreDot);
						preInt += xArray[0];
						xPreDot = (new Integer(preInt)).toString();
						tempString = "";
						for(int i = 1; i <= trunc; i++) {
							tempString += (new Integer(xArray[i])).toString();
						}
						xPostDot = tempString;
					} else {
						xPostDot = xPostDot.substring(0, trunc);
					}
				} else {
					if(tempDouble >= 5.0) {
						int preInt = Integer.parseInt(xPreDot);
						preInt++;
						xPreDot = (new Integer(preInt)).toString();
					}
					xPostDot = "0";
				}
			}
			xTruncated = first + xPreDot.trim() + "." + xPostDot.trim() + exponent;
		}
		return xTruncated.trim();
	}
	

	public static final double nearest(double _lower, double _value, double _higher) {
		return Math.abs(_value - _lower) < Math.abs(_value - _higher) ? _lower : _higher;
	}

	
	
	public static BigDecimal getBigDecimal( Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger ) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = BigDecimal.valueOf( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }

    public static BigInteger getBigInteger(Object value) {
        BigInteger ret = null;
        if ( value != null ) {
            if ( value instanceof BigInteger ) {
                ret = (BigInteger) value;
            } else if ( value instanceof String ) {
                ret = new BigInteger( (String) value );
            } else if ( value instanceof BigDecimal ) {
                ret = ((BigDecimal) value).toBigInteger();
            } else if ( value instanceof Number ) {
                ret = BigInteger.valueOf( ((Number) value).longValue() );
            } else {
                throw new ClassCastException( "Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigInteger." );
            }
        }
        return ret;
    }

	private static Complex powDouble(Complex a, double b) {
		double re = a.real();
		double im = a.imag();

		if(a.isZero()) {
			if(b == 0.0) {
				return new ComplexNumber(1.0, 0.0);
			} else if(b > 0.0) {
				return new ComplexNumber(0.0, 0.0);
			} else /*if(b < 0.0)*/ {
				return new ComplexNumber(Double.POSITIVE_INFINITY, 0.0);
			}
		} else if(im == 0.0D && re > 0.0D) {
			return new ComplexNumber(Math.pow(re, b), 0);
		} else if(re == 0.0D) {
			return exp(new ComplexNumber(b).times(log(a)));
		} else {
			double c = Math.pow(re * re + im * im, b / 2.0D);
			double th = Math.atan2(im, re);
			return new ComplexNumber(c * Math.cos(b * th), c * Math.sin(b * th));
		}
	}

}
