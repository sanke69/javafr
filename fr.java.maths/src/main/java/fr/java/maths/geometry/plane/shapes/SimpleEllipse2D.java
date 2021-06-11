package fr.java.maths.geometry.plane.shapes;

import java.text.NumberFormat;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.types.SimpleBoundingBox2D;
import fr.java.maths.geometry.types.Dimensions;
import fr.java.maths.geometry.types.Points;

public class SimpleEllipse2D extends SimpleBoundingBox2D implements Ellipse2D {
	private static final long serialVersionUID = -58203896228436150L;

	Point2D 	center;
	Dimension2D dimension;

	public SimpleEllipse2D(double _x, double _y, double _rx, double _ry) {
		super();
		center    = Points.of(_x, _y);
		dimension = Dimensions.of(2*_rx, 2*_ry);
	}
	public SimpleEllipse2D(Point2D _c, double _rx, double _ry) {
		super();
		center    = _c;
		dimension = Dimensions.of(2*_rx, 2*_ry);
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
		return dimension.getWidth() / 2d;
	}
	@Override
	public double getHeight() {
		return dimension.getHeight() / 2d;
	}

	@Override
	public boolean contains(Coordinate.TwoDims _pt) {
		double xRadius = dimension.getWidth() / 2d;
		double yRadius = dimension.getHeight() / 2d;
		double X = (_pt.getFirst()  - center.getX()) * (_pt.getFirst()  - center.getX()) / (xRadius * xRadius);
		double Y = (_pt.getSecond() - center.getY()) * (_pt.getSecond() - center.getY()) / (yRadius * yRadius);

		return X + Y <= 1.0;
	}

	@Override
	public double getArea() {
		return Math.PI * dimension.getWidth() * dimension.getHeight() / 4d;
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
