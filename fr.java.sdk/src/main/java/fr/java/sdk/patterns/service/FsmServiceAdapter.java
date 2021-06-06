package fr.java.sdk.patterns.service;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;

public abstract class FsmServiceAdapter extends ServiceAdapter {

	public FsmServiceAdapter() {
		super();
	}

	@Override
	public final void start()  throws Exception {
		if(isReady() || isStopped() && !isFaulted())
			try {
				State old = setState(ServiceState.starting);

				if(tryStart())
					setState(ServiceState.started);
				else
					setState(old);

			} catch(Exception _e) {
				setState(ServiceState.faulted);
			}
		
	}
	@Override
	public final void stop()   throws Exception {
		if(isStarted())
			try {
				State old = setState(ServiceState.stopping);

				if(tryStop())
					setState(ServiceState.stopped);
				else
					setState(old);

			} catch(Exception _e) {
				setState(ServiceState.faulted);
			}
		
	}

	protected abstract boolean tryStart()  throws Exception;
	protected abstract boolean tryStop()   throws Exception;

}
