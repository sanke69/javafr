package fr.java.sdk.data.async.caches;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

import fr.java.data.DataNotFoundException;
import fr.java.data.provider.caches.DataCacheInMemory;
import fr.java.lang.enums.AccessMode;
import fr.java.patterns.priority.DefaultPriority;

public class DefaultDataCacheInMemory<COORD, TYPE> extends AbstractDataCache<COORD, TYPE> implements DataCacheInMemory<COORD, TYPE> {
	private static final long			defaultCacheSize	 = 50 * 1024 * 1024;
	private long						cacheCapacity, 
										cacheOccupancy	     = 0;

	private final Function<TYPE, Long>	typeSizeInBytes;
	
	private final Map<COORD, TYPE> 		cachedData 			 = new HashMap<>();
	private LinkedList<COORD>			cacheLastAccessQueue = new LinkedList<>();

	public DefaultDataCacheInMemory(Function<TYPE, Long> _typeSizeInBytes) {
		this(defaultCacheSize, _typeSizeInBytes);
	}
	public DefaultDataCacheInMemory(long _capacity, Function<TYPE, Long> _typeSizeInBytes) {
		super(CacheType.MEMORY, AccessMode.ReadWrite, DefaultPriority.Highest);
		cacheCapacity   = _capacity;
		typeSizeInBytes = _typeSizeInBytes;
	}

	@Override
	public boolean 	isAvailable() {
		return true;
	}

	@Override
	public long 	getCapacity() {
		return cacheCapacity;
	}
	@Override
	public long 	getAvailable() {
		return cacheCapacity - cacheOccupancy;
	}

	@Override
	public boolean 	contains(COORD _coords) {
		return cachedData.containsKey(_coords);
	}
	@Override
	public TYPE 	get(COORD _coords) throws DataNotFoundException {
		synchronized (cachedData) {
			if(cachedData.containsKey(_coords)) {
				System.out.println("Found in Memory Cache " + _coords + " " + cachedData.get(_coords));

				cacheLastAccessQueue.remove(_coords);
				cacheLastAccessQueue.addLast(_coords);
				return cachedData.get(_coords);
			}
		}
		throw new DataNotFoundException();
	}

	@Override
	public void 	addToCache(COORD _coords, TYPE _data) {
		synchronized (cachedData) {
			if(cacheOccupancy > cacheCapacity)
				clear();

			cachedData.put(_coords, _data);
			cacheOccupancy += typeSizeInBytes.apply(_data);
			cacheLastAccessQueue.addLast(_coords);
		}
	}

	public void 	clear() {
		clear(false);
	}
	public void 	clear(boolean _clearAll) {
		if(_clearAll) {
			clearAll();
		} else {
			clearPercent(.75f);
		}
	}

	public void 	clearAll() {
		cachedData.clear();
		cacheLastAccessQueue.clear();
		cacheOccupancy = 0;
	}
	public void 	clearPercent(float _percent) {
		int retryCounter = 10; // 10 attempts and stop
		while(cacheOccupancy > _percent * cacheCapacity && retryCounter >= 0) {
			COORD   old_coords  = cacheLastAccessQueue.removeFirst();
			TYPE 	old_content = cachedData.remove(old_coords);
			if(old_content != null)
				cacheOccupancy -= typeSizeInBytes.apply(old_content);
			retryCounter--;
		}
	}

}
