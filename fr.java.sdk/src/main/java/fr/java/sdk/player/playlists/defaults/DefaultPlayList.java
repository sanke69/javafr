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
 * @file     SimplePlayableList.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.sdk.player.playlists.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.java.player.PlayList;

public class DefaultPlayList<P> implements PlayList<P> {
	List<P>         playlist;
	ListIterator<P> current;
	
	public DefaultPlayList() {
		super();
	}
	public DefaultPlayList(List<P> _playables) {
		super();
		playlist = new ArrayList<P>(_playables);
		current  = playlist.listIterator();
	}
	public DefaultPlayList(Collection<P> _playables) {
		super();
		playlist = new ArrayList<P>(_playables);
		current  = playlist.listIterator();
	}
	
	@Override
	public P 				getNext() {
		if(current.hasNext())
			return current.next();
		return null;
	}

	@Override
	public P 				getPrevious() {
		if(current.hasPrevious())
			return current.previous();
		return null;
	}

	@Override
	public P				getCurrent() {
		current.next();
		return current.previous();
	}

	@Override
	public List<P> 			getAll() {
		return playlist;
	}

	public void 			addPlayable(P _playable) {
		playlist.add(_playable);
	}
	public void 			removePlayable(P _playable) {
		playlist.remove(_playable);
	}

	// List<?> Implementation
	@Override
	public boolean 			isEmpty() {
		return playlist.isEmpty();
	}

	@Override
	public int 				size() {
		return playlist.size();
	}

	@Override
	public boolean 			contains(Object o) {
		return playlist.contains(o);
	}
	@Override
	public boolean 			containsAll(Collection<?> c) {
		return playlist.containsAll(c);
	}

	@Override
	public Iterator<P> 		iterator() {
		return playlist.iterator();
	}

	@Override
	public Object[] 		toArray() {
		return playlist.toArray();
	}
	@Override
	public <T> T[] 			toArray(T[] a) {
		return playlist.toArray(a);
	}

	@Override
	public boolean 			add(P e) {
		return playlist.add(e);
	}
	@Override
	public boolean 			remove(Object o) {
		return playlist.remove(o);
	}

	@Override
	public boolean 			addAll(Collection<? extends P> c) {
		return playlist.addAll(c);
	}
	@Override
	public boolean 			addAll(int index, Collection<? extends P> c) {
		return playlist.addAll(index, c);
	}
	@Override
	public boolean 			removeAll(Collection<?> c) {
		return playlist.removeAll(c);
	}
	@Override
	public boolean 			retainAll(Collection<?> c) {
		return playlist.retainAll(c);
	}

	@Override
	public void 			clear() {
		playlist.clear();
	}

	@Override
	public P 				get(int index) {
		return playlist.get(index);
	}
	@Override
	public P 				set(int index, P element) {
		return playlist.set(index, element);
	}

	@Override
	public void 			add(int index, P element) {
		playlist.add(index, element);
	}
	@Override
	public P 				remove(int index) {
		return playlist.remove(index);
	}

	@Override
	public int 				indexOf(Object o) {
		return playlist.indexOf(o);
	}
	@Override
	public int 				lastIndexOf(Object o) {
		return playlist.lastIndexOf(o);
	}

	@Override
	public ListIterator<P> 	listIterator() {
		return playlist.listIterator();
	}
	@Override
	public ListIterator<P> 	listIterator(int index) {
		return playlist.listIterator(index);
	}

	@Override
	public List<P> 			subList(int fromIndex, int toIndex) {
		return playlist.subList(fromIndex, toIndex);
	}

}
