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
 * @file     Classes.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import static java.util.Arrays.asList;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Classes {

	public static boolean 				isInterface(Class<?> _class) {
		return _class.isInterface();
	}
	public static boolean 				isAbstract(Class<?> _class) {
		int modifiers = _class.getModifiers();
		return !_class.isInterface() && Modifier.isAbstract(modifiers);
	}
	public static boolean 				isConcrete(Class<?> _class) {
		int modifiers = _class.getModifiers();
		return !_class.isInterface() && !Modifier.isAbstract(modifiers);
	}
	public static boolean				isStritFP(Class<?> _class) {
		int modifiers = _class.getModifiers();
		return !_class.isInterface() && !Modifier.isStrict(modifiers);
	}

	public static boolean 				isInnerClass(Class<?> _class) {
		return !Modifier.isStatic(_class.getModifiers()) && _class.getEnclosingClass() != null;
	}

	public static boolean 				isGeneric(Class<?> _class) {
		boolean hasTypeParameters        = hasTypeParameters(_class);
		boolean hasGenericSuperclass     = hasGenericSuperclass(_class);
//      boolean hasGenericSuperinterface = hasGenericSuperinterface(c);
		boolean isGeneric = hasTypeParameters || hasGenericSuperclass; // || hasGenericSuperinterface;

		return isGeneric;
	}
	public static boolean 				isGeneric(Class<?> _class, boolean _checkInheritance) {
		boolean hasTypeParameters        = hasTypeParameters(_class);
		if(!_checkInheritance)
			return hasTypeParameters;

		boolean hasGenericSuperclass     = hasGenericSuperclass(_class);
//      boolean hasGenericSuperinterface = hasGenericSuperinterface(c);

		return hasTypeParameters || hasGenericSuperclass; // || hasGenericSuperinterface;
	}

    public static Set<Class<?>> 		getInheritance(Class<?> _class, boolean _onlySuperClass) {
    	Set<Class<?>> inheritage = new LinkedHashSet<Class<?>>();

    	if(!_onlySuperClass)
			Arrays.asList(_class.getInterfaces())
				  .stream()
				  .forEach(inheritage::add);

		if(_class.getSuperclass() != null && _class.getSuperclass() != Object.class) {
	    	inheritage.add(_class.getSuperclass());
			inheritage.addAll( getInheritance(_class.getSuperclass(), _onlySuperClass) );
		}

		return inheritage;
    }
	public static Set<Class<?>> 		getInterfaces(final Class<?> _class) {
    	Set<Class<?>> interfaces = new LinkedHashSet<Class<?>>();
		Class<?> clazz = _class;
		while(clazz.getSuperclass() != null) {
			interfaces.addAll(asList(clazz.getInterfaces()));
			clazz = clazz.getSuperclass();
		}
		return interfaces;
	}

	static boolean 						hasTypeParameters(Class<?> c) {
		return c.getTypeParameters().length > 0;
	}
	static boolean 						hasGenericSuperclass(Class<?> c) {
		Class<?> testClass = c;

		while (testClass != null) {
			Type t = testClass.getGenericSuperclass();

			if (t instanceof ParameterizedType)
				return true;

			testClass = testClass.getSuperclass();
		}

		return false;
	}
	static boolean 						hasGenericSuperinterface(Class<?> c) {
		for (Type t : c.getGenericInterfaces())
			if (t instanceof ParameterizedType)
				return true;

		return false;
	}

}