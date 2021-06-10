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

import fr.java.beans.impl.NumberBeanProperty;
import fr.java.beans.impl.ReadOnlyNumberBeanProperty;
import fr.java.beans.impl.SimpleNumberBeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class ReadOnlyRangedNumberBeanProperty<T extends Number> extends ReadOnlyNumberBeanProperty<T> implements Ranged<T> {
	public static final String MIN   = "min";
	public static final String MAX   = "max";

	final NumberBeanProperty<T> min;
	final NumberBeanProperty<T> max;

	protected ReadOnlyRangedNumberBeanProperty() {
		this(null, "rangedNumber", null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(T _value, T _min, T _max) {
		this(null, "rangedNumber", _value, _min, _max);
	}
	protected ReadOnlyRangedNumberBeanProperty(String _propertyName) {
		this(null, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(String _propertyName, T _value, T _min, T _max) {
		this(null, _propertyName, _value, _min, _max);
	}
	protected ReadOnlyRangedNumberBeanProperty(Object _bean, String _propertyName) {
		this(_bean, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedNumberBeanProperty(Object _bean, String _propertyName, T _value, T _min, T _max) {
		super(_bean, _propertyName, _value);

		min   = new SimpleNumberBeanProperty<T>(this, MIN, _min);
		max   = new SimpleNumberBeanProperty<T>(this, MAX, _max);
	}

	public ReadOnlyNumberBeanProperty<T> 	valueProperty() {
		return this;
	}
	@Override
	public T 								getValue() {
		return get();
	}

	public ReadOnlyNumberBeanProperty<T> 	minProperty() {
		return min;
	}
	@Override
	public T 								getMin() {
		return min.get();
	}

	public ReadOnlyNumberBeanProperty<T> 	maxProperty() {
		return min;
	}
	@Override
	public T 								getMax() {
		return max.get();
	}

	@Override
	public Interval<T> 						getRange() {
		return Interval.of(getMin(), getMax());
	}

}
