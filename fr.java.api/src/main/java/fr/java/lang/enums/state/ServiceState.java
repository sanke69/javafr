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
