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
package fr.java.patterns.measurable;

public interface Value {

	public static Value 	of(double _value) { return new Value() { @Override public double get() { return _value; } }; }

	public double 			get();

	public default double 	micro() { return Modifier.Micro.apply( get() ); }
	public default double 	milli() { return Modifier.Milli.apply( get() ); }

	public default double 	kilo() 	{ return Modifier.Kilo.apply( get() ); }
	public default double 	mega() 	{ return Modifier.Mega.apply( get() ); }
	public default double 	giga() 	{ return Modifier.Giga.apply( get() ); }
	public default double 	tera() 	{ return Modifier.Tera.apply( get() ); }
	public default double 	peta() 	{ return Modifier.Peta.apply( get() ); }

}
