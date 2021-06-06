package fr.java.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ArrayListWithDefault<T> extends ArrayList<T> implements CollectionWithDefault<T> {
	private static final long serialVersionUID = 8508061387065704404L;

	private T defaultValue;
	
	public ArrayListWithDefault() {
		super();
	}
	public ArrayListWithDefault(int initialCapacity) {
		super(initialCapacity);
	}
	public ArrayListWithDefault(Collection<? extends T> c) {
		super(c);
	}
	public ArrayListWithDefault(Collection<? extends T> c, T _defaultValue) {
		super(c);
		defaultValue = _defaultValue;
	}

	public boolean 	add(T _value, boolean _useAsDefault) {
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
