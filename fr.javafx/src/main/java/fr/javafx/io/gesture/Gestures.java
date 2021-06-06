package fr.javafx.io.gesture;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;

public interface Gestures {
    public static final boolean IS_TOUCH_SUPPORTED = Platform.isSupported(ConditionalFeature.INPUT_TOUCH);

}
