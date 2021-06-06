package fr.javafx.stage.impl.splash.impl;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SimpleSplashScreen extends AbstractSplashStage {

	public SimpleSplashScreen(String _title, Image _icon, Image _splash) {
		super(_title, _icon, content(_splash));
	}

    protected static Scene content(Image _splash) {
        Scene newScene = new Scene(new StackPane(new ImageView(_splash)));
		newScene.setFill(Color.TRANSPARENT);
    	return newScene;
    }

	private static Scene defaultContent() {
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: green;");

		borderPane.setCenter(new Label("insert here"));

		Scene newScene = new Scene(borderPane);
		newScene.setFill(Color.TRANSPARENT);
		return newScene;
	}

}