package fr.java.graph.impl.layouts.planar;

import fr.java.beans.impl.SimpleDoubleBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.graph.GTNode;
import fr.java.graph.impl.layouts.base.GTNodeLayoutBase;
import fr.java.graph.layouts.GTNodeLayout2D;

public class GTNodeLayout2DAdapter extends GTNodeLayoutBase implements GTNodeLayout2D {

	final BeanProperty<Double> xProperty,     yProperty;
	final BeanProperty<Double> widthProperty, heightProperty;

	public GTNodeLayout2DAdapter(GTNode _node) {
		super(_node);

		xProperty      = new SimpleDoubleBeanProperty(this, "x", 0d);
		yProperty      = new SimpleDoubleBeanProperty(this, "y", 0d);
		widthProperty  = new SimpleDoubleBeanProperty(this, "width", 1d);
		heightProperty = new SimpleDoubleBeanProperty(this, "height", 1d);
	}

	public BeanProperty<Double> 	xProperty() {
		return xProperty;
	}
	public void 					setX(double _x) {
		xProperty.set(_x);
	}
	public double 					getX() {
		return xProperty.get();
	}

	public BeanProperty<Double> 	yProperty() {
		return yProperty;
	}
	public void 					setY(double _y) {
		yProperty.set(_y);
	}
	public double 					getY() {
		return yProperty.get();
	}

	public BeanProperty<Double> 	widthProperty() {
		return widthProperty;
	}
	public void 					setWidth(double _width) {
		widthProperty.set(_width);
	}
	public double 					getWidth() {
		return widthProperty.get();
	}

	public BeanProperty<Double> 	heightProperty() {
		return heightProperty;
	}
	public void 					setHeight(double _height) {
		heightProperty.set(_height);
	}
	public double 					getHeight() {
		return heightProperty.get();
	}

	public void 					relocate(double _x, double _y) {
		xProperty.set(_x);
		yProperty.set(_y);
	}
	public void 					resize(double _width, double _height) {
		widthProperty.set(_width);
		heightProperty.set(_height);
	}
	public void 					resizeRelocate(double _x, double _y, double _width, double _height) {
		xProperty.set(_x);
		yProperty.set(_y);
		widthProperty.set(_width);
		heightProperty.set(_height);
	}

}
