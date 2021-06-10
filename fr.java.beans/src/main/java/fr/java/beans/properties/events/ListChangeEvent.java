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
import java.util.Collections;
import java.util.List;

import fr.java.lang.collections.ReadOnlyList;

public class ListChangeEvent<E> extends PropertyChangeEvent {
	private static final long serialVersionUID = -2559099143248427444L;

	public abstract static class Change<E> {
        private final List<E> list;

        public abstract boolean next();

        public abstract void reset();

        public Change(List<E> _list) {
            list = _list;
        }

        public ReadOnlyList<E> getList() {
            return ReadOnlyList.of(list);
        }

        public abstract int getFrom();

        public abstract int getTo();

        public abstract List<E> getRemoved();

        public boolean wasPermutated() {
            return getPermutation().length != 0;
        }

        public boolean wasAdded() {
            return !wasPermutated() && !wasUpdated() && getFrom() < getTo();
        }

        public boolean wasRemoved() {
            return !getRemoved().isEmpty();
        }

        public boolean wasReplaced() {
            return wasAdded() && wasRemoved();
        }

        public boolean wasUpdated() {
            return false;
        }

        public List<E> getAddedSubList() {
            return wasAdded()? getList().subList(getFrom(), getTo()) : Collections.<E>emptyList();
        }

        public int getRemovedSize() {
            return getRemoved().size();
        }

        public int getAddedSize() {
            return wasAdded() ? getTo() - getFrom() : 0;
        }

        protected abstract int[] getPermutation();

        public int getPermutation(int i) {
            if (!wasPermutated()) {
                throw new IllegalStateException("Not a permutation change");
            }
            return getPermutation()[i - getFrom()];
        }

    }

	Change<E> changes;

	public ListChangeEvent(Object source, String propertyName, Change<E> _changes) {
		super(source, propertyName, null, null);
		changes = _changes;
	}
	
	public Change<E> getChanges() {
		return changes;
	}

}
