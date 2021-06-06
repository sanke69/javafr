package fr.java.sdk.math.geometry.space.transformations.specials;

import fr.java.sdk.math.geometry.space.transformations.Transformation3D;

public class Scale3D extends Transformation3D {

	public Scale3D(double _sx, double _sy, double _sz) {
		super();

	    m.m00 =  _sx; m.m01 = 0.0f; m.m02 = 0.0f; m.m03 = 0.0f;
		m.m10 = 0.0f; m.m11 =  _sy; m.m12 = 0.0f; m.m13 = 0.0f;
		m.m20 = 0.0f; m.m21 = 0.0f; m.m22 =  _sz; m.m23 = 0.0f;
		m.m30 = 0.0f; m.m31 = 0.0f; m.m32 = 0.0f; m.m33 = 1.0f;
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

	public double getSZ() {
		return m.m22;
	}
	public void setSZ(double _sz) {
		m.m22 = _sz;
	}

}
