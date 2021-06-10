package fr.java.maths.algebra.tensors;

import java.nio.ByteBuffer;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.ByteTensor;
import fr.utils.maths.primitives.Arrays;

public class ByteArrayTensor extends AbstractTensor implements ByteTensor {
	private static final long serialVersionUID = 440918471804433011L;

	protected byte[]     array;
	protected ByteBuffer buffer;

	public ByteArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.BYTE;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new byte[getCapacity()];
			buffer     = ByteBuffer.wrap(array);
		}
	}
	public ByteArrayTensor(final byte[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.BYTE;
		
		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new byte[_array.length];
			buffer = ByteBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = ByteBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public ByteArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.BYTE;

		if(_tensor.getArray() == null)
			throw new NotYetImplementedException();

		switch(_tensor.getPrimitive()) {
		case BYTE:		array = Arrays.copy((byte[]) _tensor.getArray());
						break;
		case SHORT:		array = Arrays.convertToByteArray((short[]) _tensor.getArray());
						break;
		case INTEGER:	array = Arrays.convertToByteArray((short[]) _tensor.getArray());
						break;
		case LONG:		array = Arrays.convertToByteArray((short[]) _tensor.getArray());
						break;
		case DOUBLE:	array = Arrays.convertToByteArray((short[]) _tensor.getArray());
						break;
		case FLOAT:		array = Arrays.convertToByteArray((short[]) _tensor.getArray());
						break;
		default:		throw new NotYetImplementedException();
		}

		buffer = ByteBuffer.wrap(array);
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}

	@Override
	public byte[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public ByteBuffer		getBuffer() {
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
	public byte    			getValue(final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public byte    			getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final byte _value, final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final byte _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public ByteTensor 		getSliceView(final int... _slice) {
		int           	offset = (int) getSliceOffset(_slice);
		int[] 			shape  = getSliceShape(_slice);
		ByteArrayTensor slice  = new ByteArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);
	
		ByteBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public ByteTensor 		getSliceCopy(final int... _slice) {
		int             offset = (int) getSliceOffset(_slice);
		int[] 			shape  = getSliceShape(_slice);
		ByteArrayTensor slice  = new ByteArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final ByteTensor _tensor, final int... _slice) {
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
	public ByteArrayTensor 	clone() {
		ByteArrayTensor clone = new ByteArrayTensor(this);

		if(isDirect()) {
			byte[]     arrayCopy  = new byte[array.length];
			ByteBuffer bufferCopy = ByteBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			ByteBuffer bufferCopy = ByteBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}

}
