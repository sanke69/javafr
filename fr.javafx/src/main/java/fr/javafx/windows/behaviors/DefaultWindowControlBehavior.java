package fr.javafx.windows.behaviors;

import fr.java.patterns.displayable.Selectable;

import fr.javafx.io.mouse.DragContext;
import fr.javafx.lang.enums.ExtraSide;
import fr.javafx.windows.WindowControl;
import fr.javafx.xtra.Clipboard;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;

// Means Multiple WindowControl available
public class DefaultWindowControlBehavior implements WindowBehavior {
	static DragContext 		dragContext = new DragContext();

	final WindowControl 	control;

    private ExtraSide 		resize;

	public DefaultWindowControlBehavior(WindowControl _control) {
		super();
		control = _control;
		enable();
	}

	@Override
	public void 	enable() {
		control.setOnMouseEntered	(this::onMouseEnteredEventHandler);
		control.setOnMouseExited	(this::onMouseExitedEventHandler);
		control.setOnMousePressed	(this::onMousePressedEventHandler);
		control.setOnMouseDragged	(this::onMouseDraggedEventHandler);
		control.setOnMouseReleased	(this::onMouseReleasedEventHandler);
		control.setOnMouseClicked	(this::onMouseClickedEventHandler);
		control.setOnMouseMoved		(this::onMouseMovedEventHandler);
	}
	@Override
	public void 	disable() {
		control.setOnMouseEntered	(null);
		control.setOnMouseExited	(null);
		control.setOnMousePressed	(null);
		control.setOnMouseDragged	(null);
		control.setOnMouseReleased	(null);
		control.setOnMouseClicked	(null);
		control.setOnMouseMoved		(null);
	}

	public void 	onMouseEnteredEventHandler	(MouseEvent event) { System.out.println("ENTER !!!");
	}
	public void 	onMouseExitedEventHandler	(MouseEvent event) { System.out.println("EXIT !!!");
	}
	public void 	onMousePressedEventHandler	(MouseEvent event) { System.out.println("PRESSED !!!");
        final double parentScaleX = control.getParent().localToSceneTransformProperty().getValue().getMxx();
        final double parentScaleY = control.getParent().localToSceneTransformProperty().getValue().getMyy();

        dragContext.setMouse(event);
        dragContext.setAnchor(control.getLayoutX() * parentScaleX, control.getLayoutY() * parentScaleY);

        if(control.isMoveableToFront())
            control.toFront();
	}
	public void 	onMouseDraggedEventHandler	(MouseEvent event) { System.out.println("DRAGGED !!!");
        final double parentScaleX = control.getParent().localToSceneTransformProperty().getValue().getMxx();
        final double parentScaleY = control.getParent().localToSceneTransformProperty().getValue().getMyy();

        Bounds boundsInScene      = control.localToScene(control.getBoundsInLocal());

        double sceneX = boundsInScene.getMinX();
        double sceneY = boundsInScene.getMinY();

        double offsetX = event.getSceneX() - dragContext.x;
        double offsetY = event.getSceneY() - dragContext.y;

        if(!resize.isTrue() && control.isMoveable()) {
            dragContext.translateAnchor(offsetX, offsetY);

            double scaledX = dragContext.anchorX() * 1d / parentScaleX;
            double scaledY = dragContext.anchorY() * 1d / parentScaleY;

            double offsetForAllX = scaledX - control.getLayoutX();
            double offsetForAllY = scaledY - control.getLayoutY();

            control.setLayoutX(scaledX);
            control.setLayoutY(scaledY);

            if(control.isSelected())
                dragSelectedWindows(offsetForAllX, offsetForAllY);

        } else {
            if(resize.onTop()) {
                double insetOffset = control.getInsets().getTop() * 0.5;

                double yDiff = sceneY / parentScaleY
		                        + insetOffset
		                        - event.getSceneY() / parentScaleY;

                double newHeight = control.getPrefHeight() + yDiff;

                if (newHeight > control.minHeight(0)) {
                    control.setLayoutY(control.getLayoutY() - yDiff);
                    control.setPrefHeight(newHeight);
                }
            }
            if (resize.onLeft()) {
                double insetOffset = control.getInsets().getLeft() * 0.5;

                double xDiff = sceneX / parentScaleX
		                        + insetOffset
		                        - event.getSceneX() / parentScaleX;

                double newWidth = control.getPrefWidth() + xDiff;

                if (newWidth > Math.max(control.minWidth(0), control.getContentPane().minWidth(0))) {
                    control.setLayoutX(control.getLayoutX() - xDiff);
                    control.setPrefWidth(newWidth);
                }
            }

            if (resize.onBottom()) {
                double insetOffset = control.getInsets().getBottom() * 0.5;

                double yDiff = event.getSceneY() / parentScaleY
                        		- sceneY / parentScaleY - insetOffset;

                double newHeight = yDiff;

                newHeight = Math.max(newHeight, control.minHeight(0));

                if(newHeight < control.maxHeight(0)) {
                    control.setPrefHeight(newHeight);
                }
            }

            if (resize.onRight()) {
                double insetOffset = control.getInsets().getRight() * 0.5;

                double xDiff = event.getSceneX() / parentScaleX
                        		- sceneX / parentScaleY - insetOffset;

                double newWidth = xDiff;

                newWidth = Math.max(newWidth, Math.max(control.getContentPane().minWidth(0), control.minWidth(0)));

                if (newWidth < control.maxWidth(0)) {
                    control.setPrefWidth(newWidth);
                }
            }
        }

        dragContext.setMouse(event);
	}
	public void 	onMouseReleasedEventHandler	(MouseEvent event) { System.out.println("RELEASED !!!");
		;
	}
	public void 	onMouseClickedEventHandler	(MouseEvent event) { System.out.println("CLICKED !!!");
		;
    }
	public void 	onMouseMovedEventHandler	(MouseEvent event) { System.out.println("MOVED !!!");
        if(control.isMinimized() || !control.isResizable()) {
            resize = ExtraSide.NONE;
            return;
        }

        final double scaleX = control.localToSceneTransformProperty().getValue().getMxx();
        final double scaleY = control.localToSceneTransformProperty().getValue().getMyy();

        final double border = control.getResizableBorderWidth() * scaleX;

        double diffMinX = Math.abs(control.getLayoutBounds().getMinX() - event.getX() + control.getInsets().getLeft());
        double diffMinY = Math.abs(control.getLayoutBounds().getMinY() - event.getY() + control.getInsets().getTop());
        double diffMaxX = Math.abs(control.getLayoutBounds().getMaxX() - event.getX() - control.getInsets().getRight());
        double diffMaxY = Math.abs(control.getLayoutBounds().getMaxY() - event.getY() - control.getInsets().getBottom());

        boolean left    = diffMinX * scaleX < Math.max(border, control.getInsets().getLeft() / 2 * scaleX);
        boolean top     = diffMinY * scaleY < Math.max(border, control.getInsets().getTop() / 2 * scaleY);
        boolean right   = diffMaxX * scaleX < Math.max(border, control.getInsets().getRight() / 2 * scaleX);
        boolean bottom  = diffMaxY * scaleY < Math.max(border, control.getInsets().getBottom() / 2 * scaleY);

        resize = ExtraSide.of(top, bottom, left, right);

        control.setCursor(resize.getCursor());
        control.autosize();
    }



    private void dragSelectedWindows(double offsetForAllX, double offsetForAllY) {
        for(Selectable sN : Clipboard.SelectableItem.getSelectedItems()) {
            if (sN == control || !(sN instanceof WindowControl))
                continue;

            WindowControl selectedWindow = (WindowControl) sN;

            if(control.getParent().equals(selectedWindow.getParent())) {
                selectedWindow.setLayoutX(selectedWindow.getLayoutX() + offsetForAllX);
                selectedWindow.setLayoutY(selectedWindow.getLayoutY() + offsetForAllY);
            }
        }
    }
    private void selectedWindowsToFront() {
        for(Selectable sN : Clipboard.SelectableItem.getSelectedItems()) {
            if (sN == control || !(sN instanceof WindowControl))
                continue;

            WindowControl selectedWindow = (WindowControl) sN;

            if (control.getParent().equals(selectedWindow.getParent()) && selectedWindow.isMoveableToFront()) {
                selectedWindow.toFront();
            }
        }
    }

}
