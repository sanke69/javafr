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
package fr.java.beans.reflect.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fields extends AbstractRecursiveMembers<Fields> {

	public static boolean 		setValueOf(Object _o, String _field, Object _value, boolean _forceAccess) {
		boolean isAccessible;
		Field   f;

		try {
			f = _o.getClass().getField(_field);

			if(!(isAccessible = f.isAccessible()) && _forceAccess)
				f.setAccessible(true);

			f.set(_o, _value);

			if(_forceAccess)
				f.setAccessible(isAccessible);
			return true;
		} catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return false;
		}
	}
	public static Object 		getValueOf(Object _o, String _field, boolean _forceAccess) {
		boolean isAccessible;
		Field   f;
		Object  o;
		try {
			f = _o.getClass().getField(_field);

			if(!(isAccessible = f.isAccessible()) && _forceAccess)
				f.setAccessible(true);

			o = f.get(_o);

			if(_forceAccess)
				f.setAccessible(isAccessible);

			return o;
		} catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}

	public static boolean 		isPrivate(final Field _f) {
		return java.lang.reflect.Modifier.isPrivate(_f.getModifiers());
	}
	public static boolean 		isProtected(final Field _f) {
		return java.lang.reflect.Modifier.isProtected(_f.getModifiers());
	}
	public static boolean 		isPublic(final Field _f) {
		return java.lang.reflect.Modifier.isPublic(_f.getModifiers());
	}
	public static boolean 		isFinal(final Field _f) {
		return java.lang.reflect.Modifier.isFinal(_f.getModifiers());
	}
	public static boolean 		isStatic(final Field _f) {
		return java.lang.reflect.Modifier.isStatic(_f.getModifiers());
	}
	public static boolean 		isTransient(final Field _f) {
		return java.lang.reflect.Modifier.isTransient(_f.getModifiers());
	}

	public static Field 		get(Class<?> _c, String _fieldName) {
		Field[] fs = _c.getDeclaredFields();
		for(Field f : fs)
			if(f.getName().compareTo(_fieldName) == 0)
				return f;
		return null;
	}
	public static Field 		get(Class<?> _c, String _fieldName, boolean _seekInHeritage) {
		Field[] fs = _c.getDeclaredFields();
		for(Field f : fs)
			if(f.getName().compareTo(_fieldName) == 0)
				return f;
		
		if(_seekInHeritage) {
			List<Field> candidates = getAll(_c).stream()
														.filter(f -> f.getName().compareTo(_fieldName) == 0)
														.collect(Collectors.toList());
			if(!candidates.isEmpty())
				return candidates.get(0);
		}
		
		return null;
	}

	public static List<Field> 	getAll(Class<?> _c) {
		return getFields(_c, false, true, null);
	}
	public static List<Field> 	getAll(Class<?> _c, Class<?> _stopClass) {
		return getFields(_c, false, true, Arrays.asList(_stopClass));
	}
	public static List<Field> 	getAll(Class<?> _c, Collection<Class<?>> _stopClasses) {
		return getFields(_c, false, true, _stopClasses);
	}
	public static List<Field> 	getAll(Class<?> _c, Predicate<Field> _filter) {
		return getFields(_c, false, true, null, _filter);
	}
	public static List<Field> 	getAll(Class<?> _c, Class<?> _stopClass, Predicate<Field> _filter) {
		return getFields(_c, false, true, Arrays.asList(_stopClass), _filter);
	}
	public static List<Field> 	getAll(Class<?> _c, Collection<Class<?>> _stopClasses, Predicate<Field> _filter) {
		return getFields(_c, false, true, _stopClasses, _filter);
	}

	public static List<Field> 	getVisibles(Class<?> _c) {
		return getFields(_c, true, true, null);
	}
	public static List<Field> 	getVisibles(Class<?> _c, Class<?> _stopClass) {
		return getFields(_c, true, true, Arrays.asList(_stopClass));
	}
	public static List<Field> 	getVisibles(Class<?> _c, Collection<Class<?>> _stopClasses) {
		return getFields(_c, true, true, _stopClasses);
	}
	public static List<Field> 	getVisibles(Class<?> _c, Predicate<Field> _filter) {
		return getFields(_c, true, true, null, _filter);
	}
	public static List<Field> 	getVisibles(Class<?> _c, Class<?> _stopClass, Predicate<Field> _filter) {
		return getFields(_c, true, true, Arrays.asList(_stopClass), _filter);
	}
	public static List<Field> 	getVisibles(Class<?> _c, Collection<Class<?>> _stopClasses, Predicate<Field> _filter) {
		return getFields(_c, true, true, _stopClasses, _filter);
	}

	public static List<Field> 	getDeclared(Class<?> _c) {
		return getFields(_c, false, false, null);
	}
	public static List<Field> 	getDeclared(Class<?> _c, Predicate<Field> _filter) {
		return getFields(_c, false, false, null, _filter);
	}

	private static List<Field> 	getFields(Class<?> _c, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses) {
		List<Field> fields = new ArrayList<Field>();

		Arrays.asList(_c.getDeclaredFields())
			  .stream()
			  .filter(f -> _only_public ? Modifier.isPublic(f.getModifiers()) : true)
			  .forEach(fields::add);

		recursiveGetterFunc(Fields::getFields, fields, _c, _only_public, _with_inheritance, _stopClasses);
		return fields;
	}
	private static List<Field> 	getFields(Class<?> _c, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses, Predicate<Field> _filter) {
		List<Field> fields = new ArrayList<Field>();

		Arrays.asList(_c.getDeclaredFields())
			  .stream()
			  .filter(f -> _only_public ? Modifier.isPublic(f.getModifiers()) : true)
			  .filter(_filter)
			  .forEach(fields::add);

		recursiveGetterFuncWithPredicate(Fields::getFields, fields, _c, _only_public, _with_inheritance, _stopClasses, _filter);
		return fields;
	}

}
