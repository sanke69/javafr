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
package fr.java.patterns.measurable;

public interface Modifier {
	public static final Modifier Yocto		= Standard.Yocto;
	public static final Modifier Zepto 		= Standard.Zepto;
	public static final Modifier Atto		= Standard.Atto;
	public static final Modifier Femto 		= Standard.Femto;
	public static final Modifier Pico 		= Standard.Pico;
	public static final Modifier Nano 		= Standard.Nano;
	public static final Modifier Micro 		= Standard.Micro;
	public static final Modifier Milli		= Standard.Milli;
	public static final Modifier Centi		= Standard.Centi;
	public static final Modifier Deci		= Standard.Deci;
	public static final Modifier Unit		= Standard.Unit;
	public static final Modifier Deca		= Standard.Deca;
	public static final Modifier Dozen		= Standard.Dozen;
	public static final Modifier Hecto		= Standard.Hecto;
	public static final Modifier Sixty		= Standard.Sixty;
	public static final Modifier FiveDoze	= Standard.FiveDozen;
	public static final Modifier Kilo		= Standard.Kilo;
	public static final Modifier SixtySqr	= Standard.SixtySqr;
	public static final Modifier Mega		= Standard.Mega;
	public static final Modifier Giga		= Standard.Giga;
	public static final Modifier Tera		= Standard.Tera;
	public static final Modifier Peta		= Standard.Peta;
	public static final Modifier Exa		= Standard.Exa;
	public static final Modifier Zetta		= Standard.Zetta;
	public static final Modifier Yotta		= Standard.Yotta;

	public enum Standard implements Modifier {
		Yocto		(                                0.000_000_000_000_000_000_000_001),
		Zepto		(                                0.000_000_000_000_000_000_001),
		Atto		(                                0.000_000_000_000_000_001),
		Femto		(                                0.000_000_000_000_001),
		Pico		(                                0.000_000_000_001),
		Nano		(                                0.000_000_001),
		Micro		(                                0.000_001),
		Milli		(                                0.001),
		Centi		(                                0.01),
		Deci		(                                0.1),
		Unit		(                                1.0),
		Deca		(                               10.0),
		Hecto		(                              100.0),
		Kilo		(                            1_000.0),
		Mega		(                        1_000_000.0),
		Giga        (                    1_000_000_000.0),
		Tera        (                1_000_000_000_000.0),
		Peta        (            1_000_000_000_000_000.0),
		Exa         (        1_000_000_000_000_000_000.0),
		Zetta       (    1_000_000_000_000_000_000_000.0),
		Yotta       (1_000_000_000_000_000_000_000_000.0),

		Quarter		(                                0.25),
		Half		(                                0.5),
		Unitary		(                                1.0),
		Double		(                                2.0),
		Triple		(                                3.0),
		Quadruple	(                                4.0),
		Quintuple	(                                5.0),

		Dozen		(                               12.0),
		FiveDozen	(                               60.0),
		Sixty		(                               60.0),
		SixtySqr	(                            3_600.0),;

		double coefficient;

		Standard(double _d) { coefficient = _d; }

		public double getValue() { return coefficient; }
		public double apply(double _value) { return coefficient * _value; }

	}

	public static Modifier  of(final double _coef) {;
		return new Modifier() { @Override public double apply(double _value) { return _coef * _value; } };
	}
	public static Modifier  pow10(int _exp) {
		return Modifier.of(Math.pow(10, _exp));
	}

	public default Modifier mult(double... _vals) {
		double product = 1;
		for(double v : _vals)
			product *= v;

		return Modifier.of(product);
	}

	public double apply(double _value);

}
