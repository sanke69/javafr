package fr.javafx.scene.control.collection;

import java.util.Set;

import fr.java.lang.functionals.Constructor;
import fr.java.patterns.visitable.Treeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

// see. https://gist.github.com/james-d/3da84819033a03db1496
public abstract class FxTreeableStyle {

	public abstract Constructor.OneArg<TreeableItem, Treeable<?, ?>> 			itemGenerator();
	public abstract Constructor.OneArg<TreeableCell, TreeView<Treeable<?, ?>>> 	cellGenerator();
	public abstract Constructor.OneArg<ContextMenu, Treeable<?, ?>> 			menuGenerator();

	public final TreeableItem 													newTreeItem(Treeable<?, ?> _item) {
		if(itemGenerator() != null)
			return itemGenerator().newInstance(_item);
		return new TreeableItem(_item, new ImageView());
	}
	public final TreeableCell 													newCellItem(TreeView<Treeable<?, ?>> _view) {
		if(cellGenerator() != null)
			return cellGenerator().newInstance(_view);
		return new TreeableCell(null);
	}
	public final ContextMenu 													getContextMenu(Treeable<?, ?> _item) {
		if(menuGenerator() != null)
			return menuGenerator().newInstance(_item);
		return null;
	}

	public Callback<TreeView<Treeable<?, ?>>, TreeCell<Treeable<?, ?>>> 		cellFactory() {
		return (view) -> newCellItem(null);
	}

	public class TreeableItem extends TreeItem<Treeable<?, ?>> {
		private boolean childrenBuilt = false;

		public TreeableItem(Treeable<?, ?> _data) {
			super(_data);
		}
		public TreeableItem(Treeable<?, ?> _data, Node _node) {
			super(_data);
			setGraphic(_node);
		}

		@Override
		public boolean 										isLeaf() {
			return getValue().isLeaf();
		}

		@Override
		public ObservableList<TreeItem<Treeable<?, ?>>>  	getChildren() {
			if(!childrenBuilt)
	 			super.getChildren().setAll(buildChildren(this));
			return super.getChildren();
		}

		private ObservableList<TreeItem<Treeable<?, ?>>> 	buildChildren(TreeItem<Treeable<?, ?>> _this) {
			childrenBuilt = true;

			Set<? extends Treeable<?, ?>> children = _this.getValue().getChildren();
			if(!children.isEmpty()) {
				ObservableList<TreeItem<Treeable<?, ?>>> childrenViews = FXCollections.observableArrayList();

				for(Treeable<?, ?> child : children)
					childrenViews.add(newTreeItem(child));

				return childrenViews;
			}

			return FXCollections.emptyObservableList();
		}

	}

	public class TreeableCell extends TreeCell<Treeable<?, ?>> {
		private TextField textField;
		private String    oldValue;

		public TreeableCell(TreeView<Treeable<?, ?>> _view) {
			super();
		}

		@Override
		public void updateItem(Treeable<?, ?> item, boolean empty) {
			super.updateItem(item, empty);

			if(empty) {
				setText(null);
				setGraphic(null);
			} else {
				if(isEditing()) {
					if(textField != null)
						textField.setText(getString());

					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());

					ContextMenu menu = FxTreeableStyle.this.getContextMenu(getTreeItem().getValue());
					if(menu != null)
						setContextMenu(menu);
				}
			}
		}

		private String getString() {
			return getItem() == null ? "" : getItem().getName();
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
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if(t.getCode() == KeyCode.ENTER) {
						Treeable<?, ?> item = getItem();

						System.err.println("Must operate on item");

						commitEdit(item);
					} else if(t.getCode() == KeyCode.ESCAPE)
						cancelEdit();
				}
			});
		}

	}

}
