package fr.java.sdk.math.interpolation.functions.splines.cubic;

import fr.java.sdk.math.Numbers;

public class Utils {

    public static int        	prepareData(double[] _x, double[] _y, double[] _dydx) {
    	if(_x.length != _y.length)
    		throw new IllegalArgumentException("Arrays x and y-row are of different length " + _x.length + " " + _y.length);
      	if(_x.length < (_dydx != null ? 2 : 3))
      		throw new IllegalArgumentException("The data matrix must have a minimum size of " + (_dydx != null ? 2 : 3) + "^2");

     	int nPoints = _x.length;

        int[] xIndices = Numbers.sortWithIndices(_x);

        double[] hold = new double[nPoints];

        for(int i = 0; i < nPoints; i++)
	        hold[i] = _y[xIndices[i]];
        for(int i = 0; i < nPoints; i++)
	        _y[i] = hold[i];

	    if(_dydx != null) {
            for(int i = 0; i < nPoints; i++)
	            hold[i] = _dydx[xIndices[i]];
            for(int i = 0; i < nPoints; i++)
	            _dydx[i] = hold[i];
        }

	    // check for identical x values
	    for(int i = 1; i < nPoints; i++) {
	        if(_x[i] == _x[i-1]) {
	            System.out.println("x[" + xIndices[i] + "] and x[" + xIndices[i+1] + "] are identical, " + _x[i]);
	            System.out.println("The y values have been averaged and one point has been deleted");

	            _y[i-1] = (_y[i-1] + _y[i]) / 2.0;
	            for(int j = i; j < nPoints - 1; j++) {
	                _x[j] = _x[j + 1];
	                _y[j] = _y[j + 1];
	                xIndices[j] = xIndices[j + 1];
	            }

	            if(_dydx != null) {
	                _dydx[i - 1] = (_dydx[i - 1] + _dydx[i]) / 2.0;
	                for(int j = i; j < nPoints - 1; j++)
	                    _dydx[j] = _dydx[j + 1];
	            }

	            nPoints--;
	        }
	    }
	    
	    return nPoints;
    }
    public static int[]        	prepareData(double[] _x1, double[] _x2, double[][] _y, double[][] _dydx1, double[][] _dydx2, double[][] _d2ydx1dx2) {
		if(_x1.length < 2 || _x2.length < 2)
			throw new IllegalArgumentException("The data matrix must have a minimum size of 2 X 2");
		if(_x1.length != _y.length)
			throw new IllegalArgumentException("Arrays x1 and y-row are of different length " + _x1.length + " " + _y.length);
		if(_x2.length != _y[0].length)
			throw new IllegalArgumentException("Arrays x2 and y-column are of different length "+ _x2.length + " " + _y[0].length);

		int nPoints =_x1.length;
		int mPoints =_x2.length;

		boolean useDerivative = _dydx1 != null && _dydx2 != null && _d2ydx1dx2 != null;

		int[] x1indices = Numbers.sortWithIndices(_x1);

		double[][] hold   = new double[nPoints][mPoints];
		double[][] hold1  = null;
		double[][] hold2  = null;
		double[][] hold12 = null;

		if(useDerivative) {
		    hold1  = new double[nPoints][mPoints];
		    hold2  = new double[nPoints][mPoints];
		    hold12 = new double[nPoints][mPoints];
		}

		for(int i = 0; i < nPoints; i++)
		    for(int j = 0; j < mPoints; j++)
		        hold[i][j] = _y[x1indices[i]][j];

		for(int i = 0; i < nPoints; i++)
		    for(int j = 0; j < mPoints; j++)
		        _y[i][j] = hold[i][j];

		if(useDerivative) {
		    for(int i = 0; i < nPoints; i++) {
		        for(int j = 0; j < mPoints; j++) {
		            hold1[i][j]  = _dydx1[x1indices[i]][j];
		            hold2[i][j]  = _dydx2[x1indices[i]][j];
		            hold12[i][j] = _d2ydx1dx2[x1indices[i]][j];
		        }
		    }
		    for(int i = 0; i < nPoints; i++) {
		        for(int j = 0; j < mPoints; j++) {
		            _dydx1[i][j]     = hold1[i][j];
		            _dydx2[i][j]     = hold2[i][j];
		            _d2ydx1dx2[i][j] = hold12[i][j];
		        }
		    }
		}

		int[] x2indices = Numbers.sortWithIndices(_x2);

		double[] xh = new double[mPoints];
		for(int i = 0; i < mPoints; i++)
			_x2[i] = xh[mPoints - 1 - i];

		for(int i = 0; i < mPoints; i++)
		    for(int j = 0; j < nPoints; j++)
		        hold[j][i] = _y[j][x2indices[i]];

		for(int i = 0; i < nPoints; i++)
		    for(int j = 0; j < mPoints; j++)
		        _y[i][j] = hold[i][mPoints - 1 - j];

		if(useDerivative) {
		    for(int i = 0; i < mPoints; i++) {
		        for(int j = 0; j < nPoints; j++) {
		            hold1[j][i]  = _dydx1[j][x2indices[i]];
		            hold2[j][i]  = _dydx2[j][x2indices[i]];
		            hold12[j][i] = _d2ydx1dx2[j][x2indices[i]];
		        }
		    }
		    for(int i = 0; i < nPoints; i++) {
		        for(int j = 0; j < mPoints; j++) {
		            _dydx1[i][j]     = hold1[i][mPoints-1-j];
		            _dydx2[i][j]     = hold2[i][mPoints-1-j];
		            _d2ydx1dx2[i][j] = hold12[i][mPoints-1-j];
		        }
		    }
		}

		// check for duplicate in x1
		for(int i = 1; i < nPoints; i++) {
			if(_x1[i] == _x1[i - 1]) {
				for(int j = i; j < nPoints - 1; j++) {
					_x1[j]       = _x1[j + 1];
					x1indices[j] = x1indices[j + 1];
				}

				for(int j = 0; j < mPoints; j++) {
					_y[i - 1][j] = (_y[i - 1][j] + _y[i][j]) / 2.0;

					for(int k = i; k < nPoints - 1; k++)
						_y[k][j] = _y[k + 1][j];

					if(useDerivative) {
						_dydx1[i - 1][j]     = (_dydx1[i - 1][j] + _dydx1[i][j]) / 2.0;
						_dydx2[i - 1][j]     = (_dydx2[i - 1][j] + _dydx2[i][j]) / 2.0;
						_d2ydx1dx2[i - 1][j] = (_d2ydx1dx2[i - 1][j] + _d2ydx1dx2[i][j]) / 2.0;

						for(int k = i; k < nPoints - 1; k++) {
							_dydx1[k][j]     = _dydx1[k + 1][j];
							_dydx2[k][j]     = _dydx2[k + 1][j];
							_d2ydx1dx2[k][j] = _d2ydx1dx2[k + 1][j];
						}
					}
				}

				nPoints--;
			}
		}

		// check for duplicate in x2
		for(int i = 1; i < mPoints; i++) {
			if(_x2[i] == _x2[i - 1]) {
				for(int j = i; j < mPoints - 1; j++) {
					_x2[j]       = _x2[j + 1];
					x2indices[j] = x2indices[j + 1];
				}

				for(int j = 0; j < nPoints; j++) {
					_y[j][i - 1] = (_y[j][i - 1] + _y[j][i]) / 2.0;

					for(int k = i; k < mPoints - 1; k++)
						_y[j][k] = _y[j][k + 1];

					if(useDerivative) {
						_dydx1[j][i - 1]     = (_dydx1[j][i - 1] + _dydx1[j][i]) / 2.0;
						_dydx2[j][i - 1]     = (_dydx2[j][i - 1] + _dydx2[j][i]) / 2.0;
						_d2ydx1dx2[j][i - 1] = (_d2ydx1dx2[j][i - 1] + _d2ydx1dx2[j][i]) / 2.0;

						for(int k = i; k < nPoints - 1; k++) {
							_dydx1[j][k]     = _dydx1[j][k + 1];
							_dydx2[j][k]     = _dydx2[j][k + 1];
							_d2ydx1dx2[j][k] = _d2ydx1dx2[j][k + 1];
						}
					}
				}

				mPoints--;
			}
		}

	    return new int[] { nPoints, mPoints };
    }

    public static double[]   	compute1stDerivative(double[] _x, double[] _y) {
    	int      nPoints = _x.length;
    	double[] dydx    = new double[nPoints];

        for(int i = 0; i < nPoints; i++) {
        	int ip1 = i + 1 < nPoints - 1 ? i + 1 : nPoints - 1;
        	int im1 = i - 1 > 0           ? i - 1 : 0;

            dydx[i] = (_y[ip1] - _y[im1]) / (_x[ip1] - _x[im1]);
        }
        
        return dydx;
	}
    public static double[]   	compute1stDerivativeWithInterpolation(double[] _x, double[] _y, double _delta) {
    	int      nPoints = _x.length;
    	double[] dydx    = new double[nPoints];

    	// Define the incrX
	    double range = _x[nPoints - 1] - _x[0];

	    double averageSeparation = range / nPoints;
	    double minSep            = _x[1] - _x[0];
	    double minimumSeparation = minSep;

	    for(int i = 2; i < nPoints; i++)
	        if( ( minSep = _x[i] - _x[i-1] ) < minimumSeparation ) minimumSeparation = minSep;

        double 
        incrX = _delta * range;
        incrX = incrX < averageSeparation ? incrX : minimumSeparation < 0.1 * averageSeparation ? 0.1 * minimumSeparation : minimumSeparation;

        // 'Numerical Differentiation' using delta and interpolation
        CubicSpline cs = new CubicSpline(_x, _y);

        double[] xjp1 = new double[nPoints];
        double[] xjm1 = new double[nPoints];

        for(int i = 0; i < nPoints; i++){
            xjp1[i] = _x[i] + incrX < _x[nPoints-1] ? _x[i] + incrX : _x[nPoints-1];
            xjm1[i] = _x[i] - incrX > _x[0]         ? _x[i] - incrX : _x[0];
        }

        for(int i = 0; i < nPoints; i++)
            dydx[i] = ( cs.evaluate(xjp1[i]) - cs.evaluate(xjm1[i]) ) / ( xjp1[i] - xjm1[i] );

        return dydx;
	}

	public static double[] 		compute2ndDerivative(double[] _x, double[] _y) {
		return compute2ndDerivative(_x, _y, 0d, 0d);
	}
	public static double[] 		compute2ndDerivative(double[] _x, double[] _y, double _yp1, double _ypn) {
		double p = 0.0D, qn = 0.0D, sig = 0.0D, un = 0.0D;
		int    n = _x.length;
		
		double[] d2ydx2 = new double[n];
		double[] u      = new double[n];

		if(Double.isNaN(_yp1)) {
			d2ydx2[0] = u[0] = 0.0;
		} else {
			d2ydx2[0] = - 0.5;
			u[0]      = (3.0 / (_x[1] - _x[0])) * ((_y[1] - _y[0]) / (_x[1] - _x[0]) - _yp1);
		}

		for(int i = 1; i <= n - 2; i++) {
			sig       = (_x[i] - _x[i - 1]) / (_x[i + 1] - _x[i - 1]);
			p         = sig * d2ydx2[i - 1] + 2.0;
			d2ydx2[i] = (sig - 1.0) / p;
			u[i]      = (_y[i + 1] - _y[i]) / (_x[i + 1] - _x[i]) - (_y[i] - _y[i - 1]) / (_x[i] - _x[i - 1]);
			u[i]      = (6.0 * u[i] / (_x[i + 1] - _x[i - 1]) - sig * u[i - 1]) / p;
		}

		if(Double.isNaN(_ypn)) {
			qn = un = 0.0;
		} else {
			qn = 0.5;
			un = (3.0 / (_x[n - 1] - _x[n - 2])) * (_ypn - (_y[n - 1] - _y[n - 2]) / (_x[n - 1] - _x[n - 2]));
		}

		d2ydx2[n - 1] = (un - qn * u[n - 2]) / (qn * d2ydx2[n - 2] + 1.0);

		for(int k = n - 2; k >= 0; k--) {
			d2ydx2[k] = d2ydx2[k] * d2ydx2[k + 1] + u[k];
		}

		return d2ydx2;
	}

    public static double[][][]  compute1stDerivatives(double[] _x1, double[] _x2, double[][] _y) {
    	int nPoints = _x1.length;
    	int mPoints = _x2.length;

    	double[][] dydx1     = new double[nPoints][mPoints];
    	double[][] dydx2     = new double[nPoints][mPoints];
    	double[][] d2ydx1dx2 = new double[nPoints][mPoints];

        for(int i = 0; i < nPoints; i++){
        	int ip1 = i + 1 < nPoints - 1 ? i + 1 : nPoints - 1;
        	int im1 = i - 1 > 0           ? i - 1 : 0;
 
            for(int j = 0; j < mPoints; j++){
            	int jp1 = j + 1 < mPoints - 1 ? j + 1 : mPoints - 1;
            	int jm1 = j - 1 > 0           ? j - 1 : 0;

                dydx1[i][j]     = (_y[ip1][j] - _y[im1][j])/(_x1[ip1] - _x1[im1]);
                dydx2[i][j]     = (_y[i][jp1] - _y[i][jm1])/(_x2[jp1] - _x2[jm1]);
                d2ydx1dx2[i][j] = (_y[ip1][jp1] - _y[ip1][jm1] - _y[im1][jp1] + _y[im1][jm1])/((_x1[ip1] - _x1[im1])*(_x2[jp1] - _x2[jm1]));
            }
        }
        
        return new double[][][] { dydx1, dydx2, d2ydx1dx2, };
	}
    public static double[][][]  compute1stDerivativesWithInterpolation(double[] _x1, double[] _x2, double[][] _y, double _delta) {
    	int nPoints = _x1.length;
    	int mPoints = _x2.length;

	    // numerical difference increments
	    double range1 = _x1[_x1.length - 1] - _x1[0];
	    double range2 = _x2[_x2.length - 1] - _x2[0];

	    double averageSeparation1 = range1 / nPoints;
	    double averageSeparation2 = range2 / mPoints;
	    double 

	    minSep = _x1[1] - _x1[0];
	    double minimumSeparation1 = minSep;
	    for(int i = 2; i < nPoints; i++)
	        if( (minSep = _x1[i] - _x1[i-1]) < minimumSeparation1) minimumSeparation1 = minSep;

	    minSep = _x2[1] - _x2[0];
	    double minimumSeparation2 = minSep;
	    for(int i = 2; i < mPoints; i++)
	        if( (minSep = _x2[i] - _x2[i-1]) < minimumSeparation2) minimumSeparation2 = minSep;

        double 
        incrX1 = _delta * range1;
        incrX1 = incrX1 < averageSeparation1 ? incrX1 : minimumSeparation1 < 0.1 * averageSeparation1 ? 0.1 * minimumSeparation1 : minimumSeparation1;

        double 
        incrX2 = _delta * range2;
        incrX2 = incrX2 < averageSeparation2 ? incrX2 : minimumSeparation2 < 0.1 * averageSeparation2 ? 0.1 * minimumSeparation2 : minimumSeparation2;

        // Numerical differentiation using delta and interpolation
        BiCubicSpline bcs       = new BiCubicSplineWithTranspose(_x1, _x2, _y);
    	double[][]    dydx1     = new double[nPoints][mPoints];
    	double[][]    dydx2     = new double[nPoints][mPoints];
    	double[][]    d2ydx1dx2 = new double[nPoints][mPoints];

        double[] x1jp1 = new double[nPoints];
        double[] x1jm1 = new double[nPoints];
        for(int i = 0; i < nPoints; i++){
        	x1jp1[i] = _x1[i] + incrX1 < _x1[nPoints-1] ? _x1[i] + incrX1 : _x1[nPoints-1];
        	x1jm1[i] = _x1[i] - incrX1 > _x1[0]         ? _x1[i] - incrX1 : _x1[0];
        }

        double[] x2jp1 = new double[mPoints];
        double[] x2jm1 = new double[mPoints];
        for(int i = 0; i < mPoints; i++) {
        	x2jp1[i] = _x2[i] + incrX2 < _x2[mPoints-1] ? _x2[i] + incrX2 : _x2[mPoints-1];
        	x2jm1[i] = _x2[i] - incrX2 > _x2[0]         ? _x2[i] - incrX2 : _x2[0];
        }

        for(int i = 0; i < nPoints; i++) {
            for(int j = 0; j < mPoints; j++){
                dydx1[i][j]     = (bcs.evaluate(x1jp1[i], _x2[j])   - bcs.evaluate(x1jm1[i], _x2[j])) 
                					/ (x1jp1[i] - x1jm1[i]);
                dydx2[i][j]     = (bcs.evaluate(_x1[i], x2jp1[j]) - bcs.evaluate(_x1[i], x2jm1[j])) 
                					/ (x2jp1[j] - x2jm1[j]);
                d2ydx1dx2[i][j] = (bcs.evaluate(x1jp1[i], x2jp1[j]) - bcs.evaluate(x1jp1[i], x2jm1[j]) 
                					- bcs.evaluate(x1jm1[i], x2jp1[j]) + bcs.evaluate(x1jm1[i], x2jm1[j]))
                					/ ((x1jp1[i] - x1jm1[i]) * (x2jp1[j] - x2jm1[j]));
            }
        }
        
        return new double[][][] { dydx1, dydx2, d2ydx1dx2, };
	}

    public static int 			findInterval(double _x, double[] _xs) {
    	int nbPoints = _xs.length;

    	if(_x < _xs[0])
    		return 0;
    	if(_x > _xs[nbPoints - 1])
    		return nbPoints - 1;

        int interval = 0;

        while(true) // Safe thanks to previous tests, if _xs is correctly ordered, otherwise return negative!
            if(_x < _xs[interval+1])
                return interval;
            else if(interval++ > nbPoints - 2)
            	return -1;
    }
/*
    public static int 			findInterval(double _x, double[] _xs) {
    	int nPoints = _xs.length;

    	// Find grid surrounding the interpolation point
        int     gridn   = 0;
        int     counter = 1;
        boolean test    = true;

        while(test) {
            if(_x < _xs[counter]) {
                gridn = counter - 1;
                test  = false;
            } else {
                counter++;
                if(counter >= nPoints){
                    gridn = nPoints - 2;
                    test  = false;
                }
            }
        }
        
        return gridn;
    }
*/
    public static int 			findIntervalAlt(double _x, double[] _xs) {
		int j = _xs.length - 1;

		if(_x < _xs[0]) return 0;
		if(_x > _xs[j]) return j + 1;

		int tmp = 0;
		int i   = 0;

		do {
			if(i + 1 == j)
				break;

			if(_x > _xs[tmp]) {
				i   = tmp;
				tmp = i + (j - i) / 2;
			} else {
				j   = tmp;
				tmp = i + (j - i) / 2;
			}

			if (j == 0)
				i--;

		} while (true);

		return i + 1;
	}

	public  static double   	checkForViolationBounds(double _x, double _min, double _max, double _err) {
		if(Double.isNaN(_err))
			if( (_x < _min) || (_x > _max) )
				throw new IllegalArgumentException("x (" + _x + ") is outside the range of data points (" + _min + " to " + _max + ")");

		if (_x < _min) {
			if (Math.abs(_min - _x) <= Math.pow(10, Math.floor(Math.log10(Math.abs(_min)))) * _err) {
				_x = _min;
			} else {
				throw new IllegalArgumentException("x (" + _x + ") is outside the range of data points (" + _min + " to " + _max + ")");
			}
		}

		if (_x > _max) {
			if (Math.abs(_x - _max) <= Math.pow(10, Math.floor(Math.log10(Math.abs(_max)))) * _err) {
				_x = _max;
			} else {
				throw new IllegalArgumentException("x (" + _x + ") is outside the range of data points (" + _min + " to " + _max + ")");
			}
		}

		return _x;
	}

}
