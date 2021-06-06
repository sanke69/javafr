package fr.java.graph.impl.layouts.planar;

import fr.java.beans.impl.SimpleDoubleBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.graph.GTGate;
import fr.java.graph.impl.layouts.base.GTGateLayoutBase;
import fr.java.graph.layouts.GTGateLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;

public class GTGateLayout2DAdapter extends GTGateLayoutBase implements GTGateLayout2D {

	final BeanProperty<Double> xProperty,     yProperty;
	final BeanProperty<Double> widthProperty, heightProperty;

	public GTGateLayout2DAdapter(GTGate _gate, GTNodeLayout2D _ownerLayout) {
		super(_gate, _ownerLayout);

		xProperty      = new SimpleDoubleBeanProperty(this, "x");
		yProperty      = new SimpleDoubleBeanProperty(this, "y");
		widthProperty  = new SimpleDoubleBeanProperty(this, "width");
		heightProperty = new SimpleDoubleBeanProperty(this, "height");
	}

	@Override
	public GTNodeLayout2D			getOwner() {
		return (GTNodeLayout2D) super.getOwner();
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
		return xProperty;
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

}
