// http://rco.fr.nf/index.php/2016/07/10/tuto-trajectoire-interpol/
package fr.java.sdk.math.interpolation.functions.splines.cubic;

import java.util.ArrayList;
import java.util.List;

import fr.java.sdk.math.Numbers;

public class BiCubicInterpolation {
    private double[][] weights =   {{ 1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
						            {-3.0,0.0,0.0,3.0,0.0,0.0,0.0,0.0,-2.0,0.0,0.0,-1.0,0.0,0.0,0.0,0.0},
						            { 2.0,0.0,0.0,-2.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,-3.0,0.0,0.0,3.0,0.0,0.0,0.0,0.0,-2.0,0.0,0.0,-1.0},
						            { 0.0,0.0,0.0,0.0,2.0,0.0,0.0,-2.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,1.0},
						            {-3.0,3.0,0.0,0.0,-2.0,-1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,-3.0,3.0,0.0,0.0,-2.0,-1.0,0.0,0.0},
						            { 9.0,-9.0,9.0,-9.0,6.0,3.0,-3.0,-6.0,6.0,-6.0,-3.0,3.0,4.0,2.0,1.0,2.0},
						            {-6.0,6.0,-6.0,6.0,-4.0,-2.0,2.0,4.0,-3.0,3.0,3.0,-3.0,-2.0,-1.0,-1.0,-2.0},
						            { 2.0,-2.0,0.0,0.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
						            { 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,2.0,-2.0,0.0,0.0,1.0,1.0,0.0,0.0},
						            {-6.0,6.0,-6.0,6.0,-3.0,-3.0,3.0,3.0,-4.0,4.0,2.0,-2.0,-2.0,-2.0,-1.0,-1.0},
						            { 4.0,-4.0,4.0,-4.0,2.0,2.0,-2.0,-2.0,2.0,-2.0,-2.0,2.0,1.0,1.0,1.0,1.0}};

//  record GridCoefficient(double d1, double x1lo, double d2, double x2lo, double[][] coeff) {
    class GridCoefficient {
    	final double     d1, x1lo, d2, x2lo; 
    	final double[][] coeff;

    	GridCoefficient(double _d1, double _x1lo, double _d2, double _x2lo, double[][] _coeff) {
    		d1    = _d1;
    		x1lo  = _x1lo;
    		d2    = _d2;
    		x2lo  = _x2lo;
    		coeff = _coeff;
    	}
    	
    	double d1()        { return d1; }
    	double x1lo()      { return x1lo; }
    	double d2()        { return d2; }
    	double x2lo()      { return x2lo; }
    	double[][] coeff() { return coeff; }
    }
    
	private int 		 nPoints   = 0;   	                        // no. of x1 tabulated points
	private int 		 mPoints   = 0;   	                        // no. of x2 tabulated points
	private double[]   	 x1        = null;   	                    // x1 in tabulated function f(x1,x2)
	private double[]   	 x2        = null;                          // x2 in tabulated function f(x1,x2)
	private double[][] 	 y         = null;  	                    // y=f(x1,x2) tabulated function
	private double[][] 	 dydx1     = null;
	private double[][] 	 dydx2     = null;
	private double[][] 	 d2ydx1dx2 = null;

    private List<GridCoefficient> coeff     = new ArrayList<GridCoefficient>();

	public BiCubicInterpolation(double[] _x1, double[] _x2, double[][] _y) {
        this(_x1, _x2, _y, true);
	}
	public BiCubicInterpolation(double[] _x1, double[] _x2, double[][] _y, boolean _numerDiffOption) {
		super();

        initialize(Numbers.copy(_x1), Numbers.copy(_x2), Numbers.copy(_y), null, null, null);
        computeDerivatives(_numerDiffOption ? 1e-3 : Double.NaN);

        computeGridCoefficients();
	}
	public BiCubicInterpolation(double[] _x1, double[] _x2, double[][] _y, double[][] _dydx1, double[][] _dydx2, double[][] _d2ydx1dx2) {
		super();

        initialize(Numbers.copy(_x1), Numbers.copy(_x2), Numbers.copy(_y), Numbers.copy(_dydx1), Numbers.copy(_dydx2), Numbers.copy(_d2ydx1dx2));

        computeGridCoefficients();
	}

	//	Returns an interpolated value of y for a value of x
	//  	from a tabulated function y=f(x1,x2)
	public double   evaluate(double _x1, double _x2) {
		_x1 = Utils.checkForViolationBounds(_x1, x1[0], x1[nPoints-1], 5e-15);
		_x2 = Utils.checkForViolationBounds(_x2, x2[0], x2[mPoints-1], 5e-15);

        // Find grid surrounding the interpolation point
        int grid1 = Utils.findInterval(_x1, x1);
        int grid2 = Utils.findInterval(_x2, x2);
        int gridn = grid1 * (mPoints - 1) + grid2;

        double     distance1 = coeff.get(gridn).d1;
        double     x1lower   = coeff.get(gridn).x1lo;
        double     distance2 = coeff.get(gridn).d2;
        double     x2lower   = coeff.get(gridn).x2lo;
        double[][] gCoeff    = coeff.get(gridn).coeff;

        double x1Normalised = (_x1 - x1lower) / distance1;
        double x2Normalised = (_x2 - x2lower) / distance2;

        // interpolation
        double interpolatedValue = 0.0;           // interpolated value of y
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                interpolatedValue += gCoeff[i][j] * Math.pow(x1Normalised, i) * Math.pow(x2Normalised, j);

        double interpolatedDydx1 = 0.0;           // interpolated value of dy/dx1
        for(int i = 1; i < 4; i++)
            for(int j = 0; j < 4; j++)
                interpolatedDydx1 += i * gCoeff[i][j] * Math.pow(x1Normalised, i - 1) * Math.pow(x2Normalised, j);

        double interpolatedDydx2 = 0.0;           // interpolated value of dydx2
        for(int i = 0; i < 4; i++)
            for(int j = 1; j < 4; j++)
                interpolatedDydx2 += j * gCoeff[i][j] * Math.pow(x1Normalised, i) * Math.pow(x2Normalised, j - 1);

        double interpolatedD2ydx1dx2 = 0.0;       // interpolated value of d2y/dx1dx2
        for(int i = 1; i < 4; i++)
            for(int j = 1; j < 4; j++)
                interpolatedD2ydx1dx2 += i * j * gCoeff[i][j] * Math.pow(x1Normalised, i - 1) * Math.pow(x2Normalised, j - 1);

//      return new double[] { interpolatedValue, interpolatedDydx1, interpolatedDydx2, interpolatedD2ydx1dx2 };
        return interpolatedValue;
    }

	private void    initialize(double[] _x1, double[] _x2, double[][] _y, double[][] _dydx1, double[][] _dydx2, double[][] _d2ydx1dx2) {
	    int[]   nbPoints      = Utils.prepareData(_x1, _x2, _y, _dydx1, _dydx2, _d2ydx1dx2);
		boolean useDerivative = _dydx1 != null && _dydx2 != null && _d2ydx1dx2 != null;

	    nPoints   = nbPoints[0];
	    mPoints   = nbPoints[1];

	    x1        = new double[nPoints];
    	x2        = new double[mPoints];
    	y         = new double[nPoints][mPoints];

    	for(int i = 0; i < nPoints; i++)
        	x1[i] = _x1[i];

    	for(int j = 0; j < mPoints; j++)        		
        	x2[j] = _x2[j];

    	for(int i = 0; i < nPoints; i++) {
        	for(int j = 0; j < mPoints; j++)
            	y[i][j] = _y[i][j];
		
			if(useDerivative) {
			    dydx1     = new double[nPoints][mPoints];
			    dydx2     = new double[nPoints][mPoints];
			    d2ydx1dx2 = new double[nPoints][mPoints];
			
			    for(int j = 0; j < mPoints; j++) {
			        dydx1[i][j]     = _dydx1[i][j];
			        dydx2[i][j]     = _dydx2[i][j];
			        d2ydx1dx2[i][j] = _d2ydx1dx2[i][j];
				}
			}
    	}

    }

    private void    computeDerivatives(double _delta) {
        if(Double.isNaN(_delta)) {
        	double[][][] 
        	derivatives = Utils.compute1stDerivatives(x1, x2, y);
        	dydx1       = derivatives[0];
            dydx2       = derivatives[1];
            d2ydx1dx2   = derivatives[2];
        }

    	double[][][] 
    	derivatives = Utils.compute1stDerivativesWithInterpolation(x1, x2, y, _delta);
    	dydx1       = derivatives[0];
        dydx2       = derivatives[1];
        d2ydx1dx2   = derivatives[2];
	}

	private void    computeGridCoefficients() {
	    double[] yt         = new double[4];
	    double[] dydx1t     = new double[4];
	    double[] dydx2t     = new double[4];
	    double[] d2ydx1dx2t = new double[4];
	    double[] ct         = new double[16];
	    double[] xt         = new double[16];
	    double   d1         = 0.0;
	    double   d2         = 0.0;

	    for(int i = 0; i < nPoints - 1; i++) {
	        d1 = x1[i + 1] - x1[i];

	        for(int j = 0; j < mPoints -1; j++) {
	            d2 = x2[j] - x2[j+1];

	            // Calculate grid coefficients for 4-point grid square with point i,j at the top left corner
                double[][] cc = new double[4][4];

	            yt[0]         = y[i][j+1];
	            dydx1t[0]     = dydx1[i][j+1];
	            dydx2t[0]     = dydx2[i][j+1];
	            d2ydx1dx2t[0] = d2ydx1dx2[i][j+1];

	            yt[1]         = y[i+1][j+1];
	            dydx1t[1]     = dydx1[i+1][j+1];
	            dydx2t[1]     = dydx2[i+1][j+1];
	            d2ydx1dx2t[1] = d2ydx1dx2[i+1][j+1];

	            yt[2]         = y[i+1][j];
	            dydx1t[2]     = dydx1[i+1][j];
	            dydx2t[2]     = dydx2[i+1][j];
	            d2ydx1dx2t[2] = d2ydx1dx2[i+1][j];

	            yt[3]         = y[i][j];
	            dydx1t[3]     = dydx1[i][j];
	            dydx2t[3]     = dydx2[i][j];
	            d2ydx1dx2t[3] = d2ydx1dx2[i][j];

	            for(int k = 0; k < 4; k++){
	                xt[k]     = yt[k];
	                xt[k+4]   = dydx1t[k]*d1;
	                xt[k+8]   = dydx2t[k]*d2;
	                xt[k+12]  = d2ydx1dx2t[k]*d1*d2;
	            }

	            for(int k = 0; k < 16; k++){
	                double xh = 0.0;
	                for(int kk = 0; kk < 16; kk++)
	                    xh += weights[k][kk]*xt[kk];

	                ct[k] = xh;
	            }

	            int counter = 0;
	            for(int k = 0; k < 4; k++)
	                for(int kk = 0; kk < 4; kk++)
	                    cc[k][kk] = ct[counter++];

	            // Add grid coefficient 4x4 array to ArrayList
	            coeff.add( new GridCoefficient(d1, x1[i], d2, x2[j+1], cc) );
	        }
	    }
	}

}

