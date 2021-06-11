package fr.java.maths.algebra.matrices;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.util.stream.DoubleStream;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;
import fr.java.math.algebra.tensor.DoubleTensor;
import fr.java.math.algebra.vector.DoubleVector;
import fr.java.maths.Numbers;
import fr.java.maths.algebra.matrices.doubles.LinearSystemMatrix;
import fr.java.maths.algebra.matrices.doubles.decompositions.CholeskyDecomposition;
import fr.java.maths.algebra.matrices.doubles.decompositions.EigenvalueDecomposition;
import fr.java.maths.algebra.matrices.doubles.decompositions.LUDecomposition;
import fr.java.maths.algebra.matrices.doubles.decompositions.QRDecomposition;
import fr.java.maths.algebra.matrices.doubles.decompositions.SingularValueDecomposition;
import fr.java.maths.algebra.vectors.DoubleVectorND;

public class DoubleMatrixMN implements DoubleMatrix.Editable, LinearSystemMatrix {
	private static final long serialVersionUID = 1;

	public static DoubleMatrixMN allocate(final int _m, final int _n) {
		return new DoubleMatrixMN(_m, _n);
	}
	public static DoubleMatrixMN allocate(final double[][] _A) {
		int m = _A.length;
		int n = _A[0].length;
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			if (_A[i].length != n) {
				throw new IllegalArgumentException("All rows must have the same length.");
			}
			for (int j = 0; j < n; j++) {
				C[i][j] = _A[i][j];
			}
		}
		return X;
	}

	public static DoubleMatrixMN wrap(final double[][] _A) {
		DoubleMatrixMN M = new DoubleMatrixMN();
		
		M.m = _A.length;
		M.n = _A[0].length;
		for (int i = 0; i < M.m; i++) {
			if (_A[i].length != M.n) {
				throw new IllegalArgumentException("All rows must have the same length.");
			}
		}

		M.A = _A;
		return M;
	}
	public static DoubleMatrixMN wrap(final double[][] _A, final int _m, final int _n) {
		DoubleMatrixMN M = new DoubleMatrixMN();
		
		M.m = _m;
		M.n = _n;
		M.A = _A;
		return M;
	}
	
	public static DoubleMatrixMN from(final double _vals[], final int _m) {
		DoubleMatrixMN M = new DoubleMatrixMN();

		M.m = _m;
		M.n = (_m != 0 ? _vals.length / _m : 0);
		if (M.m * M.n != _vals.length)
			throw new IllegalArgumentException("Array length must be a multiple of m.");

		M.A = new double[M.m][M.n];
		for (int i = 0; i < M.m; i++)
			for (int j = 0; j < M.n; j++)
				M.A[i][j] = _vals[i + j * M.m];

		return M;
	}

	public static DoubleMatrixMN identity(final int _m) {
		return identity(_m, _m);
	}
	public static DoubleMatrixMN identity(final int _m, final int _n) {
		DoubleMatrixMN A = new DoubleMatrixMN(_m, _n);
		double[][] X = A.getStorage();
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				X[i][j] = (i == j ? 1.0 : 0.0);
			}
		}
		return A;
	}

	public static DoubleMatrixMN diagonal(final double[] _values) {
		DoubleMatrixMN A = new DoubleMatrixMN(_values.length, _values.length);
		double[][] X = A.getStorage();
		for (int i = 0; i < _values.length; i++)
				X[i][i] = _values[i];
		return A;
	}

	public static DoubleMatrixMN random(final int _m, final int _n) {
		DoubleMatrixMN A = new DoubleMatrixMN(_m, _n);
		double[][] X = A.getStorage();
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				X[i][j] = Math.random();
			}
		}
		return A;
	}

	private double[][] 	A;
	private int 		m, n;

	private DoubleMatrixMN() {
		super();
	}
	public DoubleMatrixMN(final int _m, final int _n) {
		super();
		m = _m;
		n = _n;
		A = new double[_m][_n];
	}
	public DoubleMatrixMN(final int _m, final int _n, final double _value) {
		super();
		m = _m;
		n = _n;
		A = new double[_m][_n];
		for(int i = 0; i < _m; i++) {
			for(int j = 0; j < _n; j++) {
				A[i][j] = _value;
			}
		}
	}

	// TENSOR METHODS
	@Override
	public final boolean 		isDirect() {
		return false;
	}
	@Override
	public final double[] 		getArray() {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleBuffer 	getBuffer() {
		throw new NotYetImplementedException();
	}
	@Override
	public DoubleStream 		getStream() {
		throw new NotYetImplementedException();
	}

	@Override
	public int 					getSliceOffset(int... _slice) {
		return _slice[0] * A[0].length + _slice[1];
	}
	@Override
	public int[] 				getSliceShape(int... _slice) {
		return _slice == null || _slice.length == 0 ? getShape() : _slice.length == 1 ? new int[] { A[0].length } : new int[0];
	}

	@Override
	public Number 				getNumber(int _index) {
		return getValue(_index);
	}
    @Override
	public final Number 		getNumber(final int... _coords) {
		return getValue(_coords);
	}

	@Override
	public double 				getValue(int _index) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void 				setValue(double _value, int _index) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void 			setValue(final double _value, final int... _coords) {
		A[_coords[1]][_coords[0]] = _value;
	}
	@Override
	public final double 		getValue(final int... _coords) {
		return A[_coords[1]][_coords[0]];
	}

	@Override
	public final void			setSlice(final DoubleTensor _tensor, final int... _slice) {
//		int slice = getSliceO
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceView(final int... _slice) {
		throw new NotYetImplementedException();
	}
	@Override
	public final DoubleTensor 	getSliceCopy(final int... _slice) {
		throw new NotYetImplementedException();
	}

	@Override
	public final void 			reshape(final int... _shape) {
		throw new NotYetImplementedException();

		/*
		if(_shape.length > 2)
			throw new IllegalArgumentException("Can't reshape a Matrix with more than 2 dimensions");

		if(_shape.length == 1) {
			if(_shape[0] != getCapacity())
				throw new IllegalArgumentException("Can't reshape a Matrix with different capacity");
			shape = new Integer[] { 1, getCapacity() };
		}
			
			
		int newCapacity = _shape[0] * _shape[1];
		if(newCapacity != getCapacity())
			throw new IllegalArgumentException("Can't reshape a Matrix with different capacity");

		shape = new Integer[] { _shape[0], _shape[1] };
		*/
	}

	// MATRIX METHODS
	@Override
	public final int 			rows() {
		return m;
	}
	@Override
	public final int 			columns() {
		return n;
	}

	@Override
	public final void 			setNumber(final int _i, final int _j, final Number _value) {
		A[_i][_j] = _value.doubleValue();
	}
	@Override
	public final Number 		getNumber(final int _i, final int _j) {
		return A[_i][_j];
	}

	@Override
	public final void 			set(final int _i, final int _j, final double _value) {
		A[_i][_j] = _value;
	}
	@Override
	public final double 		get(final int _i, final int _j) {
		return A[_i][_j];
	}

	@Override
	public final DoubleVector 	getRow(final int _i) {
		DoubleVectorND row = new DoubleVectorND(columns());
		for(int j = 0; j < columns(); ++j)
			row.set(get(_i, j), _i);
		return row;
	}
	@Override
	public final DoubleVector 	getColumn(final int _j) {
		DoubleVectorND col = new DoubleVectorND(rows());
		for(int i = 0; i < columns(); ++i)
			col.set(i, _j);
		return col;
	}

	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int _j0, final int _j1, final NumberMatrix _X) {
		try {
			for(int i = _i0; i <= _i1; i++)
				for(int j = _j0; j <= _j1; j++)
					A[i][j] = _X.getNumber(i - _i0, j - _j0).doubleValue();

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	public final void 			setMatrix(final int _i0, final int _i1, final int _j0, final int _j1, final DoubleMatrix _X) {
		try {
			for(int i = _i0; i <= _i1; i++)
				for(int j = _j0; j <= _j1; j++)
					A[i][j] = _X.get(i - _i0, j - _j0);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	@Override
	public final DoubleMatrixMN 		getMatrix(final int _i0, final int _i1, final int _j0, final int _j1) {
		DoubleMatrixMN X = new DoubleMatrixMN(_i1 - _i0 + 1, _j1 - _j0 + 1);
		double[][] B = X.getStorage();
		try {
			for(int i = _i0; i <= _i1; i++)
				for(int j = _j0; j <= _j1; j++)
					B[i - _i0][j - _j0] = A[i][j];

		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}
	@Override
	public final void 			setMatrix(final int[] _rows, final int[] _columns, final NumberMatrix _X) {
		try {
			for(int i = 0; i < _rows.length; i++)
				for(int j = 0; j < _columns.length; j++)
					A[_rows[i]][_columns[j]] = _X.getNumber(i, j).doubleValue();

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	public final void 			setMatrix(final int[] _rows, final int[] _columns, final DoubleMatrix _X) {
		try {
			for(int i = 0; i < _rows.length; i++)
				for(int j = 0; j < _columns.length; j++)
					A[_rows[i]][_columns[j]] = _X.get(i, j);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	@Override
	public final DoubleMatrixMN 		getMatrix(final int[] _rows, final int[] _columns) {
		DoubleMatrixMN X = new DoubleMatrixMN(_rows.length, _columns.length);
		double[][] B = X.getStorage();
		try {
			for(int i = 0; i < _rows.length; i++)
				for(int j = 0; j < _columns.length; j++)
					B[i][j] = A[_rows[i]][_columns[j]];

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}
	@Override
	public final void 			setMatrix(final int _i0, final int _i1, final int[] _c, final NumberMatrix _X) {
		try {
			for (int i = _i0; i <= _i1; i++)
				for (int j = 0; j < _c.length; j++)
					A[i][_c[j]] = _X.getNumber(i - _i0, j).doubleValue();

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	public final void 			setMatrix(final int _i0, final int _i1, final int[] _c, final DoubleMatrix _X) {
		try {
			for (int i = _i0; i <= _i1; i++)
				for (int j = 0; j < _c.length; j++)
					A[i][_c[j]] = _X.get(i - _i0, j);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	@Override
	public final DoubleMatrixMN 		getMatrix(final int _i0, final int _i1, final int[] c) {
		DoubleMatrixMN X = new DoubleMatrixMN(_i1 - _i0 + 1, c.length);
		double[][] B = X.getStorage();
		try {
			for(int i = _i0; i <= _i1; i++)
				for(int j = 0; j < c.length; j++)
					B[i - _i0][j] = A[i][c[j]];

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}
	@Override
	public final void 			setMatrix(final int[] _rows, final int _j0, final int _j1, final NumberMatrix _X) {
		try {
			for (int i = 0; i < _rows.length; i++)
				for (int j = _j0; j <= _j1; j++)
					A[_rows[i]][j] = _X.getNumber(i, j - _j0).doubleValue();

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	public final void 			setMatrix(final int[] _rows, final int _j0, final int _j1, final DoubleMatrix _X) {
		try {
			for (int i = 0; i < _rows.length; i++)
				for (int j = _j0; j <= _j1; j++)
					A[_rows[i]][j] = _X.get(i, j - _j0);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}
	@Override
	public final DoubleMatrixMN 		getMatrix(final int[] _rows, final int _j0, final int _j1) {
		DoubleMatrixMN X = new DoubleMatrixMN(_rows.length, _j1 - _j0 + 1);
		double[][] B = X.getStorage();
		try {
			for(int i = 0; i < _rows.length; i++)
				for(int j = _j0; j <= _j1; j++)
					B[i][j - _j0] = A[_rows[i]][j];

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		plus(final Number _t) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + _t.doubleValue();
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		plus(final NumberMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B.getNumber(i, j).doubleValue();
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		plus(final double _t) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + _t;
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		plus(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B.get(i, j);
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		plusEquals(final Number _t) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] + _t.doubleValue();
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		plusEquals(final NumberMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] + B.getNumber(i, j).doubleValue();
			}
		}
		return this;
	}

	@Override
	public final DoubleMatrixMN 		plusEquals(final double _t) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] + _t;
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		plusEquals(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] + B.get(i, j);
			}
		}
		return this;
	}

	@Override
	public final DoubleMatrixMN 		minus(final Number _t) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - _t.doubleValue();
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		minus(final NumberMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B.getNumber(i, j).doubleValue();
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		minus(final double _t) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - _t;
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		minus(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B.get(i, j);
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		minusEquals(final Number _t) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] - _t.doubleValue();
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		minusEquals(final NumberMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] - B.getNumber(i, j).doubleValue();
			}
		}
		return this;
	}

	@Override
	public final DoubleMatrixMN 		minusEquals(final double _t) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] - _t;
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		minusEquals(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] - B.get(i, j);
			}
		}
		return this;
	}

	@Override
	public final DoubleMatrixMN 		times(final Number s) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = s.doubleValue() * A[i][j];
			}
		}
		return X;
	}
	@Override
	public final DoubleVectorND 		times(final NumberVector B) {
		if(B.size() != n)
			throw new IllegalArgumentException("Matrix/Vector inner dimensions must agree.");

		DoubleVectorND X = new DoubleVectorND(m);
		for(int j = 0; j < m; j++) {
			double s = 0;

			for(int k = 0; k < n; k++)
				s += A[j][k] * B.getNumber(k).doubleValue();

			X.setNumber(s, j);
		}

		return X;
	}
	@Override
	public final DoubleMatrixMN 		times(final NumberMatrix B) {
		if(B.rows() != n)
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");

		DoubleMatrixMN X = new DoubleMatrixMN(m, B.columns());
		double[][] C = X.getStorage();
		double[] Bcolj = new double[n];
		for(int j = 0; j < B.columns(); j++) {
			for(int k = 0; k < n; k++)
				Bcolj[k] = B.getNumber(k, j).doubleValue();

			for(int i = 0; i < m; i++) {
				double[] Arowi = A[i];
				double s = 0;
				for(int k = 0; k < n; k++)
					s += Arowi[k] * Bcolj[k];
				C[i][j] = s;
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		times(final double s) {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = s * A[i][j];
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		times(final DoubleMatrix B) {
		if(B.rows() != n)
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");

		DoubleMatrixMN X = new DoubleMatrixMN(m, B.columns());
		double[][] C = X.getStorage();
		double[] Bcolj = new double[n];
		for(int j = 0; j < B.columns(); j++) {
			for(int k = 0; k < n; k++)
				Bcolj[k] = B.get(k, j);

			for(int i = 0; i < m; i++) {
				double[] Arowi = A[i];
				double s = 0;
				for(int k = 0; k < n; k++)
					s += Arowi[k] * Bcolj[k];
				C[i][j] = s;
			}
		}
		return X;
	}
	@Override
	public final DoubleVectorND		times(final DoubleVector B) {
		if(B.size() != n)
			throw new IllegalArgumentException("Matrix/Vector inner dimensions must agree.");

		DoubleVectorND X = new DoubleVectorND(m);
		for(int j = 0; j < m; j++) {
			double s = 0;

			for(int k = 0; k < n; k++)
				s += A[j][k] * B.get(k);

			X.set(s, j);
		}

		return X;
	}

	@Override
	public final DoubleMatrixMN 		timesEquals(final Number s) {
		for(int i = 0; i < m; i++)
			for(int j = 0; j < n; j++)
				A[i][j] = s.doubleValue() * A[i][j];
		return this;
	}
	@Override
	public final DoubleMatrixMN 		timesEquals(final double s) {
		for(int i = 0; i < m; i++)
			for(int j = 0; j < n; j++)
				A[i][j] = s * A[i][j];
		return this;
	}
	@Override
	public final DoubleMatrixMN 		timesEquals(final NumberMatrix m) {
		throw new NotYetImplementedException();
//		return this;
	}
	@Override
	public final DoubleMatrixMN 		timesEquals(final DoubleMatrix m) {
		throw new NotYetImplementedException();
//		return this;
	}

	@Override
	public final DoubleMatrixMN 		arrayTimes(final NumberMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] * B.getNumber(i, j).doubleValue();
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		arrayTimes(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] * B.get(i, j);
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		arrayTimesEquals(final NumberMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] * B.getNumber(i, j).doubleValue();
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		arrayTimesEquals(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] * B.get(i, j);
			}
		}
		return this;
	}


	@Override
	public final DoubleMatrixMN 		arrayRightDivide(final NumberMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] / B.getNumber(i, j).doubleValue();
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		arrayRightDivide(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] / B.get(i, j);
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		arrayRightDivideEquals(final NumberMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] / B.getNumber(i, j).doubleValue();
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		arrayRightDivideEquals(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] / B.get(i, j);
			}
		}
		return this;
	}

	@Override
	public final DoubleMatrixMN 		arrayLeftDivide(final NumberMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = B.getNumber(i, j).doubleValue() / A[i][j];
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		arrayLeftDivide(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = B.get(i, j) / A[i][j];
			}
		}
		return X;
	}

	@Override
	public final DoubleMatrixMN 		arrayLeftDivideEquals(final NumberMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = B.getNumber(i, j).doubleValue() / A[i][j];
			}
		}
		return this;
	}
	@Override
	public final DoubleMatrixMN 		arrayLeftDivideEquals(final DoubleMatrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = B.get(i, j) / A[i][j];
			}
		}
		return this;
	}

	public final boolean 		isValid() {
		return false;
	}

	public final boolean 		isEqual(final double _d) {
		return false;
	}
	public final boolean 		isEqual(final NumberMatrix _m) {
		if(_m.rows() != rows() || _m.columns() != columns())
			return false;
		return false;
	}
	public final boolean 		isEqual(final DoubleMatrix _m) {
		if(_m.rows() != rows() || _m.columns() != columns())
			return false;
		return false;
	}

	public final boolean 		isDifferent(final double _d) {
		return false;
	}
	public final boolean 		isDifferent(final NumberMatrix _m) {
		if(_m.rows() != rows() || _m.columns() != columns())
			return false;
		return false;
	}
	public final boolean 		isDifferent(final DoubleMatrix _m) {
		if(_m.rows() != rows() || _m.columns() != columns())
			return false;
		return false;
	}

	@Override
	public DoubleMatrixMN 			clone() {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		abs() {
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = Math.abs(A[i][j]);
			}
		}
		return X;
	}
	@Override
	public final NumberMatrix			inverse() {
		return solve(identity(m, m));
	}
	@Override
	public final DoubleMatrixMN 		transpose() {
		DoubleMatrixMN X = new DoubleMatrixMN(n, m);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[j][i] = A[i][j];
			}
		}
		return X;
	}
	@Override
	public final DoubleMatrixMN 		uminus() {
		// TODO Auto-generated method stub
		DoubleMatrixMN X = new DoubleMatrixMN(m, n);
		double[][] C = X.getStorage();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = -A[i][j];
			}
		}
		return X;
	}

	@Override
	public final int 			rank() {
		return new SingularValueDecomposition(this).rank();
	}
	@Override
	public final double 		cond() {
		return new SingularValueDecomposition(this).cond();
	}
	@Override
	public final double 		det() {
		return new LUDecomposition(this).det();
	}
	@Override
	public final double 		trace() {
		double t = 0;
		for (int i = 0; i < Math.min(m, n); i++) {
			t += A[i][i];
		}
		return t;
	}
	@Override
	public final double 		norm1() {
		double f = 0;
		for (int j = 0; j < n; j++) {
			double s = 0;
			for (int i = 0; i < m; i++) {
				s += Math.abs(A[i][j]);
			}
			f = Math.max(f, s);
		}
		return f;
	}
	@Override
	public final double 		norm2() {
		return new SingularValueDecomposition(this).norm2();
	}
	@Override
	public final double 		normInf() {
		double f = 0;
		for (int i = 0; i < m; i++) {
			double s = 0;
			for (int j = 0; j < n; j++) {
				s += Math.abs(A[i][j]);
			}
			f = Math.max(f, s);
		}
		return f;
	}
	@Override
	public final double 		normF() { // Frobenius
		double f = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				f = Numbers.hypot(f, A[i][j]);
			}
		}
		return f;
	}

	@Override
	public final String 		toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		
		for(int i = 0; i < m; ++i) {
			for(int j = 0; j < n; ++j)
				sb.append(get(i, j) + " ");
			sb.append("\n");
		}

		sb.append("]");
		return sb.toString();
	}
	@Override
	public final String 		toString(final NumberFormat _nf) {
		return ""; //"[" + _nf.format(m00) + " " + _nf.format(m01) + " " + _nf.format(m02) + ",\n " + _nf.format(m10) + " " + _nf.format(m11) + " " + _nf.format(m12) + ",\n " + _nf.format(m20) + " " + _nf.format(m21) + " " + _nf.format(m22) + "]";
	}

	@Override
	public final NumberMatrix 		solve(final NumberMatrix B) {
		return (m == n ? (new LUDecomposition(this)).solve(B) : (new QRDecomposition(this)).solve(B));
	}
	@Override
	public final NumberVector			solve(NumberVector B) {
		return null;
	}

	@Override
	public final NumberMatrix 		solveTranspose(final NumberMatrix B) {
		return transpose().solve(B.transpose());
	}
	@Override
	public final NumberVector 		solveTranspose(NumberVector B) {
		return null;
	}

	@Override
	public final int 			compareTo(final Object o) {
		return 0;
	}

	/**
	 * Matrix Decomposition
	 */
	public final LUDecomposition 			lu() {
		return new LUDecomposition(this);
	}
	public final QRDecomposition 			qr() {
		return new QRDecomposition(this);
	}
	public final CholeskyDecomposition 		chol() {
		return new CholeskyDecomposition(this);
	}
	public final SingularValueDecomposition svd() {
		return new SingularValueDecomposition(this);
	}
	public final EigenvalueDecomposition 	eig() {
		return new EigenvalueDecomposition(this);
	}

	private void checkMatrixDimensions(final NumberMatrix B) {
		if (B.rows() != m || B.columns() != n) {
			throw new IllegalArgumentException("Matrix dimensions must agree.");
		}
	}

//	@Override
	public final double[][] 	getStorage() {
		return A;
	}
//	@Override
	public final double[][] 	getStorageCopy() {
		double[][] C = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return C;
	}
//	@Override
	public final double[] 	getColumnPackedCopy() {
		double[] vals = new double[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i + j * m] = A[i][j];
			}
		}
		return vals;
	}
//	@Override
	public final double[] 	getRowPackedCopy() {
		double[] vals = new double[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i * n + j] = A[i][j];
			}
		}
		return vals;
	}

}
