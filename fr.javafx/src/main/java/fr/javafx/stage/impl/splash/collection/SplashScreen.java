package fr.javafx.stage.impl.splash.collection;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Stage {
	public Scene scene = null;

	Label       title;
	ImageView   splash;

	ProgressBar progress;
	Label       action;

	public SplashScreen(String _title, Image _splashImage) {
		super();

//		getIcons().add(new Image(FileHelper.getContent(SplashScreen.class, "sp-web.fr/icon.png")));
		initStyle(StageStyle.UNDECORATED);
		initStyle(StageStyle.TRANSPARENT);
//		initStyle(StageStyle.UTILITY);
		setAlwaysOnTop(true);

		setTitle(_title);
		
		setWidth(480);
		setHeight(320);
		setResizable(false);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        setX((primScreenBounds.getWidth() - getWidth()) / 2);
        setY((primScreenBounds.getHeight() - getHeight()) / 2);

		setScene( scene = splashScene(_splashImage) );
		show();
	}

	private Scene splashScene(Image _splashImage) {
		title    = new Label("TITLE");
		title.getStyleClass().add("splash-title");

		splash   = new ImageView();
		splash.setImage(_splashImage);
		
		progress = new ProgressBar();
		progress.getStyleClass().add("splash-progress");

		action   = new Label("current phase");
		action.getStyleClass().add("splash-phase");
		action.setTextAlignment(TextAlignment.CENTER);

		VBox dynamics = new VBox();
		dynamics.setAlignment(Pos.CENTER);
		dynamics.getChildren().addAll(progress, action);

		BorderPane borderPane = new BorderPane();
		borderPane.getStyleClass().add("splash-background");
		borderPane.setTop(title);
		borderPane.setCenter(splash);
		borderPane.setBottom(dynamics);
		

		dynamics.prefWidthProperty().bind(borderPane.widthProperty());
		title.prefWidthProperty().bind(borderPane.widthProperty());
		progress.prefWidthProperty().bind(borderPane.widthProperty());
		action.prefWidthProperty().bind(borderPane.widthProperty());
		action.setTextAlignment(TextAlignment.CENTER);
		
		Scene newScene = new Scene(borderPane, 480, 320);
		newScene.getStylesheets().add(SplashScreen.class.getResource("/default/splash/default.css").toExternalForm());

		newScene.setFill(Color.TRANSPARENT);
		return newScene;
	}

}
