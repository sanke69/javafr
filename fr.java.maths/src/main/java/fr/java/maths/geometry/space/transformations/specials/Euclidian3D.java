package fr.java.maths.geometry.space.transformations.specials;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.DoubleMatrix33;
import fr.java.maths.geometry.space.transformations.Transformation3D;

public class Euclidian3D extends Transformation3D {
	DoubleMatrix33 rotation;

	public static final Euclidian3D aroundX(double _tx, double _ty, double _tz, double _angle) {
		Euclidian3D e3d = new Euclidian3D();

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		e3d.m.m03 = _tx;
		e3d.m.m13 = _ty;
		e3d.m.m23 = _tz;

		e3d.m.m11 =  Cos; e3d.m.m12 = -Sin;
		e3d.m.m21 =  Sin; e3d.m.m22 =  Cos;

		return e3d;
	}
	public static final Euclidian3D aroundY(double _tx, double _ty, double _tz, double _angle) {
		Euclidian3D e3d = new Euclidian3D();

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		e3d.m.m03 = _tx;
		e3d.m.m13 = _ty;
		e3d.m.m23 = _tz;

		e3d.m.m00 =  Cos; e3d.m.m02 = -Sin;
		e3d.m.m20 =  Sin; e3d.m.m22 =  Cos;

		return e3d;
	}
	public static final Euclidian3D aroundZ(double _tx, double _ty, double _tz, double _angle) {
		Euclidian3D e3d = new Euclidian3D();

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		e3d.m.m03 = _tx;
		e3d.m.m13 = _ty;
		e3d.m.m23 = _tz;

		e3d.m.m00 =  Cos; e3d.m.m01 = -Sin;
		e3d.m.m10 =  Sin; e3d.m.m11 =  Cos;

		return e3d;
	}

	public Euclidian3D() {
		super();
		rotation = DoubleMatrix33.identity();
	}
	public Euclidian3D(double _tx, double _ty, double _tz, DoubleMatrix33 _rotation) {
		super();
		rotation = _rotation;

		m.m03 = _tx;
		m.m13 = _ty;
		m.m23 = _tz;

		m.setMatrix(0, 2, 0, 2, rotation);
	}

	public void 		set(double _tx, double _ty, double _tz, DoubleMatrix33 _rotation) {
		rotation = _rotation;

		m.m03 = _tx;
		m.m13 = _ty;
		m.m23 = _tz;

		m.setMatrix(0, 2, 0, 2, rotation);
	}
	public void 		set(Vector3D _translation, DoubleMatrix33 _rotation) {
		rotation = _rotation;

		m.m03 = _translation.getX();
		m.m13 = _translation.getY();
		m.m23 = _translation.getZ();

		m.setMatrix(0, 2, 0, 2, rotation);
	}

	public double 		getTX() {
		return m.m03;
	}
	public void 		setTX(double _tx) {
		m.m03 = _tx;
	}

	public double 		getTY() {
		return m.m13;
	}
	public void 		setTY(double _ty) {
		m.m13 = _ty;
	}

	public double 		getTZ() {
		return m.m23;
	}
	public void 		setTZ(double _tz) {
		m.m23 = _tz;
	}

	public Vector3D 	getTranslation() {
		return Vectors.of(m.m03, m.m13, m.m23);
	}
	public void 		setTranslation(double _tx, double _ty, double _tz) {
		m.m03 = _tx;
		m.m13 = _ty;
		m.m23 = _tz;
	}
	public void 		setTranslation(Vector3D _translation) {
		m.m03 = _translation.getX();
		m.m13 = _translation.getY();
		m.m23 = _translation.getZ();
	}

	public DoubleMatrix33 	getRotation() {
		return rotation;
	}
	public void 		setRotation(double _ax, double _ay, double _az) {
//		Rotation3D X   = Rotation3D.aroundX(_ax);
//		Rotation3D Y   = Rotation3D.aroundX(_ay);
//		Rotation3D Z   = Rotation3D.aroundX(_az);
//		Matrix44d  rot = X.asUniformMatrix().times(Y.asUniformMatrix()).times(Z.asUniformMatrix());
	}
	public void 		setRotation(DoubleMatrix33 _rotation) {
		rotation = _rotation;
	}

}
