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
 * @file     BeanReflector.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.beans.reflect;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import fr.java.beans.annotations.BeanClass;
import fr.java.beans.annotations.BeanPropertyField;
import fr.java.beans.annotations.BeanPropertyGetter;
import fr.java.beans.annotations.BeanPropertySetter;
import fr.java.beans.annotations.BeanPropertyTag;
import fr.java.beans.reflect.types.TypeFactory;
import fr.java.beans.reflect.types.TypeResolver;
import fr.java.beans.reflect.utils.Annotations;
import fr.java.beans.reflect.utils.Fields;
import fr.java.beans.reflect.utils.Methods;
import fr.java.beans.reflect.utils.Types;
import fr.java.lang.exceptions.GenericClassException;
import fr.java.lang.exceptions.MalformedBeanException;
import fr.java.lang.exceptions.NotABeanException;

class BeanReflector extends JavaReflector {
	private static final boolean       PUBLIC_FIELD_AS_PROPERTY = false;
	private static final Set<Class<?>> KNOWN_STOP_CLASSES       = new HashSet<Class<?>>();
	
	static {
		KNOWN_STOP_CLASSES.add( Object.class );
	}

	public static BeanDescriptor 					getBeanDescriptor(Object _obj) throws NotABeanException {
		if(!isBean(_obj))
			throw new NotABeanException();

		Class<?>       beanClass      = _obj.getClass();
		BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass);
		BeanClass      beanAnnotation = (BeanClass) beanClass.getAnnotation(BeanClass.class);

		if(beanAnnotation != null) {
			if(beanAnnotation.display().compareToIgnoreCase("default") != 0)
				beanDescriptor.setDisplayName(beanAnnotation.display());

			if(beanAnnotation.description().compareToIgnoreCase("default") != 0)
				beanDescriptor.setShortDescription(beanAnnotation.description());
			else
				beanDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);
		} else
			beanDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);

		return beanDescriptor;
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj) throws NotABeanException, GenericClassException {
		return getPropertyDescriptors(_obj, Object.class);
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj, Class<?> _stopClass) throws NotABeanException, GenericClassException {
		return getPropertyDescriptors(_obj, Arrays.asList(_stopClass));
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Object _obj, Collection<Class<?>> _stopClasses) throws NotABeanException, GenericClassException {
		if(!isBean(_obj))
			throw new NotABeanException();
//		if(isGeneric(_obj))
//			throw new GenericClassException();

		Class<?> beanClass = _obj.getClass();

		// =========================================================
		// BeanInfo Case
		// no mean if it's a bean or not, user choice!
		// =========================================================
		if(hasBeanInfo(beanClass))
			return getBeanInfo(beanClass).getPropertyDescriptors();

		// =========================================================
		// Generate Property Descriptors
		// =========================================================
		return generatePropertyDescriptors(_obj, _stopClasses);
	}

	public static BeanInfo 							getBeanInfo(Object _obj) {
		Class<?> clazz = _obj.getClass();

		String pck = clazz.getPackage().getName(); 
		String cls = clazz.getSimpleName();

		String beanInfo = pck + '.' + cls + "BeanInfo";

		try {
			return (BeanInfo) (Object) Class.forName(beanInfo).newInstance();
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			try {
				BeanDescriptor 			beanDescriptor      = getBeanDescriptor(_obj);
				PropertyDescriptor[] 	propertyDescriptors	= getPropertyDescriptors(_obj);

				return new BeanInfoAdapter() {
					@Override
					public BeanDescriptor       getBeanDescriptor()       { return beanDescriptor; }
					@Override
					public PropertyDescriptor[] getPropertyDescriptors()  { return propertyDescriptors; }
				};
			} catch(Exception ex) {
				return null;
			}
		}
	}

	// We consider here that a property can be just 'ReadOnly'
	static boolean 									hasProperties(Class<?> _class) {
		for(Field f : _class.getDeclaredFields()) {
			if(isPropertyField(f))
				return true;

			for(Method m : _class.getDeclaredMethods())
				if(isPropertyGetter(m) || isPropertyGetter(m, f.getName()))
					return true;
				else if(isPropertySetter(m) || isPropertySetter(m, f.getName()))
					return true;
		}

    	return false;
    }

    private static boolean 							isPropertyField(Field _f) {
		BeanPropertyField isAProperty = (BeanPropertyField) _f.getAnnotation(BeanPropertyField.class);
		if(isAProperty != null)
			return true;

    	// TODO:: For now, not useable
//    	if(Modifier.isPublic(_f.getModifiers()))
//   		return true;
		return false;
	}
    private static boolean 							isPropertyGetter(Method _m) {
		BeanPropertyGetter isAPropertyGetter = (BeanPropertyGetter) _m.getAnnotation(BeanPropertyGetter.class);
		if(isAPropertyGetter != null)
			return true;

		if  (
				_m.getName().startsWith(GET_PREFIX)
				||
				_m.getName().startsWith(IS_PREFIX)
				||
				_m.getName().startsWith(HAS_PREFIX)
			)
			if(Modifier.isPublic(_m.getModifiers()))
				return true;
		
		return false;
	}
    private static boolean 							isPropertyGetter(Method _m, String _propertyName) {
		if  (
				_m.getName().compareTo(GET_PREFIX + Utils.upperFirst(_propertyName)) == 0
				||
				_m.getName().compareTo(IS_PREFIX  + Utils.upperFirst(_propertyName)) == 0
				||
				_m.getName().compareTo(HAS_PREFIX + Utils.upperFirst(_propertyName)) == 0
			)
			if(Modifier.isPublic(_m.getModifiers()))
				return true;
		
		return false;
	}
    private static boolean 							isPropertySetter(Method _m) {
		BeanPropertySetter isAPropertySetter = (BeanPropertySetter) _m.getAnnotation(BeanPropertySetter.class);
		if(isAPropertySetter != null)
			return true;

		if( _m.getName().startsWith(SET_PREFIX) )
			if(Modifier.isPublic(_m.getModifiers()))
				return true;
		
		return false;
	}
    private static boolean 							isPropertySetter(Method _m, String _propertyName) {
		if( _m.getName().compareTo(SET_PREFIX + Utils.upperFirst(_propertyName)) == 0 )
			if(Modifier.isPublic(_m.getModifiers()))
				return true;
		
		return false;
	}

	private static final String 					getPropertyName(Method _method) {
		try {
			return getPropertyName(_method.getName());

		} catch(IllegalArgumentException _ex) {
			System.err.println("WE MUSTN'T BE HERE !!!");

			// ... Maybe a Beanable with annotations
			BeanPropertyGetter isAPropertyGetter;
			BeanPropertySetter isAPropertySetter;
			try {
				isAPropertyGetter = (BeanPropertyGetter) _method.getAnnotation(BeanPropertyGetter.class);
				isAPropertySetter = (BeanPropertySetter) _method.getAnnotation(BeanPropertySetter.class);
				
				if(isAPropertyGetter == null && isAPropertySetter == null)
					throw new IllegalArgumentException(_method.getName() + " is not a valid Beanable getter/setter");

				if(isAPropertyGetter != null)
					if(isAPropertyGetter.name().compareToIgnoreCase("default") != 0)
						return isAPropertyGetter.name();
				
				if(isAPropertySetter != null)
					if(isAPropertySetter.name().compareToIgnoreCase("default") != 0)
						return isAPropertySetter.name();
				
				throw new IllegalArgumentException(_method.getName() + " is not a valid getter/setter");

			} catch(SecurityException e) {
				throw new IllegalArgumentException(_method.getName() + " is not a valid getter/setter");
			}
		}
	}
	private static final String 					getPropertyName(String _methodName) {
		if(_methodName.startsWith(SET_PREFIX))
			return Utils.lowerFirst(_methodName.substring(3));
		if(_methodName.startsWith(GET_PREFIX))
			return Utils.lowerFirst(_methodName.substring(3));
		if(_methodName.startsWith(IS_PREFIX))
			return Utils.lowerFirst(_methodName.substring(2));
		if(_methodName.startsWith(HAS_PREFIX))
			return Utils.lowerFirst(_methodName.substring(3));

		throw new IllegalArgumentException(_methodName + " is not a valid getter/setter name");
	}

	private static final PropertyDescriptor[] 		generatePropertyDescriptors(Object _obj, Collection<Class<?>> _stopClasses) throws NotABeanException {
		if(!isBean(_obj))
			throw new NotABeanException();

		List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

		Class<?> beanClass = _obj.getClass();

		// =========================================================
		// 1st Step: We check all Fields to 
		//   - find Annotation, or
		//   - determine if they are public
		// -> Generate 1st List of Properties
		// =========================================================
		List<Field> fields = Fields.getAll(beanClass, _stopClasses);
		for(Field field : fields) {
			BeanPropertyField annotation = (BeanPropertyField) field.getAnnotation(BeanPropertyField.class);
			if(annotation != null)
				propertyDescriptors.add( getPropertyDescriptor(null, _obj, field, annotation) );
			else if(PUBLIC_FIELD_AS_PROPERTY)
				if(Fields.isPublic(field))
					propertyDescriptors.add( getPropertyDescriptor(null, _obj, field) );
		}

		// =========================================================
		// 2nd Step: We check all Methods to 
		//   - find Annotation, or
		//   - determine if it respect pattern <set|get|is|has><PropertyName>
		// -> Generate 2nd List of Properties
		// =========================================================
		List<Method> methods = Methods.getAll(beanClass, _stopClasses);
		for(Method method : methods) {
			List<BeanPropertyGetter> beanPropertyGetters = Annotations.getAnnotationsOfType(method, BeanPropertyGetter.class);
			List<BeanPropertySetter> beanPropertySetters = Annotations.getAnnotationsOfType(method, BeanPropertySetter.class);

			if(beanPropertyGetters.size() > 1 || beanPropertySetters.size() > 1)
				throw new MalformedBeanException("Found more than one annotation for same methods (check inheritance...) !!!");
			if(beanPropertyGetters.size() >= 1 && beanPropertySetters.size() >= 1)
				throw new MalformedBeanException("A method can't be both: getter and setter !!!");

			BeanPropertyGetter getterAnnotation = beanPropertyGetters.size() > 0 ? beanPropertyGetters.get(0) : null;
			BeanPropertySetter setterAnnotation = beanPropertySetters.size() > 0 ? beanPropertySetters.get(0) : null;

			if(getterAnnotation != null || setterAnnotation != null) {
				// ANNOTATION USE CASE
				if(getterAnnotation != null && !getterAnnotation.hidden())
					propertyDescriptors.add( getPropertyDescriptor(null, _obj, method, getterAnnotation) );
				else if(setterAnnotation != null && !setterAnnotation.hidden())
					propertyDescriptors.add( getPropertyDescriptor(null, _obj, method, setterAnnotation) );

			} else {
				if(isPropertyGetter(method) || isPropertySetter(method)) {
					// JAVABEAN USE CASE
					String 				propertyName       = getPropertyName(method);
					PropertyDescriptor 	propertyDescriptor = null;

					if(propertyName.length() > 0) {
						try {
							propertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
							propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
							propertyDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);
						} catch(IntrospectionException e) {
							// Property is ReadOnly ?
							Method getter = Methods.get(beanClass, GET_PREFIX + Utils.upperFirst(propertyName), 0);
							if(getter != null)
								try {
									propertyDescriptor = new PropertyDescriptor(propertyName, getter, null);
									propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
								} catch(IntrospectionException e1) {
									e1.printStackTrace();
									propertyDescriptor = null;
								}
							else {
								// Property is ReadOnly ?
								getter = Methods.get(beanClass, IS_PREFIX + Utils.upperFirst(propertyName), 0);
								if(getter != null)
									try {
										propertyDescriptor = new PropertyDescriptor(propertyName, getter, null);
										propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
									} catch(IntrospectionException e1) {
										e1.printStackTrace();
										propertyDescriptor = null;
									}
							}
						}

						if(propertyDescriptor != null)
							propertyDescriptors.add( propertyDescriptor );
					}
				}
				
			}
		}

		return mergedPropertyDescriptorList(propertyDescriptors).toArray(new PropertyDescriptor[0]);
	}

	private static final Set<PropertyDescriptor> 	mergedPropertyDescriptorList(final List<PropertyDescriptor> _propertyDescriptors) {
		Set<PropertyDescriptor> cleanedPropertyDescriptors = new HashSet<PropertyDescriptor>();

		for(PropertyDescriptor descriptor : _propertyDescriptors) {
			Predicate<PropertyDescriptor> sameProperty = (desc) -> {

				if(descriptor.getName().compareTo(desc.getName()) == 0)
					return true;

				Method 	read0  = descriptor.getReadMethod(),  read1 = desc.getReadMethod(),
						write0 = descriptor.getWriteMethod(), write1 = desc.getWriteMethod();

				if(read0 != null && read1 != null)
					if( read0.equals(read1) )
						return true;
				if(write0 != null && write1 != null)
					if( write0.equals(write1) )
						return true;
				
				return false;
			};

			if(cleanedPropertyDescriptors.stream().anyMatch(sameProperty)) {

				for(PropertyDescriptor toUpdate : cleanedPropertyDescriptors) {
					Method 	read0  = descriptor.getReadMethod(),  read1 = toUpdate.getReadMethod(),
							write0 = descriptor.getWriteMethod(), write1 = toUpdate.getWriteMethod();
					
					boolean test0  = descriptor.getName().compareTo(toUpdate.getName()) == 0;
					boolean test1  = read0  != null && read1  != null && read0.equals(read1);
					boolean test2  = write0 != null && write1 != null && write0.equals(write1);

					if(test0 || test1 || test2) {
						updateDescriptor(toUpdate, descriptor);
					}
				}

			} else {
				cleanedPropertyDescriptors.add(descriptor);
			}
		}

		return cleanedPropertyDescriptors;
	}

	private static final void 						updateDescriptor(PropertyDescriptor _toUpdate, PropertyDescriptor _descriptor) {
		// =========================================================
		// Display Name
		String storedDisplay = _toUpdate.getDisplayName();
		String newDisplay    = _descriptor.getDisplayName();

		if(storedDisplay.compareToIgnoreCase(newDisplay) != 0) {
			System.err.println("Inconsistent Information :" + storedDisplay + " vs " + newDisplay);

			if(newDisplay.compareToIgnoreCase(_descriptor.getName()) != 0)
				// Update Display Name
				_toUpdate.setDisplayName(newDisplay);
		}

		// =========================================================
		// Description
		String storedDescription  = _toUpdate.getShortDescription();
		String currentDescription = _descriptor.getShortDescription();

		if(storedDescription.compareToIgnoreCase(currentDescription) != 0)
			if(currentDescription.compareToIgnoreCase(BeanPropertyTag.NO_DESCRIPTION) != 0)
				// Update Display Name
				_toUpdate.setDisplayName(newDisplay);

		// =========================================================
		// Category
		String storedCategory  = (String) _toUpdate.getValue(BeanPropertyTag.CATEGORY_PROPERTY);
		String currentCategory = (String) _descriptor.getValue(BeanPropertyTag.CATEGORY_PROPERTY);

		if(currentCategory != null && currentCategory.compareToIgnoreCase(BeanPropertyTag.DEFAULT_CATEGORY) != 0) {
			if(storedCategory == null || storedCategory.compareToIgnoreCase(currentCategory) != 0) {
				System.err.println("Inconsistent Information :" + storedCategory + " vs " + currentCategory);
				// Update Category
				_toUpdate.setValue(BeanPropertyTag.CATEGORY_PROPERTY, currentCategory);
			}
		}

		// =========================================================
		// Getter
		Method storedGetter  = _toUpdate.getReadMethod();
		Method currentGetter = _descriptor.getReadMethod();

		if(storedGetter != null) {
			if(currentGetter != null && storedGetter.getName().compareToIgnoreCase(currentGetter.getName()) != 0) {
				System.err.println("Inconsistent Information :" + storedGetter.getName() + " vs " + currentGetter.getName());
				// Update Getter
				try {
					_toUpdate.setReadMethod(currentGetter);
				} catch(IntrospectionException e) {
					e.printStackTrace();
				}
			}
		} else {
			// Update Getter
			try {
				_toUpdate.setReadMethod(currentGetter);
			} catch(IntrospectionException e) {
				e.printStackTrace();
			}
		}

		// =========================================================
		// Setter
		Method storedSetter  = _toUpdate.getWriteMethod();
		Method currentSetter = _descriptor.getWriteMethod();

		if(storedSetter != null) {
			if(currentSetter != null && storedSetter.getName().compareToIgnoreCase(currentSetter.getName()) != 0) {
				System.err.println("Inconsistent Information : " + storedSetter.getName() + " vs " + currentSetter.getName());
				if(currentSetter != null) {
					// Update Getter
					try {
						_toUpdate.setWriteMethod(currentSetter);
					} catch(IntrospectionException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			// Update Setter
			try {
				_toUpdate.setWriteMethod(currentSetter);
			} catch(IntrospectionException e) {
				e.printStackTrace();
			}
		}

		// Boolean Properties
		_toUpdate.isBound();
		_toUpdate.isExpert();
		_toUpdate.isHidden();
		_toUpdate.isPreferred();
		_toUpdate.isConstrained();

	}

	static abstract class BeanInfoAdapter implements BeanInfo {
	
		@Override public BeanDescriptor 		getBeanDescriptor()       { return null; }
		@Override public EventSetDescriptor[] 	getEventSetDescriptors()  { return null; }
		@Override public int 					getDefaultEventIndex()    { return 0; }
		@Override public PropertyDescriptor[] 	getPropertyDescriptors()  { return null; }
		@Override public int 					getDefaultPropertyIndex() { return 0; }
		@Override public MethodDescriptor[] 	getMethodDescriptors()    { return null; }
		@Override public BeanInfo[] 			getAdditionalBeanInfo()   { return null; }
		@Override public Image 					getIcon(int iconKind)     { return null; }
	
		@Override public String 				toString() {
			StringBuilder sb = new StringBuilder();
	
			BeanDescriptor bean = getBeanDescriptor();
	
			sb.append("+ " + (this instanceof BeanInfoAdapter ?
							 "BeanInfo<" + bean.getName() + ">"
							 :
							 this.getClass().getSimpleName() ) + "\n");
	
			sb.append("Bean class: " + bean.getBeanClass().getSimpleName() + "\n");
			sb.append("  - display:     " + bean.getDisplayName() + "\n");
			sb.append("  - description: " + bean.getShortDescription() + "\n");
	
			if(getPropertyDescriptors() != null) {
				sb.append("Bean properties: " + getPropertyDescriptors().length + "\n");
				for(PropertyDescriptor prop : getPropertyDescriptors()) {
					sb.append("  + " + prop.getName() + "\n");
					sb.append("    - display:     " + prop.getDisplayName() + "\n");
					sb.append("    - description: " + prop.getShortDescription() + "\n");
					sb.append("    - getter:      " + (prop.getReadMethod()  != null ? prop.getReadMethod().getName()  : "none") + "\n");
					sb.append("    - setter:      " + (prop.getWriteMethod() != null ? prop.getWriteMethod().getName() : "none") + "\n");
				}
			}
	
			return sb.toString();
		}
	
	}

	/**
	 * AT THIS POINT, GENERICS ARE ISSUES !!!
	 */
	private static final PropertyDescriptor[] 		generatePropertyDescriptors(Class<?> _generic, Object _obj, Collection<Class<?>> _stopClasses) throws NotABeanException {
		if(!isBean(_obj))
			throw new NotABeanException();

		List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

		Class<?> beanClass = _obj.getClass();

		// =========================================================
		// 1st Step: We check all Fields to 
		//   - find Annotation, or
		//   - determine if they are public (experimental)
		// -> Generate 1st List of Properties
		// =========================================================
		List<Field> fields = Fields.getAll(beanClass, _stopClasses);
		for(Field field : fields) {
			BeanPropertyField annotation = (BeanPropertyField) field.getAnnotation(BeanPropertyField.class);
			if(annotation != null)
				propertyDescriptors.add( getPropertyDescriptor(_generic, _obj, field, annotation) );
			else if(PUBLIC_FIELD_AS_PROPERTY)
				if(Fields.isPublic(field))
					propertyDescriptors.add( getPropertyDescriptor(_generic, _obj, field) );
		}

		// =========================================================
		// 2nd Step: We check all Methods to 
		//   - find Annotation, or
		//   - determine if it respects pattern <set|get|is|has><PropertyName>
		// -> Generate 2nd List of Properties
		// =========================================================
		List<Method> methods = Methods.getAll(beanClass, _stopClasses);
		for(Method method : methods) {
			List<BeanPropertyGetter> beanPropertyGetters = Annotations.getAnnotationsOfType(method, BeanPropertyGetter.class);
			List<BeanPropertySetter> beanPropertySetters = Annotations.getAnnotationsOfType(method, BeanPropertySetter.class);

			if(beanPropertyGetters.size() > 1 || beanPropertySetters.size() > 1)
				throw new MalformedBeanException("Found more than one annotation for same methods (check inheritance...) !!!");
			if(beanPropertyGetters.size() >= 1 && beanPropertySetters.size() >= 1)
				throw new MalformedBeanException("A method can't be both: getter and setter !!!");

			BeanPropertyGetter getterAnnotation = beanPropertyGetters.size() > 0 ? beanPropertyGetters.get(0) : null;
			BeanPropertySetter setterAnnotation = beanPropertySetters.size() > 0 ? beanPropertySetters.get(0) : null;

			if(getterAnnotation != null || setterAnnotation != null) {
				// ANNOTATION USE CASE
				if(getterAnnotation != null && !getterAnnotation.hidden())
					propertyDescriptors.add( getPropertyDescriptor(_generic, _obj, method, getterAnnotation) );
				else if(setterAnnotation != null && !setterAnnotation.hidden())
					propertyDescriptors.add( getPropertyDescriptor(_generic, _obj, method, setterAnnotation) );

			} else {
				if(isPropertyGetter(method) || isPropertySetter(method)) {
					// JAVABEAN USE CASE
					String 				propertyName       = getPropertyName(method);
					PropertyDescriptor 	propertyDescriptor = null;

					if(propertyName.length() > 0) {
						try {
							propertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
							propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
							propertyDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);
						} catch(IntrospectionException e) {
							// Property is ReadOnly ?
							Method getter = Methods.get(beanClass, GET_PREFIX + Utils.upperFirst(propertyName), 0);
							if(getter != null)
								try {
									propertyDescriptor = new PropertyDescriptor(propertyName, getter, null);
									propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
								} catch(IntrospectionException e1) {
									e1.printStackTrace();
									propertyDescriptor = null;
								}
							else {
								// Property is ReadOnly ?
								getter = Methods.get(beanClass, IS_PREFIX + Utils.upperFirst(propertyName), 0);
								if(getter != null)
									try {
										propertyDescriptor = new PropertyDescriptor(propertyName, getter, null);
										propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
									} catch(IntrospectionException e1) {
										e1.printStackTrace();
										propertyDescriptor = null;
									}
							}
						}

						if(propertyDescriptor != null)
							propertyDescriptors.add( propertyDescriptor );
					}
				}
				
			}
		}

		return mergedPropertyDescriptorList(propertyDescriptors).toArray(new PropertyDescriptor[0]);
	}

	public static final PropertyDescriptor[] 		getPropertyDescriptors(Class<?> _generic, Object _obj) throws NotABeanException {
		return getPropertyDescriptors(_generic, _obj, Object.class);
	}
	public static final PropertyDescriptor[] 		getPropertyDescriptors(Class<?> _generic, Object _obj, Class<?> _stopClass) throws NotABeanException {
		if(!isBean(_obj))
			throw new NotABeanException();

		Class<?> beanClass = _obj.getClass();

		// =========================================================
		// BeanInfo Case
		// no mean if it's a bean or not, user choice!
		// =========================================================
		if(hasBeanInfo(beanClass))
			return getBeanInfo(beanClass).getPropertyDescriptors();

		// =========================================================
		// Generate Property Descriptors
		// =========================================================
		return generatePropertyDescriptors(_generic, _obj,Arrays.asList(_stopClass));
	}

	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _generic, Object _obj, Field _field) {
		PropertyDescriptor propertyDescriptor = null;

		Class<?> beanClass    = _obj.getClass();

		String  name          = _field.getName(), 
				display       = name, 
				description   = BeanPropertyTag.NO_DESCRIPTION, 
				category      = BeanPropertyTag.DEFAULT_CATEGORY,
				mGetter       = null,
				mSetter       = null;
		boolean isHidden      = false, 
				isExpert      = false, 
				isBounded     = false, 
				isConstrained = false, 
				isPreferred   = false;
		Method  getter        = null,
				setter        = null;

		Type fieldType = Types.ofField(_field);
		if(_generic != null) {
			Class<?>[] typeArgs = TypeResolver.resolveRawArguments(_generic, _obj.getClass());
//			Type       type     = GenericTypeReflector.getTypeParameter(_obj.getClass(), _generic.getTypeParameters()[0]);
//			Type       type0    = GenericTypeReflector.getExactFieldType(_field, type);
			
//			Type              generic = _class;   
//			TypeVariable<?>[] vars    = _class.getTypeParameters();

			System.err.println("Not yet functionnal");
			Type fooWildcard2 = TypeFactory.parameterizedClass(_generic, typeArgs);
//			Type fooWildcard2 = TypeFactory.parameterizedClass(Generic.class, Integer.class);
			fieldType = Types.ofField(_field, fooWildcard2);
		} else {
			fieldType = Types.ofField(_field);
		}
		
		Class<?> fieldClass = null;
		try {
			fieldClass = Types.toClass(fieldType);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		getter = getPropertyGetterMethod(name, mGetter, beanClass);
		setter = getPropertySetterMethod(name, fieldClass, mSetter, beanClass);

		try {
			propertyDescriptor = new PropertyDescriptor(name, getter, setter);
			propertyDescriptor.setDisplayName(display);
			propertyDescriptor.setShortDescription(description);
			propertyDescriptor.setExpert(isExpert);
			propertyDescriptor.setHidden(isHidden);
			propertyDescriptor.setBound(isBounded);
			propertyDescriptor.setConstrained(isConstrained);
			propertyDescriptor.setPreferred(isPreferred);
			propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, category);
			return propertyDescriptor;
		} catch(IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
    }
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _generic, Object _obj, Field _field,   BeanPropertyField _annotation) {
		PropertyDescriptor propertyDescriptor = null;

		Class<?> beanClass    = _obj.getClass();

//		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? Strings.capitalize(_field.getName()) : Strings.capitalize(_annotation.name()),
		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? _field.getName() : _annotation.name(), 
				display       = _annotation.display().compareToIgnoreCase("default") == 0 ? name : _annotation.display(), 
				description   = _annotation.description().compareToIgnoreCase("default") == 0 ? BeanPropertyTag.NO_DESCRIPTION : _annotation.display(), 
				category      = _annotation.category(),
				mGetter       = _annotation.getter(),
				mSetter       = _annotation.setter();
		boolean isHidden      = _annotation.hidden(), 
				isExpert      = _annotation.expert(), 
				isBounded     = _annotation.bounded(), 
				isConstrained = _annotation.constrained(), 
				isPreferred   = _annotation.preferred();
		Method  getter = null,
				setter = null;

		getter = getPropertyGetterMethod(name, mGetter, beanClass);
		setter = getPropertySetterMethod(name, _field.getType(), mSetter, beanClass);

		try {
			propertyDescriptor = new PropertyDescriptor(name, getter, setter);
			propertyDescriptor.setDisplayName(display);
			propertyDescriptor.setShortDescription(description);
			propertyDescriptor.setExpert(isExpert);
			propertyDescriptor.setHidden(isHidden);
			propertyDescriptor.setBound(isBounded);
			propertyDescriptor.setConstrained(isConstrained);
			propertyDescriptor.setPreferred(isPreferred);
			propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, category);
			return propertyDescriptor;
		} catch(IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _generic, Object _obj, Method _getter_or_setter) {
		Method m = _getter_or_setter;

		String  propertyName = null;
		boolean isGetter     = false,
				isSetter     = false;

		try {
			propertyName = getPropertyName(m.getName());
			isGetter     = isPropertyGetter(m) ? true : false;
			isSetter     = isPropertySetter(m) ? true : false;
			
			if(!isGetter && !isSetter)
				throw new IllegalArgumentException();
		} catch(IllegalArgumentException e) {
			return null;
		}

		PropertyDescriptor propertyDescriptor = null;

		String  name          = propertyName, 
				display       = propertyName, 
				description   = BeanPropertyTag.NO_DESCRIPTION, 
				category      = BeanPropertyTag.DEFAULT_CATEGORY;
		boolean isHidden      = false, 
				isExpert      = false, 
				isBounded     = false, 
				isConstrained = false, 
				isPreferred   = false;
		Method  getter        = isGetter ? m : null,
				setter        = isSetter ? m : null;

		try {
			propertyDescriptor = new PropertyDescriptor(name, getter, setter);
			propertyDescriptor.setDisplayName(display);
			propertyDescriptor.setShortDescription(description);
			propertyDescriptor.setExpert(isExpert);
			propertyDescriptor.setHidden(isHidden);
			propertyDescriptor.setBound(isBounded);
			propertyDescriptor.setConstrained(isConstrained);
			propertyDescriptor.setPreferred(isPreferred);
			propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, category);
			return propertyDescriptor;
		} catch(IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
    }
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _generic, Object _obj, Method _getter, BeanPropertyGetter _annotation) {
		PropertyDescriptor propertyDescriptor = null;

		Class<?> beanClass    = _obj.getClass();

//		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? getPropertyName(_getter.getName()) : Strings.capitalize(_annotation.name()), 
		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? getPropertyName(_getter.getName()) : _annotation.name(), 
				display       = _annotation.display().compareToIgnoreCase("default") == 0 ? name : _annotation.display(), 
				description   = _annotation.description().compareToIgnoreCase("default") == 0 ? BeanPropertyTag.NO_DESCRIPTION : _annotation.description(), 
				category      = _annotation.category(),
				mSetter       = _annotation.setter();
		boolean isHidden      = _annotation.hidden(), 
				isExpert      = _annotation.expert(), 
				isBounded     = _annotation.bounded(), 
				isConstrained = _annotation.constrained(), 
				isPreferred   = _annotation.preferred();
		Method  setter = null;

		if(mSetter.compareToIgnoreCase("none") != 0)
			setter = getPropertySetterMethod(name, _getter.getReturnType(), mSetter, beanClass);

		try {
			propertyDescriptor = new PropertyDescriptor(name, _getter, setter);
			propertyDescriptor.setDisplayName(display);
			propertyDescriptor.setShortDescription(description);
			propertyDescriptor.setExpert(isExpert);
			propertyDescriptor.setHidden(isHidden);
			propertyDescriptor.setBound(isBounded);
			propertyDescriptor.setConstrained(isConstrained);
			propertyDescriptor.setPreferred(isPreferred);
			propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, category);
			return propertyDescriptor;
		} catch(IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
    }
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _generic, Object _obj, Method _setter, BeanPropertySetter _annotation) {
		PropertyDescriptor propertyDescriptor = null;

		Class<?> beanClass    = _obj.getClass();

//		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? getPropertyName(_setter.getName()) : Strings.capitalize(_annotation.name()), 
		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? getPropertyName(_setter.getName()) : _annotation.name(), 
				display       = _annotation.display().compareToIgnoreCase("default") == 0 ? name : _annotation.display(), 
				description   = _annotation.description().compareToIgnoreCase("default") == 0 ? BeanPropertyTag.NO_DESCRIPTION : _annotation.description(), 
				category      = _annotation.category(),
				mGetter       = _annotation.getter();
		boolean isHidden      = _annotation.hidden(), 
				isExpert      = _annotation.expert(), 
				isBounded     = _annotation.bounded(), 
				isConstrained = _annotation.constrained(), 
				isPreferred   = _annotation.preferred();
		Method  getter = null;

		if(mGetter.compareToIgnoreCase("none") != 0)
			getter = getPropertyGetterMethod(name, mGetter, beanClass);

		try {
			propertyDescriptor = new PropertyDescriptor(name, getter, _setter);
			propertyDescriptor.setDisplayName(display);
			propertyDescriptor.setShortDescription(description);
			propertyDescriptor.setExpert(isExpert);
			propertyDescriptor.setHidden(isHidden);
			propertyDescriptor.setBound(isBounded);
			propertyDescriptor.setConstrained(isConstrained);
			propertyDescriptor.setPreferred(isPreferred);
			propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, category);
			return propertyDescriptor;
		} catch(IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
    }

	private static final Method 					getPropertyGetterMethod(String _property, String _getterName, Class<?> _class) {
		Method getter = null;

		if(_getterName == null || _getterName.compareToIgnoreCase("default") == 0) {
			String testGetter = null;
			try {
				testGetter = GET_PREFIX + Utils.upperFirst(_property);
				getter     = _class.getMethod(testGetter, (Class<?>[]) null);
			} catch(NoSuchMethodException _e) {
				try {
					testGetter = IS_PREFIX + Utils.upperFirst(_property);
					getter     = _class.getMethod(testGetter, (Class<?>[]) null);
				} catch(NoSuchMethodException __e) {
					try {
						testGetter = HAS_PREFIX + Utils.upperFirst(_property);
						getter     = _class.getMethod(testGetter, (Class<?>[]) null);
					} catch(NoSuchMethodException ___e) {
						// TODO:: Potentially happened in another step...
						// ___e.printStackTrace();
						throw new MalformedBeanException(___e.getMessage());
					} catch(SecurityException ___e) {
						___e.printStackTrace();
					}
				} catch(SecurityException __e) {
					__e.printStackTrace();
				}
			} catch(SecurityException _e) {
				_e.printStackTrace();
			}
		} else
			try {
				getter = _class.getMethod(_getterName, (Class<?>[]) null);
			} catch(NoSuchMethodException _e) {
				_e.printStackTrace();
			} catch(SecurityException _e) {
				_e.printStackTrace();
			}

		return getter;
    }
	private static final Method 					getPropertySetterMethod(String _property, Class<?> _type, String _setterName, Class<?> _class) {
		Method setter = null;

		if(_setterName == null || _setterName.compareToIgnoreCase("default") == 0) {
			String testSetter = null;
			try {
				testSetter = "set" + Utils.upperFirst(_property);
				setter     = _class.getMethod(testSetter, _type);
			} catch(NoSuchMethodException _e) {
				// _e.printStackTrace();
			} catch(SecurityException _e) {
				_e.printStackTrace();
			}
		} else
			try {
				setter = _class.getMethod(_setterName, _type);
			} catch(NoSuchMethodException _e) {
				// _e.printStackTrace();
			} catch(SecurityException _e) {
				_e.printStackTrace();
			}

		return setter;
    }
	
}

