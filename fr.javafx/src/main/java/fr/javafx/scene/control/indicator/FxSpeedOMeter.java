package fr.javafx.scene.control.indicator;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.behavior.AdvancedSkin;

public class FxSpeedOMeter extends Control {
	public static final EventType<Event> EVENT_TYPE_CHANGE_VALUE = new EventType<Event>("Change");

	double value;

	public enum Style { simple }

	public FxSpeedOMeter() {
		this(Style.simple);
	}
	public FxSpeedOMeter(Style _style) {
		super();
		value = 0;
	}

	@Override
	protected  Skin<FxSpeedOMeter> 			createDefaultSkin() {
		return new AdvancedSkin<FxSpeedOMeter>(this, new FxSpeedOMeterVisualDefault(this));
	}

	public void setValue(double _v) {
		value = _v;
    	Platform.runLater(() -> fireEvent(new Event(Double.valueOf(_v), this, EVENT_TYPE_CHANGE_VALUE)) );
	}
	public double getValue() {
		return value;
	}

}
