package fr.javafx.sdk.controls.media;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import fr.java.player.PlayList;
import fr.javafx.sdk.controls.media.skins.MediaPlayListControllerDefaultSkin;

public class MediaPlayListController extends Control {
	
	public static enum Style { gallery, list };
	
	private PlayList 	playlist;
	private IntegerProperty	currentIndex;

	public MediaPlayListController() {
		this(null);
	}
	public MediaPlayListController(PlayList _playlist) {
		super();
		playlist     = _playlist;
		currentIndex = new SimpleIntegerProperty(0); 
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new MediaPlayListControllerDefaultSkin(this);
	}

	boolean isValidMediaFile(File _file) {
		String ext = _file.getAbsolutePath().substring(_file.getAbsolutePath().lastIndexOf("."));

		if(ext.compareTo(".mp3") == 0) return true;
		if(ext.compareTo(".mp4") == 0) return true;
		if(ext.compareTo(".wav") == 0) return true;
		if(ext.compareTo(".ogg") == 0) return true;

		return false;
	}

}
