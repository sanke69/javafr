package fr.javafx.stage.impl;

import fr.javafx.stage.StageExt;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

public class TransparentStage extends StageExt {

	Scene     transparentScene;
	StackPane transparentPane;

	public TransparentStage() {
		super();

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		double width  = primScreenBounds.getWidth();
		double height = primScreenBounds.getHeight();

		initStyle(StageStyle.TRANSPARENT);
		setAlwaysOnTop(true);
//		setFullScreen(true);
//		getIcons().add(new Image("https://example.com/javaicon.png"));

		setX(0);
		setY(0);
 		setMinWidth(width);
		setMinHeight(height);

		setResizable(false);

		setScene(transparentScene = new Scene(transparentPane = new StackPane()));

		transparentScene . setFill(Color.TRANSPARENT);
		transparentPane  . setStyle("-fx-background-color: transparent;");

		show();
	}

	public StackPane getPane() {
		return transparentPane;
	}
	
}
