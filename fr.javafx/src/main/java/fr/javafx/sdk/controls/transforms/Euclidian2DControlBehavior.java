package fr.javafx.sdk.controls.transforms;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Euclidian2DControlBehavior {
	private Euclidian2DControl control;
	private double speed = 1.0;
	
	EventHandler<KeyEvent> 		keyboard;
	EventHandler<ScrollEvent> 	scroll;

	public Euclidian2DControlBehavior(Euclidian2DControl _control) {
		super();
		control = _control;

		control.addEventHandler	(KeyEvent.KEY_PRESSED, 		keyboard      = keyEventHandler());
//		control.addEventHandler	(KeyEvent.KEY_RELEASED, 	null);
//		control.addEventHandler	(MouseEvent.MOUSE_PRESSED, 	mousePressed  = mousePressedHandler4Motion());
//		control.addEventHandler	(MouseEvent.MOUSE_RELEASED, mousePressed  = mousePressedHandler4Motion());
//		control.addEventHandler	(MouseEvent.MOUSE_MOVED, 	mouseMoved    = mouseMovedHandler4Motion());
//		control.addEventHandler	(MouseEvent.MOUSE_DRAGGED, 	mouseDragging = mouseDragHandler4Motion());
		control.addEventHandler (MouseEvent.MOUSE_ENTERED,  (evt) -> control.requestFocus());
		control.addEventHandler (ScrollEvent.ANY,   		scroll        = scrollEventHandler());
		
		control.getTranslationControl().setListener((dir, step) -> {
			switch(dir) {
			case UP: 		control.setTranslationY(control.getTranslationY() + step);
							break;
			case DOWN:		control.setTranslationY(control.getTranslationY() - step);
							break;
			case LEFT:		control.setTranslationX(control.getTranslationX() - step);
							break;
			case RIGHT:		control.setTranslationX(control.getTranslationX() + step);
							break;
			case CENTER:	control.setTranslationX(0);
							control.setTranslationY(0);
							break;
			}
		});
	}

	protected double getSpeedModifier(KeyEvent event) {
		return event.isShiftDown() ? 100.0 : 1.0;
	}

	protected EventHandler<KeyEvent>   keyEventHandler() {
		return (evt) -> {
			int mode = evt.getEventType() == KeyEvent.KEY_PRESSED ? 1 : (evt.getEventType() == KeyEvent.KEY_RELEASED) ? 0 : -1;
			if(mode == -1)
				return ;

			float step = 0.5f;
			switch(evt.getCode()) {
			case Z: 		control.setTranslationY(control.getTranslationY() + step);
							break;
			case S: 		control.setTranslationY(control.getTranslationY() - step);
							break;
			case Q: 		control.setTranslationX(control.getTranslationX() - step);
							break;
			case D: 		control.setTranslationX(control.getTranslationX() + step);
							break;
			case A: 		control.setRotation(control.getRotation() - step);
							break;
			case E: 		control.setRotation(control.getRotation() + step);
							break;
			default : 		break;
			}
		};
	}
	protected EventHandler<ScrollEvent> scrollEventHandler() {
		return (evt) -> {
			float step = 0.1f;
			
			if(evt.getDeltaY() > 0) {
				control.setRotation(control.getRotation() + step);
			} else {
				control.setRotation(control.getRotation() - step);
			}
		};
		
	}
	
	
	
	/*
	protected Optional<EventHandler<MouseEvent>> mouseEventHandler() {
		return Optional.of((MouseEvent t) -> {
			if(camera == null)
				return ;
	
			if(t.getEventType() == MouseEvent.MOUSE_PRESSED) {
				switch(t.getButton()) {
				case PRIMARY : 		handleLeftMousePress(t); break;
				case MIDDLE : 		handleMiddleMousePress(t); break;
				case SECONDARY : 	handleRightMousePress(t); break;
				default : 			throw new AssertionError();
				}
				handleMousePress(t);
			} else if(t.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				Point2D d = getMouseDelta(t);
	
				switch(t.getButton()) {
				case PRIMARY :		handleLeftMouseDrag(t, d, speed); break;
				case MIDDLE : 		handleMiddleMouseDrag(t, d, speed); break;
				case SECONDARY :	handleRightMouseDrag(t, d, speed); break;
				default :			throw new AssertionError();
				}
			} else if(t.getEventType() == MouseEvent.MOUSE_MOVED) {
				handleMouseMoved(t, getMouseDelta(t), speed);
			} else if(t.getEventType() == MouseEvent.MOUSE_ENTERED) {
				if(viewport != null)
					viewport.requestFocus();
			} else if(t.getEventType() == MouseEvent.MOUSE_CLICKED) {
				switch(t.getButton()) {
				case PRIMARY :  	handleLeftMouseReleased(t); break;
				case MIDDLE :		handleMiddleMouseReleased(t); break;
				case SECONDARY :	handleRightMouseReleased(t); break;
				default: 			throw new AssertionError();
				}
			}
	
			camera.lookAt(new Point3D.Double(0, 0, 0), new Vector3D.Double(0, 0, -1));
		});
	}
	
	
	// Mouse Controller
	// ================
	private void handleMousePress(MouseEvent event) {
		resetMouse(event);
		event.consume();
	}

	private void handleLeftMousePress(MouseEvent e) {
		// do nothing for now
	}
	private void handleLeftMouseReleased(MouseEvent t) {
		// do nothing for now
	}
	private void handleLeftMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		if(camera == null)
			return ;

		this.camera.strafe((float) (- dragDelta.getX()));
		this.camera.raise((float) (- dragDelta.getY()));
	}

	private void handleMiddleMousePress(MouseEvent e) {
		// do nothing for now
	}
	private void handleMiddleMouseReleased(MouseEvent t) {
		// do nothing for now
	}
	private void handleMiddleMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		// do nothing for now
	}

	private void handleRightMousePress(MouseEvent e) {
		// do nothing for now
	}
	private void handleRightMouseReleased(MouseEvent t) {
		// do nothing for now
	}
	private void handleRightMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		// do nothing for now
	}
	
	private void handleMouseMoved(MouseEvent event, Point2D moveDelta, double speed) {
		// do nothing for now
	}
*/
}
