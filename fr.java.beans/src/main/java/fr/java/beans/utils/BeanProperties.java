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
package fr.java.beans.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fr.java.beans.impl.BeanPropertyList;
import fr.java.beans.impl.BeanPropertyMap;
import fr.java.beans.impl.ObjectBeanProperty;
import fr.java.beans.impl.ReadOnlyBeanPropertyList;
import fr.java.beans.impl.ReadOnlyObjectBeanProperty;
import fr.java.beans.impl.SimpleBeanPropertyList;
import fr.java.beans.impl.SimpleBeanPropertyMap;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.beans.properties.listeners.BeanPropertyChangeListener;
import fr.java.lang.tuples.Pair;
import fr.utils.beans.Tuples;

public class BeanProperties {

	private static final Map<Pair<ReadOnlyBeanProperty<?>, BeanProperty<?>>, BeanPropertyChangeListener<?>> bindings;
	
	static {
		bindings = new HashMap<>();
	}

	public static <T> ReadOnlyObjectBeanProperty<T> 	newReadOnly(ObjectBeanProperty<T> _property) {
		return _property;
	}

	public static <T> BeanPropertyList<T> 				newList() {
		return new SimpleBeanPropertyList<T>();
	}
	public static <T> BeanPropertyList<T> 				newList(Class<T> _class) {
		return new SimpleBeanPropertyList<T>();
	}
	public static <T> BeanPropertyList<T> 				newList(List<T> _list) {
		return new SimpleBeanPropertyList<T>(_list);
	}
	public static <T> BeanPropertyList<T> 				newReadOnlyList(BeanPropertyList<T> _list) {
		return _list;
	}
	public static <T> ReadOnlyBeanPropertyList<T> 		newReadOnlyList(List<T> _list) {
//		return new ReadOnlyListBeanProperty<T>(newList(_list));
		return newList(_list);
	}
	@Deprecated
	public static <T> ReadOnlyBeanPropertyList<T> 		newReadOnlyList(Collection<T> _collection) {
		if(_collection instanceof List)
//			return new ReadOnlyListBeanProperty<T>(newList((List<T>) _collection));
			return newList((List<T>) _collection);
		throw new IllegalArgumentException();
	}

	public static <K, V> BeanPropertyMap<K, V> 			newMap() {
		return new SimpleBeanPropertyMap<K, V>();
	}
	public static <K, V> BeanPropertyMap<K, V> 			newMap(Class<K> _keyClass, Class<V> _valueClass) {
		return new SimpleBeanPropertyMap<K, V>();
	}
	public static <K, V> BeanPropertyMap<K, V> 			newReadOnlyMap(BeanPropertyMap<K, V> _map) {
//		return new ReadOnlyMapBeanProperty<K, V>(_map);
		return _map;
	}
	

    public static <T> void bind(ReadOnlyBeanProperty<T> propertyA, BeanProperty<T> propertyB) {
        addFlaggedChangeListener(propertyA, propertyB, t -> t);
    }
    public static <A ,B> void bind(ReadOnlyBeanProperty<A> propertyA, BeanProperty<B> propertyB, Function<A, B> updateB) {
        addFlaggedChangeListener(propertyA, propertyB, updateB);
    }
    public static <A ,B> void unbind(ReadOnlyBeanProperty<A> propertyA, BeanProperty<B> propertyB) {
    	bindings.remove(Tuples.of(propertyA, propertyB));
    }

    public static <T> void bindBidirectional(BeanProperty<T> propertyA, BeanProperty<T> propertyB) {
        addFlaggedChangeListener(propertyA, propertyB, t -> t);
        addFlaggedChangeListener(propertyB, propertyA, t -> t);
    }
    public static <A ,B> void bindBidirectional(BeanProperty<A> propertyA, BeanProperty<B> propertyB, Function<A,B> updateB, Function<B,A> updateA) {
        addFlaggedChangeListener(propertyA, propertyB, updateB);
        addFlaggedChangeListener(propertyB, propertyA, updateA);
    }
    public static <A ,B> void unbindBidirectional(BeanProperty<A> propertyA, BeanProperty<B> propertyB) {
    	bindings.remove(Tuples.of(propertyA, propertyB));
    	bindings.remove(Tuples.of(propertyB, propertyA));
    }

    private static <X,Y> void addFlaggedChangeListener(ReadOnlyBeanProperty<X> propertyX, BeanProperty<Y> propertyY, Function<X,Y> updateY) {
    	BeanPropertyChangeListener<X> cl = new BeanPropertyChangeListener<X>() {
            private boolean alreadyCalled = false;

			@Override
			public void propertyChange(Object _property, X _old, X _new) {
                if(alreadyCalled) return;
                try {
                    alreadyCalled = true;
                    propertyY.set(updateY.apply(_new));
                }
                finally {alreadyCalled = false; }
			}
        };

        bindings.put(Tuples.of(propertyX, propertyY), cl);

        propertyX.addListener(cl);
    }

}
