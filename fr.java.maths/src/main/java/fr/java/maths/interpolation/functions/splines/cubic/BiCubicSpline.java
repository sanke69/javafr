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
 * @file     BiCubicSpline.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.maths.interpolation.functions.splines.cubic;

import fr.java.maths.Numbers;
import fr.java.maths.interpolation.FunctionInterpolator;

public abstract class BiCubicSpline implements FunctionInterpolator.TwoVars {

	public static BiCubicSpline[] createArray(int _n, int _m, int _l) {
		if(_m < 3 || _l < 3)
			throw new IllegalArgumentException("A minimum of three x three data points is needed");

		BiCubicSpline[] array = new BiCubicSpline[_n];

		for(int i = 0; i < _n; i++)
			array[i] = new BiCubicSplineWithTranspose(_m, _l);

		return array;
	}

	protected final int        		dim1, dim2;

	protected final double[]   		x1, x2;
	protected final double[][] 		y, d2ydx2;

	protected final CubicSpline[] 	csn;
	protected final CubicSpline 	csm;

	public BiCubicSpline(int _n, int _m) {
		super();

		if(_n < 3 || _m < 3)
			throw new IllegalArgumentException("The data matrix must have a minimum size of 3 X 3");

		dim1   = _n;
		dim2   = _m;
		
		x1     = new double[dim1];
		x2     = new double[dim2];
		y      = new double[dim1][dim2];

		d2ydx2 = new double[dim1][dim2];

		csn    = CubicSpline.createArray(dim1, dim2);
		csm    = new CubicSpline(dim1);
	}
	public BiCubicSpline(double[] _x1, double[] _x2, double[][] _y) {
		this(_x1.length, _x2.length);
		init(Numbers.copy(_x1), Numbers.copy(_x2), Numbers.copy(_y));
	}

	public void 				init(double[] _x1, double[] _x2, double[][] _y) {
		if(_x1.length < 3 || _x2.length < 3)
			throw new IllegalArgumentException("The data matrix must have a minimum size of 3 X 3");
		if(_x1.length != _y.length)
			throw new IllegalArgumentException( "Arrays x1 and y-row are of different length " + _x1.length + " " + y.length);
		if(_x2.length != _y[0].length)
			throw new IllegalArgumentException( "Arrays x2 and y-column are of different length " + _x2.length + " " + y[0].length);
		if(x1.length != _x1.length || x2.length != _x2.length)
			throw new IllegalArgumentException("Original array length not matched by new array length");

		for (int i = 0; i < dim1; i++)
			x1[i] = _x1[i];

		for (int j = 0; j < dim2; j++)
			x2[j] = _x2[j];

		for (int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++)
				y[i][j]  = _y[i][j];
	}

//	public abstract double[] 	interpolate(double _x1, double _x2);

	public double[][] 			getDeriv() {
		return d2ydx2;
	}

}
