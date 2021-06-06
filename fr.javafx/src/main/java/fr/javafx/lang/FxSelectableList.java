package fr.javafx.lang;

import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;

public interface FxSelectableList<T> {

	public interface Item<T> {
		public static final PseudoClass CURRENT_PSEUDO_CLASS  = PseudoClass.getPseudoClass("current");
        public static final PseudoClass FOCUSED_PSEUDO_CLASS  = PseudoClass.getPseudoClass("focused");
        public static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

		public ReadOnlyObjectProperty<T> valueProperty();
		public T  						 getValue();

		public ReadOnlyBooleanProperty   currentProperty();
		public void 					 setCurrent(boolean _true);
		public boolean 					 isCurrent();

		public ReadOnlyBooleanProperty   selectedProperty();
		public void 					 setSelected(boolean _true);
		public boolean 					 isSelected();

	}

	public void							enableMultiSelection(boolean _true);

	public List<T>						getValues();

	public T							getCurrent();
	public T							getSelected();

	public List<T>						getSelection();

	public void							setCurrent(T _value);

	public void 						select(Integer... _indexes);
	public void 						deselect(Integer... _indexes);

	public void 						select(T... _values);
	public void 						deselect(T... _values);

	public void 						selectAll();
	public void 						deselectAll();

	public void 						clearSelection();
	public void 						inverseSelection();

	public ObservableObjectValue<T> 	currentProperty();
	public ObservableObjectValue<T> 	selectedProperty();
	public ObservableList<T> 			selectionProperty();

}
