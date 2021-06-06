package fr.javafx.sdk.controls.time;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class TimeSelectionView extends Control {

	@Override
	protected Skin<?> createDefaultSkin() {
		return new TimeSelectionViewSkin(this);
	}

	public void forceLayout() {
		setNeedsLayout(true);
		layout();
	}

}