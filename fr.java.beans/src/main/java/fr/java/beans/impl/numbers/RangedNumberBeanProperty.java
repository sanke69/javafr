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
package fr.java.beans.impl.numbers;

import fr.java.beans.properties.BeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class RangedNumberBeanProperty<T extends Number> extends ReadOnlyRangedNumberBeanProperty<T> implements BeanProperty<T>, Ranged.Editable<T> {

	protected RangedNumberBeanProperty() {
		super();
	}
	protected RangedNumberBeanProperty(T _value, T _min, T _max) {
		super(_value, _min, _max);
	}
	protected RangedNumberBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	protected RangedNumberBeanProperty(String _propertyName, T _value, T _min, T _max) {
		super(_propertyName, _value, _min, _max);
	}
	protected RangedNumberBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	protected RangedNumberBeanProperty(Object _bean, String _propertyName, T _value, T _min, T _max) {
		super(_bean, _propertyName, _value, _min, _max);
	}

	@Override
	public void set(T _value) {
		set(_value);
	}
	@Override
	public void setValue(T _value) {
		set(_value);
	}

	@Override
	public void setRange(T _min, T _max) {
		min.set(_min);
		max.set(_max);
		firePropertyChange(null, get());
	}
	@Override
	public void setRange(Interval<T> _range) {
		min.set(_range.getMin());
		max.set(_range.getMax());
		firePropertyChange(null, get());
	}

	@Override
	public void setMin(T _min) {
		min.set(_min);
		firePropertyChange(null, get());
	}
	@Override
	public void setMax(T _max) {
		max.set(_max);
		firePropertyChange(null, get());
	}

}
