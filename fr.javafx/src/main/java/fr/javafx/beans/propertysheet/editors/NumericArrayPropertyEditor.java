package fr.javafx.beans.propertysheet.editors;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.PropertyEditor;
import fr.javafx.scene.control.editor.NumberEditor;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class NumericArrayPropertyEditor implements PropertyEditor<Number[]> {

	private final Item				property;
	private Class<? extends Number>	arrayType;
	private int						arraySize;

	private final HBox				editor;
	private NumberEditor[] 			elementEditors;

	public NumericArrayPropertyEditor(Item _property) {
		super();

		property = _property;
		editor   = new HBox();
	}

	@Override
	public Node     getEditor() {
		return editor;
	}

	@Override
	public Number[] getValue() {
		Number[] values = new Number[arraySize];

		for(int i = 0; i < arraySize; ++i)
			values[i] = elementEditors[i].valueProperty().getValue();

		return values;
	}
	@Override
	public void     setValue(Number[] value) {
		if(value == null)
			return ;

		arraySize = value.length;
		arrayType = (Class<? extends Number>) value[0].getClass();

		if(elementEditors == null || elementEditors.length != arraySize) {
			editor.getChildren().clear();

			elementEditors = new NumberEditor[arraySize];
			for(int i = 0; i < value.length; i++) {
				elementEditors[i] = new NumberEditor(arrayType);
				elementEditors[i].setValue(value[i]);
			}

			editor.getChildren().addAll(elementEditors);
		} else
			for(int i = 0; i < value.length; i++)
				elementEditors[i].setValue(value[i]);
	}

}
