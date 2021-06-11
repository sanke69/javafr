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
package fr.java.math.numbers;

public interface Interval<T extends Number> {

	public static interface Editable<T extends Number> extends Interval<T> {

		public void setRange(T _min, T _max);

		public void setMin(T _min);
		public void setMax(T _max);
		
	}

	public static <U extends Number> Interval<U> of(final U _min, final U _max) {
		return new Interval<U>() {
			public U getMin() { return _min; }
			public U getMax() { return _max; }
		};
	}

//	public default double getWidth() { return getMax().doubleValue() - getMin().doubleValue(); }

	public T getMin();
	public T getMax();

	public default double getWidth() { return getMax().doubleValue() - getMin().doubleValue(); }

}
