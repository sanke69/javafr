package fr.java.sdk.data.async;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

import fr.java.data.Data;
import fr.java.data.DataException;
import fr.java.data.DataNotFoundException;
import fr.java.data.provider.DataCache;
import fr.java.data.provider.DataCache.CacheType;
import fr.java.data.provider.DataProvider;
import fr.java.data.provider.caches.DataCacheInMemory;
import fr.java.data.provider.caches.DataCacheOnStorage;

public class AsyncDataProvider<COORD, TYPE> implements DataProvider.Async<COORD, TYPE> { private static final boolean debug = true;
	List<DataCache<COORD, TYPE>>						caches;
	Map<COORD, SoftReference<Data.Async<COORD, TYPE>>> 	cachedData;

	CacheWorkerManager									workers;

	public AsyncDataProvider() { // TODO:: less instances...
		super();
		caches     = new ArrayList<DataCache<COORD, TYPE>>();
		workers    = new CacheWorkerManager();
		
		cachedData = new HashMap<COORD, SoftReference<Data.Async<COORD, TYPE>>>();
	}

	@Override
	public Data.Async<COORD, TYPE> 				get(COORD _coords) {
		Data.Async<COORD, TYPE> data = null;

		if(cachedData.containsKey(_coords)) {
			data = cachedData.get(_coords).get();

			if(data != null)
				return data;
			else
				cachedData.remove(_coords);
		}

		data = new AsyncData<COORD, TYPE>(_coords, this);
		cachedData.put(_coords, new SoftReference<Data.Async<COORD,TYPE>>(data));
		return data;
	}
	@Override
	public void 								startLoading(Data.Async<COORD, TYPE> _data) {
		workers.createNewRequest(_data, caches.iterator());
	}

	/** CACHES **/
	public void 								registerNewCache(String _name, DataCache<COORD, TYPE> _cache) {
		if(_cache instanceof DataCacheInMemory) {
			if(getMemoryCache() != null) {
				System.err.println("Already have a MemoryCache !!!");
				return ;
			}
		}
		if(_cache instanceof DataCacheOnStorage) {
			if(getStorageCache() != null) {
				System.err.println("Already have a StorageCache !!!");
				return ;
			}
		}

		caches.add(_cache);
		Collections.sort(caches, DataCache.comparator);
	}

	protected DataCacheInMemory<COORD, TYPE> 	getMemoryCache() {
		return caches . stream() 
						. filter(c -> ( c instanceof DataCacheInMemory ) && ( c.getCacheType() == CacheType.MEMORY ) )
					    . map( c -> (DataCacheInMemory<COORD, TYPE>) c)
					    . findFirst()
					    . orElse(null);
	}
	protected DataCacheOnStorage<COORD, TYPE> 	getStorageCache() {
		return caches . stream()
						. filter(c -> ( c instanceof DataCacheOnStorage ) && ( c.getCacheType() == CacheType.STORAGE ) )
					    . map( c -> (DataCacheOnStorage<COORD, TYPE>) c)
					    . findFirst()
					    . orElse(null);
	}

	/** DATA REGISTRY **/
	private void 								addToMemoryCache(COORD _coords, TYPE _content) {
		if(getMemoryCache() != null)
			getMemoryCache().addToCache(_coords, _content);
	}
	private void 								addToStorageCache(COORD _coords, TYPE _content) {
		if(getStorageCache() != null)
			try {
				getStorageCache().addToStorage(_coords, _content);
			} catch(IOException e) {
				e.printStackTrace();
				System.err.println("Failed to store data on local storage");
			} catch(DataException e) {
				e.printStackTrace();
				System.err.println("Unknown Failed to store data on local storage");
			}
	}

	/**
	 * WORKERS
	 */
	public void 								clearQueueAndStopLoading() {
		workers.clearQueueAndStopLoading();
	}

	/**
	 * INTERNAL CLASSES
	 */
	class CacheWorker implements Callable<TYPE> {
		Data.Async<COORD, TYPE> 			data;
		Throwable							error;

		Iterator<DataCache<COORD, TYPE>>  	caches;
		DataCache.CacheType					foundAt;

		public CacheWorker(Data.Async<COORD, TYPE> _data, Iterator<DataCache<COORD, TYPE>> _caches) {
			super();
			data   = _data;
			caches = _caches;
		}

		public Data.Async<COORD, TYPE> getData() {
			return data;
		}

		@Override
		public TYPE call() throws Exception {
			if( data instanceof AsyncData<?,?> )
				((AsyncData<?,?>) data).setLoading(true);

			while(caches.hasNext()) {
				DataCache<COORD, TYPE> cache = caches.next();

				try {
					TYPE content = cache.get(data.getCoordinates());

					if(content != null) {
						foundAt = cache.getCacheType();

						return content;

					}

				} catch(DataNotFoundException e) {
					error = e;
				}

			}

			return null; // No data yet...
		}
	
	}

	class CacheFutureTask extends FutureTask<TYPE> implements Future<TYPE> {
		CacheWorker worker;
	
		public CacheFutureTask(CacheWorker _callable) {
			super(_callable);
			worker = _callable;
		}

		public CacheWorker getWorker() {
			return worker;
		}

	}

	class CacheWorkerManager {
		private static final int POOL_SIZE = 3;

		private final List<CacheFutureTask> taskInProgress;

		private final Thread				threadMain;
		private final ExecutorService 	  	threadPool;

		public CacheWorkerManager() {
			this(POOL_SIZE);
		}
		public CacheWorkerManager(int _poolSize) {
			super();
	
			taskInProgress = new CopyOnWriteArrayList<CacheFutureTask>();
	
			threadPool = Executors.newFixedThreadPool(_poolSize, new ThreadFactory() {
				private int count = 0;
	
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "data_worker-pool-" + count++);
					t.setPriority(Thread.MIN_PRIORITY);
					t.setDaemon(true);
					return t;
				}
	
			});

			threadMain = new Thread(() -> {
				while(!Thread.currentThread().isInterrupted()) {
					try {
						if(taskInProgress.isEmpty()) {
								Thread.sleep(40);
		
						} else {
							for(CacheFutureTask task : taskInProgress)
								processTask(task);

						}
					} catch(InterruptedException e) { Thread.currentThread().interrupt(); }
				}
			});

			threadMain.start();
		}

		public void createNewRequest(Data.Async<COORD, TYPE> _data, Iterator<DataCache<COORD, TYPE>> _caches) {
			if(_data.isLoading())	// data.promote()
				return;
	
			CacheFutureTask dataWorker = new CacheFutureTask( new CacheWorker(_data, _caches) );

			threadPool		. execute	( dataWorker );
			taskInProgress	. add		( dataWorker );

			if(_data instanceof AsyncData)
				((AsyncData<COORD, TYPE>) _data).setLoading(true);
		}
		public void clearQueueAndStopLoading() {
			threadPool     . shutdown();
			taskInProgress . clear();

			threadMain     . interrupt();
		}

		private Data.Async<COORD, TYPE> processTask(CacheFutureTask _task) throws InterruptedException {
			if(!(_task.getWorker().getData() instanceof AsyncData))
				throw new RuntimeException("Where do you come from ?");

			AsyncData<COORD, TYPE> data = (AsyncData<COORD, TYPE>) _task.getWorker().getData();

			if( _task.isDone() ) {
				taskInProgress.remove(_task);

				try {
					TYPE content = _task.get();
					
					if(content != null) {
						DataCache.CacheType foundIn = _task.getWorker().foundAt;
	
						if(foundIn != DataCache.CacheType.MEMORY) {
							addToMemoryCache(data.getCoordinates(), content);

							if(foundIn != DataCache.CacheType.STORAGE && data.isPersistent())
								addToStorageCache(data.getCoordinates(), content);
						}

						data.setLoading(false);
						data.setLoaded(true);
						data.setContent(content);

						data.fireLoadedEvent(content);

					} else {
						data.setLoading(false);
						createNewRequest(data, caches.iterator());
					}

				} catch(ExecutionException e) {
					data.setLoading(false);
					data.setLoaded(false);
					data.setError(e);
				}

			} else if( _task.isCancelled() ) {
				data.setLoading(false);
				data.setLoaded(false);
				data.setError(new DataNotFoundException("DataProviderAsync:: Mandated data not available in any cache"));
			}

			return data;
		}

	}

}
