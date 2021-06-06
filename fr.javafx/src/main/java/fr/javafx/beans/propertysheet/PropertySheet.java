package fr.javafx.beans.propertysheet;

import java.util.Optional;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Skin;
import javafx.util.Callback;

import fr.javafx.beans.propertysheet.api.PropertyEditor;
import fr.javafx.behavior.AdvancedSkin;
import fr.javafx.behavior.extended.ControlBase;

public class PropertySheet extends ControlBase {
	private static final String DEFAULT_STYLE_SHEET = "PropertySheet.css";
	private static final String	DEFAULT_STYLE_CLASS	= "property-sheet";

	public static enum VisualMode {
		NAME, CATEGORY, CATEGORY_WITH_ACCORDION
	}

	public static interface Item {
		public Class<?> getType();
		public String 	getCategory();
		public String 	getName();
		public String 	getDescription();
		public Object 	getValue();
		public Optional<ObservableValue<? extends Object>> getObservableValue();

		public void setValue(Object value);

		default public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
			return Optional.empty();
		}

		default public boolean isEditable() {
			return true;
		}

	}

	private final SimpleObjectProperty<VisualMode>							visualModeProperty		= new SimpleObjectProperty<>(this, "mode", VisualMode.CATEGORY_WITH_ACCORDION);
	private final SimpleStringProperty										itemNameFilterProperty	= new SimpleStringProperty(this, "titleFilter", "");

	private final ObservableList<Item> 										items;
	private final SimpleBooleanProperty										visualSwitchEnabled		= new SimpleBooleanProperty(this, "modeSwitcherVisible", true);
	private final SimpleBooleanProperty										searchModeEnabled		= new SimpleBooleanProperty(this, "searchBoxVisible", true);

	private final SimpleObjectProperty<Callback<Item, PropertyEditor<?>>>	propertyEditorFactory	= new SimpleObjectProperty<>(this, "propertyEditor", DefaultPropertyEditorFactory.instance());

	public PropertySheet() {
		this(null);
	}
	public PropertySheet(ObservableList<Item> _items) {
		getStyleClass().add(DEFAULT_STYLE_CLASS);

		items = _items == null ? FXCollections.<Item>observableArrayList() : _items;
	}

	public ObservableList<Item> 											getItems() {
		return items;
	}

	@Override
	protected Skin<PropertySheet> 											createDefaultSkin() {
		return new AdvancedSkin<PropertySheet>(this, PropertySheetVisual::new, PropertySheetBehavior::new);
	}

	@Override
	public String 															getUserAgentStylesheet() {
		return getUserAgentStylesheet(PropertySheet.class, DEFAULT_STYLE_SHEET);
	}

	public final SimpleObjectProperty<VisualMode> 							visualModeProperty() {
		return visualModeProperty;
	}
	public final void 														setVisualMode(VisualMode mode) {
		visualModeProperty.set(mode);
	}
	public final VisualMode 												getVisualMode() {
		return visualModeProperty.get();
	}

	public final SimpleBooleanProperty 										modeSwitcherVisibleProperty() {
		return visualSwitchEnabled;
	}
	public final void 														setModeSwitcherVisible(boolean visible) {
		visualSwitchEnabled.set(visible);
	}
	public final boolean 													isModeSwitcherVisible() {
		return visualSwitchEnabled.get();
	}

	public final SimpleBooleanProperty 										searchBoxVisibleProperty() {
		return searchModeEnabled;
	}
	public final void 														setSearchBoxVisible(boolean visible) {
		searchModeEnabled.set(visible);
	}
	public final boolean 													isSearchBoxVisible() {
		return searchModeEnabled.get();
	}

	public final SimpleStringProperty 										itemNameFilterProperty() {
		return itemNameFilterProperty;
	}
	public final void 														setItemNameFilter(String filter) {
		itemNameFilterProperty.set(filter);
	}
	public final String 													getItemNameFilter() {
		return itemNameFilterProperty.get();
	}

	public final SimpleObjectProperty<Callback<Item, PropertyEditor<?>>> 	propertyEditorFactory() {
		return propertyEditorFactory;
	}
	public final void 														setPropertyEditorFactory(Callback<Item, PropertyEditor<?>> factory) {
		propertyEditorFactory.set(factory == null ? DefaultPropertyEditorFactory.instance() : factory);
	}
	public final Callback<Item, PropertyEditor<?>> 							getPropertyEditorFactory() {
		return propertyEditorFactory.get();
	}

}
