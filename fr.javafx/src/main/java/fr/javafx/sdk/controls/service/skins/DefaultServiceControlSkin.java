package fr.javafx.sdk.controls.service.skins;

import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import fr.java.patterns.service.Service;
import fr.javafx.sdk.controls.service.ServiceActionControl;
import fr.javafx.sdk.controls.service.ServiceControl;

public class DefaultServiceControlSkin<S extends Service, SCtrl extends ServiceControl<S>> extends HBox implements Skin<SCtrl> {
	private final SCtrl 	control;
	private final S			service;

	protected final VBox 	infoPane;
	protected final Label 	label, message;

	public DefaultServiceControlSkin(SCtrl _control) {
		super();
		control = _control;
		service = control.getService();

		int nbAction = control.leftButtons.size() + control.rightButtons.size();

		label    = new Label(service.getName());
		message  = new Label();
		message.textProperty().bind(control.messageProperty());

		label   .setAlignment(Pos.CENTER_LEFT);
		message .setAlignment(Pos.CENTER_LEFT);

		label   .setPrefHeight	(ServiceActionControl.ICON_SIZE - 10);
		message .setPrefHeight	(10);

		infoPane = new VBox();
		infoPane.getChildren().addAll(label, message);

		getChildren().addAll(control.leftButtons);
		getChildren().addAll(infoPane);
		getChildren().addAll(control.rightButtons);

		control.leftButtons.addListener((InvalidationListener) (evt) -> {
			getChildren().clear();
			getChildren().addAll(control.leftButtons);
			getChildren().addAll(infoPane);
			getChildren().addAll(control.rightButtons);
		});
		control.rightButtons.addListener((InvalidationListener) (evt) -> {
			getChildren().clear();
			getChildren().addAll(control.leftButtons);
			getChildren().addAll(infoPane);
			getChildren().addAll(control.rightButtons);
		});
		
		
		prefWidthProperty().bind(widthProperty());

//		label   .prefWidthProperty().bind(widthProperty().subtract(ServiceActionControl.ICON_SIZE * nbAction));
//		message .prefWidthProperty().bind(widthProperty().subtract(ServiceActionControl.ICON_SIZE * nbAction));

		setPrefHeight(ServiceActionControl.ICON_SIZE);

	}

	@Override
	public SCtrl getSkinnable() {
		return control;
	}
	public S getService() {
		return getSkinnable().getService();
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		;
	}

}
