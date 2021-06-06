package fr.javafx.beans.propertysheet.api;

import javafx.scene.Node;

public interface PropertyEditor<T> /*extends java.beans.PropertyEditor*/ {

	public Node getEditor();

	public T    getValue();
	public void setValue(T _value);

}
