package fr.java.measure;

import fr.java.patterns.measurable.Measure;
import fr.java.patterns.measurable.Modifier;
import fr.java.patterns.measurable.Unit;

public interface Duration extends Measure {

	public static Duration of(double _value) {
		return new Duration() {
			
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
	public static Duration of(double _value, Unit _unit) {
		return new Duration() {
			
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
	public static Duration of(double _value, Unit _unit, Modifier _modifier) {
		return new Duration() {
			
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
		return Measure.Type.TIME;
	}

}
