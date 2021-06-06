package fr.java.media;

import java.io.IOException;

import fr.java.player.Player;

public interface MediaPlayer<T> extends Player<T> {

	public Media.Infos	media();

	public void 		openMedia(String _filename) 	throws InterruptedException, IOException;
	public void 		closeMedia() 					throws InterruptedException, IOException;

}
