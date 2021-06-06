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
