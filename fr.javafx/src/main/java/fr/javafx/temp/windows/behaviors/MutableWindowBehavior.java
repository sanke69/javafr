package fr.javafx.temp.windows.behaviors;

import java.util.HashMap;

import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.log.LogInstance;
import fr.java.sdk.math.Points;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.components.WindowHeader;
import fr.javafx.temp.windows.components.WindowProperties.Mode;
import fr.javafx.temp.windows.events.MutableWindowEvent;
import fr.javafx.temp.windows.pane.DockPane;
import fr.javafx.temp.windows.skins.DockedWindowSkin;
import fr.javafx.temp.windows.skins.FloatingWindowSkin;
import fr.javafx.temp.windows.skins.StagedWindowSkin;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MutableWindowBehavior {
	static final LogInstance log = LogInstance.getLogger();

	public static abstract class EventTask {
		protected int executions = 0; // TODO:: REMOVE !!!

		public abstract void run(Node node, Node dragNode);

		public int getExecutions() {
			return executions;
		}
		public void reset() {
			executions = 0;
		}
	}

	private static HashMap<Window, Node> dragNodes = new HashMap<Window, Node>();

	private static final int borderMagnet = 5;

	static private boolean RESIZE_BOTTOM;
	static private boolean RESIZE_RIGHT;
	static Bounds       bndDefault = null, bndCurrent = null; 

	static private Point2D dragDelta;

	public static void defineControlBehaviors(MutableWindow _window) {
		WindowHeader header = _window.getHeader();

		header.getStateControl()	.setOnMouseReleased(MutableWindowBehavior.stateControlAction(_window));
		header.getMaximizeControl()	.setOnMouseReleased(MutableWindowBehavior.maximizeControlAction(_window));
		header.getMinimizeControl()	.setOnMouseReleased(MutableWindowBehavior.minimizeControlAction(_window));
		header.getCloseControl()	.setOnMouseReleased(MutableWindowBehavior.closeControlAction(_window));
	}
	public static void undefineControlBehaviors(MutableWindow _window) {
		WindowHeader header = _window.getHeader();

		header.getStateControl()	.setOnMouseReleased(null);
		header.getMaximizeControl()	.setOnMouseReleased(null);
		header.getMinimizeControl()	.setOnMouseReleased(null);
		header.getCloseControl()	.setOnMouseReleased(null);
	}

	private static EventHandler<MouseEvent> stateControlAction(final MutableWindow _window) {
		return (e) -> {
			switch(_window.getMode()) {
			case DOCKED 	: _window.staged(null); break;
			case FLOATING	: _window.staged(null); break;
			case STAGED		: break;
			case DISABLED	: break;
			case HIDDEN		: break;
			case UNKNOWN	: break;
			default			: break;
			};
		};
	}
	private static EventHandler<MouseEvent> maximizeControlAction(final MutableWindow _window) {
//		return (e) -> _window.maximize( !_window.status().isFullScreen() );
		return (e) -> {};
	}
	private static EventHandler<MouseEvent> minimizeControlAction(final MutableWindow _window) {
//		return (e) -> _window.minimize( true );
		return (e) -> {};
	}
	private static EventHandler<MouseEvent> closeControlAction(final MutableWindow _window) {
		return (e) -> _window.close();
	}

	
	

	public static EventHandler<MouseEvent> onMousePressed(final MutableWindow _window) {
		return (e) -> {
			log.debug("MutableWindowBehavior:: onMousePressed");
			// IF DOCKED
			if(_window.status().isDocked()) {
				if(e.getClickCount() < 2 && e.getButton() == MouseButton.PRIMARY)
					_window.status().setDragOrigin(Points.of(e.getX(), e.getY()));
				
				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				dragDelta = Points.of((float) (_window.getFloatSkin().getLayoutX() - e.getScreenX()), (float) (_window.getFloatSkin().getLayoutY() - e.getScreenY()));
				_window.getFloatSkin().toFront();
				
				return ;
			}
				
			// IF STAGED
			if(_window.status().isStaged()) {
				if(e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY)
					_window.getStageSkin().setMaximized(!_window.status().isMaximized());
				
				return ;
			}
		};
	}
	public static EventHandler<MouseEvent> onMouseMoved(final MutableWindow _window) {
		return (e) -> {
//			log.debug("MutableWindowBehavior:: onMouseMoved");
			// IF DOCKED
			if(_window.status().isDocked()) {
				DockedWindowSkin skin = _window.getDockSkin();

				//			if(event.getSource().equals(_skin))
				//				System.out.println(_skin.borderPane);

//			Insets insets = _skin.borderPane.getPadding();
				Cursor cursor = Cursor.DEFAULT;

				skin.sizeWest = e.getX() < 0/*insets.getLeft()*/;
				skin.sizeEast = e.getX() > skin.widthProperty().get()/* _skin.borderPane.getWidth() - insets.getRight()*/;
				skin.sizeNorth = e.getY() < 0/*insets.getTop()*/;
				skin.sizeSouth = e.getY() > skin.heightProperty().get()/* _skin.borderPane.getHeight() - insets.getBottom()*/;
	/** /

				Insets insets = _window.getStageSkin().getPane().getPadding();
				Cursor cursor = Cursor.DEFAULT;

				_skin.sizeWest = event.getX() < insets.getLeft();
				_skin.sizeEast = event.getX() > _window.getStageSkin().getPane().getWidth() - insets.getRight();
				_skin.sizeNorth = event.getY() < insets.getTop();
				_skin.sizeSouth = event.getY() > _window.getStageSkin().getPane().getHeight() - insets.getBottom();
	*/
				
				if(skin.sizeWest) {
					if(skin.sizeNorth) {
						cursor = Cursor.NW_RESIZE;
					} else if(skin.sizeSouth) {
						cursor = Cursor.SW_RESIZE;
					} else {
						cursor = Cursor.W_RESIZE;
					}
				} else if(skin.sizeEast) {
					if(skin.sizeNorth) {
						cursor = Cursor.NE_RESIZE;
					} else if(skin.sizeSouth) {
						cursor = Cursor.SE_RESIZE;
					} else {
						cursor = Cursor.E_RESIZE;
					}
				} else if(skin.sizeNorth) {
					cursor = Cursor.N_RESIZE;
				} else if(skin.sizeSouth) {
					cursor = Cursor.S_RESIZE;
				}

				skin.getScene().setCursor(cursor);

				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				FloatingWindowSkin skin = (FloatingWindowSkin) _window.getSkin();

				final int deltaMouseY = 140;
				bndCurrent = skin.boundsInParentProperty().get();

				if (Math.abs(e.getSceneX() - bndCurrent.getMaxX()) < borderMagnet && Math.abs(e.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) < borderMagnet) {
					RESIZE_RIGHT = true;
					RESIZE_BOTTOM = true;
					skin.setCursor(Cursor.SE_RESIZE);
				} else if (Math.abs(e.getSceneX() - bndCurrent.getMaxX()) < borderMagnet && Math.abs(e.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) > borderMagnet) {
					RESIZE_RIGHT = true;
					RESIZE_BOTTOM = false;
					skin.setCursor(Cursor.W_RESIZE);
				} else if (Math.abs(e.getSceneX() - bndCurrent.getMaxX()) > borderMagnet && Math.abs(e.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) < borderMagnet) {
					RESIZE_RIGHT = false;
					RESIZE_BOTTOM = true;
					skin.setCursor(Cursor.S_RESIZE);
				} else {
					RESIZE_BOTTOM = false;
					RESIZE_RIGHT = false;
					skin.setCursor(Cursor.DEFAULT);
				}

				return ;
			}
				
			// IF STAGED
			if(_window.status().isStaged()) {

				return ;
			}
		};
	}
	public static EventHandler<MouseEvent> onMouseReleased(final MutableWindow _window) {
		return (e) -> {
			log.debug("MutableWindowBehavior:: onMouseReleased");

			// IF DOCKED
			if(_window.status().isDocked()) {
				attachToDockPane(_window, Points.of(e.getX(), e.getY()), Points.of(e.getScreenX(), e.getScreenY()));
				/*
				_window.status().setDragging(false);

				MutableWindowEvent dockReleasedEvent = new MutableWindowEvent(
						_window.getHeader(),
						MutableWindowEvent.NULL_SOURCE_TARGET,
						MutableWindowEvent.DOCK_RELEASED,
						e.getX(), e.getY(), e.getScreenX(), e.getScreenY(),
						null);

				EventTask eventTask = new EventTask() {
					@Override
					public void run(Node node, Node dragNode) {
						executions++;
						if(dragNode != node)
							Event.fireEvent(node, dockReleasedEvent.copyFor(_window.getHeader(), node));
						Event.fireEvent(node, dockReleasedEvent.copyFor(_window.getHeader(), node));
					}
				};

				_window.getHeader().pickEventTarget(new Point2D(e.getScreenX(), e.getScreenY()), eventTask, null);

				dragNodes.clear();

				// Remove temporary event handler for bug mentioned above.
				DockPane dockPane = _window.getDockSkin().getDockPane();
				if(dockPane != null) {
					dockPane.removeEventFilter(MouseEvent.MOUSE_DRAGGED, _window.getHeader().onMouseDragged);
					dockPane.removeEventFilter(MouseEvent.MOUSE_RELEASED, _window.getHeader().onMouseReleased);
				}*/

				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				log.debug("MutableWindowBehavior:: onMouseReleased when using floating skin");

				return ;
			}

			// IF STAGED
			if(_window.status().isStaged()) {
				log.debug("MutableWindowBehavior:: onMouseReleased when using staged skin");
				attachToDockPane(_window, Points.of(e.getX(), e.getY()), Points.of(e.getScreenX(), e.getScreenY()));

				return ;
			}
		};
	}

	public static EventHandler<MouseEvent> enableDragAndDrop(final MutableWindow _window) {
		return (e) -> {
			log.debug("MutableWindowBehavior:: enableDragAndDrop");
			// IF DOCKED
			if(_window.status().isDocked()) {
				log.debug("MutableWindowBehavior:: enableDragAndDrop when using docked skin");

				DockedWindowSkin skin = _window.getDockSkin();
				resizeDockedFrame(_window, _window.getDockSkin(), Points.of(e.getScreenX(), e.getScreenY()));
				
//				if(skin.sizeNorth || skin.sizeSouth || skin.sizeWest || skin.sizeEast) {
				if(skin.isMouseResizeZone()) 
					e.consume();
				
				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				log.debug("MutableWindowBehavior:: enableDragAndDrop when using floating skin");

				return ;
			}

			// IF STAGED
			if(_window.status().isStaged()) {
				log.debug("MutableWindowBehavior:: enableDragAndDrop when using staged skin");

				return ;
			}
		};
	}
	public static EventHandler<MouseEvent> onMouseDragDetected(final MutableWindow _window) {
		return (e) -> {
			log.debug("StageMode:: onMouseDragDetected");

			// IF DOCKED
			if(_window.status().isDocked()) {
				detachFromDockPane(_window, Points.of(e.getX(), e.getY()));
				e.consume();

				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				log.debug("MutableWindowBehavior:: onMouseDragDetected when using floating skin");

				return ;
			}

			// IF STAGED
			if(_window.status().isStaged()) {
				log.debug("MutableWindowBehavior:: onMouseDragDetected when using staged skin");

				return ;
			}
		};
	}
	public static EventHandler<MouseEvent> onMouseDragged(final MutableWindow _window) {
		return (e) -> {
			if(e.getEventType() == MouseEvent.MOUSE_DRAGGED)
				log.debug("OK");

			// IF DOCKED
			if(_window.status().isDocked()) {
				log.debug("MutableWindowBehavior:: onMouseDragged when using floating skin");

				return ;
			}

			// IF FLOATING
			if(_window.status().isFloating()) {
				log.debug("MutableWindowBehavior:: onMouseDragged when using floating skin");
				FloatingWindowSkin skin = (FloatingWindowSkin) _window.getSkin();
				
				if(bndDefault == null)
					bndDefault = skin.boundsInLocalProperty().get();

				Region region = (Region) _window.getContent(); //getChildren().get(0);
				if(RESIZE_BOTTOM && RESIZE_RIGHT) {
					switch(_window.properties().getResizeMode()) {
					case RESIZE : region.setPrefSize(e.getX(), e.getY()); break;
					case SCALE  : region.setScaleX(e.getX() / bndDefault.getWidth());
								  region.setScaleY(e.getY() / bndDefault.getHeight());
								  break;
					}
				} else if (RESIZE_RIGHT) {
					switch(_window.properties().getResizeMode()) {
					case RESIZE : region.setPrefWidth(e.getX()); break;
					case SCALE  : region.setScaleX(e.getX() / bndDefault.getWidth());
								  break;
					}
				} else if (RESIZE_BOTTOM) {
					switch(_window.properties().getResizeMode()) {
					case RESIZE : region.setPrefHeight(e.getY()); break;
					case SCALE  : region.setScaleY(e.getY() / bndDefault.getHeight());
								  break;
					}
				}
				return ;
			}

			// IF STAGED
			if(_window.status().isStaged()) {
				log.debug("MutableWindowBehavior:: onMouseDragged when using staged skin");

				if(_window.status().isStaged() && e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY) {
					log.debug("Floating");
					e.setDragDetect(false);
					e.consume();
					return;
				}

				if(!_window.status().isDragging())
					return;

				Stage stage = _window.getStageSkin();
				Insets insetsDelta = _window.getStageSkin().getPane().getInsets();

				log.debug("X= " + (e.getScreenX() - _window.status().getDragOrigin().getX() - insetsDelta.getLeft()) + ", Y= " + (e.getScreenY() - _window.status().getDragOrigin().getY() - insetsDelta.getTop()));
				
				stage.setX(e.getScreenX() - _window.status().getDragOrigin().getX() - insetsDelta.getLeft());
				stage.setY(e.getScreenY() - _window.status().getDragOrigin().getY() - insetsDelta.getTop());

				// TODO: change the pick result by adding a copyForPick()
				MutableWindowEvent dockEnterEvent = 
						new MutableWindowEvent(_window.getHeader(), 
								MutableWindowEvent.NULL_SOURCE_TARGET, MutableWindowEvent.DOCK_ENTER, 
								e.getX(), e.getY(), e.getScreenX(), e.getScreenY(), null);
				MutableWindowEvent dockOverEvent = 
						new MutableWindowEvent(_window.getHeader(), 
								MutableWindowEvent.NULL_SOURCE_TARGET, MutableWindowEvent.DOCK_OVER, 
								e.getX(), e.getY(), e.getScreenX(), e.getScreenY(), null);
				MutableWindowEvent dockExitEvent = 
						new MutableWindowEvent(_window.getHeader(), 
								MutableWindowEvent.NULL_SOURCE_TARGET, MutableWindowEvent.DOCK_EXIT, 
								e.getX(), e.getY(), e.getScreenX(), e.getScreenY(), null);

				EventTask eventTask = new EventTask() {
					@Override
					public void run(Node node, Node dragNode) {
						executions++;

						if(dragNode != node) {
							Event.fireEvent(node, dockEnterEvent.copyFor(_window.getHeader(), node));

							if(dragNode != null) {
								// fire the dock exit first so listeners
								// can actually keep track of the node we
								// are currently over and know when we
								// aren't over any which DOCK_OVER
								// does not provide
								Event.fireEvent(dragNode, dockExitEvent.copyFor(_window.getHeader(), dragNode));
							}

							dragNodes.put(node.getScene().getWindow(), node);
						}
						Event.fireEvent(node, dockOverEvent.copyFor(_window.getHeader(), node));
					}
				};

				_window.getHeader().pickEventTarget(Points.of(e.getScreenX(), e.getScreenY()), eventTask, dockExitEvent);
				return ;
			}
			
		};
	}

	
	
	public static void resizeDockedFrame(final MutableWindow _window, final DockedWindowSkin _skin, final Point2D _p) {
		if(_skin.isMouseResizeZone()) {
			Point2D sizeCurrent = _p; //new Point2D(e.getScreenX(), e.getScreenY());
			Point2D sizeDelta = sizeCurrent.minus(_skin.sizeLast);

			double newX = _window.getStageSkin().getX(), newY = _window.getStageSkin().getY(), newWidth = _window.getStageSkin().getWidth(), newHeight = _window.getStageSkin().getHeight();

			if(_skin.sizeNorth) {
				newHeight -= sizeDelta.getY();
				newY += sizeDelta.getY();
			} else if(_skin.sizeSouth) {
				newHeight += sizeDelta.getY();
			}

			if(_skin.sizeWest) {
				newWidth -= sizeDelta.getX();
				newX += sizeDelta.getX();
			} else if(_skin.sizeEast) {
				newWidth += sizeDelta.getX();
			}

			// TODO: find a way to do this synchronously and eliminate the flickering of moving the stage
			// around, also file a bug report for this feature if a work around can not be found this
			// primarily occurs when dragging north/west but it also appears in native windows and Visual
			// Studio, so not that big of a concern.
			// Bug report filed:
			// https://bugs.openjdk.java.net/browse/JDK-8133332
			double currentX = _skin.sizeLast.getX(), currentY = _skin.sizeLast.getY();
			if(newWidth >= _window.getStageSkin().getMinWidth()) {
				_window.getStageSkin().setX(newX);
				_window.getStageSkin().setWidth(newWidth);
				currentX = sizeCurrent.getX();
			}

			if(newHeight >= _window.getStageSkin().getMinHeight()) {
				_window.getStageSkin().setY(newY);
				_window.getStageSkin().setHeight(newHeight);
				currentY = sizeCurrent.getY();
			}
			_skin.sizeLast = Points.of(currentX, currentY);
			// we do not want the title bar getting these events
			// while we are actively resizing
			/*
			if(_skin.sizeNorth || _skin.sizeSouth || _skin.sizeWest || _skin.sizeEast) {
				e.consume();
			}
			*/
		}
	}
	
	public static void detachFromDockPane(final MutableWindow _window, final Point2D _p) {
		if(!_window.status().isFloating()) {
			// if we are not using a custom title bar and the user
			// is not forcing the default one for floating and
			// the dock node does have native window decorations
			// then we need to offset the stage position by
			// the height of this title bar
			StagedWindowSkin stage = _window.getStageSkin();
			if(!stage.isCustomTitleBar() && stage.isDecorated()) {
				_window.staged(Points.of(0, _window.getHeader().getHeight()));
			} else {
				_window.staged(null);
			}

			// TODO: Find a better solution.
			// Temporary work around for nodes losing the drag event when removed from
			// the scene graph.
			// A possible alternative is to use "ghost" panes in the DockPane layout
			// while making DockNode simply an overlay stage that is always shown.
			// However since flickering when popping out was already eliminated that would
			// be overkill and is not a suitable solution for native decorations.
			// Bug report open: https://bugs.openjdk.java.net/browse/JDK-8133335
			DockPane dockPane = null; //_window.getDockSkin().getDockPane(); // TODO:: HACK:: FIXME::
			if(dockPane != null) {
				dockPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, _window.getHeader().onMouseDragged);
				dockPane.addEventFilter(MouseEvent.MOUSE_RELEASED, _window.getHeader().onMouseReleased);
			}
		} else if(_window.status().isMaximized()) {
			double ratioX = _p.getX() / _window.properties().getPrefWidth();
			double ratioY = _p.getY() / _window.properties().getPrefHeight();

			// Please note that setMaximized is ruined by width and height changes occurring on the
			// stage and there is currently a bug report filed for this though I did not give them an
			// accurate test case which I should and wish I would have. This was causing issues in the
			// original release requiring maximized behavior to be implemented manually by saving the
			// restored bounds. The problem was that the resize functionality in DockNode.java was
			// executing at the same time canceling the maximized change.
			// https://bugs.openjdk.java.net/browse/JDK-8133334

			// restore/minimize the window after we have obtained its dimensions
			//				_window.setMaximized(false);

			// scale the drag start location by our restored dimensions
			_window.status().setDragOrigin(Points.of(ratioX * _window.properties().getPrefWidth(), ratioY * _window.properties().getPrefHeight()));
		}
		_window.status().setDragging(true);
//		e.consume();

		return ;
	}
	public static void attachToDockPane(final MutableWindow _window, final Point2D _p, final Point2D _q) {
		_window.status().setDragging(false);

		MutableWindowEvent dockReleasedEvent = new MutableWindowEvent(
				_window.getHeader(),
				MutableWindowEvent.NULL_SOURCE_TARGET,
				MutableWindowEvent.DOCK_RELEASED,
				_p.getX(), _p.getY(), _q.getX(), _q.getY(),
				null);

		EventTask eventTask = new EventTask() {
			@Override
			public void run(Node node, Node dragNode) {
				executions++;
				if(dragNode != node)
					Event.fireEvent(node, dockReleasedEvent.copyFor(_window.getHeader(), node));
				Event.fireEvent(node, dockReleasedEvent.copyFor(_window.getHeader(), node));
			}
		};

		_window.getHeader().pickEventTarget(Points.of(_q.getX(), _q.getY()), eventTask, null);

		dragNodes.clear();

		// Remove temporary event handler for bug mentioned above.
		if(_window.getMode() == Mode.DOCKED) {
			/* FIXME::
			DockPane dockPane = _window.getDockSkin().getDockPane();
			if(dockPane != null) {
				dockPane.removeEventFilter(MouseEvent.MOUSE_DRAGGED, _window.getHeader().onMouseDragged);
				dockPane.removeEventFilter(MouseEvent.MOUSE_RELEASED, _window.getHeader().onMouseReleased);
			}
			*/
		}

		return ;
	}
	
	
	public void setDockeMode(MutableWindow _window) {
		EventHandler<MouseEvent> h = (event) -> {
			log.debug("TADDDADADADDADADADADADADA");
			Cursor cursor = Cursor.DEFAULT;

//			if(!_window.isFloating() || !this.isStageResizable())
			if(!_window.status().isFloating() || !_window.properties().isMutable())
				return;

			if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				_window.getDockSkin().sizeLast = Points.of(event.getScreenX(), event.getScreenY());
			} else if(event.getEventType() == MouseEvent.MOUSE_MOVED) {
				Insets insets = _window.getStageSkin().getPane().getPadding();

				_window.getDockSkin().sizeWest = event.getX() < insets.getLeft();
				_window.getDockSkin().sizeEast = event.getX() > _window.getStageSkin().getPane().getWidth() - insets.getRight();
				_window.getDockSkin().sizeNorth = event.getY() < insets.getTop();
				_window.getDockSkin().sizeSouth = event.getY() > _window.getStageSkin().getPane().getHeight() - insets.getBottom();

				if(_window.getDockSkin().sizeWest) {
					if(_window.getDockSkin().sizeNorth) {
						cursor = Cursor.NW_RESIZE;
					} else if(_window.getDockSkin().sizeSouth) {
						cursor = Cursor.SW_RESIZE;
					} else {
						cursor = Cursor.W_RESIZE;
					}
				} else if(_window.getDockSkin().sizeEast) {
					if(_window.getDockSkin().sizeNorth) {
						cursor = Cursor.NE_RESIZE;
					} else if(_window.getDockSkin().sizeSouth) {
						cursor = Cursor.SE_RESIZE;
					} else {
						cursor = Cursor.E_RESIZE;
					}
				} else if(_window.getDockSkin().sizeNorth) {
					cursor = Cursor.N_RESIZE;
				} else if(_window.getDockSkin().sizeSouth) {
					cursor = Cursor.S_RESIZE;
				}

				_window.getDockSkin().getScene().setCursor(cursor);
			} else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED && _window.getDockSkin().isMouseResizeZone()) {
				Point2D sizeCurrent = Points.of(event.getScreenX(), event.getScreenY());
				Point2D sizeDelta = sizeCurrent.minus(_window.getDockSkin().sizeLast);

				double newX = _window.getStageSkin().getX(), newY = _window.getStageSkin().getY(), newWidth = _window.getStageSkin().getWidth(),
						newHeight = _window.getStageSkin().getHeight();

				if(_window.getDockSkin().sizeNorth) {
					newHeight -= sizeDelta.getY();
					newY += sizeDelta.getY();
				} else if(_window.getDockSkin().sizeSouth) {
					newHeight += sizeDelta.getY();
				}

				if(_window.getDockSkin().sizeWest) {
					newWidth -= sizeDelta.getX();
					newX += sizeDelta.getX();
				} else if(_window.getDockSkin().sizeEast) {
					newWidth += sizeDelta.getX();
				}

				double currentX = _window.getDockSkin().sizeLast.getX(), currentY = _window.getDockSkin().sizeLast.getY();
				if(newWidth >= _window.getStageSkin().getMinWidth()) {
					_window.getStageSkin().setX(newX);
					_window.getStageSkin().setWidth(newWidth);
					currentX = sizeCurrent.getX();
				}

				if(newHeight >= _window.getStageSkin().getMinHeight()) {
					_window.getStageSkin().setY(newY);
					_window.getStageSkin().setHeight(newHeight);
					currentY = sizeCurrent.getY();
				}
				_window.getDockSkin().sizeLast = Points.of(currentX, currentY);
				if(_window.getDockSkin().sizeNorth || _window.getDockSkin().sizeSouth || _window.getDockSkin().sizeWest || _window.getDockSkin().sizeEast) {
					event.consume();
				}
			}
		};
	}


	public static void enableTakeFocus(MutableWindow _window, FloatingWindowSkin _skin) {
		assert(_window.getMode() == Mode.FLOATING);
		assert(_window.getSkin() instanceof FloatingWindowSkin);

		FloatingWindowSkin skin = (FloatingWindowSkin) _window.getSkin();

		skin.setOnMouseClicked( (e) -> skin.toFront() );
	}

	public static void enableTranslation(MutableWindow _window, FloatingWindowSkin _skin) {
		assert(_window.getMode() == Mode.FLOATING);
		assert(_window.getSkin() instanceof FloatingWindowSkin);

		FloatingWindowSkin skin = (FloatingWindowSkin) _window.getSkin();

		skin.setOnMouseClicked( (e) -> skin.toFront() );
	}

	public void setStaged(final MutableWindow _window, boolean floating, Point2D translation) {
		if(floating && !_window.status().isFloating()) {
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
		} else if(!floating && _window.status().isFloating()) {
			//_window.status().floatingProperty().set(floating);
/*
			stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
			stage.removeEventFilter(MouseEvent.MOUSE_MOVED, this);
			stage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, this);

			stage.close();
*/
		}
	}
}
