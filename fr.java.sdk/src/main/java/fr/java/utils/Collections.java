package fr.java.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.beans.reflect.utils.Instances;

public class Collections {

	/**
	 * asymmetric difference between two sets
	 * @param <T>
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static <T> Set<T> asymDiff(final Set<? extends T> s1, final Set<? extends T> s2) {
	    Set<T> asymmetricDiff = new HashSet<T>(s1);
	    asymmetricDiff.removeAll(s2);

	    return asymmetricDiff;
	}
	/**
	 * symmetric difference between two sets
	 * @param <T>
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static <T> Set<T> symDiff(final Set<? extends T> s1, final Set<? extends T> s2) {
	    Set<T> symmetricDiff = new HashSet<T>(s1); symmetricDiff.addAll(s2);
	    Set<T> tmp           = new HashSet<T>(s1); tmp.retainAll(s2);
	    symmetricDiff.removeAll(tmp);

	    return symmetricDiff;
	}

	public static boolean compareSets(Set<String> set1, Set<String> set2) {
		if(set1.size() != set2.size()) {
			return false;
		}
		for(String s : set1) {
			if(!set2.contains(s)) {
				return false;
			}
		}
		return true;
	}

	public static boolean compareMaps(Map<String, Object> m1, Map<String, Object> m2) {
		if(!compareSets(m1.keySet(), m2.keySet())) {
			return false;
		}
		for(String s : m1.keySet()) {
			if(!Instances.compare(m1.get(s), m2.get(s))) {
				return false;
			}
		}
		return true;
	}

	public static <T> List<T> toList(Collection<T> _values) {
		return new ArrayList<T>(_values);
	}


	public static String formatStringMap(Map<String, String> _stringMap, Function<? super Entry<String, String>, ? extends String> _mapper, CharSequence _joiner) {
		//_stringMap.entrySet().stream().map((entry) -> "-D" + entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(" "));
		return _stringMap.entrySet().stream().map(_mapper).collect(Collectors.joining(_joiner));
	}
	public static <U> U formatStringMap(Map<String, String> _stringMap, BiFunction<? super String,? super String,? extends U> _reduce, BiFunction<? super U,? super U,? extends U> _assemble) {
		//return new ConcurrentHashMap<>(_stringMap).reduce(1, (k, v) -> ("-D" + k + "=" + v), (r1, r2) -> r1 + " " + r2);
		return new ConcurrentHashMap<>(_stringMap).reduce(1, _reduce, _assemble);
	}

	/**
	 * Returns a map where the ith entry is a combination of 
	 * 	the ith item of {@code keys} as key, and the
	 * 	the ith item of {@code values} as value.
	 *
	 * @param <K>                           the {@code keys} objects class
	 * @param <V>                           the {@code values} objects class
	 * @param  keys 						the list to map
	 * @param  values 						the function to produce the key from a list item
	 * @return 								the resulting map
	 * @throws IllegalArgumentException 	if {@code keys} contains more elements than {@code values}
	 * @throws IllegalStateException 		on duplicate key
	 * 
	 */
	public static <K, V> Map<K, V> 				toMap(List<K> keys, List<V> values) {
		if(keys.size() > values.size())
			throw new IllegalArgumentException();
		return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
	}

	/**
	 * Returns a map where each entry is an item of {@code list} mapped by the
	 * key produced by applying {@code mapper} to the item.
	 *
	 * @param list the list to map
	 * @param mapper the function to produce the key from a list item
	 * @return the resulting map
	 * @throws IllegalStateException on duplicate key
	 */
	public static <K, V, T extends V> Map<K, V> toMap(List<T> list, Function<? super T, ? extends K> mapper) {
	    return list.stream().collect(Collectors.toMap(mapper, Function.identity()));
	}
//	public static <K, T>              Map<K, T> toMap(List<T> list, Function<? super T, ? extends K> mapper) {
//	    return list.stream().collect(Collectors.toMap(mapper, Function.identity()));
//	}
	// https://stackoverflow.com/questions/3199657/java-how-to-convert-a-list-to-a-mapstring/3199899#3199899
	// https://stackoverflow.com/questions/26691278/generic-static-method-constrains-types-too-much
	
}
