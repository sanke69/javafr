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

public abstract class ShortBeanProperty extends ReadOnlyShortBeanProperty implements BeanProperty<Short> {

	public ShortBeanProperty() {
		super("shortBeanProperty");
	}
	public ShortBeanProperty(Short _initialValue) {
		super(_initialValue);
	}
	public ShortBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	public ShortBeanProperty(String _propertyName, Short _initialValue) {
		super(_propertyName, _initialValue);
	}
	public ShortBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public ShortBeanProperty(Object _bean, String _propertyName, Short _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public void 		set(Short _value) {
		Short oldValue = get();
        try {
			fireVetoableChange(oldValue, _value);
			value = _value;
	        firePropertyChange(oldValue, _value);
		} catch(PropertyVetoException e) {
			value = oldValue;
		}
	}
	public void 		set(short _s) { set((Short) _s); }

}
