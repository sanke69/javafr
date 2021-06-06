package fr.java.sdk.nio.stream.util;

import java.io.IOException;

import fr.java.lang.exceptions.InvalidInitializationException;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;

public class ThreadedStreamConnectorX extends Thread {
    private static int threadInitNumber;
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

	boolean	threadRunning;

	InputStreamX  in;
	OutputStreamX out;

	public ThreadedStreamConnectorX(InputStreamX _in, OutputStreamX _out) throws IOException {
		super("StreamConnector-" + nextThreadNum());

		in  = _in;
        out = _out;
	}

	@Override
	public void run() {
		if(in == null || out == null)
			throw new InvalidInitializationException("At least, one stream is null");

		threadRunning = true;
		try {
			int input;
			while(threadRunning && (input = in.read()) != -1)
				out.write(input);
			out.flush();
		} catch(IOException e) {
			System.err.println("StreamConnector " + getName());
			e.printStackTrace();
			terminate();
		}
	}

	public void terminate() {
		threadRunning = false;
	}

}
