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
package fr.java.beans.reflect;

import java.beans.BeanInfo;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import fr.java.beans.annotations.BeanClass;
import fr.java.beans.reflect.utils.Classes;
import fr.java.beans.reflect.utils.Constructors;

class JavaReflector {
	static final List<Class<?>> IGNORED_SUPER_CLASSES    = Arrays.asList(
			Object.class
			);
	static final List<Class<?>> IGNORED_SUPER_INTERFACES = Arrays.asList(
			Serializable.class
			);

	// Setter
    static final String SET_PREFIX    = "set";
    // Getters
    static final String GET_PREFIX    = "get";
    static final String IS_PREFIX     = "is";
    static final String HAS_PREFIX    = "has";
    // Collections
    static final String ADD_PREFIX    = "add";
    static final String REMOVE_PREFIX = "remove";

    static class Utils {

    	public static String 		upperFirst(String _string) {
    		if(_string.length() > 1)
    			return _string.substring(0, 1).toUpperCase() + _string.substring(1);
    		else
    			return _string.substring(0, 1).toUpperCase();
    	}
    	public static String 		lowerFirst(String _string) {
    		if(_string.length() > 1)
    			return _string.substring(0, 1).toLowerCase() + _string.substring(1);
    		else
    			return _string.substring(0, 1).toLowerCase();
    	}

    }
    
	public static boolean 							isPOJO(Object _obj) {
		Class<?> clazz = _obj.getClass();

		boolean hasDefaultConstructor = hasDefaultConstructor(clazz);
		boolean hasNoInheritance      = !hasInheritance(clazz);

		return hasDefaultConstructor && hasNoInheritance;
	}
	public static boolean 							isJavaBean(Object _obj) {
		Class<?> clazz = _obj.getClass();

		boolean isSerializable        = Classes.getInheritance(clazz, false).contains(Serializable.class);
		boolean hasDefaultConstructor = hasDefaultConstructor(clazz);
		boolean hasProperties         = BeanReflector.hasProperties(clazz);

		return isSerializable && hasDefaultConstructor && hasProperties;
	}
	public static boolean 							isTaggedBean(Object _obj) {
		Class<?> clazz = _obj.getClass();

		BeanClass beanFlag = (BeanClass) clazz.getAnnotation(BeanClass.class);
		if(beanFlag != null)
			return true;

		return false;
	}
	public static boolean 							isBean(Object _obj) {
		return isJavaBean(_obj) || isTaggedBean(_obj);
	}

	public static boolean 							hasDefaultConstructor(Object _obj) {
		return Constructors.hasDefault(_obj.getClass());
	}
	public static boolean 							hasDefaultConstructor(Class<?> _class) {
		return Constructors.hasDefault(_class);
	}

	public static boolean 							hasInheritance(Object _obj) {
		return !Classes.getInheritance(_obj.getClass(), false).isEmpty();
	}
	public static boolean 							hasInheritance(Class<?> _class) {
		return !Classes.getInheritance(_class, false).isEmpty();
	}

	public static boolean 							implementsInterface(Object _obj) {
		return !Classes.getInterfaces(_obj.getClass()).isEmpty();
	}
	public static boolean 							implementsInterface(Class<?> _class) {
		return !Classes.getInterfaces(_class).isEmpty();
	}

	public static boolean 							isImplement(Object _obj, Class<?> _interface) {
		return Classes.getInterfaces(_obj.getClass()).contains(_interface);
	}
	public static boolean 							isImplement(Class<?> _class, Class<?> _interface) {
		return Classes.getInterfaces(_class).contains(_interface);
	}

	public static boolean 							isGeneric(Object _obj) {
		return isGeneric(_obj.getClass());
	}
	public static boolean 							isGeneric(Object _obj, boolean _checkInheritance) {
		return isGeneric(_obj.getClass(), _checkInheritance);
	}
	public static boolean 							isGeneric(Class<?> _class) {
		return Classes.isGeneric(_class);
	}
	public static boolean 							isGeneric(Class<?> _class, boolean _checkInheritance) {
		return Classes.isGeneric(_class, _checkInheritance);
	}

	public static boolean 							hasBeanInfo(Object _obj) {
		return hasBeanInfo(_obj.getClass());
	}
	public static boolean 							hasBeanInfo(Class<?> _class) {
		String pck = _class.getPackage().getName(); 
		String cls = _class.getSimpleName();

		String beanInfo = pck + '.' + cls + "BeanInfo";

		try {
			Class<?> beanInfoClass = Class.forName(beanInfo);
			return beanInfoClass != null && BeanInfo.class.isAssignableFrom(beanInfoClass);
		} catch( ClassNotFoundException e ) {
			 return false;
		}
		
	}

}
