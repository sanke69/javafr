package fr.javafx.stage.animation.impl;

import fr.javafx.stage.StageExt;
import fr.javafx.stage.animation.AbstractStageAnimation;
import javafx.animation.*;
import javafx.util.Duration;

public class PopupAnimation extends AbstractStageAnimation {

    public PopupAnimation(StageExt _stage) {
    	super(_stage);
    }

    @Override
    protected Timeline setupShowAnimation() {

        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(getStage().yWritableProperty(), getStage().getBottomRight().getY() + 2 * getStage().getHeight());
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);

        KeyValue kv2 = new KeyValue(getStage().yWritableProperty(), getStage().getBottomRight().getY() + getStage().getHeight());
        KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);

        KeyValue kv3 = new KeyValue(getStage().opacityProperty(), 0.0);
        KeyFrame kf3 = new KeyFrame(Duration.ZERO, kv3);

        KeyValue kv4 = new KeyValue(getStage().opacityProperty(), 1.0);
        KeyFrame kf4 = new KeyFrame(Duration.millis(2000), kv4);

        tl.getKeyFrames().addAll(kf1, kf2, kf3, kf4);

        tl.setOnFinished(e -> nodeIsShowing = true);

        return tl;
    }

    @Override
    protected Timeline setupDismissAnimation() {

        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(getStage().yWritableProperty(), getStage().getY() + getStage().getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.millis(2000), kv1);

        KeyValue kv2 = new KeyValue(getStage().opacityProperty(), 0.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);

//        tl.getKeyFrames().addAll(kf1, kf2);

        tl.setOnFinished(e -> {
            nodeIsShowing = false;
//            stage.setPosition(stage.getBottomRight());
            getStage().hide();
//            stage.close();
        });

        return tl;
    }

}