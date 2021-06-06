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
 * @file     Annotations.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import static java.lang.String.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Annotations {

	public static boolean 							isAnnotationPresent(final Class<?> annotatedType, final Class<? extends Annotation> targetAnnotation) {
		return getAnnotation(targetAnnotation, annotatedType) != null;
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> A 			getAnnotation(final Class<? extends Annotation> targetAnnotation, final Class<?> annotatedType) {
        if(targetAnnotation == null)
            throw new IllegalArgumentException(format("The %s must not be null", "targetAnnotation"));
        if(annotatedType == null)
            throw new IllegalArgumentException(format("The %s must not be null", "annotatedType"));

		Annotation foundAnnotation = annotatedType.getAnnotation(targetAnnotation);
		if(foundAnnotation == null) {
			for(Annotation annotation : annotatedType.getAnnotations()) {
				Class<? extends Annotation> annotationType = annotation.annotationType();
				if(annotationType.isAnnotationPresent(targetAnnotation)) {
					foundAnnotation = annotationType.getAnnotation(targetAnnotation);
					break;
				}
			}
		}
		return (A) foundAnnotation;
	}

	public static boolean 							isMarker(Class<? extends Annotation> _annotationClass) {
		return _annotationClass.getDeclaredMethods().length == 0;
	}

	//
	// CLASS ANNOTATIONS
	//
	public static List<Annotation> 					getAnnotations(Class<?> _class) {
		return getAnnotations(_class, true, null);
	}
	public static List<Annotation> 					getAnnotations(Class<?> _class, Class<?> _stopClass) {
		return getAnnotations(_class, true, _stopClass);
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getAnnotationsOfType(Class<?> _class, final Class<A> _annotationClass) {
		return getAnnotations(_class, true, null).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getAnnotationsOfType(Class<?> _class, Class<?> _stopClass, final Class<A> _annotationClass) {
		return getAnnotations(_class, true, _stopClass).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}

	public static List<Annotation> 					getDeclaredAnnotations(Class<?> _class) {
		return getAnnotations(_class, false, null);
	}
	public static List<Annotation> 					getDeclaredAnnotations(Class<?> _class, Class<?> _stopClass) {
		return getAnnotations(_class, false, _stopClass);
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getDeclaredAnnotationsOfType(Class<?> _class, final Class<A> _annotationClass) {
		return getAnnotations(_class, false, null).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getDeclaredAnnotationsOfType(Class<?> _class, Class<?> _stopClass, final Class<A> _annotationClass) {
		return getAnnotations(_class, false, _stopClass).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}


	//
	// METHOD ANNOTATIONS
	//
	public static List<Annotation> 					getAnnotations(Method _method) {
		return getAnnotations(_method, true, null);
	}
	public static List<Annotation> 					getAnnotations(Method _method, Class<?> _stopClass) {
		return getAnnotations(_method, true, _stopClass);
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getAnnotationsOfType(Method _method, final Class<A> _annotationClass) {
		return getAnnotations(_method, true, null).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getAnnotationsOfType(Method _method, Class<?> _stopClass, final Class<A> _annotationClass) {
		return getAnnotations(_method, true, _stopClass).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}

	public static List<Annotation> 					getDeclaredAnnotations(Method _method) {
		return getAnnotations(_method, false, null);
	}
	public static List<Annotation> 					getDeclaredAnnotations(Method _method, Class<?> _stopClass) {
		return getAnnotations(_method, false, _stopClass);
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getDeclaredAnnotationsOfType(Method _method, final Class<A> _annotationClass) {
		return getAnnotations(_method, false, null).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getDeclaredAnnotationsOfType(Method _method, Class<?> _stopClass, final Class<A> _annotationClass) {
		return getAnnotations(_method, false, _stopClass).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}


	//
	// FIELD ANNOTATIONS
	//
	public static List<Annotation> 					getDeclaredAnnotations(Field _field) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <A extends Annotation> List<A> 	getDeclaredAnnotationOfType(Field _field, final Class<A> _annotationClass) {
		return getDeclaredAnnotations(_field).stream()
												.filter(a -> _annotationClass.isAssignableFrom(a.getClass()))
												.map(a -> (A) a)
												.collect(Collectors.toList());
	}


	
	private static List<Annotation> 				getAnnotations(Class<?> _clazz, boolean _with_inheritance, Class<?> _stopClass) {
		List<Annotation> annotations = new ArrayList<Annotation>();

		if(_with_inheritance)
			if(_stopClass == null)
				return Arrays.asList(_clazz.getDeclaredAnnotations());
			else {
				Class<?> superClass = _clazz.getSuperclass();

				do {
					annotations.addAll( Arrays.asList( superClass.getDeclaredAnnotations() ) );
					superClass = superClass.getSuperclass();
				} while(!_stopClass.isAssignableFrom(superClass));

				return annotations;
			}

		return Arrays.asList( _clazz.getDeclaredAnnotations() );
	}

	private static List<Annotation> 				getAnnotations(Method _method, boolean _with_inheritance, Class<?> _stopClass) {
		List<Annotation> annotations = new ArrayList<Annotation>();

		annotations.addAll( Arrays.asList(_method.getAnnotations()) );

		if(_with_inheritance) {
/**
			Set<Class<?>> inheritage = Classes.getInheritage(_method.getDeclaringClass(), false);
			for(Class<?> cls : inheritage) {
				Method superMethod = null;
				try {
					superMethod = cls.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

					if(superMethod.getReturnType() == _method.getReturnType())
						annotations.addAll(Arrays.asList(superMethod.getDeclaredAnnotations()));

				} catch(NoSuchMethodException | SecurityException e) {}
			}
/*/			
			Class<?> superClass  = _method.getDeclaringClass().getSuperclass();
			Method   superMethod = null;

			do {
				try {
					superMethod = superClass.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

					annotations.addAll( Arrays.asList( superMethod.getDeclaredAnnotations() ) );

				} catch(NoSuchMethodException | SecurityException e) { }

				superClass = superClass.getSuperclass();
			} while(superClass != null && (_stopClass != null && !_stopClass.isAssignableFrom(superClass)));
/**/		
		}

		return annotations;
	}


	public static final <T extends Annotation> List<T> getInheritedAnnotations(Method _method, Class<T> annotationClass) {
		List<T> annotations = new ArrayList<T>();

		Set<Class<?>> inheritage = Classes.getInheritance(_method.getDeclaringClass(), false);
		for(Class<?> cls : inheritage) {
			Method superMethod = null;
			try {
				superMethod = cls.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

				T annotation = superMethod.getAnnotation(annotationClass);
				if(annotation != null)
					annotations.add(annotation);

			} catch(NoSuchMethodException e) {
			} catch(SecurityException e) {
				e.printStackTrace();
			}
		}

		return annotations;
	}

	public static final List<Annotation> getInheritedAnnotations(Method _method) {
		return getInheritedAnnotations(_method, true);
	}
	public static final List<Annotation> getInheritedAnnotations(Method _method, boolean _withExactReturnType) {
		List<Annotation> annotations = new ArrayList<Annotation>();

		Set<Class<?>> inheritage = Classes.getInheritance(_method.getDeclaringClass(), false);
		for(Class<?> cls : inheritage) {
			Method superMethod = null;
			try {
				System.out.println(cls.getSimpleName());
				superMethod = cls.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

				if(_withExactReturnType) {
					if(superMethod.getReturnType() == _method.getReturnType())
						annotations.addAll(Arrays.asList(superMethod.getDeclaredAnnotations()));
				} else {
					annotations.addAll(Arrays.asList(superMethod.getDeclaredAnnotations()));
				}

			} catch(NoSuchMethodException | SecurityException e) {
			}
		}

		return annotations;
	}

	public static boolean isAllDefaultMethods(Class<? extends Annotation> annotationType) {
		boolean hasMethods = false;
		for(Method m : annotationType.getDeclaredMethods()) {
			hasMethods = true;
			if(m.getDefaultValue() == null) {
				return false;
			}
		}
		return hasMethods;
	}

	/*
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> A getAnnotation(final Class<? extends Annotation> targetAnnotation, final Class<?> annotatedType) {
		checkNotNull(targetAnnotation, "The %s must not be null", "targetAnnotation");
		checkNotNull(targetAnnotation, "The %s must not be null", "annotatedType");

		Annotation foundAnnotation = annotatedType.getAnnotation(targetAnnotation);
		if(foundAnnotation == null) {
			for(Annotation annotation : annotatedType.getAnnotations()) {
				Class<? extends Annotation> annotationType = annotation.annotationType();
				if(annotationType.isAnnotationPresent(targetAnnotation)) {
					foundAnnotation = annotationType.getAnnotation(targetAnnotation);
					break;
				}
			}
		}
		return (A) foundAnnotation;
	}

	public static boolean isAnnotationPresent(final Class<? extends Annotation> targetAnnotation, final Class<?> annotatedType) {
		return getAnnotation(targetAnnotation, annotatedType) != null;
	}

	public static final <T extends Annotation> List<T> getInheritedAnnotations(Method _method, Class<T> annotationClass) {
		List<T> annotations = new ArrayList<T>();

		Set<Class<?>> inheritage = Classes.getInheritage(_method.getDeclaringClass(), false);
		for(Class<?> cls : inheritage) {
			Method superMethod = null;
			try {
				superMethod = cls.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

				T annotation = superMethod.getAnnotation(annotationClass);
				if(annotation != null)
					annotations.add(annotation);

			} catch(NoSuchMethodException e) {
			} catch(SecurityException e) {
				e.printStackTrace();
			}
		}

		return annotations;
	}

	public static final List<Annotation> getInheritedAnnotations(Method _method) {
		return getInheritedAnnotations(_method, true);
	}
	public static final List<Annotation> getInheritedAnnotations(Method _method, boolean _withExactReturnType) {
		List<Annotation> annotations = new ArrayList<Annotation>();

		Set<Class<?>> inheritage = Classes.getInheritage(_method.getDeclaringClass(), false);
		for(Class<?> cls : inheritage) {
			Method superMethod = null;
			try {
				System.out.println(cls.getSimpleName());
				superMethod = cls.getDeclaredMethod(_method.getName(), _method.getParameterTypes());

				if(_withExactReturnType) {
					if(superMethod.getReturnType() == _method.getReturnType())
						annotations.addAll(Arrays.asList(superMethod.getDeclaredAnnotations()));
				} else {
					annotations.addAll(Arrays.asList(superMethod.getDeclaredAnnotations()));
				}

			} catch(NoSuchMethodException | SecurityException e) {
			}
		}

		return annotations;
	}

	public static boolean isAllDefaultMethods(Class<? extends Annotation> _annotationClass) {
		boolean hasMethods = false;
		for(Method m : _annotationClass.getDeclaredMethods()) {
			hasMethods = true;
			if(m.getDefaultValue() == null) {
				return false;
			}
		}
		return hasMethods;
	}
*/

}
