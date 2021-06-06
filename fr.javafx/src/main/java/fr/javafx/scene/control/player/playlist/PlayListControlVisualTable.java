package fr.javafx.scene.control.player.playlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.java.lang.exceptions.NotYetImplementedException;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PlayListControlVisualTable<P> extends PlayListControlVisualBase<P> implements PlayListControl.VisualTable<P> {
	private TableView<P>    	table;

	private ObjectProperty<P>	current, focused;

	public PlayListControlVisualTable(PlayListControl<P> _control) {
		super(_control);

		getTable().setItems(getSkinnable().playlistProperty());
		getTable().setContextMenu(createContextMenu());

		current = new SimpleObjectProperty<P>();
		current
			.addListener((InvalidationListener) evt -> getSkinnable().setCurrent(current.get()));

		focused = new SimpleObjectProperty<P>();
		focused
			.addListener((InvalidationListener) evt -> getSkinnable().setFocusedItem(focused.get()));

		getTable().getSelectionModel().getSelectedItems()
			.addListener((InvalidationListener) evt -> getSkinnable().setSelection(table.getSelectionModel().getSelectedItems()));
	}

	@Override
	public void 								enableMultiSelection(boolean _true) {
		throw new NotYetImplementedException();
	}

	public  TableView<P> 						getTable() {
		if(table != null)
			return table;

		table = new TableView<P>();
		if(getSkinnable().getTableColumnSet().isPresent())
			for(TableColumn<P, ?> column : getSkinnable().getTableColumnSet().get().get()) {
				System.out.println("COLUMN: " + column.getText());
				table.getColumns().add(column);
			}

		table.setPlaceholder(new Label("Insert element here"));

		table.autosize();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> current.set(_new));

		return table;
	}

	@Override
	public List<P> 								getValues() {
		return getTable().getItems();
	}

	@Override
	public void 								setCurrent(P _item) {
		current.set(_item);
	}
	@Override
	public P 									getCurrent() {
		return current.get();
	}
	@Override
	public List<P> 								getSelection() {
		return table.getSelectionModel().getSelectedItems();
	}

	public Integer 								getCurrentIndex() {
		return getValues().indexOf(getCurrent());
	}
	public List<Integer> 						getSelectedIndices() {
		return getSelection().stream()
								.map(i -> getValues().indexOf(i))
								.collect(Collectors.toList());
	}

	@Override
	public P 									getSelected() {
		return table.getSelectionModel().getSelectedItem();
	}

	@Override
	public void 								select(Integer... idx) {
		int   idx0   = idx[0];
		int[] idx_m1 = new int[idx.length - 1];
		for(int i = 1; i < idx.length; ++i)
			idx_m1[i] = idx[i - 1];
		table.getSelectionModel().selectIndices(idx0, idx_m1);
	}
	@Override
	public void 								deselect(Integer... _idx) {
		List<Integer> removed = Arrays.asList(_idx);
		List<Integer> all     = new ArrayList<>(table.getSelectionModel().getSelectedIndices());

		all.stream()
			.filter(i -> removed.contains(i))
			.forEach(i -> all.remove(i));

		int   sel_0   = all.get(0);
		int[] sel_lst = new int[all.size() - 1];
		for(int i = 1; i < all.size(); ++i)
			sel_lst[i] = all.get(i - 1);

		table.getSelectionModel().clearSelection();
		table.getSelectionModel().selectIndices(sel_0, sel_lst);
	}

	@Override
	public void									select(P... _values) {
		for(P value : _values)
			table.getSelectionModel().select(value);
	}
	@Override
	public void 								deselect(P... _values) {

	}

	@Override
	public void 								selectAll() {
		table.getSelectionModel().selectAll();
	}
	@Override
	public void 								deselectAll() {
		table.getSelectionModel().clearSelection();
	}

	@Override
	public ReadOnlyObjectProperty<P> 			currentProperty() {
		current.set(table.getSelectionModel().getSelectedItem());
		return current;
	}
	@Override
	public ReadOnlyObjectProperty<P> 			selectedProperty() {
		return null;
	}
	@Override
	public ObservableList<P> 					selectionProperty() {
		return table.getSelectionModel().getSelectedItems();
	}

	@Override
	public void 								clearSelection() {
		table.getSelectionModel().clearSelection();
	}

	@Override
	public void 								inverseSelection() {
		
	}

}
