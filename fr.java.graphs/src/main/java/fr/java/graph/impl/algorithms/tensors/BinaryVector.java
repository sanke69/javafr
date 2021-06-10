package fr.java.graph.impl.algorithms.tensors;

import java.nio.Buffer;
import java.text.NumberFormat;
import java.util.BitSet;
import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.NumberVector.Norm;

public class BinaryVector implements NumberVector.Editable {

	int    size;
	BitSet data;

	public BinaryVector(int _size) {
		super();
		size = _size;
		data = new BitSet(size);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NumberVector plus(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector plus(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector plus(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minus(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minus(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minus(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector times(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector times(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector times(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector divides(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector divides(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector divides(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number dotProduct(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector crossProduct(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(Number _value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(Number[] _values) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(NumberVector _vector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDifferent(Number _value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDifferent(Number[] _values) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDifferent(NumberVector _vector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isColinear(NumberVector _vector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrthogonal(NumberVector _vector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NumberVector abs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector negate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector normalized() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final double 		norm(Norm _norm/*, double _p*/) {
		double p = 1;
		switch(_norm) {
		case EuclidianSquare:	return data.cardinality();

		case Euclidian:			return Math.sqrt(data.cardinality());

		case Manhattan:			return data.cardinality();

		case P:					
								return Math.pow( data.cardinality(), 1d/p);

		case Maximum:			return data.cardinality() > 0 ? 1d : 0d;
								
		default:				throw new NotYetImplementedException();
		}
	}
	@Override
	public double magnitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double norm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double norm2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString(NumberFormat _nf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Editable clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDirect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Primitive getPrimitive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number getNumber(int _index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number getNumber(int... _coords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberTensor getSliceView(int... _slice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberTensor getSliceCopy(int... _slice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reshape(int... _shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer getBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseStream<?, ?> getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void set(Number _value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(Number[] _values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(NumberVector _vector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NumberVector plusEquals(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector plusEquals(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector plusEquals(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minusEquals(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minusEquals(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector minusEquals(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector timesEquals(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector timesEquals(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector timesEquals(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector dividesEquals(Number _value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector dividesEquals(Number[] _values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberVector dividesEquals(NumberVector _vector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumber(Number _value, int _i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return size;
	}

}
