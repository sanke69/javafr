package fr.java.sdk.nio.buffer;

import java.nio.ByteOrder;
import java.nio.IntBuffer;

import fr.java.nio.buffer.IntBufferX;

public class DelegatedIntBufferX extends DelegateBufferX<IntBuffer> implements IntBufferX.Editable {

	private final IntBuffer delegate;

	public DelegatedIntBufferX(int _capacity) {
		super();
		delegate = IntBuffer.allocate(_capacity);
	}
	public DelegatedIntBufferX(int[] _wrapped) {
		super();
		delegate = IntBuffer.wrap(_wrapped);
	}
	public DelegatedIntBufferX(IntBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final IntBuffer 		delegate() {
		return delegate;
	}

	@Override
	public final int[] 				array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final IntBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final IntBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final IntBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final IntBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final IntBufferX 		put(int i) {
		delegate.put(i);
		return this;
	}
	@Override
	public final IntBufferX 		put(int index, int i) {
		delegate.put(index, i);
		return this;
	}
	@Override
	public final IntBufferX 		put(int[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final IntBufferX 		put(int[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final IntBufferX 		put(IntBuffer src) {
		delegate.put(src);
		return this;
	}

	@Override
	public final int 				get() {
		return delegate.get();
	}
	@Override
	public final int 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final IntBufferX 		get(int[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final IntBufferX 		get(int[] dst, int offset, int length) {
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
	public int 						compareTo(IntBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
