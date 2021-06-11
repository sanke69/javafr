package fr.java.maths.geometry.space.types;

import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Ray3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.types.Points;

public class SimpleRay3D implements Ray3D.Editable {

	Point3D  origin;
	Vector3D direction;
	
	public SimpleRay3D() {
		super();
		origin    = Points.zero3();
		direction = Vectors.of(1, 0, 0);
	}
	public SimpleRay3D(Point3D _origin, Vector3D _direction) {
		super();
		origin    = _origin;
		direction = _direction;
	}

	@Override
	public void 	set(Ray3D _ray) {
		origin    = _ray.getOrigin();
		direction = _ray.getDirection();
	}
	@Override
	public Point3D  get(double _t) {
		return origin.plus(direction.times(_t));
	}

	@Override
	public void 	setOrigin(Point3D _pt) {
		origin =_pt;
	}
	@Override
	public Point3D  getOrigin() {
		return origin;
	}
	@Override
	public void 	setDirection(Vector3D _dir) {
		direction = _dir;
	}
	@Override
	public Vector3D getDirection() {
		return direction;
	}

	@Override
	public String 	toString() {
		return "[RAY: o= " + origin + ", d= " + direction + "]";
	}

}
