package fr.javafx.beans.propertysheet.editors;

import java.util.Arrays;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;

public class EnumPropertyEditor<T> extends BasePropertyEditor<T, ComboBox<T>> {

	@SuppressWarnings("unchecked")
	public EnumPropertyEditor(Item _property) {
		super(_property, new ComboBox<T>());

		getEditor().setItems((ObservableList<T>) FXCollections.observableArrayList( Arrays.<Object>asList(_property.getType().getEnumConstants()) ));
	}


	@Override
	protected ObservableValue<T> 	getObservableValue() {
		return getEditor().getSelectionModel().selectedItemProperty();
	}

	@Override
	public void 					setValue(T value) {
		getEditor().getSelectionModel().select(value);
	}

}
