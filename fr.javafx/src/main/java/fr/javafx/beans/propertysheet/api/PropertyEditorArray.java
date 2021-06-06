package fr.javafx.beans.propertysheet.api;

import javafx.scene.Node;

public interface PropertyEditorArray<T> {

	public Node getEditor();

	public T[]  getValues();
	public void setValues(T[] _values);

	public T    getValue(int _index);
	public void setValue(int _index, T _value);

}
