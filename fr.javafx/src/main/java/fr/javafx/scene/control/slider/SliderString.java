package fr.javafx.scene.control.slider;

import fr.javafx.utils.DoubleConverter;

public class SliderString extends SliderAdv<String> {

	protected SliderString(double _min, double _max, double _value) {
		super(_min, _max, _value, DoubleConverter.toString("#.##"));
	}
	protected SliderString(double _min, double _max, double _value, double _step) {
		super(_min, _max, _value, _step, DoubleConverter.toString("#.##"));
	}

}
