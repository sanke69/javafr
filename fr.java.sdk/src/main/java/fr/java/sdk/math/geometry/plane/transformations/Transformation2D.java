package fr.java.sdk.math.geometry.plane.transformations;

import fr.java.math.geometry.plane.Point2D;
import fr.java.nio.buffer.Point2DBufferX;
import fr.java.sdk.math.algebra.matrices.Matrix33d;

// http://morpheo.inrialpes.fr/people/Boyer/Teaching/RICM/
public class Transformation2D implements Cloneable {

	protected Matrix33d m;

	public Transformation2D() {
		super();
		m = Matrix33d.identity();
	}
	public Transformation2D(Matrix33d _m) {
		super();
		m = _m.clone();
	}

	public Matrix33d asUniformMatrix() {
		return m;
	}

	public Point2D 			apply(Point2D _pt) {
		return (Point2D) asUniformMatrix().times(_pt.uniform(1.0)).as2D();
	}
	public Point2D[] 		apply(Point2D[] _ptArray) {
		for(int i = 0; i < _ptArray.length; ++i)
			_ptArray[i] = (Point2D) asUniformMatrix().times(_ptArray[i].uniform()).as2D();
		return _ptArray;
	}
	public Point2DBufferX 	apply(Point2DBufferX _ptBuffer) {
		for(int i = 0; i < _ptBuffer.capacity(); ++i)
			_ptBuffer.put(i, (Point2D) asUniformMatrix().times(_ptBuffer.get(i).uniform()).as2D());
		return _ptBuffer;
	}

	public Matrix33d times(Transformation2D _other) {
		return asUniformMatrix().times(_other.asUniformMatrix());
	}
	public Matrix33d times(Matrix33d _m) {
		return asUniformMatrix().times(_m);
	}

	public Transformation2D clone() {
		return new Transformation2D(m);
	}
}
