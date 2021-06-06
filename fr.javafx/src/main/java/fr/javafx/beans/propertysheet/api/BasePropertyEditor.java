package fr.javafx.beans.propertysheet.api;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import fr.javafx.beans.propertysheet.PropertySheet.Item;

public abstract class BasePropertyEditor<T, C extends Node> implements PropertyEditor<T> {

	private final Item	property;
	private final C		control;
	private boolean		suspendUpdate;

	public BasePropertyEditor(Item _property, C _control) {
		this(_property, _control, !_property.isEditable());
	}
	@SuppressWarnings("unchecked")
	public BasePropertyEditor(Item _property, C _control, boolean _readOnly) {
		super();
		control  = _control;
		property = _property;

		if(!_readOnly) {
			getObservableValue().addListener((_obs, _old, _new) -> {
				if(!suspendUpdate) {
					suspendUpdate = true;
					BasePropertyEditor.this.property.setValue(getValue());
					suspendUpdate = false;
				}
			});

			if(_property.getObservableValue().isPresent()) {
				_property.getObservableValue().get().addListener((ObservableValue<? extends Object> o, Object oldValue, Object newValue) -> {
					if(!suspendUpdate) {
						suspendUpdate = true;
						BasePropertyEditor.this.setValue((T) _property.getValue());
						suspendUpdate = false;
					}
				});
			}
		}
	}

	protected abstract ObservableValue<T> 	getObservableValue();

	public final Item 						getProperty() {
		return property;
	}

	@Override
	public C 								getEditor() {
		return control;
	}

	@Override
	public T 								getValue() {
		return getObservableValue().getValue();
	}

}
