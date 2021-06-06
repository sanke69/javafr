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
 * @file     SimpleListBeanProperty.java
 * @version  0.0.0.1
 * @date     2018/04/27
 * 
**/
package fr.java.beans.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.beans.impl.events.SetPropertyChangeEvent;

public class SimpleBeanPropertySet<T> extends BeanPropertySet<T> {

	public class InnerIterator implements Iterator<T> {

		private final Iterator<T> innerDelegate;
		private T 		  		  lastResult;

		public InnerIterator() {
			super();
			innerDelegate = delegate.iterator();
			lastResult    = null;
		}

		@Override
		public boolean hasNext() {
			return innerDelegate.hasNext();
		}

		@Override
		public T next() {
			return lastResult = innerDelegate.next();
		}

		@Override
		public void remove() {
			innerDelegate.remove();
			fireSetPropertyChange(singleSuppressionChange(lastResult));
		}

	}

	public SimpleBeanPropertySet() {
		super();
	}
	public SimpleBeanPropertySet(String _propertyName) {
		super(_propertyName);
	}
	public SimpleBeanPropertySet(Set<T> _list) {
		super(_list);
	}
	public SimpleBeanPropertySet(String _propertyName, Set<T> _list) {
		super(_propertyName, _list);
	}
	public SimpleBeanPropertySet(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public SimpleBeanPropertySet(Object _bean, String _propertyName, Set<T> _set) {
		super(_bean, _propertyName, _set);
	}

	public void set(Set<T> _value) {
		throw new IllegalAccessError();
	}

	public int 					size() {
		return delegate.size();
	}

	public boolean 				isEmpty() {
		return delegate.isEmpty();
	}

	public boolean 				contains(Object o) {
		return delegate.contains(o);
	}

	public boolean 				containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public Object[] 			toArray() {
		return delegate.toArray();
	}

	public <U> U[] 				toArray(U[] a) {
		return delegate.toArray(a);
	}

	public boolean 				add(T e) {
		boolean added = delegate.add(e);
		fireSetPropertyChange(singleAdditionChange(e));
        return added;
	}

	public boolean 				addAll(Collection<? extends T> c) {
		delegate.addAll(c);
		for(T e : c)
			fireSetPropertyChange(singleAdditionChange(e));
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean 				remove(Object o) {
		boolean removed = delegate.remove(o);
		if(removed)
			fireSetPropertyChange(singleSuppressionChange((T) o));
		return removed;
	}

	public boolean 				removeAll(Collection<?> c) {
		List<T> removedList = delegate.stream().filter(v -> c.contains(v)).collect(Collectors.toList());
		boolean removed = delegate.removeAll(c);
		if(removed)
			for(T e : removedList)
				fireSetPropertyChange(singleSuppressionChange(e));
		return removed;
	}

	public boolean 				retainAll(Collection<?> c) {
		List<T> removedList = delegate.stream().filter(v -> !c.contains(v)).collect(Collectors.toList());
		boolean removed = delegate.retainAll(c);
		if(removed)
			for(T e : removedList)
				fireSetPropertyChange(singleSuppressionChange(e));
		return removed;
	}

	public void 				clear() {
		List<T> removedList = new ArrayList<T>(delegate);
		delegate.clear();
		for(T e : removedList)
			fireSetPropertyChange(singleSuppressionChange(e));
	}

	public Iterator<T> 			iterator() {
		return new InnerIterator();
//		return delegate.iterator();
	}

	private SetPropertyChangeEvent.Change<T> singleAdditionChange(T _addValue) {
		return new SetPropertyChangeEvent.Change<T>(delegate) {
			@Override public T getElementAdded() 	{ return _addValue; }
			@Override public T getElementRemoved() 	{ return null; }
		};
	}
	private SetPropertyChangeEvent.Change<T> singleSuppressionChange(T _oldValue) {
		return new SetPropertyChangeEvent.Change<T>(delegate) {
			@Override public T getElementAdded() 	{ return null; }
			@Override public T getElementRemoved() 	{ return _oldValue; }
		};
	}

}
