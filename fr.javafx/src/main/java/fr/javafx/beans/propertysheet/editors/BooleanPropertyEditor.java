package fr.javafx.beans.propertysheet.editors;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;
import fr.javafx.scene.control.editor.BooleanEditor;

import javafx.beans.property.BooleanProperty;

public class BooleanPropertyEditor extends BasePropertyEditor<Boolean, BooleanEditor> {

	public BooleanPropertyEditor(Item _property) {
		super(_property, new BooleanEditor());
	}

	@Override
	protected BooleanProperty 	getObservableValue() {
		return getEditor().valueProperty();
	}

	@Override
	public void 				setValue(Boolean value) {
		getEditor().setValue((Boolean) value);
	}

}
