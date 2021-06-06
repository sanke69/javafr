package fr.java.lang.enums.state;

import fr.java.lang.enums.State;

public enum ServiceState implements State {
	unavailable ( -1, "unavailable"),
	setting	  	(100, "setting"),
	ready		(100, "ready"),
	starting	(150, "starting"),
	resuming	(150, "resuming"),
	started	  	(200, "started"),	
	paused	  	(200, "paused"),
	stopping	(250, "stopping"),	
	pausing	  	(250, "pausing"),
	stopped	  	(100, "stopped"),
	faulted	  	(999, "faulted");

	int    level;
	String description;

	ServiceState(int _value, String _description) {
		level       = _value;
		description = _description;
	}

	@Override
	public int stateCode() { return level; }

	@Override
	public String  toString()  { return description; }
//	public String  toString()  { return "[State: desc= " + description + ", code= " + stateCode() + "]"; }

}
