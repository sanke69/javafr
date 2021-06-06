package fr.java.patterns.timeable;

import java.util.Optional;

import fr.java.events.Event;
import fr.java.lang.properties.Timestamp;

public interface TimeEvent extends Event {

	public enum Type { NEW_TIME, START, PAUSE, RESUME, STOP }; 

	public Type 			getType();
	public Timestamp 		getTimestamp();
	public Optional<Object> getValue();

}
