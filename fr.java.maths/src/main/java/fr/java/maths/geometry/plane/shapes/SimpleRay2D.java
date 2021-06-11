package fr.java.maths.geometry.plane.shapes;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Ray2D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.types.Points;

public class SimpleRay2D implements Ray2D.Editable {

	Point2D  origin;
	Vector2D direction;
	
	public SimpleRay2D() {
		super();
		origin    = Points.zero2();
		direction = Vectors.of(1, 0);
	}
	public SimpleRay2D(Point2D _origin, Vector2D _direction) {
		super();
		origin    = _origin;
		direction = _direction;
	}

	@Override
	public void 		set(Ray2D _ray) {
		origin    = _ray.getOrigin();
		direction = _ray.getDirection();
	}
	@Override
	public Point2D  	get(double _t) {
		return origin.plus(direction.times(_t));
	}

	@Override
	public void 		setOrigin(Point2D _pt) {
		origin =_pt;
	}
	@Override
	public Point2D  	getOrigin() {
		return origin;
	}

	@Override
	public void 		setDirection(Vector2D _dir) {
		direction = _dir;
	}
	@Override
	public Vector2D 	getDirection() {
		return direction;
	}

	@Override
	public String 		toString() {
		return "[RAY: o= " + origin + ", d= " + direction + "]";
	}
	
}
