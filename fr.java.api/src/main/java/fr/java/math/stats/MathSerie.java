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
package fr.java.math.stats;

public interface MathSerie {

	public interface OneDim extends MathSerie {
		public double[] getY();
		public double   getY(int _idx);
	}
	public interface TwoDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY1(int _idx);
		public double     getY2(int _idx);
	}
	public interface ThreeDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY1(int _idx);
		public double     getY2(int _idx);
		public double     getY3(int _idx);
	}
	public interface NDims extends MathSerie {
		public double[][] getY();
		public double[]   getY(int _idx);
		public double     getY(int _xIdx, int _yIdx);
	}

	public int 		size();
	public int 		getLength();

	public double[] getX();
	public double   getX(int _idx);

}
