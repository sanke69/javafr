package fr.javafx.beans.propertysheet.editors;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;

public class ColorPropertyEditor extends BasePropertyEditor<Color, ColorPicker> {

	public ColorPropertyEditor(Item _property) {
		super(_property, new ColorPicker());
	}

	@Override
	protected ObservableValue<Color> getObservableValue() {
		return getEditor().valueProperty();
	}

	@Override
	public void setValue(Color value) {
		getEditor().setValue((Color) value);
	}

}
