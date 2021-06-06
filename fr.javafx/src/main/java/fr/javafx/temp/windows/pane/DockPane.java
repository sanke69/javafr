package fr.javafx.temp.windows.pane;

import java.util.Stack;

import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.MutableWindowPane;
import fr.javafx.temp.windows.behaviors.DockPaneBehavior;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.events.MutableWindowEvent;
import fr.javafx.temp.windows.skins.DockedWindowSkin;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.util.Duration;

public class DockPane extends MutableWindowPane {
	static final LogInstance log = LogInstance.getLogger();

	public boolean receivedEnter = false;

	public Node		dockNodeDrag;
	public Node		dockAreaDrag;
	public Position	dockPosDrag;

	public Node root;

	DockOverlay overlay;

	private class DockNodeEventHandler implements EventHandler<MutableWindowEvent> {
		private Node node = null;

		public DockNodeEventHandler(Node node) {
			this.node = node;
		}

		@Override
		public void handle(MutableWindowEvent event) {
			DockPane.this.dockNodeDrag = node;
		}
	}
	private ObservableMap<Node, DockNodeEventHandler> dockNodeEventFilters = FXCollections.observableHashMap();

	public DockPane() {
		super();

		this.addEventFilter(MutableWindowEvent.ANY, new EventHandler<MutableWindowEvent>() {
			@Override
			public void handle(MutableWindowEvent event) {
				if(event.getEventType() == MutableWindowEvent.DOCK_ENTER) {
					DockPane.this.receivedEnter = true;
				} else if(event.getEventType() == MutableWindowEvent.DOCK_OVER) {
					DockPane.this.dockNodeDrag = null;
				} else if(event.getEventType() == MutableWindowEvent.DOCK_RELEASED) {
					log.debug("RELEASED !!!");
				} else if(event.getEventType() == MutableWindowEvent.DOCK_RELEASED) {
					log.debug("EXIT !!!");
				}
			}
		});

		overlay = new DockOverlay(this);

		addEventHandler(MutableWindowEvent.DOCK_ENTER,    DockPaneBehavior.onEntering(this, overlay));
		addEventHandler(MutableWindowEvent.DOCK_OVER,     DockPaneBehavior.onOver(this, overlay));
		addEventHandler(MutableWindowEvent.DOCK_RELEASED, DockPaneBehavior.onRelease(this, overlay));
		addEventHandler(MutableWindowEvent.DOCK_EXIT,     DockPaneBehavior.onExit(this, overlay));
	}

	public final static String getDefaultUserAgentStyleheet() {
		return DockPane.class.getResource("default.css").toExternalForm();
	}
	public final static void initializeDefaultUserAgentStylesheet() {
		//StyleManager.getInstance().addUserAgentStylesheet(DockPane.class.getResource("default.css").toExternalForm());
	}

	public void dock(Node node, Position dockPos, Node sibling) {
		DockNodeEventHandler dockNodeEventHandler = new DockNodeEventHandler(node);
		dockNodeEventFilters.put(node, dockNodeEventHandler);
		node.addEventFilter(MutableWindowEvent.DOCK_OVER, dockNodeEventHandler);

		SplitPane split = (SplitPane) root;
		if(split == null) {
			split = new SplitPane();
			split.getItems().add(node);
			root = split;
			this.getChildren().add(root);
			return;
		}

		// find the parent of the sibling
		if(sibling != null && sibling != root) {
			Stack<Parent> stack = new Stack<Parent>();
			stack.push((Parent) root);
			while(!stack.isEmpty()) {
				Parent parent = stack.pop();

				ObservableList<Node> children = parent.getChildrenUnmodifiable();

				if(parent instanceof SplitPane) {
					SplitPane splitPane = (SplitPane) parent;
					children = splitPane.getItems();
				}

				for(int i = 0; i < children.size(); i++) {
					if(children.get(i) == sibling) {
						split = (SplitPane) parent;
					} else if(children.get(i) instanceof Parent) {
						stack.push((Parent) children.get(i));
					}
				}
			}
		}

		Orientation requestedOrientation = 
				(dockPos == Position.LEFT || dockPos == Position.RIGHT)
				? Orientation.HORIZONTAL : Orientation.VERTICAL;

		// if the orientation is different then reparent the split pane
		if(split.getOrientation() != requestedOrientation) {
			if(split.getItems().size() > 1) {
				SplitPane splitPane = new SplitPane();
				if(split == root && sibling == root) {
					this.getChildren().set(this.getChildren().indexOf(root), splitPane);
					splitPane.getItems().add(split);
					root = splitPane;
				} else {
					split.getItems().set(split.getItems().indexOf(sibling), splitPane);
					splitPane.getItems().add(sibling);
				}

				split = splitPane;
			}
			split.setOrientation(requestedOrientation);
		}

		// finally dock the node to the correct split pane
		ObservableList<Node> splitItems = split.getItems();

		double magnitude = 0;

		if(splitItems.size() > 0) {
			if(split.getOrientation() == Orientation.HORIZONTAL) {
				for(Node splitItem : splitItems) {
					magnitude += splitItem.prefWidth(0);
				}
			} else {
				for(Node splitItem : splitItems) {
					magnitude += splitItem.prefHeight(0);
				}
			}
		}

		if(dockPos == Position.LEFT || dockPos == Position.TOP) {
			int relativeIndex = 0;
			if(sibling != null && sibling != root) {
				relativeIndex = splitItems.indexOf(sibling);
			}

			splitItems.add(relativeIndex, node);

			if(splitItems.size() > 1) {
				if(split.getOrientation() == Orientation.HORIZONTAL) {
					split.setDividerPosition(relativeIndex,
							node.prefWidth(0) / (magnitude + node.prefWidth(0)));
				} else {
					split.setDividerPosition(relativeIndex,
							node.prefHeight(0) / (magnitude + node.prefHeight(0)));
				}
			}
		} else if(dockPos == Position.RIGHT || dockPos == Position.BOTTOM) {
			int relativeIndex = splitItems.size();
			if(sibling != null && sibling != root) {
				relativeIndex = splitItems.indexOf(sibling) + 1;
			}

			splitItems.add(relativeIndex, node);
			if(splitItems.size() > 1) {
				if(split.getOrientation() == Orientation.HORIZONTAL) {
					split.setDividerPosition(relativeIndex - 1,
							1 - node.prefWidth(0) / (magnitude + node.prefWidth(0)));
				} else {
					split.setDividerPosition(relativeIndex - 1,
							1 - node.prefHeight(0) / (magnitude + node.prefHeight(0)));
				}
			}
		}

	}
	public void dock(Node node, Position dockPos) {
		dock(node, dockPos, root);
	}
	public void undock(DockedWindowSkin node) {
		DockNodeEventHandler dockNodeEventHandler = dockNodeEventFilters.get(node);
		node.removeEventFilter(MutableWindowEvent.DOCK_OVER, dockNodeEventHandler);
		dockNodeEventFilters.remove(node);

		// depth first search to find the parent of the node
		Stack<Parent> findStack = new Stack<Parent>();
		findStack.push((Parent) root);
		while(!findStack.isEmpty()) {
			Parent parent = findStack.pop();

			ObservableList<Node> children = parent.getChildrenUnmodifiable();

			if(parent instanceof SplitPane) {
				SplitPane split = (SplitPane) parent;
				children = split.getItems();
			}

			for(int i = 0; i < children.size(); i++) {
				if(children.get(i) == node) {
					children.remove(i);

					// start from the root again and remove any SplitPane's with no children in them
					Stack<Parent> clearStack = new Stack<Parent>();
					clearStack.push((Parent) root);
					while(!clearStack.isEmpty()) {
						parent = clearStack.pop();

						children = parent.getChildrenUnmodifiable();

						if(parent instanceof SplitPane) {
							SplitPane split = (SplitPane) parent;
							children = split.getItems();
						}

						for(i = 0; i < children.size(); i++) {
							if(children.get(i) instanceof SplitPane) {
								SplitPane split = (SplitPane) children.get(i);
								if(split.getItems().size() < 1) {
									children.remove(i);
									continue;
								} else {
									clearStack.push(split);
								}
							}

						}
					}

					return;
				} else if(children.get(i) instanceof Parent) {
					findStack.push((Parent) children.get(i));
				}
			}
		}
	}

}
