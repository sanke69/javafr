package fr.java.maths.interpolation.functions.splines.cubic;

import fr.java.math.interpolation.FunctionInterpolator;
import fr.java.maths.Numbers;

public class CubicSpline implements FunctionInterpolator.OneVar {
	public static double potentialRoundingError = 5e-15;

	public static CubicSpline[] createArray(int _n, int _m) {
    	if(_m < 3)
    		throw new IllegalArgumentException("A minimum of three data points is needed");

    	CubicSpline[] a = new CubicSpline[_n];
    	for(int i = 0; i < _m; i++)
        	a[i] = new CubicSpline(_m);

    	return a;
	}

	protected int      			nPoints;

	protected final double[] 	x, y;
	protected double[] 			d2ydx2;

	public CubicSpline(int _nPoints) {
		super();

		if(_nPoints < 3) throw new IllegalArgumentException("A minimum of three data points is needed");
		
		nPoints = _nPoints;

		x       = new double[nPoints];
		y       = new double[nPoints];
		d2ydx2  = new double[nPoints];
	}
	public CubicSpline(double[] _x, double[] _y) {
		this(_x.length);
		init(Numbers.copy(_x), Numbers.copy(_y), Double.NaN, Double.NaN);
	}

	public void 			init(double[] _x, double[] _y){
		init(_x, _y, Double.NaN, Double.NaN);
	}
	public void 			init(double[] _x, double[] _y, double _yp1, double _ypn) {
		if(_x.length < 3) throw new IllegalArgumentException("A minimum of three data points is needed");
    	if(_x.length != _y.length) throw new IllegalArgumentException("Arrays x and y are of different length");
    	if(_x.length !=  x.length) throw new IllegalArgumentException("Original array length not matched by new array length");

    	int nbPoints = Utils.prepareData(_x, _y, null);

		System.arraycopy(_x, 0, x, 0, nbPoints);
		System.arraycopy(_y, 0, y, 0, nbPoints);
//    	for(int i = 0; i< nbPoints; i++){
//        	x[i] = _x[i];
//        	y[i] = _y[i];
//    	}

		nPoints = Numbers.checkForIdenticalPoints(x, y);
		d2ydx2  = Utils.compute2ndDerivative(x, y, _yp1, _ypn);
	}

	@Override
	public double   		evaluate(double _x) {
    	if( _x < x[0] )
	        return y[0];
//        	return new double[] { y[0], 0d };
    	if( _x > x[nPoints-1] )
	        return y[nPoints-1];
//    		return new double[] { y[nPoints-1], 0d };

    	if( (_x < x[0]) || (_x > x[nPoints-1]) )
	        throw new IllegalArgumentException("x (" + _x + ") is outside the range of data points (" + x[0] + " to " + x[nPoints-1] + ")");

        double h = 0.0D, b = 0.0D, a = 0.0D, yx = 0.0D, dydx = 0.0D;
    	int    k = 0, klo = 0, khi = nPoints - 1;

    	while(khi-klo > 1) {
	    	k = ( khi + klo ) >> 1;
	    	if(x[k] > _x)
		    	khi = k;
	    	else
		    	klo = k;
    	}

    	h = x[khi] - x[klo];

    	if(h == 0.0)
        	throw new IllegalArgumentException("Two values of x are identical: point "+klo+ " ("+x[klo]+") and point "+khi+ " ("+x[khi]+")" );

    	a    = (x[khi] - _x) / h;
    	b    = (_x - x[klo]) / h;
    	yx   = a * y[klo] + b * y[khi] +((a*a*a - a) * d2ydx2[klo] + (b*b*b - b) * d2ydx2[khi]) * (h * h) / 6.0;
    	dydx = (y[khi] - y[klo]) / h - ((3 * a*a - 1.0) * d2ydx2[klo] - (3 * b*b - 1.0) * d2ydx2[khi]) * h / 6.0;

    	return yx;
//  	return new double[] { yx, dydx };
	}

	public double[] 		getDeriv() { return d2ydx2; }

}
