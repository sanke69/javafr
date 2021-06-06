/**
 * JavaFR
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.control.list;

import java.util.Collection;
import java.util.function.Function;

import fr.javafx.scene.properties.Selecter;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;

public abstract class ListSelecter<T> extends Control implements Selecter<T> {
	public enum Style { ComboBox, ListView }

	interface ListSelecterSkin<T>       extends Skin<ListSelecter<T>> {
		public SelectionModel<T>                 selectionModel();
	}
	interface ListSelecterSkinSingle<T> extends ListSelecterSkin<T> {
		public default SingleSelectionModel<T>   selectionModel() { return singleSelectionModel(); }
		public SingleSelectionModel<T>           singleSelectionModel();
		}
	interface ListSelecterSkinMulti<T>  extends ListSelecterSkin<T> {
		public default MultipleSelectionModel<T> selectionModel() { return multiSelectionModel(); }
		public MultipleSelectionModel<T>         multiSelectionModel();
	}
	
	public static class Single<T> extends ListSelecter<T> implements Selecter.Single<T> {

		public Single() {
			super((Class<T>) null, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class) {
			super(_class, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items) {
			super(_items, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items) {
			super(_items, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Single(Function<T, String> _stringConverter) {
			super((Class<T>) null, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class, Function<T, String> _stringConverter) {
			super(_class, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items, Function<T, String> _stringConverter) {
			super(_items, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items, Function<T, String> _stringConverter) {
			super(_items, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Single(StringConverter<T> _stringConverter) {
			super((Class<T>) null, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class, StringConverter<T> _stringConverter) {
			super(_class, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items, StringConverter<T> _stringConverter) {
			super(_items, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items, StringConverter<T> _stringConverter) {
			super(_items, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Single(Style _visual) {
			super((Class<T>) null, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class, Style _visual) {
			super(_class, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items, Style _visual) {
			super(_items, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items, Style _visual) {
			super(_items, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Single(Function<T, String> _stringConverter, Style _visual) {
			super((Class<T>) null, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class, Function<T, String> _stringConverter, Style _visual) {
			super(_class, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items, Function<T, String> _stringConverter, Style _visual) {
			super(_items, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items, Function<T, String> _stringConverter, Style _visual) {
			super(_items, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Single(StringConverter<T> _stringConverter, Style _visual) {
			super((Class<T>) null, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Class<T> _class, StringConverter<T> _stringConverter, Style _visual) {
			super(_class, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
		}
		public Single(Collection<T> _items, StringConverter<T> _stringConverter, Style _visual) {
			super(_items, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Single(T[] _items, StringConverter<T> _stringConverter, Style _visual) {
			super(_items, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		@Override
		protected Skin<?> 							createDefaultSkin() {
			switch(visual) {
			case ComboBox : return createComboBoxSingleSkin();
			case ListView : 
			default       : return createListViewSingleSkin();
			}
		}

	    public ReadOnlyObjectProperty<T> 			selectedItemProperty()  { return getSelectionModel().selectedItemProperty(); }

		@Override public ObservableValue<T> 		selectedProperty()      { return singleSelectionModel().selectedItemProperty(); }

		public SingleSelectionModel<T>   			singleSelectionModel() {
			Skin<?> skin = getSkin();

			if(skin instanceof ListSelecterSkinSingle)
				return ((ListSelecterSkinSingle) skin).singleSelectionModel();

			throw new IllegalStateException();
		}

		@Override
		public Region 								getNode() {
			return this;
		}
		@Override
		protected SelectionModel<T> 				getSelectionModel() {
			return singleSelectionModel();
		}

	}
	public static class Multi<T>  extends ListSelecter<T> implements Selecter.Multi<T> {

		public Multi() {
			super((Class<T>) null, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class) {
			super(_class, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items) {
			super(_items, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items) {
			super(_items, (StringConverter<T>) null, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Multi(Function<T, String> _stringConverter) {
			super((Class<T>) null, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class, Function<T, String> _stringConverter) {
			super(_class, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items, Function<T, String> _stringConverter) {
			super(_items, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items, Function<T, String> _stringConverter) {
			super(_items, func2strconv(_stringConverter), Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Multi(StringConverter<T> _stringConverter) {
			super((Class<T>) null,  _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class, StringConverter<T> _stringConverter) {
			super(_class, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items, StringConverter<T> _stringConverter) {
			super(_items, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items, StringConverter<T> _stringConverter) {
			super(_items, _stringConverter, Style.ListView);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Multi(Style _visual) {
			super((Class<T>) null, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class, Style _visual) {
			super(_class, (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items, Style _visual) {
			super(_items,  (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items, Style _visual) {
			super(_items,  (StringConverter<T>) null, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Multi(Function<T, String> _stringConverter, Style _visual) {
			super((Class<T>) null, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class, Function<T, String> _stringConverter, Style _visual) {
			super(_class, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items, Function<T, String> _stringConverter, Style _visual) {
			super(_items,  func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items, Function<T, String> _stringConverter, Style _visual) {
			super(_items, func2strconv(_stringConverter), _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		public Multi(StringConverter<T> _stringConverter, Style _visual) {
			super((Class<T>) null, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Class<T> _class, StringConverter<T> _stringConverter, Style _visual) {
			super(_class, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
		}
		public Multi(Collection<T> _items, StringConverter<T> _stringConverter, Style _visual) {
			super(_items, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}
		public Multi(T[] _items, StringConverter<T> _stringConverter, Style _visual) {
			super(_items, _stringConverter, _visual);

			setSkin( createDefaultSkin() );
			if(_items != null)
				getItems().setAll(_items);
		}

		@Override
		protected Skin<?> 							createDefaultSkin() {
			switch(visual) {
			case ListView : return createListViewMultiSkin();
			case ComboBox : 
			default       : return null;
			}
		}

	    public ObservableList<T> 						selectedItemsProperty() { return multiSelectionModel().getSelectedItems(); }

		@Override public ObservableList<T> 				selectedProperty()      { return multiSelectionModel().getSelectedItems(); }

		public MultipleSelectionModel<T>   				multiSelectionModel() {
			Skin<?> skin = getSkin();

			if(skin instanceof ListSelecterSkinMulti)
				return ((ListSelecterSkinMulti) skin).multiSelectionModel();

			throw new IllegalStateException();
		}

		@Override
		public Region 									getNode() {
			return this;
		}
		@Override
		protected SelectionModel<T> 					getSelectionModel() {
			return multiSelectionModel();
		}

	}


	private static final <T> StringConverter<T> func2strconv(Function<T, String> _f) {
		return new StringConverter<T>() {
			@Override public String  toString(T o)       { return _f.apply(o); }
			@Override public T fromString(String string) { return null; }
		};
	};

	final protected Style              visual;
	final protected StringConverter<T> stringConverter;

	public ListSelecter() {
		this((Class<T>) null, (StringConverter<T>) null, Style.ListView);
	}
	public ListSelecter(Class<T> _class) {
		this(_class, (StringConverter<T>) null, Style.ListView);
	}
	public ListSelecter(Collection<T> _items) {
		this(_items, (StringConverter<T>) null, Style.ListView);
	}
	public ListSelecter(T[] _items) {
		this(_items, (StringConverter<T>) null, Style.ListView);
	}


	public ListSelecter(Function<T, String> _stringConverter) {
		this((Class<T>) null, func2strconv(_stringConverter), Style.ListView);
	}
	public ListSelecter(Class<T> _class, Function<T, String> _stringConverter) {
		this(_class, func2strconv(_stringConverter), Style.ListView);
	}
	public ListSelecter(Collection<T> _items, Function<T, String> _stringConverter) {
		this(_items, func2strconv(_stringConverter), Style.ListView);
	}
	public ListSelecter(T[] _items, Function<T, String> _stringConverter) {
		this(_items, func2strconv(_stringConverter), Style.ListView);
	}


	public ListSelecter(StringConverter<T> _stringConverter) {
		this((Class<T>) null, _stringConverter, Style.ListView);
	}
	public ListSelecter(Class<T> _class, StringConverter<T> _stringConverter) {
		this(_class, _stringConverter, Style.ListView);
	}
	public ListSelecter(Collection<T> _items, StringConverter<T> _stringConverter) {
		this(_items, _stringConverter, Style.ListView);
	}
	public ListSelecter(T[] _items, StringConverter<T> _stringConverter) {
		this(_items, _stringConverter, Style.ListView);
	}


	public ListSelecter(Function<T, String> _stringConverter, Style _visual) {
		this((Class<T>) null, func2strconv(_stringConverter), _visual);
	}
	public ListSelecter(Class<T> _class, Function<T, String> _stringConverter, Style _visual) {
		this(_class, func2strconv(_stringConverter), _visual);
	}
	public ListSelecter(Collection<T> _items, Function<T, String> _stringConverter, Style _visual) {
		this(_items, func2strconv(_stringConverter), _visual);
	}
	public ListSelecter(T[] _items, Function<T, String> _stringConverter, Style _visual) {
		this(_items, func2strconv(_stringConverter), _visual);
	}


	public ListSelecter(StringConverter<T> _stringConverter, Style _visual) {
		super();
		visual          = _visual;
		stringConverter = _stringConverter;
	}
	public ListSelecter(Class<T> _class, StringConverter<T> _stringConverter, Style _visual) {
		super();
		visual          = _visual;
		stringConverter = _stringConverter;
	}
	public ListSelecter(Collection<T> _items, StringConverter<T> _stringConverter, Style _visual) {
		super();
		visual          = _visual;
		stringConverter = _stringConverter;
	}
	public ListSelecter(T[] _items, StringConverter<T> _stringConverter, Style _visual) {
		super();
		visual          = _visual;
		stringConverter = _stringConverter;
	}


	public void 								setItems(Collection<T> _layers) {
		getItems().setAll(_layers);
	}
	public void 								setItems(Collection<T> _layers, T _default) {
		getItems().setAll(_layers);
		if(_default != null)
			selectionModel().select(_default);
	}
	public void 								setItems(ObservableList<T> _layers) {
		getItems().setAll(_layers);
	}
	public void 								setItems(ObservableList<T> _layers, T _default) {
		getItems().setAll(_layers);
		if(_default != null)
			selectionModel().select(_default);
	}

	public ObservableList<T> 					getItems() {
		Skin<?> skin = getSkin();
		Node    node = getSkin().getNode();

		if(skin instanceof ListView)
			return ((ListView) skin).getItems();
		else if(node instanceof ListView)
			return ((ListView) node).getItems();
		else if(skin instanceof ComboBox)
			return ((ComboBox) skin).getItems();
		else if(node instanceof ComboBox)
			return ((ComboBox) node).getItems();
		throw new IllegalAccessError();
	}
	
	public ObservableList<T>   					itemsProperty() {
		return getItems();
	}


	protected abstract SelectionModel<T> 		getSelectionModel();
	protected SelectionModel<T> 				selectionModel() {
		return getSelectionModel();
	}


	public void									setMaxDisplayedItems(int _maxLines) {
		autoAjdustHeight(true, _maxLines);
	}


	class InternalListCell extends ListCell<T> {

		public InternalListCell() {
			super();
		}

		@Override
		public void updateItem(T item, boolean empty) {
			super.updateItem(item, empty);

			if(empty || item == null)
				setText(null);
			else
				setText(stringConverter != null ? stringConverter.toString(item) : item.toString());
		}

	}


	protected Skin<?> 		createListViewSingleSkin() {
		return new ListSelecterSkinSingle<T>() {
			final ListView<T>             node;
			final SingleSelectionModel<T> selectionModel = new SingleSelectionModel<T>() {

				@Override
				protected T getModelItem(int index) {
					return node.getItems().get(index);
				}

				@Override
				protected int getItemCount() {
					return node.getItems().size();
				}
				
			};

			{
				node = new ListView<T>();
				node.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				node.setCellFactory(lv -> new InternalListCell());
				node.selectionModelProperty().get().getSelectedItems().addListener((ListChangeListener<? super T>) _c -> {
					while(_c.next()) {
						if(_c.wasPermutated() || _c.wasUpdated() || _c.wasReplaced()) {
							selectionModel.select(_c.getAddedSubList().get(0));
						} else if(_c.wasAdded()) {
							selectionModel.select(_c.getAddedSubList().get(0));					
						} else if(_c.wasRemoved()) {
							selectionModel.clearSelection();
						} else {
							throw new RuntimeException("WTF");
						}
					}
				});
			}

			@Override
			public ListSelecter<T> 				getSkinnable() {
				return ListSelecter.this;
			}

			@Override
			public Node 						getNode() {
				return node;
			}

			@Override
			public void 						dispose() {

			}

			@Override
			public SingleSelectionModel<T> 		singleSelectionModel() {
				return selectionModel;
			}

		};
	}
	protected Skin<?> 		createListViewMultiSkin() {
		return new ListSelecterSkinMulti<T>() {
			ListView<T> node;

			{
				node = new ListView<T>();
				node.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				node.setCellFactory(lv -> new InternalListCell());
			}

			@Override
			public ListSelecter<T> 				getSkinnable() {
				return ListSelecter.this;
			}

			@Override
			public Node 						getNode() {
				return node;
			}

			@Override
			public void 						dispose() {

			}

			@Override
			public MultipleSelectionModel<T> 	multiSelectionModel() {
				return node.getSelectionModel();
			}

		};

	}

	protected Skin<?> 		createComboBoxSingleSkin() {
		return new ListSelecterSkinSingle<T>() {
			ComboBox<T>	node;

			{
				node = new ComboBox<T>();
				node.setCellFactory(lv -> new InternalListCell());
			}

			@Override
			public ListSelecter<T> 				getSkinnable() {
				return ListSelecter.this;
			}

			@Override
			public Node 						getNode() {
				return node;
			}

			@Override
			public void 						dispose() {

			}

			@Override
			public SingleSelectionModel<T> 		singleSelectionModel() {
				return node.getSelectionModel();
			}

		};
	}


	public static final double rowHeight = 27d;
	public static final double padHeight =  2d;

	public void 			autoAjdustHeight(boolean _autoAdjust, int _maxLines) {
		if(_autoAdjust) {
			ObjectBinding<Double> hb = new ObjectBinding<Double>() {
				@Override
				protected Double computeValue() {
					int nbLines = getItems().size();

					return (nbLines > _maxLines ? _maxLines : nbLines) * rowHeight + padHeight;
				}
			};
			prefHeightProperty().bind(hb);
		} else
			prefHeightProperty().unbind();
	}

}
