package fr.javafx.sdk.controls.transforms;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;

public class Euclidian2DControlSkin extends BorderPane implements Skin<Euclidian2DControl> {
	private Euclidian2DControl control;

	public Euclidian2DControlSkin(Euclidian2DControl _control) {
		super();
		control = _control;
		build();
		
		control.translationEnabledProperty().addListener((_obs, _old, _new) -> {
			build();
		});
		control.rotationEnabledProperty().addListener((_obs, _old, _new) -> {
			build();
		});
	}
	
	@Override
	public Euclidian2DControl getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		
	}

	public void build() {
		setLeft(control.isTranslationEnabled() ? control.getTranslationControl() : null);
		setRight(control.isRotationEnabled() ? control.getRotationControl() : null);
	}

}
