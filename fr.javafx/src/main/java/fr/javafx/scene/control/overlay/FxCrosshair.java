package fr.javafx.scene.control.overlay;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.behavior.AdvancedSkin;

@Deprecated
public class FxCrosshair extends Control {

	public FxCrosshair() {
		super();
	}

	@Override
	protected Skin<FxCrosshair> createDefaultSkin() {
		return new AdvancedSkin<FxCrosshair>(this, FxCrosshairVisualDefault::new);
	}

}
