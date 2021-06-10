package fr.java.maths.geometry.plane.shapes;

import java.io.Serializable;
import java.text.NumberFormat;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Shape2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.types.SimpleBoundingBox2D;

public class SimpleEllipse2D extends SimpleBoundingBox2D implements BoundingBox.TwoDims.Editable, Cloneable, Comparable<Object>, Serializable, Shape2D {
	private static final long serialVersionUID = -58203896228436150L;

	Point2D center;
	double  xRadius, yRadius;
	
	public SimpleEllipse2D(Point2D _c, double _rx, double _ry) {
		super();
		center = _c;
		xRadius = _rx;
		yRadius = _ry;
	}

	@Override
	public Point2D getCenter() {
		return center;
	}

	@Override
	public double getX() {
		return center.getX();
	}
	@Override
	public double getY() {
		return center.getY();
	}
	@Override
	public double getWidth() {
		return 2 * xRadius;
	}
	@Override
	public double getHeight() {
		return 2 * yRadius;
	}

	@Override
	public boolean contains(Coordinate.TwoDims _pt) {
		double X = (_pt.getFirst()  - center.getX()) * (_pt.getFirst()  - center.getX()) / (xRadius * xRadius);
		double Y = (_pt.getSecond() - center.getY()) * (_pt.getSecond() - center.getY()) / (yRadius * yRadius);

		return X + Y <= 1.0;
	}

	@Override
	public double getArea() {
		return Math.PI * xRadius * yRadius;
	}
	@Override
	public double getIntersectionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
//		return 0;
	}
	@Override
	public double getUnionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
//		return 0;
	}

	@Override
	public int compareTo(Object o) {
		throw new NotYetImplementedException();
	}

	@Override
	public BoundingBox.TwoDims.Editable clone() {
		throw new NotYetImplementedException();
	}

	@Override
	public String toString(NumberFormat _nf) {
		return toString();
	}

}
