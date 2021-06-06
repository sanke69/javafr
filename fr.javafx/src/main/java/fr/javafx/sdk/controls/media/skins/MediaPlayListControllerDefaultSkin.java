package fr.javafx.sdk.controls.media.skins;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import fr.javafx.sdk.controls.media.MediaPlayListController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Skin;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MediaPlayListControllerDefaultSkin extends Pane implements Skin<MediaPlayListController> {

	private TableView<String>      table;
	private ObservableList<String> data;

//	FxVideoPlayer mediaPlayer;
	int             currentIndex;

	public MediaPlayListControllerDefaultSkin(MediaPlayListController _control) {
		super();

		getChildren().add(build());
	}

	@Override
	public MediaPlayListController getSkinnable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public Pane build() {
		VBox canvas = new VBox();
		canvas.getChildren().addAll(getMenuBar(), createTable());
		return canvas;
	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		EventHandler<ActionEvent> action = e -> menuAction(((KeyCodeCombination) ((MenuItem) e.getSource()).acceleratorProperty().get()).getCode().getName());
		Menu menuFile = new Menu("File");
		ObservableList<MenuItem> items = menuFile.getItems();
		items.add(createMenuItem("New_Playlist", "Ctrl+N", action));
		items.add(createMenuItem("Open_Playlist", "Ctrl+O", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuItem("Add_Files", "Ctrl+L", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuItem("Save_Playlist", "Ctrl+S", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuItem("File_info", "Ctrl+D", action));
		Menu menuPlayList = new Menu("Playlist");
		items = menuPlayList.getItems();
		items.add(createMenuItem("Select_all", "Ctrl+A", action));
		items.add(createMenuItem("Select_none", "Ctrl+Z", action));
		items.add(createMenuItem("Invert_selection", "Ctrl+I", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuItem("Remove_selected", "Ctrl+Delete", action));
		items.add(createMenuItem("Clear_playlist", "Ctrl+C", action));
		items.add(createMenuItem("Remove_missing_files", "Ctrl+P", action));
		Menu menuSort = new Menu("Sort");
		items = menuSort.getItems();
		items.add(createMenuItem("Sort_by_name", "Ctrl+T", action));
		items.add(createMenuItem("Sort_by_path", "Ctrl+Q", action));
		items.add(new SeparatorMenuItem());
		items.add(createMenuItem("Reverse_list", "Ctrl+R", action));
		items.add(createMenuItem("Randomize_list", "Ctrl+H", action));
		menuBar.getMenus().addAll(menuFile, menuPlayList, menuSort);
		return menuBar;
	}

	private void menuAction(String keyCode) {
		switch (keyCode) {
		case "N":
			System.out.println("A faire: New_Playlist");
			break;
		case "O":
			System.out.println("A faire: Open_Playlist");
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
		case "L":
			System.out.println("A faire: Add_Files");
			break;
		case "S":
			System.out.println("A faire: Save_Playlist");
			break;
		case "D":
			System.out.println("A faire: File_info");
			break;

		case "A":
			table.getSelectionModel().selectAll();
			break;
		case "Z":
			table.getSelectionModel().clearSelection();
			break;
		case "I":
			TableViewSelectionModel<String> sm = table.getSelectionModel();
			for (int i = 0; i < data.size(); i++)
				if (sm.isSelected(i))
					sm.clearSelection(i);
				else
					sm.select(i);
			break;
		case "Delete":
			data.removeAll(table.getSelectionModel().getSelectedItems());
			break;
		case "C":
			data.clear();
			break;
		case "P":
			//data.removeIf((scenarioDesc) -> !scenarioDesc.isAvailable/*!ScenarioManager.isAvailable(scenarioDesc.scenarioSource)*/);
			break;
		case "T":
			data.sort((f1, f2) -> f1.toString().toLowerCase().compareTo(f2.toString().toLowerCase()));
			break;
		case "Q":
			data.sort((f2, f1) -> f1.toString().toLowerCase().compareTo(f2.toString().toLowerCase()));
			break;
		case "R":
			Collections.reverse(data);
			break;
		case "H":
			Collections.shuffle(data);
			break;
		default:
			break;
		}
	}

	private MenuItem createMenuItem(String name, String keyCombination, EventHandler<ActionEvent> action) {
		MenuItem menuitem = new MenuItem(name);
		menuitem.setAccelerator(KeyCombination.keyCombination(keyCombination));
		menuitem.setOnAction(action);
		return menuitem;
	}

	private TableView<String> createTable() {
		data  = FXCollections.observableArrayList();

		table = new TableView<>();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		TableColumn<String, String> indexCol = new TableColumn<>("id");
		TableColumn<String, String> scenarioCol = new TableColumn<>("Title");
		TableColumn<String, String> durationCol = new TableColumn<>("Duration");

		table.setPlaceholder(new Label("Insert element here"));
		table.getColumns().add(indexCol);
		table.getColumns().add(scenarioCol);
		table.getColumns().add(durationCol);

		table.setItems(data);
		table.autosize();
		table.setPrefWidth(400.0);
		
		table.getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> {
			TableView.TableViewSelectionModel<String> selectionModel = table.getSelectionModel();
		    ObservableList selectedCells = selectionModel.getSelectedCells();
		    TablePosition<Integer, String> tablePosition = (TablePosition<Integer, String>) (selectedCells.get(0));

			currentIndex = tablePosition.getRow();
			System.out.println(currentIndex);
		});

		MenuItem removeItem = new MenuItem("Remove");
		removeItem.setOnAction(a -> {
			data.removeAll(table.getSelectionModel().getSelectedItems());
			table.getSelectionModel().clearSelection();
		});
		MenuItem infoItem = new MenuItem("Info");
		infoItem.setOnAction(a -> System.out.println("A faire"));
		table.setContextMenu(new ContextMenu(infoItem, new SeparatorMenuItem(), removeItem));

		indexCol.setCellValueFactory(param -> new SimpleStringProperty(""));
		indexCol.setCellFactory(param -> new TableCell<String, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				TableRow<?> tr = getTableRow();
				setText((tr != null && item != null) ? Integer.toString(tr.getIndex()) : "");
			}
		});
		indexCol.setMinWidth(50.0);
		indexCol.setPrefWidth(50.0);
		indexCol.setMaxWidth(50.0);
		indexCol.setSortable(false);

		scenarioCol.setCellFactory(param -> new TableCell<String, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.toString());
					setTextFill(new File(item).isFile() ? Color.BLACK : Color.RED);
				} else
					setText("");
			}
		});
		scenarioCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(param.getValue()));
		scenarioCol.setMinWidth(250.0);
		scenarioCol.setPrefWidth(250.0);
		scenarioCol.setMaxWidth(250.0);

		durationCol.setCellValueFactory(param -> {
			int maxLenght = Integer.MIN_VALUE;
			int lenght = param.getValue().length();
			if (lenght > maxLenght)
				maxLenght = lenght;
			return new SimpleStringProperty((lenght < 0) ? "-" : Integer.toString(lenght));
		});
		durationCol.setMinWidth(100.0);
		durationCol.setPrefWidth(100.0);
		durationCol.setMaxWidth(100.0);

		final IntegerProperty    dragFromIndex  = new SimpleIntegerProperty(-1);
		final ArrayList<Integer> dragOriIndexs  = new ArrayList<>();
		final ArrayList<Integer> dragCurrIndexs = new ArrayList<>();
		final ArrayList<String>  dragElements   = new ArrayList<>();
		final IntegerProperty    oldRowIndex    = new SimpleIntegerProperty(-1);

		table.setRowFactory(cb -> {
			TableRow<String> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!row.isEmpty()))
					; //mediaPlayer.setMedia("file:///" + row.getItem().replace("\\", "/").replace(" ", "%20"));
			});
			row.setOnDragDetected(ev -> {
				dragOriIndexs  . clear();
				dragElements   . clear();
				dragCurrIndexs . clear();

				dragFromIndex  . set(row.getIndex());
				dragOriIndexs  . addAll(table.getSelectionModel().getSelectedIndices());

				String contentString = "";
				for(Integer id : dragOriIndexs) {
					String ele = data.get(id.intValue());
					dragElements.add(ele);
					contentString += ele + "\n";
				}

				dragCurrIndexs.addAll(dragOriIndexs);

				ClipboardContent content = new ClipboardContent();
				content.putString(contentString);
				row.startDragAndDrop(TransferMode.MOVE).setContent(content);
				oldRowIndex.set(row.getIndex());
				ev.consume();
			});
			row.setOnDragDropped(ev -> {
				Object gs = ev.getGestureSource();
				if(gs != null && gs instanceof TableRow<?> && ((TableRow<?>) gs).getTableView() == table) {
					dragFromIndex . set(-1);
					oldRowIndex   . set(-1);
					ev.setDropCompleted(true);
					ev.consume();
				} else if (ev.getDragboard().hasFiles())
					dragFileDropped(ev, row.getIndex());
			});
			row.setOnDragOver(ev -> {
				if(ev.getGestureSource() != null) {
					ev.acceptTransferModes(TransferMode.MOVE);
					if(row.getIndex() != oldRowIndex.get()) {
						int id = 0;
						for(Integer currId : dragCurrIndexs)
							data.remove(currId.intValue() - id++);
						int base = row.getIndex() - dragFromIndex.get();
						int previousIndex = -1;
						for(int i = 0; i < dragElements.size(); i++) {
							int newIndex = base + dragOriIndexs.get(i);
							if(newIndex <= previousIndex)
								newIndex = previousIndex + 1;
							else if(newIndex > data.size())
								newIndex = data.size();
							data.add(newIndex, dragElements.get(i));
							dragCurrIndexs.set(i, newIndex);
							table.getSelectionModel().clearSelection();
							for (Integer integer : dragCurrIndexs)
								table.getSelectionModel().select(integer);
							previousIndex = newIndex;
							oldRowIndex.set(row.getIndex());
						}
					}
					ev.consume();
				} else if (ev.getDragboard().hasFiles())
					dragOverFile(ev);
			});
			row.setOnDragDone(ev -> {
				if(ev.getGestureSource() != null) {
					ev.acceptTransferModes(TransferMode.MOVE);
					ev.consume();
				}
			});
			return row;
		});

		table.setOnDragOver(ev -> { 
			if(ev.getDragboard().hasFiles())
				dragOverFile(ev);
		});
		table.setOnDragDropped(ev -> {
			System.out.println("tada");
			if (ev.getDragboard().hasFiles())
				dragFileDropped(ev, 0);
		});
		table.setOnKeyPressed(ev -> {
			if(ev.getCode() == KeyCode.DELETE) {
				data.removeAll(table.getSelectionModel().getSelectedItems());
				table.getSelectionModel().clearSelection();
			}
		});

		return table;
	}

	private void dragOverFile(DragEvent ev) { System.out.println("drag over");
		for(File file : ev.getDragboard().getFiles()) {
			System.out.println("file " + file.getAbsolutePath());
			if(!isValidMediaFile(file))
				return;
		}
		System.out.println("transfert OK");
		ev.acceptTransferModes(TransferMode.MOVE);
		ev.consume();
	}
	
	private void dragFileDropped(DragEvent ev, int index) {
		System.out.println(data.size());
		if (index < 0)
			index = 0;
		else if (index > data.size())
			index = data.size();
		for(File file : ev.getDragboard().getFiles()) {
			if(isValidMediaFile(file))
				data.add(index, file.getAbsolutePath());
		}
		ev.setDropCompleted(true);
		ev.consume();
	}

	
	boolean isValidMediaFile(File _file) {
		String ext = _file.getAbsolutePath().substring(_file.getAbsolutePath().lastIndexOf("."));

		if(ext.compareTo(".mp3") == 0) return true;
		if(ext.compareTo(".mp4") == 0) return true;
		if(ext.compareTo(".wav") == 0) return true;
		if(ext.compareTo(".ogg") == 0) return true;
		
		return false;
	}
/*
	public void setMediaPlayer(FxVideoPlayer _mediaPlayer) {
		mediaPlayer = _mediaPlayer;
	}
*/
	public int getCurrentIndex() {
		return currentIndex;
	}
	public String getNext() {
		if(++currentIndex > data.size())
			currentIndex = 0;

		table.getSelectionModel().clearSelection();
		table.getSelectionModel().select(currentIndex);
		return data.get(currentIndex);
	}
	public String getPrevious() {
		if(--currentIndex < 0)
			currentIndex = data.size() - 1;
		table.getSelectionModel().clearSelection();
		table.getSelectionModel().select(currentIndex);
		return data.get(currentIndex);
	}

	
}
