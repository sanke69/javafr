package fr.java.sdk.nio.buffer;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.algebra.Vectors;
import fr.java.nio.buffer.Vector3DBufferX;

public class Vector3DDoubleBufferX implements Vector3DBufferX {
	DoubleBuffer buffer;

	public Vector3DDoubleBufferX(int _nbPoints) {
		super();
		buffer = DoubleBuffer.allocate(_nbPoints * 3);
	}
	public Vector3DDoubleBufferX(DoubleBuffer _buffer) {
		super();
		buffer = _buffer;
	}

	@Override
	public boolean 			isDirect() {
		return buffer.isDirect();
	}
	@Override
	public boolean 			isReadOnly() {
		return buffer.isReadOnly();
	}

	@Override
	public DoubleBuffer 	Buffer() {
		return buffer;
	}

	@Override
	public int 				capacity() {
		return buffer.capacity() / 3; 
	}

	@Override
	public int				position() {
		return buffer.position() / 3; 
	}
	@Override
	public Vector3DBufferX 	position(int _newPosition) {
		buffer.position(3 * _newPosition);
		return this;
	}

	@Override
	public int 				limit() {
		return buffer.limit() / 3;
	}
	@Override
    public Vector3DBufferX 	limit(int _newLimit) {
		buffer.limit(_newLimit * 3);
		return this;
    }

	@Override
	public Vector3DBufferX 	mark() {
		buffer.mark();
		return this;
	}
	@Override
    public Vector3DBufferX 	reset() {
		buffer.reset();
		return this;
	}
	@Override
    public Vector3DBufferX 	clear() {
		buffer.clear();
		return this;
	}
	@Override
    public Vector3DBufferX 	rewind() {
		buffer.rewind();
		return this;
	}
	@Override
	public Vector3DBufferX 	flip() {
		buffer.flip();
		return this;
	}
	@Override
    public int 				remaining() {
		return buffer.remaining() / 3;
	}

	@Override
	public Vector3D			get() {
		if(position() > capacity())
			throw new BufferOverflowException();
		return Vectors.of(buffer.get(), buffer.get(), buffer.get()); 
	}
	@Override
	public Vector3D			get(int _index) {
		if(_index > capacity())
			throw new BufferOverflowException();
		return Vectors.of(buffer.get(3 * _index), buffer.get(3 * _index + 1), buffer.get(3 * _index + 2)); 
	}
	@Override
	public Vector3DBufferX 	get(Vector3D.Editable[] _dst, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();
		
		for(int i = _offset; i < _length - _offset; ++i)
			_dst[i].set(buffer.get(), buffer.get(), buffer.get());
		
		return this;
	}

	@Override
	public Vector3DBufferX 	put(Vector3D _pt) {
		buffer.put((float) _pt.getX());
		buffer.put((float) _pt.getY());
		buffer.put((float) _pt.getZ());
		return this;
	}
	@Override
	public Vector3DBufferX 	put(int _index, Vector3D _pt) {
		buffer.put(3 * _index, _pt.getX());
		buffer.put(3 * _index + 1, _pt.getY());
		buffer.put(3 * _index + 2, _pt.getZ());
		return this;
	}
	@Override
	public Vector3DBufferX 	put(Vector3D[] _src, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();

		for(int i = _offset; i < _length - _offset; ++i)
			buffer.put((float) _src[i].getX()).put((float) _src[i].getY()).put((float) _src[i].getZ());

		return this;
	}
	@Override
	public Vector3DBufferX 	put(Vector3DBufferX _src) {
		if(_src.remaining() > remaining())
			throw new BufferOverflowException();

		if(!(_src instanceof Vector3DDoubleBufferX))
			throw new NotYetImplementedException();

		buffer.put(((Vector3DDoubleBufferX) _src).buffer);

		return this;
	}
	@Override
	public Vector3DBufferX 	put(Buffer _src) {
		if(!(_src instanceof DoubleBuffer))
			throw new NotYetImplementedException();

		if(_src.remaining() / 3 > remaining())
			throw new BufferOverflowException();

		buffer.put((DoubleBuffer) _src);

		return this;
	}

	@Override
	public boolean			hasArray() {
		return buffer.hasArray();
	}
	@Override
	public Object 			array() throws ReadOnlyBufferException, UnsupportedOperationException {
		return buffer.array();
	}
	@Override
	public int 				arrayOffset() throws ReadOnlyBufferException, UnsupportedOperationException {
		return buffer.arrayOffset();
	}
	@Override
	public Vector3D[] 		arrayCopy() {
		buffer.rewind();

		Vector3D[] copy = new Vector3D[capacity()];
		for(int i = 0; i < capacity(); ++i)
			copy[i] = get();
		return copy;
	}

	@Override
	public Vector3DBufferX 	slice() {
		return new Vector3DDoubleBufferX(buffer.slice());
	}
	@Override
	public Vector3DBufferX 	duplicate() {
		return new Vector3DDoubleBufferX(buffer.duplicate());
	}

	@Override
	public Vector3DDoubleBufferX	clone() {
		Vector3DDoubleBufferX copy = new Vector3DDoubleBufferX(remaining());
		copy.put(slice());
		copy.rewind();
		return copy;
	}

}
