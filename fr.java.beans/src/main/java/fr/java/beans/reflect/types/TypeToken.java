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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Wrapper around {@link Type}.
 * 
 * You can use this to create instances of Type for a type known at compile
 * time.
 * 
 * For example, to get the Type that represents List&lt;String&gt;:
 * <code>Type listOfString = new TypeToken&lt;List&lt;String&gt;&gt;(){}.getType();</code>
 * 
 * @author Wouter Coekaerts <wouter@coekaerts.be>
 * 
 * @param <T>
 *            The type represented by this TypeToken.
 */
public abstract class TypeToken<T> {

	private final Type type;

	/**
	 * Constructs a type token.
	 */
	protected TypeToken() {
		this.type = extractType();
	}

	private TypeToken(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	private Type extractType() {
		Type t = getClass().getGenericSuperclass();
		if (!(t instanceof ParameterizedType)) {
			throw new RuntimeException("Invalid TypeToken; must specify type parameters");
		}
		ParameterizedType pt = (ParameterizedType) t;
		if (pt.getRawType() != TypeToken.class) {
			throw new RuntimeException("Invalid TypeToken; must directly extend TypeToken");
		}
		return pt.getActualTypeArguments()[0];
	}

	/**
	 * Gets type token for the given {@code Class} instance.
	 */
	public static <T> TypeToken<T> get(Class<T> type) {
		return new TypeToken<T>(type) {};
	}

	/**
	 * Gets type token for the given {@code Type} instance.
	 */
	public static TypeToken<?> get(Type type) {
		return new TypeToken<Object>(type) {};
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeToken) && type.equals(((TypeToken<?>) obj).type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}
}
