package fr.javafx.scene.control.player.playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import fr.java.player.PlayList;
import fr.javafx.behavior.extended.VisualBase;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public abstract class PlayListControlVisualBase<P> extends VisualBase<PlayListControl<P>> implements PlayListControl.Visual<P> {
	private BorderPane					mainContainer;
	private MenuBar						editorMenu;
	private ContextMenu					contextMenu;

	@SuppressWarnings("unchecked")
	public PlayListControlVisualBase(PlayListControl<P> _control) {
		super(_control);

		mainContainer = new BorderPane();
		mainContainer.setTop(createMenuBar());

		switch(getSkinnable().getVisualMode()) {
		case GALLERY:	PlayListControl.VisualGallery<P> visualGallery = (PlayListControl.VisualGallery<P>) this;
						mainContainer.setCenter(visualGallery.getGallery());
						break;
		case TABLE:		PlayListControl.VisualTable<P> visualTable = (PlayListControl.VisualTable<P>) this;
						mainContainer.setCenter(visualTable.getTable());
						break;
		default:
						break;
		}

	}

	public abstract ObservableObjectValue<P>		currentProperty();
	public abstract ObservableObjectValue<P>		selectedProperty();
	public abstract ObservableList<P>				selectionProperty();

	@Override
	public Node 									getNode() {
		return mainContainer;
	}
	@Override
	public void 									dispose() {
		;
	}

	protected MenuBar 								createMenuBar() {
		if(editorMenu != null)
			return editorMenu;

		editorMenu = new MenuBar();

		EventHandler<ActionEvent> action = e -> actionMenuBar(((KeyCodeCombination) ((MenuItem) e.getSource()).acceleratorProperty().get()).getCode().getName());
		Menu menuFile = new Menu("File");
		ObservableList<MenuItem> items = menuFile.getItems();
		items.add(createMenuBarItem("New_Playlist", "Ctrl+N", action));
		items.add(createMenuBarItem("Open_Playlist", "Ctrl+O", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuBarItem("Add_Files", "Ctrl+L", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuBarItem("Save_Playlist", "Ctrl+S", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuBarItem("File_info", "Ctrl+D", action));
		Menu menuPlayList = new Menu("Playlist");
		items = menuPlayList.getItems();
		items.add(createMenuBarItem("Select_all", "Ctrl+A", action));
		items.add(createMenuBarItem("Select_none", "Ctrl+Z", action));
		items.add(createMenuBarItem("Invert_selection", "Ctrl+I", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuBarItem("Remove_selected", "Ctrl+Delete", action));
		items.add(createMenuBarItem("Clear_playlist", "Ctrl+C", action));
		items.add(createMenuBarItem("Remove_missing_files", "Ctrl+P", action));
		Menu menuSort = new Menu("Sort");
		items = menuSort.getItems();
		items.add(createMenuBarItem("Sort_by_name", "Ctrl+T", action));
		items.add(createMenuBarItem("Sort_by_path", "Ctrl+Q", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuBarItem("Reverse_list", "Ctrl+R", action));
		items.add(createMenuBarItem("Randomize_list", "Ctrl+H", action));
		editorMenu.getMenus().addAll(menuFile, menuPlayList, menuSort);

		return editorMenu;
	}
	protected MenuItem 								createMenuBarItem(String name, String keyCombination, EventHandler<ActionEvent> action) {
		MenuItem menuitem = new MenuItem(name);
		menuitem.setAccelerator(KeyCombination.keyCombination(keyCombination));
		menuitem.setOnAction(action);
		return menuitem;
	}
	protected void 									actionMenuBar(String keyCode) {
		PlayList<P> data = getSkinnable().getPlaylist();
		
		switch (keyCode) {
		case "N":		System.out.println("A faire: New_Playlist"); break;
		case "O":		System.out.println("A faire: Open_Playlist");
						FileChooser chooser = new FileChooser();
						ExtensionFilter mp3 = new ExtensionFilter("MP3 Files(*.mp3)", "*.mp3");
						ExtensionFilter aac = new ExtensionFilter("AAC Files(*.aac)", "*.aac");
						chooser.getExtensionFilters().addAll(mp3, aac);
						File file = chooser.showOpenDialog(null);
						if(file == null)
							return ;
						String fi   = file.getAbsoluteFile().toURI().toString();
						String name = file.getName().toString();
					    ListView<String> list = new ListView<String>();
						list.getItems().add(name);
						ObservableList<String>array = FXCollections.observableArrayList();
						array.addAll(fi);
						break;
		case "L":		System.out.println("A faire: Add_Files");
						break;
		case "S":		System.out.println("A faire: Save_Playlist");
						break;
		case "D":		System.out.println("A faire: File_info");
						break;

		case "A":		selectAll();
						break;
		case "Z":		deselectAll();
						break;

		case "I":		inverseSelection();
						break;

		case "Delete":	data.removeAll(getSelection());
						break;

		case "C":		data.clear();
						break;

		case "P":		//data.removeIf((scenarioDesc) -> !scenarioDesc.isAvailable/*!ScenarioManager.isAvailable(scenarioDesc.scenarioSource)*/);
						break;
		case "T":		data.sort((f1, f2) -> f1.toString().toLowerCase().compareTo(f2.toString().toLowerCase()));
						break;
		case "Q":		data.sort((f2, f1) -> f1.toString().toLowerCase().compareTo(f2.toString().toLowerCase()));
						break;
		case "R":		Collections.reverse(data);
						break;
		case "H":		Collections.shuffle(data);
						break;
		default:		break;
		}
	}

	protected ContextMenu 							createContextMenu() {
		if(contextMenu != null)
			return contextMenu;

		if(getSkinnable().getContextMenuEntries().isPresent()) {
			contextMenu = new ContextMenu();

			List<MenuItem> items = new ArrayList<MenuItem>();
			for(Entry<String, Consumer<P>> item : getSkinnable().getContextMenuEntries().get().entrySet()) {
				final Consumer<P> consumer = item.getValue();

				MenuItem mi = new MenuItem(item.getKey());
				mi.setOnAction((ActionEvent event) -> consumer.accept(selectedProperty().get()));

				items.add(mi);
			}

			contextMenu.getItems().addAll(items);
		}

		return contextMenu;
	}

}
