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
module javafr.beans {
	requires java.desktop;
	requires java.logging;

	requires jdk.unsupported;

	requires transitive javafr;

	exports fr.java.beans;
	exports fr.java.beans.annotations;
	exports fr.java.beans.properties;
	exports fr.java.beans.impl.events;
	exports fr.java.beans.properties.listeners;
	exports fr.java.beans.properties.listeners.vetoable;
	exports fr.java.beans.impl;

	exports fr.java.beans.reflect;
	exports fr.java.beans.reflect.types;
	exports fr.java.beans.reflect.utils;

	exports fr.java.beans.proxies;

	exports fr.java.beans.utils;

	exports fr.java.patterns;

}
