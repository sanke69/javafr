package fr.java.sdk.nio.buffer;

import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import fr.java.nio.buffer.ShortBufferX;

public class DelegatedShortBufferX extends DelegateBufferX<ShortBuffer> implements ShortBufferX.Editable {

	private final ShortBuffer delegate;

	public DelegatedShortBufferX(int _capacity) {
		super();
		delegate = ShortBuffer.allocate(_capacity);
	}
	public DelegatedShortBufferX(short[] _wrapped) {
		super();
		delegate = ShortBuffer.wrap(_wrapped);
	}
	public DelegatedShortBufferX(ShortBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final ShortBuffer		delegate() {
		return delegate;
	}

	@Override
	public final short[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final ShortBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final ShortBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final ShortBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final ShortBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final ShortBufferX 		put(short s) {
		delegate.put(s);
		return this;
	}
	@Override
	public final ShortBufferX 		put(int index, short s) {
		delegate.put(index, s);
		return this;
	}
	@Override
	public final ShortBufferX 		put(short[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final ShortBufferX 		put(short[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final ShortBufferX 		put(ShortBuffer src) {
		delegate.put(src);
		return this;
	}

	@Override
	public final short 				get() {
		return delegate.get();
	}
	@Override
	public final short 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final ShortBufferX 		get(short[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final ShortBufferX 		get(short[] dst, int offset, int length) {
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
	public int 						compareTo(ShortBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
