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
 * @file     Statusable.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.sdk.patterns.stateable;

import java.util.EventListener;

import fr.java.events.EventListenerList;
import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.patterns.capabilities.Stateable;
import fr.java.sdk.events.Events;
import fr.java.state.StateListener;

public abstract class StateableAdapter implements Stateable {
	private final EventListenerList listeners;

	private State   state;
	private boolean publishState = true;

	protected StateableAdapter() {
		this(ServiceState.unavailable);
	}
	protected StateableAdapter(State _current) {
		super();
		state    = _current;
		listeners = Events.newListenerList();
	}

	public boolean 							isPublishStatus() {
		return publishState;
	}
	public StateableAdapter 				publishStatus(boolean b) {
		publishState = b;
		return this;
	}

	public final State 						getState() {
		return state;
	}
	public synchronized final State 		setState(State _newStatus) {
		State old = getState();
		
		if(old == _newStatus)
			return old;

		state = _newStatus;
		if(isPublishStatus())
			fire(Events.newStateEvent(this, old, _newStatus));
		return old;
	}

	protected EventListenerList 			getListenerList() {
		return listeners;
	}
	public <T extends EventListener> T[] 	getListeners(Class<T> _class) {
		return listeners.getListeners(_class);
	}

	public void 							registerListener(StateListener _listener) {
		getListenerList().add(StateListener.class, _listener);
	}
	public void 							unregisterListener(StateListener _listener) {
		getListenerList().remove(StateListener.class, _listener);
	}

}
