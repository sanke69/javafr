/** ************************************************************************ **\
 * Copyright (c) 2007-?XYZ Steve PECHBERTI                                    *
 *                                                                            *
 * @author <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>   *
 *                                                                            *
 * @section license License                                                   *
 *    [EN] This program is free software:                                     *
 *         you can redistribute it and/or modify it under the terms of        * 
 *         the GNU General Public License as published by                     *
 *         the Free Software Foundation, either version 3 of the License, or  *
 *         (at your option) any later version.                                *
 *         You should have received a copy of the GNU General Public License  *
 *         along with this program. If not, see                               *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *    [FR] Ce programme est un logiciel libre ; vous pouvez le redistribuer   * 
 *         ou le modifier suivant les termes de la GNU General Public License *
 *         telle que publiée par la Free Software Foundation ;                *
 *         soit la version 3 de la licence, soit (à votre gré) toute version  *
 *         ultérieure.                                                        *
 *         Vous devez avoir reçu une copie de la GNU General Public License   *
 *         en même temps que ce programme ; si ce n'est pas le cas, consultez *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *                                                                            *
 * @section disclaimer Disclaimer                                             *
 *    [EN] This program is distributed in the hope that it will be useful,    *
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.java.sdk.math.numbers.complex;

import java.text.NumberFormat;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.numbers.Complex;
import fr.java.sdk.math.Angles;

public class ComplexNumber extends Number implements Complex {
	private static final long serialVersionUID = -6589050305901548429L;

	public double re, im;

	public static Complex polar(final double _abs, final double _arg) {
		return new ComplexNumber(_abs * Math.cos(_arg), _abs * Math.sin(_arg));
	}
	public static Complex expi(final double _k) {
		return new ComplexNumber(Math.cos(_k), Math.sin(_k));
	}

	public ComplexNumber() { re = 0; im = 0; }
	public ComplexNumber(double _re) { re = _re; im = 0.0; }
	public ComplexNumber(double _re, double _im) { re = _re; im = _im; }
	public ComplexNumber(ComplexNumber _c) { re = _c.re; im = _c.im; }

	public double 	real() {
		return re;
	}
	public double 	imag() {
		return im;
	}

	public Complex 	equals(final double _d) {
		re = _d;
		im = _d;

		return this;
	}
	public Complex equals(final double _re, final double _im) {
		re = _re;
		im = _im;
		
		return this;
	}
	public Complex equals(final Complex _d) {
		re = _d.real();
		im = _d.imag();
		
		return this;
	}

	public Complex plus(final double _t) {
		return new ComplexNumber(re + _t, im);
	}
	public Complex plus(final double _re, final double _im) {
		return new ComplexNumber(re + _re, im + _im);
	}
	public Complex plus(final Complex _d) {
		return new ComplexNumber(re + _d.real(), im + _d.imag());
	}

	public Complex plusEquals(final double _t) {
		re += _t;
		return this;
	}
	public Complex plusEquals(final double _re, final double _im) {
		re += _re;
		im += _im;
		return this;
	}
	public Complex plusEquals(final Complex _d) {
		re += _d.real();
		im += _d.imag();
		return this;
	}

	public Complex add(final double _t) {
		return plusEquals(_t);
	}
	public Complex add(final double _re, final double _im) {
		return plusEquals(_re, _im);
	}
	public Complex add(final Complex _d) {
		return plusEquals(_d.real(), _d.imag());
	}

	public Complex minus(final double _t) {
		return new ComplexNumber(re - _t, im);
	}
	public Complex minus(final double _re, final double _im) {
		return new ComplexNumber(re - _re, im - _im);
	}
	public Complex minus(final Complex _d) {
		return new ComplexNumber(re - _d.real(), im - _d.imag());
	}

	public Complex minusEquals(final double _t) {
		re -= _t;
		return this;
	}
	public Complex minusEquals(final double _re, final double _im) {
		re -= _re;
		im -= _im;
		return this;
	}
	public Complex minusEquals(final Complex _d) {
		re -= _d.real();
		im -= _d.imag();
		return this;
	}

	public Complex substract(final double _t) {
		return minusEquals(_t);
	}
	public Complex substract(final double _re, final double _im) {
		return minusEquals(_re, _im);
	}
	public Complex substract(final Complex _d) {
		return minusEquals(_d.real(), _d.imag());
	}

	public Complex times(final double _t) {
		return new ComplexNumber(re * _t, im * _t);
	}
	public Complex times(final double _re, final double _im) {
		return new ComplexNumber(re * _re - im * _im, re * _im + im * _re);
	}
	public Complex times(final Complex _c) {
		return new ComplexNumber(re * _c.real() - im * _c.imag(), re * _c.imag() + im * _c.real());
	}

	public Complex timesEquals(final double _t) {
		re *= _t;
		im *= _t;
		return this;
	}
	public Complex timesEquals(final double _re, final double _im) {
		double tre = re, tim = im;
		re = tre * _re - tim * _im;
		im = tre * _im + tim * _re;
		return this;
	}
	public Complex timesEquals(final Complex _c) {
		double tre = re, tim = im;
		re = tre * _c.real() - tim * _c.imag();
		im = tre * _c.imag() + tim * _c.real();
		return this;
	}

	public Complex multiply(final double _t) {
		return timesEquals(_t);
	}
	public Complex multiply(final double _re, final double _im) {
		return timesEquals(_re, _im);
	}
	public Complex multiply(final Complex _c) {
		return timesEquals(_c);
	}

	public Complex divides(final double _d) {
		if(_d == 0) throw new RuntimeException();
		re /= _d;
		im /= _d;
		return this;
	}
	public Complex divides(final double _k_re, final double _k_im) throws Exception {
		if(_k_re == 0 || _k_im == 0) throw new Exception();
		re /= _k_re;
		im /= _k_im;
		
		return this;
	}
	public Complex divides(final Complex _c) {
		if(_c.real() == 0 && _c.imag() == 0) throw new RuntimeException();

		ComplexNumber tmp = new ComplexNumber(re, im);
		double Q  = _c.real()*_c.real() + _c.imag()*_c.imag();
		re = (tmp.re * _c.real() + tmp.im * _c.imag()) / Q;
		im = (tmp.im * _c.real() - tmp.re * _c.imag()) / Q;
		return this;
	}

	public Complex dividesEquals(Complex _other) {
		// TODO Auto-generated method stub
		return null;
	}
	public Complex dividesEquals(double _d) {
		return new ComplexNumber(re / _d, im / _d);
	}

	public double  abs() {
		return Math.sqrt(re*re + im*im);
	}
	public double  arg() {
		double a = Math.atan2(im, re);
		if(a < 0)
			a += 2.0 * Angles.pi;
		return a;
	}
	public Complex conjugate() {
		return new ComplexNumber(re, - im);
	}
	public Complex negate() {
        return new ComplexNumber(- re, - im);
}
	public Complex inverse() {
        return new ComplexNumber(1).divides(this);
	}
	
	public boolean isEqual(double _d) {
		return (re == _d && im == 0.0) ? true : false;
	}
	public boolean isEqual(Complex _c) {
		return (re == _c.real() && im == _c.imag()) ? true : false;
	}

	public boolean isDifferent(double _d) {
		return (re != _d || im != 0.0) ? true : false;
	}
	public boolean isDifferent(Complex _c) {
		return (re != _c.real() || im != _c.imag()) ? true : false;
	}

	@Override
	public int 		intValue() {
		return (int) re;
	}
	public int 		intValue(boolean _imaginary) {
		return (int) (_imaginary ? im : re);
	}
	@Override
	public long 	longValue() {
		return (long) re;
	}
	public long 	longValue(boolean _imaginary) {
		return (long) (_imaginary ? im : re);
	}
	@Override
	public float 	floatValue() {
		return (float) re;
	}
	public float 	floatValue(boolean _imaginary) {
		return (float) (_imaginary ? im : re);
	}
	@Override
	public double 	doubleValue() {
		return re;
	}
	public double 	doubleValue(boolean _imaginary) {
		return _imaginary ? im : re;
	}

	public Complex[] transposeComplexMatrix(Complex[] _c, int _nx, int _ny, boolean _asReal) {
		Complex[] tmp = new Complex[_nx * _ny];

		for(int i = 0, j = 0; i < _nx*_ny; i++, j = (int) (i / _nx)) {
			if(!_asReal)
				tmp[i] = _c[(i%_nx) * _ny + j].conjugate();
			else
				tmp[i] = _c[(i%_nx) * _ny + j];
		}

		return tmp;
	}

	public static Complex parseComplex(String _s) {
		throw new NotYetImplementedException();
	}
	public static Complex valueOf(String ss) {
		throw new NotYetImplementedException();
	}
	
	public final String toString(NumberFormat _nf) {
		return _nf.format(re) + " + i . " + _nf.format(im);
	}

}
