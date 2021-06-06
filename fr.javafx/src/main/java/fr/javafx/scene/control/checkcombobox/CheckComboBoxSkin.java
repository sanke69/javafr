package fr.javafx.scene.control.checkcombobox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CheckComboBoxSkin<T> implements Skin<CheckComboBox<T>> {
	private final CheckComboBox<T> 							skinnable;

    // visuals
    private final ListCell<T> 								buttonCell;
    private final ComboBox<T> 								comboBox;
    
    // data
    private final ObservableList<T> 						items;
    private final ReadOnlyUnbackedObservableList<Integer> 	selectedIndices;
    private final ReadOnlyUnbackedObservableList<T> 		selectedItems;

    public CheckComboBoxSkin(final CheckComboBox<T> control) {
        super();
        skinnable       = control;
        items           = control.getItems();
        
        selectedIndices = (ReadOnlyUnbackedObservableList<Integer>) control.getCheckModel().getCheckedIndices();
        selectedItems   = (ReadOnlyUnbackedObservableList<T>)       control.getCheckModel().getCheckedItems();
        
        comboBox = new ComboBox<T>(items) {
            protected javafx.scene.control.Skin<?> createDefaultSkin() {
                return new ComboBoxListViewSkin<T>(this) {{ setHideOnClick(false); }};
            }
        };
        comboBox . setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            public ListCell<T> call(ListView<T> listView) {
                return new CheckBoxListCell<T>(new Callback<T, ObservableValue<Boolean>>() {
                    @Override public ObservableValue<Boolean> call(T item) {
                        return control.getItemBooleanProperty(item);
                    }
                }, control.getConverter());
            };
        });
       	comboBox . setConverter(control.getConverter());

        buttonCell = new ListCell<T>() {
        	ChangeListener<? super String> sl = (_obs, _old, _new) -> { updateItem(null, true); };

        	{ textProperty().addListener(sl); }

            @Override protected void updateItem(T item, boolean empty) {
                textProperty().removeListener(sl);

                final StringConverter<T> sc = getSkinnable().getConverter();

            	if(selectedItems.size() == 0) {
            		setText("#empty");

            	} else {
                    final StringBuilder sb = new StringBuilder();
                    
                    for(int i = 0, max = selectedItems.size(); i < max; i++) {
                    	sb.append(sc.toString(selectedItems.get(i)));

                        if (i < max - 1)
                            sb.append(", ");
                    }

                    setText(sb.toString());
            	}
 

                textProperty().addListener(sl);
            }

        };
        comboBox.setButtonCell(buttonCell);
        
        selectedIndices.addListener(new ListChangeListener<Integer>() {
            @Override public void onChanged(final Change<? extends Integer> c) {
                // we update the display of the ComboBox button cell by
                // just dumbly updating the index every time selection changes.
                buttonCell.updateIndex(0); // TODO:: HACK:: SP previous was 1
            }
        });

    }

	@Override
	public CheckComboBox<T> 	getSkinnable() {
		return skinnable;
	}

	@Override
	public Node 				getNode() {
		return comboBox;
	}

	@Override
	public void 				dispose() {
		
	}
    
}