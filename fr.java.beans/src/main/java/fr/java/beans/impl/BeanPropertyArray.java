package fr.java.beans.impl;

import fr.java.beans.properties.BeanProperty;

public abstract class BeanPropertyArray<T> extends ReadOnlyBeanPropertyArray<T> implements BeanProperty<T[]> {

	public BeanPropertyArray(T[] _initialArray) {
		super(_initialArray);
	}
	public BeanPropertyArray(String _propertyName, T[] _initialArray) {
		super(_propertyName, _initialArray);
	}
	public BeanPropertyArray(Object _bean, String _propertyName, T[] _initialArray) {
		super(_bean, _propertyName, _initialArray);
	}

	@Override
	public void set(T[] _value) {
		throw new IllegalAccessError();
	}

	public void set(int _index, T _value) {
		T oldValue = delegate[_index];
		delegate[_index] = _value;
		fireIndexedPropertyChange(_index, oldValue, _value);
	}

}
