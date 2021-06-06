package fr.javafx.beans.propertysheet.editors;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;

public class StringPropertyEditor extends BasePropertyEditor<String, TextField> {

	public StringPropertyEditor(Item _property) {
		super(_property, new TextField());
	}

	@Override
	protected StringProperty getObservableValue() {
		return getEditor().textProperty();
	}

	@Override
	public void setValue(String value) {
		getEditor().setText(value);
	}

}
