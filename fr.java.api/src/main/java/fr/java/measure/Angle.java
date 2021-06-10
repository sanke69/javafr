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
package fr.java.measure;

import fr.java.patterns.measurable.Measure;
import fr.java.patterns.measurable.Modifier;
import fr.java.patterns.measurable.Unit;

public interface Angle extends Measure {

	public static Angle of(double _value) {
		return new Angle() {
			
			@Override
			public double getValue() {
				return _value;
			}
			
			@Override
			public Unit getUnit() {
				return Unit.METRE;
			}
			
			@Override
			public Modifier getModifier() {
				return Modifier.Unit;
			}
		};
	}
	public static Angle of(double _value, Unit _unit) {
		return new Angle() {
			
			@Override
			public double getValue() {
				return _value;
			}
			
			@Override
			public Unit getUnit() {
				return _unit;
			}
			
			@Override
			public Modifier getModifier() {
				return Modifier.Unit;
			}
		};
	}
	public static Angle of(double _value, Unit _unit, Modifier _modifier) {
		return new Angle() {
			
			@Override
			public double getValue() {
				return _value;
			}
			
			@Override
			public Unit getUnit() {
				return _unit;
			}
			
			@Override
			public Modifier getModifier() {
				return _modifier;
			}
		};
	}

	@Override
	public default Type getType() {
		return Measure.Type.ANGLE;
	}

}
