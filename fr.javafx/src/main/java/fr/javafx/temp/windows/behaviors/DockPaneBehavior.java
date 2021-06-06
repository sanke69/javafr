package fr.javafx.temp.windows.behaviors;

import fr.java.sdk.lang.tuples.SimplePair;
import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.events.MutableWindowEvent;
import fr.javafx.temp.windows.pane.DockOverlay;
import fr.javafx.temp.windows.pane.DockPane;
import fr.javafx.temp.windows.skins.DockedWindowSkin;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class DockPaneBehavior {
	static final LogInstance log = LogInstance.getLogger();

	public static EventHandler<MutableWindowEvent> onEntering(DockPane _pane, DockOverlay _overlay) {
		return (e) -> {
			if(e.getEventType() == MutableWindowEvent.DOCK_ENTER) {
				if(!_overlay.dockIndicatorOverlay.isShowing())
					_overlay.showMainAnchors(_pane.localToScreen(0, 0));
			}
		};
	}

	public static EventHandler<MutableWindowEvent> onOver(DockPane _pane, DockOverlay _overlay) {
		return (event) -> {
			if(event.getEventType() == MutableWindowEvent.DOCK_OVER) {
				_pane.receivedEnter = false;

				if(_pane.dockNodeDrag != null)
					_overlay.showHoveredNodeAnchors(_pane.dockNodeDrag);
				else
					_overlay.hideHoveredNodeAnchors();

				SimplePair<Position, Node> dockInfo = _overlay.getDockInfo(event);
				_pane.dockPosDrag = dockInfo.getFirst();
				_pane.dockAreaDrag = dockInfo.getSecond() != null ? dockInfo.getSecond() : _pane.dockNodeDrag;

				if(_pane.dockPosDrag != null)
					_overlay.showHighlightArea(_pane.dockPosDrag, _pane.dockAreaDrag);
				else
					_overlay.hideHighlightArea();
			}
		};
	}

	public static EventHandler<MutableWindowEvent> onRelease(DockPane _pane, DockOverlay _overlay) {
		return (event) -> {
			log.debug("DockPaneBehavior::onRelease: " + event.getEventType() + "\t" + event.getContents() + "\t" + event.getTarget());
			//			if(event.getEventType() == MutableWindowEvent.DOCK_RELEASED && event.getContents() != null) {
			if(event.getEventType() == MutableWindowEvent.DOCK_RELEASED && event.getTarget() != null) {
				log.debug("DockPaneBehavior::onRelease");
				if(_pane.dockPosDrag != null && _overlay.dockIndicatorOverlay.isShowing()) {
					log.debug("DockPaneBehavior::onRelease -> docking");
					DockedWindowSkin dockNode = (DockedWindowSkin) event.getTarget();

					dockNode.dock(_pane, _pane.dockPosDrag, _pane.dockAreaDrag);
				}
			}
		};
	}

	public static EventHandler<MutableWindowEvent> onExit(DockPane _pane, DockOverlay _overlay) {
		return (event) -> {
			log.debug("DockPaneBehavior::onExit");
			if((event.getEventType() == MutableWindowEvent.DOCK_EXIT && !_pane.receivedEnter) || event.getEventType() == MutableWindowEvent.DOCK_RELEASED) {
				log.debug("DockPaneBehavior::onExit");
				if(_overlay.dockIndicatorHoveredNode.isShowing()) {
					_overlay.dockIndicatorOverlay.hide();
					_overlay.dockIndicatorHoveredNode.hide();
				}
			}
		};
	}

}
