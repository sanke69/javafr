package fr.java.sdk.math.geometry.plane.types;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Points;

public class SimpleBoundingBox2D implements BoundingBox.TwoDims.Editable {
	private static final long serialVersionUID = -404792374302736610L;
	
	double x, y, width, height;

	@Override public double 	getX()             	{ return 0; }
	@Override public double 	getY()             	{ return 0; }
	@Override public double 	getWidth() 			{ return 0; }
	@Override public double 	getHeight() 		{ return 0; }

//	@Override 
	public Point2D 				getPosition() 		{ return Points.of(getMinX(), 				getMinY()); }
//	@Override 
	public Point2D 				getCenter()   		{ return Points.of(getCenterX(), 			getCenterY()); }

//	@Override 
	public Point2D 				getTopLeft()      	{ return Points.of(getMinX(), 				getMinY() + getHeight()); }
//	@Override 
	public Point2D 				getTopRight()     	{ return Points.of(getMinX() + getWidth(), 	getMinY() + getHeight()); }
//	@Override 
	public Point2D 				getBottomLeft()   	{ return Points.of(getMinX(), 				getMinY()); }
//	@Override 
	public Point2D 				getBottomRight()  	{ return Points.of(getMinX() + getWidth(), 	getMinY()); }

//	@Override
	public Set<Point2D> 		getTops() {
		Set<Point2D> tops = new HashSet<Point2D>();
		tops.add(Points.of(getMinX(), 				getMinY()));
		tops.add(Points.of(getMinX() + getWidth(), 	getMinY()));
		tops.add(Points.of(getMinX(), 				getMinY() + getHeight()));
		tops.add(Points.of(getMinX() + getWidth(), 	getMinY() + getHeight()));
		return tops;
	}

	@Override
	public void 					set(double _x, double _y, double _width, double _height) {
		x      = _x;
		y      = _y;
		width  = _width;
		height = _height;
	}

	@Override
	public void 					setX(double _x) {
		x = _x;
	}
	@Override
	public void 					setY(double _y) {
		y = _y;
	}

	@Override
	public void 					setMinX(double _x) {
		x = _x;
	}
	@Override
	public void 					setMinY(double _y) {
		y = _y;
	}

	@Override
	public void 					setMaxX(double _x) {
		width = _x - x;
	}
	@Override
	public void 					setMaxY(double _y) {
		height = _y - y;
	}

	@Override
	public void 					setCenterX(double _x) {
		x = _x - width / 2;
	}
	@Override
	public void 					setCenterY(double _y) {
		y = _y - height / 2;
	}

	@Override
	public void 					setWidth(double _width) {
		width = _width;
	}
	@Override
	public void 					setHeight(double _height) {
		height = _height;
	}

	@Override
	public void 					setLeft(double _left) {
		x = _left;
	}
	@Override
	public void 					setRight(double _right) {
		width = _right - x;
	}
	@Override
	public void 					setTop(double _top) {
		y = _top;
	}
	@Override
	public void 					setBottom(double _bottom) {
		height = _bottom - y;
	}

	@Override
	public boolean 					isEmpty() {
		return width == 0 && height == 0;
	}

	@Override
	public boolean 					contains(Coordinate _pt) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 					contains(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}
	@Override
	public boolean 					intersects(BoundingBox _bb) {
		throw new NotYetImplementedException();
	}

	@Override
	public String 					toString(NumberFormat _nf) {
		return "(" + getX() + ", " + getY() + ") [" + getWidth() + "x" + getHeight() + "]";
	}

	@Override
	public BoundingBox.TwoDims.Editable 	clone() {
		throw new NotYetImplementedException();
	}

}
