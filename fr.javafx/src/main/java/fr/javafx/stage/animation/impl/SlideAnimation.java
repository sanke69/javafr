package fr.javafx.stage.animation.impl;

import fr.javafx.stage.StageExt;
import fr.javafx.stage.animation.AbstractStageAnimation;
import javafx.animation.*;
import javafx.util.Duration;

public class SlideAnimation extends AbstractStageAnimation {

    public SlideAnimation(StageExt customStage) {
    	super(customStage);
    }

    protected Timeline setupShowAnimation() {

        Timeline tl = new Timeline();

        //Sets the x location of the tray off the screen
        double offScreenX = getStage().getBottomRight().getX();
        KeyValue kvX = new KeyValue(getStage().xWritableProperty(), offScreenX);
        KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvX);

        //Animates the Tray onto the screen and interpolates at a tangent for 300 millis
        Interpolator interpolator = Interpolator.TANGENT(Duration.millis(300), 50);
        KeyValue kvInter = new KeyValue(getStage().xWritableProperty(), getStage().getBottomRight().getX(), interpolator);
        KeyFrame frame2 = new KeyFrame(Duration.millis(1300), kvInter);

        //Sets opacity to 0 instantly
        KeyValue kvOpacity = new KeyValue(getStage().opacityProperty(), 0.0);
        KeyFrame frame3 = new KeyFrame(Duration.ZERO, kvOpacity);

        //Increases the opacity to fully visible whilst moving in the space of 1000 millis
        KeyValue kvOpacity2 = new KeyValue(getStage().opacityProperty(), 1.0);
        KeyFrame frame4 = new KeyFrame(Duration.millis(1000), kvOpacity2);

        tl.getKeyFrames().addAll(frame1, frame2, frame3, frame4);

        tl.setOnFinished(e -> nodeIsShowing = true);

        return tl;
    }

    protected Timeline setupDismissAnimation() {

        Timeline tl = new Timeline();

        double offScreenX = getStage().getBottomRight().getX();
        Interpolator interpolator = Interpolator.TANGENT(Duration.millis(300), 50);
        double trayPadding = 3;

        //The destination X location for the stage. Which is off the users screen
        //Since the tray has some padding, we want to hide that too
        KeyValue kvX = new KeyValue(getStage().xWritableProperty(), offScreenX + trayPadding, interpolator);
        KeyFrame frame1 = new KeyFrame(Duration.millis(1400), kvX);

        //Change the opacity level to 0.4 over the duration of 2000 millis
        KeyValue kvOpacity = new KeyValue(getStage().opacityProperty(), 0.4);
        KeyFrame frame2 = new KeyFrame(Duration.millis(2000), kvOpacity);

        tl.getKeyFrames().addAll(frame1, frame2);

        tl.setOnFinished(e -> {
            nodeIsShowing = false;
            getStage().close();
            getStage().setPosition(getStage().getBottomRight());
        });

        return tl;
    }

}