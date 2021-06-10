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

public abstract class ReadOnlyByteBeanProperty extends AbstractBeanProperty<Byte> {

	Byte value;

	public ReadOnlyByteBeanProperty() {
		super("byteBeanProperty");
		value = (byte) 0;
	}
	public ReadOnlyByteBeanProperty(Byte _initialValue) {
		super("byteBeanProperty");
		value = _initialValue;
	}
	public ReadOnlyByteBeanProperty(String _propertyName) {
		super(_propertyName);
		value = (byte) 0;
	}
	public ReadOnlyByteBeanProperty(String _propertyName, Byte _initialValue) {
		super(_propertyName);
		value = _initialValue;
	}
	public ReadOnlyByteBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
		value = (byte) 0;
	}
	public ReadOnlyByteBeanProperty(Object _bean, String _propertyName, Byte _initialValue) {
		super(_bean, _propertyName);
		value = _initialValue;
	}

	@Override
	public Byte 		get() {
		return value;
	}

}
