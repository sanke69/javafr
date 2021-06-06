package fr.javafx.stage.animation.impl;

import fr.javafx.stage.StageExt;
import fr.javafx.stage.animation.AbstractStageAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FadeAnimation extends AbstractStageAnimation {

    /**
     * Initializes a fade type animation on a stage
     * @param customStage The stage associate the fade animation with
     */
    public FadeAnimation(StageExt customStage) {
    	super(customStage);
    }

    /**
     *
     * @return a constructed instance of a show fade animation
     */
	@Override
    protected Timeline setupShowAnimation() {

        Timeline tl = new Timeline();

        //Sets opacity to 0.0 instantly which is pretty much invisible
        KeyValue kvOpacity = new KeyValue(getStage().opacityProperty(), 0.0);
        KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvOpacity);

        //Sets opacity to 1.0 (fully visible) over the time of 3000 milliseconds.
        KeyValue kvOpacity2 = new KeyValue(getStage().opacityProperty(), 1.0);
        KeyFrame frame2 = new KeyFrame(Duration.millis(3000), kvOpacity2);

        tl.getKeyFrames().addAll(frame1, frame2);

        tl.setOnFinished(e -> nodeIsShowing = true);

        return tl;
    }

    /**
     *
     * @return a constructed instance of a dismiss fade animation
     */
	@Override
    protected Timeline setupDismissAnimation() {

        Timeline tl = new Timeline();

        //At this stage the opacity is already at 1.0

        //Lowers the opacity to 0.0 within 2000 milliseconds
        KeyValue kv1 = new KeyValue(getStage().opacityProperty(), 0.0);
        KeyFrame kf1 = new KeyFrame(Duration.millis(2000), kv1);

        tl.getKeyFrames().addAll(kf1);

        //Action to be performed when the animation has finished
        tl.setOnFinished(e -> {
        	nodeIsShowing = false;
            getStage().close();
            getStage().setPosition(getStage().getBottomRight());
        });

        return tl;
    }

}
