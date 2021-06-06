package fr.javafx.scene.control.raster;

import fr.java.math.topology.Coordinate;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.scene.control.viewport.plugins.PlaneViewportBehavior;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class RasterViewportBehavior<MODEL, COORD extends Coordinate.TwoDims> extends PlaneViewportBehavior<MODEL, COORD> {
	private EventHandler<? super KeyEvent>    	keyPressed,   keyReleased;
	private EventHandler<? super MouseEvent>  	mousePressed, mouseReleased;
	private EventHandler<? super MouseEvent>  	mouseMoved;
	private EventHandler<? super MouseEvent>  	mouseDragging;
	private EventHandler<? super MouseEvent>  	mouseEntered;
	private EventHandler<? super ScrollEvent> 	scrolling;

	public RasterViewportBehavior() {
		super();
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

	public EventHandler<? super KeyEvent>    keyPressedHandler4Sizing() {
		return e -> {
			if (e.isConsumed())
				return;

			switch(e.getCode()) {
			case A			:	control.fitContentToControl();
								break;
			case Z			: 	control.fitToContent();
								break;
			case F			: 	if(e.isControlDown())
									control.autofitToContent(!control.autofitToContent());
								else
									control.autofitContentToControl(!control.autofitContentToControl());
								break;
			case R			: 	if(e.isControlDown())
									control.setFixedSize();
								else
									control.unsetFixedSize();
								break;
			case Q			: 	control.reset();
								break;
			case SPACE		: 	break;

			case UP         :  	control.translate( 0, -1); break;
			case DOWN       :  	control.translate( 0,  1); break;
			case LEFT       :  	control.translate(-1,  0); break;
			case RIGHT      :  	control.translate( 1,  0); break;

			case ADD		: 	control.preferredFpsProperty().set(e.isAltDown() ? 60 : control.preferredFpsProperty().get() + 1); break;
			case SUBTRACT	: 	control.preferredFpsProperty().set(e.isAltDown() ?  0 : control.preferredFpsProperty().get() - 1); break;
			default			: 	return;
			}
			e.consume();
		};
	}

}
