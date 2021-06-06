package fr.javafx.beans.propertysheet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.java.beans.impl.AbstractBean;
import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.PropertyEditor;
import fr.javafx.beans.propertysheet.api.PropertyEditorFactory;
import fr.javafx.beans.propertysheet.editors.BooleanPropertyEditor;
import fr.javafx.beans.propertysheet.editors.ColorPropertyEditor;
import fr.javafx.beans.propertysheet.editors.EnumPropertyEditor;
import fr.javafx.beans.propertysheet.editors.FontPropertyEditor;
import fr.javafx.beans.propertysheet.editors.LocalDatePropertyEditor;
import fr.javafx.beans.propertysheet.editors.NumericArrayPropertyEditor;
import fr.javafx.beans.propertysheet.editors.NumericPropertyEditor;
import fr.javafx.beans.propertysheet.editors.PrimitiveArrayPropertyEditor;
import fr.javafx.beans.propertysheet.editors.StringPropertyEditor;
import fr.javafx.beans.propertysheet.editors.SubBeanPropertyEditor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DefaultPropertyEditorFactory implements PropertyEditorFactory {

	Map<Class<?>, List<Class<?>>> registeredEditorClasses;
	Map<Class<?>, Class<?>>       editorClasses;
	
	private DefaultPropertyEditorFactory() {
		registeredEditorClasses = new HashMap<Class<?>, List<Class<?>>>();
		editorClasses           = new HashMap<Class<?>, Class<?>>();
		
		// Primitives
		registerEditor(BooleanPropertyEditor.class, 		boolean.class, Boolean.class);
		registerEditor(NumericPropertyEditor.class, 		byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class, Long.class, float.class, Float.class, double.class, Double.class, BigInteger.class, BigDecimal.class);
		registerEditor(NumericArrayPropertyEditor.class, 	Byte[].class, Short[].class, Integer[].class, Long[].class, Float[].class, Double[].class, BigInteger[].class, BigDecimal[].class);
		registerEditor(PrimitiveArrayPropertyEditor.class, 	byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class);
		registerEditor(StringPropertyEditor.class, 			String.class);

		// Generic for Enum
		registerEditor(EnumPropertyEditor.class, 			Enum.class);

		// Date & Time
		registerEditor(LocalDatePropertyEditor.class, 		LocalDate.class);

		// Graphics
		registerEditor(ColorPropertyEditor.class, 			Color.class);
		registerEditor(FontPropertyEditor.class, 			Font.class);
		
		// Bean Object
		registerEditor(SubBeanPropertyEditor.class, 		AbstractBean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> PropertyEditor<T> getEditor(Item _item) {
		try {
			Class<?> editorClass = editorClasses.get(_item.getType());

			if(editorClass != null)
				return (PropertyEditor<T>) editorClass.getConstructor(Item.class).newInstance(_item);

			// Special case for Enum
			if(_item.getType() != null && _item.getType().isEnum())
				return (PropertyEditor<T>) editorClasses.get(Enum.class).getConstructor(Item.class).newInstance(_item);

			return null;
		} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void registerEditor(Class<?> _editorClass, Class<?>... _propertyClasses) {
		registeredEditorClasses.put(_editorClass, Arrays.asList(_propertyClasses));

		for(Class<?> cls : _propertyClasses)
			editorClasses.put(cls, _editorClass);
	}

	@Override
	public void unregisterEditor(Class<?> _editorClass) {
		List<Class<?>> classes = registeredEditorClasses.get(_editorClass);

		for(Class<?> cls : classes)
			editorClasses.remove(cls);
		
		registeredEditorClasses.remove(_editorClass);
	}

	@Override
	public PropertyEditor<?> call(Item item) {
		Class<?> type = item.getType();
		
		if(item.getPropertyEditorClass().isPresent()) {
			Optional<PropertyEditor<?>> ed = createCustomEditor(item);
			if(ed.isPresent())
				return ed.get();
		}
		
		PropertyEditor<?> edit;
		if(Font.class.isAssignableFrom(type)) {
			edit = getEditor(item);
			Node n = edit.getEditor();
		}
			

		PropertyEditor<?> editor;
		if((editor = getEditor(item)) != null)
			return editor;

		return null;
	}
	
	public final Optional<PropertyEditor<?>> createCustomEditor(final Item property) {
		return property.getPropertyEditorClass().map(cls -> {
			try {
				Constructor<?> cn = cls.getConstructor(PropertySheet.Item.class);
				if(cn != null) {
					return (PropertyEditor<?>) cn.newInstance(property);
				}
			} catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				ex.printStackTrace();
			}
			return null;
		});
	}

	/** ******************** **\
	 *  SINGLETON DEFINITION  *
	\** ******************** **/
	private static DefaultPropertyEditorFactory instance;
	
	public static DefaultPropertyEditorFactory instance() {
		if(instance == null)
			instance = new DefaultPropertyEditorFactory();
		return instance;
	}

}
