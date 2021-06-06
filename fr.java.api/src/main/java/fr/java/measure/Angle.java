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
