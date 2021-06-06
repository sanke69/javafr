package fr.java.utils.graphs;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.math.algebra.NumberVector;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.math.topology.CoordinateSystem;

public class Points {

	static class APoint2D implements Point2D {
		double x, y;

		APoint2D() {
			super();
			x = 0;
			y = 0;
		}
		APoint2D(final double _x, final double _y) {
			super();
			x = _x;
			y = _y;
		}
		APoint2D(final Point2D _pt) {
			super();
			x = _pt.getX();
			y = _pt.getY();
		}

		@Override
		public CoordinateSystem getCoordinateSystem() {
			return CoordinateSystem.Cartesian2D;
		}

		@Override
		public double getX() {
			return x;
		}

		@Override
		public double getY() {
			return y;
		}

		@Override
		public Point2D plus(double _t) {
			return new APoint2D(x + _t, y + _t);
		}

		@Override
		public Point2D plus(double[] _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(double _x, double _y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(TwoDims _c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(fr.java.math.geometry.Dimension.TwoDims _c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(NumberVector _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(Point2D _pt) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(Vector2D _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D plus(Dimension2D _dim) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(double _t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(double[] _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(double _x, double _y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(TwoDims _c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(fr.java.math.geometry.Dimension.TwoDims _c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(NumberVector _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(Point2D _pt) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(Vector2D _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D minus(Dimension2D _dim) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D times(double _t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D times(double _x, double _y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D times(Vector2D _vec) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D divides(double _t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D divides(double _x, double _y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D abs() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D negate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D normalized() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D clone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Vector3D uniform() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Vector3D uniform(double _w) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isEqual(double _x, double _y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEqual(Point2D _pt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDifferent(double _x, double _y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDifferent(Point2D _pt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String toString(NumberFormat _nf) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String toString(DecimalFormat _df) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static Point2D of(double _x, double _y) {
		System.err.println("TBI");
		return null;
	}
}
