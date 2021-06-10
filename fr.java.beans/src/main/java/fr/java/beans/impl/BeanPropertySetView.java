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
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.java.beans.impl.events.SetPropertyChangeEvent;
import fr.java.beans.properties.BeanProperty;

public /*abstract*/ class BeanPropertySetView<T> extends BeanPropertySet<T> implements BeanProperty<Set<T>>, Set<T> {

	public class InnerIterator implements Iterator<T> {

		private final Iterator<?> innerDelegate;
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
			return lastResult = mapper.apply(innerDelegate.next());
		}

		@Override
		public void remove() {
			innerDelegate.remove();

			Set<T> view = delegate.stream().map(mapper).collect(Collectors.toSet());
			fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(view) {
				@Override public T getElementAdded() 	{ return null; }
				@Override public T getElementRemoved() 	{ return lastResult; }
			});
		}

	}

	final BeanPropertySet<?> 	delegate;
	final Function<Object, T> 	mapper;

	public <U> BeanPropertySetView(BeanPropertySet<U> _setProperty, Function<U, T> _mapper) {
		this(_setProperty.getBean(), _setProperty.getName(), _setProperty, _mapper);
	}
	public <U> BeanPropertySetView(String _propertyName, BeanPropertySet<U> _setProperty, Function<U, T> _mapper) {
		this(_setProperty.getBean(), _propertyName, _setProperty, _mapper);
	}
	public <U> BeanPropertySetView(Object _bean, String _propertyName, BeanPropertySet<U> _setProperty, Function<U, T> _mapper) {
		super(_bean, _propertyName, null);
		delegate = _setProperty;
		mapper   = (Function<Object, T>) _mapper;

		_setProperty.addListener((prop, _change) -> {
			Set<T> view = _setProperty.stream().map(_mapper).collect(Collectors.toSet());

			if(_change.wasAdded()) {
				U i = _change.getElementAdded();
				fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(view) {
					@Override public T getElementAdded() 	{ return _mapper.apply(i); }
					@Override public T getElementRemoved() 	{ return null; }
				});
			} else if(_change.wasRemoved()) {
				U i = _change.getElementRemoved();
				fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(view) {
					@Override public T getElementAdded() 	{ return null; }
					@Override public T getElementRemoved() 	{ return _mapper.apply(i); }
				});
			}
		});
	}

	@Override
	public void 				set(Set<T> _value) {
		throw new UnsupportedOperationException();
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
    public Object[] 			toArray() { 
    	return delegate.toArray();
	}
    public <U> U[] 				toArray(U[] a) { 
    	return delegate.toArray(a);
	}
    public boolean 				containsAll(Collection<?> c) {
    	return delegate.containsAll(c);
	}

    @Override
	public Iterator<T> 			iterator() {
    	return new InnerIterator();
	}

	@Override
	public boolean 				add(T e) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				remove(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void 				clear() {
		delegate.clear();
	}

}
