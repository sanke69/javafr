package fr.javafx.scene.control.collection;

import java.util.Collection;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import fr.java.lang.exceptions.NotYetImplementedException;

import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.io.mouse.adapters.MouseMultiClickHandlerAdapter;
import fr.javafx.lang.FxSelectableList;

public class FxGallery<T, ITEM extends FxGallery.Item<T>> extends Control implements FxSelectableList<T> {
    public static final String cssDefault = FxGallery.class.getResource("FxGallery.css").toExternalForm();

	public static class Item<T> extends StackPane {
        private static final PseudoClass FOCUSED_PSEUDO_CLASS      = PseudoClass.getPseudoClass("focused");
        private static final PseudoClass HIGHLIGHTED_PSEUDO_CLASS  = PseudoClass.getPseudoClass("highlighted");
        private static final PseudoClass CURRENT_PSEUDO_CLASS      = PseudoClass.getPseudoClass("current");
        private static final PseudoClass SELECTED_PSEUDO_CLASS     = PseudoClass.getPseudoClass("selected");

        private final ObjectProperty<T> value;
        private final BooleanProperty 	current, selected, inSelection;

		protected Item(double _width, T _value) {
			super();
			value = new SimpleObjectProperty<T>(_value);

			current  = new SimpleBooleanProperty(false) {
                @Override protected void invalidated() {
                    pseudoClassStateChanged(CURRENT_PSEUDO_CLASS, get());
                }
			};
			selected = new SimpleBooleanProperty(false) {
                @Override protected void invalidated() {
                    pseudoClassStateChanged(HIGHLIGHTED_PSEUDO_CLASS, get());
                }
			};
    		inSelection = new SimpleBooleanProperty(false) {
                @Override protected void invalidated() {
                    pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, get());
                }
			};
			focusedProperty().addListener(e -> pseudoClassStateChanged(FOCUSED_PSEUDO_CLASS, focusedProperty().get()));

			setPrefWidth(_width);
			setPrefHeight(_width);
            getStyleClass().add("galery-item");
//			setOnMouseClicked(this::handleMouseEvent);
		}

		public final ObjectProperty<T>  valueProperty() {
			return value;
		}
		public final void				setValue(T _value) {
			value.set(_value);
		}
		public final T  				getValue() {
			return value.get();
		}

		public final BooleanProperty  	currentProperty() {
			return current;
		}
		public final void 				setCurrent(boolean _true) {
			current.set(_true);
		}
		public final boolean 			isCurrent() {
			return current.get();
		}

		public final BooleanProperty  	selectedProperty() {
			return selected;
		}
		public final void 				setSelected(boolean _true) {
			selected.set(_true);
		}
		public final boolean 			isSelected() {
			return selected.get();
		}

		public final BooleanProperty  	inSelectionProperty() {
			return inSelection;
		}
		public final void 				setInSelection(boolean _true) {
			inSelection.set(_true);
		}
		public final boolean 			isInSelection() {
			return inSelection.get();
		}

		MouseMultiClickHandlerAdapter  clickHandler = new MouseMultiClickHandlerAdapter(this) {
			public void onPrimarySingleClick(MouseEvent _me) 		{ 
				setSelected(true); 
				}
			public void onPrimaryDoubleClick(MouseEvent _me) 		{ setInSelection(!isInSelection()); }
		};

	}
	@FunctionalInterface
	public interface    ItemGenerator<T, ITEM extends Item<T>> {
		public ITEM build(double _size, T _node);
	}

	class 				ItemCurrentPropertyListener implements ChangeListener<Boolean> {
		ITEM item;

		ItemCurrentPropertyListener(ITEM _item) {
			item = _item;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> _obs, Boolean _old, Boolean _new) {
			if(_new) {
				items.stream().forEach(i-> {
					if(i.isCurrent() && i != item)
						i.setCurrent(false);
				});
				current.set(item.getValue());
			}
		}
	}
	class 				ItemSelectedPropertyListener implements ChangeListener<Boolean> {
		ITEM item;

		ItemSelectedPropertyListener(ITEM _item) {
			item = _item;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> _obs, Boolean _old, Boolean _new) {
			if(_new) {
				items.stream().forEach(i-> {
					if(i.isSelected() && i != item)
						i.setSelected(false);
				});
				selected.set(item.getValue());
			}
		}
	}
	class 				ItemInSelectionPropertyListener implements ChangeListener<Boolean> {
		ITEM item;

		ItemInSelectionPropertyListener(ITEM _item) {
			item = _item;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> _obs, Boolean _old, Boolean _new) {
			if(_new)
				selection.add(item.getValue());
			else
				selection.remove(item.getValue());
		}
	}
	
	final DoubleProperty	 						itemSize;
	final ObjectProperty<ItemGenerator<T, ITEM>> 	itemGenerator;

	final ListProperty<T>	 						values;
	final ListProperty<ITEM>	 					items;

	final ObjectProperty<T> 						current, selected;
	final ListProperty<T>	 						selection;

	public FxGallery(double _nodeSize, ItemGenerator<T, ITEM> _builder) {
		super();

		values        = new SimpleListProperty<T>(FXCollections.observableArrayList());

		current       = new SimpleObjectProperty<T>();
		selected      = new SimpleObjectProperty<T>();
		selection     = new SimpleListProperty<T>(FXCollections.observableArrayList());

		items         = new SimpleListProperty<ITEM>(FXCollections.observableArrayList());
		itemSize      = new SimpleDoubleProperty(_nodeSize);
		itemGenerator = new SimpleObjectProperty<ItemGenerator<T, ITEM>>(_builder);

		values.addListener((ListChangeListener<? super T>) change -> {
			while(change.next())
		        if(change.wasAdded())
		            change.getAddedSubList()
		            		.forEach(this::addItem);
		        else if(change.wasRemoved())
		            change.getRemoved()
		            		.forEach(this::removeItem);
	    });
		itemGenerator.addListener((_obs, _old, _new) -> {
			items.clear();
			for(T value : values.get())
				items.add(createItem(value));
		});
	}

	@Override
	protected Skin<FxGallery<T, ITEM>> 				createDefaultSkin() {
		return new AdvancedSkin<FxGallery<T, ITEM>>(this, FxGalleryVisualDefault::new);
	}
    @Override
	public String 									getUserAgentStylesheet() {
        return cssDefault;
    }

	public DoubleProperty 							itemSizeProperty() {
		return itemSize;
	}

	public void 									setItemSize(double _nodeSize) {
		itemSize.set(_nodeSize);
	}
	public double 									getItemSize() {
		return itemSize.doubleValue();
	}

	public void 									clear() {
		values.clear();
	}

	public void 									add(T _value) {
		if(_value != null)
			values.add(_value);
	}
	void 											addItem(T _value) {
		items.add( createItem(_value) );
	}
	public void 									addAll(T[] _values) {
		values.addAll(_values);
	}
	public void 									addAll(Collection<T> _values) {
		values.addAll(_values);
	}

	public void 									remove(T _value) {
		if(values == null)
			return ;
		values.remove(_value);
	}
	void 											removeItem(T _value) {
		
		values.add(_value);
}
	public void 									removeAll(T[] _values) {
		if(values == null)
			return ;
		values.removeAll(_values);
	}
	public void 									removeAll(Collection<T> _values) {
		if(values == null)
			return ;
		values.removeAll(_values);
	}

	public ListProperty<T> 							valuesProperty() {
		return values;
	}

	@Override
	public List<T> 									getValues() {
		return values.get();
	}

	public ListProperty<ITEM> 						itemsProperty() {
		return items;
	}

	public List<ITEM> 								getItems() {
		return items.get();
	}

	public ObjectProperty<ItemGenerator<T, ITEM>> 	builderProperty() {
		return itemGenerator;
	}

	@Override
	public void 									enableMultiSelection(boolean _true) {
		throw new NotYetImplementedException();
	}

	@Override
	public ObservableObjectValue<T> 				currentProperty() {
		return current;
	}
	@Override
	public void 									setCurrent(T _value) {
		getItem(_value).setCurrent(true);
	}
	@Override
	public T 										getCurrent() {
		return current.get();
	}

	@Override
	public ObservableObjectValue<T> 				selectedProperty() {
		return selected;
	}
	@Override
	public T 										getSelected() {
		return selected.get();
	}

	@Override
	public ObservableList<T> 						selectionProperty() {
		return selection;
	}
	@Override
	public List<T> 									getSelection() {
		return selection.get();
	}

	@Override
	public void 									select(Integer... idx) {
		for(Integer i : idx)
			items.get(i).setInSelection(true);
	}
	@Override
	public void 									deselect(Integer... idx) {
		for(Integer i : idx)
			items.get(i).setInSelection(false);
	}

	@Override
	public void 									select(T... _values) {
		for(T v : _values)
			getItem(v).setInSelection(true);
	}
	@Override
	public void 									deselect(T... _values) {
		for(T v : _values)
			getItem(v).setInSelection(false);
	}

	@Override
	public void 									selectAll() {
		for(ITEM i : items)
			i.setInSelection(true);
	}
	@Override
	public void 									deselectAll() {
		for(ITEM i : items)
			i.setInSelection(false);
	}

	@Override
	public void 									clearSelection() {
		deselectAll();
	}

	@Override
	public void 									inverseSelection() {
		for(ITEM i : items)
			i.setInSelection(!i.isInSelection());
	}


	ITEM 											createItem(T _value) {
		ITEM item = itemGenerator.get().build(itemSize.doubleValue(), _value);

		item.currentProperty().addListener(new ItemCurrentPropertyListener(item));
		item.selectedProperty().addListener(new ItemSelectedPropertyListener(item));
		item.inSelectionProperty().addListener(new ItemInSelectionPropertyListener(item));
		
		return item;
	}
	ITEM 											getItem(T _value) {
		return items.stream()
					.filter(i -> i.getValue().equals(_value))
					.findFirst().orElse(null);
	}

}
