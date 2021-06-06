package fr.java.sdk.math.adapters;

import java.text.NumberFormat;

import fr.java.lang.exceptions.IllegalAccessArgument;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;

public class BoundingBoxAdapter1D implements BoundingBox.OneDim {
	private static final long serialVersionUID = 1L;

	double x, width;
	
	public BoundingBoxAdapter1D() {
		super();
		x     = 0;
		width = 0;
	}
	public BoundingBoxAdapter1D(final double _x, final double _width) {
		super();
		x     = _x;
		width = _width;
	}

	@Override
	public double 	getX() 			 { return x; }

	@Override
	public double 	getWidth() 		 { return width; }

	@Override
	public boolean 	isEmpty()        { return getWidth() == 0; }

	@Override
	public double  	getMinX()        { return getX(); }
	@Override
    public double  	getMaxX()        { return getMinX() + getWidth(); }
	@Override
    public double  	getCenterX()     { return (getMinX() + getMaxX()) / 2; }

	@Override
	public double  	getLeft()        { return getMinX(); }
	@Override
    public double  	getRight()       { return getMaxX(); }

	@Override
	public double 	getPerimeter()   { return getWidth(); }

	@Override
	public boolean 	contains(double _x) {
		if(isEmpty())
			return false;
		return _x >= getMinX() && _x <= getMaxX();
	}
	@Override
	public boolean 	contains(double _x, double _w) {
		return contains(_x) && contains(_x + _w);
	}
	@Override
	public boolean 	contains(Coordinate _pt) {
		if(_pt instanceof Coordinate.OneDim)
			return contains(((Coordinate.OneDim) _pt).getFirst());
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	contains(Coordinate.OneDim _pt) {
		return contains(_pt.getFirst());
	}
	@Override
	public boolean 	contains(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.OneDim)
			return contains(((BoundingBox.OneDim) _pt));
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	contains(BoundingBox.OneDim _bb) {
		return _bb.getMinX() > getMinX() && _bb.getMaxX() < getMaxX();
	}

	@Override
	public boolean 	intersects(double _x, double _w) {
		if (isEmpty() || _w <= 0)
			return false;

		return (getMinX() < _x + _w && getMaxX() > _x);
	}
	@Override
	public boolean 	intersects(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.OneDim)
			return contains(((BoundingBox.OneDim) _pt));
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	intersects(BoundingBox.OneDim _bb) {
		if(isEmpty() || _bb == null || _bb.isEmpty())
			return false;

		boolean inside = false, outside = false;

		if(_bb.getMinX() > getMinX() && _bb.getMinX() < getMaxX()) inside = true; else outside = true;
		if(_bb.getMaxX() > getMinX() && _bb.getMaxX() < getMaxX()) inside = true; else outside = true;

		return inside && outside;
		
	}

	@Override
	public String				toString()					{ return "(" + getX() + ") [" + getWidth() + "]"; }
//	@Override
//	public String				toString()					{ return toString(new DecimalFormat("#.#")); }

	@Override
	public String 				toString(NumberFormat _nf) 	{ return "(" + _nf.format(getMinX()) + "), [" + _nf.format(getWidth()) + "]"; }

	@Override
	public BoundingBox.OneDim 	clone() 					{ throw new NotYetImplementedException(); }

}
