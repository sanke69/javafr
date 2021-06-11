package fr.javafx.sdk.controls.service.styles;

import java.io.IOException;

import javax.imageio.ImageIO;

import fr.java.lang.enums.State;
import fr.java.lang.enums.state.ServiceState;
import fr.java.state.StateSwitcher;
import fr.javafx.sdk.controls.service.skins.contents.ServiceStateSkinAction;
import fr.javafx.utils.FxImageUtils;
import javafx.scene.image.Image;

public class StateStyles {
	private static Image ready, starting, started, stopped, unavailable;

	static {
		try {
			unavailable = FxImageUtils.toFXImage(ImageIO.read(ServiceStateSkinAction.class.getResourceAsStream("/default/service/ChipERROR.png")), null);
			ready       = FxImageUtils.toFXImage(ImageIO.read(ServiceStateSkinAction.class.getResourceAsStream("/default/service/ChipREADY.png")), null);
			starting    = FxImageUtils.toFXImage(ImageIO.read(ServiceStateSkinAction.class.getResourceAsStream("/default/service/ChipSTARTING.png")), null);
			started     = FxImageUtils.toFXImage(ImageIO.read(ServiceStateSkinAction.class.getResourceAsStream("/default/service/ChipON.png")), null);
			stopped     = FxImageUtils.toFXImage(ImageIO.read(ServiceStateSkinAction.class.getResourceAsStream("/default/service/ChipOFF.png")), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image getIcon(State _state) {
		if(!(_state instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState state = (ServiceState) _state;
		return
		StateSwitcher.of(state, Image.class)
					 .ifEquals(()  -> ready,       ServiceState.ready)
					 .ifEquals(()  -> starting,    ServiceState.starting)
					 .ifEquals(()  -> started,     ServiceState.started)
					 .ifEquals(()  -> stopped,     ServiceState.stopped)
					 .ifEquals(()  -> unavailable, ServiceState.unavailable)
					 .otherwise(() -> unavailable)
					 .perform();
	}

	public static String getStyle(State _state) {
		if(!(_state instanceof ServiceState))
			throw new IllegalArgumentException();
		
		ServiceState state = (ServiceState) _state;
		return
		StateSwitcher.of(state, String.class)
					 .ifEquals(()  -> "-fx-background-color: darkgreen; -fx-base: darkgreen;",   ServiceState.starting)
					 .ifEquals(()  -> "-fx-background-color: lightgreen; -fx-base: lightgreen;", ServiceState.started)
					 .ifEquals(()  -> "-fx-background-color: darkred; -fx-base: darkred;",       ServiceState.stopped)
					 .ifEquals(()  -> "-fx-background-color: black; -fx-base: black;",       ServiceState.unavailable)
					 .otherwise(() -> "-fx-background-color: black; -fx-base: black;")
					 .perform();
	}

}
