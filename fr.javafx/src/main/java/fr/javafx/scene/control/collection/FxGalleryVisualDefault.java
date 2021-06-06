package fr.javafx.scene.control.collection;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

import fr.javafx.behavior.Visual;

public class FxGalleryVisualDefault<T, ITEM extends FxGallery.Item<T>> extends ScrollPane implements Visual<FxGallery<T, ITEM>> {
	private final FxGallery<T, ITEM> 	control;
	private final TilePane 				tile;

	public FxGalleryVisualDefault(FxGallery<T, ITEM> _control) {
		super();
		control = _control;

		setStyle("-fx-background-color: DAE6F3;");
		setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
		setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
		setFitToWidth(true);

		tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);
		tile.setVgap(15);
		tile.getChildren().addAll(getSkinnable().getItems());

		getSkinnable().itemsProperty().addListener((ListChangeListener<? super ITEM>) change -> {
			while(change.next())
		        if(change.wasAdded()) {
		            tile.getChildren().addAll(change.getAddedSubList());
//		            change.getAddedSubList() . forEach(tile.getChildren()::add);
		        } else if(change.wasRemoved()) {
		            tile.getChildren().removeAll(change.getRemoved());
//		            change.getRemoved()      . forEach(tile.getChildren()::remove);
		        }
	    });

		setContent(tile);
	}

	@Override
	public FxGallery<T, ITEM> 	getSkinnable() {
		return control;
	}

	@Override
	public Node 				getNode() {
		return this;
	}

}
