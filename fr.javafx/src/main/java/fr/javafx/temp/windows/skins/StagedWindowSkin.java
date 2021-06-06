package fr.javafx.temp.windows.skins;

import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Points;
import fr.javafx.temp.windows.MutableWindow;
import fr.javafx.temp.windows.MutableWindowSkin;
import fr.javafx.temp.windows.components.WindowHeader;
import fr.javafx.temp.windows.components.WindowProperties;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class StagedWindowSkin extends Stage implements MutableWindowSkin.Staged {

	private MutableWindow	window;

	private StageStyle 		stageStyle;
	private BorderPane		pane;

	public StagedWindowSkin(MutableWindow _window) {
		super();
		setTitle(_window.getTitle());

		initStyle(stageStyle = StageStyle.TRANSPARENT);
		//stage.initOwner(dockPane.getScene().getWindow());

		window = _window;

		pane = new BorderPane();
		pane.getStyleClass().add("dock-node-border");
		pane.applyCss();
/*
		pane.setTop		(_window.getHeader());
		pane.setCenter	(_window.getContent());
*/
		pane.setPadding (new Insets(0));
		pane.setOpaqueInsets(new Insets(0));

		// SET POSITION
		Insets  insets = pane.getInsets();
		if(_window.getSkin() != null) {
			Region previousSkin = _window.getSkin() == _window.getDockSkin() ? (Region) _window.getDockSkin() : (Region) _window.getFloatSkin();

			javafx.geometry.Point2D floatScene  = previousSkin.localToScene(0, 0);
			javafx.geometry.Point2D floatScreen = previousSkin.localToScreen(0, 0);

			Window owner = previousSkin.getScene().getWindow();
			javafx.geometry.Point2D stagePosition = owner != null ? floatScene.add(new javafx.geometry.Point2D(owner.getX(), owner.getY())) : floatScreen;

			setX(stagePosition.getX() - insets.getLeft());
			setY(stagePosition.getY() - insets.getTop());
		}

		// SET DIMENSION
		double insetsWidth  = insets.getLeft() + insets.getRight();
		double insetsHeight = insets.getTop() + insets.getBottom();
		setMinWidth(pane.minWidth(this.getHeight()) + insetsWidth);
		setMinHeight(pane.minHeight(this.getWidth()) + insetsHeight);
		pane.setPrefSize(this.getWidth() + insetsWidth, this.getHeight() + insetsHeight);

		setScene(new Scene(pane));
		sizeToScene();
		hide();
		show();
	}

	@Override
	public MutableWindow getControl() {
		return window;
	}
	public BorderPane getPane() {
		return pane;
	}

	@Override
	public void fill() {
		pane.setTop		(window.getHeader());
		pane.setCenter	(window.getContent());
		sizeToScene();
		show();
	}
	@Override
	public void release() {
		pane.getChildren().removeAll(window.getHeader(), window.getContent());
		hide();
	}

	public boolean isCustomTitleBar() {
		return false;
	}
	public boolean isDecorated() {
		return stageStyle != StageStyle.TRANSPARENT && stageStyle != StageStyle.UNDECORATED;
	}

	public void maximize() {
//		pseudoClassStateChanged(MAXIMIZED_PSEUDO_CLASS, true);
		if(pane != null)
			pane.pseudoClassStateChanged(WindowProperties.MAXIMIZED_PSEUDO_CLASS, true);

		Screen      screen = Screen.getScreensForRectangle(getX(), getY(), getWidth(), getHeight()).get(0);
		Rectangle2D bounds = screen.getVisualBounds();

		setX(bounds.getMinX());
		setY(bounds.getMinY());

		setWidth(bounds.getWidth());
		setHeight(bounds.getHeight());
		
		/*
		pseudoClassStateChanged(MAXIMIZED_PSEUDO_CLASS, get());
		if(borderPane != null) {
			borderPane.pseudoClassStateChanged(MAXIMIZED_PSEUDO_CLASS, get());
		}
		
		stage.setMaximized(get());
		
		if(this.get()) {
			Screen screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()).get(0);
			Rectangle2D bounds = screen.getVisualBounds();
		
			stage.setX(bounds.getMinX());
			stage.setY(bounds.getMinY());
		
			stage.setWidth(bounds.getWidth());
			stage.setHeight(bounds.getHeight());
		}
		 */
	}

	public void minimize() {
		final WindowHeader header  = window.getHeader();
		final Point2D      paneDim = Points.of( pane.getChildren().get(0).getBoundsInParent().getWidth() , pane.getChildren().get(0).getBoundsInParent().getHeight() );

		if(pane.getChildren().size() == 1) {
			pane.getChildren().get(0).setVisible(true);
			pane.setPrefHeight(header.getBoundsInParent().getHeight() + paneDim.getY());
		} else {
			pane.getChildren().get(0).setVisible(false);
			pane.getChildren().remove(0);
			pane.setPrefHeight(header.getBoundsInParent().getHeight());
		}
	}

}
