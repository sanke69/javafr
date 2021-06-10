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
package fr.java.beans.properties.events;

import java.beans.PropertyChangeEvent;
import java.util.Set;

import fr.java.lang.collections.ReadOnlySet;

public class SetChangeEvent<E> extends PropertyChangeEvent {
	private static final long serialVersionUID = -2559099143248427444L;

	public static abstract class Change<E> {
        private final Set<E> set;

        public Change(Set<E> _set) {
            set = _set;
        }

        public ReadOnlySet<E> 	getSet() 				{ return ReadOnlySet.of(set); }

        public boolean 			wasAdded() 				{ return getElementAdded() != null; }
        public abstract E 		getElementAdded();

        public boolean 			wasRemoved() 			{ return getElementRemoved() != null; }
        public abstract E 		getElementRemoved();

    }

	Change<E> changes;

	public SetChangeEvent(Object source, String propertyName, Change<E> _changes) {
		super(source, propertyName, null, null);
		changes = _changes;
	}
	
	public Change<E> getChanges() {
		return changes;
	}

}
