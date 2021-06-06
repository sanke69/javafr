package fr.javafx.beans.observable;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import fr.java.player.PlayList;

public class MyFXCollections {

	public static final <K, P> ObservableTreeMap<K, P> 				emptyTreeMap() {
		ObservableTreeMap<K, P> result = new ObservableTreeMap<K, P>(new TreeMap<>());
		return result;
	}

	public static final <K, P> ObservableTreeMap<K, P> 				observableTreeMap() {
		ObservableTreeMap<K, P> result = new ObservableTreeMap<K, P>(new TreeMap<>());
		return result;
	}
	public static<K, P>  ObservableTreeMap<K, P>  					observableTreeMap(final Map<K, P>  _initialValue) {
		ObservableTreeMap<K, P> result = new ObservableTreeMap<K, P>(_initialValue);
		return result;
	}

	public static final <K, P> ObservableTreeMap<K, P> 				observableTreeMap(final TreeMap<K, P> _initialValue) {
		ObservableTreeMap<K, P> result = new ObservableTreeMap<K, P>(_initialValue);
		return result;
	}

	public static <P> ObservablePlayList<P> 						observablePlaylist() {
		return new SimpleObservablePlayList<P>();
	}
	public static <P> ObservablePlayList<P> 						observablePlaylist(PlayList<P> _playlist) {
		return new SimpleObservablePlayList<P>(_playlist);
	}
	public static <T, P> ObservablePlayMap<T, P> 					observablePlaymap() {
		return new SimpleObservablePlayMap<T, P>();
	}
	public static <T, P> ObservablePlayMap<T, P> 					observablePlaymap(Predicate<T> _predicate, Function<T, P> _supplier) {
		return new StandardObservablePlayList<T, P>(_predicate, _supplier);
	}
	public static <T, P> ObservablePlayMap<T, P> 					observablePlaymap(Predicate<T> _predicate, Function<T, P> _supplier, Collection<T> _keys) {
		return new StandardObservablePlayList<T, P>(_predicate, _supplier, _keys);
	}

}
