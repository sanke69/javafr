package fr.javafx.sdk.controls.service.animations;

import fr.java.lang.enums.state.ServiceState;
import fr.java.multitasks.threads.lightweight.LightWeightProcesses;
import fr.javafx.sdk.controls.service.actions.StatusActionControl;
import fr.javafx.sdk.controls.service.styles.StateStyles;
import javafx.application.Platform;
import javafx.scene.image.Image;

public class StatusAnimations {

	public static void infoAnimation(StatusActionControl _ctrl, int _periodTime_ms, int _nbFlash) {
		final Image low  = StateStyles.getIcon(ServiceState.stopped);
		final Image high = StateStyles.getIcon(ServiceState.started);

		LightWeightProcesses.newThread(() -> {
			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> _ctrl.setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {
				final int step = i;
				Platform.runLater(() -> _ctrl.setIcon(step % 2 == 0 ? low : high));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> _ctrl.setDisable(false)); 
		});
	}
	public static void warningAnimation(StatusActionControl _ctrl, int _periodTime_ms, int _nbFlash) {
		final Image low  = StateStyles.getIcon(ServiceState.stopped);
		final Image high = StateStyles.getIcon(ServiceState.unavailable);
		
		LightWeightProcesses.newThread(() -> {
			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> _ctrl.setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {				
				final int step = i;
				Platform.runLater(() -> _ctrl.setIcon(step % 2 == 0 ? low : high));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> _ctrl.setDisable(false)); 
		});
	}
	public static void warningAnimation(StatusActionControl _ctrl, int _periodTime_ms, int _nbFlash, String _message) {
		final Image low  = StateStyles.getIcon(ServiceState.stopped);
		final Image high = StateStyles.getIcon(ServiceState.unavailable);

		LightWeightProcesses.newThread(() -> {
			if(_message != null)
				System.out.println(_message);

			int    nbFrame = _nbFlash * 2 + 1;
			double dt      = _periodTime_ms / nbFrame;

			Platform.runLater(() -> _ctrl.setDisable(true));
			for(int i = 0; i < nbFrame; ++i) {
				final int step = i;
				Platform.runLater(() -> _ctrl.setIcon(step % 2 == 0 ? low : high));
				try { Thread.sleep((long) dt); } catch(InterruptedException e) { }
			}
			Platform.runLater(() -> _ctrl.setDisable(false)); 
		});
	}

}
