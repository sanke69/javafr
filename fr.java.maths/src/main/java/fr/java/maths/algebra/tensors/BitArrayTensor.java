package fr.java.maths.algebra.tensors;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotSupportedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.tensor.BitTensor;
import fr.java.maths.algebra.Tensors;
import fr.utils.maths.primitives.Arrays;

public class BitArrayTensor extends AbstractTensor implements BitTensor {
	private static final long serialVersionUID = 3375501410275508451L;

	BitSet data;

	BitArrayTensor(BitSet _data, int... _shape) {
		super();
		primitive     	= null;
		shapeDimensions    		= null;
		shapeCapacities = null;
	}
	public BitArrayTensor() {
		super();
		primitive     	= null;
		shapeDimensions    		= null;
		shapeCapacities = null;
	}
	public BitArrayTensor(final int... _slices) {
		super(Primitive.BOOLEAN, _slices);
		if(_slices != null && _slices.length != 0) {
			shapeDimensions = _slices;
			shapeCapacities = Tensors.Utils.getSliceCapacities(_slices);
		}
	}
	public BitArrayTensor(final NumberTensor _tensor) {
		this(_tensor.getShape());
	}

	@Override
	public boolean 			isDirect() {
		return true;
	}

	@Override
	public byte[] 			getArray() {
		return data.toByteArray();
	}
	@Override
	public ByteBuffer 		getBuffer() throws NotSupportedException {
		return ByteBuffer.wrap(getArray());
	}

	@Override
	public boolean 			isTrue(int _index) {
		return getValue( _index );
	}
	@Override
	public boolean 			isTrue(int... _coords) {
		return getValue( getValueOffset(_coords) );
	}
	@Override
	public boolean 			isFalse(int _index) {
		return !getValue( _index );
	}
	@Override
	public boolean 			isFalse(int... _coords) {
		return !getValue( getValueOffset(_coords) );
	}

	@Override
	public boolean 			getValue(int _index) {
		return data.get((int) _index) ;
	}
	@Override
	public boolean 			getValue(int... _coords) {
		return getValue( getValueOffset(_coords) );
	}

	@Override
	public Number 			getNumber(int _index) {
		return data.get((int) _index) ? 1 : 0;
	}
	@Override
	public Number 			getNumber(int... _coords) {
		return getNumber( getValueOffset(_coords) );
	}

	@Override
	public BitTensor 		getSliceView(int... _slice) {
		int offset   = getSliceOffset(_slice);
		int capacity = getSliceCapacity(_slice);
		
		BitSet sliceData = BitSet.valueOf( Arrays.subarray(data.toByteArray(), offset, offset + capacity) );

		return new BitArrayTensor(sliceData, getSliceShape(_slice));
	}
	@Override
	public BitTensor 		getSliceCopy(int... _slice) {
		int offset   = getSliceOffset(_slice);
		int capacity = getSliceCapacity(_slice);
		
		BitSet sc = new BitSet((int) capacity);
		for(int i = (int) capacity; i-- >= 0;)
			sc.set(i, getValue(offset + i));

		return null;
	}

	@Override
	public BaseStream<?, ?> getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int 				compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String 			toString() {
		StringBuilder sb = new StringBuilder();

		for(int j = getSliceDimension(1, true); j-- > 0;) {
			sb.append('|');
			for(int i = getSliceDimension(0, true); i-- > 0;) {
				sb.append(isTrue(i, j) ? 'X' : ' ');
			}
			sb.append('|');
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public BitArrayTensor 	clone() {
		return null;
	}

}
