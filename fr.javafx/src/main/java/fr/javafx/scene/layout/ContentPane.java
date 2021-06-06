package fr.javafx.scene.layout;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;

public abstract class ContentPane extends Pane {
	private Property<Pane> contentPaneProperty;

	protected ContentPane() {
		super();
		contentPaneProperty = new SimpleObjectProperty<>();
	}

	public Property<Pane> 	contentPaneProperty() {
		return contentPaneProperty;
	}
	public void 			setContentPane(Pane contentPane) {
		contentPaneProperty.setValue(contentPane);
	}
	public Pane 			getContentPane() {
		return contentPaneProperty.getValue();
	}

}
