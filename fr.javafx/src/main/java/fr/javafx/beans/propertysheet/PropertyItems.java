package fr.javafx.beans.propertysheet;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.function.Predicate;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.items.ItemForProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PropertyItems extends fr.java.beans.utils.Beans {

	public static ObservableList<Item> getProperties(final Object _bean) {
		return getProperties(_bean, p -> !p.isHidden());
	}
	public static ObservableList<Item> getProperties(final Object _bean, Predicate<PropertyDescriptor> _test) {
		ObservableList<Item> list = FXCollections.observableArrayList();

		PropertyDescriptor[] map = getPropertyDescriptors(_bean.getClass());
		if(map != null && map.length > 0) {
			for(PropertyDescriptor propDesc : map) {
				if(_test.test(propDesc))
					list.add(new ItemForProperty(_bean, propDesc));
			}
		} else {
			try {
				for(PropertyDescriptor propDesc : Introspector.getBeanInfo(_bean.getClass()).getPropertyDescriptors())
					if(_test.test(propDesc))
						list.add(new ItemForProperty(_bean, propDesc));
			} catch(IntrospectionException e) { e.printStackTrace(); }
		}

		return list;
	}

}
