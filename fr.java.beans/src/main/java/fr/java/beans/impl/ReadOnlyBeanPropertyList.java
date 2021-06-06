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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

import fr.java.beans.impl.events.ListPropertyChangeEvent;
import fr.java.beans.properties.listeners.BeanPropertyListChangeListener;

/*
// List methods for access only
// ----------------------------
int 			size();
boolean 		isEmpty();
boolean 		contains(Object o);
Iterator<T> 	iterator();
Object[] 		toArray();
<U> U[] 		toArray(U[] a);
boolean 		containsAll(Collection<?> c);

boolean 		equals(Object o);

int 			hashCode();

int 			indexOf(Object o);
int 			lastIndexOf(Object o);

List<T> 		subList(int fromIndex, int toIndex);

ListIterator<T> listIterator();
ListIterator<T> listIterator(int index);

Spliterator<T> 	spliterator();
**/
public abstract class ReadOnlyBeanPropertyList<T> extends AbstractBeanProperty<List<T>> implements Iterable<T> {

	public class InnerSubList implements List<T> {

		private final List<T> innerDelegate;
		private final int     from; //, to;

		public InnerSubList(int fromIndex, int toIndex) {
			super();
			innerDelegate = ReadOnlyBeanPropertyList.this.delegate.subList(fromIndex, toIndex);
			from = fromIndex;
//			to   = toIndex;
		}

		@Override
		public int size() {
			return innerDelegate.size();
		}

		@Override
		public T set(int index, T element) {
			throw new UnsupportedOperationException("");
		}
		@Override
		public T get(int index) {
			return innerDelegate.get(index);
		}

		@Override
		public boolean isEmpty() {
			return innerDelegate.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return innerDelegate.contains(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return innerDelegate.containsAll(c);
		}

		@Override
		public int indexOf(Object o) {
			return innerDelegate.indexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return innerDelegate.lastIndexOf(o);
		}

		@Override
		public Object[] toArray() {
			return innerDelegate.toArray();
		}

		@Override
		public <U> U[] toArray(U[] a) {
			return innerDelegate.toArray(a);
		}

		@Override
		public boolean add(T e) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public void add(int index, T element) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public T remove(int index) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException("");
		}

		@Override
		public Iterator<T> iterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> listIterator() {
			return new InnerIterator(from);
		}

		@Override
		public ListIterator<T> listIterator(int index) {
			return new InnerIterator(from + index);
		}

		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			return new InnerSubList(from + fromIndex, from + toIndex);
		}
		
	}

	public class InnerIterator implements ListIterator<T> {

		private final ListIterator<T> innerDelegate;

		public InnerIterator() {
			super();
			innerDelegate = ReadOnlyBeanPropertyList.this.delegate.listIterator();
		}
		public InnerIterator(int index) {
			super();
			innerDelegate = ReadOnlyBeanPropertyList.this.delegate.listIterator(index);
		}

		@Override
		public boolean hasNext() {
			return innerDelegate.hasNext();
		}

		@Override
		public T next() {
			return innerDelegate.next();
		}

		@Override
		public boolean hasPrevious() {
			return innerDelegate.hasPrevious();
		}

		@Override
		public T previous() {
			return innerDelegate.previous();
		}

		@Override
		public int nextIndex() {
			return innerDelegate.nextIndex();
		}

		@Override
		public int previousIndex() {
			return innerDelegate.previousIndex();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("");
		}

		@Override
		public void set(T e) {
			throw new UnsupportedOperationException("");
		}

		@Override
		public void add(T e) {
			throw new UnsupportedOperationException("");
		}

	}

	final List<T> delegate;

	protected ReadOnlyBeanPropertyList(List<T> _initialValue) {
		super("readOnlyListProperty");
		delegate = _initialValue;
	}
	protected ReadOnlyBeanPropertyList(String _propertyName, List<T> _initialValue) {
		super(_propertyName);
		delegate = _initialValue;
	}
	protected ReadOnlyBeanPropertyList(Object _bean, String _propertyName, List<T> _initialValue) {
		super(_bean, _propertyName);
		delegate = _initialValue;
	}

	public void 				addListener(BeanPropertyListChangeListener<T> listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), listener);
	}
	public void 				removeListener(BeanPropertyListChangeListener<T> listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), listener);
	}

	protected final void 		fireListPropertyChange(ListPropertyChangeEvent.Change<T> _changes) {
		propertyChangeSupport().firePropertyChange( new ListPropertyChangeEvent<T>(getBean() != null ? getBean() : this, getName(), _changes) );
	}

	public List<T>				get() {
		throw new IllegalAccessError();
	}

	public T 					get(int _i) {
		return delegate.get(_i);
	}

	public int 					size() 							{ return delegate.size(); }
    public boolean 				isEmpty() 						{ return delegate.isEmpty(); }
    public boolean 				contains(Object o) 				{ return delegate.contains(o); }
    public Object[] 			toArray() 						{ return delegate.toArray(); }
    public <U> U[] 				toArray(U[] a) 					{ return delegate.toArray(a); }
    public boolean 				containsAll(Collection<?> c) 	{ return delegate.containsAll(c); }

    public int 					indexOf(Object o) 				{ return delegate.indexOf(o); }
    public int 					lastIndexOf(Object o) 			{ return delegate.lastIndexOf(o); }

    @Override
	public Iterator<T> 			iterator() {
		return new InnerIterator();
	}

	public ListIterator<T> 		listIterator() {
		return new InnerIterator();
	}

	public ListIterator<T> 		listIterator(int index) {
		return new InnerIterator(index);
	}

	public List<T> 				subList(int fromIndex, int toIndex) {
		return new InnerSubList(fromIndex, toIndex);
	}

    public Spliterator<T> 		spliterator() { 
    	// TODO:: ChangeIt for internal Spliterator
    	return delegate.spliterator(); 
    }
    
}
