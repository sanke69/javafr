package fr.java.sdk.data.async.caches;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fr.java.data.DataException;
import fr.java.data.DataLoadException;
import fr.java.data.DataNotFoundException;
import fr.java.patterns.priority.DefaultPriority;
import fr.java.patterns.priority.Priority;
import fr.java.sdk.thread.QueryPoolProcessor;
import fr.java.sdk.thread.QueryPoolProcessorLimited;

public abstract class AbstractDataCacheOnLineWithMaxRequests<COORD, TYPE> extends AbstractDataCacheOnLine<COORD, TYPE> {
	Map<URL, Task> 		taskCache;
	QueryPoolProcessor  taskThread;

	public AbstractDataCacheOnLineWithMaxRequests() {
		this(null, 3, DefaultPriority.Lowest);
	}
	public AbstractDataCacheOnLineWithMaxRequests(String _name) {
		this(_name, 3, DefaultPriority.Lowest);
	}
	public AbstractDataCacheOnLineWithMaxRequests(int _maxRequestPerSeconds) {
		this(null, _maxRequestPerSeconds, DefaultPriority.Lowest);
	}
	public AbstractDataCacheOnLineWithMaxRequests(Priority _priority) {
		this(null, 3, _priority);
	}
	public AbstractDataCacheOnLineWithMaxRequests(String _name, int _maxRequestPerSeconds) {
		this(_name, _maxRequestPerSeconds, DefaultPriority.Lowest);
	}
	public AbstractDataCacheOnLineWithMaxRequests(String _name, int _maxRequestPerSeconds, Priority _priority) {
		super(_priority);

		taskCache   = new HashMap<URL, Task>();
		taskThread  = _name != null ? new QueryPoolProcessorLimited(_name, _maxRequestPerSeconds) : new QueryPoolProcessorLimited(_maxRequestPerSeconds);
	}

	@Override
	public TYPE get(COORD _coords) throws DataException {
		if(!isServerOnline())
			throw new DataNotFoundException("Server is Offline");

		URL url = getURL(_coords);
		if( ! taskCache.containsKey(url) ) {
			Task task = new Task(url);

			taskCache.put(url, task);
			taskThread.runLater(task);

			return null;

		} else {
			Task task = taskCache.get(url);

			if(!task.done)
				return null;

			taskCache.remove(url);

			if(task.error != null)
				throw new DataLoadException("Download issue", task.error);
			else if(task.is == null)
				throw new DataNotFoundException("Resource not found on server");

			return getContent(task.is);

		}
	}

	private class Task implements Runnable {
		URL         url;
		InputStream is;
		boolean     done;
		Throwable   error;

		Task(URL _url) {
			url = _url;
		}

		@Override
		public void run() {
			try {
				is = download(url);
			} catch (Exception e) {
				error = e;
			}

			done = true;
		}

	}

}
