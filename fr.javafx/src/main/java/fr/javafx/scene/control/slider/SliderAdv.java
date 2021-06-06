/**
 * JavaFR
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.control.slider;

import fr.javafx.utils.DoubleConverter;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public class SliderAdv<T extends Comparable<?>> extends Control {
    private Slider              slider;
    private ObjectProperty<T>	value;

	protected SliderAdv(double _min, double _max, double _value, DoubleConverter<T> _doubleConverter) {
		super();

		slider = new Slider(_min, _max, _value);
		value  = new SimpleObjectProperty<T>();

        initSlider();
//      initSliderTicks(3, 0);
        initValueBinding(_doubleConverter);
	}
	protected SliderAdv(double _min, double _max, double _value, DoubleConverter<T> _doubleConverter, StringConverter<Double> _toTicks) {
		this(_min, _max, _value, _doubleConverter);

		initSliderTickFormat(_toTicks);
	}
	protected SliderAdv(double _min, double _max, double _value, double _step, DoubleConverter<T> _doubleConverter) {
		this(_min, _max, _value, _doubleConverter);

        initSliderTicks(_step, 0);
		initSliderBlockIncrement(_step);
	}
	protected SliderAdv(double _min, double _max, double _value, double _step, DoubleConverter<T> _doubleConverter, StringConverter<Double> _toTicks) {
		this(_min, _max, _value, _step, _doubleConverter);

		initSliderTickFormat(_toTicks);
	}

	protected SliderAdv(T _min, T _max, T _value, DoubleConverter<T> _doubleConverter) {
		this(_doubleConverter.toDouble(_min), _doubleConverter.toDouble(_max), _doubleConverter.toDouble(_value), _doubleConverter);
	}
	protected SliderAdv(T _min, T _max, T _value, DoubleConverter<T> _doubleConverter, StringConverter<Double> _toTicks) {
		this(_min, _max, _value, _doubleConverter);

		initSliderTickFormat(_toTicks);
	}
	protected SliderAdv(T _min, T _max, T _value, double _step, DoubleConverter<T> _doubleConverter) {
		this(_doubleConverter.toDouble(_min), _doubleConverter.toDouble(_max), _doubleConverter.toDouble(_value), _step, _doubleConverter);
	}
	protected SliderAdv(T _min, T _max, T _value, double _step, DoubleConverter<T> _doubleConverter, StringConverter<Double> _toTicks) {
		this(_min, _max, _value, _step, _doubleConverter);

		initSliderTickFormat(_toTicks);
	}

	protected Skin<?> 				createDefaultSkin() {
		return new Skin<SliderAdv<T>>() {

			@Override
			public SliderAdv<T> getSkinnable() { return SliderAdv.this; }

			@Override
			public Node getNode() { return slider; }

			@Override
			public void dispose() { }
			
		};
	}

	public Slider					slider() {
		return slider;
	}

	public DoubleProperty			valueProperty() {
		return slider.valueProperty();
	}
	public ObjectProperty<T> 		objectProperty() {
		return value;
	}

	protected void initSlider() {
		slider . setShowTickLabels(true);
        slider . setShowTickMarks(true);
        slider . setSnapToTicks(true);
	}
	protected void initSliderBlockIncrement(double _step) {
		double min = slider.getMin(),
			   max = slider.getMax();

        final ChangeListener<Number> numberChangeListener = (obs, old, val) -> {
        	if(val.doubleValue() > max - (_step / 10d)) {
        		slider.valueProperty().set(max);
        	} else {
                final double roundedValue = min + Math.floor((val.doubleValue() - min) / _step) * _step;

                slider.valueProperty().set(roundedValue);
        	}
        };

        slider.setBlockIncrement(_step);
        slider.valueProperty().addListener(numberChangeListener);
	}
	protected void initSliderTicks(int _major, int _minor) {
		double range = slider.getMax() - slider.getMin();

		slider.setMajorTickUnit(range / (double) (_major - 1));
		slider.setMinorTickCount(_minor);
	}
	protected void initSliderTicks(double _majorSteps, int _minorCount) {
		slider.setMajorTickUnit(_majorSteps);
		slider.setMinorTickCount(_minorCount);
	}
	protected void initSliderTickFormat(StringConverter<Double> _toTicks) {
		if(_toTicks != null)
			slider.setLabelFormatter(_toTicks);
	}

	protected void initValueBinding(DoubleConverter<T> _doubleConverter) {
        value  . bind(new ObjectBinding<T>() {
        	{ bind(slider.valueProperty()); }

			@Override protected T computeValue()
        	{ return _doubleConverter.fromDouble( slider.getValue() ); }

        });
	}

}
