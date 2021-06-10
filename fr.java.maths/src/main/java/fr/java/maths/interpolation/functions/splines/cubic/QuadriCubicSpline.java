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
 * @file     QuadriCubicSpline.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.maths.interpolation.functions.splines.cubic;

public class QuadriCubicSpline {
	protected final int 				dim1, dim2, dim3, dim4;

	protected final double[] 			x1, x2, x3, x4;
	protected final double[][][][] 		y, d2ydx2;

	protected final TriCubicSpline[] 	tcsn;
	protected final CubicSpline 		csm;

	public QuadriCubicSpline(int _n, int _m, int _l, int _k) {
		super();

		if(_n < 3 || _m < 3 || _l < 3 || _k < 3)
			throw new IllegalArgumentException("The tabulated 4D array must have a minimum size of [ 3, 3, 3, 3 ]");

		dim1   = _n;
		dim2   = _m;
		dim3   = _l;
		dim4   = _k;

		x1     = new double[dim1];
		x2     = new double[dim2];
		x3     = new double[dim3];
		x4     = new double[dim4];
		y      = new double[dim1][dim2][dim3][dim4];
		d2ydx2 = new double[dim1][dim2][dim3][dim4];

		tcsn   = TriCubicSpline.createArray(dim1, dim2, dim3, dim4);
		csm    = new CubicSpline(dim1);
	}
	public QuadriCubicSpline(double[] _x1, double[] _x2, double[] _x3, double[] _x4, double[][][][] _y) {
		this(_x1.length, _x2.length, _x3.length, _x4.length);
		init(_x1, _x2, _x3, _x4, _y);
	}

	public double[][][][] 	getDeriv() {
		return d2ydx2;
	}

	public void 			init(double[] _x1, double[] _x2, double[] _x3, double[] _x4, double[][][][] _y) {
		if(_x1.length < 3 || _x2.length < 3 || _x3.length < 3 || _x4.length < 3)
			throw new IllegalArgumentException("The tabulated 3D array must have a minimum size of 3 X 3 X 3");
		if(_x1.length != _y.length)
			throw new IllegalArgumentException("Arrays x1 and y-row are of different length " + _x1.length + " " + _y.length);
		if(_x2.length != _y[0].length)
			throw new IllegalArgumentException("Arrays x2 and y-column are of different length " + _x2.length + " " + _y[0].length);
		if(_x3.length != _y[0][0].length)
			throw new IllegalArgumentException("Arrays x3 and y-column are of different length " + _x3.length + " " + _y[0][0].length);
		if(_x4.length != _y[0][0][0].length)
			throw new IllegalArgumentException("Arrays x3 and y-column are of different length " + _x4.length + " " + _y[0][0][0].length);
		if(x1.length != _x1.length || x2.length != _x2.length || x3.length != _x3.length || x4.length != _x4.length)
			throw new IllegalArgumentException("Original array length not matched by new array length");

		for(int i = 0; i < dim1; i++)
			x1[i] = _x1[i];

		for(int i = 0; i < dim2; i++)
			x2[i] = _x2[i];

		for(int i = 0; i < dim3; i++)
			x3[i] = _x3[i];

		for(int i = 0; i < dim4; i++)
			x4[i] = _x4[i];

		for(int i = 0; i < dim1; i++)
			for(int j = 0; j < dim2; j++)
				for(int k = 0; k < dim3; k++)
					for (int l = 0; l < dim4; l++)
						y[i][j][k][l] = _y[i][j][k][l];

		double[][][] yTmp = new double[dim2][dim3][dim4];
		for (int i = 0; i < dim1; i++) {

			for (int j = 0; j < dim2; j++)
				for (int k = 0; k < dim3; k++)
					for (int l = 0; l < dim4; l++)
						yTmp[j][k][l] = y[i][j][k][l];

			tcsn[i].init(x2, x3, x4, yTmp);
			d2ydx2[i] = tcsn[i].getDeriv();
		}
	}

	public double 			evaluate(double _x1, double _x2, double _x3, double _x4) {
		return interpolate(_x1, _x2, _x3, _x4);
	}
	public double 			interpolate(double _x1, double _x2, double _x3, double _x4) {
		double[] yTmp = new double[dim1];

		for(int i = 0; i < dim1; i++)
			yTmp[i] = tcsn[i].evaluate(_x2, _x3, _x4);

		csm.init(x1, yTmp);

		return csm.evaluate(_x1);
	}

}
