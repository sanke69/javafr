package fr.javafx.scene.control.editor.validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class DoubleValidator extends AbstractNumericValidator {
	private NumberFormat 				 format;
	private DecimalFormatSymbols 		 symbols;

	public DoubleValidator(ObjectProperty<Number> _valueProperty, StringProperty _textProperty, boolean _usePrimitive) {
		this(_valueProperty, _textProperty, _usePrimitive, - Double.MAX_VALUE, + Double.MAX_VALUE);
	}
	public DoubleValidator(ObjectProperty<Number> _valueProperty, StringProperty _textProperty, boolean _usePrimitive, double _min, double _max) {
		super(_valueProperty, _textProperty, _usePrimitive, _min, _max);

		symbols = new DecimalFormatSymbols();
		symbols . setDecimalSeparator('.');
		symbols . setGroupingSeparator('\0');

		format  = new DecimalFormat("", symbols);
	}

	@Override
	public void 					handleChangeText(ObservableValue<? extends String> _obs, String _old, String _new) {
//		String regex = "([-]?)\\d{0,7}([\\.]\\d*)?";
//		String regex = "([-]?)\\d*([\\.]\\d*)?";
		String regex = "([+-]?)\\d{0,9}([\\.]\\d*)?";

		if(_new == null || _new.length() == 0) {
			valueProperty().set(null);

			if(isPrimitive()) {
				valueProperty() .set(null);
				textProperty()  .set("0");
			} else {
				valueProperty() .set(null);
				textProperty()  .set(nullText);
			}

		} else if(_new.length() > 0 && _new.matches(regex)) {
			Double newValue = Double.parseDouble(_new);
			if(newValue >= minProperty().getValue().doubleValue() && newValue <= maxProperty().getValue().doubleValue())
				valueProperty() .set(newValue);
			else
				textProperty().set(_old);
		}
	}
	@Override
	public void 					handleChangeValue(ObservableValue<? extends Number> _obs, Number _old, Number _new) {
		if(_new != null)
			textProperty().set(format.format(_new));
		else
			textProperty().set(nullText);
	}

}