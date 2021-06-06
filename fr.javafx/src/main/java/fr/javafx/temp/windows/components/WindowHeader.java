package fr.javafx.temp.windows.components;

import java.util.HashMap;
import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;
import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.behaviors.MutableWindowBehavior.EventTask;

public class WindowHeader extends HBox {
	private MutableWindow	window;

	private Label			label;
	private Button			stateButton, minimizeButton, maximizeButton, closeButton;

	private HashMap<Window, Node> dragNodes = new HashMap<Window, Node>();

	static final LogInstance log = LogInstance.getLogger();

	public EventHandler<MouseEvent> onMousePressed, onDragDetected;
	public EventHandler<MouseEvent> onMouseDragged;
	public EventHandler<MouseEvent> onMouseReleased;

	public WindowHeader(final MutableWindow _window) {
		super();

		window = _window;

		// Instanciation
		label          = new Label();
		Pane fillPane  = new Pane(); HBox.setHgrow(fillPane, Priority.ALWAYS);
		stateButton    = new Button();
		minimizeButton = new Button();
		maximizeButton = new Button();
		closeButton    = new Button();

		// Define Bindings
		label.textProperty().bind(_window.titleProperty());
		label.graphicProperty().bind(_window.iconProperty());

		stateButton.visibleProperty().bind(_window.properties().resizableProperty());
		closeButton.visibleProperty().bind(_window.properties().closableProperty());

		// Apply Styles
		label			. getStyleClass().add("dock-title-label");
		closeButton		. getStyleClass().add("dock-close-button");
		stateButton		. getStyleClass().add("dock-state-button");
		minimizeButton 	. getStyleClass().add("dock-minimize-button");
		maximizeButton 	. getStyleClass().add("dock-maximize-button");
		this			. getStyleClass().add("dock-title-bar");

		// Build
		getChildren().addAll(label, fillPane, stateButton, minimizeButton, maximizeButton, closeButton);
	}

	public final MutableWindow getWindow() {
		return window;
	}

	public final Control getStateControl() {
		return stateButton;
	}
	public final Control getMinimizeControl() {
		return minimizeButton;
	}
	public final Control getMaximizeControl() {
		return maximizeButton;
	}
	public final Control getCloseControl() {
		return closeButton;
	}

	public void pickEventTarget(Point2D location, EventTask eventTask, Event explicit) {
		ObservableList<Stage> stages = null; //FXCollections.unmodifiableObservableList(StageHelper.getStages());

		for(Stage targetStage : stages) {
			if(targetStage == this.window.getStageSkin())
				continue;

			eventTask.reset();

			Node dragNode = dragNodes.get(targetStage);

			Parent root = targetStage.getScene().getRoot();
			Stack<Parent> stack = new Stack<Parent>();
			if(root.contains(root.screenToLocal(location.getX(), location.getY())) && !root.isMouseTransparent()) {
				stack.push(root);
			}
			// depth first traversal to find the deepest node or parent with no children
			// that intersects the point of interest
			while(!stack.isEmpty()) {
				Parent parent = stack.pop();
				// if this parent contains the mouse click in screen coordinates in its local bounds
				// then traverse its children
				boolean notFired = true;
				for(Node node : parent.getChildrenUnmodifiable()) {
					if(node.contains(node.screenToLocal(location.getX(), location.getY()))
							&& !node.isMouseTransparent()) {
						if(node instanceof Parent) {
							stack.push((Parent) node);
						} else {
							eventTask.run(node, dragNode);
						}
						notFired = false;
						break;
					}
				}
				// if none of the children fired the event or there were no children
				// fire it with the parent as the target to receive the event
				if(notFired) {log.debug("fire");
					eventTask.run(parent, dragNode);
				}
			}

			if(explicit != null && dragNode != null && eventTask.getExecutions() < 1) {
				Event.fireEvent(dragNode, explicit.copyFor(this, dragNode));
				dragNodes.put(targetStage, null);
			}
		}
	}

}
