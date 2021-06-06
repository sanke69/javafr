package fr.java.sdk.data.async.caches.cleaners;

public class DataCacheOnStorageCleaner {

	/*
	private static final Logger	log = Logger.getLogger(TileCacheCleaner.class.getName());

	private static TileCacheCleaner	instance	= new TileCacheCleaner();

	public static final long	DEFAULT_SIZE	= 2000 * 1024 * 1024;
	public static final long	UNLIMITED_SIZE	= -1;

	protected long maximumCacheSize = UNLIMITED_SIZE; // DEFAULT_SIZE;

	private boolean							running	= false;
	private Comparator<FileXWrapper>	comparator;

	protected TileCacheCleaner() {
		comparator = (FileXWrapper file1, FileXWrapper file2) -> (int) (file2.getLastModifiedTime() - file1.getLastModifiedTime());
	}

	public static TileCacheCleaner getInstance() {
		return instance;
	}

	protected FileX getRootDir() {
		return TileCacheManager.getInstance().getCacheFolder();
	}

	public void setMaximumCacheSize(long maximumCacheSize) {
		long oldSize = this.maximumCacheSize;
		this.maximumCacheSize = maximumCacheSize;
		if((maximumCacheSize < oldSize && !isUnlimited()) || (oldSize == UNLIMITED_SIZE && !isUnlimited()))
			run();
	}

	public boolean isUnlimited() {
		return maximumCacheSize == UNLIMITED_SIZE;
	}

	private List<FileX> findCacheFiles(FileX root) {
		List<FileX> files = new ArrayList<>();
		findCacheFiles(root, files);
		return files;
	}
	private void findCacheFiles(FileX file, List<FileX> files) {
		if(file != null) {
			if(file.isDirectory()) {
				for(FileX child : file.asDirectory().ls())
					findCacheFiles(child, files);
			} else {
				files.add(file);
			}
		}
	}

	private List<FileX> getFilesToDelete(List<FileXWrapper> files) {
		long sizeSummary = 0;
		List<FileX> filesToDelete = new ArrayList<>();
		for(FileXWrapper file : files) {
			FileX.Regular fileObject = file.getFileObject();
			sizeSummary += fileObject.getSize();
			if(sizeSummary > maximumCacheSize)
				filesToDelete.add(fileObject);
		}
		return filesToDelete;
	}
	private void deleteFiles(List<FileXWrapper> files) {
		List<FileX> filesToDelete = getFilesToDelete(files);
		filesToDelete.stream().forEach((file) -> safelyDelete(file));
	}

	private void safelyDelete(FileX file) {
		try {
			file.rm();
		} catch(Throwable t) {
			log.log(Level.FINER, "unable to delete cache file {0}, exception {1}", new Object[] { file.toPath(), t.getMessage() });
		}
	}

	public void run() {
		if(running || isUnlimited())
			return;

		Thread thread = new Thread(() -> {
			running = true;
			try {
				List<FileX> files = findCacheFiles(getRootDir());

				List<FileXWrapper> fixDateFiles = new ArrayList<>();
				for(FileX fileObject : files) {
					FileXWrapper wrapper = new FileXWrapper(fileObject.getLastModifiedTime(), fileObject.asRegularFile());
					fixDateFiles.add(wrapper);
				}
				Collections.sort(fixDateFiles, comparator);

				deleteFiles(fixDateFiles);
			} catch(Throwable t) {
				// noop
				// no exception should be thrown, but just in case :)
			} finally {
				running = false;
			}
		});
		thread.setName("Cache deleting thread");
		thread.start();
	}

	private static class FileXWrapper {

		long			lastModifiedTime;
		FileX.Regular	fileObject;

		public FileXWrapper(long lastModifiedTime, FileX.Regular fileObject) {
			super();
			this.lastModifiedTime = lastModifiedTime;
			this.fileObject = fileObject;
		}

		public long getLastModifiedTime() {
			return lastModifiedTime;
		}

		public FileX.Regular getFileObject() {
			return fileObject;
		}

	}
	*/
}
