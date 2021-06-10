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
package fr.java.beans.reflect.types;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

import fr.java.beans.reflect.types.exceptions.UnresolvedTypeVariableException;

/**
 * Mapping between type variables and actual parameters.
 * 
 * @author Wouter Coekaerts <wouter@coekaerts.be>
 */
class TypeVariableMap {
	private final Map<TypeVariable<?>, Type> map = new HashMap<TypeVariable<?>, Type>();
	
	/**
	 * Creates an empty VarMap
	 */
	TypeVariableMap() {
	}
	
	/**
	 * Creates a VarMap mapping the type parameters of the class used in <tt>type</tt> to their actual value.
	 */
	TypeVariableMap(ParameterizedType type) {
		// loop over the type and its generic owners
		do {
			Class<?> clazz = (Class<?>)type.getRawType();
			Type[] arguments = type.getActualTypeArguments();
			TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
			
			// since we're looping over two arrays in parallel, just to be sure check they have the same size
			if (arguments.length != typeParameters.length) {
				throw new IllegalStateException("The given type [" + type + "] is inconsistent: it has " +
						arguments.length + " arguments instead of " + typeParameters.length);
			}

			
			for (int i = 0; i < arguments.length; i++) {
				add(typeParameters[i], arguments[i]);
			}
			
			Type owner = type.getOwnerType();
			type = (owner instanceof ParameterizedType) ? (ParameterizedType)owner : null;
		} while (type != null);
	}
	
	void add(TypeVariable<?> variable, Type value) {
		map.put(variable, value);
	}
	
	void addAll(TypeVariable<?>[] variables, Type[] values) {
		assert variables.length == values.length;
		for (int i = 0; i < variables.length; i++) {
			map.put(variables[i], values[i]);
		}
	}
	
	TypeVariableMap(TypeVariable<?>[] variables, Type[] values) {
		addAll(variables, values);
	}
	
	Type map(Type type) {
		if (type instanceof Class) {
			return type;
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> tv = (TypeVariable<?>) type;
			if (!map.containsKey(type)) {
				throw new UnresolvedTypeVariableException(tv);
			}
			return map.get(type);
		} else if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			return TypeFactory.createParameterizedType((Class<?>)pType.getRawType(), map(pType.getActualTypeArguments()), pType.getOwnerType() == null ? pType.getOwnerType() : map(pType.getOwnerType()));
		} else if (type instanceof WildcardType) {
			WildcardType wType = (WildcardType) type;
			return TypeFactory.createWildcardType(map(wType.getUpperBounds()), map(wType.getLowerBounds()));
		} else if (type instanceof GenericArrayType) {
			return TypeFactory.createGenericArrayType(map(((GenericArrayType)type).getGenericComponentType()));
		} else {
			throw new RuntimeException("not implemented: mapping " + type.getClass() + " (" + type + ")");
		}
	}
	
	Type[] map(Type[] types) {
		Type[] result = new Type[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = map(types[i]);
		}
		return result;
	}
}