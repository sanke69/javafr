package fr.java.sdk.math;

import java.util.Set;

import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.topology.Coordinate;

public class Ellipses {

	public static final Ellipse2D of(double _x, double _y, double _width, double _height) {
		return new Ellipse2D() {
			private static final long serialVersionUID = 1L;

			@Override public double  getX() 		{ return _x; }
			@Override public double  getY() 		{ return _y; }

			@Override public double  getWidth() 	{ return _width; }
			@Override public double  getHeight() 	{ return _height; }

		};
	}
	public static final Ellipse2D of(Set<Coordinate.TwoDims> _tops) {
		double min_x =   Double.MAX_VALUE;
		double min_y =   Double.MAX_VALUE;
		double max_x = - Double.MAX_VALUE;
		double max_y = - Double.MAX_VALUE;

		for(Coordinate.TwoDims pt : _tops) {
			if(pt.getFirst() < min_x)
				min_x = pt.getFirst();
			if(pt.getSecond() < min_y)
				min_y = pt.getSecond();
			if(pt.getFirst() > max_x)
				max_x = pt.getFirst();
			if(pt.getSecond() > max_y)
				max_y = pt.getSecond();
		}

		final double _min_x = min_x;
		final double _min_y = min_y;
		final double _max_x = max_x;
		final double _max_y = max_y;

		return new Ellipse2D() {
			private static final long serialVersionUID = 1L;

			@Override public double  getX() 		{ return _min_x; }
			@Override public double  getY() 		{ return _min_y; }

			@Override public double  getWidth() 	{ return _max_x - _min_x; }
			@Override public double  getHeight() 	{ return _max_y - _min_y; }

		};
	}

}
