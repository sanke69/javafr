package fr.javafx.sdk.controls.service.skins.contents;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.multitasks.threads.lightweight.LightWeightProcesses;
import fr.java.patterns.service.Service;
import fr.java.state.StateEvent;
import fr.java.state.StateListener;
import fr.java.state.StateSwitcher;
import fr.javafx.sdk.controls.service.styles.StateStyles;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ServiceStateSkinAction<S extends Service> extends HBox implements StateListener {
	private final S    		service;

	protected final Label 	label;
	private final Button 	button;
	private final ImageView icon;

	public ServiceStateSkinAction(S _service) {
		this(_service, _service.getName(), false);
	}
	public ServiceStateSkinAction(S _service, boolean _withLabel) {
		this(_service, _service.getName(), _withLabel);
	}
	public ServiceStateSkinAction(S _service, String _text, boolean _withLabel) {
		super();
		service = _service;

		icon   = new ImageView();
		setIconSize(32, 32);

		button = new Button();
		button.setGraphic(icon);

		if(_withLabel) {
			label   = new Label(service.getName());

			label.setAlignment(Pos.CENTER_LEFT);
			label.prefWidthProperty().bind(widthProperty().subtract(icon.fitWidthProperty()));
			label.prefHeightProperty().bind(icon.fitHeightProperty());

			getChildren().addAll(label, button);
		} else {
			label = null;
			getChildren().addAll(button);
		}

		setStyle(service.getState());

		if(service.getState() == ServiceState.unavailable)
			setDisable(true);

		service.registerListener(this);
	}

	@Override
	public void onStateEvent(StateEvent _event) {
		Platform.runLater(() -> updateStyle(_event.getState(), StateStyles.getIcon(_event.getState()), null));
	}

	public void updateStyle(State _new, Image icon, Color c) {
		setStyle(_new);
		setIcon(_new);
	}

	protected void setStyle(State _status) {
		State status = _status != null ? _status : ServiceState.unavailable;
		icon.setImage(StateStyles.getIcon(status));
//		label.setStyle(ServiceControl.StatusStyles.getStyle(_status));
		setStyle(StateStyles.getStyle(status));
	}
	protected void setIcon(State _status) {
		icon.setImage(StateStyles.getIcon(_status));
	}
	protected void setIconSize(double _w, double _h) {
		icon.setFitWidth(_w);
		icon.setFitHeight(_h);
	}

	protected void startService() {
		if(!(service.getState() instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState status = (ServiceState) service.getState();
		StateSwitcher.of(status, Void.class)
					 .ifEquals(this::start,   ServiceState.ready, ServiceState.stopped)
					 .ifEquals(this::disable, ServiceState.unavailable)
					 .perform();
	}
	protected void stopService() {
		if(!(service.getState() instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState status = (ServiceState) service.getState();
		StateSwitcher.of(status, Void.class)
					 .ifEquals(this::stop,        ServiceState.started)
					 .ifEquals(this::unavailable, ServiceState.unavailable)
					 .perform();
	}

	private Void start() {
		try {
			service.start();
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	private Void stop() {
		try {
			service.stop();
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

	protected void subProcessing(Runnable _cmd) {
		LightWeightProcesses.newThread(() -> {
			Platform.runLater(() -> setDisable(true)); 
			Platform.runLater(() -> { _cmd.run(); setDisable(false); });
		});
	}
	protected void subProcessing(Runnable _cmd, String _msg) {
		LightWeightProcesses.newThread(() -> {
			Platform.runLater(() -> setDisable(true));
			Platform.runLater(() -> { _cmd.run(); setDisable(false); });
		});
	}
	protected void subProcessing(Runnable _cmd, long _waitTime_ms) {
		LightWeightProcesses.newThread(() -> {
			Platform.runLater(() -> setDisable(true)); 
			try { Thread.sleep(_waitTime_ms); } catch(InterruptedException e) { }
			Platform.runLater(() -> { _cmd.run(); setDisable(false); });
		});
	}
	protected void subProcessing(Runnable _cmd, long _waitTime_ms, String _msg) {
		LightWeightProcesses.newThread(() -> {
			Platform.runLater(() -> setDisable(true));
			try { Thread.sleep(_waitTime_ms); } catch(InterruptedException e) { }
			Platform.runLater(() -> { _cmd.run(); setDisable(false); });
		});
	}

	protected void infoAnimation(int _periodTime_ms, int _nbFlash) {
		LightWeightProcesses.newThread(() -> {
			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {
				final int step = i;
//				Platform.runLater(() -> setStyle(step % 2 == 0 ? Status.waiting : Status.started));
				Platform.runLater(() -> setStyle(step % 2 == 0 ? ServiceState.stopped : ServiceState.started));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> setDisable(false)); 
		});
	}
	protected void warningAnimation(int _periodTime_ms, int _nbFlash) {
		LightWeightProcesses.newThread(() -> {
			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {
				final int step = i;
				Platform.runLater(() -> setStyle(step % 2 == 0 ? ServiceState.stopped : ServiceState.unavailable));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> setDisable(false)); 
		});
	}
	protected void warningAnimation(int _periodTime_ms, int _nbFlash, String _message) {
		LightWeightProcesses.newThread(() -> {
			if(_message != null)
				System.out.println(_message);

			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {
				final int step = i;
				Platform.runLater(() -> setStyle(step % 2 == 0 ? ServiceState.stopped : ServiceState.unavailable));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> setDisable(false)); 
		});
	}


	public <T extends Boolean> void handlerMousePressed(ObservableValue<T> _obs, T _old, T _new) {
		
//	public void handlerMousePressed(ObservableValue<Boolean> _obs, Boolean _old, Boolean _new) {
		
		/*
		if(!_new)
			return;
		if(service.isStarted()) {
			new Thread( () -> {
				subProcessing(service::stop, "Service is alive, try to kill"); 

            	if(service.isStarted()) {
        			System.err.println("Failed to kill");
        			warningAnimation(750, 3);
        			setStyle(service.getStatus());           		
            	} else {
        			System.out.println("Kill Success");
                	setStyle(service.getStatus());
            	}
            	setDisable(false);
			}).start();
		} else {
			new Thread( () -> {
	        	setDisable(true);
    			System.out.println("Service is not alive, try to start");
            	setStyle(service.getStatus());
            	service.start();

            	if(!service.isStarted()) {
        			System.out.println("Failed to start");
        			warningAnimation(750, 3);
        			setStyle(service.getStatus());                  		
            	} else {
        			System.out.println("Start Success");
                	setStyle(service.getStatus());
            	}
            	setDisable(false);
			}).start();
		}
		*/
	}

}