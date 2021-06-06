package fr.javafx.scene.control.editor.skin;

import fr.javafx.scene.control.editor.BooleanEditor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Skin;

public class BooleanEditorCheckBoxSkin extends CheckBox implements Skin<BooleanEditor> {
	private BooleanEditor editor;

	public BooleanEditorCheckBoxSkin(BooleanEditor _editor) {
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
