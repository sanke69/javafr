package fr.java.sdk.math.geometry.space.shapes.quadrics.surfaces;

import fr.java.sdk.math.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.sdk.math.geometry.space.shapes.quadrics.QuadricSurface3D;

// cf. https://www.mathcurve.com/surfaces/hyperboloid/hyperboloid1.shtml
// cf. https://www.mathcurve.com/surfaces/hyperboloid/hyperboloid2.shtml
public class Hyperboloid3D extends Quadric3DBase implements QuadricSurface3D {
	private static final long serialVersionUID = 1404554009084145544L;

	public static Hyperboloid3D H1_alongX(double _a, double _b, double _c) {
		return new Hyperboloid3D(- _a, _b, _c, 1);
	}
	public static Hyperboloid3D H1_alongY(double _a, double _b, double _c) {
		return new Hyperboloid3D(_a, - _b, _c, 1);
	}
	public static Hyperboloid3D H1_alongZ(double _a, double _b, double _c) {
		return new Hyperboloid3D(_a, _b, - _c, 1);
	}

	public Hyperboloid3D() {
		super(-1, 1, 1, 0, 0, 0, 0, 0, 0, -1);
	}
	private Hyperboloid3D(double _x2, double _y2, double _z2, double _cst) {
		super(_x2, _y2, _z2, 0, 0, 0, 0, 0, 0, _cst);
	}

	@Override
	public QuadricSurfaceParametrization getParametrization() {
		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[HYPERBOLOID: ]");
		
		return sb.toString();
	}

}
