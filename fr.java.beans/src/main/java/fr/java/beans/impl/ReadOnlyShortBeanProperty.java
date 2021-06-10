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

public abstract class ReadOnlyShortBeanProperty extends AbstractBeanProperty<Short> {

	Short value;

	public ReadOnlyShortBeanProperty() {
		super("shortBeanProperty");
		value = null;
	}
	public ReadOnlyShortBeanProperty(Short _initialValue) {
		super("shortBeanProperty");
		value = _initialValue;
	}
	public ReadOnlyShortBeanProperty(String _propertyName) {
		super(_propertyName);
		value = null;
	}
	public ReadOnlyShortBeanProperty(String _propertyName, Short _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public ReadOnlyShortBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public ReadOnlyShortBeanProperty(Object _bean, String _propertyName, Short _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public Short 		get() {
		return value;
	}

}
