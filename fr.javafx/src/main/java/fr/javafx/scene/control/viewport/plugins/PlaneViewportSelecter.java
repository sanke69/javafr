package fr.javafx.scene.control.viewport.plugins;

import fr.java.math.geometry.Viewport.TwoDims.Editable;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Points;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlaneViewportSelecter<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	private PlaneViewportControl<MODEL, COORD>	control;

	private EventHandler<? super KeyEvent>    	keyPressed,   keyReleased;
	private EventHandler<? super MouseEvent>  	mousePressed, mouseReleased;
	private EventHandler<? super MouseEvent>  	mouseMoved;

	private final   ObjectBinding<Rectangle> 	selection;

	private final   Rectangle 					selectionView;
	private Point2D 							anchor;
	private BooleanProperty 					initialized, finalized;

	public PlaneViewportSelecter() {
		super();
		selectionView = new Rectangle(0, 0, 1, 1);
		selectionView . setVisible(false);

		initialized = new SimpleBooleanProperty(false);
		finalized   = new SimpleBooleanProperty(false);

		selection = new ObjectBinding<Rectangle>() {
			{ bind(initialized, finalized); } 

			@Override
			protected Rectangle computeValue() {
				if(!initialized.get() || !finalized.get())
					return null;

				Coordinate.TwoDims tlInWindow  = Points.of(selectionView.getX(), selectionView.getY());
				Coordinate.TwoDims brInWindow  = Points.of(selectionView.getX() + selectionView.getWidth(), selectionView.getY() + selectionView.getHeight());
				Coordinate.TwoDims tlInModel   = getInModel(tlInWindow);
				Coordinate.TwoDims brInModel   = getInModel(brInWindow);

				double X = tlInModel.getFirst();
				double Y = tlInModel.getSecond();
				double W = brInModel.getFirst() - tlInModel.getFirst();
				double H = brInModel.getSecond() - tlInModel.getSecond();

				return new Rectangle((int) X, (int) Y, (int) W, (int) H);
			}
		};

	}

	@Override
	public void 							setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		control = _pvpControl;
		control . addEventFilter    (KeyEvent.KEY_PRESSED, 	    keyPressed    = keyPressedHandler4Sizing());
		control . addEventFilter    (KeyEvent.KEY_RELEASED, 	keyReleased   = keyReleasedHandler4Sizing());
		control . addEventFilter    (MouseEvent.MOUSE_PRESSED,  mousePressed  = mousePressedHandler4Motion());
		control . addEventFilter    (MouseEvent.MOUSE_RELEASED, mouseReleased = mouseReleasedHandler4Motion());
		control . addEventFilter    (MouseEvent.MOUSE_MOVED,    mouseMoved    = mouseMovedHandler4Motion());

		control.viewportProperty().addListener(evt -> {
			if(!initialized.get() || !finalized.get() || selection.get() == null)
				return ;

			Coordinate.TwoDims tlSelection  = Points.of(selection.get().getX(), selection.get().getY());
			Coordinate.TwoDims brSelection  = Points.of(selection.get().getX() + selection.get().getWidth(), selection.get().getY() + selection.get().getHeight());
			Coordinate.TwoDims tlInWindow   = getInWindow(tlSelection);
			Coordinate.TwoDims brInWindow   = getInWindow(brSelection);

			double X = tlInWindow.getFirst();
			double Y = tlInWindow.getSecond();
			double W = brInWindow.getFirst()  - tlInWindow.getFirst();
			double H = brInWindow.getSecond() - tlInWindow.getSecond();

			selectionView.setX((int) X);
			selectionView.setY((int) Y);
			selectionView.setWidth((int) W);
			selectionView.setHeight((int) H);

		});
	}

	public void 							unsetViewportControl() {
		control . removeEventFilter (KeyEvent.KEY_PRESSED,      keyPressed);
		control . removeEventFilter (KeyEvent.KEY_RELEASED,     keyReleased);
		control . removeEventFilter (MouseEvent.MOUSE_PRESSED,  mousePressed);
		control . removeEventFilter (MouseEvent.MOUSE_RELEASED, mouseReleased);
		control . removeEventFilter (MouseEvent.MOUSE_MOVED,    mouseMoved);
	}
	@Override
	public ObservableList<Node> 			getChildren() {
		return FXCollections.observableArrayList(selectionView);
	}

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if(e.isConsumed())
				return;

			switch(e.getCode()) {
			case SPACE		: 	initialized.set(false); finalized.set(false); selectionView.setVisible(false); break;
			default			: 	return;
			}

			e.consume();
		};
	}
	public EventHandler<? super KeyEvent>    keyReleasedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;
		};
	}
	public EventHandler<? super MouseEvent>  mousePressedHandler4Motion() {
		return e -> { 
			if(e.getButton() == MouseButton.PRIMARY) {
				Point2D pt      = Points.of(e.getX(), e.getY());
				Point2D inModel = getInModel(e);

				if(inModel == Point2D.NaN)
					return ;

				if(initialized.get() && finalized.get()) {
					initialized.set(false);
					finalized.set(false);
				}

				if(initialized.get()) {
					double X = e.getX() < anchor.getX() ? e.getX() : anchor.getX();
					double Y = e.getY() < anchor.getY() ? e.getY() : anchor.getY();
					double W = X == e.getX() ? anchor.getX() - e.getX() : e.getX() - anchor.getX();
					double H = Y == e.getY() ? anchor.getY() - e.getY() : e.getY() - anchor.getY();

					selectionView.setX((int) X);
					selectionView.setY((int) Y);
					selectionView.setWidth((int) W);
					selectionView.setHeight((int) H);

					selectionView.setStroke(Color.GREEN.deriveColor(1, 1, 1, 0.7));
					selectionView.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));

					finalized.set(true);
				} else {
					anchor = pt;

					selectionView.setX((int) pt.getX());
					selectionView.setY((int) pt.getY());
					selectionView.setWidth(0);
					selectionView.setHeight(0);

					selectionView.setVisible(true);
					selectionView.setStroke(Color.RED.deriveColor(1, 1, 1, 0.7));
					selectionView.setFill(Color.RED.deriveColor(1, 1, 1, 0.5));

					initialized.set(true);
				}
				
			}
		};
	}
	private EventHandler<? super MouseEvent> mouseReleasedHandler4Motion() {
		return e -> {
			if(!e.isSecondaryButtonDown())
				;
		};
	}
	private EventHandler<? super MouseEvent> mouseMovedHandler4Motion() {
		return e -> { 
			if(initialized.get() && !finalized.get()) {
				double X = e.getX() < anchor.getX() ? e.getX() : anchor.getX();
				double Y = e.getY() < anchor.getY() ? e.getY() : anchor.getY();
				double W = X == e.getX() ? anchor.getX() - e.getX() : e.getX() - anchor.getX();
				double H = Y == e.getY() ? anchor.getY() - e.getY() : e.getY() - anchor.getY();

				selectionView.setX(X);
				selectionView.setY(Y);
				selectionView.setWidth(W);
				selectionView.setHeight(H);
			}
		};
	}
	
	public ObjectBinding<Rectangle>			 selectionProperty() {
		return selection;
	}

	protected Point2D getInModel(Coordinate.TwoDims _pt) {
		return getInModel(_pt.getFirst(), _pt.getSecond());
	}
	protected Point2D getInModel(Point2D _pt) {
		return getInModel(_pt.getX(), _pt.getY());
	}
	protected Point2D getInModel(MouseEvent _me) {
		return getInModel(_me.getX(), _me.getY());
	}
	protected Point2D getInModel(double _x, double _y) {
		if(_x == Double.NaN || _y == Double.NaN)
			return Point2D.NaN;

		Coordinate.TwoDims cInWindow  = Points.of(_x, _y);
		Coordinate.TwoDims cInModel   = control.getViewport().windowInModel(cInWindow);

		boolean OK  = cInModel.getFirst()  > control.getViewport().getModelBounds().getMinX()
				   && cInModel.getFirst()  < control.getViewport().getModelBounds().getMaxX()
				   && cInModel.getSecond() > control.getViewport().getModelBounds().getMinY()
				   && cInModel.getSecond() < control.getViewport().getModelBounds().getMaxY();

		return OK ? Points.of(cInModel) : Point2D.NaN;
	}

	protected Point2D getInWindow(Coordinate.TwoDims _pt) {
		return getInWindow(_pt.getFirst(), _pt.getSecond());
	}
	protected Point2D getInWindow(Point2D _pt) {
		return getInWindow(_pt.getX(), _pt.getY());
	}
	protected Point2D getInWindow(double _x, double _y) {
		if(_x == Double.NaN || _y == Double.NaN)
			return Point2D.NaN;

		Coordinate.TwoDims cInModel   = Points.of(_x, _y);
		Coordinate.TwoDims cInWindow  = control.getViewport().modelInWindow((COORD) cInModel);

		return Points.of(cInWindow);
	}

}
