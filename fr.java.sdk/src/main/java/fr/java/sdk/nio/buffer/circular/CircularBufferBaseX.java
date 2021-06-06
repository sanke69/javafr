package fr.java.sdk.nio.buffer.circular;

import java.io.IOException;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;

import fr.java.nio.buffer.BufferX;
import fr.java.nio.buffer.circular.CircularBufferX;

public abstract class CircularBufferBaseX implements CircularBufferX {

	protected boolean 			blockingWrite    = true;
	protected volatile boolean 	infinite         = false;

	protected volatile int 		readPosition     = 0;
	protected volatile int 		writePosition    = 0;
	protected volatile int 		limitPosition    = 0;
	protected volatile int 		markPosition     = 0,
								markLength       = 0;
	protected volatile int 		position     	 = 0;

	public CircularBufferBaseX() {
		this(true, false);
	}
	public CircularBufferBaseX(boolean _blockingWrite) {
		this(_blockingWrite, false);
	}
	public CircularBufferBaseX(boolean _blockingWrite, boolean _infinite) {
		super();
		blockingWrite = _blockingWrite;
		infinite      = _infinite;
	}
	
	@Override
	public final boolean 	isDirect() {
		return true;
	}
	@Override
	public final boolean 	isReadOnly() {
		return true;
	}

	@Override
	public final boolean 	hasRemaining() {
		return remaining_impl() > 0;
	}
	@Override
	public final int 		remaining() {
		synchronized (this) {
			return remaining_impl();
		}
	}

	@Override
	public final boolean 	hasFree() {
		return free_impl() > 0;
	}
	@Override
	public final int 		free() {
		synchronized (this) {
			return free_impl();
		}
	}

	public final int 		capacity() {
		synchronized (this) {
			return capacity_impl();
		}
	}

	@Override
	public final BufferX 	position(int newPosition) throws IllegalArgumentException {
		if(readPosition < writePosition) {
			position = newPosition;
		} else {
			position = capacity() - writePosition - newPosition;
		}
		readPosition = newPosition;
		
		System.err.println("Not Yet Implemented");
		
		return this;
	}
	@Override
	public final int 		position() {
		return 0;
	}

	@Override
	public final BufferX 	limit(int newLimit) throws IllegalArgumentException {
		synchronized (CircularBufferBaseX.this) {
			if (capacity() - 1 <= newLimit)
				throw new IllegalArgumentException("Cannot limit stream, 'newLimit' bigger than buffer size.");
			limitPosition = newLimit;
		}
		return this;
	}
	@Override
	public final int 		limit() throws IllegalArgumentException {
		return limitPosition;
	}

	@Override
	public final BufferX 	mark() throws InvalidMarkException {
		synchronized (CircularBufferBaseX.this) {
			markPosition = readPosition;
		}
		return this;
	}
	public final BufferX 	mark(int readAheadLimit) throws IOException {
		synchronized (CircularBufferBaseX.this) {
			if (capacity() - 1 <= readAheadLimit)
				throw new IOException("Cannot mark stream, readAheadLimit bigger than buffer size.");
			markLength   = readAheadLimit;
			markPosition = readPosition;
		}
		return this;
	}
	@Override
	public final BufferX 	reset() throws IOException {
		synchronized (CircularBufferBaseX.this) {
			markLength   = -1;
			markPosition = -1;
		}
		return this;
	}

	@Override
	public final BufferX 	flip() {
		return this;
	}
	@Override
	public final BufferX 	rewind() {
		return this;
	}
	public BufferX 			clear() {
		synchronized (this) {
			position	  = 0;
			readPosition  = 0;
			writePosition = 0;
			limitPosition = 0;
			markPosition  = 0;
		}
		return this;
	}

	@Override
	public final boolean 	hasArray() {
		return false;
	}
	@Override
	public Object 			array() throws ReadOnlyBufferException, UnsupportedOperationException {
		return null;
	}
	@Override
	public final int 		arrayOffset() throws ReadOnlyBufferException, UnsupportedOperationException {
		return readPosition;
	}

	protected abstract int  capacity_impl();
	protected final int 	remaining_impl() {
		if (readPosition <= writePosition)
			return (writePosition - readPosition);
		return (capacity() - (readPosition - writePosition));
	}
	protected final int 	free_impl() {
		if (writePosition < readPosition)
			return (readPosition - writePosition - 1);
		return ((capacity() - 1) - (writePosition - readPosition));
	}

	protected final void	waitAccess() throws IOException {
		try {
			Thread.sleep(DEFAULT_SLEEP_MS);
		} catch (Exception x) {
			throw new IOException("Blocking read operation interrupted.");
		}
	}

	protected final int 	readPosition() {
		return readPosition;
	}
	protected final void 	readPositionSet(int _readPosition) {
		readPosition = _readPosition;
		if (readPosition == capacity_impl())
			readPosition = 0;
	}
	protected final void 	readPositionIncrement() {
		readPosition++;
		if (readPosition == capacity_impl())
			readPosition = 0;
	}
	protected final void 	readPositionIncrement(int _increment) {
		readPosition += _increment;
		if (readPosition == capacity_impl())
			readPosition = 0;
	}

	protected final int 	writePosition() {
		return writePosition;
	}
	protected final void 	writePositionSet(int _writePosition) {
		writePosition = _writePosition;
		if (writePosition == capacity_impl())
			writePosition = 0;
	}
	protected final void	writePositionIncrement() {
		writePosition++;
		if (writePosition == capacity_impl())
			writePosition = 0;
	}
	protected final void 	writePositionIncrement(int _increment) {
		writePosition += _increment;
		if (writePosition == capacity_impl())
			writePosition = 0;
	}

}
