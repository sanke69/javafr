package fr.javafx.temp.properties;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ScalarPropertyControl extends Control {
	StringProperty propertyName;
	DoubleProperty min, max, value;

	ChangeListener<? super Number> handler;

	public ScalarPropertyControl(String _name, double _min, double _max, double _value, ChangeListener<? super Number> _l) {
		super();
		
		propertyName = new SimpleStringProperty(_name);
		min          = new SimpleDoubleProperty(_min);
		max          = new SimpleDoubleProperty(_max);
		value        = new SimpleDoubleProperty(_value);

		value.addListener(handler = _l);
	}

	@Override
	protected Skin<ScalarPropertyControl> createDefaultSkin() {
		return new ScalarPropertyControlSkin(this);
	}

	public StringProperty 			nameProperty() {
		return propertyName;
	}
	public void 					setName(String _newName) {
		propertyName.set(_newName);
	}
	public String 					getName() {
		return propertyName.get();
	}

	public DoubleProperty 			minProperty() {
		return min;
	}
	public void 					setMin(double _newMin) {
		min.set(_newMin);
	}
	public double 					getMin() {
		return min.get();
	}

	public DoubleProperty 			maxProperty() {
		return max;
	}
	public void 					setMax(double _newMax) {
		max.set(_newMax);
	}
	public double 					getMax() {
		return max.get();
	}

	public DoubleProperty 			valueProperty() {
		return value;
	}
	public void 					setValue(double _newValue) {
		value.set(_newValue);
	}
	public double 					getValue() {
		return value.get();
	}

}
