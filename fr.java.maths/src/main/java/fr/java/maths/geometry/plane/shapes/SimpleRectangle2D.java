package fr.java.maths.geometry.plane.shapes;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.topology.Coordinate;
import fr.java.maths.Points;
import fr.java.maths.geometry.plane.types.SimpleDimension2D;
import fr.java.nio.buffer.Point2DBufferX;

public class SimpleRectangle2D implements Cloneable, Comparable<Object>, Serializable, Rectangle2D, BoundingBox.TwoDims.Editable {
	private static final long serialVersionUID = -6112340687137795838L;

	protected double minX,  minY;
    protected double width, height;

    public static SimpleRectangle2D empty() {
    	return new SimpleRectangle2D(0, 0, 0, 0);
    }
    public static SimpleRectangle2D unit() {
    	return new SimpleRectangle2D(0, 0, 1, 1);
    }
    public static SimpleRectangle2D of(double _x, double _y, double _width, double _height) {
    	return new SimpleRectangle2D(_x, _y, _width, _height);
    }
	public static SimpleRectangle2D of(Point2D _bottomLeft, Dimension2D _dimensions) {
		return new SimpleRectangle2D(_bottomLeft, _dimensions);
	}
	public static SimpleRectangle2D of(Collection<Point2D> _points) {
		return new SimpleRectangle2D(_points);
	}
    public static SimpleRectangle2D fromBounds(double _minX, double _minY, double _maxX, double _maxY) {
    	if(_maxX < _minX) {
    		double tmp = _minX; _minX = _maxX; _maxX = tmp;
    	}
    	if(_maxY < _minY) {
    		double tmp = _minY; _minY = _maxY; _maxY = tmp;
    	}
    	return new SimpleRectangle2D(_minX, _minY, _maxX - _minX, _maxY - _minY);
    }

    public SimpleRectangle2D() {
        super();
        minX  = minY   = 0.0;
        width = height = 1.0;
    }
    public SimpleRectangle2D(double _width, double _height) {
        this();
        width  = _width;
        height = _height;
    }
    public SimpleRectangle2D(Dimension2D _dimension) {
        this(_dimension.getWidth(), _dimension.getHeight());
    }
    public SimpleRectangle2D(double _x, double _y, double _width, double _height) {
//    	if(_width < 0 || _height < 0)
//    		throw new RuntimeException();
        minX   = _x;
        minY   = _y;
        width  = _width < 0 ? 0 : _width;
        height = _height < 0 ? 0 : _height;
    }
    public SimpleRectangle2D(Point2D _position, Dimension2D _dimension) {
        minX   = _position.getX();
        minY   = _position.getY();
        width  = _dimension.getWidth();
        height = _dimension.getHeight();
    }
    public SimpleRectangle2D(BoundingBox.TwoDims _clone) {
    	this();
        minX   = _clone.getLeft();
        minY   = _clone.getBottom();
        width  = _clone.getWidth();
        height = _clone.getHeight();
    }
	public SimpleRectangle2D(Point2D... _points) {
		/*
		if (_points == null || _points.length < 2) {
			throw new IllegalArgumentException(
					"The attribute 'geoPositions' cannot be null and must "
							+ "have 2 or more elements.");
		}
		*/
		minY = Integer.MAX_VALUE;
		minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;
		for(Point2D position : _points) {
			minY = Math.min(minY, position.getY());
			minX = Math.min(minX, position.getX());
			maxY = Math.max(maxY, position.getY());
			maxX = Math.max(maxX, position.getX());
		}
        width  = maxX - minX;
        height = maxY - minY;
	}
	public SimpleRectangle2D(Collection<Point2D> _points) {
		/*
		if (_points == null || _points.size() < 2)
			throw new IllegalArgumentException(
					"The attribute 'geoPositions' cannot be null and must "
							+ "have 2 or more elements. " + (_points != null ? _points.size() : "null"));
		*/
		minY = Integer.MAX_VALUE;
		minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;
		for(Point2D position : _points) {
			minX = Math.min(minX, position.getX());
			minY = Math.min(minY, position.getY());
			maxX = Math.max(maxX, position.getX());
			maxY = Math.max(maxY, position.getY());
		}
        width  = maxX - minX;
        height = maxY - minY;
	}
	public SimpleRectangle2D(Point2DBufferX _points) {
		if (_points == null || _points.remaining() < 2)
			throw new IllegalArgumentException(
					"The parameter '_bounds' cannot be null and must "
							+ "have 2 or more elements.");

		Point2D pt;

		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		while(_points.hasRemaining()) {
			pt = _points.get();
			minX = Math.min(minX, pt.getX());
			minY = Math.min(minY, pt.getY());
			maxX = Math.max(maxX, pt.getX());
			maxY = Math.max(maxY, pt.getY());
		}

		width  = maxX - minX;
		height = maxY - minY;
	}

	public Set<Coordinate.TwoDims> getTops() {
		Set<Coordinate.TwoDims> corners = new HashSet<Coordinate.TwoDims>();
		corners.add(getBottomLeft());
		corners.add(getBottomRight());
		corners.add(getTopRight());
		corners.add(getTopLeft());
		return corners;
	}

    public Point2D 		getPosition() {
    	return Points.of(getMinX(), getMinY());
    }
    public Point2D 		getCenter() {
    	return Points.of(getMinX() + getWidth() / 2.0, getMinY() + getHeight() / 2.0);
    }
    public Dimension2D 	toDimension() {
    	return new SimpleDimension2D(getWidth(), getHeight());
    }

	@Override
	public void 		set(double _w, double _h) {
		setSize(_w, _h);
	}
    public void 		set(double _x, double _y, double _w, double _h) {
    	minX   = _x;
    	minY   = _y;
    	width  = _w;
    	height = _h;
    }

	@Override
	public void 		setLeft(double _left) {
		minX = _left;
	}
	@Override
	public void 		setRight(double _right) {
		width = _right - minX;
	}
	@Override
	public void 		setTop(double _top) {
		height = _top - minY;
	}
	@Override
	public void 		setBottom(double _bottom) {
		minY = _bottom;
	}

    public void 		setPosition(double _x, double _y) {
    	minX = _x;
    	minY = _y;
    }
    public void 		setPosition(Point2D _pt) {
    	setPosition(_pt.getX(), _pt.getY());
    }

    public void 		setCenter(double _x, double _y) {
    	minX = _x - getWidth() / 2.0;	
    	minY = _y - getHeight() / 2.0;	
    }
    public void 		setCenter(Point2D _pt) {
    	setCenter(_pt.getX(), _pt.getY());
    }

    public void 		setSize(double _w, double _h) {
    	width  = _w;
    	height = _h;
    }
    public void 		setSize(Dimension2D _dim) {
    	setSize(_dim.getWidth(), _dim.getHeight());
    }

	public boolean 		isEmpty() {
        return getWidth() <= 0 || getHeight() <= 0;
    }

	@Override
	public double 		getLeft() {
		return getMinX();
	}
	@Override
	public double 		getRight() {
		return getMinX() + getWidth();
	}
	@Override
	public double 		getTop() {
		return getMinY() + getHeight();
	}
	@Override
	public double 		getBottom() {
		return getMinY();
	}

    public void 		setX(double _x) {
    	minX = _x;
    }
    public void 		setMinX(double _x) {
    	minX = _x;
    }
    public void 		setMaxX(double _x) {
    	width = _x - minX;
    }
    public void 		setCenterX(double _x) {
    	minX = _x - getWidth() / 2.0;
    }
    public void 		setWidth(double _w) {
    	width = _w;
    }

    public void 		setY(double _y) {
    	minY = _y;
    }
    public void 		setMinY(double _y) {
    	minY = _y;
    }
    public void 		setMaxY(double _y) {
    	height = _y - minY;
    }
    public void 		setCenterY(double _y) {
    	minY = _y - getHeight() / 2.0;
    }
    public void 		setHeight(double _h) {
    	height = _h;
    }

    public double 		getX() {
		return getMinX();
	}
    public double 		getMinX() {
		return minX;
	}
    public double 		getMaxX() {
		return minX + width;
	}
    public double 		getCenterX() {
		return minX + width / 2.0;
	}
    public double 		getWidth() {
		return width;
	}

    public double 		getY() {
		return getMinY();
	}
    public double 		getMinY() {
		return minY;
	}
    public double 		getMaxY() {
		return minY + height;
	}
    public double 		getCenterY() {
		return minY + height / 2.0;
	}
    public double 		getHeight() {
		return height;
	}

    public Point2D 		getBottomLeft() {
    	return Points.of(getMinX(), getMinY());
    }
    public void 		setBottomLeft(Point2D _bl) {
    	setBottomLeft(_bl.getY(), _bl.getX());
    }
    // Same as setPosition
    public void 		setBottomLeft(double _bottom, double _left) {
    	minX = _left;
    	minY = _bottom;
    }

    public Point2D 		getTopLeft() {
    	return Points.of(getMinX(), getMaxY());
    }
    public void 		setTopLeft(Point2D _tl) {
    	setTopLeft(_tl.getY(), _tl.getX());
    }
    public void 		setTopLeft(double _top, double _left) {
    	minX = _left;
    	minY = _top - getHeight();
    }

    public Point2D 		getTopRight() {
    	return Points.of(getMaxX(), getMaxY());
    }
    public void 		setTopRight(Point2D _tr) {
    	setTopRight(_tr.getY(), _tr.getX());
    }
    public void 		setTopRight(double _top, double _right) {
    	minX = _right - getWidth();
    	minY = _top - getHeight();
    }

    public Point2D 		getBottomRight() {
    	return Points.of(getMaxX(), getMinY());
    }
    public void 		setBottomRight(Point2D _br) {
    	setBottomRight(_br.getY(), _br.getX());
    }
    public void 		setBottomRight(double _bottom, double _right) {
    	minX = _right - getWidth();
    	minY = _bottom;
    }

    public void   		add(double _x, double _y) {
        double x1 = Math.min(getMinX(), _x);
        double x2 = Math.max(getMaxX(), _x);
        double y1 = Math.min(getMinY(), _y);
        double y2 = Math.max(getMaxY(), _y);
        minX = x1;
        minY = y1;
        width = x2 - minX;
        height = y2 - minY;
    }
    public void   		add(Coordinate.TwoDims p) {
        add(p.getFirst(), p.getSecond());
    }
    public void   		add(Point2D p) {
        add(p.getX(), p.getY());
    }

	@Override
	public double 		getArea() {
	    return Math.abs(getRight() - getLeft()) * Math.abs(getTop() - getBottom()); 
	}

    public boolean 		contains(double x, double y) {
        if (isEmpty()) return false;
        return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY();
    }
    public boolean 		contains(double x, double y, double w, double h) {
        return contains(x, y) && contains(x + w, y + h);
    }
    public boolean 		contains(Point2D _p) {
        if(_p == null) 
        	return false;
        return contains(_p.getX(), _p.getY());
    }
    public boolean 		contains(BoundingBox.TwoDims _bb) {
        return contains(_bb.getMinX(), _bb.getMinY()) && contains(_bb.getMaxX(), _bb.getMaxY());
    }

    public boolean 		intersects(BoundingBox.TwoDims b) {
        if ((b == null) || b.isEmpty()) return false;
        return intersects(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
    }
    public boolean 		intersects(double x, double y, double w, double h) {
        if (isEmpty() || w < 0 || h < 0) return false;

        return (getMinX() < x + w && getMaxX() > x && 
        		getMaxY() > y     && getMinY() < y + h);
    }

	@Override
	public double getIntersectionArea(BoundingBox.TwoDims _bbox) {
		if (!(_bbox instanceof SimpleRectangle2D))
			throw new NotYetImplementedException();
		
		if(!intersects(_bbox))
			return 0;

		return (Math.min(getRight(),  _bbox.getRight())  - Math.max(getLeft(), _bbox.getLeft())) 
				 * (Math.min(getBottom(), _bbox.getBottom()) - Math.max(getTop(),  _bbox.getTop()));
	}
	@Override
	public double getUnionArea(BoundingBox.TwoDims _bbox) {
		if (!(_bbox instanceof SimpleRectangle2D))
			throw new NotYetImplementedException();

		double area1 = getArea();
		double area2 = _bbox.getArea();
		double areaI = getIntersectionArea(_bbox);

		return (area1 + area2 - areaI);
	}

	@Override
	public int 			compareTo(Object o) {
		// TODO:: 
		return 0;
	}

	@Override
	public BoundingBox.TwoDims.Editable 	clone() {
		return new SimpleRectangle2D(minX, minY, width, height);
	}
	
    @Override
    public boolean 		equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof SimpleRectangle2D) {
        	SimpleRectangle2D other = (SimpleRectangle2D) obj;
            return getMinX() == other.getMinX()
                && getMinY() == other.getMinY()
                && getWidth() == other.getWidth()
                && getHeight() == other.getHeight();
        } else return false;
    }

    @Override
    public String 		toString() {
    	return "(" + getMinX() + ", " + getMinY() + "), [" + getWidth() + "x" + getHeight() + "]";
    }
	@Override
	public String 		toString(NumberFormat _nf) {
		return toString();
	}
	/*
	@Override
	public String toString(DecimalFormat df) {
		// TODO Auto-generated method stub
		return null;
	}
	*/


    public double[] 	asArray() {
    	return new double[] { getX(), getY(), getWidth(), getHeight() };
    }

	@Override
	public boolean 		contains(Coordinate _pt) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean 		contains(BoundingBox _bb) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean 		intersects(BoundingBox _bb) {
		// TODO Auto-generated method stub
		return false;
	}

}
