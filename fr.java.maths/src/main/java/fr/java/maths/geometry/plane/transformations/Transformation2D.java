package fr.java.maths.geometry.plane.transformations;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix33;
import fr.java.nio.buffer.Point2DBufferX;

// http://morpheo.inrialpes.fr/people/Boyer/Teaching/RICM/
public class Transformation2D implements Cloneable {

	protected DoubleMatrix33 m;

	public Transformation2D() {
		super();
		m = DoubleMatrix33.identity();
	}
	public Transformation2D(DoubleMatrix33 _m) {
		super();
		m = _m.clone();
	}

	public DoubleMatrix33 asUniformMatrix() {
		return m;
	}

	public Point2D 			apply(Point2D _pt) {
		return (Point2D) asUniformMatrix().times(Vectors.of(_pt).uniform(1.0)).downgrade();
	}
	public Point2D[] 		apply(Point2D[] _ptArray) {
		for(int i = 0; i < _ptArray.length; ++i)
			_ptArray[i] = (Point2D) asUniformMatrix().times(Vectors.of(_ptArray[i]).uniform()).downgrade();
		return _ptArray;
	}
	public Point2DBufferX 	apply(Point2DBufferX _ptBuffer) {
		for(int i = 0; i < _ptBuffer.capacity(); ++i)
			_ptBuffer.put(i, (Point2D) asUniformMatrix().times(Vectors.of(_ptBuffer.get(i)).uniform()).downgrade());
		return _ptBuffer;
	}

	public DoubleMatrix33 times(Transformation2D _other) {
		return asUniformMatrix().times(_other.asUniformMatrix());
	}
	public DoubleMatrix33 times(DoubleMatrix33 _m) {
		return asUniformMatrix().times(_m);
	}

	public Transformation2D clone() {
		return new Transformation2D(m);
	}
}
