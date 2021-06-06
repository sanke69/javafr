package fr.java.sdk.nio.buffer;

import java.nio.ByteOrder;
import java.nio.LongBuffer;

import fr.java.nio.buffer.LongBufferX;

public class DelegatedLongBufferX extends DelegateBufferX<LongBuffer> implements LongBufferX.Editable {

	private final LongBuffer delegate;

	public DelegatedLongBufferX(int _capacity) {
		super();
		delegate = LongBuffer.allocate(_capacity);
	}
	public DelegatedLongBufferX(long[] _wrapped) {
		super();
		delegate = LongBuffer.wrap(_wrapped);
	}
	public DelegatedLongBufferX(LongBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final LongBuffer 		delegate() {
		return delegate;
	}

	@Override
	public final long[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final LongBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final LongBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final LongBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final LongBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final LongBufferX 		put(long l) {
		delegate.put(l);
		return this;
	}
	@Override
	public final LongBufferX 		put(int index, long l) {
		delegate.put(index, l);
		return this;
	}
	@Override
	public final LongBufferX 		put(long[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final LongBufferX 		put(long[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final LongBufferX 		put(LongBuffer src) {
		delegate.put(src);
		return this;
	}

	@Override
	public final long 				get() {
		return delegate.get();
	}
	@Override
	public final long 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final LongBufferX 		get(long[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final LongBufferX 		get(long[] dst, int offset, int length) {
		delegate.get(dst, offset, length);
		return this;
	}

	@Override
	public int 						hashCode() {
		return delegate.hashCode();
	}
	@Override
	public boolean 					equals(Object ob) {
		return delegate.equals(ob);
	}
//	@Override
	public int 						compareTo(LongBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
