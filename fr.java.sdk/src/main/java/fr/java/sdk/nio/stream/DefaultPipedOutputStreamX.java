package fr.java.sdk.nio.stream;

import java.io.IOException;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;

public class DefaultPipedOutputStreamX implements PipedOutputStreamX {

	private PipedInputStreamX sink;

	public DefaultPipedOutputStreamX() {
	}
	public DefaultPipedOutputStreamX(PipedInputStreamX snk) throws IOException {
		connect(snk);
	}

	public synchronized void 	connect(PipedInputStreamX _snk) throws IOException {
		if (_snk == null)
			throw new NullPointerException();
		else if(!(_snk instanceof DefaultPipedInputStreamX))
			throw new NotYetImplementedException();

		DefaultPipedInputStreamX snk = (DefaultPipedInputStreamX) _snk;

		if (sink != null || snk.connected)
			throw new IOException("Already connected");

		sink          = snk;
		snk.in        = -1;
		snk.out       = 0;
		snk.connected = true;
	}

	@Override
	public void 				write(int b) throws IOException {
		if (sink == null)
			throw new IOException("Pipe not connected");
		else if(!(sink instanceof DefaultPipedInputStreamX))
			throw new NotYetImplementedException();
		((DefaultPipedInputStreamX) sink).receive(b);
	}
	@Override
	public void 				write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}
	@Override
	public void 				write(byte b[], int off, int len) throws IOException {
		if (sink == null)
			throw new IOException("Pipe not connected");
		else if (b == null)
			throw new NullPointerException();
		else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0))
			throw new IndexOutOfBoundsException();
		else if (len == 0)
			return;
		else if(!(sink instanceof DefaultPipedInputStreamX))
			throw new NotYetImplementedException();

		((DefaultPipedInputStreamX) sink).receive(b, off, len);
	}

	@Override
	public synchronized void 	flush() throws IOException {
		if (sink != null) {
			synchronized (sink) {
				sink.notifyAll();
			}
		}
	}

	@Override
	public void 				close() throws IOException {
		if (sink != null && sink instanceof DefaultPipedInputStreamX)
			((DefaultPipedInputStreamX) sink).receivedLast();
	}

}
