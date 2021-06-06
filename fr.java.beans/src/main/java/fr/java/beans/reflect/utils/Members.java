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
 * @file     Members.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class Members {

	public static Class<? extends Member> 	memberType(Member member) {
		if(member == null)
			throw new NullPointerException();

		if(member instanceof Field) {
			return Field.class;

		} else if(member instanceof Method) {
			return Method.class;

		} else if(member instanceof Constructor) {
			return Constructor.class;

		} else {
			throw new IllegalArgumentException("Unsupported implementation class for Member, " + member.getClass());
		}
	}

	public static String 					toString(Member member) {
		Class<? extends Member> memberType = memberType(member);

		if(memberType == Method.class) {
			return member.getDeclaringClass().getName() + "." + member.getName() + "(...)";
		} else if(memberType == Field.class) {
			return member.getDeclaringClass().getName() + "." + member.getName();
		} else if(memberType == Constructor.class) {
			return member.getDeclaringClass().getName() + ".<init>()";
		} else {
			throw new AssertionError();
		}
	}

}
