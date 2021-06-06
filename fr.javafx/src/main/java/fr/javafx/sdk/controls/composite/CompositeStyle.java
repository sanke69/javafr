package fr.javafx.sdk.controls.composite;

import fr.java.lang.functionals.Constructor;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * see {@link fr.java.proxies.CompositeProxy} for adaptation to any kind of object
 * @author sanke
 *
 */
public interface CompositeStyle {
	interface ObjectStyle {
		public Constructor.OneArg<TreeItem<Composite>,  Object> 			 itemGenerator();
		public Constructor.OneArg<TreeCell<Composite>,  TreeView<Composite>> cellGenerator();
		public Constructor.OneArg<ContextMenu,          Object> 			 menuGenerator();
	}

	public void 				register(Class<?> _class, ObjectStyle _style);
	public ObjectStyle 			unregister(Class<?> _class);

	public TreeItem<Composite> 	newTreeItem(Component _o);
	public TreeItem<Composite> 	newTreeItem(Composite _o);

	public TreeCell<Composite> 	newCellItem(TreeView<Composite> _view);
	
	public ContextMenu 			newContextMenu(Object _item);

	public default Callback<TreeView<Composite>, TreeCell<Composite>> 	cellFactory() {
		return (view) -> newCellItem(null);
	}

}
