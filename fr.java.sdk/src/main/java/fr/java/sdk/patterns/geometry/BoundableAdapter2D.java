package fr.java.sdk.patterns.geometry;

import java.text.NumberFormat;

import fr.java.patterns.geometry.Boundable;

public abstract class BoundableAdapter2D extends BoundableAdapter1D implements Boundable.TwoDims {
	private static final long serialVersionUID = 1L;

	@Override public String toString() { return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]"; }
	@Override public String toString(NumberFormat _nf) { return "(" + _nf.format(getMinX()) + ", " + _nf.format(getMinY()) + "), [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "]"; }

}
