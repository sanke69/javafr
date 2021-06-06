package fr.java.sdk.nio.buffer;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import fr.java.nio.buffer.FloatBufferX;

public class DelegatedFloatBufferX extends DelegateBufferX<FloatBuffer> implements FloatBufferX.Editable {

	private final FloatBuffer delegate;

	public DelegatedFloatBufferX(int _capacity) {
		super();
		delegate = FloatBuffer.allocate(_capacity);
	}
	public DelegatedFloatBufferX(float[] _wrapped) {
		super();
		delegate = FloatBuffer.wrap(_wrapped);
	}
	public DelegatedFloatBufferX(FloatBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final FloatBuffer 	delegate() {
		return delegate;
	}

	@Override
	public final float[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final FloatBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final FloatBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final FloatBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final FloatBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final FloatBufferX 		put(float f) {
		delegate.put(f);
		return this;
	}
	@Override
	public final FloatBufferX 		put(int index, float f) {
		delegate.put(index, f);
		return this;
	}
	@Override
	public final FloatBufferX 		put(float[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final FloatBufferX 		put(float[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final FloatBufferX 		put(FloatBuffer src) {
		delegate.put(src);
		return this;
	}

	@Override
	public final float 				get() {
		return delegate.get();
	}
	@Override
	public final float 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final FloatBufferX 		get(float[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final FloatBufferX 		get(float[] dst, int offset, int length) {
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
	public int 						compareTo(FloatBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
