package fr.java.sdk.events;

import fr.java.events.Event;

public abstract class EventBase extends java.util.EventObject implements Event {
	private static final long serialVersionUID = 3294653934760127917L;

	public EventBase(Object _source) {
		super(_source);
	}

}
