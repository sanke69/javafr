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
 * @file     Ranged.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.numbers;

import fr.java.math.Interval;

public interface Ranged<T extends Number> extends Interval<T> {

	public static interface Editable<T extends Number> extends Ranged<T>, Interval.Editable<T> {

		public void setValue(T _value);
		public void setRange(Interval<T> _range);

	}

	public T           getValue();
	public Interval<T> getRange();

}
