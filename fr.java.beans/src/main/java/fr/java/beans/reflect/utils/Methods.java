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
 * @file     Methods.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Methods extends AbstractRecursiveMembers<Methods> {

	public static Object 		invoke(Object _obj, String _methodName, Object... _args) throws Exception {
		Class<?>[] paramTypes = _args != null ? Parameters.asClasses(_args) : null;

		Method m = _obj.getClass().getMethod(_methodName, paramTypes);
		return m.invoke(_obj, _args);
	}
	public static Object 		invokeStatic(Class<?> _class, String _staticMethodName, Object... _args) throws Exception {
		Class<?>[] paramTypes = _args != null ? Parameters.asClasses(_args) : null;

		Method m = _class.getMethod(_staticMethodName, paramTypes);
		return m.invoke(null, _args);
	}

	public static Method 		get(Class<?> _c, String _methodName) {
		Method[] ms = _c.getMethods();
		for(Method m : ms)
			if(m.getName().compareToIgnoreCase(_methodName) == 0)
				return m;

		return null;
	}
	public static Method 		get(Class<?> _c, String _methodName, int _nbArg) {
		Method[] ms = _c.getMethods();
		for(Method m : ms)
			if(m.getName().compareToIgnoreCase(_methodName) == 0 && m.getParameterCount() == _nbArg)
				return m;

		return null;
	}

	public static List<Method> 	getAll(Class<?> _c) {
		return getMethods(_c, false, true, null);
	}
	public static List<Method> 	getAll(Class<?> _c, Class<?> _stopClass) {
		return getMethods(_c, false, true, Arrays.asList(_stopClass));
	}
	public static List<Method> 	getAll(Class<?> _c, Collection<Class<?>> _stopClasses) {
		return getMethods(_c, false, true, _stopClasses);
	}
	public static List<Method> 	getAll(Class<?> _c, Predicate<Method> _filter) {
		return getMethods(_c, false, true, null, _filter);
	}
	public static List<Method> 	getAll(Class<?> _c, Class<?> _stopClass, Predicate<Method> _filter) {
		return getMethods(_c, false, true, Arrays.asList(_stopClass), _filter);
	}
	public static List<Method> 	getAll(Class<?> _c, Collection<Class<?>> _stopClasses, Predicate<Method> _filter) {
		return getMethods(_c, false, true, _stopClasses, _filter);
	}

	public static List<Method> 	getVisibles(Class<?> _c) {
		return getMethods(_c, true, true, null);
	}
	public static List<Method> 	getVisibles(Class<?> _c, Class<?> _stopClass) {
		return getMethods(_c, true, true, Arrays.asList(_stopClass));
	}
	public static List<Method> 	getVisibles(Class<?> _c, Collection<Class<?>> _stopClasses) {
		return getMethods(_c, true, true, _stopClasses);
	}
	public static List<Method> 	getVisibles(Class<?> _c, Predicate<Method> _filter) {
		return getMethods(_c, true, true, null, _filter);
	}
	public static List<Method> 	getVisibles(Class<?> _c, Class<?> _stopClass, Predicate<Method> _filter) {
		return getMethods(_c, true, true, Arrays.asList(_stopClass), _filter);
	}
	public static List<Method> 	getVisibles(Class<?> _c, Collection<Class<?>> _stopClasses, Predicate<Method> _filter) {
		return getMethods(_c, true, true, _stopClasses, _filter);
	}

	public static List<Method> 	getDeclared(Class<?> _c) {
		return getMethods(_c, false, false, null);
	}
	public static List<Method> 	getDeclared(Class<?> _c, Predicate<Method> _filter) {
		return getMethods(_c, false, false, null, _filter);
	}

	private static List<Method> getMethods(Class<?> _c, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses) {
		List<Method> methods = new ArrayList<Method>();

		Arrays.asList(_c.getDeclaredMethods())
			  .stream()
			  .filter(f -> _only_public ? Modifier.isPublic(f.getModifiers()) : true)
			  .forEach(methods::add);

		recursiveGetterFunc(Methods::getMethods, methods, _c, _only_public, _with_inheritance, _stopClasses);
		return methods;
	}
	private static List<Method> getMethods(Class<?> _c, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses, Predicate<Method> _filter) {
		List<Method> methods = new ArrayList<Method>();

		Arrays.asList(_c.getDeclaredMethods())
			  .stream()
			  .filter(f -> _only_public ? Modifier.isPublic(f.getModifiers()) : true)
			  .filter(_filter)
			  .forEach(methods::add);

		recursiveGetterFuncWithPredicate(Methods::getMethods, methods, _c, _only_public, _with_inheritance, _stopClasses, _filter);
		return methods;
	}

}
