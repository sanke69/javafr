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
package fr.java.state;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.exceptions.AlreadyExistingEntry;

public class StateSwitcher<R> {
	State              				object;
	Map<Set<State>, Supplier<R>> 	switchCases;
	Supplier<R>						notFoundAction;
	
	public static <R> StateSwitcher<R> of(State _state, Class<R> _returnClass) {
		return new StateSwitcher<R>(_state);
	}
	
	private StateSwitcher(State _state) {
		super();
		object = _state;
		switchCases = new HashMap<Set<State>, Supplier<R>>();
	}

	public StateSwitcher<R> ifEquals(Supplier<R> _action, State... _states) {
		if(switchCases.keySet().stream().flatMap(Set::stream).anyMatch(s -> Arrays.asList(_states).stream().anyMatch(s2 -> s.equals(s2))))
			throw new AlreadyExistingEntry();
		
		Set<State> states = new HashSet<State>(Arrays.asList(_states));
		switchCases.put(states, _action);
		return this;
	}
	public StateSwitcher<R> otherwise(Supplier<R> _action) {
		notFoundAction = _action;
		return this;
	}

	public R perform() {
		for(Map.Entry<Set<State>, Supplier<R>> caze : switchCases.entrySet()) {
			for(State state : caze.getKey()) {
				if(state.equals(object)) {
					return caze.getValue().get();
				}
			}
		}
		if(notFoundAction != null)
			return notFoundAction.get();
		
		return null;
	}
	
	public static void main(String[] args) {
		State s = ServiceState.ready;
		System.out.println(
		StateSwitcher.of(s, String.class)
					 .ifEquals(()  -> "ready",   ServiceState.ready)
					 .ifEquals(()  -> "started", ServiceState.started)
					 .ifEquals(()  -> "stopped", ServiceState.stopped)
					 .otherwise(() -> "undef")
					 .perform()
		);
	}
}
