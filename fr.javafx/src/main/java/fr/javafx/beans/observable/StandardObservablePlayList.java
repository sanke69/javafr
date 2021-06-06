package fr.javafx.beans.observable;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import fr.java.lang.exceptions.IllegalAccessArgument;

public class StandardObservablePlayList<T, P> extends SimpleObservablePlayMap<T, P> {
	Predicate<T> 	predicate;
	Function<T, P>  supplier;

	public StandardObservablePlayList(Predicate<T> _predicate, Function<T, P> _supplier) {
		super();

		predicate = _predicate;
		supplier  = _supplier;
	}
	public StandardObservablePlayList(Predicate<T> _predicate, Function<T, P> _supplier, Collection<T> _keys) {
		super();

		predicate = _predicate;
		supplier  = _supplier;
	}

	public Predicate<T> 	getPredicate() {
		return predicate;
	}
	public Function<T, P> 	getSupplier() {
		return supplier;
	}

	public Collection<T> 	getKeys() {
		return getBackedMap().keySet();
	}

	@Override
	public boolean 			add(P _value) {
		throw new IllegalAccessArgument();
	}
	public void 			addKey(T _key) {
		if(predicate.test(_key))
			addPlayable(_key, supplier.apply(_key));
	}
	public boolean 			addAllKeys(Collection<T> _keys) {
		boolean changed = false;

		for(T k : _keys)
			if(predicate.test(k)) {
				addPlayable(k, supplier.apply(k));
				changed = true;
			}

		return changed;
	}

}
