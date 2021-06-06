package fr.javafx.beans.propertysheet.editors;

import fr.java.beans.reflect.utils.Primitives;
import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.PropertyEditor;
import fr.javafx.scene.control.editor.NumberEditor;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class PrimitiveArrayPropertyEditor<T> implements PropertyEditor<T> {

	private final Item		property;
	private Primitives		arrayType;
	private int  			arraySize;

	private final HBox		editor;
	private NumberEditor[] 	editorElements;

	public PrimitiveArrayPropertyEditor(Item _property) {
		super();

		property    = _property;
		editor      = new HBox();
	}

	@Override
	public Node getEditor() {
		return editor;
	}

	@Override
	public T    getValue() {
		T values = newPrimitiveArray(arrayType, arraySize);

		fromEditorToValue(values);

		return values;
	}
	@Override
	public void setValue(T values) {
		if(values == null)
			return ;

		arrayType = getPrimitive(values);
		arraySize = getPrimitiveArraySize(values);

		if(editorElements == null || editorElements.length != arraySize) {
			editor.getChildren().clear();

			editorElements = new NumberEditor[arraySize];
			for(int i = 0; i < arraySize; i++)
				editorElements[i] = new NumberEditor( getPrimitiveClass(arrayType) );

			editor.getChildren().addAll(editorElements);
		}

		fromValueToEditor(values);
	}


	private Primitives 					getPrimitive(T _array) {
		if(_array instanceof byte[])
			return Primitives.BYTE;
		else if(_array instanceof short[])
			return Primitives.SHORT;
		else if(_array instanceof int[])
			return Primitives.INTEGER;
		else if(_array instanceof long[])
			return Primitives.LONG;
		else if(_array instanceof float[])
			return Primitives.FLOAT;
		else if(_array instanceof double[])
			return Primitives.DOUBLE;
		else
			throw new IllegalAccessError("Generic must be a Number");
	}
	private int 						getPrimitiveArraySize(T array) {
		switch(getPrimitive(array)) {
		case BYTE:		return ((byte[])   array).length;
		case SHORT:		return ((short[])  array).length;
		case INTEGER:	return ((int[])    array).length;
		case LONG:		return ((long[])   array).length;
		case FLOAT:		return ((float[])  array).length;
		case DOUBLE:	return ((double[]) array).length;
		default:		throw new IllegalAccessError("Generic must be a Number");
		}
	};
	private Class<? extends Number> 	getPrimitiveClass(Primitives _primitive) {
		switch(arrayType) {
		case BYTE:		return byte.class;
		case SHORT:		return short.class;
		case INTEGER:	return int.class;
		case LONG:		return long.class;
		case FLOAT:		return float.class;
		case DOUBLE:	return double.class;
		default:		throw new IllegalAccessError("Generic must be a Number");
		}
	}
	@SuppressWarnings("unchecked")
	private T							newPrimitiveArray(Primitives _primitive, int _size) {
		switch(arrayType) {
		case BYTE:		return (T) new byte[_size];
		case SHORT:		return (T) new short[_size];
		case INTEGER:	return (T) new int[_size];
		case LONG:		return (T) new long[_size];
		case FLOAT:		return (T) new float[_size];
		case DOUBLE:	return (T) new double[_size];
		default:		throw new IllegalAccessError("Generic must be a Number");
		}
	}

	private void fromEditorToValue(final T _values) {
		switch(arrayType) {
		case BYTE:		{
							byte[] values = (byte[]) _values;
							for(int i = 0; i < arraySize; ++i)
								values[i] = editorElements[i].valueProperty().getValue().byteValue();
						}
						break;
		case SHORT:		{
							short[] values = (short[]) _values;
							for(int i = 0; i < arraySize; ++i)
								values[i] = editorElements[i].valueProperty().getValue().shortValue();
						}
						break;
		case INTEGER:	{
							int[] values = (int[]) _values;
							for(int i = 0; i < arraySize; ++i)
								values[i] = editorElements[i].valueProperty().getValue().intValue();
						}
						break;
		case LONG:		{
							long[] values = (long[]) _values;
							for(int i = 0; i < arraySize; ++i)
								values[i] = editorElements[i].valueProperty().getValue().longValue();
						}
						break;
		case FLOAT:		{
							float[] values = (float[]) _values;
							for(int i = 0; i < arraySize; ++i) {
								values[i] = editorElements[i].valueProperty().getValue().floatValue();
							}
						}
						break;
		case DOUBLE:	{
							double[] values = (double[]) _values;
							for(int i = 0; i < arraySize; ++i) {
								values[i] = editorElements[i].valueProperty().getValue().doubleValue();
							}
						}
						break;
		default:		throw new IllegalAccessError("Generic must be a Number");
		}
	}
	private void fromValueToEditor(final T _values) {
		switch(arrayType) {
		case BYTE:		{
							byte[] values = (byte[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Byte.valueOf(values[i]));
						}
						break;
		case SHORT:		{
							short[] values = (short[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Short.valueOf(values[i]));
						}
						break;
		case INTEGER:	{
							int[] values = (int[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Integer.valueOf(values[i]));
						}
						break;
		case LONG:		{
							long[] values = (long[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Long.valueOf(values[i]));
						}
						break;
		case FLOAT:		{
							float[] values = (float[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Float.valueOf(values[i]));
						}
						break;
		case DOUBLE:	{
							double[] values = (double[]) _values;
							for(int i = 0; i < arraySize; ++i)
								editorElements[i].setValue(Double.valueOf(values[i]));
						}
						break;
		default:		throw new IllegalAccessError("Generic must be a Number");
		}
	}

}
