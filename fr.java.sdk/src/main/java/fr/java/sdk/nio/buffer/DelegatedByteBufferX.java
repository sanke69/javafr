package fr.java.sdk.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import fr.java.nio.buffer.ByteBufferX;

public class DelegatedByteBufferX extends DelegateBufferX<ByteBuffer> implements ByteBufferX.Editable {

	private final ByteBuffer delegate;

	public DelegatedByteBufferX(int _capacity) {
		super();
		delegate = ByteBuffer.allocate(_capacity);
	}
	public DelegatedByteBufferX(byte[] _wrapped) {
		super();
		delegate = ByteBuffer.wrap(_wrapped);
	}
	public DelegatedByteBufferX(ByteBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final ByteBuffer 		delegate() {
		return delegate;
	}

	@Override
	public final byte[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}
	@Override
	public final ByteBufferX 		order(ByteOrder bo) {
		delegate.order(bo);
		return this;
	}

	@Override
	public final ByteBufferX 		compact() {
		delegate.compact();
		return this;
	}
	@Override
	public final ByteBufferX 		slice() {
		delegate.slice();
		return this;
	}
	@Override
	public final ByteBufferX 		duplicate() {
		delegate.duplicate();
		return this;
	}
	@Override
	public final ByteBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return this;
	}

	@Override
	public final ByteBufferX 		put(byte b) {
		delegate.put(b);
		return this;
	}
	@Override
	public final ByteBufferX 		put(int index, byte b) {
		delegate.put(index, b);
		return this;
	}
	@Override
	public final ByteBufferX 		put(byte[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final ByteBufferX 		put(byte[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final ByteBufferX 		put(ByteBuffer src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final ByteBufferX 		put(ByteBufferX src) {
		if(src.hasArray())
			delegate.put(src.array());
		else
			for(int i = 0; i < src.remaining(); ++i)
				delegate.put(src.get());
		return this;
	}

	@Override
	public final ByteBufferX 		putChar(char value) {
		delegate.putChar(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putChar(int index, char value) {
		delegate.putChar(index, value);
		return this;
	}
	@Override
	public final ByteBufferX 		putShort(short value) {
		delegate.putShort(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putShort(int index, short value) {
		delegate.putShort(index, value);
		return this;
	}
	@Override
	public final ByteBufferX 		putInt(int value) {
		delegate.putInt(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putInt(int index, int value) {
		delegate.putInt(index, value);
		return this;
	}
	@Override
	public final ByteBufferX 		putLong(long value) {
		delegate.putLong(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putLong(int index, long value) {
		delegate.putLong(index, value);
		return this;
	}
	@Override
	public final ByteBufferX 		putFloat(float value) {
		delegate.putFloat(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putFloat(int index, float value) {
		delegate.putFloat(index, value);
		return this;
	}
	@Override
	public final ByteBufferX 		putDouble(double value) {
		delegate.putDouble(value);
		return this;
	}
	@Override
	public final ByteBufferX 		putDouble(int index, double value) {
		delegate.putDouble(index, value);
		return this;
	}

	@Override
	public final byte 				get() {
		return delegate.get();
	}
	@Override
	public final byte 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final ByteBufferX 		get(byte[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final ByteBufferX 		get(byte[] dst, int offset, int length) {
		delegate.get(dst, offset, length);
		return this;
	}

	@Override
	public final char 				getChar() {
		return delegate.getChar();
	}
	@Override
	public final char 				getChar(int index) {
		return delegate.getChar(index);
	}
	@Override
	public final short 				getShort() {
		return delegate.getShort();
	}
	@Override
	public final short 				getShort(int index) {
		return delegate.getShort(index);
	}
	@Override
	public final int 				getInt() {
		return delegate.getInt();
	}
	@Override
	public final int 				getInt(int index) {
		return delegate.getInt(index);
	}
	@Override
	public final long 				getLong() {
		return delegate.getLong();
	}
	@Override
	public final long 				getLong(int index) {
		return delegate.getLong(index);
	}
	@Override
	public final float 				getFloat() {
		return delegate.getFloat();
	}
	@Override
	public final float 				getFloat(int index) {
		return delegate.getFloat(index);
	}
	@Override
	public final double 			getDouble() {
		return delegate.getDouble();
	}
	@Override
	public final double 			getDouble(int index) {
		return delegate.getDouble(index);
	}

	@Override
	public CharBuffer 				asCharBuffer() {
		return delegate.asCharBuffer();
	}
	@Override
	public ShortBuffer 				asShortBuffer() {
		return delegate.asShortBuffer();
	}
	@Override
	public IntBuffer 				asIntBuffer() {
		return delegate.asIntBuffer();
	}
	@Override
	public LongBuffer 				asLongBuffer() {
		return delegate.asLongBuffer();
	}
	@Override
	public FloatBuffer 				asFloatBuffer() {
		return delegate.asFloatBuffer();
	}
	@Override
	public DoubleBuffer 			asDoubleBuffer() {
		return delegate.asDoubleBuffer();
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
	public int 						compareTo(ByteBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
