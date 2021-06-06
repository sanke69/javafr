package fr.java.sdk.data.async.caches;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.java.data.DataNotFoundException;
import fr.java.data.provider.caches.DataCacheOnStorage;
import fr.java.lang.enums.AccessMode;
import fr.java.patterns.priority.DefaultPriority;
import fr.java.patterns.priority.Priority;
import fr.java.sdk.nio.file.FileObject;

public abstract class AbstractDataCacheOnStorage<COORD, TYPE> extends AbstractDataCache<COORD, TYPE> implements DataCacheOnStorage<COORD, TYPE> {
	private static final Path defaultCacheFolder = Paths.get(System.getProperty("user.home")).resolve(".javafr").resolve(".cache");

	private Path cacheFolder;

	protected AbstractDataCacheOnStorage() {
		this(defaultCacheFolder, AccessMode.ReadOnly, DefaultPriority.Highest);
	}
	protected AbstractDataCacheOnStorage(AccessMode _accessMode) {
		this(defaultCacheFolder, _accessMode);
	}
	protected AbstractDataCacheOnStorage(AccessMode _accessMode, Priority _priority) {
		this(defaultCacheFolder, _accessMode, _priority);
	}
	protected AbstractDataCacheOnStorage(Path _cacheFolder) {
		this(_cacheFolder, AccessMode.ReadOnly, DefaultPriority.Highest);
	}
	protected AbstractDataCacheOnStorage(Path _cacheFolder, AccessMode _accessMode) {
		this(_cacheFolder, _accessMode, DefaultPriority.Highest);
	}
	protected AbstractDataCacheOnStorage(Path _cacheFolder, AccessMode _accessMode, Priority _priority) {
		super(CacheType.STORAGE, _accessMode, _priority);
		cacheFolder = _cacheFolder;

		FileObject cacheFO = FileObject.of(cacheFolder);
		if(!cacheFO.isExist()) {
			System.out.println("AbstractDataCacheOnStorage:: createCacheFolder: " + cacheFolder);
			cacheFO.mkdir();
		}
	}

	@Override
	public boolean 			isAvailable() {
		return cacheFolder.toFile().isDirectory();
	}

	@Override
	public TYPE 			get(COORD _coords) throws DataNotFoundException {
		if(!contains(_coords))
			return null;
		
		return getContent( getPath(_coords) );
	}

	public void 			setCachePath(Path _cachePath) {
		cacheFolder = _cachePath;
	}
	public Path 			getCachePath() {
		return cacheFolder;
	}

	public abstract COORD 	getCoordinates(Path _file);
	public abstract TYPE	getContent(Path _file);
	public abstract Path	getPath(COORD _file);

	public abstract boolean isDataFolder(Path _folder);
	public abstract boolean isDataFile(Path _file);

}
