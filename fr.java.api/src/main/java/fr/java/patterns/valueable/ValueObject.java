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
package fr.java.patterns.valueable;

import java.util.function.Function;

import fr.java.lang.exceptions.IllegalCastException;
import fr.java.lang.tuples.Pair;
import fr.java.patterns.connection.CompatibilityResult;
import fr.java.patterns.connection.CompatibilityRules;

public class ValueObject {

	public static final ValueObject empty() 				{ return new ValueObject(null,   ValueType.NULL_TYPE); }

	public static final ValueObject of(boolean _value) 		{ return new ValueObject(_value, ValueType.BOOLEAN_TYPE); }
	public static final ValueObject of(byte _value)    		{ return new ValueObject(_value, ValueType.BYTE_TYPE); }
	public static final ValueObject of(char _value)    		{ return new ValueObject(_value, ValueType.CHAR_TYPE); }
	public static final ValueObject of(short _value)   		{ return new ValueObject(_value, ValueType.SHORT_TYPE); }
	public static final ValueObject of(int _value)     		{ return new ValueObject(_value, ValueType.INTEGER_TYPE); }
	public static final ValueObject of(long _value)    		{ return new ValueObject(_value, ValueType.LONG_TYPE); }
	public static final ValueObject of(float _value)   		{ return new ValueObject(_value, ValueType.FLOAT_TYPE); }
	public static final ValueObject of(double _value)  		{ return new ValueObject(_value, ValueType.DOUBLE_TYPE); }

	public static final ValueObject of(Boolean _value) 		{ return new ValueObject(_value, ValueType.BOOLEAN_TYPE); }
	public static final ValueObject of(Byte _value)    		{ return new ValueObject(_value, ValueType.BYTE_TYPE); }
	public static final ValueObject of(Character _value)    { return new ValueObject(_value, ValueType.CHAR_TYPE); }
	public static final ValueObject of(Short _value)   		{ return new ValueObject(_value, ValueType.SHORT_TYPE); }
	public static final ValueObject of(Integer _value)     	{ return new ValueObject(_value, ValueType.INTEGER_TYPE); }
	public static final ValueObject of(Long _value)    		{ return new ValueObject(_value, ValueType.LONG_TYPE); }
	public static final ValueObject of(Float _value)   		{ return new ValueObject(_value, ValueType.FLOAT_TYPE); }
	public static final ValueObject of(Double _value)  		{ return new ValueObject(_value, ValueType.DOUBLE_TYPE); }

	public static final ValueObject of(String _value) 		{ return new ValueObject(_value, ValueType.STRING_TYPE); }

	public static final ValueObject of(Object _value) 		{ return new ValueObject(_value); }

	final Object       			value;
	final ValueType<?> 			type;
	final Function<Object, ?> 	converter;
	final CompatibilityRules<?>	compatRules;

	public ValueObject(final Object _value) {
		super();
		value       = _value;
		type        = _value != null ? ValueType.determineValueType(_value.getClass()) : ValueType.NULL_TYPE;
		converter   = null;
		compatRules = null;
	}
	public ValueObject(final Object _value, final ValueType<?> _type) {
		super();
		value       = _value;
		type        = _type;
		converter   = null;
		compatRules = null;
	}
	public ValueObject(final Object _value, final ValueType<?> _type, final CompatibilityRules<?> _compatibilityRules) {
		super();
		value       = _value;
		type        = _type;
		compatRules = _compatibilityRules;
		converter   = null;
	}
	public ValueObject(final Object _value, final ValueType<?> _type, final Function<Object, ?> _convert2ValueType) {
		super();
		value       = _value;
		type        = _type;
		compatRules = null;
		converter   = _convert2ValueType;
	}

	public boolean							isDefined() {
		return getValue() != null || getType() != null;
	}
	public boolean							isEmpty() {
		return getValue() == null;
	}
	public boolean							isPresent() {
		return getValue() != null;
	}

	public Object 							getValue() {
		return value;
	}
	@SuppressWarnings("unchecked")
	public <T> T							getValue(Class<T> _cast) throws IllegalCastException {
		Object o = getValue();
		T casted = null;

		try {
			casted = (T) o;
		} catch(Exception e) {
//			e.printStackTrace();
			throw new IllegalCastException();
		}

		return casted;
	};
	public ValueType<?> 					getType() {
		return type;
	}

	public CompatibilityRules<ValueObject> 	compatibility() {
		return null;
	}
	public CompatibilityResult 				compatible(final ValueObject _source) {
		if(compatibility() != null)
			return compatibility().apply(_source, this);
		
		return new CompatibilityResult() {
			private final boolean compatible;
			private final String  errorMessage;
	
			{
				Pair<Boolean, String> result = computeCompatibility(_source, ValueObject.this);
				compatible   = result.getFirst();
				errorMessage = result.getSecond();
			}
	
			@Override
			public boolean 	isCompatible() {
				return compatible;
			}
	
			@Override
			public String 	getMessage() {
				if(isCompatible())
					return "compatible   : " + _source.getType() + " -> " + getType();
				return "incompatible : " + _source.getType() + " -> " + getType() + ", reason: " + errorMessage;
			}
	
			@Override
			public String 	getStatus() {
				throw new UnsupportedOperationException("Not supported yet.");
			}
	
			@Override
			public String 	toString() {
				StringBuilder sb = new StringBuilder();
	
				sb.append("[CR(DftConnVO): ");
	//    		sb.append("isCompatible? " + (isCompatible() ? "T" : "F"));
	//    		sb.append(", msg= " + getMessage());
				sb.append(getMessage());
				sb.append(" ]");
	
				return sb.toString();
			}
		};
	}

	public final int 						hashCode() {
		return value != null ? value.hashCode() : -1;
	}

	public final boolean 					equals(Object _o) {
		if(_o instanceof ValueObject) {
			ValueObject vo = (ValueObject) _o;

			if(value == null)
				return type.equals(vo.getType());

			return value.equals(vo.getValue()) && type.equals(vo.getType());
		}

		return value.equals(_o);
	}

	public final String 					toString() {
		return value.toString();
	}

	private Pair<Boolean, String> 			computeCompatibility(final ValueObject _source, final ValueObject _destination) {
		boolean differentObjects = _source != _destination;
		boolean compatibleType   = _source.getType().equals(_destination.getType());
		String  errorMessage     = !differentObjects ?
										"Compatibility can only established between different objects. <src> cannot be equal to <dst>."
										:
										"Compatibility can only established between connectors of the same connection/flow type.";

		return new Pair<Boolean, String>() {
			@Override public Boolean getFirst() { return differentObjects && compatibleType; }
			@Override public String getSecond() { return errorMessage; }
		};
	}

	public final static class NumberRules implements CompatibilityRules<ValueObject> {

		@Override
		public final CompatibilityResult apply(ValueObject _source, ValueObject _destination) {
			boolean aIsNumber  = ValueType.PNUMBER_TYPES.contains(_source.getValue()) || ValueType.NUMBER_TYPES.contains(_source.getValue());
			boolean bIsNumber  = ValueType.PNUMBER_TYPES.contains(_destination.getValue()) || ValueType.NUMBER_TYPES.contains(_destination.getValue());
			boolean compatible = aIsNumber && bIsNumber;
			
			return new CompatibilityResult() {

				@Override
				public boolean isCompatible() {
					return compatible;
				}

				@Override
				public String getMessage() {
					return compatible ?
							"src:" + _source.getType() + " and dst:" + _destination.getType() + " are compatible."
							:
							"src:" + _source.getType() + " and dst:" + _destination.getType() + " are NOT compatible.";
				}

				@Override
				public String getStatus() {
					return "OK";
				}
				
			};
		}

	}

}
