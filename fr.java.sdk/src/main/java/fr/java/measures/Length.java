package fr.java.measures;

import fr.java.patterns.measurable.Measure;
import fr.java.patterns.measurable.Measurement;
import fr.java.patterns.measurable.Modifier;
import fr.java.patterns.measurable.Unit;
import fr.java.patterns.measurable.Value;

@Deprecated
public class Length implements Measurement {
	private Value value;

	public Length(double value) {
		super();
	}

	@Override
	public Type getType() {
		return Measure.Type.LENGTH;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public double getValue() {
		return value.get();
	}

	@Override
	public Unit getUnit() {
		return null;
	}

	@Override
	public Modifier getModifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
