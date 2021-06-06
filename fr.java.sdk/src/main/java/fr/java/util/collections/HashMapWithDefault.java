package fr.java.util.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapWithDefault<K, V> extends HashMap<K, V> implements MapWithDefault<K, V> {
	private static final long serialVersionUID = 8508061387065704404L;

	private K defaultKey;
	private V defaultValue;

    public HashMapWithDefault() {
        super();
    }
    public HashMapWithDefault(int initialCapacity) {
    	super(initialCapacity);
    }
    public HashMapWithDefault(int initialCapacity, float loadFactor) {
    	super(initialCapacity, loadFactor);
    }
    public HashMapWithDefault(Map<? extends K, ? extends V> m) {
    	super(m);
    }
    public HashMapWithDefault(Map<? extends K, ? extends V> m, K _defaultKey) {
    	super(m);
    }

	public void 			setDefaultKey(K _key) {
		if(!containsKey(_key))
			throw new IllegalArgumentException();
		defaultKey = _key;
	}
	public Optional<K> 		getDefaultKey() {
		return Optional.ofNullable(defaultKey);
	}

	public void 			setDefaultValue(V _value) {
		if(!containsValue(_value))
			throw new IllegalArgumentException();
		defaultValue = _value;
	}
	public Optional<V> 		getDefaultValue() {
		return Optional.ofNullable(defaultValue);
	}

}
