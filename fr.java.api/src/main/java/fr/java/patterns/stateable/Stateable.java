package fr.java.patterns.stateable;

import fr.java.lang.enums.State;

public interface Stateable extends StatePublisher {

	public State getState();

}
