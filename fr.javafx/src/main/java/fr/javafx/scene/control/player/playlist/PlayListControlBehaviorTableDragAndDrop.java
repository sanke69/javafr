package fr.javafx.scene.control.player.playlist;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import fr.java.player.PlayList;
import fr.javafx.behavior.Behavior;
import fr.javafx.behavior.Visual;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Skin;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class PlayListControlBehaviorTableDragAndDrop<P> implements Behavior<PlayListControl<P>> {
	private final PlayListControl<P>  	control;
	private PlayListControl.Visual<P> 	visual;

	private final TableDragContext   	dc = new TableDragContext();

	public PlayListControlBehaviorTableDragAndDrop(PlayListControl<P> _control) {
		super();
		control = _control;
		initialize();
	}
	public PlayListControlBehaviorTableDragAndDrop(PlayListControl<P> _control, Visual<PlayListControl<P>> _visual) {
		super();
		control = _control;
		
		initialize();
	}
	public PlayListControlBehaviorTableDragAndDrop(PlayListControl<P> _control, PlayListControl.Visual<P> _visual) {
		super();
		control = _control;
		visual  = _visual;
		initialize();
	}

	public PlayListControl<P> 	getPlayListControl() {
		return control;
	}
	public PlayList<P> 			getPlaylist() {
		return control.getPlaylist();
	}
	public Predicate<Path> 		getDropPredicate() {
		return control.getDropPredicate().get();
	}
	public Function<Path, P> 	getDropBuilder() {
		return control.getDropBuilder().get();
	}
	public TableView<P>			getTable() {
		return control.getVisualTable().get().getTable();
	}
	public P					getPlayable(String _path) {
		return control.getDropBuilder().get().apply(Paths.get(_path));
	}
	public P					getPlayable(Path _path) {
		return control.getDropBuilder().get().apply(_path);
	}

	public void initialize() {
		if(control.getSkin() != null) {
			if(control.getVisualTable().isPresent())
				enable();
		} else {
			final ChangeListener<Skin<?>> listener = new ChangeListener<Skin<?>>() {
			    @Override
			    public void changed(ObservableValue<? extends Skin<?>> obs, Skin<?> ov, Skin<?> nv)  {
			    	enable();
					control.skinProperty().removeListener(this);
			    }
			};
			control.skinProperty().addListener(listener);
		}

	}
	@Override
	public void dispose() {
		
	}

	public void enable() {
		getTable().setOnDragOver	(de -> tableDragOver(de));
		getTable().setOnDragDropped	(de -> tableDragDropped(de));

		getTable().setRowFactory	(cb -> {
			final TableRow<P> row = new TableRow<P>();

			row.setOnMouseClicked	(me -> rowMouseClicked(me, row));
			row.setOnDragDetected	(me -> rowDragDetected(me, row));
			row.setOnDragDropped	(de -> rowDragDropped(de, row));
			row.setOnDragOver		(de -> rowDragOver(de, row));
			row.setOnDragDone		(de -> rowDragDone(de, row));
			return row;
		});

		getTable().setOnKeyPressed(ev -> {
			if(ev.getCode() == KeyCode.DELETE) {
				getTable().getSelectionModel().clearSelection();
			}
		});
	}

	// Table Drag Context
	class TableDragContext {
		final IntegerProperty    dragFromIndex = new SimpleIntegerProperty(-1);
		final ArrayList<Integer> dragOriIndexs  = new ArrayList<>();
		final ArrayList<Integer> dragCurrIndexs = new ArrayList<>();
		final ArrayList<P>  	 dragElements   = new ArrayList<>();
		final IntegerProperty    oldRowIndex    = new SimpleIntegerProperty(-1);
		
		void clear() {
			dragOriIndexs  . clear();
			dragElements   . clear();
			dragCurrIndexs . clear();
		}

		void init(int _rowIdx) {
			clear();

			dragFromIndex  . set(_rowIdx);
			dragOriIndexs  . addAll(getTable().getSelectionModel().getSelectedIndices());
		}
		void init(int _rowIdx, ObservableList<Integer> _selection) {
			clear();

			dragFromIndex  . set(_rowIdx);
			dragOriIndexs  . addAll(_selection);
		}

	}

	private void tableDragOver		(DragEvent _de) {
		if(_de.getDragboard().hasFiles())
			dragOverFile(_de);
	}
	private void tableDragDropped	(DragEvent _de) {
		if(_de.getDragboard().hasFiles())
			dragFileDropped(_de, 0);
	}
	
	private void rowMouseClicked	(MouseEvent _me, TableRow<P> _row) {
		if(_me.getClickCount() == 2 && (!_row.isEmpty()))
			; //mediaPlayer.setMedia("file:///" + row.getItem().replace("\\", "/").replace(" ", "%20"));
	}
	private void rowDragDetected	(MouseEvent _me, TableRow<P> _row) {
		dc . init(_row.getIndex());

		String contentString = "";
		for(Integer id : dc.dragOriIndexs) {
			P ele = getTable().getItems().get(id.intValue());
			dc.dragElements.add(ele);
			contentString += ele + "\n";
		}

		dc.dragCurrIndexs.addAll(dc.dragOriIndexs);

		ClipboardContent content = new ClipboardContent();
		content.putString(contentString);
		_row.startDragAndDrop(TransferMode.MOVE).setContent(content);
		dc.oldRowIndex.set(_row.getIndex());
		_me.consume();
	}
	private void rowDragDropped		(DragEvent _de, TableRow<P> _row) {
		Object gs = _de.getGestureSource();
		if(gs != null && gs instanceof TableRow<?> && ((TableRow<?>) gs).getTableView() == getTable()) {
			dc.dragFromIndex . set(-1);
			dc.oldRowIndex   . set(-1);
			_de.setDropCompleted(true);
			_de.consume();
		} else if (_de.getDragboard().hasFiles())
			dragFileDropped(_de, _row.getIndex());
		
	}
	private void rowDragOver		(DragEvent _de, TableRow<P> _row) {
		if(_de.getGestureSource() != null) {
			_de.acceptTransferModes(TransferMode.MOVE);
			if(_row.getIndex() != dc.oldRowIndex.get()) {
				int id = 0;
				for(Integer currId : dc.dragCurrIndexs)
					getTable().getItems().remove(currId.intValue() - id++);
				int base = _row.getIndex() - dc.dragFromIndex.get();
				int previousIndex = -1;
				for(int i = 0; i < dc.dragElements.size(); i++) {
					int newIndex = base + dc.dragOriIndexs.get(i);
					if(newIndex <= previousIndex)
						newIndex = previousIndex + 1;
					else if(newIndex > getTable().getItems().size())
						newIndex = getTable().getItems().size();
					getTable().getItems().add(newIndex, dc.dragElements.get(i));
					dc.dragCurrIndexs.set(i, newIndex);
					getTable().getSelectionModel().clearSelection();
					for (Integer integer : dc.dragCurrIndexs)
						getTable().getSelectionModel().select(integer);
					previousIndex = newIndex;
					dc.oldRowIndex.set(_row.getIndex());
				}
			}
			_de.consume();
		} else if (_de.getDragboard().hasFiles())
			dragOverFile(_de);
	}
	private void rowDragDone		(DragEvent _de, TableRow<P> _row) {
		if(_de.getGestureSource() != null) {
			_de.acceptTransferModes(TransferMode.MOVE);
			_de.consume();
		}
	}
	
	private void dragOverFile		(DragEvent _de) {
		if(!control.getDropPredicate().isPresent())
			return ;

		for(File file : _de.getDragboard().getFiles())
			if(!getDropPredicate().test(file.toPath()))
				return;

		_de.acceptTransferModes(TransferMode.MOVE);
		_de.consume();
	}
	private void dragFileDropped	(DragEvent ev, int index) {
		if (index < 0)
			index = 0;
		else if (index > getTable().getItems().size())
			index = getTable().getItems().size();

		for(File file : ev.getDragboard().getFiles())
			if(getDropPredicate().test(file.toPath()))
				getTable().getItems().add(index, getDropBuilder().apply(file.toPath()));

		ev.setDropCompleted(true);
		ev.consume();
	}

	private boolean isValidMediaFile(File _file) {
		String ext = _file.getAbsolutePath().substring(_file.getAbsolutePath().lastIndexOf("."));

		if(ext.compareTo(".m3u") == 0) return true;
		if(ext.compareTo(".mp3") == 0) return true;
		if(ext.compareTo(".mp4") == 0) return true;
		if(ext.compareTo(".wav") == 0) return true;
		if(ext.compareTo(".ogg") == 0) return true;

		return false;
	}

}
