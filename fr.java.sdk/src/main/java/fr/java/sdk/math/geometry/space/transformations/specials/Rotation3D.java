package fr.java.sdk.math.geometry.space.transformations.specials;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.matrices.Matrix44d;
import fr.java.sdk.math.geometry.Geometry;
import fr.java.sdk.math.geometry.space.transformations.Transformation3D;

public final class Rotation3D extends Transformation3D {

	public static final Transformation3D from(Point3D _position, double _pitch, double _roll, double _yawed) {
		return null;
	}
	public static final Rotation3D aroundX(double _angle) {
		return aroundX(_angle, null);
	}
	public static final Rotation3D aroundX(double _angle, Point3D _pivot) {
		Rotation3D rot = new Rotation3D(_angle, Geometry.Space.WorldXAxis);

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		rot.m.m00 = 1.0f; rot.m.m01 = 0.0f; rot.m.m02 = 0.0f; rot.m.m03 = 0.0f;
		rot.m.m10 = 0.0f; rot.m.m11 =  Cos; rot.m.m12 = -Sin; rot.m.m13 = 0.0f;
		rot.m.m20 = 0.0f; rot.m.m21 =  Sin; rot.m.m22 =  Cos; rot.m.m23 = 0.0f;
		rot.m.m30 = 0.0f; rot.m.m31 = 0.0f; rot.m.m32 = 0.0f; rot.m.m33 = 1.0f;

		if(_pivot != null && _pivot != Points.zero3())
			rot.setPivot(_pivot);

		return rot;
	}
	public static final Rotation3D aroundY(double _angle) {
		return aroundY(_angle, null);
	}
	public static final Rotation3D aroundY(double _angle, Point3D _pivot) {
		Rotation3D rot = new Rotation3D(_angle, Geometry.Space.WorldYAxis);

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		rot.m.m00 =  Cos; rot.m.m01 = 0.0f; rot.m.m02 = -Sin; rot.m.m03 = 0.0f;
		rot.m.m10 = 0.0f; rot.m.m11 = 1.0f; rot.m.m12 = 0.0f; rot.m.m13 = 0.0f;
		rot.m.m20 =  Sin; rot.m.m21 = 0.0f; rot.m.m22 =  Cos; rot.m.m23 = 0.0f;
		rot.m.m30 = 0.0f; rot.m.m31 = 0.0f; rot.m.m32 = 0.0f; rot.m.m33 = 1.0f;

		if(_pivot != null && _pivot != Points.zero3())
			rot.setPivot(_pivot);

		return rot;
	}
	public static final Rotation3D aroundZ(double _angle) {
		return aroundZ(_angle, null);
	}
	public static final Rotation3D aroundZ(double _angle, Point3D _pivot) {
		Rotation3D rot = new Rotation3D(_angle, Geometry.Space.WorldZAxis);

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		rot.m.m00 =  Cos; rot.m.m01 = -Sin; rot.m.m02 = 0.0f; rot.m.m03 = 0.0f;
		rot.m.m10 =  Sin; rot.m.m11 =  Cos; rot.m.m12 = 0.0f; rot.m.m13 = 0.0f;
		rot.m.m20 = 0.0f; rot.m.m21 = 0.0f; rot.m.m22 = 1.0f; rot.m.m23 = 0.0f;
		rot.m.m30 = 0.0f; rot.m.m31 = 0.0f; rot.m.m32 = 0.0f; rot.m.m33 = 1.0f;

		if(_pivot != null && _pivot != Points.zero3())
			rot.setPivot(_pivot);

		return rot;
	}

	double   angle;
	Point3D  pivot;
	Vector3D axis;

	public Rotation3D() {
		super();
	}
	public Rotation3D(double _angle, Vector3D _axis) {
		super();

		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		if(_axis.equals(Geometry.Space.WorldXAxis)) {
			m.m00 = 1.0f; m.m01 = 0.0f; m.m02 = 0.0f; m.m03 = 0.0f;
			m.m10 = 0.0f; m.m11 =  Cos; m.m12 = -Sin; m.m13 = 0.0f;
			m.m20 = 0.0f; m.m21 =  Sin; m.m22 =  Cos; m.m23 = 0.0f;
			m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
		} else if(_axis.equals(Geometry.Space.WorldYAxis)) {
			m.m00 =  Cos; m.m01 = 0.0f; m.m02 = -Sin; m.m03 = 0.0f;
			m.m10 = 0.0f; m.m11 = 1.0f; m.m12 = 0.0f; m.m13 = 0.0f;
			m.m20 =  Sin; m.m21 = 0.0f; m.m22 =  Cos; m.m23 = 0.0f;
			m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
		} else if(_axis.equals(Geometry.Space.WorldZAxis)) {
			m.m00 =  Cos; m.m01 = -Sin; m.m02 = 0.0f; m.m03 = 0.0f;
			m.m10 =  Sin; m.m11 =  Cos; m.m12 = 0.0f; m.m13 = 0.0f;
			m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f; m.m23 = 0.0f;
			m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
		} else {
			m = Matrix44d.identity();
			throw new NotYetImplementedException("Must use Quaternion !!!");
		}
	}

	public double getAngle() {
		return angle;
	}
	public void setAngle(double _angle) {
		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		if(axis.equals(Geometry.Space.WorldXAxis)) {
			m.m11 =  Cos; m.m12 = -Sin;
			m.m21 =  Sin; m.m22 =  Cos;
		} else if(axis.equals(Geometry.Space.WorldYAxis)) {
			m.m00 =  Cos; m.m02 = -Sin;
			m.m20 =  Sin; m.m22 =  Cos;
		} else if(axis.equals(Geometry.Space.WorldZAxis)) {
			m.m00 =  Cos; m.m01 = -Sin;
			m.m10 =  Sin; m.m11 =  Cos;
		} else {
			m = Matrix44d.identity();
			throw new NotYetImplementedException("Must use Quaternion !!!");
		}
	}

	public Point3D getPivot() {
		return pivot != null ? pivot : Points.zero3();
	}
	public void setPivot(Point3D _pivot) {
		pivot = _pivot;
	}

	@Override
	public Matrix44d asUniformMatrix() {
		if(pivot != null && pivot != Points.zero3()) {
			Translation3D 	Tr1 = new Translation3D(pivot.getX(), pivot.getY(), pivot.getZ()),
							Tr2 = new Translation3D(-pivot.getX(), -pivot.getY(), -pivot.getZ());

			Matrix44d mWithPivot = Tr1.asUniformMatrix().times(m.times(Tr2.asUniformMatrix()));

//			System.out.println("A= " + angle + ", P=" + pivot + ", m=");
//			System.out.println(m);
//			System.out.println("mWithPivot=");
//			System.out.println(mWithPivot);
//			System.out.println("---");

			return mWithPivot;
		}

		return m;
	}

}
