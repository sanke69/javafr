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
package fr.java.data;

import java.util.function.Consumer;

import fr.java.lang.properties.Priority;
import fr.java.patterns.capabilities.Loadable;

public interface Data<COORD, TYPE> {

	public interface Async<COORD, TYPE> extends Data<COORD, TYPE>, Loadable {

		// Indicates if collected data is  stable enough in time to be stored locally
		public default boolean  isPersistent() { return true; }

		public Priority 		getPriority();

		public void 			addUniqueLoadedListener(Consumer<TYPE> _consumer);
		@Deprecated
		public void 			fireLoadedEvent(final TYPE _value);

	}

	public COORD 			getCoordinates();
	public TYPE  			getContent();//TODO:: throws Exception;

}
