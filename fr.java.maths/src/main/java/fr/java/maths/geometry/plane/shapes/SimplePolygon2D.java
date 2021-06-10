package fr.java.maths.geometry.plane.shapes;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Shape2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.java.maths.geometry.plane.types.SimpleBoundingBox2D;
import fr.java.maths.geometry.utils.PlaneTests;

public class SimplePolygon2D extends SimpleBoundingBox2D implements BoundingBox.TwoDims.Editable, Cloneable, Comparable<Object>, Serializable, Shape2D, Polygon2D {
	private static final long serialVersionUID = -8761855227580448615L;

	double minX, minY, maxX, maxY;
	List<Point2D> tops;

	public SimplePolygon2D(Point2D... _pts) {
		super();
		
		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts) {
			tops.add(pt);
			minX = minX < pt.getX() ? minX : pt.getX();
			minY = minY < pt.getY() ? minY : pt.getY();
			maxX = maxX > pt.getX() ? maxX : pt.getX();
			maxY = maxY > pt.getY() ? maxY : pt.getY();
		}
	}
	public SimplePolygon2D(List<Point2D> _pts) {
		super();

		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts) {
			tops.add(pt);
			minX = minX < pt.getX() ? minX : pt.getX();
			minY = minY < pt.getY() ? minY : pt.getY();
			maxX = maxX > pt.getX() ? maxX : pt.getX();
			maxY = maxY > pt.getY() ? maxY : pt.getY();
		}
	}
	public SimplePolygon2D(Set<Point2D> _pts) {
		super();

		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts) {
			tops.add(pt);
			minX = minX < pt.getX() ? minX : pt.getX();
			minY = minY < pt.getY() ? minY : pt.getY();
			maxX = maxX > pt.getX() ? maxX : pt.getX();
			maxY = maxY > pt.getY() ? maxY : pt.getY();
		}
	}
	
	@Override
	public Set<Point2D> 	getTops() {
		return tops.stream().collect(Collectors.toSet());
	}

	@Override
	public Point2D 			getPosition() {
		return Points.of(minX, minY);
	}
	public Point2D 			getCenter() {
		return Points.of((maxX - minX)/2., (maxY - minY)/2.);
	}

	@Override
	public boolean 			isEmpty() {
		return tops.isEmpty() || minX == maxX || minY == maxY;
	}

	@Override
	public double 			getLeft() {
		return minX;
	}
	@Override
	public double 			getRight() {
		return maxX;
	}
	@Override
	public double 			getTop() {
		return maxY;
	}
	@Override
	public double 			getBottom() {
		return minY;
	}

	@Override
	public Point2D 			getBottomLeft() {
		return Points.of(minX, minY);
	}

	@Override
	public Point2D 			getTopLeft() {
		return Points.of(minX, maxY);
	}

	@Override
	public Point2D 			getTopRight() {
		return Points.of(maxX, maxY);
	}

	@Override
	public Point2D 			getBottomRight() {
		return Points.of(maxX,  minY);
	}

	@Override
	public double 			getX() {
		return minX;
	}
	@Override
	public double 			getMinX() {
		return minX;
	}
	@Override
	public double 			getMaxX() {
		return maxX;
	}
	@Override
	public double 			getCenterX() {
		return (maxX - minX)/2.;
	}
	@Override
	public double 			getWidth() {
		return maxX - minX;
	}

	@Override
	public double 			getY() {
		return minY;
	}
	@Override
	public double 			getMinY() {
		return minY;
	}
	@Override
	public double 			getMaxY() {
		return maxY;
	}
	@Override
	public double 			getCenterY() {
		return (maxY - minY)/2.;
	}
	@Override
	public double 			getHeight() {
		return maxY - minY;
	}

	@Override
	public boolean 			contains(double _x, double _y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean 			contains(double _x, double _y, double _w, double _h) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean 			contains(Coordinate.TwoDims _pt) {
		if( _pt.getFirst() < minX || _pt.getFirst() > maxX || _pt.getSecond() < minY || _pt.getSecond() > maxY)
	        return false;
		
		int nbImpacts = 0;
		for(int i = 0, j = tops.size() - 1; i < tops.size(); j = i++)
			nbImpacts += PlaneTests.intersect(_pt, tops.get(i), tops.get(j)) ? 1 : 0;
		return nbImpacts % 2 == 0;
	}
	@Override
	public boolean 			contains(BoundingBox.TwoDims _bb) {
		return false;
	}

	@Override
	public boolean 			intersects(BoundingBox.TwoDims _bb) {
		return false;
	}
	@Override
	public boolean 			intersects(double _x, double _y, double _w, double _h) {
		return false;
	}

	@Override
	public double 			getArea() {
		double area = 0.0;
		for(int i = 0; i < tops.size() - 1; ++i)
			area += tops.get(i).getX() * tops.get(i+1).getY() - tops.get(i).getY() * tops.get(i+1).getX();

		return Math.abs(area) / 2;
	}
	@Override
	public double 			getIntersectionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
	}
	@Override
	public double 			getUnionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public BoundingBox.TwoDims.Editable clone() {
		return new SimpleRectangle2D(tops);
	}

	@Override
	public String toString(NumberFormat _nf) {
		return toString();
	}

}
