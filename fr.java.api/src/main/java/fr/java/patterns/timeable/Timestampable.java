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
 * @file     Timestamped.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.patterns.timeable;

import fr.java.lang.properties.Timestamp;

public interface Timestampable {
	
	public interface WithValue<V> extends Timestampable {

		public V getValue();

	}

	public static     Timestampable              of(Timestamp _timestamp) {
		return new Timestampable() {
			public Timestamp getTimestamp() { return _timestamp; }
		};
	}
	public static <V> Timestampable.WithValue<V> of(Timestamp _timestamp, V _value) {
		return new Timestampable.WithValue<V>() {
			public Timestamp getTimestamp() { return _timestamp; }
			public V         getValue()     { return _value; }
		};
	}

	public Timestamp getTimestamp();

}