package fr.javafx.scene.control.editor;

import java.util.LinkedList;

import fr.javafx.scene.control.editor.skin.TextEditorSkin;
import fr.javafx.scene.properties.Editor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class TextEditor extends TextField implements Editor<String> {
    private ObjectProperty<Node> left  = new SimpleObjectProperty<>(this, "left");
    private ObjectProperty<Node> right = new SimpleObjectProperty<>(this, "right");
    
    public TextEditor() {
        getStyleClass().add("custom-text-field");

		plugins.addListener(pluginsChanged);
    }

	@Override
	public Region 						getNode() {
		return this;
	}

	@Override
	public ObservableValue<String> 		valueProperty() {
		return textProperty();
	}

    public final ObjectProperty<Node> 	leftProperty() {
        return left;
    }
    public final Node 					getLeft() {
        return left.get();
    }
    public final void 					setLeft(Node value) {
        left.set(value);
    }

    public final ObjectProperty<Node> 	rightProperty() {
        return right;
    }
    public final Node 					getRight() {
        return right.get();
    }
    public final void 					setRight(Node value) {
        right.set(value);
    }

    @Override protected Skin<?> 		createDefaultSkin() {
        return new TextEditorSkin(this) {
            @Override public ObjectProperty<Node> leftProperty() { return TextEditor.this.leftProperty(); }
            @Override public ObjectProperty<Node> rightProperty() { return TextEditor.this.rightProperty(); }
        };
    }

    @Override public String 			getUserAgentStylesheet() {
        return TextEditor.class.getResource("TextEditor.css").toExternalForm();
    }

	/*** PlugIns ***/
	private final ObservableList<Plugin> 	 plugins        = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<Plugin> pluginsChanged = (change) -> {
		while(change.next()) {
			change.getRemoved().forEach(this::pluginRemoved);
			change.getAddedSubList().forEach(this::pluginAdded);
		}
	};

	public final ObservableList<Plugin> 	getPlugins() {
		return plugins;
	}

	private void 							pluginAdded(Plugin _plugin) {
		_plugin.setEditor(this);
	}
	private void 							pluginRemoved(Plugin _plugin) {
		_plugin.setEditor(null);
	}

}
