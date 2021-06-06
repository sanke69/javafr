package fr.javafx.event;

import javafx.event.Event;
import javafx.event.EventType;

public class RefreshEvent extends Event {
	private static final long serialVersionUID = 1L;

	public final static EventType<RefreshEvent> REFRESHED        = new EventType<>("REFRESHED");
	public final static EventType<RefreshEvent> BEFORE_REFRESHED = new EventType<>(REFRESHED, "BEFORE_REFRESHED");

	public RefreshEvent() {
		super(REFRESHED);
	}

}
