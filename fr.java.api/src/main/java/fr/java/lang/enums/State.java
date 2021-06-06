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
 * @file     Status.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.lang.enums;

public interface State {
	
	class StateImpl implements State {
		int     stateCode = -1;
		boolean hasError  = false;
		boolean isRunning = false;
		
		StateImpl(int _stateCode, boolean _hasError, boolean _isRunning) {
			super();
			stateCode = _stateCode;
			hasError  = _hasError;
			isRunning = _isRunning;
		}

		public int     stateCode() { return stateCode; }
		public boolean hasError()  { return hasError; }
		public boolean isRunning() { return isRunning; }

		public String  toString()  { return "[State: code= " + stateCode() + "]"; }

	}

	public static final State UNAVAILABLE = new StateImpl( -1, false, false);
	public static final State READY       = new StateImpl(  0, false, false);
	public static final State ERROR       = new StateImpl(999, false, false);

	public default int     stateCode() { return -1; }

	public default boolean hasError()  { return false; }
	public default boolean isRunning() { return false; }

	// TODO:: Must be in an FSM, not a general concept...
/*
	unavailable	( -1),
	setting		(100),
	ready		(100),
	starting	(150),	resuming	(150),
	started		(200),	paused		(200),
	stopping	(250),	pausing		(250),
	stopped		(100),
	faulted		(999);

	int level;

	Status(int _value) {
		level = _value;
	}
*/
}
