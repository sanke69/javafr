package fr.javafx.scene.control.checkcombobox;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.StringConverter;

/**
 * A simple UI control that makes it possible to select zero or more items within
 * a ComboBox-like control. Each row item shows a {@link CheckBox}, and the state
 * of each row can be queried via the {@link #checkModelProperty() check model}.
 * 
 * <h3>Screenshot</h3>
 * <p>The following screenshot shows the CheckComboBox with some sample data:
 * 
 * <br/>
 * <img src="checkComboBox.png"/>
 * 
 * <h3>Code Example:</h3>
 * <p>To create the CheckComboBox shown in the screenshot, simply do the 
 * following:
 * 
 * <pre>
 * {@code
 * // create the data to show in the CheckComboBox 
 * final ObservableList<String> strings = FXCollections.observableArrayList();
 * for (int i = 0; i <= 100; i++) {
 *     strings.add("Item " + i);
 * }
 * 
 * // Create the CheckComboBox with the data 
 * final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);
 * 
 * // and listen to the relevant events (e.g. when the selected indices or 
 * // selected items change).
 * checkComboBox.getCheckModel().getSelectedItems().addListener(new ListChangeListener<String>() {
 *     public void onChanged(ListChangeListener.Change<? extends String> c) {
 *         System.out.println(checkComboBox.getCheckModel().getSelectedItems());
 *     }
 * });}
 * }</pre>
 *
 * @param <T> The type of the data in the ComboBox.
 */
public class CheckComboBox<T> extends Control {

    private static <T> StringConverter<T> defaultStringConverter() {
        return new StringConverter<T>() {
            @Override public String toString(T t) {
                return t == null ? null : t.toString();
            }

            @Override public T fromString(String string) {
                return (T) string;
            }
        };
    }

    /**************************************************************************
     * 
     * Private fields
     * 
     **************************************************************************/
    
    private final ObservableList<T>       items;
    private final Map<T, BooleanProperty> itemBooleanMap;
    
    /**************************************************************************
     * 
     * Constructors
     * 
     **************************************************************************/
    
    /**
     * Creates a new CheckComboBox instance with an empty list of choices.
     */
    public CheckComboBox() {
        this(null);
    }
    
    /**
     * Creates a new CheckComboBox instance with the given items available as
     * choices.
     * 
     * @param items The items to display within the CheckComboBox.
     */
    public CheckComboBox(final ObservableList<T> items) {
        final int initialSize = items == null ? 32 : items.size();
        
        this.itemBooleanMap = new HashMap<T, BooleanProperty>(initialSize);
        this.items = items == null ? FXCollections.<T>observableArrayList() : items;
        setCheckModel(new CheckComboBoxBitSetCheckModel<T>(this.items, itemBooleanMap));
    }
    
    
    
    /**************************************************************************
     * 
     * Public API
     * 
     **************************************************************************/
    
    /** {@inheritDoc} */
    @Override protected Skin<?> createDefaultSkin() {
        return new CheckComboBoxSkin<>(this);
    }
    
    /**
     * Represents the list of choices available to the user, from which they can
     * select zero or more items.
     */
    public ObservableList<T> getItems() {
        return items;
    }

    // --- string converter
    /**
     * Converts the user-typed input (when the ComboBox is
     * {@link #editableProperty() editable}) to an object of type T, such that
     * the input may be retrieved via the  {@link #valueProperty() value} property.
     * @return the converter property
     */
    public ObjectProperty<StringConverter<T>> converterProperty() { return converter; }
    private ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter", CheckComboBox.<T>defaultStringConverter());
    public final void setConverter(StringConverter<T> value) { converterProperty().set(value); }
    public final StringConverter<T> getConverter() {return converterProperty().get(); }

    /**
     * Returns the {@link BooleanProperty} for a given item index in the 
     * CheckComboBox. This is useful if you want to bind to the property.
     */
    public BooleanProperty getItemBooleanProperty(int index) {
        if (index < 0 || index >= items.size()) return null;
        return getItemBooleanProperty(getItems().get(index));
    }
    
    /**
     * Returns the {@link BooleanProperty} for a given item in the 
     * CheckComboBox. This is useful if you want to bind to the property.
     */
    public BooleanProperty getItemBooleanProperty(T item) {
        return itemBooleanMap.get(item);
    }
    
    
    
    /**************************************************************************
     * 
     * Properties
     * 
     **************************************************************************/

    // --- Check Model
    private ObjectProperty<IndexedCheckModel<T>> checkModel = 
            new SimpleObjectProperty<>(this, "checkModel"); //$NON-NLS-1$
    
    /**
     * Sets the 'check model' to be used in the CheckComboBox - this is the
     * code that is responsible for representing the selected state of each
     * {@link CheckBox} - that is, whether each {@link CheckBox} is checked or 
     * not (and not to be confused with the 
     * selection model concept, which is used in the ComboBox control to 
     * represent the selection state of each row).. 
     */
    public final void setCheckModel(IndexedCheckModel<T> value) {
        checkModelProperty().set(value);
    }

    /**
     * Returns the currently installed check model.
     */
    public final IndexedCheckModel<T> getCheckModel() {
        return checkModel == null ? null : checkModel.get();
    }

    /**
     * The check model provides the API through which it is possible
     * to check single or multiple items within a CheckComboBox, as  well as inspect
     * which items have been checked by the user. Note that it has a generic
     * type that must match the type of the CheckComboBox itself.
     */
    public final ObjectProperty<IndexedCheckModel<T>> checkModelProperty() {
        return checkModel;
    }
    
    
    
    /**************************************************************************
     * 
     * Implementation
     * 
     **************************************************************************/
    
    
    
    
    /**************************************************************************
     * 
     * Support classes
     * 
     **************************************************************************/
    
    private static class CheckComboBoxBitSetCheckModel<T> extends CheckBitSetModelBase<T> {
        
        /***********************************************************************
         *                                                                     *
         * Internal properties                                                 *
         *                                                                     *
         **********************************************************************/
        
        private final ObservableList<T> items;
        
        
        
        /***********************************************************************
         *                                                                     *
         * Constructors                                                        *
         *                                                                     *
         **********************************************************************/
        
        CheckComboBoxBitSetCheckModel(final ObservableList<T> items, final Map<T, BooleanProperty> itemBooleanMap) {
            super(itemBooleanMap);
            
            this.items = items;
            this.items.addListener(new ListChangeListener<T>() {
                @Override public void onChanged(Change<? extends T> c) {
                    updateMap();
                }
            });
            
            updateMap();
        }
        
        
        
        /***********************************************************************
         *                                                                     *
         * Implementing abstract API                                           *
         *                                                                     *
         **********************************************************************/

        @Override public T getItem(int index) {
            return items.get(index);
        }
        
        @Override public int getItemCount() {
            return items.size();
        }
        
        @Override public int getItemIndex(T item) {
            return items.indexOf(item);
        }
    }



}
