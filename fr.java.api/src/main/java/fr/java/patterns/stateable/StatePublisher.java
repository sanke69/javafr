package fr.java.patterns.stateable;

import fr.java.events.EventPublisher;

public interface StatePublisher extends EventPublisher {

	public default void fire(StateEvent _evt) {
		if(getListeners(StateListener.class) == null)
			return ;

		for(StateListener l : getListeners(StateListener.class))
			l.onStateEvent(_evt);
	}

	public void registerListener(StateListener _listener);
	public void unregisterListener(StateListener _listener);

}
