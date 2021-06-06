package fr.java.sdk.math.geometry.space.shapes.quadrics;

import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

public interface QuadricShape3D extends Quadric3D {

	public interface QuadricShapeParametrization {
		public Point3D get(double _rho, double _theta);
	}

	public QuadricShapeParametrization 	getParametrization();
	public default Point3D 				getSurfacePoint(double _u, double _v) 	{ return getParametrization().get(_u, _v); }

	public boolean 						intersects(SimpleRay3D _ray);

	public default boolean 				contains(Point3D _pt) {
		return getEquation().solve(_pt.getX(), _pt.getY(), _pt.getZ()) <= 0;
	}
	public boolean 						contains(BoundingBox3D _bbox);

}
