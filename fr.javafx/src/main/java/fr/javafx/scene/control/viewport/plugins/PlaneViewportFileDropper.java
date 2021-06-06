package fr.javafx.scene.control.viewport.plugins;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fr.filedropper.FileDropper;
import fr.java.math.topology.Coordinate;
import fr.javafx.scene.control.viewport.planar.PlaneViewportControl;
import fr.javafx.xtra.filedropper.ImageFileAction;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class PlaneViewportFileDropper<MODEL, COORD extends Coordinate.TwoDims> implements PlaneViewportControl.Plugin<MODEL, COORD>, FileDropper {
	private PlaneViewportControl<MODEL, COORD>	control;

	final ObservableList<Path>	droppedItems;
	final Collection<Action>	actions;

	final BooleanProperty		multiFile;

	public PlaneViewportFileDropper() {
		super();
		actions   = new ArrayList<Action>();
		multiFile = new SimpleBooleanProperty(true);

		droppedItems = FXCollections.observableArrayList(extractor());
		droppedItems.addListener(new ListChangeListener<Path>() {
			@Override
			public void onChanged(Change<? extends Path> c) {
				System.out.println("new event");
				while(c.next()) {
					if(c.wasPermutated()) {
						for(int i = c.getFrom(); i < c.getTo(); ++i) {
							System.out.println("Permuted: " + i + " " + droppedItems.get(i));
						}
					} else if(c.wasUpdated()) {
						for(int i = c.getFrom(); i < c.getTo(); ++i) {
							System.out.println("Updated: " + i + " " + droppedItems.get(i));
						}
					} else {
						for(Path removedItem : c.getRemoved()) {
							System.out.println("Removed: " + removedItem);
						}
						for(Path addedItem : c.getAddedSubList()) {
							System.out.println("Added: " + addedItem);
						}
					}
				}
			}
		});

		actions.add(new ImageFileAction());
	}
	public PlaneViewportFileDropper(FileDropper.Action _action) {
		this();
		addAction(_action);
	}

	@Override
	public void 							setViewportControl(PlaneViewportControl<MODEL, COORD> _pvpControl) {
		control = _pvpControl;

		control.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();

			if(db.hasFiles()) {
				List<File> candidates = new ArrayList<File>();

				if(isMultiFileAllowed())
					candidates.addAll(db.getFiles());
				else
					candidates.add(db.getFiles().get(0));
				
				boolean allIsOK = true, currentIsOK = false;
				Collection<FileDropper.Action> actions = getRegisteredActions();
				for(File f : candidates) {
					currentIsOK = false;
					for(FileDropper.Action a : actions)
						if(a.test(f.toPath()))
							currentIsOK = true;
					
					if(!currentIsOK) {
						allIsOK = false;
						break;
					}
				}

				e.acceptTransferModes(allIsOK ? TransferMode.COPY_OR_MOVE : TransferMode.NONE);
			} else
				e.consume();
		});
		control.setOnDragDropped(e -> {
			Collection<FileDropper.Action> actions = getRegisteredActions();

			boolean success = true;

			Dragboard db = e.getDragboard();
			
			if(e.getTransferMode() == TransferMode.MOVE || e.getTransferMode() == TransferMode.COPY)
				for(File f : db.getFiles())
					for(FileDropper.Action a : actions)
						if(a.test(f.toPath()))
							a.accept(f.toPath());

			e.setDropCompleted(success);
			e.consume();
		});
	}
	public void 							unsetViewportControl() {
		;
	}

	@Override
	public Node 							getNode() {
		return control;
	}
	@Override
	public ObservableList<Node> 			getChildren() {
		return FXCollections.emptyObservableList();
	}

	public Collection<FileDropper.Action> 	getActions() {
		return actions;
	}
	public void 							addAction(FileDropper.Action _action) {
		actions.add(_action);
	}
	public void 							addAction(Predicate<Path> _predicate, Consumer<Path> _consumer) {
		actions.add(new Action() {
			@Override public boolean test(Path _p)   { return _predicate.test(_p); }
			@Override public void    accept(Path _p) { _consumer.accept(_p); }
		});
	}

	public Collection<FileDropper.Action> 	getRegisteredActions() {
		return actions;
	}

	public ReadOnlyBooleanProperty 			multiFileProperty() {
		return multiFile;
	}
	public void 							setMultiFileAllowed(boolean _enable) {
		multiFile.set(_enable);
	}
	public boolean 							isMultiFileAllowed() {
		return multiFile.get();
	}

	public ObservableList<Path> 			droppedItemProperty() {
		return droppedItems;
	}
	public void 							addDroppedItems(Path _path) {
		droppedItems.add(_path);
	}
	public void 							clearDroppedItems() {
		droppedItems.clear();
	}
	public Collection<Path> 				getDroppedItems() {
		return droppedItems.sorted();
	}

	private static final Callback<Path, Observable[]> extractor() {
		return (Path p) -> new Observable[] {
				new SimpleStringProperty(p.toString())
		};
	}

}
