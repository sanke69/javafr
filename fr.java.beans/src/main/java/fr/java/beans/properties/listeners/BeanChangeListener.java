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

@FunctionalInterface
public interface BeanChangeListener<T> extends PropertyChangeListener {

    @SuppressWarnings("unchecked")
	default void propertyChange(PropertyChangeEvent evt) {
    	propertyChange(evt.getSource(), evt.getPropertyName(), (T) evt.getOldValue(), (T) evt.getNewValue());
    }

    void propertyChange(Object _bean, String _propertyName, T _oldValue, T _newValue);

}
