package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class ObjectBeanProperty<T> extends ReadOnlyObjectBeanProperty<T> implements BeanProperty<T> {

	public ObjectBeanProperty() {
		super("booleanBeanProperty");
		value = null;
	}
	public ObjectBeanProperty(T _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public ObjectBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public ObjectBeanProperty(String _propertyName, T _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public ObjectBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public ObjectBeanProperty(Object _bean, String _propertyName, T _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void 		set(T _value) {
		T oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}

	public boolean 		isNull() { return get() == null; }

}
