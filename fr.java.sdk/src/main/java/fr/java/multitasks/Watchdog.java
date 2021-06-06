package fr.java.multitasks;

import fr.java.multitasks.processes.ProcessX;

public class Watchdog extends Daemon {

	protected final ProcessX processus;
	protected final Runnable  onTermination;

	public Watchdog(ProcessX _processus, Runnable _onTermination, int _wait_ms) {
		super(main(_processus, _onTermination, _wait_ms));
		
		processus     = _processus;
		onTermination = _onTermination;
	}
	public Watchdog(int _wait_ms, ProcessX _processus, Runnable _onTermination) {
		super(main(_processus, _onTermination, _wait_ms));
		
		processus     = _processus;
		onTermination = _onTermination;
	}

	public static Runnable main(ProcessX _processus, Runnable _onTermination, int _wait_ms) {
		return () -> {
			while(_processus.isRunning())
				try {
					Thread.sleep(_wait_ms);
				} catch(InterruptedException e) { e.printStackTrace(); }
			_onTermination.run();
		};
	}
	
}
