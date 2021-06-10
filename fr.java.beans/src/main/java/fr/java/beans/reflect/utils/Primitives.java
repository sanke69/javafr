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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Deprecated
public enum Primitives {
	UNDEF		(false, -1,               ""),
	BOOLEAN		(true,  0,                "1"),
	BYTE		(true,  Byte.BYTES,       "8"),
	SHORT		(true,  Short.BYTES,      "16"),
	INTEGER		(true,  Integer.BYTES,    "32"), 
	LONG		(true,  Long.BYTES,       "64"), 
	FLOAT		(false, Float.BYTES,      "32F"), 
	DOUBLE		(false, Double.BYTES,     "64D"),

	// EXPERIMENTAL
//	FLOAT2( 2 * Float.BYTES),  FLOAT3( 3 * Float.BYTES),  FLOAT4( 4 * Float.BYTES),
//	DOUBLE2(2 * Double.BYTES), DOUBLE3(3 * Double.BYTES), DOUBLE4(4 * Double.BYTES),

	// SPECIAL CASES
	STRING		(true, Integer.MAX_VALUE, "S");

	public static Primitives of(Class<?> _class) {
		if(boolean.class.isAssignableFrom(_class) || Boolean.class.isAssignableFrom(_class))
			return BOOLEAN;
		if(byte.class.isAssignableFrom(_class) || Byte.class.isAssignableFrom(_class))
			return BYTE;
		if(short.class.isAssignableFrom(_class) || Short.class.isAssignableFrom(_class))
			return SHORT;
		if(int.class.isAssignableFrom(_class) || Integer.class.isAssignableFrom(_class))
			return INTEGER;
		if(long.class.isAssignableFrom(_class) || Long.class.isAssignableFrom(_class))
			return LONG;
		if(float.class.isAssignableFrom(_class) || Float.class.isAssignableFrom(_class))
			return FLOAT;
		if(double.class.isAssignableFrom(_class) || Double.class.isAssignableFrom(_class))
			return DOUBLE;
		if(String.class.isAssignableFrom(_class))
			return STRING;
		
		// Special Cases
		if(BigInteger.class.isAssignableFrom(_class))
			return LONG;
		if(BigDecimal.class.isAssignableFrom(_class))
			return DOUBLE;
		
//		throw new IllegalArgumentException(); 
		return null;
	}

	final public int BYTES;
	boolean          integerType;
	int 	         sizeInBytes;
	String           suffix;

	private Primitives(boolean _integer, int _sizeInBytes, String _suffix) {
		integerType = _integer;
		BYTES = sizeInBytes = _sizeInBytes;
		suffix = _suffix;
	}

	public boolean isInteger() {
		return integerType;
	}

	public int    nbBytes() {
		return sizeInBytes;
	}
	public int    bytes() {
		return sizeInBytes;
	}
	public String suffix() {
		return suffix;
	}

	
	private static final Map<Class<?>, Class<?>>	PRIMITIVE_TO_WRAPPER_TYPE;
	private static final Map<Class<?>, Class<?>>	WRAPPER_TO_PRIMITIVE_TYPE;

	static {
		Map<Class<?>, Class<?>> primToWrap = new HashMap<>(16);
		Map<Class<?>, Class<?>> wrapToPrim = new HashMap<>(16);

		add(primToWrap, wrapToPrim, boolean.class, Boolean.class);
		add(primToWrap, wrapToPrim, byte.class,    Byte.class);
		add(primToWrap, wrapToPrim, char.class,    Character.class);
		add(primToWrap, wrapToPrim, double.class,  Double.class);
		add(primToWrap, wrapToPrim, float.class,   Float.class);
		add(primToWrap, wrapToPrim, int.class,     Integer.class);
		add(primToWrap, wrapToPrim, long.class,    Long.class);
		add(primToWrap, wrapToPrim, short.class,   Short.class);
		add(primToWrap, wrapToPrim, void.class,    Void.class);

		PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
		WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(wrapToPrim);
	}

	private static void add(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
		forward.put(key, value);
		backward.put(value, key);
	}

	public static Set<Class<?>> 		allPrimitiveTypes() {
		return PRIMITIVE_TO_WRAPPER_TYPE.keySet();
	}
	public static Set<Class<?>> 		allWrapperTypes() {
		return WRAPPER_TO_PRIMITIVE_TYPE.keySet();
	}

	public static Object 				getDefaultPrimitiveValue(Class<?> _class) {
		if(boolean.class.isAssignableFrom(_class))
			return (boolean) false;
		if(byte.class.isAssignableFrom(_class))
			return (byte) 0;
		if(short.class.isAssignableFrom(_class))
			return (short) 0;
		if(int.class.isAssignableFrom(_class))
			return (int) 0;
		if(long.class.isAssignableFrom(_class))
			return (long) 0;
		if(float.class.isAssignableFrom(_class))
			return (float) 0f;
		if(double.class.isAssignableFrom(_class))
			return (double) 0d;
		return null;
	}

	public static boolean 				isPrimitiveType(Class<?> type) {
		return type.isPrimitive();
	}
	public static boolean 				isWrapperType(Class<?> type) {
		if(type == null)
			throw new NullPointerException();
		return WRAPPER_TO_PRIMITIVE_TYPE.containsKey(type);
	}

	public static Class<?> 				getPrimitiveType(final Class<?> _class) {
		return WRAPPER_TO_PRIMITIVE_TYPE.get(_class) != null ? WRAPPER_TO_PRIMITIVE_TYPE.get(_class) : _class;
	}
	public static Class<?> 				getWrapperType(final Class<?> _class) {
		return PRIMITIVE_TO_WRAPPER_TYPE.get(_class) != null ? PRIMITIVE_TO_WRAPPER_TYPE.get(_class) : _class;
	}

	public static <T> Class<T> 			wrap(Class<T> type) {
		if(type == null)
			throw new NullPointerException();

		@SuppressWarnings("unchecked")
		Class<T> wrapped = (Class<T>) PRIMITIVE_TO_WRAPPER_TYPE.get(type);
		return (wrapped == null) ? type : wrapped;
	}
	public static <T> Class<T> 			unwrap(Class<T> type) {
		if(type == null)
			throw new NullPointerException();

		@SuppressWarnings("unchecked")
		Class<T> unwrapped = (Class<T>) WRAPPER_TO_PRIMITIVE_TYPE.get(type);
		return (unwrapped == null) ? type : unwrapped;
	}


}
