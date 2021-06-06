package fr.javafx.scene.control.editor.validator;

import java.text.NumberFormat;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class LongValidator extends AbstractNumericValidator {
	private NumberFormat 				format;

	public LongValidator(ObjectProperty<Number> _valueProperty, StringProperty _textProperty, boolean _usePrimitive) {
		this(_valueProperty, _textProperty, _usePrimitive, - Long.MAX_VALUE, + Long.MAX_VALUE);
	}
	public LongValidator(ObjectProperty<Number> _valueProperty, StringProperty _textProperty, boolean _usePrimitive, long _min, long _max) {
		super(_valueProperty, _textProperty, _usePrimitive, _min, _max);

		format = NumberFormat.getInstance();
		format . setGroupingUsed(false);
	}

	@Override
	public void handleChangeText(ObservableValue<? extends String> _obs, String _old, String _new) {
		String regex = "([-]?)\\d*";

		if(_new.length() > 0 && _new.matches(regex)) {
			Long newValue = Long.parseLong(_new);

			if(newValue >= minProperty().getValue().doubleValue() && newValue <= maxProperty().getValue().doubleValue())
				valueProperty() .set(newValue);
			else
				textProperty().set(_old);

		} else if(_new.length() == 0) {
			valueProperty() .set(isPrimitive() ? 0 : null);

		} else {
			textProperty().set(_old);

		};
	}
	@Override
	public void handleChangeValue(ObservableValue<? extends Number> _obs, Number _old, Number _new) {
		if(_new != null)
			textProperty().set(format.format(_new));
		else
			textProperty().set(nullText);
	}

}
