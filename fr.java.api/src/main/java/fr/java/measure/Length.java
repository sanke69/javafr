package fr.java.measure;

import fr.java.patterns.measurable.Measure;
import fr.java.patterns.measurable.Modifier;
import fr.java.patterns.measurable.Unit;

public interface Length extends Measure {

	public static Length of(double _value) {
		return new Length() {
			
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
	public static Length of(double _value, Unit _unit) {
		return new Length() {
			
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
	public static Length of(double _value, Unit _unit, Modifier _modifier) {
		return new Length() {
			
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
		return Measure.Type.LENGTH;
	}

}
