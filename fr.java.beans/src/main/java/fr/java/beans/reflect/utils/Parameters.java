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

import java.lang.reflect.Parameter;

public class Parameters {

	public static Class<?>[] asClasses(final Object... _argv) {
		if(_argv == null)
			return (Class<?>[]) null;

		Class<?>[] classes = new Class<?>[_argv.length];

		int n = 0;
		for(Object arg : _argv) {
			classes[n] = arg.getClass();

			if(Primitives.isWrapperType(classes[n]))
				classes[n] = Primitives.getPrimitiveType(classes[n]);

			++n;
		}

		return classes;
	}

	public static boolean assertParameterTypes(final Parameter[] _params, final Class<?>... _argc) {
		if(_params.length != _argc.length)
			return false;

		for(int i = 0; i < _params.length; ++i) {
			Parameter p = _params[i];
			Class<?> c = p.getClass();

			if(!c.getClass().isAssignableFrom(_argc[i]))
				return false;
		}

		return true;
	}
	public static boolean assertParameterTypes(final Parameter[] _params, final Object... _args) {
		if(_params.length != _args.length)
			return false;

		Class<?>[] argc = new Class<?>[_args.length];
		for(int i = 0; i < _args.length; ++i) {
			argc[i] = _args[i].getClass();
			if(Primitives.isWrapperType(argc[i]))
				argc[i] = Primitives.getPrimitiveType(argc[i]);
		}

		for(int i = 0; i < _params.length; ++i) {
			Parameter p = _params[i];
			Class<?> c = p.getClass();

			if(!c.getClass().isAssignableFrom(argc[i]))
				return false;
		}

		return true;
	}

}
