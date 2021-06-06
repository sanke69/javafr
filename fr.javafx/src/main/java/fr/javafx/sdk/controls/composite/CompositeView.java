package fr.javafx.sdk.controls.composite;

import fr.java.patterns.composite.Composite;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeView;

public class CompositeView extends TreeView<Composite> {
	ObjectProperty<Composite> 	rootModel = null;
	CompositeStyle  			style     = null;

	public CompositeView() {
		this(new DefaultCompositeStyle());
	}
	public CompositeView(CompositeStyle _style) {
		super();
		style     = _style;
		rootModel = new SimpleObjectProperty<Composite>();

//		setEditable(false);
//		setShowRoot(false);

		rootModelProperty().addListener((_obs, _old, _new) -> setRoot(_new != null ? getCompositeStyle().newTreeItem(_new) : null));
	}

	@Override
	public void 							refresh() {
		if(getRoot() != null)
			getRoot().getChildren();
		super.refresh();
	}

	public ObjectProperty<Composite> 		rootModelProperty() {
		return rootModel;
	}
	public void 							setRootModel(Composite _root) {
		rootModel.set(_root);
	}
	public Composite 						getRootModel() {
		return rootModel.get();
	}

	public void 							setCompositeStyle(CompositeStyle _style) {
		style = _style;
		setCellFactory(style.cellFactory());

		if(getRoot() != null)
			setRoot(style.newTreeItem(getRootModel()));
	}
	public CompositeStyle					getCompositeStyle() {
		return style;
	}

}
