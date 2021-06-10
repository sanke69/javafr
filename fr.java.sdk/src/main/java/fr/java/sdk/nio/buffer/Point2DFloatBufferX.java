package fr.java.sdk.nio.buffer;

import java.nio.BufferOverflowException;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;
import fr.java.nio.buffer.Point2DBufferX;

public class Point2DFloatBufferX implements Point2DBufferX {
	FloatBuffer buffer;

	public Point2DFloatBufferX(int _nbPoints) {
		super();
		buffer = FloatBuffer.allocate(_nbPoints * 2);
	}
	public Point2DFloatBufferX(FloatBuffer _buffer) {
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
	public FloatBuffer 		Buffer() {
		return buffer;
	}

	@Override
	public int 				capacity() {
		return buffer.capacity() / 2; 
	}

	@Override
	public int				position() {
		return buffer.position() / 2; 
	}
	@Override
	public Point2DBufferX 	position(int _newPosition) {
		buffer.position(2 * _newPosition);
		return this;
	}

	@Override
	public int 				limit() {
		return buffer.limit() / 2;
	}
	@Override
    public Point2DBufferX 	limit(int _newLimit) {
		buffer.limit(_newLimit * 2);
		return this;
    }

	@Override
	public Point2DBufferX 	mark() {
		buffer.mark();
		return this;
	}
	@Override
    public Point2DBufferX 	reset() {
		buffer.reset();
		return this;
	}
	@Override
    public Point2DBufferX 	clear() {
		buffer.clear();
		return this;
	}
	@Override
    public Point2DBufferX 	rewind() {
		buffer.rewind();
		return this;
	}
	@Override
	public Point2DBufferX 	flip() {
		buffer.flip();
		return this;
	}
	@Override
    public int 				remaining() {
		return buffer.remaining() / 2;
	}

	@Override
	public Point2D 			get() {
		if(position() > capacity())
			throw new BufferOverflowException();
		return Points.of(buffer.get(), buffer.get()); 
	}
	@Override
	public Point2D 			get(int _index) {
		if(_index > capacity())
			throw new BufferOverflowException();
		return Points.of(buffer.get(2 * _index), buffer.get(2 * _index + 1)); 
	}
	@Override
	public Point2DBufferX 	get(Point2D.Editable[] _dst, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();
		
		for(int i = _offset; i < _length - _offset; ++i)
			_dst[i].set(buffer.get(), buffer.get());
		
		return this;
	}

	@Override
	public Point2DBufferX 	put(Point2D _pt) {
		buffer.put((float) _pt.getX());
		buffer.put((float) _pt.getY());
		return this;
	}
	@Override
	public Point2DBufferX 	put(int _index, Point2D _pt) {
		buffer.put(2 * _index, (float) _pt.getX());
		buffer.put(2 * _index + 1, (float) _pt.getY());
		return this;
	}
	@Override
	public Point2DBufferX 	put(Point2D[] _src, int _offset, int _length) {
		if(_length - _offset > remaining())
			throw new BufferOverflowException();
		
		for(int i = _offset; i < _length - _offset; ++i)
			buffer.put((float) _src[i].getX()).put((float) _src[i].getY());
		
		return this;
	}

	public void 			update(FloatBuffer _buffer) {
		buffer.rewind();
		buffer.put(_buffer);
		buffer.rewind();
	}
	public void 			update(Point2DFloatBufferX _buffer) {
		update(_buffer.buffer);
	}
	public void 			update(Point2D[] _pt, int _offset, int _length) {
		
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
	public Point2D[] 		arrayCopy() {
		buffer.rewind();

		Point2D[] copy = new Point2D[capacity()];
		for(int i = 0; i < capacity(); ++i)
			copy[i] = get();
		return copy;
	}

	@Override
	public Point2DBufferX 	slice() {
		return new Point2DFloatBufferX(buffer.slice());
	}
	@Override
	public Point2DBufferX 	duplicate() {
		return new Point2DFloatBufferX(buffer.duplicate());
	}

}