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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import fr.java.beans.impl.events.MapPropertyChangeEvent;
import fr.java.beans.properties.listeners.BeanPropertyMapChangeListener;

/*
// Map methods for access only
// ---------------------------
int 				size();

boolean 			isEmpty();
boolean 			containsKey(Object key);
boolean 			containsValue(Object value);

Set<K> 				keySet();
Collection<V> 		values();

V 					getOrDefault(Object key, V defaultValue);
void 				forEach(BiConsumer<? super K, ? super V> action);

Set<? extends Map.Entry<K, V>> 	entrySet();

interface Entry<K,V> extends Map.Entry<K, V> {
    K 			getKey();
    V 			getValue();

    default V 	setValue(V value) { throw new IllegalAccessError(); }
}
**/
public abstract class ReadOnlyBeanPropertyMap<K, V> extends AbstractBeanProperty<Map<K, V>> {

	final Map<K, V> delegate;

    public ReadOnlyBeanPropertyMap(Map<K, V> _initialValue) {
    	super(null, "map");
		delegate = _initialValue;
//		delegate = new HashMap<K, V>();
//  	delegate = new LinkedHashMap<K, V>();
    }
    public ReadOnlyBeanPropertyMap(String _propertyName, Map<K, V> _initialValue) {
    	super(null, _propertyName);
    	delegate = _initialValue;
    }
    public ReadOnlyBeanPropertyMap(Object _bean, String _propertyName, Map<K, V> _initialValue) {
    	super(_bean, _propertyName);
    	delegate = _initialValue;
    }

	public void 				addListener(BeanPropertyMapChangeListener<K, V> listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), listener);
	}
	public void 				removeListener(BeanPropertyMapChangeListener<K, V> listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), listener);
	}

	protected final void 		fireMapPropertyChange(MapPropertyChangeEvent.Change<K, V> _change) {
		propertyChangeSupport().firePropertyChange( new MapPropertyChangeEvent<K, V>(getBean() != null ? getBean() : this, getName(), _change) );
	}

	public Map<K, V>	get() {
		throw new IllegalAccessError();
	}

	public V get(K _key) {
		return delegate.get(_key);
	}

	public int 		size() 						{	return delegate.size(); }

	public boolean 	isEmpty() 					{	return delegate.isEmpty(); }

	public boolean 	containsKey(Object key) 	{	return delegate.containsKey(key); }

	public boolean  containsValue(Object value) 	{	return delegate.containsValue(value); }

	public Set<K>   keySet() {	return delegate.keySet(); }

	public Collection<V> values() {	return delegate.values(); }

	public V getOrDefault(Object key, V defaultValue) {	return delegate.getOrDefault(key, defaultValue); }

	public void forEach(BiConsumer<? super K, ? super V> action) {	delegate.forEach(action); }

	public Set<Map.Entry<K, V>> entrySet() {	return delegate.entrySet(); }

}
