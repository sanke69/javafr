package fr.javafx.windows.behaviors;

import fr.javafx.io.mouse.DragContext;
import fr.javafx.lang.enums.ExtraSide;
import fr.javafx.windows.WindowControl;
import fr.javafx.windows.WindowStage;
import javafx.scene.input.MouseEvent;

public class DefaultWindowStageBehavior implements WindowBehavior {
	static DragContext 		dragContext = new DragContext();

	final WindowControl 	control;
	final WindowStage 		stage;

    private ExtraSide 		resize;

	public DefaultWindowStageBehavior(WindowControl _control, WindowStage _stage) {
		super();
		control = _control;
		stage   = _stage;

		enable();
	}

	@Override
	public void 	enable() {
//		control.setResizable(false);

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

	public void 	onMouseEnteredEventHandler	(MouseEvent event) { //System.out.println("ENTER !!!");
	}
	public void 	onMouseExitedEventHandler	(MouseEvent event) { //System.out.println("EXIT !!!");
	}
	public void 	onMousePressedEventHandler	(MouseEvent event) { //System.out.println("PRESSED !!!");
        dragContext.setMouse(event, true);
        dragContext.setAnchor(stage.getX(), stage.getY());
	}
	public void 	onMouseDraggedEventHandler	(MouseEvent event) { //System.out.println("DRAGGED !!!");
        dragContext.deltaMouse(event, true);

        if(!resize.isTrue() && control.isMoveable()) {
            dragContext.translateAnchor(dragContext.dx, dragContext.dy);

            stage.setX(dragContext.anchorX());
            stage.setY(dragContext.anchorY());

        } else {
            if(resize.onTop()) {
                double insetOffset = control.getInsets().getTop();
                double yDiff       = insetOffset - event.getSceneY();
                double newHeight   = control.getMinHeight() + yDiff;

                if (newHeight > control.minHeight(0)) {
                	stage.setY(stage.getY() - yDiff);
                	stage.setHeight(newHeight);
                }
            }
            if (resize.onLeft()) {
                double insetOffset = control.getInsets().getLeft();
                double xDiff       = insetOffset - event.getSceneX();
                double newWidth    = control.getPrefWidth() + xDiff;

                if (newWidth > Math.max(control.minWidth(0), control.getContentPane().minWidth(0))) {
                	stage.setX(stage.getX() - xDiff);
                	stage.setWidth(newWidth);
                }
            }

            if (resize.onBottom()) {
                double insetOffset = control.getInsets().getBottom();

                double yDiff = event.getSceneY() - insetOffset;

                double newHeight = yDiff;

                newHeight = Math.max(newHeight, control.minHeight(0));

                if(newHeight < control.maxHeight(0)) {
                	stage.setHeight(newHeight);
                }
            }

            if (resize.onRight()) {
                double insetOffset = control.getInsets().getRight();

                double xDiff = event.getSceneX() - insetOffset;

                double newWidth = xDiff;

                newWidth = Math.max(newWidth, Math.max(control.getContentPane().minWidth(0), control.minWidth(0)));

                if (newWidth < control.maxWidth(0)) {
                	stage.setWidth(newWidth);
                }
            }
        }

        dragContext.setMouse(event, true);
	}
	public void 	onMouseReleasedEventHandler	(MouseEvent event) { //System.out.println("RELEASED !!!");
		;
	}
	public void 	onMouseClickedEventHandler	(MouseEvent event) { //System.out.println("CLICKED !!!");
		;
    }
	public void 	onMouseMovedEventHandler	(MouseEvent event) { //System.out.println("MOVED !!!");
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
//        control.autosize();
    }

}
