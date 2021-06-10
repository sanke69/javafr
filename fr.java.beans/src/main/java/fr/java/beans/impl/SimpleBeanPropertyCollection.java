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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import fr.java.beans.impl.events.CollectionPropertyChangeEvent;
import fr.java.lang.collections.ReadOnlyCollection;

public class SimpleBeanPropertyCollection<T> extends BeanPropertyCollection<T> {

	Collection<T> delegate;

	public SimpleBeanPropertyCollection() {
		this(null, "collection", new ArrayList<T>());
	}
	public SimpleBeanPropertyCollection(Collection<T> _initialValue) {
		this(null, "collection", _initialValue);
	}
	public SimpleBeanPropertyCollection(String _propertyName) {
		this(null, _propertyName, new ArrayList<T>());
	}
	public SimpleBeanPropertyCollection(String _propertyName, Collection<T> _initialValue) {
		this(null, _propertyName, _initialValue);
	}
	public SimpleBeanPropertyCollection(Object _bean, String _propertyName) {
		this(_bean, _propertyName, new ArrayList<T>());
	}
	public SimpleBeanPropertyCollection(Object _bean, String _propertyName, Collection<T> _initialValue) {
		super(_bean, _propertyName);
		delegate = _initialValue;
	}

	@Override
	public boolean 			add(T e) {
		if(delegate.add(e)) {
			fireCollectionPropertyChange(singleAdditionChange(e));
			return true;
		}
		return false;
	}
	@Override
	public boolean 			addAll(Collection<? extends T> c) {
		if(delegate.addAll(c)) {
			fireCollectionPropertyChange(multipleAdditionChange((Collection<T>) c));
			return true;
		}
		return false;
	}
	@Override
	public boolean 			remove(Object o) {
		if(delegate.remove(o)) {
			fireCollectionPropertyChange(singleSuppressionChange((T) o));
			return true;
		}
		return false;
	}
	@Override
	public boolean 			removeAll(Collection<?> c) {
		List<T> clone = new ArrayList<T>(delegate);
		clone.retainAll(c);

		if(delegate.removeAll(c)) {
			fireCollectionPropertyChange(multipleSuppressionChange(clone));
			return true;
		}
		return false;
	}
	@Override
	public boolean 			retainAll(Collection<?> c) {
		List<T> clone = new ArrayList<T>(delegate);
		clone.removeAll(c);

		if(delegate.retainAll(c)) {
			fireCollectionPropertyChange(multipleSuppressionChange(clone));
			return true;
		}
		return false;
	}
	@Override
	public void 			clear() {
		List<T> clone = new ArrayList<T>(delegate);
		clear();
		fireCollectionPropertyChange(multipleSuppressionChange(clone));
	}

	@Override
	public void 			set(Collection<T> _value) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Collection<T> 	get() {
		return delegate;
	}

	@Override
	public int 				size() {
		return delegate.size();
	}
	@Override
	public boolean 			isEmpty() {
		return delegate.isEmpty();
	}
	@Override
	public boolean 			contains(Object o) {
		return delegate.contains(o);
	}
	@Override
	public boolean 			containsAll(Collection<?> c) {
		return delegate.contains(c);
	}
	@Override
	public Iterator<T> 		iterator() {
		return delegate.iterator();
	}
	@Override
	public Object[] 		toArray() {
		return delegate.toArray();
	}
	@Override
	public <U> U[] 			toArray(U[] a) {
		return delegate.toArray(a);
	}

	private CollectionPropertyChangeEvent.Change<T> singleAdditionChange(T _addValue) {
		return new CollectionPropertyChangeEvent.Change<T>(delegate) {

			@Override public ReadOnlyCollection<T> getAdded() 	{ return ReadOnlyCollection.of(_addValue); }
			@Override public ReadOnlyCollection<T> getRemoved() { return null; }

		};
	}
	private CollectionPropertyChangeEvent.Change<T> singleSuppressionChange(T _removedValue) {
		return new CollectionPropertyChangeEvent.Change<T>(delegate) {

			@Override public ReadOnlyCollection<T> getAdded() 	{ return null; }
			@Override public ReadOnlyCollection<T> getRemoved() { return ReadOnlyCollection.of(_removedValue); }

		};
	}
	private CollectionPropertyChangeEvent.Change<T> multipleAdditionChange(Collection<T> _addedValues) {
		return new CollectionPropertyChangeEvent.Change<T>(delegate) {

			@Override public ReadOnlyCollection<T> getAdded() 	{ return null; }
			@Override public ReadOnlyCollection<T> getRemoved() { return ReadOnlyCollection.of(_addedValues); }

		};
	}
	private CollectionPropertyChangeEvent.Change<T> multipleSuppressionChange(Collection<T> _removedValues) {
		return new CollectionPropertyChangeEvent.Change<T>(delegate) {

			@Override public ReadOnlyCollection<T> getAdded() 	{ return null; }
			@Override public ReadOnlyCollection<T> getRemoved() { return ReadOnlyCollection.of(_removedValues); }

		};
	}

}
