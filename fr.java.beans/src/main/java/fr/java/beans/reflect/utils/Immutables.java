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
 * @file     Immutables.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Immutables {

	private static final Set<Class<?>> knownImmutables = new HashSet<Class<?>>(Arrays.asList(
	        Boolean.class,
	        Byte.class,
	        Short.class,
	        Integer.class,
	        Long.class,
	        Float.class,
	        Double.class,
	        BigInteger.class,
	        BigDecimal.class,
	        String.class
	));

	public static boolean isImmutable(Class<?> _class) {
		if(knownImmutables.contains(_class))
			return true;
	    return isImmutableTest(_class);
	}
	
	static boolean isImmutableTest(Class<?> _class) {
	    // Class of the object must be a direct child class of the required class
//	    Class<?> superClass = _class.getSuperclass();
//	    if (!Immutable.class.equals(superClass))
//	        return false;

	    // Class must be final
	    if(!Modifier.isFinal(_class.getModifiers()))
	        return false;

	    // Check all fields defined in the class for type and if they are final
	    Field[] objFields = _class.getDeclaredFields();
	    for(Field f : objFields)
	        if(!Modifier.isFinal(f.getModifiers()) || !isValidFieldType(f.getType()))
	            return false;

	    // Lets hope we didn't forget something
	    return true;
	}

	static boolean isValidFieldType(Class<?> type) {
	    // Check for all allowed property types...
	    return type.isPrimitive() || String.class.equals(type) || isImmutable(type);
	}

}
