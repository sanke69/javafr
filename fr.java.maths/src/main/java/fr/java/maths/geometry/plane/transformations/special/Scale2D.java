package fr.java.maths.geometry.plane.transformations.special;

import fr.java.maths.geometry.plane.transformations.Transformation2D;

public class Scale2D extends Transformation2D {
	double sx, sy;

	public Scale2D() {
		super();
	}
	public Scale2D(double _sx, double _sy) {
		super();

		sx = _sx;
		sy = _sy;

		m.m00 =   sx; m.m01 = 0.0f; m.m02 = 0.0f;
		m.m10 = 0.0f; m.m11 =   sy; m.m12 = 0.0f;
		m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f;
	}

	public double getSX() {
		return m.m00;
	}
	public void setSX(double _sx) {
		m.m00 = _sx;
	}

	public double getSY() {
		return m.m11;
	}
	public void setSY(double _sy) {
		m.m11 = _sy;
	}

}
