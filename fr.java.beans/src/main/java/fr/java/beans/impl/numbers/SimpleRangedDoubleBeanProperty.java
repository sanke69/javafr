package fr.java.beans.impl.numbers;

public class SimpleRangedDoubleBeanProperty extends RangedDoubleBeanProperty {

	protected SimpleRangedDoubleBeanProperty() {
		super();
	}
	protected SimpleRangedDoubleBeanProperty(Double _value, Double _min, Double _max) {
		super(_value, _min, _max);
	}
	protected SimpleRangedDoubleBeanProperty(String _propertyName) {
		super(_propertyName);
	}
	protected SimpleRangedDoubleBeanProperty(String _propertyName, Double _value, Double _min, Double _max) {
		super(_propertyName, _value, _min, _max);
	}
	protected SimpleRangedDoubleBeanProperty(Object _bean, String _propertyName) {
		super(_bean, _propertyName);
	}
	protected SimpleRangedDoubleBeanProperty(Object _bean, String _propertyName, Double _value, Double _min, Double _max) {
		super(_bean, _propertyName, _value, _min, _max);
	}

}
