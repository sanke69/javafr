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
 * @file     PolyCubicSplineFast.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.math.interpolation.functions.splines.cubic;

import java.lang.reflect.Array;

public class PolyCubicSplineFast{
	private int 					dimCount       = 0;						// number of the dimensions of the tabulated points array, y=f(x1,x2,x3 . . xn), i.e. n
    private int 					dimOne         = 0;						// xArray dimension in a recursive step

    private Object 					fOfX           = null;					// Tabulated values of y = f(x1,x2,x3 . . fn) as a multidimensional array of double [x1 length][x2 length] ... [xn length]
	private Object 					xArrays        = null;					// The variable arrays x1, x2, x3 . . . xn packed as an Object as a multidimensional array of double [][] where xArrays[0] = array of x1 values, xArrays[1] = array of x2 values etc

    private PolyCubicSplineFast[] 	pcs            = null;					// array of PolyCubicSplineFasts for use with recursive step
	private Object 					internalSpline = null;

	public PolyCubicSplineFast(Object _xArrays, Object _fOfX){
		resetData(_xArrays, _fOfX);
    }

	public void resetData(Object _xArrays, Object _fOfX) {
	    fOfX    = _fOfX;
	    xArrays = _xArrays;

	    dimCount = 1;
	    Object internalArrays = _fOfX;
        while(!((internalArrays  =  Array.get(internalArrays, 0)) instanceof Double)) dimCount++;

        if(xArrays instanceof double[] && dimCount == 1){
            double[][] xArraysTemp = new double[1][];
            xArraysTemp[0] = (double[]) xArrays;
            xArrays = (Object)xArraysTemp;
        } else
           if( ! (xArrays instanceof double[][]) )
        	   throw new IllegalArgumentException("xArrays should be a two dimensional array of doubles");

        double[][] xArray = (double[][]) xArrays;

        switch(this.dimCount){
            case 0: 	throw new IllegalArgumentException("data array must have at least one dimension");

            case 1:     CubicSpline cs = new CubicSpline(xArray[0], (double[]) fOfX);
            			internalSpline = (Object)cs;
                        break;

            case 2:     BiCubicSplineFast bcs = new BiCubicSplineFast(xArray[0], xArray[1], (double[][]) fOfX);
                        internalSpline = (Object)bcs;
                        break;

            default:    dimOne = Array.getLength(fOfX);

                        double[][] newXarrays = new double[dimCount - 1][];
                        for(int i = 0; i < dimCount - 1; i++)
                            newXarrays[i] = xArray[i + 1];

                        pcs = new PolyCubicSplineFast[dimOne];
                        for(int i = 0; i < dimOne; i++){
                            Object objT = (Object) Array.get(fOfX, i);
                            pcs[i] = new PolyCubicSplineFast(newXarrays, objT);
                        }
        }
	}

	public double interpolate(double[] unknownCoord){
	    if(unknownCoord.length != dimCount) 
	    	throw new IllegalArgumentException("Number of unknown value coordinates, " + unknownCoord.length + ", does not equal the number of tabulated data dimensions, " + dimCount);

        switch(dimCount) {
            case 0:     throw new IllegalArgumentException("data array must have at least one dimension");

            case 1:     if(internalSpline instanceof CubicSpline)
							return ((CubicSpline) internalSpline).evaluate(unknownCoord[0]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of CubicSpline.class");

            case 2:     if(internalSpline instanceof BiCubicSpline)
							return ((BiCubicSpline) internalSpline).evaluate(unknownCoord[0], unknownCoord[1]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of BiCubicSpline.class");

            default:    double[]   newCoord = new double[dimCount-1];
            			double[]   csArray  = new double[dimOne];
                        
                        for(int i = 0; i < dimCount-1; i++)
                            newCoord[i] = unknownCoord[i + 1];

                        for(int i = 0; i < dimOne; i++)
                            csArray[i] = pcs[i].interpolate(newCoord);
                     
                        // Perform simple cubic spline on the array of above returned interpolates
                        double[][]  xArray = (double[][]) xArrays;
                        CubicSpline ncs    = new CubicSpline(xArray[0], csArray);
                        return ncs.evaluate(unknownCoord[0]);
        }
	}

}

