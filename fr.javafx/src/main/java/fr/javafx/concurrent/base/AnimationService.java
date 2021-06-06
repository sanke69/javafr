package fr.javafx.concurrent.base;

import javafx.animation.AnimationTimer;

public abstract class AnimationService {

    protected abstract void timeHandler(long _timestamp);
    
    private final AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long _timestamp) {
        	timeHandler(_timestamp);
        }
    };

    protected void startAnimationTimer() {
    	animationTimer.start();
    }
    protected void stopAnimationTimer() {
    	animationTimer.stop();
    }

}
