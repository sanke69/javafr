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
package fr.java.beans.impl.events;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

import fr.java.lang.collections.ReadOnlyCollection;

public class CollectionPropertyChangeEvent<E> extends PropertyChangeEvent {
	private static final long serialVersionUID = -2559099143248427444L;

	 public static abstract class Change<E> {
        private final Collection<E> collection;

        public Change(Collection<E> _set) {
            collection = _set;
        }

        public ReadOnlyCollection<E> 			getCollection() 		{ return ReadOnlyCollection.of(collection); }

        public boolean 							wasAdded() 				{ return getAdded() != null && !getAdded().isEmpty(); }
        public abstract ReadOnlyCollection<E> 	getAdded();

        public boolean 							wasRemoved() 			{ return getRemoved() != null && !getRemoved().isEmpty(); }
        public abstract ReadOnlyCollection<E> 	getRemoved();

    }

	Change<E> changes;

	public CollectionPropertyChangeEvent(Object source, String propertyName, Change<E> _changes) {
		super(source, propertyName, null, null);
		changes = _changes;
	}
	
	public Change<E> getChanges() {
		return changes;
	}

}
