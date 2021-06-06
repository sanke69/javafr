package fr.java.sdk.nio.stream.util;

import java.io.IOException;

import fr.java.nio.Seekable;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.utils.InputStreamReaderX;
import fr.java.sdk.nio.buffer.circular.CircularByteBufferX;

public class SeekableInputStreamReaderX implements InputStreamReaderX, Seekable {
	public static final int defaultPeriod  =     25;
	public static final int bufferCapacity = 25_000;
	public static final int packetCapacity =  1_500;

	final InputStreamX 			src;

	final CircularByteBufferX   buffer;
	final byte[]				packet;

	int 						period;

	public SeekableInputStreamReaderX(InputStreamX _source) {
		this(_source, bufferCapacity, defaultPeriod);
	}
	public SeekableInputStreamReaderX(InputStreamX _source, int _backCapacity) {
		this(_source, _backCapacity, defaultPeriod);
	}
	public SeekableInputStreamReaderX(InputStreamX _source, int _backCapacity, int _period) {
		super();
		src    = _source;
		period = _period;
		
		buffer = new CircularByteBufferX(_backCapacity);
		packet = new byte[packetCapacity];
	}

	@Override
	public void 				run() {
		if(src == null)
			throw new IllegalArgumentException();

		try {

			while(true) {
				long startTime = System.nanoTime();

				while(src.available() > 0) {
					int nbBytes = src.read(packet);
					buffer.getOutputStream().write(packet, 0, nbBytes);
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
		    
		}
	}

	@Override
	public int 					available() throws IOException {
        return buffer.getInputStream() != null ? buffer.getInputStream().available() : 0;
    }
	
	@Override
	public int 					read() throws IOException {
		return buffer.getInputStream().read();
	}
	@Override
    public int 					read(byte b[]) throws IOException {
        return buffer.getInputStream().read(b);
    }
	@Override
    public int 					read(byte b[], int off, int len) throws IOException {
        return buffer.getInputStream().read(b, off, len);
    }

	@Override
    public long 				skip(long n) throws IOException {
        return buffer.getInputStream().skip(n);
    }

	@Override
    public boolean 				markSupported() {
        return buffer.getInputStream().markSupported();
    }
	@Override
    public synchronized void 	mark(int readlimit) {
		buffer.getInputStream().mark(readlimit);
	}

	@Override
    public synchronized void 	reset() throws IOException {
		buffer.getInputStream().reset();
    }

	@Override
	public void close() throws IOException {
		buffer.getInputStream().close();
	}

	@Override
	public int peek() throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().peek();
	}
	@Override
	public int peek(byte[] b) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().peek(b);
	}
	@Override
	public int peek(byte[] b, int off, int len) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().peek(b, off, len);
	}

	@Override
	public int indexOf(byte _magicWord) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord);
	}
	@Override
	public int indexOf(byte _magicWord, int _maxDepth) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord, _maxDepth);
	}
	@Override
	public int indexOf(byte[] _magicWord) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord);
	}
	@Override
	public int indexOf(byte[] _magicWord, int _maxDepth) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord, _maxDepth);
	}
	@Override
	public int indexOf(short _magicWord) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord);
	}
	@Override
	public int indexOf(short _magicWord, int _maxDepth) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord, _maxDepth);
	}
	@Override
	public int indexOf(int _magicWord) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord);
	}
	@Override
	public int indexOf(int _magicWord, int _maxDepth) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord, _maxDepth);
	}
	@Override
	public int indexOf(long _magicWord) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord);
	}
	@Override
	public int indexOf(long _magicWord, int _maxDepth) throws IOException {
		if(buffer.getInputStream() == null)
			throw new IOException("buffer input stream is closed !!!");
        return buffer.getInputStream().indexOf(_magicWord, _maxDepth);
	}
	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean ready() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int read(char[] cbuf, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
