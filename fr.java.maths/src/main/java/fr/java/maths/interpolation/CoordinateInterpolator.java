package fr.java.maths.interpolation;

import fr.java.math.topology.Coordinate;

public interface CoordinateInterpolator {

	public interface TwoDims extends CoordinateInterpolator {

		public Coordinate.TwoDims 		evaluate(double _t);
		public Coordinate.TwoDims[] 	interpolate(int _nbPoints);

	}

	public interface ThreeDims extends CoordinateInterpolator {

		public Coordinate.ThreeDims 	evaluate(double _t);
		public Coordinate.ThreeDims[] 	interpolate(int _nbPoints);

	}

	public interface MultiDims extends CoordinateInterpolator {

		public Coordinate.MultiDims 	evaluate(double _t);
		public Coordinate.MultiDims[] 	interpolate(int _nbPoints);

	}

	public Coordinate 					evaluate(double _t);
	public Coordinate[] 				interpolate(int _nbPoints);

}
