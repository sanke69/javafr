package fr.java.graph.layouts;

import fr.java.beans.properties.BeanProperty;
import fr.java.graph.viewer.GTLayout;

public interface GTGateLayout2D extends GTLayout.Gate {

	@Override
	public GTNodeLayout2D 			getOwner();

	public BeanProperty<Double> 	xProperty();
	public void 					setX(double _x);
	public double 					getX();

	public BeanProperty<Double> 	yProperty();
	public void 					setY(double _y);
	public double 					getY();

	public BeanProperty<Double> 	widthProperty();
	public void 					setWidth(double _width);
	public double 					getWidth();

	public BeanProperty<Double> 	heightProperty();
	public void 					setHeight(double _height);
	public double 					getHeight();

	public void 					relocate(double _x, double _y);
	public void 					resize(double _width, double _height);

}
