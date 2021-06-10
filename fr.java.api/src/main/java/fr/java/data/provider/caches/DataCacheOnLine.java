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
package fr.java.data.provider.caches;

import fr.java.data.provider.DataCache;
import fr.java.lang.enums.AccessMode;

public interface DataCacheOnLine<COORD, TYPE> extends DataCache<COORD, TYPE> {

	@Override
	public default CacheType 	getCacheType() {
		return CacheType.INTERNET;
	}
	@Override
	public default AccessMode 	getCacheAccess() {
		return AccessMode.ReadOnly;
	}

	@Override
	public default boolean 		isAvailable() {
		return isServerOnline();
	}

	public boolean 				isServerOnline();

	@Override
	public default boolean 		contains(final COORD _coords) {
		// As we can't know this information, always return true !
		return true;
	}

//	public default void addToCache(final DataCoordinate _coords, final T _data) { throw new UnsupportedOperationException(); }

}
