package fr.java.sdk.player.playlists.defaults;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class AdvancedPlayList<T, P> extends DefaultPlayMap<T, P> {
	Predicate<T> 	predicate;
	Function<T, P>  supplier;

	public AdvancedPlayList(Predicate<T> _predicate, Function<T, P> _supplier) {
		super();

		predicate = _predicate;
		supplier  = _supplier;
	}
	public AdvancedPlayList(Predicate<T> _predicate, Function<T, P> _supplier, Collection<T> _keys) {
		super();

		predicate = _predicate;
		supplier  = _supplier;
		
		addFromKeys(_keys);
	}

	public Predicate<T> 	getPredicate() {
		return predicate;
	}
	public Function<T, P> 	getSupplier() {
		return supplier;
	}

	public Collection<T> 	getKeys() {
		return content.keySet();
	}

	public boolean 			addFromKey(T _key) {
		if(predicate.test(_key)) {
			addEntry(_key, supplier.apply(_key));
			return true;
		}
		return false;
	}
	public boolean 			addFromKeys(Collection<T> _keys) {
		boolean changed = false;

		for(T k : _keys)
			if(predicate.test(k)) {
				addEntry(k, supplier.apply(k));
				changed = true;
			}

		return changed;
	}

}
