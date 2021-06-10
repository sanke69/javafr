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
package fr.java.beans.reflect.types.exceptions;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Thrown to indicate that a type argument for a parameterized type is not within the bound declared on the type
 * parameter.
 * 
 * @author Wouter Coekaerts <wouter@coekaerts.be>
 */
public class TypeArgumentNotInBoundException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final Type argument;
	private final TypeVariable<?> parameter;
	private final Type bound;
	
	public TypeArgumentNotInBoundException(Type argument, TypeVariable<?> parameter, Type bound) {
		super("Given argument [" + argument + "]" +
				" for type parameter [" + parameter.getName() + "] is not within the bound [" + bound + "]");
		this.argument = argument;
		this.parameter = parameter;
		this.bound = bound;
	}

	/**
	 * Returns the supplied argument that is not within the bound.
	 */
	public Type getArgument() {
		return argument;
	}

	/**
	 * Returns the type parameter.
	 */
	public TypeVariable<?> getParameter() {
		return parameter;
	}

	/**
	 * Returns the bound that was not satisfied.
	 * This is one of the members in <tt>getParameter().getBounds()</tt>.
	 */
	public Type getBound() {
		return bound;
	}
}
