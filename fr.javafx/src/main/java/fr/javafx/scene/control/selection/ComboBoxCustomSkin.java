package fr.javafx.scene.control.selection;

import fr.java.sdk.lang.SoftPtr;
import fr.javafx.scene.control.checkcombobox.CheckComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;

class ComboBoxCustomSkin<T> implements Skin<ComboBoxCustom<T>> {
	final ComboBoxCustom<T>		skinnable;

	final ObservableList<T>		interfaceListView;

	final ComboBox<T> 			singleSelector;
	final CheckComboBox<T> 		multiSelector;

	ComboBoxCustomSkin(ComboBoxCustom<T> _skinnable) {
		super();
		skinnable         = _skinnable;

		interfaceListView = FXCollections.observableArrayList();

		if( getSkinnable().isMutiSelectionEnabled() ) {
			singleSelector = null;
			multiSelector  = configureMultiSelection( getSkinnable().listProperty(), getSkinnable().getStringConverter() );

			createBindingForMulti();

		} else {
			singleSelector = configureMonoSelection( getSkinnable().listProperty(), getSkinnable().getStringConverter() );
			multiSelector  = null;

			createBindingForMono();
		}

	}

	@Override
	public ComboBoxCustom<T>	getSkinnable() {
		return skinnable;
	}

	@Override
	public Node 				getNode() {
		return getSkinnable().isMutiSelectionEnabled() ? multiSelector : singleSelector;
	}

	@Override
	public void 				dispose() {
		;
	}

	private ComboBox<T> 		configureMonoSelection(final ObservableList<T> _values, final StringConverter<T> _stringConverter) {
		ComboBox<T> singleSelector = new ComboBox<T>( _values );

		adjustDimension( singleSelector );

		if(_stringConverter != null)
			singleSelector . setConverter(_stringConverter);

		return singleSelector;
	}
	private void 				createBindingForMono() {
		final SoftPtr<Boolean> externalChange = new SoftPtr<Boolean>(false);
		final SoftPtr<Boolean> internalChange = new SoftPtr<Boolean>(false);

		// Start by populating skin lists
		if( ! getSkinnable().listSelection().isEmpty() ) {
			T selection = getSkinnable().listSelection().get(0);
			
			interfaceListView.add(selection);
			singleSelector.getSelectionModel().select(selection);
		}

		getSkinnable().listSelection().addListener((ListChangeListener<T>) c -> {
			if(externalChange.get())
				return ;

			externalChange.set(true);
			while (c.next()) {
				if (c.wasUpdated()) {
					System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
//					for(int i = c.getFrom(); i< c.getTo(); ++i)
//						listD.set(listB.get(c.getFrom()));
				} else
				if (c.wasReplaced()) {
					interfaceListView.clear();
					interfaceListView.add(c.getAddedSubList().get(0));
				} else
				if (c.wasAdded()) {
					interfaceListView.clear();
					interfaceListView.add(c.getAddedSubList().get(0));
				} else
				if (c.wasRemoved()) {
					interfaceListView.clear();
				}
			}
			externalChange.set(false);
		});
		interfaceListView.addListener((ListChangeListener<T>) c -> {
			if( ! externalChange.get() ) {
				externalChange.set(true);
				while (c.next()) {
					if (c.wasUpdated()) {
						System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
	//					for(int i = c.getFrom(); i< c.getTo(); ++i)
	//						listD.set(listB.get(c.getFrom()));
					} else
					if (c.wasReplaced()) {
						for(T t : c.getRemoved())
							getSkinnable().listSelection().remove(t);
						for(T t : c.getAddedSubList())
							if( ! getSkinnable().listSelection().contains(t) )
								getSkinnable().listSelection().add(t);
					} else
					if (c.wasAdded()) {
						getSkinnable().listSelection().addAll(c.getAddedSubList());
					} else
					if (c.wasRemoved()) {
						getSkinnable().listSelection().removeAll(c.getRemoved());
					}
				}
				externalChange.set(false);
			}
			
			if( ! internalChange.get() ) {
				if(internalChange.get())
					return ;

				internalChange.set(true);
				while (c.next()) {
					if (c.wasUpdated() || c.wasPermutated()) {
						System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
					} else
					if (c.wasReplaced()) {
						if(!c.getAddedSubList().isEmpty())
							singleSelector.getSelectionModel().select(c.getAddedSubList().get(0));
						else
							singleSelector.getSelectionModel().clearSelection();
					} else
					if (c.wasAdded()) {
						singleSelector.getSelectionModel().select(c.getAddedSubList().get(0));
					} else
					if (c.wasRemoved()) {
						singleSelector.getSelectionModel().clearSelection();
					}
				}
				internalChange.set(false);
			}
			
		});
		singleSelector.getSelectionModel().selectedItemProperty().addListener((_obs, _old, _new) -> {
			internalChange.set(true);
			interfaceListView.clear();
			if(_new != null)
				interfaceListView.add(_new);
			internalChange.set(false);
		});

	}

	private CheckComboBox<T> 	configureMultiSelection(final ObservableList<T> _values, final StringConverter<T> _stringConverter) {
		CheckComboBox<T> multiSelector  = new CheckComboBox<T>( _values );

		adjustDimension( multiSelector );

		if(_stringConverter != null)
			multiSelector . setConverter(_stringConverter);

		return multiSelector;
	}
	private void 				createBindingForMulti() {
		final SoftPtr<Boolean> externalChange = new SoftPtr<Boolean>(false);
		final SoftPtr<Boolean> internalChange = new SoftPtr<Boolean>(false);

		// Start by populating skin lists
		if( ! getSkinnable().listSelection().isEmpty() ) {
			for(T t : getSkinnable().listSelection()) {
				interfaceListView.add(t);
				multiSelector.getCheckModel().check(t);
			}
		}

		getSkinnable().listSelection().addListener((ListChangeListener<T>) c -> {
			if(externalChange.get())
				return ;

			externalChange.set(true);
			while (c.next()) {
				if (c.wasUpdated()) {
					System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
//					for(int i = c.getFrom(); i< c.getTo(); ++i)
//						listD.set(listB.get(c.getFrom()));
				} else
				if (c.wasReplaced()) {
					for(T t : c.getRemoved())
						interfaceListView.remove(t);
					for(T t : c.getAddedSubList())
						if( ! interfaceListView.contains(t) )
							interfaceListView.add(t);
				} else
				if (c.wasAdded()) {
					interfaceListView.addAll(c.getAddedSubList());
				} else
				if (c.wasRemoved()) {
					interfaceListView.removeAll(c.getRemoved());
				}
			}
			externalChange.set(false);
		});
		interfaceListView.addListener((ListChangeListener<T>) c -> {
			if( externalChange.get() ) {
				internalChange.set(true);
				while (c.next()) {
					if (c.wasUpdated() || c.wasPermutated()) {
						System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
					} else
					if (c.wasReplaced()) {
						for(T t : c.getRemoved())
							multiSelector.getCheckModel().clearCheck(t);
						for(T t : c.getAddedSubList())
							if( ! multiSelector.getCheckModel().getCheckedItems().contains(t) )
								multiSelector.getCheckModel().check(t);
					} else
					if (c.wasAdded()) {
						for(T t : c.getAddedSubList())
							multiSelector.getCheckModel().check(t);
					} else
					if (c.wasRemoved()) {
						for(T t : c.getRemoved())
							multiSelector.getCheckModel().clearCheck(t);
					}
				}
				internalChange.set(false);
			}
			
			if( internalChange.get() ) {
				externalChange.set(true);
				while (c.next()) {
					if (c.wasUpdated() || c.wasPermutated()) {
						System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
					} else
					if (c.wasReplaced()) {
						for(T t : c.getRemoved())
							getSkinnable().listSelection().remove(t);
						for(T t : c.getAddedSubList())
							if( ! getSkinnable().listSelection().contains(t) )
								getSkinnable().listSelection().add(t);
					} else
					if (c.wasAdded()) {
						getSkinnable().listSelection().addAll(c.getAddedSubList());
					} else
					if (c.wasRemoved()) {
						getSkinnable().listSelection().removeAll(c.getRemoved());
					}
				}
				externalChange.set(false);
			}
			
		});
		multiSelector.getCheckModel().getCheckedItems().addListener((ListChangeListener<T>) c -> {
			if(internalChange.get())
				return ;

			internalChange.set(true);
			while (c.next()) {
				if (c.wasUpdated() || c.wasPermutated()) {
					System.err.println("NOT TAKEN INTO ACCOUNT YET !!!!");
				} else
				if (c.wasReplaced()) {
					for(T t : c.getRemoved())
						interfaceListView.remove(t);
					for(T t : c.getAddedSubList())
						if( ! interfaceListView.contains(t) )
							interfaceListView.add(t);
				} else
				if (c.wasAdded()) {
					interfaceListView.addAll(c.getAddedSubList());
				} else
				if (c.wasRemoved()) {
					interfaceListView.removeAll(c.getRemoved());
				}
			}
			internalChange.set(false);
		});

	}
	
	private void 				adjustDimension(Region _node) {
		_node.minWidthProperty()   . bindBidirectional(getSkinnable().prefWidthProperty());
		_node.minHeightProperty()  . bindBidirectional(getSkinnable().prefHeightProperty());
		_node.prefWidthProperty()  . bindBidirectional(getSkinnable().prefWidthProperty());
		_node.prefHeightProperty() . bindBidirectional(getSkinnable().prefHeightProperty());
		_node.maxWidthProperty()   . bindBidirectional(getSkinnable().prefWidthProperty());
		_node.maxHeightProperty()  . bindBidirectional(getSkinnable().prefHeightProperty());
	}

}
