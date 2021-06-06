package fr.javafx.sdk.controls.composite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.java.beans.proxies.Proxies;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

public class DefaultCompositeStyle implements CompositeStyle {
	private final Map<Class<?>, ObjectStyle> styleMap = new HashMap<Class<?>, ObjectStyle>();

	public DefaultCompositeStyle() {
		super();
	}

	public final void 												register(Class<?> _class, ObjectStyle _style) {
		styleMap.put(_class,  _style);
	}
	public final ObjectStyle 										unregister(Class<?> _class) {
		return styleMap.remove(_class);
	}

	public final TreeItem<Composite> 								newTreeItem(Object _o) {
		ObjectStyle style = styleMap.get(_o.getClass());

		if(style != null)
			return style.itemGenerator().newInstance(_o);

		return null; //_c.hasChildren() ? new CompositeItem(_c, new ImageView()) : new ComponentItem(_c, new ImageView());
	}
	public final TreeItem<Composite> 								newTreeItem(Composite _c) {
		ObjectStyle style = styleMap.get(_c.getClass());

		if(style != null)
			return style.itemGenerator().newInstance(_c);

		return new CompositeItem(_c, new ImageView());
	}
	public final TreeItem<Composite> 								newTreeItem(Component _c) {
		ObjectStyle style = styleMap.get(_c.getClass());

		if(style != null)
			return style.itemGenerator().newInstance(_c);

		return  new ComponentItem(_c, new ImageView());
	}
	
	public final TreeCell<Composite> 								newCellItem(TreeView<Composite> _view) {
//		ObjectStyle style = styleMap.get(_view.getUserData().getClass());
		
//		if(style != null)
//			return style.cellGenerator().newInstance(_view);

		return new DefaultCellItem(_view);
	}
	
	public final ContextMenu 										newContextMenu(Object _item) {
		ObjectStyle style = styleMap.get(_item.getClass());
		
		if(style != null)
			return style.menuGenerator().newInstance(_item);
		return null;
	}

	public final Callback<TreeView<Composite>, TreeCell<Composite>> cellFactory() {
		return (view) -> newCellItem(null);
	}

	public class ComponentItem   extends TreeItem<Composite> {
		final Component proxy;

		public ComponentItem(Composite _data) {
			super(_data);
			proxy = null;
		}
		public ComponentItem(Composite _data, Node _node) {
			this(_data);
			setGraphic(_node);
		}
		public ComponentItem(Component _data) {
			super(Proxies.newComposite(_data));
			proxy = getValue();
		}
		public ComponentItem(Component _data, Node _node) {
			this(_data);
			setGraphic(_node);
		}

		@Override
		public boolean isLeaf() { return true; }

	}
	public class CompositeItem   extends TreeItem<Composite> {
		final Composite proxy;
		private boolean childrenBuilt = false;

		public CompositeItem(Composite _data) {
			super(_data);
			proxy = null;
		}
		public CompositeItem(Composite _data, Node _node) {
			this(_data);
			setGraphic(_node);
		}
		public CompositeItem(Component _data) {
			super(Proxies.newComposite(_data));
			proxy = null;
		}
		public CompositeItem(Component _data, Node _node) {
			this(_data);
			setGraphic(_node);
		}
	
		@Override
		public boolean isLeaf() {
			if(proxy != null)
				return !proxy.hasChildren();
			
			Composite c = getValue();
			
			return !c.hasChildren();
		}

		@Override
		public ObservableList<TreeItem<Composite>>  	getChildren() {
			if(!childrenBuilt)
	 			buildChildren();

			Composite model = getValue();
			if(model.getChildren().size() != super.getChildren().size())
				updateChildren();
				
			
			return super.getChildren();
		}

//		private ObservableList<TreeItem<Composite>> 	buildChildren() {
		private void 									buildChildren() {
			childrenBuilt = true;

			Set<? extends Component> childrenModels = getValue().getChildren();
			if(!childrenModels.isEmpty()) {
				ObservableList<TreeItem<Composite>> childrenViews = FXCollections.observableArrayList();

				for(Component child : childrenModels)
					childrenViews.add(newTreeItem(child));

				getChildren().setAll(childrenViews);
//				return childrenViews;
			}

//			return FXCollections.emptyObservableList();
		}
//		private ObservableList<TreeItem<Composite>> 	updateChildren() {
		private void 	updateChildren() {
			Set<? extends Component>            childrenModels = getValue().getChildren();
			ObservableList<TreeItem<Composite>> childrenViews  = FXCollections.observableArrayList();
			
			super.getChildren().clear();
			
			if(!childrenModels.isEmpty()) {
				childrenViews = FXCollections.observableArrayList();

				for(Component child : childrenModels)
					childrenViews.add(newTreeItem(child));

				super.getChildren().setAll(childrenViews);
//				return childrenViews;
			}

//			return FXCollections.emptyObservableList();
		}

	}
	public class DefaultCellItem extends TreeCell<Composite> {
		private TextField textField;
		private String    oldValue;

		public DefaultCellItem(TreeView<Composite> _view) {
			super();
		}

		@Override
		public void updateItem(Composite item, boolean empty) {
			super.updateItem(item, empty);

			if(empty) {
				setText(null);
				setGraphic(null);
			} else {
				Component value = getTreeItem().getValue();

				if(isEditing()) {
					if(textField != null)
						textField.setText(getString());

					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());

					ContextMenu menu = DefaultCompositeStyle.this.newContextMenu(value);
					if(menu != null)
						setContextMenu(menu);
				}
			}
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}

		@Override
		public void startEdit() {
			oldValue = getText();

			super.startEdit();

			if(textField == null)
				createTextField();

			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(oldValue);
			setGraphic(getTreeItem().getGraphic());
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased((t) -> {
				if(t.getCode() == KeyCode.ENTER) {
					Composite item = getItem();

					System.err.println("Must operate on item");

					commitEdit(item);
				} else if(t.getCode() == KeyCode.ESCAPE)
					cancelEdit();
			});
		}

	}

}
