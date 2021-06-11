package fr.java.maths.geometry;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Frame3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Ray3D;
import fr.java.math.geometry.space.shapes.Cube3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.space.shapes.SimpleCube3D;
import fr.java.maths.geometry.space.shapes.quadrics.shapes.QuadricEllipsoid3D;
import fr.java.maths.geometry.space.types.SimpleFrame3D;
import fr.java.maths.geometry.space.types.SimpleRay3D;
import fr.java.maths.geometry.types.Dimensions;
import fr.java.maths.geometry.types.Points;

public class Space {
	public static final Point3D  WorldOrigin = Points.of(0, 0, 0);
	public static final Vector3D WorldXAxis  = Vectors.of(1, 0, 0);
	public static final Vector3D WorldYAxis  = Vectors.of(0, 1, 0);
	public static final Vector3D WorldZAxis  = Vectors.of(0, 0, 1);
	public static final Frame3D  WorldFrame  = new SimpleFrame3D(WorldOrigin, WorldXAxis, WorldYAxis, WorldZAxis);


	public static final Frame3D 			newFrame() {
		return new SimpleFrame3D();
	}

	public static final Ray3D 				newRay(Point3D _origin, Vector3D _direction) {
		return new SimpleRay3D(_origin, _direction);
	}


	public static final Cube3D 				newCube() {
		return new SimpleCube3D(0, 0, 0, 1, 1, 1);
	}
	public static final Cube3D 				newCube(double _x, double _y, double _z, double _w, double _h, double _d) {
		return new SimpleCube3D(_x, _y, _z, _w, _h, _d);
	}
	public static final Cube3D 				newCube(Point3D _position, Dimension3D _dimensions) {
		return new SimpleCube3D(_position.getX(), _position.getY(), _position.getZ(), _dimensions.getWidth(), _dimensions.getHeight(), _dimensions.getDepth());
	}

	public static final QuadricEllipsoid3D 	newEllipsoid() {
		return QuadricEllipsoid3D.createFrom(Points.zero3(), Dimensions.unit3());
	}
	public static final QuadricEllipsoid3D 	newEllipsoid(Point3D _center, Dimension3D _dimensions) {
		return QuadricEllipsoid3D.createFrom(_center, _dimensions);
	}

}
