package fr.java.util.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class HashSetWithDefault<T> extends HashSet<T> implements CollectionWithDefault<T> {
	private static final long serialVersionUID = 8508061387065704404L;

	private T defaultValue;
	
	public HashSetWithDefault() {
		super();
	}
	public HashSetWithDefault(int initialCapacity) {
		super(initialCapacity);
	}
	public HashSetWithDefault(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	 }
	public HashSetWithDefault(Collection<? extends T> c) {
		super(c);
	}
	public HashSetWithDefault(Collection<? extends T> c, T _defaultValue) {
		super(c);
		defaultValue = _defaultValue;
	}

	public boolean 			add(T _value, boolean _useAsDefault) {
		if(_useAsDefault)
			defaultValue = _value;
		return super.add(_value);
	}

	public void 			setDefault(T _value) {
		if(!contains(_value))
			throw new IllegalArgumentException();
		defaultValue = _value;
	}
	public Optional<T> 		getDefault() {
		return Optional.ofNullable( defaultValue );
	}

}
