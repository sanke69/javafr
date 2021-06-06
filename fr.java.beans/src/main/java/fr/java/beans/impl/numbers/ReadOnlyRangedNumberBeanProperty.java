package fr.java.beans.impl.numbers;

import fr.java.beans.impl.NumberBeanProperty;
import fr.java.beans.impl.ReadOnlyNumberBeanProperty;
import fr.java.beans.impl.SimpleNumberBeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class ReadOnlyRangedNumberBeanProperty<T extends Number> extends ReadOnlyNumberBeanProperty<T> implements Ranged<T> {
	public static final String MIN   = "min";
	public static final String MAX   = "max";

	final NumberBeanProperty<T> min;
	final NumberBeanProperty<T> max;

	protected ReadOnlyRangedNumberBeanProperty() {
		this(null, "rangedNumber", null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(T _value, T _min, T _max) {
		this(null, "rangedNumber", _value, _min, _max);
	}
	protected ReadOnlyRangedNumberBeanProperty(String _propertyName) {
		this(null, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(String _propertyName, T _value, T _min, T _max) {
		this(null, _propertyName, _value, _min, _max);
	}
	protected ReadOnlyRangedNumberBeanProperty(Object _bean, String _propertyName) {
		this(_bean, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(Object _bean, String _propertyName, T _value, T _min, T _max) {
		super(_bean, _propertyName, _value);

		min   = new SimpleNumberBeanProperty<T>(this, MIN, _min);
		max   = new SimpleNumberBeanProperty<T>(this, MAX, _max);
	}

	public ReadOnlyNumberBeanProperty<T> 	valueProperty() {
		return this;
	}
	@Override
	public T 								getValue() {
		return get();
	}

	public ReadOnlyNumberBeanProperty<T> 	minProperty() {
		return min;
	}
	@Override
	public T 								getMin() {
		return min.get();
	}

	public ReadOnlyNumberBeanProperty<T> 	maxProperty() {
		return min;
	}
	@Override
	public T 								getMax() {
		return max.get();
	}

	@Override
	public Interval<T> 						getRange() {
		return Interval.of(getMin(), getMax());
	}

}
