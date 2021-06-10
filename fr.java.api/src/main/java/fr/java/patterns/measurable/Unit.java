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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum Unit {
	// S.I. UNITS
	// 7 Initial Variables
	SECOND						("s", 		null, 					Modifier.Unit,			// (time)     				
								EnumSet.of(Measure.Type.TIME, Measure.Type.TIMESTAMP),
								Arrays.asList()),
	METRE						("m", 		null, 					Modifier.Unit, 			// (distance)
								EnumSet.of(Measure.Type.DISTANCE),
								Arrays.asList()),
	KILOGRAM					("kg", 		null, 					Modifier.Unit, 			// (mass)
								EnumSet.of(Measure.Type.MASS), 
								Arrays.asList()),
	KELVIN						("K", 		null, 					Modifier.Unit, 			// (temperature)
								EnumSet.of(Measure.Type.MASS), 
								Arrays.asList()),
	MOLE 						("mol", 	null, 					Modifier.Unit, 			// (number of particules)
								EnumSet.of(Measure.Type.MASS),
								Arrays.asList()),
	AMPERE						("A", 		null, 					Modifier.Unit, 			// (electric intensity)
								EnumSet.of(Measure.Type.MASS),
								Arrays.asList()),
	CANDELA         			("cd", 		null, 					Modifier.Unit,			// ()
								EnumSet.of(Measure.Type.MASS),
								Arrays.asList()),
	
	// TIME UNITS
	NANOSECOND					("ns", 		SECOND, 				Modifier.Nano,    				EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	MICROSECOND					("µs", 		SECOND, 				Modifier.Micro,    				EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	MILLISECOND					("ms", 		SECOND, 				Modifier.Milli,    				EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	MINUTE						("m", 		SECOND, 				Modifier.Sixty,    				EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	HOUR						("h", 		SECOND, 				Modifier.SixtySqr, 				EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	DAY							("d", 		SECOND, 				HOUR.ratio.mult(24.0), 			EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	WEEK						("w", 		SECOND, 				DAY.ratio.mult(7.0), 			EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	MONTH						("M", 		SECOND, 				WEEK.ratio.mult(4.0), 			EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	YEAR						("y", 		SECOND, 				MONTH.ratio.mult(12.0), 		EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	DECADE						("D", 		SECOND, 				YEAR.ratio.mult(10.0), 			EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	CENTURY						("C", 		SECOND, 				YEAR.ratio.mult(100.0), 		EnumSet.of(Measure.Type.TIME), Arrays.asList()),
	MILLENNIUM					("M", 		SECOND, 				YEAR.ratio.mult(1000.0), 		EnumSet.of(Measure.Type.TIME), Arrays.asList()),

	// LENGTH - DISTANCE UNITS
	YOCTOMETRE					("ym", 		METRE, 					Modifier.Yocto, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	ZEPTOMETRE					("zm", 		METRE, 					Modifier.Zepto, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	ATTOMETRE					("am", 		METRE, 					Modifier.Atto, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	FEMTOMETRE					("fm", 		METRE, 					Modifier.Femto, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	PICOMETRE					("pm", 		METRE, 					Modifier.Pico, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	NANOMETRE					("nm", 		METRE, 					Modifier.Nano, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	MICROMETRE					("µm", 		METRE, 					Modifier.Micro, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	MILLIMETRE					("mm", 		METRE, 					Modifier.Milli, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	CENTIMETRE					("cm", 		METRE, 					Modifier.Centi, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	DECIMETRE					("dm", 		METRE, 					Modifier.Deci, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	DECAMETRE					("Dm", 		METRE, 					Modifier.Deca, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	HECTOMETRE					("Hm", 		METRE, 					Modifier.Hecto, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	KILOMETRE					("Km", 		METRE, 					Modifier.Kilo, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	MEGAMETRE					("Mm", 		METRE, 					Modifier.Mega, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	GIGAMETRE					("Gm", 		METRE, 					Modifier.Giga, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	TERAMETRE					("Tm", 		METRE, 					Modifier.Tera, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	PETAMETRE					("Pm", 		METRE, 					Modifier.Peta, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	EXAMETRE					("Em", 		METRE, 					Modifier.Exa, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	ZETTAMETRE					("Zm", 		METRE, 					Modifier.Zetta, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	YOTTAMETRE					("Ym", 		METRE, 					Modifier.Yotta, 				EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),

	ANGSTROM					("°A°", 	METRE, 					Modifier.pow10(- 10), 			EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),

	FEET						("f", 		METRE, 					f -> 2.33 * f, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),

	// DEPTH UNITS
	FATHOMS						("F", 		METRE, 					f -> 2.33 * f, 					EnumSet.of(Measure.Type.DISTANCE), Arrays.asList()),
	
	
	// WEIGHT UNITS
	GRAM						("g", 		KILOGRAM, 				Modifier.Milli, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	YOCTOGRAM					("yg", 		GRAM, 					Modifier.Yocto, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	ZEPTOGRAM					("zg", 		GRAM, 					Modifier.Zepto, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	ATTOGRAM					("ag", 		GRAM, 					Modifier.Atto, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	FEMTOGRAM					("fg", 		GRAM, 					Modifier.Femto, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	PICOGRAM					("pg", 		GRAM, 					Modifier.Pico, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	NANOGRAM					("ng", 		GRAM, 					Modifier.Nano, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	MICROGRAM					("µg", 		GRAM, 					Modifier.Micro, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	MILLIGRAM					("mg", 		GRAM, 					Modifier.Milli, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	CENTIGRAM					("cg", 		GRAM, 					Modifier.Centi, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	DECIGRAM					("dg", 		GRAM, 					Modifier.Deci, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	DECAGRAM					("dag", 	GRAM, 					Modifier.Deca, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	HECTOGRAM					("hg", 		GRAM, 					Modifier.Hecto, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	MEGAGRAM					("kg", 		GRAM, 					Modifier.Mega, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	GIGAGRAM					("Gg", 		GRAM, 					Modifier.Giga, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	TERAGRAM					("Tg", 		GRAM, 					Modifier.Tera, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	PETAGRAM					("Pg", 		GRAM, 					Modifier.Peta, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	EXAGRAM						("Eg", 		GRAM, 					Modifier.Exa, 					EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	ZETTAGRAM					("Zg", 		GRAM, 					Modifier.Zetta, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),
	YOTTAGRAM					("Yg", 		GRAM, 					Modifier.Yotta, 				EnumSet.of(Measure.Type.MASS), Arrays.asList()),

	// TEMPERATURE UNITS
	CELCIUS						("°C", 		KELVIN, 				(C) -> C - 273.12, 				EnumSet.of(Measure.Type.TEMPERATURE), Arrays.asList()),
	FARENHEIT					("°F", 		KELVIN, 				Modifier.Yotta, 				EnumSet.of(Measure.Type.TEMPERATURE), Arrays.asList()),

	// AREA UNITS
	SQUARE_METRE				("m2", 		null, 					Modifier.Unit, 					EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_YOCTOMETRE			("ym2", 	SQUARE_METRE, 			Modifier.pow10(- 48), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_ZEPTOMETRE			("zm2", 	SQUARE_METRE, 			Modifier.pow10(- 42), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_ATTOMETRE			("am2", 	SQUARE_METRE, 			Modifier.pow10(- 36), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_FEMTOMETRE			("fm2", 	SQUARE_METRE, 			Modifier.pow10(- 30), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_PICOMETRE			("pm2", 	SQUARE_METRE, 			Modifier.pow10(- 24), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_NANOMETRE			("nm2", 	SQUARE_METRE, 			Modifier.pow10(- 18), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_MICROMETRE			("µm2", 	SQUARE_METRE, 			Modifier.pow10(- 12), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_MILLIMETRE			("mm2", 	SQUARE_METRE, 			Modifier.pow10(-  6), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_CENTIMETRE			("cm2", 	SQUARE_METRE, 			Modifier.pow10(-  4), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_DECIMETRE			("dm2", 	SQUARE_METRE, 			Modifier.pow10(-  2), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_DECAMETRE			("dam2", 	SQUARE_METRE, 			Modifier.pow10(   2), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_HECTOMETRE			("hm2", 	SQUARE_METRE, 			Modifier.pow10(   4), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_KILOMETRE			("km2", 	SQUARE_METRE, 			Modifier.pow10(   6), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_MEGAMETRE			("Mm2", 	SQUARE_METRE, 			Modifier.pow10(  12), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_GIGAMETRE			("Gm2", 	SQUARE_METRE, 			Modifier.pow10(  18), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_TERAMETRE			("Tm2", 	SQUARE_METRE, 			Modifier.pow10(  24), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_PETAMETRE			("Pm2", 	SQUARE_METRE, 			Modifier.pow10(  30), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_EXAMETRE				("Em2", 	SQUARE_METRE, 			Modifier.pow10(  36), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_ZETTAMETRE			("Zm2", 	SQUARE_METRE, 			Modifier.pow10(  42), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_YOTTAMETRE			("Ym2", 	SQUARE_METRE, 			Modifier.pow10(  48), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	
	ARE							("a", 		SQUARE_DECAMETRE, 		Modifier.Unit, 					EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	BARN						("b", 		SQUARE_DECAMETRE, 		Modifier.pow10(- 28), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	CENTIARE					("ca", 		ARE, 					Modifier.Centi, 				EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	DECIARE						("da", 		ARE, 					Modifier.Deci, 					EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	DECARE						("daa", 	ARE, 					Modifier.Deca, 					EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	HECTARE						("ha", 		ARE, 					Modifier.Hecto, 				EnumSet.of(Measure.Type.AREA), Arrays.asList()),

	SQUARE_INCH					("inch2", 	SQUARE_METRE, 			Modifier.of(1550.0031), 		EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_FEET					("feet2", 	SQUARE_METRE, 			Modifier.of(10.763911), 		EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	SQUARE_YARD					("yard2", 	SQUARE_METRE, 			Modifier.of(1.195990), 			EnumSet.of(Measure.Type.AREA), Arrays.asList()),

	CENT						("cent", 	SQUARE_METRE, 			Modifier.of(0.024710538), 		EnumSet.of(Measure.Type.AREA), Arrays.asList()),
	ACRE						("acre", 	SQUARE_METRE, 			Modifier.of(0.000247105381),	EnumSet.of(Measure.Type.AREA), Arrays.asList()),

	// VOLUME UNITS
	CUBE_METRE					("m3", 		null, 					Modifier.Unit, 					EnumSet.of(Measure.Type.VOLUME), Arrays.asList()),
	CUBE_DECIMETRE				("dm3", 	CUBE_METRE, 			Modifier.pow10(  3),			EnumSet.of(Measure.Type.VOLUME), Arrays.asList()),
	LITER						("l", 		CUBE_DECIMETRE, 		Modifier.Unit,	 				EnumSet.of(Measure.Type.VOLUME), Arrays.asList()),
	
	// SPEED UNITS
	METRE_PER_SECOND			("m.s-1", 	null, 					Modifier.Unit,	 				EnumSet.of(Measure.Type.SPEED), Arrays.asList()),
	KILOMETRE_PER_HOUR			("km.h-1", 	METRE_PER_SECOND, 		Modifier.Unit,	 				EnumSet.of(Measure.Type.SPEED), Arrays.asList()),
	KNOT						("knots", 	METRE_PER_SECOND, 		Modifier.Unit,	 				EnumSet.of(Measure.Type.SPEED), Arrays.asList()),	// Speed       :=:  in knots (nautical miles per hour)

	// ACCELERATION UNITS
	METRE_PER_SQUARE_SECOND		("m.s-2", 	null, 					Modifier.Unit,	 				EnumSet.of(Measure.Type.SPEED), Arrays.asList()),

	// PRESSURE 
	PASCAL						("Pa", 		null, 					Modifier.Unit,	 				EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()),
	BAR							("bar", 	PASCAL, 				Modifier.pow10(5),	 			EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()),
	MILLIBAR					("mbar", 	PASCAL, 				Modifier.pow10(2),	 			EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()),
	ATMOSPHERE					("atm", 	PASCAL, 				Modifier.of(101325),	 		EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()),
	BARYE						("Ba", 		PASCAL, 				Modifier.of(0.1),	 			EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()),
	TORR						("Torr", 	ATMOSPHERE, 			Modifier.of(1. / 760.),	 		EnumSet.of(Measure.Type.PRESSURE), Arrays.asList()), 
	
	// ANGLES
	DEGREE						("Degree", 	null, 					Modifier.Unit,	 				EnumSet.of(Measure.Type.ANGLE), Arrays.asList()),
	;

	private Unit(String _si, Unit _reference, Modifier _modifier, EnumSet<Measure.Type> _usages, List<String> _aliases) {
		symbol    = _si;
		reference = _reference;
		ratio     = _modifier;
		usages    = _usages;
	}

	String                symbol;
	Unit                  reference;
	Modifier              ratio;
	EnumSet<Measure.Type> usages;
	
	public Modifier getModifier() { return ratio; }

}
