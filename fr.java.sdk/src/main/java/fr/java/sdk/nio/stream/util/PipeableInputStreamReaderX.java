package fr.java.sdk.nio.stream.util;

import java.io.IOException;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.pipes.PipedInputStreamX;
import fr.java.nio.stream.pipes.PipedOutputStreamX;
import fr.java.nio.stream.utils.InputStreamReaderX;
import fr.java.sdk.nio.stream.DefaultPipedInputStreamX;
import fr.java.sdk.nio.stream.DefaultPipedOutputStreamX;

public class PipeableInputStreamReaderX implements InputStreamReaderX, PipedInputStreamX, Runnable {
	public static final int bufferCapacity = 25_000;
	public static final int packetCapacity =  1_500;

	byte[]					packet = new byte[packetCapacity];

	final InputStreamX 		src;

	PipedInputStreamX  		in;
	PipedOutputStreamX 		out;

	int 					period;

	public PipeableInputStreamReaderX(InputStreamX _source) {
		this(_source, 25);
	}
	public PipeableInputStreamReaderX(InputStreamX _source, int _period) {
		super();
		src    = _source;
		period = _period;
	}

	@Override
	public void 				run() {
		if(src == null)
			throw new IllegalArgumentException();

		try {
			in  = new DefaultPipedInputStreamX(bufferCapacity);
			out = new DefaultPipedOutputStreamX(in);

			while(true) {
				long startTime = System.nanoTime();

				while(src.available() > 0) {
					int nbBytes = src.read(packet);
					out.write(packet, 0, nbBytes);
				}

				long timeToSleep = period - (int) ((System.nanoTime() - startTime) / 1e6);
				Thread.sleep(timeToSleep > 0 ? timeToSleep : 0);
			}

		} catch (IOException _e) {
			_e.printStackTrace();
		} catch (InterruptedException _e) {
			_e.printStackTrace();
		} catch (Exception _e) {
			_e.printStackTrace();
		} finally {
		    try { if (in  != null)  in.close(); } catch(IOException e) {}
		    try { if (out != null) out.close(); } catch(IOException e) {}
		}
	}

	@Override
	public void connect(PipedOutputStreamX src) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEncoding() {
		throw new NotYetImplementedException();
	}
	
	@Override
	public int 					available() throws IOException {
        return in != null ? in.available() : 0;
    }
	@Override
	public boolean ready() throws IOException {
		return available() > 0;
	}
	
	@Override
	public int 					read() throws IOException {
		return in.read();
	}
	@Override
    public int 					read(byte b[]) throws IOException {
        return in.read(b);
    }
	@Override
    public int 					read(byte b[], int off, int len) throws IOException {
        return in.read(b, off, len);
    }
	@Override
	public int 					read(char[] cbuf, int offset, int length) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
    public long 				skip(long n) throws IOException {
        return in.skip(n);
    }

	@Override
    public boolean 				markSupported() {
        return in.markSupported();
    }
	@Override
    public synchronized void 	mark(int readlimit) {
		in.mark(readlimit);
	}

	@Override
    public synchronized void 	reset() throws IOException {
		in.reset();
    }

	@Override
	public void 				close() throws IOException {
		in.close();
	}

}
