package fr.javafx.scene.control.overlay.drags;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

import fr.javafx.behavior.Visual;

public class FileDropControlVisualDefault extends StackPane implements Visual<FileDropControl> {
	private final FileDropControl control;

	private static final String URL = "http://sp-web.fr/images/favicon.png";

	private Label     label;
	private ImageView image;

	public FileDropControlVisualDefault(FileDropControl _control) {
		super();
		control = _control;

		label = new Label("Drag&Drop \n File" + (_control.isMultiFileAllowed() ? "(s)" : "") + " here...");
		label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 16; -fx-text-fill: black;");
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);

		image = new ImageView(new Image(URL));
		image.setOpacity(0.3);

		setStyle("-fx-background-color: green;");
		getChildren().setAll(image, label);
	}

	@Override
	public FileDropControl getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		;
	}

}
