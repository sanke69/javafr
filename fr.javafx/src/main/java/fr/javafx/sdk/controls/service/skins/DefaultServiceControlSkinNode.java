package fr.javafx.sdk.controls.service.skins;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.HBox;
import fr.java.patterns.service.Service;
import fr.javafx.sdk.controls.service.ServiceControl;
import fr.javafx.sdk.controls.service.skins.contents.ServiceStateSkinAction;
import fr.javafx.sdk.controls.service.skins.contents.ServiceStateSkinInfo;

public class DefaultServiceControlSkinNode<S extends Service, SCtrl extends ServiceControl<S>> implements Skin<SCtrl> {
	private final SCtrl 	control;
	private final S			service;

	protected final HBox 	statusControl;

	public DefaultServiceControlSkinNode(SCtrl _control) {
		super();
		control = _control;
		service = control.getService();

		switch(control.getMode()) {
		case Action:	statusControl = new ServiceStateSkinAction<S>(service, getSkinnable().getType() != ServiceControl.Style.Chip);
						break;
		case Info:
		default:		statusControl = new ServiceStateSkinInfo<S>(service, getSkinnable().getType() != ServiceControl.Style.Chip);
						break;
		}
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
		return statusControl;
	}

	@Override
	public void dispose() {
		;
	}

}
