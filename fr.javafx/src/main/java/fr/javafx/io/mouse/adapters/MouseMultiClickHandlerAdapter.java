package fr.javafx.io.mouse.adapters;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseMultiClickHandlerAdapter {
	public static final long MULTICLICK_DELAY  = 200;
	public static final long MULTICLICK_PERIOD =  10;

	Node 			node;
	long 		  	lastTime    = -1;
	Service<Void> 	nextService = null;

	public MouseMultiClickHandlerAdapter(Node _node) {
		super();
		node = _node;
		initialize();
	}

	public void initialize() {
		node.addEventHandler(MouseEvent.MOUSE_ENTERED,  this::onMouseEntered);
		node.addEventHandler(MouseEvent.MOUSE_EXITED,   this::onMouseExited);
		node.addEventHandler(MouseEvent.MOUSE_MOVED,    this::onMouseMoved);

		node.addEventHandler(MouseEvent.MOUSE_PRESSED,  this::onMousePressed);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);

		node.addEventHandler(MouseEvent.DRAG_DETECTED,  this::onDragDetected);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED,  this::onMouseDragged);
	}
	public void finalize() {
		node.removeEventHandler(MouseEvent.MOUSE_ENTERED,  this::onMouseEntered);
		node.removeEventHandler(MouseEvent.MOUSE_EXITED,   this::onMouseExited);
		node.removeEventHandler(MouseEvent.MOUSE_MOVED,    this::onMouseMoved);

		node.removeEventHandler(MouseEvent.MOUSE_PRESSED,  this::onMousePressed);
		node.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);

		node.removeEventHandler(MouseEvent.DRAG_DETECTED,  this::onDragDetected);
		node.removeEventHandler(MouseEvent.MOUSE_DRAGGED,  this::onMouseDragged);
	}

	public void onMouseEntered(MouseEvent _me) {
		lastTime      = -1;
		nextService   = null;
	}
	public void onMouseExited(MouseEvent _me) {
		lastTime      = -1;
		nextService   = null;
	}
	public void onMouseMoved(MouseEvent _me) {
		
	}

	public void onMousePressed(MouseEvent _me) {
//		boolean multiClick = System.currentTimeMillis() - lastTime <= MULTICLICK_DELAY ? true : false;
		lastTime = System.currentTimeMillis();

		nextService = new Service<Void>() {
    		final long 			taskTime   = lastTime;
    		final MouseEvent 	taskEvent  = _me;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                		while( taskTime == lastTime && System.currentTimeMillis() - taskTime < MULTICLICK_DELAY)
                			Thread.sleep(MULTICLICK_PERIOD);

                		if(taskTime != lastTime) {
                			cancel();
                			return null;
                		}

                		processClick(taskEvent);

                        return null;
                    }
                };
            }
        };
		
	}
	public void onMouseReleased(MouseEvent _me) {
//		lastTime = System.currentTimeMillis();

		if(nextService != null) {
			nextService.start();
			nextService.stateProperty().addListener((_obs, _old, _new) -> {
	            switch(_new) {
	            case FAILED:	break;
	            case CANCELLED:	break;
	            case SUCCEEDED:	break;
	            }
	        });
			nextService = null;
		}
	}

	public void onDragDetected(MouseEvent _me) {
		
	}
	public void onMouseDragged(MouseEvent _me) {
		
	}

	private void processClick(MouseEvent _me) {
		switch(_me.getButton()) {
		case PRIMARY:	switch(_me.getClickCount()) {
						case 1  : onPrimarySingleClick(_me); break;
						case 2  : onPrimaryDoubleClick(_me); break;
						case 3  : onPrimaryTripleClick(_me); break;
						default : onPrimaryCompulsiveClick(_me); break;
						}
						break;
		case MIDDLE:	switch(_me.getClickCount()) {
						case 1  : onMiddleSingleClick(_me); break;
						case 2  : onMiddleDoubleClick(_me); break;
						case 3  : onMiddleTripleClick(_me); break;
						default : onMiddleCompulsiveClick(_me); break;
						}
						break;
		case SECONDARY:	switch(_me.getClickCount()) {
						case 1  : onSecondarySingleClick(_me); break;
						case 2  : onSecondaryDoubleClick(_me); break;
						case 3  : onSecondaryTripleClick(_me); break;
						default : onSecondaryCompulsiveClick(_me); break;
						}
						break;
/* TODO:: Since JFX 12+
		case BACK:		switch(_me.getClickCount()) {
						case 1  : System.out.println("Back Single Click"); break;
						case 2  : System.out.println("Back Double Click"); break;
						case 3  : System.out.println("Back Triple Click"); break;
						default : System.out.println("Back Keep Cool Guy !!!"); break;
						}
						break;
		case FORWARD:	switch(_me.getClickCount()) {
						case 1  : System.out.println("Forward Single Click"); break;
						case 2  : System.out.println("Forward Double Click"); break;
						case 3  : System.out.println("Forward Triple Click"); break;
						default : System.out.println("Forward Keep Cool Guy !!!"); break;
						}
						break;
*/
		case NONE:
		default:
						break;
		}
	}

	boolean debug = false;
	public void onPrimarySingleClick(MouseEvent _me) 		{ if(debug) System.out.println("Primary Single Click"); }
	public void onPrimaryDoubleClick(MouseEvent _me) 		{ if(debug) System.out.println("Primary Double Click"); }
	public void onPrimaryTripleClick(MouseEvent _me) 		{ if(debug) System.out.println("Primary Triple Click"); }
	public void onPrimaryCompulsiveClick(MouseEvent _me) 	{ if(debug) System.out.println("Primary Keep Cool Guy !!!"); }

	public void onMiddleSingleClick(MouseEvent _me) 		{ if(debug) System.out.println("Middle Single Click"); }
	public void onMiddleDoubleClick(MouseEvent _me) 		{ if(debug) System.out.println("Middle Double Click"); }
	public void onMiddleTripleClick(MouseEvent _me) 		{ if(debug) System.out.println("Middle Triple Click"); }
	public void onMiddleCompulsiveClick(MouseEvent _me) 	{ if(debug) System.out.println("Middle Keep Cool Guy !!!"); }

	public void onSecondarySingleClick(MouseEvent _me) 		{ if(debug) System.out.println("Secondary Single Click"); }
	public void onSecondaryDoubleClick(MouseEvent _me) 		{ if(debug) System.out.println("Secondary Double Click"); }
	public void onSecondaryTripleClick(MouseEvent _me) 		{ if(debug) System.out.println("Secondary Triple Click"); }
	public void onSecondaryCompulsiveClick(MouseEvent _me) 	{ if(debug) System.out.println("Secondary Keep Cool Guy !!!"); }

}
