/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
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
