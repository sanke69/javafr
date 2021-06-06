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
 * @file     TriCubicSpline.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.sdk.math.interpolation.functions.splines.cubic;

import fr.java.sdk.math.interpolation.FunctionInterpolator;

public class TriCubicSpline implements FunctionInterpolator.ThreeVars {

	public static TriCubicSpline[] createArray(int _k, int _n, int _m, int _l) {
		if (_n < 3 || _m < 3 || _l < 3)
			throw new IllegalArgumentException("A minimum of three x three x three data points is needed");

		TriCubicSpline[] array = new TriCubicSpline[_k];

		for (int i = 0; i < _k; i++)
			array[i] = new TriCubicSpline(_n, _m, _l);

		return array;
	}

	protected final int  			dim1, dim2, dim3;

	protected final double[] 		x1, x2, x3;
	protected final double[][][] 	y, d2ydx2;

	protected final BiCubicSpline[] bcsn;
	protected final CubicSpline     csm;

	public TriCubicSpline(int _n, int _m, int _l) {
		super();

		if(_n < 3 || _m < 3 || _l < 3)
			throw new IllegalArgumentException("The tabulated 3D array must have a minimum size of 3 X 3 X 3");

		dim1   = _n;
		dim2   = _m;
		dim3   = _l;

		x1     = new double[dim1];
		x2     = new double[dim2];
		x3     = new double[dim3];
		y      = new double[dim1][dim2][dim3];
		d2ydx2 = new double[dim1][dim2][dim3];

		bcsn   = BiCubicSpline.createArray(dim1, dim2, dim3);
		csm    = new CubicSpline(dim1);
	}
	public TriCubicSpline(double[] _x1, double[] _x2, double[] _x3, double[][][] _y) {
		this(_x1.length, _x2.length, _x3.length);
		init(_x1, _x2, _x3, _y);
	}

	public void 		init(double[] _x1, double[] _x2, double[] _x3, double[][][] _y) {
		if(_x1.length < 3 || _x2.length < 3 || _x3.length < 3)
			throw new IllegalArgumentException("The tabulated 3D array must have a minimum size of 3 X 3 X 3");
		if(_x1.length != _y.length)
			throw new IllegalArgumentException("Arrays x1 and y-row are of different length " + _x1.length + " " + _y.length);
		if(_x2.length != _y[0].length)
			throw new IllegalArgumentException("Arrays x2 and y-column are of different length " + _x2.length + " " + _y[0].length);
		if(_x3.length != _y[0][0].length)
			throw new IllegalArgumentException("Arrays x3 and y-column are of different length " + _x3.length + " " + _y[0][0].length);
		if(x1.length != _x1.length || x2.length != _x2.length || x3.length != _x3.length)
			throw new IllegalArgumentException("Original array length not matched by new array length");

		for(int i = 0; i < dim1; i++)
			x1[i] = _x1[i];

		for(int i = 0; i < dim2; i++)
			x2[i] = _x2[i];

		for(int i = 0; i < dim3; i++)
			x3[i] = _x3[i];

		for(int i = 0; i < dim1; i++)
			for(int j = 0; j < dim2; j++)
				for(int k = 0; k < dim3; k++)
					y[i][j][k] = _y[i][j][k];

		double[][]
		yTmp = new double[dim2][dim3];
		for(int i = 0; i < dim1; i++) {

			for(int j = 0; j < dim2; j++)
				for(int k = 0; k < dim3; k++)
					yTmp[j][k] = y[i][j][k];

			bcsn[i].init(x2, x3, yTmp);
			d2ydx2[i] = bcsn[i].getDeriv();
		}
	}

	@Override
	public double 		evaluate(double _x1, double _x2, double _x3) {
		double[] yTmp = new double[dim1];

		for(int i = 0; i < dim1; i++)
			yTmp[i] = bcsn[i].evaluate(_x2, _x3);

		csm.init(x1, yTmp);
		return csm.evaluate(_x1);
	}

	public double[][][] getDeriv() {
		return d2ydx2;
	}

}

