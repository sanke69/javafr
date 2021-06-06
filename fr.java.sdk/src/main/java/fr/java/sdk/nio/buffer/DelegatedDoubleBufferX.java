package fr.java.sdk.nio.buffer;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import fr.java.nio.buffer.DoubleBufferX;

public class DelegatedDoubleBufferX extends DelegateBufferX<DoubleBuffer> implements DoubleBufferX.Editable {

	private final DoubleBuffer delegate;

	public DelegatedDoubleBufferX(int _capacity) {
		super();
		delegate = DoubleBuffer.allocate(_capacity);
	}
	public DelegatedDoubleBufferX(double[] _wrapped) {
		super();
		delegate = DoubleBuffer.wrap(_wrapped);
	}
	public DelegatedDoubleBufferX(DoubleBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final DoubleBuffer 	delegate() {
		return delegate;
	}

	@Override
	public final double[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final DoubleBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final DoubleBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final DoubleBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final DoubleBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final DoubleBufferX 		put(double d) {
		delegate.put(d);
		return this;
	}
	@Override
	public final DoubleBufferX 		put(int index, double d) {
		delegate.put(index, d);
		return this;
	}
	@Override
	public final DoubleBufferX 		put(double[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final DoubleBufferX 		put(double[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final DoubleBufferX 		put(DoubleBuffer src) {
		delegate.put(src);
		return this;
	}

	@Override
	public final double 			get() {
		return delegate.get();
	}
	@Override
	public final double 			get(int index) {
		return delegate.get(index);
	}
	@Override
	public final DoubleBufferX 		get(double[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final DoubleBufferX 		get(double[] dst, int offset, int length) {
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
	public int 						compareTo(DoubleBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
