package fr.java.sdk.nio.buffer;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.InvalidMarkException;
import java.nio.ReadOnlyBufferException;

import fr.java.nio.buffer.BufferX;

public abstract class DelegateBufferX<B extends Buffer> implements BufferX {

	protected abstract B 	delegate();

	@Override
	public final boolean 	isDirect() {
		return delegate().isDirect();
	}
	@Override
	public final boolean 	isReadOnly() {
		return delegate().isDirect();
	}

	@Override
	public final boolean 	hasRemaining() {
		return delegate().isDirect();
	}
	@Override
	public final int 		remaining() {
		return delegate().remaining();
	}

	@Override
	public final int 		capacity() {
		return delegate().capacity();
	}

	@Override
	public final BufferX 	position(int newPosition) throws IllegalArgumentException {
		delegate().position(newPosition);
		return this;
	}
	@Override
	public final int 		position() {
		return delegate().position();
	}

	@Override
	public final BufferX 	limit(int newLimit) {
		delegate().limit(newLimit);
		return this;
	}
	@Override
	public final int 		limit() throws IllegalArgumentException {
		return delegate().limit();
	}

	@Override
	public final BufferX 	mark() throws InvalidMarkException {
		delegate().mark();
		return this;
	}
	@Override
	public final BufferX 	reset() throws InvalidMarkException, IOException {
		delegate().reset();
		return this;
	}

	@Override
	public final BufferX 	flip() {
		delegate().flip();
		return this;
	}
	@Override
	public final BufferX 	rewind() {
		delegate().rewind();
		return this;
	}
	@Override
	public final BufferX 	clear() {
		delegate().clear();
		return this;
	}

	@Override
	public final boolean 	hasArray() {
		return delegate().hasArray();
	}
	@Override
	public Object 			array() throws ReadOnlyBufferException, UnsupportedOperationException {
		return delegate().array();
	}
	@Override
	public final int 		arrayOffset() throws ReadOnlyBufferException, UnsupportedOperationException {
		return delegate().arrayOffset();
	}

}
