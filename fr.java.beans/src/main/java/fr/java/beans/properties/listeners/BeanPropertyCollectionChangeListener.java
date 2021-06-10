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
package fr.java.beans.properties.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.java.beans.impl.events.CollectionPropertyChangeEvent;

@FunctionalInterface
public interface BeanPropertyCollectionChangeListener<T> extends PropertyChangeListener {

    @SuppressWarnings("unchecked")
	default void propertyChange(PropertyChangeEvent evt) {
    	if(evt instanceof CollectionPropertyChangeEvent)
    		propertyChange((CollectionPropertyChangeEvent<T>) evt);
    }

	default void propertyChange(CollectionPropertyChangeEvent<T> evt) {
		Object property = evt.getSource();
    	propertyChange(property, evt.getChanges());
    }

    void propertyChange(Object _property, CollectionPropertyChangeEvent.Change<T> _changes);

}
