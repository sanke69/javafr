package fr.javafx.scene.control.player.playlist.utils;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.java.media.Media;
import fr.java.utils.URIs;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;

public enum PlayListTables {
	OnlyName, Image, Video, Text;

	public static <P> Supplier<Set<TableColumn<P, ?>>> getTableColumns(PlayListTables _tableList) {
		return new Supplier<Set<TableColumn<P, ?>>>() {

			@Override
			public Set<TableColumn<P, ?>> get() {
				switch(_tableList) {
				case OnlyName:	return onlyNameSet();
				case Text:		return null;
				case Image:		return null;
				case Video:		return videoSet();
				default:		return onlyNameSet();
				}
			}

		};
	}
	public static <P> Supplier<Set<TableColumn<P, ?>>> getTableColumns(PlayListTables _tableList, Class<?> _casted) {
		return new Supplier<Set<TableColumn<P, ?>>>() {

			@Override
			public Set<TableColumn<P, ?>> get() {
				switch(_tableList) {
				case OnlyName:	onlyNameSet();
				case Text:		return null;
				case Image:		return null;
				case Video:		return null;//videoSet(_casted);
				default:		return null;//onlyNameSet(_casted);
				}
			}

		};
	}

	static private <P>
	Set<TableColumn<P, ?>> 		onlyNameSet() {
		Set<TableColumn<P, ?>> columns  = new LinkedHashSet<TableColumn<P, ?>>();

		TableColumn<P, Integer> indexCol = columnIndex ("id");
		TableColumn<P, String>  nameCol  = columnName  ("Name");

		columns.add(indexCol);
		columns.add(nameCol);

		return columns;
	}
	static private <P extends Media.Location> 
	Set<TableColumn<P, ?>> 		onlyNameSet(Class<P> _casted) {
		Set<TableColumn<P, ?>> columns  = new LinkedHashSet<TableColumn<P, ?>>();

		TableColumn<P, Integer> indexCol = columnIndex ("id");
		TableColumn<P, String>  nameCol  = columnName  ("Name", p -> p.getURI().toASCIIString());

		columns.add(indexCol);
		columns.add(nameCol);

		return columns;
	}

	static private <P> 
	Set<TableColumn<P, ?>> 		videoSet() {
		return (Set<TableColumn<P, ?>>) (Object) onlyNameSet(null);
	}
	static private <P extends Media.Infos> 
	Set<TableColumn<P, ?>> 		videoSet(Class<P> _casted) {
		Set<TableColumn<P, ?>> columns = new HashSet<TableColumn<P, ?>>();

		TableColumn<P, String> indexCol    = new TableColumn<>("id");
		TableColumn<P, String> scenarioCol = new TableColumn<>("Title");
		TableColumn<P, String> durationCol = new TableColumn<>("Duration");

		indexCol.setCellFactory(param -> new TableCell<P, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				TableRow<?> tr = getTableRow();
				setText((tr != null && item != null) ? Integer.toString(tr.getIndex()) : "");
			}
		});
		indexCol.setCellValueFactory(param -> new SimpleStringProperty(""));
		indexCol.setMinWidth(50.0);
		indexCol.setPrefWidth(50.0);
		indexCol.setMaxWidth(50.0);
		indexCol.setSortable(false);

		scenarioCol.setCellFactory(param -> new TableCell<P, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.toString());
					setTextFill(new File(item).isFile() ? Color.BLACK : Color.RED);
				} else
					setText("");
			}
		});
		scenarioCol.setCellValueFactory(param -> {
			P playable = param.getValue();

			if(playable.getURI() != null)
				return new ReadOnlyObjectWrapper<String>(URIs.getResourceName(playable.getURI()));
			return new ReadOnlyObjectWrapper<String>("<undef>");
		});
		scenarioCol.setMinWidth(250.0);
		scenarioCol.setPrefWidth(250.0);
		scenarioCol.setMaxWidth(250.0);

		durationCol.setCellValueFactory(param -> {
			int maxLenght = Integer.MIN_VALUE;
			int lenght = param.getValue().getURI().toString().length();
			if (lenght > maxLenght)
				maxLenght = lenght;
			return new SimpleStringProperty((lenght < 0) ? "-" : Integer.toString(lenght));
		});
		durationCol.setMinWidth(100.0);
		durationCol.setPrefWidth(100.0);
		durationCol.setMaxWidth(100.0);

		columns.add(indexCol);
		columns.add(scenarioCol);
		columns.add(durationCol);

		return columns;
	}

	static private <P> 
	TableColumn<P, Integer> 	columnIndex(String _title) {
		TableColumn<P, Integer> indexCol = new TableColumn<>(_title);

		indexCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>());
		indexCol.setCellFactory(param -> new TableCell<P, Integer>() {
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				TableRow<?> tr = getTableRow();
				setText((tr != null && !empty) ? Integer.toString(tr.getIndex()) : "");
			}
		});
		indexCol.setMinWidth(50.0);
		indexCol.setPrefWidth(50.0);
		indexCol.setMaxWidth(50.0);
		indexCol.setSortable(false);

		return indexCol;
	}
	static private <P> 
	TableColumn<P, Integer> 	columnIndex(String _title, Function<P, Integer> _getIndex) {
		TableColumn<P, Integer> indexCol = new TableColumn<>(_title);

		indexCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>( _getIndex.apply( param.getValue()) ));
		indexCol.setCellFactory(param -> new TableCell<P, Integer>() {
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				setText(!empty ? Integer.toString(item) : "");
			}
		});
		indexCol.setMinWidth(50.0);
		indexCol.setPrefWidth(50.0);
		indexCol.setMaxWidth(50.0);
		indexCol.setSortable(false);

		return indexCol;
	}

	static private <P> 
	TableColumn<P, String> 		columnName(String _title) {
		TableColumn<P, String> nameCol = new TableColumn<>(_title);

		nameCol.setCellValueFactory(param -> {
			P value = param.getValue();

			return new ReadOnlyObjectWrapper<String>( value != null ? value.toString() : "<undef>" );
		});
		nameCol.setCellFactory(param -> new TableCell<P, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.toString());
					setTextFill(new File(item).isFile() ? Color.BLACK : Color.RED);
				} else
					setText("");
			}
		});		nameCol.setMinWidth(250.0);
		nameCol.setPrefWidth(250.0);
		nameCol.setMaxWidth(250.0);

		return nameCol;
	}
	static private <P> 
	TableColumn<P, String> 		columnName(String _title, Function<P, String> _getName) {
		TableColumn<P, String> nameCol = new TableColumn<>(_title);

		nameCol.setCellValueFactory(param -> {
			P value = param.getValue();

			return new ReadOnlyObjectWrapper<String>( _getName.apply(value) );
		});
		nameCol.setCellFactory(param -> new TableCell<P, String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(!empty) {
					setText(item);
					setTextFill(new File(item).isFile() ? Color.BLACK : Color.RED);
				} else
					setText("");
			}
		});
		nameCol.setMinWidth(250.0);
		nameCol.setPrefWidth(250.0);
		nameCol.setMaxWidth(250.0);

		return nameCol;
	}

}
