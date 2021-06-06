package fr.javafx.scene.control.editor;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TextFields {
	private static final Duration FADE_DURATION = Duration.millis(350);

	private TextFields() {
		;
	}

	public static TextField createClearableTextField() {
		TextEditor inputField = new TextEditor();
		setupClearButtonField(inputField, inputField.rightProperty());
		return inputField;
	}
	public static PasswordField createClearablePasswordField() {
		PasswordEditor inputField = new PasswordEditor();
		setupClearButtonField(inputField, inputField.rightProperty());
		return inputField;
	}

	private static void setupClearButtonField(TextField inputField, ObjectProperty<Node> rightProperty) {
		inputField.getStyleClass().add("clearable-field");

		Region clearButton = new Region();
		clearButton.getStyleClass().addAll("graphic");

		StackPane clearButtonPane = new StackPane(clearButton);
		clearButtonPane.getStyleClass().addAll("clear-button");
		clearButtonPane.setOpacity(0.0);
		clearButtonPane.setCursor(Cursor.DEFAULT);
		clearButtonPane.setOnMouseReleased(e -> inputField.clear());
		clearButtonPane.managedProperty().bind(inputField.editableProperty());
		clearButtonPane.visibleProperty().bind(inputField.editableProperty());

		rightProperty.set(clearButtonPane);

		final FadeTransition fader = new FadeTransition(FADE_DURATION, clearButtonPane);
		fader.setCycleCount(1);

		inputField.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				String text = inputField.getText();
				boolean isTextEmpty = text == null || text.isEmpty();
				boolean isButtonVisible = fader.getNode().getOpacity() > 0;

				if(isTextEmpty && isButtonVisible) {
					setButtonVisible(false);
				} else if(!isTextEmpty && !isButtonVisible) {
					setButtonVisible(true);
				}
			}

			private void setButtonVisible(boolean visible) {
				fader.setFromValue(visible ? 0.0 : 1.0);
				fader.setToValue(visible ? 1.0 : 0.0);
				fader.play();
			}
		});
	}

}
