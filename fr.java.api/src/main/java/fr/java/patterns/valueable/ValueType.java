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
 * @file     ValueType.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.patterns.valueable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

class SimpleValueType {
    public static final int UNKNOWN  =  0;
    public static final int ANY      = -1;
    public static final int NULL     =  1;
    public static final int BOOLEAN  =  2;
    public static final int NUMBER   =  3;
    public static final int INTEGER  =  4;
    public static final int DECIMAL  =  5;
    public static final int CHAR     =  6;
    public static final int STRING   =  7;
    public static final int DATE     =  8;
    public static final int LIST     =  9;
    public static final int OBJECT   = 10;
    public static final int FUNCTION = 11;
}

public class ValueType<T> {
    public static final ValueType<?>  				NULL_TYPE         = new ValueType<>( "null",      null,            SimpleValueType.NULL);
    public static final ValueType<?>  				ANY_TYPE          = new ValueType<>( "any",       null,            SimpleValueType.ANY);

    // primitive types
    public static final ValueType<?>  				PBOOLEAN_TYPE     = new ValueType<>( "boolean",   Boolean.TYPE,    SimpleValueType.BOOLEAN );
    public static final ValueType<?>  				PBYTE_TYPE        = new ValueType<>( "byte",      Byte.TYPE,       SimpleValueType.INTEGER );
    public static final ValueType<?>  				PCHAR_TYPE        = new ValueType<>( "char",      Character.TYPE,  SimpleValueType.CHAR );
    public static final ValueType<?>  				PSHORT_TYPE       = new ValueType<>( "short",     Short.TYPE,      SimpleValueType.INTEGER );
    public static final ValueType<?>  				PINTEGER_TYPE     = new ValueType<>( "int",       Integer.TYPE,    SimpleValueType.INTEGER );
    public static final ValueType<?>  				PLONG_TYPE        = new ValueType<>( "long",      Long.TYPE,       SimpleValueType.INTEGER );
    public static final ValueType<?>  				PFLOAT_TYPE       = new ValueType<>( "float",     Float.TYPE,      SimpleValueType.DECIMAL );
    public static final ValueType<?>  				PDOUBLE_TYPE      = new ValueType<>( "double",    Double.TYPE,     SimpleValueType.DECIMAL );

    public static final Collection<ValueType<?>>	PNUMBER_TYPES     = Arrays.asList(PBOOLEAN_TYPE, PBYTE_TYPE, PCHAR_TYPE, PSHORT_TYPE, PINTEGER_TYPE, PLONG_TYPE, PFLOAT_TYPE, PDOUBLE_TYPE);

    // wrapper types
    public static final ValueType<?>  				BOOLEAN_TYPE      = new ValueType<>( "Boolean",   Boolean.class,   SimpleValueType.BOOLEAN );
    public static final ValueType<?>  				BYTE_TYPE         = new ValueType<>( "Byte",      Byte.class,      SimpleValueType.INTEGER);
    public static final ValueType<?>  				CHAR_TYPE         = new ValueType<>( "Character", Character.class, SimpleValueType.CHAR );
    public static final ValueType<?>  				SHORT_TYPE        = new ValueType<>( "Short",     Short.class,     SimpleValueType.INTEGER );
    public static final ValueType<?> 				INTEGER_TYPE      = new ValueType<>( "Integer",   Integer.class,   SimpleValueType.INTEGER );
    public static final ValueType<?>  				LONG_TYPE         = new ValueType<>( "Long",      Long.class,      SimpleValueType.INTEGER );
    public static final ValueType<?>  				FLOAT_TYPE        = new ValueType<>( "Float",     Float.class,     SimpleValueType.DECIMAL );
    public static final ValueType<?>  				DOUBLE_TYPE       = new ValueType<>( "Double",    Double.class,    SimpleValueType.DECIMAL );

    public static final ValueType<?>  				NUMBER_TYPE       = new ValueType<>( "Number",    Number.class,    SimpleValueType.NUMBER );

    public static final Collection<ValueType<?>>	NUMBER_TYPES      = Arrays.asList(BOOLEAN_TYPE, BYTE_TYPE, CHAR_TYPE, SHORT_TYPE, INTEGER_TYPE, LONG_TYPE, FLOAT_TYPE, DOUBLE_TYPE, NUMBER_TYPE);

    public static final ValueType<?>  				CLASS_TYPE        = new ValueType<>( "Class",     Class.class,     SimpleValueType.OBJECT );

    public static final ValueType<?>  				OBJECT_TYPE       = new ValueType<>( "Object",    Object.class,    SimpleValueType.OBJECT );

    public static final ValueType<?>  				ARRAY_TYPE        = new ValueType<>( "Array",     Object[].class,  SimpleValueType.LIST );

//  public static final ValueType<?>  				BIG_DECIMAL_TYPE  = new BigDecimalValueType();
//  public static final ValueType<?>  				BIG_INTEGER_TYPE  = new BigIntegerValueType();
    public static final ValueType<?>  				STRING_TYPE       = new StringValueType();

    private static final Collection<ValueType<?>> 	REGISTERED_TYPES  = new HashSet<ValueType<?>>();
 
    public static ValueType<?> determineValueType(final Class<?> clazz) {
        if ( clazz == null )
            return ValueType.NULL_TYPE;

		// Primitives
		if (clazz == Boolean.TYPE) 							return ValueType.PBOOLEAN_TYPE;
		else if (clazz == Byte.TYPE) 						return ValueType.PBYTE_TYPE;
		else if (clazz == Character.TYPE) 					return ValueType.PCHAR_TYPE;
		else if (clazz == Short.TYPE) 						return ValueType.PSHORT_TYPE;
		else if (clazz == Integer.TYPE) 					return ValueType.PINTEGER_TYPE;
		else if (clazz == Long.TYPE) 						return ValueType.PLONG_TYPE;
		else if (clazz == Float.TYPE) 						return ValueType.PFLOAT_TYPE;
		else if (clazz == Double.TYPE) 						return ValueType.PDOUBLE_TYPE;

        // Primitive Wrappers
        if ( clazz == Boolean.class )						return ValueType.BOOLEAN_TYPE;
        else if ( clazz == Byte.class )						return ValueType.BYTE_TYPE;
        else if ( clazz == Character.class )				return ValueType.CHAR_TYPE;
        else if ( clazz == Short.class )					return ValueType.SHORT_TYPE;
        else if ( clazz == Integer.class )					return ValueType.INTEGER_TYPE;
        else if ( clazz == Long.class )						return ValueType.LONG_TYPE;
        else if ( clazz == Float.class )					return ValueType.FLOAT_TYPE;
        else if ( clazz == Double.class )					return ValueType.DOUBLE_TYPE;
        else if ( Number.class.isAssignableFrom( clazz ) ) 	return ValueType.NUMBER_TYPE;
        
        
        // Other Object types
        if ( clazz.isArray() ) 								return ValueType.ARRAY_TYPE;
        if ( clazz == String.class )
            return ValueType.STRING_TYPE;
        if ( clazz == Class.class )
            return ValueType.CLASS_TYPE;

        for(ValueType<?> other : REGISTERED_TYPES)
        	if(other.test(clazz))
        		return other;
        
        return ValueType.OBJECT_TYPE;
    }

    private String       		name;
    private Class<T>     		classType;
    private int          		simpleType;
    private Predicate<Class<?>> ofValueType;

    public ValueType() {
        this(null, null, 0);
    }
    private ValueType(final String name, final Class<T> classType, final int simpleType) {
        this.name = name;
        this.classType = classType;
        this.simpleType = simpleType;
    }

    public String 		getName() {
        return this.name;
    }

    public Class<T> 	getClassType() {
        return this.classType;
    }

	public boolean 		test(Class<?> _valueClass) {
        return ofValueType != null ? ofValueType.test(_valueClass) : false;
    }

    @SuppressWarnings("unchecked")
	public T 			cast(Object value) {
        return (T) value;
    }

    public String 		toString() {
        return "ValueType = '" + this.name + "'";
    }

    public int 			hashCode() {
        return this.name.hashCode();
    }

    public boolean 		equals(final Object object) {
        if ( object == this ) {
            return true;
        } else if (object instanceof ValueType) {
            ValueType<?>   that    = (ValueType<?>)object;
            return classType == that.classType &&
                   simpleType == that.simpleType &&
                   (name == that.name || name != null && name.equals(that.name));
        }
        return false;
    }

    public static class StringValueType extends ValueType<String> {
        public StringValueType() {
            super("String", String.class, SimpleValueType.STRING);
        }

        @Override
        public String cast( Object value ) {
            if(value == null)
                return null;

            return value instanceof String ? (String) value : value.toString();
        }
    }
    
/*
    public static class BigIntegerValueType extends ValueType<BigInteger> {
        public BigIntegerValueType() {
            super("BigInteger", BigInteger.class, SimpleValueType.NUMBER);
        }

        @Override
        public BigInteger cast( Object value ) {
            if (value == null) {
                return null;
            }
            return Numbers.getBigInteger( value );
        }
    }

    public static class BigDecimalValueType extends ValueType<BigDecimal> {
        public BigDecimalValueType() {
            super("BigDecimal", BigDecimal.class, SimpleValueType.NUMBER);
        }

        @Override
        public BigDecimal cast( Object value ) {
            if (value == null) {
                return null;
            }
            return Numbers.getBigDecimal( value );
        }
    }
*/
}
