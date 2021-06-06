package fr.javafx.scene.control.editor.validator;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;

public abstract class BigDecimalValidator extends SimpleObjectProperty<BigDecimal> implements NumericValidator {
/*
	private NumberFormat 				format;
	private ObservableValue<BigDecimal> min, max;

	public BigDecimalValidator(NumberFieldSimpleSkin _field) {
		super(NumberFieldSimpleSkin.this, "value", new BigDecimal(0.0));
		format = NumberFormat.getInstance();
		format.setGroupingUsed(false);

		min = new SimpleObjectProperty<BigDecimal>(new BigDecimal(Long.MIN_VALUE));
		max = new SimpleObjectProperty<BigDecimal>(new BigDecimal(Long.MAX_VALUE));
	}
	public BigDecimalValidator(NumberFieldSimpleSkin _field, BigDecimal _min, BigDecimal _max) {
		super(NumberFieldSimpleSkin.this, "value", new BigDecimal(0.0));
		format = NumberFormat.getInstance();
		format.setGroupingUsed(false);

		min = new SimpleObjectProperty<BigDecimal>(_min);
		max = new SimpleObjectProperty<BigDecimal>(_max);
	}

	@Override
	public ObservableValue<BigDecimal> minProperty() {
		return min;
	}
	@Override
	public ObservableValue<BigDecimal> maxProperty() {
		return max;
	}

	@Override
	public void handleChangeText(ObservableValue<? extends String> _obs, String _old, String _new) {
		String regex = "([-]?)\\d*([\\.]\\d*)?";

		if(_new.matches(regex)) {
			BigDecimal newValue = new BigDecimal(_new);
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