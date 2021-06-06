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
 * @file     ReadOnlySet.java
 * @version  0.0.0.1
 * @date     2018/04/27
 * 
**/
package fr.java.lang.collections;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ReadOnlySet<T> extends AbstractSet<T> {

	public static <U> ReadOnlySet<U> of(Set<U> _list) {
		return new ReadOnlySet<U>(_list);
	}

	private final Set<T> set;

	public ReadOnlySet(final Set<T> _list) {
		super();
		set = new HashSet<T>(_list);
	}
	@SuppressWarnings("unchecked")
	public ReadOnlySet(final T... _items) {
		super();
		set = new HashSet<T>();
	}

	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public int size() {
		return set.size();
	}

}
