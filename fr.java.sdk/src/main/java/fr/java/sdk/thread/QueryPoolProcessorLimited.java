package fr.java.sdk.thread;

import java.time.Instant;

public class QueryPoolProcessorLimited extends QueryPoolProcessor {
	private static int nextID = 0;

	public static void main(String[] args) throws Exception {
		QueryPoolProcessor pool = new QueryPoolProcessorLimited(10);
		for(long i = 0; i < 1e6; ++i) {
			final long REQUEST_ID = i;
			pool.runLater(() -> System.out.println(REQUEST_ID));
		}

		Thread.sleep(15000);
		System.out.println("next...");

		for(long i = 0; i < 1e6; ++i) {
			final long REQUEST_ID = i;
			pool.runLater(() -> System.out.println(REQUEST_ID));
		}
	}

	private final double	maxQueryPerSecond;
	private 	  Instant	last;

	public QueryPoolProcessorLimited(int _maxQueryPerSecond) {
		this("QueryPoolProcessorLimited-" + nextID++, _maxQueryPerSecond);
	}
	public QueryPoolProcessorLimited(String _name, int _maxQueryPerSecond) {
		super(_name);
		maxQueryPerSecond = _maxQueryPerSecond;
		last              = Instant.now();
	}

	protected void iteration() {
		int loopDurationMs = (int) (maxQueryPerSecond >= 1 ? 1000d : 1000d / maxQueryPerSecond);
		int runnedQueries  = 0;
		
		do {
			runnedQueries  = 0;
	
			Runnable runnable;
			while((runnable = pendingQueries.poll()) != null) {
				runnable.run(); runnedQueries++;				
	
				if(runnedQueries >= maxQueryPerSecond)
					break;
	
				// Here, loopDurationMs = 1s, maxQueryPerSecond > 1 and remainingQueries > 0
				int  remainingQueries = (int) maxQueryPerSecond - runnedQueries;
				long elapsed          = Instant.now().toEpochMilli() - last.toEpochMilli();
	
				try {
					double delay = (loopDurationMs - elapsed) / remainingQueries;
					Thread.sleep(delay > 0 ? (long) delay : 0);
				} catch (InterruptedException e) { }
			}
	
			long elapsed = Instant.now().toEpochMilli() - last.toEpochMilli();
	
			if(elapsed < loopDurationMs) {
				double delay = loopDurationMs - elapsed;
				try {
					Thread.sleep(delay > 0 ? (long) delay : 0);
				} catch (InterruptedException e) { }
			}

			last = Instant.now();
		} while( sleepOrDie(runnedQueries) );
	}

}
