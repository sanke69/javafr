package fr.javafx.beans.observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class SimpleObservablePlayList<P> implements ObservablePlayList<P> {

	private final ObservableList<P> content;
	private ListIterator<P> 		current;

	protected SimpleObservablePlayList() {
		super();
		content = FXCollections.observableArrayList(new ArrayList<P>());
		current = content.listIterator();
	}
	protected SimpleObservablePlayList(List<P> _initialValue) {
		super();
		content    = FXCollections.observableArrayList(_initialValue);
		current = content.listIterator();
	}

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
		return content.contains(o);
	}
	@Override
	public boolean 				containsAll(Collection<?> c) {
		return content.containsAll(c);
	}

	@Override
	public Collection<P> 		getAll() {
		return content;
	}

	@Override
	public P 					getNext() {
		if(current.hasNext())
			return current.next();
		return null;
	}
	@Override
	public P 					getPrevious() {
		if(current.hasPrevious())
			return current.previous();
		return null;
	}
	@Override
	public P					getCurrent() {
		current.next();
		return current.previous();
	}

	@Override
	public P 					get(int index) {
		return content.get(index);
	}
	@Override
	public P 					set(int index, P element) {
		return content.set(index, element);
	}

	@Override
	public boolean 				add(P e) {
		return content.add(e);
	}
	@Override
	public void 				add(int index, P element) {
		content.add(index, element);
	}

	@Override
	public boolean 				remove(Object o) {
		return content.remove(o);
	}
	@Override
	public P 					remove(int index) {
		return content.remove(index);
	}
	@Override
	public void 				remove(int from, int to) {
		for(int i = from; i < to; ++i)
			content.remove(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean 				addAll(P... elements) {
		return content.addAll(Arrays.asList(elements));
	}
	@Override
	public boolean 				addAll(Collection<? extends P> c) {
		return content.addAll(c);
	}
	@Override
	public boolean 				addAll(int index, Collection<? extends P> c) {
		return content.addAll(index, c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean 				setAll(P... elements) {
		content.clear();
		return content.addAll(Arrays.asList(elements));
	}
	@Override
	public boolean 				setAll(Collection<? extends P> c) {
		content.clear();
		return content.addAll(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean 				removeAll(P... elements) {
		return content.removeAll(Arrays.asList(elements));
	}
	@Override
	public boolean 				removeAll(Collection<?> c) {
		return content.removeAll(c);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean 				retainAll(P... elements) {
		return content.retainAll(Arrays.asList(elements));
	}
	@Override
	public boolean 				retainAll(Collection<?> c) {
		return content.retainAll(c);
	}

	@Override
	public int 					indexOf(Object o) {
		return content.indexOf(o);
	}
	@Override
	public int 					lastIndexOf(Object o) {
		return content.lastIndexOf(o);
	}

	@Override
	public Iterator<P> 			iterator() {
		return content.iterator();
	}
	@Override
	public ListIterator<P> 		listIterator() {
		return content.listIterator();
	}
	@Override
	public ListIterator<P> 		listIterator(int index) {
		return content.listIterator(index);
	}

	@Override
	public List<P> 				subList(int fromIndex, int toIndex) {
		return content.subList(fromIndex, toIndex);
	}

	@Override
	public void 				clear() {
		content.clear();
	}

	@Override
	public Object[] 			toArray() {
		return content.toArray();
	}
	@Override
	public <U> U[] 				toArray(U[] a) {
		return content.toArray(a);
	}

	@Override
	public void 				addListener(ListChangeListener<? super P> listener) {
		content.addListener(listener);
	}
	@Override
	public void 				removeListener(ListChangeListener<? super P> listener) {
		content.removeListener(listener);
	}

	@Override
	public void 				addListener(InvalidationListener listener) {
		content.addListener(listener);
	}
	@Override
	public void 				removeListener(InvalidationListener listener) {
		content.removeListener(listener);
	}

}
