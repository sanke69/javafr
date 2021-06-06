package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class FloatBeanProperty extends ReadOnlyFloatBeanProperty implements BeanProperty<Float> {

	public FloatBeanProperty() {
		super("booleanBeanProperty");
		value = null;
	}
	public FloatBeanProperty(Float _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public FloatBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public FloatBeanProperty(String _propertyName, Float _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public FloatBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public FloatBeanProperty(Object _bean, String _propertyName, Float _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void 		set(Float _value) {
		Float oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(float _f) { set((Float) _f); }

}
