package fr.java.multitasks.threads;

public class Threads {

	public static void sleep(int _ms) {
		try { Thread.sleep(1500); } catch(InterruptedException e) { }
	}
	public static void sleep(int _ms, Runnable _action) {
		new Thread(() -> {
			try { Thread.sleep(1500); } catch(InterruptedException e) { }
			if(_action != null)
				_action.run();
		}).start();
	}

	public static Thread getThreadByName(String threadName) {
	    for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals(threadName)) return t;
	    }
	    return null;
	}

}
