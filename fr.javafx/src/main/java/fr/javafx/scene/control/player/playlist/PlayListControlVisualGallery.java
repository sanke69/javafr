package fr.javafx.scene.control.player.playlist;

import java.util.List;

import fr.javafx.scene.control.collection.FxGallery;
import fr.javafx.utils.FxImageUtils;
import fr.media.image.utils.BufferedImages;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class PlayListControlVisualGallery<P, ITEM extends Node> extends PlayListControlVisualBase<P> implements PlayListControl.VisualGallery<P> {

	static class Thumbnail<P> extends FxGallery.Item<P> {
        Rectangle backbround;
        ImageView graphic;

		Thumbnail(double _width, P _playable) {
			super(_width, _playable);

//			String src = _playable.getSourceName().orElse("NO SRC");
			Image  img = FxImageUtils.toFX( BufferedImages.notFound((int) _width, (int) _width) );

			graphic = new ImageView(img);
			graphic.setFitWidth(_width - 10);
			graphic.setFitHeight(_width - 10);

			getChildren().add(graphic);
		}

	}

	FxGallery<P, Thumbnail<P>> 	gallery;

	public PlayListControlVisualGallery(PlayListControl<P> _control) {
		super(_control);

		getSkinnable().playlistProperty().addListener((ListChangeListener<? super P>) change -> {
			while(change.next())
		        if(change.wasAdded()) {
		            change.getAddedSubList()
		            		.forEach(getGallery()::add);
		        } else if(change.wasRemoved()) {
		            change.getRemoved()
		            		.forEach(getGallery()::remove);
		        }
	    });/*
		getGallery().valuesProperty().addListener((ListChangeListener<? super Item<P>>) change -> {
			while(change.next())
		        if(change.wasAdded()) {
		            change.getAddedSubList()
		            		.forEach(getGallery()::add);
		        } else if(change.wasRemoved()) {
		            change.getRemoved()
		            		.forEach(getGallery()::remove);
		        }
	    });*/
		
//		ObservableList<Warning> warningList = ...;
	}

	@Override
	public FxGallery<P, ?> 				getGallery() {
		if(gallery == null)
			gallery = new FxGallery<P, Thumbnail<P>>(123, Thumbnail::new);
		return gallery;
	}

	@Override
	public void 						enableMultiSelection(boolean _true) {
		getGallery().enableMultiSelection(_true);
	}

	@Override
	public P 							getCurrent() {
		return getGallery().getCurrent();
	}

	@Override
	public List<P> 						getSelection() {
		return getGallery().getSelection();
	}

	@Override
	public void 						select(Integer... idx) {
		getGallery().select(idx);
	}
	@Override
	public void 						deselect(Integer... idx) {
		getGallery().deselect(idx);
	}

	@Override
	public void 						select(P... idx) {
		getGallery().select(idx);
	}
	@Override
	public void 						deselect(P... idx) {
		getGallery().deselect(idx);
	}

	@Override
	public void 						selectAll() {
		getGallery().selectAll();
	}
	@Override
	public void 						deselectAll() {
		getGallery().deselectAll();
	}

	@Override
	public List<P> 						getValues() {
		return getGallery().getValues();
	}

	@Override
	public void 						clearSelection() {
		getGallery().clearSelection();
	}
	@Override
	public void 						inverseSelection() {
		getGallery().inverseSelection();
	}

	@Override
	public ObservableObjectValue<P> 	currentProperty() {
		return getGallery().currentProperty();
	}

	@Override
	public void 						setCurrent(P _item) {
		getGallery().setCurrent(_item);
	}

	@Override
	public ObservableObjectValue<P> 	selectedProperty() {
		return getGallery().selectedProperty();
	}

	@Override
	public P 							getSelected() {
		return getGallery().getSelected();
	}

	@Override
	public ObservableList<P> 			selectionProperty() {
		return getGallery().selectionProperty();
	}

}
