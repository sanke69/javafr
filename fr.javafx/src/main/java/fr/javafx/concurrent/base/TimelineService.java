package fr.javafx.concurrent.base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public abstract class TimelineService {
	private double FPS = 25.0;
    private Timeline timeline;

    protected abstract void eventHandler(ActionEvent _ae);

    protected void startTimeline() {
    	if(timeline == null) {
	        double duration = 1000.0 / FPS;

    		timeline = new Timeline();
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), this::eventHandler));
    	}

        timeline.playFromStart();
    }
    protected void stopTimeline() {
        timeline.stop();
    }

}
