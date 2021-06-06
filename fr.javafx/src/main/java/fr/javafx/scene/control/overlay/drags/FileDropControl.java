package fr.javafx.scene.control.overlay.drags;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fr.filedropper.FileDropper;
import fr.filedropper.FileDropper.Action;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.Visual;
import fr.javafx.xtra.filedropper.ImageFileAction;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Callback;

public class FileDropControl extends Control implements FileDropper {

	final ObservableList<Path>	droppedItems;
	final Collection<Action>	actions;

	final BooleanProperty		multiFile;
	final Node					overlayed;

	public FileDropControl() {
		this(null);
	}
	public FileDropControl(Node _content) {
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
		
		overlayed = _content;
	}

	@Override
	protected Skin<? extends FileDropControl> createDefaultSkin() {
		if(overlayed == null)
			return new AdvancedSkin<FileDropControl> (this,
														FileDropControlVisualDefault::new, 
														FileDropControlBehavior::new);
		
		return new AdvancedSkin<FileDropControl> (this,
													new Visual<FileDropControl>() {
														@Override public List<CssMetaData<? extends Styleable, ?>> 	getCssMetaData() 	{ return null; }
														@Override public FileDropControl 							getSkinnable() 		{ return FileDropControl.this; }
														@Override public Node 										getNode() 			{ return overlayed; }
														@Override public void 										dispose() 			{ }
													},
													FileDropControlBehavior::new);
	}

	@Override
	public Node 						getNode() {
		return this;
	}

	public Collection<Action> 			getActions() {
		return actions;
	}
	public void 						addAction(Action _action) {
		actions.add(_action);
	}
	public void 						addAction(Predicate<Path> _predicate, Consumer<Path> _consumer) {
		actions.add(new Action() {
			@Override public boolean test(Path _p)   { return _predicate.test(_p); }
			@Override public void    accept(Path _p) { _consumer.accept(_p); }
		});
	}

	public Collection<Action> 			getRegisteredActions() {
		return actions;
	}

	public ReadOnlyBooleanProperty 		multiFileProperty() {
		return multiFile;
	}
	public void 						setMultiFileAllowed(boolean _enable) {
		multiFile.set(_enable);
	}
	public boolean 						isMultiFileAllowed() {
		return multiFile.get();
	}

	public ObservableList<Path> 		droppedItemProperty() {
		return droppedItems;
	}
	public void 						addDroppedItems(Path _path) {
		droppedItems.add(_path);
	}
	public void 						clearDroppedItems() {
		droppedItems.clear();
	}
	public Collection<Path> 			getDroppedItems() {
		return droppedItems.sorted();
	}

	private static final Callback<Path, Observable[]> extractor() {
		return (Path p) -> new Observable[] {
				new SimpleStringProperty(p.toString())
		};
	}

}
