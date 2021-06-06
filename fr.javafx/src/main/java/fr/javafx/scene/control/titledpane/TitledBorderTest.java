package fr.javafx.scene.control.titledpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@Deprecated
public class TitledBorderTest extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(new SelectableTitledPane("TEST", new Button("test")), 320, 240));
		primaryStage.show();
	}

}
