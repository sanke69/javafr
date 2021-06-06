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
 * @file     Complex.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.numbers;

public interface Complex {

	public double real();
	public double imag();

	public Complex equals(final double _d);
	public Complex equals(final double _re, final double _im);
	public Complex equals(final Complex _d);

	public Complex plus(final double _t);
	public Complex plus(final double _re, final double _im);
	public Complex plus(final Complex _d);

	public Complex plusEquals(final double _t);
	public Complex plusEquals(final double _re, final double _im);
	public Complex plusEquals(final Complex _d);

	public Complex add(final double _t);
	public Complex add(final double _re, final double _im);
	public Complex add(final Complex _d);

	public Complex minus(final double _t);
	public Complex minus(final double _re, final double _im);
	public Complex minus(final Complex _d);

	public Complex minusEquals(final double _t);
	public Complex minusEquals(final double _re, final double _im);
	public Complex minusEquals(final Complex _d);

	public Complex substract(final double _t);
	public Complex substract(final double _re, final double _im);
	public Complex substract(final Complex _d);

	public Complex times(final double _t);
	public Complex times(final double _re, final double _im);
	public Complex times(final Complex _c);

	public Complex timesEquals(final double _t);
	public Complex timesEquals(final double _re, final double _im);
	public Complex timesEquals(final Complex _c);

	public Complex multiply(final double _t);
	public Complex multiply(final double _re, final double _im);
	public Complex multiply(final Complex _c);

	public Complex divides(final double _t);
	public Complex divides(final double _re, final double _im) throws Exception;
	public Complex divides(final Complex _c);

	public Complex dividesEquals(Complex _other);
	public Complex dividesEquals(double _d);

	public double  abs();
	public double  arg();
	public Complex conjugate();
	public Complex negate();
	public Complex inverse();

	public default boolean isZero() { return real() == 0 && imag() == 0; }
	public default boolean isReal() { return imag() == 0; }
	public default boolean isImag() { return real() == 0; }
	public default boolean isNan()  { return Double.isNaN(real()) || Double.isNaN(imag()); }
	
	public boolean isEqual(double _d);
	public boolean isEqual(Complex _c);

	public boolean isDifferent(double _d);
	public boolean isDifferent(Complex _c);

	// 
	public int 		intValue();
	public int 		intValue(boolean _imaginary);
	public long 	longValue();
	public long 	longValue(boolean _imaginary);
	public float 	floatValue();
	public float 	floatValue(boolean _imaginary);
	public double 	doubleValue();
	public double 	doubleValue(boolean _imaginary);

	public Complex[] transposeComplexMatrix(Complex[] _c, int _nx, int _ny, boolean _asReal) ;

}
