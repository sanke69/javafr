/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @file     CubicInterpolation.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.maths.interpolation.functions.splines.cubic;

import fr.java.maths.Numbers;

public class CubicInterpolation {
    private double[][] weights  = { {1d,0d,0d,0d}, {0d,0d,1d,0d}, {-3d,3d,-2d,-1d}, {2d,-2d,1d,1d} };
    
	private int        nPoints  = 0;
	private double[]   x        = null;
	private double[]   y        = null;
	private double[]   dydx     = null;

    private double[][] coeff    = null;

	public CubicInterpolation(double[] _x, double[] _y, boolean _numerDiffOption) {
		super();

        initialize(Numbers.copy(_x), Numbers.copy(_y), null);
        compute1stDerivative(_numerDiffOption ? 1e-3 : Double.NaN);

        coeff = computeGridCoefficients(_x, _y, dydx, weights);
	}
	public CubicInterpolation(double[] _x, double[] _y, double[] _dydx) {
		super();

        initialize(_x, _y, _dydx);

        coeff = computeGridCoefficients(_x, _y, _dydx, weights);
	}

	public double   			evaluate(double _x) {
		_x = Utils.checkForViolationBounds(_x, x[0], x[nPoints-1], 5e-15);

        // Find grid surrounding the interpolation point
        int gridn = Utils.findInterval(_x, x);

        // interpolation
        double xNormalised = (_x - x[gridn]) / (x[gridn+1] - x[gridn]);
        
        double interpolatedValue = 0.0;
        for(int i = 0; i < 4; i++)
            interpolatedValue += coeff[gridn][i] * Math.pow(xNormalised, i);

        double interpolatedDydx = 0.0;
        for(int i = 1; i < 4; i++)
            interpolatedDydx += i * coeff[gridn][i] * Math.pow(xNormalised, i - 1);

        return interpolatedValue;
//      return new double[] { interpolatedValue, interpolatedDydx, _x };
    }

	private void    			initialize(double[] _x, double[] _y, double[] _dydx) {
      	nPoints = Utils.prepareData(_x, _y, _dydx);

	    x       = new double[nPoints];
    	y       = new double[nPoints];
	    dydx    = new double[nPoints];

    	for(int i = 0; i < nPoints; i++) {
        	x[i] = _x[i];
            y[i] = _y[i];
        }

        if(_dydx != null)
            for(int j = 0; j < nPoints; j++)
               dydx[j] = _dydx[j];
    }

    private void   				compute1stDerivative(double _delta) {
        dydx = Double.isNaN(_delta) ? Utils.compute1stDerivative(x, y) : Utils.compute1stDerivativeWithInterpolation(x, y, _delta);
	}

	private static double[][] 	computeGridCoefficients(double[] _x, double[] _y, double[] _dydx, double[][] weights) {
     	int        nPoints = _x.length;
	    double[][] coeffs  = new double[nPoints][4];

	    double[]   xt = new double[4];
	    double     d1 = 0.0;

	    for(int i = 0; i < nPoints - 1; i++){
	        d1    = _x[i+1] - _x[i];

	        xt[0] = _y[i];
	        xt[1] = _y[i+1];
	        xt[2] = _dydx[i] * d1;
	        xt[3] = _dydx[i+1] * d1;

	        for(int k = 0; k < 4; k++){
                double xh = 0.0;
	            for(int kk = 0; kk < 4; kk++)
	                xh += weights[k][kk]*xt[kk];

	            coeffs[i][k] = xh;
	        }
	    }
	    
	    return coeffs;
	}

}

