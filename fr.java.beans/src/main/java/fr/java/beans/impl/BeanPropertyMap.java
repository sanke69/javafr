package fr.java.beans.impl;

import java.util.HashMap;
import java.util.Map;

import fr.java.beans.properties.BeanProperty;

public abstract class BeanPropertyMap<K, V> extends ReadOnlyBeanPropertyMap<K, V> implements BeanProperty<Map<K, V>>, Map<K, V> {

	protected BeanPropertyMap() {
		this(null, "map", new HashMap<K, V>());
	}
	protected BeanPropertyMap(Map<K, V> _initialValue) {
		this(null, "map", _initialValue);
	}
	protected BeanPropertyMap(String _propertyName) {
		this(null, _propertyName, new HashMap<K, V>());
	}
	protected BeanPropertyMap(String _propertyName, Map<K, V> _initialValue) {
		this(null, _propertyName, _initialValue);
	}
	protected BeanPropertyMap(Object _bean, String _propertyName) {
		this(_bean, _propertyName, new HashMap<K, V>());
	}
	protected BeanPropertyMap(Object _bean, String _propertyName, Map<K, V> _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(Object _key) {
		return super.get((K) _key);
	}

	public abstract BeanPropertySet<Map.Entry<K, V>> 	entrySet();
	public abstract BeanPropertySet<K> 					keySet();
	public abstract BeanPropertyCollection<V> 			values();

}
