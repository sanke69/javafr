package fr.javafx.scene.control.editor;

import java.util.LinkedList;

import fr.javafx.scene.control.editor.skin.BooleanEditorCheckBoxSkin;
import fr.javafx.scene.control.editor.skin.BooleanEditorToggleButtonSkin;
import fr.javafx.scene.properties.Editor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;

public class BooleanEditor extends Control implements Editor<Boolean> {
	public static enum Style { ToggleButton, CheckBox; }

	private BooleanProperty value;
	private Style 			style;

	public BooleanEditor() {
		this(false, Style.CheckBox);
	}
	public BooleanEditor(boolean _initialValue) {
		this(_initialValue, Style.CheckBox);
	}
	public BooleanEditor(Style _style) {
		this(false, _style);
	}
	public BooleanEditor(boolean _initialValue, Style _style) {
		super();
		style = _style;
		value = new SimpleBooleanProperty(_initialValue);

		plugins.addListener(pluginsChanged);
	}

	@Override
	protected Skin<?> 		createDefaultSkin() {
		switch(style) {
		default:			
		case CheckBox:		return new BooleanEditorCheckBoxSkin(this);
		case ToggleButton:	return new BooleanEditorToggleButtonSkin(this);
		}
	}

	@Override
	public Region 			getNode() {
		return this;
	}

	public void 			setValue(Boolean _value) {
		value.set(_value != null ? _value : false);
	}
	public Boolean 			getValue() {
		return value.get();
	}
	@Override
	public BooleanProperty 	valueProperty() {
		return value;
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
