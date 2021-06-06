package fr.java.patterns.geometry;

import fr.java.math.geometry.Dimension;

public interface Dimensionable {

	public interface TwoDims extends Dimensionable {

		public double getWidth();
		public double getHeight();

	}

	public interface ThreeDims extends Dimensionable.TwoDims {

		public double getDepth();

	}

	public static Dimensionable.TwoDims of(final Dimension.TwoDims _bbox) {
		return new Dimensionable.TwoDims() {

			@Override public double getWidth()  { return _bbox.getWidth(); }
			@Override public double getHeight() { return _bbox.getHeight(); }

		};
	}

	public static Dimensionable.ThreeDims of(final Dimension.ThreeDims _bbox) {
		return new Dimensionable.ThreeDims() {

			@Override public double getWidth()  { return _bbox.getWidth(); }
			@Override public double getHeight() { return _bbox.getHeight(); }
			@Override public double getDepth()  { return _bbox.getDepth(); }

		};
	}

}
