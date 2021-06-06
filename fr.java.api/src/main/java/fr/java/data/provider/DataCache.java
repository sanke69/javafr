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
