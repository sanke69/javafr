package fr.javafx.beans.property.math;

import fr.java.math.geometry.space.Point3D;
import fr.java.maths.geometry.types.Points;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Point3DProperty extends SimpleObjectProperty<Point3D> {
	public static final Point3D DEFAULT_VALUE = Points.zero3();

	private DoubleProperty x, y, z;

	public Point3DProperty() {
		super(DEFAULT_VALUE);
	}
	public Point3DProperty(Point3D _pt) {
		super(_pt);
	}
    public Point3DProperty(Object _bean, String _name) {
		super(_bean, _name, DEFAULT_VALUE);
    }
    public Point3DProperty(Object _bean, String _name, Point3D _pt) {
		super(_bean, _name, _pt);
    }

	public void set(double _x, double _y, double _z) {
		setValue(Points.of(_x, _y, _z));
	}

	public double getX() {
	    return getValue().getX();
	}
	public void setX(double value) {
		setValue(Points.of(value, getY(), getZ()));
	}
	public DoubleProperty xProperty() {
	    if (x == null) {
	    	x = new SimpleDoubleProperty(this, "x", getX());
	    	x.addListener((_obs, _old, _new) -> setValue(Points.of(_new.doubleValue(), getY(), getZ())));
	    }
	    
	    return x;
	}

	public double getY() {
	    return getValue().getY();
	}
	public void setY(double value) {
		setValue(Points.of(getX(), value, getZ()));
	}
	public DoubleProperty yProperty() {
	    if (y == null) {
	    	y = new SimpleDoubleProperty(this, "y", getY());
	    	y.addListener((_obs, _old, _new) -> setValue(Points.of(getX(), _new.doubleValue(), getZ())));
	    }
	    
	    return y;
	}

	public double getZ() {
	    return getValue().getZ();
	}
	public void setZ(double value) {
		setValue(Points.of(getX(), getY(), value));
	}
	public DoubleProperty zProperty() {
	    if (z == null) {
	    	z = new SimpleDoubleProperty(this, "z", getZ());
	    	z.addListener((_obs, _old, _new) -> setValue(Points.of(getX(), getY(), _new.doubleValue())));
	    }
	    
	    return z;
	}

}
