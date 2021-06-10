package fr.java.maths.numbers.ranged;

import fr.java.math.Interval;
import fr.java.math.numbers.Ranged;

public class RangedNumber<T extends Number> implements Ranged<T> {

	public static class Editable<T extends Number> extends RangedNumber<T> implements Ranged.Editable<T> {

		public Editable(T _min, T _max) {
			super(_min, _max);
		}

		@Override
		public void 	setValue(T _value) {
			if(min.doubleValue() < _value.doubleValue() && _value.doubleValue() < max.doubleValue())
				value = _value;
			else if(min.doubleValue() > _value.doubleValue())
				value = min;
			else if(max.doubleValue() < _value.doubleValue())
				value = max;
		}
		

		@Override
		public void 	setRange(T _min, T _max) {
			min = _min;
			max = _max;
		}
		@Override
		public void 	setRange(Interval<T> _range) {
			min = _range.getMin();
			max = _range.getMax();
		}

		@Override
		public void 	setMin(T _min) {
			min = _min;
		}
		@Override
		public void 	setMax(T _max) {
			max = _max;
		}
	}

	T value, min, max;

	public RangedNumber(T _min, T _max) {
		super();
		value = _min;
		min   = _min;
		max   = _max;
	}
	public RangedNumber(Interval<T> _range) {
		super();
		value = _range.getMin();
		min   = _range.getMin();
		max   = _range.getMax();
	}
	public RangedNumber(T _value, T _min, T _max) {
		super();
		value = _min;
		min   = _min;
		max   = _max;
	}
	public RangedNumber(T _value, Interval<T> _range) {
		super();
		value = _value;
		min   = _range.getMin();
		max   = _range.getMax();
	}

	@Override
	public T 			getValue() {
		return value;
	}

	@Override
	public Interval<T>	getRange() {
		return Interval.of(min, max);
	}

	@Override
	public T 			getMin() {
		return min;
	}
	@Override
	public T 			getMax() {
		return max;
	}

}
