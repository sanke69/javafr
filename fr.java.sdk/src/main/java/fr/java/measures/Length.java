package fr.java.measures;

import fr.java.measure.Measure;
import fr.java.measure.Measurement;
import fr.java.measure.Modifier;
import fr.java.measure.Unit;
import fr.java.measure.Value;

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
