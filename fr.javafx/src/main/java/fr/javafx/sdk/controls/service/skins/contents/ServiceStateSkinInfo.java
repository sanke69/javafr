package fr.javafx.sdk.controls.service.skins.contents;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import fr.java.lang.enums.State;
import fr.java.patterns.service.Service;
import fr.java.patterns.stateable.StateEvent;
import fr.java.patterns.stateable.StateListener;
import fr.javafx.sdk.controls.service.styles.StateStyles;

public class ServiceStateSkinInfo<S extends Service> extends HBox implements StateListener {
	private final S			service;

	private final Label 	label;
	private final ImageView chip;

	public ServiceStateSkinInfo(S _service) {
		this(_service, _service.getName(), false);
	}
	public ServiceStateSkinInfo(S _service, boolean _withLabel) {
		this(_service, _service.getName(), _withLabel);
	}
	public ServiceStateSkinInfo(S _service, String _text, boolean _withLabel) {
		super();

		service = _service;

		chip = new ImageView();
		chip.setFitWidth(32);
		chip.setFitHeight(32);

		if(_withLabel) {
			label = new Label(_text);
	
			label.setAlignment(Pos.CENTER);
			label.prefWidthProperty().bind(widthProperty().subtract(chip.fitWidthProperty()));
			label.prefHeightProperty().bind(chip.fitHeightProperty());
	
			HBox.setMargin(chip, new Insets(0, 8, 0, 8));
	
			getChildren().addAll(label, chip);
		} else {
			label = null;

			getChildren().addAll(chip);
		}

		setStyle(service.getState());
//		setOpaqueInsets(new Insets(0, 5, 0, 5));

		service.registerListener(this);
	}

	@Override
	public void onStateEvent(StateEvent _event) {
		Platform.runLater(() -> setStyle(_event.getState()));
	}

	protected void setStyle(State _status) {
		chip.setImage(StateStyles.getIcon(_status));

		if(label != null)
			setStyle(StateStyles.getStyle(_status));
	}

}
