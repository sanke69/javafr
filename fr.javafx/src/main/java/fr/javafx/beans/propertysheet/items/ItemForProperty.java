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

public class ItemForProperty implements PropertySheet.Item {

	private final Object								bean;
	private final PropertyDescriptor					beanPropertyDescriptor;
	private final Method								readMethod;
	private boolean										editable		= true;
	private Optional<ObservableValue<? extends Object>>	observableValue	= Optional.empty();

	public ItemForProperty(final Object _bean, final PropertyDescriptor _propertyDescriptor) {
		super();
		
		bean 					= _bean;
		beanPropertyDescriptor 	= _propertyDescriptor;
		readMethod 				= _propertyDescriptor.getReadMethod();
		if(beanPropertyDescriptor.getWriteMethod() == null)
			setEditable(false);

		findObservableValue();
	}

	public void 										setEditable(final boolean _editable) {
		editable = _editable;
	}
	@Override
	public boolean 										isEditable() {
		return this.editable;
	}

	public PropertyDescriptor 							getPropertyDescriptor() {
		return beanPropertyDescriptor;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
		if((beanPropertyDescriptor.getPropertyEditorClass() != null) &&
			PropertyEditor.class.isAssignableFrom(beanPropertyDescriptor.getPropertyEditorClass()))
			return Optional.of((Class<PropertyEditor<?>>) beanPropertyDescriptor.getPropertyEditorClass());

		return Item.super.getPropertyEditorClass();
	}

	@Override
	public String 										getName() {
		return beanPropertyDescriptor.getDisplayName();
	}
	@Override
	public String 										getCategory() {
		String category = (String) beanPropertyDescriptor.getValue(BeanPropertyTag.CATEGORY_PROPERTY);

		if(category == null)
			category = BeanPropertyTag.DEFAULT_CATEGORY;
		return category;
	}
	@Override
	public String 										getDescription() {
		return beanPropertyDescriptor.getShortDescription();
	}
	@Override
	public Class<?> 									getType() {
		return beanPropertyDescriptor.getPropertyType();
	}

	public Object 										getBean() {
		return bean;
	}

	@Override
	public void 										setValue(final Object _value) {
		final Method writeMethod = beanPropertyDescriptor.getWriteMethod();
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
		return observableValue;
	}

	private void findObservableValue() {
		try {
			final String propName = beanPropertyDescriptor.getName() + "Property";
			final Method m        = getBean().getClass().getMethod(propName);
			final Object val      = m.invoke(getBean());
			if((val != null) && (val instanceof ObservableValue)) {
				observableValue = Optional.of((ObservableValue<?>) val);
			}
		} catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

		}
	}

}
