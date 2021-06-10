package fr.java.maths.geometry.plane.shapes;

import java.io.Serializable;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Shape2D;

public class SimpleTriangle2D implements Shape2D, Cloneable, Comparable<Object>, Serializable {
	private static final long serialVersionUID = -6112340687137795838L;

	protected Point2D a, b, c;

    public static SimpleTriangle2D of(Point2D _a, Point2D _b, Point2D _c) {
    	return new SimpleTriangle2D(_a, _b, _c);
    }
    
    public SimpleTriangle2D() {
        super();
        a = b = c = null;
    }
    public SimpleTriangle2D(Point2D _a, Point2D _b, Point2D _c) {
        this();
        a = _a;
        b = _b;
        c = _c;
    }

    public Point2D getA() {
    	return a;
    }
    public Point2D getB() {
    	return b;
    }
    public Point2D getC() {
    	return c;
    }
    
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
