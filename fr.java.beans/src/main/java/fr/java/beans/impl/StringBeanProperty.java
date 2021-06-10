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

import java.beans.PropertyVetoException;

import fr.java.beans.properties.BeanProperty;

public abstract class StringBeanProperty extends ReadOnlyStringBeanProperty implements BeanProperty<String> {

	public StringBeanProperty() {
		super("stringBeanProperty");
		value = null;
	}
	public StringBeanProperty(String _initialValue) {
		super("stringBeanProperty", _initialValue);
	}
	public StringBeanProperty(String _propertyName, String _initialValue) {
		super(_propertyName, _initialValue);
	}
	public StringBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public StringBeanProperty(Object _bean, String _propertyName, String _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public void 		set(String _value) {
		String oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}

	public String asString() { return get(); }

}
