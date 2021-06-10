package fr.java.maths.algebra.tensors;

import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.maths.algebra.Tensors;

abstract class AbstractTensor implements NumberTensor {
	private static final long serialVersionUID = -8826265361816372313L;

	protected Primitive 	primitive;
	protected int[] 		shapeDimensions;
	protected int[]         shapeCapacities;

	protected AbstractTensor(final int... _shape) {
		this(Primitive.UNDEF, _shape);
	}
	protected AbstractTensor(Primitive _primitive) {
		this(_primitive, null);
	}
	protected AbstractTensor(Primitive _primitive, final int... _shape) {
		super();
		primitive = _primitive;

		if(_shape != null && _shape.length != 0) {
			shapeDimensions = _shape;
			shapeCapacities = Tensors.Utils.getSliceCapacities(_shape);
		}
	}
	protected AbstractTensor(final NumberTensor _tensor) {
		this(_tensor.getPrimitive(), _tensor.getShape());
	}

	// META Access
	public Primitive 					getPrimitive() {
		return primitive;
	}

	@Override
	public Nature    					getNature() {
		switch(getSliceDepth()) {
		case 0  : return Nature.UNKNOWN;
		case 1  : return Nature.VECTOR;
		case 2  : return Nature.MATRIX;
		case 3  : return Nature.CUBE;
		case 4  : return Nature.TESSERACT;
		case 5  : return Nature.HYPERCUBE;
		default : return Nature.OVER_HYPERCUBE;
		}
	}
	@Override
	public Nature    					getNature(boolean _simplified) {
		if(!_simplified)
			return getNature();

		switch(getSliceDepth()) {
		case 0  : return Nature.UNKNOWN;
		case 1  : return shapeDimensions[0] != 1 ? Nature.VECTOR    : Nature.SCALAR;
		case 2  : return shapeDimensions[0] != 1 ? Nature.MATRIX    : shapeDimensions[1] != 1 ? Nature.VECTOR : Nature.SCALAR;
		case 3  : return shapeDimensions[0] != 1 ? Nature.CUBE      : shapeDimensions[1] != 1 ? Nature.MATRIX : shapeDimensions[2] != 1 ? Nature.VECTOR : Nature.SCALAR;
		case 4  : return shapeDimensions[0] != 1 ? Nature.TESSERACT : shapeDimensions[1] != 1 ? Nature.CUBE   : shapeDimensions[2] != 1 ? Nature.MATRIX : shapeDimensions[3] != 1 ? Nature.VECTOR : Nature.SCALAR;
		default : return Nature.OVER_HYPERCUBE;
		}
	}

	// DIMENSION Access
	@Override
	public int[] 						getShape() {
		return shapeDimensions;
	}
	@Override
	public int							getCapacity() {
		return Tensors.Utils.getCapacity(shapeDimensions);
	}

	@Override
	public int[] 						getSliceShape(final int... _slice) {
		int[] sliceShape = null;
		if(shapeDimensions.length > 1) {
			sliceShape = new int[getSliceDepth() - _slice.length];
			for(int i = _slice.length; i < getSliceDepth(); ++i)
				sliceShape[i - _slice.length] = shapeDimensions[i];
		} else {
			sliceShape = new int[] { 1 };
		}

		return sliceShape;
	}
	@Override
	public int 							getSliceOffset(final int... _slice) {
		assert(_slice.length <= getShape().length);
		for(int i = 0; i < _slice.length; ++i)
			assert(_slice[i] < shapeDimensions[i]) : "Invalid slice coordinate";

		int offset = 0;

		if(shapeCapacities.length > 0) {
			for(int i = 0; i < _slice.length; ++i)
				offset += _slice[i] * shapeCapacities[i];
			return offset;
		} else
			return (int) getValueOffset(_slice);
	}
	@Override
	public int							getSliceCapacity(final int _depth) {
		return shapeCapacities[_depth];
	}

	@Override
	public BaseStream<?,?> 				getStream() {
		throw new NotYetImplementedException();
	}

	// DATA Access

	// STORAGE Access
	@Override
	public void 						reshape(int... _shape) {
		int oldCapacity = getCapacity();
		int newCapacity = Tensors.Utils.getCapacity(_shape);

		if(oldCapacity != newCapacity)
			throw new IllegalArgumentException("dimensions not matches: old=" + oldCapacity + " != new=" + newCapacity);

		shapeDimensions = _shape;
	}

	// PACKAGE RESERVED METHODS
	protected int 						getValueOffset(final int... _coords) {
		if(_coords == null)
			return 0;

		int offset = 0;
		int iSlice = shapeCapacities.length - _coords.length;

		if(shapeDimensions.length < _coords.length) {
			int n = 0;
			while(_coords[n] == 0 && n < _coords.length - shapeCapacities.length)
				++n;

			if(shapeDimensions.length < _coords.length - n)
				throw new IllegalArgumentException();

			for(int i = 0; i < _coords.length - n; ++i)
				_coords[i] = _coords[i+n];

			iSlice = 0;
		}

		for(int i = iSlice; i < shapeCapacities.length - 1; ++i)
			offset += _coords[i - iSlice] * shapeCapacities[i];
		offset += _coords[_coords.length - 1];

		return offset;
	}

	public AbstractTensor 			clone() {
		throw new RuntimeException();
	}

	public  String 						toString() {
		return Tensors.Print.toString(this);
		/*
		StringBuilder sb = new StringBuilder();

		Primitive 	primitive = getPrimitive();
		int[]       shape     = getShape();

		sb.append("TBD\n");

		return sb.toString();
		*/
	}

}
