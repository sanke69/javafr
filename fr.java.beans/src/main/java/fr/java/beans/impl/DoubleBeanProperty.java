package fr.java.beans.impl;

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class DoubleBeanProperty extends ReadOnlyDoubleBeanProperty implements BeanProperty<Double> {

	public DoubleBeanProperty() {
		super("booleanBeanProperty");
		value = null;
	}
	public DoubleBeanProperty(Double _initialValue) {
		super("booleanBeanProperty");
		value = _initialValue;
	}
	public DoubleBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public DoubleBeanProperty(String _propertyName, Double _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public DoubleBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = null;
	}
	public DoubleBeanProperty(Object _bean, String _propertyName, Double _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public void 		set(Double _value) {
		Double oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(double _d) { set((Double) _d); }

}
