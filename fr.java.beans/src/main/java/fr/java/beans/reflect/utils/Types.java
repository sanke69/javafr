package fr.java.beans.reflect.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import fr.java.beans.reflect.types.GenericTypeReflector;

// https://github.com/xvik/generics-resolver
public class Types {
	private static final String TYPE_NAME_PREFIX_FOR_CLASS     = "class ";
	private static final String TYPE_NAME_PREFIX_FOR_INTERFACE = "interface ";

	public static Class<?> toClass(Type type) throws ClassNotFoundException {
		String className = getClassName(type);
		if (className == null || className.isEmpty()) {
			return null;
		}
		return Class.forName(className);
	}

	public static String 	getClassName(Type type) {
		if (type == null) {
			return "";
		}
		String className = type.toString();
		if (className.startsWith(TYPE_NAME_PREFIX_FOR_CLASS)) {
			className = className.substring(TYPE_NAME_PREFIX_FOR_CLASS.length());
		}
		return className;
	}

	public static Type 		ofField(Field _f) {
		return GenericTypeReflector.getExactFieldType(_f, _f.getDeclaringClass());
	}
	public static Type 		ofField(Field _f, Type _owner) {
		return GenericTypeReflector.getExactFieldType(_f, _owner);
	}
	public static Type 		ofReturn(Method _m) {
		return GenericTypeReflector.getExactReturnType(_m, _m.getDeclaringClass());
	}
	public static Type 		ofReturn(Method _m, Type _owner) {
		return GenericTypeReflector.getExactReturnType(_m, _owner);
	}
	public static Type 		ofArgument(Method _f) {
		return ofArgument(_f, 0);
	}
	public static Type 		ofArgument(Method _f, int _index) {
		return null;
	}
	public static Type[] 	ofArguments(Method _f) {
		return null;
	}

}
