package fr.java.sdk.data.async.caches;

import fr.java.data.provider.DataCache;
import fr.java.lang.enums.AccessMode;
import fr.java.lang.properties.Priority;

public abstract class AbstractDataCache<COORD, TYPE> implements DataCache<COORD, TYPE> {
	private final CacheType  type;
	private final AccessMode accessMode;
	private final Priority 	 priority;

	public AbstractDataCache(CacheType _type) {
		this(_type, AccessMode.ReadOnly, Priority.Normal);
	}
	public AbstractDataCache(CacheType _type, AccessMode _accessMode) {
		this(_type, _accessMode, Priority.Normal);
	}
	public AbstractDataCache(CacheType _type, AccessMode _accessMode, Priority _priority) {
		super();
		type       = _type;
		accessMode = _accessMode;
		priority   = _priority;
	}

	@Override
	public CacheType 	getCacheType() {
		return type;
	}
	@Override
	public AccessMode 	getCacheAccess() {
		return accessMode;
	}
	@Override
	public Priority 	getCachePriority() {
		return priority;
	}

	@Override
	public int 			compareTo(Object o) {
		return 0;
	}

}
