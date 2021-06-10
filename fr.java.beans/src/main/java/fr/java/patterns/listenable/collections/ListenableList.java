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
package fr.java.patterns.listenable.collections;

import java.util.List;
import java.util.concurrent.Executor;

import fr.java.patterns.listenable.Listenable;
import fr.java.patterns.listenable.listeners.ListListener;

@Deprecated
public interface ListenableList<T> extends Listenable<List<T>>, List<T> { //, Iterable<T>
/*
	public void addListener(Listener<List<T>> listener);
	public void addListener(Listener<List<T>> listener, Executor executor);

	public void removeListener(Listener<List<T>> listener);
*/
	public void addListener(ListListener<T> listener);
	public void addListener(ListListener<T> listener, Executor executor);

	public void removeListener(ListListener<T> listener);

}
