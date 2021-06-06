package fr.javafx.beans.propertysheet.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import fr.javafx.scene.control.editor.TextEditor;

public abstract class BasePropertyEditorDialog<T> extends HBox {

//	private static final Image image = new Image(AbstractObjectField.class.getResource("/tmp/open-editor.png").toExternalForm());

	private final TextEditor textField = new TextEditor();

	private ObjectProperty<T> objectProperty = new SimpleObjectProperty<>();

	public BasePropertyEditorDialog() {
		super(1);
		textField.setEditable(false);
		textField.setFocusTraversable(false);

		StackPane button = new StackPane(new Button("FONT")/*new ImageView(image)*/);
		button.setCursor(Cursor.DEFAULT);

		button.setOnMouseReleased(e -> {
			if(MouseButton.PRIMARY == e.getButton()) {
				final T result = edit(objectProperty.get());
				if(result != null) {
					objectProperty.set(result);
				}
			}
		});

		textField.setRight(button);
		getChildren().add(textField);
		HBox.setHgrow(textField, Priority.ALWAYS);

		objectProperty.addListener((o, oldValue, newValue) -> textProperty().set(objectToString(newValue)));
	}

	protected StringProperty textProperty() {
		return textField.textProperty();
	}

	public ObjectProperty<T> getObjectProperty() {
		return objectProperty;
	}

	protected String objectToString(T object) {
		return object == null ? "" : object.toString();
	}

	protected abstract Class<T> getType();

	protected abstract T edit(T object);

}
