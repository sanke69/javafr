package fr.java.sdk.math.geometry.space.transformations.specials;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.geometry.space.transformations.Transformation3D;

public class Translation3D extends Transformation3D {

	public static Translation3D of(double _tx, double _ty, double _tz) {
		return new Translation3D(_tx, _ty, _tz);
	}
	public static Translation3D of(Point3D _p) {
		return new Translation3D(_p.getX(), _p.getY(), _p.getZ());
	}
	public static Translation3D of(Vector3D _vec) {
		return new Translation3D(_vec.getX(), _vec.getY(), _vec.getZ());
	}

	public Translation3D(double _tx, double _ty, double _tz) {
		super();

		m.m00 = 1.0f; m.m01 = 0.0f; m.m02 = 0.0f; m.m03 =  _tx;
		m.m10 = 0.0f; m.m11 = 1.0f; m.m12 = 0.0f; m.m13 =  _ty;
		m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f; m.m23 =  _tz;
		m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
	}
	
	public double getTX() {
		return m.m03;
	}
	public void setTX(double _tx) {
		m.m03 = _tx;
	}

	public double getTY() {
		return m.m13;
	}
	public void setTY(double _ty) {
		m.m13 = _ty;
	}

	public double getTZ() {
		return m.m23;
	}
	public void setTZ(double _tz) {
		m.m23 = _tz;
	}

}
