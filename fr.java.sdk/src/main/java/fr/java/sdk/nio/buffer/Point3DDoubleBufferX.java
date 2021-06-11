package fr.java.sdk.nio.buffer;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.geometry.types.Points;
import fr.java.nio.buffer.Point3DBufferX;

public class Point3DDoubleBufferX implements Point3DBufferX {
	DoubleBuffer buffer;

	public Point3DDoubleBufferX(int _nbPoints) {
		super();
		buffer = DoubleBuffer.allocate(_nbPoints * 3);
	}
	public Point3DDoubleBufferX(DoubleBuffer _buffer) {
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
	public Point3DBufferX 	position(int _newPosition) {
		buffer.position(3 * _newPosition);
		return this;
	}

	@Override
	public int 				limit() {
		return buffer.limit() / 3;
	}
	@Override
    public Point3DBufferX 	limit(int _newLimit) {
		buffer.limit(_newLimit * 3);
		return this;
    }

	@Override
	public Point3DBufferX 	mark() {
		buffer.mark();
		return this;
	}
	@Override
    public Point3DBufferX 	reset() {
		buffer.reset();
		return this;
	}
	@Override
    public Point3DBufferX 	clear() {
		buffer.clear();
		return this;
	}
	@Override
    public Point3DBufferX 	rewind() {
		buffer.rewind();
		return this;
	}
	@Override
	public Point3DBufferX 	flip() {
		buffer.flip();
		return this;
	}
	@Override
    public int 				remaining() {
		return buffer.remaining() / 3;
	}

	@Override
	public Point3D 			get() {
		if(position() > capacity())
			throw new BufferOverflowException();
		return Points.of(buffer.get(), buffer.get(), buffer.get()); 
	}
	@Override
	public Point3D 			get(int _index) {
		if(_index > capacity())
			throw new BufferOverflowException();
		return Points.of(buffer.get(3 * _index), buffer.get(3 * _index + 1), buffer.get(3 * _index + 2)); 
	}
	@Override
	public Point3DBufferX 	get(Point3D.Editable[] _dst, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();
		
		for(int i = _offset; i < _length - _offset; ++i)
			_dst[i].set(buffer.get(), buffer.get(), buffer.get());
		
		return this;
	}

	@Override
	public Point3DBufferX 	put(Point3D _pt) {
		buffer.put((float) _pt.getX());
		buffer.put((float) _pt.getY());
		buffer.put((float) _pt.getZ());
		return this;
	}
	@Override
	public Point3DBufferX 	put(int _index, Point3D _pt) {
		buffer.put(3 * _index, _pt.getX());
		buffer.put(3 * _index + 1, _pt.getY());
		buffer.put(3 * _index + 2, _pt.getZ());
		return this;
	}
	@Override
	public Point3DBufferX 	put(Point3D[] _src, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();

		for(int i = _offset; i < _length - _offset; ++i)
			buffer.put((float) _src[i].getX()).put((float) _src[i].getY()).put((float) _src[i].getZ());

		return this;
	}
	@Override
	public Point3DBufferX put(Point3DBufferX _src) {
		if(_src.remaining() > remaining())
			throw new BufferOverflowException();

		if(!(_src instanceof Point3DDoubleBufferX))
			throw new NotYetImplementedException();

		buffer.put(((Point3DDoubleBufferX) _src).buffer);

		return this;
	}
	@Override
	public Point3DBufferX put(Buffer _src) {
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
	public Point3D[] 		arrayCopy() {
		buffer.rewind();

		Point3D[] copy = new Point3D[capacity()];
		for(int i = 0; i < capacity(); ++i)
			copy[i] = get();
		return copy;
	}

	@Override
	public Point3DBufferX 	slice() {
		return new Point3DDoubleBufferX(buffer.slice());
	}
	@Override
	public Point3DBufferX 	duplicate() {
		return new Point3DDoubleBufferX(buffer.duplicate());
	}

	@Override
	public Point3DDoubleBufferX	clone() {
		Point3DDoubleBufferX copy = new Point3DDoubleBufferX(remaining());
		copy.put(slice());
		copy.rewind();
		return copy;
	}

}
