package fr.javafx.sdk.controls.service;

import fr.java.patterns.service.Service;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ServiceActionControl extends Button {
	public static final int ICON_SIZE = 32;

	private final ServiceControl<?> control;
	private final Service           service;

	private Image     icon;
	private ImageView graphics;

	public ServiceActionControl(ServiceControl<?> _control) {
		super();
		
		control = _control;
		service = control.getService();
		
		graphics = new ImageView();
		graphics.setFitHeight(ICON_SIZE);
		graphics.setFitWidth(ICON_SIZE);
//		graphics.setPreserveRatio(true);
		
		setGraphic(graphics);
	}
	
	public void setIcon(Image _img) {
		icon = _img;
		graphics.imageProperty().set(icon);
	}

	public ServiceControl getControl() { return control; }

	public Service getService() { return service; }

}
