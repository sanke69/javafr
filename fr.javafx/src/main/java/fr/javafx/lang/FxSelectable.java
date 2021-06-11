package fr.javafx.lang;

import fr.java.patterns.capabilities.Selectable;
import javafx.beans.property.BooleanProperty;

public interface FxSelectable extends Selectable {


	public default boolean 	requestSelection(boolean _true) { setSelected(_true); return _true; }

	public default boolean 	isSelectable() { return true; }

	public BooleanProperty  selectedProperty();

	public void 			setSelected(boolean _true);
	public boolean 			isSelected();

}
