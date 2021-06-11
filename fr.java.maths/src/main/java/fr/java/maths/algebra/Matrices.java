package fr.java.maths.algebra;

import java.util.HashMap;
import java.util.Map;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.matrix.generic.Matrix33D;
import fr.java.maths.algebra.matrices.DoubleMatrix33;

public class Matrices {

	public Map<Integer, Integer> listExtrema(NumberMatrix _A) {
		int        	m = _A.rows(), 
					n = _A.columns();
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				double Aij     = _A.getNumber(i, j).doubleValue();
				double Aim1j   = _A.getNumber(i - 1, j).doubleValue();
				double Aijm1   = _A.getNumber(i, j - 1).doubleValue();
				double Aip1j   = _A.getNumber(i + 1, j).doubleValue();
				double Aijp1   = _A.getNumber(i, j + 1).doubleValue();

				if (i != 0 && j != 0) {
					if (Aij > Aim1j && Aij > Aip1j && Aij > Aijm1 && Aij > Aijp1)
						candidates.put(i, j);
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijm1 && Aij < Aijp1)
						candidates.put(i, j);
				} else if (i != 0) {
					if (Aij > Aim1j && Aij > Aip1j && Aij > Aijp1)
						candidates.put(i, j);
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijp1)
						candidates.put(i, j);
				} else if (j != 0) {
					if (Aij > Aip1j && Aij > Aijm1 && Aij > Aijp1)
						candidates.put(i, j);
					if (Aij < Aip1j && Aij < Aijm1 && Aij < Aijp1)
						candidates.put(i, j);
				}
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> listMaxima(NumberMatrix _A) {
		int        	m = _A.rows(), 
					n = _A.columns();
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				double Aij     = _A.getNumber(i, j).doubleValue();
				double Aim1j   = _A.getNumber(i - 1, j).doubleValue();
				double Aijm1   = _A.getNumber(i, j - 1).doubleValue();
				double Aip1j   = _A.getNumber(i + 1, j).doubleValue();
				double Aijp1   = _A.getNumber(i, j + 1).doubleValue();

				if (i != 0 && j != 0) {
					if (Aij > Aim1j && Aij > Aip1j && Aij > Aijm1
							&& Aij > Aijp1)
						candidates.put(i, j);
				} else if (i != 0) {
					if (Aij > Aim1j && Aij > Aip1j && Aij > Aijp1)
						candidates.put(i, j);
				} else if (j != 0) {
					if (Aij > Aip1j && Aij > Aijm1 && Aij > Aijp1)
						candidates.put(i, j);
				}
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> listMinima(NumberMatrix _A) {
		int        	m = _A.rows(), 
					n = _A.columns();
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				double Aij     = _A.getNumber(i, j).doubleValue();
				double Aim1j   = _A.getNumber(i - 1, j).doubleValue();
				double Aijm1   = _A.getNumber(i, j - 1).doubleValue();
				double Aip1j   = _A.getNumber(i + 1, j).doubleValue();
				double Aijp1   = _A.getNumber(i, j + 1).doubleValue();

				if (i != 0 && j != 0) {
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijm1
							&& Aij < Aijp1)
						candidates.put(i, j);
				} else if (i != 0) {
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijp1)
						candidates.put(i, j);
				} else if (j != 0) {
					if (Aij < Aip1j && Aij < Aijm1 && Aij < Aijp1)
						candidates.put(i, j);
				}
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> listMaximaAbove(NumberMatrix _A, final double _threshold) {
		int        	m = _A.rows(), 
					n = _A.columns();
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for (int i = 1; i < m - 1; ++i) {
			for (int j = 1; j < n - 1; ++j) {
				double Aij     = _A.getNumber(i, j).doubleValue();
				double Aim1j   = _A.getNumber(i - 1, j).doubleValue();
				double Aijm1   = _A.getNumber(i, j - 1).doubleValue();
				double Aip1j   = _A.getNumber(i + 1, j).doubleValue();
				double Aijp1   = _A.getNumber(i, j + 1).doubleValue();

				if (Aij > Aim1j && Aij > Aip1j && Aij > Aijm1 && Aij > Aijp1
						&& Aij > _threshold)
					candidates.put(i, j);
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> listMinimaBelow(NumberMatrix _A, final double _threshold) {
		int        	m = _A.rows(), 
					n = _A.columns();
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				double Aij     = _A.getNumber(i, j).doubleValue();
				double Aim1j   = _A.getNumber(i - 1, j).doubleValue();
				double Aijm1   = _A.getNumber(i, j - 1).doubleValue();
				double Aip1j   = _A.getNumber(i + 1, j).doubleValue();
				double Aijp1   = _A.getNumber(i, j + 1).doubleValue();

				if (i != 0 && j != 0) {
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijm1 && Aij < Aijp1
							&& Aij < _threshold)
						candidates.put(i, j);
				} else if (i != 0) {
					if (Aij < Aim1j && Aij < Aip1j && Aij < Aijp1 && Aij < _threshold)
						candidates.put(i, j);
				} else if (j != 0) {
					if (Aij < Aip1j && Aij < Aijm1 && Aij < Aijp1 && Aij < _threshold)
						candidates.put(i, j);
				}
			}
		}

		return candidates;
	}

	public final static Matrix33D zero() {
		return new DoubleMatrix33(0,0,0,0,0,0,0,0,0);
	}
	public final static Matrix33D identity() {
		return new DoubleMatrix33(1,0,0,0,1,0,0,0,1);
	}
	public final static Matrix33D unitary() {
		return new DoubleMatrix33(1,1,1,1,1,1,1,1,1);
	}

}
