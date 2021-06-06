package fr.javafx.temp.nodes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class IntField extends TextField {
	final private IntegerProperty	value;
	final private int				minValue;
	final private int				maxValue;

	public IntField(int _minValue, int _maxValue, int _initialValue) {
		if(_minValue > _maxValue)
			throw new IllegalArgumentException("IntField min value " + _minValue + " greater than max value " + _maxValue);
		if(_maxValue < _minValue)
			throw new IllegalArgumentException("IntField max value " + _minValue + " less than min value " + _maxValue);
		if(!( (_minValue <= _initialValue) && (_initialValue <= _maxValue) ))
			throw new IllegalArgumentException("IntField initialValue " + _initialValue + " not between " + _minValue + " and " + _maxValue);

		value    = new SimpleIntegerProperty(_initialValue);
		minValue = _minValue;
		maxValue = _maxValue;

		setText(_initialValue + "");

		value.addListener(clampValueToDomain());
		textProperty().addListener(clampKeyInputToDomain());

		addEventFilter(KeyEvent.KEY_TYPED, restrictToDigitalInput());
		setOnScroll((e) -> { value.set(value.get() + (e.getDeltaY() > 0 ? 1 : - 1)); e.consume(); });
	}

	public int getValue() {
		return value.getValue();
	}
	public void setValue(int newValue) {
		value.setValue(newValue);
	}
	public IntegerProperty valueProperty() {
		return value;
	}

	private ChangeListener<Number> clampValueToDomain() {
		return (_obs, _old, _new) -> {
			if(_new == null)
				setText("");
			else {
				if(_new.intValue() < minValue) {
					value.setValue(minValue);
					return;
				}

				if(_new.intValue() > maxValue) {
					value.setValue(maxValue);
					return;
				}

				if(_new.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
					// no action required, text property is already blank, we don't need to set it to 0.
				} else {
					setText(_new.toString());
				}
			}
		};
	}
	private ChangeListener<String> clampKeyInputToDomain() {
		return (_obs, _old, _new) -> {
			if(_new == null || "".equals(_new)) {
				value.setValue(0);
				return;
			}

			final int intValue = Integer.parseInt(_new);

			if(minValue > intValue || intValue > maxValue) {
				textProperty().setValue(_old);
			}

			value.set(Integer.parseInt(textProperty().get()));
		};
	}

	private EventHandler<? super KeyEvent> restrictToDigitalInput() {
		return (keyEvent) -> {
			if(!"0123456789".contains(keyEvent.getCharacter()))
				keyEvent.consume();
		};
	}

}