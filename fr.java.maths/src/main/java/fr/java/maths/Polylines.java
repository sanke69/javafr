package fr.java.maths;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.shapes.SimplePolyline2D;

public class Polylines {

	public static Polyline2D of(double[] _x, double[] _y) {
		int length = _x.length < _y.length ? _x.length : _y.length;
		return new SimplePolyline2D( IntStream.range(0,  length).mapToObj(i -> Points.of(_x[i], _y[i])).collect(Collectors.toList()) );
	}
	public static Polyline2D of(Set<Coordinate.TwoDims> _tops) {
		return new SimplePolyline2D( _tops.stream().map(Points::of).collect(Collectors.toSet()) );
	}

}