package fr.javafx.scene.control.editor.skin;

import fr.javafx.scene.control.editor.BooleanEditor;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.ToggleButton;

public class BooleanEditorToggleButtonSkin extends ToggleButton implements Skin<BooleanEditor> {
	private BooleanEditor editor;

	public BooleanEditorToggleButtonSkin(BooleanEditor _editor) {
		super();
		editor = _editor;
		editor . valueProperty().bind(selectedProperty());
	}

	@Override
	public BooleanEditor getSkinnable() {
		return editor;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {

	}
	
}
