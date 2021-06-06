package fr.javafx.scene.control.editor.validator;

import java.math.BigInteger;
import java.text.NumberFormat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public abstract class BigIntegerValidator extends SimpleObjectProperty<BigInteger> implements NumericValidator {
/*
		private NumberFormat 				format;
		private ObservableValue<BigInteger> min, max;

		public BigIntegerValidator(NumberFieldSimpleSkin _field) {
			super(NumberFieldSimpleSkin.this, "value", new BigInteger("0"));
			format = NumberFormat.getInstance();
			format.setGroupingUsed(false);

			min = new SimpleObjectProperty<BigInteger>(new BigInteger("" + Long.MIN_VALUE));
			max = new SimpleObjectProperty<BigInteger>(new BigInteger("" + Long.MAX_VALUE));
		}
		public BigIntegerValidator(NumberFieldSimpleSkin _field, BigInteger _min, BigInteger _max) {
			super(NumberFieldSimpleSkin.this, "value", new BigInteger("0"));
			format = NumberFormat.getInstance();
			format.setGroupingUsed(false);

			min = new SimpleObjectProperty<BigInteger>(_min);
			max = new SimpleObjectProperty<BigInteger>(_max);
		}

		@Override
		public ObservableValue<BigInteger> minProperty() {
			return min;
		}
		@Override
		public ObservableValue<BigInteger> maxProperty() {
			return max;
		}

		@Override
		public void handleChangeText(ObservableValue<? extends String> _obs, String _old, String _new) {
			String regex = "([-]?)\\d*";

			if(_new.matches(regex)) {
				BigInteger newValue = new BigInteger(_new);
				if(newValue.compareTo(min.getValue()) > 0 && newValue.compareTo(max.getValue()) < 0)
					setValue(newValue);
				else
					NumberFieldSimpleSkin.this.setText(_old);
			} else
				NumberFieldSimpleSkin.this.setText(_old);
		}
		@Override
		public void handleChangeValue(ObservableValue<? extends Number> _obs, Number _old, Number _new) {
			NumberFieldSimpleSkin.this.setText(format.format(_new));
		}
*/
	}