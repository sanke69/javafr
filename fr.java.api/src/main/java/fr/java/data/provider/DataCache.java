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
package fr.java.data.provider;

import java.util.Comparator;

import fr.java.data.DataException;
import fr.java.lang.enums.AccessMode;
import fr.java.patterns.priority.Priority;

public interface DataCache<COORD, TYPE> extends Comparable<Object> {
	public static final Comparator<? super DataCache<?, ?>> comparator = (_c1, _c2) -> - _c1.getCachePriority().compareTo( _c2.getCachePriority() );

	public static enum 	CacheType {
		NONE, MEMORY, STORAGE, DATABASE, INTERNET;
	}

	public AccessMode 	getCacheAccess();
	public CacheType 	getCacheType();
	public Priority 	getCachePriority();
	
	public boolean 		isAvailable();

	public boolean 		contains	(final COORD _coords);
	public TYPE			get			(final COORD _coords) throws DataException;

}
