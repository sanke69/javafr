package fr.java.sdk.patterns.stateable;

import java.util.Map;
import java.util.WeakHashMap;

import fr.java.events.EventListenerList;
import fr.java.lang.enums.State;
import fr.java.patterns.stateable.Stateable;
import fr.java.sdk.events.Events;

class StateableOption {
	final EventListenerList listeners;

	State  state;
	boolean publishStatus = true;

	StateableOption() {
		super();
		listeners = Events.newListenerList();
	}

}

class StateableRegister {
	static final Map<Stateable, StateableOption> hashmap = new WeakHashMap<Stateable, StateableOption>();

	static StateableOption get(Stateable _statusable) {
		return hashmap.get(_statusable);
	}

	static void add(Stateable _statusable) {
		hashmap.put(_statusable, new StateableOption());
	}
	static void remove(Stateable _statusable) {
		hashmap.remove(_statusable);
	}

}

public class Stateables {

	public static void registerStateable(Stateable _statusable) {
		StateableRegister.add(_statusable);
	}
	public static void unregisterStateable(Stateable _statusable) {
		StateableRegister.remove(_statusable);
	}

	public static State  getState(Stateable _statusable) {
		return StateableRegister.get(_statusable).state;
	}

}
