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
