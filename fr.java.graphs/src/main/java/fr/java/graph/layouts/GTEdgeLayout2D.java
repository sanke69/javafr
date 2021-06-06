package fr.java.graph.layouts;

import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.graph.viewer.GTLayout;
import fr.java.math.geometry.plane.Point2D;

public interface GTEdgeLayout2D extends GTLayout.Edge {

	public ReadOnlyBeanProperty<Point2D> 	startProperty();
	public Point2D 							getStart();

	public ReadOnlyBeanProperty<Point2D> 	stopProperty();
	public Point2D 							getStop();

	@Override
	public GTNodeLayout2D 					getSource();
	@Override
	public GTNodeLayout2D 					getTarget();

}
