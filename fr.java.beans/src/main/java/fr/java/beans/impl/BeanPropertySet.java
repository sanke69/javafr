package fr.java.beans.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;

import fr.java.beans.properties.BeanProperty;

public abstract class BeanPropertySet<T> extends ReadOnlyBeanPropertySet<T> implements BeanProperty<Set<T>>, Set<T> {

	protected BeanPropertySet() {
		super(new HashSet<T>());
	}
	protected BeanPropertySet(Set<T> _initialValue) {
		super("setProperty", _initialValue);
	}
	protected BeanPropertySet(String _propertyName) {
		super(_propertyName, new HashSet<T>());
	}
	protected BeanPropertySet(String _propertyName, Set<T> _initialValue) {
		super(_propertyName, _initialValue);
	}
	protected BeanPropertySet(Object _bean, String _propertyName) {
		super(_bean, _propertyName, new HashSet<T>());
	}
	protected BeanPropertySet(Object _bean, String _propertyName, Set<T> _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public Spliterator<T> spliterator() {
		return Set.super.spliterator();
	}

}
