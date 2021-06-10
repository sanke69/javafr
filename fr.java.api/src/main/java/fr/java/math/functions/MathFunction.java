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
**/
package fr.java.math.functions;

public interface MathFunction {

	public static interface OneVar    extends MathFunction {
		public double evaluate(double _x);

		public interface WithDerivative extends MathFunction.OneVar {
			public int 		      getDerivativeDegree();

			public default double derivate(double _x) { return derivate(_x, 1); } 
			public double 		  derivate(double _x, int _nth);
		}

		public interface WithIntegral extends MathFunction.OneVar {
		   public double integrate(double _x0, double _x1);
		}

	}
	public static interface TwoVars   extends MathFunction {
		public double evaluate(double _x1, double _x2);
	}
	public static interface ThreeVars extends MathFunction {
		public double evaluate(double _x1, double _x2, double _x3);
	}
	public static interface FourVars  extends MathFunction {
		public double evaluate(double _x1, double _x2, double _x3);
	}
	public static interface PolyVars  extends MathFunction {
		public double evaluate(double... _xs);
	}

}
