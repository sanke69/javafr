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
package fr.java.draw.tools;

import java.util.Optional;

public interface Font {

	public static Font of(double _size) {
		return new Font() {
			@Override public double 			getSize() { return _size; }
			@Override public Optional<String> 	getName() { return Optional.empty(); }
		};
	}
	public static Font of(String _name) {
		return new Font() {
			@Override public double 			getSize() { return 12; }
			@Override public Optional<String> 	getName() { return Optional.of(_name); }
		};
	}
	public static Font of(String _name, double _size) {
		return new Font() {
			@Override public double 			getSize() { return _size; }
			@Override public Optional<String> 	getName() { return Optional.of(_name); }
		};
	}

	public default double 	getSize() 		{ return 12; }
	public Optional<String> getName();

}
