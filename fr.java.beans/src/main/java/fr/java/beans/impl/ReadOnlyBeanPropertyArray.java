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

import fr.java.beans.properties.listeners.BeanPropertyIndexedChangeListener;

public abstract class ReadOnlyBeanPropertyArray<T> extends AbstractBeanProperty<T[]> {

	T[] delegate;

	public ReadOnlyBeanPropertyArray(T[] _initialArray) {
		super(null, "array");
		delegate = _initialArray.clone();
	}
	public ReadOnlyBeanPropertyArray(String _propertyName, T[] _initialArray) {
		super(null, _propertyName);
		delegate = _initialArray.clone();
	}
	public ReadOnlyBeanPropertyArray(Object _bean, String _propertyName, T[] _initialArray) {
		super(_bean, _propertyName);
		delegate = _initialArray.clone();
	}

	public void 				addListener(BeanPropertyIndexedChangeListener<T> _listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), _listener);
	}
	public void 				removeListener(BeanPropertyIndexedChangeListener<T> _listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), _listener);
	}

	protected final void 		fireIndexedPropertyChange(int index, T oldValue, T newValue) {
		propertyChangeSupport().fireIndexedPropertyChange(getName(), index, oldValue, newValue);
	}

	@Override
	public T[] get() {
		throw new IllegalAccessError();
//		return delegate.clone();
	}

	public T get(int _index) {
		return delegate[_index];
	}

}
