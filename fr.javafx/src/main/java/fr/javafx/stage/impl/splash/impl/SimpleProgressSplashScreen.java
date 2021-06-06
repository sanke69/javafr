package fr.javafx.stage.impl.splash.impl;

import fr.javafx.stage.impl.splash.ProgressSplashScreen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

public class SimpleProgressSplashScreen extends AbstractSplashStage implements ProgressSplashScreen {
//	public Scene scene = null;

	Label       phase;
	ImageView   splash;

	ProgressBar progress;
	Label       action;

	public SimpleProgressSplashScreen(String _title, Image _icon, Image _splash) {
		super(_title, _icon);
		setScene(splashScene(_splash));
		sizeToScene();
	}

	private Scene splashScene(Image _splash) {
		phase    = new Label("Current Phase");
		phase.getStyleClass().add("splash-title");

		splash   = new ImageView();
		splash.setImage(_splash);
		
		progress = new ProgressBar();
		progress.getStyleClass().add("splash-progress");

		action   = new Label("Current Action");
		action.getStyleClass().add("splash-phase");
		action.setTextAlignment(TextAlignment.CENTER);

		VBox dynamics = new VBox();
		dynamics.setAlignment(Pos.CENTER);
		dynamics.getChildren().addAll(progress, action);

		BorderPane borderPane = new BorderPane();
		borderPane.getStyleClass().add("splash-background");
		borderPane.setTop(phase);
		borderPane.setCenter(splash);
		borderPane.setBottom(dynamics);
		

		dynamics.prefWidthProperty().bind(borderPane.widthProperty());
		phase.prefWidthProperty().bind(borderPane.widthProperty());
		progress.prefWidthProperty().bind(borderPane.widthProperty());
		action.prefWidthProperty().bind(borderPane.widthProperty());
		action.setTextAlignment(TextAlignment.CENTER);
		
		Scene newScene = new Scene(borderPane, 480, 320);
		newScene.getStylesheets().add(SimpleProgressSplashScreen.class.getResource("/default/splash/default.css").toExternalForm());

		newScene.setFill(Color.TRANSPARENT);
		return newScene;
	}

	@Override
	public Labeled getPhaseLabel() {
		return phase;
	}
	@Override
	public Labeled getActionLabel() {
		return action;
	}
	@Override
	public ProgressIndicator getProgressIndicator() {
		return progress;
	}

	@Override
	public String getPhase() {
		return phase.getText();
	}
	@Override
	public void setPhase(String _phase) {
		phase.setText(_phase);
	}

	@Override
	public String getAction() {
		return action.getText();
	}
	@Override
	public void setAction(String _action) {
		action.setText(_action);
	}

	@Override
	public double getProgression() {
		return progress.getProgress();
	}

	@Override
	public void setProgression(double _percent) {
		progress.setProgress(_percent);
	}

	@Override
	public void increaseProgression(double _step) {
		progress.setProgress(getProgression() + _step);
	}

	@Override
	public void decreaseProgression(double _step) {
		progress.setProgress(getProgression() - _step);
	}

}
