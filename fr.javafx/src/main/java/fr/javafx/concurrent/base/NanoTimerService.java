package fr.javafx.concurrent.base;

import fr.javafx.concurrent.NanoTimerFx;

public abstract class NanoTimerService {
	private double FPS = 25.0;

    protected abstract void onTime();
   
    private final NanoTimerFx nanoTimer = new NanoTimerFx(1000.0 / FPS) {
        @Override
        protected void onSucceeded() {
        	onTime();
        }
    };

    protected void startTimer() {
        nanoTimer.start();
    }
    protected void stopTimer() {
        nanoTimer.cancel();
    }

}
