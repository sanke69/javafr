package fr.javafx.beans.propertysheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.PropertySheet.VisualMode;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;
import fr.javafx.beans.propertysheet.api.PropertyEditor;
import fr.javafx.behavior.extended.VisualBase;
import fr.javafx.scene.control.editor.TextFields;

public class PropertySheetVisual extends VisualBase<PropertySheet> {
	private static final int MIN_COLUMN_WIDTH = 100;

	private final PropertySheet control;

	private final BorderPane	content;

	private final ToolBar		toolbar;
	private final ToggleButton	modeButton	= new ToggleButton();
	private final TextField		searchField	= TextFields.createClearableTextField();

	private final ScrollPane	scroller;

	public PropertySheetVisual(final PropertySheet _control) {
		super(_control);
		control = _control;

		scroller = new ScrollPane();
		scroller.setFitToWidth(true);

		toolbar = new ToolBar();
		toolbar.managedProperty().bind(toolbar.visibleProperty());
		toolbar.setFocusTraversable(true);

		// property sheet mode

		modeButton.managedProperty().bind(modeButton.visibleProperty());
		modeButton.setSelected(true);
		toolbar.getItems().add(modeButton);

		// property sheet search
		searchField.setPromptText("search by name...");
		searchField.setMinWidth(0);
		HBox.setHgrow(searchField, Priority.SOMETIMES);
		searchField.managedProperty().bind(searchField.visibleProperty());
		toolbar.getItems().add(searchField);

		// layout controls
		content = new BorderPane();
		content.setTop(toolbar);
		content.setCenter(scroller);
		
		getChildren().add(content);

		// setup listeners
		registerChangeListener(control.visualModeProperty(), 					"MODE");
		registerChangeListener(control.propertyEditorFactory(), 		"EDITOR-FACTORY");
		registerChangeListener(control.itemNameFilterProperty(), 					"FILTER");
		registerChangeListener(searchField.textProperty(), 				"FILTER-UI");
		registerChangeListener(control.modeSwitcherVisibleProperty(), 	"TOOLBAR-MODE");
		registerChangeListener(control.searchBoxVisibleProperty(), 		"TOOLBAR-SEARCH");

		control.getItems().addListener((ListChangeListener<Item>) change -> refreshProperties());

		// initialize properly 
		refreshProperties();
		updateToolbar();

		addModeHandler();
	}

	@Override
	public void 			dispose() {
		;
	}

	public void layoutChildren(final double x, final double y, final double w, final double h) {
		content.resizeRelocate(x, y, w, h);
	}



	@Override
	public void handleControlPropertyChanged(String p) {
		super.handleControlPropertyChanged(p);

		if(p == "MODE" || p == "EDITOR-FACTORY" || p == "FILTER") {
			refreshProperties();
		} else if(p == "FILTER-UI") {
			getSkinnable().setItemNameFilter(searchField.getText());
		} else if(p == "TOOLBAR-MODE") {
			updateToolbar();
		} else if(p == "TOOLBAR-SEARCH") {
			updateToolbar();
		}
	}

	private void updateToolbar() {
		modeButton.setVisible(getSkinnable().isModeSwitcherVisible());
		searchField.setVisible(getSkinnable().isSearchBoxVisible());

		toolbar.setVisible(modeButton.isVisible() || searchField.isVisible());
	}

	private void refreshProperties() {
		scroller.setContent(buildPropertySheetContainer());
	}

	private Node buildPropertySheetContainer() {
		Map<String, List<Item>> categoryMap = new TreeMap<>();
		for(Item p : getSkinnable().getItems()) {
			String category = p.getCategory();
			List<Item> list = categoryMap.get(category);
			if(list == null) {
				list = new ArrayList<>();
				categoryMap.put(category, list);
			}
			list.add(p);
		}

		if(categoryMap.size() == 1) {
			getSkinnable().setVisualMode(VisualMode.NAME);
			getSkinnable().setModeSwitcherVisible(false);
			
			if(categoryMap.entrySet().iterator().next().getValue().size() < 5)
				getSkinnable().setSearchBoxVisible(false);
		}

		switch(getSkinnable().visualModeProperty().get()) {
		case CATEGORY_WITH_ACCORDION: 	Accordion accordion = new Accordion();
										for(String category : categoryMap.keySet()) {
											PropertyPane props = new PropertyPane(categoryMap.get(category));
											// Only show non-empty categories 
											if(props.getChildrenUnmodifiable().size() > 0) {
												String catDisplay = 
														category.indexOf("-") != -1 ?
																category.substring(category.indexOf("-") + 1)
																:
																category;
												TitledPane pane = new TitledPane(catDisplay, props);
												pane.setExpanded(true);
												accordion.getPanes().add(pane);
											}
										}
										if(accordion.getPanes().size() > 0) {
											accordion.setExpandedPane(accordion.getPanes().get(0));
										}
										return accordion;

		case CATEGORY: 					VBox noaccordion = new VBox();
										for(String category : categoryMap.keySet()) {
											PropertyPane props = new PropertyPane(categoryMap.get(category));
											if(props.getChildrenUnmodifiable().size() > 0) {
												String catDisplay = 
														category.indexOf("-") != -1 ?
																category.substring(category.indexOf("-") + 1)
																:
																category;
												TitledPane pane = new TitledPane(catDisplay, props);
												pane.setExpanded(true);
												noaccordion.getChildren().add(pane);
											}
										}
										return noaccordion;

		case NAME:
		default: 						return new PropertyPane(getSkinnable().getItems());
		}

	}

	private void addModeHandler() {
		modeButton.setOnMouseClicked((me) -> {
			boolean isArmed = modeButton.isSelected();

			if(isArmed) {
				System.out.println("Mode NAME");
				modeButton.setText("N");
				getSkinnable().visualModeProperty().set(VisualMode.NAME);
			} else {
				System.out.println("Mode CATEGORY");
				modeButton.setText("C");
				getSkinnable().visualModeProperty().set(VisualMode.CATEGORY);
			}
		});
	}

	/*
		private class ActionChangeMode extends Action {
	
			private final Image	CATEGORY_IMAGE	= new Image(PropertySheetSkin.class.getResource("/tmp/format-indent-more.png").toExternalForm());		 //$NON-NLS-1$
			private final Image	NAME_IMAGE		= new Image(PropertySheetSkin.class.getResource("/tmp/format-line-spacing-triple.png").toExternalForm()); //$NON-NLS-1$
	
			public ActionChangeMode(PropertySheet.Mode mode) {
				super(""); //$NON-NLS-1$
				setEventHandler(ae -> getSkinnable().modeProperty().set(mode));
	
				if(mode == Mode.CATEGORY) {
					setGraphic(new ImageView(CATEGORY_IMAGE));
					setLongText("property.sheet.group.mode.bycategory");
				} else if(mode == Mode.NAME) {
					setGraphic(new ImageView(NAME_IMAGE));
					setLongText("property.sheet.group.mode.byname");
				} else {
					setText("???");
				}
			}
	
		}
	*/
	private class PropertyPane extends GridPane {

		public PropertyPane(List<Item> properties) {
			this(properties, 0);
		}

		public PropertyPane(List<Item> properties, int nestingLevel) {
			setVgap(5);
			setHgap(5);
			setPadding(new Insets(5, 15, 5, 15 + nestingLevel * 10));
			getStyleClass().add("property-pane");
			setItems(properties);
		}

		public void setItems(List<Item> properties) {
			getChildren().clear();

			String filter = getSkinnable().itemNameFilterProperty().get();
			filter = filter == null ? "" : filter.trim().toLowerCase();
			int row = 0;

			List<Item> propertiesAlt = new ArrayList<Item>(properties);
			propertiesAlt.sort((a, b) -> a.getName().compareTo(b.getName()));
			for(Item item : propertiesAlt) {

				String itemDisplay = 
						item.getName().indexOf("-") != -1 ?
								item.getName().substring(item.getName().indexOf("-") + 1)
								:
									item.getName();

				// filter properties
				String title = itemDisplay; //item.getName();

				if(!filter.isEmpty() && title.toLowerCase().indexOf(filter) < 0)
					continue;

				// setup property label
				Label label = new Label(title);
				label.setMinWidth(MIN_COLUMN_WIDTH);

				// show description as a tooltip
				String description = item.getDescription();
				if(description != null && !description.trim().isEmpty()) {
					label.setTooltip(new Tooltip(description));
				}

				add(label, 0, row);

				// setup property editor
				Node editor = getEditor(item);

				if(editor instanceof Region) {
					((Region) editor).setMinWidth(MIN_COLUMN_WIDTH);
					((Region) editor).setMaxWidth(Double.MAX_VALUE);
				}
				label.setLabelFor(editor);
				add(editor, 1, row);
				GridPane.setHgrow(editor, Priority.ALWAYS);

				//TODO add support for recursive properties

				row++;
			}

		}

		@SuppressWarnings("unchecked")
		private Node getEditor(Item item) {
			@SuppressWarnings("rawtypes")
			PropertyEditor editor = getSkinnable().getPropertyEditorFactory().call(item);
			if(editor == null) {
				editor = new BasePropertyEditor<Object, TextField>(item, new TextField(), true) {
					{
						getEditor().setEditable(false);
						getEditor().setDisable(true);
					}

					@Override
					protected ObservableValue<Object> getObservableValue() {
						return (ObservableValue<Object>) (Object) getEditor().textProperty();
					}

					@Override
					public void setValue(Object value) {
						getEditor().setText(value == null ? "" : value.toString());
					}
				};
			} else if(!item.isEditable()) {
				editor.getEditor().setDisable(true);
			}
			editor.setValue(item.getValue());
			return editor.getEditor();
		}
	}

}
