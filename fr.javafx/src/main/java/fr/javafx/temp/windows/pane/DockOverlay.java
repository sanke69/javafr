package fr.javafx.temp.windows.pane;

import fr.java.sdk.lang.tuples.SimplePair;
import fr.java.sdk.log.LogInstance;
import fr.javafx.temp.windows.components.WindowProperties.Position;
import fr.javafx.temp.windows.events.MutableWindowEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.util.Duration;

public class DockOverlay {
	static final LogInstance log = LogInstance.getLogger();

	public class DockPosButton extends Button {
		private boolean		dockRoot	= true;
		private Position	dockPos		= Position.CENTER;

		public DockPosButton(boolean dockRoot, Position dockPos) {
			super();
			this.dockRoot = dockRoot;
			this.dockPos = dockPos;
		}

		public final void setDockRoot(boolean dockRoot) {
			this.dockRoot = dockRoot;
		}
		public final void setDockPos(Position dockPos) {
			this.dockPos = dockPos;
		}
		public final Position getDockPos() {
			return dockPos;
		}

		public final boolean isDockRoot() {
			return dockRoot;
		}
	}

	public ObservableList<DockPosButton> dockPosButtons;

	Timeline dockAreaStrokeTimeline;

	public GridPane dockPosIndicator;

	public Rectangle	dockAreaIndicator;
	public Popup		dockIndicatorOverlay;
	public Popup		dockIndicatorHoveredNode;

	DockPane pane;

	public DockOverlay(DockPane _pane) {
		super();
		pane = _pane;

		dockIndicatorHoveredNode = new Popup();
		dockIndicatorHoveredNode.setAutoFix(false);

		dockIndicatorOverlay = new Popup();
		dockIndicatorOverlay.setAutoFix(false);

		StackPane dockRootPane = new StackPane();
		dockRootPane.prefWidthProperty().bind(pane.widthProperty());
		dockRootPane.prefHeightProperty().bind(pane.heightProperty());

		dockAreaIndicator = new Rectangle();
		dockAreaIndicator.setManaged(false);
		dockAreaIndicator.setMouseTransparent(true);

		// 12 is the cumulative offset of the stroke dash array in the default.css style sheet
		// RFE filed for CSS styled timelines/animations:
		// https://bugs.openjdk.java.net/browse/JDK-8133837
		KeyValue kv = new KeyValue(dockAreaIndicator.strokeDashOffsetProperty(), 12);
		KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		dockAreaStrokeTimeline = new Timeline();
		dockAreaStrokeTimeline.setCycleCount(Timeline.INDEFINITE);
		dockAreaStrokeTimeline.getKeyFrames().add(kf);
		dockAreaStrokeTimeline.play();

		DockPosButton dockCenter = new DockPosButton(false, Position.CENTER);
		dockCenter.getStyleClass().add("dock-center");

		DockPosButton dockTop = new DockPosButton(false, Position.TOP);
		dockTop.getStyleClass().add("dock-top");
		DockPosButton dockRight = new DockPosButton(false, Position.RIGHT);
		dockRight.getStyleClass().add("dock-right");
		DockPosButton dockBottom = new DockPosButton(false, Position.BOTTOM);
		dockBottom.getStyleClass().add("dock-bottom");
		DockPosButton dockLeft = new DockPosButton(false, Position.LEFT);
		dockLeft.getStyleClass().add("dock-left");

		DockPosButton dockTopRoot = new DockPosButton(true, Position.TOP);
		StackPane.setAlignment(dockTopRoot, Pos.TOP_CENTER);
		dockTopRoot.getStyleClass().add("dock-top-root");

		DockPosButton dockRightRoot = new DockPosButton(true, Position.RIGHT);
		StackPane.setAlignment(dockRightRoot, Pos.CENTER_RIGHT);
		dockRightRoot.getStyleClass().add("dock-right-root");

		DockPosButton dockBottomRoot = new DockPosButton(true, Position.BOTTOM);
		StackPane.setAlignment(dockBottomRoot, Pos.BOTTOM_CENTER);
		dockBottomRoot.getStyleClass().add("dock-bottom-root");

		DockPosButton dockLeftRoot = new DockPosButton(true, Position.LEFT);
		StackPane.setAlignment(dockLeftRoot, Pos.CENTER_LEFT);
		dockLeftRoot.getStyleClass().add("dock-left-root");

		// TODO: dockCenter goes first when tabs are added in a future version
		dockPosButtons = FXCollections.observableArrayList(dockTop, dockRight, dockBottom, dockLeft, dockTopRoot, dockRightRoot, dockBottomRoot, dockLeftRoot);

		dockPosIndicator = new GridPane();
		dockPosIndicator.add(dockTop, 1, 0);
		dockPosIndicator.add(dockRight, 2, 1);
		dockPosIndicator.add(dockBottom, 1, 2);
		dockPosIndicator.add(dockLeft, 0, 1);
		//		dockPosIndicator.add(dockCenter, 1, 1);

		dockRootPane.getChildren().addAll(dockAreaIndicator, dockTopRoot, dockRightRoot, dockBottomRoot, dockLeftRoot);

		dockIndicatorOverlay.getContent().add(dockRootPane);
		dockIndicatorHoveredNode.getContent().addAll(dockPosIndicator);

		dockRootPane.getStyleClass().add("dock-root-pane");
		dockPosIndicator.getStyleClass().add("dock-pos-indicator");
		dockAreaIndicator.getStyleClass().add("dock-area-indicator");
	}

	public SimplePair<Position, Node> getDockInfo(MutableWindowEvent _event) {
		Position dockPos = null;
		Node targetNode = null;

		for(DockPosButton dockIndicatorButton : dockPosButtons) {
			if(dockIndicatorButton.contains(dockIndicatorButton.screenToLocal(_event.getScreenX(), _event.getScreenY()))) {
				dockPos = dockIndicatorButton.getDockPos();
				if(dockIndicatorButton.isDockRoot())
					targetNode = pane.root;

				dockIndicatorButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), true);
				break;
			} else {
				dockIndicatorButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), false);
			}
		}

		return new SimplePair<Position, Node>(dockPos, targetNode);
	}

	public void showHighlightArea(Position _dockPos, Node _dockNode) {
		Point2D originToScene = _dockNode.localToScene(0, 0);

		dockAreaIndicator.relocate(originToScene.getX(), originToScene.getY());
		if(_dockPos == Position.RIGHT)
			dockAreaIndicator.setTranslateX(_dockNode.getLayoutBounds().getWidth() / 2);
		else
			dockAreaIndicator.setTranslateX(0);

		if(_dockPos == Position.BOTTOM)
			dockAreaIndicator.setTranslateY(_dockNode.getLayoutBounds().getHeight() / 2);
		else
			dockAreaIndicator.setTranslateY(0);

		if(_dockPos == Position.LEFT || _dockPos == Position.RIGHT)
			dockAreaIndicator.setWidth(_dockNode.getLayoutBounds().getWidth() / 2);
		else
			dockAreaIndicator.setWidth(_dockNode.getLayoutBounds().getWidth());

		if(_dockPos == Position.TOP || _dockPos == Position.BOTTOM)
			dockAreaIndicator.setHeight(_dockNode.getLayoutBounds().getHeight() / 2);
		else
			dockAreaIndicator.setHeight(_dockNode.getLayoutBounds().getHeight());

		dockAreaIndicator.setVisible(true);
	}
	public void hideHighlightArea() {
		dockAreaIndicator.setVisible(false);
	}

	public void showMainAnchors(Point2D _topLeft) {
		dockIndicatorOverlay.show(pane, _topLeft.getX(), _topLeft.getY());
	}
	public void hideMainAnchor() {
		dockIndicatorOverlay.hide();
	}

	public void showHoveredNodeAnchors(Node _hovered) {
		Point2D originToScreen = _hovered.localToScreen(0, 0);

		double posX = originToScreen.getX()
				+ _hovered.getLayoutBounds().getWidth() / 2
				- dockPosIndicator.getWidth() / 2;
		double posY = originToScreen.getY()
				+ _hovered.getLayoutBounds().getHeight() / 2
				- dockPosIndicator.getHeight() / 2;

		if(!dockIndicatorHoveredNode.isShowing()) {
			dockIndicatorHoveredNode.show(pane, posX, posY);
		} else {
			dockIndicatorHoveredNode.setX(posX);
			dockIndicatorHoveredNode.setY(posY);
		}

		// set visible after moving the popup
		dockPosIndicator.setVisible(true);
	}
	public void hideHoveredNodeAnchors() {
		dockPosIndicator.setVisible(false);
	}

}
