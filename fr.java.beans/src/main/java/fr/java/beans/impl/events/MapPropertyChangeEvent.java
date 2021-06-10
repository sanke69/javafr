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
import java.util.Map;

import fr.java.lang.collections.ReadOnlyMap;

public class MapPropertyChangeEvent<K, V> extends PropertyChangeEvent {
	private static final long serialVersionUID = 1541556405068561792L;

	public static abstract class Change<K, V> {

        private final Map<K,V> map;

        public Change(Map<K, V> _map) {
            map = _map;
        }

        public ReadOnlyMap<K, V> getMap() {
            return ReadOnlyMap.of(map);
        }

        public abstract boolean wasAdded();

        public abstract boolean wasRemoved();

        public abstract K getKey();

        public abstract V getValueAdded();

        public abstract V getValueRemoved();

    }

	Change<K, V> change;

	public MapPropertyChangeEvent(Object source, String propertyName, Change<K, V> _change) {
		super(source, propertyName, null, null);
		change = _change;
	}

	public Change<K, V> getChange() {
		return change;
	}

}
