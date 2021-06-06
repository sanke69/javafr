package fr.java.sdk.patterns.service;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.patterns.service.Service;

public abstract class InterruptableServiceAdapter extends ServiceAdapter implements Service.Interruptable {
	private final boolean enableInterrupt;

	public InterruptableServiceAdapter() {
		this(false);
	}
	public InterruptableServiceAdapter(boolean _isInterruptable) {
		super();
		enableInterrupt = _isInterruptable;
	}
	public InterruptableServiceAdapter(String _name) {
		this(_name, false);
	}
	public InterruptableServiceAdapter(String _name, boolean _isInterruptable) {
		super(_name);
		enableInterrupt = _isInterruptable;
	}
	public InterruptableServiceAdapter(ID _id, String _name) {
		this(_id, _name, false);
	}
	public InterruptableServiceAdapter(ID _id, String _name, boolean _isInterruptable) {
		super(_id, _name);
		enableInterrupt = _isInterruptable;
	}

	@Override
	public final void start() {
		if(isReady() || isPaused() || isStopped() && !isFaulted()) {
			State old = setState(ServiceState.starting);

			try {
				setState(tryStart() ? ServiceState.started: ServiceState.faulted);
			} catch (Exception e) {
				e.printStackTrace();
				setState(old);
			}
		}
	}
	@Override
	public final void stop() {
		if(isStarted() || isPaused() || getState() == ServiceState.stopping) {
			State old = setState(ServiceState.stopping);

			try {
				setState(tryStop() ? ServiceState.stopped: ServiceState.faulted);
			} catch (Exception e) {
				e.printStackTrace();
				setState(old);
			}
		}
	}
	@Override
	public final void pause() {
		if(isStarted() && enableInterrupt) {
			State old = setState(ServiceState.pausing);

			try {
				setState(tryPause() ? ServiceState.paused: ServiceState.faulted);
			} catch (Exception e) {
				e.printStackTrace();
				setState(old);
			}
		}
		
	}
	@Override
	public final void resume() {
		if(isPaused()) {
			State old = setState(ServiceState.resuming);

			try {
				setState(tryPause() ? ServiceState.started: ServiceState.faulted);
			} catch (Exception e) {
				e.printStackTrace();
				setState(old);
			}
		}
	}

	protected abstract boolean 	tryStart()  throws Exception;
	protected boolean 			tryPause()  throws Exception {
		if(!enableInterrupt)
			throw new IllegalAccessError();
		throw new NotYetImplementedException("must be implemented!");
	}
	protected boolean 			tryResume() throws Exception {
		if(!enableInterrupt)
			throw new IllegalAccessError();
		throw new NotYetImplementedException("must be implemented!");
	}
	protected abstract boolean 	tryStop()   throws Exception;

}
