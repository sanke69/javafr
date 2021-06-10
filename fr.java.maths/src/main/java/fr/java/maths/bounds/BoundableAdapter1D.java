package fr.java.maths.bounds;

import java.text.NumberFormat;

import fr.java.patterns.geometry.Boundable;

public abstract class BoundableAdapter1D extends BoundableAdapter implements Boundable.OneDim {
	private static final long serialVersionUID = 1L;

	@Override public String toString() { return "(" + getMinX() + "), [" + getWidth() + "]"; }
	@Override public String toString(NumberFormat _nf) { return "(" + _nf.format(getMinX()) + "), [" + _nf.format(getWidth()) + "]"; }

}