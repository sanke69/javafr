package fr.media;

import fr.java.media.Media;
import fr.java.media.MediaPlayer;
import fr.java.sdk.player.PlayerAdapter;

public abstract class RecordPlayerAdapter<T> extends PlayerAdapter<T> implements MediaPlayer<T> {

	public RecordPlayerAdapter(boolean _isInterruptable) {
		super(_isInterruptable);
	}
	public RecordPlayerAdapter(boolean _isInterruptable, double _fps) {
		super(_isInterruptable, _fps);
	}

	@Override
	public abstract Media.Record<T>	media();

}
