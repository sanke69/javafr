package fr.java.maths.algebra.tensors;

import java.nio.FloatBuffer;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.FloatTensor;

public class FloatArrayTensor extends AbstractTensor implements FloatTensor {
	private static final long serialVersionUID = 440918471804433011L;

	protected float[] 		array;
	protected FloatBuffer 	buffer;

	public FloatArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.FLOAT;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new float[getCapacity()];
			buffer     = FloatBuffer.wrap(array);
		}
	}
	public FloatArrayTensor(final float[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.FLOAT;

		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new float[_array.length];
			buffer = FloatBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = FloatBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public FloatArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.FLOAT;

		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}
	@Override
	public float[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public FloatBuffer		getBuffer() {
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
	public float    		getValue(final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public float    		getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final float _value, final int _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final float _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public FloatTensor 		getSliceView(final int... _slice) {
		int           	 offset = (int) getSliceOffset(_slice);
		int[] 	 shape  = getSliceShape(_slice);
		FloatArrayTensor slice  = new FloatArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);
	
		FloatBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public FloatTensor 		getSliceCopy(final int... _slice) {
		int             offset = (int) getSliceOffset(_slice);
		int[]   shape  = getSliceShape(_slice);
		FloatArrayTensor slice  = new FloatArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final FloatTensor _tensor, final int... _slice) {
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
	public FloatArrayTensor	clone() {
		FloatArrayTensor clone = new FloatArrayTensor(this);

		if(isDirect()) {
			float[]     arrayCopy  = new float[array.length];
			FloatBuffer bufferCopy = FloatBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			FloatBuffer bufferCopy = FloatBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}

}
