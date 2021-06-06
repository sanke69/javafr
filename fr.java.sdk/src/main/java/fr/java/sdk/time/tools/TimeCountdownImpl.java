package fr.java.sdk.time.tools;

import java.util.Timer;
import java.util.TimerTask;

// TODO:: Try change all primitive to Instant, Duration and Period...
public class TimeCountdownImpl {
	Timer timer;
	TimerTask task;

	private Runnable doWhenDone;

	private int delay_ms, count, period_ms;
	private boolean isActive, isTerminated;

	public TimeCountdownImpl(int _delay_ms, int _count, int _period_ms, Runnable _doWhenDone) {
		count = _count;
		doWhenDone = _doWhenDone;
		
		delay_ms  = _delay_ms;
		count     = _count;
		period_ms = _period_ms;

		start();
	}

	public boolean isActive() { return isActive; }
	public boolean isTerminated() { return isTerminated; }

	public void start() {
		final int f_count = count;
		task = new TimerTask() {
			int count = f_count;

			@Override
			public void run() {
				count--;
				//System.out.println("Count is: " + count);
				if(count == 0) {
					cancel();
					isActive = false;
					doWhenDone.run();
					isTerminated = true;
					timer.cancel();
				}
			}
		};

		timer = new Timer("My Timer", false);
		timer.scheduleAtFixedRate(task, delay_ms, period_ms);

		isActive     = true;
		isTerminated = false;
	}
	public void abort() {
		if(task != null)
			task.cancel();
		timer.cancel();

		task  = null;
		timer = null;

		isActive     = false;
		isTerminated = false;
	}

	public void reset() {
		abort();
		start();
	}

}
