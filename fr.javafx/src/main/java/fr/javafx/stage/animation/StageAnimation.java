package fr.javafx.stage.animation;

import fr.javafx.animation.Animation;
import fr.javafx.stage.StageExt;

public interface StageAnimation extends Animation<StageExt> {

    default StageExt 	getTarget() { return getStage(); }
	StageExt 			getStage();

}
