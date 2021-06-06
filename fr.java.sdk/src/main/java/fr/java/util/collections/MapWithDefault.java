package fr.java.util.collections;

import java.util.Map;
import java.util.Optional;

public interface MapWithDefault<K, V> extends Map<K, V> {

	public void 			setDefaultKey(K _key);
	public Optional<K> 		getDefaultKey();

	public void 			setDefaultValue(V _value);
	public Optional<V> 		getDefaultValue();

}
