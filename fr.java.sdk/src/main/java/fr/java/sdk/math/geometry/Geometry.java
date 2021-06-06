package fr.java.sdk.math.geometry;

import java.util.Set;
import java.util.function.Supplier;

import fr.java.math.geometry.plane.Frame2D;
import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Frame3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Ray3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.geometry.plane.types.SimpleFrame2D;
import fr.java.sdk.math.geometry.space.shapes.Cube3D;
import fr.java.sdk.math.geometry.space.types.SimpleFrame3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

public class Geometry {

	@Deprecated
	public static class Plane {

		@Deprecated
		public static final class Frame {

			@Deprecated
			public static final Frame2D newInstance() {
				return new SimpleFrame2D();
			}
		
		}

	}

	public static class Space {
		public static final Point3D  WorldOrigin = Points.of(0, 0, 0);
		public static final Vector3D WorldXAxis  = Vectors.of(1, 0, 0);
		public static final Vector3D WorldYAxis  = Vectors.of(0, 1, 0);
		public static final Vector3D WorldZAxis  = Vectors.of(0, 0, 1);
		public static final Frame3D  worldFrame  = new SimpleFrame3D(WorldOrigin, WorldXAxis, WorldYAxis, WorldZAxis);

		public static final class Frame {

			public static final Frame3D newInstance() {
				return new SimpleFrame3D();
			}

		}

		@Deprecated
		public static final class BoundingBox {

		    public static BoundingBox3D fromBounds(double _minX, double _minY, double _minZ, double _maxX, double _maxY, double _maxZ) {
		    	if(_maxX < _minX) {
		    		double tmp = _minX; _minX = _maxX; _maxX = tmp;
		    	}
		    	if(_maxY < _minY) {
		    		double tmp = _minY; _minY = _maxY; _maxY = tmp;
		    	}
		    	if(_maxZ < _minZ) {
		    		double tmp = _minZ; _minZ = _maxZ; _maxZ = tmp;
		    	}
		    	return new Cube3D(_minX, _minY, _minZ, _maxX - _minX, _maxY - _minY, _maxZ - _minZ);
		    }

			public static BoundingBox3D.Editable empty() {
				return new Cube3D(0, 0, 0, 0, 0, 0);
			}
			public static BoundingBox3D.Editable unit() {
				return new Cube3D(0, 0, 0, 1, 1, 1);
			}
			
			public static BoundingBox3D of(Point3D _frontBottomLeft, Dimension3D _dimensions) {
				return new Cube3D(_frontBottomLeft, _dimensions);
			}
			public static BoundingBox3D of(Set<Point3D> _points) {
				return new Cube3D(_points);
			}

		}

		public static final class Ray {

			public static final Ray3D of(Point3D _origin, Vector3D _direction) {
				return new SimpleRay3D(_origin, _direction);
			}

		}

	}

}
