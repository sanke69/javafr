package fr.java.sdk.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// https://stackoverflow.com/questions/3397160/how-can-i-pass-a-class-as-parameter-and-return-a-generic-collection-in-java
// http://www.mybatis.org/mybatis-3/jacoco/org.apache.ibatis.type/TypeReference.java.html
public abstract class TypeReference<T> {
	private final Type type;

	protected TypeReference() {
		type = getSuperclassTypeParameter(getClass());
/*
		Type superclass = getClass().getGenericSuperclass();
		if(superclass instanceof Class<?>)
			throw new RuntimeException("Missing type parameter.");

		type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
*/
	}

	public final Type getType() {
		return type;
	}

	Type getSuperclassTypeParameter(Class<?> clazz) {
		Type genericSuperclass = clazz.getGenericSuperclass();

		if(genericSuperclass instanceof Class) {
			// try to climb up the hierarchy until meet something useful
			if(TypeReference.class != genericSuperclass)
				return getSuperclassTypeParameter(clazz.getSuperclass());

			throw new RuntimeException("'" + getClass() + "' extends TypeReference but misses the type parameter. Remove the extension or add a type parameter to it.");
		}

		Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
		// TODO remove this when Reflector is fixed to return Types
		if(rawType instanceof ParameterizedType)
			rawType = ((ParameterizedType) rawType).getRawType();

		return rawType;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
