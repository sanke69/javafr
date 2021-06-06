package fr.java.lang.collections;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class ReadOnlyCollection<T> extends AbstractCollection<T> {

	public static <U> ReadOnlyCollection<U> of(Collection<U> _collection) {
		return new ReadOnlyCollection<U>(_collection);
	}
    @SafeVarargs
	public static <U> ReadOnlyCollection<U> of(U... _items) {
		return new ReadOnlyCollection<U>(Arrays.asList(_items));
	}

	private final Collection<T> collection;

	public ReadOnlyCollection(Collection<T> _collection) {
		super();
		collection = _collection;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}

    public boolean add(T e) 							{ throw new UnsupportedOperationException(); }
    public boolean addAll(Collection<? extends T> c) 	{ throw new UnsupportedOperationException(); }
    public boolean remove(Object o) 					{ throw new UnsupportedOperationException(); }
    public boolean removeAll(Collection<?> c) 			{ throw new UnsupportedOperationException(); }
    public boolean retainAll(Collection<?> c) 			{ throw new UnsupportedOperationException(); }
    public void    clear() 								{ throw new UnsupportedOperationException(); }

}
