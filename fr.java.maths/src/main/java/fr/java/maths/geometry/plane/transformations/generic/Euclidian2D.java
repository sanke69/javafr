package fr.java.maths.geometry.plane.transformations.generic;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.maths.geometry.plane.transformations.Transformation2D;

public class Euclidian2D extends Transformation2D {
	double angle;

	public static Euclidian2D of(double _tx, double _ty, double _angle) {
		return new Euclidian2D(_tx, _ty, _angle);
	}
	
	public Euclidian2D() {
		super();
	}
	public Euclidian2D(double _tx, double _ty) {
		super();

		m.m02 =  _tx;
		m.m12 =  _ty;
	}
	public Euclidian2D(double _tx, double _ty, double _angle) {
		super();

		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		m.m00 =  Cos; m.m01 = -Sin; m.m02 =  _tx;
		m.m10 =  Sin; m.m11 =  Cos; m.m12 =  _ty;
		m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f;
	}
	
	public double getTX() {
		return m.m02;
	}
	public void setTX(double _tx) {
		m.m02 = _tx;
	}

	public double getTY() {
		return m.m12;
	}
	public void setTY(double _ty) {
		m.m12 = _ty;
	}

	public void setTranslation(double _tx, double _ty) {
		m.m02 = _tx;
		m.m12 = _ty;
	}
	public void setTranslation(Vector2D _t) {
		m.m02 = _t.getX();
		m.m12 = _t.getY();
	}

	public double getAngle() {
		return angle;
	}
	public void setAngle(double _angle) {
		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		m.m00 =  Cos; m.m01 = -Sin;
		m.m10 =  Sin; m.m11 =  Cos;
	}

	public Euclidian2D clone() {
		return new Euclidian2D(m.m02, m.m12, angle);
	}

	public String toString() {
		return "Euclidian2D:: " + "(" + m.m02 + ", " + m.m12 + ")" + ", " + angle;
	}

}
