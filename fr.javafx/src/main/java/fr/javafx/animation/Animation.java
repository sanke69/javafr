package fr.javafx.animation;

import javafx.util.Duration;

public interface Animation<FXObject> {

    /**
     * Plays both the show and dismiss animation using a sequential transition object
     * @param dismissDelay How long to delay the start of the dismiss animation
     */
    void playSequential(Duration dismissDelay);

    /**
     * Plays the implemented show animation
     */
    void playShowAnimation();

    /**
     * Plays the implemented dismiss animation
     */
    void playDismissAnimation();

    /**
     * Signifies if the stage is current showing
     * @return boolean resultant
     */
    boolean isShowing();

	/**
	 * @return The target of the animation
	 */
    FXObject getTarget();

}
