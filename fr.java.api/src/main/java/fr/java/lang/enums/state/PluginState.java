package fr.java.lang.enums.state;

import fr.java.lang.enums.State;

public class PluginState implements State {
	public static final State UNAVAILABLE = new PluginState(State.UNAVAILABLE, 	false);
	public static final State READY       = new PluginState(State.READY, 		true);
	public static final State ERROR       = new PluginState(State.ERROR, 		false);

	public static final State LOADING     = new PluginState(99, false, true, 		true);
	public static final State LOADED      = new PluginState(99, false, true, 		true);

	public static final State STARTING    = new PluginState(99, false, true, 		true);
	public static final State STARTED     = new PluginState(99, false, true, 		true);
	public static final State STOPPING    = new PluginState(99, false, true, 		true);
	public static final State STOPPED     = new PluginState(99, false, true, 		true);

	int     stateCode = -1;

	boolean hasError  = false;

	boolean isLoaded  = false;
	boolean isRunning = false;

	PluginState() {
		super();
	}
	PluginState(State _state, boolean _isLoaded) {
		super();
		stateCode = _state.stateCode();
		hasError  = _state.hasError();
		isLoaded  = _isLoaded;
		isRunning = _state.isRunning();
	}
	PluginState(int _stateCode, boolean _hasError, boolean _isLoaded, boolean _isRunning) {
		super();
		stateCode = _stateCode;
		hasError  = _hasError;
		isLoaded  = _isLoaded;
		isRunning = _isRunning;
	}

	public int     stateCode() { return stateCode; }

	public boolean hasError()  { return hasError; }

	public boolean isLoaded()  { return isLoaded; }
	public boolean isReady()   { return isRunning; }

}
