package fr.java.sdk.nio.buffer.circular;

import java.nio.BufferOverflowException;

import fr.java.nio.buffer.BufferX;
import fr.java.nio.buffer.circular.CircularBufferX;

public class CircularObjectBufferX<ElementType> extends CircularBufferBaseX {
	private final static int DEFAULT_SIZE = 1024;
	public final static int INFINITE_SIZE = -1;

	protected ElementType[] 	buffer;

	protected boolean 			inputDone     = false;

	public CircularObjectBufferX() {
		this(DEFAULT_SIZE, true);
	}
	public CircularObjectBufferX(int size) {
		this(size, true);
	}
	public CircularObjectBufferX(boolean blockingWrite) {
		this(DEFAULT_SIZE, blockingWrite);
	}
	public CircularObjectBufferX(int size, boolean blockingWrite) {
		super(blockingWrite);
		if (size == INFINITE_SIZE) {
			buffer = createArray(DEFAULT_SIZE);
			infinite = true;
		} else {
			buffer = createArray(size);
			infinite = false;
		}
		this.blockingWrite = blockingWrite;
	}

	protected int 				capacity_impl() {
		return buffer.length;
	}


	public ElementType 		read() throws InterruptedException {
		while (true) {
			synchronized (this) {
				int available = remaining_impl();
				if (available > 0) {
					ElementType result = buffer[readPosition()];
					readPositionIncrement();
					return result;
				} else if (inputDone)
					return null;
			}
			Thread.sleep(CircularBufferX.DEFAULT_SLEEP_MS);
		}
	}
	public int 				read(ElementType[] buf) throws InterruptedException {
		return read(buf, 0, buf.length);
	}
	public int 				read(ElementType[] buf, int off, int len) throws InterruptedException {
		while (true) {
			synchronized (this) {
				int available = remaining_impl();
				if (available > 0) {
					int length    = Math.min(len, available);
					int firstLen  = Math.min(length, buffer.length - readPosition);
					int secondLen = length - firstLen;
					System.arraycopy(buffer, readPosition, buf, off, firstLen);

					if (secondLen > 0) {
						System.arraycopy(buffer, 0, buf, off + firstLen, secondLen);
						readPositionSet(secondLen);
					} else
						readPositionIncrement(length);

					return length;
				} else if (inputDone)
					return -1;
			}
			Thread.sleep(CircularBufferX.DEFAULT_SLEEP_MS);
		}
	}

	public long 			skip(long n) throws InterruptedException, IllegalArgumentException {
		while (true) {
			synchronized (this) {
				int available = remaining_impl();
				if (available > 0) {
					int length = Math.min((int) n, available);
					int firstLen = Math.min(length, buffer.length - readPosition);
					int secondLen = length - firstLen;
					if (secondLen > 0) {
						readPositionSet(secondLen);
					} else {
						readPositionIncrement(length);
					}

					return length;
				} else if (inputDone) {
					return 0;
				}
			}
			Thread.sleep(CircularBufferX.DEFAULT_SLEEP_MS);
		}
	}

	public void 			write(ElementType o) throws BufferOverflowException, IllegalStateException, InterruptedException {
		boolean written = false;
		while (!written) {
			synchronized (CircularObjectBufferX.this) {
				if (inputDone)
					throw new IllegalStateException(
							"CircularObjectBuffer.done() has been called, CircularObjectBuffer.write() failed.");
				int spaceLeft = free_impl();
				while (infinite && spaceLeft < 1) {
					resize();
					spaceLeft = free_impl();
				}
				if (!blockingWrite && spaceLeft < 1)
					throw new BufferOverflowException(/*"CircularObjectBuffer is full; cannot write 1 Object"*/);
				if (spaceLeft > 0) {
					buffer[writePosition] = o;
					writePositionIncrement();
					written = true;
				}
			}

			if (!written)
				Thread.sleep(CircularBufferX.DEFAULT_SLEEP_MS);
		}
	}
	public void 			write(ElementType[] buf) throws BufferOverflowException, IllegalStateException, InterruptedException {
		write(buf, 0, buf.length);
	}
	public void 			write(ElementType[] buf, int off, int len)
			throws BufferOverflowException, IllegalStateException, InterruptedException {
		while (len > 0) {
			synchronized (CircularObjectBufferX.this) {
				if (inputDone)
					throw new IllegalStateException("CircularObjectBuffer.done() has been called, CircularObjectBuffer.write() failed.");

				int spaceLeft = free_impl();
				while(infinite && spaceLeft < len) {
					resize();
					spaceLeft = free_impl();
				}

				if (!blockingWrite && spaceLeft < len)
					throw new BufferOverflowException(/*"CircularObjectBuffer is full; cannot write " + len + " Objects"*/);

				int realLen   = Math.min(len, spaceLeft);
				int firstLen  = Math.min(realLen, buffer.length - writePosition);
				int secondLen = Math.min(realLen - firstLen, buffer.length - readPosition - 1);
				int written   = firstLen + secondLen;

				if (firstLen > 0)
					System.arraycopy(buf, off, buffer, writePosition, firstLen);

				if (secondLen > 0) {
					System.arraycopy(buf, off + firstLen, buffer, 0, secondLen);
					writePositionIncrement();
				} else
					writePositionIncrement(written);

				off += written;
				len -= written;
			}

			if (len > 0)
				Thread.sleep(CircularBufferX.DEFAULT_SLEEP_MS);
		}
	}

	public void 			done() {
		synchronized (this) {
			inputDone     = true;
		}
	}

	public BufferX 			clear() {
		synchronized (this) {
			super.clear();
			inputDone = false;
		}
		return this;
	}

	private void 			resize() {
		resize(2 * capacity());
		/*
		ElementType[] newBuffer = createArray(buffer.length * 2);
		int available = available_impl();
		if (readPosition <= writePosition) {
			int length = writePosition - readPosition;
			System.arraycopy(buffer, readPosition, newBuffer, 0, length);
		} else {
			int length1 = buffer.length - readPosition;
			System.arraycopy(buffer, readPosition, newBuffer, 0, length1);
			int length2 = writePosition;
			System.arraycopy(buffer, 0, newBuffer, length1, length2);
		}
		buffer = newBuffer;
		readPosition = 0;
		writePosition = available;
		*/
	}
	private void 			resize(int _newSize) {
		if(_newSize < capacity())
			return ;

		ElementType[] newBuffer = createArray(_newSize);
		int available = remaining_impl();
		if (readPosition <= writePosition) {
			int length = writePosition - readPosition;
			System.arraycopy(buffer, readPosition, newBuffer, 0, length);
		} else {
			int length1 = buffer.length - readPosition;
			System.arraycopy(buffer, readPosition, newBuffer, 0, length1);
			int length2 = writePosition;
			System.arraycopy(buffer, 0, newBuffer, length1, length2);
		}
		buffer        = newBuffer;
		readPosition  = 0;
		writePosition = available;
	}

	@SuppressWarnings("unchecked")
	private ElementType[] 	createArray(int size) {
		return (ElementType[]) new Object[size];
	}

}