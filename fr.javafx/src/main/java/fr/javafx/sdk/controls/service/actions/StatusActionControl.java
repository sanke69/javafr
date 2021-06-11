package fr.javafx.sdk.controls.service.actions;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.state.StateEvent;
import fr.java.state.StateListener;
import fr.java.state.StateSwitcher;
import fr.javafx.sdk.controls.service.ServiceActionControl;
import fr.javafx.sdk.controls.service.ServiceControl;
import fr.javafx.sdk.controls.service.styles.StateStyles;
import javafx.application.Platform;

public class StatusActionControl extends ServiceActionControl implements StateListener {

	public StatusActionControl(ServiceControl<?> _control) {
		super(_control);

		setIcon( StateStyles.getIcon(getService().getState()) );
		if(getService().getState() == ServiceState.unavailable)
			setDisable(true);

		getService().registerListener(this);
		
		setOnAction((e) -> {
			if(getService().getState() == ServiceState.unavailable) {
				getControl().setMessage("Service unavailable");
				return ;
			}
			
			if(getService().isReady() || getService().isStopped()) {
				getControl().setMessage("Starting service");
				startService();
				getControl().setMessage("Service started");
			} else {
				getControl().setMessage("Stopping service");
				stopService();
				getControl().setMessage("Service stopped");
			}
		});
	}

	@Override
	public void onStateEvent(StateEvent _event) {
		Platform.runLater(() -> {
			State status = _event.getState();

			setIcon( StateStyles.getIcon(status) );

			if(status == ServiceState.unavailable)
				setDisable(true);
			else
				setDisable(false);
		});
	}

	protected void startService() {
		if(!(getService().getState() instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState status = (ServiceState) getService().getState();
		StateSwitcher.of(status, Void.class)
					 .ifEquals(this::start,   ServiceState.ready, ServiceState.stopped)
					 .ifEquals(this::disable, ServiceState.unavailable)
					 .perform();
	}
	protected void stopService() {
		if(!(getService().getState() instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState status = (ServiceState) getService().getState();
		StateSwitcher.of(status, Void.class)
					 .ifEquals(this::stop,        ServiceState.started)
					 .ifEquals(this::unavailable, ServiceState.unavailable)
					 .perform();
	}

	private Void start() {
		try {
			getService().start();
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	private Void stop() {
		try {
			getService().stop();
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	private Void disable() {
		System.err.println("Service unavailable");
		setDisable(true);
		return null;
	}
	private Void unavailable() {
		System.err.println("Service unavailable");
		setDisable(true);
		return null;
	}

}
