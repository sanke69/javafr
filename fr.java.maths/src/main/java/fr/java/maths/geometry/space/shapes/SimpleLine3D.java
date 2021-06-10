package fr.java.maths.geometry.space.shapes;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.Points;
import fr.java.maths.algebra.Vectors;

public class SimpleLine3D {

	public static SimpleLine3D of(double _x1, double _y1, double _z1, double _l, double _m, double _n) {
		return new SimpleLine3D(_x1, _y1, _z1, _l, _m, _n);
	}
	public static SimpleLine3D of(Point3D _pt, Vector3D _direction) {
		return new SimpleLine3D(_pt, _direction);
	}
	public static SimpleLine3D of(Point3D _a, Point3D _b) {
		return new SimpleLine3D(_a, _b);
	}
	public static SimpleLine3D of(Segment3D _segment) {
		return new SimpleLine3D(_segment.getStart(), _segment.getEnd());
	}

	Point3D  point;
	Vector3D direction;

	public SimpleLine3D(double _x1, double _y1, double _z1, double _l, double _m, double _n) {
		super();
		point     = Points.of(_x1, _y1, _z1);
		direction = Vectors.of(_l, _m, _n);
	}
	public SimpleLine3D(Point3D _pt, Vector3D _direction) {
		super();
		point     = _pt;
		direction = _direction;
	}
	public SimpleLine3D(Point3D  _a, Point3D _b) {
		super();
		point     = _a;
		direction = Vectors.delta(_b, _a);
	}

	public Point3D getPoint() {
		return point;
	}
	public Point3D getRandomPoint() {
		return point.times(direction.times(-1e6 + Math.random() * Integer.MAX_VALUE));
	}
	public Vector3D getDirection() {
		return direction;
	}

	public boolean contains(Point3D _pt) {
		double t1 = (_pt.getX() - point.getX()) / direction.getX();
		double t2 = (_pt.getY() - point.getY()) / direction.getY();
		double t3 = (_pt.getZ() - point.getZ()) / direction.getZ();
		
		return t1 == t2 && t2 == t3;
	}

}
