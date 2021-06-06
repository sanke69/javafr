package fr.javafx.scene.control.editor.validator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public interface NumericValidator {

	public ObjectProperty<Number> 	valueProperty();
	public StringProperty 			textProperty();

	public ObservableValue<Number> 	minProperty();
	public ObservableValue<Number> 	maxProperty();

	public void 					handleChangeText  (ObservableValue<? extends String> _obs, String _old, String _new);
	public void 					handleChangeValue (ObservableValue<? extends Number> _obs, Number _old, Number _new);

}
