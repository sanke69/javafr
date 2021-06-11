package fr.java.maths.geometry.space.transformations;

import java.util.Collection;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix44;
import fr.java.nio.buffer.Point3DBufferX;
import fr.java.nio.buffer.Vector3DBufferX;

public interface ITransformation3D {

	public DoubleMatrix44 					toMatrix();

	public default Point3D 				transformPoint(Point3D _pt) {
		return (Point3D) toMatrix().times(Vectors.of(_pt).uniform()).downgrade();
	}
	public default Point3D[] 			transformPoint(Point3D[] _ptArray) {
		for(int i = 0; i < _ptArray.length; ++i)
			_ptArray[i] = (Point3D) toMatrix().times(Vectors.of(_ptArray[i]).uniform()).downgrade();
		return _ptArray;
	}
	public default Collection<Point3D.Editable>	transformPoint(Collection<Point3D.Editable> _ptCollection) {
		for(Point3D.Editable pt : _ptCollection)
			pt.set((Vector3D) toMatrix().times(Vectors.of(pt)).downgrade());
		return _ptCollection;
	}
	public default Point3DBufferX 		transformPoint(Point3DBufferX _ptBuffer) {
		for(int i = 0; i < _ptBuffer.capacity(); ++i)
			_ptBuffer.put(i, (Point3D) toMatrix().times(Vectors.of(_ptBuffer.get(i)).uniform()).downgrade());
		return _ptBuffer;
	}

	public default Vector3D 			transformVector(Vector3D _vec) {
		return toMatrix().times(_vec.uniform()).downgrade();
	}
	public default Vector3D[] 			transformVector(Vector3D[] _vecArray) {
		for(int i = 0; i < _vecArray.length; ++i)
			_vecArray[i] = toMatrix().times(_vecArray[i].uniform()).downgrade();
		return _vecArray;
	}
	public default Collection<Vector3D.Editable>	transformVector(Collection<Vector3D.Editable> _vecCollection) {
		for(Vector3D.Editable vec : _vecCollection)
			vec.set((Vector3D) toMatrix().times(vec.uniform()).downgrade());
		return _vecCollection;
	}
	public default Vector3DBufferX 		transformVector(Vector3DBufferX _vecBuffer) {
		for(int i = 0; i < _vecBuffer.capacity(); ++i)
			_vecBuffer.put(i, toMatrix().times(_vecBuffer.get(i).uniform()).downgrade());
		return _vecBuffer;
	}

}
