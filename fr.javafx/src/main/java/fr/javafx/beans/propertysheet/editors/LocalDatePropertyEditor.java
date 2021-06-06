package fr.javafx.beans.propertysheet.editors;

import java.time.LocalDate;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;

public class LocalDatePropertyEditor extends BasePropertyEditor<LocalDate, DatePicker> {
	
	public LocalDatePropertyEditor(Item _property) {
		super(_property, new DatePicker());
	}
	
	@Override
	protected ObservableValue<LocalDate> getObservableValue() {
		return getEditor().valueProperty();
	}

	@Override
	public void setValue(LocalDate value) {
		getEditor().setValue((LocalDate) value);
	}

}
