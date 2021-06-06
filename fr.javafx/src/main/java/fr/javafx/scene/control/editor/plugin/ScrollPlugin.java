package fr.javafx.scene.control.editor.plugin;

import fr.javafx.scene.control.editor.NumberEditor;
import fr.javafx.scene.properties.Editor;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class ScrollPlugin implements Editor.Plugin, EventHandler<ScrollEvent> {
	private Number       step;
	private NumberEditor editor;

	public ScrollPlugin() {
		this(1);
	}
	public ScrollPlugin(Number _step) {
		super();

		step = _step;
	}

	@Override
	public void setEditor(Editor<?> _editor) {
		if(_editor instanceof NumberEditor)
			setEditor((NumberEditor) _editor);
		else if(_editor == null)
			unsetEditor();
	}

	public void setEditor(NumberEditor _editor) {
		editor = _editor;
		editor . getNode().addEventFilter(ScrollEvent.ANY, this);
	}
	public void unsetEditor() {
		editor . getNode().removeEventFilter(ScrollEvent.ANY, this);
		editor = null;
	}
	
	public void handle(ScrollEvent scrollEvent) {
		if(scrollEvent.getDeltaY() < 0) {
			editor.valueProperty().set(minus(editor.getValue(), step));
			scrollEvent.consume();
		}
		if(scrollEvent.getDeltaY() > 0) {
			editor.valueProperty().set(plus(editor.getValue(), step));
			scrollEvent.consume();
		}
	}
	
	public static Number plus(Number value, Number _step) {
	    if(value instanceof Double || _step instanceof Double) {
	        return value.doubleValue() + _step.doubleValue();
	    } else if(value instanceof Float || _step instanceof Float) {
	        return value.floatValue() + _step.floatValue();
	    } else if(value instanceof Long || _step instanceof Long) {
	        return value.longValue() + _step.longValue();
	    } else {
	        return value.intValue() + _step.intValue();
	    }
	}
	public static Number minus(Number value, Number _step) {
	    if(value instanceof Double || _step instanceof Double) {
	        return value.doubleValue() - _step.doubleValue();
	    } else if(value instanceof Float || _step instanceof Float) {
	        return value.floatValue() - _step.floatValue();
	    } else if(value instanceof Long || _step instanceof Long) {
	        return value.longValue() - _step.longValue();
	    } else {
	        return value.intValue() - _step.intValue();
	    }
	}

}
