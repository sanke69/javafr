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

public class SimpleFloatBeanProperty extends FloatBeanProperty {

	public SimpleFloatBeanProperty() {
		super("floatBeanProperty");
	}
	public SimpleFloatBeanProperty(Float _initialValue) {
		super(_initialValue);
	}
	public SimpleFloatBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	public SimpleFloatBeanProperty(String _propertyName, Float _initialValue) {
		super(_propertyName, _initialValue);
	}
	public SimpleFloatBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public SimpleFloatBeanProperty(Object _bean, String _propertyName, Float _value) {
		super(_bean, _propertyName, _value);
	}

}
