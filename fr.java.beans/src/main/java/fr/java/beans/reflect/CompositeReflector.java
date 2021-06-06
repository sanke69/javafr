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
 * @file     CompositeReflector.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.beans.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

class CompositeReflector {
	public static final Class<?>[]  NO_PARAM_TYPE       = (Class<?>[]) null;
	public static final Object[]    NO_PARAM_ARGV       = (Object[]) null;
	public static final String   	GET_PARENT_METHOD   = "getParent";
	public static final String   	GET_CHILDREN_METHOD = "getChildren";
	public static final Class<?> 	GET_CHILDREN_RETURN = Set.class;
	
	public static final boolean 	isComponent(Object _object) {
		Class<?> clazz = _object.getClass();

		Method getParent = null;

		try {
			getParent = clazz.getMethod(GET_PARENT_METHOD, NO_PARAM_TYPE);
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}

		return getParent != null;
	}
	public static final boolean 	isComposite(Object _object) {
		Class<?> clazz = _object.getClass();

		Method getChildren = null;

		try {
			getChildren = clazz.getMethod(GET_CHILDREN_METHOD, NO_PARAM_TYPE);
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}

		return getChildren != null && GET_CHILDREN_RETURN.isAssignableFrom( getChildren.getReturnType() );
	}

	public static final Object 		getParent(Object _object) {
		return null;
	}
	public static final Set<?> 		getChildren(Object _object) {
		Class<?> clazz = _object.getClass();

		Method getChildren = null;

		try {
			getChildren = clazz.getMethod(GET_CHILDREN_METHOD, NO_PARAM_TYPE);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		if(getChildren == null || !GET_CHILDREN_RETURN.isAssignableFrom( getChildren.getReturnType() ))
			return null;

		Object getChildrenReturn = null;

		try {
			getChildrenReturn = getChildren.invoke(_object, NO_PARAM_ARGV);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}

		return (Set<?>) getChildrenReturn;
	}

}
