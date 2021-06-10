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
 * @file     PolyCubicSpline.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.maths.interpolation.functions.splines.cubic;

import java.lang.reflect.Array;

public class PolyCubicSpline {

	private int   				nbDims
	= 0;
	private int    				dimOne          = 0;
	
	private Object 				xArrays         = null; // The variable arrays x1, x2, x3 . . . xn, packed as an Object as a multidimensional array of double [][], where xArrays[0] = array of x1 values, xArrays[1] = array of x2 values etc
	private Object 				fOfX            = null; // tabulated values of y = f(x1,x2,x3 . . fn) as a multidimensional array of double [x1 length][x2 length] ... [xn length]
	private Object 				internalDeriv   = null; // Object to store second derivatives as a multidimensional array of Objects the innermost three layers being double arrays

	private PolyCubicSpline[] 	pcs             = null;
	private Object 				internalSpline  = null;

	private boolean 			calculationDone = false;

	public PolyCubicSpline(Object _xArrays, Object _fOfX) {
		super();
		resetData(_xArrays, _fOfX);
	}

	public void 		resetData(Object _xArrays, Object _fOfX) {
		fOfX    = _fOfX;
		xArrays = _xArrays;

		Object internalArrays = fOfX;

		nbDims = 1;
		while(!((internalArrays = Array.get(internalArrays, 0)) instanceof Double))
			nbDims++;

		if(xArrays instanceof double[] && nbDims == 1) {
			double[][] 
			xArraysTemp    = new double[1][];
			xArraysTemp[0] = (double[]) xArrays;
			xArrays        = (Object) xArraysTemp;

		} else if (!(xArrays instanceof double[][]))
				throw new IllegalArgumentException("xArrays should be a two dimensional array of doubles");


		double[][] xArray = (double[][]) xArrays; // Special cast, for ease of use

		switch(nbDims) {
		case 0: 		throw new IllegalArgumentException("data array must have at least one dimension");

		case 1: 		CubicSpline cs = new CubicSpline					(xArray[0], (double[]) fOfX);

						internalSpline  = (Object) cs;
						internalDeriv   = (Object) cs.getDeriv();
						calculationDone = true;
						break;

		case 2: 		BiCubicSpline bcs = new BiCubicSplineFast			(xArray[0], xArray[1], (double[][]) fOfX);

						internalDeriv   = (Object) (bcs.getDeriv());
						internalSpline  = (Object) bcs;
						calculationDone = true;
						break;
		case 3: 		TriCubicSpline tcs = new TriCubicSpline				(xArray[0], xArray[1], xArray[2], (double[][][]) fOfX);

						internalDeriv   = (Object) (tcs.getDeriv());
						internalSpline  = (Object) tcs;
						calculationDone = true;
						break;
		case 4: 		QuadriCubicSpline qcs = new QuadriCubicSpline		(xArray[0], xArray[1], xArray[2], xArray[3], (double[][][][]) fOfX);

						internalDeriv   = (Object) (qcs.getDeriv());
						internalSpline  = (Object) qcs;
						calculationDone = true;
						break;
		default: 		Object obj   = fOfX;

						dimOne  = Array.getLength(obj);

						double[][]
						newXarrays = new double[nbDims - 1][];
						for(int i = 0; i < nbDims - 1; i++)
							newXarrays[i] = xArray[i + 1];

						Object[] 
						objDeriv = new Object[dimOne];

						if(calculationDone)
							objDeriv = (Object[]) internalDeriv;

						pcs = new PolyCubicSpline[dimOne];

						for(int i = 0; i < dimOne; i++) {
							Object objT = (Object) Array.get(obj, i);
							pcs[i] = new PolyCubicSpline(newXarrays, objT);
			
							if(calculationDone)
								pcs[i].setDeriv(objDeriv[i]);
							else
								objDeriv[i] = pcs[i].getDeriv();
						}

						internalDeriv   = (Object) objDeriv;
						calculationDone = true;
		}

	}

	public double 		interpolate(double[] unknownCoord) {
		if(unknownCoord.length != nbDims)
			throw new IllegalArgumentException("Number of unknown value coordinates, " +  unknownCoord.length + ", does not equal the number of tabulated data dimensions, " + nbDims);

		switch(nbDims) {
		case 0: 		throw new IllegalArgumentException("data array must have at least one dimension");

		case 1: 		if(internalSpline instanceof CubicSpline)
							return ((CubicSpline) internalSpline).evaluate(unknownCoord[0]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of CubicSpline.class");

		case 2: 		if(internalSpline instanceof BiCubicSpline)
							return ((BiCubicSpline) internalSpline).evaluate(unknownCoord[0], unknownCoord[1]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of BiCubicSpline.class");

		case 3: 		if(internalSpline instanceof TriCubicSpline)
							return ((TriCubicSpline) internalSpline).evaluate(unknownCoord[0], unknownCoord[1], unknownCoord[2]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of TriCubicSpline.class");

		case 4: 		if(internalSpline instanceof QuadriCubicSpline)
							return ((QuadriCubicSpline) internalSpline).interpolate(unknownCoord[0], unknownCoord[1], unknownCoord[2], unknownCoord[4]);
						else
							throw new IllegalArgumentException("'internalSpline' is not an instance of QuadriCubicSpline.class");

		default:		double[] newCoord = new double[nbDims - 1];
						double[] csArray  = new double[dimOne];

						for(int i = 0; i < nbDims - 1; i++)
							newCoord[i] = unknownCoord[i + 1];

						for(int i = 0; i < dimOne; i++)
							csArray[i] = pcs[i].interpolate(newCoord);

						// Perform simple cubic spline on the array of above returned interpolates
						double[][]  xArray = (double[][]) xArrays; // Special cast, for ease of use
						CubicSpline ncs    = new CubicSpline(xArray[0], csArray);

						return ncs.evaluate(unknownCoord[0]);
		}
	}

	public int 			getNumberOfDimensions() {
		return nbDims;
	}

	public void 		setDeriv(Object _internalDeriv) {
		internalDeriv = _internalDeriv;
	}
	public Object 		getDeriv() {
		return internalDeriv;
	}

}
