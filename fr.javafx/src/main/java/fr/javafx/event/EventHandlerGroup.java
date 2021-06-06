package fr.javafx.event;

import java.util.ArrayList;
import java.util.Collection;

import javafx.event.Event;
import javafx.event.EventHandler;

public class EventHandlerGroup<T extends Event> implements EventHandler<T> {

	private Collection<EventHandler<T>> handlers = new ArrayList<>();

	public void addHandler(EventHandler<T> eventHandler) {
		handlers.add(eventHandler);
	}

	@Override
    public void handle(T t) {
    	handlers.forEach(eh -> eh.handle(t));
//        for (EventHandler<T> eventHandler : handlers)
//            eventHandler.handle(t);
    }

}