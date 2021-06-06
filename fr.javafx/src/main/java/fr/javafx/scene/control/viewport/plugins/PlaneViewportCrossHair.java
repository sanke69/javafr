package fr.javafx.scene.control.viewport.plugins;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Points;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PlaneViewportCrossHair<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	private PlaneViewportControl<MODEL, COORD>		control;
	private final Line 								hLine, vLine;
	private final EventHandler<? super MouseEvent> 	onEntering, onLeaving, onMotion;

	private final ObjectBinding<Point2D>			target;
	private final DoubleProperty					x, y;

	public PlaneViewportCrossHair() {
		super();

		x = new SimpleDoubleProperty(Double.NaN);
		y = new SimpleDoubleProperty(Double.NaN);
		
		target = new ObjectBinding<Point2D>() {
			{ bind(x, y); } 

			@Override
			protected Point2D computeValue() {
				if(x.get() == Double.NaN || y.get() == Double.NaN)
					return Point2D.NaN;

				Coordinate.TwoDims cInWindow  = Points.of(x.get(), y.get());
				Coordinate.TwoDims cInModel   = control.getViewport().windowInModel(cInWindow);

				boolean OK  = cInModel.getFirst()  > control.getViewport().getModelBounds().getMinX()
						   && cInModel.getFirst()  < control.getViewport().getModelBounds().getMaxX()
						   && cInModel.getSecond() > control.getViewport().getModelBounds().getMinY()
						   && cInModel.getSecond() < control.getViewport().getModelBounds().getMaxY();

				return OK ? Points.of(cInModel) : Point2D.NaN;
			}
		};

		hLine = new Line(0, 0, 1, 0);
		hLine . setStroke(Color.BLACK);
		hLine . setStrokeWidth(1.0f);
		hLine . setMouseTransparent(true);
		hLine . startYProperty().bind(hLine.endYProperty());

		vLine = new Line(0, 0, 0, 1);
		vLine . setStroke(Color.BLACK);
		vLine . setStrokeWidth(1.0f);
		vLine . setMouseTransparent(true);
		vLine . startXProperty().bind(vLine.endXProperty());

		onEntering = (me) -> { hLine.setVisible(true); vLine.setVisible(true); };
		onLeaving  = (me) -> { hLine.setVisible(false); vLine.setVisible(false); };
		onMotion   = (me) -> {
			x.set(me.getX());
			y.set(me.getY());
			if(me.getY() < control.getHeight()) hLine.setEndY(me.getY());
			if(me.getX() < control.getWidth())  vLine.setEndX(me.getX());
		};

	}

	@Override
	public void 					setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		control = _pvpControl;

		hLine.endXProperty().bind(control.widthProperty());
		vLine.endYProperty().bind(control.heightProperty());

		control.addEventFilter(MouseEvent.MOUSE_ENTERED,    onEntering);
		control.addEventFilter(MouseEvent.MOUSE_EXITED,     onLeaving);
		control.addEventFilter(MouseEvent.MOUSE_MOVED,      onMotion);
	}
	public void 					unsetViewportControl() {
		hLine.endXProperty().unbind();
		vLine.endXProperty().unbind();

		control.removeEventFilter(MouseEvent.MOUSE_ENTERED, onEntering);
		control.removeEventFilter(MouseEvent.MOUSE_EXITED,  onLeaving);
		control.removeEventFilter(MouseEvent.MOUSE_MOVED,   onMotion);
	}

	@Override
	public ObservableList<Node> 	getChildren() {
		return FXCollections.observableArrayList(hLine, vLine);
	}

	public ReadOnlyDoubleProperty 	xProperty() {
		return x;
	}
	public ReadOnlyDoubleProperty 	yProperty() {
		return y;
	}
	public ObjectBinding<Point2D>	targetProperty() {
		return target;
	}

}
