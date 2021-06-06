package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class LongBeanProperty extends ReadOnlyLongBeanProperty implements BeanProperty<Long> {

	public LongBeanProperty() {
		super("booleanBeanProperty");
		value = null;
	}
	public LongBeanProperty(Long _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public LongBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public LongBeanProperty(String _propertyName, Long _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public LongBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public LongBeanProperty(Object _bean, String _propertyName, Long _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void 		set(Long _value) {
		Long oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(long _l) { set((Long) _l); }

}
