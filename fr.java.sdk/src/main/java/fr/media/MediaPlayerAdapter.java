package fr.media;

import fr.java.media.MediaPlayer;
import fr.java.sdk.player.PlayerAdapter;

public abstract class MediaPlayerAdapter<T> extends PlayerAdapter<T> implements MediaPlayer<T> {

	public MediaPlayerAdapter(boolean _isInterruptable) {
		super(_isInterruptable);
	}
	public MediaPlayerAdapter(boolean _isInterruptable, double _fps) {
		super(_isInterruptable, _fps);
	}

}
