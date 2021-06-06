package fr.java.multitasks.api;

import java.util.function.Supplier;

public abstract class PeriodicRunnable implements Runnable {
	int               period_ms;
	Supplier<Boolean> nextIteration;
	Runnable          iteration;

	public PeriodicRunnable(int _period_ms, Supplier<Boolean> _nextIteration, Runnable _iteration) {
		super();
		period_ms     = _period_ms;
		nextIteration = _nextIteration;
		iteration     = _iteration;
	}

	public void run() {
		try {
		    long entryTimestamp = -1;
			long exitTimestamp  = -1;
			long sleepTime      = period_ms;

			while(nextIteration.get()) {
			    entryTimestamp = System.currentTimeMillis();

			    iteration.run();

				sleepTime      = (long) (period_ms - (entryTimestamp - exitTimestamp));
		        exitTimestamp  = entryTimestamp;
				Thread.sleep(sleepTime > 0 ? sleepTime : 0);
			}
		} catch (InterruptedException e) {
			;
		}
	}

}
