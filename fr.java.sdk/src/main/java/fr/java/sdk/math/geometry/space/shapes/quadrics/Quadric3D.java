package fr.java.sdk.math.geometry.space.shapes.quadrics;

import java.io.Serializable;

import fr.java.math.geometry.space.Point3D;
import fr.java.sdk.math.Points;

// cf. http://www.bmsc.washington.edu/people/merritt/graphics/quadrics.html
public interface Quadric3D extends Serializable {

	public interface QuadricEquation {
		public default double solve(Point3D _pt) { return solve(Points.of(_pt.getX(), _pt.getY(), _pt.getZ())); }
		public double solve(double _x, double _y, double _z);
	}

	public static enum COEF {
		X2,	Y2,	Z2,
		XY,	XZ,	YZ,
		X,	Y,	Z,
		UN,
	};

	public double 			getCoef(COEF _coef);

	public default QuadricEquation getEquation() {
		return (_x, _y, _z) -> {
			return getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z 
					+ getCoef(COEF.XY) * _x*_y + getCoef(COEF.YZ) * _y*_z + getCoef(COEF.XZ) * _x*_z 
					+ getCoef(COEF.X) * _x + getCoef(COEF.Y) * _y + getCoef(COEF.Z) * _z 
					+ getCoef(COEF.UN);
		};
	}
}
