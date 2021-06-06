package fr.javafx.beans.propertysheet.api;

import javafx.util.Callback;

import fr.javafx.beans.propertysheet.PropertySheet.Item;

public interface PropertyEditorFactory extends Callback<Item, PropertyEditor<?>> {

	public void 					registerEditor(Class<?> _editorClass, Class<?>... _propertyClasses);
	public void 					unregisterEditor(Class<?> _editorClass);

	public <T> PropertyEditor<T> 	getEditor(Item _item);

	@Override
	public PropertyEditor<?> 		call(Item _item);

}
