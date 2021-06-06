package fr.java.graph.impl.algorithms.tensors;

import java.nio.Buffer;
import java.text.NumberFormat;
import java.util.BitSet;
import java.util.stream.BaseStream;

import fr.java.lang.enums.Primitive;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberTensor;
import fr.java.math.algebra.NumberVector;

public class BinaryMatrix implements NumberMatrix.Editable {
	private static final long serialVersionUID = 3375501410275508451L;

	int    width, height;
	BitSet data;

	public BinaryMatrix(int _m, int _n) {
		super();
		width  = _m;
		height = _n;
		data   = new BitSet(_n * _m);
	}

	public boolean 					at(int _i, int _j) {
		return data.get(_j * width + _i);
	}
	public void 					on(int _i, int _j) {
		data.set(_j * width + _i, true);
	}
	public void 					off(int _i, int _j) {
		data.set(_j * width + _i, false);
	}

	@Override
	public int 						rows() {
		return height;
	}
	@Override
	public int 						columns() {
		return width;
	}

	@Override
	public void 					setNumber(int _i, int _j, Number _value) {
		data.set(_j * width + _i, _value.byteValue() != 0 ? true : false);
	}
	@Override
	public Number 					getNumber(int _i, int _j) {
		return data.get(_j * width + _i) ? 1 : 0;
	}

	@Override
	public NumberVector 					getRow(int _i) {
		throw new NotYetImplementedException();
	}
	@Override
	public NumberVector 					getColumn(int _j) {
		throw new NotYetImplementedException();
	}


	@Override public int 			rank() 														{ throw new NotYetImplementedException(); }
	@Override public double 		cond() 														{ throw new NotYetImplementedException(); }
	@Override public double 		det() 														{ throw new NotYetImplementedException(); }
	@Override public double 		trace() 													{ throw new NotYetImplementedException(); }
	@Override public double 		norm1() 													{ throw new NotYetImplementedException(); }
	@Override public double 		norm2() 													{ throw new NotYetImplementedException(); }
	@Override public double 		normInf() 													{ throw new NotYetImplementedException(); }
	@Override public double 		normF() 													{ throw new NotYetImplementedException(); }

	@Override public void 			setMatrix(int _i0, int _i1, int _j0, int _j1, NumberMatrix _X) 	{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		getMatrix(int _i0, int _i1, int _j0, int _j1) 				{ throw new NotYetImplementedException(); }
	@Override public void 			setMatrix(int[] _rows, int[] _columns, NumberMatrix _X) 			{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		getMatrix(int[] _rows, int[] _columns) 						{ throw new NotYetImplementedException(); }
	@Override public void 			setMatrix(int _i0, int _i1, int[] _columns, NumberMatrix _X) 		{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		getMatrix(int _i0, int _i1, int[] _columns) 				{ throw new NotYetImplementedException(); }
	@Override public void 			setMatrix(int[] _rows, int _j0, int _j1, NumberMatrix _X) 		{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		getMatrix(int[] _rows, int _j0, int _j1) 					{ throw new NotYetImplementedException(); }

	@Override public NumberMatrix 		plus(Number t) 												{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		plus(NumberMatrix B) 												{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		plusEquals(Number t) 										{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		plusEquals(NumberMatrix B) 										{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		minus(Number t) 											{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		minus(NumberMatrix B) 											{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		minusEquals(Number t) 										{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		minusEquals(NumberMatrix B) 										{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		times(Number s) 											{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		times(NumberMatrix B) 											{ throw new NotYetImplementedException(); }
	@Override public NumberVector 		times(NumberVector b) 											{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		timesEquals(Number s) 										{ throw new NotYetImplementedException(); }

	@Override public NumberMatrix 		arrayTimes(NumberMatrix B) 										{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		arrayTimesEquals(NumberMatrix B) 									{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		arrayRightDivide(NumberMatrix B) 									{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		arrayRightDivideEquals(NumberMatrix B) 							{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		arrayLeftDivide(NumberMatrix B) 									{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		arrayLeftDivideEquals(NumberMatrix B) 							{ throw new NotYetImplementedException(); }

	@Override public boolean 		isEqual(double _d) 											{ throw new NotYetImplementedException(); }
	@Override public boolean 		isEqual(NumberMatrix _d) 											{ throw new NotYetImplementedException(); }

	@Override public boolean 		isDifferent(double _d) 										{ throw new NotYetImplementedException(); }
	@Override public boolean 		isDifferent(NumberMatrix _d) 										{ throw new NotYetImplementedException(); }

	@Override public BinaryMatrix 	clone() 													{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		abs() 														{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		transpose() 												{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		inverse() 													{ throw new NotYetImplementedException(); }
	@Override public NumberMatrix 		uminus() 													{ throw new NotYetImplementedException(); }

	@Override public String 		toString(NumberFormat _nf) 									{ throw new NotYetImplementedException(); }

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
	public Number getNumber(long _index) {
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
	public long[] getArray() {
		return data.toLongArray();
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

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(int j = rows(); j-- > 0;) {
			sb.append('|');
			for(int i = columns(); i-- > 0;) {
				sb.append(at(i, j) ? 'X' : ' ');
			}
			sb.append('|');
			sb.append('\n');
		}
		
		return sb.toString();
	}

	@Override
	public int[] getSliceShape(int... _slice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSliceOffset(int... _slice) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
