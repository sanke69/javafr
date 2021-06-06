package fr.javafx.scene.control.editor.validator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public abstract class AbstractNumericValidator implements NumericValidator {
	static final String nullText = "<null>";

	private final ObjectProperty<Number> valueProperty;
	private final StringProperty 		 textProperty;

	private final boolean 				 primitiveType;
	private final ObjectProperty<Number> minProperty, maxProperty;

	AbstractNumericValidator(ObjectProperty<Number> _valueProperty, StringProperty _textProperty, 
			 					boolean _primitiveType, Number _min, Number _max) {
			super();
			valueProperty = _valueProperty;
			textProperty  = _textProperty;

			primitiveType = _primitiveType;

			minProperty  = new SimpleObjectProperty<Number>(_min);
			maxProperty  = new SimpleObjectProperty<Number>(_max);
	}

	protected boolean 				isPrimitive() {
		return primitiveType;
	}

	@Override
	public ObjectProperty<Number> 	valueProperty() {
		return valueProperty;
	}
	@Override
	public StringProperty			textProperty() {
		return textProperty;
	}

	@Override
	public ObservableValue<Number> 	minProperty() {
		return minProperty;
	}

	@Override
	public ObservableValue<Number> 	maxProperty() {
		return maxProperty;
	}

}
