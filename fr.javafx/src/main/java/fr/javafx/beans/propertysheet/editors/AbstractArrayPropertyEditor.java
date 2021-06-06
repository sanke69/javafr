package fr.javafx.beans.propertysheet.editors;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.PropertyEditor;

public abstract class AbstractArrayPropertyEditor<TYPE, EDITOR extends PropertyEditor<TYPE>> implements PropertyEditor<TYPE[]> {

	private final Item				property;
	private Class<? extends Number>	arrayType;
	private int						arraySize;

	private final HBox				editor;
	private EDITOR[] 				elementEditors;

	public AbstractArrayPropertyEditor(Item _property) {
		super();

		property = _property;
		editor   = new HBox();
	}

	protected abstract TYPE   	newType();
	protected abstract TYPE[] 	newTypeArray(int _size);
	protected abstract EDITOR   newEditor();
	protected abstract EDITOR[] newEditorArray(int _size);

	@Override
	public Node     getEditor() {
		return editor;
	}

	@Override
	public TYPE[] 	getValue() {
		TYPE[] values = newTypeArray(arraySize);

		for(int i = 0; i < arraySize; ++i)
			values[i] = elementEditors[i].getValue();

		return values;
	}
	@Override
	public void     setValue(TYPE[] value) {
		if(value == null)
			return ;

		arraySize = value.length;

		if(elementEditors == null || elementEditors.length != arraySize)
			createEditors(value.length);

		for(int i = 0; i < value.length; i++)
			elementEditors[i].setValue(value[i]);
	}

	private void 	createEditors(int _size) {
		if(elementEditors == null || elementEditors.length != arraySize) {
			editor.getChildren().clear();

			elementEditors = newEditorArray(arraySize);
			for(int i = 0; i < _size; i++)
				elementEditors[i] = newEditor();

			Arrays.asList(elementEditors)
					.stream()
					.forEach((elt) -> editor.getChildren().add(elt.getEditor()));
		}
	}

}
