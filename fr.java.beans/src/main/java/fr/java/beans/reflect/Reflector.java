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
 * @file     Reflector.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.beans.reflect;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Set;

import fr.java.lang.exceptions.GenericClassException;
import fr.java.lang.exceptions.NotABeanException;

public class Reflector {

	public static boolean 							isGeneric(Object _obj) {
		return JavaReflector.isGeneric(_obj);
	}
	public static boolean 							isGeneric(Object _obj, boolean _checkInheritance) {
		return JavaReflector.isGeneric(_obj, _checkInheritance);
	}
	public static boolean 							isGeneric(Class<?> _class) {
		return JavaReflector.isGeneric(_class);
	}
	public static boolean 							isGeneric(Class<?> _class, boolean _checkInheritance) {
		return JavaReflector.isGeneric(_class, _checkInheritance);
	}

	public static final boolean 					isPOJO(Object _obj) {
		return JavaReflector.isPOJO(_obj);
	}
	public static final boolean 					isJavaBean(Object _obj) {
		return JavaReflector.isJavaBean(_obj);
	}
	public static final boolean 					isTaggedBean(Object _obj) {
		return JavaReflector.isTaggedBean(_obj);
	}
	public static final boolean 					isBean(Object _obj) {
		return JavaReflector.isBean(_obj);
	}

	public static final boolean 					isComponent(Object _obj) {
		return CompositeReflector.isComponent(_obj);
	}
	public static final boolean 					isComposite(Object _obj) {
		return CompositeReflector.isComposite(_obj);
	}

	public static final Object 						getParent(Object _object) {
		return CompositeReflector.getParent(_object);
	}
	public static final Set<?> 						getChildren(Object _object) {
		return CompositeReflector.getChildren(_object);
	}

//	public static final boolean 					isIdentifiable(Object _obj) { return false; }
//	public static final ID		 					getId(Object _obj) 			{ return null; }
//	public static final boolean 					isNameable(Object _obj) 	{ return false; }
//	public static final String	 					getName(Object _obj) 		{ return null; }

	public static boolean 							hasDefaultConstructor(Object _obj) {
		return JavaReflector.hasDefaultConstructor(_obj);
	}
	public static boolean 							hasDefaultConstructor(Class<?> _clazz) {
		return JavaReflector.hasDefaultConstructor(_clazz);
	}
	
	public static boolean 							hasInheritance(Object _obj) {
		return JavaReflector.hasInheritance(_obj);
	}

	public static boolean 							hasBeanInfo(Object _obj) {
		return JavaReflector.hasBeanInfo(_obj.getClass());
	}
	public static BeanInfo 							getBeanInfo(Object _obj) {
		return BeanReflector.getBeanInfo(_obj);
	}

	public static BeanDescriptor 					getBeanDescriptor(Object _obj) throws NotABeanException {
		return BeanReflector.getBeanDescriptor(_obj);
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj) throws NotABeanException, GenericClassException {
		return BeanReflector.getPropertyDescriptors(_obj);
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj, Class<?> _stopClass) throws NotABeanException, GenericClassException {
		return BeanReflector.getPropertyDescriptors(_obj, _stopClass);
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj, Collection<Class<?>> _stopClasses) throws NotABeanException, GenericClassException {
		return BeanReflector.getPropertyDescriptors(_obj, _stopClasses);
	}

	

	
}
