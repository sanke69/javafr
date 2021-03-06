package fr.javafx.sdk.controls.time;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public abstract class TimeTextFieldView extends Control {

	private SimpleIntegerProperty time = new SimpleIntegerProperty();

	@Override
	protected Skin<?> createDefaultSkin() {
		return new TimeTextFieldViewSkin(this);
	}

	public void forceLayout() {
		setNeedsLayout(true);
		layout();
	}

	public int getTime() {
		return time.get();
	}

	public void setTime(int time) {
		this.time.set(time);
	}

	public SimpleIntegerProperty timeProperty() {
		return time;
	}

	public void select(TimeDigitView control) {
		if(getSkin() == null)
			setSkin( createDefaultSkin() );
		((TimeTextFieldViewSkin) getSkin()).select(control);
	}

	public abstract void setNewTime(int newTime);
}