package fr.javafx.scene.control.indicator;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.behavior.AdvancedSkin;

public class FxClock extends Control {
	public static final String defaultCss = FxClockVisualAnalogic.class.getResource("FxClock.css").toExternalForm();

	public enum Style { analogic }

    public FxClock(Style _style) {
    	super();
    }

	@Override
	protected Skin<FxClock> 	createDefaultSkin() {
		return new AdvancedSkin<FxClock>(this, FxClockVisualAnalogic::new);
	}

}