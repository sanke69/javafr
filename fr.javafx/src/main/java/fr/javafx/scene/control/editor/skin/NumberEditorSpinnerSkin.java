package fr.javafx.scene.control.editor.skin;

import fr.javafx.scene.control.editor.NumberEditor;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class NumberEditorSpinnerSkin extends HBox      implements Skin<NumberEditor> {
	public  static final String	ARROW				= "NumberSpinnerArrow";
	public  static final String	NUMBER_FIELD		= "NumberField";
	public  static final String	NUMBER_SPINNER		= "NumberSpinner";
	public  static final String	SPINNER_BUTTON_UP	= "SpinnerButtonUp";
	public  static final String	SPINNER_BUTTON_DOWN	= "SpinnerButtonDown";
	private static final String	BUTTONS_BOX			= "ButtonsBox";
	private static final double	ARROW_SIZE			= 4;

	private final NumberEditor 	control;

	private final NumberEditorTextualSkin	numberField;
	private final Button		incrementButton;
	private final Button		decrementButton;

	public NumberEditorSpinnerSkin(NumberEditor _control) {
		super();
		control = _control;

		setId(NUMBER_SPINNER);

		numberField = new NumberEditorTextualSkin(_control);
		numberField.setId(NUMBER_FIELD);
		numberField.prefWidthProperty().bind(widthProperty().subtract(27));

		Path arrowUp = new Path();
		arrowUp.setId(ARROW);
		arrowUp.getElements().addAll(new MoveTo(-ARROW_SIZE, 0), new LineTo(ARROW_SIZE, 0), new LineTo(0, -ARROW_SIZE), new LineTo(-ARROW_SIZE, 0));

		Path arrowDown = new Path();
		arrowDown.setId(ARROW);
		arrowDown.getElements().addAll(new MoveTo(-ARROW_SIZE, 0), new LineTo(ARROW_SIZE, 0), new LineTo(0, ARROW_SIZE), new LineTo(-ARROW_SIZE, 0));

		NumberBinding buttonWidth  = widthProperty().subtract(numberField.prefWidthProperty());
		NumberBinding buttonHeight = numberField.heightProperty().subtract(3).divide(2);

		VBox buttons = new VBox();
		buttons.setId(BUTTONS_BOX);

		incrementButton = new Button("", arrowUp);
		incrementButton.setId(SPINNER_BUTTON_UP);
		incrementButton.prefWidthProperty().bind(buttonWidth);
		incrementButton.minWidthProperty().bind(buttonWidth);
		incrementButton.maxHeightProperty().bind(buttonHeight);
		incrementButton.prefHeightProperty().bind(buttonHeight);
		incrementButton.minHeightProperty().bind(buttonHeight);

		decrementButton = new Button("", arrowDown);
		decrementButton.setId(SPINNER_BUTTON_DOWN);
		decrementButton.prefWidthProperty().bind(buttonWidth);
		decrementButton.minWidthProperty().bind(buttonWidth);
		decrementButton.maxHeightProperty().bind(buttonHeight);
		decrementButton.prefHeightProperty().bind(buttonHeight);
		decrementButton.minHeightProperty().bind(buttonHeight);

		buttons.getChildren().addAll(incrementButton, decrementButton);
		getChildren().addAll(numberField, buttons);

		getStylesheets().add(getClass().getResource("NumberEditor.SpinnerSkin.css").toExternalForm());

		numberField.addEventFilter(KeyEvent.KEY_PRESSED, (keyEvent) -> {
			if(keyEvent.getCode() == KeyCode.DOWN) {
				spinnerDecrement();
				keyEvent.consume();
			}
			if(keyEvent.getCode() == KeyCode.UP) {
				spinnerIncrement();
				keyEvent.consume();
			}
		});
		numberField.addEventFilter(ScrollEvent.ANY, (scrollEvent) -> {
			if(scrollEvent.getDeltaY() < 0) {
				spinnerDecrement();
				scrollEvent.consume();
			}
			if(scrollEvent.getDeltaY() > 0) {
				spinnerIncrement();
				scrollEvent.consume();
			}
		});
		incrementButton.setOnAction((ae) -> { spinnerIncrement(); ae.consume(); });
		decrementButton.setOnAction((ae) -> { spinnerDecrement(); ae.consume(); });
	}

	@Override
	public NumberEditor 							getSkinnable() {
		return control;
	}
	@Override
	public Node 									getNode() {
		return this;
	}
	@Override
	public void 									dispose() {
		;
	}

	void spinnerIncrement() {
		control.setValue(control.getValue().doubleValue() + control.getStep().doubleValue());
	}
	void spinnerDecrement() {
		control.setValue(control.getValue().doubleValue() - control.getStep().doubleValue());
	}

}
