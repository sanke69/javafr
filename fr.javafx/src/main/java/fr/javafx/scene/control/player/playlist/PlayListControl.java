package fr.javafx.scene.control.player.playlist;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import fr.java.patterns.identifiable.Nameable;
import fr.java.player.PlayList;
import fr.javafx.beans.observable.MyFXCollections;
import fr.javafx.beans.observable.ObservablePlayList;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.lang.FxSelectableList;
import fr.javafx.scene.control.collection.FxGallery;
import fr.javafx.scene.control.player.playlist.utils.PlayListTables;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public abstract class PlayListControl<P> extends Control {

	public enum VisualMode {
		TABLE, GALLERY;
	}

	public interface Visual<P> extends fr.javafx.behavior.Visual<PlayListControl<P>>, FxSelectableList<P> {

	}
	public interface VisualTable<P> extends Visual<P> {

		public TableView<P> getTable();

	}
	public interface VisualGallery<P> extends Visual<P> {

		public FxGallery<P, ? extends Node> getGallery();

	}

	private final ObservablePlayList<P>		observablePlaylist;

	private P 								current;
	private final JavaBeanObjectProperty<P> currentProperty;

	private P 								focusedItem;
	private final JavaBeanObjectProperty<P> focusedItemProperty;

	private final ListProperty<P>			selectionProperty;

	private final VisualMode 				visualMode;

	public PlayListControl() {
		super();
		observablePlaylist 	= createObservablePlaylist();
		currentProperty    	= createCurrentProperty();
		focusedItemProperty = createFocusedProperty();
		selectionProperty  	= createSelectionProperty();
		
		visualMode         	= VisualMode.TABLE;
	}
	public PlayListControl(FxGallery.ItemGenerator<P, ?> _generator) {
		super();
		observablePlaylist 	= createObservablePlaylist();
		currentProperty    	= createCurrentProperty();
		focusedItemProperty = createFocusedProperty();
		selectionProperty  	= createSelectionProperty();

		visualMode         	= VisualMode.GALLERY;
	}

	@Override
	protected Skin<PlayListControl<P>> 								createDefaultSkin() {
		switch(visualMode) {
		case GALLERY : 	return new AdvancedSkin<PlayListControl<P>>(this, PlayListControlVisualGallery::new);
		default      :
		case TABLE   : 	if(getDropPredicate() != null && getDropBuilder() != null)
							return new AdvancedSkin<PlayListControl<P>>(this, PlayListControlVisualTable::new, PlayListControlBehaviorTableDragAndDrop::new);
						return new AdvancedSkin<PlayListControl<P>>(this, PlayListControlVisualTable::new);
		}

	}

	@SuppressWarnings("unchecked")
	public AdvancedSkin<PlayListControl<P>>							getAdvancedSkin() {
		if(getSkin() instanceof AdvancedSkin)
			return (AdvancedSkin<PlayListControl<P>>) getSkin();
		return null;
	}
	public Visual<P>												getVisual() {
		return (Visual<P>) getAdvancedSkin().getVisual();
	}
	public Optional<VisualTable<P>>									getVisualTable() {
		return Optional.of( (VisualTable<P>) getAdvancedSkin().getVisual() );
	}
	public Optional<VisualGallery<P>>								getVisualGallery() {
		return Optional.of( (VisualGallery<P>) getAdvancedSkin().getVisual() );
	}

	public ObservablePlayList<P>									playlistProperty() {
		return observablePlaylist;
	}

	public void			 											setPlaylist(PlayList<P> _playlist) {
		observablePlaylist.setAll(_playlist);

		setCurrent(_playlist.getCurrent());
	}
	public PlayList<P>												getPlaylist() {
		return observablePlaylist;
	}

	public ReadOnlyObjectProperty<P>								currentProperty() {
		return currentProperty;
	}

	public void			 											setCurrent(P _current) {
		current = _current;
		Platform.runLater(() -> currentProperty.fireValueChangedEvent());
	}
	public P														getCurrent() {
		return current;
	}

	public ReadOnlyObjectProperty<P>								focusedItemProperty() {
		return focusedItemProperty;
	}

	public void			 											setFocusedItem(P _focusedItem) {
		focusedItem = _focusedItem;
		Platform.runLater(() -> focusedItemProperty.fireValueChangedEvent());
	}
	public P														getFocusedItem() {
		return focusedItem;
	}

	public ReadOnlyListProperty<P>									selectionProperty() {
		return selectionProperty;
	}

	public void			 											setSelection(ObservableList<P> _selection) {
		selectionProperty.set(_selection);
	}
	public List<P>													getSelection() {
		return selectionProperty.get();
	}

	public VisualMode												getVisualMode() {
		return visualMode;
	}

	public abstract boolean											isEditable();

	public abstract Optional<Supplier<Set<TableColumn<P, ?>>>>		getTableColumnSet();
	public abstract Optional<Map<String, Consumer<P>>> 				getContextMenuEntries();

	public abstract Optional<Predicate<Path>>						getDropPredicate();
	public abstract Optional<Function<Path, P>>						getDropBuilder();

    @SuppressWarnings({ "unchecked" })
	private final ObservablePlayList<P> 							createObservablePlaylist() {
    	ObservablePlayList<P> playlistProperty = null;

       	playlistProperty = (ObservablePlayList<P>) MyFXCollections.observablePlaylist();

        return playlistProperty;
    }
    @SuppressWarnings({ "unchecked" })
	private final JavaBeanObjectProperty<P> 						createCurrentProperty() {
    	JavaBeanObjectProperty<P> currentProperty = null;

        try {
        	currentProperty = JavaBeanObjectPropertyBuilder.create().bean(this).name("current").build();
		} catch(NoSuchMethodException e) { }

        return currentProperty;
    }
    @SuppressWarnings({ "unchecked" })
	private final JavaBeanObjectProperty<P> 						createFocusedProperty() {
    	JavaBeanObjectProperty<P> focusedProperty = null;

        try {
        	focusedProperty = JavaBeanObjectPropertyBuilder.create().bean(this).name("focused").build();
		} catch(NoSuchMethodException e) { }

        return focusedProperty;
    }
	private final ListProperty<P> 									createSelectionProperty() {
    	ListProperty<P> selectionProperty = null;

        selectionProperty = new SimpleListProperty<P>();

        return selectionProperty;
    }

	public static class Builder<P> {
		// Model
		private PlayList<? extends P> 								playlist;
		
		//Context Menu
		private Map<String, Consumer<P>>							contextMenuEntries;
		
		// Drag & Drop
		private Predicate<Path> 									droppedFileTest;
		private Function<Path, P>									droppedFileBuilder;

		// Table Definition
		private Supplier<Set<TableColumn<P, ?>>>  					tableColumnSet;

		// Gallery Definition
		private FxGallery.ItemGenerator<P, ?> 						itemGenerator;

		public Builder() {
			super();
			contextMenuEntries = new HashMap<String, Consumer<P>>();
		}

		public Builder<P> 					setContextMenu(Map<String, Consumer<P>> _contextMenu) {
			contextMenuEntries = _contextMenu;
			return this;
		}

		public Builder<P> 					setPlayList(PlayList<? extends P> _playlist) {
			playlist = _playlist;
			return this;
		}

		public Builder<P> 					setTableDef(Supplier<Set<TableColumn<P, ?>>> _columnSet) {
			tableColumnSet     = _columnSet;
			return this;
		}

		public Builder<P> 					setDropPredicate(Predicate<Path> _predicate) {
			droppedFileTest    = _predicate;
			return this;
		}
		public Builder<P>					setDropBuilder(Function<Path, P> _builder) {
			droppedFileBuilder = _builder;
			return this;
		}

		public PlayListControl<P> build() {
			if(tableColumnSet == null)
				tableColumnSet = PlayListTables.<P> getTableColumns( PlayListTables.OnlyName );

			return new PlayListControl<P>() {
				{ setPlayList(playlist); }

				@Override
				public boolean 																		isEditable() {
					return false;
				}

				@Override
				public Optional<Map<String, Consumer<P>>> 											getContextMenuEntries() {
					return Optional.ofNullable(contextMenuEntries);
				}

				@Override
				public Optional<Supplier<Set<TableColumn<P, ?>>>> 									getTableColumnSet() {
//				public Optional<Supplier<Set<? extends TableColumn<? extends Playable<?>, ?>>>> 	getTableColumnSet() {
					return Optional.ofNullable(tableColumnSet);
				}

				@Override
				public Optional<Predicate<Path>> 													getDropPredicate() {
					return Optional.ofNullable(droppedFileTest);
				}

				@Override
				public Optional<Function<Path, P>> 													getDropBuilder() {
					return Optional.ofNullable(droppedFileBuilder);
				}

			};
		}
		
	}

}
