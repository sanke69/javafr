package fr.java.maths.geometry.space.camera.projections;

import fr.java.maths.geometry.space.camera.Projections3D;

public class Ortho3D extends Projections3D {

	double left, right, bottom, top, zNear, zFar;

	public Ortho3D(double _left, double _right, double _bottom, double _top, double _zNear, double _zFar) {
		super();

		left   = _left;
		right  = _right;
		bottom = _bottom;
		top    = _top;
		zNear  = _zNear;
		zFar   = _zFar;

		update();
	}

	public void setLeft(double _left) {
		left = _left;
		update();
	}
	public double getLeft() {
		return left;
	}

	public void setRight(double _right) {
		right = _right;
		update();
	}
	public double getRight() {
		return right;
	}

	public void setTop(double _top) {
		top    = _top;
		update();
	}
	public double getTop() {
		return top;
	}

	public void setBottom(double _bottom) {
		bottom = _bottom;
		update();
	}
	public double getBottom() {
		return bottom;
	}

	public void setNear(double _zNear) {
		zNear  = _zNear;
		update();
	}
	public double getNear() {
		return zNear;
	}

	public void setFar(double _zFar) {
		zFar   = _zFar;
		update();
	}
	public double getFar() {
		return zFar;
	}

	private void update() {
		double A11 =         2       / (right - left);
		double A22 =         2       / (top - bottom);
		double A33 =        -2       / (zFar - zNear);
		double A14 = -(right + left) / (right - left);
		double A24 = -(top + bottom) / (top - bottom);
		double A34 = -(zFar + zNear) / (zFar - zNear);

		m.m00 = A11;     m.m10 = 0.0f;    m.m20 = 0.0f;    m.m30 = 0.0f; 
		m.m01 = 0.0f;    m.m11 = A22;     m.m21 = 0.0f;    m.m31 = 0.0f; 
		m.m02 = 0.0f;    m.m12 = 0.0f;    m.m22 = A33;     m.m32 = 0.0f; 
		m.m03 = A14;     m.m13 = A24;     m.m23 = A34;     m.m33 = 1.0f; 
		
	}

}
