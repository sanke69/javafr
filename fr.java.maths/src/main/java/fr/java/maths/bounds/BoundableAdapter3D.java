package fr.java.maths.bounds;

import java.text.NumberFormat;

import fr.java.patterns.geometry.Boundable;

public abstract class BoundableAdapter3D extends BoundableAdapter2D implements Boundable.ThreeDims {
	private static final long serialVersionUID = 1L;

	@Override public String toString() { return "(" + getMinX() + ", " + getMinY() + ", " + getMinZ() + "), [" + getWidth() + "x" + getHeight() + "x" + getDepth() + "]"; }
	@Override public String toString(NumberFormat _nf) { return "(" + _nf.format(getMinX()) + ", " + _nf.format(getMinY()) + ", " + _nf.format(getMinZ()) + "), [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "x" + _nf.format(getDepth()) + "]"; }

}
