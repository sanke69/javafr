package fr.javafx.behavior.extended.bindings;

import java.util.function.Consumer;

import javafx.event.Event;
import javafx.event.EventType;

import fr.javafx.behavior.Behavior;

public interface Binding<E extends Event, B extends Behavior<?>> {

	public EventType<E> getEventType();
	public Consumer<B> 	getAction();

	public void 		fireIfMatches(E _event, B _behavior);

}
