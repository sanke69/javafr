package fr.java.beans.impl.numbers;

import fr.java.beans.properties.BeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class RangedNumberBeanProperty<T extends Number> extends ReadOnlyRangedNumberBeanProperty<T> implements BeanProperty<T>, Ranged.Editable<T> {

	protected RangedNumberBeanProperty() {
		super();
	}
	protected RangedNumberBeanProperty(T _value, T _min, T _max) {
		super(_value, _min, _max);
	}
	protected RangedNumberBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	protected RangedNumberBeanProperty(String _propertyName, T _value, T _min, T _max) {
		super(_propertyName, _value, _min, _max);
	}
	protected RangedNumberBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	protected RangedNumberBeanProperty(Object _bean, String _propertyName, T _value, T _min, T _max) {
		super(_bean, _propertyName, _value, _min, _max);
	}

	@Override
	public void set(T _value) {
		set(_value);
	}
	@Override
	public void setValue(T _value) {
		set(_value);
	}

	@Override
	public void setRange(T _min, T _max) {
		min.set(_min);
		max.set(_max);
		firePropertyChange(null, get());
	}
	@Override
	public void setRange(Interval<T> _range) {
		min.set(_range.getMin());
		max.set(_range.getMax());
		firePropertyChange(null, get());
	}

	@Override
	public void setMin(T _min) {
		min.set(_min);
		firePropertyChange(null, get());
	}
	@Override
	public void setMax(T _max) {
		max.set(_max);
		firePropertyChange(null, get());
	}

}
