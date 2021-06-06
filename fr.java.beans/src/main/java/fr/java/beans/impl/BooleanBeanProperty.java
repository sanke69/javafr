package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class BooleanBeanProperty extends ReadOnlyBooleanBeanProperty implements BeanProperty<Boolean> {

	public BooleanBeanProperty() {
		super("booleanBeanProperty");
		value = false;
	}
	public BooleanBeanProperty(Boolean _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public BooleanBeanProperty(String _propertyName) {
		super(_propertyName);
		value = false;
	}
	public BooleanBeanProperty(String _propertyName, Boolean _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public BooleanBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = false;
	}
	public BooleanBeanProperty(Object _bean, String _propertyName, Boolean _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void set(Boolean _b) {
		Boolean oldValue = get();

        try {
			fireVetoableChange(oldValue, _b);
			value = _b;
	        firePropertyChange(oldValue, _b);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void set(boolean _b) { set((Boolean) _b); }

}
