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

import fr.java.beans.impl.DoubleBeanProperty;
import fr.java.beans.impl.ReadOnlyDoubleBeanProperty;
import fr.java.beans.impl.SimpleDoubleBeanProperty;
import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class ReadOnlyRangedDoubleBeanProperty extends ReadOnlyDoubleBeanProperty implements Ranged<Double> {
	public static final String MIN   = "min";
	public static final String MAX   = "max";

	final DoubleBeanProperty min;
	final DoubleBeanProperty max;

	protected ReadOnlyRangedDoubleBeanProperty() {
		this(null, "rangedDouble", null, null, null);
	}
	protected ReadOnlyRangedDoubleBeanProperty(Double _value, Double _min, Double _max) {
		this(null, "rangedDouble", _value, _min, _max);
	}
	protected ReadOnlyRangedDoubleBeanProperty(String _propertyName) {
		this(null, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedDoubleBeanProperty(String _propertyName, Double _value, Double _min, Double _max) {
		this(null, _propertyName, _value, _min, _max);
	}
	protected ReadOnlyRangedDoubleBeanProperty(Object _bean, String _propertyName) {
		this(_bean, _propertyName, null, null, null);
	}
	protected ReadOnlyRangedDoubleBeanProperty(Object _bean, String _propertyName, Double _value, Double _min, Double _max) {
		super(_bean, _propertyName, _value);

		min   = new SimpleDoubleBeanProperty(this, MIN, _min);
		max   = new SimpleDoubleBeanProperty(this, MAX, _max);
	}

	public ReadOnlyDoubleBeanProperty 		valueProperty() {
		return this;
	}
	@Override
	public Double 							getValue() {
		return get();
	}

	public ReadOnlyDoubleBeanProperty 		minProperty() {
		return min;
	}
	@Override
	public Double							getMin() {
		return min.get();
	}

	public ReadOnlyDoubleBeanProperty 		maxProperty() {
		return min;
	}
	@Override
	public Double							getMax() {
		return max.get();
	}

	@Override
	public Interval<Double> 				getRange() {
		return Interval.of(getMin(), getMax());
	}

}
