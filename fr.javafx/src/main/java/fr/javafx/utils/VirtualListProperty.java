package fr.javafx.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class VirtualListProperty<T> extends SimpleListProperty<T> implements ObservableList<T> {

	final ListProperty<ObservableList<T>> 	bindings;
	final ListProperty<T>                 	delegate;

	final ListChangeListener<? extends T> 	updater;

	public static <T> ObservableList<T> concat(ObservableList<T>... _lists) {
		VirtualListProperty<T> aggregate = new VirtualListProperty<T>();
		aggregate.addToAggregation(_lists);
		
		return aggregate;
	}
	
	public VirtualListProperty() {
		super();
		bindings = new SimpleListProperty<ObservableList<T>>(FXCollections.observableArrayList());
		delegate = new SimpleListProperty<T>(FXCollections.observableArrayList());
		updater  = c -> {
			while(c.next()) {
				if(c.wasReplaced())
					System.err.println("NTIA");
				else if(c.wasAdded())
					delegate.addAll(c.getAddedSubList());
				else if(c.wasRemoved())
					delegate.removeAll(c.getRemoved());
				else
					System.err.println("NTIA"); // NOT Take Into Account
			}
		};
	}
	public VirtualListProperty(ObservableList<T>... _lists) {
		this();
		addToAggregation(_lists);
	}

	public void addToAggregation(ObservableList<T> _list) {
		_list.addListener((ListChangeListener<? super T>) updater);

		delegate.addAll(_list);

		bindings.add(_list);
	}
	public void addToAggregation(ObservableList<T>... _lists) {
		for(ObservableList<T> list : _lists) {
			list.addListener((ListChangeListener<? super T>) updater);

			delegate.addAll(list);

			bindings.add(list);
		}
	}
	public void removeFromAggregation(ObservableList<T> _list) {
		_list.removeListener((ListChangeListener<? super T>) updater);

		delegate.removeAll(_list);

		bindings.remove(_list);
	}
	public void removeFromAggregation(ObservableList<T>... _lists) {
		for(ObservableList<T> list : _lists) {
			list.removeListener((ListChangeListener<? super T>) updater);

			delegate.removeAll(list);

			bindings.remove(list);
		}
	}

	public String toString() {
		return delegate.toString();
	}
	
	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(bindings);
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return delegate.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return delegate.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		delegate.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		delegate.removeListener(listener);
	}

	@Override
	public void addListener(ListChangeListener<? super T> listener) {
		delegate.addListener(listener);
	}

	@Override
	public void removeListener(ListChangeListener<? super T> listener) {
		delegate.removeListener(listener);
	}

	//< NEXT throws new IllegalAccessError >
	
	@Override
	public T set(int index, T element) { throw new IllegalAccessError(); }

	@Override
	public void add(int index, T element) { throw new IllegalAccessError(); }

	@Override
	public T remove(int index) { throw new IllegalAccessError(); }

	@Override
	public boolean add(T e) { throw new IllegalAccessError(); }

	@Override
	public boolean remove(Object o) { throw new IllegalAccessError(); }

	@Override
	public boolean addAll(Collection<? extends T> c) { throw new IllegalAccessError(); }

	@Override
	public boolean addAll(int index, Collection<? extends T> c) { throw new IllegalAccessError(); }

	@Override
	public boolean removeAll(Collection<?> c) { throw new IllegalAccessError(); }

	@Override
	public boolean retainAll(Collection<?> c) { throw new IllegalAccessError(); }

	@Override
	public void clear() { throw new IllegalAccessError(); }

	@Override
	public boolean addAll(T... elements) { throw new IllegalAccessError(); }

	@Override
	public boolean setAll(T... elements) { throw new IllegalAccessError(); }

	@Override
	public boolean setAll(Collection<? extends T> col) { throw new IllegalAccessError(); }

	@Override
	public boolean removeAll(T... elements) { throw new IllegalAccessError(); }

	@Override
	public boolean retainAll(T... elements) { throw new IllegalAccessError(); }

	@Override
	public void remove(int from, int to) { throw new IllegalAccessError(); }

}