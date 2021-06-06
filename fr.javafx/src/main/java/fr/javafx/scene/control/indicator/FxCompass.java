package fr.javafx.scene.control.indicator;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.behavior.AdvancedSkin;
 
public class FxCompass extends Control {
	public static final EventType<Event> EVENT_TYPE_CHANGE_NORTH = new EventType<Event>("ChangeNorth");
	public static final EventType<Event> EVENT_TYPE_CHANGE_VALUE = new EventType<Event>("Change");

	double angle, northAngle;

	public enum Style { simple }

	public FxCompass() {
		this(Style.simple);
	}
	public FxCompass(Style _style) {
		super();
		northAngle = 0;
		angle      = 0;
	}

	@Override
	protected Skin<FxCompass> 	createDefaultSkin() {
		return new AdvancedSkin<FxCompass>(this, FxCompassVisualDefault::new);
	}

	public void setNorthPosition(double _degree) {
		northAngle = _degree;
    	Platform.runLater(() -> fireEvent(new Event(Double.valueOf(_degree), this, EVENT_TYPE_CHANGE_NORTH)) );
	}
	public double getNorthPosition() {
		return northAngle;
	}

	public void setAngle(double _degree) {
		angle = _degree;
    	Platform.runLater(() -> fireEvent(new Event(Double.valueOf(_degree), this, EVENT_TYPE_CHANGE_VALUE)) );
	}
	public double getAngle() {
		return angle;
	}

}
