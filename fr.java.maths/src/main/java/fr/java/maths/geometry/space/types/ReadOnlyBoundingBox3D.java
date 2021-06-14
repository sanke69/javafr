package fr.java.maths.geometry.space.types;

import java.text.NumberFormat;

import fr.java.lang.exceptions.IllegalAccessArgument;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.topology.Coordinate;

public class ReadOnlyBoundingBox3D implements BoundingBox.ThreeDims {
	private static final long serialVersionUID = 1L;

	double x, width;
	double y, height;
	double z, depth;

	public ReadOnlyBoundingBox3D() {
		super();
		x      = 0;
		width  = 0;
		y      = 0;
		height = 0;
	}
	public ReadOnlyBoundingBox3D(final double _x, final double _y, final double _z, final double _width, final double _height, final double _depth) {
		super();
		x     = _x;
		width = _width;
		y      = _y;
		height = _height;
		z      = _z;
		depth  = _depth;
	}

	@Override
	public double 	getX() 			{ return x; }
	@Override
	public double 	getY() 			{ return y; }
	@Override
	public double 	getZ() 			{ return z; }

	@Override
	public double 	getWidth() 		{ return width; }
	@Override
	public double 	getHeight() 	{ return height; }
	@Override
	public double 	getDepth() 		{ return depth; }

	@Override
	public boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0 || getDepth() == 0; }

	public double  	getMinZ()        { return getZ(); }
    public double  	getMaxZ()        { return getMinZ() + getDepth(); }
    public double  	getCenterZ()     { return (getMinZ() + getMaxZ()) / 2; }

    public double  	getFront()       { return getMaxZ(); }
    public double  	getBack()        { return getMinZ(); }

	public double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
	public double 	getArea() 		 { return 2 * getWidth() * getHeight() + 2 * getWidth() * getDepth() + 2 * getHeight() * getDepth(); }
	public double 	getVolume()      { return getWidth() * getHeight() * getDepth(); }

	public boolean 	contains(double _x, double _y, double _z) {
		if(isEmpty())
			return false;
		return _x >= getMinX() && _x <= getMaxX()
			&& _y >= getMinY() && _y <= getMaxY()
			&& _z >= getMinZ() && _z <= getMaxZ();
	}
	public boolean 	contains(double _x, double _y, double _z, double _w, double _h, double _d) {
		return contains(_x, _y, _z) && contains(_x + _w, _y + _h, _z + _d);
	}
	public boolean 	contains(Coordinate _pt) {
		if(_pt instanceof Coordinate.ThreeDims)
			return contains((Coordinate.ThreeDims) _pt);
		throw new IllegalAccessArgument();
	}
	public boolean 	contains(Coordinate.ThreeDims _pt) {
		if (_pt == null)
			return false;
		return _pt.getFirst()  >= getMinX() && _pt.getFirst()  <= getMaxX()  
			&& _pt.getSecond() >= getMinY() && _pt.getSecond() <= getMaxY() 
			&& _pt.getThird()  >= getMinZ() && _pt.getThird()  <= getMaxZ();
	}
	public boolean 	contains(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.ThreeDims)
			return contains(((BoundingBox.ThreeDims) _pt));
		throw new IllegalAccessArgument();
	}
	public boolean 	contains(BoundingBox.ThreeDims _bbox) {
		return contains(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ())
			&& contains(_bbox.getMaxX(), _bbox.getMaxY(), _bbox.getMaxZ());
	}

	public boolean 	intersects(double _x, double _y, double _z, double _w, double _h, double _d) {
		if (isEmpty() || _w < 0 || _h < 0)
			return false;

		return getMinX() < _x + _w && getMaxX() > _x
			&& getMinY() < _y + _h && getMaxY() > _y
			&& getMinZ() < _z + _d && getMaxY() < _z;
	}
	public boolean 	intersects(BoundingBox _pt) {
		if(_pt instanceof BoundingBox.ThreeDims)
			return contains(((BoundingBox.ThreeDims) _pt));
		throw new IllegalAccessArgument();
	}
	public boolean 	intersects(BoundingBox.ThreeDims _bbox) {
		if ((_bbox == null) || _bbox.isEmpty())
			return false;
		return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ(), _bbox.getWidth(), _bbox.getHeight(), _bbox.getDepth());
	}

	@Override
	public String					toString()					{ return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]"; }
//	@Override
//	public String					toString()					{ return toString(new DecimalFormat("#.#")); }

	@Override 
	public String 					toString(NumberFormat _nf) 	{ return "(" + _nf.format(getX()) + ", " + _nf.format(getY()) + ", " + _nf.format(getZ()) + ") [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "x" + _nf.format(getDepth()) + "]"; }

	@Override
	public BoundingBox.ThreeDims 	clone() 					{ throw new NotYetImplementedException(); }

}
