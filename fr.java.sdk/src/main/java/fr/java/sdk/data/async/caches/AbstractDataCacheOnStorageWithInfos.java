package fr.java.sdk.data.async.caches;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import fr.java.lang.enums.AccessMode;
import fr.java.lang.properties.Priority;
import fr.java.sdk.nio.file.FileObject;

public abstract class AbstractDataCacheOnStorageWithInfos<COORD, TYPE> extends AbstractDataCacheOnStorage<COORD, TYPE> {

	protected AbstractDataCacheOnStorageWithInfos() {
		super();
	}
	protected AbstractDataCacheOnStorageWithInfos(AccessMode _accessMode) {
		super(_accessMode);
	}
	protected AbstractDataCacheOnStorageWithInfos(AccessMode _accessMode, Priority _priority) {
		super(_accessMode, _priority);
	}
	protected AbstractDataCacheOnStorageWithInfos(Path _cacheFolder) {
		super(_cacheFolder, AccessMode.ReadOnly, Priority.Normal);
	}
	protected AbstractDataCacheOnStorageWithInfos(Path _cacheFolder, AccessMode _accessMode) {
		super(_cacheFolder, _accessMode, Priority.Normal);
	}
	protected AbstractDataCacheOnStorageWithInfos(Path _cacheFolder, AccessMode _accessMode, Priority _priority) {
		super(_cacheFolder, _accessMode, _priority);
	}

	public void 			updateCacheInfos() {
		FileObject cacheFO = FileObject.of(getCachePath());
		if(cacheFO.isExist())
			visitSourceFolder( getCachePath() );
		else
			cacheFO.mkdir();
	}

	public abstract void 	clearCacheInfo();
	public abstract void 	addToCacheInfo(COORD _dataCoords, long _dataSize);

	private void 			visitSourceFolder(Path _directory) {
		clearCacheInfo();

		try {
			Files.walkFileTree(_directory, new CacheVisitor());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	class CacheVisitor extends SimpleFileVisitor<Path> {
		boolean dataFolder = false;

		@Override
		public FileVisitResult preVisitDirectory(Path _path, BasicFileAttributes _att) throws IOException {
			dataFolder = isDataFolder(_path.getFileName());

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path _path, IOException _e) throws IOException {
			dataFolder = false;

			Objects.requireNonNull(_path);
			if(_e != null)
				throw _e;

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path _path, BasicFileAttributes _att) throws IOException {
			if( isDataFile( _path.getFileName() ) )
				addToCacheInfo( getCoordinates( _path.getFileName() ), -1 );

			return FileVisitResult.CONTINUE;
		}

	}

}
