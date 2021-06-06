package fr.javafx.scene.control.selection;

import java.util.Collection;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.StringConverter;

public class ComboBoxCustom<T> extends Control {
	final protected ListProperty<T> 				listProperty;
	final protected ListProperty<T> 				listSelection;

	final protected boolean			 				multiEnabled;
	final private   StringConverter<T>				stringConverter;

	public ComboBoxCustom(boolean _enableMultiSelection) {
		this(_enableMultiSelection, null, null);
	}
	public ComboBoxCustom(boolean _enableMultiSelection, final StringConverter<T> _stringConverter) {
		this(_enableMultiSelection, _stringConverter, null);
	}
	public ComboBoxCustom(boolean _enableMultiSelection, final ObservableList<T> _values) {
		this(_enableMultiSelection, null, _values);
	}
	public ComboBoxCustom(boolean _enableMultiSelection, final StringConverter<T> _stringConverter, final ObservableList<T> _values) {
		super();
		listProperty    = new SimpleListProperty<T>(_values != null ? _values : FXCollections.observableArrayList());
		listSelection   = new SimpleListProperty<T>(FXCollections.observableArrayList());

		multiEnabled    = _enableMultiSelection;
		stringConverter = _stringConverter;
	}

	@Override
	protected Skin<?> 				createDefaultSkin() {
		return new ComboBoxCustomSkin<T>(this);
	}

	public ReadOnlyListProperty<T> 	listProperty() {
		return listProperty;
	}
	public void 					setList(List<T> _list) {
		listSelection.clear();
		listProperty.get().setAll(_list);
	}

	public ReadOnlyListProperty<T> 	listSelection() {
		return listSelection;
	}
	public void						setSelection(T _selection) {
		if( ! listProperty.contains(_selection) )
			throw new IllegalArgumentException();

		listSelection.clear();
		listSelection.get().add(_selection);
	}
	public void						setSelection(Collection<T> _selection) {
		for(T t : _selection)
			if( ! listProperty.contains(t) )
				throw new IllegalArgumentException();

		listSelection.get().setAll(_selection);
	}

	public boolean 					isMutiSelectionEnabled() {
		return multiEnabled;
	}

	public StringConverter<T> 		getStringConverter() {
		return stringConverter;
	}

}
