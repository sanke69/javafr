package fr.java.maths.geometry.space.shapes.quadrics;

import java.io.Serializable;

import fr.java.math.geometry.space.Point3D;
import fr.java.maths.geometry.space.types.SimpleRay3D;

// cf. http://www.bmsc.washington.edu/people/merritt/graphics/quadrics.html
public interface QuadricSurface3D extends Quadric3D, Serializable {

	public interface QuadricSurfaceParametrization {
		public Point3D[] get(SimpleRay3D _ray);
	}

	public default double 					solve(Point3D _pt) 						{ return getEquation().solve(_pt.getX(), _pt.getY(), _pt.getZ()); }

	public QuadricSurfaceParametrization 	getParametrization();

}
