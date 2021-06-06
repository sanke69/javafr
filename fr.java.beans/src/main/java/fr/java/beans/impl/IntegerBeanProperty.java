package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class IntegerBeanProperty extends ReadOnlyIntegerBeanProperty implements BeanProperty<Integer> {

	public IntegerBeanProperty() {
		super("booleanBeanProperty");
		value = null;
	}
	public IntegerBeanProperty(Integer _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public IntegerBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public IntegerBeanProperty(String _propertyName, Integer _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public IntegerBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public IntegerBeanProperty(Object _bean, String _propertyName, Integer _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void 		set(Integer _value) {
		Integer oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(int _i) { set((Integer) _i); }

}
