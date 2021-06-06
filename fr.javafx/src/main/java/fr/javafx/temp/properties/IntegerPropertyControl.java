package fr.javafx.temp.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class IntegerPropertyControl extends Control {
	StringProperty propertyName;
	IntegerProperty min, max, value;

	ChangeListener<? super Number> handler;

	public IntegerPropertyControl(String _name, int _min, int _max, int _value, ChangeListener<? super Number> _l) {
		super();
		
		propertyName = new SimpleStringProperty(_name);
		min          = new SimpleIntegerProperty(_min);
		max          = new SimpleIntegerProperty(_max);
		value        = new SimpleIntegerProperty(_value);

		value.addListener(handler = _l);
	}

	@Override
	protected Skin<IntegerPropertyControl> createDefaultSkin() {
		return new IntegerPropertyControlSkin(this);
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

	public IntegerProperty 			minProperty() {
		return min;
	}
	public void 					setMin(int _newMin) {
		min.set(_newMin);
	}
	public int 						getMin() {
		return min.get();
	}

	public IntegerProperty 			maxProperty() {
		return max;
	}
	public void 					setMax(int _newMax) {
		max.set(_newMax);
	}
	public int 						getMax() {
		return max.get();
	}

	public IntegerProperty 			valueProperty() {
		return value;
	}
	public void 					setValue(int _newValue) {
		value.set(_newValue);
	}
	public int	 					getValue() {
		return value.get();
	}

}
