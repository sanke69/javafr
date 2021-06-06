package fr.javafx.beans.observable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;

public class SimpleObservablePlayMap<T, P> implements ObservablePlayMap<T, P> {

	protected final ObservableTreeMap<T, P>	content;
	protected Map.Entry<T, P> 				current, previous, next;

	public SimpleObservablePlayMap() {
		super();
		content = MyFXCollections.observableTreeMap();

		__setCurrentAtBeginning();
	}
	public SimpleObservablePlayMap(Map<T, P> _playables) {
		super();
		content = MyFXCollections.observableTreeMap(_playables);

		__setCurrentAtBeginning();
	}

	@Override
	public P 					getPlayable(T _key) {
		return content.get(_key);
	}

	@Override
	public Collection<P> 		getAll() {
		return content.values();
	}

	@Override
	public P 					getCurrent() {
		if(current == null)
			__setCurrentAtBeginning();

		return current.getValue();
	}

	@Override
	public P 					getPrevious() {
		if(previous != null) {
			__setCurrent(previous);
			return current.getValue();
		}
		return null;
	}
	@Override
	public P 					getNext() {
		if(next != null) {
			__setCurrent(next);
			return current.getValue();
		}
		return null;
	}

	protected void				addPlayable(T _key, P _playable) {
		content.put(_key, _playable);
//		__setCurrent(current);
	}

	private void 				__setCurrentAtBeginning() {
		if(content.isEmpty())
			return ;
		current  = content.entrySet().iterator().next();
		previous = content.lowerEntry(current.getKey());
		next     = content.higherEntry(current.getKey());
	}
	private void 				__setCurrent(Map.Entry<T, P> _current) {
		current  = _current;
		previous = content.lowerEntry(current.getKey());
		next     = content.higherEntry(current.getKey());
	}

	// List<?> Implementation
	@Override
	public int 					size() {
		return content.size();
	}
	@Override
	public boolean 				isEmpty() {
		return content.isEmpty();
	}

	@Override
	public boolean 				contains(Object o) {
		throw new IllegalAccessError();
	}
	public boolean 				containsKey(T _key) {
		return content.containsKey(_key);
	}
	public boolean 				containsValue(P _playable) {
		return content.containsValue(_playable);
	}
	@Override
	public boolean 				containsAll(Collection<?> c) {
		throw new IllegalAccessError();
	}
	public boolean 				containsKeys(Collection<T> _keys) {
		for(T k : _keys)
			if(!content.containsKey(k))
				return false;
		return true;
	}
	public boolean 				containsValues(Collection<P> _playables) {
		for(P p : _playables)
			if(!content.containsValue(p))
				return false;
		return true;
	}

	@Override
	public Iterator<P> 			iterator() {
		return null;
	}

	@Override
	public Object[] 			toArray() {
		return null;
	}
	@Override
	public <U> U[] 				toArray(U[] a) {
		return null;
	}

	@Override
	public boolean 				add(P e) {
		throw new IllegalAccessError();
	}
	public boolean 				add(T _key, P _playable) {
		content.put(_key, _playable);
		return true;
	}
	@Override
	public boolean 				remove(Object o) {
		return false;
	}
	public boolean 				removeFromKey(T _key) {
		content.remove(_key);
		return false;
	}
	public boolean 				removeFromValue(P _playable) {
		for(Map.Entry<T, P> e : content.entrySet())
			if(e.getValue().equals(_playable))
				content.remove(e.getKey());
		return true;
	}

	@Override
	public boolean 				addAll(Collection<? extends P> c) {
		throw new IllegalAccessError();
	}
	@Override
	public boolean 				addAll(int index, Collection<? extends P> c) {
		throw new IllegalAccessError();
	}
	public boolean 				addAll(List<T> _keys, List<? extends P> _playables) {
		assert(_keys.size() == _playables.size());

		boolean changed = false;

		for(int i = 0; i < _keys.size(); ++i) {
			addPlayable(_keys.get(i), _playables.get(i));
			changed = true;
		}

		return changed;
	}
	@Override
	public boolean 				removeAll(Collection<?> c) {
		throw new IllegalAccessError();
	}
	@Override
	public boolean 				retainAll(Collection<?> c) {
		throw new IllegalAccessError();
	}

	@Override
	public void 				clear() {
		content.clear();
	}

	@Override
	public P 					get(int index) {
		throw new IllegalAccessError();
	}
	@Override
	public P 					set(int index, P element) {
		throw new IllegalAccessError();
	}

	@Override
	public void 				add(int index, P element) {
		throw new IllegalAccessError();
	}
	@Override
	public P 					remove(int index) {
		throw new IllegalAccessError();
	}

	@Override
	public int 					indexOf(Object o) {
		throw new IllegalAccessError();
	}
	@Override
	public int 					lastIndexOf(Object o) {
		throw new IllegalAccessError();
	}

	@Override
	public ListIterator<P> 		listIterator() {
		throw new IllegalAccessError();
	}
	@Override
	public ListIterator<P> 		listIterator(int index) {
		throw new IllegalAccessError();
	}

	@Override
	public List<P> 				subList(int fromIndex, int toIndex) {
		throw new IllegalAccessError();
	}

	@Override
	public void 				addListener(InvalidationListener listener) {
		content.addListener(listener);
	}
	@Override
	public void 				removeListener(InvalidationListener listener) {
		content.removeListener(listener);
	}
	@Override
	public void 				addListener(MapChangeListener<? super T, ? super P> listener) {
		content.addListener(listener);
	}
	@Override
	public void 				removeListener(MapChangeListener<? super T, ? super P> listener) {
		content.removeListener(listener);
	}

	protected ObservableTreeMap<T, P>	getBackedMap() {
		return content;
	}
	
}
