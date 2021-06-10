package fr.java.graph.impl.layouts.planar;

import fr.java.beans.impl.SimpleObjectBeanProperty;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.graph.GTEdge;
import fr.java.graph.impl.layouts.base.GTEdgeLayoutBase;
import fr.java.graph.layouts.GTEdgeLayout2D;
import fr.java.graph.layouts.GTNodeLayout2D;
import fr.java.math.geometry.plane.Point2D;
import fr.utils.graphs.Points;

public class GTEdgeLayout2DAdapter extends GTEdgeLayoutBase implements GTEdgeLayout2D {

	final BeanProperty<Point2D> startProperty, stopProperty;

	public GTEdgeLayout2DAdapter(GTEdge _edge, GTNodeLayout2D _sourceLayout, GTNodeLayout2D _targetLayout) {
		super(_edge, _sourceLayout, _targetLayout);

		startProperty = new SimpleObjectBeanProperty<Point2D>(this, "start");
		stopProperty  = new SimpleObjectBeanProperty<Point2D>(this, "stop");

		// TODO:: Add BoundingBox2D Bean Property !!!
		// TODO:: Add a finalize mechanism
		final Runnable updateStart = () -> {
			double px = getSource().getX() + getSource().getWidth() / 2;
			double py = getSource().getY() + getSource().getHeight() / 2;
			startProperty.set(Points.of(px, py));
		};
		final Runnable updateStop = () -> {
			double px = getTarget().getX() + getTarget().getWidth() / 2;
			double py = getTarget().getY() + getTarget().getHeight() / 2;
			stopProperty.set(Points.of(px, py));
		};

		_sourceLayout.xProperty()      . addListener((_bean, _old, _new) -> updateStart.run());
		_sourceLayout.yProperty()      . addListener((_bean, _old, _new) -> updateStart.run());
		_sourceLayout.widthProperty()  . addListener((_bean, _old, _new) -> updateStart.run());
		_sourceLayout.heightProperty() . addListener((_bean, _old, _new) -> updateStart.run());
		_targetLayout.xProperty()      . addListener((_bean, _old, _new) -> updateStop.run());
		_targetLayout.yProperty()      . addListener((_bean, _old, _new) -> updateStop.run());
		_targetLayout.widthProperty()  . addListener((_bean, _old, _new) -> updateStop.run());
		_targetLayout.heightProperty() . addListener((_bean, _old, _new) -> updateStop.run());
	}

	public ReadOnlyBeanProperty<Point2D> 	startProperty() {
		return startProperty;
	}
	public Point2D 							getStart() {
		return startProperty.get();
	}

	public ReadOnlyBeanProperty<Point2D> 	stopProperty() {
		return stopProperty;
	}
	public Point2D 							getStop() {
		return stopProperty.get();
	}

	@Override
	public GTNodeLayout2D 					getSource() {
		return (GTNodeLayout2D) super.getSource();
	}
	@Override
	public GTNodeLayout2D 					getTarget() {
		return (GTNodeLayout2D) super.getTarget();
	}

}
