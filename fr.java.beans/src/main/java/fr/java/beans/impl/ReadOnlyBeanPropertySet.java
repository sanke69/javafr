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
import java.util.Set;
import java.util.Spliterator;

import fr.java.beans.impl.events.SetPropertyChangeEvent;
import fr.java.beans.properties.listeners.BeanPropertySetChangeListener;

/*
// Set methods for access only
// ----------------------------
int size();
boolean isEmpty();
boolean contains(Object o);
Iterator<E> iterator();
Object[] toArray();
<T> T[] toArray(T[] a);


// Modification Operations
boolean add(E e);
boolean remove(Object o);


// Bulk Operations
boolean containsAll(Collection<?> c);
boolean addAll(Collection<? extends E> c);
boolean retainAll(Collection<?> c);
boolean removeAll(Collection<?> c);
void clear();


// Comparison and hashing
boolean equals(Object o);
int hashCode();
Spliterator<E> spliterator()
**/
public abstract class ReadOnlyBeanPropertySet<T> extends AbstractBeanProperty<Set<T>> implements Iterable<T> {

	public class InnerIterator implements Iterator<T> {

		private final Iterator<T> innerDelegate;

		public InnerIterator() {
			super();
			innerDelegate = ReadOnlyBeanPropertySet.this.delegate.iterator();
		}
		public InnerIterator(Iterator<T> _innerDelegate) {
			super();
			innerDelegate = _innerDelegate;
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
		public void remove() {
			throw new UnsupportedOperationException("");
		}

	}

	final Set<T> delegate;

	protected ReadOnlyBeanPropertySet(Set<T> _initialValue) {
		super("readOnlyListProperty");
		delegate = _initialValue;
	}
	protected ReadOnlyBeanPropertySet(String _propertyName, Set<T> _initialValue) {
		super(_propertyName);
		delegate = _initialValue;
	}
	protected ReadOnlyBeanPropertySet(Object _bean, String _propertyName, Set<T> _initialValue) {
		super(_bean, _propertyName);
		delegate = _initialValue;
	}

	public void 				addListener(BeanPropertySetChangeListener<T> listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), listener);
	}
	public void 				removeListener(BeanPropertySetChangeListener<T> listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), listener);
	}

	protected final void 		fireSetPropertyChange(SetPropertyChangeEvent.Change<T> _changes) {
		propertyChangeSupport().firePropertyChange( new SetPropertyChangeEvent<T>(getBean() != null ? getBean() : this, getName(), _changes) );
	}

	public Set<T>				get() {
		throw new IllegalAccessError();
	}

	public int 					size() 							{ return delegate.size(); }
    public boolean 				isEmpty() 						{ return delegate.isEmpty(); }
    public boolean 				contains(Object o) 				{ return delegate.contains(o); }
    public Object[] 			toArray() 						{ return delegate.toArray(); }
    public <U> U[] 				toArray(U[] a) 					{ return delegate.toArray(a); }
    public boolean 				containsAll(Collection<?> c) 	{ return delegate.containsAll(c); }

    @Override
	public Iterator<T> 			iterator() {
		return new InnerIterator();
	}

    public Spliterator<T> 		spliterator() { 
    	// TODO:: ChangeIt for internal Spliterator
    	return delegate.spliterator(); 
    }
    
}
