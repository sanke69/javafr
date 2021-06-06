package fr.javafx.sdk.controls.service;

import java.lang.reflect.InvocationTargetException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import fr.java.patterns.service.Service;
import fr.javafx.sdk.controls.service.actions.StatusActionControl;
import fr.javafx.sdk.controls.service.skins.DefaultServiceControlSkin;

public class ServiceControl<S extends Service> extends Control {

	public static enum Style {
		Chip,
		Node, 
		Pane	// TitledPane
	}
	public static enum Mode {
		Info, 	// Label with Icon
		Action	// Label with Button
	}

	Class<? extends Skin<? extends ServiceControl<S>>> skiner;

	S      service;
	String title;
	
	StringProperty messageProperty;
	public ObservableList<ServiceActionControl> leftButtons;
	public ObservableList<ServiceActionControl> rightButtons;

	Style  type;
	Mode   mode;

	public ServiceControl(S _service) {
		this(_service, _service.getName(), Style.Node, Mode.Info, null);
	}
	public ServiceControl(S _service, Style _type, Mode _mode) {
		this(_service, _service.getName(), _type, _mode, null);
	}
	public ServiceControl(S _service, Class<? extends Skin<? extends ServiceControl<S>>> _skiner) {
		this(_service, _service.getName(), Style.Node, Mode.Info, null);
	}
	public ServiceControl(S _service, Style _type, Mode _mode, Class<? extends Skin<? extends ServiceControl<S>>> _skiner) {
		this(_service, _service.getName(), _type, _mode, _skiner);
	}
	public ServiceControl(S _service, String _title) {
		this(_service, _title, Style.Node, Mode.Info, null);
	}
	public ServiceControl(S _service, String _title, Style _type, Mode _mode) {
		this(_service, _title, _type, _mode, null);
	}
	public ServiceControl(S _service, String _title, Class<? extends Skin<? extends ServiceControl<S>>> _skiner) {
		this(_service, _title, Style.Node, Mode.Info, _skiner);
	}
	public ServiceControl(S _service, String _title, Style _type, Mode _mode, Class<? extends Skin<? extends ServiceControl<S>>> _skiner) {
		super();
		service      = _service;
		title        = _title;

		type         = _type;
		mode         = _mode;
		skiner       = _skiner;

		messageProperty = new SimpleStringProperty();

		leftButtons  = FXCollections.observableArrayList();
		rightButtons = FXCollections.observableArrayList();
		
		getRightButtons().add(new StatusActionControl(this));
	}

	@Override
	protected Skin<? extends ServiceControl<S>> createDefaultSkin() {
		if(skiner != null)
			try {
				return skiner.getConstructor(this.getClass()).newInstance(this);
			} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

		return new DefaultServiceControlSkin<S, ServiceControl<S>>(this);
		/*
		if(type == null)
			return new DefaultServiceControlSkinNode<S, ServiceControl<S>>(this);

		switch(type) {
		case Pane:	return new DefaultServiceControlSkinPane<S, ServiceControl<S>>(this);
		case Node:
		case Chip:
		default:	return new DefaultServiceControlSkinNode<S, ServiceControl<S>>(this);
		}
		*/
	}

	public S 									getService() {
		return service;
	}
	public String 								getTitle() {
		return title;
	}

	public Style 								getType() {
		return type;
	}
	public Mode 								getMode() {
		return mode;
	}

	public StringProperty 						messageProperty() {
		return messageProperty;
	}
	public void	 								setMessage(String _msg) {
		messageProperty.set(_msg);
	}

	public ObservableList<ServiceActionControl> getLeftButtons() {
		return leftButtons;
	}
	public ObservableList<ServiceActionControl> getRightButtons() {
		return rightButtons;
	}

}
