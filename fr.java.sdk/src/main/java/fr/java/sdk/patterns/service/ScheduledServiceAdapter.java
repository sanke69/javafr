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
 * @file     ServiceSkeleton.java
 * @version  0.0.0.1
 * @date     2016/04/27
 * 
**/
package fr.java.sdk.patterns.service;

import java.util.EventListener;

import fr.java.events.EventListenerList;
import fr.java.jvm.properties.id.IDs;
import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.properties.ID;
import fr.java.patterns.service.Service;
import fr.java.patterns.stateable.StateListener;
import fr.java.sdk.events.Events;

public abstract class ScheduledServiceAdapter implements Service.Interruptable {

	private ID	 					id;
	private String 					name;
	private Runnable 				runnable;
	private final EventListenerList listeners;
	private double					fps;

	private State  					status;
	private boolean 				publishStatus = true;

	private Thread					internalThread;

	protected ScheduledServiceAdapter() {
		this(null, IDs.random(16), null);
	}
	public ScheduledServiceAdapter(Runnable _runnable) {
		this(_runnable, IDs.random(16), null);
	}
	public ScheduledServiceAdapter(Runnable _runnable, String _name) {
		this(_runnable, IDs.random(16), _name);
	}
	public ScheduledServiceAdapter(Runnable _runnable, ID _id, String _name) {
		this(_runnable, IDs.random(16), _name, 25);
	}
	public ScheduledServiceAdapter(Runnable _runnable, ID _id, String _name, double _fps) {
		super();

		id        = _id;
		name      = _name;
		runnable  = _runnable;
		fps       = _fps;

		status    = ServiceState.unavailable;
		listeners = Events.newListenerList();
		
	}

	protected void setRunnable(Runnable _runnable) {
		runnable = _runnable;
	}

	@Override
	public final ID 						getId() {
		return id;
	}
	@Override
	public final String 					getName() {
		return name;
	}
	
	public final double 					getFPS() {
		return fps;
	}

	public boolean 							isPublishStatus() {
		return publishStatus;
	}
	public ScheduledServiceAdapter 			publishStatus(boolean b) {
		publishStatus = b;
		return this;
	}

	@Override
	public final State 						getState() {
		return status;
	}
	public final State   					setState(State _newStatus) {
		State old = getState();
		status = _newStatus;
		if(isPublishStatus())
			fire(Events.newStateEvent(this, old, _newStatus));
		return old;
	}

	protected EventListenerList 			getListenerList() {
		return listeners;
	}

	@Override
	public <T extends EventListener> T[] 	getListeners(Class<T> _class) {
		return listeners.getListeners(_class);
	}

	@Override
	public void registerListener(StateListener _listener) {
		getListenerList().add(StateListener.class, _listener);
	}
	@Override
	public void unregisterListener(StateListener _listener) {
		getListenerList().remove(StateListener.class, _listener);
	}

	@Override
	public void	start() 	{ 
		if(internalThread == null) {
			internalThread = new Thread(() -> {
				try {
					setState(ServiceState.starting);
					// ???
					setState(ServiceState.started);

				    long thisTime  = System.currentTimeMillis();
					long lastTime  = System.currentTimeMillis();
					long pauseTime = (long) (1e3 / getFPS());

					while(!isStopped() && !Thread.currentThread().isInterrupted()) {
					    thisTime = System.currentTimeMillis();

						if(!isPaused())
							runnable.run();

						pauseTime = (long) ((1e3 - (thisTime - lastTime)) / getFPS());
				        lastTime  = thisTime;

						Thread.sleep(pauseTime > 0 ? pauseTime : 0);
					}

				} catch (InterruptedException e) {
					setState(ServiceState.stopping);
				}

				// ???
				setState(ServiceState.stopped);
			});
			
			internalThread.start();
		}
	}
	@Override
	public void	stop() 		{ setState(ServiceState.stopping); }

	@Override
	public void	pause() 	{ System.err.println("Operation not available"); }
	@Override
	public void	resume() 	{ System.err.println("Operation not available"); }

}
