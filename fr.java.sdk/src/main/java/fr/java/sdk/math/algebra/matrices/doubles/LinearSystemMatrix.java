package fr.java.sdk.math.algebra.matrices.doubles;

import fr.java.math.algebra.NumberMatrix;
import fr.java.math.algebra.NumberVector;
import fr.java.math.algebra.matrix.DoubleMatrix;

public interface LinearSystemMatrix extends DoubleMatrix {

	/**
	 * Solve A*X = B
	 * 
	 * @param 	B			right hand side
	 * @return 	solution 	if A is square, least squares solution otherwise
	 */
	public NumberMatrix solve(NumberMatrix B);
	public NumberVector solve(NumberVector B);

	/**
	 * Solve X*A = B, which is also A'*X' = B'
	 * 
	 * @param 	B			right hand side
	 * @return 	solution 	if A is square, least squares solution otherwise.
	 */
	public NumberMatrix solveTranspose(NumberMatrix B);
	public NumberVector solveTranspose(NumberVector B);

}
