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

public abstract class ReadOnlyNumberBeanProperty<T extends Number> extends AbstractBeanProperty<T> {

	T value;

	public ReadOnlyNumberBeanProperty() {
		super("number");
		value = null;
	}
	public ReadOnlyNumberBeanProperty(T _initialValue) {
		super("number");
		value = _initialValue;
	}
	public ReadOnlyNumberBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public ReadOnlyNumberBeanProperty(String _propertyName, T _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public ReadOnlyNumberBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public ReadOnlyNumberBeanProperty(Object _bean, String _propertyName, T _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public T 		get() {
		return value;
	}

}
