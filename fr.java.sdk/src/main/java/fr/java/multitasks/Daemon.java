package fr.java.multitasks;

public abstract class Daemon extends Thread {

	public Daemon() {
		this(() -> {
                Object o = new Object();
                try {
                    synchronized (o) {
                        o.wait();
                    }
                } catch (InterruptedException ie) {
                }
            });
	}
	public Daemon(Runnable _r) {
		super(_r);
        setDaemon(true);
	}

}
