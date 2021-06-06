package fr.javafx.beans.propertysheet.editors;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;
import fr.javafx.scene.control.editor.NumberEditor;
import javafx.beans.value.ObservableValue;

public class NumericPropertyEditor extends BasePropertyEditor<Number, NumberEditor> {

	@SuppressWarnings("unchecked")
	public NumericPropertyEditor(Item _property) {
		super(_property, new NumberEditor( (Class<? extends Number>) _property.getType() ));
	}

	@Override
	protected ObservableValue<Number> getObservableValue() {
		return (ObservableValue<Number>) getEditor().valueProperty();
	}

	@Override
	public Number getValue() {
		return getEditor().getValue();
	}

	@Override
	public void setValue(Number value) {
		getEditor().setValue(value);
	}

}
