/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
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
