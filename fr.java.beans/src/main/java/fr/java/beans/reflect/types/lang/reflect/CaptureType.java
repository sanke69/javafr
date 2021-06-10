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
package fr.java.beans.reflect.types.lang.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * CaptureType represents a wildcard that has gone through capture conversion.
 * It is a custom subinterface of Type, not part of the java builtin Type hierarchy.
 * 
 * @author Wouter Coekaerts <wouter@coekaerts.be>
 */
public interface CaptureType extends Type {
    /**
     * Returns an array of <tt>Type</tt> objects representing the upper
     * bound(s) of this capture. This includes both the upper bound of a <tt>? extends</tt> wildcard,
     * and the bounds declared with the type variable.
     * References to other (or the same) type variables in bounds coming from the type variable are
     * replaced by their matching capture.
     */
	Type[] getUpperBounds();
	
    /**
     * Returns an array of <tt>Type</tt> objects representing the 
     * lower bound(s) of this type variable. This is the bound of a <tt>? super</tt> wildcard.
     * This normally contains only one or no types; it is an array for consistency with {@link WildcardType#getLowerBounds()}.
     */
	Type[] getLowerBounds();
}
