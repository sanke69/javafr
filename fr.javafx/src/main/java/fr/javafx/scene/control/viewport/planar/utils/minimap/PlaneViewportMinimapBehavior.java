package fr.javafx.scene.control.viewport.planar.utils.minimap;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Points;
import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.Visual;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PlaneViewportMinimapBehavior implements Behavior<PlaneViewportMinimapControl> {
	final private PlaneViewportMinimapControl control;

	private EventHandler<? super KeyEvent>    keyboard;
	private EventHandler<? super MouseEvent>  mouseClicked;
	private EventHandler<? super MouseEvent>  mousePressed;
	private EventHandler<? super MouseEvent>  mouseMoved;
	private EventHandler<? super MouseEvent>  mouseDragging;
	private EventHandler<? super MouseEvent>  mouseEntered;
	private EventHandler<? super ScrollEvent> scrolling;
	
	/** *************************** **\
	 *                               *
	 *         CANVAS MOTION         *
	 *                               *
	\** *************************** **/
	public boolean crtlDown, AltDown, ShiftDown;
	public Point2D mouse;
	public Point2D anchorPosition;

	public boolean isDragging  = true;

	public PlaneViewportMinimapBehavior(PlaneViewportMinimapControl _control) {
		super();
		control = _control;
		enable();
	}
	public PlaneViewportMinimapBehavior(PlaneViewportMinimapControl _control, Visual<PlaneViewportMinimapControl> _visual) {
		super();
		control = _control;
		enable();
	}

	public PlaneViewportMinimapControl			 		getControl() {
		return control;
	}

	public void								 enable() {
		if(keyboard != null)
			return ;

		control.addEventHandler	(KeyEvent.KEY_PRESSED, 		keyboard      = keyboardHandler());
		control.addEventHandler	(MouseEvent.MOUSE_CLICKED, 	mouseClicked  = mouseClickHandler());
		control.addEventHandler	(MouseEvent.MOUSE_PRESSED, 	mousePressed  = mousePressedHandler());
		control.addEventHandler	(MouseEvent.MOUSE_MOVED, 	mouseMoved    = mouseMovedHandler());
		control.addEventHandler	(MouseEvent.MOUSE_DRAGGED, 	mouseDragging = mouseDragHandler());
		control.addEventHandler (MouseEvent.MOUSE_ENTERED,  mouseEntered  = mouseEnteredHandler());
		control.addEventHandler (ScrollEvent.ANY,   		scrolling     = scrollHandler());
	}
	@Override
	public void 							 dispose() {
		control.removeEventHandler(KeyEvent.KEY_PRESSED,     keyboard);
		control.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
		control.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressed);
		control.removeEventHandler(MouseEvent.MOUSE_MOVED,   mouseMoved); 
		control.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragging);
		control.removeEventHandler(MouseEvent.MOUSE_ENTERED, mouseEntered);
		control.removeEventHandler(ScrollEvent.ANY, 		 scrolling);  
		
		keyboard      = null;
		mousePressed  = null;
		mouseMoved    = null;
		mouseDragging = null;
		mouseEntered  = null;
		scrolling     = null;
	}

	public EventHandler<? super KeyEvent>    keyboardHandler() {
		return e -> {
			if (e.isConsumed())
				return;
/*
			switch(e.getCode()) {
			case A			:	control.viewportProperty().fitModelToWindow();
								control.requestRefresh();
								break;
			case Z			: 	control.viewportProperty().fitWindowToModel();
								control.requestRefresh();
								break;
			case F			: 	break;
			case SPACE		: 	break;

			case UP         :  	control.viewportProperty().translateView( 0, -1); break;
			case DOWN       :  	control.viewportProperty().translateView( 0,  1); break;
			case LEFT       :  	control.viewportProperty().translateView(-1,  0); break;
			case RIGHT      :  	control.viewportProperty().translateView( 1,  0); break;

			case ADD		: 	control.fpsProperty().set(e.isAltDown() ? 60 : control.fpsProperty().get() + 1); break;
			case SUBTRACT	: 	control.fpsProperty().set(e.isAltDown() ?  0 : control.fpsProperty().get() - 1); break;
			default			: 	return;
			}*/
			e.consume();
		};
	}
	public EventHandler<? super MouseEvent>  mouseEnteredHandler() {
		return e -> control.requestFocus();
	}
	public EventHandler<? super MouseEvent>  mousePressedHandler() {
		return e -> { 
			if(e.getButton() == MouseButton.SECONDARY) {
//				anchorPosition = control.getViewport().windowInView(Point2D.of(e.getX(), e.getY()));
				anchorPosition = Points.of(e.getX(), e.getY());
			}
		};
	}
	public EventHandler<? super MouseEvent>  mouseMovedHandler() {
		return e -> mouse = Points.of(e.getX(), e.getY());
	}
	public EventHandler<? super MouseEvent>  mouseClickHandler() {
		return e -> {
			double rw = (control.getViewport().getViewBounds().getWidth() -  (control.getViewport().getEdges().getLeft() + control.getViewport().getEdges().getRight()) ) / control.getWidth();
			double rh = (control.getViewport().getViewBounds().getHeight() - (control.getViewport().getEdges().getTop()  + control.getViewport().getEdges().getBottom()) ) / control.getHeight();

			if (e.getButton() == MouseButton.PRIMARY) {
//				control.viewportProperty().translateView(e.getX() * rw, e.getY() * rh);
				e.consume();
			}
		};
	}
	public EventHandler<? super MouseEvent>  mouseDragHandler() {
		return e -> {
			double rw = control.getWidth()  / control.getViewport().getViewBounds().getWidth();
			double rh = control.getHeight() / control.getViewport().getViewBounds().getHeight();

			if (e.getButton() == MouseButton.SECONDARY) {
				if (anchorPosition != null) {
					if (!isDragging)
						control.setCursor(Cursor.MOVE);
//					control.viewportProperty().translateView(e.getX() * rw, e.getY() * rh);
					anchorPosition = Points.of(e.getX(), e.getY());
					e.consume();
				}
				isDragging = true;
			}
			mouse = Points.of(e.getX(), e.getY());
		};
	}
	public EventHandler<? super ScrollEvent> scrollHandler() {
		return e -> {
			/*
			if(e.isControlDown()) {
				Point2D oldAP  = control.getViewport().getViewAnchor().clone();
				control.getViewport().scaleView(e.getDeltaY() < 0, e.getX(), e.getY());
				if (anchorPosition != null) {
					Point2D newAP  = control.getViewport().getViewAnchor().clone();
					anchorPosition = new Point2D.Double(anchorPosition.getX() - oldAP.getX() + newAP.getX(), 
							 						    anchorPosition.getY() - oldAP.getY() + newAP.getY());
				}
				e.consume();
			}
			*/
		};
	}

}
