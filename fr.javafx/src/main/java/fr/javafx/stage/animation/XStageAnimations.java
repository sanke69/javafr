package fr.javafx.stage.animation;

import java.util.function.Function;

import fr.javafx.stage.StageExt;
import fr.javafx.stage.animation.impl.FadeAnimation;
import fr.javafx.stage.animation.impl.PopupAnimation;
import fr.javafx.stage.animation.impl.SlideAnimation;

public enum XStageAnimations {
	SLIDE	(SlideAnimation::new), 
	FADE	(FadeAnimation::new), 
	POPUP	(PopupAnimation::new);

	private final Function<StageExt, StageAnimation> newInstance;

	XStageAnimations(Function<StageExt, StageAnimation> newInstance) {
		this.newInstance = newInstance;
	}

	public StageAnimation newInstance(StageExt stage) {
		return newInstance.apply(stage);
	}

}
