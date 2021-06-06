package fr.java.beans.impl.numbers;

import fr.java.beans.properties.BeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class RangedDoubleBeanProperty extends ReadOnlyRangedDoubleBeanProperty implements BeanProperty<Double>, Ranged.Editable<Double> {

	protected RangedDoubleBeanProperty() {
		super();
	}
	protected RangedDoubleBeanProperty(Double _value, Double _min, Double _max) {
		super(_value, _min, _max);
	}
	protected RangedDoubleBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	protected RangedDoubleBeanProperty(String _propertyName, Double _value, Double _min, Double _max) {
		super(_propertyName, _value, _min, _max);
	}
	protected RangedDoubleBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	protected RangedDoubleBeanProperty(Object _bean, String _propertyName, Double _value, Double _min, Double _max) {
		super(_bean, _propertyName, _value, _min, _max);
	}

	@Override
	public void set(Double _value) {
		set(_value);
	}
	@Override
	public void setValue(Double _value) {
		set(_value);
	}

	@Override
	public void setRange(Double _min, Double _max) {
		min.set(_min);
		max.set(_max);
		firePropertyChange(null, get());
	}
	@Override
	public void setRange(Interval<Double> _range) {
		min.set(_range.getMin());
		max.set(_range.getMax());
		firePropertyChange(null, get());
	}

	@Override
	public void setMin(Double _min) {
		min.set(_min);
		firePropertyChange(null, get());
	}
	@Override
	public void setMax(Double _max) {
		max.set(_max);
		firePropertyChange(null, get());
	}

}
