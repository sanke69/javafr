/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.beans.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import fr.java.beans.impl.events.CollectionPropertyChangeEvent;
import fr.java.beans.impl.events.MapPropertyChangeEvent;
import fr.java.beans.impl.events.SetPropertyChangeEvent;
import fr.java.lang.collections.ReadOnlyCollection;

public class SimpleBeanPropertyMap<K, V> extends BeanPropertyMap<K, V> {

	// Views
	BeanPropertySet<Map.Entry<K, V>> 	entrySetProperty;
	BeanPropertySet<K> 					keySetProperty;
	BeanPropertyCollection<V> 			valuesProperty;

	public SimpleBeanPropertyMap() {
		this(null, "map", new HashMap<K, V>());
	}
	public SimpleBeanPropertyMap(Map<K, V> _initialValue) {
		this(null, "map", _initialValue);
	}
	public SimpleBeanPropertyMap(String _propertyName) {
		this(null, _propertyName, new HashMap<K, V>());
	}
	public SimpleBeanPropertyMap(String _propertyName, Map<K, V> _initialValue) {
		this(null, _propertyName, _initialValue);
	}
	public SimpleBeanPropertyMap(Object _bean, String _propertyName) {
		this(_bean, _propertyName, new HashMap<K, V>());
	}
	public SimpleBeanPropertyMap(Object _bean, String _propertyName, Map<K, V> _initialValue) {
		super(_bean, _propertyName, _initialValue);

//		entrySetProperty = new SimpleBeanPropertySet<Map.Entry<K, V>>(this, "entrySet", delegate.entrySet());
//		keySetProperty   = new SimpleBeanPropertySet<K>(this, "keySet", delegate.keySet());
//		valuesProperty   = new SimpleBeanPropertyCollection<V>(this, "values", delegate.values());
	}

	public void								set(Map<K, V> _map) {
		throw new UnsupportedOperationException();
	}

    @Override
    public int 								size() {
        return delegate.size();
    }

    @Override
    public boolean 							isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean 							containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean 							containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public BeanPropertySet<Map.Entry<K, V>> entrySet() {
    	if(entrySetProperty == null)
    		entrySetProperty = new SimpleBeanPropertySet<Map.Entry<K, V>>(this, "entrySet", delegate.entrySet());
        return entrySetProperty; //delegate.entrySet();
    }

    @Override
    public BeanPropertySet<K> 				keySet() {
    	if(keySetProperty == null)
    		keySetProperty   = new SimpleBeanPropertySet<K>(this, "keySet", delegate.keySet());
        return keySetProperty ; //delegate.keySet();
    }

    @Override
    public BeanPropertyCollection<V> 		values() {
    	if(valuesProperty == null)
    		valuesProperty   = new SimpleBeanPropertyCollection<V>(this, "values", delegate.values());
        return valuesProperty ; //delegate.values();
    }

	@Override
	public V getOrDefault(Object key, V defaultValue) {
        return delegate.getOrDefault(key, defaultValue);
	}
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
        delegate.forEach(action);
	}

    @Override
    public V 					get(Object key) {
        return delegate.get(key);
    }

    @Override
    public V 					put(K key, V value) {
    	if(delegate.containsKey(key)) {
            V oldValue = delegate.put(key, value);
            fireMapPropertyChange(singleUpdateChange(key, oldValue, value));
        	if(valuesProperty != null)
        		valuesProperty.fireCollectionPropertyChange(singleUpdateChange_Values(oldValue, value));
        	if(entrySetProperty != null)
        		entrySetProperty.fireSetPropertyChange(singleUpdateChange_EntrySet(key, oldValue, value));
            return oldValue;
    	} else {
    		delegate.put(key, value);
            fireMapPropertyChange(singleAdditionChange(key, value));
        	if(keySetProperty != null)
        		keySetProperty.fireSetPropertyChange(singleAdditionChange_KeySet(key));
        	if(valuesProperty != null)
        		valuesProperty.fireCollectionPropertyChange(singleAdditionChange_Values(value));
        	if(entrySetProperty != null)
        		entrySetProperty.fireSetPropertyChange(singleAdditionChange_EntrySet(key, value));
            return null;
    	}
    }

    @Override
    public void 				putAll(Map<? extends K, ? extends V> m) {
    	// Temporary solution...
    	for(Map.Entry<? extends K, ? extends V> e : m.entrySet())
    		put(e.getKey(), e.getValue());

//      delegate.putAll(m);
//      firePropertyChange(multipleUpdateChange(added, removed));
    }

	@Override
    @SuppressWarnings("unchecked")
    public V 					remove(Object key) {
        V oldValue = delegate.remove(key);
        fireMapPropertyChange(singleRemoveChange((K) key, oldValue));
    	if(keySetProperty != null)
    		keySetProperty.fireSetPropertyChange(singleRemoveChange_KeySet((K) key));
    	if(valuesProperty != null)
    		valuesProperty.fireCollectionPropertyChange(singleRemoveChange_Values(oldValue));
    	if(entrySetProperty != null)
    		entrySetProperty.fireSetPropertyChange(singleRemoveChange_EntrySet((K) key, oldValue));
        return oldValue;
    }

    @Override
    public void 				clear() {
    	// Temporary solution...
    	Set<K> keySet = new HashSet<K>(delegate.keySet());
    	for(K key : keySet)
    		remove(key);
    	
//    	Map<K, V> oldDelegate = new HashMap<K, V>(delegate);
//		delegate.clear();
//      firePropertyChange(multipleRemoveChange(oldDelegate));
    }

	private MapPropertyChangeEvent.Change<K, V> singleAdditionChange(K _key, V _value) {
		return new MapPropertyChangeEvent.Change<K, V>(delegate) {

			@Override
			public boolean wasAdded() {
				return true;
			}

			@Override
			public boolean wasRemoved() {
				return false;
			}

			@Override
			public K getKey() {
				return _key;
			}

			@Override
			public V getValueAdded() {
				return _value;
			}

			@Override
			public V getValueRemoved() {
				return null;
			}

		};
	}
	private MapPropertyChangeEvent.Change<K, V> singleUpdateChange(K _key, V _oldValue, V _newValue) {
		return new MapPropertyChangeEvent.Change<K, V>(delegate) {

			@Override
			public boolean wasAdded() {
				return true;
			}

			@Override
			public boolean wasRemoved() {
				return true;
			}

			@Override
			public K getKey() {
				return _key;
			}

			@Override
			public V getValueAdded() {
				return _newValue;
			}

			@Override
			public V getValueRemoved() {
				return _oldValue;
			}

		};
	}
	private MapPropertyChangeEvent.Change<K, V> singleRemoveChange(K _key, V _value) {
		return new MapPropertyChangeEvent.Change<K, V>(delegate) {

			@Override
			public boolean wasAdded() {
				return false;
			}

			@Override
			public boolean wasRemoved() {
				return true;
			}

			@Override
			public K getKey() {
				return _key;
			}

			@Override
			public V getValueAdded() {
				return null;
			}

			@Override
			public V getValueRemoved() {
				return _value;
			}

		};
	}

	private SetPropertyChangeEvent.Change<Map.Entry<K, V>> singleAdditionChange_EntrySet(K _key, V _value) {
		return new SetPropertyChangeEvent.Change<Map.Entry<K, V>>(delegate.entrySet()) {

			@Override
			public Entry<K, V> getElementAdded() {
				return new Map.Entry<K, V>() {
					@Override public K getKey() 			{ return _key; }
					@Override public V getValue() 			{ return _value; }
					@Override public V setValue(V value) 	{ throw new IllegalAccessError(); }
				};
			}

			@Override
			public Entry<K, V> getElementRemoved() {
				return null;
			}

		};
	}
	private SetPropertyChangeEvent.Change<Map.Entry<K, V>> singleUpdateChange_EntrySet(K _key, V _oldValue, V _newValue) {
		return new SetPropertyChangeEvent.Change<Map.Entry<K, V>>(delegate.entrySet()) {

			@Override
			public Entry<K, V> getElementAdded() {
				return new Map.Entry<K, V>() {
					@Override public K getKey() 			{ return _key; }
					@Override public V getValue() 			{ return _newValue; }
					@Override public V setValue(V value) 	{ throw new IllegalAccessError(); }
				};
			}

			@Override
			public Entry<K, V> getElementRemoved() {
				return new Map.Entry<K, V>() {
					@Override public K getKey() 			{ return _key; }
					@Override public V getValue() 			{ return _oldValue; }
					@Override public V setValue(V value) 	{ throw new IllegalAccessError(); }
				};
			}

		};
	}
	private SetPropertyChangeEvent.Change<Map.Entry<K, V>> singleRemoveChange_EntrySet(K _key, V _value) {
		return new SetPropertyChangeEvent.Change<Map.Entry<K, V>>(delegate.entrySet()) {

			@Override
			public Entry<K, V> getElementAdded() {
				return null;
			}

			@Override
			public Entry<K, V> getElementRemoved() {
				return new Map.Entry<K, V>() {
					@Override public K getKey() 			{ return _key; }
					@Override public V getValue() 			{ return _value; }
					@Override public V setValue(V value) 	{ throw new IllegalAccessError(); }
				};
			}

		};
	}

	private SetPropertyChangeEvent.Change<K> singleAdditionChange_KeySet(K _key) {
		return new SetPropertyChangeEvent.Change<K>(delegate.keySet()) {

			@Override
			public K getElementAdded() {
				return _key;
			}

			@Override
			public K getElementRemoved() {
				return null;
			}

		};
	}
	private SetPropertyChangeEvent.Change<K> singleRemoveChange_KeySet(K _key) {
		return new SetPropertyChangeEvent.Change<K>(delegate.keySet()) {

			@Override
			public K getElementAdded() {
				return null;
			}

			@Override
			public K getElementRemoved() {
				return _key;
			}

		};
	}

	private CollectionPropertyChangeEvent.Change<V> singleAdditionChange_Values(V _value) {
		return new CollectionPropertyChangeEvent.Change<V>(delegate.values()) {

			@Override
			public ReadOnlyCollection<V> getAdded() {
				return ReadOnlyCollection.of(_value);
			}

			@Override
			public ReadOnlyCollection<V> getRemoved() {
				return null;
			}

		};
	}
	private CollectionPropertyChangeEvent.Change<V> singleUpdateChange_Values(V _oldValue, V _newValue) {
		return new CollectionPropertyChangeEvent.Change<V>(delegate.values()) {

			@Override
			public ReadOnlyCollection<V> getAdded() {
				return ReadOnlyCollection.of(_newValue);
			}

			@Override
			public ReadOnlyCollection<V> getRemoved() {
				return ReadOnlyCollection.of(_oldValue);
			}

		};
	}
	private CollectionPropertyChangeEvent.Change<V> singleRemoveChange_Values(V _value) {
		return new CollectionPropertyChangeEvent.Change<V>(delegate.values()) {

			@Override
			public ReadOnlyCollection<V> getAdded() {
				return null;
			}

			@Override
			public ReadOnlyCollection<V> getRemoved() {
				return ReadOnlyCollection.of(_value);
			}

		};
	}

}
