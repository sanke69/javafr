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
 * @file     SimpleBooleanBeanProperty.java
 * @version  0.0.0.1
 * @date     2018/04/27
 * 
**/
package fr.java.beans.impl;

public class SimpleByteBeanProperty extends ByteBeanProperty {

	public SimpleByteBeanProperty() {
		super("byteBeanProperty");
	}
	public SimpleByteBeanProperty(Byte _initialValue) {
		super(_initialValue);
	}
	public SimpleByteBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	public SimpleByteBeanProperty(String _propertyName, Byte _initialValue) {
		super(_propertyName, _initialValue);
	}
	public SimpleByteBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	public SimpleByteBeanProperty(Object _bean, String _propertyName, Byte _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

}