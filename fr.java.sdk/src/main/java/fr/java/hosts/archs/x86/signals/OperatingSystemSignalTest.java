package fr.java.hosts.archs.x86.signals;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class OperatingSystemSignalTest {
	
	enum WindowsSignals { ABRT, FPE, ILL, INT, SEGV, TERM };

	enum SolarisSignals { ABRT, ALRM, BUS, CHLD, CONT, EMT, FPE, HUP, ILL, INT, IO, KILL, PIPE, POLL, PROF, PWR, QUIT, SEGV, STOP, SYS, TERM, TRAP, TSTP, TTIN, TTOU, URG, USR1, USR2, VTALRM, WINCH, XCPU, XFSZ };
	

	private static boolean running = true;

	public static void init() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("reached point of no return ...");
			}
		});

		SignalHandler handler = new SignalHandler() {
			public void handle(Signal sig) {
				if (running) {
					running = false;
					System.out.println("Signal " + sig);
					System.out.println("Shutting down database...");
				} else {
					// only on the second attempt do we exit
					System.out.println(" database shutdown interrupted!");
					System.exit(0);
				}
			}
		};
		Signal.handle(new Signal("INT"), handler);
		Signal.handle(new Signal("TERM"), handler);
	}

	public static void main(String args[]) throws Exception {
		init();
		Object o = new Object();
		synchronized (o) {
			o.wait(10000);
		}
		System.exit(0);
	}
	
}