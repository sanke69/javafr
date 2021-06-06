package fr.javafx.scene.control.viewport.plugins;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Points;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PlaneViewportBehavior<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD> {
	protected PlaneViewportControl<MODEL, COORD>	control;

	private EventHandler<? super KeyEvent>    	keyPressed,   keyReleased;
	private EventHandler<? super MouseEvent>  	mousePressed, mouseReleased;
	private EventHandler<? super MouseEvent>  	mouseMoved;
	private EventHandler<? super MouseEvent>  	mouseDragging;
	private EventHandler<? super MouseEvent>  	mouseEntered;
	private EventHandler<? super ScrollEvent> 	scrolling;

	/** *************************** **\
	 *                               *
	 *         CANVAS MOTION         *
	 *                               *
	\** *************************** **/
	public boolean crtlDown, AltDown, ShiftDown;
	public Point2D mouse;
	public Point2D lastPosition;

	protected final BooleanProperty	isMovingProperty;
	protected final BooleanProperty	isZoomingProperty;
	protected final BooleanProperty	isDraggingProperty;

	public PlaneViewportBehavior() {
		super();

		isMovingProperty   = new SimpleBooleanProperty(false);
		isZoomingProperty  = new SimpleBooleanProperty(false);
		isDraggingProperty = new SimpleBooleanProperty(false);
	}

	@Override
	public void 				setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		control = _pvpControl;
		control . addEventHandler    (KeyEvent.KEY_PRESSED, 	 keyPressed    = keyPressedHandler4Sizing());
		control . addEventHandler    (KeyEvent.KEY_RELEASED, 	 keyReleased   = keyReleasedHandler4Sizing());
		control . addEventHandler    (MouseEvent.MOUSE_PRESSED,  mousePressed  = mousePressedHandler4Motion());
		control . addEventHandler    (MouseEvent.MOUSE_RELEASED, mouseReleased = mouseReleasedHandler4Motion());
		control . addEventHandler    (MouseEvent.MOUSE_MOVED,    mouseMoved    = mouseMovedHandler4Motion());
		control . addEventHandler    (MouseEvent.MOUSE_DRAGGED,  mouseDragging = mouseDragHandler4Motion());
		control . addEventHandler    (MouseEvent.MOUSE_ENTERED,  mouseEntered  = mouseEnteredHandler4Motion());
		control . addEventHandler    (ScrollEvent.ANY,   		 scrolling     = scrollHandler4Motion());
	}
	public void 				unsetViewportControl() {
		control . removeEventHandler (KeyEvent.KEY_PRESSED,      keyPressed);
		control . removeEventHandler (KeyEvent.KEY_RELEASED,     keyReleased);
		control . removeEventHandler (MouseEvent.MOUSE_PRESSED,  mousePressed);
		control . removeEventHandler (MouseEvent.MOUSE_RELEASED, mouseReleased);
		control . removeEventHandler (MouseEvent.MOUSE_MOVED,    mouseMoved);
		control . removeEventHandler (MouseEvent.MOUSE_DRAGGED,  mouseDragging);
		control . removeEventHandler (MouseEvent.MOUSE_ENTERED,  mouseEntered);
		control . removeEventHandler (ScrollEvent.ANY, 		     scrolling);
		control = null;

		keyPressed    = null;
		keyReleased   = null;
		mousePressed  = null;
		mouseReleased = null;
		mouseMoved    = null;
		mouseDragging = null;
		mouseEntered  = null;
		scrolling     = null;
	}
	@Override
	public ObservableList<Node> getChildren() {
		return FXCollections.emptyObservableList();
	}

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			switch(e.getCode()) {
/*
			case A			:	control.fitContentToControl();
								break;
			case Z			: 	control.fitToContent();
								break;
			case F			: 	if(e.isControlDown())
									control.autofitToContent(!control.autofitToContent());
								else
									control.autofitContentToControl(!control.autofitContentToControl());
								break;
			case R			: 	control.setResizable(!control.isResizable());
								break;
			case Q			: 	control.reset();
								break;
			case SPACE		: 	break;
*/
			case UP         :  	control.translate( 0, -1); break;
			case DOWN       :  	control.translate( 0,  1); break;
			case LEFT       :  	control.translate(-1,  0); break;
			case RIGHT      :  	control.translate( 1,  0); break;

//			case ADD		: 	control.preferredFpsProperty().set(e.isAltDown() ? 60 : control.preferredFpsProperty().get() + 1); break;
//			case SUBTRACT	: 	control.preferredFpsProperty().set(e.isAltDown() ?  0 : control.preferredFpsProperty().get() - 1); break;
			default			: 	return;
			}
			e.consume();
		};
	}
	public EventHandler<? super KeyEvent>    keyReleasedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			if( !e.isControlDown() )
				isZoomingProperty.set(false);
		};
	}
	public EventHandler<? super MouseEvent>  mouseEnteredHandler4Motion() {
		return e -> control.requestFocus();
	}
	public EventHandler<? super MouseEvent>  mousePressedHandler4Motion() {
		return e -> { 
			if(e.getButton() == MouseButton.SECONDARY) {
				lastPosition = Points.of(e.getX(), e.getY());
			}
		};
	}
	public EventHandler<? super MouseEvent>  mouseReleasedHandler4Motion() {
		return e -> {
			if(!e.isSecondaryButtonDown())
				isDraggingProperty.set(false);
		};
	}

	public EventHandler<? super MouseEvent>  mouseMovedHandler4Motion() {
		return e -> mouse = Points.of(e.getX(), e.getY());
	}
	public EventHandler<? super MouseEvent>  mouseDragHandler4Motion() {
		return e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				if (lastPosition != null) {
					if (!isDraggingProperty.get())
						control.setCursor(Cursor.MOVE);

					Point2D newPosition = Points.of(e.getX(), e.getY());

					control.translate(newPosition.minus(lastPosition));

					lastPosition = newPosition;

					e.consume();
				}
				isDraggingProperty.set(true);
			}
			mouse = Points.of(e.getX(), e.getY());
		};
	}
	public EventHandler<? super ScrollEvent> scrollHandler4Motion() {
		return e -> {
			if(e.isControlDown()) {
				isZoomingProperty.set(true);
				Point2D oldAP  = Points.of(control.getViewport().getViewAnchor()); // TODO:: Change to Coordinate
				control.zoom(e.getDeltaY() < 0, e.getX(), e.getY());
				if (lastPosition != null) {
					Point2D newAP = Points.of(control.getViewport().getViewAnchor());
					lastPosition  = Points.of( lastPosition.getX() - oldAP.getX() + newAP.getX(), 
							 					lastPosition.getY() - oldAP.getY() + newAP.getY());
				}
				e.consume();
			} else
				isZoomingProperty.set(false);
		};
	}

}
