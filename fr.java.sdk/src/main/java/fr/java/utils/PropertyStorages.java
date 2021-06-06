package fr.java.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import fr.java.beans.ListenableBean;
import fr.java.beans.impl.SimpleBeanPropertyMap;
import fr.java.beans.properties.BeanPropertyStorage;
import fr.java.beans.properties.listeners.BeanPropertyMapChangeListener;

public class PropertyStorages {

	public static final BeanPropertyStorage newStorage() {
		return new PropertyStorageImpl();
	}

	static class PropertyStorageImpl extends SimpleBeanPropertyMap<String, Object> implements BeanPropertyStorage, Serializable {
		private static final long serialVersionUID = -2405551210469366690L;
	
		public PropertyStorageImpl() {
			super(null, "propertyStorage");
		}
		public PropertyStorageImpl(String _name) {
			super(null, _name);
		}
		public PropertyStorageImpl(ListenableBean _bean, String _name) {
			super(_bean, _name);
		}
	
		@Override
		public Collection<String> 	keys() {
			return keySet();
		}
	
		@Override
		public <T> void 			set(String key, T property) {
			put(key, property);
		}
		@Override
		public <T> Optional<T> 		get(String key) {
			Object value = super.get(key);
	
			try {
				return Optional.ofNullable((T) value);
			} catch(ClassCastException ex) {
				return Optional.empty();
			}
		}
	
		@Override
		public boolean 				contains(String key) {
			return containsKey(key);
		}
	
		@Override
		public void 				remove(String key) {
			super.remove(key);
		}
	
		@Override
		public void addListener(BeanPropertyMapChangeListener<String, Object> l) {
			super.addListener(l);
		}
		@Override
		public void removeListener(BeanPropertyMapChangeListener<String, Object> l) {
			super.removeListener(l);
		}
	
	}

}
