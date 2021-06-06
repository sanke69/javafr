package fr.javafx.beans.propertysheet.items;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import fr.java.beans.annotations.BeanPropertyTag;

import fr.javafx.beans.propertysheet.PropertySheet;
import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.PropertyEditor;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

public class ItemForAttribute implements PropertySheet.Item {

	private final Object								bean;
	private final PropertyDescriptor					beanAttributeDescriptor;
	private final Method								readMethod;
	private boolean										editable		= true;

	public ItemForAttribute(final Object _bean, final PropertyDescriptor _propertyDescriptor) {
		super();

		bean 					= _bean;
		beanAttributeDescriptor = _propertyDescriptor;
		readMethod 				= _propertyDescriptor.getReadMethod();
		if(beanAttributeDescriptor.getWriteMethod() == null)
			setEditable(false);
	}

	public void 										setEditable(final boolean _editable) {
		editable = _editable;
	}
	@Override
	public boolean 										isEditable() {
		return editable;
	}

	public PropertyDescriptor 							getPropertyDescriptor() {
		return beanAttributeDescriptor;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
		if((beanAttributeDescriptor.getPropertyEditorClass() != null) && PropertyEditor.class.isAssignableFrom(beanAttributeDescriptor.getPropertyEditorClass()))
			return Optional.of((Class<PropertyEditor<?>>) this.beanAttributeDescriptor.getPropertyEditorClass());

		return Item.super.getPropertyEditorClass();
	}

	@Override
	public String 										getName() {
		return beanAttributeDescriptor.getDisplayName();
	}
	@Override
	public String 										getCategory() {
		String category = (String) beanAttributeDescriptor.getValue(BeanPropertyTag.CATEGORY_PROPERTY);

		if(category == null)
			category = BeanPropertyTag.DEFAULT_CATEGORY;
		return category;
	}
	@Override
	public String 										getDescription() {
		return beanAttributeDescriptor.getShortDescription();
	}
	@Override
	public Class<?> 									getType() {
		return beanAttributeDescriptor.getPropertyType();
	}

	public Object 										getBean() {
		return bean;
	}

	@Override
	public void 										setValue(final Object _value) {
		final Method writeMethod = beanAttributeDescriptor.getWriteMethod();
		if(writeMethod != null) {
			try {
				writeMethod.invoke(bean, _value);
			} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			} catch(final Throwable e) {
				if(e instanceof PropertyVetoException) {
					final Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Alert");
					alert.setHeaderText("HeaderText");
					alert.setContentText(e.getLocalizedMessage());
					alert.showAndWait();
				} else {
					throw e;
				}
			}
		}
	}
	@Override
	public Object 										getValue() {
		try {
			return readMethod.invoke(bean);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<ObservableValue<? extends Object>> 	getObservableValue() {
		return null;
	}

}
