package fr.javafx.sdk.controls.media;

import fr.java.media.Media;
import fr.java.media.image.Image;
import fr.javafx.sdk.controls.media.skins.MediaViewerDefaultSkin;
import fr.javafx.sdk.controls.media.skins.MediaViewerXRasterSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class MediaViewer extends Control {

	public static enum Style { simple, advanced };

	private SimpleObjectProperty<Media> playable = new SimpleObjectProperty<Media>();

	private Style style;
	
	public MediaViewer() {
		this(Style.simple);
	}
	public MediaViewer(Style _style) {
		super();
		setStyle("-fx-background-color: green;");

		switch(style = _style) {
		case simple   : setSkin(new MediaViewerDefaultSkin(this)); break;
		case advanced : setSkin(new MediaViewerXRasterSkin(this)); break;
		default       : break;
		}
	}
	public MediaViewer(int _width, int _height, Style _style) {
		this(_style);
		setPrefSize(_width, _height);
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new MediaViewerDefaultSkin(this);
	}

	public ObjectProperty<Media> playableProperty() {
		return playable;
	}

	public Media getPlayable() {
		return playable.get();
	}
	public void setPlayable(Image<?> _playable) {
		playable.set(_playable);
	}

}
