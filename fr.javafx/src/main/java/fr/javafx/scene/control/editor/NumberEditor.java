package fr.javafx.scene.control.editor;

import java.util.LinkedList;

import fr.java.beans.reflect.utils.Primitives;
import fr.java.lang.enums.Primitive;
import fr.java.maths.Numbers;
import fr.javafx.scene.control.editor.skin.NumberEditorSliderSkin;
import fr.javafx.scene.control.editor.skin.NumberEditorSpinnerSkin;
import fr.javafx.scene.control.editor.skin.NumberEditorTextualSkin;
import fr.javafx.scene.properties.Editor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;

public class NumberEditor extends Control implements Editor<Number> {
	public static enum Style { Simple, Spinner, Slider; }

	private Style							style;
	private final boolean					isPrimitive;
	private final Primitive 				numberType;

	private final ObjectProperty<Number> 	min, max, step;
	private final ObjectProperty<Number> 	value;

	public NumberEditor(Class<? extends Number> _cls) {
		this(_cls, getMin(_cls), getMax(_cls), 0, 0.1, Style.Simple);
	}
	public NumberEditor(Class<? extends Number> _cls, Number _min, Number _max, Number _value) {
		this(_cls, _min, _max, _value, 0.1, Style.Simple);
	}
	public NumberEditor(Class<? extends Number> _cls, Number _min, Number _max, Number _value, Number _step) {
		this(_cls, _min, _max, _value, _step, Style.Simple);
	}
	public NumberEditor(Class<? extends Number> _cls, Style _style) {
		this(_cls, getMin(_cls), getMax(_cls), 0, 0.1, _style);
	}
	public NumberEditor(Class<? extends Number> _cls, Number _min, Number _max, Number _value, Style _style) {
		this(_cls, _min, _max, _value, 0.1, _style);
	}
	public NumberEditor(Class<? extends Number> _cls, Number _min, Number _max, Number _value, Number _step, Style _style) {
		super();

		style       = _style;

		isPrimitive = Primitives.isPrimitiveType(_cls);
		numberType  = Primitive.of(_cls);

		min   = new SimpleObjectProperty<Number>(_min);
		max   = new SimpleObjectProperty<Number>(_max);
		value = new SimpleObjectProperty<Number>(_value);
		step  = new SimpleObjectProperty<Number>(_step);

		plugins.addListener(pluginsChanged);
	}

	@Override
	protected Skin<?> 								createDefaultSkin() {
		switch(style) {
		case Slider:	return new NumberEditorSliderSkin(this);
		case Spinner:	return new NumberEditorSpinnerSkin(this);
		case Simple:
		default:		return new NumberEditorTextualSkin(this);
		}
	}
	
	@Override
	public Region 									getNode() {
		return this;
	}

	public boolean 									isPrimitive() {
		return isPrimitive;
	}
	public Primitive 								getNumberType() {
		return numberType;
	}

	public final ObjectProperty<Number> 			valueProperty() {
		return value;
	}
	public final void 								setValue(Number _value) {
		value.set(_value);
	}
	public final Number 							getValue() {
		Number tmp = value.getValue();
		
		if(tmp != null)
			return Numbers.convert(tmp, numberType);
		return null;
	}

	public final ObservableValue<Number> 			minProperty() {
		return min;
	}
	public final void 								setMin(Number _value) {
		min.set(_value);
	}
	public final Number		 						getMin() {
		Number tmp = minProperty().getValue();
		
		return Numbers.convert(tmp, numberType);
	}

	public final ObservableValue<Number> 			maxProperty() {
		return max;
	}
	public final void 								setMax(Number _value) {
		max.set(_value);
	}
	public final Number 							getMax() {
		Number tmp = maxProperty().getValue();
		
		return Numbers.convert(tmp, numberType);
	}

	public final ObservableValue<Number> 			stepProperty() {
		return step;
	}
	public final void 								setStep(Number _value) {
		step.set(_value);
	}
	public final Number 							getStep() {
		Number tmp = stepProperty().getValue();
		
		return Numbers.convert(tmp, numberType);
	}

	/*** PlugIns ***/
	private final ObservableList<Plugin> 	 plugins        = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<Plugin> pluginsChanged = (change) -> {
		while(change.next()) {
			change.getRemoved().forEach(this::pluginRemoved);
			change.getAddedSubList().forEach(this::pluginAdded);
		}
	};

	public final ObservableList<Plugin> 	getPlugins() {
		return plugins;
	}

	private void 							pluginAdded(Plugin _plugin) {
		_plugin.setEditor(this);
	}
	private void 							pluginRemoved(Plugin _plugin) {
		_plugin.setEditor(null);
	}

	private static Number	getMin(Class<? extends Number> _cls) {
		switch( Primitive.of(_cls) ) {
		case BYTE    : return Byte.MIN_VALUE;
		case SHORT   : return Short.MIN_VALUE;
		case INTEGER : return Integer.MIN_VALUE;
		case LONG    : return Long.MIN_VALUE;
		case FLOAT   : return Float.MIN_VALUE;
		case DOUBLE  : return Double.MIN_VALUE;
		default      : throw new IllegalArgumentException();
		}
	}
	private static Number	getMax(Class<? extends Number> _cls) {
		switch( Primitive.of(_cls) ) {
		case BYTE    : return Byte.MAX_VALUE;
		case SHORT   : return Short.MAX_VALUE;
		case INTEGER : return Integer.MAX_VALUE;
		case LONG    : return Long.MAX_VALUE;
		case FLOAT   : return Float.MAX_VALUE;
		case DOUBLE  : return Double.MAX_VALUE;
		default      : throw new IllegalArgumentException();
		}
	}

}
