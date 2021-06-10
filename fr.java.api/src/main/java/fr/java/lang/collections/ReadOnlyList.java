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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class ReadOnlyList<T> extends AbstractList<T> {

	public static <U> ReadOnlyList<U> of(List<U> _list) {
		return new ReadOnlyList<U>(_list);
	}
	public static <U> ReadOnlyList<U> of(Set<U> _set) {
		return new ReadOnlyList<U>(_set);
	}
	@SuppressWarnings("unchecked")
	public static <U> ReadOnlyList<U> of(U... _items) {
		return new ReadOnlyList<U>(Arrays.asList(_items));
	}

	private final List<T> list;

	public ReadOnlyList(final List<T> _list) {
		super();
		list = new ArrayList<T>(_list);
	}
	public ReadOnlyList(final Set<T> _set) {
		super();
		list = new ArrayList<T>(_set);
	}
	@SuppressWarnings("unchecked")
	public ReadOnlyList(final T... _items) {
		super();
		list = Arrays.asList(_items);
	}

	@Override
	public T get(int _i) {
		return list.get(_i);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override public void 		clear() 										{ throw new UnsupportedOperationException(); }

	@Override public final T 	set(int index, T element) 						{ throw new UnsupportedOperationException(); }
	@Override public final void add(int index, T element) 						{ throw new UnsupportedOperationException(); }
	@Override public final T 	remove(int index) 								{ throw new UnsupportedOperationException(); }

	@Override public boolean 	addAll(int index, Collection<? extends T> c) 	{ throw new UnsupportedOperationException(); }
	@Override protected void 	removeRange(int fromIndex, int toIndex) 		{ throw new UnsupportedOperationException(); }

}