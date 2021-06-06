package fr.javafx.temp.windows.skins;

import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.MutableWindowSkin;
import fr.javafx.temp.windows.components.WindowHeader;
import fr.javafx.temp.windows.components.WindowProperties.Mode;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.pane.DockPane;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DockedWindowSkin extends VBox implements MutableWindowSkin.Docked {
	static final LogInstance log = LogInstance.getLogger();

	private DockPane		dockPane;

	MutableWindow window;

	public DockedWindowSkin(MutableWindow _window) {
		super();
		window = _window;
/*
		getChildren().addAll(window.getHeader(), window.getContent());
		VBox.setVgrow(window.getContent(), Priority.ALWAYS);
*/
		this.getStyleClass().add("dock-node");
	}

	@Override
	public MutableWindow getControl() {
		return window;
	}

	@Override
	public void fill() {
		getChildren().addAll(window.getHeader(), window.getContent());
		VBox.setVgrow(window.getContent(), Priority.ALWAYS);
	}
	@Override
	public void release() {
		getChildren().removeAll(window.getHeader(), window.getContent());
	}

	public void dock(DockPane dockPane, Position dockPos, Node sibling) {
		dockImpl(dockPane);
		dockPane.dock(this, dockPos, sibling);
	}

	public void dock(DockPane dockPane, Position dockPos) {
		dockImpl(dockPane);
		dockPane.dock(this, dockPos);
	}

	private final void dockImpl(DockPane dockPane) {

		this.dockPane = dockPane;
//		getControl().getSkin().release();
//		getControl().setMode(Mode.DOCKED);
//		getControl().setSkin(this);
//		fill();
	}

	public void undock() {
		if(dockPane != null) {
			dockPane.undock(this);
		}
//		this.dockedProperty.set(false);
	}

	public void close() {
		undock();
	}

	public void setDockTitleBar(WindowHeader dockTitleBar) {
		if(dockTitleBar != null) {
			if(this.window.getHeader() != null) {
				this.getChildren().set(this.getChildren().indexOf(window.getHeader()), dockTitleBar);
			} else {
				this.getChildren().add(0, dockTitleBar);
			}
		} else {
			this.getChildren().remove(window.getHeader());
		}
	}
	public final WindowHeader getDockTitleBar() {
		return window.getHeader();
	}

	public void setFloating(boolean floating, Point2D translation) {
		log.debug("!!! setFloating !!!");
		if(floating && !getControl().status().isFloating()) {
			/*
			// position the new stage relative to the old scene offset
			Point2D floatScene = this.localToScene(0, 0);
			Point2D floatScreen = this.localToScreen(0, 0);

			// setup window stage
			window.getHeader().setVisible(this.isCustomTitleBar());
			window.getHeader().setManaged(this.isCustomTitleBar());

			if(this.isDocked()) {
				this.undock();
			}

			stage = new Stage();
//			stage.titleProperty().bind(titleProperty);
			stage.titleProperty().bind(window.titleProperty());
			if(dockPane != null && dockPane.getScene() != null
					&& dockPane.getScene().getWindow() != null) {
				stage.initOwner(dockPane.getScene().getWindow());
			}

			stage.initStyle(stageStyle);

			// offset the new stage to cover exactly the area the dock was local to the scene
			// this is useful for when the user presses the + sign and we have no information
			// on where the mouse was clicked
			Point2D stagePosition;
			if(this.isDecorated()) {
				Window owner = stage.getOwner();
				stagePosition = floatScene.add(new Point2D(owner.getX(), owner.getY()));
			} else {
				stagePosition = floatScreen;
			}
			if(translation != null) {
				stagePosition = stagePosition.add(translation);
			}

			// the border pane allows the dock node to
			// have a drop shadow effect on the border
			// but also maintain the layout of contents
			// such as a tab that has no content
			borderPane = new BorderPane();
			borderPane.getStyleClass().add("dock-node-border");
			borderPane.setCenter(this);

			Scene scene = new Scene(borderPane);

			// apply the floating property so we can get its padding size
			// while it is floating to offset it by the drop shadow
			// this way it pops out above exactly where it was when docked
			this.floatingProperty.set(floating);
			this.applyCss();

			// apply the border pane css so that we can get the insets and
			// position the stage properly
			borderPane.applyCss();
			Insets insetsDelta = borderPane.getInsets();

			double insetsWidth = insetsDelta.getLeft() + insetsDelta.getRight();
			double insetsHeight = insetsDelta.getTop() + insetsDelta.getBottom();

			stage.setX(stagePosition.getX() - insetsDelta.getLeft());
			stage.setY(stagePosition.getY() - insetsDelta.getTop());

			stage.setMinWidth(borderPane.minWidth(this.getHeight()) + insetsWidth);
			stage.setMinHeight(borderPane.minHeight(this.getWidth()) + insetsHeight);

			borderPane.setPrefSize(this.getWidth() + insetsWidth, this.getHeight() + insetsHeight);

			stage.setScene(scene);

			if(stageStyle == StageStyle.TRANSPARENT) {
				scene.setFill(null);
			}

			stage.setResizable(this.isStageResizable());
			if(this.isStageResizable()) {
				stage.addEventFilter(MouseEvent.MOUSE_PRESSED, this);
				stage.addEventFilter(MouseEvent.MOUSE_MOVED, this);
				stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, this);
			}

			// we want to set the client area size
			// without this it subtracts the native border sizes from the scene
			// size
			stage.sizeToScene();

			stage.show();
			*/
		} else if(!floating && getControl().status().isFloating()) {
			getControl().staged(null);
/*
			stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
			stage.removeEventFilter(MouseEvent.MOUSE_MOVED, this);
			stage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, this);
*/
			//stage.close();
		}
	}/*
	public void setFloating(boolean floating) {
		setFloating(floating, null);
	}*/

	public final DockPane getDockPane() {
		return dockPane;
	}
/*
	private BooleanProperty customTitleBarProperty = new SimpleBooleanProperty(true) {
		@Override
		public String getName() {
			return "customTitleBar";
		}
	};

	public final void setUseCustomTitleBar(boolean useCustomTitleBar) {
		if(getControl().status().isFloating()) {
			window.getHeader().setVisible(useCustomTitleBar);
			window.getHeader().setManaged(useCustomTitleBar);
		}
		this.customTitleBarProperty.set(useCustomTitleBar);
	}
	public final boolean isCustomTitleBar() {
		return customTitleBarProperty.get();
	}
	public final BooleanProperty customTitleBarProperty() {
		return customTitleBarProperty;
	}
*/
/*
	private BooleanProperty stageResizableProperty = new SimpleBooleanProperty(true) {
		@Override
		public String getName() {
			return "resizable";
		}
	};

	public final void setStageResizable(boolean resizable) {
		stageResizableProperty.set(resizable);
	}
	public final boolean isStageResizable() {
		return stageResizableProperty.get();
	}
	public final BooleanProperty resizableProperty() {
		return stageResizableProperty;
	}
*/
/*
	private BooleanProperty dockedProperty = new SimpleBooleanProperty(false) {
		@Override
		protected void invalidated() {
			if(get()) {
				if(window.getHeader() != null) {
					window.getHeader().setVisible(true);
					window.getHeader().setManaged(true);
				}
			}

			pseudoClassStateChanged(DOCKED_PSEUDO_CLASS, get());
		}

		@Override
		public String getName() {
			return "docked";
		}
	};

	public final boolean isDocked() {
		return dockedProperty.get();
	}
	public final BooleanProperty dockedProperty() {
		return dockedProperty;
	}
*/
	public Point2D sizeLast;
	public boolean sizeWest = false;
	public boolean sizeEast = false;
	public boolean sizeNorth = false;
	public boolean sizeSouth = false;

	public boolean isMouseResizeZone() {
		return sizeWest || sizeEast || sizeNorth || sizeSouth;
	}
/*
	@Override
	public void handle(MouseEvent event) {
		Cursor cursor = Cursor.DEFAULT;

		// TODO: use escape to cancel resize/drag operation like visual studio
		if(!this.isFloating() || !this.isStageResizable()) {
			return;
		}

		if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
			sizeLast = new Point2D(event.getScreenX(), event.getScreenY());
		/* else if(event.getEventType() == MouseEvent.MOUSE_MOVED) {
			Insets insets = borderPane.getPadding();

			sizeWest = event.getX() < insets.getLeft();
			sizeEast = event.getX() > borderPane.getWidth() - insets.getRight();
			sizeNorth = event.getY() < insets.getTop();
			sizeSouth = event.getY() > borderPane.getHeight() - insets.getBottom();

			if(sizeWest) {
				if(sizeNorth) {
					cursor = Cursor.NW_RESIZE;
				} else if(sizeSouth) {
					cursor = Cursor.SW_RESIZE;
				} else {
					cursor = Cursor.W_RESIZE;
				}
			} else if(sizeEast) {
				if(sizeNorth) {
					cursor = Cursor.NE_RESIZE;
				} else if(sizeSouth) {
					cursor = Cursor.SE_RESIZE;
				} else {
					cursor = Cursor.E_RESIZE;
				}
			} else if(sizeNorth) {
				cursor = Cursor.N_RESIZE;
			} else if(sizeSouth) {
				cursor = Cursor.S_RESIZE;
			}

			this.getScene().setCursor(cursor);
		} else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED && this.isMouseResizeZone()) {
			Point2D sizeCurrent = new Point2D(event.getScreenX(), event.getScreenY());
			Point2D sizeDelta = sizeCurrent.subtract(sizeLast);

			double newX = stage.getX(), newY = stage.getY(), newWidth = stage.getWidth(),
					newHeight = stage.getHeight();

			if(sizeNorth) {
				newHeight -= sizeDelta.getY();
				newY += sizeDelta.getY();
			} else if(sizeSouth) {
				newHeight += sizeDelta.getY();
			}

			if(sizeWest) {
				newWidth -= sizeDelta.getX();
				newX += sizeDelta.getX();
			} else if(sizeEast) {
				newWidth += sizeDelta.getX();
			}

			// TODO: find a way to do this synchronously and eliminate the flickering of moving the stage
			// around, also file a bug report for this feature if a work around can not be found this
			// primarily occurs when dragging north/west but it also appears in native windows and Visual
			// Studio, so not that big of a concern.
			// Bug report filed:
			// https://bugs.openjdk.java.net/browse/JDK-8133332
			double currentX = sizeLast.getX(), currentY = sizeLast.getY();
			if(newWidth >= stage.getMinWidth()) {
				stage.setX(newX);
				stage.setWidth(newWidth);
				currentX = sizeCurrent.getX();
			}

			if(newHeight >= stage.getMinHeight()) {
				stage.setY(newY);
				stage.setHeight(newHeight);
				currentY = sizeCurrent.getY();
			}
			sizeLast = new Point2D(currentX, currentY);
			// we do not want the title bar getting these events
			// while we are actively resizing
			if(sizeNorth || sizeSouth || sizeWest || sizeEast) {
				event.consume();
			}
		}* /
	}
*/
}
