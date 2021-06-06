package fr.java.data.provider.caches;

import fr.java.data.provider.DataCache;

//aka MemoryCache
public interface DataCacheInMemory<COORD, TYPE> extends DataCache<COORD, TYPE> {

	@Override
	public default CacheType getCacheType() {
		return CacheType.MEMORY;
	}

	public long getCapacity();		// return in octets
	public long getAvailable();		// return in octets

	public void addToCache(final COORD _coords, final TYPE _data);

	public void clear();
	
}
