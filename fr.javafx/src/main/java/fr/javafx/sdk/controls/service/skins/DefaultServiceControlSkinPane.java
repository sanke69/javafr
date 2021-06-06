package fr.javafx.sdk.controls.service.skins;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import fr.java.patterns.service.Service;
import fr.javafx.sdk.controls.service.ServiceControl;
import fr.javafx.sdk.controls.service.skins.contents.ServiceStateSkinAction;
import fr.javafx.sdk.controls.service.skins.contents.ServiceStateSkinInfo;

public class DefaultServiceControlSkinPane<S extends Service, SCtrl extends ServiceControl<S>> extends VBox implements Skin<SCtrl> {
	private final SCtrl 		control;
	private final S				service;

	private final TitledPane    mainTitledPane;
//	private final ScrollPane    mainScrollPane;
	private final VBox    		mainPane;
	private final HBox 			statusControl;

	public DefaultServiceControlSkinPane(SCtrl _control) {
		super();
		control = _control;
		service = control.getService();

		switch(control.getMode()) {
		case Action:	statusControl = new ServiceStateSkinAction<S>(service);
						break;
		case Info:
		default:		statusControl = new ServiceStateSkinInfo<S>(service);
						break;
		}

		mainPane       	= new VBox();
//		mainScrollPane  = new ScrollPane(mainPane);
		mainTitledPane  = new TitledPane(control.getTitle(), mainPane);

		mainPane.getChildren().addAll(statusControl);

		VBox.setMargin(statusControl, new Insets(0, 0, 10, 0));
		getChildren().addAll(mainTitledPane);
	}

	@Override
	public SCtrl getSkinnable() {
		return control;
	}
	public S     getService() {
		return getSkinnable().getService();
	}

	@Override
	public Node getNode() {
		return this;
	}
	public VBox getPane() {
		return mainPane;
	}

	@Override
	public void dispose() {
		;
	}

}
