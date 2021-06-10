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
package fr.java.lang.functionals;

public interface Constructor<R> {

	@FunctionalInterface
	public static interface NoArg<R> extends Constructor<R> {
		public R newInstance();
	}
	@FunctionalInterface
	public static interface OneArg<R, T0> extends Constructor<R> {
		public R newInstance(T0 _arg);
	}
	@FunctionalInterface
	public static interface TwoArgs<R, T0, T1> extends Constructor<R> {
		public R newInstance(T0 _arg1, T1 _arg2);
	}
	@FunctionalInterface
	public static interface ThreeArgs<R, T0, T1, T2> extends Constructor<R> {
		public R newInstance(T0 _arg1, T1 _arg2, T2 _arg3);
	}

}
