package fr.java.maths.algebra.tensors;

import java.nio.ShortBuffer;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ShortTensor;

public class ShortArrayTensor extends AbstractTensor implements ShortTensor {
	private static final long serialVersionUID = 440918471804433011L;

	protected short[] 		array;
	protected ShortBuffer 	buffer;

	public ShortArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.SHORT;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new short[getCapacity()];
			buffer     = ShortBuffer.wrap(array);
		}
	}
	public ShortArrayTensor(final short[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.SHORT;

		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new short[_array.length];
			buffer = ShortBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = ShortBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public ShortArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.SHORT;

		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}
	@Override
	public short[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public ShortBuffer		getBuffer() {
		buffer.rewind();
		return buffer;
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
	public short    			getValue(final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public short    			getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final short _value, final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final short _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public ShortTensor 		getSliceView(final int... _slice) {
		int           	offset = (int) getSliceOffset(_slice);
		int[] 	shape  = getSliceShape(_slice);
		ShortArrayTensor slice  = new ShortArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);
	
		ShortBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public ShortTensor 		getSliceCopy(final int... _slice) {
		int             offset = (int) getSliceOffset(_slice);
		int[]   shape  = getSliceShape(_slice);
		ShortArrayTensor slice  = new ShortArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final ShortTensor _tensor, final int... _slice) {
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
	public ShortArrayTensor 	clone() {
		ShortArrayTensor clone = new ShortArrayTensor(this);

		if(isDirect()) {
			short[]     arrayCopy  = new short[array.length];
			ShortBuffer bufferCopy = ShortBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			ShortBuffer bufferCopy = ShortBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}

}
