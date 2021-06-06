package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class ByteBeanProperty extends ReadOnlyByteBeanProperty implements BeanProperty<Byte> {

	public ByteBeanProperty() {
		super("booleanBeanProperty");
		value = (byte) 0;
	}
	public ByteBeanProperty(Byte _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public ByteBeanProperty(String _propertyName) {
		super(_propertyName);
		value = (byte) 0;
	}
	public ByteBeanProperty(String _propertyName, Byte _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public ByteBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = (byte) 0;
	}
	public ByteBeanProperty(Object _bean, String _propertyName, Byte _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void set(Byte _byte) {
		Byte oldValue = get();

        try {
			fireVetoableChange(oldValue, _byte);
			value = _byte;
	        firePropertyChange(oldValue, _byte);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void set(byte _b) { set((Byte) _b); }

}
