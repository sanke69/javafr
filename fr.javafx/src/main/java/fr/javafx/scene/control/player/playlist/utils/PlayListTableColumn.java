package fr.javafx.scene.control.player.playlist.utils;

import java.lang.reflect.Method;
import java.util.function.Function;

import fr.java.patterns.capabilities.Nameable;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PlayListTableColumn<T> extends TableColumn<Object, T> {

    public  Method   method;

	public static PlayListTableColumn<String> nameColumn(Class<? extends Nameable> _clazz) {
		PlayListTableColumn<String> column = new PlayListTableColumn<String>();
		try { column.method = _clazz.getMethod("getName", (Class<?>[]) null); } catch(Exception e) { e.printStackTrace(); }

		column.setText("Name");
		column.setCellValueFactory(getCellValueFactoryForString(column.method));

		return column;
	}
	public static PlayListTableColumn<String> newStringColumn(String _title, Method _getter) {
		PlayListTableColumn<String> column = new PlayListTableColumn<String>();
		column.setText(_title);
		column.setCellValueFactory(getCellValueFactoryForString(_getter));

		column.method = _getter;
		
		return column;
	}
	public static PlayListTableColumn<Number> newIntegerColumn(String _title, Method _getter) {
		PlayListTableColumn<Number> column = new PlayListTableColumn<Number>();
		column.setText(_title);
		column.setCellValueFactory(getCellValueFactoryForInteger(_getter));

		column.method = _getter;
		
		return column;
	}
	public static PlayListTableColumn<Number> newLongColumn(String _title, Method _getter) {
		PlayListTableColumn<Number> column = new PlayListTableColumn<Number>();
		column.setText(_title);
		column.setCellValueFactory(getCellValueFactoryForLong(_getter));

		column.method = _getter;
		
		return column;
	}

	public static <P, T> Callback<CellDataFeatures<P, T>, ObservableValue<T>>  			getCellValueFactory(Method _getter, Function<T, ObservableValue<T>> _converter) {
		return (CellDataFeatures<P, T> p) -> {
			try {
				T value = (T) _getter.invoke(p.getValue(), (Object[]) null);
				return _converter.apply(value);
			} catch (Exception e) {
				e.printStackTrace();
				return new ObservableValue<T>() {
					@Override public T    getValue() { return null; }
					@Override public void addListener(ChangeListener<? super T> listener) {}
					@Override public void removeListener(ChangeListener<? super T> listener) {}
					@Override public void addListener(InvalidationListener listener) {}
					@Override public void removeListener(InvalidationListener listener) {}
				};
			}
		};
	}
	public static <P> Callback<CellDataFeatures<P, String>, ObservableValue<String>>  	getCellValueFactoryForString(final Method _getter) {
		return (CellDataFeatures<P, String> p) -> {
			try {
				return new ReadOnlyStringWrapper((String) _getter.invoke(p.getValue(), (Object[]) null));
			} catch (Exception e) {
				e.printStackTrace();
				return new ObservableValue<String>() {
					@Override public String getValue() { return null; }
					@Override public void 	addListener(ChangeListener<? super String> listener) {}
					@Override public void 	removeListener(ChangeListener<? super String> listener) {}
					@Override public void 	addListener(InvalidationListener listener) {}
					@Override public void 	removeListener(InvalidationListener listener) {}
				};
			}
		};
	}
	public static <P> Callback<CellDataFeatures<P, Number>, ObservableValue<Number>>  	getCellValueFactoryForInteger(Method _getter) {
		return (CellDataFeatures<P, Number> p) -> {
			try {
				return new ReadOnlyIntegerWrapper((Integer) _getter.invoke(p.getValue(), (Object[]) null));
			} catch (Exception e) {
				e.printStackTrace();
				return new ObservableValue<Number>() {
					@Override public Integer getValue() { return null; }
					@Override public void 	addListener(ChangeListener<? super Number> listener) {}
					@Override public void 	removeListener(ChangeListener<? super Number> listener) {}
					@Override public void 	addListener(InvalidationListener listener) {}
					@Override public void 	removeListener(InvalidationListener listener) {}
				};
			}
		};
	}
	public static <P> Callback<CellDataFeatures<P, Number>, ObservableValue<Number>>  	getCellValueFactoryForLong(Method _getter) {
		return (CellDataFeatures<P, Number> p) -> {
			try {
				return new ReadOnlyLongWrapper((Long) _getter.invoke(p.getValue(), (Object[]) null));
			} catch (Exception e) {
				e.printStackTrace();
				return new ObservableValue<Number>() {
					@Override public Integer getValue() { return null; }
					@Override public void 	addListener(ChangeListener<? super Number> listener) {}
					@Override public void 	removeListener(ChangeListener<? super Number> listener) {}
					@Override public void 	addListener(InvalidationListener listener) {}
					@Override public void 	removeListener(InvalidationListener listener) {}
				};
			}
		};
	}

	
	
	public static <T> PlayListTableColumn<T> newColumn(Class<?> _clazz, String _title, Function<?, ObservableValue<T>> _getter) {
		PlayListTableColumn<T> column = new PlayListTableColumn<T>();
		column.setText(_title);
		//column.setCellFactory(getCellFactory(_getter));
		return null;
	}


	public static <T> Callback<TableColumn<?,T>,TableCell<?,T>>  getCellFactory(Function<?, ObservableValue<T>> _getter) {
		return (TableColumn<?, T> p) -> {
			return null; //new TableCell<? extends Playable<?>, T>();
		};
	}

}
