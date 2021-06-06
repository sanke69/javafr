package fr.javafx.scene.properties;

import java.util.ArrayList;
import java.util.LinkedList;

import fr.javafx.scene.control.titledpane.TitledBorder;
import fr.javafx.scene.properties.Editor.Plugin;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class PropertiesControl<T> extends Control implements Editor<T> {
	private static final int controlWidth = 360;
	private static final int labelWidth   = 240;
	private static final int rowHeight    = 32;

//	private static record GridPaneColumnProperty(double width, Color color) {}
	private static class GridPaneColumnProperty {
		double width; Color color;
		GridPaneColumnProperty(double _width, Color _color) { width = _width; color = _color; }
		
		double width() { return width; }
		Color  color() { return color; }
	}
	private static final  GridPaneColumnProperty left   = new GridPaneColumnProperty( labelWidth, Color.GRAY  ); 
	private static final  GridPaneColumnProperty right  = new GridPaneColumnProperty( controlWidth - labelWidth, Color.GRAY.brighter() );
	private static final  GridPaneColumnProperty unique = new GridPaneColumnProperty( controlWidth, left.color.interpolate(right.color, 0.5) );

//	public static record Entry(Labeled label, Region control) {
	public static class Entry {
		Labeled label; Region control;

		Entry(Labeled _label, Region _control) 	{ label = _label; control = _control; }
		Entry(Region _control)                  { this((Labeled) null, _control); }
		Entry(String _label, Region _control)   { this(new Label(_label), _control); }
		Entry(PropertiesControl<Void> _submenu) { this((Labeled) null, _submenu); }

		Labeled 				label() 		{ return label; }
		Region  				control() 		{ return control; }

		boolean 				hasLabel()      { return label != null; }

		boolean 				isSubMenu()     { return control instanceof PropertiesControl; }
		PropertiesControl<Void> toSubMenu()     { return (PropertiesControl<Void>) control; }

	}

	class PropertyListPane extends GridPane {
		GridPane content;

		PropertyListPane() {
			super();
		}

	}

	TitledBorder     						border;
	PropertyListPane 						content;

	ObservableList<PropertiesControl.Entry>	entries;

	public PropertiesControl() {
		this(null);
	}
	public PropertiesControl(String _title) {
		super();

		if(_title != null)
			border = new TitledBorder(_title, content = new PropertyListPane());
		else {
			border  = null;
			content = new PropertyListPane();
		}

		entries = FXCollections.observableArrayList(new ArrayList<PropertiesControl.Entry>());
		entries.addListener((ListChangeListener<Entry>) lc -> {
			while(lc.next()) {
				int na = lc.getAddedSize();
				int nr = lc.getRemovedSize();
				int i0 = lc.getFrom(), i1 = lc.getTo();

				if(lc.wasPermutated()) {
					for(int i = i0; i < i1; ++i) {
						int it = lc.getPermutation(i);

					}
				} else if(lc.wasReplaced()) {
					for(int i = i0; i < i1; ++i) {
						;
					}
				} else if(lc.wasUpdated()) {
					for(int i = i0; i < i1; ++i) {
						;
					}
				} else if(lc.wasAdded()) {
					for(Entry e : lc.getAddedSubList()) {
						if(e.isSubMenu())
							addEntry(content, e.toSubMenu());
						else if(e.hasLabel())
							addEntry(content, e.label(), e.control());
						else
							addEntry(content, e.control());
					}
				} else if(lc.wasRemoved()) {
					for(Entry e : lc.getRemoved()) {
						if(e.isSubMenu())
							removeEntry(content, e.toSubMenu());
						else if(e.hasLabel())
							removeEntry(content, e.label(), e.control());
						else
							removeEntry(content, e.control());
					}
				} else {
					System.err.println("Failed to find ");
				}
			}
		});
	}

	@Override
	public Region 						getNode() {
		return this;
	}
	public PropertiesControl<Void> 			getSubMenu(String _title) {
		return entries	.stream()
						.filter(e -> e.isSubMenu() && e.label().getText().compareTo(_title) == 0)
						.findAny()
						.map(Entry::toSubMenu)
						.orElse(null);
	}
	public PropertiesControl<Void> 			addSubPane(String _title) {
		PropertiesControl<Void> subMenu;
		entries.add(new PropertiesControl.Entry(subMenu = new PropertiesControl<Void>(_title)));

		return subMenu;
	}

	public void 						addEntry(Region _control) {
		entries.add(new Entry( _control));
	}
	public void 						addEntry(String _name, Region _control) {
		entries.add(new Entry(_name, _control));
	}
	public void 						addEntry(Labeled _label, Region _control) {
		entries.add(new Entry(_label, _control));
	}

	public void 						removeEntry(String _name) {
		entries .stream()
				.filter(e -> e.hasLabel() && e.label().getText().compareTo(_name) == 0)
				.findFirst()
				.ifPresent(e -> entries.remove(e));
	}
	public void 						removeEntry(Region _control) {
		entries .stream()
		.filter(e -> e.control().equals(_control))
		.findFirst()
		.ifPresent(e -> entries.remove(e));
	}

	protected Skin<PropertiesControl> createDefaultSkin() {
		return new Skin<PropertiesControl>() {

			@Override
			public PropertiesControl getSkinnable() {
				return PropertiesControl.this;
			}

			@Override
			public Node getNode() {
				return border != null ? border : content;
			}

			@Override
			public void dispose() {
				;
			}
			
		};
	}

	private static	void 				addEntry(GridPane _pane, Region _control) {
		final int nextRow = _pane.getRowCount();

		_control        . setBackground(new Background(new BackgroundFill(unique.color(), CornerRadii.EMPTY, Insets.EMPTY)));
		_control        . setMinWidth   (unique.width());
		_control        . setPrefWidth  (unique.width());
		_control        . setMaxWidth   (unique.width());
		_control        . setMinHeight  (rowHeight);
		_control        . setMaxHeight  (5 * rowHeight);

		_pane.add(_control, 0, nextRow, 2, 1);
	}
	private static 	void 				addEntry(GridPane _pane, Region _label, Region _control) {
		final int nextRow = _pane.getRowCount();

		_label          . setBackground (new Background(new BackgroundFill(left.color(), CornerRadii.EMPTY, Insets.EMPTY)));
		_label          . setMinWidth   (left.width());
		_label          . setPrefWidth  (left.width());
		_label          . setMaxWidth   (left.width());
		_label          . setMinHeight  (rowHeight);
		_label          . setPrefHeight (rowHeight);
		_label          . setMaxHeight  (rowHeight);

		_control        . setBackground(new Background(new BackgroundFill(right.color(), CornerRadii.EMPTY, Insets.EMPTY)));
		_control        . setMinWidth   (right.width());
		_control        . setPrefWidth  (right.width());
		_control        . setMaxWidth   (right.width());
		_control        . setMinHeight  (rowHeight);
		_control        . setMaxHeight  (rowHeight);

		_pane.add(_label,   0, nextRow, 1, 1);
		_pane.add(_control, 1, nextRow, 1, 1);
	}

	private static	void 				removeEntry(GridPane _pane, Region _control) {
		_pane.getChildren().remove(_control);
	}
	private static 	void 				removeEntry(GridPane _pane, Region _label, Region _control) {
		_pane.getChildren().remove(_label);
		_pane.getChildren().remove(_control);
	}

	/*** PlugIns ***/
	private final ObservableList<Plugin> 	 plugins        = FXCollections.observableList(new LinkedList<>());
	private final ListChangeListener<Plugin> pluginsChanged = (change) -> {
		while(change.next()) {
			change.getRemoved().forEach(this::pluginRemoved);
			change.getAddedSubList().forEach(this::pluginAdded);
		}
	};

	public final ObservableList<Plugin> 	getPlugins() {
		return plugins;
	}

	private void 							pluginAdded(Plugin _plugin) {
		_plugin.setEditor(this);
	}
	private void 							pluginRemoved(Plugin _plugin) {
		_plugin.setEditor(null);
	}
	@Override
	public ObservableValue<T> valueProperty() {
		// TODO Auto-generated method stub
		return null;
	}

}
