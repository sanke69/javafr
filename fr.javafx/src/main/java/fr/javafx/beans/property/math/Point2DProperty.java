package fr.javafx.beans.property.math;

import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Points;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Point2DProperty extends SimpleObjectProperty<Point2D> {
	public static final Point2D DEFAULT_VALUE = Points.zero2();

	private DoubleProperty x, y;

	public Point2DProperty() {
		super(DEFAULT_VALUE);
	}
	public Point2DProperty(Point2D _pt) {
		super(_pt);
	}
    public Point2DProperty(Object _bean, String _name) {
		super(_bean, _name, DEFAULT_VALUE);
    }
    public Point2DProperty(Object _bean, String _name, Point2D _pt) {
		super(_bean, _name, _pt);
    }

	public void set(double _x, double _y) {
		setValue(Points.of(_x, _y));
	}

	public double getX() {
	    return getValue().getX();
	}
	public void setX(double value) {
		setValue(Points.of(value, getY()));
	}
	public DoubleProperty xProperty() {
	    if (x == null) {
	    	x = new SimpleDoubleProperty(this, "x", getX());
	    	x.addListener((_obs, _old, _new) -> setValue(Points.of(_new.doubleValue(), getY())));
	    }
	    
	    return x;
	}

	public double getY() {
	    return getValue().getY();
	}
	public void setY(double value) {
		setValue(Points.of(getX(), value));
	}
	public DoubleProperty yProperty() {
	    if (y == null) {
	    	y = new SimpleDoubleProperty(this, "y", getY());
	    	y.addListener((_obs, _old, _new) -> setValue(Points.of(getX(), _new.doubleValue())));
	    }
	    
	    return y;
	}

}
