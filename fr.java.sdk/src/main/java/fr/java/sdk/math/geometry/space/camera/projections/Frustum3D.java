package fr.java.sdk.math.geometry.space.camera.projections;

import fr.java.sdk.math.geometry.space.camera.Projections3D;

public class Frustum3D extends Projections3D {

	double left, right, bottom, top, zNear, zFar;

	public Frustum3D(double _left, double _right, double _bottom, double _top, double _zNear, double _zFar) {
		super();

		left   = _left;
		right  = _right;
		bottom = _bottom;
		top    = _top;
		zNear  = _zNear;
		zFar   = _zFar;

		update();
	}

	public void 	setLeft(double _left) {
		left = _left;
		update();
	}
	public double 	getLeft() {
		return left;
	}

	public void 	setRight(double _right) {
		right = _right;
		update();
	}
	public double 	getRight() {
		return right;
	}

	public void 	setTop(double _top) {
		top    = _top;
		update();
	}
	public double 	getTop() {
		return top;
	}

	public void 	setBottom(double _bottom) {
		bottom = _bottom;
		update();
	}
	public double 	getBottom() {
		return bottom;
	}

	public void 	setNear(double _zNear) {
		zNear  = _zNear;
		update();
	}
	public double 	getNear() {
		return zNear;
	}

	public void 	setFar(double _zFar) {
		zFar   = _zFar;
		update();
	}
	public double 	getFar() {
		return zFar;
	}

    private void 	update() {
        double A11 =     (2 * zNear)      / (right - left);
        double A22 =     (2 * zNear)      / (top - bottom);
        double A13 =    (right + left)    / (right - left);
        double A23 =    (top + bottom)    / (top - bottom);
        double A33 =   - (zFar + zNear)   / (zFar - zNear);
        double A34 = (- 2 * zFar * zNear) / (zFar - zNear);

        m.m00 = A11;	m.m01 = 0.0f;	m.m02 = A13;	m.m03 = 0.0f;
        m.m10 = 0.0f;	m.m11 = A22;	m.m12 = A23;	m.m13 = 0.0f;
        m.m20 = 0.0f;	m.m21 = 0.0f;	m.m22 = A33;	m.m23 = A34;
        m.m30 = 0.0f;	m.m31 = 0.0f;	m.m32 = -1;	    m.m33 = 1.0f;
    }

}
