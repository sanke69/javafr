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
 * @file     Constructors.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import java.lang.reflect.Constructor;

public class Constructors {

	public static boolean 			hasDefault(Class<?> _class) throws SecurityException {
	    Class<?>[] empty = {};
	    try {
	        _class.getConstructor(empty);			// Ensured here that it's public!
	    } catch (NoSuchMethodException e) { 
	    	try {
				Object o = _class.newInstance();	// Ensured here that it's accessible!
				return o != null;
			} catch (InstantiationException | IllegalAccessException e1) {
				return false;
			}
	    }
	    return true;
	}

	public static Constructor<?> 	get(Class<?> _class, Class<?>... _args) {
		try {
			return _class.getConstructor(_args);
		} catch(NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	public static Constructor<?>[] 	getAll(Class<?> _class) {
		try {
			return _class.getConstructors();
		} catch(SecurityException e) {
			return null;
		}
	}
	public static Class<?>[] 		getArgs(Constructor<?> _constructor) {
		return _constructor.getParameterTypes();
	}

}
