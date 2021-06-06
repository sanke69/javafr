package fr.javafx.animation;

import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class AbstractAnimation<N> implements Animation<N> {
	protected final N 						nodeFx;

	protected volatile boolean				nodeIsShowing;
	protected final Timeline				showAnimation, dismissAnimation;
	protected final SequentialTransition	sq;

	/**
	 * Initializes an animation on a stage
	 * @param customStage The stage associate the animation with
	 */
	protected AbstractAnimation(N _node) {
		super();
		nodeFx 				= _node;

		showAnimation    	= setupShowAnimation();
		dismissAnimation 	= setupDismissAnimation();

		sq = new SequentialTransition(setupShowAnimation(), setupDismissAnimation());
	}

    /**
     *
     * @return a constructed instance of a show animation
     */
	protected abstract Timeline setupShowAnimation();

    /**
     *
     * @return a constructed instance of a dismiss animation
     */
	protected abstract Timeline setupDismissAnimation();

	@Override
	public final N 				getTarget() {
		return nodeFx;
	}

    /**
     * Plays both the show and dismiss animation using a sequential transition object
     *
     * @param dismissDelay How long to delay the start of the dismiss animation
     */
	@Override
	public final void 			playSequential(Duration dismissDelay) {
		sq.getChildren().get(1).setDelay(showAnimation.getTotalDuration().add(dismissDelay));
		sq.play();
	}

    /**
     * Plays the implemented show animation
     */
	@Override
	public final void 			playShowAnimation() {
		showAnimation.play();
	}

    /**
     * Plays the implemented dismiss animation
     */
	@Override
	public final void 			playDismissAnimation() {
		dismissAnimation.play();
	}

    /**
     * Signifies if the stage is current showing
     *
     * @return boolean resultant
     */
	@Override
	public final boolean 		isShowing() {
		return nodeIsShowing;
	}

}
