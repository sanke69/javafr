package fr.java.patterns.timeable;

import java.util.EventListener;

@FunctionalInterface
public interface TimeListener extends EventListener {

	public void onTimeEvent(TimeEvent _event);

}
