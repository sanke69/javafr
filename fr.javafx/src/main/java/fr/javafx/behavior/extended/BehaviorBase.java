package fr.javafx.behavior.extended;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.Visual;
import fr.javafx.behavior.extended.bindings.Bindings;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.behavior.extended.bindings.MouseBinding;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding;
import javafx.beans.InvalidationListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class BehaviorBase<C extends Control, V extends Visual<C>, B extends BehaviorBase<C, V, B>> implements Behavior<C> {
	private final C           				control;
	private final V           				visual;

    private final InvalidationListener 		focusListener   = focus -> focusChanged();

    private final boolean					useBINDINGS;
    private final boolean 					useCONTROL      = false;
    private final boolean 					useANY          = false;

	private /*final*/ Bindings<B> 			inputBindings;
	private final EventHandler<Event> 		inputHandler    = this::fireBindings;

    private /*final*/ List<KeyBinding<B>> 	keyBindings;
    private final EventHandler<KeyEvent> 	keyHandler      = this::handleKeyEvent;
    private /*final*/ List<MouseBinding<B>> mouseBindings;
    private final EventHandler<MouseEvent>  mouseHandler    = this::handleMouseEvent;

	public BehaviorBase(final C _control) {
		this(_control, null, null, null);

	}
	public BehaviorBase(final C _control, final Bindings<B> _bindings) {
    	super();
		Objects.requireNonNull(_control, "Control cannot be null");

    	useBINDINGS   = true;

		control       = _control;
		visual        = null;

		inputBindings = _bindings;
        keyBindings   = null;
        mouseBindings = null;

        control.focusedProperty().addListener(focusListener);
        attachInputHandlers();
    }
	public BehaviorBase(final C _control, final List<? extends KeyBinding<B>> _keyBindings, final List<? extends MouseBinding<B>> _mouseBindings) {
    	super();
		Objects.requireNonNull(_control, "Control cannot be null");

    	useBINDINGS   = true;

		control       = _control;
		visual        = null;

		inputBindings = null;
        keyBindings   = _keyBindings   == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(_keyBindings));
		mouseBindings = _mouseBindings == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(_mouseBindings));

        control.focusedProperty().addListener(focusListener);
        attachKeyboardHandlers();
        attachMouseHandlers();
    }
	public BehaviorBase(final C _control, final V _visual) {
		this(_control, _visual, null);

	}
	public BehaviorBase(final C _control, final V _visual, final Bindings<B> _bindings) {
    	super();
		Objects.requireNonNull(_control, "Control cannot be null");

    	useBINDINGS   = true;

		control       = _control;
		visual        = _visual;

		inputBindings = _bindings;
        keyBindings   = null;
        mouseBindings = null;

        control.focusedProperty().addListener(focusListener);
        attachInputHandlers();
    }
	public BehaviorBase(final C _control, final V _visual, final List<? extends KeyBinding<B>> _keyBindings, final List<? extends MouseBinding<B>> _mouseBindings) {
    	super();
		Objects.requireNonNull(_control, "Control cannot be null");

    	useBINDINGS   = true;

		control       = _control;
		visual        = _visual;

		inputBindings = null;
        keyBindings   = _keyBindings   == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(_keyBindings));
		mouseBindings = _mouseBindings == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(_mouseBindings));

        control.focusedProperty().addListener(focusListener);
        attachKeyboardHandlers();
        attachMouseHandlers();
    }

	public final C 				getControl() {
		return control;
	}
	public final V 				getVisual() {
		return visual;
	}

    protected void 				focusChanged() { }

    public void 				onKeyPressed(KeyEvent e)    { }
    public void 				onKeyReleased(KeyEvent e)   { }
    public void 				onKeyTyped(KeyEvent e)      { }

    public void 				onMousePressed(MouseEvent e)  { }
    public void 				onMouseDragged(MouseEvent e)  { }
    public void 				onMouseReleased(MouseEvent e) { }
    public void 				onMouseEntered(MouseEvent e)  { }
    public void 				onMouseExited(MouseEvent e)   { }

	private void 				fireBindings(Event event) {
		if(!event.isConsumed() && inputBindings != null)
			inputBindings.fire(event, (B) this);
	}
	private void 				handleKeyEvent(KeyEvent _keyEvent) {
    	if(!_keyEvent.isConsumed())
    		callActionForEvent(_keyEvent);
	}
	private void 				handleMouseEvent(MouseEvent _mouseEvent) {
    	if(_mouseEvent.isConsumed())
    		return ;

        final EventType<?> type = _mouseEvent.getEventType();

        if(type == MouseEvent.MOUSE_ENTERED)			onMouseEntered(_mouseEvent);
        else if(type == MouseEvent.MOUSE_EXITED)		onMouseExited(_mouseEvent);
        else if(type == MouseEvent.MOUSE_PRESSED)		onMousePressed(_mouseEvent);
        else if(type == MouseEvent.MOUSE_RELEASED)		onMouseReleased(_mouseEvent);
        else if(type == MouseEvent.MOUSE_DRAGGED)		onMouseDragged(_mouseEvent);
        else											throw new AssertionError("Unsupported event type received");
	}

	public void 				dispose() {
        control.focusedProperty().removeListener(focusListener);

        if(useBINDINGS)
	        detachInputHandlers();
        else {
	        detachKeyboardHandlers();
	        detachMouseHandlers();
        }
	}

    protected void 				callActionForEvent(KeyEvent e) {
        String action = matchActionForEvent(e);
        if (action != null) {
            callAction(action);
            e.consume();
        }
    }

    protected String 			matchActionForEvent(final KeyEvent e) {
        if (e == null) throw new NullPointerException("KeyEvent must not be null");
        
        // Find better match between event context and key binding
        int          specificity = 0;
        KeyBinding<B> match       = null;

        /*
        int maxBindings = keyBindings.size();
        for (int i = 0; i < maxBindings; i++) {
            SimpleKeyBinding binding = keyBindings.get(i);
            int s = binding.getSpecificity(control, e);
            if (s > specificity) {
                specificity = s;
                match = binding;
            }
        }
        */

        for(KeyBinding<B> kb : keyBindings) {
            int s = kb.getSpecificity(control, e);
            if(s > specificity) {
                specificity = s;
                match       = kb;
            }
        }

        String actionName = null;
        if(match != null && match instanceof SimpleKeyBinding)
        	actionName = ((SimpleKeyBinding) match).getActionName();

        return actionName;
    }
    protected void 				callAction(String name) { }

    public void 				contextMenuRequested(ContextMenuEvent e) { }

    private void 				attachInputHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null)
        	controlSkin.addEventHandler(InputEvent.ANY,  					inputHandler);
    }
    private void 				attachKeyboardHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null) {
        	if(useANY)
        		controlSkin.addEventHandler(KeyEvent.ANY,  					keyHandler);
        	else {
		    	controlSkin.addEventHandler(KeyEvent.KEY_PRESSED,  			keyHandler);
		    	controlSkin.addEventHandler(KeyEvent.KEY_RELEASED,   		keyHandler);
		    	controlSkin.addEventHandler(KeyEvent.KEY_TYPED,  			keyHandler);
        	}
        }
    }
    private void 				attachMouseHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null) {
        	if(useANY)
    	    	controlSkin.addEventHandler(MouseEvent.ANY,  				mouseHandler);
            else {
		    	controlSkin.addEventHandler(MouseEvent.MOUSE_ENTERED,  		mouseHandler);
		    	controlSkin.addEventHandler(MouseEvent.MOUSE_EXITED,   		mouseHandler);
		    	controlSkin.addEventHandler(MouseEvent.MOUSE_PRESSED,  		mouseHandler);
		    	controlSkin.addEventHandler(MouseEvent.MOUSE_RELEASED, 		mouseHandler);
		    	controlSkin.addEventHandler(MouseEvent.MOUSE_DRAGGED,  		mouseHandler);
            }
        }
    }

    private void 				detachInputHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null)
        	controlSkin.removeEventHandler(InputEvent.ANY,  				inputHandler);
    }
    private void 				detachKeyboardHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null) {
        	if(useANY)
    	    	controlSkin.removeEventHandler(KeyEvent.ANY,  				keyHandler);
            else {
		    	controlSkin.removeEventHandler(KeyEvent.KEY_PRESSED,  		keyHandler);
		    	controlSkin.removeEventHandler(KeyEvent.KEY_RELEASED,   	keyHandler);
		    	controlSkin.removeEventHandler(KeyEvent.KEY_TYPED,  		keyHandler);
            }
        }
    }
    private void 				detachMouseHandlers() {
    	Node    controlSkin = useCONTROL ? control : visual.getNode();

        if(controlSkin != null) {
        	if(useANY)
        		controlSkin.removeEventHandler(MouseEvent.ANY,  			mouseHandler);
            else {
	        	controlSkin.removeEventHandler(MouseEvent.MOUSE_ENTERED,  	mouseHandler);
	        	controlSkin.removeEventHandler(MouseEvent.MOUSE_EXITED,   	mouseHandler);
	        	controlSkin.removeEventHandler(MouseEvent.MOUSE_PRESSED,  	mouseHandler);
	        	controlSkin.removeEventHandler(MouseEvent.MOUSE_RELEASED, 	mouseHandler);
	        	controlSkin.removeEventHandler(MouseEvent.MOUSE_DRAGGED,  	mouseHandler);
            }
        }
    }

}
