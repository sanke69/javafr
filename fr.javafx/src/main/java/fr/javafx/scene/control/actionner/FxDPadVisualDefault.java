package fr.javafx.scene.control.actionner;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import fr.javafx.behavior.Visual;
import fr.javafx.lang.enums.Direction;

public class FxDPadVisualDefault extends GridPane implements Visual<FxDPad> {
	FxDPad control;

	public FxDPadVisualDefault(FxDPad _control) {
		super();
		control = _control;
		setMinSize(50, 50);
		setPrefSize(50, 50);
		setMaxSize(50, 50);

		build();
	}
	
	@Override
	public FxDPad getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	private void build() {
		getStylesheets().add(FxDPad.defaultCss);
        getStyleClass().addAll("button", "d-pad");

        Region upIcon     = new Region();
        upIcon.getStyleClass().add("up");
        Region downIcon   = new Region();
        downIcon.getStyleClass().add("down");
        Region leftIcon   = new Region();
        leftIcon.getStyleClass().add("left");
        Region rightIcon  = new Region();
        rightIcon.getStyleClass().add("right");
        Region centerIcon = new Region();
        centerIcon.getStyleClass().add("center");

        GridPane.setConstraints(upIcon,1,0);
        GridPane.setConstraints(leftIcon,0,1);
        GridPane.setConstraints(centerIcon,1,1);
        GridPane.setConstraints(rightIcon,2,1);
        GridPane.setConstraints(downIcon, 1, 2);

        getChildren().addAll(upIcon,downIcon,leftIcon,rightIcon,centerIcon);

        setOnMouseEntered((evt) -> requestFocus());
        
        upIcon.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                    	control.currentDirection = Direction.UP;
                    	control.hasFired = false;
                    	control.eventFiringTimeline.playFromStart();
                    }
                });
        downIcon.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                    	control.currentDirection = Direction.DOWN;
                    	control. hasFired = false;
                    	control.eventFiringTimeline.playFromStart();
                    }
                });
        leftIcon.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                    	control.currentDirection = Direction.LEFT;
                    	control.hasFired = false;
                    	control.eventFiringTimeline.playFromStart();
                    }
                });
        rightIcon.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                    	control.currentDirection = Direction.RIGHT;
                    	control.hasFired = false;
                    	control.eventFiringTimeline.playFromStart();
                    }
                });
        centerIcon.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                    	control.currentDirection = Direction.CENTER;
                    	control.hasFired = false;
                    	control.eventFiringTimeline.playFromStart();
                    }
                });

        EventHandler<MouseEvent> stopHandler = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (control.listener != null && control.currentDirection != null && !control.hasFired) {
                	control.listener.onNavigationAction(control.currentDirection,1);
                }
                control.currentDirection = null;
                control.eventFiringTimeline.stop();
            }
        };
        upIcon		.setOnMouseReleased(stopHandler);
        downIcon	.setOnMouseReleased(stopHandler);
        rightIcon	.setOnMouseReleased(stopHandler);
        leftIcon	.setOnMouseReleased(stopHandler);
        centerIcon	.setOnMouseReleased(stopHandler);
	}

}
