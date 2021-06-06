package fr.javafx.scene.control.actionner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Duration;

import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.lang.enums.Direction;

public class FxDPad extends Control {
	public static final String defaultCss = FxDPad.class.getResource("FxDPad.css").toExternalForm();

    public static interface FourWayNavigationListener {
        public void onNavigationAction(Direction direction, double amount);
    }

    private double step = 0.1;

	public FourWayNavigationListener 	listener;
    public Direction 					currentDirection = null;
    public Timeline 					eventFiringTimeline;
    public boolean 						hasFired = false;

    public FxDPad() {
        eventFiringTimeline = new Timeline(
            new KeyFrame(Duration.millis(80), new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) {
                    if (listener != null && currentDirection != null) listener.onNavigationAction(currentDirection, step);
                    hasFired = true;
                }
            })
        );
        eventFiringTimeline.setDelay(Duration.millis(300));
        eventFiringTimeline.setCycleCount(Timeline.INDEFINITE);
/*
        EventHandler<MouseEvent> stopHandler = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (listener != null && currentDirection != null && !hasFired) {
                    listener.onNavigationAction(currentDirection, step);
                }
                currentDirection = null;
                eventFiringTimeline.stop();
            }
        };*/
    }

	@Override
	protected  Skin<FxDPad> 			createDefaultSkin() {
		return new AdvancedSkin<FxDPad>(this, FxDPadVisualDefault::new);
	}

    public FourWayNavigationListener 	getListener() {
        return listener;
    }

    public void 						setListener(FourWayNavigationListener _listener) {
        listener = _listener;
    }
    
}
