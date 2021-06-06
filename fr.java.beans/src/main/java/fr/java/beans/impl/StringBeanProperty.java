package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class StringBeanProperty extends ReadOnlyStringBeanProperty implements BeanProperty<String> {

	public StringBeanProperty() {
		super("stringBeanProperty");
		value = null;
	}
	public StringBeanProperty(String _initialValue) {
		super("stringBeanProperty", _initialValue);
	}
	public StringBeanProperty(String _propertyName, String _initialValue) {
		super(_propertyName, _initialValue);
	}
	public StringBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public StringBeanProperty(Object _bean, String _propertyName, String _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public void 		set(String _value) {
		String oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}

	public String asString() { return get(); }

}
