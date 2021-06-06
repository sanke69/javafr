package fr.java.sdk.math.geometry.space.shapes.quadrics.surfaces;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.sdk.math.geometry.space.shapes.quadrics.QuadricSurface3D;

//General Equation: F(x, y, z) = Ax2 + By2 + Cz2 + Dxy+ Exz + Fyz + Gx + Hy + Iz + J = 0
public class Plane3D extends Quadric3DBase implements QuadricSurface3D {
	private static final long serialVersionUID = 1404554045684145544L;

	public static Plane3D of(double _a, double _b, double _c, double _d) {
		return new Plane3D(_a, _b, _c, _d);
	}
	public static Plane3D of(Point3D _point, Vector3D _normal) {
		double d = - ( _normal.getX() * _point.getX() + _normal.getY() * _point.getY() + _normal.getZ() * _point.getZ() );

		return new Plane3D(_normal.getX(), _normal.getY(), _normal.getZ(), d);
	}
	public static Plane3D of(Vector3D _normal, Point3D _point) {
		double d = - ( _normal.getX() * _point.getX() + _normal.getY() * _point.getY() + _normal.getZ() * _point.getZ() );

		return new Plane3D(_normal.getX(), _normal.getY(), _normal.getZ(), d);
	}
	
	public Plane3D() {
		super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 1, 0);
	}
	public Plane3D(double _a, double _b, double _c, double _d) {
		super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, _a, _b, _c, _d);
	}

	public QuadricEquation 					getEquation() {
		return (_x, _y, _z) -> {
			return getCoef(COEF.X) * _x + getCoef(COEF.Y) * _y + getCoef(COEF.Z) * _z + getCoef(COEF.UN);
		};
	}
	public QuadricSurfaceParametrization 	getParametrization() {
		return (_ray) -> {
			double q = _ray.getDirection().dotProduct(getNormal());
			if(q == 0)
				return null;
			
			double r = Vectors.delta(_ray.getOrigin(), getZero()).dotProduct(getNormal());
			double t = r / q;

			return new Point3D[] { _ray.getOrigin().plus(_ray.getDirection().times(t)) };
		};
	}

	public double A() {
		return getCoef(COEF.X);
	}
	public double B() {
		return getCoef(COEF.Y);
	}
	public double C() {
		return getCoef(COEF.Z);
	}
	public double D() {
		return getCoef(COEF.UN);
	}
	
	public Vector3D 						getNormal() {
		return Vectors.of(getCoef(COEF.X), getCoef(COEF.Y), getCoef(COEF.Z));
	}
	public Point3D 							getZero() {
		if(getCoef(COEF.X) != 0)
			return Points.of(- getCoef(COEF.UN) / getCoef(COEF.X), 0, 0);
		else if(getCoef(COEF.Y) != 0)
			return Points.of(0, - getCoef(COEF.UN) / getCoef(COEF.Y), 0);
		else if(getCoef(COEF.Z) != 0)
			return Points.of(0, 0, - getCoef(COEF.UN) / getCoef(COEF.Z));
		else throw new IllegalArgumentException();
	}

	public double 							getSurfacePointX(double _y, double _z) { return - (getCoef(COEF.UN) + _y * getCoef(COEF.Y) + _z * getCoef(COEF.Z)) / getCoef(COEF.X); }
	public double							getSurfacePointY(double _x, double _z) { return - (getCoef(COEF.UN) + _x * getCoef(COEF.X) + _z * getCoef(COEF.Z)) / getCoef(COEF.Y); }
	public double							getSurfacePointZ(double _x, double _y) { return - (getCoef(COEF.UN) + _x * getCoef(COEF.X) + _y * getCoef(COEF.Y)) / getCoef(COEF.Z); }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[PLANE: a= " + getCoef(COEF.X) + ", b= " + getCoef(COEF.Y) + ", c=" + getCoef(COEF.Z) + ", d=" + getCoef(COEF.UN) + "]");
		return sb.toString();
	}

}
