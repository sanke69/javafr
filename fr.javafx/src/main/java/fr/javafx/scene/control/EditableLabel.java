package fr.javafx.scene.control;

import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EditableLabel extends Label {

	private final TextField editableArea;
	
	public EditableLabel() {
		super();
		editableArea = new TextField();
		editableArea.setOnKeyReleased(onEndOfEdition());

		setGraphic(editableArea);
		setContentDisplay(ContentDisplay.TEXT_ONLY);
		
		setOnMouseClicked((evt) -> setEditable(true));
	}
	public EditableLabel(String _text) {
		super(_text);
		editableArea = new TextField();
	}

	public TextField getEditor() {
		return editableArea;
	}

	public void setEditable(boolean _enable) {
		if(_enable) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

			editableArea.setText(getText());
			editableArea.setTranslateX(- getGraphicTextGap() - 1);
			editableArea.setTranslateY(- 6);
			editableArea.requestFocus();
			editableArea.selectAll();
		} else {
			setContentDisplay(ContentDisplay.TEXT_ONLY);
		}
	}

	public EventHandler<KeyEvent> onEndOfEdition() {
		return (KeyEvent t) -> {
			switch(t.getCode()) {
			case ENTER  : 	if(editableArea.getText() != null && editableArea.getText().equals(""))
								setText(editableArea.getText());
			case ESCAPE : 	setContentDisplay(ContentDisplay.TEXT_ONLY);
			default     : 	break;
			}
		};
	}

}
