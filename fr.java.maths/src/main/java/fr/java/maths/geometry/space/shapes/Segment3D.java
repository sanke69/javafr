package fr.java.maths.geometry.space.shapes;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.Points;
import fr.java.maths.algebra.Vectors;

public class Segment3D {

	public static Segment3D of(Point3D _start, Point3D _end) {
		return new Segment3D(_start, _end);
	}
	
	Point3D  start, end;
	
	public Segment3D() {
		super();
		start = Points.zero3();
		end   = Points.zero3();
	}
	public Segment3D(Point3D _start, Point3D _end) {
		super();
		start = _start;
		end   = _end;
	}

	public Point3D A()   { return getStart(); }
	public Point3D B()   { return getEnd(); }
	public Vector3D AB() { return getDirection(); }

	public void setStart(Point3D _pt) {
		start =_pt;
	}
	public void setEnd(Point3D _pt) {
		end = _pt;
	}

	public Point3D  getStart() {
		return start;
	}
	public Point3D  getEnd() {
		return end;
	}
	public Vector3D getDirection() {
		return Vectors.delta(getStart(), getEnd());
	}

	public String toString() {
		return "[SEGMENT: A= " + start + ", B= " + end + "]";
	}

}
