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
package fr.java.beans.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPropertyTag {
	// Extra Properties
		// Category
	public static final String CATEGORY_PROPERTY   = "Category";
		// For Numeric Value
	public static final String MIN_VALUE           = "Min";
	public static final String MAX_VALUE           = "Max";
	public static final String STEP_VALUE          = "Step";

	// Default Values
	public static final String NO_DESCRIPTION      = "No description available";

	public static final String DEFAULT             = "default";
	public static final String NONE                = "none";

	public static final String DEFAULT_CATEGORY    = "Generic";
	public static final double DEFAULT_MIN_VALUE   = -1e13;
	public static final double DEFAULT_MAX_VALUE   =  1e13;
	public static final double DEFAULT_STEP_VALUE  =     1;

}
