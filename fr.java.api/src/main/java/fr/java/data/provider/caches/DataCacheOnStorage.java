package fr.java.data.provider.caches;

import java.io.IOException;

import fr.java.data.DataException;
import fr.java.data.provider.DataCache;

public interface DataCacheOnStorage<COORD, TYPE> extends DataCache<COORD, TYPE> {

	@Override
	public default CacheType getCacheType() {
		return CacheType.STORAGE;
	}

	public void addToStorage(final COORD _coords, final TYPE _data) throws DataException, IOException;

}
