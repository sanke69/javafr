package fr.javafx.beans.propertysheet;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.END;
import static javafx.scene.input.KeyCode.F4;
import static javafx.scene.input.KeyCode.HOME;
import static javafx.scene.input.KeyCode.KP_DOWN;
import static javafx.scene.input.KeyCode.KP_LEFT;
import static javafx.scene.input.KeyCode.KP_RIGHT;
import static javafx.scene.input.KeyCode.KP_UP;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventType;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import fr.javafx.behavior.Visual;
import fr.javafx.behavior.extended.BehaviorBase;
import fr.javafx.behavior.extended.bindings.KeyBinding;
import fr.javafx.behavior.extended.bindings.impl.OrientedKeyBinding;
import fr.javafx.behavior.extended.bindings.impl.SimpleKeyBinding;

public class PropertySheetBehavior extends BehaviorBase<PropertySheet, PropertySheetVisual, PropertySheetBehavior> {

    protected static final List<KeyBinding<PropertySheetBehavior>> PROPERTY_SHEET_BINDINGS = new ArrayList<KeyBinding<PropertySheetBehavior>>();
    static {
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(F4, "TraverseDebug").alt().ctrl().shift());

        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(LEFT, "DecrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(KP_LEFT, "DecrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(UP, "IncrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(KP_UP, "IncrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(RIGHT, "IncrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(KP_RIGHT, "IncrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(DOWN, "DecrementValue"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(KP_DOWN, "DecrementValue"));

        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(HOME, KEY_RELEASED, "Home"));
        PROPERTY_SHEET_BINDINGS.add(new SimpleKeyBinding<PropertySheetBehavior>(END, KEY_RELEASED, "End"));
    }


    public PropertySheetBehavior(PropertySheet _propertySheet) {
        super(_propertySheet, PROPERTY_SHEET_BINDINGS, null);
    }
    public PropertySheetBehavior(PropertySheet _propertySheet, Visual<PropertySheet> _skin) {
        super(_propertySheet, (PropertySheetVisual) _skin, PROPERTY_SHEET_BINDINGS, null);
    }
    public PropertySheetBehavior(PropertySheet _propertySheet, PropertySheetVisual _skin) {
        super(_propertySheet, _skin, PROPERTY_SHEET_BINDINGS, null);
    }

    @Override public void dispose() {
        super.dispose();
    }

    
    
    
    
    
    
    
    
    
    
    
    protected /*final*/ String matchActionForEvent(KeyEvent e) {
        String action = super.matchActionForEvent(e);
        if (action != null) {
        	/*
            if (e.getCode() == LEFT || e.getCode() == KP_LEFT) {
                if (getControl().getEffectiveNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT) {
                    action = getControl().getOrientation() == Orientation.HORIZONTAL ? "IncrementValue" : "DecrementValue";
                }
            } else if (e.getCode() == RIGHT || e.getCode() == KP_RIGHT) {
                if (getControl().getEffectiveNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT) {
                    action = getControl().getOrientation() == Orientation.HORIZONTAL ? "DecrementValue" : "IncrementValue";
                }
            }
            */
        }
        return action;
    }

    @Override
    protected void callAction(String _name) {
    	switch(_name) {
    	
    	default : super.callAction(_name);
    	}
    }

    public static class SliderKeyBinding extends OrientedKeyBinding {
        public SliderKeyBinding(KeyCode code, String action) {
            super(code, action);
        }

        public SliderKeyBinding(KeyCode code, EventType<KeyEvent> type, String action) {
            super(code, type, action);
        }

        public @Override boolean getVertical(Control control) {
            return ((Slider)control).getOrientation() == Orientation.VERTICAL;
        }
    }

}
