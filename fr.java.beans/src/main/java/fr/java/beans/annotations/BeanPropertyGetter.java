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
 * @file     BeanPropertyGetter.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BeanAnnotation
@BeanPropertyTag
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPropertyGetter {

	public String  name()			default BeanPropertyTag.DEFAULT;
	public String  display()		default BeanPropertyTag.DEFAULT;
	public String  category()		default BeanPropertyTag.DEFAULT_CATEGORY;
	public String  description()	default BeanPropertyTag.DEFAULT;

	public String  setter()			default BeanPropertyTag.NONE;

	public boolean expert()			default false;
	public boolean hidden()			default false;
	public boolean readOnly()		default false;

	public boolean bounded()		default false;
	public boolean constrained()	default false;
	public boolean preferred()		default false;

	public String  editor()			default BeanPropertyTag.DEFAULT;

}
