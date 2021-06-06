package fr.javafx.stage.animation;

import fr.javafx.animation.AbstractAnimation;
import fr.javafx.stage.StageExt;

public abstract class AbstractStageAnimation extends AbstractAnimation<StageExt> implements StageAnimation {

	/**
	 * Initializes an animation on a stage
	 * @param customStage The stage associate the animation with
	 */
	protected AbstractStageAnimation(StageExt stage) {
		super(stage);
	}

	@Override
	public StageExt getStage() { return getTarget(); }

}
