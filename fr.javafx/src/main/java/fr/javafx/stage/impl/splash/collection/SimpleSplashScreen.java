package fr.javafx.stage.impl.splash.collection;

import java.io.InputStream;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SimpleSplashScreen extends SplashScreen2 {
	private Image        splashImage;
	private ImageView    splashView;

	public SimpleSplashScreen(InputStream _img) {
		super();
        setScene(scene = content(_img));
	}

    protected Scene content(InputStream _img) {
		splashImage = new Image(_img);
		splashView  = new ImageView();
		
		splashView.setImage(splashImage);

        Scene newScene = new Scene(new StackPane(splashView));
		newScene.setFill(Color.TRANSPARENT);
    	return newScene;
    }

}