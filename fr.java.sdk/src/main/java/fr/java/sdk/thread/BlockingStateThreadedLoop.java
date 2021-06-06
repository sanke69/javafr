package fr.java.sdk.thread;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

@FunctionalInterface
interface Iteration {

	public boolean run();

}

public class BlockingStateThreadedLoop {
	private final Iteration iteration;
	private final String    threadName;

	private Thread    		thread;
	private CountDownLatch 	isStarted, isStopped;
	private boolean         isRunning;

	BlockingStateThreadedLoop(Supplier<Boolean> _iteration) {
		this(_iteration, null);
	}
	BlockingStateThreadedLoop(Supplier<Boolean> _iteration, String _threadName) {
		super();
		iteration  = _iteration::get;
		threadName = _threadName;

		thread     = null;
		isStarted  = null;
		isStopped  = null;
		isRunning  = false; 
	}

	public Thread 		currentThread() {
		return thread;
	}

	public void 		start() {
		if(thread != null)
			throw new IllegalAccessError();

		thread = threadName == null ?
								new Thread(this::mainLoop) 				{{ isStarted = new CountDownLatch(1); isStopped = new CountDownLatch(1); isRunning = false; }}
								:
								new Thread(this::mainLoop, threadName) 	{{ isStarted = new CountDownLatch(1); isStopped = new CountDownLatch(1); isRunning = false; }};

		try { 
			thread.start();
			isStarted.await();
		} catch(InterruptedException e) { e.printStackTrace(); System.err.println("\n\n\n" + threadName + " crashed on start"); }

	}
	public void 		stop() {
		isRunning = false;

		try {
			thread.wait(500);
			if(thread.isAlive())
				thread.interrupt();

			isStopped.await();

		} catch(InterruptedException e) { e.printStackTrace(); System.err.println(threadName + " crashed on stop");
		} catch(IllegalArgumentException e) { e.printStackTrace(); System.err.println(threadName + " seriously...");
		} catch(IllegalMonitorStateException e) { e.printStackTrace(); System.err.println(threadName + " WTF!!!"); }

		thread = null;
	}

	private void 		mainLoop() {
		isRunning = true;

		isStarted . countDown();

		try {
			while( isRunning && iteration.run() ) ;
		} catch(Throwable e) { e.printStackTrace(); }

		isStopped . countDown();
	}

}
