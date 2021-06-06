package fr.filedropper;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface FileDropper {

	public interface Action extends Predicate<Path>, Consumer<Path> { }

	public Node				 			getNode();

	public Collection<Action> 			getActions();
	public void 						addAction(Action _action);
	public void 						addAction(Predicate<Path> _predicate, Consumer<Path> _consumer);

	public Collection<Action> 			getRegisteredActions();

	public ReadOnlyBooleanProperty 		multiFileProperty();
	public void 						setMultiFileAllowed(boolean _enable);
	public boolean 						isMultiFileAllowed();

	public ObservableList<Path> 		droppedItemProperty();
	public void 						addDroppedItems(Path _path);
	public void 						clearDroppedItems();
	public Collection<Path> 			getDroppedItems();

}
