package fr.java.sdk.nio.buffer.circular;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;

import fr.java.nio.Seekable;
import fr.java.nio.buffer.BufferX;
import fr.java.nio.buffer.ByteBufferX;
import fr.java.nio.stream.InputStreamX;
import fr.java.nio.stream.OutputStreamX;

public class CircularByteBufferX extends CircularBufferBaseX implements ByteBufferX.Editable {	
	private final static int DEFAULT_CAPACITY = 1024;

	protected class InputStreamViewX implements InputStreamX, Seekable {

		@Override
		public int 			available() throws IOException {
			synchronized (CircularByteBufferX.this) {
				if (inputStreamClosed)
					throw new IOException("InputStream has been closed, it is not ready.");

				return (CircularByteBufferX.this.remaining_impl());
			}
		}

		@Override
		public int 			read() throws IOException {
			while(true) {
				synchronized (CircularByteBufferX.this) {
					if (inputStreamClosed)
						throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");

					int available = CircularByteBufferX.this.remaining_impl();
					if (available > 0) {
						int result = buffer[readPosition] & 0xff;
						readPositionIncrement();
						ensureMark();
						return result;
					} else if (outputStreamClosed) {
						return -1;
					}
				}

				waitAccess();
			}
		}
		@Override
		public int 			read(byte[] cbuf) throws IOException {
			return read(cbuf, 0, cbuf.length);
		}
		@Override
		public int 			read(byte[] cbuf, int off, int len) throws IOException {
			while (true) {
				synchronized (CircularByteBufferX.this) {
					if (inputStreamClosed)
						throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");

					int available = CircularByteBufferX.this.remaining_impl();
					if (available > 0) {
						int length    = Math.min(len, available);
						int firstLen  = Math.min(length, buffer.length - readPosition);
						int secondLen = length - firstLen;

						System.arraycopy(buffer, readPosition, cbuf, off, firstLen);

						if (secondLen > 0) {
							System.arraycopy(buffer, 0, cbuf, off + firstLen, secondLen);
							readPositionSet(secondLen);
						} else
							readPositionIncrement(length);

						ensureMark();
						return length;
					} else if (outputStreamClosed)
						return -1;
				}

				waitAccess();
			}
		}

		@Override
		public long 		skip(long n) throws IOException, IllegalArgumentException {
			while (true) {
				synchronized (CircularByteBufferX.this) {
					if (inputStreamClosed)
						throw new IOException("InputStream has been closed; cannot skip bytes on a closed InputStream.");

					int available = CircularByteBufferX.this.remaining_impl();
					if (available > 0) {
						int length    = Math.min((int) n, available);
						int firstLen  = Math.min(length, buffer.length - readPosition);
						int secondLen = length - firstLen;

						if (secondLen > 0)
							readPositionSet(secondLen);
						else
							readPositionIncrement(length);

						ensureMark();
						return length;
					} else if (outputStreamClosed)
						return 0;

				}

				waitAccess();
			}
		}

		@Override
		public boolean 		markSupported() {
			return true;
		}

		@Override
		public void 		mark(int readAheadLimit) {
			synchronized (CircularByteBufferX.this) {
				if (buffer.length - 1 > readAheadLimit) {
					markSize     = readAheadLimit;
					markPosition = readPosition;
				}
			}
		}

		@Override
		public void 		reset() throws IOException {
			synchronized (CircularByteBufferX.this) {
				if (inputStreamClosed)
					throw new IOException("InputStream has been closed; cannot reset a closed InputStream.");
				readPositionSet(markPosition);
			}
		}

		@Override
		public void 		close() throws IOException {
			synchronized (CircularByteBufferX.this) {
				inputStreamClosed = true;
			}
		}

		@Override
		public int peek() throws IOException {
			return buffer[readPosition];
		}
		@Override
		public int peek(byte[] b) throws IOException {
			return peek(b, 0, b.length);
		}
		@Override
		public int peek(byte[] b, int off, int len) throws IOException {
			int length    = Math.min(b.length, available());
			int firstLen  = Math.min(length, capacity() - readPosition);
			int secondLen = length - firstLen;

			System.arraycopy(buffer, readPosition, b, off, firstLen);
			if (secondLen > 0)
				System.arraycopy(buffer, 0, b, off + firstLen, secondLen);

			return length;
		}

		@Override
		public int indexOf(byte _magicWord) throws IOException {
			return indexOf(_magicWord, available());
		}
		@Override
		public int indexOf(byte _magicWord, int _maxDepth) throws IOException {
			if(available() == 0)
				return -1;

			int firstLen  = Math.min(available(), capacity() - readPosition);
			int secondLen = available() - firstLen;

			for(int i = readPosition; i < firstLen; ++i)
				if(buffer[i] == _magicWord)
					return i - readPosition;
	
			for(int i = 0; i < secondLen; ++i)
				if(buffer[i] == _magicWord) 
					return i + firstLen;

			return -1;
		}

		@Override
		public int indexOf(byte[] _magicWord) throws IOException {
			return indexOf(_magicWord, available());
		}

		@Override
		public int indexOf(byte[] _magicWord, int _maxDepth) throws IOException {
			int firstLen  = Math.min(available(), capacity() - readPosition);
			int secondLen = available() - firstLen;

			for(int i = readPosition; i < readPosition + firstLen - 1; ++i)
				if(buffer[i] == _magicWord[0] && buffer[i+1] == _magicWord[1])
					return i - readPosition;

			if(buffer[capacity() - 1] == _magicWord[0] && buffer[0] == _magicWord[1])
				return capacity() - 1;

			for(int i = 0; i < secondLen - 1; ++i)
				if(buffer[i] == _magicWord[0] && buffer[i+1] == _magicWord[1])
					return i + firstLen;

			return -1;
		}

		@Override
		public int indexOf(short _magicWord) throws IOException {
			return indexOf(_magicWord, remaining());
		}

		@Override
		public int indexOf(short _magicWord, int _maxDepth) throws IOException {
			return indexOf(_magicWord, _maxDepth);
		}

		@Override
		public int indexOf(int _magicWord) throws IOException {
			return indexOf(_magicWord, remaining());
		}

		@Override
		public int indexOf(int _magicWord, int _maxDepth) throws IOException {
			return indexOf(_magicWord, _maxDepth);
		}

		@Override
		public int indexOf(long _magicWord) throws IOException {
			return indexOf(_magicWord, remaining());
		}

		@Override
		public int indexOf(long _magicWord, int _maxDepth) throws IOException {
			return indexOf(_magicWord, _maxDepth);
		}

	}
	protected class OutputStreamViewX implements OutputStreamX {

		@Override
		public void 		write(int c) throws IOException {
			boolean written = false;
			while (!written) {
				synchronized (CircularByteBufferX.this) {
					if (outputStreamClosed)
						throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
					if (inputStreamClosed)
						throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");

					int spaceLeft = free_impl();
					while (infinite && spaceLeft < 1) {
						resize();
						spaceLeft = free_impl();
					}
					if (!blockingWrite && spaceLeft < 1)
						throw new BufferOverflowException(/*"CircularByteBuffer is full; cannot write 1 byte"*/);
					if (spaceLeft > 0) {
						buffer[writePosition] = (byte) (c & 0xff);
						writePositionIncrement();
						written = true;
					}
				}

				if (!written)
					waitAccess();
			}
		}
		@Override
		public void 		write(byte[] cbuf) throws IOException {
			write(cbuf, 0, cbuf.length);
		}
		@Override
		public void 		write(byte[] cbuf, int off, int len) throws IOException {
			while (len > 0) {
				synchronized (CircularByteBufferX.this) {
					if (outputStreamClosed)
						throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
					if (inputStreamClosed)
						throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");
					int spaceLeft = free_impl();
					while (infinite && spaceLeft < len) {
						resize();
						spaceLeft = free_impl();
					}

					if (!blockingWrite && spaceLeft < len)
						throw new BufferOverflowException(/*"CircularByteBuffer is full; cannot write " + len + " bytes"*/);

					int realLen   = Math.min(len, spaceLeft);
					int firstLen  = Math.min(realLen, buffer.length - writePosition);
					int secondLen = Math.min(realLen - firstLen, buffer.length - markPosition - 1);
					int written   = firstLen + secondLen;

					if (firstLen > 0)
						System.arraycopy(cbuf, off, buffer, writePosition, firstLen);

					if (secondLen > 0) {
						System.arraycopy(cbuf, off + firstLen, buffer, 0, secondLen);
						writePositionSet(secondLen);
					} else
						writePositionIncrement(written);

					off += written;
					len -= written;
				}

				if (len > 0)
					waitAccess();
			}
		}

		@Override
		public void 		flush() throws IOException {
			synchronized (CircularByteBufferX.this) {
				if (outputStreamClosed)
					throw new IOException("OutputStream has been closed; cannot flush a closed OutputStream.");
				if (inputStreamClosed)
					throw new IOException("Buffer closed by inputStream; cannot flush.");
			}
			// this method needs to do nothing
		}

		@Override
		public void 		close() throws IOException {
			synchronized (CircularByteBufferX.this) {
				if (!outputStreamClosed)
					flush();

				outputStreamClosed = true;
			}
		}

	}

	protected byte[]           buffer;
	protected volatile int     markSize           = 0;

	protected InputStreamX     in                 = new InputStreamViewX();
	protected boolean          inputStreamClosed  = false;
	protected OutputStreamX    out                = new OutputStreamViewX();
	protected boolean          outputStreamClosed = false;

	public CircularByteBufferX() {
		this(DEFAULT_CAPACITY, true);
	}
	public CircularByteBufferX(int _capacity) {
		this(_capacity, true);
	}
	public CircularByteBufferX(boolean _blockingWrite) {
		this(DEFAULT_CAPACITY, _blockingWrite);
	}
	public CircularByteBufferX(int size, boolean _blockingWrite) {
		super(_blockingWrite, size == INFINITE_SIZE);
		buffer = new byte[size == INFINITE_SIZE ? DEFAULT_CAPACITY : size];
	}

	@SuppressWarnings("unchecked")
	public <T extends InputStreamX & Seekable> T  	getInputStream() {
		return (T) in;
	}
	public OutputStreamX 							getOutputStream() {
		return out;
	}

	@Override
	public byte[] 		array() throws ReadOnlyBufferException, UnsupportedOperationException {
		return buffer;
	}

	@Override
	public BufferX      clear() {
		synchronized (this) {
			super.clear();
			outputStreamClosed = false;
			inputStreamClosed  = false;
		}
		return this;
	}

	@Override
	protected int 		capacity_impl() {
		return buffer.length;
	}

	private void 		resize() {
		byte[] newBuffer = new byte[buffer.length * 2];
		int    marked    = marked();
		int    available = remaining_impl();

		if (markPosition <= writePosition) {
			int length = writePosition - markPosition;
			System.arraycopy(buffer, markPosition, newBuffer, 0, length);
		} else {
			int length1 = buffer.length - markPosition;
			int length2 = writePosition;
			System.arraycopy(buffer, markPosition, newBuffer, 0, length1);
			System.arraycopy(buffer, 0, newBuffer, length1, length2);
		}

		buffer        = newBuffer;
		markPosition  = 0;
		readPosition  = marked;
		writePosition = marked + available;
	}

	private int 		marked() {
		if (markPosition <= readPosition)
			return (readPosition - markPosition);

		return (buffer.length - (markPosition - readPosition));
	}
	private void 		ensureMark() {
		if (marked() > markSize) {
			markPosition = readPosition;
			markSize = 0;
		}
	}

	@Override
	public ByteOrder order() {
		return null;
	}
	@Override
	public ByteBufferX order(ByteOrder bo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX compact() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX slice() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX duplicate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX asReadOnlyBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte get() {
		return buffer[readPosition++];
	}
	@Override
	public byte get(int index) {
		return buffer[readPosition + index];
	}
	@Override
	public ByteBufferX get(byte[] dst) {
		return get(dst, 0, dst.length);
	}
	@Override
	public ByteBufferX get(byte[] dst, int offset, int length) {
		if(writePosition - readPosition < length - offset)
			throw new BufferUnderflowException();

		System.arraycopy(buffer, readPosition, dst, offset, length);
		readPosition += length - offset;

		return this;
	}
	@Override
	public char getChar() {
		return (char) (get() << 8 + get());
	}
	@Override
	public char getChar(int index) {
		return (char) (get(index) << 8 + get(index + 1));
	}
	@Override
	public short getShort() {
		return (short) (get() << 8 + get());
	}
	@Override
	public short getShort(int index) {
		return (short) (get(index) << 8 + get(index + 1));
	}
	@Override
	public int getInt() {
		return (int) (get() << 24 + get() << 16 + get() << 8 + get());
	}
	@Override
	public int getInt(int index) {
		return (int) (get(index) << 24 + get(index + 1) << 16 + get(index + 2) << 8 + get(index + 3));
	}
	@Override
	public long getLong() {
		return (long) (get() << 56 + get() << 48 + get() << 40 + get() << 32 + get() << 24 + get() << 16 + get() << 8 + get());
	}
	@Override
	public long getLong(int index) {
		return (long) (get(index) << 56 + get(index + 1) << 48 + get(index + 2) << 40 + get(index + 3) << 32 + get(index + 4) << 24 + get(index + 5) << 16 + get(index + 6) << 8 + get(index + 7));
	}
	@Override
	public float getFloat() {
		return ByteBuffer.wrap(buffer, readPosition, readPosition + Float.BYTES).getFloat();
	}
	@Override
	public float getFloat(int index) {
		return ByteBuffer.wrap(buffer, readPosition + index, readPosition + index + Float.BYTES).getFloat();
	}
	@Override
	public double getDouble() {
		return ByteBuffer.wrap(buffer, readPosition, readPosition + Double.BYTES).getDouble();
	}
	@Override
	public double getDouble(int index) {
		return ByteBuffer.wrap(buffer, readPosition + index, readPosition + index + Double.BYTES).getDouble();
	}

	@Override
	public CharBuffer asCharBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ShortBuffer asShortBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IntBuffer asIntBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public LongBuffer asLongBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public FloatBuffer asFloatBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DoubleBuffer asDoubleBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX put(byte b) {
		buffer[readPosition++] = b;
		return this;
	}
	@Override
	public ByteBufferX put(int index, byte b) {
		buffer[readPosition + index] = b;
		return this;
	}
	@Override
	public ByteBufferX put(byte[] src) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX put(byte[] src, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX put(ByteBuffer src) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX put(ByteBufferX src) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putChar(char value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putChar(int index, char value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putShort(short value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putShort(int index, short value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putInt(int value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putInt(int index, int value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putFloat(float value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putFloat(int index, float value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putLong(long value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putLong(int index, long value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putDouble(double value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ByteBufferX putDouble(int index, double value) {
		// TODO Auto-generated method stub
		return null;
	}

}