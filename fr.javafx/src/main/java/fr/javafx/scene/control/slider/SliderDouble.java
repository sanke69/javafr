package fr.javafx.scene.control.slider;

import fr.javafx.utils.DoubleConverter;

public class SliderDouble extends SliderAdv<Double> {

	protected SliderDouble(double _min, double _max, double _value) {
		super(_min, _max, _value, DoubleConverter.identity());
	}
	public SliderDouble(double _min, double _max, double _value, double _step) {
		super(_min, _max, _value, _step, DoubleConverter.identity());
	}

}
