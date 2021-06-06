package fr.java.sdk.math.algebra.vectors;

import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.tensor.Vector;

public abstract class AbstractVector implements Vector {
	private static final long serialVersionUID = 5422299450249173175L;

	protected Primitive 	primitive;
	protected int			capacity;

	protected AbstractVector(final int _size) {
		this(Primitive.UNDEF, _size);
	}
	protected AbstractVector(Primitive _primitive) {
		this(_primitive, 0);
	}
	protected AbstractVector(Primitive _primitive, final int _capacity) {
		super();
		primitive = _primitive;
		capacity  = _capacity;
	}
	protected AbstractVector(final NumberTensor _tensor) {
		this(_tensor.getPrimitive(), _tensor.getCapacity());
	}

	// META Access
	public Primitive 					getPrimitive() {
		return primitive;
	}

	@Override
	public Nature    					getNature() {
		return capacity == 0 ? Nature.UNKNOWN : capacity == 1 ? Nature.SCALAR : Nature.VECTOR;
	}
	@Override
	public Nature    					getNature(boolean _simplified) {
		return getNature();
	}

	// DIMENSION Access
	@Override
	public int[] 						getShape() {
		return new int[] { capacity };
	}
	@Override
	public int							getCapacity() {
		return capacity;
	}

	@Override
	public int[] 						getSliceShape(final int... _slice) {
		return new int[] { _slice == null || _slice.length == 0 ? capacity : 1 };
	}
	@Override
	public int 							getSliceOffset(final int... _slice) {
		return ( _slice == null || _slice.length == 0 ) ? 0 : _slice[0];
	}
	@Override
	public int							getSliceCapacity(final int _depth) {
		return _depth == 0 ? capacity : 1;
	}

	@Override
	public BaseStream<?,?> 				getStream() {
		throw new NotYetImplementedException();
	}

	// DATA Access

	// STORAGE Access
	@Override
	public void 						reshape(int... _shape) {}

	// PACKAGE RESERVED METHODS
	protected long 						getValueOffset(final int... _coords) {
		return _coords == null ? 0 : (_coords.length > 1 && _coords[0] == 0 ? _coords[1] : _coords[0]);
	}

	public abstract Vector		clone();

}
