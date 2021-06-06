package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class NumberBeanProperty<T extends Number> extends ReadOnlyNumberBeanProperty<T> implements BeanProperty<T> {

	public NumberBeanProperty() {
		super("number");
		value = null;
	}
	public NumberBeanProperty(T _initialValue) {
		super("number");
		value = _initialValue;
	}
	public NumberBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public NumberBeanProperty(String _propertyName, T _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public NumberBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public NumberBeanProperty(Object _bean, String _propertyName, T _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void set(T _b) {
		T oldValue = get();

        try {
			fireVetoableChange(oldValue, _b);
			value = _b;
	        firePropertyChange(oldValue, _b);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}

}
