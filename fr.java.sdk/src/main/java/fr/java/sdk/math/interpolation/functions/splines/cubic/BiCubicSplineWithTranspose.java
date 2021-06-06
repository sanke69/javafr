/**********************************************************
*
*   BiCubicSpline.java
*
*   Class for performing an interpolation on the tabulated
*   function y = f(x1,x2) using a natural bicubic spline
*   Assumes second derivatives at end points = 0 (natural spine)
*
*   See BiCubicSplineFast.java for a faster running version
*       (http://www.ee.ucl.ac.uk/~mflanaga/java/BiCubicSplineFast.html)
*
*   WRITTEN BY: Dr Michael Thomas Flanagan
*
*   DATE:	May 2002
*   UPDATE: 20 May 2003, 17 February 2006, 27 July 2007, 4 December 2007, 21 September 2008, 31 October 2009, 5 January 2011
*
*   DOCUMENTATION:
*   See Michael Thomas Flanagan's Java library on-line web page:
*   http://www.ee.ucl.ac.uk/~mflanaga/java/BiCubicSpline.html
*   http://www.ee.ucl.ac.uk/~mflanaga/java/
*
*   Copyright (c) 2003 - 2011   Michael Thomas Flanagan
*
*   PERMISSION TO COPY:
*
* Permission to use, copy and modify this software and its documentation for NON-COMMERCIAL purposes is granted, without fee,
* provided that an acknowledgement to the author, Dr Michael Thomas Flanagan at www.ee.ucl.ac.uk/~mflanaga, appears in all copies
* and associated documentation or publications.
*
* Redistributions of the source code of this source code, or parts of the source codes, must retain the above copyright notice, this list of conditions
* and the following disclaimer and requires written permission from the Michael Thomas Flanagan:
*
* Redistribution in binary form of all or parts of this class must reproduce the above copyright notice, this list of conditions and
* the following disclaimer in the documentation and/or other materials provided with the distribution and requires written permission from the Michael Thomas Flanagan:
*
* Dr Michael Thomas Flanagan makes no representations about the suitability or fitness of the software for any or for a particular purpose.
* Dr Michael Thomas Flanagan shall not be liable for any damages suffered as a result of using, modifying or distributing this software
* or its derivatives.
*
***************************************************************************************/

package fr.java.sdk.math.interpolation.functions.splines.cubic;

public class BiCubicSplineWithTranspose extends BiCubicSpline {
	protected final double[][]  	yT;
	protected final double[][]  	d2ydx2T;

	protected final CubicSpline[] 	csnT;
	protected final CubicSpline 	csmT;

	public BiCubicSplineWithTranspose(int _n, int _m) {
		super(_n, _m);

		// Transpose part
		yT      = new double[dim2][dim1];
		d2ydx2T = new double[dim2][dim1];

		csnT    = CubicSpline.createArray(dim2, dim1);
		csmT    = new CubicSpline(dim2);
	}
	public BiCubicSplineWithTranspose(double[] _x1, double[] _x2, double[][] _y) {
		this(_x1.length, _x2.length);
		init(_x1, _x2, _y);
	}

	public void 	initNoSpline(double[] _x1, double[] _x2, double[][] _y) {
		super.init(_x1, _x2, _y);

		// Transpose
		for(int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++)
				yT[j][i] = _y[i][j];
	}
	public void 	init(double[] _x1, double[] _x2, double[][] _y) {
		initNoSpline(_x1, _x2, _y);

		double[]

		yTmp = new double[dim2];
		for(int i = 0; i < dim1; i++) {
			for(int j = 0; j < dim2; j++)
				yTmp[j] = y[i][j];

			csn[i].init(x2, yTmp);
			d2ydx2[i] = csn[i].getDeriv();
		}

		yTmp = new double[dim1];
		for (int i = 0; i < dim2; i++) {
			for (int j = 0; j < dim1; j++)
				yTmp[j] = yT[i][j];

			csnT[i].init(x1, yTmp);
			d2ydx2T[i] = csnT[i].getDeriv();
		}
	}

	public double   evaluate(double _x1, double _x2) {
		double Value, ValueT;

		// Original
		double[] 

		yTmp = new double[dim1];
		for (int i = 0; i < dim1; i++)
			yTmp[i] = csn[i].evaluate(_x2);

		csm.init(x1, yTmp);
		Value = csm.evaluate(_x1);

		// Transposed
		yTmp = new double[dim2];
		for (int i = 0; i < dim2; i++)
			yTmp[i] = csnT[i].evaluate(_x1);

		csmT.init(x2, yTmp);
		ValueT = csmT.evaluate(_x2);

		// Result
//		return new double[] { (Value + ValueT) / 2.0, Value, ValueT, _x1, _x2 };
		return (Value + ValueT) / 2.0;
	}

}
