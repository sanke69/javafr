package fr.javafx.scene.control.collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;

import fr.java.lang.functionals.Constructor.OneArg;
import fr.java.patterns.visitable.Treeable;

public class FxTreeable extends TreeView<Treeable<?, ?>> {
	private ObjectProperty<Treeable<?, ?>> rootItem, selectedItem;
	
	FxTreeableStyle style;

	public FxTreeable() {
		super();
		rootItem     = new SimpleObjectProperty<Treeable<?, ?>>();
		selectedItem = new SimpleObjectProperty<Treeable<?, ?>>();
		style        = new FxTreeableStyle() {
			@Override public OneArg<TreeableItem, Treeable<?, ?>> 			itemGenerator() { return null; }
			@Override public OneArg<TreeableCell, TreeView<Treeable<?, ?>>> cellGenerator() { return null; }
			@Override public OneArg<ContextMenu, Treeable<?, ?>> 			menuGenerator() { return null; }
		};

		setCellFactory(style.cellFactory());
		setEditable(false);
		setShowRoot(true);

		rootItemProperty().addListener((_obs, _old, _new) -> { 
			if(_new != null) 
				setRoot(style.newTreeItem(_new)); 
		});
		getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> selectedItem.set(_new.getValue()));
	}
	public FxTreeable(FxTreeableStyle _style) {
		this();
		setStyle(_style);
	}
	public FxTreeable(Treeable<?, ?> _root) {
		this();
		setRoot(style.newTreeItem(_root));
	}

	public void setStyle(FxTreeableStyle _style) {
		style = _style;

		if(getRoot() != null)
			setRoot(style.newTreeItem(getRoot().getValue()));
		setCellFactory(style.cellFactory());
	}

	public ObjectProperty<Treeable<?, ?>> 	rootItemProperty() {
		return rootItem;
	}
	public Treeable<?, ?>					getRootItem() {
		return rootItem.get();
	}
	public void 							setRootItem(Treeable<?, ?> _bean) {
		rootItem.set(_bean);
	}

	public ObjectProperty<Treeable<?, ?>> 	selectedItemProperty() {
		return selectedItem;
	}
	public Treeable<?, ?>					getSelectedItem() {
		return selectedItem.get();
	}
	public void 							setSelectedItem(Treeable<?, ?> _bean) {
		selectedItem.set(_bean);
	}

}
