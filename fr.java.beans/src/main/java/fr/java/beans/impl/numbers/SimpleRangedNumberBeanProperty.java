package fr.java.beans.impl.numbers;

public class SimpleRangedNumberBeanProperty<T extends Number> extends RangedNumberBeanProperty<T> {

	protected SimpleRangedNumberBeanProperty() {
		super();
	}
	protected SimpleRangedNumberBeanProperty(T _value, T _min, T _max) {
		super(_value, _min, _max);
	}
	protected SimpleRangedNumberBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	protected SimpleRangedNumberBeanProperty(String _propertyName, T _value, T _min, T _max) {
		super(_propertyName, _value, _min, _max);
	}
	protected SimpleRangedNumberBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	protected SimpleRangedNumberBeanProperty(Object _bean, String _propertyName, T _value, T _min, T _max) {
		super(_bean, _propertyName, _value, _min, _max);
	}

}
