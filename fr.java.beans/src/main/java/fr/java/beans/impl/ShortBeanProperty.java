package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class ShortBeanProperty extends ReadOnlyShortBeanProperty implements BeanProperty<Short> {

	public ShortBeanProperty() {
		super("shortBeanProperty");
	}
	public ShortBeanProperty(Short _initialValue) {
		super(_initialValue);
	}
	public ShortBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	public ShortBeanProperty(String _propertyName, Short _initialValue) {
		super(_propertyName, _initialValue);
	}
	public ShortBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public ShortBeanProperty(Object _bean, String _propertyName, Short _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public void 		set(Short _value) {
		Short oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(short _s) { set((Short) _s); }

}
