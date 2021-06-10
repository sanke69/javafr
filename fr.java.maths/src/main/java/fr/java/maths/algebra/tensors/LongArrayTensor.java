package fr.java.maths.algebra.tensors;

import java.nio.LongBuffer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.LongTensor;

public class LongArrayTensor extends AbstractTensor implements LongTensor {
	private static final long serialVersionUID = 440918471804433011L;

	protected long[] 		array;
	protected LongBuffer 	buffer;

	public LongArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.LONG;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new long[getCapacity()];
			buffer     = LongBuffer.wrap(array);
		}
	}
	public LongArrayTensor(final long[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.LONG;

		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new long[_array.length];
			buffer = LongBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = LongBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public LongArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.LONG;

		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}
	@Override
	public long[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public LongBuffer		getBuffer() {
		buffer.rewind();
		return buffer;
	}
	@Override
	public LongStream		getStream() {
		return IntStream.range(buffer.position(), buffer.limit()).mapToLong(buffer::get);
	}

	@Override
	public Number     		getNumber(final int _index) {
		return getValue(_index);
	}
	@Override
	public Number     		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public long    			getValue(final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public long    			getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final long _value, final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final long _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public LongTensor 		getSliceView(final int... _slice) {
		int           	offset = (int) getSliceOffset(_slice);
		int[] 	shape  = getSliceShape(_slice);
		LongArrayTensor slice  = new LongArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);
	
		LongBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public LongTensor 		getSliceCopy(final int... _slice) {
		int             offset = (int) getSliceOffset(_slice);
		int[]   shape  = getSliceShape(_slice);
		LongArrayTensor slice  = new LongArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final LongTensor _tensor, final int... _slice) {
		long offset = getSliceOffset(_slice);

		if(offset >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (offset=" + offset + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(_tensor.getCapacity() != shapeCapacities[getSliceDepth() - _slice.length - 1])
			throw new IllegalAccessError("Invalid dimensions, check your dimension !!!");

		buffer.position( (int) offset);
		buffer.put(_tensor.getBuffer());
	}

	@Override
	public int 				compareTo(final Object _o) {
		throw new NotYetImplementedException();
	}

	@Override
	public LongArrayTensor 	clone() {
		LongArrayTensor clone = new LongArrayTensor(this);

		if(isDirect()) {
			long[]     arrayCopy  = new long[array.length];
			LongBuffer bufferCopy = LongBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			LongBuffer bufferCopy = LongBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}

}
