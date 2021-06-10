package fr.java.maths.geometry.plane.transformations.special;

import fr.java.maths.geometry.plane.transformations.Transformation2D;

public class Translation2D extends Transformation2D {

	public Translation2D(double _tx, double _ty) {
		super();

		m.m00 = 1.0f; m.m01 = 0.0f; m.m02 =  _tx;
		m.m10 = 0.0f; m.m11 = 1.0f; m.m12 =  _ty;
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

}
