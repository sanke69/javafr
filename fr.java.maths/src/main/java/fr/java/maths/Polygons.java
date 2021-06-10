package fr.java.maths;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.geometry.plane.shapes.SimplePolygon2D;

public class Polygons {

	public static Polygon2D of(double[] _x, double[] _y) {
		int length = _x.length < _y.length ? _x.length : _y.length;
		
		return new SimplePolygon2D(
									IntStream.range(0,  length).mapToObj(i -> Points.of(_x[i], _y[i])).collect(Collectors.toList())
								  );
	}
	public static Polygon2D of(Set<Coordinate.TwoDims> _tops) {
		return new SimplePolygon2D( _tops.stream().map(Points::of).collect(Collectors.toSet()) );
	}

}
