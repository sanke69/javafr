package fr.java.sdk.nio.stream;

import java.io.IOException;

import fr.java.nio.Peekable;
import fr.java.nio.stream.InputStreamX;

public class FlexibleInputStreamX extends PushbackInputStreamX implements Peekable {

	final InputStreamX 		  in;
//	final PushbackInputStreamX peeker;
	
	public FlexibleInputStreamX(final InputStreamX _source) {
		super(_source, 1_500);
		in     = _source;
//		peeker = new PushbackInputStreamX(in, 1_500);
	}

	public int 					peek() throws IOException {
		int peekedValue = read();
		unread(peekedValue);
		return peekedValue;
	}
	public int 					peek(byte b[]) throws IOException {
		int size = read(b);
		unread(b, 0, size);
		return size;
	}
	public int 					peek(byte b[], int off, int len) throws IOException {
		int size = read(b, off, len);
		unread(b, off, size);
		return size;
	}
/*
	@Override
    public int 					available() throws IOException {
        return peeker.available();
    }

	@Override
	public int 					read() throws IOException {
		return peeker.read();
	}
	@Override
    public int 					read(byte b[]) throws IOException {
        return peeker.read(b);
    }
	@Override
    public int 					read(byte b[], int off, int len) throws IOException {
        return peeker.read(b, off, len);
    }

	@Override
    public long 				skip(long n) throws IOException {
        return peeker.skip(n);
    }

	@Override
    public boolean 				markSupported() {
        return peeker.markSupported();
    }
	@Override
    public synchronized void 	mark(int readlimit) {
    	peeker.mark(readlimit);
    }

	@Override
    public synchronized void 	reset() throws IOException {
        peeker.reset();
    }

	@Override
    public void 				close() throws IOException {
    	peeker.close();
    }
*/
}