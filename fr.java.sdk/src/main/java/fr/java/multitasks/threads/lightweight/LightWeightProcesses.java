package fr.java.multitasks.threads.lightweight;

public class LightWeightProcesses {

	public static void newThread(Runnable _run) {
		new Thread(_run).start();
	}

}
