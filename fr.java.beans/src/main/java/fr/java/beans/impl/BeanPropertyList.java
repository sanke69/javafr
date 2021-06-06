package fr.java.beans.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

import fr.java.beans.properties.BeanProperty;

public abstract class BeanPropertyList<T> extends ReadOnlyBeanPropertyList<T> implements BeanProperty<List<T>>, List<T> { // , ListenableList<T>

	protected BeanPropertyList() {
		super(new ArrayList<T>());
	}
	protected BeanPropertyList(List<T> _initialValue) {
		super("listProperty", _initialValue);
	}
	protected BeanPropertyList(String _propertyName) {
		super(_propertyName, new ArrayList<T>());
	}
	protected BeanPropertyList(String _propertyName, List<T> _initialValue) {
		super(_propertyName, _initialValue);
	}
	protected BeanPropertyList(Object _bean, String _propertyName) {
		super(_bean, _propertyName, new ArrayList<T>());
	}
	protected BeanPropertyList(Object _bean, String _propertyName, List<T> _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public Spliterator<T> 		spliterator() {
		return List.super.spliterator();
	}

}
