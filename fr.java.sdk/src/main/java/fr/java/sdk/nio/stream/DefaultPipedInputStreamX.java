package fr.java.sdk.nio.stream;

import java.io.IOException;
import java.nio.InvalidMarkException;

import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;

public class DefaultPipedInputStreamX implements PipedInputStreamX {
	private  static  final int DEFAULT_PIPE_SIZE = 1024;
	protected static final int PIPE_SIZE         = DEFAULT_PIPE_SIZE;

	boolean 			closedByWriter 	= false;
	volatile boolean 	closedByReader 	= false;
	boolean 			connected 		= false;

	Thread readSide;
	Thread writeSide;

	protected byte buffer[];
	protected int in = -1;
	protected int out = 0;

	public DefaultPipedInputStreamX() {
		initPipe(DEFAULT_PIPE_SIZE);
	}
	public DefaultPipedInputStreamX(int pipeSize) {
		initPipe(pipeSize);
	}
	public DefaultPipedInputStreamX(PipedOutputStreamX src) throws IOException {
		this(src, DEFAULT_PIPE_SIZE);
	}
	public DefaultPipedInputStreamX(PipedOutputStreamX src, int pipeSize) throws IOException {
		initPipe(pipeSize);
		connect(src);
	}

	@Override
	public void 				connect(PipedOutputStreamX src) throws IOException {
		src.connect(this);
	}

	@Override
	public synchronized int 	available() throws IOException {
		if (in < 0)
			return 0;
		else if (in == out)
			return buffer.length;
		else if (in > out)
			return in - out;
		else
			return in + buffer.length - out;
	}

	@Override
	public synchronized int 	read() throws IOException {
		if (!connected) {
			throw new IOException("Pipe not connected");
		} else if (closedByReader) {
			throw new IOException("Pipe closed");
		} else if (writeSide != null && !writeSide.isAlive() && !closedByWriter && (in < 0)) {
			throw new IOException("Write end dead");
		}

		readSide = Thread.currentThread();
		int trials = 2;
		while (in < 0) {
			if (closedByWriter) {
				/* closed by writer, return EOF */
				return -1;
			}
			if ((writeSide != null) && (!writeSide.isAlive()) && (--trials < 0)) {
				throw new IOException("Pipe broken");
			}
			/* might be a writer waiting */
			notifyAll();
			try {
				wait(1000);
			} catch (InterruptedException ex) {
				throw new java.io.InterruptedIOException();
			}
		}
		int ret = buffer[out++] & 0xFF;
		if (out >= buffer.length) {
			out = 0;
		}
		if (in == out) {
			/* now empty */
			in = -1;
		}

		return ret;
	}
	@Override
	public synchronized int 	read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}
	@Override
	public synchronized int 	read(byte b[], int off, int len) throws IOException {
		if (b == null)
			throw new NullPointerException();
		else if (off < 0 || len < 0 || len > b.length - off)
			throw new IndexOutOfBoundsException();
		else if (len == 0)
			return 0;

		/* possibly wait on the first character */
		int c = read();
		if (c < 0)
			return -1;

		b[off] = (byte) c;
		int rlen = 1;
		while ((in >= 0) && (len > 1)) {
			int available;

			if (in > out)
				available = Math.min((buffer.length - out), (in - out));
			else
				available = buffer.length - out;

			// A byte is read beforehand outside the loop
			if (available > (len - 1))
				available = len - 1;

			System.arraycopy(buffer, out, b, off + rlen, available);
			out  += available;
			rlen += available;
			len  -= available;

			if (out >= buffer.length)
				out = 0;

			if (in == out) 
				in = -1;
		}
		return rlen;
	}

	@Override
	public long 				skip(long n) throws IOException {
		return 0;
	}

	@Override
	public boolean 				markSupported() {
		return false;
	}
	@Override
	public void 				mark(int readlimit) {
		throw new InvalidMarkException();
	}
	@Override
	public void 				reset() throws IOException {
		;
	}

	@Override
	public void 				close() throws IOException {
		closedByReader = true;
		synchronized (this) {
			in = -1;
		}
	}

	private void 				initPipe(int pipeSize) {
		if (pipeSize <= 0)
			throw new IllegalArgumentException("Pipe Size <= 0");
		buffer = new byte[pipeSize];
	}

	private void 				checkStateForReceive() throws IOException {
		if (!connected) {
			throw new IOException("Pipe not connected");
		} else if (closedByWriter || closedByReader) {
			throw new IOException("Pipe closed");
		} else if (readSide != null && !readSide.isAlive()) {
			throw new IOException("Read end dead");
		}
	}

	protected synchronized void receive(int b) throws IOException {
		checkStateForReceive();
		writeSide = Thread.currentThread();
		if (in == out)
			awaitSpace();
		if (in < 0) {
			in  = 0;
			out = 0;
		}
		buffer[in++] = (byte) (b & 0xFF);
		if (in >= buffer.length) {
			in = 0;
		}
	}
	synchronized void 			receive(byte b[], int off, int len) throws IOException {
		checkStateForReceive();
		writeSide = Thread.currentThread();
		int bytesToTransfer = len;
		while (bytesToTransfer > 0) {
			if (in == out)
				awaitSpace();
			int nextTransferAmount = 0;
			if (out < in) {
				nextTransferAmount = buffer.length - in;
			} else if (in < out) {
				if (in == -1) {
					in = out = 0;
					nextTransferAmount = buffer.length - in;
				} else {
					nextTransferAmount = out - in;
				}
			}
			if (nextTransferAmount > bytesToTransfer)
				nextTransferAmount = bytesToTransfer;
			assert (nextTransferAmount > 0);
			System.arraycopy(b, off, buffer, in, nextTransferAmount);
			bytesToTransfer -= nextTransferAmount;
			off += nextTransferAmount;
			in += nextTransferAmount;
			if (in >= buffer.length) {
				in = 0;
			}
		}
	}

	private void 				awaitSpace() throws IOException {
		while (in == out) {
			checkStateForReceive();

			notifyAll();
			try {
				wait(1000);
			} catch (InterruptedException ex) {
				throw new java.io.InterruptedIOException();
			}
		}
	}

	synchronized void 			receivedLast() {
		closedByWriter = true;
		notifyAll();
	}

}
