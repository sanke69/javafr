package fr.java.math.geometry;

import fr.java.math.geometry.plane.Point2D;

public interface Geometry {

	public enum Side {
		LEFT, RIGHT, TOP, BOTTOM, FRONT, BACK;
	}

	public static enum Intersection {
		INSIDE, OUTSIDE, INTERSECT;
	}

	public enum Distance {
		EUCLIDIAN;
	}

	public static double getDistance(Point2D _a, Point2D _b) {
		return Math.sqrt( (_b.getX() - _a.getX()) * (_b.getX() - _a.getX()) + (_b.getY() - _a.getY()) * (_b.getY() - _a.getY()));
	}

	
}
