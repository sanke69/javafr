package fr.java.sdk.thread;

import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueryPoolProcessor {
	private static final long 	idlePerTask = 0;
	private static final long 	idleToDieMs = 1_000;

	public static void main(String[] args) throws Exception {
		final QueryPoolProcessor pool = new QueryPoolProcessor();
		for(long i = 0; i < 1_000_000; ++i) {
			final long REQUEST_ID = i;
			pool.runLater(() -> { System.out.println(REQUEST_ID); try { Thread.sleep(idlePerTask); } catch(Exception e) {} });
		}

		Thread.sleep(5000);
		System.out.println("\t\t\t\tnext...");

		for(long i = 1_000_000; i < 2_000_000; ++i) {
			final long REQUEST_ID = i;
			pool.runLater(() -> { System.out.println(REQUEST_ID); try { Thread.sleep(idlePerTask); } catch(Exception e) {} });
		}
	}

	protected final String    						name;
	protected final ConcurrentLinkedQueue<Runnable> pendingQueries;

	protected Thread								queryThread;
	private   Instant 								lastRun;

	private static int nextID = 0;
	public QueryPoolProcessor() {
		this("QueryPoolProcessor-" + nextID++);
	}
	public QueryPoolProcessor(String _name) {
		super();
		name           = _name;

		queryThread    = null;
		pendingQueries = new ConcurrentLinkedQueue<>();
		lastRun        = null;
	}

	public final void  					runLater(Runnable _request) {
		if(queryThread == null || !queryThread.isAlive()) {
			pendingQueries.add(_request);

			queryThread = new Thread(this::iteration, name);
			queryThread.start();

		} else {
			if(Thread.currentThread() != queryThread)
				pendingQueries.add(_request);
			else
				_request.run();
		}
	}

	protected void 					iteration() {
		int runnedQueries = 0;
		do {
			runnedQueries = 0;
			Runnable queries;

			while((queries = pendingQueries.poll()) != null)
			{ queries.run(); runnedQueries++; }	

		} while(sleepOrDie(runnedQueries));
	}

	protected boolean 					sleepOrDie(int _runned) {
		if(lastRun == null)
		{ lastRun = Instant.now(); return true; }

		if(_runned == 0) {
			try { Thread.sleep(idleToDieMs / 100);
			} catch (InterruptedException e) { }

			return Instant.now().toEpochMilli() - lastRun.toEpochMilli() < idleToDieMs;
		}

		lastRun = Instant.now();
		return true;
	}

}
