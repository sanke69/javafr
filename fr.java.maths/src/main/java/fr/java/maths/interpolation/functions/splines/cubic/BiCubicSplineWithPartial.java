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

public class BiCubicSplineWithPartial extends BiCubicSplineWithTranspose {
	private PartialDerivative cspdy  = null;
	private PartialDerivative cspdyT = null;

	public BiCubicSplineWithPartial(double[] _x1, double[] _x2, double[][] _y) {
		super(_x1.length, _x2.length);
		init(_x1, _x2, _y);
	}

	public void 	init(double[] _x1, double[] _x2, double[][] _y) {
		super.initNoSpline(_x1, _x2, _y);

		cspdy  = new PartialDerivative(x1, x2, y);
		cspdyT = new PartialDerivative(x2, x1, yT);
	}

	@Override
	public double   evaluate(double _x1, double _x2) {
		double interpy  = cspdy.evaluate(_x1, _x2);
		double interpyT = cspdyT.evaluate(_x2, _x1);

		double averageY = (interpy + interpyT) / 2.0;

		return averageY;
//		return new double[] { averageY, interpy[1], interpyT[1], interpy[0], interpyT[0] };
	}

	public class PartialDerivative extends BiCubicSplineFast {

		public PartialDerivative(double[] _x1, double[] _x2, double[][] _y) {
			super(_x1, _x2, _y);
		}

	}

}
