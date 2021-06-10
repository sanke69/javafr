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
