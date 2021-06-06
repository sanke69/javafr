package fr.java.sdk.nio.buffer;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.stream.IntStream;

import fr.java.nio.buffer.CharBufferX;

public class DelegatedCharBufferX extends DelegateBufferX<CharBuffer> implements CharBufferX.Editable {

	private final CharBuffer delegate;

	public DelegatedCharBufferX(int _capacity) {
		super();
		delegate = CharBuffer.allocate(_capacity);
	}
	public DelegatedCharBufferX(char[] _wrapped) {
		super();
		delegate = CharBuffer.wrap(_wrapped);
	}
	public DelegatedCharBufferX(CharBuffer _wrapped) {
		super();
		delegate = _wrapped;
	}

	@Override
	protected final CharBuffer 		delegate() {
		return delegate;
	}

	@Override
	public final char[] 			array() {
		return delegate.array();
	}

	@Override
	public final ByteOrder 			order() {
		return delegate.order();
	}

	@Override
	public final CharBufferX 		compact() {
		delegate.compact();
		return null;
	}
	@Override
	public final CharBufferX 		slice() {
		delegate.slice();
		return null;
	}
	@Override
	public final CharBufferX 		duplicate() {
		delegate.duplicate();
		return null;
	}
	@Override
	public final CharBufferX 		asReadOnlyBuffer() {
		delegate.asReadOnlyBuffer();
		return null;
	}

	@Override
	public final CharBufferX 		put(char c) {
		delegate.put(c);
		return this;
	}
	@Override
	public final CharBufferX 		put(int index, char c) {
		delegate.put(index, c);
		return this;
	}
	@Override
	public final CharBufferX 		put(char[] src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final CharBufferX 		put(char[] src, int offset, int length) {
		delegate.put(src, offset, length);
		return this;
	}
	@Override
	public final CharBufferX 		put(CharBuffer src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final CharBufferX 		put(String src) {
		delegate.put(src);
		return this;
	}
	@Override
	public final CharBufferX 		put(String src, int start, int end) {
		delegate.put(src, start, end);
		return this;
	}

	@Override
	public final char 				get() {
		return delegate.get();
	}
	@Override
	public final char 				get(int index) {
		return delegate.get(index);
	}
	@Override
	public final CharBufferX 		get(char[] dst) {
		delegate.get(dst);
		return this;
	}
	@Override
	public final CharBufferX 		get(char[] dst, int offset, int length) {
		delegate.get(dst, offset, length);
		return this;
	}

	@Override
	public final int 				read(CharBuffer target) throws IOException {
		return delegate.read(target);
	}

	@Override
	public final int 				length() {
		return delegate.length();
	}

	@Override
	public final IntStream 			chars() {
		return delegate.chars();
	}
	@Override
	public final IntStream 			codePoints() {
		return delegate.codePoints();
	}

	@Override
	public final char 				charAt(int index) {
		return delegate.charAt(index);
	}
	@Override
	public final CharBuffer 		subSequence(int start, int end) {
		return delegate.subSequence(start, end);
	}

	@Override
	public final CharBuffer 		append(char c) {
		return delegate.append(c);
	}
	@Override
	public final CharBuffer 		append(CharSequence csq) {
		return delegate.append(csq);
	}
	@Override
	public final CharBuffer 		append(CharSequence csq, int start, int end) {
		return delegate.append(csq, start, end);
	}

	@Override
	public int 						hashCode() {
		return delegate.hashCode();
	}
	@Override
	public boolean 					equals(Object ob) {
		return delegate.equals(ob);
	}
	@Override
	public int 						compareTo(CharBuffer that) {
		return delegate.compareTo(that);
	}
	@Override
	public String 					toString() {
		return delegate.toString();
	}

}
