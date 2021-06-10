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

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

abstract class AbstractRecursiveMembers<T> {

	@FunctionalInterface
	public static interface RecursiveMemberGetter<T> {
		List<T> getAll(Class<?> _class, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses);
	}
	@FunctionalInterface
	public static interface RecursiveMemberGetterWithPredicate<T> {
		List<T> getAll(Class<?> _class, boolean _only_public, boolean _with_inheritance, Collection<Class<?>> _stopClasses, Predicate<T> _filter);
	}

	static <T> void recursiveGetterFunc(RecursiveMemberGetter<T> _fn, List<T> _members, Class<?> _class, Boolean _only_public, Boolean _with_inheritance, Collection<Class<?>> _stopClasses) {
		if(_with_inheritance && _class.getSuperclass() != null)
			if(_stopClasses == null || _stopClasses.isEmpty() || !_stopClasses.contains(_class.getSuperclass())) {
				boolean notSuperStopClass = true;

				for(Class<?> stopClass : _stopClasses)
					if(_class.getSuperclass().isAssignableFrom(stopClass))
						notSuperStopClass = false;
				
				if(notSuperStopClass)
					_members.addAll(_fn.getAll(_class.getSuperclass(), _only_public, _with_inheritance, _stopClasses));
			}
	}
	static <T> void recursiveGetterFuncWithPredicate(RecursiveMemberGetterWithPredicate<T> _fn, List<T> _members, Class<?> _class, Boolean _only_public, Boolean _with_inheritance, Collection<Class<?>> _stopClasses, Predicate<T> _filter) {
		if(_with_inheritance && _class.getSuperclass() != null)
			if(_stopClasses == null || _stopClasses.isEmpty() || !_stopClasses.contains(_class.getSuperclass())) {
				boolean notSuperStopClass = true;

				for(Class<?> stopClass : _stopClasses)
					if(_class.getSuperclass().isAssignableFrom(stopClass))
						notSuperStopClass = false;
				
				if(notSuperStopClass)
					_members.addAll(_fn.getAll(_class.getSuperclass(), _only_public, _with_inheritance, _stopClasses, _filter));
			}
	}

}
