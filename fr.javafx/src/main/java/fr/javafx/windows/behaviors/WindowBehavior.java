package fr.javafx.windows.behaviors;

import javafx.scene.input.MouseEvent;

public interface WindowBehavior {

	public void 	enable();
	public void 	disable();

	public void 	onMouseEnteredEventHandler	(MouseEvent event);
	public void 	onMouseExitedEventHandler	(MouseEvent event);
	public void 	onMousePressedEventHandler	(MouseEvent event);
	public void 	onMouseDraggedEventHandler	(MouseEvent event);
	public void 	onMouseReleasedEventHandler	(MouseEvent event);
	public void 	onMouseClickedEventHandler	(MouseEvent event);
	public void 	onMouseMovedEventHandler	(MouseEvent event);

}
