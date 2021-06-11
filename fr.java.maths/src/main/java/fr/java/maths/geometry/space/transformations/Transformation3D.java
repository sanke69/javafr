package fr.java.maths.geometry.space.transformations;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix44;
import fr.java.maths.geometry.types.Points;
import fr.java.nio.buffer.Point3DBufferX;

public class Transformation3D {

	public static Transformation3D of(DoubleMatrix44 _matrix) {
		return new Transformation3D(_matrix);
	}

	protected DoubleMatrix44 m;

	public Transformation3D() {
		super();
		m = DoubleMatrix44.identity();
	}
	public Transformation3D(DoubleMatrix44 _matrix) {
		super();
		m = _matrix;
	}

	public DoubleMatrix44 			asUniformMatrix() {
		return m;
	}

	public Point3D 				apply(Point3D _pt) {
		return (Point3D) asUniformMatrix().times(Vectors.of(_pt).uniform(1.0)).downgrade();
	}
	public Point3D[] 			apply(Point3D[] _ptArray) {
		for(int i = 0; i < _ptArray.length; ++i)
			_ptArray[i] = (Point3D) asUniformMatrix().times(Vectors.of(_ptArray[i]).uniform()).downgrade();
		return _ptArray;
	}
	public List<Point3D>		apply(List<Point3D> _ptList) {
		for(int i = 0; i < _ptList.size(); ++i)
			_ptList.set(i, (Point3D) asUniformMatrix().times(Vectors.of(_ptList.get(i)).uniform()).downgrade());
//		for(Point3D pt : _ptCollection)
//			pt.set((Vector3D) asUniformMatrix().times(pt.uniform()).as3D());
		return _ptList;
	}
	public Set<Point3D>			apply(Set<Point3D> _ptSet) {
		Set<Point3D> newSet = new LinkedHashSet<Point3D>();
		for(Point3D pt : _ptSet)
			newSet.add((Point3D) asUniformMatrix().times(Vectors.of(pt).uniform()).downgrade());
		return newSet;
	}
	/*
	public Collection<Point3D>	apply(Collection<Point3D> _ptCollection) {
		for(Point3D pt : _ptCollection)
			pt.set((Vector3D) asUniformMatrix().times(pt.uniform()).as3D());
		return _ptCollection;
	}
	*/
	public Point3DBufferX 		apply(Point3DBufferX _ptBuffer) {
		for(int i = 0; i < _ptBuffer.capacity(); ++i)
			_ptBuffer.put(i, (Point3D) asUniformMatrix().times(Vectors.of(_ptBuffer.get(i)).uniform()).downgrade());
		return _ptBuffer;
	}

	public Transformation3D 	times(Transformation3D _other) {
		return new Transformation3D( asUniformMatrix().times(_other.asUniformMatrix()) );
	}
	public DoubleMatrix44 			times(DoubleMatrix44 _m) {
		return asUniformMatrix().times(_m);
	}

	public static Point3D 		transform(Transformation3D T, Point3D p) {
		// TODO:: q = new Matrix3d(i, j, k).times(p).plus(o);
		return Points.of(	p.getX() * T.m.m00 + p.getY() * T.m.m01 + p.getZ() * T.m.m02 + T.m.m03,
							p.getX() * T.m.m10 + p.getY() * T.m.m11 + p.getZ() * T.m.m12 + T.m.m13,
							p.getX() * T.m.m20 + p.getY() * T.m.m21 + p.getZ() * T.m.m22 + T.m.m23 );
	}
	public static Point3D 		transform(DoubleMatrix44 m, Point3D p) {
		// TODO:: q = new Matrix3d(i, j, k).times(p).plus(o);
		return Points.of(	p.getX() * m.m00 + p.getY() * m.m01 + p.getZ() * m.m02 + m.m03,
							p.getX() * m.m10 + p.getY() * m.m11 + p.getZ() * m.m12 + m.m13,
							p.getX() * m.m20 + p.getY() * m.m21 + p.getZ() * m.m22 + m.m23 );
	}

	public static Vector3D 		transform(Transformation3D T, Vector3D v) {
		// TODO:: q = new Matrix3d(i, j, k).times(p);
		return Vectors.of(	v.getX() * T.m.m00 + v.getY() * T.m.m01 + v.getZ() * T.m.m02,
							v.getX() * T.m.m10 + v.getY() * T.m.m11 + v.getZ() * T.m.m12,
							v.getX() * T.m.m20 + v.getY() * T.m.m21 + v.getZ() * T.m.m22 );
	}
	public static Vector3D 		transform(DoubleMatrix44 m, Vector3D v) {
		// TODO:: q = new Matrix3d(i, j, k).times(p);
		return Vectors.of(	v.getX() * m.m00 + v.getY() * m.m01 + v.getZ() * m.m02,
							v.getX() * m.m10 + v.getY() * m.m11 + v.getZ() * m.m12,
							v.getX() * m.m20 + v.getY() * m.m21 + v.getZ() * m.m22 );
	}

	public static Vector3D 		transform(DoubleMatrix44 m, Vector3D v, double w) {
		return Vectors.of( 	v.getX() * m.m00 + v.getY() * m.m10 + v.getZ() * m.m20 + w * m.m30,
							v.getX() * m.m01 + v.getY() * m.m11 + v.getZ() * m.m21 + w * m.m31,
							v.getX() * m.m02 + v.getY() * m.m12 + v.getZ() * m.m22 + w * m.m32 );
	}
	/*
	public Vector4d transform(Vector4d v) {
	    return new Vector4d(v.x * m00 + v.y * m10 + v.z * m20 + v.w * m30,
	                        v.x * m01 + v.y * m11 + v.z * m21 + v.w * m31,
	                        v.x * m02 + v.y * m12 + v.z * m22 + v.w * m32,
	                        v.x * m03 + v.y * m13 + v.z * m23 + v.w * m33);
	}
	*/

}
