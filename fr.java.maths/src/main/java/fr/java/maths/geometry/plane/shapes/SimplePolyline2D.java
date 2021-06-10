package fr.java.maths.geometry.plane.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.maths.Segments;
import fr.java.maths.algebra.Vectors;

public class SimplePolyline2D implements Polyline2D {
	private static final long serialVersionUID = -8761855227580448615L;

	List<Point2D> tops;

	public SimplePolyline2D(Point2D... _pts) {
		super();
		
		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts)
			tops.add(pt);
	}
	public SimplePolyline2D(List<Point2D> _pts) {
		super();

		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts)
			tops.add(pt);
	}
	public SimplePolyline2D(Set<Point2D> _pts) {
		super();

		tops = new ArrayList<Point2D>();
		for(Point2D pt : _pts)
			tops.add(pt);
	}

	@Override
	public Point2D 			getPoint(int _index) {
		return tops.get(_index);
	}

	@Override
	public Set<Point2D> 	getPoints() {
		return tops.stream().collect(Collectors.toSet());
	}

	@Override
	public Segment2D 		getSegment(int _index) {
		return Segments.of(tops.get(_index), tops.get(_index+1));
	}

	@Override
	public Set<Segment2D> 	getSegments() {
		return IntStream.range(0,  tops.size()-1).mapToObj(i -> Segments.of(tops.get(i), tops.get(i+1))).collect(Collectors.toSet());
	}

	@Override
	public Vector2D 		getDirection(int _index) {
		Segment2D c = getSegment(_index);
		return Vectors.of(c.getEnd().minus(c.getBegin()));
	}

	@Override
	public Vector2D 		getNormal(int _index) {
		return null;
	}

}
