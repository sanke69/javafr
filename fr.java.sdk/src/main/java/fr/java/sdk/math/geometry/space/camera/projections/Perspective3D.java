package fr.java.sdk.math.geometry.space.camera.projections;

import fr.java.sdk.math.geometry.space.camera.Projections3D;

public class Perspective3D extends Projections3D {

	double fovy, aspect, zNear, zFar;

	public Perspective3D(double _fovy, double _aspect, double _zNear, double _zFar) {
		super();

		fovy   = _fovy;
		aspect = _aspect;
		zNear  = _zNear;
		zFar   = _zFar;

		update();
	}

	public void 			setFovY(double _fovy) {
		fovy = _fovy;
		update();
	}
	public double 			getFovY() {
		return fovy;
	}

	public void 			setAspect(double _aspect) {
		aspect = _aspect;
	}
	public double 			getAspect() {
		return aspect;
	}

	public void 			setNear(double _zNear) {
		zNear  = _zNear;
		update();
	}
	public double 			getNear() {
		return zNear;
	}

	public void 			setFar(double _zFar) {
		zFar   = _zFar;
		update();
	}
	public double 			getFar() {
		return zFar;
	}

	private void 			update() {
		double f      =           1.0f        / (float) Math.tan(fovy * Math.PI / 360.0f);
        double A33    =     (zNear + zFar)    / (zNear - zFar);
        double A34    = (2.0f * zNear * zFar) / (zNear - zFar);

        m.m00 = f / aspect;		m.m10 = 0.0f;	m.m20 = 0.0f;		m.m30 = 0.0f;                                    
        m.m01 = 0.0f;			m.m11 = f;		m.m21 = 0.0f;		m.m31 = 0.0f;                                    
        m.m02 = 0.0f;			m.m12 = 0.0f;	m.m22 = A33;		m.m32 = A34;
        m.m03 = 0.0f;			m.m13 = 0.0f;	m.m23 = -1.0f;		m.m33 = 0.0f;  
		
	}

}
