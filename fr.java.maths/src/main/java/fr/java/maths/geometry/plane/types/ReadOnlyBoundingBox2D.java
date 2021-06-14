package fr.java.maths.geometry.plane.types;

import java.text.NumberFormat;

import fr.java.lang.exceptions.IllegalAccessArgument;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;

public class ReadOnlyBoundingBox2D implements BoundingBox.TwoDims {
	private static final long serialVersionUID = 1L;

	double x, width;
	double y, height;

	public ReadOnlyBoundingBox2D() {
		super();
		x      = 0;
		width  = 0;
		y      = 0;
		height = 0;
	}
	public ReadOnlyBoundingBox2D(final double _x, final double _y, final double _width, final double _height) {
		super();
		x     = _x;
		width = _width;
		y      = _y;
		height = _height;
	}

	@Override
	public double 	getX() 			 { return x; }
	@Override
	public double 	getY() 			 { return y; }

	@Override
	public double 	getWidth() 		 { return width; }
	@Override
	public double 	getHeight() 	 { return height; }

	@Override
	public boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0; }

	@Override
	public double  	getMinY()        { return getY(); }
	@Override
    public double  	getMaxY()        { return getMinY() + getHeight(); }
	@Override
    public double  	getCenterY()     { return (getMinY() + getMaxY()) / 2; }

	@Override
    public double  	getTop()         { return getMaxY(); }
	@Override
    public double  	getBottom()      { return getMinY(); }

	@Override
	public double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
	@Override
	public double 	getArea() 		 { return getWidth() * getHeight(); }

	@Override
	public boolean 	contains(double _x, double _y) {
		if(isEmpty() || Double.isNaN(_x) || Double.isNaN(_y))
			return false;
		return _x >= getMinX() && _x <= getMaxX()
			&& _y >= getMinY() && _y <= getMaxY();
	}
	@Override
	public boolean 	contains(double _x, double _y, double _w, double _h) {
		return contains(_x, _y)
			&& contains(_x + _w, _y + _h);
	}
	@Override
	public boolean 	contains(Coordinate _pt) {
		if(_pt instanceof Coordinate.TwoDims)
			return contains((Coordinate.TwoDims) _pt);
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	contains(Coordinate.TwoDims _pt) {
		return contains( _pt.getFirst(), _pt.getSecond());
	}
	@Override
	public boolean 	contains(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.TwoDims)
			return contains(((BoundingBox.TwoDims) _pt));
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	contains(BoundingBox.TwoDims _bbox) {
		return contains(_bbox.getMinX(), _bbox.getMinY())
			&& contains(_bbox.getMaxX(), _bbox.getMaxY());
	}

	@Override
	public boolean 	intersects(double _x, double _y, double _w, double _h) {
		if (isEmpty() || _w < 0 || _h < 0)
			return false;

		return getMinX() < _x + _w && getMaxX() > _x 
			&& getMaxY() > _y      && getMinY() < _y + _h;
	}
	@Override
	public boolean 	intersects(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.TwoDims)
			return contains(((BoundingBox.TwoDims) _pt));
		throw new IllegalAccessArgument();
	}
	@Override
	public boolean 	intersects(BoundingBox.TwoDims _bbox) {
//		if ((_bbox == null) || _bbox.isEmpty())
		if (_bbox == null)
			return false;
		return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getWidth(), _bbox.getHeight());
	}

	@Override
	public double 	getIntersectionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
	}
	@Override
	public double 	getUnionArea(BoundingBox.TwoDims _bbox) {
		throw new NotYetImplementedException();
	}
	@Override
	public double 	getIOU(BoundingBox.TwoDims _bbox) {
		return getIntersectionArea(_bbox) / getUnionArea(_bbox);
	}

	@Override
	public String				toString()					{ return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]"; }
//	@Override
//	public String				toString()					{ return toString(new DecimalFormat("#.#")); }

	@Override
	public String 				toString(NumberFormat _nf) 	{ return "(" + _nf.format(getMinX()) + ", " + _nf.format(getMinY()) + "), [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "]"; }

	@Override
	public BoundingBox.TwoDims 	clone() 					{ throw new NotYetImplementedException(); }

}
