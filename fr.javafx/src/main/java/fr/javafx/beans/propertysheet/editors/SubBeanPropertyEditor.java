package fr.javafx.beans.propertysheet.editors;

import fr.java.beans.impl.AbstractBean;
import fr.javafx.beans.propertysheet.api.PropertyEditor;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class SubBeanPropertyEditor implements PropertyEditor<AbstractBean> {

	@Override
	public Node getEditor() {
		return new Button("SUB BEAN \\\\(^_^)");
	}

	@Override
	public AbstractBean getValue() {
		return null;
	}

	@Override
	public void setValue(AbstractBean value) {
		
	}

}