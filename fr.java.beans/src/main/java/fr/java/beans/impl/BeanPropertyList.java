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

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

import fr.java.beans.properties.BeanProperty;

public abstract class BeanPropertyList<T> extends ReadOnlyBeanPropertyList<T> implements BeanProperty<List<T>>, List<T> { // , ListenableList<T>

	protected BeanPropertyList() {
		super(new ArrayList<T>());
	}
	protected BeanPropertyList(List<T> _initialValue) {
		super("listProperty", _initialValue);
	}
	protected BeanPropertyList(String _propertyName) {
		super(_propertyName, new ArrayList<T>());
	}
	protected BeanPropertyList(String _propertyName, List<T> _initialValue) {
		super(_propertyName, _initialValue);
	}
	protected BeanPropertyList(Object _bean, String _propertyName) {
		super(_bean, _propertyName, new ArrayList<T>());
	}
	protected BeanPropertyList(Object _bean, String _propertyName, List<T> _initialValue) {
		super(_bean, _propertyName, _initialValue);
	}

	@Override
	public Spliterator<T> 		spliterator() {
		return List.super.spliterator();
	}

}
