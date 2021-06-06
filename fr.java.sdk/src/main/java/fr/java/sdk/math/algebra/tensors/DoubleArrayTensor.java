package fr.java.sdk.math.algebra.tensors;

import java.nio.DoubleBuffer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.DoubleTensor;

public class DoubleArrayTensor extends AbstractTensor implements DoubleTensor.Editable {
	private static final long serialVersionUID = 440918471804433011L;

	protected double[] 		array;
	protected DoubleBuffer 	buffer;

	public DoubleArrayTensor(final boolean _createArray, final int... _slices) {
		super(_slices);
		primitive  = Primitive.DOUBLE;

		if(getCapacity() == 0)
			throw new IllegalArgumentException("Capacity can't be null");

		if(_createArray) {
			array      = new double[getCapacity()];
			buffer     = DoubleBuffer.wrap(array);
		}
	}
	public DoubleArrayTensor(final double[] _array, final boolean _makeArrayCopy, final int... _slice) {
		super(_slice);
		primitive  = Primitive.DOUBLE;

		if(getCapacity() != _array.length)
			throw new IllegalArgumentException("Capacity does not match _array.length");

		if(_makeArrayCopy) {
			array  = new double[_array.length];
			buffer = DoubleBuffer.wrap(array);

			System.arraycopy(_array, 0, array, 0, _array.length);
		} else {
			array  = _array;
			buffer = DoubleBuffer.wrap(_array);
		}

		if(_slice == null || _slice.length == 0)
			reshape(_array.length);
	}
	public DoubleArrayTensor(final NumberTensor _tensor) {
		super(_tensor);
		primitive  = Primitive.DOUBLE;
		throw new NotYetImplementedException();
	}

	@Override
	public boolean 			isDirect() {
		return array != null;
	}
	@Override
	public double[]			getArray() {
		if(!isDirect())
			throw new UnsupportedOperationException("Tensor is not direct");
		return array;
	}
	@Override
	public DoubleBuffer		getBuffer() {
		buffer.rewind();
		return buffer;
	}
	@Override
	public DoubleStream		getStream() {
		return IntStream.range(buffer.position(), buffer.limit()).mapToDouble(buffer::get);
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
	public double    		getValue(final long _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		return buffer.get( (int) _index);
	}
	@Override
	public double    		getValue(final int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public void	    		setValue(final double _value, final long _index) {
		if(_index >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (index=" + _index + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(isDirect())
			array[ (int) _index] = _value;
		else
			buffer.put( (int) _index, _value);
	}
	@Override
	public void	    		setValue(final double _value, final int... _coords) {
		setValue(_value, getValueOffset(_coords));
	}

	@Override
	public DoubleTensor 	getSliceView(final int... _slice) {
		int           	  offset = (int) getSliceOffset(_slice);
		int[] 	  		  shape  = getSliceShape(_slice);
		DoubleArrayTensor slice  = new DoubleArrayTensor(false, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		DoubleBuffer sliceBuffer = buffer.slice();
		sliceBuffer.limit(slice.getCapacity());
		slice.buffer = sliceBuffer;

		buffer.position(lastPosition);
	
		return slice;
	}
	@Override
	public DoubleTensor 	getSliceCopy(final int... _slice) {
		int[]    		  shape   = getSliceShape(_slice);
		int               offset  = (int) getSliceOffset(_slice);
		DoubleArrayTensor slice   = new DoubleArrayTensor(true, shape);

		int lastPosition = buffer.position();
		buffer.position(offset);

		buffer.get(slice.array);

		buffer.position(lastPosition);

		return slice;
	}
	@Override
	public void	    		setSlice(final DoubleTensor _tensor, final int... _slice) {
		long offset = getSliceOffset(_slice);

		if(offset >= buffer.capacity())
			throw new IllegalAccessError("Coordinates (offset=" + offset + ") exceed Tensor Capacity (capacity=" + buffer.capacity() + "), check your dimension !!!");

		if(_tensor.getCapacity() != shapeCapacities[getSliceDepth() - _slice.length - 1])
			throw new IllegalAccessError("Invalid dimensions, check your dimension !!!");

		buffer.position( (int) offset);
		buffer.put(_tensor.getBuffer());
	}

	@Override
	public DoubleArrayTensor clone() {
		DoubleArrayTensor clone = new DoubleArrayTensor(this);
		
		if(isDirect()) {
			double[]     arrayCopy  = new double[array.length];
			DoubleBuffer bufferCopy = DoubleBuffer.wrap(arrayCopy);

			System.arraycopy(array, 0, arrayCopy, 0, array.length);

			clone.array  = arrayCopy;
			clone.buffer = bufferCopy;
		} else {
			DoubleBuffer bufferCopy = DoubleBuffer.allocate(buffer.capacity());
			bufferCopy.put(buffer);

			clone.buffer = bufferCopy;
		}
		
		return clone;
	}
	@Override
	public int 				compareTo(Object _o) {
		throw new NotYetImplementedException();
	}

}
