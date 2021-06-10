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
 * @file     Beans.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.utils;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import fr.java.beans.annotations.BeanClass;
import fr.java.beans.annotations.BeanPropertyField;
import fr.java.beans.annotations.BeanPropertyGetter;
import fr.java.beans.annotations.BeanPropertySetter;
import fr.java.beans.annotations.BeanPropertyTag;
import fr.java.beans.impl.AbstractBean;
import fr.java.beans.reflect.types.TypeFactory;
import fr.java.beans.reflect.utils.Annotations;
import fr.java.beans.reflect.utils.Classes;
import fr.java.beans.reflect.utils.Constructors;
import fr.java.beans.reflect.utils.Fields;
import fr.java.beans.reflect.utils.Methods;
import fr.java.beans.reflect.utils.Types;
import fr.java.lang.exceptions.MalformedBeanException;
import fr.utils.beans.Strings;

public class Beans {
	// Setter
    static final String SET_PREFIX    = "set";
    // Getters
    static final String GET_PREFIX    = "get";
    static final String IS_PREFIX     = "is";
    static final String HAS_PREFIX    = "has";
    // Collections
    static final String ADD_PREFIX    = "add";
    static final String REMOVE_PREFIX = "remove";


	public static boolean 							isPOJO(Class<?> _class) {
		boolean hasDefaultConstructor = Constructors.hasDefault(_class);
		boolean hasNoInheritance      = Classes.getInheritance(_class, false).isEmpty();

		return hasDefaultConstructor && hasNoInheritance;
	}
	public static boolean 							isJavaBean(Class<?> _class) {
		boolean isSerializable        = Classes.getInheritance(_class, false).contains(Serializable.class);
		boolean hasDefaultConstructor = Constructors.hasDefault(_class);
		boolean hasProperties         = hasBeanProperties(_class);

		return isSerializable && hasDefaultConstructor && hasProperties;
	}
	public static boolean 							isTaggedBean(Class<?> _class) {
		BeanClass beanFlag = (BeanClass) _class.getAnnotation(BeanClass.class);
		if(beanFlag != null)
			return true;

		return false;
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
	public static BeanInfo 							getBeanInfo(Class<?> _class) {
		String pck = _class.getPackage().getName(); 
		String cls = _class.getSimpleName();

		String beanInfo = pck + '.' + cls + "BeanInfo";

		try {
			return (BeanInfo) (Object) Class.forName(beanInfo).newInstance();
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			try {
				BeanDescriptor 			beanDescriptor      = Beans.getBeanDescriptor(_class);
				PropertyDescriptor[] 	propertyDescriptors	= Beans.getPropertyDescriptors(_class);

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

	public static BeanDescriptor 					getBeanDescriptor(Class<?> _class) {
		BeanDescriptor beanDescriptor = new BeanDescriptor(_class);

		BeanClass bean = (BeanClass) _class.getAnnotation(BeanClass.class);
		if(bean != null) {
			if(bean.display().compareToIgnoreCase("default") != 0)
				beanDescriptor.setDisplayName(bean.display());

			if(bean.description().compareToIgnoreCase("default") != 0)
				beanDescriptor.setShortDescription(bean.description());
			else
				beanDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);
		} else
			beanDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);

		return beanDescriptor;
	}
	public static PropertyDescriptor[] 				getPropertyDescriptors(Class<?> _class) {
		List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

		Class<?> stopClass = (AbstractBean.class.isAssignableFrom(_class) ? AbstractBean.class : Object.class);

		// =========================================================
		// BeanInfo Case
		// =========================================================
		if(hasBeanInfo(_class))
			return getBeanInfo(_class).getPropertyDescriptors();

		// =========================================================
		// 1st Step: We check all Fields to 
		//   - find Annotation, or
		//   - determine if they are public
		// -> Generate 1st List of Properties
		// =========================================================
		List<Field> fields = Fields.getAll(_class, stopClass);
		for(Field field : fields) {
			BeanPropertyField annotation = (BeanPropertyField) field.getAnnotation(BeanPropertyField.class);
			if(annotation != null)
				propertyDescriptors.add( getPropertyDescriptor(_class, field, annotation) );
			else if(Fields.isPublic(field))
				propertyDescriptors.add( getPropertyDescriptor(_class, field) );
		}

		// =========================================================
		// 2nd Step: We check all Methods to 
		//   - find Annotation, or
		//   - determine if it respect pattern <set|get|is|has><PropertyName>
		// -> Generate 2nd List of Properties
		// =========================================================
		List<Method> methods = Methods.getAll(_class, stopClass);
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
					propertyDescriptors.add( getPropertyDescriptor(_class, method, getterAnnotation) );
				else if(setterAnnotation != null && !setterAnnotation.hidden())
					propertyDescriptors.add( getPropertyDescriptor(_class, method, setterAnnotation) );

			} else {
				if(isPropertyGetter(method) || isPropertySetter(method)) {
					// JAVABEAN USE CASE
					String 				propertyName       = getPropertyName(method);
					PropertyDescriptor 	propertyDescriptor = null;

					if(propertyName.length() > 0) {
						try {
							propertyDescriptor = new PropertyDescriptor(propertyName, _class);
							propertyDescriptor.setValue(BeanPropertyTag.CATEGORY_PROPERTY, BeanPropertyTag.DEFAULT_CATEGORY);
							propertyDescriptor.setShortDescription(BeanPropertyTag.NO_DESCRIPTION);
						} catch(IntrospectionException e) {
							// Property is ReadOnly ?
							Method getter = Methods.get(_class, GET_PREFIX + Strings.capitalize(propertyName), 0);
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
								getter = Methods.get(_class, IS_PREFIX + Strings.capitalize(propertyName), 0);
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

	// We consider here that a property can be just 'ReadOnly'
    private static boolean 							hasBeanProperties(Class<?> _class) {
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
    	if(Modifier.isPublic(_f.getModifiers()))
    		return true;

		BeanPropertyField isAProperty = (BeanPropertyField) _f.getAnnotation(BeanPropertyField.class);
		return isAProperty != null;
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
				_m.getName().compareTo(GET_PREFIX + Strings.upperFirst(_propertyName)) == 0
				||
				_m.getName().compareTo(IS_PREFIX  + Strings.upperFirst(_propertyName)) == 0
				||
				_m.getName().compareTo(HAS_PREFIX + Strings.upperFirst(_propertyName)) == 0
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
		if( _m.getName().compareTo(SET_PREFIX + Strings.upperFirst(_propertyName)) == 0 )
			if(Modifier.isPublic(_m.getModifiers()))
				return true;
		
		return false;
	}

	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _class, Field _field) {
		PropertyDescriptor propertyDescriptor = null;

//		String  name          = _annotation.name().compareToIgnoreCase("default") == 0 ? Strings.capitalize(_field.getName()) : Strings.capitalize(_annotation.name()),
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
		Method  getter = null,
				setter = null;

		Type fieldType = Types.ofField(_field);
		if(Classes.isGeneric(_class)) {
//			Type              generic = _class;   
			TypeVariable<?>[] vars    = _class.getTypeParameters();
			
			System.err.println("Not yet functionnal");
			Type fooWildcard2 = TypeFactory.parameterizedClass(_class, vars);
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

		getter = getPropertyGetterMethod(name, mGetter, _class);
		setter = getPropertySetterMethod(name, fieldClass, mSetter, _class);

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
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _class, Field _field,   BeanPropertyField _annotation) {
		PropertyDescriptor propertyDescriptor = null;

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

		getter = getPropertyGetterMethod(name, mGetter, _class);
		setter = getPropertySetterMethod(name, _field.getType(), mSetter, _class);

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
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _class, Method _getter, BeanPropertyGetter _annotation) {
		PropertyDescriptor propertyDescriptor = null;

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
			setter = getPropertySetterMethod(name, _getter.getReturnType(), mSetter, _class);

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
	private static final PropertyDescriptor 		getPropertyDescriptor(Class<?> _class, Method _setter, BeanPropertySetter _annotation) {
		PropertyDescriptor propertyDescriptor = null;

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
			getter = getPropertyGetterMethod(name, mGetter, _class);

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
			return Strings.lowerFirst(_methodName.substring(3));
		if(_methodName.startsWith(GET_PREFIX))
			return Strings.lowerFirst(_methodName.substring(3));
		if(_methodName.startsWith(IS_PREFIX))
			return Strings.lowerFirst(_methodName.substring(2));
		if(_methodName.startsWith(HAS_PREFIX))
			return Strings.lowerFirst(_methodName.substring(3));

		throw new IllegalArgumentException(_methodName + " is not a valid getter/setter name");
	}

	private static final Method 					getPropertyGetterMethod(String _property, String _getterName, Class<?> _class) {
		Method getter = null;

		if(_getterName == null || _getterName.compareToIgnoreCase("default") == 0) {
			String testGetter = null;
			try {
				testGetter = GET_PREFIX + Strings.capitalize(_property);
				getter     = _class.getMethod(testGetter, (Class<?>[]) null);
			} catch(NoSuchMethodException _e) {
				try {
					testGetter = IS_PREFIX + Strings.capitalize(_property);
					getter     = _class.getMethod(testGetter, (Class<?>[]) null);
				} catch(NoSuchMethodException __e) {
					try {
						testGetter = HAS_PREFIX + Strings.capitalize(_property);
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

		if(_setterName.compareToIgnoreCase("default") == 0) {
			String testSetter = null;
			try {
				testSetter = "set" + Strings.capitalize(_property);
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

	private static Set<PropertyDescriptor> 			mergedPropertyDescriptorList(final List<PropertyDescriptor> _propertyDescriptors) {
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

	public static abstract class BeanInfoAdapter implements BeanInfo {

		@Override public BeanDescriptor 		getBeanDescriptor() 		{ return null; }
		@Override public EventSetDescriptor[] 	getEventSetDescriptors() 	{ return null; }
		@Override public int 					getDefaultEventIndex() 		{ return 0; }
		@Override public PropertyDescriptor[] 	getPropertyDescriptors() 	{ return null; }
		@Override public int 					getDefaultPropertyIndex() 	{ return 0; }
		@Override public MethodDescriptor[] 	getMethodDescriptors() 		{ return null; }
		@Override public BeanInfo[] 			getAdditionalBeanInfo() 	{ return null; }
		@Override public Image 					getIcon(int iconKind) 		{ return null; }

		@Override
		public String toString() {
			StringBuilder  sb   = new StringBuilder();
			BeanDescriptor bean = getBeanDescriptor();

			if(bean == null)
				return "No bean info...";

			sb.append("+ " + (this instanceof BeanInfoAdapter ? 
								"BeanInfo<" + bean.getName() + ">"
								: this.getClass().getSimpleName()) + "\n");

			sb.append("Bean class: " + bean.getBeanClass().getSimpleName() + "\n");
			sb.append("  - display:     " + bean.getDisplayName() + "\n");
			sb.append("  - description: " + bean.getShortDescription() + "\n");

			if (getPropertyDescriptors() != null) {
				sb.append("Bean properties: " + getPropertyDescriptors().length + "\n");
				for (PropertyDescriptor prop : getPropertyDescriptors()) {
					sb.append("  + " + prop.getName() + "\n");
					sb.append("    - display:     " + prop.getDisplayName() + "\n");
					sb.append("    - description: " + prop.getShortDescription() + "\n");
					sb.append("    - getter:      "
							+ (prop.getReadMethod() != null ? prop.getReadMethod().getName() : "none") + "\n");
					sb.append("    - setter:      "
							+ (prop.getWriteMethod() != null ? prop.getWriteMethod().getName() : "none") + "\n");
				}
			}

			return sb.toString();
		}

	}

}
