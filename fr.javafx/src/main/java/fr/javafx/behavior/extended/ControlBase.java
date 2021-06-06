package fr.javafx.behavior.extended;

import javafx.scene.control.Control;

import fr.javafx.beans.propertysheet.PropertySheet;

public abstract class ControlBase extends Control {
	private String stylesheet;

	protected final String getUserAgentStylesheet(Class<?> _class, String _filename) {
		if(stylesheet == null)
			stylesheet = _class.getResource(_filename).toExternalForm();

		return stylesheet;
	}

}
