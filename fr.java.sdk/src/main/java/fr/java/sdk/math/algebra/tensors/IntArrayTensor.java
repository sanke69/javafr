package fr.java.sdk.math.algebra.tensors;

import java.nio.IntBuffer;
import java.util.stream.IntStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.IntTensor;

public class IntArrayTensor extends AbstractTensor implements IntTensor {
	private static final long serialVersionUID = 440918471804433011L;

	protected int[] 	array;
	protected IntBuffer buffer;

	public IntArrayTensor(final int... _slices) {
		super(_slices);
		primitive  = Primitive.INTEGER;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		array      = new int[getCapacity()];
		buffer     = IntBuffer.wrap(array);
	}
	public IntArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.INTEGER;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new int[getCapacity()];
			buffer     = IntBuffer.wrap(array);
		}
	}
	public IntArrayTensor(final int[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.INTEGER;
		
		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new int[_array.length];
			buffer = IntBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = IntBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public IntArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.INTEGER;

		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}
	@Override
	public int[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public IntBuffer		getBuffer() {
		buffer.rewind();
		return buffer;
	}
	@Override
	public IntStream		getStream() {
		return IntStream.range(buffer.position(), buffer.limit()).map(buffer::get);
	}

	@Override
	public Number     		getNumber(final long _index) {
		return getValue(_index);
	}
	@Override
	public Number     		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public int    			getValue(final long _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public int    			getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final int _value, final long _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final int _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public IntTensor 		getSliceView(final int... _slice) {
		int           	offset = (int) getSliceOffset(_slice);
		int[] 	shape  = getSliceShape(_slice);
		IntArrayTensor  slice  = new IntArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);
	
		IntBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public IntTensor 		getSliceCopy(final int... _slice) {
		int            offset = (int) getSliceOffset(_slice);
		int[]  shape  = getSliceShape(_slice);
		IntArrayTensor slice  = new IntArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final IntTensor _tensor, final int... _slice) {
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
	public IntArrayTensor 	clone() {
		IntArrayTensor clone = new IntArrayTensor(this);

		if(isDirect()) {
			int[]     arrayCopy  = new int[array.length];
			IntBuffer bufferCopy = IntBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			IntBuffer bufferCopy = IntBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}

}
