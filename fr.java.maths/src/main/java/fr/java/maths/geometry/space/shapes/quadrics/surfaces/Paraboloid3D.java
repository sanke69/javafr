package fr.java.maths.geometry.space.shapes.quadrics.surfaces;

import fr.java.maths.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.maths.geometry.space.shapes.quadrics.QuadricSurface3D;

public class Paraboloid3D extends Quadric3DBase implements QuadricSurface3D {
	private static final long serialVersionUID = 1404554045684145544L;

	public static Paraboloid3D alongX(double _y2, double _z2, double _x, double _cst) {
		return new Paraboloid3D(0, _y2, _z2, - _x, 0, 0, _cst);
	}
	public static Paraboloid3D alongY(double _x2, double _z2, double _y, double _cst) {
		return new Paraboloid3D(_x2, 0, _z2, 0, - _y, 0, _cst);
	}
	public static Paraboloid3D alongZ(double _x2, double _y2, double _z, double _cst) {
		return new Paraboloid3D(_x2, _y2, 0, 0, 0, - _z, _cst);
	}

	public Paraboloid3D() {
		super(0, 1, 1, 0, 0, 0, -1, 0, 0, 0);
	}
	private Paraboloid3D(double _x2, double _y2, double _z2, double _x, double _y, double _z, double _cst) {
		super(_x2, _y2, _z2, 0, 0, 0, _x, _y, _z, _cst);
	}


	public QuadricEquation 					getEquation() {
		return (_x, _y, _z) -> { 
			return getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z 
					+ getCoef(COEF.X) * _x + getCoef(COEF.Y) * _y + getCoef(COEF.Z) * _z 
					+ getCoef(COEF.UN);
		};
	}
	public QuadricSurfaceParametrization 	getParametrization() {
		return (_ray) -> {
			return null;
		};
	}

	public double							getSurfacePointX(double _y, double _z) {
		double a = getCoef(COEF.X),
			   c = getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z
				 + getCoef(COEF.Y)  * _y    + getCoef(COEF.Z)  * _z
				 + getCoef(COEF.UN);
		return -c / a;
	}
	public double							getSurfacePointY(double _x, double _z) {
		double a = getCoef(COEF.Y),
			   c = getCoef(COEF.X2) * _x*_x + getCoef(COEF.Z2) * _z*_z
					 + getCoef(COEF.X)  * _x    + getCoef(COEF.Z)  * _z
					 + getCoef(COEF.UN);
		return -c / a;
	}
	public double							getSurfacePointZ(double _x, double _y) {
		double a = getCoef(COEF.Z),
			   c = getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y
					 + getCoef(COEF.X)  * _x    + getCoef(COEF.Y)  * _y
					 + getCoef(COEF.UN);
		return -c / a;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
//		sb.append("[PARABOLOID: c= " + getCenter() + ", d= " + getDimension() + ", o={" + getAxis().get() + "}]");
//		sb.append("[PLANE: a= " + getCoef(COEF.X) + ", b= " + getCoef(COEF.Y) + ", c=" + getCoef(COEF.Z) + ", d=" + getCoef(COEF.UN) + "]");
		return sb.toString();
	}

}
