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
 * @file     BiCubicSplineFast.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.maths.interpolation.functions.splines.cubic;

public class BiCubicSplineFast extends BiCubicSpline {

	public BiCubicSplineFast(int _n, int _m) {
		super(_n, _m);
	}
	public BiCubicSplineFast(double[] _x1, double[] _x2, double[][] _y) {
		super(_x1, _x2, _y);
		init(x1, x2, y);
	}

	public void     init(double[] _x1, double[] _x2, double[][] _y) {
		super.init(_x1, _x2, _y);

		double[] 
		yTmp = new double[dim2];
		for(int i = 0; i < dim1; i++) {
			for(int j = 0; j < dim2; j++)
				yTmp[j] = y[i][j];

			csn[i].init(x2, yTmp);
			d2ydx2[i] = csn[i].getDeriv();
		}
	}

	@Override
	public double   evaluate(double _x1, double _x2) {
		double[] yTmp = new double[dim1];

		for(int i = 0; i < dim1; i++)
			yTmp[i] = csn[i].evaluate(_x2);

		csm.init(x1, yTmp);

		return csm.evaluate(_x1);
	}

}
