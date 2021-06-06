package fr.java.sdk.patterns.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.java.lang.enums.ErrorCode;
import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.lang.properties.ID;
import fr.java.patterns.service.Service;

public abstract class InterruptableServiceAdapterOld extends ServiceAdapter implements Service.Interruptable {
	private final boolean enableInterrupt;

	ExecutorService executor = Executors.newCachedThreadPool();

	public InterruptableServiceAdapterOld() {
		this(false);
	}
	public InterruptableServiceAdapterOld(boolean _isInterruptable) {
		super();
		enableInterrupt = _isInterruptable;
	}
	public InterruptableServiceAdapterOld(String _name) {
		this(_name, false);
	}
	public InterruptableServiceAdapterOld(String _name, boolean _isInterruptable) {
		super(_name);
		enableInterrupt = _isInterruptable;
	}
	public InterruptableServiceAdapterOld(ID _id, String _name) {
		this(_id, _name, false);
	}
	public InterruptableServiceAdapterOld(ID _id, String _name, boolean _isInterruptable) {
		super(_id, _name);
		enableInterrupt = _isInterruptable;
	}

	@Override
	public final void start() {
		if(isReady() || isPaused() || isStopped() && !isFaulted()) {
			State old = setState(ServiceState.starting);

			switch(execute(this::tryStart)) {
			case SUCCESS:	setState(ServiceState.started);
							break;
			case FAILURE:	setState(ServiceState.faulted);
							break;
			case TIMEOUT:	
			default:		setState(old);
			}
		}
	}
	@Override
	public final void stop() {
		if(isStarted() || isPaused() || getState() == ServiceState.stopping) {
			State old = setState(ServiceState.stopping);

			switch(execute(this::tryStop)) {
			case SUCCESS:	setState(ServiceState.stopped);
							break;
			case FAILURE:	setState(ServiceState.faulted);
							break;
			case TIMEOUT:	
			default:		setState(old);
			
			}
		}
	}
	@Override
	public final void pause() {
		if(isStarted() && enableInterrupt) {
			State old = setState(ServiceState.pausing);

			switch(execute(this::tryPause)) {
			case SUCCESS:	setState(ServiceState.paused);
							break;
			case FAILURE:	setState(ServiceState.faulted);
							break;
			case TIMEOUT:	
			default:		setState(old);
			
			}
		}
		
	}
	@Override
	public final void resume() {
		if(isPaused()) {
			State old = setState(ServiceState.resuming);

			switch(execute(this::tryPause)) {
			case SUCCESS:	setState(ServiceState.started);
							break;
			case FAILURE:	setState(ServiceState.faulted);
							break;
			case TIMEOUT:	
			default:		setState(old);
			
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

	private final ErrorCode execute(Callable<Boolean> _blockingMethod) {
//		Future<Boolean> future = executor.submit(() -> _blockingMethod.call());
		Future<Boolean> future = executor.submit(_blockingMethod);

		try {
			future.get(5, TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			return ErrorCode.TIMEOUT;
		} catch (ExecutionException e) {
			return ErrorCode.FAILURE;
		} catch (InterruptedException e) {
			// Nothing to do as it is used by tryStop() method ... maybe a fix is needed!			
//			return ErrorCode.FAILURE;
		} finally {
			future.cancel(true);
		}

		return ErrorCode.SUCCESS;
	}

}
